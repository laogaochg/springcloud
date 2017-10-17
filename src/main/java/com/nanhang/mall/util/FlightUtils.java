package com.nanhang.mall.util;

import com.alibaba.fastjson.JSON;
import com.nanhang.mall.dto.CityCode;
import com.nanhang.mall.dto.FlightQueryDto;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: LaoGaoChuang
 * @Date : 2017/10/11 14:46
 */
public class FlightUtils {
    /**
     * 世界城市三字码
     * key 三
     * value 中文名字
     */
    public static final Map<String, CityCode> cityCode = new HashMap<>();

    private static final String SHOPPING_XML_STRING =
            "<?xml version=\"1.0\" encoding=\"GB2312\" ?>" +
                    "<page>" +
                    "    <QUERYCONDITION>" +
                    "        <VIPNUM></VIPNUM><!--大客户号，可为空-->" +
                    "        <ENTRYNO>0</ENTRYNO><!--0：国际；1：国内-->" +
                    "        <SEGTYPE>S</SEGTYPE><!--S：单程；R：来回程或者多程-->" +
                    "        <ADULTNUM>1</ADULTNUM><!--成人数-->" +
                    "        <CHILDNUM>0</CHILDNUM><!--儿童数-->" +
                    "        <INFANTNUM>0</INFANTNUM><!--婴儿数-->" +
                    "        <CABINORDER>1,2,3,4</CABINORDER><!--仓位(1代表头等舱，2代表公务舱，3代表明珠经济舱，4代表经济舱)-->" +
                    "        <CITIES>" +
                    "            <CITY>" +
                    "                <DEPCITY>replaceDEPCITY</DEPCITY><!--出发城市三字码-->" +
                    "                <ARRCITY>replaceARRCITY</ARRCITY><!--到达城市三字码-->" +
                    "            </CITY>" +
                    "        </CITIES>" +
                    "        <DATES>" +
                    "            <FLIGHTDATE>" +
                    "                <DAY>replaceDEPDATETIME</DAY><!--出发日期-->" +
                    "                <CLOCK>0001-2359</CLOCK><!--默认0001-2359-->" +
                    "            </FLIGHTDATE>" +
                    "        </DATES>" +
                    "    </QUERYCONDITION>" +
                    "</page>";
    /**
     * 合作伙伴密码
     */
    private static String PASSWORD;
    /**
     * 合作伙伴密码
     */
    private static String USER_NAME;
    /**
     * 请求的URL
     */
    private static String SHOPPING_URL;
    /**
     * 公钥
     */
    private static RSAPublicKey PUBLIC_KEY;

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream cityCodeInput = classLoader.getResourceAsStream("CityCode.json");
        InputStream proertiesInput = classLoader.getResourceAsStream("flightConfig.properties");
        InputStream publicKeyInput = classLoader.getResourceAsStream("public.dat");
        try {
            //公钥
            PUBLIC_KEY = (RSAPublicKey) new ObjectInputStream(publicKeyInput).readObject();
            //配置文件
            Properties properties = new Properties();
            properties.load(proertiesInput);
            USER_NAME = String.valueOf(properties.get("USER_NAME"));
            PASSWORD = String.valueOf(properties.get("PASSWORD"));
            SHOPPING_URL = String.valueOf(properties.get("SHOPPING_URL"));
            //城市三字码
            String jsonString = StreamUtils.copyToString(cityCodeInput, Charset.forName("UTF-8"));
            List<CityCode> cityCodes = JSON.parseArray(jsonString, CityCode.class);
            for (CityCode code : cityCodes) {
                cityCode.put(code.getCode().trim(), code);
            }
            cityCodeInput.close();
            proertiesInput.close();
            publicKeyInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String handleShoppingRequest(FlightQueryDto qo) throws Exception {
        //加密身份验证
        byte[] encbyte = new RSA().encrypt(PASSWORD, PUBLIC_KEY);
        String param = RSA.toHexString(encbyte);
        //调用的URL
        String url = String.format(SHOPPING_URL, USER_NAME, param);
        System.out.println("USER_NAME = " + USER_NAME);
        System.out.println("PASSWORD = " + PASSWORD);
        //得到入参xml明文
        String xmlString = getXmlString(qo);
        //对XML进行加密加压
        Encrypt encrypt = new Encrypt();
        byte[] encoded = Encrypt.encryptMode(xmlString.getBytes());
        String encodeInputString = encrypt.encode(encoded);
        byte[] xmlBytes = GZipUtil.gzip(encodeInputString.getBytes("UTF-8"));
        //发送请求得到返回数据
        byte[] httpResult = handleRequest(url, xmlBytes);
        //对返回的xmlString进行解压解密
        String zipString = new String(GZipUtil.unzip(httpResult), "UTF-8");
        Encrypt resultEncrypt = new Encrypt();
        byte[] srcBytes = Encrypt.getInstance().decryptMode(resultEncrypt.decode(zipString));
        return new String(srcBytes, "UTF-8");
    }

    /**
     * 得到输入参数明文
     */
    public static String getXmlString(FlightQueryDto qo) {
        String result = SHOPPING_XML_STRING;
        String depCity = StringUtils.isNotBlank(qo.getDepCity()) ? qo.getDepCity() : "";
        String arrCity = StringUtils.isNotBlank(qo.getArrCity()) ? qo.getArrCity() : "";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");//2012-06-12 08:20
        String depDateTime = qo.getDepDateTime() == null ? "" : format.format(qo.getDepDateTime());
        result = result.replace("replaceDEPCITY", depCity);//出发城市三字码
        result = result.replace("replaceARRCITY", arrCity);//到达城市三字码
        result = result.replace("replaceDEPDATETIME", depDateTime);//出发时间
        return result;
    }


    private static byte[] handleRequest(String url, byte[] xmlBytes) throws Exception {
        HttpClient httpclient = new HttpClient();
        PostMethod httpPost = new PostMethod(url);
        RequestEntity entity = new ByteArrayRequestEntity(xmlBytes);
        httpPost.setRequestEntity(entity);
        httpPost.setRequestHeader("Content-type", "text/xml;charset=UTF-8");
        //long a = System.currentTimeMillis();
        httpclient.executeMethod(httpPost);
        //System.out.println("请求花费时间" + (System.currentTimeMillis() - a));
        return httpPost.getResponseBody();
    }
}

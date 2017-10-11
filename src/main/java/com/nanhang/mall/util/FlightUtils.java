package com.nanhang.mall.util;

import com.alibaba.fastjson.JSON;
import com.nanhang.mall.dto.CityCode;
import com.nanhang.mall.dto.FlightQueryDto;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static final Map<String, String> cityCode = new HashMap<>();

    private static String xmlString;
    /**
     * 合作伙伴密码
     */
    private static String password = "asdsd";
    /**
     * 请求的URL
     */
    private static String URL = "http://127.0.0.1/E-UNION/data/order/getAvPrice.do?type=TAOBAO&param=";

    static {
        InputStream cityCodeInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("CityCode.json");
        InputStreamReader cityCodeReader = new InputStreamReader(cityCodeInputStream, Charset.forName("UTF-8"));
        InputStream xmlStringInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("FlightQueryParamsTemplate.xml");
        InputStreamReader xmlStringReader = new InputStreamReader(xmlStringInputStream, Charset.forName("UTF-8"));
        try {
            String s = FileCopyUtils.copyToString(cityCodeReader);
            xmlString = FileCopyUtils.copyToString(xmlStringReader);
            List<CityCode> cityCodes = JSON.parseArray(s, CityCode.class);
            for (CityCode code : cityCodes) {
                cityCode.put(code.getCode(), code.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        FlightQueryDto qo = new FlightQueryDto();
        String s = handleOrderRequest(qo);
        System.out.println("s = " + s);
    }

    public static String handleOrderRequest(FlightQueryDto qo) throws Exception {
        String result;
        //加密身份验证
        RSA rsa = new RSA();
        RSAPublicKey publicKey = (RSAPublicKey) rsa.readFromFile("D:\\nanhang\\workspace\\FlightCenter\\src\\main\\resources\\public.dat");
        byte[] encbyte = rsa.encrypt(password, publicKey);
        String param = RSA.toHexString(encbyte);
        //调用的URL
        String url = URL + param;
        //得到入参xml明文
        String xmlString = getXmlString(qo);
        //对XML进行加密加压
        Encrypt encrypt = new Encrypt();
        byte[] encoded = Encrypt.encryptMode(xmlString.getBytes());
        String encodeInputString = encrypt.encode(encoded);
        byte[] zipInputString = GZipUtil.gzip(encodeInputString.getBytes("UTF-8"));
        xmlString = new String(zipInputString, "UTF-8");
        //发送请求得到返回数据
        byte[] httpResult = handleRequest(url, xmlString);
        //对返回的xmlString进行解压解官
        result = decode(httpResult);
        return result;
    }

    /**
     * 得到输入参数明文
     *
     * @param qo
     * @return
     */
    private static String getXmlString(FlightQueryDto qo) {
        String result;
        String depCity = StringUtils.isNotBlank(qo.getDepCity()) ? qo.getDepCity() : "";
        String arrCity = StringUtils.isNotBlank(qo.getArrCity()) ? qo.getArrCity() : "";
        String flightNo = StringUtils.isNotBlank(qo.getFlightNo()) ? qo.getFlightNo() : "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");//2012-06-12 08:20
        String depDateTime = qo.getDepDateTime() == null ? "" : format.format(qo.getDepDateTime());
        String arrDateTime = qo.getArrDateTime() == null ? "" : format.format(qo.getArrDateTime());
        result = xmlString.replace("replaceDEPCITY", depCity);//出发城市三字码
        result = result.replace("replaceARRCITY", arrCity);//到达城市三字码
        result = result.replace("replaceDEPDATETIME", depDateTime);//出发时间
        result = result.replace("replaceARRDATETIME", arrDateTime);//到达时间
        result = result.replace("replaceFLIGHTNO", flightNo);//到达时间
        return result;
    }

    private static String encryptXmlString(String xml) throws IOException {
        String result;
        //所有请求接口的参数数据进行3DES加密加压处理
        //加密：
        Encrypt encrypt = new Encrypt();
        byte[] encoded = Encrypt.encryptMode(xml.getBytes());
        String encodeInputString = encrypt.encode(encoded);
        //加压
        byte[] zipInputString = GZipUtil.gzip(encodeInputString.getBytes("UTF-8"));
        result = new String(zipInputString, "UTF-8");
        return result;
    }

    /**
     * 解密 解压
     */
    private static String decode(byte[] httpResultString) throws IOException {
        //解密解压
        String zipString = new String(GZipUtil.unzip(httpResultString), "UTF-8");
        Encrypt encrypt = new Encrypt();
        byte[] srcBytes = Encrypt.getInstance().decryptMode(encrypt.decode(zipString));
        return new String(srcBytes, "UTF-8");
    }

    private static byte[] handleRequest(String url, String xmlDom) throws Exception {
        HttpClient httpclient = new HttpClient();
        String result = null;
        PostMethod httpPost = new PostMethod(url);
        RequestEntity entity = new StringRequestEntity(xmlDom, "text/xml", "UTF-8");
        httpPost.setRequestEntity(entity);
        httpPost.setRequestHeader("Content-type", "text/xml;charset=UTF-8");
        httpclient.executeMethod(httpPost);
        byte[] encodeString = httpPost.getResponseBody();
        return encodeString;
    }
}

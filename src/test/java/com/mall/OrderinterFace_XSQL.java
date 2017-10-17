package com.mall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import com.nanhang.mall.dto.FlightQueryDto;
import com.nanhang.mall.service.impl.FlightServiceImpl;
import com.nanhang.mall.util.Encrypt;
import com.nanhang.mall.util.FlightUtils;
import com.nanhang.mall.util.GZipUtil;
import com.nanhang.mall.util.RSA;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.time.DateUtils;


public class OrderinterFace_XSQL {

    public static void main(String[] args) throws Exception {

        //国际航班Pricing接口(B2C) URL
//        String url = "http://10.79.0.86:9080/E-UNION/data/order/international/priceInfoInter.do?type=CZSHOP&param=";
        //国际航班票价查询接口
        String url = "http://10.79.0.86:9080/E-UNION/data/avprice/getAvPrice.do?type=CZSHOP&param=";
        //得到xml明文
        FlightQueryDto qo = new FlightQueryDto();
        qo.setArrCity("NYC");
        qo.setDepCity("CAN");
        qo.setDepDateTime(DateUtils.addDays(new Date(), 1));
        //国际航班Pricing接口(B2C) XML
//        String xmlString = FlightUtils.getXmlString(qo);
        String xmlString = FlightUtils.getXmlString(qo);
        System.out.println("xmlString = " + xmlString);

        String s = request(url + getPassword(), xmlString);
        System.out.println(s);
    }

    private static String getPassword() throws Exception {
        RSA rsa = new RSA();
        RSAPublicKey publickKey = (RSAPublicKey) rsa.readFromFile("D:\\nanhang\\workspace\\FlightCenter\\src\\main\\resources\\public.dat");
        byte[] encbyte = rsa.encrypt("", publickKey);
        return RSA.toHexString(encbyte);
    }

    private static String encodeXmlString(String xmlString) throws IOException {
        //对XML进行加密加压
        Encrypt encrypt = new Encrypt();
        byte[] encoded = Encrypt.encryptMode(xmlString.getBytes());
        String encodeInputString = encrypt.encode(encoded);
        byte[] zipInputString = GZipUtil.gzip(encodeInputString.getBytes("UTF-8"));
        xmlString = new String(zipInputString, "UTF-8");
        return xmlString;
    }

    /**
     * 创建订单
     *
     * @return String 调用接口创建订单的返回结果
     */
    public static String handleOrderRequest(String XML, String URL) {
        String requestData = "";
        try {
            RSA rsa = new RSA();
            RSAPublicKey publickKey = (RSAPublicKey) rsa.readFromFile("D:\\nanhang\\workspace\\FlightCenter\\src\\main\\resources\\public.dat");
            byte[] encbyte = rsa.encrypt("", publickKey);
            String password = RSA.toHexString(encbyte);
            String url = URL + password;
            requestData = requestXSQL(url, XML);
            //System.out.println("-真实数据：" + requestData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestData;
    }

    public static String requestXSQL(String url, String xmlDom) throws Exception {
        HttpClient httpclient = null;
        String result = null;
        httpclient = new HttpClient();
        PostMethod httpPost = new PostMethod(url);
        if (xmlDom == null) {
            try {
                httpclient.executeMethod(httpPost);
                byte[] encodeString = httpPost.getResponseBody();
                System.out.println("传输数据：" + new String(encodeString, "UTF-8"));
                // 解压
                GZipUtil gzip = new GZipUtil();
                String zipString = new String(gzip.unzip(encodeString), "UTF-8");
                Encrypt encrypt = new Encrypt();
                byte[] srcBytes = Encrypt.getInstance().decryptMode(encrypt.decode(zipString));
                result = new String(srcBytes, "UTF-8");
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            try {
                RequestEntity entity = new StringRequestEntity(xmlDom.toString(), "text/xml", "UTF-8");
                httpPost.setRequestEntity(entity);
                httpPost.setRequestHeader("Content-type", "text/xml;charset=UTF-8");

                httpclient.executeMethod(httpPost);
                InputStream inputStream = httpPost.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                result = stringBuffer.toString();
                System.out.println("result:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 调用接口通讯实现方法
     */
    public static String request(String url, String xmlDom) throws Exception {
        HttpClient httpclient = null;
        String result = null;
        httpclient = new HttpClient();
        //使用匿名代理
//		httpclient.getHostConfiguration().setProxy("10.101.173.91", 808);
//		httpclient.getParams().setAuthenticationPreemptive(true);

        PostMethod httpPost = new PostMethod(url);
        Encrypt encrypt = new Encrypt();
        byte[] encoded = Encrypt.getInstance().encryptMode(xmlDom.toString().getBytes("UTF-8"));
        String encodeInputString = encrypt.encode(encoded);
        byte[] srcBytess = Encrypt.getInstance().decryptMode(encrypt.decode(encodeInputString));
        String bizBindMsg = new String(srcBytess, "UTF-8");
        byte[] zipInputString = GZipUtil.gzip(encodeInputString.getBytes("UTF-8"));
        RequestEntity entity = new ByteArrayRequestEntity(zipInputString);
        httpPost.setRequestEntity(entity);
        httpPost.setRequestHeader("Content-type", "text/xml;charset=UTF-8");
        httpclient.executeMethod(httpPost);
        byte[] encodeString = httpPost.getResponseBody();
        String zipString = new String(GZipUtil.unzip(encodeString), "UTF-8");
        byte[] srcBytes = Encrypt.getInstance().decryptMode(encrypt.decode(zipString));
        result = new String(srcBytes, "UTF-8");
        return result;

    }
}

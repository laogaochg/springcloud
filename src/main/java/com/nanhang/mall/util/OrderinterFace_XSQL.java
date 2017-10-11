package com.nanhang.mall.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;


public class OrderinterFace_XSQL {
    public static void main(String[] args) throws Exception {
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
            RSAPublicKey publicKey = (RSAPublicKey) rsa.readFromFile("D:\\nanhang\\workspace\\FlightCenter\\src\\main\\resources\\public.dat");
            String password = "合作伙伴密码";
            byte[] encbyte = rsa.encrypt(password, publicKey);
            //加密参数
            String param = RSA.toHexString(encbyte);
            //调用的URL
            URL = "http://127.0.0.1/E-UNION/data/order/getAvPrice.do?type=TAOBAO&param=";
            String url = URL + param;
            requestData = requestXSQL(url, XML);
            System.out.println("-真实数据：" + requestData);
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
                RequestEntity entity = new StringRequestEntity(xmlDom, "text/xml", "UTF-8");
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
}

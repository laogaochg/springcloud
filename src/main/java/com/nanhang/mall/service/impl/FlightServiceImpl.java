package com.nanhang.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.bjucloud.common.ExecuteResult;
import com.nanhang.mall.dto.CityCode;
import com.nanhang.mall.dto.FlightDto;
import com.nanhang.mall.dto.FlightQueryDto;
import com.nanhang.mall.service.FlightService;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: LaoGaoChuang
 * @Date : 2017/10/6 9:44
 */
@Service
public class FlightServiceImpl implements FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);


    @Override
    public ExecuteResult<List<FlightDto>> queryFlight(FlightQueryDto qo) {
        ExecuteResult<List<FlightDto>> result = new ExecuteResult<>();
        try {
            String httpResult = "";
            result = this.parseXml(httpResult);
            return result;
        } catch (Exception e) {
            result.getErrorMessages().add("decode or parseXml throw Exception :" + e.getMessage());
            logger.error("decode or parseXml throw Exception :" + e);
            return result;
        }
    }


    private ExecuteResult<List<FlightDto>> parseXml(String xmlString) throws Exception {
        ExecuteResult<List<FlightDto>> result = new ExecuteResult<>();
        List<FlightDto> data = new ArrayList<>();
        result.setResult(data);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));
        Element rootElement = document.getRootElement();
        List<Element> pricedItinerary = rootElement.elements("pricedItinerary");
        //证明出错了
        if (pricedItinerary == null || pricedItinerary.size() == 0) {
            //航班信息，航段组合
            List<Element> messageEls = rootElement.elements("MESSAGE");
            for (Element messageEl : messageEls) {
                result.getErrorMessages().add(messageEl.getTextTrim());
            }
            return result;
        }
        for (Element subele : pricedItinerary) {
            //航班信息，航段组合
            List<Element> flightSegments = subele.elements("flightSegments");
            for (Element flightSegmentEle : flightSegments) {
                List<Element> flightSegmentEles = flightSegmentEle.elements("flightSegment");
                for (Element flightSegment : flightSegmentEles) {
                    FlightDto flight = new FlightDto();
                    //到达城市三字码
                    flight.setArrCity(flightSegment.element("departureLocation").getTextTrim());
                    //出发城市三字码
                    flight.setDepCity(flightSegment.element("arrivalLocation").getTextTrim());
                    //航班号
                    flight.setFlightNo(flightSegment.element("flightNumber").getTextTrim());
                    //2012-05-27T21:05
                    //到达时间
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    String arrDateString = flightSegment.element("arrivalDateTime").getTextTrim();
                    arrDateString = arrDateString.replace("T", " ");
                    Date arrDateTime = dateFormat.parse(arrDateString);
                    flight.setArrDateTime(arrDateTime);
                    //出发时间
                    String depDateString = flightSegment.element("departureDateTime").getTextTrim();
                    depDateString = depDateString.replace("T", " ");
                    Date depDateTime = dateFormat.parse(depDateString);
                    flight.setDepDateTime(depDateTime);
                    data.add(flight);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        FlightServiceImpl a = new FlightServiceImpl();
        ExecuteResult<List<FlightDto>> listExecuteResult = a.parseXml("<?xml version = '1.0' encoding = 'utf-8'?><page><MESSAGE>校验Token失败</MESSAGE></page>");
        System.out.println("listExecuteResult = " + listExecuteResult.getErrorMessages());
    }

}

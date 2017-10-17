package com.nanhang.mall.service.impl;

import com.bjucloud.common.ExecuteResult;
import com.nanhang.mall.dto.CityCode;
import com.nanhang.mall.dto.FlightDto;
import com.nanhang.mall.dto.FlightQueryDto;
import com.nanhang.mall.service.FlightService;
import com.nanhang.mall.util.FlightUtils;
import com.nanhang.mall.util.NanHangExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: LaoGaoChuang
 * @Date : 2017/10/6 9:44
 */
@Service
public class FlightServiceImpl implements FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

    @Override
    public CityCode queryCityByCode(String code) {
        return FlightUtils.cityCode.get(code);
    }

    @Override
    public ExecuteResult<List<FlightDto>> queryFlight(FlightQueryDto qo) {
        ExecuteResult<List<FlightDto>> result = new ExecuteResult<>();
        if (StringUtils.isBlank(qo.getArrCity())) result.getErrorMessages().add("到达城市不能为空");
        if (StringUtils.isBlank(qo.getDepCity())) result.getErrorMessages().add("出发城市不能为空");
        if (qo.getDepDateTime() == null) result.getErrorMessages().add("出发时间不能为空");
        if (result.getErrorMessages().size() != 0) return result;
        try {
            //发送请求
            String httpResult = FlightUtils.handleShoppingRequest(qo);
            //解析返回的xml
            result = this.parseShoppingXml(httpResult);
            //设置出发城市和到达城市的中文名
            if (result.getResult().size() != 0) {
                for (FlightDto flight : result.getResult()) {
                    System.out.println(FlightUtils.cityCode.get(flight.getArrCity()));
                    flight.setArrCityName(FlightUtils.cityCode.get(flight.getArrCity()).getName());
                    flight.setDepCityName(FlightUtils.cityCode.get(flight.getDepCity()).getName());
                }
            }
            return result;
        } catch (Exception e) {
            String exception = NanHangExceptionUtils.getStackTraceAsString(e);
            result.getErrorMessages().add(exception);
            logger.error("decode or parseXml throw Exception :" + exception);
            return result;
        }
    }


    private ExecuteResult<List<FlightDto>> parseShoppingXml(String xmlString) throws Exception {
        ExecuteResult<List<FlightDto>> result = new ExecuteResult<>();
        List<FlightDto> data = new ArrayList<>();
        result.setResult(data);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));
        Element rootElement = document.getRootElement();
        List<Element> pricedItinerary = rootElement.elements("FLIGHTS");
        //证明出错了
        if (pricedItinerary == null || pricedItinerary.size() == 0) {
            //错误信息
            List<Element> messageEls = rootElement.elements("MESSAGE");
            for (Element messageEl : messageEls) {
                result.getErrorMessages().add(messageEl.getTextTrim());
            }
            return result;
        }
        for (Element flightSegmentEle : pricedItinerary) {
            List<Element> SEGMENTs = flightSegmentEle.elements("SEGMENT");
            for (Element SEGMENT : SEGMENTs) {
                List<Element> DATEFLIGHTs = SEGMENT.elements("DATEFLIGHT");
                for (Element DATEFLIGHT : DATEFLIGHTs) {
                    List<Element> FLIGHTs = DATEFLIGHT.elements("FLIGHT");
                    for (Element FLIGHT : FLIGHTs) {
                        FlightDto flight = new FlightDto();
                        //到达城市三字码
                        flight.setDepCity(SEGMENT.element("DEPCITY").getTextTrim());
                        //出发城市三字码
                        flight.setArrCity(SEGMENT.element("ARRCITY").getTextTrim());
                        //航班号
                        flight.setFlightNo(FLIGHT.element("FLIGHTNO").getTextTrim());
                        //2012-05-27T21:05
                        //到达时间
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        String arrDateString = FLIGHT.element("ARRTIME").getTextTrim();
                        arrDateString = arrDateString.replace("T", " ");
                        Date arrDateTime = dateFormat.parse(arrDateString);
                        flight.setArrDateTime(arrDateTime);
                        //出发时间
                        String depDateString = FLIGHT.element("DEPTIME").getTextTrim();
                        depDateString = depDateString.replace("T", " ");
                        Date depDateTime = dateFormat.parse(depDateString);
                        flight.setDepDateTime(depDateTime);
                        data.add(flight);
                    }
                }
            }
        }
        return result;
    }


}

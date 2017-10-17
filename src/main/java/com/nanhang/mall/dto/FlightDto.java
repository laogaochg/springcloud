package com.nanhang.mall.dto;

import java.util.Date;

/**
 * @Author: LaoGaoChuang
 * @Date : 2017/10/6 9:16
 * 航班数据封装
 */
public class FlightDto {
    /**
     * 出发城市三字码
     */
    private String depCity;
    /**
     * 到达城市三字码
     */
    private String arrCity;
    /**
     * 出发时间
     */
    private Date depDateTime;
    /**
     * 到达时间
     */
    private Date arrDateTime;
    /**
     * 航班号
     */
    private String flightNo;

    /**
     * 出发城市（中文）
     */
    private String depCityName;
    /**
     * 到达城市（中文）
     */
    private String arrCityName;

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    public String getArrCity() {
        return arrCity;
    }

    public void setArrCity(String arrCity) {
        this.arrCity = arrCity;
    }

    public Date getDepDateTime() {
        return depDateTime;
    }

    public void setDepDateTime(Date depDateTime) {
        this.depDateTime = depDateTime;
    }

    public Date getArrDateTime() {
        return arrDateTime;
    }

    public void setArrDateTime(Date arrDateTime) {
        this.arrDateTime = arrDateTime;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getDepCityName() {
        return depCityName;
    }

    public void setDepCityName(String depCityName) {
        this.depCityName = depCityName;
    }

    public String getArrCityName() {
        return arrCityName;
    }

    public void setArrCityName(String arrCityName) {
        this.arrCityName = arrCityName;
    }

    @Override
    public String toString() {
        return "FlightDto{" +
                "depCity='" + depCity + '\'' +
                ", arrCity='" + arrCity + '\'' +
                ", depDateTime=" + depDateTime +
                ", arrDateTime=" + arrDateTime +
                ", flightNo='" + flightNo + '\'' +
                ", depCityName='" + depCityName + '\'' +
                ", arrCityName='" + arrCityName + '\'' +
                '}';
    }
}

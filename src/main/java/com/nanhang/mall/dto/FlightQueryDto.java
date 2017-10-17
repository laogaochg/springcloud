package com.nanhang.mall.dto;

import java.util.Date;

/**
 * @Author: LaoGaoChuang
 * @Date : 2017/10/5 17:05
 * 航班信息查询对象封装
 */
public class FlightQueryDto {
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
     * 格式：2012-06-12 08:20
     */
    private Date depDateTime;

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


    @Override
    public String toString() {
        return "FlightQueryDto{" +
                "depCity='" + depCity + '\'' +
                ", arrCity='" + arrCity + '\'' +
                ", depDateTime='" + depDateTime + '\'' +
                '}';
    }
}

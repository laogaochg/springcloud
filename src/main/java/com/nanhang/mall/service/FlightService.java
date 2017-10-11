package com.nanhang.mall.service;

import com.bjucloud.common.ExecuteResult;
import com.nanhang.mall.dto.FlightDto;
import com.nanhang.mall.dto.FlightQueryDto;

import java.util.List;

/**
 * @Author: LaoGaoChuang
 * @Date : 2017/10/6 9:42
 * 航班相关
 */
public interface FlightService {
    /**
     * 航班信息查询
     */
    ExecuteResult<List<FlightDto>> queryFlight(FlightQueryDto qo);
}

package com.yz.serviceorder.remote;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.TerminalResponse;
import com.yz.internalcommon.response.TrsearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: yangzhen
 * @Date 2022/12/8-12:04
 * @Description: com.yz.serviceorder.remote
 * @version: 1.0
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    //搜索周边车辆
    @RequestMapping(method = RequestMethod.POST,value = "/terminal/aroundsearch")
    public ResponseResult<List<TerminalResponse>> terminalAroundsearch(@RequestParam String center , @RequestParam Integer radius);

    //查询终端在某个时间段的轨迹结果
    @RequestMapping(method = RequestMethod.POST, value = "/terminal/trsearch")
    public ResponseResult<TrsearchResponse> trsearch(@RequestParam String tid, @RequestParam Long starttime, @RequestParam Long endtime);

}

package com.yz.servicemap.controller;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.TerminalResponse;
import com.yz.internalcommon.response.TrsearchResponse;
import com.yz.servicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-4:50
 * @Description: com.yz.servicemap.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/terminal")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    /**
     * 添加终端
     * @param name
     * @param desc
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<TerminalResponse> add(String name , String desc){
        return terminalService.add(name , desc);
    }

    /**
     * 终端搜索
     * @param center
     * @param radius
     * @return
     */
    @PostMapping("/aroundsearch")
    public ResponseResult<List<TerminalResponse>> aroundsearch(String center , Integer radius){

        return terminalService.aroundsearch(center,radius);
    }

    /**
     * 轨迹查询
     * @param tid
     * @param starttime
     * @param endtime
     * @return
     */
    @PostMapping("/trsearch")
    public ResponseResult<TrsearchResponse> trsearch(String tid, Long starttime , Long endtime){

        return terminalService.trsearch(tid,starttime,endtime);
    }


}

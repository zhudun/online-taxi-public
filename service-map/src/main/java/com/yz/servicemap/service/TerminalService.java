package com.yz.servicemap.service;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.TerminalResponse;
import com.yz.servicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangzhen
 * @Date 2022/11/9-4:51
 * @Description: com.yz.servicemap.service
 * @version: 1.0
 */
@Service
public class TerminalService {

    @Autowired
    TerminalClient terminalClient;

    public ResponseResult<TerminalResponse> add(String name , String desc){

        return terminalClient.add(name , desc);
    }



}

package com.yz.serviceverificationcode.controller;

import com.yz.internalcommon.dto.ResponseResult;
import com.yz.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangzhen
 * @Date 2022/10/22-15:49
 * @Description: com.yz.serviceverificationcode.controller
 * @version: 1.0
 */
@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size) {
        System.out.println("size = " + size);
        //生成验证码
        double mathRondom = (Math.random()*9+1)* Math.pow(10,size-1);
        System.out.println("mathRondom = " + mathRondom);
        int resultInt = (int) mathRondom;
        System.out.println("generator src code:" + resultInt);

        //定义返回值
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(resultInt);

        return ResponseResult.success(response);

    }

}

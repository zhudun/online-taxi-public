package com.yz.ssedriverclientweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @Author: yangzhen
 * @Date 2022/12/25-22:23
 * @Description: com.yz.ssedriverclientweb.controller
 * @version: 1.0
 */
@RestController
public class SseController {

    @GetMapping("/connect/{driverId}")
    public SseEmitter connect(@PathVariable String driverId){
        System.out.println("司机ID:"+driverId);
        SseEmitter sseEmitter = new SseEmitter(0l);

        return sseEmitter;
    }
}

package com.yz.servicessepush.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yangzhen
 * @Date 2022/12/25-22:23
 * @Description: com.yz.ssedriverclientweb.controller
 * @version: 1.0
 */
@RestController
public class SseController {

    public static Map<String, SseEmitter> sseEmitterMap = new HashMap<>();

    /**
     * 建立连接
     * @param driverId
     * @return
     */
    @GetMapping("/connect/{driverId}")
    public SseEmitter connect(@PathVariable String driverId){
        System.out.println("司机ID:"+driverId);
        SseEmitter sseEmitter = new SseEmitter(0l);

        sseEmitterMap.put(driverId, sseEmitter);

        return sseEmitter;
    }

    /**
     * 发送消息
     * @param driverId 消息接收者
     * @param content 消息内容
     * @return
     */
    @GetMapping("/push")
    public String push(@RequestParam String driverId, @RequestParam String content){

        try {
            if (sseEmitterMap.containsKey(driverId)){
                sseEmitterMap.get(driverId).send(content);
            }else {
                return "推送失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "给用户："+driverId+",发送了消息："+content;
    }

    /**
     * 关闭连接
     * @param driverId
     * @return
     */
    @GetMapping("/close/{driverId}")
    public String close(@PathVariable String driverId){
        System.out.println("关闭连接："+driverId);
        if (sseEmitterMap.containsKey(driverId)){
            sseEmitterMap.remove(driverId);
        }
        return "close 成功";

    }
}

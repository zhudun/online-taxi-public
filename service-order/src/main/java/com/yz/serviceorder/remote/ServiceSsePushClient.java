package com.yz.serviceorder.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yangzhen
 * @Date 2022/12/26-23:06
 * @Description: com.yz.serviceorder.remote
 * @version: 1.0
 */
@FeignClient("service-sse-push")
public interface ServiceSsePushClient {

    @RequestMapping(method = RequestMethod.GET,value = "/push")
    public String push(@RequestParam Long userId, @RequestParam String identity, @RequestParam String content);
}

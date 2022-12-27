package com.yz.servicessepush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: yangzhen
 * @Date 2022/12/25-21:43
 * @Description: com.yz.ssedriverclientweb
 * @version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceSsePushApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceSsePushApplication.class,args);
    }
}

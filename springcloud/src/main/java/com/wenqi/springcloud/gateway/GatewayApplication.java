package com.wenqi.springcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liangwenqi
 * @date 2022/6/22
 */
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }


    public static void testGateway() {
        String url = "";
        String s = String.valueOf("123");
    }
}

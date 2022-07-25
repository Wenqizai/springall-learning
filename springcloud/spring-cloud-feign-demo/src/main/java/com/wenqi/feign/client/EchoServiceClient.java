package com.wenqi.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author liangwenqi
 * @date 2022/7/25
 */
@FeignClient(value = "echo-service", url = "http://127.0.0.1:8001")
public interface EchoServiceClient {
    @GetMapping("/echo/{msg}")
    String echo(@PathVariable String msg);
}

package com.wenqi.feign.client;

import org.bouncycastle.util.Store;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author liangwenqi
 * @date 2022/7/22
 */
@FeignClient("stores")
public interface StoreClient {
}

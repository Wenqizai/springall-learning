package com.wenqi.feign;

import com.wenqi.feign.client.EchoServiceClient;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liangwenqi
 * @date 2022/7/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignApplication.class)
public class TestFeign {

    @Autowired
    private EchoServiceClient echoServiceClient;

    @Test
    public void test() {
        echoServiceClient.echo("1");
    }
}

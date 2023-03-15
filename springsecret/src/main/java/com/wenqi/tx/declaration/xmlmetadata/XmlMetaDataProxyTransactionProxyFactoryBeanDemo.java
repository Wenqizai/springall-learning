package com.wenqi.tx.declaration.xmlmetadata;

import com.wenqi.tx.declaration.IQuoteService;
import com.wenqi.tx.declaration.Quote;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liangwenqi
 * @date 2023/3/13
 */
public class XmlMetaDataProxyTransactionProxyFactoryBeanDemo {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:tx/spring-tx-context.xml");
        // 注意使用代理对象事务才生效
        IQuoteService quoteService = (IQuoteService) context.getBean("quoteService2");
        // 正常 -> 事务提交
        quoteService.getQuote();
        // 异常 -> 事务回滚
        quoteService.deleteQuote(new Quote());
    }
}

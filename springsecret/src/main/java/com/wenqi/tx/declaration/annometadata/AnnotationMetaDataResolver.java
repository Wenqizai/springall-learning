package com.wenqi.tx.declaration.annometadata;

import com.wenqi.tx.declaration.IQuoteService;
import com.wenqi.tx.declaration.Quote;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Method;

/**
 * @author liangwenqi
 * @date 2023/3/15
 */
public class AnnotationMetaDataResolver {
    /**
     * 代理模式生成代理类
     */
    private IQuoteService quoteService;

    public Quote getQuote() {
        try {
            Method method = quoteService.getClass().getDeclaredMethod("getQuote", null);
            boolean isTxAnnotationPresent = method.isAnnotationPresent(Transactional.class);
            // 没有@Transactional注解, 直接执行
            if (!isTxAnnotationPresent) {
                return quoteService.getQuote();
            }
            Transactional txInfo = method.getAnnotation(Transactional.class);
            TransactionTemplate transactionTemplate = new TransactionTemplate(getTransactionManager());
            if (!txInfo.propagation().equals(Propagation.REQUIRED)) {
                transactionTemplate.setPropagationBehavior(txInfo.propagation().value());
            }
            if (txInfo.readOnly()) {
                transactionTemplate.setReadOnly(true);
            }
            return transactionTemplate.execute(transactionStatus -> quoteService.getQuote());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return quoteService.getQuote();
    }

    private PlatformTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager();
    }
}

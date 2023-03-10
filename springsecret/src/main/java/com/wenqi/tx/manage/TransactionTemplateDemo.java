package com.wenqi.tx.manage;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.io.IOException;

/**
 * @author liangwenqi
 * @date 2023/3/10
 */
public class TransactionTemplateDemo {
    private static final Logger logger = LoggerFactory.getLogger(TransactionTemplateDemo.class);

    public static void main(String[] args) {
    }

    private static void demo02() {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    // do something
                } catch (RuntimeException ex) {
                    logger.warn("一定要记得打日志, 不然不知道事务被回滚啦");
                   status.setRollbackOnly();
                }
            }
        });
    }

    private static void demo01() {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    File file = new File("");
                    file.createNewFile();
                } catch (IOException ex) {
                    // 抛出特定的异常类型, 回滚事务
                    throw new RuntimeException();
                }
            }
        });
    }
}

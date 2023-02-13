package com.wenqi.dao.common;

import org.springframework.transaction.TransactionDefinition;

/**
 * @author liangwenqi
 * @date 2023/2/13
 */
public class MyTransactionDefinition implements TransactionDefinition {
    @Override
    public int getPropagationBehavior() {
        return 0;
    }

    @Override
    public int getIsolationLevel() {
        return 0;
    }

    @Override
    public int getTimeout() {
        return 0;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }
}

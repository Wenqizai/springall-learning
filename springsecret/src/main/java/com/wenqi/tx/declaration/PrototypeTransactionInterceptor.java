package com.wenqi.tx.declaration;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;

/**
 * 声明式事务: 定义方法拦截器
 *
 * 该拦截器目前需要考虑的问题:
 * 1. 可否针对特定的方法进行事务拦截
 * 2. TransactionDefinition需要的元数据metadata从那里获取
 * 3. 可否针对方法抛出特定的异常进行事务回滚
 *
 * @author Wenqi Liang
 * @date 2023/3/12
 */
public class PrototypeTransactionInterceptor implements MethodInterceptor {
    // 注入
    private final PlatformTransactionManager transactionManager;

    public PrototypeTransactionInterceptor(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        TransactionDefinition definition = getTransactionDefinitionByMethod(method);
        TransactionStatus txStatus = transactionManager.getTransaction(definition);
        Object result = null;

        try {
            result = invocation.proceed();
        } catch (Throwable e) {
            if (needRollbackOn(e)) {
                transactionManager.rollback(txStatus);
            } else {
                transactionManager.commit(txStatus);
            }
            throw e;
        }
        transactionManager.commit(txStatus);

        return result;
    }

    private boolean needRollbackOn(Throwable e) {
        // todo 实现指定异常回滚
        return false;
    }

    private TransactionDefinition getTransactionDefinitionByMethod(Method method) {
        // todo 根据method创建事务
        return null;
    }
}

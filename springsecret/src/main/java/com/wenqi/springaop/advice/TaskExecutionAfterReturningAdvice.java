package com.wenqi.springaop.advice;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import java.lang.reflect.Method;

/**
 * @author liangwenqi
 * @date 2023/1/27
 */
public class TaskExecutionAfterReturningAdvice implements AfterReturningAdvice {
    private final SqlMapClientTemplate sqlMapClientTemplate;

    public TaskExecutionAfterReturningAdvice(SqlMapClientTemplate sqlMapClientTemplate) {
        this.sqlMapClientTemplate = sqlMapClientTemplate;
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        sqlMapClientTemplate.insert("方法处理完成之后, 更新状态", target.getClass().getName());
    }
}

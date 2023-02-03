package com.wenqi.dao.common;

/**
 * @author liangwenqi
 * @date 2023/2/3
 */
public class DaoException extends RuntimeException {
    public DaoException(Exception e) {
        super(e);
    }
}

package com.wenqi.dao.transaction;

/**
 * @author liangwenqi
 * @date 2023/2/13
 */
public class TransactionResourceManager {
    private static final ThreadLocal resources = new ThreadLocal();

    public static Object getResource() {
        return resources.get();
    }

    public static void bindResource(Object resource) {
        resources.set(resource);
    }

    public static Object unbindResource() {
        Object resource = getResource();
        resources.remove();
        return resource;
    }
}

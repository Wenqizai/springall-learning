package com.wenqi.tx.expand;

/**
 * @author liangwenqi
 * @date 2023/3/16
 */
public class DataSourceTypeManager {
    private static final ThreadLocal<DataSources> dsTypes = new ThreadLocal<DataSources>() {
        @Override
        protected DataSources initialValue() {
            return DataSources.MAIN;
        }
    };

    public static DataSources get() {
        return dsTypes.get();
    }

    public static void set(DataSources dataSourcesType) {
        dsTypes.set(dataSourcesType);
    }

    public static void reset() {
        dsTypes.set(DataSources.MAIN);
    }
}

package com.wenqi.springioc.instance.xml.factory;

/**
 * @author Wenqi Liang
 * @date 2022/11/27
 */
public class StaticBarInterfaceFactory extends BarInterfaceFactory {
    /**
     * 无参数形式的工厂方法
     */
    public static BarInterface getInstance() {
        return new BarInterfaceImpl();
    }

    /**
     * 带参数形式的工厂方法
     */
    public static BarInterface getInstance(FooBar fooBar) {
        return new BarInterfaceImpl(fooBar);
    }


}

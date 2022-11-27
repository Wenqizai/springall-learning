package com.wenqi.springioc.instance.xml.factory;

/**
 * @author Wenqi Liang
 * @date 2022/11/27
 */
public class BarInterfaceImpl implements BarInterface{
    private FooBar fooBar;

    public BarInterfaceImpl(FooBar fooBar) {
        this.fooBar = fooBar;
    }

    public BarInterfaceImpl() {
    }
}

package com.wenqi.springioc.instance.xml.factory;

/**
 * @author Wenqi Liang
 * @date 2022/11/27
 */
public class NoStaticBarInterfaceFactory extends BarInterfaceFactory {

    public BarInterface getInstance() {
        return new BarInterfaceImpl();
    }
}

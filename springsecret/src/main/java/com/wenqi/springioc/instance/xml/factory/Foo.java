package com.wenqi.springioc.instance.xml.factory;

/**
 * @author Wenqi Liang
 * @date 2022/11/27
 */
public class Foo {
    private BarInterface barInterface;

    public Foo() {
        // 非容器注入方式
        // 实例化接口类 (静态工厂)
//        this.barInterface = StaticBarInterfaceFactory.getInstance();
        // 实例化接口类 (非静态工厂)
//        this.barInterface = new NoStaticBarInterfaceFactory().getInstance();
    }

    public void setBarInterface(BarInterface barInterface) {
        this.barInterface = barInterface;
    }
}

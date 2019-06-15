package impl;

import org.springframework.stereotype.Component;

@Component("car")
public class Car {

    public void produce() {
        System.out.println("***制造小汽车***");
    }

    public Car() {
        System.out.println("小汽车被加载了");
    }
}

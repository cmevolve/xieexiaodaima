package com.impl;

import com.Product;

public class Car implements Product {

    @Override
    public void produce() {
        System.out.println("***制造小汽车***");
    }
}

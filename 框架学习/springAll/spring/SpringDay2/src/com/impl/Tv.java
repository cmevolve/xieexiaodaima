package com.impl;

import com.Product;

public class Tv implements Product{

    @Override
    public void produce() {
        System.out.println("***制造电视***");
    }
}

package com.how2java.test;

import com.how2java.pojo.Product;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.how2java.pojo.Category;

public class TestSpring {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml"});

		//Category c = (Category) context.getBean("c");
		Product d = (Product) context.getBean("p");

		System.out.println(d.getName());
		System.out.println(d.getCategory().getName());
	}
}
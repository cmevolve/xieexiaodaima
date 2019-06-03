package com.factory;

import com.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 创建一个Bean对象工厂
 * Bean：计算机英语中有可重用组件的含义
 * JavaBean: 用java语言编写的可重用组件
 * 第一个：需要一个配置文件来配置加载项
 *          配置内容：唯一标识 = 全限定类名（KEY ，Value）
 * 第二个：通过读取配置文件中配置的内容，反射创建对象
 * 配置文件既可以是xml 也可以是properties
 */
public class BeanFactory {
    private static Properties props ;
    //定义一个map用来存放需要的对象，模拟容器
    private static Map<String,Object> beans;

    static {
        InputStream in = null;
        beans = new HashMap();
        props = new Properties();
        try {
            //为防止加载时路径异常使用类加载器获取路径
            in = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            props.load(in);
            Iterator<String> iterator = props.stringPropertyNames().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = props.getProperty(key);
                Object bean = Class.forName(value).newInstance();
                beans.put(key, bean);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("初始化失败");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
            if (null != in) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Product produce(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//      Product product = (Product)Class.forName(className).newInstance();
       return (Product) beans.get(name);
    }
}

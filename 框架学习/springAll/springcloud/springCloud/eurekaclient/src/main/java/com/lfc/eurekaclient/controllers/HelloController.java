package com.lfc.eurekaclient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping("/hello/{name}")
    @ResponseBody
    public String hi(@PathVariable String name){
        System.out.println(name+"访问过来了");
        return "hello "+name+"，this is first messge";
    }
}

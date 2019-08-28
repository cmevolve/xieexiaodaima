package com.lfc.sericefeign.controllers;

import com.lfc.sericefeign.service.SchedualServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @Autowired
    SchedualServiceHi schedualServiceHi;

    @GetMapping(value = "/hello/{name}")
    public String sayHi(@PathVariable("name") String name) {
        System.out.println("调用fen"+name);
        return schedualServiceHi.sayHiFromClientOne( name );
    }
}

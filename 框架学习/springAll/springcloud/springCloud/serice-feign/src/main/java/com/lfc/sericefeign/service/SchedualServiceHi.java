package com.lfc.sericefeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "eurekaclient")
public interface SchedualServiceHi {
    @RequestMapping(value = "/hello/{name}")
    String sayHiFromClientOne(@PathVariable(value = "name") String name);
}

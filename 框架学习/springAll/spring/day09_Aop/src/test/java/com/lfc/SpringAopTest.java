package com.lfc;

import com.springAll.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpringAopTest {

     @Resource
     private IAccountService accountService;

     @Test
     public void xmlAopTest(){
          accountService.saveAccount();
          accountService.updateAccount(1);
     }
}

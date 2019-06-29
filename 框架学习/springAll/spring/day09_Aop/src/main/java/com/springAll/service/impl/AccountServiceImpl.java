package com.springAll.service.impl;

import com.springAll.service.IAccountService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;


@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    public void saveAccount() {
        System.out.println("执行保存");
    }

    public void updateAccount(int i) {
        System.out.println("执行修改" +i);
        //i= 10/0;
    }

    public int deleteAccount() {
        System.out.println("执行删除");
        return 0;
    }
}

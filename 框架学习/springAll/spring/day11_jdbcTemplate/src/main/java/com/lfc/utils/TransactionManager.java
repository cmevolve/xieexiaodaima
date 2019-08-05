package com.lfc.utils;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 事务管理器
 */
public class TransactionManager {
    @Autowired
    ConnectionUtils connectionUtils;

    /**
     * 开启事务
     */
    public  void beginTransaction(){
        try {
            connectionUtils.getConnection().setAutoCommit(false);//关闭自动提交
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 提交事务
     */
    public  void commit(){
        try {
            connectionUtils.getConnection().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    public  void rollback(){
        try {
            connectionUtils.getConnection().rollback();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 释放连接
     */
    public  void release(){
        try {
            connectionUtils.getConnection().close();//还回连接池中
            connectionUtils.removeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package com.lfc.utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库连接缓存类，保证当前线程结束前使用同一连接
 */
public class ConnectionUtils {

    private ThreadLocal<Connection> tl = new ThreadLocal();

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection(){
        //1.先从ThreadLocal上获取
        Connection conn = tl.get();
        try {
            //2.判断当前线程上是否有连接
            if(null == conn){
                //3.从数据源中获取一个连接，并且存入ThreadLocal中
                conn = dataSource.getConnection();
                tl.set(conn);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
    /**
     * 把连接和线程解绑
     */
    public void removeConnection(){
        tl.remove();
    }
}

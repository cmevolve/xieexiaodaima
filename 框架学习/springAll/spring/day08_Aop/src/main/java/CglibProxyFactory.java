import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

public class CglibProxyFactory implements MethodInterceptor{
    private Object target;
    Transaction transaction;

    public CglibProxyFactory(Object target, Transaction transaction) {
        this.target = target;
        this.transaction = transaction;
    }


    public Object getProxyInstance(){
        //工具类
        Enhancer en = new Enhancer();
        //设置父类
        en.setSuperclass(target.getClass());
        //设置回调函数 拦截调用intercept函数
        en.setCallback(this);
        //创建子类对象代理
        return en.create();
    }

    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        transaction.beginTransaction();
        method.invoke(target,args);
        transaction.commit();
        return null;
    }
}

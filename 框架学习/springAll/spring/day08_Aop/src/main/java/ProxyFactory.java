import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    Transaction transaction = new Transaction();
    Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    /**
     * 生成代理类
     */
    public Object getProxyInstance (){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String methodName = method.getName();
                        if("execute".equals(methodName)){
                            transaction.beginTransaction();
                            method.invoke(target,args);
                            transaction.commit();
                        }else{
                            method.invoke(target);
                        }
                        return null;
                    }
                });
    }
}

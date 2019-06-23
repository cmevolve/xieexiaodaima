import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Interceptor implements InvocationHandler{
    Transaction transaction;
    Object target;

    public Interceptor(Transaction transaction, Object target) {
        this.transaction = transaction;
        this.target = target;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if("execute".equals(methodName)){
            transaction.beginTransaction();
            method.invoke(target);
            transaction.commit();
        }else{
            method.invoke(target);
        }
        return null;
    }
}

/**
 * 事务类
 */
class Transaction{

    void beginTransaction(){
        System.out.println("******** 开启事务 **************");
    }
    void commit(){
        System.out.println("********* 提交事务 ************");
    }

}
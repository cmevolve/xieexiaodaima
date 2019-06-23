import impl.Command;
import org.junit.Test;

import java.lang.reflect.Proxy;


public class ProxyTest {
    @Test
    public void testSave(){
        Object target = new PlaceOrderDecorator();
        Transaction transaction = new Transaction();
        Interceptor proxy = new Interceptor(transaction,target);
        Command com = (Command) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),proxy);
        com.execute();
    }

    @Test
    public void testJDKProxy(){
        Object target = new PlaceOrderDecorator();
        Command com = (Command)new ProxyFactory(target).getProxyInstance();
        com.execute();
    }

    @Test
    public void testCGlibProxy(){
        PlaceOrderDecorator target = new PlaceOrderDecorator();
        System.out.println(target.getClass());
        PlaceOrderDecorator proxy = (PlaceOrderDecorator)new CglibProxyFactory(target,new Transaction())
                .getProxyInstance();
        System.out.println(proxy.getClass());
        proxy.execute();
    }
}

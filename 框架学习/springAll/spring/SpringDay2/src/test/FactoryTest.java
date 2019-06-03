import com.factory.BeanFactory;

public class FactoryTest {

    public static void main(String[] args) {
        try {
//            BeanFactory.produce("com.impl.Car").produce();
//            BeanFactory.produce("com.impl.Tv").produce();
            BeanFactory.produce("car").produce();
            BeanFactory.produce("tv").produce();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}

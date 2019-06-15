
import impl.Car;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:applicationContext.xml"});

		//Category c = (Category) context.getBean("c");
		Car d = (Car) context.getBean("car");
		d.produce();
	}
}
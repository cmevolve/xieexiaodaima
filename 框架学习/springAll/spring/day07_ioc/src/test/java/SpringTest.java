import impl.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:applicationContext.xml")
@ContextConfiguration(classes = Car.class)
public class SpringTest {
    @Autowired
    private Car car;

    @Test
    public void testFindAll(){
        car.produce();
        System.out.println("收拾收拾");
    }



}

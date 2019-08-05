import config.SpringConfigurantion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfigurantion.class)
public class AnnotationTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void queryTest(){
        String sql = "select money from account where id = ?";
        String money = jdbcTemplate.queryForObject(sql,new Object[]{1},String.class);
        System.out.println("账户余额："+money);
    }
}

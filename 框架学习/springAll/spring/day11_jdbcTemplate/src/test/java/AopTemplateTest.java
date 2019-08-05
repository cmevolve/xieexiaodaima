import com.lfc.utils.ConnectionUtils;
import com.lfc.utils.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
//@ContextConfiguration(classes = SpringConfigurantion.class)

public class AopTemplateTest {

//    @Autowired
//    JdbcTemplate jdbcTemplate;

    @Autowired
    TransactionManager tm;

    @Autowired
    ConnectionUtils connectionUtils;

    @Autowired
    QueryRunner runner;

    @Test
    public void templateTest(){
        //转账操作
        try {
            tm.beginTransaction();
            String updSql2 = "update account set money=money-100 where id=?";
            runner.update(connectionUtils.getConnection(),updSql2,new Object[]{2});
            System.out.println("id 2 扣款");
            //int i=1/0;
            String updSql1 = "update account set money=money+100 where id=?";
            runner.update(connectionUtils.getConnection(),updSql1,new Object[]{1});
            System.out.println("id  1 增加");
            tm.commit();
        }catch (Exception e){
            tm.rollback();
            System.out.println("========异常回滚===========");
        }finally {
            tm.release();
        }
    }

}

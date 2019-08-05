package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration    //该类作为spring的xml配置文件中的<beans>
@ComponentScan("com.lfc")    //扫描配置包下被注解过的类
@Import({JdbcConfig.class,TransactionConfig.class})
@PropertySource("db.properties")
@EnableTransactionManagement  //事务注解
public class SpringConfigurantion {

}

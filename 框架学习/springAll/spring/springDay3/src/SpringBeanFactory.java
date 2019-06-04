import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class SpringBeanFactory {
    public static void main(String[] args) {
        //调用资源加载器加载Spring配置文件
        PathMatchingResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
        Resource res =resolver.getResource("classpath:/applicationContext.xml");

        //创建默认Spring提供的BeanFactory实现类
        DefaultListableBeanFactory bf =new DefaultListableBeanFactory();

        //BeanDefinition 读取器,专门读取资源到容器
        XmlBeanDefinitionReader reader =new XmlBeanDefinitionReader(bf);

        //读取资源进到容器
        reader.loadBeanDefinitions(res);

        //此时容器初始化完毕后可以取里面的bean了.
        bf.getBean("car");

    }
}

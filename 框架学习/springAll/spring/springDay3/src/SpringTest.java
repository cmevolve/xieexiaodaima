import org.apache.commons.logging.impl.ServletContextCleaner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class SpringTest {
    public static void main(String[] args) {
        /**
         *
         */
        //ApplicationContext ap = new FileSystemXmlApplicationContext();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        BufferedReader bf = null;
        try {
            Resource[] res = resolver.getResources("classpath:/test.txt");
            for (Resource  resource : res){
                System.out.println(resource.getDescription()+"----"+resource.getFilename());
                //System.out.println(resource.getFile().);
                bf = new BufferedReader(new FileReader(resource.getFile()));
                String data = "";
                while((data = bf.readLine())!=null){
                    System.out.println(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != bf)bf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

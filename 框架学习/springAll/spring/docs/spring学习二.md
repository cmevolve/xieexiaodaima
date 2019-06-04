# spring学习二

tags ： spring

---

## Spring好处
经过昨天的练习学会了使用spring进行IOC，那么今天来思考一下，spring解决了什么问题，又有什么优势呢？

先来看下一段jdbc程序
```java
//注册驱动
DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//创建连接
Connection connectMySQL  = DriverManager.getConnection(“jdbc:mysql://localhost:3306/myuser","root" ,"123" );
//获取操作数据库的预处理对象
Statement statamentMySQL = connectMySQL.createStatement(); 
//获得返回结果
statement.excuteUpdate( "INSERT INTO student(name, age, sex,address, depart, worklen,wage)" + " VALUES ('Tom1', 321, 'M', 'china','Personnel','3','3000' ) ") ; 
//释放资源
 conn.close(); 
```
**那么这段程序有什么问题呢？**在第二行的位置注册驱动时，因为直接使用了DriverManager.registerDriver如果程序中没有mysql的包将会直接产生检查型error进行报错。此时类之间的耦合性很高，那么如何能降低依赖呢？
解耦的思路：

 - 使用反射来创建对象，而避免使用关键字new；
 - 通过读取配置文件来获取要创建的对象全限定类名；

其实还有一种驱动加载的方式是使用反射 ```java  Class.forName(“com.mysql.jdbc.Driver”) ``` 来代替注册驱动的位置，在程序编译时Class.forName参数中的只是字符串并不会产生报错，只有在运行时加载类的时候才会因找不到该类产生异常，由此来降低程序间的依赖。实际开发过程中也应该做到在编译期不依赖，运行时才依赖。

在传统方式使用中mvc时，使用的是通过接口 = new 具体实现类的方式获得具体对象,并执行方法，这的强依赖和上面的jdbc样例是一样的。
```java 
Car car = new Bus();
car.run();
```
**那么如何能解决眼前的问题呢？**
工厂模式解耦
```java
//工厂接口
public interface Product {
    void produce();
}
//汽车接口
public class Car implements Product {
    @Override
    public void produce() {
        System.out.println("***制造小汽车***");
    }
}
//电视接口
public class Tv implements Product{
    @Override
    public void produce() {
        System.out.println("***制造电视***");
    }
}
//工厂类
public static Product produce(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    Product product = (Product)Class.forName(className).newInstance();
    return product;
}
//测试类
 public static void main(String[] args) {
    BeanFactory.produce("com.impl.Car").produce();
    BeanFactory.produce("com.impl.Tv").produce();
 }
```
执行结果：
****制造小汽车****
****制造电视****
Process finished with exit code 0

**使用此模式在使用时参数还是需要准确的写出包的路径，并且每次反射都是新建了一个新的对象，在非多线程环境下对资源的消耗是一种浪费。由此对此程序进行优化**

 1. 创建一个Bean对象工厂
 2. 需要一个配置文件来配置加载项( 配置内容：唯一标识 = 全限定类名（KEY ，Value）)
 3. 通过读取配置文件中配置的内容，反射创建对象
 4. 配置文件既可以是xml 也可以是properties

```java
public class BeanFactory {
    private static Properties props ;
    //定义一个map用来存放需要的对象，模拟容器
    private static Map<String,Object> beans;
    //类加载时读取配置文件并加载到容器中
    static {
        InputStream in = null;
        beans = new HashMap();
        props = new Properties();
        try {
            //为防止加载时路径异常使用类加载器获取路径
            in = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            props.load(in);
            Iterator<String> iterator = props.stringPropertyNames().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = props.getProperty(key);
                Object bean = Class.forName(value).newInstance();
                beans.put(key, bean);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("初始化失败");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
            if (null != in) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Product produce(String name) throws ClassNotFoundException,IllegalAccessException, InstantiationException {
       return (Product) beans.get(name);
    }
}

```
bean.properties内容：
```properties
tv=com.impl.Tv
car=com.impl.Car
```
通过以上方式来模拟了一个容器，实现了应用对资源的解耦，而使用了spring的IOC则完全避免了去实现这些复杂的处理，优雅的实现。”不要给我们打电话，我们会打给你的。”这是著名的好莱坞原则。
早先传统的方式通过new 关键字主动创建一个依赖的对象，使用了spring后只需要告诉它需要什么类，剩下的交给它搞定，于是控制反转了Inversion of Control 简称IOC。
**[代码地址][1]**


  [1]: https://github.com/cmevolve/xieexiaodaima/tree/master/%E6%A1%86%E6%9E%B6%E5%AD%A6%E4%B9%A0/springAll/spring/SpringDay2/src
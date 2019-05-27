# Spring学习一

tags ： Spring

---

## IOC、DI
### 基于配置文件的练习
**1.准备pojo**
``` java
package com.how2java.pojo;
public class Category {

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private int id;
	private String name;
}
```
**2.配置文件**
在src目录下新建applicationContext.xml文件
applicationContext.xml是Spring的核心配置文件，通过关键字c即可获取Category对象，该对象获取的时候，即被注入了字符串"category 1“到name属性中
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
   http://www.springframework.org/schema/beans 
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/aop 
   http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
   http://www.springframework.org/schema/tx 
   http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
   http://www.springframework.org/schema/context      
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">
    <bean name="c" class="com.how2java.pojo.Category">
        <property name="name" value="category 1" />
    </bean>
</beans>
```

### 注入对象
**1.准备pojo**
```java
package com.how2java.pojo;

public class Product {

	private int id;
	private String name;
	private Category category;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
}
```
**2.在applicationContext.xml中注入对象**
```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:tx="http://www.springframework.org/schema/tx" xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="
   http://www.springframework.org/schema/beans 
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/aop 
   http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
   http://www.springframework.org/schema/tx 
   http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
   http://www.springframework.org/schema/context      
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">
	<bean name="c" class="com.how2java.pojo.Category">
		<property name="name" value="category 1" />
	</bean>
	<bean name="p" class="com.how2java.pojo.Product">
		<property name="name" value="product1" />
		<property name="category" ref="c" />
	</bean>
</beans>
```
### 注解的方式 IOC、DI
**1.配置applicationContext.xml**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx" xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
   http://www.springframework.org/schema/beans 
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/aop 
   http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
   http://www.springframework.org/schema/tx 
   http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
   http://www.springframework.org/schema/context      
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">
 	<context:annotation-config/>
    <bean name="c" class="com.how2java.pojo.Category">
        <property name="name" value="category 1" />
    </bean>
    <bean name="p" class="com.how2java.pojo.Product">
        <property name="name" value="product1" />
<!--         <property name="category" ref="c" /> -->
    </bean>
</beans>
```
**2.pojo中加入注解** 
@Autowired
``` java
package com.how2java.pojo;
import org.springframework.beans.factory.annotation.Autowired;
public class Product {
	private int id;
	private String name;
	@Autowired
	private Category category;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
}
```
除了在属性前加上@Autowired 这种方式外，也可以在setCategory方法前加上@Autowired，这样来达到相同的效果
除了@Autowired之外，也可以使用@Resource
@Resource和@Autowired注解都是用来实现依赖注入的。只是@Autowired按byType自动注入，而@Resource默认按 byName自动注入。 
@Resource有两个重要的属性，分是name和type。 
Spring将name属性解析为bean的名字，而type属性则解析为bean的类型。所以如果使用name属性，则使用byName的自动注入策略，而使用type属性时则使用byType自动注入策略。如果既不指定name也不指定type属性，这时将通过反射机制使用byName自动注入策略。

在配置了两个bean后使用Autowired因为有两个bean没法指定所以报错，此时可以使用@Resource 来指定加载name是c2那个bean
``` java
  //@Resource(name ="c2")
    @Autowired
    private Category category;
    public Category getCategory() {
        return category;
    }
    错误提示
    Could not autowire. There is more than     one bean of 'Category' type.
    Beans:
    c   (applicationContext.xml)
    c2  (applicationContext.xml) 
```
### 对Bean的注解
**1.配置applicationContext.xml**
只保留``` xml <context:component-scan base-package="com.how2java.pojo"/> ```
其作用是告诉Spring，bean都放在com.how2java.pojo这个包下
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx" xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
   http://www.springframework.org/schema/beans 
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/aop 
   http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
   http://www.springframework.org/schema/tx 
   http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
   http://www.springframework.org/schema/context      
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">
 	<context:component-scan base-package="com.how2java.pojo"/>
</beans>
```
**2.pojo使用@Component 注入**
为Product类加上@Component注解，即表明此类是bean
``` java
package com.how2java.pojo;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("p")
public class Product {
	private int id;
	private String name="product 1";
	@Autowired
	private Category category;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
}
```
**3.运行测试**
``` java
package com.how2java.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.how2java.pojo.Product;
public class TestSpring {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		Product p = (Product) context.getBean("p");
		System.out.println(p.getName());
		System.out.println(p.getCategory().getName());
	}
}
```
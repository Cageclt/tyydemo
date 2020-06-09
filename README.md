# 途游游后台设计说明文档[^1]

##  前言

### 行文风格

​		本文作为本人于过去几个月内编写的途游游后台的说明文档，以我自己的语言形式向师弟师妹们进行说明，因为最后并没有进入比赛阶段，所以编写的规范并未按照标准的开发文档进行说明，若要编写开发文档，还请师弟师妹按照比赛或规定的开发文档的标准进行撰写。

### 项目概要

​		本文主要对途游游这一版本的后端设计进行描述，不涉及前端的内容，目前为止由本人一人开发，由于自己学习时间短，所以设计和编码上还有不少的缺陷，见笑了。

​		首先是编码环境，这个项目选择的开发模式是**前后端分离**，语言选择为Java语言，选择的是小程序作前端，SSM框架作后端的web项目。

​		目前代码的开发已经达到预期功能设计的80%，但剩下的都是较为核心的内容，因小程序必须需要https域名访问的原因，所以前后端并未进行到对接并测试的阶段。

​		**注：本文的所有说明与代码均对照附带的文档文件以及idea项目文件**

### 环境与工具概述

​		**jdk: 9.0.4**

​		**maven: 3.6.1**

​		**mysql: 8.0.16**

​		**tomcat: 9.0.24**

​		**idea: 2018.2.5**

​		**navicat：12**

### 学习建议

​		若是沿用SSM框架进行开发学习，建议的学习路线为：

​		JavaSE基础->Java进阶内容->前端(有所掌握即可，不必精通)->数据库->JavaWeb->SSM框架->服务器运维

​		接下来进行详细说明

- JavaSE基础：语法那些不必多说，主要是面向对象的思想，set，get的封装思想，接口编程等要有概念
- Java进阶：多线程，网络编程，输入输出流，ORM思想，反射机制等，对于该项目，学习到反射机制即可，JVM的内容不需要过多深入
- 前端：作为后端开发，最好不要对前端一窍不通，因为在某些设计开发的思路上，如果不清楚某些前端的概念，可能会导致后端从设计上就是不可用的，虽然师兄涉猎的也不多，但是这是身边人的经验，最好还是要学习一下，同样的也不用多，了解基础的机制就好
- 数据库：学会sql基础语句的编写，目前后台选择使用的数据库是MySQL，存储过程，触发器，事件等写法要学会，有可能在业务实现中使用到
- JavaWeb：Web开发中的重中之重，http协议学习得多透彻都不为过，还有是要了解目前JavaWeb交互架构的逻辑以及原理，最核心的重点是Servlet的原理，这是Web交互的核心，Cookie以及Session的知识也是必须学习的
- SSM框架：SSM框架即SpringMVC+Spring+Mybatis三个技术栈的整合，这里需要学习理解三层架构：视图/网络转发层，业务层，数据/持久层。SSM框架并不是和三层架构一一对应的，这块师弟师妹深入进行学习就能理解。不作过多说明，唯一要清楚的就是开发中的所有代码写法都使用SSM框架的写法以及三层架构的思想。
- 服务器运维：到后期我们需要将后台的项目部署到线上的服务器进行访问，我这边使用的是阿里云的云服务器，需要租借，这边需要学习Linux系统的知识，学会在服务器环境上进行操作即可。
- 其他：GitHub，maven等开发工具的使用，GitHub可以对团队项目协同开发管理起到莫大的帮助，而maven可以让你在开发中使用许多开源框架以及工具时快速地导入项目而不需要四处寻找库文件

***

## 设计说明

### 总体设计

​		整个项目的总体设计都可以在配套的文件中看到

​		所有的页面本人都使用了Mockplus软件进行绘制，见途游游导出页面文件夹

​		若要使用Mockplus进行设计，可以下载Windows客户端后打开途游游页面设计.mp文件进行编辑

​		页面跳转逻辑见：界面逻辑及详解.docx

### 数据库表设计

​		结构见文件：途游游表名.docx

​		表结构sql文件：tyydemo1.sql

[^2]:醉后不知天在水，满船清梦压星河

*****

## 编码

### 概述

​		项目是基于idea环境下创建的maven项目，首先给出使用的pom依赖，并且进行讲解

#### maven依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.tyy</groupId>
    <artifactId>ssmbuild</artifactId>
    <version>1.0-SNAPSHOT</version>


    <!--依赖  junit，数据库驱动，连接池，servlet，mybatis，mybatis-spring，spring-->
    <dependencies>
        <!--Junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        用作测试使用的junit组件

        <!--数据库驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        Java与MySQL的连接驱动

        <!--数据库连接池：c3p0-->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>
        使用c3p0的连接池类型，当然也可以使用原生的或者选择Druid，师弟师妹都可以自己按需选择

        <!--Servlet JSP-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        理论上我们作为前后端分离的项目，并不需要操作jsp，但是我为了学习需要还是加入进来了这个依赖
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        servlet的依赖，非常重要

        <!--Mybatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.2</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.2</version>
        </dependency>
        mybatis与spring进行整合的依赖包，将mybatis注入到spring

        <!--Spring-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        spring与springmvc和jdbc整合需要的依赖

        <!--lombok插件-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.10</version>
        </dependency>
        lombok插件是用来进行类设计的偷懒工具，不必去自己为每一个成员变量进行set，get方法的生成，直接使用Lombok里面的注解完成，但是也有相应的缺点，网络上都有说到，这里师弟师妹也可以自由选择是否使用

        <!--Json工具类-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.60</version>
        </dependency>
        阿里的fastjson工具类，使用这个工具类完成各种有关json的操作

        <!--阿里云SDK依赖-->
        <dependency>
            <groupId>com.aliyun</groupId>
            <artifactId>aliyun-java-sdk-core</artifactId>
            <version>4.0.3</version>
        </dependency>
        关于实现日常中发送短信验证码进行身份验证的业务，这里使用了阿里云的短信SDK

        <!--log4j日志-->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        等到真正上线运维的时候，调试bug不会像在本地控制台这么简单，这个时候需要生成日志文件进行调试与查看，这里使用的是log4j，如果有更好的选择，师弟师妹也可以自由选择

        <dependency>
            <groupId>commons-httpclient</groupId>
            <artifactId>commons-httpclient</artifactId>
            <version>3.1</version>
        </dependency>
        <dependency>
            <groupId>me.chanjar</groupId>
            <artifactId>weixin-java-mp</artifactId>
            <version>1.3.3</version>
        </dependency>
        <dependency>
            <groupId>me.chanjar</groupId>
            <artifactId>weixin-java-common</artifactId>
            <version>1.3.3</version>
        </dependency>
        以上三个依赖是师兄在解决小程序登录API中获得openid等信息时，病急乱投医找的（狗头），后来师兄找了16级的师兄解决了，但是以上三个依赖是否还有作用存疑，暂且留着，师弟师妹自行发挥（师兄有点菜）

        <!-- https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp -->
        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
            <version>3.12.1</version>
        </dependency>
		一个发送http请求的工具类
    </dependencies>


    <!--静态资源导出，让Java目录下的properties与xml文件能够导出-->
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>7</source>
                    <target>7</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
    这里并不是maven依赖，而是针对整个项目的资源导出设置，如果不设置的话，项目在编译导出时将会忽略所有的xml配置文件，这是非常致命的


</project>
```

#### Resources配置文件

​		均存放在Resources包下，里面都有相应的注释

#### 编码思路及注意事项

​		本人编写的后台编码沿用三层架构思想，即网络层->业务层->数据层三层递进的思路，对应包为Controller，Service与Dao。注意，为了方便阅读与开发，这三层中的所有的类命名以及方法的命名，全部都是统一的

​	pojo即实体类，承担数据载体的作用，根据ORM思想，其设计和数据库表全部一一对应

![image-20200608183506130](文档图片/image-20200608183506130.png)

​		该文档从Controller层一路下探进行讲解，对于简单的增删改查不作过多讲解，师弟师妹若是学会了SSM之后自然看得懂。

![image-20200608184029818](文档图片/image-20200608184029818.png)

​			其中Dao层与Service层都是面向接口编程，接口后接实现类，但是由于开发时间原因，这里的接口并没有做到复用，而是一个接口对应一个实现方法，着实是有点不简洁，其实有很多方法都可以进行一个复用的写法，比如后面会讲到的分页查询的实现，不必要像我那样写那么多重复的代码，但是由于时间原因，只能找这种最不用动脑的方式，能实现出来功能为核心目标:disappointed_relieved:。若是师弟师妹有时间，可以重写一下接口与实现这方面的代码，可以让代码简洁很多，提高可读性。

​			思路大致为：

​		ArticleController：针对文章进行的操作

​		BillController：针对账单进行的操作

​		InitializeController：进入页面时的初始化操作

​		UserController：针对用户进行的操作

![image-20200608184345390](文档图片/image-20200608184345390.png)

​		注意：**所有返回的参数均为Json格式的字符串**，内部封装的类为BaseResponse，BaseResponse分为三个域：code，msg，data。真正的数据存储在data中，data的类型多变，注意查看类的设计。而code与msg则是为了测试方便而设计的状态码以及输出信息。

```java
public class BaseResponse<T> {
private Integer code;
private String msg;
private T data;
}
```
​		该接口文档中所有的返回参数均为data域中的数据，前端拿到数据后注意提取封装。

​		**所有的参数传递方式均使用POST方式，且请求的header的content-type字段设置一定要为：application/x-www-form-urlencoded**

​		在此文档中出现的所有的Map类型的对象，**K-V键值对均为(String,Object)类型**，数据互相传递的时候注意即可



### 详细讲解

**注意：详细讲解，仅讲解部分业务逻辑较为复杂的内容，纯粹的增删改查并没有太多说明的价值，仅仅只是搬砖而已**

​		建议配合这两个文件进行阅读![image-20200609153957377](文档图片/image-20200609153957377.png)

#### ArticleController

##### addReview （第78行）

```java
@RequestMapping("/addReview")
//评论游记
public String addReview(@RequestParam("DataMap") Map<String, Object> Datamap,@RequestParam("ArticleId") int articleId){
    BaseResponse response=new BaseResponse(StatusCode.Fail);
    int status = articleService.addReview(Datamap);
    if (status != 0){
        articleService.addReviewNum(articleId);
        response.setCode(0);
        response.setMsg("成功");
        response.setData(Datamap);
        String result = JSON.toJSONString(response);
        return result;
    }
    else{
        String result = JSON.toJSONString(response);
        return  result;
    }
}
```

​		这里需要前端传入要添加评论的游记ID以及一个Map，需要的参数为UserId,ArticleId,Reviews，然后调用articleService.addRevire进入Service层

```java
//为游记添加评论
public int addReview(Map<String, Object> map) {
    Date nowdate = new Date();
    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    sdf.format(nowdate);
    map.put("Time",nowdate);
    map.put("Good",0);
    return articleMapper.addReview(map);
}
```

​		生成当前时间，格式转换为yyyy-MM-dd HH:mm:ss加入Map，然后初始化点赞量Good为0加入Map，将Map传入Dao层

```xml
<insert id="addReview" parameterType="map">
    insert into tyydemo1.review (UserId, ArticleId, Reviews, Time, Good)
    values (#{UserId},#{ArticleId},#{Reviews},#{Time},#{Good});
</insert>
```

​		执行插入语句

​		回到Controller层，执行插入语句成功之后，修改该游记的评论数

```java
int status = articleService.addReview(Datamap);
    if (status != 0){
        articleService.addReviewNum(articleId);
```

​		**addR_Review方法逻辑与此相同**

其他的方法均为普通的增删改查，不作过多赘述



#### BillController

##### addNewBill（第48行）

```java
@RequestMapping("/addNewBill")//添加新的账单
public String addNewBill(@RequestParam("UserId")int UserId,@RequestParam("Title")String Title){
    BaseResponse response=new BaseResponse(StatusCode.Fail);
    int status = billService.addNewBill(UserId, Title);
    if (status != 0){
        response.setCode(0);
        response.setMsg("成功");
        response.setData(status);
        String result = JSON.toJSONString(response);
        return result;
    }
    else{
        String result = JSON.toJSONString(response);
        return  result;
    }
}
```

​		其实这个方法也比较简单，但是涉及到业务设计的逻辑还是稍微讲一下

​		进入账单主界面后，点击添加账单会弹出一个输入框，用户输入此账单的标题即可

##### updateBillItem（第83行）

```java
@RequestMapping("/updateBillItem")//更新账单详情
    public String updateBillItem(@RequestParam("BillId")int BillId,@RequestParam("BillItemlist") List<BillItem> newList) throws Exception {
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        List<BillItem> oldList = new ArrayList<BillItem>();
        oldList = billService.queryAllBillItemById(BillId);//根据账单ID查出所有的账单项
        int delete = 0;
        int add = 0;
        int update = 0;
        Iterator<BillItem> oldIterator = oldList.iterator();
        while(oldIterator.hasNext()){
            BillItem old = oldIterator.next();
            int exist = 0;//是否依然存在新的list中

            Iterator<BillItem> newIterator = newList.iterator();
            while (newIterator.hasNext()){
                BillItem New = newIterator.next();
                if(old.getId().equals(New.getId()))
                {
                    exist = exist+1;
                    break;
                }
            }

            if(exist==0){//已经不存在
                oldList.remove(old);//将其从账单中删除
                delete = delete + billService.deleteBillItemById(old.getId().intValue());//删除该记录
                oldIterator.remove();
            }
        }


        List<BillItem> diffrentList = new ArrayList<BillItem>();
        diffrentList = Utils.getDiffrent(oldList,newList);//抓取出所有修改过的数据

        for (BillItem diffrentitem : diffrentList) {
            if (diffrentitem.getId()==null){
                add = add + billService.addNewBillItem(diffrentitem);//添加新的账单记录
            }
            else update = update + billService.updateBillItem(Utils.objectToMap(diffrentitem));//修改账单记录
            
        }
        double Sum = billService.queryBillSum(BillId);//查出新的账单中的Sum
        HashMap<String, Object> map = new HashMap<>();
        map.put("Sum",Sum);
        map.put("BillId",BillId);
        billService.updateBillSum(map);//修改账单总值Sum
        int status = add+delete+update;
        if (status != 0){
            response.setCode(0);
            response.setMsg("成功执行");
            String msgresult;
            msgresult = "删除"+delete+"条数据，"+"添加"+add+"条数据，"+"修改"+update+"条数据";
            response.setData(msgresult);
            String result = JSON.toJSONString(response);
            return result;
        }
        else
            response.setCode(0);
        response.setMsg("成功执行");
        String msgresult;
        msgresult = "没有修改任何数据";
        response.setData(msgresult);
        String result = JSON.toJSONString(response);
        return result;
    }
```

​		这里是账单编辑业务的重点，在用户点击保存账单详情后，向后台传入一个包含账单项BillItem的List，此时系统向后台进行检索，检索出修改前的账单项List，对两个账单项目的List进行比对，对新添加的项目进行插入，未修改过的项目保持不变，已经删除掉的项目也相应从后台记录中删除，修改了内容的项目进行修改，即增删改3种全部情况都要进行判断。

​		这里的逻辑是，首先判断修改过后的List中，所有已经被用户删除掉的账单项，所以我们首先先根据账单Id查出所有的账单项，然后与前端传进来的新List进行遍历比较，本来这里是使用for循环进行遍历的，但是涉及到List这个结构，不允许在遍历的过程中对元素进行删除的机制（这个坑踩得贼难受），所以这里使用了while配合迭代器的写法进行遍历，这样子可以实现对List进行remove操作。

```java
while(oldIterator.hasNext()){
    BillItem old = oldIterator.next();
    int exist = 0;//是否依然存在新的list中

    Iterator<BillItem> newIterator = newList.iterator();
    while (newIterator.hasNext()){
        BillItem New = newIterator.next();
        if(old.getId().equals(New.getId()))
        {
            exist = exist+1;
            break;
        }
    }

    if(exist==0){//已经不存在
        oldList.remove(old);//将其从账单中删除
        delete = delete + billService.deleteBillItemById(old.getId().intValue());//删除该记录
        oldIterator.remove();
    }
}
```

​		现在我们已经将所有被删除的项目从后台数据库中删除掉了，剩下的就是判断是否被修改过，这里重点用到了一个算法，可以将两个List中所有不同的元素进行筛选出来，这里我把这个算法封装到了工具类Utils中的getDiffrent方法中。

```java
public class Utils {
    //对两个list进行比对，取出不相同的元素封装成List返回
    public static List<BillItem> getDiffrent(List<BillItem> col1, List<BillItem> col2){
        //创建返回结果
        List<BillItem> diffrentResult = new ArrayList<>();
        //比较出两个集合的大小，在添加进map的时候先遍历较大集合，这样子可以减少没必要的判断
        List<BillItem> bigCol = null;
        List<BillItem> smallCol = null;
        if (col1.size() > col2.size()) {
            bigCol = col1;
            smallCol = col2;
        }else {
            bigCol = col2;
            smallCol = col1;
        }
        //创建 Map<对象,出现次数> (直接指定大小减少空间浪费)
        Map<Object, Integer> map = new HashMap<>(bigCol.size());
        //遍历大集合把元素put进map，初始出现次数为1
        for(BillItem p : bigCol) {
            map.put(p, 1);
        }
        //遍历小集合，如果map中不存在小集合中的元素，就添加到返回结果，如果存在，把出现次数置为2
        for(BillItem p : smallCol) {
            if (map.get(p) == null) {
                diffrentResult.add(p);
            }else {
                map.put(p, 2);
            }
        }
        //把出现次数为1的 Key:Value 捞出，并把Key添加到返回结果
        for(Map.Entry<Object, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diffrentResult.add((BillItem) entry.getKey());
            }
        }
        return diffrentResult;
    }
```

​		这个算法可以做到很好的时间和空间复杂度，使用map计数的方法快速遍历筛选出不一样的对象元素，算法也比较直观易懂，如果这段源码理解不了的话，可以参考：

https://blog.csdn.net/qq_35235028/article/details/78553514?utm_medium=distribute.pc_relevant.none-task-blog-baidujs-3

https://blog.csdn.net/wanzhix/article/details/85706852?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-5.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-5.nonecase

​		这两个博客都有提到这个算法的具体实现，注意如果要实现这个算法，若List中的元素是类对象（如这里是BillItem），则该类需要重写类的equals与hashCode方法，这里我使用了Lombok的@Data注解，除了生成get，set方法之外也为我重写了equals与hashCode方法

​		接着说，我们在判断出哪些是已经删除过的账单项后，这个List就只剩下3种元素：**未修改过的，修改过的，以及新添加的**，首先判断后台是否有这个账单项的Id，检索数据库的时候就能得知，**因为我们的账单项都是在后台进行插入操作的时候，自动生成标识某条数据记录的，唯一的主键Id**，此时前端在将这个账单项对象加入List传到后台的时候，**切记不能自己给账单项的JS对象填写Id，就直接让Id这一项为空，事实上随便为这个新账单项安一个Id也是做不到的**，便于此时进行识别。我们遍历这个List，如果Id这一项为空，直接将这个账单项插入到我们的数据库记录中

```java
List<BillItem> diffrentList = new ArrayList<BillItem>();
diffrentList = Utils.getDiffrent(oldList,newList);//抓取出所有修改过的数据

for (BillItem diffrentitem : diffrentList) {
    if (diffrentitem.getId()==null){
        add = add + billService.addNewBillItem(diffrentitem);//添加新的账单记录
    }
    else update = update + billService.updateBillItem(Utils.objectToMap(diffrentitem));//修改账单记录

}
```

​		那么剩下来的，就只有未修改过和被修改过的账单项了，未修改过的账单项自然不用去动执行修改账单项的操作即可



#### InitializeController

​		**注意：这一Controller的内容均为页面初始化的操作，大同小异，这里仅以某个方法作示例**

##### groundInitialize（第72行）

```java
@RequestMapping("/ground")
//进入游记广场进行初始化数据填充，刷新时重复这一方法
public String groundInitialize(@RequestParam("UserId") int userid,@RequestParam("Count") int count){
    BaseResponse response=new BaseResponse(StatusCode.Fail);
    //初始化精选游记
    PageBean<Article> IsChoicelyArticle = new PageBean<Article>();
    IsChoicelyArticle = initializeService.initializeIsChoicelyArticleIndex(count,6);
    //初始化小当家游记
    PageBean<Article>  IsGoodKidArticle = new PageBean<Article>();
    IsGoodKidArticle = initializeService.initializeIsGoodKidArticleIndex(count,6);
    //初始化好友动态
    PageBean<Article>  FriendArticle = new PageBean<Article>();
    FriendArticle = initializeService.initializeFriendArticleIndex(userid,count,6);
    //初始化身边动态
    PageBean<Article>  AroundArticle = new PageBean<Article>();
    AroundArticle = initializeService.initializeAroundArticleIndex(userid, count,6);
    //封装成map
    Map<String, Object> Datamap = new HashMap<String, Object>();
    Datamap.put("IsChoicelyArticle",IsChoicelyArticle);
    Datamap.put("IsGoodKidArticle",IsGoodKidArticle);
    Datamap.put("FriendArticle",FriendArticle);
    Datamap.put("AroundArticle",AroundArticle);
    if(Datamap != null && Datamap.isEmpty()!= true ){
        response.setCode(0);
        response.setMsg("成功");
        response.setData(Datamap);
        String result = JSON.toJSONString(response);
        return result;
    }
    else{
        String result = JSON.toJSONString(response);
        return  result;
    }
}
```

​		这里我们对首页用户的游记内容推送，是通过分页查询的方式实现的，因为推送算法这么牛逼的东西还没有学会，学会了也没办法作这么多推送相关参数的设计（有点菜）。既然谈到分页查询，这里就必须说一下分页查询实现的类，PageBean的设计以及对PageBean相关的操作

```java
public class PageBean <T> {
    private int currPage;//当前页数
    private int pageSize;//每页显示的记录数
    private int totalCount;//总记录数
    private int totalPage;//总页数
    private List<T> lists;//每页的显示的数据

}
```

​		可以看到，真正的数据是封装在PageBean的lists这一成员变量中的，使用到了泛型的知识

​		我们抽取其中一条操作作为例子进行解析

```java
 //初始化精选游记
PageBean<Article> IsChoicelyArticle = new PageBean<Article>();
IsChoicelyArticle = initializeService.initializeIsChoicelyArticleIndex(count,6);
```

​		前端需要设计一个变量Count来记录访问的次数，因为用户刷新时，我们需要进行分页查询给用户返回不同的内容，一般来说，进入页面时Count为1，用户每次刷新后，Count++

​		调用了相应的Service方法，将每页要显示的数据写死为6

```java
//首页初始化分页检索精选游记
public PageBean<Article> initializeIsChoicelyArticleIndex(int Count, int Page) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    PageBean<Article> pageBean = new PageBean<Article>();
    //封装当前页数
    pageBean.setCurrPage(Count);
    //每页显示的数据
    int pageSize = Page;
    pageBean.setPageSize(pageSize);
    //封装总记录数
    int totalCount = initializeMapper.queryIsChoicelyCount();
    pageBean.setTotalCount(totalCount);

    //封装总页数
    double tc = totalCount;
    Double num = Math.ceil(tc / pageSize);//向上取整,除不尽的时候都向上取整
    pageBean.setTotalPage(num.intValue());

    map.put("start", (Count - 1) * pageSize);
    map.put("size", pageBean.getPageSize());
    //封装每页显示的数据
    List<Article> lists = initializeMapper.initializeIsChoicelyArticleIndex(map);
    pageBean.setLists(lists);
    return pageBean;

}
```

​		可以看出，无论是操作次数的计数Count还是页数大小的设置Page，均使用了set方法，然后执行queryIsChoicelyCount方法将总的记录数记录下来，以便后期计算页数

​		最后将要查询的起点start以及数据量sieze封装进map，传输到Dao层进行查询

```xml
<select id="initializeIsChoicelyArticleIndex" parameterType="map" resultType="Article">
    select *
    from tyydemo1.article
    <where>
        Is_Choicely = 'T'
    </where>
    order by Time desc
    <if test="start!=null and size!=null">
        limit #{start},#{size}
    </if>
</select>
```

​		查询出一个List，封装进pageBean的lists中，返回给前端

​		**其实这里仅仅只有普通的分页查询过程，当用户的点击量非常多以至于触碰到了最后一页时，这种情况的处理并没有写完，但是思路大概是前端每一次Count++之后，与上一次拿到的PageBean对象比对，查看此时的Count是否大于PageBean域中的currPage，若是的话，则已经到达上限，并作相应的处理，或者直接摒弃这种分页查询的方法，使用另外的方式进行内容推送都可以**

​		InitializeServiceImpl这一层中，initializeFriendArticleIndex与initializeAroundArticleIndex这两个方法只是需要多一个UserId参数进行文章的检索而已，其他的并没有什么区别



#### UserController

##### login（第52行）

​		十分重要的内容，因为在设计上，我们并没有设计自己的登录功能，而是使用了微信自带的登录API，这样设计的目的是为了获得微信后台的授权，这样子我们可以直接获得一些微信端的数据，比如说微信头像作用户头像，通过微信API接口获得用户当前的位置等等，这里也是开发中的一个坑之一，因为我们要去搞清楚wx.login()这个方法的工作机制。

​		首先来看官方提供的图

​		![img](文档图片/webp)

​		解释一下流程：

​		首先，是小程序的前端调用wx.login()方法，获得一个code，然后通过请求，将这个code发送到我们后端的服务器上，后端的服务器将小程序的appid，wxSercret以及获取到的code等参数打包，发送到微信的服务器端，最后微信服务器端会给后台返回openid与session_key，openid理论上来说对于每一个微信用户都是唯一的，因此可以使用openid对用户进行识别。后端获取到这两个参数之后，需要自己对其进行处理，如何处理？开发者可以按照自己的业务需求任意处理均可。**但是要注意，不能够直接向前端暴露openid以及session_key这两个敏感信息，这是唯一要遵守的原则，在满足这个条件下，如何对这两个参数进行处理封装都可以**，前端的登录态是由我们开发者自己设计并维护的，上图只是一种参考做法而已

​		流程大概说完了，进入代码部分，这部分是请教了16级的师兄才完成的，有所借鉴师兄的代码，先说一下现在的设计：由于开发进度的关系，这边直接将openid作为后台的主键Id，这种做法非常不好，师弟师妹应该修改，主键Id和openid应该分开，意思就是说后台数据库的设计中要增加一个openid字段。然后，因为这边没有用到Redis数据库的内容，所以在后台的设计是登录态永久，即第一次登录后直接在数据库内写死记录，以后再不用重新登录，这一点也设计得不好，至少要有那种三天过后重新登录的机制

​		和师兄交流的过程也贴上，希望能有所帮助：

<img src="文档图片/image-20200609105734006.png" alt="image-20200609105734006" style="zoom: 25%;" />

<img src="文档图片/image-20200609105822544.png" alt="image-20200609105822544" style="zoom:25%;" />

<img src="文档图片/image-20200609105849924.png" alt="image-20200609105849924" style="zoom:25%;" />

<img src="文档图片/image-20200609105905673.png" alt="image-20200609105905673" style="zoom:25%;" />

<img src="文档图片/image-20200609105919501.png" alt="image-20200609105919501" style="zoom:25%;" />

<img src="文档图片/image-20200609105932382.png" alt="image-20200609105932382" style="zoom:25%;" />

​		所以这里先贴上根据自己的后台设计修改过的代码：

​		

```java
@RequestMapping("/login")
    public String login(@RequestParam(required = false) String code){
        BaseResponse response=new BaseResponse(StatusCode.Fail);
        try {
            logger.info("[登录参数]:code = "+code);

            if(StringUtils.isEmpty(code)){
                response.setMsg("缺少必要参数");
                String result = JSON.toJSONString(response);
               return result;
            }


            String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+wxAppid+"&secret="+wxSecret+"&js_code="+code+"&grant_type=authorization_code";
            logger.info("调用微信登录接口,参数:["+url+"]");
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            String wxResponse = okHttpUtil.get(url);//向微信服务器发送请求
            logger.info("调用微信登录接口,code:["+code+"],返回数据:["+wxResponse+"]");
            if(StringUtils.isEmpty(wxResponse)){
                logger.error("调用微信登录接口错误,code:["+code+"],返回数据为空");
                response.setMsg("登录失败");
                String result = JSON.toJSONString(response);
                return result;
            }
            Map<String,Object> resultMap = (Map<String, Object>) JSON.parse(wxResponse);
            if(resultMap.containsKey("errcode")){
                logger.error("调用微信登录接口错误,code:["+code+"],返回数据:["+wxResponse+"]");
                response.setMsg("登录失败");
                String result = JSON.toJSONString(response);
                return result;
            }
//            String wxResponse = "test";
//            Map<String,Object> resultMap = new HashMap<>();
//            resultMap.put("openid","oJYeX5D-EUEthnSaxAZGr0j9nbeg");
//            resultMap.put("session_key","123");



            User user = new User();
            int openid ;
            String sessionKey = null;
            if(resultMap.containsKey("openid") && resultMap.containsKey("session_key")){
                openid = (int) resultMap.get("openid");
                sessionKey = (String) resultMap.get("session_key");
            }else{
                logger.error("调用微信登录接口错误,code:["+code+"],返回数据:["+wxResponse+"]");
                response.setMsg("openid为空");
                String result = JSON.toJSONString(response);
                return result;
            }

            user.setUserId(openid);

            Integer userId = null;
            user = userService.queryUserById(openid);
            if(user == null){
                user.setUserId(openid);
                userService.addUser(openid);//此处使用分配到的openid作为数据库中的用户主键ID以区分唯一性
                userService.addUserInfo(openid);
                response.setCode(0);
                response.setMsg("登录成功");
                response.setData(userService.queryUserInfoById(openid));
                String result = JSON.toJSONString(response);
                return result;
            }else{
                response.setCode(0);
                response.setMsg("登录成功");
                response.setData(userService.queryUserInfoById(openid));
                String result = JSON.toJSONString(response);
                return result;
            }

        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("[登录异常]");
            String result = JSON.toJSONString(response);
            return result;
        }
    }
```

​		可以看出，就是简单的进行查询，若后台不存在数据则进行新用户的插入，关于登录态的过期等设计，还有openid的保护等等全部都没有做（道理都懂但就是写不出:sob:）

​		所以，这边贴上师兄的源代码，师弟师妹们尽量参考这个对以后要实现的业务的进行修改，包含当时我对师兄提出的问题以及回复

​		

```java
/**
     * 小程序登录
     * @param code
     * @return
     */
    @ApiOperation(value = "小程序登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "访问凭证",required = false,paramType = "header"),
            @ApiImplicitParam(name = "code",value = "编码",required = false,paramType = "query")
    })

    @PostMapping("login")
    public Result login(@RequestHeader(required = false)String token,@RequestParam(required = false) String code){//可否看一下Result类的设计？
        try {
            infoLogger.info("[登录参数]:token = "+token+";code = "+code);//infoLogger.info是师兄自己写的打印方法是吗，若是的话就不作过多疑问了
            //是的，实现如下
            //在controller下面加上 private static final Log infoLogger = LogFactory.getLog(WxApiController.class);
  
            if(StringUtils.isNotEmpty(token)){
                String redisKey = RedisConstants.USER_TOKEN + ":{"+token+"}";//这里应该是生成redisKey的方法，可否看一下token字符串的生成方法RedisConstants.USER_TOKEN？
                //redis是key-value格式存储数据，通过Key,拿value；那么
                
                WxUserVo userInfo = JSON.parseObject(stringRedisTemplate.opsForValue().get(redisKey),WxUserVo.class);//WxUserVo是师兄写的实体类是吗，可否看一下类的设计？
                if(userInfo != null){//可否看一下userinfo的设计？主要是为了了解前端需要获得什么字段才能完成正常的登录
                    long logintime = System.currentTimeMillis();
                    userInfo.setLogintime(logintime);
                    return Result.success(userInfo);//更新登陆时间，没有疑问
                }
                return Result.express();
            }
            if(StringUtils.isEmpty(code)){
                return Result.fail(-1, "缺少必要参数");
            }

            String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+wxAppid+"&secret="+wxSecret+"&js_code="+code+"&grant_type=authorization_code";
            infoLogger.info("调用微信登录接口,参数:["+url+"]");
            String wxResponse = okHttpUtil.get(url);
            infoLogger.info("调用微信登录接口,code:["+code+"],返回数据:["+wxResponse+"]");
            if(StringUtils.isEmpty(wxResponse)){
                infoLogger.error("调用微信登录接口错误,code:["+code+"],返回数据为空");
                return Result.fail(-1, "登录失败");
            }
            Map<String,Object> resultMap = (Map<String, Object>) JSON.parse(wxResponse);
            if(resultMap.containsKey("errcode")){
                infoLogger.error("调用微信登录接口错误,code:["+code+"],返回数据:["+wxResponse+"]");
                return Result.fail(-1, "登录失败");

            }
//            String wxResponse = "test";
//            Map<String,Object> resultMap = new HashMap<>();
//            resultMap.put("openid","oJYeX5D-EUEthnSaxAZGr0j9nbeg");
//            resultMap.put("session_key","123");

            WxUser wxUser = new WxUser();//wxUser是在数据库自定义的用户类是吗，可否看一下类的设计？
            String openid = null;
            String sessionKey = null;
            String unionid = null;//我们的项目仅仅只是小程序端，不涉及公众号等应用，在这种情况下unionid作为同一微信账号的用户唯一性识别ID，是否必须要进行存储？
            if(resultMap.containsKey("openid") && resultMap.containsKey("session_key")){
                openid = (String) resultMap.get("openid");
                sessionKey = (String) resultMap.get("session_key");
            }else{
                infoLogger.error("调用微信登录接口错误,code:["+code+"],返回数据:["+wxResponse+"]");
                return Result.fail(-1, "openid为空");
            }
            unionid = (String) resultMap.get("unionid");
            wxUser.setOpenid(openid);
            wxUser.setUnionid(unionid);
            Integer userId = null;
            WxUser user = wxUserManager.selectByOpenid(openid);
            if(user == null){
                wxUserManager.add(wxUser);//此处使用分配到的openid作为数据库中的用户主键ID以区分唯一性
                userId = wxUser.getId();//此处的Id与openId是否有所区别？想观摩一下类的设计
            }else{
                userId = user.getId();
                if(StringUtils.isNotBlank(unionid)){
                    wxUserManager.updateUnionidByOpenid(wxUser);
                }
            }
            //以下操作为对redis进行数据更新，但是由于我并没有学习过redis数据库，后台数据库是由MySQL完成，可否师兄解释一下其中做了什么操作？
            long logintime = System.currentTimeMillis();
            String sign = jwtUtil.sign(openid + logintime);
            String redisKey = RedisConstants.USER_TOKEN + ":{"+sign+"}";//生成token签名，以便下次登录的操作
            String redisSessionKey = RedisConstants.USER_SESSIONKEY + ":{"+sign+"}";//此SessionKey和上一行的redisKsy有何不同？烦请师兄解惑
            WxUserVo userVo = wxUserManager.selectUserVoByOpenId(openid);
            userVo.setToken(sign);
            userVo.setUserId(userId);
            userVo.setLogintime(logintime);
            stringRedisTemplate.opsForValue().set(redisKey,JSON.toJSONString(userVo));
            stringRedisTemplate.expire(redisKey, EXPRIE_REDIS, TimeUnit.MINUTES);
            stringRedisTemplate.opsForValue().set(redisSessionKey,sessionKey);
            stringRedisTemplate.expire(redisSessionKey, EXPRIE_REDIS, TimeUnit.MINUTES);
            userVo.setUserId(null);
            return Result.success(userVo);//最后封装的对象是userVo，与先前的userinfo有何区别？为何返回的类型不同？可否看一下类的设计？
        }
        catch (Exception e){
            e.printStackTrace();
            infoLogger.error("[登录异常]" + ExceptionDetailUtil.getExceptionDetail(e));
            return Result.fail();
        }
    }

```

​		1、Result()类是用来做统一数据返回，也就是代码规范

```java
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		// 响应业务状态
		private Integer status;
	 
		// 响应消息
		private String msg;
	 
		// 响应中的数据
		private Object data;
		
		public Result(Integer status, String msg, Object data) {
			this.status = status;
			this.msg = msg;
			this.data = data;
		}

		public static Result build(Integer status, String msg, Object data) {
			return new Result(status, msg, data);
		}

		public static Result success(Object data) {
			return new Result(200, "success", data);
		}

		public static Result success() {
		return new Result(200, "success", null);
	}
		
		public static Result fail(Integer status, String message) {
			return new Result(status, message, null);
		}
		public static Result fail() {
			return new Result(-1, "网络异常，请稍后重试！", null);
		}
		public static Result fail(String message) {
		return new Result(-1, message, null);
	}
		public static Result express() {
		return new Result(400, "凭证无效或超时！", null);
	}
		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		
}
```

2、 infoLogger实现如下： 在controller下面加上

```java
private static final LoginfoLogger = LogFactory.getLog(WxApiController.class);
```

3、redis是key-value格式存取数据，通过Key,拿value；此处生成key，RedisConstants是一个通用方法，这是String 的+运算，RedisConstants.USER_TOKEN 你可以随便取，统一就行

4、WxUserVo()实体类如下，这是VO层，真正实体类是WxUser()

```java
public class WxUserVo {
    private Integer userId;

    private String nickname;

    private String avatarUrl;

    private String openid;

    private String unionid;

    private String phone;

    private int gender;

    private String token;

    private long logintime;

    private Integer authUserid;

    private Integer type;
    }
public class WxUser {
    private Integer id;

    private String openid;

    private String unionid;

    private String phone;

    private Date createTime;

    private Date updateTime;

    private String nickname;

    private String gender;

    private String avatarUrl;

    private Integer authUserid;

    private Integer type;
    }
```

5、userInfo 存的是用户信息，内容就是下面用户不是用token登录时忘redis存的内容

6、unionid是否需要，要看你们自己的业务，能拿到的数据最好就拿，后续如果有需要就不用改代码

7、Id是自增主键，openid是openid

8、redis块主要是对用户信息进行存储，可以看到代码逻辑是用户不是使用token进行登录，而是使用code进行登录，那么这个时候要做的就是生成用户token，并且保存起来，之后再token未失效情况下，和前端都是使用Token进行数据交互；此处根据opinid以及登录时间进行加密生成token ，然后直接放到uservo里面，再把这个uservo直接存到redis里面，并设置过期时间；同样的，也把session_key存到redis里面，并设置过期时间

9、

```java
 String redisKey = RedisConstants.USER_TOKEN + ":{"+sign+"}";//生成token签名，以便下次登录的操作
```

​		这是生成key，不是生成签名

10、

```java
            String redisSessionKey = RedisConstants.USER_SESSIONKEY + ":{"+sign+"}";//此SessionKey和上一行的redisKsy有何不同？烦请师兄解惑
```

​		这里是生成存取session_key的key

11、

```java
  return Result.success(userVo);//最后封装的对象是userVo，与先前的userinfo有何区别？为何返回的类型不同？可否看一下类的设计？
```

​		这里是把你存到redis的用户数据返回给前端而已，前端拿到token之后，以后都拿token进行数据交互就行了



​		大概资料就是这些了，其实这一部分真的非常的重要，因为我们是要调用微信的登录API，所以这部分需要按照微信的规范来走，要花比较多的功夫在这一部分的设计和编码上

​		**如果师弟师妹要沿用使用微信API登录的设计思路，在这里我强烈建议**

​		**①学习redis数据库的内容，如此一来维护登录态的实现会简单很多**

​		**②重新设计User的数据库，将openid与数据库的主键id分开为两个字段，不要混淆**

​		这一部分师兄写的不是很好，所以尽量参考16级大师兄的写法，也要尽量在网上多看相关的技术博客以及官方文档，啃下这一块业务，加油！



##### sendPhoneCode （第226行）

```java
@RequestMapping("/sendPhoneCode")//绑定手机号或换绑手机号，发送验证码到手机
public String sendPhoneCode(@RequestParam("UserId")int UserId,@RequestParam("PhoneNum")String PhoneNum){
    BaseResponse response=new BaseResponse(StatusCode.Fail);
    boolean status = userService.sendCode(UserId,PhoneNum);
    if (status){
        response.setCode(0);
        response.setMsg("成功");
        response.setData("验证码已发送");
        String result = JSON.toJSONString(response);
        return result;
    }
    else{
        response.setData("请求失败");
        String result = JSON.toJSONString(response);
        return  result;
    }
}
```

​		这一部分主要是关于手机验证码发送的逻辑，在用户输入要验证的新手机后，点击获取验证码，进入这个方法，进入Service层查看业务逻辑

```java
//用户更新手机号，发送短信验证码
public boolean sendCode(int UserId,String Phone) {
    int random = (int)((Math.random()*9+1)*100000);//生成6位随机数作验证码
    Date nowdate = new Date();
    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    sdf.format(nowdate);//生成当前时间
    HashMap<String, Object> param = new HashMap<>();
    param.put("code",random);
    boolean suceeess = Utils.sendSms(Phone,"SMS_189836374",param);//调用工具类发送短信
    if (suceeess){//若短信成功则向数据库插入一条记录
        PhoneCode phoneCode = new PhoneCode();//对象封装
        phoneCode.setUserId(UserId);
        phoneCode.setPhone(Phone);
        phoneCode.setTime(nowdate);
        phoneCode.setCode(random);//生成6位随机数作验证码
        if(userMapper.createRandomCode(phoneCode)!=0)
        return true;
    }
        return false;
}
```

​		这里注意了，我实现这部分内容使用了阿里云的短信业务，师弟师妹们可以参考这个视频完成，如果看不懂代码，请把这个视频反复的观看，直到了解整个过程

​		https://www.bilibili.com/video/BV1c64y1M7qN

​		这里我将这个业务调用封装进了工具类Utils中，查看这个方法：

​		

```java
//基于阿里云的短信发送方法
public static boolean sendSms(String phoneNum,String templateCode,Map<String,Object> code){

    //连接阿里云
    DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FyJqPbDcQG9Qu3Us4bA", "pPY3xldRMUuWiB2ijedV0y94eubApp");
    IAcsClient client = new DefaultAcsClient(profile);

    //构建请求
    CommonRequest request = new CommonRequest();
    request.setMethod(MethodType.POST);//勿要改动
    request.setDomain("dysmsapi.aliyuncs.com");//勿要改动
    request.setVersion("2017-05-25");
    request.setAction("SendSms");//事件名称

    //自定义参数 手机号 验证码 签名 模板
    request.putQueryParameter("PhoneNumbers", phoneNum);
    request.putQueryParameter("SignName", "途游游");
    request.putQueryParameter("TemplateCode", templateCode);
    //短信验证码
    request.putQueryParameter("TemplateParam", JSON.toJSONString(code));
    try {
        CommonResponse response = client.getCommonResponse(request);
        System.out.println(response.getData());
        return response.getHttpResponse().isSuccess();
    } catch (ServerException e) {
        e.printStackTrace();
    } catch (ClientException e) {
        e.printStackTrace();
    }
    return false;
}
```

​		注意这里面的某些参数是用我自己的阿里云账号的某些信息填充的，比如短信的模板等，师弟师妹们到时候需要自己上阿里云完成模板的审核，然后填写自己账号信息进去。

​		这里如果成功发送了短信后，调用userMapper.createRandomCode(phoneCode)，把验证码的内容插入到后台的数据库中

​		在MySQL数据库中我写入了一个事件，对于PhoneCode表，每过1分钟就会扫描全表，将已经存在表内10分钟的数据项自动删除，换言之，就是每个验证码记录的有效期是10分钟

```sql
DROP EVENT IF EXISTS `PhoneCodeDelete`;
delimiter ;;
CREATE DEFINER = `root`@`localhost` EVENT `PhoneCodeDelete`
ON SCHEDULE
EVERY '1' MINUTE STARTS '2020-05-10 21:06:25'
DO DELETE FROM phonecode
WHERE TIMESTAMPDIFF(SECOND,Time,NOW())>600
;;
delimiter ;
```
​		而后Controller层的checkPhoneCode方法就简单了，用户在输入收到的短信验证码后，在后台的PhoneCode表检索是否有相应的记录，若有则验证成功



##### uploadPicture（第285行）

​		这个说来复杂，是我们未能完成的功能之一，就是关于前端页面中，如何在小程序实现文章的文字和图片交替显示，文字和图片是用户随意组合的。如何做到用户上传服务器之后，再从后台提取出来的时候能按照原来的次序进行排列？因为我们按照设计，图片和文字是分开来在后台服务器进行存储的。本质上，其实就是类似于博客，浏览文章这个功能的正常实现。

​		这一部分应该是前端的内容，但是可惜师兄对前端没有什么深入的了解，这一部分只能前后端之间多多沟通，主要要解决的问题就是：前端要如何实现文章编辑，在用户点击发布文章之后，给后端传输什么样类型的数据存储到数据库中，以便以后直接从数据库中抽取出来就可以在前端进行显示

​		这一部分作为未完成的功能，师兄也还在探索之中，但是不管怎么样，图片的上传总是要完成的，所以即使目前前端的那位师兄并没有想出相应的解决方案给我，我索性提前写好了图片的上传以作备用

```java
@RequestMapping("/uploadPicture")//上传图片到服务器
public String uploadPicture(HttpServletRequest request){
    BaseResponse response=new BaseResponse(StatusCode.Fail);
    MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
    MultipartFile multipartFile = req.getFile("file");//获取前端的upload的name参数"file"
    String imgname = Utils.putImage(multipartFile);//上传到服务器
    if (imgname!=null){
        userService.addPicture(imgname);
        response.setCode(0);
        response.setMsg("成功");
        response.setData(imgname);
        String result = JSON.toJSONString(response);
        return result;
    }
    String result = JSON.toJSONString(response);
    return result;
}
```

​		这里上传图片封装到了工具类Utils.putImage()

```java
public static String putImage(MultipartFile file){
    Date nowdate = new Date();
    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    StringBuilder filename = new StringBuilder(sdf.format(nowdate));
    //生成时间防止文件名重复
    //生成一个随机数来防止文件名重复
    int x=(int)(Math.random()*1000);
    String str = String.valueOf(x);
        filename.append(str);

    //取得文件的格式后缀
    String originalLastName = file.getOriginalFilename();
    String picLastName = originalLastName.substring(originalLastName.lastIndexOf("."));
    filename.append(picLastName);

    //写死存储路径
    String realpath = "/home/img";
    try {
        File dir = new File(realpath);
        //若目录不存在则创建目录
        if (!dir.exists()) {
            dir.mkdir();
            System.out.println("创建文件目录成功：" + realpath);
        }
        File savefile = new File(realpath,filename.toString().toLowerCase());
        file.transferTo(savefile);
        System.out.println("添加图片成功！");
        return filename.toString();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
}
```

​		注意这里写死的存储路径/home/img是Linux服务器环境下的路径

#### 注意

​		在部分需要保持不同表之间的字段相同的部分，本来实现是需要触发器实现的，但是写了好几个都不起作用，可以确认不是语法上的错误，后来没有办法，就写了一些业务方法在Service层面保证数据一致，曲线救国，例如UserServiceImpl实现类的updateUserInfo

```java
//修改个人信息
public int updateUserInfo(Map<String, Object> map) {
    int re = userMapper.updateUserInfo(map);
    if(re==1);
    userMapper.friendtrigger(map);
    return re;
}
```

​		后面的friendtrigger方法其实就是在做这样的事，这一点师弟师妹也可以优化一下，不要像我一样写的这么曲线救国:expressionless:

### 接口文档

​		其实本项目是和前端有编辑过一个接口文档的，不过使用的是在线的编辑工具，网站是：

​		https://easydoc.xyz/console

​		到时候师弟师妹自己注册账号上去创建自己的团队项目，在这个位置导入文件即可

![image-20200609151616443](文档图片/image-20200609151616443.png)

​		进去之后就能直接看到前后端传递的参数了

​		导入的文件见文件夹![image-20200609152514184](文档图片/image-20200609152514184.png)

​		配合这两个文件阅读本文档		![image-20200609153855838](文档图片/image-20200609153855838.png)

​		当然，这也是仓促之间在网上编辑的，事实上，据师兄了解还有一些更加优秀的接口文档方案，比如根据代码内的@API等注解就能自动生成的接口文档等等，这里师兄只是把我们小组的成果拿出来而已，师弟师妹也可以自行斟酌改进

​		**注意：本项目中所写到的所有URL，即Controller层方法中的@RequestMapping("")里面的访问路径，均是暂时编辑的，因为未到上线测试阶段所以暂时搁置了，URL路径的设计这一部分前后端都可以参与，关键是前后端要统一，哪个路径对应访问哪一个方法，前后端的心里都要有数**



---



## 后记

​		到这里，基本上比较困难的地方都讲解完了，在这里再啰嗦一些东西

​		首先，以上没有讲到的东西虽然只是纯粹的增删改查，没有详细地解释，但并不代表这不重要。如果是像师兄一样从JavaSE基础重新开始巩固知识学起的话，走到SSM这一步，都已经需要投入不少的时间来学习了，待到真正理解SSM的基础写法的时候，也已经走了不少的路，但是到了这一步也只是基础知识储备而已，如果前面学习的基础没有打好，比如Mybatis的<set>，<if>，<where>标签会不会用？@RestController和@Controller这些注解的区别？这些开发以及增删改查的逻辑还是没办法搞懂的。

​		除此之外，要重点理解三层架构的设计思路，这里有一些方法，比如上面提到的addReview，在Controller层也就是前端传入的Map只需要UserId,ArticleId,Reviews，但是到了Dao层却需要Time,Good这两个参数才能完成插入。事实上，在中间的Service层我们对这个Map加入了Time,Good这两个字段，因为生成时间Time和初始化点赞量Good这些操作根本没有必要在Controller层由前端传入，前端只需要关注UserId,ArticleId,Reviews这三个最关键的传入参数，剩下的重复工作由中间的Service层来进行处理，这样子在逻辑上可以更好地进行区分，体现三层架构的设计优势。如果Spring的AOP理解和掌握熟练的话，生成时间这种操作甚至可以直接在切面切入，只需要写一次时间生成就能在各个需要当前时间的Service中插入操作，不用写如此之多的重复代码，当然这都是后话了，能够实现功能的编码与设计是我们的第一要务，将代码写得更简洁需要更多的经验才能做到。

​		最后，再次对这个后台项目梳理一下，需要注意的点

​		**①openid的格式如图**![img](https://upload-images.jianshu.io/upload_images/4055666-9789786af7bc19db.png?imageMogr2/auto-orient/strip|imageView2/2/w/308/format/webp)

​		可以看到openid是一连串不规则的随机字符，包含数字与英文字符，所以在后台存储的时候要注意格式为char

​		

​		**②本项目还未完结的功能：**文章编辑/浏览的后台存储，涉及到图片链接等等，这里主要是前端的师弟师妹要注意完成的，写好之后要通知后端自己传输的参数是什么形式，后台好根据这一点设计数据库的存储

​		这一点我也有咨询过一些前端的师兄，师兄的意见是

		>  ​	一般这种我做的话应该有两种思路，一种编辑博客的时候采用富文本编辑器的方式，然后上传到服务器保存的也是一个dom结构树，这种优点是实现比较简单，然后用户编辑的时候就写word一样，缺点是应该提供的样式就比较受富文本编辑器本身限制。第二种是自己写一个文章的编辑器，可以自定义各类型的元素，然后上传服务器也是相应元素的数据结构，优点是可以根据业务开发一些比较好看的组件供用户调用，自由度较好，用户编辑的效果也好，缺点是开发编辑器要消耗比较大的精力，而且如果要真正作为产品还要很多适配
		>
		>  ​		如果做毕设的话而且师弟对前端比较熟悉的话。可以去github上当一些简单编辑器的源码。然后根据自己的需求进行二次的封装

​		希望能对负责前端的师弟师妹有所帮助

​		其次还有一个没有完成的功能是，关于亲子关系之间的绑定

​		我们的设计思路是，用户点击亲子关系绑定的按钮后，用户根据用户名检索想要绑定的用户(Target)，然后确认绑定的关系类型后（我是他爹妈/儿子），将会以系统通知的形式发送到被绑定的用户处，被绑定用户阅读完消息之后，同意绑定则这两个用户的亲子关系就确认下来

​		本来这个功能并不是我在负责的，把精力都放到了整个后台的开发上，但是偶尔也会思考实现，抽空想出来一个非常笨的方法：专门做一个表，用户对另外一个用户发起关系绑定请求的时候就添加一条记录到表里，然后每个用户上线进入个人页面之后，查那个表看看有没有针对自己的记录，记录设置一个过期时间，数据库定时将过期记录删除，有表项的话说明有人对自己发出了关系绑定的申请

​		

​		**③本项目编码的一些建议：**

​		首先，师弟师妹可以需要租下一台云服务器，师兄使用的是阿里云的设备，因为疫情期间有学生免费赠送6个月云服务器的活动，所以免费得到了一台服务器进行部署。在测试之前要做好服务器的域名购买以及备案的工作，小程序规定，其网络访问请求必须通过https协议进行域名访问，不能通过IP进行访问，又因为域名信息交到国家工信部进行备案需要一定的时间（至少一周），所以为了避免耽误测试的进度，最好在进入上线测试阶段时尽快做好这些工作，如果能在测试前做好就更好了，在项目部署上可以稍微快一点

​		建议学习redis数据库的内容，可以更好地实现一些业务

​		基础知识的功底要学扎实，前面提到的学习路线一定要看。当然这只是师兄我自己的学习方式，如果不知其所以然，很多东西用起来会非常的不顺心，当然也不要太过钻牛角尖，后端学习的特点就是深度深，前端的特点是广度广，一入后端深似海，但是后端的好处就是比较规范，大家都敞开了写代码

​		关于时间类的处理，这里我全部都统一使用了yyyy-MM-dd HH:mm:ss的Date格式，而且没有封装成工具类，直接添加到了数据库的DateTime类型的字段中，如果到前端显示的部分需要进行格式处理的话，还需要师弟师妹自行完善一下

​		一定要好好地写测试，在师兄这里，因为和前端没有对接的原因，Controller层这种一定要在线上环境做测试的方法我并没有测试，但是Service层的所有方法我基本都测试过一遍。开发中写完一个方法后就要对这个方法进行单独的测试，要养成好习惯，如果一点测试都没有做，到时候在线上出现错误，就无法知道到底是前后端传参的，Contreller层，Service层或者Dao层哪一层出现了错误，排错极其地麻烦，效率降低。写完一个方法后立刻在Service层先测试，这样子可以一口气对Service与Dao层两层进行排错，待到方法积少成多后，最后只需要关注前后端网络传输的部分。至于平时写代码要写注释这个习惯就不多说了，如果不写注释过一个星期甚至自己写的东西都看不懂了:laughing:

![image-20200609152007455](文档图片/image-20200609152007455.png)

​		最后再重复一次，本项目后台开发进度约为80%，Controller层的的方法均未经过测试，目前项目止步于前后端对接测试阶段，本文档由本人撰写而成，仅对后台开发的关键部分进行说明，我与前端部分也没有接触，至今不知道前端设计的界面模样，自己全都是照着我画的设计图进行后台设计的，大概的进度就是在这个位置。

​		所以师弟师妹如果要按照我们已有的成果进行开发的话，首先要理解这个后台的设计，希望师兄写的代码可读性不会太差:stuck_out_tongue_winking_eye:，事实上也有很多设计的非常不好的地方，师弟师妹可以考虑重构一下。理解了设计之后，把剩下的20%内容却也是非常重点的内容给开发攻克下来，最后进行上线测试，再debug。大概的流程就是这样子

​		这个项目最后没有看到它的模样，也是有点可惜，但是师兄确实是在开发过程中学到了不少东西，希望师弟师妹们也能有所收获，努力完成这个开发，加油！[^2]



[^1]:作者：17网工1班 陈林涛
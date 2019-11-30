###小匠项目跟着视频学习中学到的一些知识
码匠Spring Boot笔记

写完代码关闭电脑前先将代码放入到github


Ctrl+F火狐浏览器快速搜索项要的字符内容
Ctrl+W关闭当前页面


####1
idea快捷键:
Ctrl+Shift+n==>快速搜索文件
Ctrl+Shift+F12==>快速切换最大屏幕(再按住切回原屏幕)
psvm==>public static void main(String[] args) { }
Ctrl+Alt+v==>快速创建实例化对象
Shift+Enter==>快速换行(从上一行任何位置换至下一行开头)
Ctrl+E==>切换为最近的工作环境
Shift+F6==>更改变量名（可更改一个或全部）（也可进行对文件的命名）
Ctrl+W==>选中一层{}包含的内容，不停的摁，不停的选中上一层
Ctrl+Alt+L==>格式化程序（对齐）
Ctrl+D==>直接复制该行至下一行
Ctrl+Shift+向上键==>直接将该行代码上移
Ctrl+Enter==>查看源码
Ctrl+P==>获得该方法的参数信息
Ctrl+F12==>查看该类的所有属性跟方法



####2
用idea将项目中的文件发送到远程仓库github(创建一个新仓库将文件远程导入,开始创建仓库时没有添加readme,因为添加了后面代码比较麻烦,所以自己创建一个readme)
  打开idea下面的terminal控制台
   一.初始化git ==>git init
   二.将所有修改文件==>git add .
  三.将文件打包到一个文件中==>git commit -m "名字"
  四.git 添加origin的一个远程地址==>git remote add origin git@github.com:yy-huang2019/community.git
      如远程地址输入有误将该远程地址移除(git remote remove origin)然后重新添加
  五.将文件发送到远程仓库==>git push -u origin master
  六.回到idea,在项目根目录下创建一个文件取名README.md
  七.若要再次将文件上传到github,则需要使用另一条命令==>git commit --amend --no-edit
       --amend表示追加，将之前变化的文件追加上去,--no-edit表示不需要编辑原来文件
      然后再git push将文件push上去

 八：如果别人在远端也将项目导入到仓库，则将项目push上去时会被拒绝，因此先将远端项目拉过来git pull，
        然后 :x 退出并保存（merge合并），然后git push将项目push上去




####3
调验github的API，实现github的快速登录(一般网站下方都有API)
   ①先注册一个API在github
   ②如何授权github登录
       https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/

Actor--->访问码匠社区                                                        GitHub
                        |                                                                        |
                        |----- 登录                 authorize                         |
                        |---------------------------------------------->|
                        |<----------------------------------------------|
                        |                    回调redirect-uri携带code             |
                        |                                                                        |
                        |                    access_token携带code                 |
                        |----------------------------------------------->|
                        |<----------------------------------------------|
                        |                        返回access_token                    |
                        |                                                                       |
                        |                      user携带access_token                |
                        |---------------------------------------------->|
                        |<----------------------------------------------|
                        |                          返回user信息                         |
    登陆成功       |-----|
<-------------- |-----存入数据，跟新登录状态



####4
注解
   ①Contoller:是将路由API是承载者
   ②Component是将当前类初始化到spring的上下文(对象自动实例化,不需要重新实例化对象)，
   一般用于bean的实例化（get和set方法）
   在使用 @Component 注释后，Spring 容器必须启用类扫描机制以启用注释驱动 Bean 定义
   和注释驱动 Bean 自动注入的策略。再在使用该bean的class中添加@AutoWired
   ③Autowired自动注入不需要实例化对象,与Compentent连用
   



####5
编程习惯：
   数据超过两个,应将其封装起来，调用对象的方式去调用;
 DTO:data transfor do数据传输模型




####6
okHttp==》java使用post发送请求（需要先添加dependency的依赖）
   (官网：https://square.github.io/okHttp)  （如搜索不到最新版本，去maven仓库查找https://mvnrepository.com）
   <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
            <version>(最新版本)</version>     
        </dependency>

(post方式请求)
   public static final MediaType JSON
    = MediaType.get("application/json; charset=utf-8");

OkHttpClient client = new OkHttpClient();

String post(String url, String json) throws IOException {
  RequestBody body = RequestBody.create(json, JSON);            //JSON表示的是传递的是JSON类型的数据,若是java类型的实体类,需先通过JSON.toJSONString()转化
  Request request = new Request.Builder()
      .url(url)
      .post(body)
      .build();
  try (Response response = client.newCall(request).execute()) {
    return response.body().string();
  }
}   
(get方式请求)
OkHttpClient client = new OkHttpClient();

String run(String url) throws IOException {
  Request request = new Request.Builder()
      .url(url)
      .build();

  try (Response response = client.newCall(request).execute()) {
    return response.body().string();
  }
}




####7
maven仓库地址：下载一些mevan依赖地址:https://mvnrepository.com






####8
[时序图下载Visual Paradigm](https://www.visual-paradigm.com)
   通过时序图可方便的处理逻辑关系





####9
①JSON.parseObject(获取的JSON形式的数据 , GitHubUser.class)
   JSON.parseObject能将JSON格式的数据自动转化成java新方式的类对象(fastJson依赖项)
   ②JSON.toJSONString(accessTokenDTO)
      JSON.toJSONString(）将String转化为JSON类型的对象,accessTokenDTO是一个封装的实体类





####10
appolication.properties的作用（配置文件的分离）：
     将所有的参数名统一写到properties，当到线上部署时出现需要修改的东西直接到properties修改即可
     当需要使用该参数时，用@Value(${参数名})注解符注入，然后申明变量直接将该变量放置所引用参数位置即可；
     形如==>@Value("${Github.client.id}")
                    private String clientID;





####11
使用h2数据库直接在服务器端运行数据库文件(需要添加配置文件,可直接在mvnproperties里面找)

优点：H2是一个文本数据库，你可以引用到自己的项目中，或在电脑上直接使用。
          好处在于不需要像Oracle或Mysql那样要安装软件，而且是轻量的。H2在使用时，
         只要引入h2*.jar文件，并指明JDBC驱动(spring.datasource.driver-class-name)：org.h2.Driver，还有就是数据库的路径即可。
         H2在项目中使用时，你可以看作H2有一个服务平台，将你的项目web端和H2数据库连接起来。

   ######idea右边的Database里面直接找到h2数据库,注意直接将Database栏中的user和passeord给填写上







####12
使用mybatis整合数据库(需要先添加依赖，去http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html下载)
````
      <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.0</version>
        </dependency>
	(jdbc连接驱动：spring-boot-starter-jdbc)
    数据库连接：spring.datasource.url=jdbc:mysql://localhost/test
	spring.datasource.username=dbuser
	spring.datasource.password=dbpass
	spring.datasource.driver-class-name=com.mysql.jdbc.Driver
````
     
     

####13
使用flyway管理数据库（https://www.jianshu.com/p/5b3ee67e3598）
（https://flywaydb.org/getstarted/firststeps/maven）
          在真实的项目开发中，我们每个人都会有一个应用软件和与其相联系的数据库。
    对于个人开发来说，这样就够了。但是，项目开发一般都不止一个人，因此一定
    会出现我在我的本地有一套软件和相应的数据库系统，我的另一个同事会在他的
    本地有一套他自己的软件和相应的数据库系统。我们需要面临的第一个问题就是
    我们两个人如何集成我们的数据库系统，之后还要处理如何将数据库系统迁移到
    测试环境和生产环境当中去。

   当flyway出现Migration checksum mismatch for migration version 2......问题时，解决方案，可能是
    schema_version出现错误，先使用mvn flyway:repair将其修复，后mvn flyway:migrate
    (https://blog.csdn.net/stevejobson/article/details/54341122)







####14
lombok的使用：
（https://projectlombok.org/setup/maven）官方文档
使用lombok的作用：那么lombok到底是个什么呢，lombok是一个可以通过简单的注解的形式来帮助我们简化消除一些
必须有但显得很臃肿的 Java 代码的工具，简单来说，比如我们新建了一个类，然后在其中写了几个字段，然后通常情况下
我们需要手动去建立getter和setter方法啊，构造函数啊之类的，lombok的作用就是为了省去我们手动创建这些代码的
麻烦，它能够在我们编译源码的时候自动帮我们生成这些方法。

lombok能够达到的效果就是在源码中不需要写一些通用的方法，但是在编译生成的字节码文件中会帮
我们生成这些方法，这就是lombok的神奇作用。<idea下使用lombok需要先下载lombok plugin然后再导入依赖>

博客：https://www.cnblogs.com/holten/p/5729226.html

(@Data     
@Slf4j       //lombook自动将log注入,进行log日志的打印)






#####15.
mybatis不能识别驼峰标识，因此写的时候应当尽量避免写成驼峰标识(myBatis,hellWorld这类)
开启驼峰标识：（application.properties里面）
#####开启驼峰命名转换
mybatis.configuration.map-underscore-to-camel-case=true






####16
java的beanUtils.copyProperties()方法，能够快速处理两个类中许多相同的属性，将其中一个类里面的属性复制给
    另一个类里面，注：如果User和UserActionForm 间存在名称不相同的属性，则BeanUtils不对这些属性进行处理，
    需要手动处理。例如：User类里面有个createDate 创建时间字段，而UserActionForm里面无此字段。
    BeanUtils.copyProperties()不会对此字段做任何处理。必须要自己手动处理。
    ===>BeanUtils.copyProperties(user, uForm);====>将user类里面的与uFrom类里面相同的属性直接复制给uFrom





####17
oneTab插件，能够快速将之前浏览过的页面快速打开
    Octotree是一个能将github里面的内容分层显示的插件，能够快速找到想要的目录下面的内容




 
    
   热部署方式(快速重启项目)====>
####18
Spring Developer Tools/JRebel是一个热部署插件，最重要的功能就是自动应用代码更改到最新的App上面去，
     即在我们改变了一些代码或者配置文件的时候，应用可以自动重启，这在我们开发的时候，非常有用。

   使用Spring Developer Tools:使用idea开发应先添加依赖，然后重新启动项目，在Setting里面将compiler里面的
   Build project aotumatical打勾，然后他是让项目不再编译或者运行环境下启动，若想修改，则Ctril+Alt+Shift+?
   打开Regitry把compiler when Running勾选上即可（blog：https://segmentfault.com/a/1190000014488100）
    
   要想实现自动加载页面功能而不是手动刷新页面功能，则需加上liveReLoad插件（先点击保存，但变化有点慢）






####19
spring mvc框架官方文档
   https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-introduction





####20
将数据库中user_name转化成userName与实体类属性对应,如果数据库使用如user_name的命名方式，
    实体类采用驼峰命名。配置后无需写resultMapper将数据库字段和实体类属性对应
    mybatis.configuration.mapUnderscoreToCamelCase=true（驼峰标识是java语言规范化的标准）
    mybatis.configuration.map-underscore-to-camel-case=true



####21
在线json校验器：http://www.bejson.com
   啥叫json==>网络传输的一种数据结构
     






####22
数据库文件自动生成工具：mybatis generate（http://mybatis.org/generator/running/runningWithMaven.html）
(小匠视频34讲)
mapper有两种形式选择：注解方式和XML文件方式（xml方式写复杂mapper更加简单）
运用maven方式进行配置，若之前有写注解的方式，则XML会自动覆盖掉当前的同名文件  可之前就运用XML方式写
    产生XML文件的方式
The goal can be executed from the command line with the command:（产生新的XML）
    mvn mybatis-generator:generate

You can pass parameters to the goal with standard Maven command line properties. For example:（覆盖掉之前的同名文件）

    mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

This will run MBG and instruct it to overwrite any existing Java files it may find.

1.添加依赖    2.增加配置文件，mbg通过该配置文件自动产生（http://mybatis.org/generator/configreference/xmlconfig.html）(在resouces目录下添加generatorConfig.xml文件)   
3.熟悉每一个tag，并通过示例进行修改    4.使用命令生成文件
   怎么通过不是使用注解的方式@Mapper的方式找到该mapper，通过加入注解的方式在运行文件Application当中，
   @MapperScan("目标mapper")；






####23
localStorage:
    html web存储
   window.localStorage.setItem(key,value); //设置存储值
  | window是直接提供的一个用于展示的页面
  | window.close();              //将之前的页面关掉
localStorage 用于长久保存整个网站的数据，保存的数据没有过期时间，直到手动去删除。
localStorage 属性是只读的。





####24
要想进行前端页面的debug，在js文件中加上debugger；则运行到此处时自动会在该处加上断点





####25
org.apache.commons工具包
    apache.commons.lang中的工具包的StringUtils方法直接将判断null值和“==>StringUtils.isBlank(commentDTO.getContent())





####26
服务端和客户端应同时进行相应的判断，服务端避免数据的错误，客户端为了快速相应用js进行判断






####27
富文本的编辑，引入markdown
     markdown下载地址：https://pandao.github.io/editor.md/
     下载github上面的源码，按照操作将所要导入的js文件，css文件以及lib包导入，并按照操作实例将其引入，注意修改path路径
      （textarea的style"display=none;"）
     图片资源也应加载出来把图片资源image将其加入

参数设置：saveHTMLToTextarea   : true


 在显示的页面将markdown显示出来（github目录下的Markdown  to  html）
 将所要导入的js文件导入
```html
<link rel="stylesheet" href="editormd/css/editormd.preview.css" />
<div id="test-markdown-view">
    <!-- Server-side output Markdown text -->
    <textarea style="display:none;">### Hello world!</textarea>             
</div>
<script src="jquery.min.js"></script>
<script src="editormd/editormd.js"></script>
<script src="editormd/lib/marked.min.js"></script>
<script src="editormd/lib/prettify.min.js"></script>
```

```javascript 
<script  type="text/javascript">
    $(function(){
	    var testView = editormd.markdownToHTML("test-markdown-view", {
            // markdown : "[TOC]\n### Hello world!\n## Heading 2", // Also, you can dynamic set Markdown text
            // htmlDecode : true,  // Enable / disable HTML tag encode.
            // htmlDecode : "style,script,iframe",  // Note: If enabled, you should filter some dangerous HTML tags for website security.
        });
    });
</script>
```

设置图片上传==>按照示例将其加入(需将plugins文件加入js)，注意观看浏览器下面的网络窗口看所有的静态资源是否全部加载出来






####28
添加日志功能：便于调试(https://docs.spring.io/spring-boot/docs/2.1.7.RELEASE/reference/html/boot-features-logging.html)

 想要设置某一个文件debug，logging.level. 。。。到其想要的目录下
logging.level.root=info打印相关信息
logging.file.max-history=30（保留30天日志历史）
logging.file.max-size=200MB（每一个日志200MB）





####29
git 使用
   git pull用于将远程的目标代码拉过来与之进行合并
   .gitignore 用于忽略某个文件不将其提交到github仓库，文件夹则（路径/）
     






































##  Invalid signature file digest for Manifest main attributes 报错原因说明 

#### 1. idea里可以正常run，但是打包以后 执行报错：  

test-shade 模块 依赖  
```
        <dependency>
            <groupId>org.codehaus.janino</groupId>
            <artifactId>janino</artifactId>
            <version>3.0.12</version>
        </dependency>
```

并且打包的时候 注释掉下列代码：
```
<!--                            <filters>-->
<!--                                <filter>-->
<!--                                    <artifact>*:*</artifact>-->
<!--                                    <excludes>-->
<!--                                        <exclude>META-INF/*.SF</exclude>-->
<!--                                        <exclude>META-INF/*.DSA</exclude>-->
<!--                                        <exclude>META-INF/*.RSA</exclude>-->
<!--                                    </excludes>-->
<!--                                </filter>-->
<!--                            </filters>-->

```

本case 说明 打 shade 包的时候，需要排掉 SF DSA 等签名文件。因为 jvm加载 jar包的时候，会检测当前jar包 是否 跟 签名文件中的密钥对应上  

shade本身就是对jar包的修改，所以肯定会校验不通过  

所以需要在打出的shade包中 删除 签名文件 


#### 2. 打包过程中报错  Error creating shaded jar: Invalid signature file digest for Manifest main attributes
 
本 case 打包命令如下： 
```
mvn clean package -DskipTests -U -pl test-shade -am -e
```

其中 test-shade 模块 依赖  
```
    <dependency>
        <groupId>com.zhaohui</groupId>
        <artifactId>common</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
```

并且 test-shade 模块 保留下列配置： 

```
<filters>
    <filter>
        <artifact>*:*</artifact>
        <excludes>
            <exclude>META-INF/*.SF</exclude>
            <exclude>META-INF/*.DSA</exclude>
            <exclude>META-INF/*.RSA</exclude>
        </excludes>
    </filter>
</filters>
```

可以看到 即使test-shade 模块 排掉了 签名文件，打包的时候 还是报错了 

原因是 test-shade 模块 依赖了 common 模块，所以打包之前 会先打包 common 模块

然后 common 模块 如果配置了shade ，就会导致 common 包 签名不合法，导致common包不可用 

所以 打包 test-shade 模块 的时候 才会报这个错。

解决的方法是 在 common 模块 排除掉 签名文件

ps: 如果 jvm 或者 mvn 报错信息能再详细一点 就好了，比如  xxx.sf 校验失败 或者 xxx.jar 签名验证失败 之类的 都能方便排查问题

现在 只报这么简单的一个信息，很难排查问题。。。

以上！！
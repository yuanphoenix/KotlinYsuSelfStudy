### 燕习（YsuSelfStudy)

___

**一款帮助燕大同学的APP，由 @Ahyer 独自开发。**

软件采用Kotlin +Jetpack +MVVM 设计，这是重构后的版本。
<p align="center">
 <img src ="https://s1.ax1x.com/2020/05/06/YALsBR.png"/>
</p>


#### 主要功能

___

* 查询当日空教室，可自由选择时间段。
* 查询在校期间成绩
* 查询本学期的考试
* 查询本学期的课程表
* 查询教务处的通告



### 界面预览

____

[![YAI1dU.jpg](https://s1.ax1x.com/2020/05/06/YAI1dU.jpg)](https://imgchr.com/i/YAI1dU)
[![YAI3oF.jpg](https://s1.ax1x.com/2020/05/06/YAI3oF.jpg)](https://imgchr.com/i/YAI3oF)
[![YAIMLV.jpg](https://s1.ax1x.com/2020/05/06/YAIMLV.jpg)](https://imgchr.com/i/YAIMLV)
[![YAIKs0.jpg](https://s1.ax1x.com/2020/05/06/YAIKs0.jpg)](https://imgchr.com/i/YAIKs0)
[![YAIlZT.jpg](https://s1.ax1x.com/2020/05/06/YAIlZT.jpg)](https://imgchr.com/i/YAIlZT)



### 下载地址

____

​	软件目前已上架国内各大应用商店，但 [小米商店](http://app.mi.com/details?id=com.example.ysuselfstudy&ref=search) 更新最快。



### 编译配置

___

在build.gradle 中删除

```groovy
def keystoreProperFile = rootProject.file('keystore.properties')
def keystoreProperties = new Properties()
keystoreProperties.load(new FileInputStream(keystoreProperFile))

    signingConfigs {
        release {
            keyAlias keystoreProperties['KEY_ALIAS']
            keyPassword keystoreProperties['KEY_PASSWORD']
            storeFile file(keystoreProperties['STORE_FILE'])
            storePassword keystoreProperties['STORE_PASSWORD']
        }
    }
    
       debug {
            signingConfig signingConfigs.release
        }
```

后可以正常打包。但因为软件的签名并未开源。因此打包后无法登录QQ，无法获取空教室。



### 设计思路

___

APP采用Kotlin+Jetpack+MVVM模式设计。（部分采用Java）

软件主体部分采用的是爬虫---Jsoup。

后台部分原先用的是服务器，考虑到价格和安全的原因，改为了Bmob。



### LICENSE

______

**Mozilla**

2019年（第12届）中国大学生计算机设计大赛省赛三等奖

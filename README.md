

# LogcatView 
> 一款可以在手机中打开logcat控制台

- 方便快捷
- 支持内容搜索
- 支持自定义标题
- 支持根据tag筛选
- 支持根据log级别显示

## 如何引入

### Android Studio 引入

#### 第1步 将JitPack存储库添加到您的构建文件  
将其添加到存储库末尾的根build.gradle中：

       allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io' }
            }
        }
        
#### 第2步 添加依赖关系
    
        dependencies {
        	        implementation 'com.github.SHPDZY:LogCatDialog:v1.0.1'
        }
        	
        	
### Eclipse 引入
建议使用As，方便版本更新。

        dependencies {
                  compile project(path: ':logcatdialog')
        }
    

## 如何使用
        
        //显示dialog
        LogCatControl.getBuilder(this)
                .setTitle("自定义标题")
                .setSearchContent("自定义搜索内容")
                .setSearchTag("自定义Tag")
                .setShowGrade(3) //设置显示级别:0 所有，1 系统，2 警告,3 错误
                .show();
                
        //清除dialog
        LogCatControl.getBuilder(this).clear();

## 效果图

### 首页写了几个功能测试用例，您可根据需求组合搭配使用。

![首页](http://blog.9aiplay.com/zb_users/upload/2018/06/20180622135955152964719564867.png)

##

### 默认方式打开

![默认方式打开](http://blog.9aiplay.com/zb_users/upload/2018/06/20180622140009152964720960527.png)

##

### 自定义标题方式打开

![自定义标题方式打开](http://blog.9aiplay.com/zb_users/upload/2018/06/20180622135958152964719853179.png)

##

### 自定义搜索内容方式打开

![自定义搜索内容方式打开](http://blog.9aiplay.com/zb_users/upload/2018/06/20180622140004152964720426154.png)

##

### 自定义目标TAG方式打开

![自定义目标TAG方式打开](http://blog.9aiplay.com/zb_users/upload/2018/06/20180622140001152964720177860.png)

##
### 自定义LOG级别方式打开

![自定义LOG级别方式打开](http://blog.9aiplay.com/zb_users/upload/2018/06/20180622140007152964720766879.png)



## 作者信息：

* [zhangyong](http://9aiplay.com)


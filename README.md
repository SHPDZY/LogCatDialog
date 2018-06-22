

# LogcatView 
> 一款可以在手机中打开logcat控制台

- 方便快捷
- 支持内容搜索
- 支持自定义标题
- 支持根据tag筛选
- 支持根据log级别显示

## 使用

        LogCatControl.getInstance(this)
                .setTitle("自定义标题")
                .setSearchContent("自定义搜索内容")
                .setSearchTag("自定义Tag")
                .setShowGrade(3) //设置显示级别:0 所有，1 系统，2 警告,3 错误
                .show();

## 效果图

####首页

![首页](http://blog.9aiplay.com/zb_users/upload/2018/06/20180622134454152964629496115.png)

####默认方式打开

![默认方式打开](http://blog.9aiplay.com/zb_users/upload/2018/06/20180622134501152964630120858.png)

####自定义标题方式打开

![自定义标题方式打开](http://blog.9aiplay.com/zb_users/upload/2018/06/20180622134458152964629881244.png)

####自定义搜索内容方式打开

![自定义搜索内容方式打开](http://blog.9aiplay.com/zb_users/upload/2018/06/20180622134503152964630318296.png)

####自定义目标TAG方式打开

![自定义目标TAG方式打开](http://blog.9aiplay.com/zb_users/upload/2018/06/20180622134509152964630918228.png)

####自定义LOG级别方式打开

![自定义LOG级别方式打开](http://blog.9aiplay.com/zb_users/upload/2018/06/20180622134507152964630726508.png)



## 作者信息：

* [zhangyong](http://blog.9aiplay.com)


# jsp-music

音乐分享评论网站

## 项目介绍

JSP+Servlet+mysql5+tomcat7

采用bootstrap实现**响应式布局**

![响应式](https://s2.ax1x.com/2019/11/03/KjsnXR.png)

## 部分功能模块

* 数据库连接模块

    采用JDBC配置文件方式，与其他模块分离，降低耦合。

* 首页

    由于服务器图片丢失，又懒得重新上传，所以图片没有加载。正常情况下后台管理界面登录后，添加的歌曲、专辑、歌手信息都有图片。

    ![首页](https://s2.ax1x.com/2019/11/03/KjsS6s.png)

* 用户登录、注册模块

    截图

    ![登录](https://s2.ax1x.com/2019/11/03/KjsCmq.png)

* 音乐评论模块

* 播放音乐模块

    使用开源HTML5播放器插件[APlayer](https://github.com/MoePlayer/APlayer)。

    ![播放器](https://s2.ax1x.com/2019/11/03/Kjrzlj.png)

* 搜索音乐模块

    模糊匹配方式对歌曲进行搜索。

    ![搜索](https://s2.ax1x.com/2019/11/03/KjrxpQ.png)

* 后台管理模块

    可以登录管理员页面admin.jsp，对后台进行管理。用户管理、网站管理、账号管理。

    ![后台](https://s2.ax1x.com/2019/11/03/KjspXn.png)
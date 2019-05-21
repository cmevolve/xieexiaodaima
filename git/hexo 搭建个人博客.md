# hexo 搭建个人博客

tags ： git

---
## 什么是Hexo ?
Hexo是一款基于Node.js的静态博客框架，依赖少易于安装使用，可以方便的生成静态网页托管在GitHub和Heroku上，是搭建博客的首选框架。

## 准备工作
### 相关资料
[Github 官网][1]
[Github Pages][2]
[Hexo 官网][3]
[Node.js 官网][4]
[Git 官网][5]


### 相关教程
[使用 GitHub 和 Hexo 搭建免费静态 Blog][6]
[简书 搭建个人博客][7]
[主题配置][8]

### 常用命令
依照上面的连接把准备工作做完了之后来使用下面命令开始博客之旅。

```js
npm install hexo -g #安装Hexo
npm update hexo -g #升级 
hexo init #初始化博客
命令简写
hexo n "我的博客" == hexo new "我的博客" #新建文章
hexo g == hexo generate #生成
hexo s == hexo server #启动服务预览
hexo d == hexo deploy #部署
hexo server #Hexo会监视文件变动并自动更新，无须重启服务器
hexo server -s #静态模式
hexo server -p 5000 #更改端口
hexo server -i 192.168.1.1 #自定义 IP
hexo clean #清除缓存，若是网页正常情况下可以忽略这条命令
```



  [1]: https://github.com/
  [2]: https://pages.github.com/
  [3]: https://hexo.io/zh-cn/
  [4]: https://nodejs.org/en/
  [5]: https://git-scm.com/
  [6]: https://wsgzao.github.io/post/hexo-guide/
  [7]: https://www.jianshu.com/p/f4dce0e76886
  [8]: http://theme-next.iissnan.com/theme-settings.html
## 平台简介

一直想做一款HBase表管理系统，不仅是为了从命令行下创建HBase表的琐碎工作之中解放出来。同时，也是为了更好地管理与维护HBase的表信息。 出于这些目的，看了很多优秀的开源项目，但是一直没有发现合适自己的。于是利用空闲时间，基于若依后台管理框架，开始写一套HBase表管理系统。 如此便有了HBaseManager。目前，系统的功能还很初级，只有基本的namespace管理、HBase表的增删改查，以及HBase表的标签管理等功能。 当然，您也可以对他进行深度定制，以做出更强的系统。所有前端和后台的代码都是基于若依框架进行封装，十分的精简易上手，出错概率低。 同时支持移动客户端访问（若依框架的特性）。之后系统会陆续更新一些实用的功能。

* 感谢 [ruoyi](https://ruoyi.vip/) 后台管理系统。

## HBase表管理功能

1. namespace管理：包括namespace的创建、删除（后续将支持）等功能
2. HBase表管理：表创建（包括预分区建表等）、表信息登记维护、表删除、列簇管理等
3. 标签管理：HBase表的一些标签管理等
4. 数据预览：后续将支持数据预览等表数据管理的功能。
5. 多集群管理：后续将支持多集群管理。
6. 监控功能：后续考虑增加丰富的监控功能，以期待代替HBase本身的监控界面
7. 更多功能：......

## 若依系统本身功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。（后续将考虑整合团队统一的登录中心，ladp等等）
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10.  登录日志：系统登录日志记录查询包含登录异常。
11.  在线用户：当前系统中活跃用户状态监控。
12.  定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13.  代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14.  系统接口：根据业务代码自动生成相关的api接口文档。
15.  服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16.  在线构建器：拖动表单元素生成相应的HTML代码。
17.  连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

## 在线体验

## 演示图

### 1. namespace管理

![namespace](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-08-23-091544.jpg)

### 2. 表管理

新增表

![add table](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-08-23-094231.jpg)

![show1](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-08-23-%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_d5d45db3-3db7-4396-99cb-42817367134f.png)



![detail](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-08-23-%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_135c9aa0-c6d7-4764-93d7-e9b3ebee22bb.png)

表信息列表

![table-list](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-08-23-094559.jpg)

查看表详情

![table-detail](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-08-23-094734.jpg)

列簇信息，点击表名连接，跳转查看被选择表的列簇信息。

![family](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-08-23-094829.jpg)

目前列簇只支持修改，replication-scope

![update](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-08-23-094910.jpg)


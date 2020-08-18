-- ----------------------------
-- 20、HBase表name space
-- ----------------------------

drop table if exists sys_hbase_namespace;
create table sys_hbase_namespace
(
    namespace_id   bigint(20)  not null auto_increment comment '编号',
    namespace_name varchar(128) not null comment 'HBase所属namespace的名称',
    create_by      varchar(64) default '' comment '创建者',
    create_time    datetime comment '创建时间',
    update_by      varchar(64) default '' comment '更新者',
    update_time    datetime comment '更新时间',
    primary key (namespace_id)
) engine = innodb
  auto_increment = 1 comment = 'HBaseNamespace';


-- ----------------------------
-- 21、HBase表tag信息表
-- ----------------------------

drop table if exists sys_hbase_tag;
create table sys_hbase_tag
(
    tag_id      bigint(20)  not null auto_increment comment 'tag的编号',
    tag_name    varchar(128) not null comment 'HBase表Tag名称',
    create_by   varchar(64) default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64) default '' comment '更新者',
    update_time datetime comment '更新时间',
    primary key (tag_id)

) engine = innodb
  auto_increment = 1 comment = 'HBaseTag';


-- ----------------------------
-- 22、HBase表信息保存表
-- ----------------------------
drop table if exists sys_hbase_table;
create table sys_hbase_table
(
    table_id        bigint(20)   not null auto_increment comment 'HBase表编号',
    namespace_id    bigint(20)   not null comment 'HBase表namespace编号',
    table_name      varchar(200) not null comment 'HBase表名称',
    online_regions  int comment '在线region数',
    offline_regions int comment '下线region数',
    failed_regions  int comment '失败的region数',
    split_regions   int comment '在分裂的region数',
    other_regions   int comment '其他状态的region数',
    table_desc      varchar(5000) comment '表描述信息',
    del_flag        char(1)      default '0' comment '删除标志（0代表存在 2代表删除）',
    disable_flag    char(1)      default '0' comment '禁用标志（0代表启用表 2代表禁用表）',
    create_by       varchar(64)  default '' comment '创建者',
    create_time     datetime comment '创建时间',
    update_by       varchar(64)  default '' comment '更新者',
    update_time     datetime comment '更新时间',
    status          char(1)      default '0' comment '状态（0线上表 1测试表 2弃用表）',
    remark          varchar(255) default null comment '备注',
    primary key (table_id)

) engine = innodb
  auto_increment = 1 comment = 'HBase表';

-- ----------------------------
-- 23、HBase表信息与tag信息对应关系表
-- ----------------------------

drop table if exists sys_hbase_table_tag;
create table sys_hbase_table_tag
(
    table_id bigint(20) not null comment 'HBase 表编号',
    tag_id   bigint(20) not null comment 'HBase tag 编号',
    primary key (table_id, tag_id)
) engine = innodb
  auto_increment = 1 comment = 'HBase表所属Tag';


-- ----------------------------
-- 24、HBase表信息与用户信息对应关系表
-- ----------------------------

drop table if exists sys_hbase_user_table;
create table sys_hbase_user_table
(
    user_id  bigint(20) not null comment '用户ID',
    table_id bigint(20) not null comment 'HBase 表编号',
    primary key (user_id, table_id)
) engine = innodb
  auto_increment = 1 comment = 'HBase表所属用户';
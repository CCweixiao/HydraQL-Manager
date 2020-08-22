-- ----------------------------
-- namespace信息表
-- ----------------------------

drop table if exists sys_hbase_namespace;
create table sys_hbase_namespace
(
    namespace_id   bigint(20)   not null auto_increment comment '编号',
    namespace_name varchar(128) not null comment 'HBase所属namespace的名称',
    create_by      varchar(64) default '' comment '创建者',
    create_time    datetime comment '创建时间',
    update_by      varchar(64) default '' comment '更新者',
    update_time    datetime comment '更新时间',
    primary key (namespace_id)
) engine = innodb
  auto_increment = 1 comment = 'HBaseNamespace';


-- ----------------------------
-- tag信息表
-- ----------------------------

drop table if exists sys_hbase_tag;
create table sys_hbase_tag
(
    tag_id      bigint(20)   not null auto_increment comment 'tag的编号',
    tag_name    varchar(128) not null comment 'HBase表Tag名称',
    create_by   varchar(64) default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64) default '' comment '更新者',
    update_time datetime comment '更新时间',
    primary key (tag_id)

) engine = innodb
  auto_increment = 1 comment = 'HBaseTag';


-- ----------------------------
-- 列簇信息表
-- ----------------------------

drop table if exists sys_hbase_family;
create table sys_hbase_family
(
    family_id        bigint(20)   not null auto_increment comment 'family的编号',
    table_id         bigint(20)   not null comment 'HBase表编号',
    family_name      varchar(128) not null comment 'family名称',
    max_versions     int(11)      not null comment '最大版本号',
    ttl              int(11)      not null comment 'ttl',
    compression_type varchar(64) default '' comment '列簇数据的压缩类型',
    replication_scope char(1)      default '0' comment 'REPLICATION_SCOPE(0, 1)',
    create_by        varchar(64) default '' comment '创建者',
    create_time      datetime comment '创建时间',
    update_by        varchar(64) default '' comment '更新者',
    update_time      datetime comment '更新时间',
    primary key (family_id)

) engine = innodb
  auto_increment = 1 comment = 'HBase Family';


-- ----------------------------
-- HBase表信息保存表
-- ----------------------------
drop table if exists sys_hbase_table;
create table sys_hbase_table
(
    table_id          bigint(20)   not null auto_increment comment 'HBase表编号',
    namespace_id      bigint(20)   not null comment 'HBase表namespace编号',
    table_name        varchar(200) not null comment 'HBase表名称',
    disable_flag      char(1)      default '0' comment '禁用标志（0代表启用表 2代表禁用表）',
    create_by         varchar(64)  default '' comment '创建者',
    create_time       datetime comment '创建时间',
    update_by         varchar(64)  default '' comment '更新者',
    update_time       datetime comment '更新时间',
    status            char(1)      default '0' comment '状态（0线上表 1待上线表 2测试表 3弃用表）',
    remark            varchar(255) default null comment '备注',
    primary key (table_id)

) engine = innodb
  auto_increment = 1 comment = 'HBase表';

-- ----------------------------
-- HBase表与tag对应关系表
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
-- HBase表与用户信息对应关系表
-- ----------------------------

drop table if exists sys_hbase_user_table;
create table sys_hbase_user_table
(
    user_id  bigint(20) not null comment '用户ID',
    table_id bigint(20) not null comment 'HBase 表编号',
    primary key (user_id, table_id)
) engine = innodb
  auto_increment = 1 comment = 'HBase表所属用户';
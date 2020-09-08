/*
 Navicat Premium Data Transfer

 Source Server         : local-mysql
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : hbase-manager

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 08/09/2020 22:19:53
*/

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE "gen_table"
(
    "table_id"          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    "table_name"        varchar(200)  DEFAULT '' COMMENT '表名称',
    "table_comment"     varchar(500)  DEFAULT '' COMMENT '表描述',
    "sub_table_name"    varchar(64)   DEFAULT NULL COMMENT '关联子表的表名',
    "sub_table_fk_name" varchar(64)   DEFAULT NULL COMMENT '子表关联的外键名',
    "class_name"        varchar(100)  DEFAULT '' COMMENT '实体类名称',
    "tpl_category"      varchar(200)  DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作 sub主子表操作）',
    "package_name"      varchar(100)  DEFAULT NULL COMMENT '生成包路径',
    "module_name"       varchar(30)   DEFAULT NULL COMMENT '生成模块名',
    "business_name"     varchar(30)   DEFAULT NULL COMMENT '生成业务名',
    "function_name"     varchar(50)   DEFAULT NULL COMMENT '生成功能名',
    "function_author"   varchar(50)   DEFAULT NULL COMMENT '生成功能作者',
    "gen_type"          char(1)       DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
    "gen_path"          varchar(200)  DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
    "options"           varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
    "create_by"         varchar(64)   DEFAULT '' COMMENT '创建者',
    "create_time"       datetime      DEFAULT NULL COMMENT '创建时间',
    "update_by"         varchar(64)   DEFAULT '' COMMENT '更新者',
    "update_time"       datetime      DEFAULT NULL COMMENT '更新时间',
    "remark"            varchar(500)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY ("table_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 34
  DEFAULT CHARSET = utf8mb4 COMMENT ='代码生成业务表';

-- ----------------------------
-- Records of gen_table
-- ----------------------------
BEGIN;
INSERT INTO `gen_table`
VALUES (16, 'sys_hbase_namespace', 'HBaseNamespace', NULL, NULL, 'SysHbaseNamespace', 'crud',
        'com.leo.hbase.manager.system', 'system', 'namespace', 'HBaseNamespace', 'leojie', '0', '/', NULL, 'admin',
        '2020-08-16 07:07:05', '', NULL, NULL);
INSERT INTO `gen_table`
VALUES (17, 'sys_hbase_table', 'HBase表', NULL, NULL, 'SysHbaseTable', 'crud', 'com.leo.hbase.manager.system', 'system',
        'table', 'HBase', 'leojie', '0', '/', NULL, 'admin', '2020-08-16 07:07:05', '', NULL, NULL);
INSERT INTO `gen_table`
VALUES (18, 'sys_hbase_table_tag', 'HBase表所属Tag', NULL, NULL, 'SysHbaseTableTag', 'crud',
        'com.leo.hbase.manager.system', 'system', 'tag', 'HBase所属Tag', 'leojie', '0', '/', NULL, 'admin',
        '2020-08-16 07:07:05', '', NULL, NULL);
INSERT INTO `gen_table`
VALUES (19, 'sys_hbase_tag', 'HBaseTag', NULL, NULL, 'SysHbaseTag', 'crud', 'com.leo.hbase.manager.system', 'system',
        'tag', 'HBaseTag', 'leojie', '0', '/', NULL, 'admin', '2020-08-16 07:07:05', '', NULL, NULL);
INSERT INTO `gen_table`
VALUES (20, 'sys_hbase_user_table', 'HBase表所属用户', NULL, NULL, 'SysHbaseUserTable', 'crud',
        'com.leo.hbase.manager.system', 'system', 'table', 'HBase所属用户', 'leojie', '0', '/', NULL, 'admin',
        '2020-08-16 07:07:05', '', NULL, NULL);
INSERT INTO `gen_table`
VALUES (32, 'sys_hbase_family', 'HBase Family', NULL, NULL, 'SysHbaseFamily', 'crud', 'com.leo.hbase.manager.system',
        'system', 'family', 'HBase Family', 'leojie', '0', '/', NULL, 'admin', '2020-08-22 13:23:24', '', NULL, NULL);
INSERT INTO `gen_table`
VALUES (33, 'sys_hbase_table_data', 'HBase表', NULL, NULL, 'SysHbaseTableData', 'crud', 'com.leo.hbase.manager.system',
        'system', 'data', 'HBase', 'leojie', '0', '/', NULL, 'admin', '2020-09-07 12:19:28', '', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE "gen_table_column"
(
    "column_id"      bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    "table_id"       varchar(64)  DEFAULT NULL COMMENT '归属表编号',
    "column_name"    varchar(200) DEFAULT NULL COMMENT '列名称',
    "column_comment" varchar(500) DEFAULT NULL COMMENT '列描述',
    "column_type"    varchar(100) DEFAULT NULL COMMENT '列类型',
    "java_type"      varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
    "java_field"     varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
    "is_pk"          char(1)      DEFAULT NULL COMMENT '是否主键（1是）',
    "is_increment"   char(1)      DEFAULT NULL COMMENT '是否自增（1是）',
    "is_required"    char(1)      DEFAULT NULL COMMENT '是否必填（1是）',
    "is_insert"      char(1)      DEFAULT NULL COMMENT '是否为插入字段（1是）',
    "is_edit"        char(1)      DEFAULT NULL COMMENT '是否编辑字段（1是）',
    "is_list"        char(1)      DEFAULT NULL COMMENT '是否列表字段（1是）',
    "is_query"       char(1)      DEFAULT NULL COMMENT '是否查询字段（1是）',
    "query_type"     varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
    "html_type"      varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
    "dict_type"      varchar(200) DEFAULT '' COMMENT '字典类型',
    "sort"           int(11)      DEFAULT NULL COMMENT '排序',
    "create_by"      varchar(64)  DEFAULT '' COMMENT '创建者',
    "create_time"    datetime     DEFAULT NULL COMMENT '创建时间',
    "update_by"      varchar(64)  DEFAULT '' COMMENT '更新者',
    "update_time"    datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY ("column_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 280
  DEFAULT CHARSET = utf8mb4 COMMENT ='代码生成业务表字段';

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
BEGIN;
INSERT INTO `gen_table_column`
VALUES (129, '16', 'namespace_id', '编号', 'bigint(20)', 'Long', 'namespaceId', '1', '1', NULL, '1', NULL, NULL, NULL,
        'EQ', 'input', '', 1, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (130, '16', 'namespace_name', 'HBase所属namespace的名称', 'varchar(50)', 'String', 'namespaceName', '0', '0', '1',
        '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (131, '16', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ',
        'input', '', 3, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (132, '16', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ',
        'datetime', '', 4, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (133, '16', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ',
        'input', '', 5, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (134, '16', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ',
        'datetime', '', 6, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (135, '17', 'table_id', 'HBase表编号', 'bigint(20)', 'Long', 'tableId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ',
        'input', '', 1, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (136, '17', 'namespace_id', 'HBase表namespace编号', 'bigint(20)', 'Long', 'namespaceId', '0', '0', '1', '1', '1',
        '1', '1', 'EQ', 'input', '', 2, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (137, '17', 'table_name', 'HBase表名称', 'varchar(200)', 'String', 'tableName', '0', '0', '1', '1', '1', '1', '1',
        'LIKE', 'input', '', 3, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (138, '17', 'online_regions', '在线region数', 'int(11)', 'Long', 'onlineRegions', '0', '0', NULL, '1', '1', '1',
        '1', 'EQ', 'input', '', 4, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (139, '17', 'offline_regions', '下线region数', 'int(11)', 'Long', 'offlineRegions', '0', '0', NULL, '1', '1', '1',
        '1', 'EQ', 'input', '', 5, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (140, '17', 'failed_regions', '失败的region数', 'int(11)', 'Long', 'failedRegions', '0', '0', NULL, '1', '1', '1',
        '1', 'EQ', 'input', '', 6, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (141, '17', 'split_regions', '在分裂的region数', 'int(11)', 'Long', 'splitRegions', '0', '0', NULL, '1', '1', '1',
        '1', 'EQ', 'input', '', 7, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (142, '17', 'other_regions', '其他状态的region数', 'int(11)', 'Long', 'otherRegions', '0', '0', NULL, '1', '1', '1',
        '1', 'EQ', 'input', '', 8, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (143, '17', 'table_desc', '表描述信息', 'varchar(5000)', 'String', 'tableDesc', '0', '0', NULL, '1', '1', '1', '1',
        'EQ', 'textarea', '', 9, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (144, '17', 'del_flag', '删除标志（0代表存在 2代表删除）', 'char(1)', 'String', 'delFlag', '0', '0', NULL, '1', NULL, NULL,
        NULL, 'EQ', 'input', '', 10, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (145, '17', 'disable_flag', '禁用标志（0代表启用表 2代表禁用表）', 'char(1)', 'String', 'disableFlag', '0', '0', NULL, '1', '1',
        '1', '1', 'EQ', 'input', '', 11, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (146, '17', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ',
        'input', '', 12, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (147, '17', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ',
        'datetime', '', 13, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (148, '17', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ',
        'input', '', 14, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (149, '17', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ',
        'datetime', '', 15, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (150, '17', 'status', '状态（0线上表 1测试表 2启用表）', 'char(1)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1',
        'EQ', 'radio', '', 16, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (151, '17', 'remark', '备注', 'varchar(255)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ',
        'input', '', 17, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (152, '18', 'table_id', 'HBase 表编号', 'bigint(20)', 'Long', 'tableId', '1', '0', NULL, '1', NULL, NULL, NULL,
        'EQ', 'input', '', 1, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (153, '18', 'tag_id', 'HBase tag 编号', 'bigint(20)', 'Long', 'tagId', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ',
        'input', '', 2, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (154, '19', 'tag_id', 'tag的编号', 'bigint(20)', 'Long', 'tagId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ',
        'input', '', 1, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (155, '19', 'tag_name', 'HBase表Tag名称', 'varchar(50)', 'String', 'tagName', '0', '0', '1', '1', '1', '1', '1',
        'LIKE', 'input', '', 2, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (156, '19', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ',
        'input', '', 3, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (157, '19', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ',
        'datetime', '', 4, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (158, '19', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ',
        'input', '', 5, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (159, '19', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ',
        'datetime', '', 6, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (160, '20', 'user_id', '用户ID', 'bigint(20)', 'Long', 'userId', '1', '0', NULL, '1', NULL, NULL, NULL, 'EQ',
        'input', '', 1, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (161, '20', 'table_id', 'HBase 表编号', 'bigint(20)', 'Long', 'tableId', '1', '0', NULL, '1', NULL, NULL, NULL,
        'EQ', 'input', '', 2, 'admin', '2020-08-16 07:07:05', '', NULL);
INSERT INTO `gen_table_column`
VALUES (266, '32', 'family_id', 'family的编号', 'bigint(20)', 'Long', 'familyId', '1', '1', NULL, '1', NULL, NULL, NULL,
        'EQ', 'input', '', 1, 'admin', '2020-08-22 13:23:24', '', NULL);
INSERT INTO `gen_table_column`
VALUES (267, '32', 'table_id', 'HBase表编号', 'bigint(20)', 'Long', 'tableId', '0', '0', '1', '1', '1', '1', '1', 'EQ',
        'input', '', 2, 'admin', '2020-08-22 13:23:24', '', NULL);
INSERT INTO `gen_table_column`
VALUES (268, '32', 'family_name', 'family名称', 'varchar(128)', 'String', 'familyName', '0', '0', '1', '1', '1', '1', '1',
        'LIKE', 'input', '', 3, 'admin', '2020-08-22 13:23:24', '', NULL);
INSERT INTO `gen_table_column`
VALUES (269, '32', 'max_versions', '最大版本号', 'int(11)', 'Long', 'maxVersions', '0', '0', '1', '1', '1', '1', '1', 'EQ',
        'input', '', 4, 'admin', '2020-08-22 13:23:24', '', NULL);
INSERT INTO `gen_table_column`
VALUES (270, '32', 'ttl', 'ttl', 'int(11)', 'Long', 'ttl', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 5,
        'admin', '2020-08-22 13:23:24', '', NULL);
INSERT INTO `gen_table_column`
VALUES (271, '32', 'compression_type', '列簇数据的压缩类型', 'varchar(64)', 'String', 'compressionType', '0', '0', NULL, '1',
        '1', '1', '1', 'EQ', 'select', '', 6, 'admin', '2020-08-22 13:23:24', '', NULL);
INSERT INTO `gen_table_column`
VALUES (272, '32', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ',
        'input', '', 7, 'admin', '2020-08-22 13:23:24', '', NULL);
INSERT INTO `gen_table_column`
VALUES (273, '32', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ',
        'datetime', '', 8, 'admin', '2020-08-22 13:23:24', '', NULL);
INSERT INTO `gen_table_column`
VALUES (274, '32', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ',
        'input', '', 9, 'admin', '2020-08-22 13:23:24', '', NULL);
INSERT INTO `gen_table_column`
VALUES (275, '32', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ',
        'datetime', '', 10, 'admin', '2020-08-22 13:23:24', '', NULL);
INSERT INTO `gen_table_column`
VALUES (276, '33', 'row_key', 'row key', 'varchar(200)', 'String', 'rowKey', '0', '0', '1', '1', '1', '1', '1', 'EQ',
        'input', '', 1, 'admin', '2020-09-07 12:19:28', '', NULL);
INSERT INTO `gen_table_column`
VALUES (277, '33', 'family_name', '列簇名称', 'varchar(200)', 'String', 'familyName', '0', '0', '1', '1', '1', '1', '1',
        'LIKE', 'input', '', 2, 'admin', '2020-09-07 12:19:28', '', NULL);
INSERT INTO `gen_table_column`
VALUES (278, '33', 'timestamp', '时间戳', 'varchar(20)', 'String', 'timestamp', '0', '0', NULL, '1', '1', '1', '1', 'EQ',
        'input', '', 3, 'admin', '2020-09-07 12:19:28', '', NULL);
INSERT INTO `gen_table_column`
VALUES (279, '33', 'value', '数据值', 'varchar(200)', 'String', 'value', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input',
        '', 4, 'admin', '2020-09-07 12:19:28', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE "qrtz_blob_triggers"
(
    "sched_name"    varchar(120) NOT NULL,
    "trigger_name"  varchar(200) NOT NULL,
    "trigger_group" varchar(200) NOT NULL,
    "blob_data"     blob,
    PRIMARY KEY ("sched_name", "trigger_name", "trigger_group"),
    CONSTRAINT "qrtz_blob_triggers_ibfk_1" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "qrtz_triggers" ("sched_name", "trigger_name", "trigger_group")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE "qrtz_calendars"
(
    "sched_name"    varchar(120) NOT NULL,
    "calendar_name" varchar(200) NOT NULL,
    "calendar"      blob         NOT NULL,
    PRIMARY KEY ("sched_name", "calendar_name")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE "qrtz_cron_triggers"
(
    "sched_name"      varchar(120) NOT NULL,
    "trigger_name"    varchar(200) NOT NULL,
    "trigger_group"   varchar(200) NOT NULL,
    "cron_expression" varchar(200) NOT NULL,
    "time_zone_id"    varchar(80) DEFAULT NULL,
    PRIMARY KEY ("sched_name", "trigger_name", "trigger_group"),
    CONSTRAINT "qrtz_cron_triggers_ibfk_1" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "qrtz_triggers" ("sched_name", "trigger_name", "trigger_group")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_cron_triggers`
VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', '0/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers`
VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', '0/15 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers`
VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', '0/20 * * * * ?', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE "qrtz_fired_triggers"
(
    "sched_name"        varchar(120) NOT NULL,
    "entry_id"          varchar(95)  NOT NULL,
    "trigger_name"      varchar(200) NOT NULL,
    "trigger_group"     varchar(200) NOT NULL,
    "instance_name"     varchar(200) NOT NULL,
    "fired_time"        bigint(13)   NOT NULL,
    "sched_time"        bigint(13)   NOT NULL,
    "priority"          int(11)      NOT NULL,
    "state"             varchar(16)  NOT NULL,
    "job_name"          varchar(200) DEFAULT NULL,
    "job_group"         varchar(200) DEFAULT NULL,
    "is_nonconcurrent"  varchar(1)   DEFAULT NULL,
    "requests_recovery" varchar(1)   DEFAULT NULL,
    PRIMARY KEY ("sched_name", "entry_id")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE "qrtz_job_details"
(
    "sched_name"        varchar(120) NOT NULL,
    "job_name"          varchar(200) NOT NULL,
    "job_group"         varchar(200) NOT NULL,
    "description"       varchar(250) DEFAULT NULL,
    "job_class_name"    varchar(250) NOT NULL,
    "is_durable"        varchar(1)   NOT NULL,
    "is_nonconcurrent"  varchar(1)   NOT NULL,
    "is_update_data"    varchar(1)   NOT NULL,
    "requests_recovery" varchar(1)   NOT NULL,
    "job_data"          blob,
    PRIMARY KEY ("sched_name", "job_name", "job_group")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_job_details`
VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', NULL,
        'com.leo.hbase.manager.quartz.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0',
        0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372002A636F6D2E6C656F2E68626173652E6D616E616765722E71756172747A2E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720033636F6D2E6C656F2E68626173652E6D616E616765722E636F6D6D6F6E2E636F72652E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001622CDE29E078707400007070707400013174000E302F3130202A202A202A202A203F74001172795461736B2E72794E6F506172616D7374000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000001740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E697A0E58F82EFBC8974000133740001317800);
INSERT INTO `qrtz_job_details`
VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', NULL,
        'com.leo.hbase.manager.quartz.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0',
        0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372002A636F6D2E6C656F2E68626173652E6D616E616765722E71756172747A2E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720033636F6D2E6C656F2E68626173652E6D616E616765722E636F6D6D6F6E2E636F72652E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001622CDE29E078707400007070707400013174000E302F3135202A202A202A202A203F74001572795461736B2E7279506172616D7328277279272974000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000002740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E69C89E58F82EFBC8974000133740001317800);
INSERT INTO `qrtz_job_details`
VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', NULL,
        'com.leo.hbase.manager.quartz.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0',
        0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372002A636F6D2E6C656F2E68626173652E6D616E616765722E71756172747A2E646F6D61696E2E5379734A6F6200000000000000010200084C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000673746174757371007E000978720033636F6D2E6C656F2E68626173652E6D616E616765722E636F6D6D6F6E2E636F72652E646F6D61696E2E42617365456E7469747900000000000000010200074C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C0006706172616D7371007E00034C000672656D61726B71007E00094C000B73656172636856616C756571007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000C787074000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001622CDE29E078707400007070707400013174000E302F3230202A202A202A202A203F74003872795461736B2E72794D756C7469706C65506172616D7328277279272C20747275652C20323030304C2C203331362E3530442C203130302974000744454641554C547372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000000003740018E7B3BBE7BB9FE9BB98E8AEA4EFBC88E5A49AE58F82EFBC8974000133740001317800);
COMMIT;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE "qrtz_locks"
(
    "sched_name" varchar(120) NOT NULL,
    "lock_name"  varchar(40)  NOT NULL,
    PRIMARY KEY ("sched_name", "lock_name")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_locks`
VALUES ('RuoyiScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks`
VALUES ('RuoyiScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE "qrtz_paused_trigger_grps"
(
    "sched_name"    varchar(120) NOT NULL,
    "trigger_group" varchar(200) NOT NULL,
    PRIMARY KEY ("sched_name", "trigger_group")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE "qrtz_scheduler_state"
(
    "sched_name"        varchar(120) NOT NULL,
    "instance_name"     varchar(200) NOT NULL,
    "last_checkin_time" bigint(13)   NOT NULL,
    "checkin_interval"  bigint(13)   NOT NULL,
    PRIMARY KEY ("sched_name", "instance_name")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_scheduler_state`
VALUES ('RuoyiScheduler', 'macdeMacBook-Air-2.local1599574664895', 1599574744018, 15000);
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE "qrtz_simple_triggers"
(
    "sched_name"      varchar(120) NOT NULL,
    "trigger_name"    varchar(200) NOT NULL,
    "trigger_group"   varchar(200) NOT NULL,
    "repeat_count"    bigint(7)    NOT NULL,
    "repeat_interval" bigint(12)   NOT NULL,
    "times_triggered" bigint(10)   NOT NULL,
    PRIMARY KEY ("sched_name", "trigger_name", "trigger_group"),
    CONSTRAINT "qrtz_simple_triggers_ibfk_1" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "qrtz_triggers" ("sched_name", "trigger_name", "trigger_group")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE "qrtz_simprop_triggers"
(
    "sched_name"    varchar(120) NOT NULL,
    "trigger_name"  varchar(200) NOT NULL,
    "trigger_group" varchar(200) NOT NULL,
    "str_prop_1"    varchar(512)   DEFAULT NULL,
    "str_prop_2"    varchar(512)   DEFAULT NULL,
    "str_prop_3"    varchar(512)   DEFAULT NULL,
    "int_prop_1"    int(11)        DEFAULT NULL,
    "int_prop_2"    int(11)        DEFAULT NULL,
    "long_prop_1"   bigint(20)     DEFAULT NULL,
    "long_prop_2"   bigint(20)     DEFAULT NULL,
    "dec_prop_1"    decimal(13, 4) DEFAULT NULL,
    "dec_prop_2"    decimal(13, 4) DEFAULT NULL,
    "bool_prop_1"   varchar(1)     DEFAULT NULL,
    "bool_prop_2"   varchar(1)     DEFAULT NULL,
    PRIMARY KEY ("sched_name", "trigger_name", "trigger_group"),
    CONSTRAINT "qrtz_simprop_triggers_ibfk_1" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "qrtz_triggers" ("sched_name", "trigger_name", "trigger_group")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE "qrtz_triggers"
(
    "sched_name"     varchar(120) NOT NULL,
    "trigger_name"   varchar(200) NOT NULL,
    "trigger_group"  varchar(200) NOT NULL,
    "job_name"       varchar(200) NOT NULL,
    "job_group"      varchar(200) NOT NULL,
    "description"    varchar(250) DEFAULT NULL,
    "next_fire_time" bigint(13)   DEFAULT NULL,
    "prev_fire_time" bigint(13)   DEFAULT NULL,
    "priority"       int(11)      DEFAULT NULL,
    "trigger_state"  varchar(16)  NOT NULL,
    "trigger_type"   varchar(8)   NOT NULL,
    "start_time"     bigint(13)   NOT NULL,
    "end_time"       bigint(13)   DEFAULT NULL,
    "calendar_name"  varchar(200) DEFAULT NULL,
    "misfire_instr"  smallint(2)  DEFAULT NULL,
    "job_data"       blob,
    PRIMARY KEY ("sched_name", "trigger_name", "trigger_group"),
    KEY "sched_name" ("sched_name", "job_name", "job_group"),
    CONSTRAINT "qrtz_triggers_ibfk_1" FOREIGN KEY ("sched_name", "job_name", "job_group") REFERENCES "qrtz_job_details" ("sched_name", "job_name", "job_group")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_triggers`
VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME1', 'DEFAULT', 'TASK_CLASS_NAME1', 'DEFAULT', NULL, 1599574670000, -1, 5,
        'PAUSED', 'CRON', 1599574665000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers`
VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME2', 'DEFAULT', 'TASK_CLASS_NAME2', 'DEFAULT', NULL, 1599574665000, -1, 5,
        'PAUSED', 'CRON', 1599574665000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers`
VALUES ('RuoyiScheduler', 'TASK_CLASS_NAME3', 'DEFAULT', 'TASK_CLASS_NAME3', 'DEFAULT', NULL, 1599574680000, -1, 5,
        'PAUSED', 'CRON', 1599574665000, 0, NULL, 2, '');
COMMIT;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE "sys_config"
(
    "config_id"    int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
    "config_name"  varchar(100) DEFAULT '' COMMENT '参数名称',
    "config_key"   varchar(100) DEFAULT '' COMMENT '参数键名',
    "config_value" varchar(500) DEFAULT '' COMMENT '参数键值',
    "config_type"  char(1)      DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
    "create_by"    varchar(64)  DEFAULT '' COMMENT '创建者',
    "create_time"  datetime     DEFAULT NULL COMMENT '创建时间',
    "update_by"    varchar(64)  DEFAULT '' COMMENT '更新者',
    "update_time"  datetime     DEFAULT NULL COMMENT '更新时间',
    "remark"       varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY ("config_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4 COMMENT ='参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config`
VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config`
VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '初始化密码 123456');
INSERT INTO `sys_config`
VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '深黑主题theme-dark，浅色主题theme-light，深蓝主题theme-blue');
INSERT INTO `sys_config`
VALUES (4, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'true', 'Y', 'admin', '2018-03-16 11:33:00', 'admin',
        '2020-08-21 16:08:32', '是否开启注册用户功能');
INSERT INTO `sys_config`
VALUES (5, '用户管理-密码字符范围', 'sys.account.chrtype', '0', 'Y', 'admin', '2018-03-16 11:33:00', 'admin',
        '2020-09-07 10:03:28',
        '默认任意字符范围，0任意（密码可以输入任意字符），1数字（密码只能为0-9数字），2英文字母（密码只能为a-z和A-Z字母），3字母和数字（密码必须包含字母，数字）,4字母数组和特殊字符（密码必须包含字母，数字，特殊字符-_）');
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE "sys_dept"
(
    "dept_id"     bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
    "parent_id"   bigint(20)  DEFAULT '0' COMMENT '父部门id',
    "ancestors"   varchar(50) DEFAULT '' COMMENT '祖级列表',
    "dept_name"   varchar(30) DEFAULT '' COMMENT '部门名称',
    "order_num"   int(4)      DEFAULT '0' COMMENT '显示顺序',
    "leader"      varchar(20) DEFAULT NULL COMMENT '负责人',
    "phone"       varchar(11) DEFAULT NULL COMMENT '联系电话',
    "email"       varchar(50) DEFAULT NULL COMMENT '邮箱',
    "status"      char(1)     DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
    "del_flag"    char(1)     DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    "create_by"   varchar(64) DEFAULT '' COMMENT '创建者',
    "create_time" datetime    DEFAULT NULL COMMENT '创建时间',
    "update_by"   varchar(64) DEFAULT '' COMMENT '更新者',
    "update_time" datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY ("dept_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 110
  DEFAULT CHARSET = utf8mb4 COMMENT ='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept`
VALUES (100, 0, '0', 'hbase-manager', 0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00',
        'admin', '2020-09-07 10:02:38');
INSERT INTO `sys_dept`
VALUES (101, 100, '0,100', '大数据事业部', 1, 'admin', '15888888888', 'big_data@qq.com', '0', '0', 'admin',
        '2018-03-16 11:33:00', 'admin', '2020-09-07 10:02:38');
INSERT INTO `sys_dept`
VALUES (102, 100, '0,100', '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00');
INSERT INTO `sys_dept`
VALUES (103, 101, '0,100,101', '研发部门', 1, 'leojie', '15888888888', 'leojie@qq.com', '0', '0', 'admin',
        '2018-03-16 11:33:00', 'admin', '2020-09-07 10:02:38');
INSERT INTO `sys_dept`
VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept`
VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept`
VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept`
VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept`
VALUES (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00');
INSERT INTO `sys_dept`
VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE "sys_dict_data"
(
    "dict_code"   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    "dict_sort"   int(4)       DEFAULT '0' COMMENT '字典排序',
    "dict_label"  varchar(100) DEFAULT '' COMMENT '字典标签',
    "dict_value"  varchar(100) DEFAULT '' COMMENT '字典键值',
    "dict_type"   varchar(100) DEFAULT '' COMMENT '字典类型',
    "css_class"   varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
    "list_class"  varchar(100) DEFAULT NULL COMMENT '表格回显样式',
    "is_default"  char(1)      DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
    "status"      char(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    "create_by"   varchar(64)  DEFAULT '' COMMENT '创建者',
    "create_time" datetime     DEFAULT NULL COMMENT '创建时间',
    "update_by"   varchar(64)  DEFAULT '' COMMENT '更新者',
    "update_time" datetime     DEFAULT NULL COMMENT '更新时间',
    "remark"      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY ("dict_code")
) ENGINE = InnoDB
  AUTO_INCREMENT = 114
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_data`
VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00',
        '性别男');
INSERT INTO `sys_dict_data`
VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00',
        '性别女');
INSERT INTO `sys_dict_data`
VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00',
        '性别未知');
INSERT INTO `sys_dict_data`
VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '显示菜单');
INSERT INTO `sys_dict_data`
VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '隐藏菜单');
INSERT INTO `sys_dict_data`
VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data`
VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data`
VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data`
VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data`
VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '默认分组');
INSERT INTO `sys_dict_data`
VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '系统分组');
INSERT INTO `sys_dict_data`
VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '系统默认是');
INSERT INTO `sys_dict_data`
VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '系统默认否');
INSERT INTO `sys_dict_data`
VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '通知');
INSERT INTO `sys_dict_data`
VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '公告');
INSERT INTO `sys_dict_data`
VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data`
VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '关闭状态');
INSERT INTO `sys_dict_data`
VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '其他操作');
INSERT INTO `sys_dict_data`
VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '新增操作');
INSERT INTO `sys_dict_data`
VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '修改操作');
INSERT INTO `sys_dict_data`
VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '删除操作');
INSERT INTO `sys_dict_data`
VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '授权操作');
INSERT INTO `sys_dict_data`
VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '导出操作');
INSERT INTO `sys_dict_data`
VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '导入操作');
INSERT INTO `sys_dict_data`
VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '强退操作');
INSERT INTO `sys_dict_data`
VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '生成操作');
INSERT INTO `sys_dict_data`
VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '清空操作');
INSERT INTO `sys_dict_data`
VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data`
VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data`
VALUES (100, 1, '线上表', '0', 'sys_hbase_table_status', '', 'success', 'Y', '0', 'admin', '2020-08-16 08:12:36', 'admin',
        '2020-08-16 10:52:51', '线上表');
INSERT INTO `sys_dict_data`
VALUES (101, 2, '待上线表', '1', 'sys_hbase_table_status', '', 'info', 'N', '0', 'admin', '2020-08-16 08:13:27', 'admin',
        '2020-08-18 12:43:30', '待上线表');
INSERT INTO `sys_dict_data`
VALUES (102, 4, '弃用表', '3', 'sys_hbase_table_status', '', 'danger', 'N', '0', 'admin', '2020-08-16 08:14:04', 'admin',
        '2020-08-18 12:44:43', '弃用表');
INSERT INTO `sys_dict_data`
VALUES (103, 1, 'ENABLED', '0', 'sys_hbase_table_disable', '', 'primary', 'Y', '0', 'admin', '2020-08-16 11:00:15',
        'admin', '2020-08-18 12:41:25', 'HBase 表enabled');
INSERT INTO `sys_dict_data`
VALUES (104, 2, 'DISABLED', '2', 'sys_hbase_table_disable', '', 'danger', 'N', '0', 'admin', '2020-08-16 11:01:06',
        'admin', '2020-08-18 12:41:39', 'HBase禁用表');
INSERT INTO `sys_dict_data`
VALUES (105, 3, '测试表', '2', 'sys_hbase_table_status', '', 'warning', 'N', '0', 'admin', '2020-08-18 12:43:20', 'admin',
        '2020-08-18 12:44:39', '测试表');
INSERT INTO `sys_dict_data`
VALUES (107, 1, '0', '0', 'sys_hbase_family_replication_scope', '', 'warning', 'Y', '0', 'admin', '2020-08-23 05:59:35',
        'admin', '2020-08-23 06:00:35', '');
INSERT INTO `sys_dict_data`
VALUES (108, 2, '1', '1', 'sys_hbase_family_replication_scope', '', 'primary', 'N', '0', 'admin', '2020-08-23 06:00:55',
        'admin', '2020-08-23 06:01:02', '');
INSERT INTO `sys_dict_data`
VALUES (109, 1, 'NONE', 'NONE', 'sys_hbase_family_compression_type', NULL, 'info', 'Y', '0', 'admin',
        '2020-08-23 06:02:31', '', NULL, 'NONE');
INSERT INTO `sys_dict_data`
VALUES (110, 2, 'SNAPPY', 'SNAPPY', 'sys_hbase_family_compression_type', NULL, 'primary', 'N', '0', 'admin',
        '2020-08-23 06:03:00', '', NULL, 'SNAPPY');
INSERT INTO `sys_dict_data`
VALUES (111, 3, 'GZIP', 'GZIP', 'sys_hbase_family_compression_type', NULL, 'warning', 'N', '0', 'admin',
        '2020-08-23 06:03:37', '', NULL, 'GZIP');
INSERT INTO `sys_dict_data`
VALUES (112, 4, 'LZO', 'LZO', 'sys_hbase_family_compression_type', '', 'danger', 'N', '0', 'admin',
        '2020-08-23 06:03:59', 'admin', '2020-08-23 06:04:11', 'LZO');
INSERT INTO `sys_dict_data`
VALUES (113, 5, 'LZ4', 'LZ4', 'sys_hbase_family_compression_type', NULL, 'success', 'N', '0', 'admin',
        '2020-08-23 06:04:33', '', NULL, 'LZ4');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE "sys_dict_type"
(
    "dict_id"     bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    "dict_name"   varchar(100) DEFAULT '' COMMENT '字典名称',
    "dict_type"   varchar(100) DEFAULT '' COMMENT '字典类型',
    "status"      char(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    "create_by"   varchar(64)  DEFAULT '' COMMENT '创建者',
    "create_time" datetime     DEFAULT NULL COMMENT '创建时间',
    "update_by"   varchar(64)  DEFAULT '' COMMENT '更新者',
    "update_time" datetime     DEFAULT NULL COMMENT '更新时间',
    "remark"      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY ("dict_id"),
    UNIQUE KEY "dict_type" ("dict_type")
) ENGINE = InnoDB
  AUTO_INCREMENT = 104
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典类型表';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type`
VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '用户性别列表');
INSERT INTO `sys_dict_type`
VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '菜单状态列表');
INSERT INTO `sys_dict_type`
VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统开关列表');
INSERT INTO `sys_dict_type`
VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '任务状态列表');
INSERT INTO `sys_dict_type`
VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '任务分组列表');
INSERT INTO `sys_dict_type`
VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统是否列表');
INSERT INTO `sys_dict_type`
VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知类型列表');
INSERT INTO `sys_dict_type`
VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知状态列表');
INSERT INTO `sys_dict_type`
VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '操作类型列表');
INSERT INTO `sys_dict_type`
VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '登录状态列表');
INSERT INTO `sys_dict_type`
VALUES (100, 'HBase表状态', 'sys_hbase_table_status', '0', 'admin', '2020-08-16 08:10:17', '', NULL, 'HBase表状态');
INSERT INTO `sys_dict_type`
VALUES (101, 'HBase表禁用标志', 'sys_hbase_table_disable', '0', 'admin', '2020-08-16 10:58:45', '', NULL, 'HBase表禁用标志');
INSERT INTO `sys_dict_type`
VALUES (102, 'HBase Replication Scope', 'sys_hbase_family_replication_scope', '0', 'admin', '2020-08-23 05:57:58', '',
        NULL, 'HBase Replication Scope');
INSERT INTO `sys_dict_type`
VALUES (103, 'HBase列簇压缩类型', 'sys_hbase_family_compression_type', '0', 'admin', '2020-08-23 06:01:39', '', NULL,
        'HBase列簇压缩类型');
COMMIT;

-- ----------------------------
-- Table structure for sys_hbase_family
-- ----------------------------
DROP TABLE IF EXISTS `sys_hbase_family`;
CREATE TABLE "sys_hbase_family"
(
    "family_id"         bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'family的编号',
    "table_id"          bigint(20)   NOT NULL COMMENT 'HBase表编号',
    "family_name"       varchar(128) NOT NULL COMMENT 'family名称',
    "max_versions"      int(11)      NOT NULL COMMENT '最大版本号',
    "ttl"               int(11)      NOT NULL COMMENT 'ttl',
    "compression_type"  varchar(64) DEFAULT '' COMMENT '列簇数据的压缩类型',
    "replication_scope" char(1)     DEFAULT '0' COMMENT 'REPLICATION_SCOPE(0, 1)',
    "create_by"         varchar(64) DEFAULT '' COMMENT '创建者',
    "create_time"       datetime    DEFAULT NULL COMMENT '创建时间',
    "update_by"         varchar(64) DEFAULT '' COMMENT '更新者',
    "update_time"       datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY ("family_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 30
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBase Family';

-- ----------------------------
-- Records of sys_hbase_family
-- ----------------------------
BEGIN;
INSERT INTO `sys_hbase_family`
VALUES (4, 6, 'a', 1, 2147483647, '', '0', 'admin', '2020-08-23 00:40:40', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (5, 7, 'b', 1, 2147483647, '', '1', 'admin', '2020-08-23 00:41:00', '', '2020-08-23 15:01:03');
INSERT INTO `sys_hbase_family`
VALUES (6, 8, 'g', 1, 2147483647, '', '0', 'admin', '2020-08-23 00:51:54', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (7, 9, 'v', 1, 2147483647, 'LZO', '1', 'admin', '2020-08-23 12:56:59', '', '2020-08-23 15:00:50');
INSERT INTO `sys_hbase_family`
VALUES (8, 10, 'f1', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:18:57', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (9, 10, 'f2', 3, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:18:57', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (10, 11, 'INFO', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:21:15', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (11, 12, 'INFO', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:22:34', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (12, 13, 'f1', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:25:08', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (13, 13, 'f2', 3, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:25:08', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (14, 14, 'f1', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:26:41', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (15, 14, 'f2', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:26:41', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (16, 15, 'f1', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:28:05', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (17, 15, 'f2', 3, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:28:05', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (18, 16, 'f1', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:29:19', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (19, 16, 'f2', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:29:19', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (20, 17, 'f1', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:31:59', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (21, 17, 'f2', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:31:59', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (22, 18, 'f1', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:35:02', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (23, 18, 'f2', 3, 360000, 'SNAPPY', '0', 'admin', '2020-08-23 17:35:02', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (24, 19, 'info1', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:37:01', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (25, 19, 'info2', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:37:01', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (26, 20, 'INFO1', 1, 2147483647, 'NONE', '1', 'admin', '2020-08-23 17:42:48', '', '2020-08-23 17:49:48');
INSERT INTO `sys_hbase_family`
VALUES (27, 20, 'INFO2', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:42:48', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (28, 21, 'f1', 1, 2147483647, 'NONE', '0', 'admin', '2020-09-07 17:50:53', '', NULL);
INSERT INTO `sys_hbase_family`
VALUES (29, 21, 'f2', 1, 2147483647, 'NONE', '0', 'admin', '2020-09-07 17:50:53', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_hbase_namespace
-- ----------------------------
DROP TABLE IF EXISTS `sys_hbase_namespace`;
CREATE TABLE "sys_hbase_namespace"
(
    "namespace_id"   bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '编号',
    "namespace_name" varchar(128) NOT NULL COMMENT 'HBase所属namespace的名称',
    "create_by"      varchar(64) DEFAULT '' COMMENT '创建者',
    "create_time"    datetime    DEFAULT NULL COMMENT '创建时间',
    "update_by"      varchar(64) DEFAULT '' COMMENT '更新者',
    "update_time"    datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY ("namespace_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBaseNamespace';

-- ----------------------------
-- Records of sys_hbase_namespace
-- ----------------------------
BEGIN;
INSERT INTO `sys_hbase_namespace`
VALUES (1, 'TEST', '', '2020-08-22 23:11:17', '', NULL);
INSERT INTO `sys_hbase_namespace`
VALUES (2, 'TEST2', '', '2020-08-22 23:11:17', '', NULL);
INSERT INTO `sys_hbase_namespace`
VALUES (3, 'TEST3', '', '2020-08-22 23:11:17', '', NULL);
INSERT INTO `sys_hbase_namespace`
VALUES (4, 'default', '', '2020-08-22 23:11:17', '', NULL);
INSERT INTO `sys_hbase_namespace`
VALUES (5, 'hbase', '', '2020-08-22 23:11:17', '', NULL);
INSERT INTO `sys_hbase_namespace`
VALUES (8, 'JohnTest', '', '2020-09-07 17:44:36', '', NULL);
INSERT INTO `sys_hbase_namespace`
VALUES (9, 'LEO_NS', '', '2020-09-07 17:44:36', '', NULL);
INSERT INTO `sys_hbase_namespace`
VALUES (10, 'TESSS', '', '2020-09-07 17:44:36', '', NULL);
INSERT INTO `sys_hbase_namespace`
VALUES (11, 'TEST31', '', '2020-09-07 17:44:36', '', NULL);
INSERT INTO `sys_hbase_namespace`
VALUES (14, 'aa', '', '2020-09-07 17:48:37', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_hbase_table
-- ----------------------------
DROP TABLE IF EXISTS `sys_hbase_table`;
CREATE TABLE "sys_hbase_table"
(
    "table_id"     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'HBase表编号',
    "namespace_id" bigint(20)   NOT NULL COMMENT 'HBase表namespace编号',
    "table_name"   varchar(200) NOT NULL COMMENT 'HBase表名称',
    "disable_flag" char(1)      DEFAULT '0' COMMENT '禁用标志（0代表启用表 2代表禁用表）',
    "create_by"    varchar(64)  DEFAULT '' COMMENT '创建者',
    "create_time"  datetime     DEFAULT NULL COMMENT '创建时间',
    "update_by"    varchar(64)  DEFAULT '' COMMENT '更新者',
    "update_time"  datetime     DEFAULT NULL COMMENT '更新时间',
    "status"       char(1)      DEFAULT '0' COMMENT '状态（0线上表 1待上线表 2测试表 3弃用表）',
    "remark"       varchar(255) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY ("table_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 22
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBase表';

-- ----------------------------
-- Records of sys_hbase_table
-- ----------------------------
BEGIN;
INSERT INTO `sys_hbase_table`
VALUES (11, 1, 'USER', '0', 'admin', '2020-08-23 17:21:15', '', NULL, '1', '用户表');
INSERT INTO `sys_hbase_table`
VALUES (12, 1, 'PERSON', '0', 'admin', '2020-08-23 17:22:34', 'admin', '2020-09-07 18:00:16', '2',
        '人员信息表kkjjljljlklkl金黄色的发货时刻反搜对方水电费');
INSERT INTO `sys_hbase_table`
VALUES (20, 4, 'HISTORY_ORDER', '0', 'admin', '2020-08-23 17:42:48', '', NULL, '0', '历史订单表');
INSERT INTO `sys_hbase_table`
VALUES (21, 14, 'rewr', '0', 'admin', '2020-09-07 17:50:53', 'admin', '2020-09-07 17:51:32', '0', '测试表');
COMMIT;

-- ----------------------------
-- Table structure for sys_hbase_table_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_hbase_table_data`;
CREATE TABLE "sys_hbase_table_data"
(
    "row_key"     varchar(200) NOT NULL COMMENT 'row key',
    "family_name" varchar(200) NOT NULL COMMENT '列簇名称',
    "timestamp"   varchar(20) DEFAULT '0' COMMENT '时间戳',
    "value"       varchar(200) NOT NULL COMMENT '数据值'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBase表';

-- ----------------------------
-- Table structure for sys_hbase_table_tag
-- ----------------------------
DROP TABLE IF EXISTS `sys_hbase_table_tag`;
CREATE TABLE "sys_hbase_table_tag"
(
    "table_id" bigint(20) NOT NULL COMMENT 'HBase 表编号',
    "tag_id"   bigint(20) NOT NULL COMMENT 'HBase tag 编号',
    PRIMARY KEY ("table_id", "tag_id")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBase表所属Tag';

-- ----------------------------
-- Records of sys_hbase_table_tag
-- ----------------------------
BEGIN;
INSERT INTO `sys_hbase_table_tag`
VALUES (6, 1);
INSERT INTO `sys_hbase_table_tag`
VALUES (7, 1);
INSERT INTO `sys_hbase_table_tag`
VALUES (9, 1);
INSERT INTO `sys_hbase_table_tag`
VALUES (10, 1);
INSERT INTO `sys_hbase_table_tag`
VALUES (10, 2);
INSERT INTO `sys_hbase_table_tag`
VALUES (11, 1);
INSERT INTO `sys_hbase_table_tag`
VALUES (12, 2);
INSERT INTO `sys_hbase_table_tag`
VALUES (13, 1);
INSERT INTO `sys_hbase_table_tag`
VALUES (13, 2);
INSERT INTO `sys_hbase_table_tag`
VALUES (16, 1);
INSERT INTO `sys_hbase_table_tag`
VALUES (18, 1);
INSERT INTO `sys_hbase_table_tag`
VALUES (19, 1);
INSERT INTO `sys_hbase_table_tag`
VALUES (20, 2);
INSERT INTO `sys_hbase_table_tag`
VALUES (21, 1);
INSERT INTO `sys_hbase_table_tag`
VALUES (21, 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_hbase_tag
-- ----------------------------
DROP TABLE IF EXISTS `sys_hbase_tag`;
CREATE TABLE "sys_hbase_tag"
(
    "tag_id"      bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'tag的编号',
    "tag_name"    varchar(128) NOT NULL COMMENT 'HBase表Tag名称',
    "create_by"   varchar(64) DEFAULT '' COMMENT '创建者',
    "create_time" datetime    DEFAULT NULL COMMENT '创建时间',
    "update_by"   varchar(64) DEFAULT '' COMMENT '更新者',
    "update_time" datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY ("tag_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBaseTag';

-- ----------------------------
-- Records of sys_hbase_tag
-- ----------------------------
BEGIN;
INSERT INTO `sys_hbase_tag`
VALUES (1, '实时更新', '', '2020-08-22 23:48:26', '', NULL);
INSERT INTO `sys_hbase_tag`
VALUES (2, '离线更新', '', '2020-08-23 16:43:22', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_hbase_user_table
-- ----------------------------
DROP TABLE IF EXISTS `sys_hbase_user_table`;
CREATE TABLE "sys_hbase_user_table"
(
    "user_id"  bigint(20) NOT NULL COMMENT '用户ID',
    "table_id" bigint(20) NOT NULL COMMENT 'HBase 表编号',
    PRIMARY KEY ("user_id", "table_id")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBase表所属用户';

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE "sys_job"
(
    "job_id"          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    "job_name"        varchar(64)  NOT NULL DEFAULT '' COMMENT '任务名称',
    "job_group"       varchar(64)  NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
    "invoke_target"   varchar(500) NOT NULL COMMENT '调用目标字符串',
    "cron_expression" varchar(255)          DEFAULT '' COMMENT 'cron执行表达式',
    "misfire_policy"  varchar(20)           DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
    "concurrent"      char(1)               DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
    "status"          char(1)               DEFAULT '0' COMMENT '状态（0正常 1暂停）',
    "create_by"       varchar(64)           DEFAULT '' COMMENT '创建者',
    "create_time"     datetime              DEFAULT NULL COMMENT '创建时间',
    "update_by"       varchar(64)           DEFAULT '' COMMENT '更新者',
    "update_time"     datetime              DEFAULT NULL COMMENT '更新时间',
    "remark"          varchar(500)          DEFAULT '' COMMENT '备注信息',
    PRIMARY KEY ("job_id", "job_name", "job_group")
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='定时任务调度表';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
BEGIN;
INSERT INTO `sys_job`
VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2018-03-16 11:33:00',
        'ry', '2020-08-16 07:06:01', '');
INSERT INTO `sys_job`
VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin',
        '2018-03-16 11:33:00', 'ry', '2020-08-16 07:06:04', '');
INSERT INTO `sys_job`
VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3',
        '1', '1', 'admin', '2018-03-16 11:33:00', 'ry', '2020-08-16 07:06:06', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE "sys_job_log"
(
    "job_log_id"     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
    "job_name"       varchar(64)  NOT NULL COMMENT '任务名称',
    "job_group"      varchar(64)  NOT NULL COMMENT '任务组名',
    "invoke_target"  varchar(500) NOT NULL COMMENT '调用目标字符串',
    "job_message"    varchar(500)  DEFAULT NULL COMMENT '日志信息',
    "status"         char(1)       DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
    "exception_info" varchar(2000) DEFAULT '' COMMENT '异常信息',
    "create_time"    datetime      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY ("job_log_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 36237
  DEFAULT CHARSET = utf8mb4 COMMENT ='定时任务调度日志表';

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE "sys_logininfor"
(
    "info_id"        bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
    "login_name"     varchar(50)  DEFAULT '' COMMENT '登录账号',
    "ipaddr"         varchar(50)  DEFAULT '' COMMENT '登录IP地址',
    "login_location" varchar(255) DEFAULT '' COMMENT '登录地点',
    "browser"        varchar(50)  DEFAULT '' COMMENT '浏览器类型',
    "os"             varchar(50)  DEFAULT '' COMMENT '操作系统',
    "status"         char(1)      DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
    "msg"            varchar(255) DEFAULT '' COMMENT '提示消息',
    "login_time"     datetime     DEFAULT NULL COMMENT '访问时间',
    PRIMARY KEY ("info_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统访问记录';

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
BEGIN;
INSERT INTO `sys_logininfor`
VALUES (1, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '退出成功', '2020-09-07 10:01:06');
INSERT INTO `sys_logininfor`
VALUES (2, 'dev', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-09-07 10:01:12');
INSERT INTO `sys_logininfor`
VALUES (3, 'dev', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '退出成功', '2020-09-07 10:01:21');
INSERT INTO `sys_logininfor`
VALUES (4, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-09-07 10:01:25');
INSERT INTO `sys_logininfor`
VALUES (5, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '退出成功', '2020-09-07 10:39:42');
INSERT INTO `sys_logininfor`
VALUES (6, 'dev', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-09-07 10:39:47');
INSERT INTO `sys_logininfor`
VALUES (7, 'dev', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '退出成功', '2020-09-07 10:40:19');
INSERT INTO `sys_logininfor`
VALUES (8, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-09-07 10:40:26');
INSERT INTO `sys_logininfor`
VALUES (9, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-09-07 12:19:10');
INSERT INTO `sys_logininfor`
VALUES (10, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-09-07 12:19:15');
INSERT INTO `sys_logininfor`
VALUES (11, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-09-08 13:29:32');
INSERT INTO `sys_logininfor`
VALUES (12, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '退出成功', '2020-09-08 13:34:34');
INSERT INTO `sys_logininfor`
VALUES (13, 'dev', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-09-08 13:34:44');
INSERT INTO `sys_logininfor`
VALUES (14, 'dev', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '退出成功', '2020-09-08 13:35:10');
INSERT INTO `sys_logininfor`
VALUES (15, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-09-08 13:35:16');
INSERT INTO `sys_logininfor`
VALUES (16, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-09-08 14:17:54');
INSERT INTO `sys_logininfor`
VALUES (17, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-09-08 14:18:12');
INSERT INTO `sys_logininfor`
VALUES (18, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-09-08 14:18:15');
INSERT INTO `sys_logininfor`
VALUES (19, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '退出成功', '2020-09-08 14:19:07');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE "sys_menu"
(
    "menu_id"     bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    "menu_name"   varchar(50) NOT NULL COMMENT '菜单名称',
    "parent_id"   bigint(20)   DEFAULT '0' COMMENT '父菜单ID',
    "order_num"   int(4)       DEFAULT '0' COMMENT '显示顺序',
    "url"         varchar(200) DEFAULT '#' COMMENT '请求地址',
    "target"      varchar(20)  DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
    "menu_type"   char(1)      DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    "visible"     char(1)      DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    "perms"       varchar(100) DEFAULT NULL COMMENT '权限标识',
    "icon"        varchar(100) DEFAULT '#' COMMENT '菜单图标',
    "create_by"   varchar(64)  DEFAULT '' COMMENT '创建者',
    "create_time" datetime     DEFAULT NULL COMMENT '创建时间',
    "update_by"   varchar(64)  DEFAULT '' COMMENT '更新者',
    "update_time" datetime     DEFAULT NULL COMMENT '更新时间',
    "remark"      varchar(500) DEFAULT '' COMMENT '备注',
    PRIMARY KEY ("menu_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 2032
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu`
VALUES (1, '系统管理', 0, 2, '#', 'menuItem', 'M', '0', '', 'fa fa-gear', 'admin', '2018-03-16 11:33:00', 'admin',
        '2020-08-16 07:24:44', '系统管理目录');
INSERT INTO `sys_menu`
VALUES (2, '系统监控', 0, 3, '#', 'menuItem', 'M', '0', '', 'fa fa-video-camera', 'admin', '2018-03-16 11:33:00', 'admin',
        '2020-08-16 07:24:32', '系统监控目录');
INSERT INTO `sys_menu`
VALUES (3, '系统工具', 0, 4, '#', 'menuItem', 'M', '0', '', 'fa fa-bars', 'admin', '2018-03-16 11:33:00', 'admin',
        '2020-08-16 07:24:26', '系统工具目录');
INSERT INTO `sys_menu`
VALUES (4, '若依官网', 0, 5, 'http://ruoyi.vip', 'menuBlank', 'C', '1', '', 'fa fa-location-arrow', 'admin',
        '2018-03-16 11:33:00', 'admin', '2020-08-21 16:11:16', '若依官网地址');
INSERT INTO `sys_menu`
VALUES (100, '用户管理', 1, 1, '/system/user', '', 'C', '0', 'system:user:view', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '用户管理菜单');
INSERT INTO `sys_menu`
VALUES (101, '角色管理', 1, 2, '/system/role', '', 'C', '0', 'system:role:view', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '角色管理菜单');
INSERT INTO `sys_menu`
VALUES (102, '菜单管理', 1, 3, '/system/menu', '', 'C', '0', 'system:menu:view', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '菜单管理菜单');
INSERT INTO `sys_menu`
VALUES (103, '部门管理', 1, 4, '/system/dept', '', 'C', '0', 'system:dept:view', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '部门管理菜单');
INSERT INTO `sys_menu`
VALUES (104, '岗位管理', 1, 5, '/system/post', '', 'C', '0', 'system:post:view', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '岗位管理菜单');
INSERT INTO `sys_menu`
VALUES (105, '字典管理', 1, 6, '/system/dict', '', 'C', '0', 'system:dict:view', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '字典管理菜单');
INSERT INTO `sys_menu`
VALUES (106, '参数设置', 1, 7, '/system/config', '', 'C', '0', 'system:config:view', '#', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00', '参数设置菜单');
INSERT INTO `sys_menu`
VALUES (107, '通知公告', 1, 8, '/system/notice', '', 'C', '0', 'system:notice:view', '#', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00', '通知公告菜单');
INSERT INTO `sys_menu`
VALUES (108, '日志管理', 1, 9, '#', '', 'M', '0', '', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00',
        '日志管理菜单');
INSERT INTO `sys_menu`
VALUES (109, '在线用户', 2, 1, '/monitor/online', '', 'C', '0', 'monitor:online:view', '#', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00', '在线用户菜单');
INSERT INTO `sys_menu`
VALUES (110, '定时任务', 2, 2, '/monitor/job', '', 'C', '0', 'monitor:job:view', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '定时任务菜单');
INSERT INTO `sys_menu`
VALUES (111, '数据监控', 2, 3, '/monitor/data', '', 'C', '0', 'monitor:data:view', '#', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00', '数据监控菜单');
INSERT INTO `sys_menu`
VALUES (112, '服务监控', 2, 3, '/monitor/server', '', 'C', '0', 'monitor:server:view', '#', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00', '服务监控菜单');
INSERT INTO `sys_menu`
VALUES (113, '表单构建', 3, 1, '/tool/build', '', 'C', '0', 'tool:build:view', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '表单构建菜单');
INSERT INTO `sys_menu`
VALUES (114, '代码生成', 3, 2, '/tool/gen', '', 'C', '0', 'tool:gen:view', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '代码生成菜单');
INSERT INTO `sys_menu`
VALUES (115, '系统接口', 3, 3, '/tool/swagger', '', 'C', '0', 'tool:swagger:view', '#', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00', '系统接口菜单');
INSERT INTO `sys_menu`
VALUES (500, '操作日志', 108, 1, '/monitor/operlog', '', 'C', '0', 'monitor:operlog:view', '#', 'admin',
        '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '操作日志菜单');
INSERT INTO `sys_menu`
VALUES (501, '登录日志', 108, 2, '/monitor/logininfor', '', 'C', '0', 'monitor:logininfor:view', '#', 'admin',
        '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '登录日志菜单');
INSERT INTO `sys_menu`
VALUES (1000, '用户查询', 100, 1, '#', '', 'F', '0', 'system:user:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1001, '用户新增', 100, 2, '#', '', 'F', '0', 'system:user:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1002, '用户修改', 100, 3, '#', '', 'F', '0', 'system:user:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1003, '用户删除', 100, 4, '#', '', 'F', '0', 'system:user:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1004, '用户导出', 100, 5, '#', '', 'F', '0', 'system:user:export', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1005, '用户导入', 100, 6, '#', '', 'F', '0', 'system:user:import', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1006, '重置密码', 100, 7, '#', '', 'F', '0', 'system:user:resetPwd', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1007, '角色查询', 101, 1, '#', '', 'F', '0', 'system:role:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1008, '角色新增', 101, 2, '#', '', 'F', '0', 'system:role:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1009, '角色修改', 101, 3, '#', '', 'F', '0', 'system:role:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1010, '角色删除', 101, 4, '#', '', 'F', '0', 'system:role:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1011, '角色导出', 101, 5, '#', '', 'F', '0', 'system:role:export', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1012, '菜单查询', 102, 1, '#', '', 'F', '0', 'system:menu:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1013, '菜单新增', 102, 2, '#', '', 'F', '0', 'system:menu:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1014, '菜单修改', 102, 3, '#', '', 'F', '0', 'system:menu:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1015, '菜单删除', 102, 4, '#', '', 'F', '0', 'system:menu:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1016, '部门查询', 103, 1, '#', '', 'F', '0', 'system:dept:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1017, '部门新增', 103, 2, '#', '', 'F', '0', 'system:dept:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1018, '部门修改', 103, 3, '#', '', 'F', '0', 'system:dept:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1019, '部门删除', 103, 4, '#', '', 'F', '0', 'system:dept:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1020, '岗位查询', 104, 1, '#', '', 'F', '0', 'system:post:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1021, '岗位新增', 104, 2, '#', '', 'F', '0', 'system:post:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1022, '岗位修改', 104, 3, '#', '', 'F', '0', 'system:post:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1023, '岗位删除', 104, 4, '#', '', 'F', '0', 'system:post:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1024, '岗位导出', 104, 5, '#', '', 'F', '0', 'system:post:export', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1025, '字典查询', 105, 1, '#', '', 'F', '0', 'system:dict:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1026, '字典新增', 105, 2, '#', '', 'F', '0', 'system:dict:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1027, '字典修改', 105, 3, '#', '', 'F', '0', 'system:dict:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1028, '字典删除', 105, 4, '#', '', 'F', '0', 'system:dict:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1029, '字典导出', 105, 5, '#', '', 'F', '0', 'system:dict:export', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1030, '参数查询', 106, 1, '#', '', 'F', '0', 'system:config:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1031, '参数新增', 106, 2, '#', '', 'F', '0', 'system:config:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1032, '参数修改', 106, 3, '#', '', 'F', '0', 'system:config:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1033, '参数删除', 106, 4, '#', '', 'F', '0', 'system:config:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1034, '参数导出', 106, 5, '#', '', 'F', '0', 'system:config:export', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1035, '公告查询', 107, 1, '#', '', 'F', '0', 'system:notice:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1036, '公告新增', 107, 2, '#', '', 'F', '0', 'system:notice:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1037, '公告修改', 107, 3, '#', '', 'F', '0', 'system:notice:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1038, '公告删除', 107, 4, '#', '', 'F', '0', 'system:notice:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1039, '操作查询', 500, 1, '#', '', 'F', '0', 'monitor:operlog:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1040, '操作删除', 500, 2, '#', '', 'F', '0', 'monitor:operlog:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1041, '详细信息', 500, 3, '#', '', 'F', '0', 'monitor:operlog:detail', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1042, '日志导出', 500, 4, '#', '', 'F', '0', 'monitor:operlog:export', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1043, '登录查询', 501, 1, '#', '', 'F', '0', 'monitor:logininfor:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1044, '登录删除', 501, 2, '#', '', 'F', '0', 'monitor:logininfor:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1045, '日志导出', 501, 3, '#', '', 'F', '0', 'monitor:logininfor:export', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1046, '账户解锁', 501, 4, '#', '', 'F', '0', 'monitor:logininfor:unlock', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1047, '在线查询', 109, 1, '#', '', 'F', '0', 'monitor:online:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1048, '批量强退', 109, 2, '#', '', 'F', '0', 'monitor:online:batchForceLogout', '#', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1049, '单条强退', 109, 3, '#', '', 'F', '0', 'monitor:online:forceLogout', '#', 'admin', '2018-03-16 11:33:00',
        'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1050, '任务查询', 110, 1, '#', '', 'F', '0', 'monitor:job:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1051, '任务新增', 110, 2, '#', '', 'F', '0', 'monitor:job:add', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1052, '任务修改', 110, 3, '#', '', 'F', '0', 'monitor:job:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1053, '任务删除', 110, 4, '#', '', 'F', '0', 'monitor:job:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1054, '状态修改', 110, 5, '#', '', 'F', '0', 'monitor:job:changeStatus', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1055, '任务详细', 110, 6, '#', '', 'F', '0', 'monitor:job:detail', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1056, '任务导出', 110, 7, '#', '', 'F', '0', 'monitor:job:export', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1057, '生成查询', 114, 1, '#', '', 'F', '0', 'tool:gen:list', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1058, '生成修改', 114, 2, '#', '', 'F', '0', 'tool:gen:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1059, '生成删除', 114, 3, '#', '', 'F', '0', 'tool:gen:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1060, '预览代码', 114, 4, '#', '', 'F', '0', 'tool:gen:preview', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (1061, '生成代码', 114, 5, '#', '', 'F', '0', 'tool:gen:code', '#', 'admin', '2018-03-16 11:33:00', 'ry',
        '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu`
VALUES (2000, '命名空间', 2006, 1, '/system/namespace', 'menuItem', 'C', '0', 'system:namespace:view', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2020-08-22 14:01:31', 'HBaseNamespace菜单');
INSERT INTO `sys_menu`
VALUES (2001, 'namespace查询', 2000, 1, '#', '', 'F', '0', 'system:namespace:list', '#', 'admin', '2018-03-01 00:00:00',
        'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2002, 'namespace新增', 2000, 2, '#', '', 'F', '0', 'system:namespace:add', '#', 'admin', '2018-03-01 00:00:00',
        'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2003, 'namespace修改', 2000, 3, '#', '', 'F', '0', 'system:namespace:edit', '#', 'admin', '2018-03-01 00:00:00',
        'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2004, 'namespace删除', 2000, 4, '#', '', 'F', '0', 'system:namespace:remove', '#', 'admin', '2018-03-01 00:00:00',
        'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2005, 'namespace导出', 2000, 5, '#', '', 'F', '0', 'system:namespace:export', '#', 'admin', '2018-03-01 00:00:00',
        'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2006, 'HBase表管理', 0, 1, '#', 'menuItem', 'M', '0', '', 'fa fa-newspaper-o', 'admin', '2020-08-16 07:24:06',
        'admin', '2020-08-16 07:24:38', '');
INSERT INTO `sys_menu`
VALUES (2007, 'HBase表管理', 2006, 2, '/system/table', 'menuItem', 'C', '0', 'system:table:view', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2020-08-22 14:02:06', 'HBase表菜单');
INSERT INTO `sys_menu`
VALUES (2008, 'HBase表查询', 2007, 1, '#', '', 'F', '0', 'system:table:list', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2009, 'HBase表新增', 2007, 2, '#', '', 'F', '0', 'system:table:add', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2010, 'HBase表修改', 2007, 3, '#', '', 'F', '0', 'system:table:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2011, 'HBase表删除', 2007, 4, '#', '', 'F', '0', 'system:table:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2012, 'HBase表导出', 2007, 5, '#', '', 'F', '0', 'system:table:export', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2013, '标签管理', 2006, 4, '/system/tag', 'menuItem', 'C', '0', 'system:tag:view', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2020-08-22 14:03:08', 'HBaseTag菜单');
INSERT INTO `sys_menu`
VALUES (2014, 'HBaseTag查询', 2013, 1, '#', '', 'F', '0', 'system:tag:list', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2015, 'HBaseTag新增', 2013, 2, '#', '', 'F', '0', 'system:tag:add', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2016, 'HBaseTag修改', 2013, 3, '#', '', 'F', '0', 'system:tag:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2018, 'HBaseTag导出', 2013, 5, '#', '', 'F', '0', 'system:tag:export', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2019, '查看详情', 2007, 6, '#', 'menuItem', 'F', '0', 'system:table:detail', '#', 'admin', '2020-08-21 14:54:22',
        '', NULL, '');
INSERT INTO `sys_menu`
VALUES (2020, '列簇管理', 2006, 3, '/system/family', 'menuItem', 'C', '1', 'system:family:view', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2020-08-23 07:03:57', 'HBase Family菜单');
INSERT INTO `sys_menu`
VALUES (2021, 'HBase列簇', 2020, 1, '#', '', 'F', '0', 'system:family:list', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2022, 'HBase列簇新增', 2020, 2, '#', '', 'F', '0', 'system:family:add', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2023, 'HBase列簇修改', 2020, 3, '#', '', 'F', '0', 'system:family:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2024, 'HBase列簇删除', 2020, 4, '#', '', 'F', '0', 'system:family:remove', '#', 'admin', '2018-03-01 00:00:00',
        'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2025, 'HBase列簇导出', 2020, 5, '#', '', 'F', '0', 'system:family:export', '#', 'admin', '2018-03-01 00:00:00',
        'ry', '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2026, 'HBase表数据管理', 2006, 5, '/system/data', 'menuItem', 'C', '0', 'system:data:view', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2020-09-07 12:52:45', 'HBase表数据管理菜单');
INSERT INTO `sys_menu`
VALUES (2027, 'HBase表数据查询', 2026, 1, '#', '', 'F', '0', 'system:data:list', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2028, 'HBase表数据新增', 2026, 2, '#', '', 'F', '0', 'system:data:add', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2029, 'HBase表数据修改', 2026, 3, '#', '', 'F', '0', 'system:data:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2030, 'HBase表数据删除', 2026, 4, '#', '', 'F', '0', 'system:data:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
INSERT INTO `sys_menu`
VALUES (2031, 'HBase表数据导出', 2026, 5, '#', '', 'F', '0', 'system:data:export', '#', 'admin', '2018-03-01 00:00:00', 'ry',
        '2018-03-01 00:00:00', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE "sys_notice"
(
    "notice_id"      int(4)      NOT NULL AUTO_INCREMENT COMMENT '公告ID',
    "notice_title"   varchar(50) NOT NULL COMMENT '公告标题',
    "notice_type"    char(1)     NOT NULL COMMENT '公告类型（1通知 2公告）',
    "notice_content" varchar(2000) DEFAULT NULL COMMENT '公告内容',
    "status"         char(1)       DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
    "create_by"      varchar(64)   DEFAULT '' COMMENT '创建者',
    "create_time"    datetime      DEFAULT NULL COMMENT '创建时间',
    "update_by"      varchar(64)   DEFAULT '' COMMENT '更新者',
    "update_time"    datetime      DEFAULT NULL COMMENT '更新时间',
    "remark"         varchar(255)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY ("notice_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4 COMMENT ='通知公告表';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
BEGIN;
INSERT INTO `sys_notice`
VALUES (1, 'hbase-manager上线', '2',
        '<p>hbase-manager功能一览</p><ol><li>命名空间管理</li><li>表管理</li><li>列簇管理</li><li>表标签管理</li></ol>', '0', 'admin',
        '2020-09-07 10:04:40', '', NULL, NULL);
INSERT INTO `sys_notice`
VALUES (2, 'hbase-manager上线', '1', '<p>hbase-manager已经上线</p>', '0', 'admin', '2020-09-08 13:34:31', '', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE "sys_oper_log"
(
    "oper_id"        bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    "title"          varchar(50)   DEFAULT '' COMMENT '模块标题',
    "business_type"  int(2)        DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    "method"         varchar(100)  DEFAULT '' COMMENT '方法名称',
    "request_method" varchar(10)   DEFAULT '' COMMENT '请求方式',
    "operator_type"  int(1)        DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    "oper_name"      varchar(50)   DEFAULT '' COMMENT '操作人员',
    "dept_name"      varchar(50)   DEFAULT '' COMMENT '部门名称',
    "oper_url"       varchar(255)  DEFAULT '' COMMENT '请求URL',
    "oper_ip"        varchar(50)   DEFAULT '' COMMENT '主机地址',
    "oper_location"  varchar(255)  DEFAULT '' COMMENT '操作地点',
    "oper_param"     varchar(2000) DEFAULT '' COMMENT '请求参数',
    "json_result"    varchar(2000) DEFAULT '' COMMENT '返回参数',
    "status"         int(1)        DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
    "error_msg"      varchar(2000) DEFAULT '' COMMENT '错误消息',
    "oper_time"      datetime      DEFAULT NULL COMMENT '操作时间',
    PRIMARY KEY ("oper_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 43
  DEFAULT CHARSET = utf8mb4 COMMENT ='操作日志记录';

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_oper_log`
VALUES (1, 'HBaseNamespace', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseNamespaceController.remove()',
        'POST', 1, 'admin', '研发部门', '/system/namespace/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"9\" ]\n}',
        'null', 1,
        'org.apache.hadoop.hbase.DoNotRetryIOException: Unable to load exception received from server:org.apache.hadoop.hbase.constraint.ConstraintException',
        '2020-09-07 09:47:57');
INSERT INTO `sys_oper_log`
VALUES (2, 'HBaseNamespace', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseNamespaceController.remove()',
        'POST', 1, 'admin', '研发部门', '/system/namespace/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"6\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 09:48:13');
INSERT INTO `sys_oper_log`
VALUES (3, 'HBaseNamespace', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseNamespaceController.remove()',
        'POST', 1, 'admin', '研发部门', '/system/namespace/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"12\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 09:48:24');
INSERT INTO `sys_oper_log`
VALUES (4, 'HBaseNamespace', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseNamespaceController.remove()',
        'POST', 1, 'admin', '研发部门', '/system/namespace/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"13\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 09:48:28');
INSERT INTO `sys_oper_log`
VALUES (5, 'HBaseNamespace', 1, 'com.leo.hbase.manager.web.controller.system.SysHbaseNamespaceController.addSave()',
        'POST', 1, 'admin', '研发部门', '/system/namespace/add', '127.0.0.1', '内网IP',
        '{\n  \"namespaceName\" : [ \"aa\" ]\n}', '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL,
        '2020-09-07 09:48:37');
INSERT INTO `sys_oper_log`
VALUES (6, 'HBase', 1, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.addSave()', 'POST', 1,
        'admin', '研发部门', '/system/table/add', '127.0.0.1', '内网IP',
        '{\n  \"namespaceId\" : [ \"14\" ],\n  \"tableName\" : [ \"rewr\" ],\n  \"status\" : [ \"0\" ],\n  \"disableFlag\" : [ \"0\" ],\n  \"remark\" : [ \"测试表\" ],\n  \"families[0].familyName\" : [ \"f1\" ],\n  \"families[0].maxVersions\" : [ \"1\" ],\n  \"families[0].ttl\" : [ \"2147483647\" ],\n  \"families[0].compressionType\" : [ \"NONE\" ],\n  \"families[1].familyName\" : [ \"f2\" ],\n  \"families[1].maxVersions\" : [ \"1\" ],\n  \"families[1].ttl\" : [ \"2147483647\" ],\n  \"families[1].compressionType\" : [ \"NONE\" ],\n  \"startKey\" : [ \"1\" ],\n  \"endKey\" : [ \"100\" ],\n  \"preSplitRegions\" : [ \"10\" ],\n  \"preSplitKeys\" : [ \"\" ],\n  \"tagIds\" : [ \"1\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 09:50:53');
INSERT INTO `sys_oper_log`
VALUES (7, 'HBase', 2, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.editSave()', 'POST', 1,
        'admin', '研发部门', '/system/table/edit', '127.0.0.1', '内网IP',
        '{\n  \"tableId\" : [ \"21\" ],\n  \"status\" : [ \"0\" ],\n  \"remark\" : [ \"测试表\" ],\n  \"tagIds\" : [ \"1,2\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 09:51:14');
INSERT INTO `sys_oper_log`
VALUES (8, 'HBase', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.remove()', 'POST', 1,
        'admin', '研发部门', '/system/table/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"21\" ]\n}',
        '{\n  \"msg\" : \"非禁用状态的表不能被删除\",\n  \"code\" : 500\n}', 0, NULL, '2020-09-07 09:51:24');
INSERT INTO `sys_oper_log`
VALUES (9, 'HBase', 2, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.changeDisableStatus()',
        'POST', 1, 'admin', '研发部门', '/system/table/changeDisableStatus', '127.0.0.1', '内网IP',
        '{\n  \"tableId\" : [ \"21\" ],\n  \"disableFlag\" : [ \"2\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 09:51:29');
INSERT INTO `sys_oper_log`
VALUES (10, 'HBase', 2, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.changeDisableStatus()',
        'POST', 1, 'admin', '研发部门', '/system/table/changeDisableStatus', '127.0.0.1', '内网IP',
        '{\n  \"tableId\" : [ \"21\" ],\n  \"disableFlag\" : [ \"0\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 09:51:31');
INSERT INTO `sys_oper_log`
VALUES (11, 'HBase Family', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseFamilyController.remove()', 'POST',
        1, 'admin', '研发部门', '/system/family/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"10\" ]\n}',
        '{\n  \"msg\" : \"暂不支持列簇删除！\",\n  \"code\" : 500\n}', 0, NULL, '2020-09-07 09:59:19');
INSERT INTO `sys_oper_log`
VALUES (12, 'HBase', 2, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.editSave()', 'POST', 1,
        'admin', '研发部门', '/system/table/edit', '127.0.0.1', '内网IP',
        '{\n  \"tableId\" : [ \"12\" ],\n  \"status\" : [ \"2\" ],\n  \"remark\" : [ \"人员信息表kkjjljljlklkl金黄色的发货时刻反搜对方水电费\" ],\n  \"tagIds\" : [ \"2\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 09:59:45');
INSERT INTO `sys_oper_log`
VALUES (13, 'HBase', 2, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.changeDisableStatus()',
        'POST', 1, 'admin', '研发部门', '/system/table/changeDisableStatus', '127.0.0.1', '内网IP',
        '{\n  \"tableId\" : [ \"12\" ],\n  \"disableFlag\" : [ \"2\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 10:00:07');
INSERT INTO `sys_oper_log`
VALUES (14, 'HBase', 2, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.changeDisableStatus()',
        'POST', 1, 'admin', '研发部门', '/system/table/changeDisableStatus', '127.0.0.1', '内网IP',
        '{\n  \"tableId\" : [ \"12\" ],\n  \"disableFlag\" : [ \"0\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 10:00:15');
INSERT INTO `sys_oper_log`
VALUES (15, '角色管理', 2, 'com.leo.hbase.manager.web.controller.system.SysRoleController.authDataScopeSave()', 'POST', 1,
        'admin', '研发部门', '/system/role/authDataScope', '127.0.0.1', '内网IP',
        '{\n  \"roleId\" : [ \"2\" ],\n  \"roleName\" : [ \"普通角色\" ],\n  \"roleKey\" : [ \"common\" ],\n  \"dataScope\" : [ \"2\" ],\n  \"deptIds\" : [ \"100,101,103\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 10:00:54');
INSERT INTO `sys_oper_log`
VALUES (16, '部门管理', 2, 'com.leo.hbase.manager.web.controller.system.SysDeptController.editSave()', 'POST', 1, 'admin',
        '研发部门', '/system/dept/edit', '127.0.0.1', '内网IP',
        '{\n  \"deptId\" : [ \"101\" ],\n  \"parentId\" : [ \"100\" ],\n  \"parentName\" : [ \"hbase-manager\" ],\n  \"deptName\" : [ \"大数据事业部\" ],\n  \"orderNum\" : [ \"1\" ],\n  \"leader\" : [ \"admin\" ],\n  \"phone\" : [ \"15888888888\" ],\n  \"email\" : [ \"big_data@qq.com\" ],\n  \"status\" : [ \"0\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 10:02:16');
INSERT INTO `sys_oper_log`
VALUES (17, '部门管理', 2, 'com.leo.hbase.manager.web.controller.system.SysDeptController.editSave()', 'POST', 1, 'admin',
        '研发部门', '/system/dept/edit', '127.0.0.1', '内网IP',
        '{\n  \"deptId\" : [ \"103\" ],\n  \"parentId\" : [ \"101\" ],\n  \"parentName\" : [ \"大数据事业部\" ],\n  \"deptName\" : [ \"研发部门\" ],\n  \"orderNum\" : [ \"1\" ],\n  \"leader\" : [ \"leojie\" ],\n  \"phone\" : [ \"15888888888\" ],\n  \"email\" : [ \"leojie@qq.com\" ],\n  \"status\" : [ \"0\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 10:02:38');
INSERT INTO `sys_oper_log`
VALUES (18, '参数管理', 2, 'com.leo.hbase.manager.web.controller.system.SysConfigController.editSave()', 'POST', 1, 'admin',
        '研发部门', '/system/config/edit', '127.0.0.1', '内网IP',
        '{\n  \"configId\" : [ \"5\" ],\n  \"configName\" : [ \"用户管理-密码字符范围\" ],\n  \"configKey\" : [ \"sys.account.chrtype\" ],\n  \"configValue\" : [ \"0\" ],\n  \"configType\" : [ \"Y\" ],\n  \"remark\" : [ \"默认任意字符范围，0任意（密码可以输入任意字符），1数字（密码只能为0-9数字），2英文字母（密码只能为a-z和A-Z字母），3字母和数字（密码必须包含字母，数字）,4字母数组和特殊字符（密码必须包含字母，数字，特殊字符-_）\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 10:03:28');
INSERT INTO `sys_oper_log`
VALUES (19, '通知公告', 1, 'com.leo.hbase.manager.web.controller.system.SysNoticeController.addSave()', 'POST', 1, 'admin',
        '研发部门', '/system/notice/add', '127.0.0.1', '内网IP',
        '{\n  \"noticeTitle\" : [ \"hbase-manager上线\" ],\n  \"noticeType\" : [ \"2\" ],\n  \"noticeContent\" : [ \"<p>hbase-manager功能一览</p><ol><li>命名空间管理</li><li>表管理</li><li>列簇管理</li><li>表标签管理</li></ol>\" ],\n  \"status\" : [ \"0\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 10:04:41');
INSERT INTO `sys_oper_log`
VALUES (20, 'HBaseNamespace', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseNamespaceController.remove()',
        'POST', 1, 'admin', '研发部门', '/system/namespace/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"7\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 10:05:37');
INSERT INTO `sys_oper_log`
VALUES (21, 'HBaseNamespace', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseNamespaceController.remove()',
        'POST', 1, 'admin', '研发部门', '/system/namespace/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"14\" ]\n}',
        '{\n  \"msg\" : \"该namespace中存在表，不能被删除!\",\n  \"code\" : 500\n}', 0, NULL, '2020-09-07 10:05:44');
INSERT INTO `sys_oper_log`
VALUES (22, 'HBase', 2, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.editSave()', 'POST', 1,
        'admin', '研发部门', '/system/table/edit', '127.0.0.1', '内网IP',
        '{\n  \"tableId\" : [ \"17\" ],\n  \"status\" : [ \"1\" ],\n  \"remark\" : [ \"测试表\" ],\n  \"tagIds\" : [ \"\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 10:06:00');
INSERT INTO `sys_oper_log`
VALUES (23, 'HBase', 2, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.editSave()', 'POST', 1,
        'admin', '研发部门', '/system/table/edit', '127.0.0.1', '内网IP',
        '{\n  \"tableId\" : [ \"17\" ],\n  \"status\" : [ \"3\" ],\n  \"remark\" : [ \"测试表\" ],\n  \"tagIds\" : [ \"\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 10:06:08');
INSERT INTO `sys_oper_log`
VALUES (24, '角色管理', 2, 'com.leo.hbase.manager.web.controller.system.SysRoleController.editSave()', 'POST', 1, 'admin',
        '研发部门', '/system/role/edit', '127.0.0.1', '内网IP',
        '{\n  \"roleId\" : [ \"2\" ],\n  \"roleName\" : [ \"普通角色\" ],\n  \"roleKey\" : [ \"common\" ],\n  \"roleSort\" : [ \"2\" ],\n  \"status\" : [ \"0\" ],\n  \"remark\" : [ \"普通角色\" ],\n  \"menuIds\" : [ \"2006,2000,2001,2002,2005,2007,2008,2009,2010,2012,2019,2020,2021,2022,2023,2024,2025,2013,2014,2018,1,107,1035,1036,1037,1038\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 10:39:36');
INSERT INTO `sys_oper_log`
VALUES (25, 'HBaseNamespace', 1, 'com.leo.hbase.manager.web.controller.system.SysHbaseNamespaceController.addSave()',
        'POST', 1, 'dev', '研发部门', '/system/namespace/add', '127.0.0.1', '内网IP',
        '{\n  \"namespaceName\" : [ \"de\" ]\n}', '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL,
        '2020-09-07 10:40:00');
INSERT INTO `sys_oper_log`
VALUES (26, 'HBaseNamespace', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseNamespaceController.remove()',
        'POST', 1, 'admin', '研发部门', '/system/namespace/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"14\" ]\n}',
        '{\n  \"msg\" : \"该namespace中存在表，不能被删除!\",\n  \"code\" : 500\n}', 0, NULL, '2020-09-07 11:03:36');
INSERT INTO `sys_oper_log`
VALUES (27, 'HBaseNamespace', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseNamespaceController.remove()',
        'POST', 1, 'admin', '研发部门', '/system/namespace/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"15\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 11:03:43');
INSERT INTO `sys_oper_log`
VALUES (28, '代码生成', 6, 'com.leo.hbase.manager.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin',
        '研发部门', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\n  \"tables\" : [ \"sys_hbase_table_data\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 12:19:28');
INSERT INTO `sys_oper_log`
VALUES (29, '代码生成', 8, 'com.leo.hbase.manager.generator.controller.GenController.download()', 'GET', 1, 'admin', '研发部门',
        '/tool/gen/download/sys_hbase_table_data', '127.0.0.1', '内网IP', '{ }', 'null', 0, NULL, '2020-09-07 12:19:35');
INSERT INTO `sys_oper_log`
VALUES (30, '菜单管理', 2, 'com.leo.hbase.manager.web.controller.system.SysMenuController.editSave()', 'POST', 1, 'admin',
        '研发部门', '/system/menu/edit', '127.0.0.1', '内网IP',
        '{\n  \"menuId\" : [ \"2026\" ],\n  \"parentId\" : [ \"2006\" ],\n  \"menuType\" : [ \"C\" ],\n  \"menuName\" : [ \"HBase表数据管理\" ],\n  \"url\" : [ \"/system/data\" ],\n  \"target\" : [ \"menuItem\" ],\n  \"perms\" : [ \"system:data:view\" ],\n  \"orderNum\" : [ \"1\" ],\n  \"icon\" : [ \"#\" ],\n  \"visible\" : [ \"0\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 12:23:07');
INSERT INTO `sys_oper_log`
VALUES (31, 'HBase数据', 1, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableDataController.addSave()', 'POST',
        1, 'admin', '研发部门', '/system/data/add', '127.0.0.1', '内网IP',
        '{\n  \"rowKey\" : [ \"1231\" ],\n  \"familyName\" : [ \"1323\" ],\n  \"timestamp\" : [ \"13123\" ],\n  \"value\" : [ \"12313\" ]\n}',
        '{\n  \"msg\" : \"暂不支持新增数据\",\n  \"code\" : 500\n}', 0, NULL, '2020-09-07 12:51:42');
INSERT INTO `sys_oper_log`
VALUES (32, '菜单管理', 2, 'com.leo.hbase.manager.web.controller.system.SysMenuController.editSave()', 'POST', 1, 'admin',
        '研发部门', '/system/menu/edit', '127.0.0.1', '内网IP',
        '{\n  \"menuId\" : [ \"2026\" ],\n  \"parentId\" : [ \"2006\" ],\n  \"menuType\" : [ \"C\" ],\n  \"menuName\" : [ \"HBase表数据管理\" ],\n  \"url\" : [ \"/system/data\" ],\n  \"target\" : [ \"menuItem\" ],\n  \"perms\" : [ \"system:data:view\" ],\n  \"orderNum\" : [ \"5\" ],\n  \"icon\" : [ \"#\" ],\n  \"visible\" : [ \"0\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-07 12:52:45');
INSERT INTO `sys_oper_log`
VALUES (33, 'HBase数据', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableDataController.remove()', 'POST', 1,
        'admin', '研发部门', '/system/data/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"11004\" ]\n}',
        '{\n  \"msg\" : \"暂不支持删除数据\",\n  \"code\" : 500\n}', 0, NULL, '2020-09-07 14:34:41');
INSERT INTO `sys_oper_log`
VALUES (34, 'HBase数据', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableDataController.remove()', 'POST', 1,
        'admin', '研发部门', '/system/data/remove', '127.0.0.1', '内网IP',
        '{\n  \"ids\" : [ \"TEST:LEO_USER,info1:username,11004\" ]\n}',
        '{\n  \"msg\" : \"暂不支持删除数据\",\n  \"code\" : 500\n}', 0, NULL, '2020-09-07 15:05:34');
INSERT INTO `sys_oper_log`
VALUES (35, 'HBase数据', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableDataController.remove()', 'POST', 1,
        'admin', '研发部门', '/system/data/remove', '127.0.0.1', '内网IP',
        '{\n  \"ids\" : [ \"TEST:LEO_USER,info1:username,11004\" ]\n}',
        '{\n  \"msg\" : \"暂不支持删除数据\",\n  \"code\" : 500\n}', 0, NULL, '2020-09-07 15:06:02');
INSERT INTO `sys_oper_log`
VALUES (36, 'HBase数据', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableDataController.remove()', 'POST', 1,
        'admin', '研发部门', '/system/data/remove', '127.0.0.1', '内网IP',
        '{\n  \"ids\" : [ \"TEST:LEO_USER,info1:age,10003\" ]\n}', '{\n  \"msg\" : \"数据删除成功！\",\n  \"code\" : 0\n}', 0,
        NULL, '2020-09-07 15:25:54');
INSERT INTO `sys_oper_log`
VALUES (37, 'HBase', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.remove()', 'POST', 1,
        'admin', '研发部门', '/system/table/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"11\" ]\n}',
        '{\n  \"msg\" : \"非禁用状态的表不能被删除\",\n  \"code\" : 500\n}', 0, NULL, '2020-09-08 13:31:55');
INSERT INTO `sys_oper_log`
VALUES (38, 'HBase', 2, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.changeDisableStatus()',
        'POST', 1, 'admin', '研发部门', '/system/table/changeDisableStatus', '127.0.0.1', '内网IP',
        '{\n  \"tableId\" : [ \"17\" ],\n  \"disableFlag\" : [ \"2\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-08 13:31:59');
INSERT INTO `sys_oper_log`
VALUES (39, 'HBase', 3, 'com.leo.hbase.manager.web.controller.system.SysHbaseTableController.remove()', 'POST', 1,
        'admin', '研发部门', '/system/table/remove', '127.0.0.1', '内网IP', '{\n  \"ids\" : [ \"17\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-08 13:32:10');
INSERT INTO `sys_oper_log`
VALUES (40, '岗位管理', 2, 'com.leo.hbase.manager.web.controller.system.SysPostController.editSave()', 'POST', 1, 'admin',
        '研发部门', '/system/post/edit', '127.0.0.1', '内网IP',
        '{\n  \"postId\" : [ \"2\" ],\n  \"postName\" : [ \"项目经理\" ],\n  \"postCode\" : [ \"xmjl\" ],\n  \"postSort\" : [ \"2\" ],\n  \"status\" : [ \"0\" ],\n  \"remark\" : [ \"\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-08 13:33:16');
INSERT INTO `sys_oper_log`
VALUES (41, '通知公告', 1, 'com.leo.hbase.manager.web.controller.system.SysNoticeController.addSave()', 'POST', 1, 'admin',
        '研发部门', '/system/notice/add', '127.0.0.1', '内网IP',
        '{\n  \"noticeTitle\" : [ \"hbase-manager上线\" ],\n  \"noticeType\" : [ \"1\" ],\n  \"noticeContent\" : [ \"<p>hbase-manager已经上线</p>\" ],\n  \"status\" : [ \"0\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-08 13:34:31');
INSERT INTO `sys_oper_log`
VALUES (42, '角色管理', 2, 'com.leo.hbase.manager.web.controller.system.SysRoleController.editSave()', 'POST', 1, 'admin',
        '研发部门', '/system/role/edit', '127.0.0.1', '内网IP',
        '{\n  \"roleId\" : [ \"2\" ],\n  \"roleName\" : [ \"普通角色\" ],\n  \"roleKey\" : [ \"common\" ],\n  \"roleSort\" : [ \"2\" ],\n  \"status\" : [ \"0\" ],\n  \"remark\" : [ \"普通角色\" ],\n  \"menuIds\" : [ \"2006,2000,2001,2002,2005,2007,2008,2009,2010,2012,2019,2020,2021,2022,2023,2024,2025,2013,2014,2015,2016,2018,2026,2027,2028,2029,2030,2031,1,107,1035,1036,1037,1038\" ]\n}',
        '{\n  \"msg\" : \"操作成功\",\n  \"code\" : 0\n}', 0, NULL, '2020-09-08 13:36:21');
COMMIT;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE "sys_post"
(
    "post_id"     bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    "post_code"   varchar(64) NOT NULL COMMENT '岗位编码',
    "post_name"   varchar(50) NOT NULL COMMENT '岗位名称',
    "post_sort"   int(4)      NOT NULL COMMENT '显示顺序',
    "status"      char(1)     NOT NULL COMMENT '状态（0正常 1停用）',
    "create_by"   varchar(64)  DEFAULT '' COMMENT '创建者',
    "create_time" datetime     DEFAULT NULL COMMENT '创建时间',
    "update_by"   varchar(64)  DEFAULT '' COMMENT '更新者',
    "update_time" datetime     DEFAULT NULL COMMENT '更新时间',
    "remark"      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY ("post_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4 COMMENT ='岗位信息表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
INSERT INTO `sys_post`
VALUES (1, 'dks', '大数据开发工程师', 1, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-08-11 14:05:55', '');
INSERT INTO `sys_post`
VALUES (2, 'xmjl', '项目经理', 2, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-09-08 13:33:16', '');
INSERT INTO `sys_post`
VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_post`
VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-08-16 10:54:22', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE "sys_role"
(
    "role_id"     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    "role_name"   varchar(30)  NOT NULL COMMENT '角色名称',
    "role_key"    varchar(100) NOT NULL COMMENT '角色权限字符串',
    "role_sort"   int(4)       NOT NULL COMMENT '显示顺序',
    "data_scope"  char(1)      DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    "status"      char(1)      NOT NULL COMMENT '角色状态（0正常 1停用）',
    "del_flag"    char(1)      DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    "create_by"   varchar(64)  DEFAULT '' COMMENT '创建者',
    "create_time" datetime     DEFAULT NULL COMMENT '创建时间',
    "update_by"   varchar(64)  DEFAULT '' COMMENT '更新者',
    "update_time" datetime     DEFAULT NULL COMMENT '更新时间',
    "remark"      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY ("role_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role`
VALUES (1, '超级管理员', 'admin', 1, '1', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '超级管理员');
INSERT INTO `sys_role`
VALUES (2, '普通角色', 'common', 2, '2', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-09-08 13:36:21', '普通角色');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE "sys_role_dept"
(
    "role_id" bigint(20) NOT NULL COMMENT '角色ID',
    "dept_id" bigint(20) NOT NULL COMMENT '部门ID',
    PRIMARY KEY ("role_id", "dept_id")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色和部门关联表';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_dept`
VALUES (2, 100);
INSERT INTO `sys_role_dept`
VALUES (2, 101);
INSERT INTO `sys_role_dept`
VALUES (2, 103);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE "sys_role_menu"
(
    "role_id" bigint(20) NOT NULL COMMENT '角色ID',
    "menu_id" bigint(20) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY ("role_id", "menu_id")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu`
VALUES (2, 1);
INSERT INTO `sys_role_menu`
VALUES (2, 107);
INSERT INTO `sys_role_menu`
VALUES (2, 1035);
INSERT INTO `sys_role_menu`
VALUES (2, 1036);
INSERT INTO `sys_role_menu`
VALUES (2, 1037);
INSERT INTO `sys_role_menu`
VALUES (2, 1038);
INSERT INTO `sys_role_menu`
VALUES (2, 2000);
INSERT INTO `sys_role_menu`
VALUES (2, 2001);
INSERT INTO `sys_role_menu`
VALUES (2, 2002);
INSERT INTO `sys_role_menu`
VALUES (2, 2005);
INSERT INTO `sys_role_menu`
VALUES (2, 2006);
INSERT INTO `sys_role_menu`
VALUES (2, 2007);
INSERT INTO `sys_role_menu`
VALUES (2, 2008);
INSERT INTO `sys_role_menu`
VALUES (2, 2009);
INSERT INTO `sys_role_menu`
VALUES (2, 2010);
INSERT INTO `sys_role_menu`
VALUES (2, 2012);
INSERT INTO `sys_role_menu`
VALUES (2, 2013);
INSERT INTO `sys_role_menu`
VALUES (2, 2014);
INSERT INTO `sys_role_menu`
VALUES (2, 2015);
INSERT INTO `sys_role_menu`
VALUES (2, 2016);
INSERT INTO `sys_role_menu`
VALUES (2, 2018);
INSERT INTO `sys_role_menu`
VALUES (2, 2019);
INSERT INTO `sys_role_menu`
VALUES (2, 2020);
INSERT INTO `sys_role_menu`
VALUES (2, 2021);
INSERT INTO `sys_role_menu`
VALUES (2, 2022);
INSERT INTO `sys_role_menu`
VALUES (2, 2023);
INSERT INTO `sys_role_menu`
VALUES (2, 2024);
INSERT INTO `sys_role_menu`
VALUES (2, 2025);
INSERT INTO `sys_role_menu`
VALUES (2, 2026);
INSERT INTO `sys_role_menu`
VALUES (2, 2027);
INSERT INTO `sys_role_menu`
VALUES (2, 2028);
INSERT INTO `sys_role_menu`
VALUES (2, 2029);
INSERT INTO `sys_role_menu`
VALUES (2, 2030);
INSERT INTO `sys_role_menu`
VALUES (2, 2031);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE "sys_user"
(
    "user_id"     bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    "dept_id"     bigint(20)   DEFAULT NULL COMMENT '部门ID',
    "login_name"  varchar(30) NOT NULL COMMENT '登录账号',
    "user_name"   varchar(30)  DEFAULT '' COMMENT '用户昵称',
    "user_type"   varchar(2)   DEFAULT '00' COMMENT '用户类型（00系统用户 01注册用户）',
    "email"       varchar(50)  DEFAULT '' COMMENT '用户邮箱',
    "phonenumber" varchar(11)  DEFAULT '' COMMENT '手机号码',
    "sex"         char(1)      DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    "avatar"      varchar(100) DEFAULT '' COMMENT '头像路径',
    "password"    varchar(50)  DEFAULT '' COMMENT '密码',
    "salt"        varchar(20)  DEFAULT '' COMMENT '盐加密',
    "status"      char(1)      DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    "del_flag"    char(1)      DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    "login_ip"    varchar(50)  DEFAULT '' COMMENT '最后登陆IP',
    "login_date"  datetime     DEFAULT NULL COMMENT '最后登陆时间',
    "create_by"   varchar(64)  DEFAULT '' COMMENT '创建者',
    "create_time" datetime     DEFAULT NULL COMMENT '创建时间',
    "update_by"   varchar(64)  DEFAULT '' COMMENT '更新者',
    "update_time" datetime     DEFAULT NULL COMMENT '更新时间',
    "remark"      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY ("user_id")
) ENGINE = InnoDB
  AUTO_INCREMENT = 102
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user`
VALUES (1, 103, 'admin', 'leo_jie', '00', 'leojie1314@gmail.com', '15618193521', '1', '',
        '29c67a30398638269fe600f73a054934', '111111', '0', '0', '127.0.0.1', '2020-09-08 22:18:16', 'admin',
        '2018-03-16 11:33:00', 'ry', '2020-09-08 14:18:15', '管理员');
INSERT INTO `sys_user`
VALUES (2, 105, 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '8e6d98b90472783cc73c17047ddccf36', '222222',
        '0', '2', '127.0.0.1', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00',
        '测试员');
INSERT INTO `sys_user`
VALUES (100, 103, 'dev', 'dev', '00', 'admin@itranswarp.com', '18739577989', '0', '',
        '46b31da4a7e4873847cb04e482df42ce', 'd401f3', '0', '0', '127.0.0.1', '2020-09-08 21:34:44', 'admin',
        '2020-08-12 14:50:19', 'admin', '2020-09-08 13:34:44', '普通角色');
INSERT INTO `sys_user`
VALUES (101, NULL, 'leojie', '', '01', '', '', '0', '', '4dc457192c97dd8823d69a98e9d5a5d8', '83ce56', '0', '0',
        '127.0.0.1', '2020-08-22 00:09:19', '', '2020-08-21 16:08:58', '', '2020-08-21 16:09:18', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_online
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_online`;
CREATE TABLE "sys_user_online"
(
    "sessionId"        varchar(50) NOT NULL DEFAULT '' COMMENT '用户会话id',
    "login_name"       varchar(50)          DEFAULT '' COMMENT '登录账号',
    "dept_name"        varchar(50)          DEFAULT '' COMMENT '部门名称',
    "ipaddr"           varchar(50)          DEFAULT '' COMMENT '登录IP地址',
    "login_location"   varchar(255)         DEFAULT '' COMMENT '登录地点',
    "browser"          varchar(50)          DEFAULT '' COMMENT '浏览器类型',
    "os"               varchar(50)          DEFAULT '' COMMENT '操作系统',
    "status"           varchar(10)          DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
    "start_timestamp"  datetime             DEFAULT NULL COMMENT 'session创建时间',
    "last_access_time" datetime             DEFAULT NULL COMMENT 'session最后访问时间',
    "expire_time"      int(5)               DEFAULT '0' COMMENT '超时时间，单位为分钟',
    PRIMARY KEY ("sessionId")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='在线用户记录';

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE "sys_user_post"
(
    "user_id" bigint(20) NOT NULL COMMENT '用户ID',
    "post_id" bigint(20) NOT NULL COMMENT '岗位ID',
    PRIMARY KEY ("user_id", "post_id")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户与岗位关联表';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_post`
VALUES (1, 1);
INSERT INTO `sys_user_post`
VALUES (2, 2);
INSERT INTO `sys_user_post`
VALUES (100, 1);
INSERT INTO `sys_user_post`
VALUES (100, 3);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE "sys_user_role"
(
    "user_id" bigint(20) NOT NULL COMMENT '用户ID',
    "role_id" bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY ("user_id", "role_id")
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role`
VALUES (1, 1);
INSERT INTO `sys_user_role`
VALUES (2, 2);
INSERT INTO `sys_user_role`
VALUES (100, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS gen_table;
CREATE TABLE gen_table
(
    table_id          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    table_name        varchar(200)  DEFAULT '' COMMENT '表名称',
    table_comment     varchar(500)  DEFAULT '' COMMENT '表描述',
    sub_table_name    varchar(64)   DEFAULT NULL COMMENT '关联子表的表名',
    sub_table_fk_name varchar(64)   DEFAULT NULL COMMENT '子表关联的外键名',
    class_name        varchar(100)  DEFAULT '' COMMENT '实体类名称',
    tpl_category      varchar(200)  DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作 sub主子表操作）',
    package_name      varchar(100)  DEFAULT NULL COMMENT '生成包路径',
    module_name       varchar(30)   DEFAULT NULL COMMENT '生成模块名',
    business_name     varchar(30)   DEFAULT NULL COMMENT '生成业务名',
    function_name     varchar(50)   DEFAULT NULL COMMENT '生成功能名',
    function_author   varchar(50)   DEFAULT NULL COMMENT '生成功能作者',
    gen_type          char(1)       DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
    gen_path          varchar(200)  DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
    options           varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
    create_by         varchar(64)   DEFAULT '' COMMENT '创建者',
    create_time       datetime      DEFAULT NULL COMMENT '创建时间',
    update_by         varchar(64)   DEFAULT '' COMMENT '更新者',
    update_time       datetime      DEFAULT NULL COMMENT '更新时间',
    remark            varchar(500)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (table_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 33
  DEFAULT CHARSET = utf8mb4 COMMENT ='代码生成业务表';


-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS gen_table_column;
CREATE TABLE gen_table_column
(
    column_id      bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    table_id       varchar(64)  DEFAULT NULL COMMENT '归属表编号',
    column_name    varchar(200) DEFAULT NULL COMMENT '列名称',
    column_comment varchar(500) DEFAULT NULL COMMENT '列描述',
    column_type    varchar(100) DEFAULT NULL COMMENT '列类型',
    java_type      varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
    java_field     varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
    is_pk          char(1)      DEFAULT NULL COMMENT '是否主键（1是）',
    is_increment   char(1)      DEFAULT NULL COMMENT '是否自增（1是）',
    is_required    char(1)      DEFAULT NULL COMMENT '是否必填（1是）',
    is_insert      char(1)      DEFAULT NULL COMMENT '是否为插入字段（1是）',
    is_edit        char(1)      DEFAULT NULL COMMENT '是否编辑字段（1是）',
    is_list        char(1)      DEFAULT NULL COMMENT '是否列表字段（1是）',
    is_query       char(1)      DEFAULT NULL COMMENT '是否查询字段（1是）',
    query_type     varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
    html_type      varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
    dict_type      varchar(200) DEFAULT '' COMMENT '字典类型',
    sort           int(11)      DEFAULT NULL COMMENT '排序',
    create_by      varchar(64)  DEFAULT '' COMMENT '创建者',
    create_time    datetime     DEFAULT NULL COMMENT '创建时间',
    update_by      varchar(64)  DEFAULT '' COMMENT '更新者',
    update_time    datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (column_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 276
  DEFAULT CHARSET = utf8mb4 COMMENT ='代码生成业务表字段';


-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS sys_config;
CREATE TABLE sys_config
(
    config_id    int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
    config_name  varchar(100) DEFAULT '' COMMENT '参数名称',
    config_key   varchar(100) DEFAULT '' COMMENT '参数键名',
    config_value varchar(500) DEFAULT '' COMMENT '参数键值',
    config_type  char(1)      DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
    create_by    varchar(64)  DEFAULT '' COMMENT '创建者',
    create_time  datetime     DEFAULT NULL COMMENT '创建时间',
    update_by    varchar(64)  DEFAULT '' COMMENT '更新者',
    update_time  datetime     DEFAULT NULL COMMENT '更新时间',
    remark       varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (config_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4 COMMENT ='参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO sys_config
VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO sys_config
VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '初始化密码 123456');
INSERT INTO sys_config
VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '深黑主题theme-dark，浅色主题theme-light，深蓝主题theme-blue');
INSERT INTO sys_config
VALUES (4, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'true', 'Y', 'admin', '2018-03-16 11:33:00', 'admin',
        '2020-08-21 16:08:32', '是否开启注册用户功能');
INSERT INTO sys_config
VALUES (5, '用户管理-密码字符范围', 'sys.account.chrtype', '0', 'Y', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00',
        '默认任意字符范围，0任意（密码可以输入任意字符），1数字（密码只能为0-9数字），2英文字母（密码只能为a-z和A-Z字母），3字母和数字（密码必须包含字母，数字）,4字母数组和特殊字符（密码必须包含字母，数字，特殊字符-_）');
COMMIT;


-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS sys_dict_data;
CREATE TABLE sys_dict_data
(
    dict_code   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    dict_sort   int(4)       DEFAULT '0' COMMENT '字典排序',
    dict_label  varchar(100) DEFAULT '' COMMENT '字典标签',
    dict_value  varchar(100) DEFAULT '' COMMENT '字典键值',
    dict_type   varchar(100) DEFAULT '' COMMENT '字典类型',
    css_class   varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
    list_class  varchar(100) DEFAULT NULL COMMENT '表格回显样式',
    is_default  char(1)      DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
    status      char(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    create_by   varchar(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime     DEFAULT NULL COMMENT '创建时间',
    update_by   varchar(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime     DEFAULT NULL COMMENT '更新时间',
    remark      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (dict_code)
) ENGINE = InnoDB
  AUTO_INCREMENT = 114
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO sys_dict_data
VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00',
        '性别男');
INSERT INTO sys_dict_data
VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00',
        '性别女');
INSERT INTO sys_dict_data
VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00',
        '性别未知');
INSERT INTO sys_dict_data
VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '显示菜单');
INSERT INTO sys_dict_data
VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '隐藏菜单');
INSERT INTO sys_dict_data
VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '正常状态');
INSERT INTO sys_dict_data
VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '停用状态');
INSERT INTO sys_dict_data
VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '正常状态');
INSERT INTO sys_dict_data
VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '停用状态');
INSERT INTO sys_dict_data
VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '默认分组');
INSERT INTO sys_dict_data
VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '系统分组');
INSERT INTO sys_dict_data
VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '系统默认是');
INSERT INTO sys_dict_data
VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '系统默认否');
INSERT INTO sys_dict_data
VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '通知');
INSERT INTO sys_dict_data
VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '公告');
INSERT INTO sys_dict_data
VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '正常状态');
INSERT INTO sys_dict_data
VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '关闭状态');
INSERT INTO sys_dict_data
VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '其他操作');
INSERT INTO sys_dict_data
VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '新增操作');
INSERT INTO sys_dict_data
VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '修改操作');
INSERT INTO sys_dict_data
VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '删除操作');
INSERT INTO sys_dict_data
VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '授权操作');
INSERT INTO sys_dict_data
VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '导出操作');
INSERT INTO sys_dict_data
VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '导入操作');
INSERT INTO sys_dict_data
VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '强退操作');
INSERT INTO sys_dict_data
VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '生成操作');
INSERT INTO sys_dict_data
VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '清空操作');
INSERT INTO sys_dict_data
VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '正常状态');
INSERT INTO sys_dict_data
VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '停用状态');
INSERT INTO sys_dict_data
VALUES (100, 1, '线上表', '0', 'sys_hbase_table_status', '', 'success', 'Y', '0', 'admin', '2020-08-16 08:12:36', 'admin',
        '2020-08-16 10:52:51', '线上表');
INSERT INTO sys_dict_data
VALUES (101, 2, '待上线表', '1', 'sys_hbase_table_status', '', 'info', 'N', '0', 'admin', '2020-08-16 08:13:27', 'admin',
        '2020-08-18 12:43:30', '待上线表');
INSERT INTO sys_dict_data
VALUES (102, 4, '弃用表', '3', 'sys_hbase_table_status', '', 'danger', 'N', '0', 'admin', '2020-08-16 08:14:04', 'admin',
        '2020-08-18 12:44:43', '弃用表');
INSERT INTO sys_dict_data
VALUES (103, 1, 'ENABLED', '0', 'sys_hbase_table_disable', '', 'primary', 'Y', '0', 'admin', '2020-08-16 11:00:15',
        'admin', '2020-08-18 12:41:25', 'HBase 表enabled');
INSERT INTO sys_dict_data
VALUES (104, 2, 'DISABLED', '2', 'sys_hbase_table_disable', '', 'danger', 'N', '0', 'admin', '2020-08-16 11:01:06',
        'admin', '2020-08-18 12:41:39', 'HBase禁用表');
INSERT INTO sys_dict_data
VALUES (105, 3, '测试表', '2', 'sys_hbase_table_status', '', 'warning', 'N', '0', 'admin', '2020-08-18 12:43:20', 'admin',
        '2020-08-18 12:44:39', '测试表');
INSERT INTO sys_dict_data
VALUES (107, 1, '0', '0', 'sys_hbase_family_replication_scope', '', 'warning', 'Y', '0', 'admin', '2020-08-23 05:59:35',
        'admin', '2020-08-23 06:00:35', '');
INSERT INTO sys_dict_data
VALUES (108, 2, '1', '1', 'sys_hbase_family_replication_scope', '', 'primary', 'N', '0', 'admin', '2020-08-23 06:00:55',
        'admin', '2020-08-23 06:01:02', '');
INSERT INTO sys_dict_data
VALUES (109, 1, 'NONE', 'NONE', 'sys_hbase_family_compression_type', NULL, 'info', 'Y', '0', 'admin',
        '2020-08-23 06:02:31', '', NULL, 'NONE');
INSERT INTO sys_dict_data
VALUES (110, 2, 'SNAPPY', 'SNAPPY', 'sys_hbase_family_compression_type', NULL, 'primary', 'N', '0', 'admin',
        '2020-08-23 06:03:00', '', NULL, 'SNAPPY');
INSERT INTO sys_dict_data
VALUES (111, 3, 'GZIP', 'GZIP', 'sys_hbase_family_compression_type', NULL, 'warning', 'N', '0', 'admin',
        '2020-08-23 06:03:37', '', NULL, 'GZIP');
INSERT INTO sys_dict_data
VALUES (112, 4, 'LZO', 'LZO', 'sys_hbase_family_compression_type', '', 'danger', 'N', '0', 'admin',
        '2020-08-23 06:03:59', 'admin', '2020-08-23 06:04:11', 'LZO');
INSERT INTO sys_dict_data
VALUES (113, 5, 'LZ4', 'LZ4', 'sys_hbase_family_compression_type', NULL, 'success', 'N', '0', 'admin',
        '2020-08-23 06:04:33', '', NULL, 'LZ4');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS sys_dict_type;
CREATE TABLE sys_dict_type
(
    dict_id     bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    dict_name   varchar(100) DEFAULT '' COMMENT '字典名称',
    dict_type   varchar(100) DEFAULT '' COMMENT '字典类型',
    status      char(1)      DEFAULT '0' COMMENT '状态（0正常 1停用）',
    create_by   varchar(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime     DEFAULT NULL COMMENT '创建时间',
    update_by   varchar(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime     DEFAULT NULL COMMENT '更新时间',
    remark      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (dict_id),
    UNIQUE KEY dict_type (dict_type)
) ENGINE = InnoDB
  AUTO_INCREMENT = 104
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典类型表';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO sys_dict_type
VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '用户性别列表');
INSERT INTO sys_dict_type
VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '菜单状态列表');
INSERT INTO sys_dict_type
VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '系统开关列表');
INSERT INTO sys_dict_type
VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '任务状态列表');
INSERT INTO sys_dict_type
VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '任务分组列表');
INSERT INTO sys_dict_type
VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '系统是否列表');
INSERT INTO sys_dict_type
VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '通知类型列表');
INSERT INTO sys_dict_type
VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '通知状态列表');
INSERT INTO sys_dict_type
VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '操作类型列表');
INSERT INTO sys_dict_type
VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '登录状态列表');
INSERT INTO sys_dict_type
VALUES (100, 'HBase表状态', 'sys_hbase_table_status', '0', 'admin', '2020-08-16 08:10:17', '', NULL, 'HBase表状态');
INSERT INTO sys_dict_type
VALUES (101, 'HBase表禁用标志', 'sys_hbase_table_disable', '0', 'admin', '2020-08-16 10:58:45', '', NULL, 'HBase表禁用标志');
INSERT INTO sys_dict_type
VALUES (102, 'HBase Replication Scope', 'sys_hbase_family_replication_scope', '0', 'admin', '2020-08-23 05:57:58', '',
        NULL, 'HBase Replication Scope');
INSERT INTO sys_dict_type
VALUES (103, 'HBase列簇压缩类型', 'sys_hbase_family_compression_type', '0', 'admin', '2020-08-23 06:01:39', '', NULL,
        'HBase列簇压缩类型');
COMMIT;

-- ----------------------------
-- Table structure for sys_hbase_family
-- ----------------------------
DROP TABLE IF EXISTS sys_hbase_family;
CREATE TABLE sys_hbase_family
(
    family_id         bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'family的编号',
    table_id          bigint(20)   NOT NULL COMMENT 'HBase表编号',
    family_name       varchar(128) NOT NULL COMMENT 'family名称',
    max_versions      int(11)      NOT NULL COMMENT '最大版本号',
    ttl               int(11)      NOT NULL COMMENT 'ttl',
    compression_type  varchar(64) DEFAULT '' COMMENT '列簇数据的压缩类型',
    replication_scope char(1)     DEFAULT '0' COMMENT 'REPLICATION_SCOPE(0, 1)',
    create_by         varchar(64) DEFAULT '' COMMENT '创建者',
    create_time       datetime    DEFAULT NULL COMMENT '创建时间',
    update_by         varchar(64) DEFAULT '' COMMENT '更新者',
    update_time       datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (family_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 28
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBase Family';

-- ----------------------------
-- Records of sys_hbase_family
-- ----------------------------
BEGIN;
INSERT INTO sys_hbase_family
VALUES (4, 6, 'a', 1, 2147483647, '', '0', 'admin', '2020-08-23 00:40:40', '', NULL);
INSERT INTO sys_hbase_family
VALUES (5, 7, 'b', 1, 2147483647, '', '1', 'admin', '2020-08-23 00:41:00', '', '2020-08-23 15:01:03');
INSERT INTO sys_hbase_family
VALUES (6, 8, 'g', 1, 2147483647, '', '0', 'admin', '2020-08-23 00:51:54', '', NULL);
INSERT INTO sys_hbase_family
VALUES (7, 9, 'v', 1, 2147483647, 'LZO', '1', 'admin', '2020-08-23 12:56:59', '', '2020-08-23 15:00:50');
INSERT INTO sys_hbase_family
VALUES (8, 10, 'f1', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:18:57', '', NULL);
INSERT INTO sys_hbase_family
VALUES (9, 10, 'f2', 3, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:18:57', '', NULL);
INSERT INTO sys_hbase_family
VALUES (10, 11, 'INFO', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:21:15', '', NULL);
INSERT INTO sys_hbase_family
VALUES (11, 12, 'INFO', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:22:34', '', NULL);
INSERT INTO sys_hbase_family
VALUES (12, 13, 'f1', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:25:08', '', NULL);
INSERT INTO sys_hbase_family
VALUES (13, 13, 'f2', 3, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:25:08', '', NULL);
INSERT INTO sys_hbase_family
VALUES (14, 14, 'f1', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:26:41', '', NULL);
INSERT INTO sys_hbase_family
VALUES (15, 14, 'f2', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:26:41', '', NULL);
INSERT INTO sys_hbase_family
VALUES (16, 15, 'f1', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:28:05', '', NULL);
INSERT INTO sys_hbase_family
VALUES (17, 15, 'f2', 3, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:28:05', '', NULL);
INSERT INTO sys_hbase_family
VALUES (18, 16, 'f1', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:29:19', '', NULL);
INSERT INTO sys_hbase_family
VALUES (19, 16, 'f2', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:29:19', '', NULL);
INSERT INTO sys_hbase_family
VALUES (20, 17, 'f1', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:31:59', '', NULL);
INSERT INTO sys_hbase_family
VALUES (21, 17, 'f2', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:31:59', '', NULL);
INSERT INTO sys_hbase_family
VALUES (22, 18, 'f1', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:35:02', '', NULL);
INSERT INTO sys_hbase_family
VALUES (23, 18, 'f2', 3, 360000, 'SNAPPY', '0', 'admin', '2020-08-23 17:35:02', '', NULL);
INSERT INTO sys_hbase_family
VALUES (24, 19, 'info1', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:37:01', '', NULL);
INSERT INTO sys_hbase_family
VALUES (25, 19, 'info2', 1, 2147483647, 'SNAPPY', '0', 'admin', '2020-08-23 17:37:01', '', NULL);
INSERT INTO sys_hbase_family
VALUES (26, 20, 'INFO1', 1, 2147483647, 'NONE', '1', 'admin', '2020-08-23 17:42:48', '', '2020-08-23 17:49:48');
INSERT INTO sys_hbase_family
VALUES (27, 20, 'INFO2', 1, 2147483647, 'NONE', '0', 'admin', '2020-08-23 17:42:48', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_hbase_namespace
-- ----------------------------
DROP TABLE IF EXISTS sys_hbase_namespace;
CREATE TABLE sys_hbase_namespace
(
    namespace_id   bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '编号',
    namespace_name varchar(128) NOT NULL COMMENT 'HBase所属namespace的名称',
    create_by      varchar(64) DEFAULT '' COMMENT '创建者',
    create_time    datetime    DEFAULT NULL COMMENT '创建时间',
    update_by      varchar(64) DEFAULT '' COMMENT '更新者',
    update_time    datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (namespace_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBaseNamespace';

-- ----------------------------
-- Records of sys_hbase_namespace
-- ----------------------------
BEGIN;
INSERT INTO sys_hbase_namespace
VALUES (1, 'TEST', '', '2020-08-22 23:11:17', '', NULL);
INSERT INTO sys_hbase_namespace
VALUES (2, 'TEST2', '', '2020-08-22 23:11:17', '', NULL);
INSERT INTO sys_hbase_namespace
VALUES (3, 'TEST3', '', '2020-08-22 23:11:17', '', NULL);
INSERT INTO sys_hbase_namespace
VALUES (4, 'default', '', '2020-08-22 23:11:17', '', NULL);
INSERT INTO sys_hbase_namespace
VALUES (5, 'hbase', '', '2020-08-22 23:11:17', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_hbase_table
-- ----------------------------
DROP TABLE IF EXISTS sys_hbase_table;
CREATE TABLE sys_hbase_table
(
    table_id     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'HBase表编号',
    namespace_id bigint(20)   NOT NULL COMMENT 'HBase表namespace编号',
    table_name   varchar(200) NOT NULL COMMENT 'HBase表名称',
    disable_flag char(1)      DEFAULT '0' COMMENT '禁用标志（0代表启用表 2代表禁用表）',
    create_by    varchar(64)  DEFAULT '' COMMENT '创建者',
    create_time  datetime     DEFAULT NULL COMMENT '创建时间',
    update_by    varchar(64)  DEFAULT '' COMMENT '更新者',
    update_time  datetime     DEFAULT NULL COMMENT '更新时间',
    status       char(1)      DEFAULT '0' COMMENT '状态（0线上表 1待上线表 2测试表 3弃用表）',
    remark       varchar(255) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (table_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBase表';

-- ----------------------------
-- Records of sys_hbase_table
-- ----------------------------
BEGIN;
INSERT INTO sys_hbase_table
VALUES (11, 1, 'USER', '0', 'admin', '2020-08-23 17:21:15', '', NULL, '1', '用户表');
INSERT INTO sys_hbase_table
VALUES (12, 1, 'PERSON', '0', 'admin', '2020-08-23 17:22:34', 'admin', '2020-08-23 17:22:53', '2', '人员信息表');
INSERT INTO sys_hbase_table
VALUES (17, 4, 'leo_test3', '0', 'admin', '2020-08-23 17:31:59', '', NULL, '0', '测试表');
INSERT INTO sys_hbase_table
VALUES (20, 4, 'HISTORY_ORDER', '0', 'admin', '2020-08-23 17:42:48', '', NULL, '0', '历史订单表');
COMMIT;

-- ----------------------------
-- Table structure for sys_hbase_table_tag
-- ----------------------------
DROP TABLE IF EXISTS sys_hbase_table_tag;
CREATE TABLE sys_hbase_table_tag
(
    table_id bigint(20) NOT NULL COMMENT 'HBase 表编号',
    tag_id   bigint(20) NOT NULL COMMENT 'HBase tag 编号',
    PRIMARY KEY (table_id, tag_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBase表所属Tag';

-- ----------------------------
-- Records of sys_hbase_table_tag
-- ----------------------------
BEGIN;
INSERT INTO sys_hbase_table_tag
VALUES (6, 1);
INSERT INTO sys_hbase_table_tag
VALUES (7, 1);
INSERT INTO sys_hbase_table_tag
VALUES (9, 1);
INSERT INTO sys_hbase_table_tag
VALUES (10, 1);
INSERT INTO sys_hbase_table_tag
VALUES (10, 2);
INSERT INTO sys_hbase_table_tag
VALUES (11, 1);
INSERT INTO sys_hbase_table_tag
VALUES (12, 2);
INSERT INTO sys_hbase_table_tag
VALUES (13, 1);
INSERT INTO sys_hbase_table_tag
VALUES (13, 2);
INSERT INTO sys_hbase_table_tag
VALUES (16, 1);
INSERT INTO sys_hbase_table_tag
VALUES (18, 1);
INSERT INTO sys_hbase_table_tag
VALUES (19, 1);
INSERT INTO sys_hbase_table_tag
VALUES (20, 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_hbase_tag
-- ----------------------------
DROP TABLE IF EXISTS sys_hbase_tag;
CREATE TABLE sys_hbase_tag
(
    tag_id      bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'tag的编号',
    tag_name    varchar(128) NOT NULL COMMENT 'HBase表Tag名称',
    create_by   varchar(64) DEFAULT '' COMMENT '创建者',
    create_time datetime    DEFAULT NULL COMMENT '创建时间',
    update_by   varchar(64) DEFAULT '' COMMENT '更新者',
    update_time datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (tag_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBaseTag';

-- ----------------------------
-- Records of sys_hbase_tag
-- ----------------------------
BEGIN;
INSERT INTO sys_hbase_tag
VALUES (1, '实时更新', '', '2020-08-22 23:48:26', '', NULL);
INSERT INTO sys_hbase_tag
VALUES (2, '离线更新', '', '2020-08-23 16:43:22', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_hbase_user_table
-- ----------------------------
DROP TABLE IF EXISTS sys_hbase_user_table;
CREATE TABLE sys_hbase_user_table
(
    user_id  bigint(20) NOT NULL COMMENT '用户ID',
    table_id bigint(20) NOT NULL COMMENT 'HBase 表编号',
    PRIMARY KEY (user_id, table_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='HBase表所属用户';

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS sys_job;
CREATE TABLE sys_job
(
    job_id          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    job_name        varchar(64)  NOT NULL DEFAULT '' COMMENT '任务名称',
    job_group       varchar(64)  NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
    invoke_target   varchar(500) NOT NULL COMMENT '调用目标字符串',
    cron_expression varchar(255)          DEFAULT '' COMMENT 'cron执行表达式',
    misfire_policy  varchar(20)           DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
    concurrent      char(1)               DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
    status          char(1)               DEFAULT '0' COMMENT '状态（0正常 1暂停）',
    create_by       varchar(64)           DEFAULT '' COMMENT '创建者',
    create_time     datetime              DEFAULT NULL COMMENT '创建时间',
    update_by       varchar(64)           DEFAULT '' COMMENT '更新者',
    update_time     datetime              DEFAULT NULL COMMENT '更新时间',
    remark          varchar(500)          DEFAULT '' COMMENT '备注信息',
    PRIMARY KEY (job_id, job_name, job_group)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4 COMMENT ='定时任务调度表';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
BEGIN;
INSERT INTO sys_job
VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2018-03-16 11:33:00',
        'leo', '2020-08-16 07:06:01', '');
INSERT INTO sys_job
VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'leo\')', '0/15 * * * * ?', '3', '1', '1', 'admin',
        '2018-03-16 11:33:00', 'leo', '2020-08-16 07:06:04', '');
INSERT INTO sys_job
VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'leo\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3',
        '1', '1', 'admin', '2018-03-16 11:33:00', 'leo', '2020-08-16 07:06:06', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS sys_job_log;
CREATE TABLE sys_job_log
(
    job_log_id     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
    job_name       varchar(64)  NOT NULL COMMENT '任务名称',
    job_group      varchar(64)  NOT NULL COMMENT '任务组名',
    invoke_target  varchar(500) NOT NULL COMMENT '调用目标字符串',
    job_message    varchar(500)  DEFAULT NULL COMMENT '日志信息',
    status         char(1)       DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
    exception_info varchar(2000) DEFAULT '' COMMENT '异常信息',
    create_time    datetime      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (job_log_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 36237
  DEFAULT CHARSET = utf8mb4 COMMENT ='定时任务调度日志表';
-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS sys_dept;
CREATE TABLE sys_dept
(
    dept_id     bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
    parent_id   bigint(20)  DEFAULT '0' COMMENT '父部门id',
    ancestors   varchar(50) DEFAULT '' COMMENT '祖级列表',
    dept_name   varchar(30) DEFAULT '' COMMENT '部门名称',
    order_num   int(4)      DEFAULT '0' COMMENT '显示顺序',
    leader      varchar(20) DEFAULT NULL COMMENT '负责人',
    phone       varchar(11) DEFAULT NULL COMMENT '联系电话',
    email       varchar(50) DEFAULT NULL COMMENT '邮箱',
    status      char(1)     DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
    del_flag    char(1)     DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    create_by   varchar(64) DEFAULT '' COMMENT '创建者',
    create_time datetime    DEFAULT NULL COMMENT '创建时间',
    update_by   varchar(64) DEFAULT '' COMMENT '更新者',
    update_time datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (dept_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 200
  DEFAULT CHARSET = utf8mb4 COMMENT ='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO sys_dept
VALUES (100, 0, '0', 'hbase-manager', 0, 'leojie', '15888888888', 'leo@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00');
INSERT INTO sys_dept
VALUES (101, 100, '0,100', 'AIM', 1, 'leojie', '15888888888', 'leojie@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00');
INSERT INTO sys_dept
VALUES (102, 100, '0,100', '长沙分公司', 2, 'leojie', '15888888888', 'leojie@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00');
INSERT INTO sys_dept
VALUES (103, 101, '0,100,101', '研发部门', 1, 'leojie', '15888888888', 'leojie@qq.com', '0', '0', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00');
INSERT INTO sys_dept
VALUES (104, 101, '0,100,101', '市场部门', 2, 'leojie', '15888888888', 'leojie@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00');
INSERT INTO sys_dept
VALUES (105, 101, '0,100,101', '测试部门', 3, 'leojie', '15888888888', 'leojie@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00');
INSERT INTO sys_dept
VALUES (106, 101, '0,100,101', '财务部门', 4, 'leojie', '15888888888', 'leojie@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00');
INSERT INTO sys_dept
VALUES (107, 101, '0,100,101', '运维部门', 5, 'leojie', '15888888888', 'leojie@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00');
INSERT INTO sys_dept
VALUES (108, 102, '0,100,102', '市场部门', 1, 'leojie', '15888888888', 'leojie@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00');
INSERT INTO sys_dept
VALUES (109, 102, '0,100,102', '财务部门', 2, 'leojie', '15888888888', 'leojie@qq.com', '0', '2', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user
(
    user_id     bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    dept_id     bigint(20)   DEFAULT NULL COMMENT '部门ID',
    login_name  varchar(30) NOT NULL COMMENT '登录账号',
    user_name   varchar(30)  DEFAULT '' COMMENT '用户昵称',
    user_type   varchar(2)   DEFAULT '00' COMMENT '用户类型（00系统用户 01注册用户）',
    email       varchar(50)  DEFAULT '' COMMENT '用户邮箱',
    phonenumber varchar(11)  DEFAULT '' COMMENT '手机号码',
    sex         char(1)      DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    avatar      varchar(100) DEFAULT '' COMMENT '头像路径',
    password    varchar(50)  DEFAULT '' COMMENT '密码',
    salt        varchar(20)  DEFAULT '' COMMENT '盐加密',
    status      char(1)      DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    del_flag    char(1)      DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    login_ip    varchar(50)  DEFAULT '' COMMENT '最后登陆IP',
    login_date  datetime     DEFAULT NULL COMMENT '最后登陆时间',
    create_by   varchar(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime     DEFAULT NULL COMMENT '创建时间',
    update_by   varchar(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime     DEFAULT NULL COMMENT '更新时间',
    remark      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (user_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 102
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户信息表';

-- ----------------------------
-- 初始化用户数据
-- ----------------------------
BEGIN;
INSERT INTO sys_user
VALUES (1, 103, 'admin', 'leo_jie', '00', 'leojie1314@gmail.com', '15618193521', '1', '',
        '29c67a30398638269fe600f73a054934', '111111', '0', '0', '127.0.0.1', '2020-08-22 15:17:00', 'admin',
        '2018-03-16 11:33:00', 'leo', '2020-08-22 07:17:00', '管理员');
INSERT INTO sys_user
VALUES (2, 105, 'leo', 'leojie', '00', 'leojie@qq.com', '15666666666', '1', '', '8e6d98b90472783cc73c17047ddccf36', '222222',
        '0', '2', '127.0.0.1', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00',
        '测试员');
INSERT INTO sys_user
VALUES (100, 103, 'dev', 'dev', '00', 'admin@itranswarp.com', '18739577989', '0', '',
        '46b31da4a7e4873847cb04e482df42ce', 'd401f3', '0', '0', '127.0.0.1', '2020-08-12 22:52:37', 'admin',
        '2020-08-12 14:50:19', 'admin', '2020-08-18 13:14:25', '普通角色');
INSERT INTO sys_user
VALUES (101, NULL, 'leojie', '', '01', '', '', '0', '', '4dc457192c97dd8823d69a98e9d5a5d8', '83ce56', '0', '0',
        '127.0.0.1', '2020-08-22 00:09:19', '', '2020-08-21 16:08:58', '', '2020-08-21 16:09:18', NULL);
COMMIT;
-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS sys_logininfor;
CREATE TABLE sys_logininfor
(
    info_id        bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
    login_name     varchar(50)  DEFAULT '' COMMENT '登录账号',
    ipaddr         varchar(50)  DEFAULT '' COMMENT '登录IP地址',
    login_location varchar(255) DEFAULT '' COMMENT '登录地点',
    browser        varchar(50)  DEFAULT '' COMMENT '浏览器类型',
    os             varchar(50)  DEFAULT '' COMMENT '操作系统',
    status         char(1)      DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
    msg            varchar(255) DEFAULT '' COMMENT '提示消息',
    login_time     datetime     DEFAULT NULL COMMENT '访问时间',
    PRIMARY KEY (info_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 157
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统访问记录';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu
(
    menu_id     bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    menu_name   varchar(50) NOT NULL COMMENT '菜单名称',
    parent_id   bigint(20)   DEFAULT '0' COMMENT '父菜单ID',
    order_num   int(4)       DEFAULT '0' COMMENT '显示顺序',
    url         varchar(200) DEFAULT '#' COMMENT '请求地址',
    target      varchar(20)  DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
    menu_type   char(1)      DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    visible     char(1)      DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    perms       varchar(100) DEFAULT NULL COMMENT '权限标识',
    icon        varchar(100) DEFAULT '#' COMMENT '菜单图标',
    create_by   varchar(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime     DEFAULT NULL COMMENT '创建时间',
    update_by   varchar(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime     DEFAULT NULL COMMENT '更新时间',
    remark      varchar(500) DEFAULT '' COMMENT '备注',
    PRIMARY KEY (menu_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2026
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO sys_menu
VALUES (1, '系统管理', 0, 2, '#', 'menuItem', 'M', '0', '', 'fa fa-gear', 'admin', '2018-03-16 11:33:00', 'admin',
        '2020-08-16 07:24:44', '系统管理目录');
INSERT INTO sys_menu
VALUES (2, '系统监控', 0, 3, '#', 'menuItem', 'M', '0', '', 'fa fa-video-camera', 'admin', '2018-03-16 11:33:00', 'admin',
        '2020-08-16 07:24:32', '系统监控目录');
INSERT INTO sys_menu
VALUES (3, '系统工具', 0, 4, '#', 'menuItem', 'M', '0', '', 'fa fa-bars', 'admin', '2018-03-16 11:33:00', 'admin',
        '2020-08-16 07:24:26', '系统工具目录');
INSERT INTO sys_menu
VALUES (100, '用户管理', 1, 1, '/system/user', '', 'C', '0', 'system:user:view', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '用户管理菜单');
INSERT INTO sys_menu
VALUES (101, '角色管理', 1, 2, '/system/role', '', 'C', '0', 'system:role:view', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '角色管理菜单');
INSERT INTO sys_menu
VALUES (102, '菜单管理', 1, 3, '/system/menu', '', 'C', '0', 'system:menu:view', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '菜单管理菜单');
INSERT INTO sys_menu
VALUES (103, '部门管理', 1, 4, '/system/dept', '', 'C', '0', 'system:dept:view', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '部门管理菜单');
INSERT INTO sys_menu
VALUES (104, '岗位管理', 1, 5, '/system/post', '', 'C', '0', 'system:post:view', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '岗位管理菜单');
INSERT INTO sys_menu
VALUES (105, '字典管理', 1, 6, '/system/dict', '', 'C', '0', 'system:dict:view', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '字典管理菜单');
INSERT INTO sys_menu
VALUES (106, '参数设置', 1, 7, '/system/config', '', 'C', '0', 'system:config:view', '#', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00', '参数设置菜单');
INSERT INTO sys_menu
VALUES (107, '通知公告', 1, 8, '/system/notice', '', 'C', '0', 'system:notice:view', '#', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00', '通知公告菜单');
INSERT INTO sys_menu
VALUES (108, '日志管理', 1, 9, '#', '', 'M', '0', '', '#', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00',
        '日志管理菜单');
INSERT INTO sys_menu
VALUES (109, '在线用户', 2, 1, '/monitor/online', '', 'C', '0', 'monitor:online:view', '#', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00', '在线用户菜单');
INSERT INTO sys_menu
VALUES (110, '定时任务', 2, 2, '/monitor/job', '', 'C', '0', 'monitor:job:view', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '定时任务菜单');
INSERT INTO sys_menu
VALUES (111, '数据监控', 2, 3, '/monitor/data', '', 'C', '0', 'monitor:data:view', '#', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00', '数据监控菜单');
INSERT INTO sys_menu
VALUES (112, '服务监控', 2, 3, '/monitor/server', '', 'C', '0', 'monitor:server:view', '#', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00', '服务监控菜单');
INSERT INTO sys_menu
VALUES (113, '表单构建', 3, 1, '/tool/build', '', 'C', '0', 'tool:build:view', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '表单构建菜单');
INSERT INTO sys_menu
VALUES (114, '代码生成', 3, 2, '/tool/gen', '', 'C', '0', 'tool:gen:view', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '代码生成菜单');
INSERT INTO sys_menu
VALUES (115, '系统接口', 3, 3, '/tool/swagger', '', 'C', '0', 'tool:swagger:view', '#', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00', '系统接口菜单');
INSERT INTO sys_menu
VALUES (500, '操作日志', 108, 1, '/monitor/operlog', '', 'C', '0', 'monitor:operlog:view', '#', 'admin',
        '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '操作日志菜单');
INSERT INTO sys_menu
VALUES (501, '登录日志', 108, 2, '/monitor/logininfor', '', 'C', '0', 'monitor:logininfor:view', '#', 'admin',
        '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '登录日志菜单');
INSERT INTO sys_menu
VALUES (1000, '用户查询', 100, 1, '#', '', 'F', '0', 'system:user:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1001, '用户新增', 100, 2, '#', '', 'F', '0', 'system:user:add', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1002, '用户修改', 100, 3, '#', '', 'F', '0', 'system:user:edit', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1003, '用户删除', 100, 4, '#', '', 'F', '0', 'system:user:remove', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1004, '用户导出', 100, 5, '#', '', 'F', '0', 'system:user:export', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1005, '用户导入', 100, 6, '#', '', 'F', '0', 'system:user:import', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1006, '重置密码', 100, 7, '#', '', 'F', '0', 'system:user:resetPwd', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1007, '角色查询', 101, 1, '#', '', 'F', '0', 'system:role:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1008, '角色新增', 101, 2, '#', '', 'F', '0', 'system:role:add', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1009, '角色修改', 101, 3, '#', '', 'F', '0', 'system:role:edit', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1010, '角色删除', 101, 4, '#', '', 'F', '0', 'system:role:remove', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1011, '角色导出', 101, 5, '#', '', 'F', '0', 'system:role:export', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1012, '菜单查询', 102, 1, '#', '', 'F', '0', 'system:menu:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1013, '菜单新增', 102, 2, '#', '', 'F', '0', 'system:menu:add', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1014, '菜单修改', 102, 3, '#', '', 'F', '0', 'system:menu:edit', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1015, '菜单删除', 102, 4, '#', '', 'F', '0', 'system:menu:remove', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1016, '部门查询', 103, 1, '#', '', 'F', '0', 'system:dept:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1017, '部门新增', 103, 2, '#', '', 'F', '0', 'system:dept:add', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1018, '部门修改', 103, 3, '#', '', 'F', '0', 'system:dept:edit', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1019, '部门删除', 103, 4, '#', '', 'F', '0', 'system:dept:remove', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1020, '岗位查询', 104, 1, '#', '', 'F', '0', 'system:post:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1021, '岗位新增', 104, 2, '#', '', 'F', '0', 'system:post:add', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1022, '岗位修改', 104, 3, '#', '', 'F', '0', 'system:post:edit', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1023, '岗位删除', 104, 4, '#', '', 'F', '0', 'system:post:remove', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1024, '岗位导出', 104, 5, '#', '', 'F', '0', 'system:post:export', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1025, '字典查询', 105, 1, '#', '', 'F', '0', 'system:dict:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1026, '字典新增', 105, 2, '#', '', 'F', '0', 'system:dict:add', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1027, '字典修改', 105, 3, '#', '', 'F', '0', 'system:dict:edit', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1028, '字典删除', 105, 4, '#', '', 'F', '0', 'system:dict:remove', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1029, '字典导出', 105, 5, '#', '', 'F', '0', 'system:dict:export', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1030, '参数查询', 106, 1, '#', '', 'F', '0', 'system:config:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1031, '参数新增', 106, 2, '#', '', 'F', '0', 'system:config:add', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1032, '参数修改', 106, 3, '#', '', 'F', '0', 'system:config:edit', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1033, '参数删除', 106, 4, '#', '', 'F', '0', 'system:config:remove', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1034, '参数导出', 106, 5, '#', '', 'F', '0', 'system:config:export', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1035, '公告查询', 107, 1, '#', '', 'F', '0', 'system:notice:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1036, '公告新增', 107, 2, '#', '', 'F', '0', 'system:notice:add', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1037, '公告修改', 107, 3, '#', '', 'F', '0', 'system:notice:edit', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1038, '公告删除', 107, 4, '#', '', 'F', '0', 'system:notice:remove', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1039, '操作查询', 500, 1, '#', '', 'F', '0', 'monitor:operlog:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1040, '操作删除', 500, 2, '#', '', 'F', '0', 'monitor:operlog:remove', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1041, '详细信息', 500, 3, '#', '', 'F', '0', 'monitor:operlog:detail', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1042, '日志导出', 500, 4, '#', '', 'F', '0', 'monitor:operlog:export', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1043, '登录查询', 501, 1, '#', '', 'F', '0', 'monitor:logininfor:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1044, '登录删除', 501, 2, '#', '', 'F', '0', 'monitor:logininfor:remove', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1045, '日志导出', 501, 3, '#', '', 'F', '0', 'monitor:logininfor:export', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1046, '账户解锁', 501, 4, '#', '', 'F', '0', 'monitor:logininfor:unlock', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1047, '在线查询', 109, 1, '#', '', 'F', '0', 'monitor:online:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1048, '批量强退', 109, 2, '#', '', 'F', '0', 'monitor:online:batchForceLogout', '#', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1049, '单条强退', 109, 3, '#', '', 'F', '0', 'monitor:online:forceLogout', '#', 'admin', '2018-03-16 11:33:00',
        'leo', '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1050, '任务查询', 110, 1, '#', '', 'F', '0', 'monitor:job:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1051, '任务新增', 110, 2, '#', '', 'F', '0', 'monitor:job:add', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1052, '任务修改', 110, 3, '#', '', 'F', '0', 'monitor:job:edit', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1053, '任务删除', 110, 4, '#', '', 'F', '0', 'monitor:job:remove', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1054, '状态修改', 110, 5, '#', '', 'F', '0', 'monitor:job:changeStatus', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1055, '任务详细', 110, 6, '#', '', 'F', '0', 'monitor:job:detail', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1056, '任务导出', 110, 7, '#', '', 'F', '0', 'monitor:job:export', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1057, '生成查询', 114, 1, '#', '', 'F', '0', 'tool:gen:list', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1058, '生成修改', 114, 2, '#', '', 'F', '0', 'tool:gen:edit', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1059, '生成删除', 114, 3, '#', '', 'F', '0', 'tool:gen:remove', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1060, '预览代码', 114, 4, '#', '', 'F', '0', 'tool:gen:preview', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (1061, '生成代码', 114, 5, '#', '', 'F', '0', 'tool:gen:code', '#', 'admin', '2018-03-16 11:33:00', 'leo',
        '2018-03-16 11:33:00', '');
INSERT INTO sys_menu
VALUES (2000, '命名空间', 2006, 1, '/system/namespace', 'menuItem', 'C', '0', 'system:namespace:view', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2020-08-22 14:01:31', 'HBaseNamespace菜单');
INSERT INTO sys_menu
VALUES (2001, 'namespace查询', 2000, 1, '#', '', 'F', '0', 'system:namespace:list', '#', 'admin', '2018-03-01 00:00:00',
        'leo', '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2002, 'namespace新增', 2000, 2, '#', '', 'F', '0', 'system:namespace:add', '#', 'admin', '2018-03-01 00:00:00',
        'leo', '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2003, 'namespace修改', 2000, 3, '#', '', 'F', '0', 'system:namespace:edit', '#', 'admin', '2018-03-01 00:00:00',
        'leo', '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2004, 'namespace删除', 2000, 4, '#', '', 'F', '0', 'system:namespace:remove', '#', 'admin', '2018-03-01 00:00:00',
        'leo', '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2005, 'namespace导出', 2000, 5, '#', '', 'F', '0', 'system:namespace:export', '#', 'admin', '2018-03-01 00:00:00',
        'leo', '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2006, 'HBase表管理', 0, 1, '#', 'menuItem', 'M', '0', '', 'fa fa-newspaper-o', 'admin', '2020-08-16 07:24:06',
        'admin', '2020-08-16 07:24:38', '');
INSERT INTO sys_menu
VALUES (2007, 'HBase表管理', 2006, 2, '/system/table', 'menuItem', 'C', '0', 'system:table:view', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2020-08-22 14:02:06', 'HBase表菜单');
INSERT INTO sys_menu
VALUES (2008, 'HBase表查询', 2007, 1, '#', '', 'F', '0', 'system:table:list', '#', 'admin', '2018-03-01 00:00:00', 'leo',
        '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2009, 'HBase表新增', 2007, 2, '#', '', 'F', '0', 'system:table:add', '#', 'admin', '2018-03-01 00:00:00', 'leo',
        '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2010, 'HBase表修改', 2007, 3, '#', '', 'F', '0', 'system:table:edit', '#', 'admin', '2018-03-01 00:00:00', 'leo',
        '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2011, 'HBase表删除', 2007, 4, '#', '', 'F', '0', 'system:table:remove', '#', 'admin', '2018-03-01 00:00:00', 'leo',
        '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2012, 'HBase表导出', 2007, 5, '#', '', 'F', '0', 'system:table:export', '#', 'admin', '2018-03-01 00:00:00', 'leo',
        '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2013, '标签管理', 2006, 4, '/system/tag', 'menuItem', 'C', '0', 'system:tag:view', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2020-08-22 14:03:08', 'HBaseTag菜单');
INSERT INTO sys_menu
VALUES (2014, 'HBaseTag查询', 2013, 1, '#', '', 'F', '0', 'system:tag:list', '#', 'admin', '2018-03-01 00:00:00', 'leo',
        '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2015, 'HBaseTag新增', 2013, 2, '#', '', 'F', '0', 'system:tag:add', '#', 'admin', '2018-03-01 00:00:00', 'leo',
        '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2016, 'HBaseTag修改', 2013, 3, '#', '', 'F', '0', 'system:tag:edit', '#', 'admin', '2018-03-01 00:00:00', 'leo',
        '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2018, 'HBaseTag导出', 2013, 5, '#', '', 'F', '0', 'system:tag:export', '#', 'admin', '2018-03-01 00:00:00', 'leo',
        '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2019, '查看详情', 2007, 6, '#', 'menuItem', 'F', '0', 'system:table:detail', '#', 'admin', '2020-08-21 14:54:22',
        '', NULL, '');
INSERT INTO sys_menu
VALUES (2020, '列簇管理', 2006, 3, '/system/family', 'menuItem', 'C', '1', 'system:family:view', '#', 'admin',
        '2018-03-01 00:00:00', 'admin', '2020-08-23 07:03:57', 'HBase Family菜单');
INSERT INTO sys_menu
VALUES (2021, 'HBase列簇', 2020, 1, '#', '', 'F', '0', 'system:family:list', '#', 'admin', '2018-03-01 00:00:00', 'leo',
        '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2022, 'HBase列簇新增', 2020, 2, '#', '', 'F', '0', 'system:family:add', '#', 'admin', '2018-03-01 00:00:00', 'leo',
        '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2023, 'HBase列簇修改', 2020, 3, '#', '', 'F', '0', 'system:family:edit', '#', 'admin', '2018-03-01 00:00:00', 'leo',
        '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2024, 'HBase列簇删除', 2020, 4, '#', '', 'F', '0', 'system:family:remove', '#', 'admin', '2018-03-01 00:00:00',
        'leo', '2018-03-01 00:00:00', '');
INSERT INTO sys_menu
VALUES (2025, 'HBase列簇导出', 2020, 5, '#', '', 'F', '0', 'system:family:export', '#', 'admin', '2018-03-01 00:00:00',
        'leo', '2018-03-01 00:00:00', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS sys_notice;
CREATE TABLE sys_notice
(
    notice_id      int(4)      NOT NULL AUTO_INCREMENT COMMENT '公告ID',
    notice_title   varchar(50) NOT NULL COMMENT '公告标题',
    notice_type    char(1)     NOT NULL COMMENT '公告类型（1通知 2公告）',
    notice_content varchar(2000) DEFAULT NULL COMMENT '公告内容',
    status         char(1)       DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
    create_by      varchar(64)   DEFAULT '' COMMENT '创建者',
    create_time    datetime      DEFAULT NULL COMMENT '创建时间',
    update_by      varchar(64)   DEFAULT '' COMMENT '更新者',
    update_time    datetime      DEFAULT NULL COMMENT '更新时间',
    remark         varchar(255)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (notice_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4 COMMENT ='通知公告表';

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS sys_oper_log;
CREATE TABLE sys_oper_log
(
    oper_id        bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    title          varchar(50)   DEFAULT '' COMMENT '模块标题',
    business_type  int(2)        DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    method         varchar(100)  DEFAULT '' COMMENT '方法名称',
    request_method varchar(10)   DEFAULT '' COMMENT '请求方式',
    operator_type  int(1)        DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    oper_name      varchar(50)   DEFAULT '' COMMENT '操作人员',
    dept_name      varchar(50)   DEFAULT '' COMMENT '部门名称',
    oper_url       varchar(255)  DEFAULT '' COMMENT '请求URL',
    oper_ip        varchar(50)   DEFAULT '' COMMENT '主机地址',
    oper_location  varchar(255)  DEFAULT '' COMMENT '操作地点',
    oper_param     varchar(2000) DEFAULT '' COMMENT '请求参数',
    json_result    varchar(2000) DEFAULT '' COMMENT '返回参数',
    status         int(1)        DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
    error_msg      varchar(2000) DEFAULT '' COMMENT '错误消息',
    oper_time      datetime      DEFAULT NULL COMMENT '操作时间',
    PRIMARY KEY (oper_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 599
  DEFAULT CHARSET = utf8mb4 COMMENT ='操作日志记录';

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS sys_post;
CREATE TABLE sys_post
(
    post_id     bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    post_code   varchar(64) NOT NULL COMMENT '岗位编码',
    post_name   varchar(50) NOT NULL COMMENT '岗位名称',
    post_sort   int(4)      NOT NULL COMMENT '显示顺序',
    status      char(1)     NOT NULL COMMENT '状态（0正常 1停用）',
    create_by   varchar(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime     DEFAULT NULL COMMENT '创建时间',
    update_by   varchar(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime     DEFAULT NULL COMMENT '更新时间',
    remark      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (post_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4 COMMENT ='岗位信息表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
INSERT INTO sys_post
VALUES (1, 'dks', '大数据开发工程师', 1, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-08-11 14:05:55', '');
INSERT INTO sys_post
VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '');
INSERT INTO sys_post
VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '');
INSERT INTO sys_post
VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-08-16 10:54:22', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role
(
    role_id     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    role_name   varchar(30)  NOT NULL COMMENT '角色名称',
    role_key    varchar(100) NOT NULL COMMENT '角色权限字符串',
    role_sort   int(4)       NOT NULL COMMENT '显示顺序',
    data_scope  char(1)      DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    status      char(1)      NOT NULL COMMENT '角色状态（0正常 1停用）',
    del_flag    char(1)      DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    create_by   varchar(64)  DEFAULT '' COMMENT '创建者',
    create_time datetime     DEFAULT NULL COMMENT '创建时间',
    update_by   varchar(64)  DEFAULT '' COMMENT '更新者',
    update_time datetime     DEFAULT NULL COMMENT '更新时间',
    remark      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (role_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO sys_role
VALUES (1, '超级管理员', 'admin', 1, '1', '0', '0', 'admin', '2018-03-16 11:33:00', 'leo', '2018-03-16 11:33:00', '超级管理员');
INSERT INTO sys_role
VALUES (2, '普通角色', 'common', 2, '2', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-08-12 14:51:44', '普通角色');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS sys_role_dept;
CREATE TABLE sys_role_dept
(
    role_id bigint(20) NOT NULL COMMENT '角色ID',
    dept_id bigint(20) NOT NULL COMMENT '部门ID',
    PRIMARY KEY (role_id, dept_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色和部门关联表';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
BEGIN;
INSERT INTO sys_role_dept
VALUES (2, 100);
INSERT INTO sys_role_dept
VALUES (2, 101);
INSERT INTO sys_role_dept
VALUES (2, 105);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu
(
    role_id bigint(20) NOT NULL COMMENT '角色ID',
    menu_id bigint(20) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO sys_role_menu
VALUES (2, 1);
INSERT INTO sys_role_menu
VALUES (2, 2);
INSERT INTO sys_role_menu
VALUES (2, 3);
INSERT INTO sys_role_menu
VALUES (2, 4);
INSERT INTO sys_role_menu
VALUES (2, 107);
INSERT INTO sys_role_menu
VALUES (2, 109);
INSERT INTO sys_role_menu
VALUES (2, 110);
INSERT INTO sys_role_menu
VALUES (2, 111);
INSERT INTO sys_role_menu
VALUES (2, 112);
INSERT INTO sys_role_menu
VALUES (2, 113);
INSERT INTO sys_role_menu
VALUES (2, 114);
INSERT INTO sys_role_menu
VALUES (2, 115);
INSERT INTO sys_role_menu
VALUES (2, 1035);
INSERT INTO sys_role_menu
VALUES (2, 1036);
INSERT INTO sys_role_menu
VALUES (2, 1037);
INSERT INTO sys_role_menu
VALUES (2, 1038);
INSERT INTO sys_role_menu
VALUES (2, 1047);
INSERT INTO sys_role_menu
VALUES (2, 1048);
INSERT INTO sys_role_menu
VALUES (2, 1049);
INSERT INTO sys_role_menu
VALUES (2, 1050);
INSERT INTO sys_role_menu
VALUES (2, 1051);
INSERT INTO sys_role_menu
VALUES (2, 1052);
INSERT INTO sys_role_menu
VALUES (2, 1053);
INSERT INTO sys_role_menu
VALUES (2, 1054);
INSERT INTO sys_role_menu
VALUES (2, 1055);
INSERT INTO sys_role_menu
VALUES (2, 1056);
INSERT INTO sys_role_menu
VALUES (2, 1057);
INSERT INTO sys_role_menu
VALUES (2, 1058);
INSERT INTO sys_role_menu
VALUES (2, 1059);
INSERT INTO sys_role_menu
VALUES (2, 1060);
INSERT INTO sys_role_menu
VALUES (2, 1061);
COMMIT;



-- ----------------------------
-- Table structure for sys_user_online
-- ----------------------------
DROP TABLE IF EXISTS sys_user_online;
CREATE TABLE sys_user_online
(
    sessionId        varchar(50) NOT NULL DEFAULT '' COMMENT '用户会话id',
    login_name       varchar(50)          DEFAULT '' COMMENT '登录账号',
    dept_name        varchar(50)          DEFAULT '' COMMENT '部门名称',
    ipaddr           varchar(50)          DEFAULT '' COMMENT '登录IP地址',
    login_location   varchar(255)         DEFAULT '' COMMENT '登录地点',
    browser          varchar(50)          DEFAULT '' COMMENT '浏览器类型',
    os               varchar(50)          DEFAULT '' COMMENT '操作系统',
    status           varchar(10)          DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
    start_timestamp  datetime             DEFAULT NULL COMMENT 'session创建时间',
    last_access_time datetime             DEFAULT NULL COMMENT 'session最后访问时间',
    expire_time      int(5)               DEFAULT '0' COMMENT '超时时间，单位为分钟',
    PRIMARY KEY (sessionId)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='在线用户记录';

-- ----------------------------
-- Records of sys_user_online
-- ----------------------------
BEGIN;
INSERT INTO sys_user_online
VALUES ('7c89d0d1-d577-41d9-93bf-e8531ef34f34', 'admin', '研发部门', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', 'on_line',
        '2020-08-23 16:21:12', '2020-08-23 17:55:44', 1800000);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS sys_user_post;
CREATE TABLE sys_user_post
(
    user_id bigint(20) NOT NULL COMMENT '用户ID',
    post_id bigint(20) NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (user_id, post_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户与岗位关联表';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
BEGIN;
INSERT INTO sys_user_post
VALUES (1, 1);
INSERT INTO sys_user_post
VALUES (2, 2);
INSERT INTO sys_user_post
VALUES (100, 1);
INSERT INTO sys_user_post
VALUES (100, 3);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role
(
    user_id bigint(20) NOT NULL COMMENT '用户ID',
    role_id bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO sys_user_role
VALUES (1, 1);
INSERT INTO sys_user_role
VALUES (2, 2);
INSERT INTO sys_user_role
VALUES (100, 2);
COMMIT;

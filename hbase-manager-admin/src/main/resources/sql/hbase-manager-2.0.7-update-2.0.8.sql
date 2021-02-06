SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for sys_user_hbase_table
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_hbase_table`;
CREATE TABLE `sys_user_hbase_table`
(
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `cluster_alias` varchar(200)  NOT NULL COMMENT '集群标识符号',
    `table_id` varchar(200) NOT NULL COMMENT '表名加密后的ID',
    `table_name` varchar(500) NOT NULL COMMENT '表名',
    `namespace_name` varchar(500) NOT NULL COMMENT '命名空间',
    PRIMARY KEY (`user_id`, `cluster_alias`, `table_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户和HBase表的关联表';

SET FOREIGN_KEY_CHECKS = 1;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for sys_user_hbase_table
-- ----------------------------
DROP TABLE IF EXISTS `sys_hbase_cluster`;
CREATE TABLE `sys_hbase_cluster`
(
    `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `cluster_id` varchar(50)  NOT NULL COMMENT '集群ID',
    `cluster_name` varchar(100) DEFAULT '' COMMENT '集群的简短中文名称',
    `cluster_desc` varchar(200) DEFAULT '' COMMENT '集群的用途描述',
    `cluster_config` text NOT NULL COMMENT '集群配置',
    `state` varchar(20) NOT NULL DEFAULT 'online' COMMENT '集群状态',
    UNIQUE KEY `unique_cluster_id` (`cluster_id`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='集群信息和配置表';

INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2032, '集群信息管理', 2006, 0, '/system/cluster', 'menuItem', 'C', '0', 'hbase:cluster:view', '#', 'admin', '2023-07-09 12:21:51', 'admin', '2023-07-09 12:28:07', '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2033, '集群信息查询', 2032, 1, '#', 'menuItem', 'F', '0', 'hbase:cluster:list', '#', 'admin', '2023-07-09 12:23:51', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2034, '集群信息新增', 2032, 2, '#', 'menuItem', 'F', '0', 'hbase:cluster:add', '#', 'admin', '2023-07-09 12:25:39', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2035, '集群信息更新', 2032, 3, '#', 'menuItem', 'F', '0', 'hbase:cluster:edit', '#', 'admin', '2023-07-09 12:26:15', '', NULL, '');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2036, 'HBaseShell', 2006, 0, '/system/shell', 'menuItem', 'C', '0', 'hbase:shell:view', '#', 'admin', '2023-07-10 12:38:48', '', NULL, '');
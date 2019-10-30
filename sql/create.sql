
-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` int(11) DEFAULT '0' COMMENT '父部门id 0表示根目录',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `seq` tinyint(3) unsigned DEFAULT '0' COMMENT '现实排序 最大支持 255',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(3) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` tinyint(3) DEFAULT '0' COMMENT '逻辑删除标志（0代表存在 -1代表删除）',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `last_update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` int(11) DEFAULT '0' COMMENT '父菜单ID',
  `seq` tinyint(3) unsigned DEFAULT '0' COMMENT '现实排序 最大支持 255',
  `url` varchar(200) DEFAULT '#' COMMENT '请求地址',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单,B 按钮）',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `last_update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `seq` tinyint(3) unsigned DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `last_update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和部门关联表';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `dept_id` int(11) DEFAULT NULL COMMENT '所属部门编号',
  `login_account` varchar(30) NOT NULL COMMENT '账号',
  `login_pwd` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(6) DEFAULT NULL COMMENT '盐值',
  `email` varchar(100) DEFAULT NULL COMMENT '邮件地址',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_ip` varchar(30) DEFAULT NULL COMMENT '最后登录IP',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
  `status` tinyint(3) DEFAULT '0' COMMENT '0可用 -1表示禁用 -2表示逻辑删除',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `super_user` tinyint(11) DEFAULT '0' COMMENT '超级管理员0否1是',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uniq_login` (`login_account`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------

INSERT INTO `sys_user` VALUES (1, '管理员', NULL, 'admin', '$2a$10$z78yiN0qOS8IyN4tE5617eXCCjNmZNZSabgB201hVDNUOJdC8WMEu', NULL, NULL, '2019-10-23 10:41:25', NULL, 0, 0, NULL, NULL, NULL, 1, 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

SET FOREIGN_KEY_CHECKS = 1;

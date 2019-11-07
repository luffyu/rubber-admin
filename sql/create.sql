/*
 Navicat Premium Data Transfer

 Source Server         : docker_local_3308
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3308
 Source Schema         : rubber_admin

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 07/11/2019 16:53:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` int(11) DEFAULT '0' COMMENT '父部门id 0表示根目录',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `seq` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '现实排序 最大支持 255',
  `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(3) DEFAULT '0' COMMENT '部门状态（0正常 -1停用）',
  `del_flag` tinyint(3) DEFAULT '0' COMMENT '逻辑删除标志（0代表存在 -1代表删除）',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (1, 0, '云技术部门', 0, '路飞', '1999', 'luffyu@onepiece.com', 0, 0, 1, '2019-11-07 16:52:18', 1, '2019-11-07 16:52:20', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` int(11) DEFAULT '0' COMMENT '父菜单ID',
  `seq` tinyint(3) unsigned DEFAULT '0' COMMENT '现实排序 最大支持 255',
  `url` varchar(200) DEFAULT '#' COMMENT '请求地址',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单,B 按钮）',
  `auth_key` varchar(50) DEFAULT NULL COMMENT '权限标示key',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态（0正常 -1表示停用）',
  `del_flag` tinyint(3) DEFAULT '0' COMMENT '是否删除 0表示没有 -1表示被删除',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 0, '#', 'M', NULL, 0, 0, '#', NULL, NULL, NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (2, '菜单管理', 1, 1, '#', 'C', NULL, 0, 0, '#', NULL, NULL, NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (3, '角色管理', 1, 2, '#', 'C', NULL, 0, 0, '#', NULL, NULL, NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (4, '用户管理', 1, 3, '#', 'C', NULL, 0, 0, '#', NULL, NULL, NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (6, '部门管理', 1, 0, '#', 'C', NULL, 0, 0, '#', 1, '2019-11-05 18:05:59', 1, '2019-11-05 18:12:45', '这是一个词而是');
COMMIT;

-- ----------------------------
-- Table structure for sys_permission_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_dict`;
CREATE TABLE `sys_permission_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `dict_key` varchar(100) NOT NULL COMMENT '字典关键值',
  `dict_name` varchar(50) DEFAULT NULL COMMENT '字典名称',
  `dict_value` varchar(255) DEFAULT NULL COMMENT '字典值',
  `dict_type` varchar(100) DEFAULT NULL COMMENT '字典类型',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态（0正常 -1停用）',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_pri_key` (`dict_key`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='权限字典名称';

-- ----------------------------
-- Records of sys_permission_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission_dict` VALUES (1, 'select', '查询', 'list,query,get,find,page,info,download,export,select', 'basic_unit', 0, NULL, NULL, NULL, NULL, '');
INSERT INTO `sys_permission_dict` VALUES (2, 'edit', '编辑', 'edit,update,modify,mod', 'basic_unit', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission_dict` VALUES (3, 'add', '新增', 'add,save,install,saving,copy', 'basic_unit', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission_dict` VALUES (4, 'delete', '删除', 'del,delete,remove,rf', 'basic_unit', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission_dict` VALUES (5, 'verify', '审核', 'auth,online,offline,verify', 'basic_unit', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission_dict` VALUES (6, 'upload', '上传', 'upload,import', 'basic_unit', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission_dict` VALUES (7, 'download', '下载', 'down,download,export', 'basic_unit', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission_dict` VALUES (8, 'menu', '菜单管理', 'menu', 'basic_module', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission_dict` VALUES (9, 'permission', '权限管理', 'permission', 'basic_module', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission_dict` VALUES (10, 'role', '角色管理', 'role', 'basic_module', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission_dict` VALUES (11, 'user', '用户管理', 'user', 'basic_module', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission_dict` VALUES (12, 'dect', '部门管理', 'dect', 'basic_module', 0, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `seq` tinyint(3) unsigned DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态（0正常 -1停用）',
  `del_flag` tinyint(3) DEFAULT '0' COMMENT '删除标志（0代表存在 -1代表删除）',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '系统管理员', 'admin', 1, 0, 0, 1, '2019-11-07 16:50:51', 1, '2019-11-07 16:50:55', '备注');
INSERT INTO `sys_role` VALUES (2, '开发人员', 'developers', 1, 0, 0, 1, '2019-11-05 17:18:26', 1, '2019-11-05 17:36:34', '开发人员测-2试');
COMMIT;

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
-- Records of sys_role_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_dept` VALUES (1, 1);
COMMIT;

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
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 3);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `role_id` varchar(100) DEFAULT NULL COMMENT '角色id',
  `module` varchar(50) DEFAULT NULL COMMENT '权限模块key',
  `unit_array` varchar(255) DEFAULT NULL COMMENT '权限单元操作key数组',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='角色权限列表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES (23, '1', 'menu', 'add,select,edit,delete', 1, '2019-11-07 16:26:04', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `dept_id` int(11) DEFAULT NULL COMMENT '所属部门编号',
  `login_account` varchar(50) NOT NULL COMMENT '账号',
  `login_pwd` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(50) DEFAULT NULL COMMENT '盐值',
  `email` varchar(100) DEFAULT NULL COMMENT '邮件地址',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像信息',
  `sex` tinyint(3) DEFAULT NULL COMMENT '性别 0表示女 1表示男 2表示未知 ',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_ip` varchar(30) DEFAULT NULL COMMENT '最后登录IP',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
  `status` tinyint(3) DEFAULT '0' COMMENT '账户状态 0可用 -1表示禁用',
  `del_flag` tinyint(3) DEFAULT '0' COMMENT '删除标志 0表示存在  -1表示逻辑删除',
  `super_user` tinyint(11) DEFAULT '0' COMMENT '超级管理员0否1是',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uniq_login` (`login_account`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, '超级管理员', NULL, 'admin', '$2a$10$DddJsIctDNwGHiucMB90HegUa4iX.oszYhfQe8hXQ1nCANZfMeBeW', '$2a$04$FKmd8XmZ4xBp0vlTPAZ1NO', NULL, NULL, NULL, NULL, '2019-11-07 16:36:08', '0:0:0:0:0:0:0:1', 42, 0, 0, 1, NULL, NULL, NULL, NULL, '2019-11-07 16:36:08', 36);
INSERT INTO `sys_user` VALUES (2, '路飞', NULL, 'luffyu', '$2a$10$DddJsIctDNwGHiucMB90HegUa4iX.oszYhfQe8hXQ1nCANZfMeBeW', '$2a$04$FKmd8XmZ4xBp0vlTPAZ1NO', NULL, NULL, NULL, NULL, '2019-11-07 16:35:16', '0:0:0:0:0:0:0:1', 41, 0, 0, 0, NULL, NULL, NULL, 1, '2019-11-07 16:35:21', 41);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (2, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

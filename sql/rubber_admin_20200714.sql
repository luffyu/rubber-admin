/*
 Navicat Premium Data Transfer

 Source Server         : 本地电脑
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : rubber_admin

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 14/07/2020 14:59:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_group_config
-- ----------------------------
DROP TABLE IF EXISTS `auth_group_config`;
CREATE TABLE `auth_group_config` (
  `group_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `group_key` varchar(50) NOT NULL DEFAULT '' COMMENT '组关键字',
  `group_name` varchar(50) NOT NULL DEFAULT '' COMMENT '组名称',
  `group_type` varchar(50) NOT NULL DEFAULT '' COMMENT '组类型 ',
  `group_member` varchar(500) DEFAULT '' COMMENT '组成员 多个组成员用英文逗号隔开',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态（0正常 -1停用）',
  `update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`group_id`),
  KEY `idx_auth_group` (`group_key`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='权限族群配置表';

-- ----------------------------
-- Records of auth_group_config
-- ----------------------------
BEGIN;
INSERT INTO `auth_group_config` VALUES (1, 'add', '增加', 'option', 'add,save,install,saving,copy', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (2, 'delete', '删除', 'option', 'del,delete,remove,rf', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (3, 'update', '修改', 'option', 'edit,update,modify,mod', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (4, 'select', '查询', 'option', 'list,query,get,find,page,info,download,export,select,read,tree', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (5, 'menu', '菜单业务', 'apply', 'menu', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (6, 'permission', '权限业务', 'apply', 'permission', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (7, 'role', '角色业务', 'apply', 'role', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (8, 'user', '用户业务', 'apply', 'user', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (9, 'dept', '部门业务', 'apply', 'dept', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (10, 'monitor', '监控业务', 'apply', 'monitor', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (11, 'u', '用户基础业务', 'apply', 'u,login', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (13, 'auth', '权限业务', 'apply', 'auth', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (14, 'other', '其他业务', 'apply', 'error', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (15, 'test', '测试业务', 'apply', 'test', 0, NULL, NULL, NULL);
INSERT INTO `auth_group_config` VALUES (16, 'unknown', '其他操作', 'option', 'error,login', 0, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for auth_group_menu
-- ----------------------------
DROP TABLE IF EXISTS `auth_group_menu`;
CREATE TABLE `auth_group_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `menu_id` varchar(50) NOT NULL DEFAULT '' COMMENT '组关键字',
  `option_key` varchar(50) NOT NULL DEFAULT '' COMMENT '操作组的key',
  `related_apply` varchar(255) NOT NULL DEFAULT '' COMMENT '相关应用关键字，多个关键字用英文,隔开',
  PRIMARY KEY (`id`),
  KEY `idx_meun` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='权限族和菜单管理表';

-- ----------------------------
-- Records of auth_group_menu
-- ----------------------------
BEGIN;
INSERT INTO `auth_group_menu` VALUES (22, '15', 'add', 'role,menu');
INSERT INTO `auth_group_menu` VALUES (23, '15', 'tree', 'dept');
INSERT INTO `auth_group_menu` VALUES (28, '16', 'add', 'auth');
INSERT INTO `auth_group_menu` VALUES (29, '16', 'update', 'auth');
INSERT INTO `auth_group_menu` VALUES (30, '16', 'select', 'auth');
INSERT INTO `auth_group_menu` VALUES (31, '16', 'delete', 'auth');
INSERT INTO `auth_group_menu` VALUES (36, '3', 'add', 'role');
INSERT INTO `auth_group_menu` VALUES (37, '3', 'update', 'role');
INSERT INTO `auth_group_menu` VALUES (38, '3', 'select', 'role,auth,menu');
INSERT INTO `auth_group_menu` VALUES (39, '3', 'delete', 'role');
INSERT INTO `auth_group_menu` VALUES (40, '4', 'add', 'user');
INSERT INTO `auth_group_menu` VALUES (41, '4', 'update', 'user');
INSERT INTO `auth_group_menu` VALUES (42, '4', 'select', 'role,user');
INSERT INTO `auth_group_menu` VALUES (43, '4', 'delete', 'user');
INSERT INTO `auth_group_menu` VALUES (44, '14', 'add', 'auth');
INSERT INTO `auth_group_menu` VALUES (45, '14', 'update', 'auth');
INSERT INTO `auth_group_menu` VALUES (46, '14', 'select', 'auth');
INSERT INTO `auth_group_menu` VALUES (47, '14', 'delete', 'auth');
INSERT INTO `auth_group_menu` VALUES (48, '2', 'add', 'menu');
INSERT INTO `auth_group_menu` VALUES (49, '2', 'update', 'menu');
INSERT INTO `auth_group_menu` VALUES (50, '2', 'select', 'auth,menu');
INSERT INTO `auth_group_menu` VALUES (51, '2', 'delete', 'menu');
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` int(11) DEFAULT '0' COMMENT '父部门id 0表示根目录',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `seq` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '现实排序 最大支持 255',
  `leader` varchar(20) NOT NULL COMMENT '负责人',
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (9, 0, '这是一个测试', 1, '余', '18617181447', NULL, 0, 0, 1, '2020-01-31 14:35:44', 1, '2020-01-31 15:32:23', '');
INSERT INTO `sys_dept` VALUES (11, 0, '草帽海贼团', 3, '余', '18617181447', NULL, 0, 0, 1, '2020-01-31 15:29:14', 1, '2020-01-31 15:29:53', '');
INSERT INTO `sys_dept` VALUES (12, 11, '一分队', 1, '余1', '18617181447', NULL, 0, 0, 1, '2020-01-31 15:30:37', 1, '2020-01-31 15:30:37', '');
INSERT INTO `sys_dept` VALUES (13, 11, '二分队', 2, '余rap', '18617181447', NULL, 0, 0, 1, '2020-01-31 15:30:54', 1, '2020-01-31 15:30:54', '');
INSERT INTO `sys_dept` VALUES (15, 9, '哈哈哈-1', 2, 'test', '18617181447', '91991@qq.com', 0, 0, 1, '2020-04-11 15:34:09', 1, '2020-07-13 15:41:06', '');
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 0, '#', 'M', NULL, 0, 0, 'el-icon-setting', NULL, NULL, 1, '2020-07-10 17:55:45', '');
INSERT INTO `sys_menu` VALUES (2, '菜单管理', 1, 7, '/sys/menu-view', 'C', NULL, 0, 0, '#', NULL, NULL, 1, '2020-04-10 20:15:19', '');
INSERT INTO `sys_menu` VALUES (3, '角色管理', 1, 6, '/sys/role-view', 'C', NULL, 0, 0, 'tag', NULL, NULL, 1, '2020-04-05 15:59:57', '');
INSERT INTO `sys_menu` VALUES (4, '用户管理', 1, 4, '/sys/user-view', 'C', NULL, 0, 0, '#', NULL, NULL, 1, '2020-04-05 16:00:22', '');
INSERT INTO `sys_menu` VALUES (6, '部门管理', 1, 3, '/sys/dept-view', 'C', NULL, 0, 0, '#', 1, '2019-11-05 18:05:59', 1, '2020-02-01 13:27:08', '这是一个词而是');
INSERT INTO `sys_menu` VALUES (14, '业务列表', 17, 0, '/sys/authorize-view', 'C', NULL, 0, 0, '#', 1, '2020-03-13 15:04:31', 1, '2020-04-05 16:00:51', '');
INSERT INTO `sys_menu` VALUES (16, '权限配置', 17, 11, '/sys/authorize-config', 'C', NULL, 0, 0, '', 1, '2020-03-19 10:51:03', 1, '2020-03-25 15:55:45', '');
INSERT INTO `sys_menu` VALUES (17, '权限管理', 1, 11, '#', 'M', NULL, 0, 0, '#', 1, '2020-03-25 15:48:57', 1, '2020-03-25 15:49:37', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
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
INSERT INTO `sys_role` VALUES (1, '系统管理员', 1, 0, 0, 1, '2019-11-07 16:50:51', 1, '2020-04-05 17:51:54', '备注');
INSERT INTO `sys_role` VALUES (2, '开发人员', 1, 0, 0, 1, '2019-11-05 17:18:26', 1, '2020-07-13 16:11:48', '开发人员测-2试');
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
  `option_key` varchar(255) DEFAULT NULL COMMENT '当前角色可以操作的菜单key值',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (1, 1, '');
INSERT INTO `sys_role_menu` VALUES (1, 2, 'add,select,update,delete');
INSERT INTO `sys_role_menu` VALUES (1, 3, 'add,select,update,delete');
INSERT INTO `sys_role_menu` VALUES (1, 4, 'add,select,update,delete');
INSERT INTO `sys_role_menu` VALUES (1, 6, '');
INSERT INTO `sys_role_menu` VALUES (1, 14, 'add,select,update,delete');
INSERT INTO `sys_role_menu` VALUES (1, 16, 'add,select,update,delete');
INSERT INTO `sys_role_menu` VALUES (1, 17, '');
INSERT INTO `sys_role_menu` VALUES (2, 1, '');
INSERT INTO `sys_role_menu` VALUES (2, 2, 'add,select,update,delete');
INSERT INTO `sys_role_menu` VALUES (2, 4, 'select');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, '超级管理员', NULL, 'admin', '$2a$10$DddJsIctDNwGHiucMB90HegUa4iX.oszYhfQe8hXQ1nCANZfMeBeW', '$2a$04$FKmd8XmZ4xBp0vlTPAZ1NO', NULL, NULL, NULL, NULL, '2020-07-13 18:20:16', '127.0.0.1', 188, 0, 0, 1, NULL, NULL, NULL, NULL, '2020-07-13 18:20:16', 182);
INSERT INTO `sys_user` VALUES (2, '路飞-2', 12, 'luffyu', '$2a$10$DddJsIctDNwGHiucMB90HegUa4iX.oszYhfQe8hXQ1nCANZfMeBeW', '$2a$04$FKmd8XmZ4xBp0vlTPAZ1NO', 'qw', 'qw', NULL, 1, '2020-07-13 16:10:40', '127.0.0.1', 49, 0, 0, 0, NULL, NULL, NULL, 1, '2020-07-13 16:10:40', 49);
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
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (7, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

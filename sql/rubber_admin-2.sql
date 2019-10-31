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

 Date: 31/10/2019 21:25:34
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
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `seq` tinyint(3) unsigned DEFAULT '0' COMMENT '现实排序 最大支持 255',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

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
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- ----------------------------
-- Table structure for sys_privilege_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_privilege_dict`;
CREATE TABLE `sys_privilege_dict` (
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='权限字典名称';

-- ----------------------------
-- Records of sys_privilege_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_privilege_dict` VALUES (1, 'select', '查询', 'list,query,get,find,page,info,download,export,select', 'basic_unit', 0, NULL, NULL, NULL, NULL, '');
INSERT INTO `sys_privilege_dict` VALUES (2, 'edit', '编辑', 'edit,update,modify,mod', 'basic_unit', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege_dict` VALUES (3, 'add', '新增', 'add,save,install,saving,copy', 'basic_unit', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege_dict` VALUES (4, 'delect', '删除', 'del,delete,remove,rf', 'basic_unit', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege_dict` VALUES (5, 'verify', '审核', 'auth,online,offline,verify', 'basic_unit', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege_dict` VALUES (6, 'upload', '上传', 'upload,import', 'basic_unit', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege_dict` VALUES (7, 'download', '下载', 'down,download,export', 'basic_unit', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege_dict` VALUES (8, 'active', '活动管理', 'active', 'basic_module', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege_dict` VALUES (9, 'award', '奖品管理', 'award', 'basic_module', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege_dict` VALUES (10, 'task', '任务管理', 'task', 'basic_module', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege_dict` VALUES (11, 'sys-menu', '菜单管理', 'sys-menu,menu', 'basic_module', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege_dict` VALUES (12, 'test', '测试管理', 'test', 'basic_module', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege_dict` VALUES (13, 'sys-privilege', '权限管理', 'sys-privilege', 'basic_module', 0, NULL, NULL, NULL, NULL, NULL);
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
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 -1代表删除）',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
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
-- Table structure for sys_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_privilege`;
CREATE TABLE `sys_role_privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `permission` tinyint(3) unsigned DEFAULT '0' COMMENT '权限字段',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态（0正常 -1停用）',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_role` (`role_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限列表';

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
  `salt` varchar(6) DEFAULT NULL COMMENT '盐值',
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
  UNIQUE KEY `uniq_login` (`login_account`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, '系统管理员', NULL, 'admin', '$2a$10$rNY0Tb3p2gcNc69IXUJ5B.jZhyB84eI5sEyTFllJOm3YueJ7H9W3.', '798835', NULL, NULL, NULL, NULL, '2019-10-31 20:57:24', '0:0:0:0:0:0:0:1', 18, 0, 0, 0, NULL, NULL, NULL, NULL, NULL, 12);
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

SET FOREIGN_KEY_CHECKS = 1;

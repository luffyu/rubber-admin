
CREATE TABLE `auth_group_config` (
  `group_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `group_key` VARCHAR (50) NOT NULL DEFAULT '' COMMENT '组关键字',
  `group_name` VARCHAR (50) NOT NULL DEFAULT '' COMMENT '组名称',
  `group_type` VARCHAR (50) NOT NULL DEFAULT '' COMMENT '组类型 ',
  `group_member` VARCHAR (500) DEFAULT '' COMMENT '组成员 多个组成员用英文逗号隔开',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态（0正常 -1停用）',
  `update_by` int(11) DEFAULT NULL COMMENT '最后一次更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '最好一个更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`group_id`),
  UNION KEY `idx_auth_group` (`group_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='权限族群配置表';


CREATE TABLE `auth_group_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `menu_id` VARCHAR (50) NOT NULL DEFAULT '' COMMENT '组关键字',
  `controller_key` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'controller组名称',
  `mapping_key` VARCHAR (255) NOT NULL DEFAULT '' COMMENT 'mapping组名称',
  PRIMARY KEY (`id`),
  KEY `idx_meun` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限族和菜单管理表';

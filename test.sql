/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50619
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2018-06-22 18:02:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `permissions`
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT '0' COMMENT '父级权限id',
  `permission_name` varchar(20) DEFAULT NULL COMMENT '权限名称',
  `permission_url` varchar(100) DEFAULT NULL COMMENT '权限路径',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions` VALUES ('1', '0', '角色管理', '');
INSERT INTO `permissions` VALUES ('2', '0', '用户管理', '');
INSERT INTO `permissions` VALUES ('3', '0', '订单管理', '');
INSERT INTO `permissions` VALUES ('4', '1', '角色列表', '/user/roleManage');
INSERT INTO `permissions` VALUES ('5', '2', '用户列表', '/user/userManage');
INSERT INTO `permissions` VALUES ('6', '3', '订单列表', '/orderManage');

-- ----------------------------
-- Table structure for `roles`
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', '超级管理员');
INSERT INTO `roles` VALUES ('2', '用户管理员');
INSERT INTO `roles` VALUES ('3', '订单管理员');

-- ----------------------------
-- Table structure for `roles_permissions`
-- ----------------------------
DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE `roles_permissions` (
  `role_permission_id` int(11) NOT NULL DEFAULT '0',
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roles_permissions
-- ----------------------------
INSERT INTO `roles_permissions` VALUES ('1', '1', '1');
INSERT INTO `roles_permissions` VALUES ('2', '1', '2');
INSERT INTO `roles_permissions` VALUES ('3', '1', '3');
INSERT INTO `roles_permissions` VALUES ('4', '2', '2');
INSERT INTO `roles_permissions` VALUES ('5', '3', '3');
INSERT INTO `roles_permissions` VALUES ('6', '1', '4');
INSERT INTO `roles_permissions` VALUES ('7', '1', '5');
INSERT INTO `roles_permissions` VALUES ('8', '1', '6');
INSERT INTO `roles_permissions` VALUES ('9', '2', '5');
INSERT INTO `roles_permissions` VALUES ('10', '3', '6');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_account` varchar(20) DEFAULT NULL COMMENT '用户登录账号',
  `user_name` varchar(20) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uq_user_account` (`user_account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'huangtl', 'huangtl', '4fee9ec2f8e594545803c14d49413a0d');
INSERT INTO `users` VALUES ('2', 'userAdmin', 'userAdmin', 'a810a3838d7cec049601b2db2f201e0c');
INSERT INTO `users` VALUES ('3', 'orderAdmin', 'orderAdmin', '2504a876a757c3964ece88e769d8d4cb');
INSERT INTO `users` VALUES ('4', 'test', 'test', '123456');
INSERT INTO `users` VALUES ('5', 'super', 'super', '2bffb3bbd66a9b469723cbfe03a521d0');
INSERT INTO `users` VALUES ('6', 'admin', 'admin', 'a66abb5684c45962d887564f08346e8d');
INSERT INTO `users` VALUES ('13', 'xiaoming', 'xiaoming', '48e231e66ff8943db0f6d2b6cb6536d2');
INSERT INTO `users` VALUES ('31', 'yinghua', '一数樱花落', '0260af905c0f02ef4fc44c6e6df58f6b');
INSERT INTO `users` VALUES ('32', 'xxx', '一数樱花落', '5c18216aeb19c1a79f821902d846067b');

-- ----------------------------
-- Table structure for `user_roles`
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_role_id` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES ('1', '1', '1');
INSERT INTO `user_roles` VALUES ('2', '2', '2');
INSERT INTO `user_roles` VALUES ('3', '3', '3');
INSERT INTO `user_roles` VALUES ('4', '4', '1');

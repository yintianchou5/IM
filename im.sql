/*
Navicat MySQL Data Transfer

Source Server         : dzqtest
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : im

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2019-07-30 17:12:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(20) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `userPwd` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'test', '1234');
INSERT INTO `users` VALUES ('2', 'zhangsan', '1234');
INSERT INTO `users` VALUES ('3', 'abcd', '1234');

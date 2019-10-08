/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50553
 Source Host           : localhost:3306
 Source Schema         : sqlonlinejudge

 Target Server Type    : MySQL
 Target Server Version : 50553
 File Encoding         : 65001

 Date: 08/10/2019 22:19:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_base
-- ----------------------------
DROP TABLE IF EXISTS `data_base`;
CREATE TABLE `data_base`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据库ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '数据库名称',
  `create_table` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '建表语句',
  `test_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据插入语句',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '题目ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目标题',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目描述',
  `sample_output` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '样例输出',
  `hint` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '提示',
  `answer` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '答案',
  `solved` int(11) NOT NULL DEFAULT 0 COMMENT '通过数',
  `submit` int(11) NOT NULL DEFAULT 0 COMMENT '提交数',
  `true_result` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '正确输出',
  `database_id` int(11) NOT NULL DEFAULT 0 COMMENT '数据库ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_problem_database`(`database_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for solution
-- ----------------------------
DROP TABLE IF EXISTS `solution`;
CREATE TABLE `solution`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '解答ID',
  `uid` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `pid` int(11) NULL DEFAULT NULL COMMENT '题目ID',
  `source_code` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '源代码',
  `submit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `run_error` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '错误信息',
  `result` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `pid`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` bit(1) NULL DEFAULT b'1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
INSERT INTO `sys_admin` VALUES (1, 'admin', '8315af46b6d38ebdaec05ec05393edd3', 'NOOQnVyN2NI2GD4q/V28', b'1');
INSERT INTO `sys_admin` VALUES (2, 'sgh', '7078553a2476e9c86ea74234145febef', 'yK5o/VZdWdpJplm+5VVt', b'1');
INSERT INTO `sys_admin` VALUES (4, 'xdh', '5e831f34f03ac6512acac21206af848c', 's4cqIwFmuoC0OJejOQRB', b'1');
INSERT INTO `sys_admin` VALUES (5, 'ljq', 'a5e601133d00af2637b71b739e28b9ed', 'l4jipX7kVnrYr1xZhn8L', b'1');

-- ----------------------------
-- Table structure for sys_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `a_id` int(11) NOT NULL DEFAULT 0,
  `r_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_admin_role
-- ----------------------------
INSERT INTO `sys_admin_role` VALUES (1, 1, 1);
INSERT INTO `sys_admin_role` VALUES (2, 2, 2);
INSERT INTO `sys_admin_role` VALUES (3, 4, 2);
INSERT INTO `sys_admin_role` VALUES (4, 5, 2);
INSERT INTO `sys_admin_role` VALUES (5, 6, 2);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
  `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '总权限', '*:*');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
  `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_name`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'admin', '总管理员');
INSERT INTO `sys_role` VALUES (2, 'teacher', '教师');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `r_id` int(11) NOT NULL DEFAULT 0,
  `p_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '用户名',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱地址',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码盐',
  `submit` int(11) NOT NULL DEFAULT 0 COMMENT '提交数',
  `solved` int(11) NOT NULL DEFAULT 0 COMMENT '通过数',
  `avatar` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'default.jpg' COMMENT '头像',
  `status` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态,0未激活，1正常，2锁定',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'user_sgh', 'sgh@qq.com', '7078553a2476e9c86ea74234145febef', 'yK5o/VZdWdpJplm+5VVt', 0, 0, 'default.jpg', b'1');
INSERT INTO `sys_user` VALUES (2, 'user_xdh', 'xdh@qq.com', 'c51f9c7eba223c36a9e79aef5dbe3dd1', 'stFp0Nc4hYuiLaiN2aNe', 0, 0, 'default.jpg', b'1');
INSERT INTO `sys_user` VALUES (3, 'user_ljq', 'ljq@qq.com', 'd1abfc1dcd61244bed23b54ad3e67142', 'u5EUoEIF0yerJVoQLye8', 0, 0, 'default.jpg', b'1');

-- ----------------------------
-- Table structure for user_problem
-- ----------------------------
DROP TABLE IF EXISTS `user_problem`;
CREATE TABLE `user_problem`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户题库ID',
  `uid` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `pid` int(11) NULL DEFAULT NULL COMMENT '题目ID',
  `state` bit(1) NULL DEFAULT NULL COMMENT '用户题目状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

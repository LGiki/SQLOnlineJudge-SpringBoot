/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : sqlonlinejudge

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 08/10/2019 19:18:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_base
-- ----------------------------
DROP TABLE IF EXISTS `data_base`;
CREATE TABLE `data_base`
(
    `id`           int(11)                                                      NOT NULL AUTO_INCREMENT COMMENT '数据库ID',
    `name`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '数据库名称',
    `create_table` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci        NULL COMMENT '建表语句',
    `test_data`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci        NULL COMMENT '数据插入语句',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of data_base
-- ----------------------------
INSERT INTO `data_base`
VALUES (4, '图书馆',
        'CREATE TABLE `employees` (\n`emp_no` int(11) NOT NULL,\n`birth_date` date NOT NULL,\n`first_name` varchar(14) NOT NULL,\n`last_name` varchar(16) NOT NULL,\n`gender` char(1) NOT NULL,\n`hire_date` date NOT NULL,\nPRIMARY KEY (`emp_no`));',
        'INSERT INTO employees VALUES(10001,\'1953-09-02\',\'Georgi\',\'Facello\',\'M\',\'1986-06-26\'); INSERT INTO employees VALUES(10002,\'1964-06-02\',\'Bezalel\',\'Simmel\',\'F\',\'1985-11-21\'); INSERT INTO employees VALUES(10003,\'1959-12-03\',\'Parto\',\'Bamford\',\'M\',\'1986-08-28\'); INSERT INTO employees VALUES(10004,\'1954-05-01\',\'Chirstian\',\'Koblick\',\'M\',\'1986-12-01\'); INSERT INTO employees VALUES(10005,\'1955-01-21\',\'Kyoichi\',\'Maliniak\',\'M\',\'1989-09-12\'); INSERT INTO employees VALUES(10006,\'1953-04-20\',\'Anneke\',\'Preusig\',\'F\',\'1989-06-02\'); INSERT INTO employees VALUES(10007,\'1957-05-23\',\'Tzvetan\',\'Zielinski\',\'F\',\'1989-02-10\'); INSERT INTO employees VALUES(10008,\'1958-02-19\',\'Saniya\',\'Kalloufi\',\'M\',\'1994-09-15\'); INSERT INTO employees VALUES(10009,\'1952-04-19\',\'Sumant\',\'Peac\',\'F\',\'1985-02-18\'); INSERT INTO employees VALUES(10010,\'1963-06-01\',\'Duangkaew\',\'Piveteau\',\'F\',\'1989-08-24\'); INSERT INTO employees VALUES(10011,\'1953-11-07\',\'Mary\',\'Sluis\',\'F\',\'1990-01-22\');');
INSERT INTO `data_base`
VALUES (9, '图书馆2',
        'CREATE TABLE `employees` (\n`emp_no` int(11) NOT NULL,\n`birth_date` date NOT NULL,\n`first_name` varchar(14) NOT NULL,\n`last_name` varchar(16) NOT NULL,\n`gender` char(1) NOT NULL,\n`hire_date` date NOT NULL,\nPRIMARY KEY (`emp_no`));',
        'INSERT INTO employees VALUES(10001,\'1953-09-02\',\'Georgi\',\'Facello\',\'M\',\'1986-06-26\'); INSERT INTO employees VALUES(10002,\'1964-06-02\',\'Bezalel\',\'Simmel\',\'F\',\'1985-11-21\'); INSERT INTO employees VALUES(10003,\'1959-12-03\',\'Parto\',\'Bamford\',\'M\',\'1986-08-28\'); INSERT INTO employees VALUES(10004,\'1954-05-01\',\'Chirstian\',\'Koblick\',\'M\',\'1986-12-01\'); INSERT INTO employees VALUES(10005,\'1955-01-21\',\'Kyoichi\',\'Maliniak\',\'M\',\'1989-09-12\'); INSERT INTO employees VALUES(10006,\'1953-04-20\',\'Anneke\',\'Preusig\',\'F\',\'1989-06-02\'); INSERT INTO employees VALUES(10007,\'1957-05-23\',\'Tzvetan\',\'Zielinski\',\'F\',\'1989-02-10\'); INSERT INTO employees VALUES(10008,\'1958-02-19\',\'Saniya\',\'Kalloufi\',\'M\',\'1994-09-15\'); INSERT INTO employees VALUES(10009,\'1952-04-19\',\'Sumant\',\'Peac\',\'F\',\'1985-02-18\'); INSERT INTO employees VALUES(10010,\'1963-06-01\',\'Duangkaew\',\'Piveteau\',\'F\',\'1989-08-24\'); INSERT INTO employees VALUES(10011,\'1953-11-07\',\'Mary\',\'Sluis\',\'F\',\'1990-01-22\');');

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem`
(
    `id`            int(11)                                                 NOT NULL AUTO_INCREMENT COMMENT '题目ID',
    `title`         varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目标题',
    `description`   text CHARACTER SET utf8 COLLATE utf8_general_ci         NOT NULL COMMENT '题目描述',
    `sample_output` text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '样例输出',
    `hint`          text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '提示',
    `answer`        text CHARACTER SET utf8 COLLATE utf8_general_ci         NOT NULL COMMENT '答案',
    `solved`         int(11)                                                 NOT NULL DEFAULT 0 COMMENT '通过数',
    `submit`        int(11)                                                 NOT NULL DEFAULT 0 COMMENT '提交数',
    `true_result`   text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '正确输出',
    `database_id`   int(11)                                                 NOT NULL DEFAULT 0 COMMENT '数据库ID',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `FK_problem_database` (`database_id`) USING BTREE,
    CONSTRAINT `FK_problem_data_base` FOREIGN KEY (`database_id`) REFERENCES `data_base` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of problem
-- ----------------------------
INSERT INTO `problem`
VALUES (3, '查找入职员工时间排名倒数第三的员工所有信息', '查找入职员工时间排名倒数第三的员工所有信息', '', '',
        'select * from employees order by hire_date desc limit 3', 0, 0,
        '[(10008, \'1958-02-19\', \'Saniya\', \'Kalloufi\', \'M\', \'1994-09-15\'), (10011, \'1953-11-07\', \'Mary\', \'Sluis\', \'F\', \'1990-01-22\'), (10005, \'1955-01-21\', \'Kyoichi\', \'Maliniak\', \'M\', \'1989-09-12\')]',
        4);
INSERT INTO `problem`
VALUES (6, '查找所有员工的last_name和first_name以及对应部门编号dept_no，也包括展示没有分配具体部门的员工',
        '查找所有员工的last_name和first_name以及对应部门编号dept_no，也包括展示没有分配具体部门的员工', '', '', 'select * from employees', 0, 0,
        '[(10001, \'1953-09-02\', \'Georgi\', \'Facello\', \'M\', \'1986-06-26\'), (10002, \'1964-06-02\', \'Bezalel\', \'Simmel\', \'F\', \'1985-11-21\'), (10003, \'1959-12-03\', \'Parto\', \'Bamford\', \'M\', \'1986-08-28\'), (10004, \'1954-05-01\', \'Chirstian\', \'Koblick\', \'M\', \'1986-12-01\'), (10005, \'1955-01-21\', \'Kyoichi\', \'Maliniak\', \'M\', \'1989-09-12\'), (10006, \'1953-04-20\', \'Anneke\', \'Preusig\', \'F\', \'1989-06-02\'), (10007, \'1957-05-23\', \'Tzvetan\', \'Zielinski\', \'F\', \'1989-02-10\'), (10008, \'1958-02-19\', \'Saniya\', \'Kalloufi\', \'M\', \'1994-09-15\'), (10009, \'1952-04-19\', \'Sumant\', \'Peac\', \'F\', \'1985-02-18\'), (10010, \'1963-06-01\', \'Duangkaew\', \'Piveteau\', \'F\', \'1989-08-24\'), (10011, \'1953-11-07\', \'Mary\', \'Sluis\', \'F\', \'1990-01-22\')]',
        4);

-- ----------------------------
-- Table structure for solution
-- ----------------------------
DROP TABLE IF EXISTS `solution`;
CREATE TABLE `solution`
(
    `id`          int(11)                                               NOT NULL AUTO_INCREMENT COMMENT '解答ID',
    `uid`         int(11)                                               NULL     DEFAULT NULL COMMENT '用户ID',
    `pid`         int(11)                                               NULL     DEFAULT NULL COMMENT '题目ID',
    `source_code` text CHARACTER SET utf8 COLLATE utf8_general_ci       NOT NULL COMMENT '源代码',
    `submit_time` timestamp(0)                                          NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '提交时间',
    `run_error`   text CHARACTER SET utf8 COLLATE utf8_general_ci       NULL COMMENT '错误信息',
    `result`      varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '结果',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `uid` (`uid`) USING BTREE,
    INDEX `pid` (`pid`) USING BTREE,
    CONSTRAINT `FK_solution_problem` FOREIGN KEY (`pid`) REFERENCES `problem` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of solution
-- ----------------------------
INSERT INTO `solution`
VALUES (4, 7, 3, 'select * from employees order by hire_date desc limit 3', '2019-10-08 15:23:15', NULL, '1');
INSERT INTO `solution`
VALUES (5, 7, 3, 'select * from employees', '2019-10-08 15:27:02', '123', '3');
INSERT INTO `solution`
VALUES (8, 14, 3, 'select * from employees order by hire_date desc limit 3', '2019-10-08 15:48:45', NULL, '1');
INSERT INTO `solution`
VALUES (9, 14, 3, 'select * from employees order by hire_date desc', '2019-10-08 15:49:09', NULL, '3');
INSERT INTO `solution`
VALUES (10, 14, 3, 'select * from employees order by hire_date desc limit 3', '2019-10-08 15:49:30', NULL, '1');
INSERT INTO `solution`
VALUES (11, 14, 6, 'select * FROM employees', '2019-10-08 15:52:17', NULL, '1');

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`
(
    `id`       int(11)                                                      NOT NULL AUTO_INCREMENT,
    `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `salt`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `status`   bit(1)                                                       NOT NULL DEFAULT b'1' COMMENT '状态,0锁定，1正常',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `username` (`username`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
INSERT INTO `sys_admin`
VALUES (1, 'admin', '8315af46b6d38ebdaec05ec05393edd3', 'NOOQnVyN2NI2GD4q/V28', b'1');
INSERT INTO `sys_admin`
VALUES (2, 'a', 'a', 'a', b'1');
INSERT INTO `sys_admin`
VALUES (3, 'sgh', '7078553a2476e9c86ea74234145febef', 'yK5o/VZdWdpJplm+5VVt', b'1');

-- ----------------------------
-- Table structure for sys_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `a_id` int(11) NOT NULL DEFAULT 0,
    `r_id` int(11) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_admin_role
-- ----------------------------
INSERT INTO `sys_admin_role`
VALUES (1, 1, 1);
INSERT INTO `sys_admin_role`
VALUES (2, 3, 1);
INSERT INTO `sys_admin_role`
VALUES (3, 4, 2);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `id`         int(11)                                                      NOT NULL AUTO_INCREMENT,
    `name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
    `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission`
VALUES (1, '总权限', '*:*');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          int(11)                                                      NOT NULL AUTO_INCREMENT,
    `role_name`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
    `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `role_name` (`role_name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, 'admin', '总管理员');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `r_id` int(11) NOT NULL DEFAULT 0,
    `p_id` int(11) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission`
VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`       int(11)                                                NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '用户名',
    `email`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱地址',
    `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
    `salt`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码盐',
    `submit`   int(11)                                                NOT NULL DEFAULT 0 COMMENT '提交数',
    `solved`   int(11)                                                NOT NULL DEFAULT 0 COMMENT '通过数',
    `avatar`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT 'default.jpg' COMMENT '头像',
    `status`   bit(1)                                                 NOT NULL DEFAULT b'1' COMMENT '状态,0锁定，1正常',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `username` (`username`) USING BTREE,
    UNIQUE INDEX `email` (`email`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, '1', '1', '1', '', 100, 5, 'default.jpg', b'1');
INSERT INTO `sys_user`
VALUES (2, '2', '2', '2', '2', 5555, 6, 'default.jpg', b'1');
INSERT INTO `sys_user`
VALUES (7, 'sgh', 'sgh@qq.com', '7078553a2476e9c86ea74234145febef', 'yK5o/VZdWdpJplm+5VVt', 3, 8, 'default.jpg', b'1');
INSERT INTO `sys_user`
VALUES (10, '5555', '5555@qq.com', '24caf03e132a9a1d31dad6e19b7cb723', 'DpZtEwvvBwpPLNKWAccS', 0, 0, 'default.jpg',
        b'0');
INSERT INTO `sys_user`
VALUES (14, 'ssss', 'ss@qq.com', '7a222d20522d354bde7526e58b3798a4', 'D1xACs3Dsl16eJqRbYrF', 4, 2, 'default.jpg', b'1');

-- ----------------------------
-- Table structure for user_problem
-- ----------------------------
DROP TABLE IF EXISTS `user_problem`;
CREATE TABLE `user_problem`
(
    `id`    int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `uid`   int(11) NULL DEFAULT NULL COMMENT '用户id',
    `pid`   int(11) NULL DEFAULT NULL COMMENT '题目id',
    `state` bit(1)  NULL DEFAULT NULL COMMENT '状态,1表示通过,0表示尝试但未通过',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_problem
-- ----------------------------
INSERT INTO `user_problem`
VALUES (1, 7, 3, b'1');
INSERT INTO `user_problem`
VALUES (2, 14, 3, b'1');
INSERT INTO `user_problem`
VALUES (3, 14, 6, b'1');

SET FOREIGN_KEY_CHECKS = 1;

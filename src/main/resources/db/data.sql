/*
 Navicat Premium Data Transfer

 Source Server         : mysql-local
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : sqlonlinejudge

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 15/03/2020 21:40:17
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
    `create_table` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci        NOT NULL COMMENT '建表语句',
    `test_data`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci        NOT NULL COMMENT '数据插入语句',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of data_base
-- ----------------------------
INSERT INTO `data_base`
VALUES (1, '公司系统',
        'CREATE TABLE `dept_manager` (\n`dept_no` char(4) NOT NULL,\n`emp_no` int(11) NOT NULL,\n`from_date` date NOT NULL,\n`to_date` date NOT NULL,\nPRIMARY KEY (`emp_no`,`dept_no`));\nCREATE TABLE `employees` (\n`emp_no` int(11) NOT NULL,\n`birth_date` date NOT NULL,\n`first_name` varchar(14) NOT NULL,\n`last_name` varchar(16) NOT NULL,\n`gender` char(1) NOT NULL,\n`hire_date` date NOT NULL,\nPRIMARY KEY (`emp_no`));',
        'INSERT INTO dept_manager VALUES(\'d001\',10002,\'1996-08-03\',\'9999-01-01\'); INSERT INTO dept_manager VALUES(\'d002\',10006,\'1990-08-05\',\'9999-01-01\'); INSERT INTO dept_manager VALUES(\'d003\',10005,\'1989-09-12\',\'9999-01-01\'); INSERT INTO dept_manager VALUES(\'d004\',10004,\'1986-12-01\',\'9999-01-01\'); INSERT INTO dept_manager VALUES(\'d005\',10010,\'1996-11-24\',\'2000-06-26\'); INSERT INTO dept_manager VALUES(\'d006\',10010,\'2000-06-26\',\'9999-01-01\'); INSERT INTO employees VALUES(10001,\'1953-09-02\',\'Georgi\',\'Facello\',\'M\',\'1986-06-26\'); INSERT INTO employees VALUES(10002,\'1964-06-02\',\'Bezalel\',\'Simmel\',\'F\',\'1985-11-21\'); INSERT INTO employees VALUES(10003,\'1959-12-03\',\'Parto\',\'Bamford\',\'M\',\'1986-08-28\'); INSERT INTO employees VALUES(10004,\'1954-05-01\',\'Chirstian\',\'Koblick\',\'M\',\'1986-12-01\'); INSERT INTO employees VALUES(10005,\'1955-01-21\',\'Kyoichi\',\'Maliniak\',\'M\',\'1989-09-12\'); INSERT INTO employees VALUES(10006,\'1953-04-20\',\'Anneke\',\'Preusig\',\'F\',\'1989-06-02\'); INSERT INTO employees VALUES(10007,\'1957-05-23\',\'Tzvetan\',\'Zielinski\',\'F\',\'1989-02-10\'); INSERT INTO employees VALUES(10008,\'1958-02-19\',\'Saniya\',\'Kalloufi\',\'M\',\'1994-09-15\'); INSERT INTO employees VALUES(10009,\'1952-04-19\',\'Sumant\',\'Peac\',\'F\',\'1985-02-18\'); INSERT INTO employees VALUES(10010,\'1963-06-01\',\'Duangkaew\',\'Piveteau\',\'F\',\'1989-08-24\'); INSERT INTO employees VALUES(10011,\'1953-11-07\',\'Mary\',\'Sluis\',\'F\',\'1990-01-22\');');

-- ----------------------------
-- Table structure for problem_categories
-- ----------------------------
DROP TABLE IF EXISTS `problem_categories`;
CREATE TABLE `problem_categories`
(
    `id`   int(11)                                                       NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of problem_categories
-- ----------------------------
INSERT INTO `problem_categories`
VALUES (1, '16级 1');
INSERT INTO `problem_categories`
VALUES (2, '16级 2');

ALTER TABLE `problem_categories`
ADD COLUMN `start_time` DATETIME NULL AFTER `name`,
ADD COLUMN `end_time` DATETIME NULL AFTER `start_time`,
ADD COLUMN `view_after_end` BIT NOT NULL DEFAULT 1 AFTER `end_time`;
-- ----------------------------
-- Table structure for problem_collections
-- ----------------------------
DROP TABLE IF EXISTS `problem_collections`;
CREATE TABLE `problem_collections`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `category_id` int(11) NOT NULL,
    `problem_id`  int(11) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `category_id` (`category_id`) USING BTREE,
    INDEX `problem_id` (`problem_id`) USING BTREE,
    CONSTRAINT `problem_collections_ibfk_2` FOREIGN KEY (`problem_id`) REFERENCES `problems` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `problem_collections_ibfk_3` FOREIGN KEY (`category_id`) REFERENCES `problem_categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of problem_collections
-- ----------------------------
INSERT INTO `problem_collections`
VALUES (1, 1, 1);
INSERT INTO `problem_collections`
VALUES (2, 1, 2);
INSERT INTO `problem_collections`
VALUES (3, 2, 2);

ALTER TABLE `problem_collections`
CHANGE COLUMN `category_id` `category_id` INT(11) NOT NULL COMMENT '题目集ID' AFTER `id`,
CHANGE COLUMN `problem_id` `problem_id` INT(11) NOT NULL COMMENT '题目ID' AFTER `category_id`,
ADD COLUMN `problem_score` INT NOT NULL DEFAULT 0 COMMENT '题目分值' AFTER `problem_id`;

ALTER TABLE `problem_collections`
ADD COLUMN `problem_submit` INT NOT NULL DEFAULT 0 COMMENT '题目提交数' AFTER `problem_id`,
ADD COLUMN `problem_solved` INT NOT NULL DEFAULT 0 COMMENT '题目通过数' AFTER `problem_submit`;
-- ----------------------------
-- Table structure for problems
-- ----------------------------
DROP TABLE IF EXISTS `problems`;
CREATE TABLE `problems`
(
    `id`            int(11)                                                 NOT NULL AUTO_INCREMENT COMMENT '题目ID',
    `title`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目标题',
    `description`   text CHARACTER SET utf8 COLLATE utf8_general_ci         NOT NULL COMMENT '题目描述',
    `sample_output` text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '样例输出',
    `hint`          text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '提示',
    `answer`        text CHARACTER SET utf8 COLLATE utf8_general_ci         NOT NULL COMMENT '答案',
    `solved`        int(11)                                                 NOT NULL DEFAULT 0 COMMENT '通过数',
    `submit`        int(11)                                                 NOT NULL DEFAULT 0 COMMENT '提交数',
    `true_result`   text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '正确输出',
    `database_id`   int(11)                                                 NOT NULL DEFAULT 0 COMMENT '数据库ID',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `FK_problem_database` (`database_id`) USING BTREE,
    CONSTRAINT `problems_ibfk_1` FOREIGN KEY (`database_id`) REFERENCES `data_base` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of problems
-- ----------------------------
INSERT INTO `problems`
VALUES (1, '获取所有非manager的员工emp_no', '获取所有非manager的员工emp_no', '', '',
        'select emp_no from employees where not exists(select 1 from dept_manager where employees.emp_no = dept_manager.emp_no)',
        1, 4, 'E70D8D6AA53A9AF17093C2B01BF2A386', 1);
INSERT INTO `problems`
VALUES (2, '查找employees表', '查找employees表所有emp_no为奇数，且last_name不为Mary的员工信息，并按照hire_date逆序排列', '', '',
        'select * from employees where emp_no % 2 = 1 and last_name != \'Mary\'order by hire_date desc', 1, 1,
        'CF5A78656EBA8C87F885D99FFC057173', 1);

ALTER TABLE `problems` ADD COLUMN `difficulty` TINYINT NOT NULL DEFAULT 1 COMMENT '题目难度' AFTER `true_result`;
ALTER TABLE `problems` CHANGE COLUMN `true_result` `true_result` TEXT(32) NULL DEFAULT NULL COMMENT '正确输出' COLLATE 'utf8_general_ci' AFTER `submit`;
ALTER TABLE `problems`
ADD COLUMN `is_update` BIT NOT NULL DEFAULT 0 COMMENT '题目是否是Update/Delete等会对表中记录进行修改的题目' AFTER `difficulty`,
ADD COLUMN `select_after_update` TEXT NULL DEFAULT NULL COMMENT 'Update类题目执行完之后对修改部分的Select语句，用于比对修改的结果是否正确' AFTER `is_update`;

ALTER TABLE `problems`
DROP COLUMN `solved`,
DROP COLUMN `submit`;

-- ----------------------------
-- Table structure for solutions
-- ----------------------------
DROP TABLE IF EXISTS `solutions`;
CREATE TABLE `solutions`
(
    `id`          int(11)                                               NOT NULL AUTO_INCREMENT COMMENT '解答ID',
    `uid`         int(11)                                               NOT NULL COMMENT '用户ID',
    `pid`         int(11)                                               NOT NULL COMMENT '题目ID',
    `source_code` text CHARACTER SET utf8 COLLATE utf8_general_ci       NOT NULL COMMENT '源代码',
    `submit_time` timestamp(0)                                          NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '提交时间',
    `run_error`   text CHARACTER SET utf8 COLLATE utf8_general_ci       NULL COMMENT '错误信息',
    `result`      varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '结果',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `uid` (`uid`) USING BTREE,
    INDEX `pid` (`pid`) USING BTREE,
    CONSTRAINT `solutions_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `sys_users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `solutions_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `problems` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of solutions
-- ----------------------------
INSERT INTO `solutions`
VALUES (1, 1, 1, 'SELECT * FROM dept_manager', '2019-10-09 09:41:52', NULL, '3');
INSERT INTO `solutions`
VALUES (2, 1, 1, 'select * from employees where emp_no % 2 = 1 and last_name != \'Mary\'\norder by hire_date desc',
        '2019-10-09 09:48:59', NULL, '3');
INSERT INTO `solutions`
VALUES (3, 1, 2, 'select * from employees where emp_no % 2 = 1 and last_name != \'Mary\'\norder by hire_date desc',
        '2019-10-09 09:49:26', NULL, '1');
INSERT INTO `solutions`
VALUES (4, 1, 1,
        'select emp_no from employees where not exists(select 1 from dept_manager where employees.emp_no = dept_manager.emp_no)',
        '2019-10-09 09:49:43', NULL, '1');
INSERT INTO `solutions`
VALUES (5, 1, 1, 'ffff', '2019-10-09 10:04:40', 'near \"ffff\": syntax error', '2');

ALTER TABLE `solutions`
ADD COLUMN `pcid` INT NOT NULL DEFAULT 1 COMMENT '题目集ID' AFTER `pid`,
ADD CONSTRAINT `FK_solutions_problem_categories` FOREIGN KEY (`pcid`) REFERENCES `problem_categories` (`id`);

-- ----------------------------
-- Table structure for sys_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员角色ID',
    `a_id` int(11) NOT NULL DEFAULT 0 COMMENT '管理员ID',
    `r_id` int(11) NOT NULL DEFAULT 0 COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `a_id` (`a_id`) USING BTREE,
    INDEX `r_id` (`r_id`) USING BTREE,
    CONSTRAINT `sys_admin_role_ibfk_1` FOREIGN KEY (`a_id`) REFERENCES `sys_administrators` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `sys_admin_role_ibfk_2` FOREIGN KEY (`r_id`) REFERENCES `sys_roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_admin_role
-- ----------------------------
INSERT INTO `sys_admin_role`
VALUES (1, 1, 1);
INSERT INTO `sys_admin_role`
VALUES (2, 2, 2);
INSERT INTO `sys_admin_role`
VALUES (3, 4, 2);
INSERT INTO `sys_admin_role`
VALUES (4, 5, 2);

-- ----------------------------
-- Table structure for sys_administrators
-- ----------------------------
DROP TABLE IF EXISTS `sys_administrators`;
CREATE TABLE `sys_administrators`
(
    `id`       int(11)                                                      NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
    `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员名称\r\n',
    `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员密码',
    `salt`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '盐',
    `status`   bit(1)                                                       NULL DEFAULT b'1' COMMENT '管理员状态',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `username` (`username`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_administrators
-- ----------------------------
INSERT INTO `sys_administrators`
VALUES (1, 'admin', '8315af46b6d38ebdaec05ec05393edd3', 'NOOQnVyN2NI2GD4q/V28', b'1');
INSERT INTO `sys_administrators`
VALUES (2, 'sgh', '7078553a2476e9c86ea74234145febef', 'yK5o/VZdWdpJplm+5VVt', b'1');
INSERT INTO `sys_administrators`
VALUES (4, 'xdh', '5e831f34f03ac6512acac21206af848c', 's4cqIwFmuoC0OJejOQRB', b'1');
INSERT INTO `sys_administrators`
VALUES (5, 'ljq', 'a5e601133d00af2637b71b739e28b9ed', 'l4jipX7kVnrYr1xZhn8L', b'1');

-- ----------------------------
-- Table structure for sys_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_permissions`;
CREATE TABLE `sys_permissions`
(
    `id`         int(11)                                                      NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    `name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '权限名称',
    `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '权限详情',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permissions
-- ----------------------------
INSERT INTO `sys_permissions`
VALUES (1, '总权限', '*:*');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT COMMENT '角色权限ID',
    `r_id` int(11) NOT NULL DEFAULT 0 COMMENT '角色ID',
    `p_id` int(11) NOT NULL DEFAULT 0 COMMENT '权限ID',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `r_id` (`r_id`) USING BTREE,
    INDEX `p_id` (`p_id`) USING BTREE,
    CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`r_id`) REFERENCES `sys_roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `sys_permissions` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission`
VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for sys_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles`
(
    `id`          int(11)                                                      NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '角色名',
    `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '角色描述',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `role_name` (`role_name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_roles
-- ----------------------------
INSERT INTO `sys_roles`
VALUES (1, 'admin', '总管理员');
INSERT INTO `sys_roles`
VALUES (2, 'teacher', '教师');

-- ----------------------------
-- Table structure for sys_user_groups
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_groups`;
CREATE TABLE `sys_user_groups`
(
    `id`          int(11)                                                       NOT NULL AUTO_INCREMENT COMMENT '用户组ID',
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户组名称',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户组描述',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_groups
-- ----------------------------
INSERT INTO `sys_user_groups`
VALUES (1, '计算1614', '计算机科学与技术16级4班');

ALTER TABLE `sys_user_groups`
    DROP COLUMN `description`;

-- ----------------------------
-- Table structure for sys_users
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users`
(
    `id`         int(11)                                                NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '用户名',
    `student_no` bigint(20)                                             NULL     DEFAULT NULL COMMENT '学号',
    `email`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱地址',
    `password`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
    `salt`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码盐',
    `submit`     int(11)                                                NOT NULL DEFAULT 0 COMMENT '提交数',
    `solved`     int(11)                                                NOT NULL DEFAULT 0 COMMENT '通过数',
    `avatar`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT 'default.jpg' COMMENT '头像',
    `status`     bit(1)                                                 NOT NULL DEFAULT b'1' COMMENT '状态,0未激活，1正常，2锁定',
    `group_id`   int(11)                                                NULL     DEFAULT NULL COMMENT '用户组ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `username` (`username`) USING BTREE,
    UNIQUE INDEX `email` (`email`) USING BTREE,
    INDEX `group_id` (`group_id`) USING BTREE,
    CONSTRAINT `sys_users_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `sys_user_groups` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_users
-- ----------------------------
INSERT INTO `sys_users`
VALUES (1, 'user_sgh', 201621121110, 'sgh@qq.com', '7078553a2476e9c86ea74234145febef', 'yK5o/VZdWdpJplm+5VVt', 5, 2,
        'default.jpg', b'1', 1);
INSERT INTO `sys_users`
VALUES (2, 'user_xdh', 201621121101, 'xdh@qq.com', 'c51f9c7eba223c36a9e79aef5dbe3dd1', 'stFp0Nc4hYuiLaiN2aNe', 0, 0,
        'default.jpg', b'1', 1);
INSERT INTO `sys_users`
VALUES (3, 'user_ljq', 201621121108, 'ljq@qq.com', 'd1abfc1dcd61244bed23b54ad3e67142', 'u5EUoEIF0yerJVoQLye8', 0, 0,
        'default.jpg', b'1', 1);

ALTER TABLE `sys_users`
    DROP COLUMN `submit`,
    DROP COLUMN `solved`;

ALTER TABLE `sys_users`
    CHANGE COLUMN `student_no` `student_no` VARCHAR(20) NULL DEFAULT NULL COMMENT '学号' AFTER `username`;

-- ----------------------------
-- Table structure for upload_images
-- ----------------------------
DROP TABLE IF EXISTS `upload_images`;
CREATE TABLE `upload_images`
(
    `id`       int(11)                                                   NOT NULL AUTO_INCREMENT,
    `filename` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `uid`      int(11)                                                   NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `FK__sys_user` (`uid`) USING BTREE,
    CONSTRAINT `FK__sys_user` FOREIGN KEY (`uid`) REFERENCES `sys_administrators` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_problems
-- ----------------------------
DROP TABLE IF EXISTS `user_problems`;
CREATE TABLE `user_problems`
(
    `id`    int(11) NOT NULL AUTO_INCREMENT COMMENT '用户题库ID',
    `uid`   int(11) NOT NULL COMMENT '用户ID',
    `pid`   int(11) NOT NULL COMMENT '题目ID',
    `state` bit(1)  NOT NULL COMMENT '用户题目状态',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `uid` (`uid`) USING BTREE,
    INDEX `pid` (`pid`) USING BTREE,
    CONSTRAINT `user_problems_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `sys_users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `user_problems_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `problems` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_problems
-- ----------------------------
INSERT INTO `user_problems`
VALUES (1, 1, 1, b'1');
INSERT INTO `user_problems`
VALUES (2, 1, 2, b'1');

ALTER TABLE `user_problems`
ADD COLUMN `pcid` INT NOT NULL DEFAULT 10 COMMENT '题目集ID' AFTER `uid`,
ADD CONSTRAINT `FK_user_problems_problem_categories` FOREIGN KEY (`pcid`) REFERENCES `problem_categories` (`id`);

ALTER TABLE `user_problems`
CHANGE COLUMN `state` `passed` BIT(1) NOT NULL COMMENT '用户题目状态（是否通过）' AFTER `pid`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `user_group_collections` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL DEFAULT '0',
  `user_group_id` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `FK__sys_users` FOREIGN KEY (`user_id`) REFERENCES `sys_users` (`id`),
  CONSTRAINT `FK__sys_user_groups` FOREIGN KEY (`user_group_id`) REFERENCES `sys_user_groups` (`id`)
) COLLATE='utf8mb4_general_ci';

CREATE TABLE `problem_category_permission` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `problem_category_id` INT NOT NULL,
   `user_group_id` INT NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `FK_problem_category_id` FOREIGN KEY (`problem_category_id`) REFERENCES `problem_categories` (`id`),
   CONSTRAINT `FK_user_group_id` FOREIGN KEY (`user_group_id`) REFERENCES `sys_user_groups` (`id`)
)
COMMENT='关联题目集ID和用户组ID'
COLLATE='utf8mb4_general_ci';
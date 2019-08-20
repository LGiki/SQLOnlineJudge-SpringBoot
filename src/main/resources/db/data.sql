/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE IF NOT EXISTS `sqlonlinejudge` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `sqlonlinejudge`;

DROP TABLE IF EXISTS `database`;
CREATE TABLE IF NOT EXISTS `database`
(
    `id`           bigint(20)  NOT NULL DEFAULT '0' COMMENT '数据库ID',
    `name`         varchar(50) NOT NULL DEFAULT '0' COMMENT '数据库名称',
    `create_table` text COMMENT '建表语句',
    `test_data`    text COMMENT '数据插入语句',
    `is_created`   bit(1)      NOT NULL DEFAULT b'0' COMMENT '数据库是否已创建',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `database`
    DISABLE KEYS */;
INSERT INTO `database` (`id`, `name`, `create_table`, `test_data`, `is_created`)
VALUES (1, '1', '1', '1', b'1');
/*!40000 ALTER TABLE `database`
    ENABLE KEYS */;

DROP TABLE IF EXISTS `problem`;
CREATE TABLE IF NOT EXISTS `problem`
(
    `id`            bigint(20)   NOT NULL DEFAULT '0' COMMENT '题目ID',
    `title`         varchar(255) NOT NULL COMMENT '题目标题',
    `description`   text         NOT NULL COMMENT '题目描述',
    `input_format`  text COMMENT '输入格式',
    `output_format` text COMMENT '输出格式',
    `sample_input`  text COMMENT '样例输入',
    `sample_output` text COMMENT '样例输出',
    `hint`          text COMMENT '提示',
    `answer`        text         NOT NULL COMMENT '答案',
    `solve`         int(11)      NOT NULL DEFAULT '0' COMMENT '通过数',
    `submit`        int(11)      NOT NULL DEFAULT '0' COMMENT '提交数',
    `true_result`   text COMMENT '正确输出',
    `need_order`    bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否需要答案有序',
    `database_id`   bigint(20)   NOT NULL DEFAULT '0' COMMENT '数据库ID',
    PRIMARY KEY (`id`),
    KEY `FK_problem_database` (`database_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*!40000 ALTER TABLE `problem`
    DISABLE KEYS */;
INSERT INTO `problem` (`id`, `title`, `description`, `input_format`, `output_format`, `sample_input`, `sample_output`,
                       `hint`, `answer`, `solve`, `submit`, `true_result`, `need_order`, `database_id`)
VALUES (1, '1', '1', '1', '1', '1', '1', '1', '1', 0, 0, '1', b'1', 1);
/*!40000 ALTER TABLE `problem`
    ENABLE KEYS */;

DROP TABLE IF EXISTS `problem_detail`;
CREATE TABLE IF NOT EXISTS `problem_detail`
(
    `id`         bigint(20) NOT NULL DEFAULT '0',
    `problem_id` bigint(20) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='题目的详细描述';

/*!40000 ALTER TABLE `problem_detail`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `problem_detail`
    ENABLE KEYS */;

DROP TABLE IF EXISTS `solution`;
CREATE TABLE IF NOT EXISTS `solution`
(
    `id`          bigint(20) NOT NULL DEFAULT '0' COMMENT '解答ID',
    `uid`         bigint(20)          DEFAULT NULL COMMENT '用户ID',
    `pid`         bigint(20)          DEFAULT NULL COMMENT '题目ID',
    `source_code` text       NOT NULL COMMENT '源代码',
    `submit_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
    `run_error`   text COMMENT '错误信息',
    `result`      varchar(2)          DEFAULT NULL COMMENT '结果',
    PRIMARY KEY (`id`),
    KEY `uid` (`uid`),
    KEY `pid` (`pid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*!40000 ALTER TABLE `solution`
    DISABLE KEYS */;
INSERT INTO `solution` (`id`, `uid`, `pid`, `source_code`, `submit_time`, `run_error`, `result`)
VALUES (1, 1, 1, '1', '2011-06-24 18:37:39', '', '0');
/*!40000 ALTER TABLE `solution`
    ENABLE KEYS */;

DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE IF NOT EXISTS `sys_admin`
(
    `id`       bigint(20)                                                   NOT NULL AUTO_INCREMENT,
    `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `salt`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

/*!40000 ALTER TABLE `sys_admin`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_admin`
    ENABLE KEYS */;

DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE IF NOT EXISTS `sys_admin_role`
(
    `id`   bigint(20) NOT NULL AUTO_INCREMENT,
    `a_id` bigint(20) NOT NULL DEFAULT '0',
    `r_id` bigint(20) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

/*!40000 ALTER TABLE `sys_admin_role`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_admin_role`
    ENABLE KEYS */;

DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE IF NOT EXISTS `sys_permission`
(
    `id`         bigint(20)                                                   NOT NULL AUTO_INCREMENT,
    `name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
    `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

/*!40000 ALTER TABLE `sys_permission`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_permission`
    ENABLE KEYS */;

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE IF NOT EXISTS `sys_role`
(
    `id`          bigint(20)                                                   NOT NULL AUTO_INCREMENT,
    `role_code`   varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '0',
    `role_name`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
    `description` varchar(50) COLLATE utf8mb4_general_ci                       NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `role_code` (`role_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

/*!40000 ALTER TABLE `sys_role`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role`
    ENABLE KEYS */;

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE IF NOT EXISTS `sys_role_permission`
(
    `id`   bigint(20) NOT NULL DEFAULT '0',
    `r_id` bigint(20) NOT NULL DEFAULT '0',
    `p_id` bigint(20) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

/*!40000 ALTER TABLE `sys_role_permission`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_permission`
    ENABLE KEYS */;

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user`
(
    `id`        bigint(20)  NOT NULL DEFAULT '0' COMMENT '用户ID',
    `username`  varchar(50) NOT NULL COMMENT '用户名',
    `email`     varchar(50) NOT NULL COMMENT '邮箱地址',
    `reel_name` varchar(50)          DEFAULT NULL COMMENT '真名',
    `password`  varchar(50) NOT NULL COMMENT '密码',
    `salt`      varchar(50) NOT NULL COMMENT '密码盐',
    `submit`    int(11)     NOT NULL DEFAULT '0' COMMENT '提交数',
    `solved`    int(11)     NOT NULL DEFAULT '0' COMMENT '通过数',
    `avatar`    varchar(50)          DEFAULT 'default.jpg' COMMENT '头像',
    `status`    varchar(2)  NOT NULL DEFAULT '0' COMMENT '状态,0未激活，1正常，2锁定',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`),
    UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/*!40000 ALTER TABLE `sys_user`
    DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `email`, `reel_name`, `password`, `salt`, `submit`, `solved`, `avatar`,
                        `status`)
VALUES (1, '1', '1', NULL, 'c4ca4238a0b923820dcc509a6f75849b', '', 100, 0, NULL, '1'),
       (3, '2', '2', NULL, 'c81e728d9d4c2f636f067f89cc14862c', '', 122, 0, NULL, '1'),
       (4, 'admin', '123@qq.com', NULL, '21232f297a57a5a743894a0e4a801fc3', '', 200, 0, NULL, '1');
/*!40000 ALTER TABLE `sys_user`
    ENABLE KEYS */;

/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;

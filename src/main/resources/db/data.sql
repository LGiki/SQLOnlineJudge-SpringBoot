-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.17 - MySQL Community Server - GPL
-- 服务器OS:                        Linux
-- HeidiSQL 版本:                  10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for sqlonlinejudge
CREATE DATABASE IF NOT EXISTS `sqlonlinejudge` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sqlonlinejudge`;

-- Dumping structure for table sqlonlinejudge.data_base
CREATE TABLE IF NOT EXISTS `data_base` (
                                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据库ID',
                                           `name` varchar(50) NOT NULL DEFAULT '0' COMMENT '数据库名称',
                                           `create_table` text COMMENT '建表语句',
                                           `test_data` text COMMENT '数据插入语句',
                                           `is_created` bit(1) NOT NULL DEFAULT b'0' COMMENT '数据库是否已创建',
                                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table sqlonlinejudge.data_base: ~0 rows (大约)
/*!40000 ALTER TABLE `data_base` DISABLE KEYS */;
INSERT INTO `data_base` (`id`, `name`, `create_table`, `test_data`, `is_created`) VALUES
(1, '1', '1', '1', b'1');
/*!40000 ALTER TABLE `data_base` ENABLE KEYS */;

-- Dumping structure for table sqlonlinejudge.problem
CREATE TABLE IF NOT EXISTS `problem` (
                                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '题目ID',
                                         `title` varchar(255) NOT NULL COMMENT '题目标题',
                                         `description` text NOT NULL COMMENT '题目描述',
                                         `sample_output` text COMMENT '样例输出',
                                         `hint` text COMMENT '提示',
                                         `answer` text NOT NULL COMMENT '答案',
                                         `solve` int(11) NOT NULL DEFAULT '0' COMMENT '通过数',
                                         `submit` int(11) NOT NULL DEFAULT '0' COMMENT '提交数',
                                         `true_result` text COMMENT '正确输出',
                                         `need_order` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否需要答案有序',
                                         `database_id` int(11) NOT NULL DEFAULT '0' COMMENT '数据库ID',
                                         PRIMARY KEY (`id`),
                                         KEY `FK_problem_database` (`database_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table sqlonlinejudge.problem: ~0 rows (大约)
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
INSERT INTO `problem` (`id`, `title`, `description`, `sample_output`, `hint`, `answer`, `solve`, `submit`, `true_result`, `need_order`, `database_id`) VALUES
(1, '1', '1', '1', '1', '1', 0, 0, '1', b'1', 1);
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;

-- Dumping structure for table sqlonlinejudge.solution
CREATE TABLE IF NOT EXISTS `solution` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '解答ID',
                                          `uid` int(11) DEFAULT NULL COMMENT '用户ID',
                                          `pid` int(11) DEFAULT NULL COMMENT '题目ID',
                                          `source_code` text NOT NULL COMMENT '源代码',
                                          `submit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
                                          `run_error` text COMMENT '错误信息',
                                          `result` varchar(2) DEFAULT NULL COMMENT '结果',
                                          PRIMARY KEY (`id`),
                                          KEY `uid` (`uid`),
                                          KEY `pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table sqlonlinejudge.solution: ~0 rows (大约)
/*!40000 ALTER TABLE `solution` DISABLE KEYS */;
INSERT INTO `solution` (`id`, `uid`, `pid`, `source_code`, `submit_time`, `run_error`, `result`) VALUES
(1, 1, 1, '1', '2011-06-24 18:37:39', '', '0'),
(2, 1, 1, '2', '2019-08-26 21:46:51', NULL, '1'),
(3, 2, 2, '3', '2019-08-26 13:47:45', NULL, '1');
/*!40000 ALTER TABLE `solution` ENABLE KEYS */;

-- Dumping structure for table sqlonlinejudge.sys_admin
CREATE TABLE IF NOT EXISTS `sys_admin` (
                                           `id` int(11) NOT NULL AUTO_INCREMENT,
                                           `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                           `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                           `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                           PRIMARY KEY (`id`) USING BTREE,
                                           UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sqlonlinejudge.sys_admin: ~2 rows (大约)
/*!40000 ALTER TABLE `sys_admin` DISABLE KEYS */;
INSERT INTO `sys_admin` (`id`, `username`, `password`, `salt`) VALUES
(1, 'admin', 'admin', '8d78869f470951332959580424d4bf4f'),
(2, 'a', 'a', 'a');
/*!40000 ALTER TABLE `sys_admin` ENABLE KEYS */;

-- Dumping structure for table sqlonlinejudge.sys_admin_role
CREATE TABLE IF NOT EXISTS `sys_admin_role` (
                                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                                `a_id` int(11) NOT NULL DEFAULT '0',
                                                `r_id` int(11) NOT NULL DEFAULT '0',
                                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sqlonlinejudge.sys_admin_role: ~0 rows (大约)
/*!40000 ALTER TABLE `sys_admin_role` DISABLE KEYS */;
INSERT INTO `sys_admin_role` (`id`, `a_id`, `r_id`) VALUES
(1, 1, 1);
/*!40000 ALTER TABLE `sys_admin_role` ENABLE KEYS */;

-- Dumping structure for table sqlonlinejudge.sys_permission
CREATE TABLE IF NOT EXISTS `sys_permission` (
                                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                                `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
                                                `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
                                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sqlonlinejudge.sys_permission: ~0 rows (大约)
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
INSERT INTO `sys_permission` (`id`, `name`, `permission`) VALUES
(1, '总权限', '*:*');
/*!40000 ALTER TABLE `sys_permission` ENABLE KEYS */;

-- Dumping structure for table sqlonlinejudge.sys_role
CREATE TABLE IF NOT EXISTS `sys_role` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `role_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
                                          `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
                                          PRIMARY KEY (`id`),
                                          UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sqlonlinejudge.sys_role: ~0 rows (大约)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `role_name`, `description`) VALUES
(1, 'admin', '总管理员');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- Dumping structure for table sqlonlinejudge.sys_role_permission
CREATE TABLE IF NOT EXISTS `sys_role_permission` (
                                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                                     `r_id` int(11) NOT NULL DEFAULT '0',
                                                     `p_id` int(11) NOT NULL DEFAULT '0',
                                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sqlonlinejudge.sys_role_permission: ~0 rows (大约)
/*!40000 ALTER TABLE `sys_role_permission` DISABLE KEYS */;
INSERT INTO `sys_role_permission` (`id`, `r_id`, `p_id`) VALUES
(1, 1, 1);
/*!40000 ALTER TABLE `sys_role_permission` ENABLE KEYS */;

-- Dumping structure for table sqlonlinejudge.sys_user
CREATE TABLE IF NOT EXISTS `sys_user` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                                          `username` varchar(50) NOT NULL DEFAULT '0' COMMENT '用户名',
                                          `email` varchar(50) NOT NULL COMMENT '邮箱地址',
                                          `password` varchar(50) NOT NULL COMMENT '密码',
                                          `salt` varchar(50) NOT NULL COMMENT '密码盐',
                                          `submit` int(11) NOT NULL DEFAULT '0' COMMENT '提交数',
                                          `solved` int(11) NOT NULL DEFAULT '0' COMMENT '通过数',
                                          `avatar` varchar(50) DEFAULT 'default.jpg' COMMENT '头像',
                                          `status` varchar(2) NOT NULL DEFAULT '0' COMMENT '状态,0未激活，1正常，2锁定',
                                          PRIMARY KEY (`id`),
                                          UNIQUE KEY `username` (`username`),
                                          UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Dumping data for table sqlonlinejudge.sys_user: ~2 rows (大约)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `email`, `password`, `salt`, `submit`, `solved`, `avatar`, `status`) VALUES
(1, '1', '1', '1', '', 100, 5, 'default.jpg', '1'),
(7, 'sgh', 'sgh@qq.com', '7078553a2476e9c86ea74234145febef', 'yK5o/VZdWdpJplm+5VVt', 0, 7, 'default.jpg', '0'),
(9, '2', '2', '2', '2', 5555, 6, 'default.jpg', '0');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

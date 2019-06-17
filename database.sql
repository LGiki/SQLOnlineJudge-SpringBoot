-- Dumping database structure for sqlonlinejudge
CREATE DATABASE IF NOT EXISTS `sqlonlinejudge` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `sqlonlinejudge`;

-- Dumping structure for table sqlonlinejudge.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `email` varchar(255) NOT NULL COMMENT '邮箱地址',
  `is_email_valid` bit(1) NOT NULL DEFAULT b'0' COMMENT '邮箱是否有效',
  `submit` int(11) NOT NULL DEFAULT '0' COMMENT '提交数',
  `solved` int(11) NOT NULL DEFAULT '0' COMMENT '通过数',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `status` smallint(6) NOT NULL DEFAULT '10' COMMENT '状态',
  `role` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户角色',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table sqlonlinejudge.database
CREATE TABLE IF NOT EXISTS `database` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据库ID',
  `name` varchar(50) NOT NULL DEFAULT '0' COMMENT '数据库名称',
  `create_table` text COMMENT '建表语句',
  `test_data` text COMMENT '数据插入语句',
  `is_created` bit(1) NOT NULL DEFAULT b'0' COMMENT '数据库是否已创建',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table sqlonlinejudge.problem
CREATE TABLE IF NOT EXISTS `problem` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '题目ID',
  `title` varchar(255) NOT NULL COMMENT '题目标题',
  `description` text NOT NULL COMMENT '题目描述',
  `input_format` text COMMENT '输入格式',
  `output_format` text COMMENT '输出格式',
  `sample_input` text COMMENT '样例输入',
  `sample_output` text COMMENT '样例输出',
  `hint` text COMMENT '提示',
  `answer` text NOT NULL COMMENT '答案',
  `solve` int(11) NOT NULL DEFAULT '0' COMMENT '通过数',
  `submit` int(11) NOT NULL DEFAULT '0' COMMENT '提交数',
  `true_result` text COMMENT '正确输出',
  `need_order` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否需要答案有序',
  `database_id` int(11) NOT NULL COMMENT '数据库ID',
  PRIMARY KEY (`id`),
  KEY `FK_problem_database` (`database_id`),
  CONSTRAINT `FK_problem_database` FOREIGN KEY (`database_id`) REFERENCES `database` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table sqlonlinejudge.solution
CREATE TABLE IF NOT EXISTS `solution` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '解答ID',
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `pid` int(11) DEFAULT NULL COMMENT '题目ID',
  `source_code` text NOT NULL COMMENT '源代码',
  `submit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `run_error` text COMMENT '错误信息',
  `result` tinyint(4) DEFAULT NULL COMMENT '结果',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `pid` (`pid`),
  CONSTRAINT `solution_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`),
  CONSTRAINT `solution_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `problem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

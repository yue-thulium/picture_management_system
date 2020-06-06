-- --------------------------------------------------------
-- 主机:                           182.92.208.18
-- Server version:               8.0.18 - MySQL Community Server - GPL
-- Server OS:                    Linux
-- HeidiSQL 版本:                  10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for picture_management_system
CREATE DATABASE IF NOT EXISTS `picture_management_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `picture_management_system`;

-- Dumping structure for table picture_management_system.album_tag_relation
CREATE TABLE IF NOT EXISTS `album_tag_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `album_id` int(11) NOT NULL DEFAULT '0' COMMENT '图册图片ID',
  `tag_id` int(11) NOT NULL DEFAULT '0' COMMENT '图册图片标签ID',
  PRIMARY KEY (`id`),
  KEY `FK_album_tag_relation_picture_album` (`album_id`),
  KEY `FK_album_tag_relation_picture_tag` (`tag_id`),
  CONSTRAINT `FK_album_tag_relation_picture_album` FOREIGN KEY (`album_id`) REFERENCES `picture_album` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_album_tag_relation_picture_tag` FOREIGN KEY (`tag_id`) REFERENCES `picture_tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图册分类表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.message
CREATE TABLE IF NOT EXISTS `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pm_id` int(11) NOT NULL DEFAULT '0' COMMENT '发布者ID',
  `message_content` text NOT NULL COMMENT '发布内容',
  `message_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `message_to` int(11) NOT NULL DEFAULT '0' COMMENT '发送对象【0：全部；其他：对应的用户id】',
  PRIMARY KEY (`id`),
  KEY `FK_message_pm` (`pm_id`),
  CONSTRAINT `FK_message_pm` FOREIGN KEY (`pm_id`) REFERENCES `pm` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群发、私信消息记录表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.message_already_read
CREATE TABLE IF NOT EXISTS `message_already_read` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `m_mess_id` int(11) NOT NULL DEFAULT '0' COMMENT '群发消息ID',
  `read_pm_id` int(11) NOT NULL DEFAULT '0' COMMENT '已阅读人ID',
  PRIMARY KEY (`id`),
  KEY `FK_message_already_read_message` (`m_mess_id`),
  KEY `FK_message_already_read_pm` (`read_pm_id`),
  CONSTRAINT `FK_message_already_read_message` FOREIGN KEY (`m_mess_id`) REFERENCES `message` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_message_already_read_pm` FOREIGN KEY (`read_pm_id`) REFERENCES `pm` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群发、私信消息已读表';

-- Data exporting was unselected.
-- Dumping structure for event picture_management_system.message_auto_clear
DELIMITER //
CREATE DEFINER=`root`@`%` EVENT `message_auto_clear` ON SCHEDULE EVERY 2 MINUTE STARTS '2020-06-01 00:00:00' ON COMPLETION NOT PRESERVE ENABLE COMMENT '自动清空两个月前的消息' DO BEGIN

delete From message where DATE(message.message_time) <= DATE(DATE_SUB(NOW(),INTERVAL 2 month));

END//
DELIMITER ;

-- Dumping structure for table picture_management_system.message_recycle
CREATE TABLE IF NOT EXISTS `message_recycle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) NOT NULL DEFAULT '0' COMMENT '要屏蔽的消息',
  `hidden_pm_id` int(11) NOT NULL DEFAULT '0' COMMENT '选择隐藏的用户',
  PRIMARY KEY (`id`),
  KEY `FK_message_recycle_message` (`message_id`),
  KEY `FK_message_recycle_pm` (`hidden_pm_id`),
  CONSTRAINT `FK_message_recycle_message` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_message_recycle_pm` FOREIGN KEY (`hidden_pm_id`) REFERENCES `pm` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户屏蔽消息表，彻底删除功能由发布人完成';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.picture_album
CREATE TABLE IF NOT EXISTS `picture_album` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pm_id` int(11) NOT NULL DEFAULT '0' COMMENT '作者ID',
  `tittle` varchar(20) NOT NULL DEFAULT '0' COMMENT '标题',
  `picture` varchar(65) NOT NULL DEFAULT '0' COMMENT '图片',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `start_number` int(11) NOT NULL DEFAULT '0' COMMENT '收藏数量',
  `comment_number` int(11) DEFAULT '0' COMMENT '评论数量',
  PRIMARY KEY (`id`),
  KEY `FK_picture_album_pm` (`pm_id`),
  CONSTRAINT `FK_picture_album_pm` FOREIGN KEY (`pm_id`) REFERENCES `pm` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图册表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.picture_album_collection
CREATE TABLE IF NOT EXISTS `picture_album_collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pm_id` int(11) NOT NULL DEFAULT '0' COMMENT '收藏着ID',
  `pa_id` int(11) NOT NULL DEFAULT '0' COMMENT '图册图片ID',
  PRIMARY KEY (`id`),
  KEY `FK_picture_album_collection_picture_album` (`pa_id`),
  KEY `FK_picture_album_collection_pm` (`pm_id`),
  CONSTRAINT `FK_picture_album_collection_picture_album` FOREIGN KEY (`pa_id`) REFERENCES `picture_album` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_picture_album_collection_pm` FOREIGN KEY (`pm_id`) REFERENCES `pm` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图册收藏表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.picture_album_comment
CREATE TABLE IF NOT EXISTS `picture_album_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pa_id` int(11) NOT NULL DEFAULT '0' COMMENT '图册id',
  `pm_id` int(11) NOT NULL DEFAULT '0' COMMENT '评论用户id',
  `content` text NOT NULL COMMENT '评论内容',
  `comment_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`id`),
  KEY `FK_picture_album_comment_picture_album` (`pa_id`),
  KEY `FK_picture_album_comment_pm` (`pm_id`),
  CONSTRAINT `FK_picture_album_comment_picture_album` FOREIGN KEY (`pa_id`) REFERENCES `picture_album` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_picture_album_comment_pm` FOREIGN KEY (`pm_id`) REFERENCES `pm` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图册评论表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.picture_tag
CREATE TABLE IF NOT EXISTS `picture_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(5) NOT NULL COMMENT '图册图片标签名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tag_name` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图册标签表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.pm
CREATE TABLE IF NOT EXISTS `pm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `phone` varchar(11) DEFAULT NULL COMMENT '电话',
  `pm_role` int(11) NOT NULL DEFAULT '1' COMMENT '权限',
  `ban` int(11) NOT NULL DEFAULT '0' COMMENT '账户状态',
  `icon` varchar(100) DEFAULT 'group1/M00/00/00/rBDDUl5bcO2ANCAWAAAGsa4U3Is409.png' COMMENT '用户头像',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`),
  KEY `FK_pm_pm_role` (`pm_role`),
  CONSTRAINT `FK_pm_pm_role` FOREIGN KEY (`pm_role`) REFERENCES `pm_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.pm_inf
CREATE TABLE IF NOT EXISTS `pm_inf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vip` int(11) NOT NULL DEFAULT '0' COMMENT '预留字段',
  `total_volume` int(11) NOT NULL DEFAULT '10' COMMENT '预留字段',
  `used_volume` int(11) NOT NULL DEFAULT '0' COMMENT '预留字段',
  `balance` double NOT NULL DEFAULT '0' COMMENT '预留字段',
  `integral` int(11) NOT NULL DEFAULT '50' COMMENT '预留字段',
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_op_inf_pm` FOREIGN KEY (`id`) REFERENCES `pm` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户详细信息表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.pm_relation
CREATE TABLE IF NOT EXISTS `pm_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '关注（拉黑）发起用户',
  `to_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '被关注（拉黑）的用户',
  `rel_type` enum('1','2') NOT NULL DEFAULT '2' COMMENT '关系类型1拉黑2关注',
  PRIMARY KEY (`id`),
  KEY `FK_pm_relation_pm` (`from_user_id`),
  KEY `FK_pm_relation_pm_2` (`to_user_id`),
  CONSTRAINT `FK_pm_relation_pm` FOREIGN KEY (`from_user_id`) REFERENCES `pm` (`id`),
  CONSTRAINT `FK_pm_relation_pm_2` FOREIGN KEY (`to_user_id`) REFERENCES `pm` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='关注&粉丝表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.pm_role
CREATE TABLE IF NOT EXISTS `pm_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(10) NOT NULL COMMENT '角色',
  `permission` varchar(10) NOT NULL COMMENT '权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.topic
CREATE TABLE IF NOT EXISTS `topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tittle` varchar(20) NOT NULL DEFAULT '0' COMMENT '标题',
  `pm_id` int(11) NOT NULL DEFAULT '0' COMMENT '作者id',
  `picture` varchar(100) NOT NULL DEFAULT '0' COMMENT '图片地址',
  `text` text NOT NULL COMMENT '正文内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `start_number` int(11) NOT NULL DEFAULT '0' COMMENT '收藏数',
  `comment_number` int(11) DEFAULT '0' COMMENT '评论数',
  PRIMARY KEY (`id`),
  KEY `FK_topic_pm` (`pm_id`),
  CONSTRAINT `FK_topic_pm` FOREIGN KEY (`pm_id`) REFERENCES `pm` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='话题表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.topic_collection
CREATE TABLE IF NOT EXISTS `topic_collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pm_id` int(11) NOT NULL DEFAULT '0' COMMENT '收藏着ID',
  `topic_id` int(11) NOT NULL DEFAULT '0' COMMENT '话题ID',
  PRIMARY KEY (`id`),
  KEY `FK_topic_collection_pm` (`pm_id`),
  KEY `FK_topic_collection_topic` (`topic_id`),
  CONSTRAINT `FK_topic_collection_pm` FOREIGN KEY (`pm_id`) REFERENCES `pm` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_topic_collection_topic` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='话题收藏表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.topic_comment
CREATE TABLE IF NOT EXISTS `topic_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL DEFAULT '0' COMMENT '话题id',
  `pm_id` int(11) NOT NULL DEFAULT '0' COMMENT '评论人id',
  `content` text NOT NULL COMMENT '评论内容',
  `comment_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`id`),
  KEY `FK_topic_comment_pm` (`pm_id`),
  KEY `FK_topic_comment_topic` (`topic_id`),
  CONSTRAINT `FK_topic_comment_pm` FOREIGN KEY (`pm_id`) REFERENCES `pm` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_topic_comment_topic` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='话题评论表';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.topic_tag
CREATE TABLE IF NOT EXISTS `topic_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(10) NOT NULL DEFAULT '0' COMMENT '话题标签名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='话题分类标签';

-- Data exporting was unselected.
-- Dumping structure for table picture_management_system.topic_tag_relation
CREATE TABLE IF NOT EXISTS `topic_tag_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_id` int(11) NOT NULL DEFAULT '0' COMMENT '话题id',
  `tt_id` int(11) NOT NULL DEFAULT '0' COMMENT '话题标签id',
  PRIMARY KEY (`id`),
  KEY `FK_topic_tag_relation_topic` (`t_id`),
  KEY `FK_topic_tag_relation_topic_tag` (`tt_id`),
  CONSTRAINT `FK_topic_tag_relation_topic` FOREIGN KEY (`t_id`) REFERENCES `topic` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_topic_tag_relation_topic_tag` FOREIGN KEY (`tt_id`) REFERENCES `topic_tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='话题分类表';

-- Data exporting was unselected.
-- Dumping structure for view picture_management_system.view_of_album
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `view_of_album` (
	`comment_number` INT(11) NULL COMMENT '评论数量',
	`create_time` DATETIME NOT NULL COMMENT '创建时间',
	`icon` VARCHAR(100) NULL COMMENT '用户头像' COLLATE 'utf8mb4_0900_ai_ci',
	`id` INT(11) NOT NULL,
	`nick_name` VARCHAR(50) NULL COMMENT '用户昵称' COLLATE 'utf8mb4_0900_ai_ci',
	`picture` VARCHAR(65) NOT NULL COMMENT '图片' COLLATE 'utf8mb4_0900_ai_ci',
	`pm_id` INT(11) NOT NULL,
	`start_number` INT(11) NOT NULL COMMENT '收藏数量',
	`tittle` VARCHAR(20) NOT NULL COMMENT '标题' COLLATE 'utf8mb4_0900_ai_ci',
	`username` VARCHAR(50) NOT NULL COMMENT '用户名' COLLATE 'utf8mb4_0900_ai_ci'
) ENGINE=MyISAM;

-- Dumping structure for view picture_management_system.view_of_album_comment
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `view_of_album_comment` (
	`comment_time` DATETIME NOT NULL COMMENT '评论时间',
	`content` TEXT NOT NULL COMMENT '评论内容' COLLATE 'utf8mb4_0900_ai_ci',
	`icon` VARCHAR(100) NULL COMMENT '用户头像' COLLATE 'utf8mb4_0900_ai_ci',
	`nick_name` VARCHAR(50) NULL COMMENT '用户昵称' COLLATE 'utf8mb4_0900_ai_ci',
	`pa_id` INT(11) NOT NULL,
	`username` VARCHAR(50) NOT NULL COMMENT '用户名' COLLATE 'utf8mb4_0900_ai_ci'
) ENGINE=MyISAM;

-- Dumping structure for view picture_management_system.view_of_message_mess
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `view_of_message_mess` (
	`id` INT(11) NOT NULL,
	`message_content` TEXT NOT NULL COMMENT '发布内容' COLLATE 'utf8mb4_0900_ai_ci',
	`message_time` DATETIME NOT NULL COMMENT '发布时间',
	`message_to` INT(11) NOT NULL COMMENT '发送对象【0：全部；其他：对应的用户id】',
	`pm_id` INT(11) NOT NULL COMMENT '发布者ID'
) ENGINE=MyISAM;

-- Dumping structure for view picture_management_system.view_of_message_private
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `view_of_message_private` (
	`id` INT(11) NOT NULL,
	`message_content` TEXT NOT NULL COMMENT '发布内容' COLLATE 'utf8mb4_0900_ai_ci',
	`message_time` DATETIME NOT NULL COMMENT '发布时间',
	`message_to` INT(11) NOT NULL COMMENT '发送对象【0：全部；其他：对应的用户id】',
	`pm_id` INT(11) NOT NULL COMMENT '发布者ID'
) ENGINE=MyISAM;

-- Dumping structure for view picture_management_system.view_of_topic
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `view_of_topic` (
	`comment_number` INT(11) NULL COMMENT '评论数',
	`create_time` DATETIME NOT NULL COMMENT '创建时间',
	`icon` VARCHAR(100) NULL COMMENT '用户头像' COLLATE 'utf8mb4_0900_ai_ci',
	`id` INT(11) NOT NULL,
	`nick_name` VARCHAR(50) NULL COMMENT '用户昵称' COLLATE 'utf8mb4_0900_ai_ci',
	`picture` VARCHAR(100) NOT NULL COMMENT '图片地址' COLLATE 'utf8mb4_0900_ai_ci',
	`pm_id` INT(11) NOT NULL,
	`start_number` INT(11) NOT NULL COMMENT '收藏数',
	`text` TEXT NOT NULL COMMENT '正文内容' COLLATE 'utf8mb4_0900_ai_ci',
	`tittle` VARCHAR(20) NOT NULL COMMENT '标题' COLLATE 'utf8mb4_0900_ai_ci',
	`username` VARCHAR(50) NOT NULL COMMENT '用户名' COLLATE 'utf8mb4_0900_ai_ci'
) ENGINE=MyISAM;

-- Dumping structure for view picture_management_system.view_of_topic_comment
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `view_of_topic_comment` (
	`comment_time` DATETIME NOT NULL COMMENT '评论时间',
	`content` TEXT NOT NULL COMMENT '评论内容' COLLATE 'utf8mb4_0900_ai_ci',
	`icon` VARCHAR(100) NULL COMMENT '用户头像' COLLATE 'utf8mb4_0900_ai_ci',
	`nick_name` VARCHAR(50) NULL COMMENT '用户昵称' COLLATE 'utf8mb4_0900_ai_ci',
	`topic_id` INT(11) NOT NULL,
	`username` VARCHAR(50) NOT NULL COMMENT '用户名' COLLATE 'utf8mb4_0900_ai_ci'
) ENGINE=MyISAM;

-- Dumping structure for trigger picture_management_system.picture_album_collection_after_delete
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `picture_album_collection_after_delete` AFTER DELETE ON `picture_album_collection` FOR EACH ROW BEGIN

update picture_album
set start_number = start_number - 1
where id=old.pa_id;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger picture_management_system.picture_album_collection_after_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `picture_album_collection_after_insert` AFTER INSERT ON `picture_album_collection` FOR EACH ROW BEGIN

update picture_album
set start_number = start_number + 1
where id=new.pa_id;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger picture_management_system.picture_album_comment_after_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `picture_album_comment_after_insert` AFTER INSERT ON `picture_album_comment` FOR EACH ROW BEGIN

update picture_album as pa
set pa.comment_number = pa.comment_number + 1
where pa.id = new.pa_id;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger picture_management_system.pm_after_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `pm_after_insert` AFTER INSERT ON `pm` FOR EACH ROW BEGIN

insert into pm_inf values(new.id, default, default, default, default, default);

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger picture_management_system.topic_collection_after_delete
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `topic_collection_after_delete` AFTER DELETE ON `topic_collection` FOR EACH ROW BEGIN

update topic
set start_number = start_number - 1
where id=old.topic_id;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger picture_management_system.topic_collection_after_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `topic_collection_after_insert` AFTER INSERT ON `topic_collection` FOR EACH ROW BEGIN

update topic
set start_number = start_number + 1
where id=new.topic_id;


END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger picture_management_system.topic_comment_after_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `topic_comment_after_insert` AFTER INSERT ON `topic_comment` FOR EACH ROW BEGIN

update topic
set topic.comment_number = topic.comment_number + 1
where topic.id = new.topic_id;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for view picture_management_system.view_of_album
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `view_of_album`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `view_of_album` AS select distinct `pa`.`id` AS `id`,`pm`.`id` AS `pm_id`,`pm`.`username` AS `username`,`pm`.`nick_name` AS `nick_name`,`pm`.`icon` AS `icon`,`pa`.`tittle` AS `tittle`,`pa`.`picture` AS `picture`,`pa`.`create_time` AS `create_time`,`pa`.`start_number` AS `start_number`,`pa`.`comment_number` AS `comment_number` from (`pm` join `picture_album` `pa`) where (`pa`.`pm_id` = `pm`.`id`) order by `pa`.`create_time` desc;

-- Dumping structure for view picture_management_system.view_of_album_comment
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `view_of_album_comment`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `view_of_album_comment` AS select `pm`.`username` AS `username`,`pm`.`nick_name` AS `nick_name`,`pm`.`icon` AS `icon`,`pa`.`id` AS `pa_id`,`pac`.`content` AS `content`,`pac`.`comment_time` AS `comment_time` from ((`picture_album` `pa` join `picture_album_comment` `pac`) join `pm`) where ((`pa`.`id` = `pac`.`pa_id`) and (`pm`.`id` = `pac`.`pm_id`));

-- Dumping structure for view picture_management_system.view_of_message_mess
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `view_of_message_mess`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `view_of_message_mess` AS select `message`.`id` AS `id`,`message`.`pm_id` AS `pm_id`,`message`.`message_content` AS `message_content`,`message`.`message_time` AS `message_time`,`message`.`message_to` AS `message_to` from `message` where (`message`.`message_to` = 0);

-- Dumping structure for view picture_management_system.view_of_message_private
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `view_of_message_private`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `view_of_message_private` AS select `message`.`id` AS `id`,`message`.`pm_id` AS `pm_id`,`message`.`message_content` AS `message_content`,`message`.`message_time` AS `message_time`,`message`.`message_to` AS `message_to` from `message` where (`message`.`message_to` <> 0);

-- Dumping structure for view picture_management_system.view_of_topic
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `view_of_topic`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `view_of_topic` AS select `t`.`id` AS `id`,`pm`.`id` AS `pm_id`,`pm`.`username` AS `username`,`pm`.`nick_name` AS `nick_name`,`pm`.`icon` AS `icon`,`t`.`tittle` AS `tittle`,`t`.`picture` AS `picture`,`t`.`text` AS `text`,`t`.`create_time` AS `create_time`,`t`.`start_number` AS `start_number`,`t`.`comment_number` AS `comment_number` from (`topic` `t` join `pm`) where (`pm`.`id` = `t`.`pm_id`) order by `t`.`create_time` desc;

-- Dumping structure for view picture_management_system.view_of_topic_comment
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `view_of_topic_comment`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `view_of_topic_comment` AS select `pm`.`username` AS `username`,`pm`.`nick_name` AS `nick_name`,`pm`.`icon` AS `icon`,`t`.`id` AS `topic_id`,`tc`.`content` AS `content`,`tc`.`comment_time` AS `comment_time` from ((`topic_comment` `tc` join `topic` `t`) join `pm`) where ((`tc`.`topic_id` = `t`.`id`) and (`tc`.`pm_id` = `pm`.`id`));

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

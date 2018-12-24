-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: hellobaby
-- ------------------------------------------------------
-- Server version	5.6.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE TABLE `t_mem_monitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(45) NOT NULL COMMENT '任务名称',
  `host_name` varchar(45) DEFAULT NULL COMMENT '主机信息',
  `template` varchar(500) NOT NULL COMMENT '消息模板',
  `threshold` double(5,4) NOT NULL COMMENT '阈值',
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  `desc` varchar(45) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `fk_mem_job_idx` (`job_name`),
  KEY `fk_mem_host_idx` (`host_name`),
  CONSTRAINT `fk_mem_host` FOREIGN KEY (`host_name`) REFERENCES `t_host_cfg` (`host_name`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_mem_job` FOREIGN KEY (`job_name`) REFERENCES `t_job_config` (`name`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_cpu_monitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(45) NOT NULL COMMENT '任务名称',
  `host_name` varchar(45) DEFAULT NULL COMMENT '主机名称',
  `template` varchar(500) NOT NULL COMMENT '消息模板',
  `threshold` double(5,4) NOT NULL COMMENT '阈值',
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  `desc` varchar(45) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `fk_cpu_job_idx` (`job_name`),
  KEY `fk_cpu_host_idx` (`host_name`),
  CONSTRAINT `fk_cpu_host` FOREIGN KEY (`host_name`) REFERENCES `t_host_cfg` (`host_name`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_cpu_job` FOREIGN KEY (`job_name`) REFERENCES `t_job_config` (`name`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_dir_monitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(45) NOT NULL COMMENT '任务名词',
  `host_name` varchar(45) DEFAULT NULL COMMENT '主机信息',
  `dir_name` varchar(255) NOT NULL COMMENT '监控目录',
  `template` varchar(500) NOT NULL COMMENT '内容',
  `threshold` double(5,4) NOT NULL COMMENT '阈值',
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  `desc` varchar(45) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `fk_job_name_idx` (`job_name`),
  KEY `fk_dir_host_idx` (`host_name`),
  CONSTRAINT `fk_dir_host` FOREIGN KEY (`host_name`) REFERENCES `t_host_cfg` (`host_name`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_dir_monitor_job_name` FOREIGN KEY (`job_name`) REFERENCES `t_job_config` (`name`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `t_app_config`
--

DROP TABLE IF EXISTS `t_app_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_app_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` varchar(45) NOT NULL COMMENT '模块',
  `key` varchar(45) NOT NULL COMMENT '名',
  `value` varchar(200) DEFAULT NULL COMMENT '值',
  `desc` varchar(45) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_datasource`
--

DROP TABLE IF EXISTS `t_datasource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_datasource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` varchar(45) NOT NULL COMMENT '模块名称',
  `datasource` varchar(45) NOT NULL COMMENT '数据源名称',
  `url` varchar(250) NOT NULL COMMENT '数据源URL',
  `username` varchar(45) NOT NULL COMMENT '用户名',
  `encrypt_password` varchar(100) DEFAULT NULL COMMENT '加密密码',
  `properties` varchar(250) DEFAULT NULL COMMENT '附加属性',
  `desc` varchar(45) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `datasource_UNIQUE` (`datasource`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_event_mail`
--

DROP TABLE IF EXISTS `t_event_mail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_event_mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` varchar(45) NOT NULL COMMENT '模块名称',
  `datasource` varchar(45) NOT NULL,
  `job_name` varchar(45) NOT NULL,
  `sql` varchar(1000) DEFAULT NULL COMMENT 'SQL事件',
  `subject` varchar(45) NOT NULL COMMENT '主题',
  `template` varchar(500) NOT NULL COMMENT '内容',
  `tos` varchar(250) NOT NULL COMMENT '收件人列表',
  `ccs` varchar(250) DEFAULT NULL COMMENT '抄送人列表',
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  `desc` varchar(45) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `fk_datasource_idx` (`datasource`),
  KEY `fk_job_name_idx` (`job_name`),
  CONSTRAINT `fk_email_datasource` FOREIGN KEY (`datasource`) REFERENCES `t_datasource` (`datasource`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_email_job_name` FOREIGN KEY (`job_name`) REFERENCES `t_job_config` (`name`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_event_mail_attachment`
--

DROP TABLE IF EXISTS `t_event_mail_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_event_mail_attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mail_id` int(11) NOT NULL COMMENT '邮件Id',
  `datasource` varchar(45) NOT NULL COMMENT '数据源',
  `sql` varchar(1000) NOT NULL COMMENT 'SQL',
  `attachment_type` varchar(45) NOT NULL COMMENT '附件类型',
  `attachment_name` varchar(45) DEFAULT NULL COMMENT '附件类型',
  `desc` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_datasource_idx` (`datasource`),
  KEY `fk_mail_id_idx` (`mail_id`),
  CONSTRAINT `fk_datasource` FOREIGN KEY (`datasource`) REFERENCES `t_datasource` (`datasource`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_mail_id` FOREIGN KEY (`mail_id`) REFERENCES `t_event_mail` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_event_monitor`
--

DROP TABLE IF EXISTS `t_event_monitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_event_monitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` varchar(45) NOT NULL COMMENT '模块名称',
  `level` varchar(45) NOT NULL COMMENT '事件级别',
  `datasource` varchar(45) NOT NULL,
  `job_name` varchar(45) NOT NULL,
  `sql` varchar(1000) NOT NULL COMMENT 'SQL事件',
  `notify_type` varchar(45) NOT NULL COMMENT '通知类型',
  `template` varchar(500) DEFAULT NULL COMMENT 'thymeleaf text 消息模板',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用',
  `desc` varchar(45) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `fk_datasource_idx` (`datasource`),
  KEY `fk_job_name_idx` (`job_name`),
  CONSTRAINT `fk_emonitor_datasource` FOREIGN KEY (`datasource`) REFERENCES `t_datasource` (`datasource`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_emonitor_job_name` FOREIGN KEY (`job_name`) REFERENCES `t_job_config` (`name`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_job_config`
--

DROP TABLE IF EXISTS `t_job_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_job_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL COMMENT '任务名称',
  `class` varchar(256) NOT NULL COMMENT '全路径类名',
  `corn_expression` varchar(128) NOT NULL COMMENT 'corn表达式',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `desc` varchar(45) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


CREATE TABLE `t_host_cfg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `host_name` varchar(45) NOT NULL COMMENT '主机名',
  `ip` varchar(45) NOT NULL COMMENT '主机地址',
  `port` int(11) NOT NULL COMMENT '服务端口',
  `enable` tinyint(1) NOT NULL COMMENT '启用',
  `desc` varchar(45) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `host_name_UNIQUE` (`host_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主机服务配置表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-12 23:43:01

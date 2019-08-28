/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 127.0.0.1
 Source Database       : datax-web

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 07/05/2019 16:00:20 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `datax_plugin`
-- ----------------------------
DROP TABLE IF EXISTS `datax_plugin`;
CREATE TABLE `datax_plugin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plugin_type` varchar(32) DEFAULT NULL COMMENT '插件类型，reader writer',
  `plugin_name` varchar(255) NOT NULL COMMENT '插件名，用作主键',
  `template_json` text COMMENT 'json模板',
  `comments` varchar(1000) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='datax插件信息';

-- ----------------------------
--  Records of `datax_plugin`
-- ----------------------------
BEGIN;
INSERT INTO `datax_plugin` VALUES ('1', 'reader', 'streamreader', '', '内存读取'), ('2', 'writer', 'streamwriter', null, '内存写'), ('3', 'reader', 'mysqlreader', null, 'mysql读取'), ('4', 'writer', 'mysqlwriter', null, 'myysql写'), ('5', 'reader', 'oraclereader', null, 'oracle读取');
COMMIT;

-- ----------------------------
--  Table structure for `job_config`
-- ----------------------------
DROP TABLE IF EXISTS `job_config`;
CREATE TABLE `job_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '作业名',
  `job_group` varchar(255) DEFAULT NULL COMMENT '分组',
  `config_json` varchar(5000) DEFAULT NULL,
  `config_json_param` varchar(5000) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '作业描述信息',
  `label` varchar(255) DEFAULT NULL COMMENT '标签（读插件、写插件)',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  `create_by` int(11) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='作业配置表';

-- ----------------------------
--  Records of `job_config`
-- ----------------------------
BEGIN;
INSERT INTO `job_config` VALUES ('3', null, '同步apolloconfigdb_dev(appnamespace)', 'test', '{\n  \"job\": {\n    \"setting\": {\n      \"speed\": {\n        \"channel\": 3\n      },\n      \"errorLimit\": {\n        \"record\": 0,\n        \"percentage\": 0.02\n      }\n    },\n    \"content\": [\n      {\n        \"reader\": {\n          \"name\": \"mysqlreader\",\n          \"parameter\": {\n            \"username\": \"root\",\n            \"password\": \"123456\",\n            \"column\": [\n              \"id\",\n              \"Name\"\n            ],\n            \"splitPk\": \"id\",\n            \"connection\": [\n              {\n                \"table\": [\n                  \"appnamespace\"\n                ],\n                \"jdbcUrl\": [\n                  \"jdbc:mysql://127.0.0.1:3306/datax_test\"\n                ]\n              }\n            ]\n          }\n        },\n        \"writer\": {\n          \"name\": \"mysqlwriter\",\n          \"parameter\": {\n            \"writeMode\": \"insert\",\n            \"username\": \"root\",\n            \"password\": \"123456\",\n            \"column\": [\n              \"id\",\n              \"Name\"\n            ],\n            \"session\": [\n              \"set session sql_mode=\'ANSI\'\"\n            ],\n            \"preSql\": [\n              \"delete from appnamespace\"\n            ],\n            \"connection\": [\n              {\n                \"jdbcUrl\": \"jdbc:mysql://127.0.0.1:3306/datax_test1\",\n                \"table\": [\n                  \"appnamespace\"\n                ]\n              }\n            ]\n          }\n        }\n      }\n    ]\n  }\n}', null, null, '2019-07-05 15:34:46', '1', null, '2019-06-17 22:05:31', null);
COMMIT;

-- ----------------------------
--  Table structure for `job_log`
-- ----------------------------
DROP TABLE IF EXISTS `job_log`;
CREATE TABLE `job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_id` bigint(20) NOT NULL COMMENT '抽取任务，主键ID',
  `log_file_path` varchar(255) DEFAULT NULL COMMENT '日志文件路径',
  `update_date` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  `create_by` int(11) DEFAULT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='抽取日志记录表';

-- ----------------------------
--  Records of `job_log`
-- ----------------------------
BEGIN;
INSERT INTO `job_log` VALUES ('16', '3', '/Users/sky/develop/workspace_github/skycloud-datax/data/applogs/datax-web/3_1562312097720.log', '2019-07-05 15:34:58', '1', null, '2019-07-05 15:34:58', null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

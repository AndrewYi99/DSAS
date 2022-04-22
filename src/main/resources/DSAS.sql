/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 127.0.0.1:3306
 Source Schema         : DSAS

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 12/04/2022 19:02:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dsas_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `dsas_evaluation`;
CREATE TABLE `dsas_evaluation` (
  `evaluation_id` bigint NOT NULL COMMENT '评价表主键',
  `user_id` int NOT NULL COMMENT '当前评论的用户id',
  `food_id` int DEFAULT NULL COMMENT '当前菜品id',
  `evaluation_category` int NOT NULL COMMENT '1菜品，2员工，3其他',
  `content` varchar(255) DEFAULT NULL COMMENT '评价内容',
  `likes` int NOT NULL COMMENT '点赞数量',
  `state` varchar(16) NOT NULL COMMENT '审核状态 enable 有效 ,disable已禁用',
  `disable_reason` varchar(255) DEFAULT NULL COMMENT '禁用理由',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `photo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '评价照片',
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '管理员回答内容',
  PRIMARY KEY (`evaluation_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of dsas_evaluation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for dsas_food
-- ----------------------------
DROP TABLE IF EXISTS `dsas_food`;
CREATE TABLE `dsas_food` (
  `id` int NOT NULL COMMENT 'food主键id',
  `food_name` varchar(255) NOT NULL COMMENT '菜品名称',
  `food_description` varchar(500) DEFAULT NULL COMMENT ' 菜品描述',
  `food_image` varchar(500) NOT NULL COMMENT '菜品图片',
  `price` int NOT NULL COMMENT '菜品价格',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_recommend` int NOT NULL COMMENT '是否为用户推荐：1推荐 2不推荐',
  `status` int NOT NULL COMMENT '菜品状态',
  `is_today_food` int DEFAULT NULL COMMENT '是否设置为今日菜品',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of dsas_food
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for dsas_operationLog
-- ----------------------------
DROP TABLE IF EXISTS `dsas_operationLog`;
CREATE TABLE `dsas_operationLog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `operation_time` datetime DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of dsas_operationLog
-- ----------------------------
BEGIN;
INSERT INTO `dsas_operationLog` VALUES (1, 1, '127.0.0.1:8090', '插入方法', 'test', 'test1', '2022-04-09 12:56:10', '插入成功');
INSERT INTO `dsas_operationLog` VALUES (22, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-10 11:59:49', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (23, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-10 12:11:30', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (24, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-10 12:23:45', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (25, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-10 20:27:57', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (26, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-10 22:06:27', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (27, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-10 22:08:09', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (28, 12, '0:0:0:0:0:0:0:1', '登陆', '进入后台系统', '管理员模块', '2022-04-10 22:16:45', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (29, 12, '0:0:0:0:0:0:0:1', '登陆', '进入后台系统', '管理员模块', '2022-04-10 22:18:53', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (30, 12, '0:0:0:0:0:0:0:1', '登陆', '进入后台系统', '管理员模块', '2022-04-10 22:22:57', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (31, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 08:04:17', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (32, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 08:09:26', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (33, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 09:32:25', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (34, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 10:06:41', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (35, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 10:09:32', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (36, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 10:17:19', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (37, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 10:19:09', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (38, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 10:26:40', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (39, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 10:29:26', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (40, 12, '0:0:0:0:0:0:0:1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 10:32:30', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (41, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 10:34:09', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (42, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 11:04:32', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (43, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 11:23:43', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (44, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 13:58:12', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (45, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 14:03:54', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (46, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 14:26:51', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (47, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 14:29:12', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (48, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 15:17:04', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (49, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 15:18:01', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (50, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 15:18:54', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (51, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 15:21:01', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (52, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 15:23:45', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (53, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 17:05:35', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (54, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 17:31:37', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (55, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 19:42:14', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (56, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 19:45:40', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (57, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 20:13:05', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (58, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 20:29:53', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (59, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 20:42:46', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (60, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 20:58:16', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (61, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 20:58:58', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (62, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 21:20:14', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (63, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 21:31:08', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (64, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 21:37:12', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (65, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 21:47:09', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (66, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 21:51:51', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (67, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 22:21:25', '执行进入后台系统功能成功!');
INSERT INTO `dsas_operationLog` VALUES (68, 12, '127.0.1.1', '登陆', '进入后台系统', '管理员模块', '2022-04-11 22:22:56', '执行进入后台系统功能成功!');
COMMIT;

-- ----------------------------
-- Table structure for dsas_pwd
-- ----------------------------
DROP TABLE IF EXISTS `dsas_pwd`;
CREATE TABLE `dsas_pwd` (
  `id` int NOT NULL AUTO_INCREMENT,
  `md5_str` varchar(255) NOT NULL COMMENT '加密后的密码',
  `origin_pwd` varchar(255) NOT NULL COMMENT '加密前的密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of dsas_pwd
-- ----------------------------
BEGIN;
INSERT INTO `dsas_pwd` VALUES (1, 'ffsf', '1234');
INSERT INTO `dsas_pwd` VALUES (2, '0N9ySiIoxrWvYcPPbCP+tQ==', '12345678');
COMMIT;

-- ----------------------------
-- Table structure for dsas_user
-- ----------------------------
DROP TABLE IF EXISTS `dsas_user`;
CREATE TABLE `dsas_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) NOT NULL COMMENT '用户名（用于登陆）',
  `password` varchar(255) NOT NULL COMMENT '用户密码,md5加密、',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1234@163.com' COMMENT '用户邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `role` int NOT NULL DEFAULT '1' COMMENT '用户权限（角色）1 普通用户 2.普通管理员 3. 超级管理员',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '用户启用状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of dsas_user
-- ----------------------------
BEGIN;
INSERT INTO `dsas_user` VALUES (1, 'test', '11', '11', '111', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-06 23:05:33', '2022-04-11 09:35:35');
INSERT INTO `dsas_user` VALUES (12, 'test123', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 2, 1, '2022-04-07 15:25:13', '2022-04-08 19:25:28');
INSERT INTO `dsas_user` VALUES (13, 'test124', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:42');
INSERT INTO `dsas_user` VALUES (15, 'test125', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:43');
INSERT INTO `dsas_user` VALUES (16, 'test126', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:44');
INSERT INTO `dsas_user` VALUES (17, 'test127', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:44');
INSERT INTO `dsas_user` VALUES (18, 'test128', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:45');
INSERT INTO `dsas_user` VALUES (19, 'test129', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:45');
INSERT INTO `dsas_user` VALUES (20, 'test130', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:46');
INSERT INTO `dsas_user` VALUES (21, 'test131', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:47');
INSERT INTO `dsas_user` VALUES (22, 'test132', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:47');
INSERT INTO `dsas_user` VALUES (23, 'test133', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:48');
INSERT INTO `dsas_user` VALUES (24, 'test134', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:48');
INSERT INTO `dsas_user` VALUES (25, 'test135', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:49');
INSERT INTO `dsas_user` VALUES (26, 'test136', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:49');
INSERT INTO `dsas_user` VALUES (27, 'test137', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:50');
INSERT INTO `dsas_user` VALUES (28, 'test138', '0N9ySiIoxrWvYcPPbCP+tQ==', '这是昵称', 'test123.qq.com', 'https://c.wallhere.com/photos/14/32/1920x1200_px_animals_cats-1906067.jpg!d', 1, 1, '2022-04-07 15:25:13', '2022-04-11 20:13:52');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

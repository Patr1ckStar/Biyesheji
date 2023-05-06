/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : auto

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 06/05/2023 12:00:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for apply
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `reason` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `end_time` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of apply
-- ----------------------------
INSERT INTO `apply` VALUES (9, '我要回家', 16, '0', '2023-03-11 16:17:28', '2023-03-14 00:00:00', '1');
INSERT INTO `apply` VALUES (12, '生病', 19, '0', '2023-03-29 23:23:32', '2023-03-31 00:00:00', '1');
INSERT INTO `apply` VALUES (14, 'buxiangshangle', 19, '0', '2023-03-29 23:32:03', '2023-04-01 00:00:00', '0');
INSERT INTO `apply` VALUES (16, '111', 19, '0', '2023-03-15 00:00:00', '2023-03-24 00:00:00', '1');
INSERT INTO `apply` VALUES (17, '23321', 19, '0', '2023-03-05 00:00:00', '2023-03-25 00:00:00', '1');
INSERT INTO `apply` VALUES (18, 'xiuxi', 21, '0', '2023-03-31 00:00:00', '2023-05-05 00:00:00', '1');
INSERT INTO `apply` VALUES (19, '我要出差', 2, '1', '2023-05-12 00:00:00', '2023-05-13 00:00:00', '1');
INSERT INTO `apply` VALUES (21, '123', 21, '0', '2023-05-05 00:00:00', '2023-05-11 00:00:00', '0');
INSERT INTO `apply` VALUES (22, '123', 21, '1', '2023-05-12 00:00:00', '2023-05-18 00:00:00', '0');

-- ----------------------------
-- Table structure for calendar
-- ----------------------------
DROP TABLE IF EXISTS `calendar`;
CREATE TABLE `calendar`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `current_day` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `month` int NULL DEFAULT NULL,
  `event_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1--正常 2--请假  其他缺勤',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of calendar
-- ----------------------------
INSERT INTO `calendar` VALUES (27, '2023-03-26', 21, '测试', 3, '1');
INSERT INTO `calendar` VALUES (28, '2023-03-27', 21, '测hi', 3, '1');
INSERT INTO `calendar` VALUES (29, '2023-02-07', 21, '今天好了', 2, '1');
INSERT INTO `calendar` VALUES (30, '2023-03-11', 21, '哈哈哈', 3, '1');
INSERT INTO `calendar` VALUES (31, '2023-03-28', 17, '111', 3, '1');
INSERT INTO `calendar` VALUES (32, '2023-03-28', 16, '22', 3, '1');
INSERT INTO `calendar` VALUES (33, '2023-03-29', 19, NULL, 3, '1');
INSERT INTO `calendar` VALUES (34, '2023-03-30', 21, 'qqq', 4, '1');
INSERT INTO `calendar` VALUES (35, '2023-03-30', 16, NULL, 3, '1');
INSERT INTO `calendar` VALUES (36, '2023-03-30', 22, '9876554', 4, '1');
INSERT INTO `calendar` VALUES (37, '2023-03-31', 21, NULL, 4, '1');
INSERT INTO `calendar` VALUES (41, '2023-04-01', 21, NULL, 4, '1');
INSERT INTO `calendar` VALUES (42, '2023-04-02', 2, NULL, 4, '1');
INSERT INTO `calendar` VALUES (43, '2023-04-26', 21, NULL, 4, '1');
INSERT INTO `calendar` VALUES (44, '2023-05-04', 1, NULL, 5, '1');

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名',
  `create_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `remark` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'dept' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES (6, '研发部', '2023-02-07 08:55:28', 'xinafabumen');
INSERT INTO `dept` VALUES (7, '测试部', '2023-02-07 09:27:09', '这是测试的');
INSERT INTO `dept` VALUES (8, '测开部', '2023-02-07 09:37:52', '这是测试开发部门');

-- ----------------------------
-- Table structure for invite
-- ----------------------------
DROP TABLE IF EXISTS `invite`;
CREATE TABLE `invite`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `real_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系方式',
  `education` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学历',
  `image_url` varchar(230) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '照片',
  `remark` varchar(130) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述信息',
  `is_hire` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否录用',
  `create_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `interview_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '面试时间',
  `type` int NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userid` int NULL DEFAULT NULL,
  `gongzi` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'invite' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of invite
-- ----------------------------
INSERT INTO `invite` VALUES (8, '小王', '1998384833', '北京市', 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-03-11//33a0df85-ad28-4165-8e77-641dd94fd5bc_1.png', '111', '1', '2023-03-11 16:28:12', '2023-03-11 16:28:11', 1, 18, NULL, NULL, NULL, NULL);
INSERT INTO `invite` VALUES (9, '1', '1', '1', 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-03-28//cbecb371-8df0-4139-8ac9-7da8bff19adf_Snipaste_2023-03-13_19-48-34.png', '22', NULL, '2023-03-28 23:34:09', '2023-03-28 23:34:08', 2, 20, NULL, NULL, NULL, NULL);
INSERT INTO `invite` VALUES (10, '王五', '199838483833', '本科', 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-03-28//b9b9f9dc-fa12-47e3-81e0-16ec34db6679_Snipaste_2023-03-13_19-49-26.png', '这是很好的', '1', '2023-03-28 23:59:12', '2023-03-28 23:59:11', 1, 32, NULL, NULL, NULL, NULL);
INSERT INTO `invite` VALUES (11, '张三', '19989938433', '博士', 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-03-28//cbe51e0b-622b-44af-803b-04a0a6abbfab_Snipaste_2023-03-25_22-59-33.png', '再见', NULL, '2023-03-28 23:59:30', '2023-03-21 14:54:30', 2, 50, NULL, NULL, NULL, NULL);
INSERT INTO `invite` VALUES (12, '小红', '12323', '奥琦玮', 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-03-30//baedcc11-e706-4b38-81ce-51c8d65049d9_5a16160c6c69588a32fc6998e6fa959b.jpeg', '二', '1', '2023-03-30 09:17:17', '2023-04-01 10:01:01', 1, 60, NULL, NULL, NULL, NULL);
INSERT INTO `invite` VALUES (13, '2312', '1231515', NULL, '', '2131', NULL, '2023-04-22 19:15:53', '2023-04-15 00:00:00', 2, 41, NULL, NULL, 23, '1650.0');
INSERT INTO `invite` VALUES (15, '白骨精', '13511145444', '硕士', 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-04-22//549b0742-e832-467e-8d54-70156365f13f_09fa513d269759ee1631ab86b8fb43166d22df1e.jpg', '1020', '1', '2023-04-22 19:36:24', '2023-04-22 19:35:18', 1, 26, '1', '21345@qq.com', NULL, NULL);
INSERT INTO `invite` VALUES (16, '白骨精', '2', NULL, '', '132', NULL, '2023-04-22 21:50:52', '2023-04-20 00:00:00', 2, NULL, NULL, NULL, 25, '5280.0');

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary_one` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary_two` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary_three` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary_four` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary_five` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES (1, '董事长', '100', '200', '300', '5000', '500');
INSERT INTO `job` VALUES (3, '操作工', '20', '10', '33333', '6666', NULL);
INSERT INTO `job` VALUES (5, '组长', '10', '100', '30', '8000', NULL);
INSERT INTO `job` VALUES (10, '程序员', '100', '200', '100', '10000', NULL);
INSERT INTO `job` VALUES (11, '测试', '100', '200', '200', '10000', NULL);
INSERT INTO `job` VALUES (12, '清洁工', '100', '400', '200', '3000', NULL);
INSERT INTO `job` VALUES (13, '研发', '100', '100', '100', '10000', NULL);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `notice` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `document_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sender_id` int NOT NULL,
  `receiver_id` int NOT NULL,
  PRIMARY KEY (`id`, `notice`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (2, '今天晚上跳舞', 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2022-04-03//aec04713-6238-4ca5-842e-12e5eb883674_基于政企平台的人事管理OA系统的研究设计与实现.doc', 1, 0);
INSERT INTO `notice` VALUES (3, '今天晚上开会', NULL, 1, 0);
INSERT INTO `notice` VALUES (7, '马上回来加班11', NULL, 1, 16);
INSERT INTO `notice` VALUES (8, '今天晚上一起吃饭11', NULL, 1, 16);
INSERT INTO `notice` VALUES (11, '大家看下这个项目', 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-02-07//c663ab9f-3f9d-4c39-8e0b-b925615c9629_预选题目(2).docx', 1, 16);
INSERT INTO `notice` VALUES (12, '今天晚上吃饭', 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-03-11//d5489fde-6328-44fd-8eb5-7d0ccddda772_核算报告.docx', 1, 21);
INSERT INTO `notice` VALUES (13, '1231', NULL, 1, 21);
INSERT INTO `notice` VALUES (17, '231', NULL, 2, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_id` int NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `real_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_type` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary_one` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary_two` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary_three` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary_five` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary_four` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `year_bonus` int NULL DEFAULT NULL,
  `image_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dept_id` int NULL DEFAULT NULL,
  `jixiao` int NULL DEFAULT 0,
  `education` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '12345', '18809878132', '超管', '19998834@qq.com', 1, '1', '超管', '1', '221', '1000', '1001', '-340', '5000', 52882, 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-03-30//f11dc7c7-2cc5-4576-8376-b5c220d093de_c8bcb9bc0f05a73fa7be9f2a0b74dd4a.jpeg', 6, 1000, '博士', 18);
INSERT INTO `user` VALUES (2, 'wangqiang', '12345', '18921333222', '13', '1233454@qq.com', 3, '1', '王强', '3', '4020', '11', '400', '-60', '5000', 9371, 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2022-04-03//90286b20-937c-462b-8bc2-9c026ebaf8dc_89.png', 6, 0, '博士', 60);
INSERT INTO `user` VALUES (16, 'xiaolan', '12345677', '19988483378', '', '29798120@qq.com', 5, '2', '小兰', '2', '2', '1', '2', '-80', '5000', 5625, 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-03-11//4402ea12-891d-4731-89d2-6abeef20ce2e_1.png', 6, 700, '本科', 30);
INSERT INTO `user` VALUES (18, 'xiaoming', '11111', '18888880888', 'qwe', '88888@qq.com', 11, '1', '小明', '2', '21', '1', '12', '-380', '5000', 4654, 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-03-29//e245fcf0-e9db-4a6b-8760-ef61ddc4181e_16pic_1309482_b.jpg', 6, 0, '本科', 24);
INSERT INTO `user` VALUES (19, 'xiaohei', '123123', '13123141234', '1233', '1040058894@qq.com', 5, '1', '小黑', '2', '21', '12', '45', '-380', '5000', 4698, 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-03-29//8f3905e8-96c2-407f-8334-acfe039c1abf_5a16160c6c69588a32fc6998e6fa959b.jpeg', 7, 0, '本科', 33);
INSERT INTO `user` VALUES (21, 'xiaohong', '1234567', '18819229211', 'qwewq', '1040058894@qq.com', 10, '1', '小宏', '2', '12', '12', '1231', '-260', '5000', 5995, 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-04-26//2a16fe43-f3db-4b64-8443-a53aeeea23cb_messi.jpg', 7, 0, '硕士', 36);
INSERT INTO `user` VALUES (22, 'sun', '123456', '19811012231', NULL, '12345@163.com', 10, '1', '孙悟空', '2', '100', '200', '100', '-310', '10000', 10090, 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2023-04-26//0dd1e406-cffb-4905-8dff-f2cf025c833a_辛烷.png', 6, 0, '硕士', 43);

SET FOREIGN_KEY_CHECKS = 1;

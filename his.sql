/*
 Navicat MySQL Data Transfer

 Source Server         : 2his
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : his

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 21/06/2019 15:27:45
*/

/**drop DATABASE his;*/
CREATE DATABASE his;
use his;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bill_record
-- ----------------------------
DROP TABLE IF EXISTS `bill_record`;
CREATE TABLE `bill_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `medical_record_id` int(11) NOT NULL,
  `type` varchar(20)  NOT NULL,
  `print_status` int(11) NULL DEFAULT 0,
  `cost` float NOT NULL,
  `should_pay` float NOT NULL DEFAULT 0,
  `truely_pay` float NOT NULL DEFAULT 0,
  `retail_fee` float NOT NULL DEFAULT 0,
  `user_id` int(11) NOT NULL,
  `create_time` varchar(50)  NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for common_diagnosis
-- ----------------------------
DROP TABLE IF EXISTS `common_diagnosis`;
CREATE TABLE `common_diagnosis`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255)  NOT NULL,
  `content` text  NOT NULL,
  `user_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for daily_collect
-- ----------------------------
DROP TABLE IF EXISTS `daily_collect`;
CREATE TABLE `daily_collect`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_time` varchar(50)  NULL DEFAULT NULL,
  `end_time` varchar(50)  NULL DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for daily_detail
-- ----------------------------
DROP TABLE IF EXISTS `daily_detail`;
CREATE TABLE `daily_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `daily_collect_id` int(11) NOT NULL,
  `bill_record_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` int(11) NOT NULL,
  `pinyin` varchar(50)  NOT NULL,
  `name` varchar(50)  NOT NULL,
  `type` varchar(30)  NOT NULL,
  `classification_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, 'XXGNK', '心血管内科', '临床科室', 1);
INSERT INTO `department` VALUES (2, 'SJNK', '神经内科', '临床科室', 1);
INSERT INTO `department` VALUES (3, 'PTNK', '普通内科', '临床科室', 1);
INSERT INTO `department` VALUES (4, 'XHNK', '消化内科', '临床科室', 1);
INSERT INTO `department` VALUES (5, 'HXNK', '呼吸内科', '临床科室', 1);
INSERT INTO `department` VALUES (6, 'NFMK', '内分泌科', '临床科室', 1);
INSERT INTO `department` VALUES (7, 'SBNK', '肾病内科', '临床科室', 1);
INSERT INTO `department` VALUES (8, 'XYNK', '血液内科', '临床科室', 1);
INSERT INTO `department` VALUES (9, 'GRNK', '感染内科', '临床科室', 1);
INSERT INTO `department` VALUES (10, 'LNBNK', '老年病内科', '临床科室', 1);
INSERT INTO `department` VALUES (11, 'FSMYNK', '风湿免疫内科', '临床科室', 1);
INSERT INTO `department` VALUES (12, 'TXK', '透析科', '临床科室', 1);
INSERT INTO `department` VALUES (13, 'BTFYK', '变态反应科', '临床科室', 1);
INSERT INTO `department` VALUES (14, 'PTWK', '普通外科', '临床科室', 2);
INSERT INTO `department` VALUES (15, 'MNWK', '泌尿外科', '临床科室', 2);
INSERT INTO `department` VALUES (16, 'SJWK', '神经外科', '临床科室', 2);
INSERT INTO `department` VALUES (17, 'XWK', '胸外科', '临床科室', 2);
INSERT INTO `department` VALUES (18, 'ZXWK', '整形外科', '临床科室', 2);
INSERT INTO `department` VALUES (19, 'GCWK', '肛肠外科', '临床科室', 2);
INSERT INTO `department` VALUES (20, 'GDWK', '肝胆外科', '临床科室', 2);
INSERT INTO `department` VALUES (21, 'RXWK', '乳腺外科', '临床科室', 2);
INSERT INTO `department` VALUES (22, 'XXGWK', '心血管外科', '临床科室', 2);
INSERT INTO `department` VALUES (23, 'XZWK', '心脏外科', '临床科室', 2);
INSERT INTO `department` VALUES (24, 'QGYZ', '器官移植', '临床科室', 2);
INSERT INTO `department` VALUES (25, 'WCWK', '微创外科', '临床科室', 2);
INSERT INTO `department` VALUES (26, 'GNSJWK', '功能神经外科', '临床科室', 2);
INSERT INTO `department` VALUES (27, 'XTWK', '腺体外科', '临床科室', 2);
INSERT INTO `department` VALUES (28, 'EKZH', '儿科综合', '临床科室', 3);
INSERT INTO `department` VALUES (29, 'XEWK', '小儿外科', '临床科室', 3);
INSERT INTO `department` VALUES (30, 'ETBJK', '儿童保健科', '临床科室', 3);
INSERT INTO `department` VALUES (31, 'XSEK', '新生儿科', '临床科室', 3);
INSERT INTO `department` VALUES (32, 'XEGK', '小儿骨科', '临床科室', 3);
INSERT INTO `department` VALUES (33, 'XESJNK', '小儿神经内科', '临床科室', 3);
INSERT INTO `department` VALUES (34, 'XEHXK', '小儿呼吸科', '临床科室', 3);
INSERT INTO `department` VALUES (35, 'XEXYK', '小儿血液科', '临床科室', 3);
INSERT INTO `department` VALUES (36, 'XEEBHK', '小儿耳鼻喉科', '临床科室', 3);
INSERT INTO `department` VALUES (37, 'XEXNK', '小儿心内科', '临床科室', 3);
INSERT INTO `department` VALUES (38, 'XEKFK', '小儿康复科', '临床科室', 3);
INSERT INTO `department` VALUES (39, 'XEJSK', '小儿精神科', '临床科室', 3);
INSERT INTO `department` VALUES (40, 'XESNK', '小儿肾内科', '临床科室', 3);
INSERT INTO `department` VALUES (41, 'XEXHK', '小儿消化科', '临床科室', 3);
INSERT INTO `department` VALUES (42, 'XEPFK', '小儿皮肤科', '临床科室', 3);
INSERT INTO `department` VALUES (43, 'XEJZK', '小儿急诊科', '临床科室', 3);
INSERT INTO `department` VALUES (44, 'XENFMK', '小儿内分泌科', '临床科室', 3);
INSERT INTO `department` VALUES (45, 'XEMNWK', '小儿泌尿外科', '临床科室', 3);
INSERT INTO `department` VALUES (46, 'XEGRK', '小儿感染科', '临床科室', 3);
INSERT INTO `department` VALUES (47, 'XEXWK01', '小儿心外科', '临床科室', 3);
INSERT INTO `department` VALUES (48, 'XEXWK02', '小儿胸外科', '临床科室', 3);
INSERT INTO `department` VALUES (49, 'XESJWK', '小儿神经外科', '临床科室', 3);
INSERT INTO `department` VALUES (50, 'XEZXK', '小儿整形科', '临床科室', 3);
INSERT INTO `department` VALUES (51, 'XEFSMYK', '小儿风湿免疫科', '临床科室', 3);
INSERT INTO `department` VALUES (52, 'XEFK', '小儿妇科', '临床科室', 3);
INSERT INTO `department` VALUES (53, 'CRK', '传染科', '临床科室', 4);
INSERT INTO `department` VALUES (54, 'GBK', '肝病科', '临床科室', 4);
INSERT INTO `department` VALUES (55, 'AZBK', '艾滋病科', '临床科室', 4);
INSERT INTO `department` VALUES (56, 'CRWZS', '传染危重室', '临床科室', 4);
INSERT INTO `department` VALUES (57, 'FCKZH', '妇产科综合', '临床科室', 5);
INSERT INTO `department` VALUES (58, 'FK', '妇科', '临床科室', 5);
INSERT INTO `department` VALUES (59, 'CK', '产科', '临床科室', 5);
INSERT INTO `department` VALUES (60, 'JHSYK', '计划生育科', '临床科室', 5);
INSERT INTO `department` VALUES (61, 'FKNFM', '妇科内分泌', '临床科室', 5);
INSERT INTO `department` VALUES (62, 'YCZYK', '遗传咨询科', '临床科室', 5);
INSERT INTO `department` VALUES (63, 'CQJCK', '产前检查科', '临床科室', 5);
INSERT INTO `department` VALUES (64, 'FMNK', '妇泌尿科', '临床科室', 5);
INSERT INTO `department` VALUES (65, 'QLX', '前列腺', '临床科室', 6);
INSERT INTO `department` VALUES (66, 'XGNZA', '性功能障碍', '临床科室', 6);
INSERT INTO `department` VALUES (67, 'SZQGR', '生殖器感染', '临床科室', 6);
INSERT INTO `department` VALUES (68, 'NXBY', '男性不育', '临床科室', 6);
INSERT INTO `department` VALUES (69, 'SZZX', '生殖整形', '临床科室', 6);
INSERT INTO `department` VALUES (70, 'JSK', '精神科', '临床科室', 7);
INSERT INTO `department` VALUES (71, 'SFJDK', '司法鉴定科', '临床科室', 7);
INSERT INTO `department` VALUES (72, 'YWYLK', '药物依赖科', '临床科室', 7);
INSERT INTO `department` VALUES (73, 'ZYJSK', '中医精神科', '临床科室', 7);
INSERT INTO `department` VALUES (74, 'SXZAK', '双相障碍科', '临床科室', 7);
INSERT INTO `department` VALUES (75, 'PFK', '皮肤科', '临床科室', 8);
INSERT INTO `department` VALUES (76, 'XBK', '性病科', '临床科室', 8);
INSERT INTO `department` VALUES (77, 'ZYZHK', '中医综合科', '临床科室', 9);
INSERT INTO `department` VALUES (78, 'ZJK', '针灸科', '临床科室', 9);
INSERT INTO `department` VALUES (79, 'ZYGK', '中医骨科', '临床科室', 9);
INSERT INTO `department` VALUES (80, 'ZYFCK', '中医妇产科', '临床科室', 9);
INSERT INTO `department` VALUES (81, 'ZYWK', '中医外科', '临床科室', 9);
INSERT INTO `department` VALUES (82, 'ZYEK', '中医儿科', '临床科室', 9);
INSERT INTO `department` VALUES (83, 'ZYGCK', '中医肛肠科', '临床科室', 9);
INSERT INTO `department` VALUES (84, 'ZYPFK', '中医皮肤科', '临床科室', 9);
INSERT INTO `department` VALUES (85, 'ZYWGK', '中医五官科', '临床科室', 9);
INSERT INTO `department` VALUES (86, 'ZYAMK', '中医按摩科', '临床科室', 9);
INSERT INTO `department` VALUES (87, 'ZYXHK', '中医消化科', '临床科室', 9);
INSERT INTO `department` VALUES (88, 'ZYZLK', '中医肿瘤科', '临床科室', 9);
INSERT INTO `department` VALUES (89, 'ZYXNK', '中医心内科', '临床科室', 9);
INSERT INTO `department` VALUES (90, 'ZYSJNK', '中医神经内科', '临床科室', 9);
INSERT INTO `department` VALUES (91, 'ZYSBNK', '中医肾病内科', '临床科室', 9);
INSERT INTO `department` VALUES (92, 'ZYNFM', '中医内分泌', '临床科室', 9);
INSERT INTO `department` VALUES (93, 'ZYHXK', '中医呼吸科', '临床科室', 9);
INSERT INTO `department` VALUES (94, 'ZYGBK', '中医肝病科', '临床科室', 9);
INSERT INTO `department` VALUES (95, 'ZYNK', '中医男科', '临床科室', 9);
INSERT INTO `department` VALUES (96, 'ZYFSMYNK', '中医风湿免疫内科', '临床科室', 9);
INSERT INTO `department` VALUES (97, 'ZYXYK', '中医血液科', '临床科室', 9);
INSERT INTO `department` VALUES (98, 'ZYRXWK', '中医乳腺外科', '临床科室', 9);
INSERT INTO `department` VALUES (99, 'ZYLNBK', '中医老年病科', '临床科室', 9);
INSERT INTO `department` VALUES (100, 'ZLZHK', '肿瘤综合科', '临床科室', 10);
INSERT INTO `department` VALUES (101, 'ZLNK', '肿瘤内科', '临床科室', 10);
INSERT INTO `department` VALUES (102, 'FLK', '放疗科', '临床科室', 10);
INSERT INTO `department` VALUES (103, 'ZLWK', '肿瘤外科', '临床科室', 10);
INSERT INTO `department` VALUES (104, 'ZLFK', '肿瘤妇科', '临床科室', 10);
INSERT INTO `department` VALUES (105, 'GZLK', '骨肿瘤科', '临床科室', 10);
INSERT INTO `department` VALUES (106, 'ZLKFK', '肿瘤康复科', '临床科室', 10);
INSERT INTO `department` VALUES (107, 'GWK', '骨外科', '临床科室', 11);
INSERT INTO `department` VALUES (108, 'SWK', '手外科', '临床科室', 11);
INSERT INTO `department` VALUES (109, 'CSGK', '创伤骨科', '临床科室', 11);
INSERT INTO `department` VALUES (110, 'JZWK', '脊柱外科', '临床科室', 11);
INSERT INTO `department` VALUES (111, 'GGJK', '骨关节科', '临床科室', 11);
INSERT INTO `department` VALUES (112, 'GZSSK', '骨质疏松科', '临床科室', 11);
INSERT INTO `department` VALUES (113, 'JXGK', '矫形骨科', '临床科室', 11);
INSERT INTO `department` VALUES (114, 'EBYHTJK', '耳鼻咽喉头颈科', '临床科室', 12);
INSERT INTO `department` VALUES (115, 'KQK', '口腔科', '临床科室', 12);
INSERT INTO `department` VALUES (116, 'YK', '眼科', '临床科室', 12);
INSERT INTO `department` VALUES (117, 'KFK', '康复科', '医技科室', 13);
INSERT INTO `department` VALUES (118, 'LLK', '理疗科', '医技科室', 13);
INSERT INTO `department` VALUES (119, 'MZK', '麻醉科', '医技科室', 14);
INSERT INTO `department` VALUES (120, 'TTK', '疼痛科', '医技科室', 14);
INSERT INTO `department` VALUES (121, 'YYK', '营养科', '医技科室', 15);
INSERT INTO `department` VALUES (122, 'GYYK', '高压氧科', '医技科室', 16);
INSERT INTO `department` VALUES (123, 'GNJCK', '功能检查科', '医技科室', 16);
INSERT INTO `department` VALUES (124, 'BLK', '病理科', '医技科室', 16);
INSERT INTO `department` VALUES (125, 'JYK', '检验科', '医技科室', 16);
INSERT INTO `department` VALUES (126, 'SYZX', '实验中心', '医技科室', 16);
INSERT INTO `department` VALUES (127, 'XDTK', '心电图科', '医技科室', 16);
INSERT INTO `department` VALUES (128, 'FSK', '放射科', '医技科室', 17);
INSERT INTO `department` VALUES (129, 'CSZDK', '超声诊断科', '医技科室', 17);
INSERT INTO `department` VALUES (130, 'YXYXK', '医学影像科', '医技科室', 17);
INSERT INTO `department` VALUES (131, 'HY学K', '核医学科', '医技科室', 17);
INSERT INTO `department` VALUES (132, 'YJK', '药剂科', '医技科室', 18);
INSERT INTO `department` VALUES (133, 'HLK', '护理科', '医技科室', 18);
INSERT INTO `department` VALUES (134, 'TJK', '体检科', '医技科室', 18);
INSERT INTO `department` VALUES (135, 'JZK', '急诊科', '医技科室', 18);
INSERT INTO `department` VALUES (136, 'GGWSYYFK', '公共卫生与预防科', '医技科室', 18);
INSERT INTO `department` VALUES (137, 'SBK', '设备科', '行政科室', 18);
INSERT INTO `department` VALUES (138, 'CWK', '财务科', '财务科室', 18);

-- ----------------------------
-- Table structure for department_classification
-- ----------------------------
DROP TABLE IF EXISTS `department_classification`;
CREATE TABLE `department_classification`  (
  `id` int(11) NOT NULL,
  `name` varchar(50)  NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ;

-- ----------------------------
-- Records of department_classification
-- ----------------------------
INSERT INTO `department_classification` VALUES (9, '中医科');
INSERT INTO `department_classification` VALUES (12, '五官科');
INSERT INTO `department_classification` VALUES (4, '传染病科');
INSERT INTO `department_classification` VALUES (3, '儿科');
INSERT INTO `department_classification` VALUES (18, '其他科室');
INSERT INTO `department_classification` VALUES (1, '内科');
INSERT INTO `department_classification` VALUES (17, '医学影像学');
INSERT INTO `department_classification` VALUES (16, '医技科');
INSERT INTO `department_classification` VALUES (2, '外科');
INSERT INTO `department_classification` VALUES (5, '妇产科');
INSERT INTO `department_classification` VALUES (13, '康复医学科');
INSERT INTO `department_classification` VALUES (6, '男科');
INSERT INTO `department_classification` VALUES (8, '皮肤性病科');
INSERT INTO `department_classification` VALUES (7, '精神心理科');
INSERT INTO `department_classification` VALUES (10, '肿瘤科');
INSERT INTO `department_classification` VALUES (15, '营养科');
INSERT INTO `department_classification` VALUES (11, '骨科');
INSERT INTO `department_classification` VALUES (14, '麻醉医学科');

-- ----------------------------
-- Table structure for disease
-- ----------------------------
DROP TABLE IF EXISTS `disease`;
CREATE TABLE `disease`  (
  `id` int(11) NOT NULL,
  `code` varchar(20)  NOT NULL,
  `classification_id` int(11) NOT NULL,
  `name` varchar(255)  NOT NULL,
  `pinyin` varchar(100)  NOT NULL,
  `custom_name` varchar(100)  NULL DEFAULT NULL,
  `custom_pinyin` varchar(100)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for disease_classification
-- ----------------------------
DROP TABLE IF EXISTS `disease_classification`;
CREATE TABLE `disease_classification`  (
  `id` int(11) NOT NULL DEFAULT 0,
  `name` varchar(255)  NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ;

-- ----------------------------
-- Table structure for doctor_scheduling
-- ----------------------------
DROP TABLE IF EXISTS `doctor_scheduling`;
CREATE TABLE `doctor_scheduling`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `schedule_date` varchar(20)  NOT NULL,
  `week` int(11) NOT NULL,
  `valid` varchar(20)  NOT NULL,
  `reg_limit` int(11) NOT NULL,
  `registration_level_id` int(11) NOT NULL,
  `residue` int(11) NOT NULL,
  `shift` varchar(20)  NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for doctor_scheduling_info
-- ----------------------------
DROP TABLE IF EXISTS `doctor_scheduling_info`;
CREATE TABLE `doctor_scheduling_info`  (
  `uid` int(11) NOT NULL,
  `shift_id` varchar(30)  NOT NULL,
  `expiry_date` date NOT NULL,
  `scheduling_limit` int(11) NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ;

-- ----------------------------
-- Records of doctor_scheduling_info
-- ----------------------------
INSERT INTO `doctor_scheduling_info` VALUES (10000, '1', '2019-06-30', 20);
INSERT INTO `doctor_scheduling_info` VALUES (10012, '2', '2019-06-20', 1);

-- ----------------------------
-- Table structure for doctor_scheduling_shift
-- ----------------------------
DROP TABLE IF EXISTS `doctor_scheduling_shift`;
CREATE TABLE `doctor_scheduling_shift`  (
  `id` int(11) NOT NULL,
  `shift` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Records of doctor_scheduling_shift
-- ----------------------------
INSERT INTO `doctor_scheduling_shift` VALUES (1, '全天');
INSERT INTO `doctor_scheduling_shift` VALUES (2, '上午');
INSERT INTO `doctor_scheduling_shift` VALUES (3, '下午');

-- ----------------------------
-- Table structure for drug
-- ----------------------------
DROP TABLE IF EXISTS `drug`;
CREATE TABLE `drug`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(100)  NOT NULL,
  `name` varchar(200)  NOT NULL,
  `format` varchar(200)  NOT NULL,
  `unit` varchar(10)  NOT NULL,
  `manufacturer` varchar(200)  NOT NULL,
  `dosage_form` varchar(50)  NULL DEFAULT NULL,
  `type` varchar(50)  NOT NULL,
  `price` float NOT NULL,
  `pinyin` varchar(200)  NULL DEFAULT NULL,
  `stock` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Records of drug
-- ----------------------------
INSERT INTO `drug` VALUES (1, '1', '1', '1', '1', '1', '1', '1', 1, '1', 145);

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `medical_record_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `create_time` varchar(50)  NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `status` varchar(50)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for exam_item
-- ----------------------------
DROP TABLE IF EXISTS `exam_item`;
CREATE TABLE `exam_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_id` int(11) NOT NULL,
  `non_drug_item_id` int(11) NOT NULL,
  `status` varchar(50)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for exam_item_result
-- ----------------------------
DROP TABLE IF EXISTS `exam_item_result`;
CREATE TABLE `exam_item_result`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file` varchar(255)  NULL DEFAULT NULL,
  `result` text  NULL,
  `advice` text  NULL,
  `exam_item_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for exam_template
-- ----------------------------
DROP TABLE IF EXISTS `exam_template`;
CREATE TABLE `exam_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(255)  NOT NULL,
  `user_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `display_type` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `create_time` varchar(50)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for exam_template_item
-- ----------------------------
DROP TABLE IF EXISTS `exam_template_item`;
CREATE TABLE `exam_template_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_template_id` int(11) NOT NULL,
  `non_drug_item_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for expense_classification
-- ----------------------------
DROP TABLE IF EXISTS `expense_classification`;
CREATE TABLE `expense_classification`  (
  `id` int(11) NOT NULL,
  `pinyin` varchar(50)  NOT NULL,
  `fee_name` varchar(50)  NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `fee_name`(`fee_name`) USING BTREE
) ;

-- ----------------------------
-- Records of expense_classification
-- ----------------------------
INSERT INTO `expense_classification` VALUES (1, 'GHF', '挂号费');
INSERT INTO `expense_classification` VALUES (2, 'ZLF', '诊疗费');
INSERT INTO `expense_classification` VALUES (3, 'JYF', '检验费');
INSERT INTO `expense_classification` VALUES (4, 'JYCLF', '检验材料费');
INSERT INTO `expense_classification` VALUES (5, 'CSJCF', '超声检查费');
INSERT INTO `expense_classification` VALUES (6, 'CSCLF', '超声材料费');
INSERT INTO `expense_classification` VALUES (7, 'FSJCF', '放射检查费');
INSERT INTO `expense_classification` VALUES (8, 'FSCLF', '放射材料费');
INSERT INTO `expense_classification` VALUES (9, 'MRIJCF', 'MRI检查费');
INSERT INTO `expense_classification` VALUES (10, 'MRICLF', 'MRI材料费');
INSERT INTO `expense_classification` VALUES (11, 'CTJCF', 'CT检查费');
INSERT INTO `expense_classification` VALUES (12, 'CTCLF', 'CT材料费');
INSERT INTO `expense_classification` VALUES (13, 'XYF', '西药费');
INSERT INTO `expense_classification` VALUES (14, 'ZCYF', '中成药费');
INSERT INTO `expense_classification` VALUES (15, 'ZCYF', '中草药费');
INSERT INTO `expense_classification` VALUES (16, 'CZF', '处置费');
INSERT INTO `expense_classification` VALUES (17, 'CZCLF', '处置材料费');
INSERT INTO `expense_classification` VALUES (18, 'MZF', '麻醉费');
INSERT INTO `expense_classification` VALUES (19, 'MZYF', '麻醉药费');
INSERT INTO `expense_classification` VALUES (20, 'MZSSF', '门诊手术费');
INSERT INTO `expense_classification` VALUES (21, 'QT', '其他');

-- ----------------------------
-- Table structure for medical_record
-- ----------------------------
DROP TABLE IF EXISTS `medical_record`;
CREATE TABLE `medical_record`  (
  `id` int(11) NOT NULL,
  `chief_complaint` text  NOT NULL,
  `current_medical_history` text  NOT NULL,
  `current_treatment_situation` text  NOT NULL,
  `past_history` text  NOT NULL,
  `allergy_history` text  NOT NULL,
  `physical_examination` text  NOT NULL,
  `western_initial_diagnosis` text  NOT NULL,
  `chinese_initial_diagnosis` text  NOT NULL,
  `end_diagnosis` text  NOT NULL,
  `create_time` varchar(50)  NOT NULL,
  `status` varchar(50)  NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for medical_record_template
-- ----------------------------
DROP TABLE IF EXISTS `medical_record_template`;
CREATE TABLE `medical_record_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255)  NOT NULL,
  `type` int(11) NOT NULL DEFAULT 0,
  `user_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `chief_complaint` text  NOT NULL,
  `current_medical_history` text  NOT NULL,
  `current_treatment_situation` text  NOT NULL,
  `past_history` text  NOT NULL,
  `allergy_history` text  NOT NULL,
  `physical_examination` text  NOT NULL,
  `western_initial_diagnosis` text  NOT NULL,
  `chinese_initial_diagnosis` text  NOT NULL,
  `end_diagnosis` text  NOT NULL,
  `create_time` varchar(50)  NOT NULL,
  `status` varchar(50)  NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for non_drug_charge_item
-- ----------------------------
DROP TABLE IF EXISTS `non_drug_charge_item`;
CREATE TABLE `non_drug_charge_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20)  NOT NULL,
  `pinyin` varchar(100)  NOT NULL,
  `format` varchar(50)  NOT NULL,
  `name` varchar(255)  NOT NULL,
  `fee` float NOT NULL,
  `expense_classification_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ;

-- ----------------------------
-- Table structure for operate_log
-- ----------------------------
DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `operate_id` int(11) NULL DEFAULT NULL,
  `type` varchar(50)  NULL DEFAULT NULL,
  `bill_record_id` int(11) NULL DEFAULT NULL,
  `fee` float NULL DEFAULT NULL,
  `create_time` varchar(50)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for outpatient_charges_record
-- ----------------------------
DROP TABLE IF EXISTS `outpatient_charges_record`;
CREATE TABLE `outpatient_charges_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `medical_record_id` int(11) NOT NULL,
  `bill_record_id` int(11) NOT NULL DEFAULT 0,
  `item_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `expense_classification_id` int(11) NOT NULL,
  `status` varchar(20)  NOT NULL,
  `quantity` int(11) NOT NULL,
  `cost` float NOT NULL,
  `execute_department_id` int(11) NOT NULL,
  `create_time` varchar(50)  NOT NULL,
  `collect_time` varchar(50)  NULL DEFAULT NULL,
  `return_time` varchar(50)  NULL DEFAULT NULL,
  `create_user_id` int(11) NULL DEFAULT NULL,
  `collect_user_id` int(11) NULL DEFAULT NULL,
  `return_user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Records of outpatient_charges_record
-- ----------------------------
INSERT INTO `outpatient_charges_record` VALUES (1, 10002, 0, 1, 1, 1, '已缴费', 1, 3, 1, '1', '1', '1', 1, 1, 1);
INSERT INTO `outpatient_charges_record` VALUES (9, 10002, 0, 16, 1, 1, '已缴费', 1, -2, 1, '1', '1', '1', 1, 1, 1);

-- ----------------------------
-- Table structure for prescription
-- ----------------------------
DROP TABLE IF EXISTS `prescription`;
CREATE TABLE `prescription`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `medical_record_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `status` varchar(50)  NULL DEFAULT NULL,
  `create_time` varchar(50)  NULL DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Records of prescription
-- ----------------------------
INSERT INTO `prescription` VALUES (1, 10002, 1, '已提交', '0', 10002);

-- ----------------------------
-- Table structure for prescription_item
-- ----------------------------
DROP TABLE IF EXISTS `prescription_item`;
CREATE TABLE `prescription_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note` text  NULL,
  `usage` varchar(255)  NOT NULL,
  `dosage` varchar(255)  NOT NULL,
  `frequency` varchar(255)  NOT NULL,
  `day_count` int(11) NOT NULL DEFAULT 0,
  `times` int(11) NOT NULL DEFAULT 0,
  `amount` int(11) NOT NULL DEFAULT 1,
  `prescription_id` int(11) NOT NULL,
  `drug_id` int(11) NOT NULL,
  `status` varchar(50)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Records of prescription_item
-- ----------------------------
INSERT INTO `prescription_item` VALUES (1, '好吃', '多吃', '吃', '一天一次', 0, 0, 3, 1, 1, '已退药');
INSERT INTO `prescription_item` VALUES (16, '好吃', '多吃', '吃', '一天一次', 0, 0, 7, 1, 1, '已取药');

-- ----------------------------
-- Table structure for prescription_template
-- ----------------------------
DROP TABLE IF EXISTS `prescription_template`;
CREATE TABLE `prescription_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(255)  NOT NULL,
  `create_time` varchar(50)  NULL DEFAULT NULL,
  `type` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for prescription_template_item
-- ----------------------------
DROP TABLE IF EXISTS `prescription_template_item`;
CREATE TABLE `prescription_template_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prescription_template_id` int(11) NOT NULL,
  `drug_id` int(11) NOT NULL,
  `note` text  NULL,
  `usage` varchar(255)  NOT NULL,
  `dosage` varchar(255)  NOT NULL,
  `frequency` varchar(255)  NOT NULL,
  `day_count` int(11) NOT NULL DEFAULT 0,
  `times` int(11) NOT NULL DEFAULT 0,
  `amount` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for registration
-- ----------------------------
DROP TABLE IF EXISTS `registration`;
CREATE TABLE `registration`  (
  `medical_record_id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(100)  NOT NULL,
  `age` int(11) NOT NULL,
  `gender` varchar(10)  NOT NULL,
  `medical_insurance_diagnosis` varchar(200)  NOT NULL,
  `birthday` varchar(20)  NOT NULL,
  `consultation_date` varchar(20)  NOT NULL,
  `id_number` varchar(50)  NOT NULL,
  `medical_certificate_number` varchar(50)  NOT NULL,
  `medical_category` varchar(50)  NOT NULL,
  `patient_name` varchar(20)  NOT NULL,
  `outpatient_doctor_id` int(11) NULL DEFAULT NULL,
  `registration_department_id` int(11) NOT NULL,
  `settlement_category_id` int(11) NOT NULL,
  `registration_category` varchar(20)  NOT NULL,
  `registration_source` varchar(50)  NOT NULL,
  `status` varchar(10)  NOT NULL,
  `cost` float NOT NULL,
  PRIMARY KEY (`medical_record_id`) USING BTREE
) ;

-- ----------------------------
-- Table structure for registration_level
-- ----------------------------
DROP TABLE IF EXISTS `registration_level`;
CREATE TABLE `registration_level`  (
  `id` int(11) NOT NULL,
  `name` varchar(10)  NOT NULL,
  `is_default` tinyint(1) NULL DEFAULT 0,
  `seq_num` int(11) NOT NULL,
  `fee` float NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ;

-- ----------------------------
-- Records of registration_level
-- ----------------------------
INSERT INTO `registration_level` VALUES (1, '专家号', 0, 1, 20);
INSERT INTO `registration_level` VALUES (2, '普通号', 1, 2, 10);
INSERT INTO `registration_level` VALUES (3, '急诊号', 0, 3, 30);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL,
  `name` varchar(30)  NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (6, '医技工作者');
INSERT INTO `role` VALUES (1, '医院管理员');
INSERT INTO `role` VALUES (4, '药房操作员');
INSERT INTO `role` VALUES (3, '财务管理员');
INSERT INTO `role` VALUES (5, '门诊医生');
INSERT INTO `role` VALUES (2, '门诊收费员');

-- ----------------------------
-- Table structure for settlement_category
-- ----------------------------
DROP TABLE IF EXISTS `settlement_category`;
CREATE TABLE `settlement_category`  (
  `id` int(11) NOT NULL,
  `name` varchar(20)  NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ;

-- ----------------------------
-- Records of settlement_category
-- ----------------------------
INSERT INTO `settlement_category` VALUES (4, '交通银行');
INSERT INTO `settlement_category` VALUES (2, '农业银行');
INSERT INTO `settlement_category` VALUES (3, '建设银行');
INSERT INTO `settlement_category` VALUES (6, '微信');
INSERT INTO `settlement_category` VALUES (5, '支付宝');
INSERT INTO `settlement_category` VALUES (1, '现金');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50)  NOT NULL,
  `password` varchar(300)  NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (10000, 'HospitialAdmin', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10001, 'RegisteredTollCollector', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10002, 'FinancialAdmin', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10003, 'PharmacyOperator', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10004, 'OutpatientDoctor', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10005, 'DoctorOfTechnology', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10006, '赵竹林', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10007, '李抒萍', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10008, '周蓓蓓', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10009, '王唯依', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10010, '杨宁', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10011, '陈祥旭', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10012, '张杰', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10013, '郑哲隽', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10014, '苏轶', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10015, '袁军辉', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10016, '赵威皓', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10017, '李书萍', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10018, '周海迪', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10019, '王婷婷', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10020, '杨满优', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10021, '陈晓', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10022, '张筱宇', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10023, '郑灏鼎', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10024, '苏珍', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10025, '袁生文', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10026, '赵冬梅', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10027, '李昭欣', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10028, '周缙绅', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10029, '王玲娟', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10030, '杨满娃', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10031, '陈东慧', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10032, '张力弘', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10033, '郑春晖', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10034, '苏峻衡', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10035, '袁金华', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10036, '赵中锴', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10037, '李坊', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10038, '周家福', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10039, '王鹏宇', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10040, '杨思远', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10041, '陈镇彬', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10042, '张可露', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10043, '郑希前', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10044, '苏银冬', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10045, '袁军', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10046, '赵吾光', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10047, '李坊颖', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10048, '周维佳', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10049, '王佁函', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10050, '杨倍颐', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10051, '陈晨', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10052, '张竞予', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10053, '郑鲜丹', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10054, '苏展', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10055, '袁志兰', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10056, '赵山川', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10057, '李舒萍', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10058, '周琎', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10059, '王巍娟', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10060, '杨倍依', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10061, '陈锐句', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10062, '张雨卓', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10063, '郑文英', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10064, '苏雪峰', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10065, '袁辉', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10066, '赵璇海', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10067, '李嘉城', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10068, '周江', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10069, '王帼玮', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10070, '杨飞颐', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10071, '陈锐', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10072, '张广', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10073, '郑砚鹳', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10074, '苏玺祺', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10075, '袁基妮', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10076, '赵学海', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10077, '李淑萍', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10078, '周军', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10079, '王天琦', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10080, '杨潇颐', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10081, '陈品希', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10082, '张天合', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10083, '郑迎华', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10084, '苏之哲', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10085, '袁帅', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10086, '赵绚海', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10087, '李慕雪', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10088, '周小艺', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10089, '王鹏云', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10090, '杨希颐', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10091, '陈悦句', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10092, '张颖', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10093, '郑叶飞', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10094, '苏之祺', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10095, '袁伟淳', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10096, '赵午光', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10097, '李姝萍', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10098, '周薇', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10099, '王明涛', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10100, '杨闻颐', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10101, '陈韦明', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10102, '张天弘', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10103, '郑盈', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10104, '苏彦丁', '827ccb0eea8a706c4c34a16891f84e7b');
INSERT INTO `user` VALUES (10105, '袁博', '827ccb0eea8a706c4c34a16891f84e7b');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `uid` int(11) NOT NULL,
  `real_name` varchar(30)  NOT NULL,
  `department_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `participate_in_scheduling` tinyint(1) NOT NULL,
  `title` varchar(100)  NOT NULL,
  `registration_level_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (10000, '菜徐坤', 1, 1, 0, '专家', 1);
INSERT INTO `user_info` VALUES (10001, '菜徐坤', 1, 2, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10002, '菜徐坤', 1, 3, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10003, '菜徐坤', 1, 4, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10004, '菜徐坤', 1, 5, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10005, '菜徐坤', 1, 6, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10006, '赵竹林', 1, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10007, '李抒萍', 1, 3, 0, '', NULL);
INSERT INTO `user_info` VALUES (10008, '周蓓蓓', 1, 2, 0, '副主任医师', NULL);
INSERT INTO `user_info` VALUES (10009, '王唯依', 1, 4, 0, '主治医师', NULL);
INSERT INTO `user_info` VALUES (10010, '杨宁', 1, 5, 0, '住院医师', NULL);
INSERT INTO `user_info` VALUES (10011, '陈祥旭', 1, 6, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10012, '张杰', 2, 1, 0, '', 2);
INSERT INTO `user_info` VALUES (10013, '郑哲隽', 2, 3, 0, '', NULL);
INSERT INTO `user_info` VALUES (10014, '苏轶', 2, 2, 0, '副主任医师', NULL);
INSERT INTO `user_info` VALUES (10015, '袁军辉', 2, 3, 0, '', NULL);
INSERT INTO `user_info` VALUES (10016, '赵威皓', 2, 4, 0, '住院医师', NULL);
INSERT INTO `user_info` VALUES (10017, '李书萍', 2, 5, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10018, '周海迪', 2, 5, 0, '副主任医师', NULL);
INSERT INTO `user_info` VALUES (10019, '王婷婷', 2, 5, 0, '主治医师', NULL);
INSERT INTO `user_info` VALUES (10020, '杨满优', 2, 6, 0, '住院医师', NULL);
INSERT INTO `user_info` VALUES (10021, '陈晓', 2, 6, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10022, '张筱宇', 3, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10023, '郑灏鼎', 3, 3, 0, '', NULL);
INSERT INTO `user_info` VALUES (10024, '苏珍', 3, 2, 0, '副主任医师', NULL);
INSERT INTO `user_info` VALUES (10025, '袁生文', 3, 5, 0, '主治医师', NULL);
INSERT INTO `user_info` VALUES (10026, '赵冬梅', 5, 5, 0, '住院医师', NULL);
INSERT INTO `user_info` VALUES (10027, '李昭欣', 4, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10028, '周缙绅', 4, 3, 0, '', NULL);
INSERT INTO `user_info` VALUES (10029, '王玲娟', 4, 5, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10030, '杨满娃', 4, 2, 0, '副主任医师', NULL);
INSERT INTO `user_info` VALUES (10031, '陈东慧', 4, 6, 0, '主治医师', NULL);
INSERT INTO `user_info` VALUES (10032, '张力弘', 5, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10033, '郑春晖', 5, 3, 0, '', NULL);
INSERT INTO `user_info` VALUES (10034, '苏峻衡', 5, 5, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10035, '袁金华', 5, 5, 0, '副主任医师', NULL);
INSERT INTO `user_info` VALUES (10036, '赵中锴', 5, 6, 0, '主治医师', NULL);
INSERT INTO `user_info` VALUES (10037, '李坊', 5, 6, 0, '住院医师', NULL);
INSERT INTO `user_info` VALUES (10038, '周家福', 5, 2, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10039, '王鹏宇', 5, 4, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10040, '杨思远', 6, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10041, '陈镇彬', 6, 3, 0, '', NULL);
INSERT INTO `user_info` VALUES (10042, '张可露', 6, 5, 0, '住院医师', NULL);
INSERT INTO `user_info` VALUES (10043, '郑希前', 6, 6, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10044, '苏银冬', 6, 2, 0, '主治医师', NULL);
INSERT INTO `user_info` VALUES (10045, '袁军', 8, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10046, '赵吾光', 8, 3, 0, '', NULL);
INSERT INTO `user_info` VALUES (10047, '李坊颖', 8, 2, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10048, '周维佳', 8, 4, 0, '副主任医师', NULL);
INSERT INTO `user_info` VALUES (10049, '王佁函', 8, 2, 0, '主治医师', NULL);
INSERT INTO `user_info` VALUES (10050, '杨倍颐', 8, 5, 0, '住院医师', NULL);
INSERT INTO `user_info` VALUES (10051, '陈晨', 7, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10052, '张竞予', 7, 3, 0, '', NULL);
INSERT INTO `user_info` VALUES (10053, '郑鲜丹', 7, 2, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10054, '苏展', 7, 4, 0, '副主任医师', NULL);
INSERT INTO `user_info` VALUES (10055, '袁志兰', 7, 5, 0, '主治医师', NULL);
INSERT INTO `user_info` VALUES (10056, '赵山川', 7, 5, 0, '住院医师', NULL);
INSERT INTO `user_info` VALUES (10057, '李舒萍', 9, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10058, '周琎', 9, 3, 0, '', NULL);
INSERT INTO `user_info` VALUES (10059, '王巍娟', 9, 5, 0, '专家', NULL);
INSERT INTO `user_info` VALUES (10060, '杨倍依', 9, 6, 0, '副主任医师', NULL);
INSERT INTO `user_info` VALUES (10061, '陈锐句', 10, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10062, '张雨卓', 10, 3, 0, '', NULL);
INSERT INTO `user_info` VALUES (10063, '郑文英', 10, 5, 0, '主治医师', NULL);
INSERT INTO `user_info` VALUES (10064, '苏雪峰', 10, 6, 0, '住院医师', NULL);
INSERT INTO `user_info` VALUES (10065, '袁辉', 11, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10066, '赵璇海', 12, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10067, '李嘉城', 13, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10068, '周江', 14, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10069, '王帼玮', 15, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10070, '杨飞颐', 16, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10071, '陈锐', 17, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10072, '张广', 18, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10073, '郑砚鹳', 19, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10074, '苏玺祺', 20, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10075, '袁基妮', 21, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10076, '赵学海', 22, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10077, '李淑萍', 23, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10078, '周军', 24, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10079, '王天琦', 25, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10080, '杨潇颐', 26, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10081, '陈品希', 27, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10082, '张天合', 28, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10083, '郑迎华', 29, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10084, '苏之哲', 30, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10085, '袁帅', 31, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10086, '赵绚海', 32, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10087, '李慕雪', 33, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10088, '周小艺', 34, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10089, '王鹏云', 35, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10090, '杨希颐', 36, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10091, '陈悦句', 37, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10092, '张颖', 38, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10093, '郑叶飞', 39, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10094, '苏之祺', 40, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10095, '袁伟淳', 41, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10096, '赵午光', 42, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10097, '李姝萍', 43, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10098, '周薇', 44, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10099, '王明涛', 45, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10100, '杨闻颐', 46, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10101, '陈韦明', 47, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10102, '张天弘', 48, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10103, '郑盈', 49, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10104, '苏彦丁', 50, 1, 0, '', NULL);
INSERT INTO `user_info` VALUES (10105, '袁博', 51, 1, 0, '', NULL);

SET FOREIGN_KEY_CHECKS = 1;

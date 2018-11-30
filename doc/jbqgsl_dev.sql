/*
Navicat MySQL Data Transfer

Source Server         : local_root
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : jbqgsl_dev

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-08-04 18:08:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for institution_info
-- ----------------------------
DROP TABLE IF EXISTS `institution_info`;
CREATE TABLE `institution_info` (
  `institution_id` varchar(32) NOT NULL COMMENT '机构ID',
  `institution_nature` varchar(2) DEFAULT '1' COMMENT '机构性质(1：政府部门，2：民营企业，默认1)',
  `institution_type_id` varchar(32) DEFAULT NULL COMMENT '机构类型ID',
  `institution_type_name` varchar(50) DEFAULT NULL COMMENT '机构类型名称',
  `institution_name` varchar(50) DEFAULT NULL COMMENT '机构名称',
  `institution_describe` longtext COMMENT '机构职能描述',
  `institution_linkman_name` varchar(50) DEFAULT NULL COMMENT '机构联系人姓名',
  `institution_linkman_phone` varchar(11) DEFAULT NULL COMMENT '机构联系电话',
  `institution_linkman_email` varchar(50) DEFAULT NULL COMMENT '机构联系邮箱',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注说明',
  `del_flag` varchar(2) DEFAULT '0' COMMENT '删除标识(0：未删除，1：已删除，默认0)',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`institution_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of institution_info
-- ----------------------------
INSERT INTO `institution_info` VALUES ('111', '1', '222', '街镇', '333', '666', 'aaa', '444', '555', '888', '0', '2018-08-02 15:49:14', '2018-08-03 10:49:22');

-- ----------------------------
-- Table structure for institution_type
-- ----------------------------
DROP TABLE IF EXISTS `institution_type`;
CREATE TABLE `institution_type` (
  `institution_type_id` varchar(32) NOT NULL COMMENT '机构类型ID',
  `institution_type_name` varchar(50) DEFAULT NULL COMMENT '机构类型名称',
  `del_flag` varchar(2) DEFAULT '0' COMMENT '删除标识(0：未删除，1：已删除，默认0)',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`institution_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of institution_type
-- ----------------------------
INSERT INTO `institution_type` VALUES ('111', '区级部门', '0', '2018-08-02 15:50:25', null);
INSERT INTO `institution_type` VALUES ('222', '街镇', '0', '2018-08-02 15:50:56', null);
INSERT INTO `institution_type` VALUES ('333', '工业', '0', '2018-08-02 15:53:02', null);
INSERT INTO `institution_type` VALUES ('444', '\r\n\r\n金融业\r\n', '0', '2018-08-02 15:53:02', null);
INSERT INTO `institution_type` VALUES ('555', '建筑业', '0', '2018-08-02 15:53:02', null);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` varchar(36) NOT NULL COMMENT '部门ID',
  `dept_code` varchar(30) NOT NULL COMMENT '部门编码',
  `dept_name` varchar(50) NOT NULL COMMENT '部门名称',
  `dept_type` char(1) NOT NULL DEFAULT '3' COMMENT '部门类型 3 部门 2 公司 1 集团',
  `p_dept_id` varchar(36) NOT NULL COMMENT '上级部门ID：顶级部门id为-1 ',
  `leader_id` varchar(36) DEFAULT NULL COMMENT '部门领导ID',
  `leader_type` char(1) DEFAULT NULL COMMENT '部门领导类型 U 岗位 U 用户',
  `keyword` varchar(100) DEFAULT NULL COMMENT '关键字',
  `order_no` int(11) DEFAULT NULL COMMENT '排序号',
  `memo` varchar(400) DEFAULT NULL COMMENT '备注',
  `creator_id` varchar(36) NOT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '操作人ID',
  `alter_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('d1d0c5d5a9034a03b323c4d030751675', '10000', '集团总公司', '1', '-1', 'd206c3eded384b46b519edcdd249a4a9', 'U', null, '1', null, 'd206c3eded384b46b519edcdd249a4a9', '2018-05-28 18:01:30', null);
INSERT INTO `sys_dept` VALUES ('d39532a171344f10aadb7fa94cde190c', '10001', '设计开发部', '3', 'd1d0c5d5a9034a03b323c4d030751675', 'd206c3eded384b46b519edcdd249a4a9', 'U', null, '1', null, 'd206c3eded384b46b519edcdd249a4a9', '2018-05-28 18:01:32', null);

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `dict_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编码',
  `dict_type` varchar(30) DEFAULT NULL COMMENT '字典类别',
  `dict_name` varchar(50) DEFAULT NULL COMMENT '字典名字',
  `dict_value` varchar(120) DEFAULT NULL COMMENT '字典值',
  `dict_pid` int(11) DEFAULT NULL COMMENT '父级编码',
  `isSystem` int(11) DEFAULT '0' COMMENT '是否系统参数 0默认为不为系统参数，1为系统参数',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
INSERT INTO `sys_dictionary` VALUES ('1', '民族', '汉族', '1', null, '1');
INSERT INTO `sys_dictionary` VALUES ('4', '民族', '汉族1', '2', '1', '0');
INSERT INTO `sys_dictionary` VALUES ('5', '11', '11', '11', '1', '0');
INSERT INTO `sys_dictionary` VALUES ('6', '11', '11', '11', '1', '1');

-- ----------------------------
-- Table structure for sys_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_func`;
CREATE TABLE `sys_func` (
  `func_id` varchar(36) NOT NULL COMMENT '功能ID',
  `menu_id` varchar(36) DEFAULT NULL COMMENT '菜单ID',
  `func_name` varchar(50) NOT NULL COMMENT '功能名称',
  `func_desc` varchar(200) DEFAULT NULL COMMENT '功能描述',
  `order_no` int(11) DEFAULT NULL COMMENT '排序号',
  `auth_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '授权标识 A 匿名访问 N 登录访问 Y 授权访问',
  `func_level` int(11) NOT NULL DEFAULT '1' COMMENT '功能等级',
  `creator_id` varchar(36) NOT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`func_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='功能表';

-- ----------------------------
-- Records of sys_func
-- ----------------------------
INSERT INTO `sys_func` VALUES ('10.01.01.01', '10.01.01', '模块权限', '', '1', 'Y', '1', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-29 16:45:44');
INSERT INTO `sys_func` VALUES ('10.01.02.01', '10.01.02', '模块权限', '', '1', 'Y', '1', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-29 16:45:41');
INSERT INTO `sys_func` VALUES ('10.01.03.01', '10.01.03', '模块权限', '', '1', 'Y', '1', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-29 16:45:43');
INSERT INTO `sys_func` VALUES ('10.01.04.01', '10.01.04', '模块权限', '', '1', 'Y', '1', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-29 16:45:45');
INSERT INTO `sys_func` VALUES ('10.01.05.01', '10.01.05', '模块权限', '', '1', 'Y', '1', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-29 16:45:50');
INSERT INTO `sys_func` VALUES ('10.01.06.01', '10.01.06', '模块权限', '', '1', 'Y', '1', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-31 10:37:56');
INSERT INTO `sys_func` VALUES ('10.02.01.01', '10.02.01', '模块权限', '', '1', 'Y', '1', '1a5d0215620b42358f042fdd96a8ac6c', '2018-06-05 14:35:26');
INSERT INTO `sys_func` VALUES ('10.04.01.01', '10.04.01', '模块权限', '', '1', 'Y', '1', '9faac380235548998d4bc2b8b4a15193', '2018-07-21 17:29:51');
INSERT INTO `sys_func` VALUES ('10.04.02.01', '10.04.02', '模块权限', '', '1', 'Y', '1', '9faac380235548998d4bc2b8b4a15193', '2018-07-21 17:29:51');

-- ----------------------------
-- Table structure for sys_func_url
-- ----------------------------
DROP TABLE IF EXISTS `sys_func_url`;
CREATE TABLE `sys_func_url` (
  `url_id` varchar(36) NOT NULL COMMENT 'URL_ID',
  `func_id` varchar(36) NOT NULL COMMENT '功能ID',
  `func_url` varchar(200) DEFAULT NULL COMMENT '功能URL',
  `record_log` char(1) NOT NULL DEFAULT 'N' COMMENT '是否记录日志 Y 是 N 否',
  `order_no` int(11) DEFAULT NULL COMMENT '排序号',
  `creator_id` varchar(36) NOT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`url_id`) USING BTREE,
  KEY `FK_url_func` (`func_id`) USING BTREE,
  CONSTRAINT `sys_func_url_ibfk_1` FOREIGN KEY (`func_id`) REFERENCES `sys_func` (`func_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='功能表URL; InnoDB free: 10240 kB; (`func_id`) REFER `jbqgsl_dev/sys_func`(`func_id`';

-- ----------------------------
-- Records of sys_func_url
-- ----------------------------

-- ----------------------------
-- Table structure for sys_icon
-- ----------------------------
DROP TABLE IF EXISTS `sys_icon`;
CREATE TABLE `sys_icon` (
  `ic_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编码',
  `ic_class` varchar(50) DEFAULT NULL COMMENT '样式',
  PRIMARY KEY (`ic_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=976 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='图标样式';

-- ----------------------------
-- Records of sys_icon
-- ----------------------------
INSERT INTO `sys_icon` VALUES ('1', 'fa fa-address-book');
INSERT INTO `sys_icon` VALUES ('2', 'fa fa-address-book-o');
INSERT INTO `sys_icon` VALUES ('3', 'fa fa-address-card');
INSERT INTO `sys_icon` VALUES ('4', 'fa fa-address-card-o');
INSERT INTO `sys_icon` VALUES ('5', 'fa fa-bandcamp');
INSERT INTO `sys_icon` VALUES ('6', 'fa fa-bath');
INSERT INTO `sys_icon` VALUES ('7', 'fa fa-bathtub');
INSERT INTO `sys_icon` VALUES ('8', 'fa fa-drivers-license');
INSERT INTO `sys_icon` VALUES ('9', 'fa fa-drivers-license-o');
INSERT INTO `sys_icon` VALUES ('10', 'fa fa-eercast');
INSERT INTO `sys_icon` VALUES ('11', 'fa fa-envelope-open');
INSERT INTO `sys_icon` VALUES ('12', 'fa fa-envelope-open-o');
INSERT INTO `sys_icon` VALUES ('13', 'fa fa-etsy');
INSERT INTO `sys_icon` VALUES ('14', 'fa fa-free-code-camp');
INSERT INTO `sys_icon` VALUES ('15', 'fa fa-grav');
INSERT INTO `sys_icon` VALUES ('16', 'fa fa-handshake-o');
INSERT INTO `sys_icon` VALUES ('17', 'fa fa-id-badge');
INSERT INTO `sys_icon` VALUES ('18', 'fa fa-id-card');
INSERT INTO `sys_icon` VALUES ('19', 'fa fa-id-card-o');
INSERT INTO `sys_icon` VALUES ('20', 'fa fa-imdb');
INSERT INTO `sys_icon` VALUES ('21', 'fa fa-linode');
INSERT INTO `sys_icon` VALUES ('22', 'fa fa-meetup');
INSERT INTO `sys_icon` VALUES ('23', 'fa fa-microchip');
INSERT INTO `sys_icon` VALUES ('24', 'fa fa-podcast');
INSERT INTO `sys_icon` VALUES ('25', 'fa fa-quora');
INSERT INTO `sys_icon` VALUES ('26', 'fa fa-ravelry');
INSERT INTO `sys_icon` VALUES ('27', 'fa fa-s15');
INSERT INTO `sys_icon` VALUES ('28', 'fa fa-shower');
INSERT INTO `sys_icon` VALUES ('29', 'fa fa-snowflake-o');
INSERT INTO `sys_icon` VALUES ('30', 'fa fa-superpowers');
INSERT INTO `sys_icon` VALUES ('31', 'fa fa-telegram');
INSERT INTO `sys_icon` VALUES ('32', 'fa fa-thermometer');
INSERT INTO `sys_icon` VALUES ('33', 'fa fa-thermometer-0');
INSERT INTO `sys_icon` VALUES ('34', 'fa fa-thermometer-1');
INSERT INTO `sys_icon` VALUES ('35', 'fa fa-thermometer-2');
INSERT INTO `sys_icon` VALUES ('36', 'fa fa-thermometer-3');
INSERT INTO `sys_icon` VALUES ('37', 'fa fa-thermometer-4');
INSERT INTO `sys_icon` VALUES ('38', 'fa fa-thermometer-empty');
INSERT INTO `sys_icon` VALUES ('39', 'fa fa-thermometer-full');
INSERT INTO `sys_icon` VALUES ('40', 'fa fa-thermometer-half');
INSERT INTO `sys_icon` VALUES ('41', 'fa fa-thermometer-quarter');
INSERT INTO `sys_icon` VALUES ('42', 'fa fa-thermometer-three-quarters');
INSERT INTO `sys_icon` VALUES ('43', 'fa fa-times-rectangle');
INSERT INTO `sys_icon` VALUES ('44', 'fa fa-times-rectangle-o');
INSERT INTO `sys_icon` VALUES ('45', 'fa fa-user-circle');
INSERT INTO `sys_icon` VALUES ('46', 'fa fa-user-circle-o');
INSERT INTO `sys_icon` VALUES ('47', 'fa fa-user-o');
INSERT INTO `sys_icon` VALUES ('48', 'fa fa-vcard');
INSERT INTO `sys_icon` VALUES ('49', 'fa fa-vcard-o');
INSERT INTO `sys_icon` VALUES ('50', 'fa fa-window-close');
INSERT INTO `sys_icon` VALUES ('51', 'fa fa-window-close-o');
INSERT INTO `sys_icon` VALUES ('52', 'fa fa-window-maximize');
INSERT INTO `sys_icon` VALUES ('53', 'fa fa-window-minimize');
INSERT INTO `sys_icon` VALUES ('54', 'fa fa-window-restore');
INSERT INTO `sys_icon` VALUES ('55', 'fa fa-wpexplorer');
INSERT INTO `sys_icon` VALUES ('56', 'fa fa-address-book');
INSERT INTO `sys_icon` VALUES ('57', 'fa fa-address-book-o');
INSERT INTO `sys_icon` VALUES ('58', 'fa fa-address-card');
INSERT INTO `sys_icon` VALUES ('59', 'fa fa-address-card-o');
INSERT INTO `sys_icon` VALUES ('60', 'fa fa-adjust');
INSERT INTO `sys_icon` VALUES ('61', 'fa fa-american-sign-language-interpreting');
INSERT INTO `sys_icon` VALUES ('62', 'fa fa-anchor');
INSERT INTO `sys_icon` VALUES ('63', 'fa fa-archive');
INSERT INTO `sys_icon` VALUES ('64', 'fa fa-area-chart');
INSERT INTO `sys_icon` VALUES ('65', 'fa fa-arrows');
INSERT INTO `sys_icon` VALUES ('66', 'fa fa-arrows-h');
INSERT INTO `sys_icon` VALUES ('67', 'fa fa-arrows-v');
INSERT INTO `sys_icon` VALUES ('68', 'fa fa-asl-interpreting');
INSERT INTO `sys_icon` VALUES ('69', 'fa fa-assistive-listening-systems');
INSERT INTO `sys_icon` VALUES ('70', 'fa fa-asterisk');
INSERT INTO `sys_icon` VALUES ('71', 'fa fa-at');
INSERT INTO `sys_icon` VALUES ('72', 'fa fa-audio-description');
INSERT INTO `sys_icon` VALUES ('73', 'fa fa-automobile');
INSERT INTO `sys_icon` VALUES ('74', 'fa fa-balance-scale');
INSERT INTO `sys_icon` VALUES ('75', 'fa fa-ban');
INSERT INTO `sys_icon` VALUES ('76', 'fa fa-bank');
INSERT INTO `sys_icon` VALUES ('77', 'fa fa-bar-chart');
INSERT INTO `sys_icon` VALUES ('78', 'fa fa-bar-chart-o');
INSERT INTO `sys_icon` VALUES ('79', 'fa fa-barcode');
INSERT INTO `sys_icon` VALUES ('80', 'fa fa-bars');
INSERT INTO `sys_icon` VALUES ('81', 'fa fa-bath');
INSERT INTO `sys_icon` VALUES ('82', 'fa fa-bathtub');
INSERT INTO `sys_icon` VALUES ('83', 'fa fa-battery');
INSERT INTO `sys_icon` VALUES ('84', 'fa fa-battery-0');
INSERT INTO `sys_icon` VALUES ('85', 'fa fa-battery-1');
INSERT INTO `sys_icon` VALUES ('86', 'fa fa-battery-2');
INSERT INTO `sys_icon` VALUES ('87', 'fa fa-battery-3');
INSERT INTO `sys_icon` VALUES ('88', 'fa fa-battery-4');
INSERT INTO `sys_icon` VALUES ('89', 'fa fa-battery-empty');
INSERT INTO `sys_icon` VALUES ('90', 'fa fa-battery-full');
INSERT INTO `sys_icon` VALUES ('91', 'fa fa-battery-half');
INSERT INTO `sys_icon` VALUES ('92', 'fa fa-battery-quarter');
INSERT INTO `sys_icon` VALUES ('93', 'fa fa-battery-three-quarters');
INSERT INTO `sys_icon` VALUES ('94', 'fa fa-bed');
INSERT INTO `sys_icon` VALUES ('95', 'fa fa-beer');
INSERT INTO `sys_icon` VALUES ('96', 'fa fa-bell');
INSERT INTO `sys_icon` VALUES ('97', 'fa fa-bell-o');
INSERT INTO `sys_icon` VALUES ('98', 'fa fa-bell-slash');
INSERT INTO `sys_icon` VALUES ('99', 'fa fa-bell-slash-o');
INSERT INTO `sys_icon` VALUES ('100', 'fa fa-bicycle');
INSERT INTO `sys_icon` VALUES ('101', 'fa fa-binoculars');
INSERT INTO `sys_icon` VALUES ('102', 'fa fa-birthday-cake');
INSERT INTO `sys_icon` VALUES ('103', 'fa fa-blind');
INSERT INTO `sys_icon` VALUES ('104', 'fa fa-bluetooth');
INSERT INTO `sys_icon` VALUES ('105', 'fa fa-bluetooth-b');
INSERT INTO `sys_icon` VALUES ('106', 'fa fa-bolt');
INSERT INTO `sys_icon` VALUES ('107', 'fa fa-bomb');
INSERT INTO `sys_icon` VALUES ('108', 'fa fa-book');
INSERT INTO `sys_icon` VALUES ('109', 'fa fa-bookmark');
INSERT INTO `sys_icon` VALUES ('110', 'fa fa-bookmark-o');
INSERT INTO `sys_icon` VALUES ('111', 'fa fa-braille');
INSERT INTO `sys_icon` VALUES ('112', 'fa fa-briefcase');
INSERT INTO `sys_icon` VALUES ('113', 'fa fa-bug');
INSERT INTO `sys_icon` VALUES ('114', 'fa fa-building');
INSERT INTO `sys_icon` VALUES ('115', 'fa fa-building-o');
INSERT INTO `sys_icon` VALUES ('116', 'fa fa-bullhorn');
INSERT INTO `sys_icon` VALUES ('117', 'fa fa-bullseye');
INSERT INTO `sys_icon` VALUES ('118', 'fa fa-bus');
INSERT INTO `sys_icon` VALUES ('119', 'fa fa-cab');
INSERT INTO `sys_icon` VALUES ('120', 'fa fa-calculator');
INSERT INTO `sys_icon` VALUES ('121', 'fa fa-calendar');
INSERT INTO `sys_icon` VALUES ('122', 'fa fa-calendar-check-o');
INSERT INTO `sys_icon` VALUES ('123', 'fa fa-calendar-minus-o');
INSERT INTO `sys_icon` VALUES ('124', 'fa fa-calendar-o');
INSERT INTO `sys_icon` VALUES ('125', 'fa fa-calendar-plus-o');
INSERT INTO `sys_icon` VALUES ('126', 'fa fa-calendar-times-o');
INSERT INTO `sys_icon` VALUES ('127', 'fa fa-camera');
INSERT INTO `sys_icon` VALUES ('128', 'fa fa-camera-retro');
INSERT INTO `sys_icon` VALUES ('129', 'fa fa-car');
INSERT INTO `sys_icon` VALUES ('130', 'fa fa-caret-square-o-down');
INSERT INTO `sys_icon` VALUES ('131', 'fa fa-caret-square-o-left');
INSERT INTO `sys_icon` VALUES ('132', 'fa fa-caret-square-o-right');
INSERT INTO `sys_icon` VALUES ('133', 'fa fa-caret-square-o-up');
INSERT INTO `sys_icon` VALUES ('134', 'fa fa-cart-arrow-down');
INSERT INTO `sys_icon` VALUES ('135', 'fa fa-cart-plus');
INSERT INTO `sys_icon` VALUES ('136', 'fa fa-cc');
INSERT INTO `sys_icon` VALUES ('137', 'fa fa-certificate');
INSERT INTO `sys_icon` VALUES ('138', 'fa fa-check');
INSERT INTO `sys_icon` VALUES ('139', 'fa fa-check-circle');
INSERT INTO `sys_icon` VALUES ('140', 'fa fa-check-circle-o');
INSERT INTO `sys_icon` VALUES ('141', 'fa fa-check-square');
INSERT INTO `sys_icon` VALUES ('142', 'fa fa-check-square-o');
INSERT INTO `sys_icon` VALUES ('143', 'fa fa-child');
INSERT INTO `sys_icon` VALUES ('144', 'fa fa-circle');
INSERT INTO `sys_icon` VALUES ('145', 'fa fa-circle-o');
INSERT INTO `sys_icon` VALUES ('146', 'fa fa-circle-o-notch');
INSERT INTO `sys_icon` VALUES ('147', 'fa fa-circle-thin');
INSERT INTO `sys_icon` VALUES ('148', 'fa fa-clock-o');
INSERT INTO `sys_icon` VALUES ('149', 'fa fa-clone');
INSERT INTO `sys_icon` VALUES ('150', 'fa fa-close');
INSERT INTO `sys_icon` VALUES ('151', 'fa fa-cloud');
INSERT INTO `sys_icon` VALUES ('152', 'fa fa-cloud-download');
INSERT INTO `sys_icon` VALUES ('153', 'fa fa-cloud-upload');
INSERT INTO `sys_icon` VALUES ('154', 'fa fa-code');
INSERT INTO `sys_icon` VALUES ('155', 'fa fa-code-fork');
INSERT INTO `sys_icon` VALUES ('156', 'fa fa-coffee');
INSERT INTO `sys_icon` VALUES ('157', 'fa fa-cog');
INSERT INTO `sys_icon` VALUES ('158', 'fa fa-cogs');
INSERT INTO `sys_icon` VALUES ('159', 'fa fa-comment');
INSERT INTO `sys_icon` VALUES ('160', 'fa fa-comment-o');
INSERT INTO `sys_icon` VALUES ('161', 'fa fa-commenting');
INSERT INTO `sys_icon` VALUES ('162', 'fa fa-commenting-o');
INSERT INTO `sys_icon` VALUES ('163', 'fa fa-comments');
INSERT INTO `sys_icon` VALUES ('164', 'fa fa-comments-o');
INSERT INTO `sys_icon` VALUES ('165', 'fa fa-compass');
INSERT INTO `sys_icon` VALUES ('166', 'fa fa-copyright');
INSERT INTO `sys_icon` VALUES ('167', 'fa fa-creative-commons');
INSERT INTO `sys_icon` VALUES ('168', 'fa fa-credit-card');
INSERT INTO `sys_icon` VALUES ('169', 'fa fa-credit-card-alt');
INSERT INTO `sys_icon` VALUES ('170', 'fa fa-crop');
INSERT INTO `sys_icon` VALUES ('171', 'fa fa-crosshairs');
INSERT INTO `sys_icon` VALUES ('172', 'fa fa-cube');
INSERT INTO `sys_icon` VALUES ('173', 'fa fa-cubes');
INSERT INTO `sys_icon` VALUES ('174', 'fa fa-cutlery');
INSERT INTO `sys_icon` VALUES ('175', 'fa fa-dashboard');
INSERT INTO `sys_icon` VALUES ('176', 'fa fa-database');
INSERT INTO `sys_icon` VALUES ('177', 'fa fa-deaf');
INSERT INTO `sys_icon` VALUES ('178', 'fa fa-deafness');
INSERT INTO `sys_icon` VALUES ('179', 'fa fa-desktop');
INSERT INTO `sys_icon` VALUES ('180', 'fa fa-diamond');
INSERT INTO `sys_icon` VALUES ('181', 'fa fa-dot-circle-o');
INSERT INTO `sys_icon` VALUES ('182', 'fa fa-download');
INSERT INTO `sys_icon` VALUES ('183', 'fa fa-drivers-license');
INSERT INTO `sys_icon` VALUES ('184', 'fa fa-drivers-license-o');
INSERT INTO `sys_icon` VALUES ('185', 'fa fa-edit');
INSERT INTO `sys_icon` VALUES ('186', 'fa fa-ellipsis-h');
INSERT INTO `sys_icon` VALUES ('187', 'fa fa-ellipsis-v');
INSERT INTO `sys_icon` VALUES ('188', 'fa fa-envelope');
INSERT INTO `sys_icon` VALUES ('189', 'fa fa-envelope-o');
INSERT INTO `sys_icon` VALUES ('190', 'fa fa-envelope-open');
INSERT INTO `sys_icon` VALUES ('191', 'fa fa-envelope-open-o');
INSERT INTO `sys_icon` VALUES ('192', 'fa fa-envelope-square');
INSERT INTO `sys_icon` VALUES ('193', 'fa fa-eraser');
INSERT INTO `sys_icon` VALUES ('194', 'fa fa-exchange');
INSERT INTO `sys_icon` VALUES ('195', 'fa fa-exclamation');
INSERT INTO `sys_icon` VALUES ('196', 'fa fa-exclamation-circle');
INSERT INTO `sys_icon` VALUES ('197', 'fa fa-exclamation-triangle');
INSERT INTO `sys_icon` VALUES ('198', 'fa fa-external-link');
INSERT INTO `sys_icon` VALUES ('199', 'fa fa-external-link-square');
INSERT INTO `sys_icon` VALUES ('200', 'fa fa-eye');
INSERT INTO `sys_icon` VALUES ('201', 'fa fa-eye-slash');
INSERT INTO `sys_icon` VALUES ('202', 'fa fa-eyedropper');
INSERT INTO `sys_icon` VALUES ('203', 'fa fa-fax');
INSERT INTO `sys_icon` VALUES ('204', 'fa fa-feed');
INSERT INTO `sys_icon` VALUES ('205', 'fa fa-female');
INSERT INTO `sys_icon` VALUES ('206', 'fa fa-fighter-jet');
INSERT INTO `sys_icon` VALUES ('207', 'fa fa-file-archive-o');
INSERT INTO `sys_icon` VALUES ('208', 'fa fa-file-audio-o');
INSERT INTO `sys_icon` VALUES ('209', 'fa fa-file-code-o');
INSERT INTO `sys_icon` VALUES ('210', 'fa fa-file-excel-o');
INSERT INTO `sys_icon` VALUES ('211', 'fa fa-file-image-o');
INSERT INTO `sys_icon` VALUES ('212', 'fa fa-file-movie-o');
INSERT INTO `sys_icon` VALUES ('213', 'fa fa-file-pdf-o');
INSERT INTO `sys_icon` VALUES ('214', 'fa fa-file-photo-o');
INSERT INTO `sys_icon` VALUES ('215', 'fa fa-file-picture-o');
INSERT INTO `sys_icon` VALUES ('216', 'fa fa-file-powerpoint-o');
INSERT INTO `sys_icon` VALUES ('217', 'fa fa-file-sound-o');
INSERT INTO `sys_icon` VALUES ('218', 'fa fa-file-video-o');
INSERT INTO `sys_icon` VALUES ('219', 'fa fa-file-word-o');
INSERT INTO `sys_icon` VALUES ('220', 'fa fa-file-zip-o');
INSERT INTO `sys_icon` VALUES ('221', 'fa fa-film');
INSERT INTO `sys_icon` VALUES ('222', 'fa fa-filter');
INSERT INTO `sys_icon` VALUES ('223', 'fa fa-fire');
INSERT INTO `sys_icon` VALUES ('224', 'fa fa-fire-extinguisher');
INSERT INTO `sys_icon` VALUES ('225', 'fa fa-flag');
INSERT INTO `sys_icon` VALUES ('226', 'fa fa-flag-checkered');
INSERT INTO `sys_icon` VALUES ('227', 'fa fa-flag-o');
INSERT INTO `sys_icon` VALUES ('228', 'fa fa-flash');
INSERT INTO `sys_icon` VALUES ('229', 'fa fa-flask');
INSERT INTO `sys_icon` VALUES ('230', 'fa fa-folder');
INSERT INTO `sys_icon` VALUES ('231', 'fa fa-folder-o');
INSERT INTO `sys_icon` VALUES ('232', 'fa fa-folder-open');
INSERT INTO `sys_icon` VALUES ('233', 'fa fa-folder-open-o');
INSERT INTO `sys_icon` VALUES ('234', 'fa fa-frown-o');
INSERT INTO `sys_icon` VALUES ('235', 'fa fa-futbol-o');
INSERT INTO `sys_icon` VALUES ('236', 'fa fa-gamepad');
INSERT INTO `sys_icon` VALUES ('237', 'fa fa-gavel');
INSERT INTO `sys_icon` VALUES ('238', 'fa fa-gear');
INSERT INTO `sys_icon` VALUES ('239', 'fa fa-gears');
INSERT INTO `sys_icon` VALUES ('240', 'fa fa-gift');
INSERT INTO `sys_icon` VALUES ('241', 'fa fa-glass');
INSERT INTO `sys_icon` VALUES ('242', 'fa fa-globe');
INSERT INTO `sys_icon` VALUES ('243', 'fa fa-graduation-cap');
INSERT INTO `sys_icon` VALUES ('244', 'fa fa-group');
INSERT INTO `sys_icon` VALUES ('245', 'fa fa-hand-grab-o');
INSERT INTO `sys_icon` VALUES ('246', 'fa fa-hand-lizard-o');
INSERT INTO `sys_icon` VALUES ('247', 'fa fa-hand-paper-o');
INSERT INTO `sys_icon` VALUES ('248', 'fa fa-hand-peace-o');
INSERT INTO `sys_icon` VALUES ('249', 'fa fa-hand-pointer-o');
INSERT INTO `sys_icon` VALUES ('250', 'fa fa-hand-rock-o');
INSERT INTO `sys_icon` VALUES ('251', 'fa fa-hand-scissors-o');
INSERT INTO `sys_icon` VALUES ('252', 'fa fa-hand-spock-o');
INSERT INTO `sys_icon` VALUES ('253', 'fa fa-hand-stop-o');
INSERT INTO `sys_icon` VALUES ('254', 'fa fa-handshake-o');
INSERT INTO `sys_icon` VALUES ('255', 'fa fa-hard-of-hearing');
INSERT INTO `sys_icon` VALUES ('256', 'fa fa-hashtag');
INSERT INTO `sys_icon` VALUES ('257', 'fa fa-hdd-o');
INSERT INTO `sys_icon` VALUES ('258', 'fa fa-headphones');
INSERT INTO `sys_icon` VALUES ('259', 'fa fa-heart');
INSERT INTO `sys_icon` VALUES ('260', 'fa fa-heart-o');
INSERT INTO `sys_icon` VALUES ('261', 'fa fa-heartbeat');
INSERT INTO `sys_icon` VALUES ('262', 'fa fa-history');
INSERT INTO `sys_icon` VALUES ('263', 'fa fa-home');
INSERT INTO `sys_icon` VALUES ('264', 'fa fa-hotel');
INSERT INTO `sys_icon` VALUES ('265', 'fa fa-hourglass');
INSERT INTO `sys_icon` VALUES ('266', 'fa fa-hourglass-1');
INSERT INTO `sys_icon` VALUES ('267', 'fa fa-hourglass-2');
INSERT INTO `sys_icon` VALUES ('268', 'fa fa-hourglass-3');
INSERT INTO `sys_icon` VALUES ('269', 'fa fa-hourglass-end');
INSERT INTO `sys_icon` VALUES ('270', 'fa fa-hourglass-half');
INSERT INTO `sys_icon` VALUES ('271', 'fa fa-hourglass-o');
INSERT INTO `sys_icon` VALUES ('272', 'fa fa-hourglass-start');
INSERT INTO `sys_icon` VALUES ('273', 'fa fa-i-cursor');
INSERT INTO `sys_icon` VALUES ('274', 'fa fa-id-badge');
INSERT INTO `sys_icon` VALUES ('275', 'fa fa-id-card');
INSERT INTO `sys_icon` VALUES ('276', 'fa fa-id-card-o');
INSERT INTO `sys_icon` VALUES ('277', 'fa fa-image');
INSERT INTO `sys_icon` VALUES ('278', 'fa fa-inbox');
INSERT INTO `sys_icon` VALUES ('279', 'fa fa-industry');
INSERT INTO `sys_icon` VALUES ('280', 'fa fa-info');
INSERT INTO `sys_icon` VALUES ('281', 'fa fa-info-circle');
INSERT INTO `sys_icon` VALUES ('282', 'fa fa-institution');
INSERT INTO `sys_icon` VALUES ('283', 'fa fa-key');
INSERT INTO `sys_icon` VALUES ('284', 'fa fa-keyboard-o');
INSERT INTO `sys_icon` VALUES ('285', 'fa fa-language');
INSERT INTO `sys_icon` VALUES ('286', 'fa fa-laptop');
INSERT INTO `sys_icon` VALUES ('287', 'fa fa-leaf');
INSERT INTO `sys_icon` VALUES ('288', 'fa fa-legal');
INSERT INTO `sys_icon` VALUES ('289', 'fa fa-lemon-o');
INSERT INTO `sys_icon` VALUES ('290', 'fa fa-level-down');
INSERT INTO `sys_icon` VALUES ('291', 'fa fa-level-up');
INSERT INTO `sys_icon` VALUES ('292', 'fa fa-life-bouy');
INSERT INTO `sys_icon` VALUES ('293', 'fa fa-life-buoy');
INSERT INTO `sys_icon` VALUES ('294', 'fa fa-life-ring');
INSERT INTO `sys_icon` VALUES ('295', 'fa fa-life-saver');
INSERT INTO `sys_icon` VALUES ('296', 'fa fa-lightbulb-o');
INSERT INTO `sys_icon` VALUES ('297', 'fa fa-line-chart');
INSERT INTO `sys_icon` VALUES ('298', 'fa fa-location-arrow');
INSERT INTO `sys_icon` VALUES ('299', 'fa fa-lock');
INSERT INTO `sys_icon` VALUES ('300', 'fa fa-low-vision');
INSERT INTO `sys_icon` VALUES ('301', 'fa fa-magic');
INSERT INTO `sys_icon` VALUES ('302', 'fa fa-magnet');
INSERT INTO `sys_icon` VALUES ('303', 'fa fa-mail-forward');
INSERT INTO `sys_icon` VALUES ('304', 'fa fa-mail-reply');
INSERT INTO `sys_icon` VALUES ('305', 'fa fa-mail-reply-all');
INSERT INTO `sys_icon` VALUES ('306', 'fa fa-male');
INSERT INTO `sys_icon` VALUES ('307', 'fa fa-map');
INSERT INTO `sys_icon` VALUES ('308', 'fa fa-map-marker');
INSERT INTO `sys_icon` VALUES ('309', 'fa fa-map-o');
INSERT INTO `sys_icon` VALUES ('310', 'fa fa-map-pin');
INSERT INTO `sys_icon` VALUES ('311', 'fa fa-map-signs');
INSERT INTO `sys_icon` VALUES ('312', 'fa fa-meh-o');
INSERT INTO `sys_icon` VALUES ('313', 'fa fa-microchip');
INSERT INTO `sys_icon` VALUES ('314', 'fa fa-microphone');
INSERT INTO `sys_icon` VALUES ('315', 'fa fa-microphone-slash');
INSERT INTO `sys_icon` VALUES ('316', 'fa fa-minus');
INSERT INTO `sys_icon` VALUES ('317', 'fa fa-minus-circle');
INSERT INTO `sys_icon` VALUES ('318', 'fa fa-minus-square');
INSERT INTO `sys_icon` VALUES ('319', 'fa fa-minus-square-o');
INSERT INTO `sys_icon` VALUES ('320', 'fa fa-mobile');
INSERT INTO `sys_icon` VALUES ('321', 'fa fa-mobile-phone');
INSERT INTO `sys_icon` VALUES ('322', 'fa fa-money');
INSERT INTO `sys_icon` VALUES ('323', 'fa fa-moon-o');
INSERT INTO `sys_icon` VALUES ('324', 'fa fa-mortar-board');
INSERT INTO `sys_icon` VALUES ('325', 'fa fa-motorcycle');
INSERT INTO `sys_icon` VALUES ('326', 'fa fa-mouse-pointer');
INSERT INTO `sys_icon` VALUES ('327', 'fa fa-music');
INSERT INTO `sys_icon` VALUES ('328', 'fa fa-navicon');
INSERT INTO `sys_icon` VALUES ('329', 'fa fa-newspaper-o');
INSERT INTO `sys_icon` VALUES ('330', 'fa fa-object-group');
INSERT INTO `sys_icon` VALUES ('331', 'fa fa-object-ungroup');
INSERT INTO `sys_icon` VALUES ('332', 'fa fa-paint-brush');
INSERT INTO `sys_icon` VALUES ('333', 'fa fa-paper-plane');
INSERT INTO `sys_icon` VALUES ('334', 'fa fa-paper-plane-o');
INSERT INTO `sys_icon` VALUES ('335', 'fa fa-paw');
INSERT INTO `sys_icon` VALUES ('336', 'fa fa-pencil');
INSERT INTO `sys_icon` VALUES ('337', 'fa fa-pencil-square');
INSERT INTO `sys_icon` VALUES ('338', 'fa fa-pencil-square-o');
INSERT INTO `sys_icon` VALUES ('339', 'fa fa-percent');
INSERT INTO `sys_icon` VALUES ('340', 'fa fa-phone');
INSERT INTO `sys_icon` VALUES ('341', 'fa fa-phone-square');
INSERT INTO `sys_icon` VALUES ('342', 'fa fa-photo');
INSERT INTO `sys_icon` VALUES ('343', 'fa fa-picture-o');
INSERT INTO `sys_icon` VALUES ('344', 'fa fa-pie-chart');
INSERT INTO `sys_icon` VALUES ('345', 'fa fa-plane');
INSERT INTO `sys_icon` VALUES ('346', 'fa fa-plug');
INSERT INTO `sys_icon` VALUES ('347', 'fa fa-plus');
INSERT INTO `sys_icon` VALUES ('348', 'fa fa-plus-circle');
INSERT INTO `sys_icon` VALUES ('349', 'fa fa-plus-square');
INSERT INTO `sys_icon` VALUES ('350', 'fa fa-plus-square-o');
INSERT INTO `sys_icon` VALUES ('351', 'fa fa-podcast');
INSERT INTO `sys_icon` VALUES ('352', 'fa fa-power-off');
INSERT INTO `sys_icon` VALUES ('353', 'fa fa-print');
INSERT INTO `sys_icon` VALUES ('354', 'fa fa-puzzle-piece');
INSERT INTO `sys_icon` VALUES ('355', 'fa fa-qrcode');
INSERT INTO `sys_icon` VALUES ('356', 'fa fa-question');
INSERT INTO `sys_icon` VALUES ('357', 'fa fa-question-circle');
INSERT INTO `sys_icon` VALUES ('358', 'fa fa-question-circle-o');
INSERT INTO `sys_icon` VALUES ('359', 'fa fa-quote-left');
INSERT INTO `sys_icon` VALUES ('360', 'fa fa-quote-right');
INSERT INTO `sys_icon` VALUES ('361', 'fa fa-random');
INSERT INTO `sys_icon` VALUES ('362', 'fa fa-recycle');
INSERT INTO `sys_icon` VALUES ('363', 'fa fa-refresh');
INSERT INTO `sys_icon` VALUES ('364', 'fa fa-registered');
INSERT INTO `sys_icon` VALUES ('365', 'fa fa-remove');
INSERT INTO `sys_icon` VALUES ('366', 'fa fa-reorder');
INSERT INTO `sys_icon` VALUES ('367', 'fa fa-reply');
INSERT INTO `sys_icon` VALUES ('368', 'fa fa-reply-all');
INSERT INTO `sys_icon` VALUES ('369', 'fa fa-retweet');
INSERT INTO `sys_icon` VALUES ('370', 'fa fa-road');
INSERT INTO `sys_icon` VALUES ('371', 'fa fa-rocket');
INSERT INTO `sys_icon` VALUES ('372', 'fa fa-rss');
INSERT INTO `sys_icon` VALUES ('373', 'fa fa-rss-square');
INSERT INTO `sys_icon` VALUES ('374', 'fa fa-s15');
INSERT INTO `sys_icon` VALUES ('375', 'fa fa-search');
INSERT INTO `sys_icon` VALUES ('376', 'fa fa-search-minus');
INSERT INTO `sys_icon` VALUES ('377', 'fa fa-search-plus');
INSERT INTO `sys_icon` VALUES ('378', 'fa fa-send');
INSERT INTO `sys_icon` VALUES ('379', 'fa fa-send-o');
INSERT INTO `sys_icon` VALUES ('380', 'fa fa-server');
INSERT INTO `sys_icon` VALUES ('381', 'fa fa-share');
INSERT INTO `sys_icon` VALUES ('382', 'fa fa-share-alt');
INSERT INTO `sys_icon` VALUES ('383', 'fa fa-share-alt-square');
INSERT INTO `sys_icon` VALUES ('384', 'fa fa-share-square');
INSERT INTO `sys_icon` VALUES ('385', 'fa fa-share-square-o');
INSERT INTO `sys_icon` VALUES ('386', 'fa fa-shield');
INSERT INTO `sys_icon` VALUES ('387', 'fa fa-ship');
INSERT INTO `sys_icon` VALUES ('388', 'fa fa-shopping-bag');
INSERT INTO `sys_icon` VALUES ('389', 'fa fa-shopping-basket');
INSERT INTO `sys_icon` VALUES ('390', 'fa fa-shopping-cart');
INSERT INTO `sys_icon` VALUES ('391', 'fa fa-shower');
INSERT INTO `sys_icon` VALUES ('392', 'fa fa-sign-in');
INSERT INTO `sys_icon` VALUES ('393', 'fa fa-sign-language');
INSERT INTO `sys_icon` VALUES ('394', 'fa fa-sign-out');
INSERT INTO `sys_icon` VALUES ('395', 'fa fa-signal');
INSERT INTO `sys_icon` VALUES ('396', 'fa fa-signing');
INSERT INTO `sys_icon` VALUES ('397', 'fa fa-sitemap');
INSERT INTO `sys_icon` VALUES ('398', 'fa fa-sliders');
INSERT INTO `sys_icon` VALUES ('399', 'fa fa-smile-o');
INSERT INTO `sys_icon` VALUES ('400', 'fa fa-snowflake-o');
INSERT INTO `sys_icon` VALUES ('401', 'fa fa-soccer-ball-o');
INSERT INTO `sys_icon` VALUES ('402', 'fa fa-sort');
INSERT INTO `sys_icon` VALUES ('403', 'fa fa-sort-alpha-asc');
INSERT INTO `sys_icon` VALUES ('404', 'fa fa-sort-alpha-desc');
INSERT INTO `sys_icon` VALUES ('405', 'fa fa-sort-amount-asc');
INSERT INTO `sys_icon` VALUES ('406', 'fa fa-sort-amount-desc');
INSERT INTO `sys_icon` VALUES ('407', 'fa fa-sort-asc');
INSERT INTO `sys_icon` VALUES ('408', 'fa fa-sort-desc');
INSERT INTO `sys_icon` VALUES ('409', 'fa fa-sort-down');
INSERT INTO `sys_icon` VALUES ('410', 'fa fa-sort-numeric-asc');
INSERT INTO `sys_icon` VALUES ('411', 'fa fa-sort-numeric-desc');
INSERT INTO `sys_icon` VALUES ('412', 'fa fa-sort-up');
INSERT INTO `sys_icon` VALUES ('413', 'fa fa-space-shuttle');
INSERT INTO `sys_icon` VALUES ('414', 'fa fa-spinner');
INSERT INTO `sys_icon` VALUES ('415', 'fa fa-spoon');
INSERT INTO `sys_icon` VALUES ('416', 'fa fa-square');
INSERT INTO `sys_icon` VALUES ('417', 'fa fa-square-o');
INSERT INTO `sys_icon` VALUES ('418', 'fa fa-star');
INSERT INTO `sys_icon` VALUES ('419', 'fa fa-star-half');
INSERT INTO `sys_icon` VALUES ('420', 'fa fa-star-half-empty');
INSERT INTO `sys_icon` VALUES ('421', 'fa fa-star-half-full');
INSERT INTO `sys_icon` VALUES ('422', 'fa fa-star-half-o');
INSERT INTO `sys_icon` VALUES ('423', 'fa fa-star-o');
INSERT INTO `sys_icon` VALUES ('424', 'fa fa-sticky-note');
INSERT INTO `sys_icon` VALUES ('425', 'fa fa-sticky-note-o');
INSERT INTO `sys_icon` VALUES ('426', 'fa fa-street-view');
INSERT INTO `sys_icon` VALUES ('427', 'fa fa-suitcase');
INSERT INTO `sys_icon` VALUES ('428', 'fa fa-sun-o');
INSERT INTO `sys_icon` VALUES ('429', 'fa fa-support');
INSERT INTO `sys_icon` VALUES ('430', 'fa fa-tablet');
INSERT INTO `sys_icon` VALUES ('431', 'fa fa-tachometer');
INSERT INTO `sys_icon` VALUES ('432', 'fa fa-tag');
INSERT INTO `sys_icon` VALUES ('433', 'fa fa-tags');
INSERT INTO `sys_icon` VALUES ('434', 'fa fa-tasks');
INSERT INTO `sys_icon` VALUES ('435', 'fa fa-taxi');
INSERT INTO `sys_icon` VALUES ('436', 'fa fa-television');
INSERT INTO `sys_icon` VALUES ('437', 'fa fa-terminal');
INSERT INTO `sys_icon` VALUES ('438', 'fa fa-thermometer');
INSERT INTO `sys_icon` VALUES ('439', 'fa fa-thermometer-0');
INSERT INTO `sys_icon` VALUES ('440', 'fa fa-thermometer-1');
INSERT INTO `sys_icon` VALUES ('441', 'fa fa-thermometer-2');
INSERT INTO `sys_icon` VALUES ('442', 'fa fa-thermometer-3');
INSERT INTO `sys_icon` VALUES ('443', 'fa fa-thermometer-4');
INSERT INTO `sys_icon` VALUES ('444', 'fa fa-thermometer-empty');
INSERT INTO `sys_icon` VALUES ('445', 'fa fa-thermometer-full');
INSERT INTO `sys_icon` VALUES ('446', 'fa fa-thermometer-half');
INSERT INTO `sys_icon` VALUES ('447', 'fa fa-thermometer-quarter');
INSERT INTO `sys_icon` VALUES ('448', 'fa fa-thermometer-three-quarters');
INSERT INTO `sys_icon` VALUES ('449', 'fa fa-thumb-tack');
INSERT INTO `sys_icon` VALUES ('450', 'fa fa-thumbs-down');
INSERT INTO `sys_icon` VALUES ('451', 'fa fa-thumbs-o-down');
INSERT INTO `sys_icon` VALUES ('452', 'fa fa-thumbs-o-up');
INSERT INTO `sys_icon` VALUES ('453', 'fa fa-thumbs-up');
INSERT INTO `sys_icon` VALUES ('454', 'fa fa-ticket');
INSERT INTO `sys_icon` VALUES ('455', 'fa fa-times');
INSERT INTO `sys_icon` VALUES ('456', 'fa fa-times-circle');
INSERT INTO `sys_icon` VALUES ('457', 'fa fa-times-circle-o');
INSERT INTO `sys_icon` VALUES ('458', 'fa fa-times-rectangle');
INSERT INTO `sys_icon` VALUES ('459', 'fa fa-times-rectangle-o');
INSERT INTO `sys_icon` VALUES ('460', 'fa fa-tint');
INSERT INTO `sys_icon` VALUES ('461', 'fa fa-toggle-down');
INSERT INTO `sys_icon` VALUES ('462', 'fa fa-toggle-left');
INSERT INTO `sys_icon` VALUES ('463', 'fa fa-toggle-off');
INSERT INTO `sys_icon` VALUES ('464', 'fa fa-toggle-on');
INSERT INTO `sys_icon` VALUES ('465', 'fa fa-toggle-right');
INSERT INTO `sys_icon` VALUES ('466', 'fa fa-toggle-up');
INSERT INTO `sys_icon` VALUES ('467', 'fa fa-trademark');
INSERT INTO `sys_icon` VALUES ('468', 'fa fa-trash');
INSERT INTO `sys_icon` VALUES ('469', 'fa fa-trash-o');
INSERT INTO `sys_icon` VALUES ('470', 'fa fa-tree');
INSERT INTO `sys_icon` VALUES ('471', 'fa fa-trophy');
INSERT INTO `sys_icon` VALUES ('472', 'fa fa-truck');
INSERT INTO `sys_icon` VALUES ('473', 'fa fa-tty');
INSERT INTO `sys_icon` VALUES ('474', 'fa fa-tv');
INSERT INTO `sys_icon` VALUES ('475', 'fa fa-umbrella');
INSERT INTO `sys_icon` VALUES ('476', 'fa fa-universal-access');
INSERT INTO `sys_icon` VALUES ('477', 'fa fa-university');
INSERT INTO `sys_icon` VALUES ('478', 'fa fa-unlock');
INSERT INTO `sys_icon` VALUES ('479', 'fa fa-unlock-alt');
INSERT INTO `sys_icon` VALUES ('480', 'fa fa-unsorted');
INSERT INTO `sys_icon` VALUES ('481', 'fa fa-upload');
INSERT INTO `sys_icon` VALUES ('482', 'fa fa-user');
INSERT INTO `sys_icon` VALUES ('483', 'fa fa-user-circle');
INSERT INTO `sys_icon` VALUES ('484', 'fa fa-user-circle-o');
INSERT INTO `sys_icon` VALUES ('485', 'fa fa-user-o');
INSERT INTO `sys_icon` VALUES ('486', 'fa fa-user-plus');
INSERT INTO `sys_icon` VALUES ('487', 'fa fa-user-secret');
INSERT INTO `sys_icon` VALUES ('488', 'fa fa-user-times');
INSERT INTO `sys_icon` VALUES ('489', 'fa fa-users');
INSERT INTO `sys_icon` VALUES ('490', 'fa fa-vcard');
INSERT INTO `sys_icon` VALUES ('491', 'fa fa-vcard-o');
INSERT INTO `sys_icon` VALUES ('492', 'fa fa-video-camera');
INSERT INTO `sys_icon` VALUES ('493', 'fa fa-volume-control-phone');
INSERT INTO `sys_icon` VALUES ('494', 'fa fa-volume-down');
INSERT INTO `sys_icon` VALUES ('495', 'fa fa-volume-off');
INSERT INTO `sys_icon` VALUES ('496', 'fa fa-volume-up');
INSERT INTO `sys_icon` VALUES ('497', 'fa fa-warning');
INSERT INTO `sys_icon` VALUES ('498', 'fa fa-wheelchair');
INSERT INTO `sys_icon` VALUES ('499', 'fa fa-wheelchair-alt');
INSERT INTO `sys_icon` VALUES ('500', 'fa fa-wifi');
INSERT INTO `sys_icon` VALUES ('501', 'fa fa-window-close');
INSERT INTO `sys_icon` VALUES ('502', 'fa fa-window-close-o');
INSERT INTO `sys_icon` VALUES ('503', 'fa fa-window-maximize');
INSERT INTO `sys_icon` VALUES ('504', 'fa fa-window-minimize');
INSERT INTO `sys_icon` VALUES ('505', 'fa fa-window-restore');
INSERT INTO `sys_icon` VALUES ('506', 'fa fa-wrench');
INSERT INTO `sys_icon` VALUES ('507', 'fa fa-american-sign-language-interpreting');
INSERT INTO `sys_icon` VALUES ('508', 'fa fa-asl-interpreting');
INSERT INTO `sys_icon` VALUES ('509', 'fa fa-assistive-listening-systems');
INSERT INTO `sys_icon` VALUES ('510', 'fa fa-audio-description');
INSERT INTO `sys_icon` VALUES ('511', 'fa fa-blind');
INSERT INTO `sys_icon` VALUES ('512', 'fa fa-braille');
INSERT INTO `sys_icon` VALUES ('513', 'fa fa-cc');
INSERT INTO `sys_icon` VALUES ('514', 'fa fa-deaf');
INSERT INTO `sys_icon` VALUES ('515', 'fa fa-deafness');
INSERT INTO `sys_icon` VALUES ('516', 'fa fa-hard-of-hearing');
INSERT INTO `sys_icon` VALUES ('517', 'fa fa-low-vision');
INSERT INTO `sys_icon` VALUES ('518', 'fa fa-question-circle-o');
INSERT INTO `sys_icon` VALUES ('519', 'fa fa-sign-language');
INSERT INTO `sys_icon` VALUES ('520', 'fa fa-signing');
INSERT INTO `sys_icon` VALUES ('521', 'fa fa-tty');
INSERT INTO `sys_icon` VALUES ('522', 'fa fa-universal-access');
INSERT INTO `sys_icon` VALUES ('523', 'fa fa-volume-control-phone');
INSERT INTO `sys_icon` VALUES ('524', 'fa fa-wheelchair');
INSERT INTO `sys_icon` VALUES ('525', 'fa fa-wheelchair-alt');
INSERT INTO `sys_icon` VALUES ('526', 'fa fa-hand-grab-o');
INSERT INTO `sys_icon` VALUES ('527', 'fa fa-hand-lizard-o');
INSERT INTO `sys_icon` VALUES ('528', 'fa fa-hand-o-down');
INSERT INTO `sys_icon` VALUES ('529', 'fa fa-hand-o-left');
INSERT INTO `sys_icon` VALUES ('530', 'fa fa-hand-o-right');
INSERT INTO `sys_icon` VALUES ('531', 'fa fa-hand-o-up');
INSERT INTO `sys_icon` VALUES ('532', 'fa fa-hand-paper-o');
INSERT INTO `sys_icon` VALUES ('533', 'fa fa-hand-peace-o');
INSERT INTO `sys_icon` VALUES ('534', 'fa fa-hand-pointer-o');
INSERT INTO `sys_icon` VALUES ('535', 'fa fa-hand-rock-o');
INSERT INTO `sys_icon` VALUES ('536', 'fa fa-hand-scissors-o');
INSERT INTO `sys_icon` VALUES ('537', 'fa fa-hand-spock-o');
INSERT INTO `sys_icon` VALUES ('538', 'fa fa-hand-stop-o');
INSERT INTO `sys_icon` VALUES ('539', 'fa fa-thumbs-down');
INSERT INTO `sys_icon` VALUES ('540', 'fa fa-thumbs-o-down');
INSERT INTO `sys_icon` VALUES ('541', 'fa fa-thumbs-o-up');
INSERT INTO `sys_icon` VALUES ('542', 'fa fa-thumbs-up');
INSERT INTO `sys_icon` VALUES ('543', 'fa fa-ambulance');
INSERT INTO `sys_icon` VALUES ('544', 'fa fa-automobile');
INSERT INTO `sys_icon` VALUES ('545', 'fa fa-bicycle');
INSERT INTO `sys_icon` VALUES ('546', 'fa fa-bus');
INSERT INTO `sys_icon` VALUES ('547', 'fa fa-cab');
INSERT INTO `sys_icon` VALUES ('548', 'fa fa-car');
INSERT INTO `sys_icon` VALUES ('549', 'fa fa-fighter-jet');
INSERT INTO `sys_icon` VALUES ('550', 'fa fa-motorcycle');
INSERT INTO `sys_icon` VALUES ('551', 'fa fa-plane');
INSERT INTO `sys_icon` VALUES ('552', 'fa fa-rocket');
INSERT INTO `sys_icon` VALUES ('553', 'fa fa-ship');
INSERT INTO `sys_icon` VALUES ('554', 'fa fa-space-shuttle');
INSERT INTO `sys_icon` VALUES ('555', 'fa fa-subway');
INSERT INTO `sys_icon` VALUES ('556', 'fa fa-taxi');
INSERT INTO `sys_icon` VALUES ('557', 'fa fa-train');
INSERT INTO `sys_icon` VALUES ('558', 'fa fa-truck');
INSERT INTO `sys_icon` VALUES ('559', 'fa fa-wheelchair');
INSERT INTO `sys_icon` VALUES ('560', 'fa fa-wheelchair-alt');
INSERT INTO `sys_icon` VALUES ('561', 'fa fa-genderless');
INSERT INTO `sys_icon` VALUES ('562', 'fa fa-intersex');
INSERT INTO `sys_icon` VALUES ('563', 'fa fa-mars');
INSERT INTO `sys_icon` VALUES ('564', 'fa fa-mars-double');
INSERT INTO `sys_icon` VALUES ('565', 'fa fa-mars-stroke');
INSERT INTO `sys_icon` VALUES ('566', 'fa fa-mars-stroke-h');
INSERT INTO `sys_icon` VALUES ('567', 'fa fa-mars-stroke-v');
INSERT INTO `sys_icon` VALUES ('568', 'fa fa-mercury');
INSERT INTO `sys_icon` VALUES ('569', 'fa fa-neuter');
INSERT INTO `sys_icon` VALUES ('570', 'fa fa-transgender');
INSERT INTO `sys_icon` VALUES ('571', 'fa fa-transgender-alt');
INSERT INTO `sys_icon` VALUES ('572', 'fa fa-venus');
INSERT INTO `sys_icon` VALUES ('573', 'fa fa-venus-double');
INSERT INTO `sys_icon` VALUES ('574', 'fa fa-venus-mars');
INSERT INTO `sys_icon` VALUES ('575', 'fa fa-file');
INSERT INTO `sys_icon` VALUES ('576', 'fa fa-file-archive-o');
INSERT INTO `sys_icon` VALUES ('577', 'fa fa-file-audio-o');
INSERT INTO `sys_icon` VALUES ('578', 'fa fa-file-code-o');
INSERT INTO `sys_icon` VALUES ('579', 'fa fa-file-excel-o');
INSERT INTO `sys_icon` VALUES ('580', 'fa fa-file-image-o');
INSERT INTO `sys_icon` VALUES ('581', 'fa fa-file-movie-o');
INSERT INTO `sys_icon` VALUES ('582', 'fa fa-file-o');
INSERT INTO `sys_icon` VALUES ('583', 'fa fa-file-pdf-o');
INSERT INTO `sys_icon` VALUES ('584', 'fa fa-file-photo-o');
INSERT INTO `sys_icon` VALUES ('585', 'fa fa-file-picture-o');
INSERT INTO `sys_icon` VALUES ('586', 'fa fa-file-powerpoint-o');
INSERT INTO `sys_icon` VALUES ('587', 'fa fa-file-sound-o');
INSERT INTO `sys_icon` VALUES ('588', 'fa fa-file-text');
INSERT INTO `sys_icon` VALUES ('589', 'fa fa-file-text-o');
INSERT INTO `sys_icon` VALUES ('590', 'fa fa-file-video-o');
INSERT INTO `sys_icon` VALUES ('591', 'fa fa-file-word-o');
INSERT INTO `sys_icon` VALUES ('592', 'fa fa-file-zip-o');
INSERT INTO `sys_icon` VALUES ('593', 'fa fa-circle-o-notch');
INSERT INTO `sys_icon` VALUES ('594', 'fa fa-cog');
INSERT INTO `sys_icon` VALUES ('595', 'fa fa-gear');
INSERT INTO `sys_icon` VALUES ('596', 'fa fa-refresh');
INSERT INTO `sys_icon` VALUES ('597', 'fa fa-spinner');
INSERT INTO `sys_icon` VALUES ('598', 'fa fa-check-square');
INSERT INTO `sys_icon` VALUES ('599', 'fa fa-check-square-o');
INSERT INTO `sys_icon` VALUES ('600', 'fa fa-circle');
INSERT INTO `sys_icon` VALUES ('601', 'fa fa-circle-o');
INSERT INTO `sys_icon` VALUES ('602', 'fa fa-dot-circle-o');
INSERT INTO `sys_icon` VALUES ('603', 'fa fa-minus-square');
INSERT INTO `sys_icon` VALUES ('604', 'fa fa-minus-square-o');
INSERT INTO `sys_icon` VALUES ('605', 'fa fa-plus-square');
INSERT INTO `sys_icon` VALUES ('606', 'fa fa-plus-square-o');
INSERT INTO `sys_icon` VALUES ('607', 'fa fa-square');
INSERT INTO `sys_icon` VALUES ('608', 'fa fa-square-o');
INSERT INTO `sys_icon` VALUES ('609', 'fa fa-cc-amex');
INSERT INTO `sys_icon` VALUES ('610', 'fa fa-cc-diners-club');
INSERT INTO `sys_icon` VALUES ('611', 'fa fa-cc-discover');
INSERT INTO `sys_icon` VALUES ('612', 'fa fa-cc-jcb');
INSERT INTO `sys_icon` VALUES ('613', 'fa fa-cc-mastercard');
INSERT INTO `sys_icon` VALUES ('614', 'fa fa-cc-paypal');
INSERT INTO `sys_icon` VALUES ('615', 'fa fa-cc-stripe');
INSERT INTO `sys_icon` VALUES ('616', 'fa fa-cc-visa');
INSERT INTO `sys_icon` VALUES ('617', 'fa fa-credit-card');
INSERT INTO `sys_icon` VALUES ('618', 'fa fa-credit-card-alt');
INSERT INTO `sys_icon` VALUES ('619', 'fa fa-google-wallet');
INSERT INTO `sys_icon` VALUES ('620', 'fa fa-paypal');
INSERT INTO `sys_icon` VALUES ('621', 'fa fa-area-chart');
INSERT INTO `sys_icon` VALUES ('622', 'fa fa-bar-chart');
INSERT INTO `sys_icon` VALUES ('623', 'fa fa-bar-chart-o');
INSERT INTO `sys_icon` VALUES ('624', 'fa fa-line-chart');
INSERT INTO `sys_icon` VALUES ('625', 'fa fa-pie-chart');
INSERT INTO `sys_icon` VALUES ('626', 'fa fa-bitcoin');
INSERT INTO `sys_icon` VALUES ('627', 'fa fa-btc');
INSERT INTO `sys_icon` VALUES ('628', 'fa fa-cny');
INSERT INTO `sys_icon` VALUES ('629', 'fa fa-dollar');
INSERT INTO `sys_icon` VALUES ('630', 'fa fa-eur');
INSERT INTO `sys_icon` VALUES ('631', 'fa fa-euro');
INSERT INTO `sys_icon` VALUES ('632', 'fa fa-gbp');
INSERT INTO `sys_icon` VALUES ('633', 'fa fa-gg');
INSERT INTO `sys_icon` VALUES ('634', 'fa fa-gg-circle');
INSERT INTO `sys_icon` VALUES ('635', 'fa fa-ils');
INSERT INTO `sys_icon` VALUES ('636', 'fa fa-inr');
INSERT INTO `sys_icon` VALUES ('637', 'fa fa-jpy');
INSERT INTO `sys_icon` VALUES ('638', 'fa fa-krw');
INSERT INTO `sys_icon` VALUES ('639', 'fa fa-money');
INSERT INTO `sys_icon` VALUES ('640', 'fa fa-rmb');
INSERT INTO `sys_icon` VALUES ('641', 'fa fa-rouble');
INSERT INTO `sys_icon` VALUES ('642', 'fa fa-rub');
INSERT INTO `sys_icon` VALUES ('643', 'fa fa-ruble');
INSERT INTO `sys_icon` VALUES ('644', 'fa fa-rupee');
INSERT INTO `sys_icon` VALUES ('645', 'fa fa-shekel');
INSERT INTO `sys_icon` VALUES ('646', 'fa fa-sheqel');
INSERT INTO `sys_icon` VALUES ('647', 'fa fa-try');
INSERT INTO `sys_icon` VALUES ('648', 'fa fa-turkish-lira');
INSERT INTO `sys_icon` VALUES ('649', 'fa fa-usd');
INSERT INTO `sys_icon` VALUES ('650', 'fa fa-won');
INSERT INTO `sys_icon` VALUES ('651', 'fa fa-yen');
INSERT INTO `sys_icon` VALUES ('652', 'fa fa-align-center');
INSERT INTO `sys_icon` VALUES ('653', 'fa fa-align-justify');
INSERT INTO `sys_icon` VALUES ('654', 'fa fa-align-left');
INSERT INTO `sys_icon` VALUES ('655', 'fa fa-align-right');
INSERT INTO `sys_icon` VALUES ('656', 'fa fa-bold');
INSERT INTO `sys_icon` VALUES ('657', 'fa fa-chain');
INSERT INTO `sys_icon` VALUES ('658', 'fa fa-chain-broken');
INSERT INTO `sys_icon` VALUES ('659', 'fa fa-clipboard');
INSERT INTO `sys_icon` VALUES ('660', 'fa fa-columns');
INSERT INTO `sys_icon` VALUES ('661', 'fa fa-copy');
INSERT INTO `sys_icon` VALUES ('662', 'fa fa-cut');
INSERT INTO `sys_icon` VALUES ('663', 'fa fa-dedent');
INSERT INTO `sys_icon` VALUES ('664', 'fa fa-eraser');
INSERT INTO `sys_icon` VALUES ('665', 'fa fa-file');
INSERT INTO `sys_icon` VALUES ('666', 'fa fa-file-o');
INSERT INTO `sys_icon` VALUES ('667', 'fa fa-file-text');
INSERT INTO `sys_icon` VALUES ('668', 'fa fa-file-text-o');
INSERT INTO `sys_icon` VALUES ('669', 'fa fa-files-o');
INSERT INTO `sys_icon` VALUES ('670', 'fa fa-floppy-o');
INSERT INTO `sys_icon` VALUES ('671', 'fa fa-font');
INSERT INTO `sys_icon` VALUES ('672', 'fa fa-header');
INSERT INTO `sys_icon` VALUES ('673', 'fa fa-indent');
INSERT INTO `sys_icon` VALUES ('674', 'fa fa-italic');
INSERT INTO `sys_icon` VALUES ('675', 'fa fa-link');
INSERT INTO `sys_icon` VALUES ('676', 'fa fa-list');
INSERT INTO `sys_icon` VALUES ('677', 'fa fa-list-alt');
INSERT INTO `sys_icon` VALUES ('678', 'fa fa-list-ol');
INSERT INTO `sys_icon` VALUES ('679', 'fa fa-list-ul');
INSERT INTO `sys_icon` VALUES ('680', 'fa fa-outdent');
INSERT INTO `sys_icon` VALUES ('681', 'fa fa-paperclip');
INSERT INTO `sys_icon` VALUES ('682', 'fa fa-paragraph');
INSERT INTO `sys_icon` VALUES ('683', 'fa fa-paste');
INSERT INTO `sys_icon` VALUES ('684', 'fa fa-repeat');
INSERT INTO `sys_icon` VALUES ('685', 'fa fa-rotate-left');
INSERT INTO `sys_icon` VALUES ('686', 'fa fa-rotate-right');
INSERT INTO `sys_icon` VALUES ('687', 'fa fa-save');
INSERT INTO `sys_icon` VALUES ('688', 'fa fa-scissors');
INSERT INTO `sys_icon` VALUES ('689', 'fa fa-strikethrough');
INSERT INTO `sys_icon` VALUES ('690', 'fa fa-subscript');
INSERT INTO `sys_icon` VALUES ('691', 'fa fa-superscript');
INSERT INTO `sys_icon` VALUES ('692', 'fa fa-table');
INSERT INTO `sys_icon` VALUES ('693', 'fa fa-text-height');
INSERT INTO `sys_icon` VALUES ('694', 'fa fa-text-width');
INSERT INTO `sys_icon` VALUES ('695', 'fa fa-th');
INSERT INTO `sys_icon` VALUES ('696', 'fa fa-th-large');
INSERT INTO `sys_icon` VALUES ('697', 'fa fa-th-list');
INSERT INTO `sys_icon` VALUES ('698', 'fa fa-underline');
INSERT INTO `sys_icon` VALUES ('699', 'fa fa-undo');
INSERT INTO `sys_icon` VALUES ('700', 'fa fa-unlink');
INSERT INTO `sys_icon` VALUES ('701', 'fa fa-angle-double-down');
INSERT INTO `sys_icon` VALUES ('702', 'fa fa-angle-double-left');
INSERT INTO `sys_icon` VALUES ('703', 'fa fa-angle-double-right');
INSERT INTO `sys_icon` VALUES ('704', 'fa fa-angle-double-up');
INSERT INTO `sys_icon` VALUES ('705', 'fa fa-angle-down');
INSERT INTO `sys_icon` VALUES ('706', 'fa fa-angle-left');
INSERT INTO `sys_icon` VALUES ('707', 'fa fa-angle-right');
INSERT INTO `sys_icon` VALUES ('708', 'fa fa-angle-up');
INSERT INTO `sys_icon` VALUES ('709', 'fa fa-arrow-circle-down');
INSERT INTO `sys_icon` VALUES ('710', 'fa fa-arrow-circle-left');
INSERT INTO `sys_icon` VALUES ('711', 'fa fa-arrow-circle-o-down');
INSERT INTO `sys_icon` VALUES ('712', 'fa fa-arrow-circle-o-left');
INSERT INTO `sys_icon` VALUES ('713', 'fa fa-arrow-circle-o-right');
INSERT INTO `sys_icon` VALUES ('714', 'fa fa-arrow-circle-o-up');
INSERT INTO `sys_icon` VALUES ('715', 'fa fa-arrow-circle-right');
INSERT INTO `sys_icon` VALUES ('716', 'fa fa-arrow-circle-up');
INSERT INTO `sys_icon` VALUES ('717', 'fa fa-arrow-down');
INSERT INTO `sys_icon` VALUES ('718', 'fa fa-arrow-left');
INSERT INTO `sys_icon` VALUES ('719', 'fa fa-arrow-right');
INSERT INTO `sys_icon` VALUES ('720', 'fa fa-arrow-up');
INSERT INTO `sys_icon` VALUES ('721', 'fa fa-arrows');
INSERT INTO `sys_icon` VALUES ('722', 'fa fa-arrows-alt');
INSERT INTO `sys_icon` VALUES ('723', 'fa fa-arrows-h');
INSERT INTO `sys_icon` VALUES ('724', 'fa fa-arrows-v');
INSERT INTO `sys_icon` VALUES ('725', 'fa fa-caret-down');
INSERT INTO `sys_icon` VALUES ('726', 'fa fa-caret-left');
INSERT INTO `sys_icon` VALUES ('727', 'fa fa-caret-right');
INSERT INTO `sys_icon` VALUES ('728', 'fa fa-caret-square-o-down');
INSERT INTO `sys_icon` VALUES ('729', 'fa fa-caret-square-o-left');
INSERT INTO `sys_icon` VALUES ('730', 'fa fa-caret-square-o-right');
INSERT INTO `sys_icon` VALUES ('731', 'fa fa-caret-square-o-up');
INSERT INTO `sys_icon` VALUES ('732', 'fa fa-caret-up');
INSERT INTO `sys_icon` VALUES ('733', 'fa fa-chevron-circle-down');
INSERT INTO `sys_icon` VALUES ('734', 'fa fa-chevron-circle-left');
INSERT INTO `sys_icon` VALUES ('735', 'fa fa-chevron-circle-right');
INSERT INTO `sys_icon` VALUES ('736', 'fa fa-chevron-circle-up');
INSERT INTO `sys_icon` VALUES ('737', 'fa fa-chevron-down');
INSERT INTO `sys_icon` VALUES ('738', 'fa fa-chevron-left');
INSERT INTO `sys_icon` VALUES ('739', 'fa fa-chevron-right');
INSERT INTO `sys_icon` VALUES ('740', 'fa fa-chevron-up');
INSERT INTO `sys_icon` VALUES ('741', 'fa fa-exchange');
INSERT INTO `sys_icon` VALUES ('742', 'fa fa-hand-o-down');
INSERT INTO `sys_icon` VALUES ('743', 'fa fa-hand-o-left');
INSERT INTO `sys_icon` VALUES ('744', 'fa fa-hand-o-right');
INSERT INTO `sys_icon` VALUES ('745', 'fa fa-hand-o-up');
INSERT INTO `sys_icon` VALUES ('746', 'fa fa-long-arrow-down');
INSERT INTO `sys_icon` VALUES ('747', 'fa fa-long-arrow-left');
INSERT INTO `sys_icon` VALUES ('748', 'fa fa-long-arrow-right');
INSERT INTO `sys_icon` VALUES ('749', 'fa fa-long-arrow-up');
INSERT INTO `sys_icon` VALUES ('750', 'fa fa-toggle-down');
INSERT INTO `sys_icon` VALUES ('751', 'fa fa-toggle-left');
INSERT INTO `sys_icon` VALUES ('752', 'fa fa-toggle-right');
INSERT INTO `sys_icon` VALUES ('753', 'fa fa-toggle-up');
INSERT INTO `sys_icon` VALUES ('754', 'fa fa-arrows-alt');
INSERT INTO `sys_icon` VALUES ('755', 'fa fa-backward');
INSERT INTO `sys_icon` VALUES ('756', 'fa fa-compress');
INSERT INTO `sys_icon` VALUES ('757', 'fa fa-eject');
INSERT INTO `sys_icon` VALUES ('758', 'fa fa-expand');
INSERT INTO `sys_icon` VALUES ('759', 'fa fa-fast-backward');
INSERT INTO `sys_icon` VALUES ('760', 'fa fa-fast-forward');
INSERT INTO `sys_icon` VALUES ('761', 'fa fa-forward');
INSERT INTO `sys_icon` VALUES ('762', 'fa fa-pause');
INSERT INTO `sys_icon` VALUES ('763', 'fa fa-pause-circle');
INSERT INTO `sys_icon` VALUES ('764', 'fa fa-pause-circle-o');
INSERT INTO `sys_icon` VALUES ('765', 'fa fa-play');
INSERT INTO `sys_icon` VALUES ('766', 'fa fa-play-circle');
INSERT INTO `sys_icon` VALUES ('767', 'fa fa-play-circle-o');
INSERT INTO `sys_icon` VALUES ('768', 'fa fa-random');
INSERT INTO `sys_icon` VALUES ('769', 'fa fa-step-backward');
INSERT INTO `sys_icon` VALUES ('770', 'fa fa-step-forward');
INSERT INTO `sys_icon` VALUES ('771', 'fa fa-stop');
INSERT INTO `sys_icon` VALUES ('772', 'fa fa-stop-circle');
INSERT INTO `sys_icon` VALUES ('773', 'fa fa-stop-circle-o');
INSERT INTO `sys_icon` VALUES ('774', 'fa fa-youtube-play');
INSERT INTO `sys_icon` VALUES ('775', 'fa fa-500px');
INSERT INTO `sys_icon` VALUES ('776', 'fa fa-adn');
INSERT INTO `sys_icon` VALUES ('777', 'fa fa-amazon');
INSERT INTO `sys_icon` VALUES ('778', 'fa fa-android');
INSERT INTO `sys_icon` VALUES ('779', 'fa fa-angellist');
INSERT INTO `sys_icon` VALUES ('780', 'fa fa-apple');
INSERT INTO `sys_icon` VALUES ('781', 'fa fa-bandcamp');
INSERT INTO `sys_icon` VALUES ('782', 'fa fa-behance');
INSERT INTO `sys_icon` VALUES ('783', 'fa fa-behance-square');
INSERT INTO `sys_icon` VALUES ('784', 'fa fa-bitbucket');
INSERT INTO `sys_icon` VALUES ('785', 'fa fa-bitbucket-square');
INSERT INTO `sys_icon` VALUES ('786', 'fa fa-bitcoin');
INSERT INTO `sys_icon` VALUES ('787', 'fa fa-black-tie');
INSERT INTO `sys_icon` VALUES ('788', 'fa fa-bluetooth');
INSERT INTO `sys_icon` VALUES ('789', 'fa fa-bluetooth-b');
INSERT INTO `sys_icon` VALUES ('790', 'fa fa-btc');
INSERT INTO `sys_icon` VALUES ('791', 'fa fa-buysellads');
INSERT INTO `sys_icon` VALUES ('792', 'fa fa-cc-amex');
INSERT INTO `sys_icon` VALUES ('793', 'fa fa-cc-diners-club');
INSERT INTO `sys_icon` VALUES ('794', 'fa fa-cc-discover');
INSERT INTO `sys_icon` VALUES ('795', 'fa fa-cc-jcb');
INSERT INTO `sys_icon` VALUES ('796', 'fa fa-cc-mastercard');
INSERT INTO `sys_icon` VALUES ('797', 'fa fa-cc-paypal');
INSERT INTO `sys_icon` VALUES ('798', 'fa fa-cc-stripe');
INSERT INTO `sys_icon` VALUES ('799', 'fa fa-cc-visa');
INSERT INTO `sys_icon` VALUES ('800', 'fa fa-chrome');
INSERT INTO `sys_icon` VALUES ('801', 'fa fa-codepen');
INSERT INTO `sys_icon` VALUES ('802', 'fa fa-codiepie');
INSERT INTO `sys_icon` VALUES ('803', 'fa fa-connectdevelop');
INSERT INTO `sys_icon` VALUES ('804', 'fa fa-contao');
INSERT INTO `sys_icon` VALUES ('805', 'fa fa-css3');
INSERT INTO `sys_icon` VALUES ('806', 'fa fa-dashcube');
INSERT INTO `sys_icon` VALUES ('807', 'fa fa-delicious');
INSERT INTO `sys_icon` VALUES ('808', 'fa fa-deviantart');
INSERT INTO `sys_icon` VALUES ('809', 'fa fa-digg');
INSERT INTO `sys_icon` VALUES ('810', 'fa fa-dribbble');
INSERT INTO `sys_icon` VALUES ('811', 'fa fa-dropbox');
INSERT INTO `sys_icon` VALUES ('812', 'fa fa-drupal');
INSERT INTO `sys_icon` VALUES ('813', 'fa fa-edge');
INSERT INTO `sys_icon` VALUES ('814', 'fa fa-eercast');
INSERT INTO `sys_icon` VALUES ('815', 'fa fa-empire');
INSERT INTO `sys_icon` VALUES ('816', 'fa fa-envira');
INSERT INTO `sys_icon` VALUES ('817', 'fa fa-etsy');
INSERT INTO `sys_icon` VALUES ('818', 'fa fa-expeditedssl');
INSERT INTO `sys_icon` VALUES ('819', 'fa fa-fa');
INSERT INTO `sys_icon` VALUES ('820', 'fa fa-facebook');
INSERT INTO `sys_icon` VALUES ('821', 'fa fa-facebook-f');
INSERT INTO `sys_icon` VALUES ('822', 'fa fa-facebook-official');
INSERT INTO `sys_icon` VALUES ('823', 'fa fa-facebook-square');
INSERT INTO `sys_icon` VALUES ('824', 'fa fa-firefox');
INSERT INTO `sys_icon` VALUES ('825', 'fa fa-first-order');
INSERT INTO `sys_icon` VALUES ('826', 'fa fa-flickr');
INSERT INTO `sys_icon` VALUES ('827', 'fa fa-font-awesome');
INSERT INTO `sys_icon` VALUES ('828', 'fa fa-fonticons');
INSERT INTO `sys_icon` VALUES ('829', 'fa fa-fort-awesome');
INSERT INTO `sys_icon` VALUES ('830', 'fa fa-forumbee');
INSERT INTO `sys_icon` VALUES ('831', 'fa fa-foursquare');
INSERT INTO `sys_icon` VALUES ('832', 'fa fa-free-code-camp');
INSERT INTO `sys_icon` VALUES ('833', 'fa fa-ge');
INSERT INTO `sys_icon` VALUES ('834', 'fa fa-get-pocket');
INSERT INTO `sys_icon` VALUES ('835', 'fa fa-gg');
INSERT INTO `sys_icon` VALUES ('836', 'fa fa-gg-circle');
INSERT INTO `sys_icon` VALUES ('837', 'fa fa-git');
INSERT INTO `sys_icon` VALUES ('838', 'fa fa-git-square');
INSERT INTO `sys_icon` VALUES ('839', 'fa fa-github');
INSERT INTO `sys_icon` VALUES ('840', 'fa fa-github-alt');
INSERT INTO `sys_icon` VALUES ('841', 'fa fa-github-square');
INSERT INTO `sys_icon` VALUES ('842', 'fa fa-gitlab');
INSERT INTO `sys_icon` VALUES ('843', 'fa fa-gittip');
INSERT INTO `sys_icon` VALUES ('844', 'fa fa-glide');
INSERT INTO `sys_icon` VALUES ('845', 'fa fa-glide-g');
INSERT INTO `sys_icon` VALUES ('846', 'fa fa-google');
INSERT INTO `sys_icon` VALUES ('847', 'fa fa-google-plus');
INSERT INTO `sys_icon` VALUES ('848', 'fa fa-google-plus-circle');
INSERT INTO `sys_icon` VALUES ('849', 'fa fa-google-plus-official');
INSERT INTO `sys_icon` VALUES ('850', 'fa fa-google-plus-square');
INSERT INTO `sys_icon` VALUES ('851', 'fa fa-google-wallet');
INSERT INTO `sys_icon` VALUES ('852', 'fa fa-gratipay');
INSERT INTO `sys_icon` VALUES ('853', 'fa fa-grav');
INSERT INTO `sys_icon` VALUES ('854', 'fa fa-hacker-news');
INSERT INTO `sys_icon` VALUES ('855', 'fa fa-houzz');
INSERT INTO `sys_icon` VALUES ('856', 'fa fa-html5');
INSERT INTO `sys_icon` VALUES ('857', 'fa fa-imdb');
INSERT INTO `sys_icon` VALUES ('858', 'fa fa-instagram');
INSERT INTO `sys_icon` VALUES ('859', 'fa fa-internet-explorer');
INSERT INTO `sys_icon` VALUES ('860', 'fa fa-ioxhost');
INSERT INTO `sys_icon` VALUES ('861', 'fa fa-joomla');
INSERT INTO `sys_icon` VALUES ('862', 'fa fa-jsfiddle');
INSERT INTO `sys_icon` VALUES ('863', 'fa fa-lastfm');
INSERT INTO `sys_icon` VALUES ('864', 'fa fa-lastfm-square');
INSERT INTO `sys_icon` VALUES ('865', 'fa fa-leanpub');
INSERT INTO `sys_icon` VALUES ('866', 'fa fa-linkedin');
INSERT INTO `sys_icon` VALUES ('867', 'fa fa-linkedin-square');
INSERT INTO `sys_icon` VALUES ('868', 'fa fa-linode');
INSERT INTO `sys_icon` VALUES ('869', 'fa fa-linux');
INSERT INTO `sys_icon` VALUES ('870', 'fa fa-maxcdn');
INSERT INTO `sys_icon` VALUES ('871', 'fa fa-meanpath');
INSERT INTO `sys_icon` VALUES ('872', 'fa fa-medium');
INSERT INTO `sys_icon` VALUES ('873', 'fa fa-meetup');
INSERT INTO `sys_icon` VALUES ('874', 'fa fa-mixcloud');
INSERT INTO `sys_icon` VALUES ('875', 'fa fa-modx');
INSERT INTO `sys_icon` VALUES ('876', 'fa fa-odnoklassniki');
INSERT INTO `sys_icon` VALUES ('877', 'fa fa-odnoklassniki-square');
INSERT INTO `sys_icon` VALUES ('878', 'fa fa-opencart');
INSERT INTO `sys_icon` VALUES ('879', 'fa fa-openid');
INSERT INTO `sys_icon` VALUES ('880', 'fa fa-opera');
INSERT INTO `sys_icon` VALUES ('881', 'fa fa-optin-monster');
INSERT INTO `sys_icon` VALUES ('882', 'fa fa-pagelines');
INSERT INTO `sys_icon` VALUES ('883', 'fa fa-paypal');
INSERT INTO `sys_icon` VALUES ('884', 'fa fa-pied-piper');
INSERT INTO `sys_icon` VALUES ('885', 'fa fa-pied-piper-alt');
INSERT INTO `sys_icon` VALUES ('886', 'fa fa-pied-piper-pp');
INSERT INTO `sys_icon` VALUES ('887', 'fa fa-pinterest');
INSERT INTO `sys_icon` VALUES ('888', 'fa fa-pinterest-p');
INSERT INTO `sys_icon` VALUES ('889', 'fa fa-pinterest-square');
INSERT INTO `sys_icon` VALUES ('890', 'fa fa-product-hunt');
INSERT INTO `sys_icon` VALUES ('891', 'fa fa-qq');
INSERT INTO `sys_icon` VALUES ('892', 'fa fa-quora');
INSERT INTO `sys_icon` VALUES ('893', 'fa fa-ra');
INSERT INTO `sys_icon` VALUES ('894', 'fa fa-ravelry');
INSERT INTO `sys_icon` VALUES ('895', 'fa fa-rebel');
INSERT INTO `sys_icon` VALUES ('896', 'fa fa-reddit');
INSERT INTO `sys_icon` VALUES ('897', 'fa fa-reddit-alien');
INSERT INTO `sys_icon` VALUES ('898', 'fa fa-reddit-square');
INSERT INTO `sys_icon` VALUES ('899', 'fa fa-renren');
INSERT INTO `sys_icon` VALUES ('900', 'fa fa-resistance');
INSERT INTO `sys_icon` VALUES ('901', 'fa fa-safari');
INSERT INTO `sys_icon` VALUES ('902', 'fa fa-scribd');
INSERT INTO `sys_icon` VALUES ('903', 'fa fa-sellsy');
INSERT INTO `sys_icon` VALUES ('904', 'fa fa-share-alt');
INSERT INTO `sys_icon` VALUES ('905', 'fa fa-share-alt-square');
INSERT INTO `sys_icon` VALUES ('906', 'fa fa-shirtsinbulk');
INSERT INTO `sys_icon` VALUES ('907', 'fa fa-simplybuilt');
INSERT INTO `sys_icon` VALUES ('908', 'fa fa-skyatlas');
INSERT INTO `sys_icon` VALUES ('909', 'fa fa-skype');
INSERT INTO `sys_icon` VALUES ('910', 'fa fa-slack');
INSERT INTO `sys_icon` VALUES ('911', 'fa fa-slideshare');
INSERT INTO `sys_icon` VALUES ('912', 'fa fa-snapchat');
INSERT INTO `sys_icon` VALUES ('913', 'fa fa-snapchat-ghost');
INSERT INTO `sys_icon` VALUES ('914', 'fa fa-snapchat-square');
INSERT INTO `sys_icon` VALUES ('915', 'fa fa-soundcloud');
INSERT INTO `sys_icon` VALUES ('916', 'fa fa-spotify');
INSERT INTO `sys_icon` VALUES ('917', 'fa fa-stack-exchange');
INSERT INTO `sys_icon` VALUES ('918', 'fa fa-stack-overflow');
INSERT INTO `sys_icon` VALUES ('919', 'fa fa-steam');
INSERT INTO `sys_icon` VALUES ('920', 'fa fa-steam-square');
INSERT INTO `sys_icon` VALUES ('921', 'fa fa-stumbleupon');
INSERT INTO `sys_icon` VALUES ('922', 'fa fa-stumbleupon-circle');
INSERT INTO `sys_icon` VALUES ('923', 'fa fa-superpowers');
INSERT INTO `sys_icon` VALUES ('924', 'fa fa-telegram');
INSERT INTO `sys_icon` VALUES ('925', 'fa fa-tencent-weibo');
INSERT INTO `sys_icon` VALUES ('926', 'fa fa-themeisle');
INSERT INTO `sys_icon` VALUES ('927', 'fa fa-trello');
INSERT INTO `sys_icon` VALUES ('928', 'fa fa-tripadvisor');
INSERT INTO `sys_icon` VALUES ('929', 'fa fa-tumblr');
INSERT INTO `sys_icon` VALUES ('930', 'fa fa-tumblr-square');
INSERT INTO `sys_icon` VALUES ('931', 'fa fa-twitch');
INSERT INTO `sys_icon` VALUES ('932', 'fa fa-twitter');
INSERT INTO `sys_icon` VALUES ('933', 'fa fa-twitter-square');
INSERT INTO `sys_icon` VALUES ('934', 'fa fa-usb');
INSERT INTO `sys_icon` VALUES ('935', 'fa fa-viacoin');
INSERT INTO `sys_icon` VALUES ('936', 'fa fa-viadeo');
INSERT INTO `sys_icon` VALUES ('937', 'fa fa-viadeo-square');
INSERT INTO `sys_icon` VALUES ('938', 'fa fa-vimeo');
INSERT INTO `sys_icon` VALUES ('939', 'fa fa-vimeo-square');
INSERT INTO `sys_icon` VALUES ('940', 'fa fa-vine');
INSERT INTO `sys_icon` VALUES ('941', 'fa fa-vk');
INSERT INTO `sys_icon` VALUES ('942', 'fa fa-wechat');
INSERT INTO `sys_icon` VALUES ('943', 'fa fa-weibo');
INSERT INTO `sys_icon` VALUES ('944', 'fa fa-weixin');
INSERT INTO `sys_icon` VALUES ('945', 'fa fa-whatsapp');
INSERT INTO `sys_icon` VALUES ('946', 'fa fa-wikipedia-w');
INSERT INTO `sys_icon` VALUES ('947', 'fa fa-windows');
INSERT INTO `sys_icon` VALUES ('948', 'fa fa-wordpress');
INSERT INTO `sys_icon` VALUES ('949', 'fa fa-wpbeginner');
INSERT INTO `sys_icon` VALUES ('950', 'fa fa-wpexplorer');
INSERT INTO `sys_icon` VALUES ('951', 'fa fa-wpforms');
INSERT INTO `sys_icon` VALUES ('952', 'fa fa-xing');
INSERT INTO `sys_icon` VALUES ('953', 'fa fa-xing-square');
INSERT INTO `sys_icon` VALUES ('954', 'fa fa-y-combinator');
INSERT INTO `sys_icon` VALUES ('955', 'fa fa-y-combinator-square');
INSERT INTO `sys_icon` VALUES ('956', 'fa fa-yahoo');
INSERT INTO `sys_icon` VALUES ('957', 'fa fa-yc');
INSERT INTO `sys_icon` VALUES ('958', 'fa fa-yc-square');
INSERT INTO `sys_icon` VALUES ('959', 'fa fa-yelp');
INSERT INTO `sys_icon` VALUES ('960', 'fa fa-yoast');
INSERT INTO `sys_icon` VALUES ('961', 'fa fa-youtube');
INSERT INTO `sys_icon` VALUES ('962', 'fa fa-youtube-play');
INSERT INTO `sys_icon` VALUES ('963', 'fa fa-youtube-square');
INSERT INTO `sys_icon` VALUES ('964', 'fa fa-ambulance');
INSERT INTO `sys_icon` VALUES ('965', 'fa fa-h-square');
INSERT INTO `sys_icon` VALUES ('966', 'fa fa-heart');
INSERT INTO `sys_icon` VALUES ('967', 'fa fa-heart-o');
INSERT INTO `sys_icon` VALUES ('968', 'fa fa-heartbeat');
INSERT INTO `sys_icon` VALUES ('969', 'fa fa-hospital-o');
INSERT INTO `sys_icon` VALUES ('970', 'fa fa-medkit');
INSERT INTO `sys_icon` VALUES ('971', 'fa fa-plus-square');
INSERT INTO `sys_icon` VALUES ('972', 'fa fa-stethoscope');
INSERT INTO `sys_icon` VALUES ('973', 'fa fa-user-md');
INSERT INTO `sys_icon` VALUES ('974', 'fa fa-wheelchair');
INSERT INTO `sys_icon` VALUES ('975', 'fa fa-wheelchair-alt');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` varchar(36) NOT NULL COMMENT '菜单ID',
  `menu_name` varchar(30) NOT NULL COMMENT '菜单名称',
  `p_menu_id` varchar(36) NOT NULL DEFAULT '-1' COMMENT '父级菜单ID：顶级菜单的父级菜单id为-1',
  `menu_url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `menu_pic_css` varchar(50) DEFAULT NULL COMMENT '菜单图片样式',
  `order_no` int(11) DEFAULT NULL COMMENT '排序号',
  `creator_id` varchar(36) NOT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('10', '菜单列表', '-1', '', null, '1', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-28 17:31:21');
INSERT INTO `sys_menu` VALUES ('10.01', '系统设置', '10', '', 'fa fa-cog green', '1', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-29 15:21:50');
INSERT INTO `sys_menu` VALUES ('10.01.01', '功能配置', '10.01', '/organ/menuSetting', 'fa fa-group', '3', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-29 15:27:05');
INSERT INTO `sys_menu` VALUES ('10.01.02', '岗位授权', '10.01', '/organ/positionAuthList', 'fa fa-pencil-square', '2', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-29 15:28:05');
INSERT INTO `sys_menu` VALUES ('10.01.03', '用户管理', '10.01', '/user/toUserListPage', 'fa fa-male', '1', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-29 15:29:04');
INSERT INTO `sys_menu` VALUES ('10.01.04', '组织架构', '10.01', '/organ/organizeList', 'fa fa-address-book', '4', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-29 15:26:01');
INSERT INTO `sys_menu` VALUES ('10.01.05', '组织架构图', '10.01', '/organ/organStructure', 'fa fa-picture-o', '5', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-29 15:28:14');
INSERT INTO `sys_menu` VALUES ('10.02', '双向评价', '10', '', 'fa fa-newspaper-o red', '2', '9faac380235548998d4bc2b8b4a15193', '2018-08-03 10:59:10');
INSERT INTO `sys_menu` VALUES ('10.02.01', '参评机构', '10.02', '/institution/toInstitutionListPage', 'fa fa-fort-awesome', '1', '9faac380235548998d4bc2b8b4a15193', '2018-08-03 11:03:00');
INSERT INTO `sys_menu` VALUES ('10.04', '新闻管理', '10', '', 'fa fa-leaf purple', '4', '1a5d0215620b42358f042fdd96a8ac6c', '2018-07-20 16:22:56');
INSERT INTO `sys_menu` VALUES ('10.04.01', '新闻发布', '10.04', '/news/queryListPage', 'fa fa-thermometer-empty', '1', '1a5d0215620b42358f042fdd96a8ac6c', '2018-06-08 09:48:23');
INSERT INTO `sys_menu` VALUES ('10.04.02', '新闻栏目', '10.04', '/newsMenu/queryListPage', 'fa fa-bar-chart', '2', '1a5d0215620b42358f042fdd96a8ac6c', '2018-06-08 09:48:23');

-- ----------------------------
-- Table structure for sys_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_position`;
CREATE TABLE `sys_position` (
  `position_id` varchar(36) NOT NULL COMMENT '岗位ID',
  `position_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `dept_id` varchar(36) NOT NULL COMMENT '所属部门ID',
  `leader_id` varchar(36) DEFAULT NULL COMMENT '岗位领导ID',
  `leader_type` char(1) DEFAULT NULL COMMENT '岗位领导类型（P 岗位 U 用户）',
  `keyword` varchar(50) DEFAULT NULL COMMENT '关键字',
  `order_no` int(11) DEFAULT NULL COMMENT '排序号',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator_id` varchar(36) NOT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `alter_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`position_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统岗位表';

-- ----------------------------
-- Records of sys_position
-- ----------------------------
INSERT INTO `sys_position` VALUES ('733b1c68784a4e0c9d59cafa21b7c954', '管理员', 'd1d0c5d5a9034a03b323c4d030751675', null, null, null, null, '', '1f6552284a994aa6b0c36e4cd19199d4', '2018-06-13 17:06:38', '2018-06-13 17:06:38');
INSERT INTO `sys_position` VALUES ('7b6ac8ae7cd94a3e99b754cbfd32cdf0', 'java开发', 'd39532a171344f10aadb7fa94cde190c', null, null, null, null, '', 'd206c3eded384b46b519edcdd249a4a9', '2018-05-29 09:37:48', '2018-05-29 09:37:48');
INSERT INTO `sys_position` VALUES ('aa3c6a22adc84f549da15c34d946fa55', '项目经理', 'd39532a171344f10aadb7fa94cde190c', null, null, null, null, '', '1a5d0215620b42358f042fdd96a8ac6c', '2018-06-08 14:19:18', '2018-06-08 14:19:18');

-- ----------------------------
-- Table structure for sys_position_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_position_func`;
CREATE TABLE `sys_position_func` (
  `position_id` varchar(36) NOT NULL COMMENT '岗位ID',
  `func_id` varchar(36) NOT NULL COMMENT '功能ID',
  PRIMARY KEY (`position_id`,`func_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='岗位对应的功能';

-- ----------------------------
-- Records of sys_position_func
-- ----------------------------
INSERT INTO `sys_position_func` VALUES ('733b1c68784a4e0c9d59cafa21b7c954', '10.01.01.01');
INSERT INTO `sys_position_func` VALUES ('733b1c68784a4e0c9d59cafa21b7c954', '10.01.02.01');
INSERT INTO `sys_position_func` VALUES ('733b1c68784a4e0c9d59cafa21b7c954', '10.01.03.01');
INSERT INTO `sys_position_func` VALUES ('733b1c68784a4e0c9d59cafa21b7c954', '10.01.04.01');
INSERT INTO `sys_position_func` VALUES ('733b1c68784a4e0c9d59cafa21b7c954', '10.01.05.01');
INSERT INTO `sys_position_func` VALUES ('733b1c68784a4e0c9d59cafa21b7c954', '10.02.01.01');
INSERT INTO `sys_position_func` VALUES ('733b1c68784a4e0c9d59cafa21b7c954', '10.04.01.01');
INSERT INTO `sys_position_func` VALUES ('733b1c68784a4e0c9d59cafa21b7c954', '10.04.02.01');
INSERT INTO `sys_position_func` VALUES ('7b6ac8ae7cd94a3e99b754cbfd32cdf0', '10.01.06.01');
INSERT INTO `sys_position_func` VALUES ('7b6ac8ae7cd94a3e99b754cbfd32cdf0', '10.02.01.01');
INSERT INTO `sys_position_func` VALUES ('7b6ac8ae7cd94a3e99b754cbfd32cdf0', '10.03.01.01');
INSERT INTO `sys_position_func` VALUES ('7b6ac8ae7cd94a3e99b754cbfd32cdf0', '10.03.02.01');
INSERT INTO `sys_position_func` VALUES ('aa3c6a22adc84f549da15c34d946fa55', '10.01.06.01');
INSERT INTO `sys_position_func` VALUES ('aa3c6a22adc84f549da15c34d946fa55', '10.03.01.01');
INSERT INTO `sys_position_func` VALUES ('aa3c6a22adc84f549da15c34d946fa55', '10.03.02.01');
INSERT INTO `sys_position_func` VALUES ('aa3c6a22adc84f549da15c34d946fa55', '10.04.01.01');
INSERT INTO `sys_position_func` VALUES ('aa3c6a22adc84f549da15c34d946fa55', '10.04.02.01');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(36) NOT NULL COMMENT '用户ID',
  `user_name` varchar(50) NOT NULL COMMENT '用户名称',
  `user_no` varchar(30) DEFAULT NULL COMMENT '编号',
  `login_name` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(254) DEFAULT NULL COMMENT '登录密码',
  `email` varchar(200) DEFAULT NULL COMMENT '邮件地址',
  `work_phone` varchar(30) DEFAULT NULL COMMENT '办公电话',
  `mobile_no` varchar(30) DEFAULT NULL COMMENT '手机号码',
  `home_phone` varchar(30) DEFAULT NULL COMMENT '家庭电话',
  `keyword` varchar(50) DEFAULT NULL COMMENT '关键字',
  `order_no` int(11) DEFAULT NULL COMMENT '排序号',
  `memo` varchar(400) DEFAULT NULL COMMENT '备注说明',
  `user_type` char(1) NOT NULL DEFAULT '0' COMMENT '用户类型：0 普通用户 1 管理员',
  `account_status` char(1) NOT NULL DEFAULT '1' COMMENT '账号状态(0：禁用 ，1：正常)',
  `user_status` char(1) NOT NULL DEFAULT '1' COMMENT '用户状态(1：正常 2，已离职 ，：已删除)',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `creator_id` varchar(36) NOT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `alter_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `udx_login_name` (`login_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('0588435e8ece462095258475ecb8d71c', '方俊', '0007', 'fangjun', 'e10adc3949ba59abbe56e057f20f883e', '', '', '15178956341', '', null, null, '', '0', '1', '1', null, '1a5d0215620b42358f042fdd96a8ac6c', '2018-06-08 14:16:24', '2018-06-08 14:16:24');
INSERT INTO `sys_user` VALUES ('0c1bfc57229d48d8b573bc7f39209c4b', '121', '212', '1212', 'd9eff2de5a0e0e46efad7ba4ef2e8706', '2121212@163.com', '13311112222', '13311112233', '', null, null, '', '0', '1', '1', null, '9faac380235548998d4bc2b8b4a15193', '2018-08-02 18:35:18', '2018-08-02 18:35:18');
INSERT INTO `sys_user` VALUES ('1a5d0215620b42358f042fdd96a8ac6c', '陈智颖', '40001', 'c409998649', 'e10adc3949ba59abbe56e057f20f883e', '', '', '18716641778', '', null, null, '', '0', '1', '1', '2018-05-29 14:59:40', '9faac380235548998d4bc2b8b4a15193', '2018-05-29 12:55:09', '2018-05-29 12:55:09');
INSERT INTO `sys_user` VALUES ('1f6552284a994aa6b0c36e4cd19199d4', '李桐', '01', 'litong', 'e10adc3949ba59abbe56e057f20f883e', '', '', '18523152974', '', null, null, '', '0', '1', '1', null, '9faac380235548998d4bc2b8b4a15193', '2018-06-08 14:22:45', '2018-06-08 14:22:45');
INSERT INTO `sys_user` VALUES ('3935e5934d1e4e44a99e5f3b6bb81a80', '郑吉斌', '00006', 'zhengjibin', 'e10adc3949ba59abbe56e057f20f883e', '', '', '18996165453', '', null, null, '', '0', '1', '1', null, '9faac380235548998d4bc2b8b4a15193', '2018-06-08 14:15:35', '2018-06-08 14:15:35');
INSERT INTO `sys_user` VALUES ('4776c3f40efc40bd9412a3ba43018f54', '韩凛1', '400002', 'hanlin', 'e10adc3949ba59abbe56e057f20f883e', '', '', '17623077822', '', null, null, '', '0', '1', '1', null, '9faac380235548998d4bc2b8b4a15193', '2018-06-02 12:05:00', '2018-06-02 12:05:00');
INSERT INTO `sys_user` VALUES ('5abe64c7208844d6b27aa66c474897d1', '李川', '00006', 'lichuan', 'e10adc3949ba59abbe56e057f20f883e', '', '', '18508176635', '', null, null, '', '0', '1', '1', null, '9faac380235548998d4bc2b8b4a15193', '2018-06-08 14:14:08', '2018-06-08 14:14:08');
INSERT INTO `sys_user` VALUES ('6c43e7eabd074859a390723f64b81a45', '龙新', '0005', 'longxin', 'e10adc3949ba59abbe56e057f20f883e', '', '', '13983932527', '', null, null, '', '0', '1', '1', null, '1a5d0215620b42358f042fdd96a8ac6c', '2018-06-08 14:10:25', '2018-06-08 14:10:25');
INSERT INTO `sys_user` VALUES ('9faac380235548998d4bc2b8b4a15193', '管理员', '1', 'sys_admin', 'e10adc3949ba59abbe56e057f20f883e', '', '', '18716641778', '', null, null, '', '1', '1', '1', null, '1f6552284a994aa6b0c36e4cd19199d4', '2018-06-13 17:01:47', '2018-06-13 17:01:47');
INSERT INTO `sys_user` VALUES ('a63eeb295b8d4e03b11e91e7edca6fa0', '刘勇', '02', 'liuyong', 'e10adc3949ba59abbe56e057f20f883e', '', '', '13996074231', '', null, null, '', '0', '1', '1', null, '1a5d0215620b42358f042fdd96a8ac6c', '2018-06-08 14:24:14', '2018-06-08 14:24:14');
INSERT INTO `sys_user` VALUES ('b007a38c40b7447da643148045f4ad2f', '蒋小松', '0005', 'jiangxiaosong', 'e10adc3949ba59abbe56e057f20f883e', '', '', '15823421438', '', null, null, '', '0', '1', '1', null, '1a5d0215620b42358f042fdd96a8ac6c', '2018-06-08 14:13:18', '2018-06-08 14:13:18');
INSERT INTO `sys_user` VALUES ('d99088a85ef7449bb5a802f2406c81b0', '何佳沿', '0005', 'hejiayan', 'e10adc3949ba59abbe56e057f20f883e', '', '', '18883869573', '', null, null, '', '0', '1', '1', null, '1a5d0215620b42358f042fdd96a8ac6c', '2018-06-08 14:12:14', '2018-06-08 14:12:14');
INSERT INTO `sys_user` VALUES ('ee8be31e87e8408c9045ead0b1444c48', '邹懿', '03', 'zouyi', 'e10adc3949ba59abbe56e057f20f883e', '', '', '18182366333', '', null, null, '', '0', '1', '1', null, '1a5d0215620b42358f042fdd96a8ac6c', '2018-06-08 14:42:02', '2018-06-08 14:42:02');

-- ----------------------------
-- Table structure for sys_user_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_position`;
CREATE TABLE `sys_user_position` (
  `user_id` varchar(36) NOT NULL COMMENT '用户ID',
  `position_id` varchar(36) NOT NULL COMMENT '岗位ID',
  `is_primary` char(1) NOT NULL DEFAULT '0' COMMENT '是否主要岗位 0 否 1 是（同一员工只有一个主岗位）',
  PRIMARY KEY (`user_id`,`position_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='员工岗位表';

-- ----------------------------
-- Records of sys_user_position
-- ----------------------------
INSERT INTO `sys_user_position` VALUES ('0588435e8ece462095258475ecb8d71c', '7b6ac8ae7cd94a3e99b754cbfd32cdf0', '1');
INSERT INTO `sys_user_position` VALUES ('0c1bfc57229d48d8b573bc7f39209c4b', '7b6ac8ae7cd94a3e99b754cbfd32cdf0', '1');
INSERT INTO `sys_user_position` VALUES ('1a5d0215620b42358f042fdd96a8ac6c', '7b6ac8ae7cd94a3e99b754cbfd32cdf0', '1');
INSERT INTO `sys_user_position` VALUES ('1f6552284a994aa6b0c36e4cd19199d4', 'aa3c6a22adc84f549da15c34d946fa55', '1');
INSERT INTO `sys_user_position` VALUES ('3935e5934d1e4e44a99e5f3b6bb81a80', '7b6ac8ae7cd94a3e99b754cbfd32cdf0', '1');
INSERT INTO `sys_user_position` VALUES ('4776c3f40efc40bd9412a3ba43018f54', '7b6ac8ae7cd94a3e99b754cbfd32cdf0', '1');
INSERT INTO `sys_user_position` VALUES ('5abe64c7208844d6b27aa66c474897d1', '7b6ac8ae7cd94a3e99b754cbfd32cdf0', '1');
INSERT INTO `sys_user_position` VALUES ('6c43e7eabd074859a390723f64b81a45', '7b6ac8ae7cd94a3e99b754cbfd32cdf0', '1');
INSERT INTO `sys_user_position` VALUES ('9faac380235548998d4bc2b8b4a15193', '733b1c68784a4e0c9d59cafa21b7c954', '0');
INSERT INTO `sys_user_position` VALUES ('9faac380235548998d4bc2b8b4a15193', '7b6ac8ae7cd94a3e99b754cbfd32cdf0', '1');
INSERT INTO `sys_user_position` VALUES ('a63eeb295b8d4e03b11e91e7edca6fa0', 'aa3c6a22adc84f549da15c34d946fa55', '1');
INSERT INTO `sys_user_position` VALUES ('b007a38c40b7447da643148045f4ad2f', '7b6ac8ae7cd94a3e99b754cbfd32cdf0', '1');
INSERT INTO `sys_user_position` VALUES ('d206c3eded384b46b519edcdd249a4a9', 'b71e1839ccf0460f98b3763fb107f2ed', '0');
INSERT INTO `sys_user_position` VALUES ('d99088a85ef7449bb5a802f2406c81b0', '7b6ac8ae7cd94a3e99b754cbfd32cdf0', '1');
INSERT INTO `sys_user_position` VALUES ('ee8be31e87e8408c9045ead0b1444c48', 'aa3c6a22adc84f549da15c34d946fa55', '1');

-- ----------------------------
-- Table structure for tb_news
-- ----------------------------
DROP TABLE IF EXISTS `tb_news`;
CREATE TABLE `tb_news` (
  `newsId` int(11) NOT NULL AUTO_INCREMENT COMMENT '新闻id',
  `newsName` varchar(100) DEFAULT NULL COMMENT '新闻标题',
  `newsImg` varchar(50) DEFAULT NULL COMMENT '首页图片（附件表id）',
  `newsImgName` varchar(100) DEFAULT NULL COMMENT '首页图片名称',
  `infoSource` varchar(100) DEFAULT NULL COMMENT '信息来源',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `externalUrl` varchar(200) DEFAULT NULL COMMENT '外部链接地址',
  `remark` varchar(1000) DEFAULT NULL COMMENT '内容摘要',
  `newsContent` longtext COMMENT '新闻内容',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `releaseUserId` varchar(40) DEFAULT NULL COMMENT '发布人id、',
  `releaseArea` varchar(200) DEFAULT NULL COMMENT '发布区域',
  `releaseTime` varchar(25) DEFAULT NULL COMMENT '发布时间',
  `updateUserId` varchar(40) DEFAULT NULL COMMENT '修改人',
  `updateTime` varchar(25) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`newsId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_news
-- ----------------------------
INSERT INTO `tb_news` VALUES ('1', '外媒揭秘美国总统专用直升机:5架中只有1架是真的', '', null, '百度新闻', '0', 'https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_7608975642683815577%', '英国《太阳报》网站7月15日发表了题为《特朗普总统专用直升机——“海军陆战队员一号”长啥样?》的报道', '<p><span class=\"source\" style=\"font-size: 13px; line-height: 2; color: rgb(153, 153, 153); padding-right: 10px; border-right: 1px solid rgb(242, 242, 242);\">网易新闻</span><span class=\"date\" style=\"font-size: 13px; line-height: 2; color: rgb(153, 153, 153); margin: 0px 9px 0px 6px;\">07-24</span><span class=\"time\" style=\"font-size: 13px; line-height: 2; color: rgb(153, 153, 153);\">07:44</span></p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">（原标题：5架中只有1架是真的！英媒揭秘美国总统专用直升机）</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">英国《太阳报》网站7月15日发表了题为《特朗普总统专用直升机——“海军陆战队员一号”长啥样?》的报道。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">人们经常可以看到美国总统特朗普进出被称为“海军陆战队一号”的总统专用直升机，但它不只一架。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">这些直升机被称为“白顶”，原因在于它们被喷涂的颜色。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">唐纳德·特朗普的“海军陆战队一号”究竟是什么?</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">“海军陆战队一号”实际上指的是美国海军陆战队搭载美国现任总统的专机所使用的呼叫信号。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">它通常表示由海军陆战队第一直升机中队(HMX-1)操作的直升机。事实上，总统所使用的直升机有两种不同型号。</p><p><img class=\"large\" src=\"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3726923432,2690094607&fm=173&app=25&f=JPEG?w=550&h=413&s=5E229345405137C25F047C840300D0EA\"/></p><p style=\"margin-top: 26px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">资料图片：VH-3D“海王”直升机。(图片来源于网络)</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">较大的直升机是由西科尔斯基直升机公司制造的VH-3D“海王”直升机，较新且体型较小的是VH-60N“白鹰”直升机。海军陆战队第一直升机中队装备有35架直升机。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">这些直升机被称为“白顶”，因为飞机的上半部涂成白色，底部漆成暗绿色。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">“海军陆战队一号”还被用来运送内阁资深成员和外国要人。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">800多名海军陆战队员负责监督“海军陆战队一号”直升机编队的行动，其基地设在海军陆战队在加利福尼亚州基地——匡蒂科。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">“海军陆战队一号”采取了哪些安全措施?</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">“海军陆战队一号”总是与多达5架外形相同的直升机一同飞行，其他5架都是诱饵。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">这些直升机在飞行过程中会变化编队，以免暴露搭载总统的那架直升机。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">“海军陆战队一号”还具备标准的军事反导措施，比如用热焰弹来诱骗热寻的导弹。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">它还可以反制雷达制导导弹以及红外线干扰措施。</p><p><img class=\"large\" src=\"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1778789340,93402729&fm=173&app=25&f=JPEG?w=550&h=367&s=56B486654C59A3C206A95D850300E0AA\"/></p><p style=\"margin-top: 26px; margin-bottom: 0px; padding: 0px; font-size: 16px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify;\">资料图片：通常会有5架相同的直升机编队飞行，图为VH-3D双机编队飞行。(图片来源于网络)</p><p><br/></p>', '2018-07-24 15:30:47', '1', '9faac380235548998d4bc2b8b4a15193', null, '2018-07-24T15:13:02', null, null);
INSERT INTO `tb_news` VALUES ('2', '长生生物之问：股市不需要黑金！', '', null, '网易新闻', '0', 'https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_14945852547635353144', '文/新浪财经意见领袖（微信公众号kopleader）专栏作家 李德林', '<p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">中国股市应该去支持鼓励企业发展去盈利，但盈利挣取的不应是黑金。中国股市不应该树立赚取黑金合法化的土壤和温床。</p><p><img class=\"large\" src=\"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3352659263,1672899800&fm=173&app=25&f=JPEG?w=550&h=330&s=7A089E451272443F8E2A7F7C03008078\"/></p><p style=\"margin-top: 26px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">电影《我不是药神》一句经典台词：世界只有一种病，穷病。现在看来，世界还有一种病：唯利是图。有些病，乃至病死，是唯利是图而带来的。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">有病就得治。穷病难治，唯利是图之病难治，那就是监管失职。只有让那些唯利是图者，透过那铁窗甚至让他们面临死刑判决的时候，他们才会体会到常人的感叹“那帮人是不是都疯了”。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">长生生物，就是疯了。疫苗的假冒伪劣事件，既让中国的老百姓疯了，也让持有其股票的2.48万股民疯了。长生生物已经连续6个跌停。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">股民是无辜的。现在看来，长生生物市值蒸发近百亿之后，依然面临无休止的的下跌。公司作恶，加上其中存在的信息披露违规问题，损失惨重的股民投资者就应该发起集体诉讼要求赔偿。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">从维护股市乃至社会的公平正义而言，从维护股市的资源配置功能而言，我们认为不管长生生物的疫苗被判定是劣药还是假药，这家公司就应该退市——因为中国股市不应是孕育“黑金”之地。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">我相信，对于长生生物劣质疫苗事件，我们每一个国人都会关心，这一点相信也包含证监监管的人士，疫苗可是全民皆种的。在这种涉及到A股公司的天怒人怨大事件中，证监监管部门不应该缺席，应该为投资者清理这种黑天鹅、爆雷公司。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">中国股市存在的意义是什么？</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">我们知道，股市需要支持实体经济的发展，股市需要实业报国。支持国企改革脱困，我们股民无话可说，反正是为了国家脱困。支持国有银行改革，我们也无话可说，反正也是为了国家金融安全。支持创新创业，我们还是无话可说，反正还是为了国家科技强国。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">但，这一次，我们居然发现，我们支持了一个假冒伪劣医药公司，而且是变相草菅人命的生物疫苗公司。我们不禁要问，中国股市还需要继续支持一个危害生命安全，甚至从小孩身上赚取黑金的上市公司吗？</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">我们知道，创新药物，在中国确实是稀缺的，需要中国的生物科技公司加速努力突破。我们也知道，国外对于创新药也很鼓励，也允许暴利。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">《我不是药神》电影里面的癌症药物格列宁，现实中就是治癌药物格列卫。类似这种药物，因为研发周期超长，投入巨大，10年10亿美金的投入也不在话下，且失败居多。如果没有暴利，很多药企就没有动力去研发，博取那类似于赌博的成功概率。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">对于这种创新药物公司，我们尊敬，且认可暴利。只要你有技术，有科技含量，真的能救死扶伤，长期辛苦投入巨资的研发，即便暴利那也是企业该赚的钱。但是，那得是真真正正的药品。如果是假药或者劣药，还有致伤甚至有致人死亡的，也没关系，判刑严惩以及天价赔偿。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">本月，美国法院就判决，因22名患癌女性与强生公司滑石粉产品有关，责令强生公司支付5.5亿美元补偿性赔偿，以及41.4亿美元惩罚性赔偿。反观我们，由于长春长生生产、销售百白破疫苗劣药，刚刚才被罚没总计344.29万元。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">如此的恶劣行为，违法成本太低。要知道，不管狂犬疫苗、百白破疫苗，并非什么创新药物，都已经是很成熟的疫苗了。就是这种相对成熟的疫苗，依然有企业击穿基本的生产流程，造出来的居然是“效价测定”不符合规定。当然，长生生物的狂犬疫苗还不确定是劣药还是假药，如果是假药，那就是更为恶劣的问题。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">所谓“效价测定”说的劣药，专业术语就别那么文绉绉，说白了就是“没用”又打不死人。问题是，这是疫苗，不是其他慢性病的药物，疫苗本来就是为了防范某些重症疾病的，就是了防止得死人病的。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">就说说这个“狂犬病”，有些人可能只知道他得病了很严重，基本没得治。18年前，笔者在报社的时候，曾深度参与操作过一组狂犬病系列报道，得以全面了解这个疯狂的病。一旦得病发病，死亡率100%，而且死状极惨。在医院，狂犬病人是隔离着，被绑在床上，呼吸一点一点衰竭而死的。这个过程有些人还需要持续几天时间，生不如死。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">自从做过这组报道之后，我就对养猫养狗不感冒了，平时并不害怕，但要自己养，还是心有余悸。不过现代社会，养猫养狗的城市居民比比皆是，小宝宝们更是喜爱小动物。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">因此，狂犬疫苗所谓的无效，并不是危害小的一种疫苗，这是涉及到成千上亿人的生命健康安全的事情。在普通民众看来，药品无小事，尤其涉及到疫苗领域的，都是人命关天的大事。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">长生生物这种赚取“黑金”的公司，证监监管机构，就应该态度鲜明的站出来，坚决清理出资本市场。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">中国股市最大的功能是什么？是资源配置功能。长生生物完全颠覆了股市作为资源配置的功能，资源错配了，而且不仅仅是错配，是变相的助纣为虐了。错配了就应该修正，退市制度不是形同虚设的，这个时候证监监管不能也不应缺席，就应该借此树权威树典型。要知道，长生生物的违法违规，比那些财务造假、虚假上市的公司，要黑十倍甚至百倍，不仅割股民得韭菜，还割股民的命。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">中国股市应该去支持鼓励企业发展去盈利，但盈利挣取的不应是黑金。中国股市不应该树立赚取黑金合法化的土壤和温床。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">三年前的3·15，我写过专栏说过：执法不严，监管无力，结果将会是——守规者，钱财不入；越规者，暴富而起。违法者先富，越违法越易富，不顾人命者则暴富。这才是中国商业诚信的黑天鹅，才是3·15真正让人恐惧之处。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">三年过去了，我不知道改变了多少，但今天依然让我们恐惧，甚至全民恐惧，见识了一家“黑金”公司的存在。40年改革开放下，中国已经过了野蛮生长的年代，应该是立规矩的时代了。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">我们看到，中国股市正在监管回归，包括对财务造假零容忍。我们也看到，证监会已经对长生生物信息披露违法违规进行调查。但我们依然担心，监管之手太柔太松了，监管的回归更应该告诉那些上市公司，那些妄图赚取“黑金”来蒙混上市的公司，中国股市不欢迎，而且一旦发现就应该被清理甚至退市。因此，对于长生生物，勒令退市才是监管应有的态度。</p><p style=\"margin-top: 22px; margin-bottom: 0px; padding: 0px; line-height: 24px; color: rgb(51, 51, 51); text-align: justify; font-family: arial; white-space: normal; background-color: rgb(255, 255, 255);\">(本文作者介绍：著名财经作家、《德林爆语》主持人。三分钟财经脱口秀，每天一个资本真相，微信公众号:delinshe)</p><p><br/></p>', '2018-07-24 16:06:55', '1', '9faac380235548998d4bc2b8b4a15193', null, '2018-07-23T16:06:10', null, null);

-- ----------------------------
-- Table structure for tb_news_belong_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_news_belong_menu`;
CREATE TABLE `tb_news_belong_menu` (
  `id` int(11) DEFAULT NULL COMMENT 'id',
  `menuId` int(11) DEFAULT NULL COMMENT '栏目id',
  `newsId` int(11) DEFAULT NULL COMMENT '新闻id',
  `state` int(11) DEFAULT NULL COMMENT '状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_news_belong_menu
-- ----------------------------
INSERT INTO `tb_news_belong_menu` VALUES (null, '2', '1', null);
INSERT INTO `tb_news_belong_menu` VALUES (null, '1', '2', null);

-- ----------------------------
-- Table structure for tb_news_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_news_menu`;
CREATE TABLE `tb_news_menu` (
  `menuId` int(11) NOT NULL AUTO_INCREMENT COMMENT '栏目id',
  `menuName` varchar(50) NOT NULL COMMENT '栏目名称',
  `superiorId` int(11) DEFAULT NULL COMMENT '上级Id',
  `createUserId` varchar(50) DEFAULT NULL COMMENT '创建人',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `sequence` int(11) DEFAULT NULL COMMENT '顺序，越大越靠前，可重复',
  `menuUrl` varchar(200) DEFAULT NULL COMMENT '栏目链接',
  `updateUserid` varchar(50) DEFAULT NULL COMMENT '修改人',
  `updateTime` varchar(30) DEFAULT NULL COMMENT '修改时间',
  `fileImg` varchar(500) DEFAULT NULL COMMENT '栏目图片（附件表id）',
  `fileImgName` varchar(100) DEFAULT NULL COMMENT '栏目图片名称',
  `menuModel` varchar(100) DEFAULT NULL COMMENT '栏目模型',
  `type` int(11) DEFAULT NULL COMMENT '类型，0=跟节点，1为子节点',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`menuId`,`menuName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_news_menu
-- ----------------------------
INSERT INTO `tb_news_menu` VALUES ('1', 'test_lanmu1', null, '9faac380235548998d4bc2b8b4a15193', '2018-07-24 15:03:31', '1', '1', 'www.baidu.com', null, null, '', null, 'TextVideoArticles', '0', '1111111111111111111');
INSERT INTO `tb_news_menu` VALUES ('2', 'test_lanmu1_1', '1', '9faac380235548998d4bc2b8b4a15193', '2018-07-24 15:05:18', '1', '1', 'www.baidu.com', null, null, '', null, 'FriendshipUrl', '1', '2222222222222');

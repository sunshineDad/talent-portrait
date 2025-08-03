-- ============================================
-- 基础字典表
-- ============================================

-- 字典类型表
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典类型ID',
  `dict_name` varchar(100) NOT NULL COMMENT '字典名称',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- 字典数据表
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典数据ID',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `dict_label` varchar(100) NOT NULL COMMENT '字典标签',
  `dict_value` varchar(100) NOT NULL COMMENT '字典键值',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `sort_order` int(11) DEFAULT '0' COMMENT '排序',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- ============================================
-- 团队组织表
-- ============================================

DROP TABLE IF EXISTS `team_info`;
CREATE TABLE `team_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '团队ID',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父团队ID',
  `team_code` varchar(50) NOT NULL COMMENT '团队编码',
  `team_name` varchar(100) NOT NULL COMMENT '团队名称',
  `team_type` varchar(20) NOT NULL COMMENT '团队类型（参考字典：team_type）',
  `leader_id` bigint(20) DEFAULT NULL COMMENT '团队负责人ID',
  `ancestors` varchar(500) DEFAULT '' COMMENT '祖级列表',
  `order_num` int(11) DEFAULT '0' COMMENT '显示顺序',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0存在 1删除）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_team_code` (`team_code`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队信息表';

-- ============================================
-- 人员基础信息表
-- ============================================

DROP TABLE IF EXISTS `person_info`;
CREATE TABLE `person_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '人员ID',
  `person_code` varchar(50) NOT NULL COMMENT '人员编号',
  `person_name` varchar(50) NOT NULL COMMENT '姓名',
  `gender` char(1) DEFAULT NULL COMMENT '性别（1男 2女）',
  `birth_date` date DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `education` varchar(20) DEFAULT NULL COMMENT '最高学历（参考字典：education）',
  `degree` varchar(20) DEFAULT NULL COMMENT '最高学位（参考字典：degree）',
  `graduate_school` varchar(100) DEFAULT NULL COMMENT '毕业院校',
  `major` varchar(100) DEFAULT NULL COMMENT '所学专业',
  `work_start_date` date DEFAULT NULL COMMENT '参加工作时间',
  `join_date` date DEFAULT NULL COMMENT '入职时间',
  `job_title` varchar(50) DEFAULT NULL COMMENT '职称（参考字典：job_title）',
  `job_level` varchar(20) DEFAULT NULL COMMENT '职级（参考字典：job_level）',
  `position` varchar(100) DEFAULT NULL COMMENT '岗位',
  `team_id` bigint(20) DEFAULT NULL COMMENT '所属团队ID',
  `work_location` varchar(100) DEFAULT NULL COMMENT '工作地点',
  `employment_type` varchar(20) DEFAULT NULL COMMENT '用工类型（参考字典：employment_type）',
  `political_status` varchar(20) DEFAULT NULL COMMENT '政治面貌（参考字典：political_status）',
  `photo_url` varchar(500) DEFAULT NULL COMMENT '照片URL',
  `status` char(1) DEFAULT '0' COMMENT '状态（0在职 1离职）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0存在 1删除）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_person_code` (`person_code`),
  KEY `idx_team_id` (`team_id`),
  KEY `idx_person_name` (`person_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员基础信息表';

-- ============================================
-- 人员专业能力表
-- ============================================

DROP TABLE IF EXISTS `person_skill`;
CREATE TABLE `person_skill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `person_id` bigint(20) NOT NULL COMMENT '人员ID',
  `skill_type` varchar(50) NOT NULL COMMENT '能力类型（参考字典：skill_type）',
  `skill_name` varchar(100) NOT NULL COMMENT '能力名称',
  `skill_level` varchar(20) DEFAULT NULL COMMENT '能力等级（参考字典：skill_level）',
  `experience_years` decimal(3,1) DEFAULT NULL COMMENT '经验年限',
  `certification` varchar(200) DEFAULT NULL COMMENT '相关证书',
  `description` text COMMENT '能力描述',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_person_id` (`person_id`),
  KEY `idx_skill_type` (`skill_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员专业能力表';

-- ============================================
-- 人员项目经历表
-- ============================================

DROP TABLE IF EXISTS `person_project`;
CREATE TABLE `person_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `person_id` bigint(20) NOT NULL COMMENT '人员ID',
  `project_name` varchar(200) NOT NULL COMMENT '项目名称',
  `project_code` varchar(100) DEFAULT NULL COMMENT '项目编号',
  `project_type` varchar(50) DEFAULT NULL COMMENT '项目类型（参考字典：project_type）',
  `project_level` varchar(20) DEFAULT NULL COMMENT '项目级别（参考字典：project_level）',
  `start_date` date DEFAULT NULL COMMENT '开始时间',
  `end_date` date DEFAULT NULL COMMENT '结束时间',
  `project_role` varchar(50) DEFAULT NULL COMMENT '项目角色（参考字典：project_role）',
  `project_status` varchar(20) DEFAULT NULL COMMENT '项目状态（参考字典：project_status）',
  `achievement` text COMMENT '主要成果',
  `technology_stack` varchar(500) DEFAULT NULL COMMENT '技术栈',
  `team_size` int(11) DEFAULT NULL COMMENT '团队规模',
  `project_budget` decimal(12,2) DEFAULT NULL COMMENT '项目预算（万元）',
  `description` text COMMENT '项目描述',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_person_id` (`person_id`),
  KEY `idx_project_type` (`project_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员项目经历表';

-- ============================================
-- 人员绩效信息表
-- ============================================

DROP TABLE IF EXISTS `person_performance`;
CREATE TABLE `person_performance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `person_id` bigint(20) NOT NULL COMMENT '人员ID',
  `year` int(4) NOT NULL COMMENT '年度',
  `quarter` int(1) DEFAULT NULL COMMENT '季度（1-4）',
  `performance_score` decimal(5,2) DEFAULT NULL COMMENT '绩效分数',
  `performance_level` varchar(20) DEFAULT NULL COMMENT '绩效等级（参考字典：performance_level）',
  `kpi_completion` decimal(5,2) DEFAULT NULL COMMENT 'KPI完成率（%）',
  `work_quality` varchar(20) DEFAULT NULL COMMENT '工作质量评价',
  `work_attitude` varchar(20) DEFAULT NULL COMMENT '工作态度评价',
  `team_contribution` varchar(20) DEFAULT NULL COMMENT '团队贡献评价',
  `improvement_suggestions` text COMMENT '改进建议',
  `evaluator` varchar(50) DEFAULT NULL COMMENT '评价人',
  `evaluation_date` date DEFAULT NULL COMMENT '评价日期',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_person_year_quarter` (`person_id`,`year`,`quarter`),
  KEY `idx_year` (`year`),
  KEY `idx_performance_level` (`performance_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员绩效信息表';

-- ============================================
-- 人员影响力信息表
-- ============================================

DROP TABLE IF EXISTS `person_influence`;
CREATE TABLE `person_influence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `person_id` bigint(20) NOT NULL COMMENT '人员ID',
  `influence_type` varchar(50) NOT NULL COMMENT '影响力类型（参考字典：influence_type）',
  `influence_name` varchar(200) NOT NULL COMMENT '影响力名称（如：职务名称、委员会名称等）',
  `organization` varchar(200) DEFAULT NULL COMMENT '组织/机构名称',
  `position_level` varchar(50) DEFAULT NULL COMMENT '职位级别',
  `start_date` date DEFAULT NULL COMMENT '开始时间',
  `end_date` date DEFAULT NULL COMMENT '结束时间',
  `is_current` char(1) DEFAULT '1' COMMENT '是否在任（0否 1是）',
  `influence_scope` varchar(50) DEFAULT NULL COMMENT '影响范围（参考字典：influence_scope）',
  `description` text COMMENT '职责描述',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_person_id` (`person_id`),
  KEY `idx_influence_type` (`influence_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员影响力信息表';

-- ============================================
-- 人员创新产出表
-- ============================================

DROP TABLE IF EXISTS `person_innovation`;
CREATE TABLE `person_innovation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `person_id` bigint(20) NOT NULL COMMENT '人员ID',
  `innovation_type` varchar(50) NOT NULL COMMENT '创新类型（参考字典：innovation_type）',
  `innovation_name` varchar(200) NOT NULL COMMENT '创新成果名称',
  `innovation_level` varchar(50) DEFAULT NULL COMMENT '成果级别（参考字典：innovation_level）',
  `complete_date` date DEFAULT NULL COMMENT '完成时间',
  `patent_no` varchar(100) DEFAULT NULL COMMENT '专利号/论文号/标准号',
  `authors` varchar(500) DEFAULT NULL COMMENT '作者/发明人',
  `author_order` int(11) DEFAULT NULL COMMENT '作者排序',
  `publication` varchar(200) DEFAULT NULL COMMENT '发表刊物/授权机构',
  `innovation_status` varchar(20) DEFAULT NULL COMMENT '成果状态（参考字典：innovation_status）',
  `economic_benefit` decimal(12,2) DEFAULT NULL COMMENT '经济效益（万元）',
  `social_benefit` text COMMENT '社会效益',
  `description` text COMMENT '成果描述',
  `attachment_url` varchar(500) DEFAULT NULL COMMENT '附件URL',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_person_id` (`person_id`),
  KEY `idx_innovation_type` (`innovation_type`),
  KEY `idx_complete_date` (`complete_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员创新产出表';

-- ============================================
-- 人员人才评估表
-- ============================================

DROP TABLE IF EXISTS `person_evaluation`;
CREATE TABLE `person_evaluation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `person_id` bigint(20) NOT NULL COMMENT '人员ID',
  `evaluation_year` int(4) NOT NULL COMMENT '评估年度',
  `evaluation_dimension` varchar(50) NOT NULL COMMENT '评估维度（参考字典：evaluation_dimension）',
  `dimension_score` decimal(5,2) DEFAULT NULL COMMENT '维度得分',
  `dimension_level` varchar(20) DEFAULT NULL COMMENT '维度等级',
  `evaluation_content` text COMMENT '评估内容',
  `strengths` text COMMENT '优势分析',
  `weaknesses` text COMMENT '劣势分析',
  `development_suggestions` text COMMENT '发展建议',
  `evaluator` varchar(50) DEFAULT NULL COMMENT '评估人',
  `evaluation_date` date DEFAULT NULL COMMENT '评估日期',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_person_id` (`person_id`),
  KEY `idx_evaluation_year` (`evaluation_year`),
  KEY `idx_evaluation_dimension` (`evaluation_dimension`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员人才评估表';
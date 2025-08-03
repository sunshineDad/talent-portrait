-- ============================================
-- 初始化字典类型数据
-- ============================================

INSERT INTO sys_dict_type (dict_name, dict_type, description, status) VALUES
('性别', 'sys_user_sex', '用户性别列表', '0'),
('学历', 'education', '学历列表', '0'),
('学位', 'degree', '学位列表', '0'),
('职称', 'job_title', '职称列表', '0'),
('职级', 'job_level', '职级列表', '0'),
('用工类型', 'employment_type', '用工类型列表', '0'),
('政治面貌', 'political_status', '政治面貌列表', '0'),
('团队类型', 'team_type', '团队类型列表', '0'),
('能力类型', 'skill_type', '专业能力类型', '0'),
('能力等级', 'skill_level', '能力等级列表', '0'),
('项目类型', 'project_type', '项目类型列表', '0'),
('项目级别', 'project_level', '项目级别列表', '0'),
('项目角色', 'project_role', '项目角色列表', '0'),
('项目状态', 'project_status', '项目状态列表', '0'),
('绩效等级', 'performance_level', '绩效等级列表', '0'),
('影响力类型', 'influence_type', '影响力类型列表', '0'),
('影响范围', 'influence_scope', '影响范围列表', '0'),
('创新类型', 'innovation_type', '创新成果类型', '0'),
('创新级别', 'innovation_level', '创新成果级别', '0'),
('创新状态', 'innovation_status', '创新成果状态', '0'),
('评估维度', 'evaluation_dimension', '人才评估维度', '0');

-- ============================================
-- 初始化字典数据
-- ============================================

-- 性别
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('sys_user_sex', '男', '1', 1, '0'),
('sys_user_sex', '女', '2', 2, '0');

-- 学历
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('education', '博士研究生', 'doctor', 1, '0'),
('education', '硕士研究生', 'master', 2, '0'),
('education', '本科', 'bachelor', 3, '0'),
('education', '大专', 'college', 4, '0'),
('education', '高中及以下', 'high_school', 5, '0');

-- 学位
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('degree', '博士', 'doctor', 1, '0'),
('degree', '硕士', 'master', 2, '0'),
('degree', '学士', 'bachelor', 3, '0'),
('degree', '无', 'none', 4, '0');

-- 职称
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('job_title', '正高级工程师', 'senior_engineer', 1, '0'),
('job_title', '高级工程师', 'advanced_engineer', 2, '0'),
('job_title', '工程师', 'engineer', 3, '0'),
('job_title', '助理工程师', 'assistant_engineer', 4, '0'),
('job_title', '技术员', 'technician', 5, '0');

-- 职级
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('job_level', '首席专家', 'chief_expert', 1, '0'),
('job_level', '专家', 'expert', 2, '0'),
('job_level', '高级主管', 'senior_manager', 3, '0'),
('job_level', '主管', 'manager', 4, '0'),
('job_level', '高级专员', 'senior_specialist', 5, '0'),
('job_level', '专员', 'specialist', 6, '0');

-- 用工类型
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('employment_type', '正式员工', 'formal', 1, '0'),
('employment_type', '劳务派遣', 'dispatch', 2, '0'),
('employment_type', '外包', 'outsource', 3, '0'),
('employment_type', '实习生', 'intern', 4, '0');

-- 政治面貌
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('political_status', '中共党员', 'party_member', 1, '0'),
('political_status', '中共预备党员', 'probationary_member', 2, '0'),
('political_status', '共青团员', 'league_member', 3, '0'),
('political_status', '民主党派', 'democratic_party', 4, '0'),
('political_status', '群众', 'masses', 5, '0');

-- 团队类型
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('team_type', '研发团队', 'rd_team', 1, '0'),
('team_type', '项目团队', 'project_team', 2, '0'),
('team_type', '技术团队', 'tech_team', 3, '0'),
('team_type', '管理团队', 'manage_team', 4, '0'),
('team_type', '临时团队', 'temp_team', 5, '0');

-- 能力类型
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('skill_type', '核电技术', 'nuclear_tech', 1, '0'),
('skill_type', '软件开发', 'software_dev', 2, '0'),
('skill_type', '项目管理', 'project_mgmt', 3, '0'),
('skill_type', '质量控制', 'quality_ctrl', 4, '0'),
('skill_type', '安全管理', 'safety_mgmt', 5, '0'),
('skill_type', '数据分析', 'data_analysis', 6, '0');

-- 能力等级
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('skill_level', '专家级', 'expert', 1, '0'),
('skill_level', '精通', 'proficient', 2, '0'),
('skill_level', '熟练', 'skilled', 3, '0'),
('skill_level', '掌握', 'competent', 4, '0'),
('skill_level', '了解', 'basic', 5, '0');

-- 项目类型
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('project_type', '国家级项目', 'national', 1, '0'),
('project_type', '省部级项目', 'provincial', 2, '0'),
('project_type', '企业重点项目', 'key_enterprise', 3, '0'),
('project_type', '一般项目', 'general', 4, '0'),
('project_type', '横向课题', 'horizontal', 5, '0');

-- 项目级别
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('project_level', '特大型', 'mega', 1, '0'),
('project_level', '大型', 'large', 2, '0'),
('project_level', '中型', 'medium', 3, '0'),
('project_level', '小型', 'small', 4, '0');

-- 项目角色
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('project_role', '项目负责人', 'project_leader', 1, '0'),
('project_role', '技术负责人', 'tech_leader', 2, '0'),
('project_role', '核心成员', 'core_member', 3, '0'),
('project_role', '一般成员', 'member', 4, '0');

-- 项目状态
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('project_status', '进行中', 'ongoing', 1, '0'),
('project_status', '已完成', 'completed', 2, '0'),
('project_status', '已验收', 'accepted', 3, '0'),
('project_status', '暂停', 'paused', 4, '0'),
('project_status', '终止', 'terminated', 5, '0');

-- 绩效等级
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, css_class, status) VALUES
('performance_level', 'S级', 'S', 1, 'el-tag--success', '0'),
('performance_level', 'A级', 'A', 2, 'el-tag--primary', '0'),
('performance_level', 'B级', 'B', 3, 'el-tag--info', '0'),
('performance_level', 'C级', 'C', 4, 'el-tag--warning', '0'),
('performance_level', 'D级', 'D', 5, 'el-tag--danger', '0');

-- 影响力类型
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('influence_type', '学术委员会', 'academic_committee', 1, '0'),
('influence_type', '标准委员会', 'standard_committee', 2, '0'),
('influence_type', '行业协会', 'industry_association', 3, '0'),
('influence_type', '技术专家组', 'expert_group', 4, '0'),
('influence_type', '社会兼职', 'social_position', 5, '0');

-- 影响范围
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('influence_scope', '国际级', 'international', 1, '0'),
('influence_scope', '国家级', 'national', 2, '0'),
('influence_scope', '行业级', 'industry', 3, '0'),
('influence_scope', '企业级', 'enterprise', 4, '0');

-- 创新类型
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('innovation_type', '发明专利', 'invention_patent', 1, '0'),
('innovation_type', '实用新型专利', 'utility_patent', 2, '0'),
('innovation_type', 'SCI论文', 'sci_paper', 3, '0'),
('innovation_type', 'EI论文', 'ei_paper', 4, '0'),
('innovation_type', '核心期刊', 'core_journal', 5, '0'),
('innovation_type', '技术标准', 'tech_standard', 6, '0'),
('innovation_type', '软件著作权', 'software_copyright', 7, '0'),
('innovation_type', '科技奖励', 'tech_award', 8, '0');

-- 创新级别
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('innovation_level', '国际级', 'international', 1, '0'),
('innovation_level', '国家级', 'national', 2, '0'),
('innovation_level', '省部级', 'provincial', 3, '0'),
('innovation_level', '企业级', 'enterprise', 4, '0');

-- 创新状态
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('innovation_status', '已授权', 'authorized', 1, '0'),
('innovation_status', '审核中', 'reviewing', 2, '0'),
('innovation_status', '已发表', 'published', 3, '0'),
('innovation_status', '已应用', 'applied', 4, '0');

-- 评估维度
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, sort_order, status) VALUES
('evaluation_dimension', '专业能力', 'professional', 1, '0'),
('evaluation_dimension', '创新能力', 'innovation', 2, '0'),
('evaluation_dimension', '团队协作', 'teamwork', 3, '0'),
('evaluation_dimension', '学习能力', 'learning', 4, '0'),
('evaluation_dimension', '领导力', 'leadership', 5, '0'),
('evaluation_dimension', '执行力', 'execution', 6, '0');

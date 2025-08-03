-- ============================================
-- 创建索引以优化查询性能
-- ============================================

-- 字典数据表索引
CREATE INDEX idx_dict_status ON sys_dict_data(status);
CREATE INDEX idx_dict_sort ON sys_dict_data(dict_type, sort_order);

-- 团队信息表索引
CREATE INDEX idx_team_status ON team_info(status);
CREATE INDEX idx_team_ancestors ON team_info(ancestors);

-- 人员基础信息表索引
CREATE INDEX idx_person_status ON person_info(status);
CREATE INDEX idx_person_education ON person_info(education);
CREATE INDEX idx_person_job_title ON person_info(job_title);
CREATE INDEX idx_person_join_date ON person_info(join_date);
CREATE INDEX idx_person_work_date ON person_info(work_start_date);

-- 人员专业能力表索引
CREATE INDEX idx_skill_level ON person_skill(skill_level);
CREATE INDEX idx_skill_person_type ON person_skill(person_id, skill_type);

-- 人员项目经历表索引
CREATE INDEX idx_project_dates ON person_project(start_date, end_date);
CREATE INDEX idx_project_role ON person_project(project_role);
CREATE INDEX idx_project_status ON person_project(project_status);

-- 人员绩效信息表索引
CREATE INDEX idx_performance_person_year ON person_performance(person_id, year);

-- 人员影响力信息表索引
CREATE INDEX idx_influence_current ON person_influence(is_current);
CREATE INDEX idx_influence_dates ON person_influence(start_date, end_date);

-- 人员创新产出表索引
CREATE INDEX idx_innovation_year ON person_innovation(complete_date);
CREATE INDEX idx_innovation_level ON person_innovation(innovation_level);

-- 人员人才评估表索引
CREATE INDEX idx_evaluation_person_year ON person_evaluation(person_id, evaluation_year);

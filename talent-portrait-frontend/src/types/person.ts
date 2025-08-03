/**
 * 人员信息类型定义
 * 根据数据库设计文档[[4]][doc_4]定义
 */

// 人员基础信息
export interface PersonInfo {
  id?: number
  personCode: string
  personName: string
  gender?: string
  birthDate?: string
  idCard?: string
  phone?: string
  email?: string
  education?: string
  degree?: string
  graduateSchool?: string
  major?: string
  workStartDate?: string
  joinDate?: string
  jobTitle?: string
  jobLevel?: string
  position?: string
  teamId: number
  teamName?: string
  workLocation?: string
  employmentType?: string
  politicalStatus?: string
  photoUrl?: string
  status?: string
  remark?: string
  // 子表数据
  skillList?: PersonSkill[]
  projectList?: PersonProject[]
  performanceList?: PersonPerformance[]
  influenceList?: PersonInfluence[]
  innovationList?: PersonInnovation[]
  evaluationList?: PersonEvaluation[]
  maintenanceList?: PersonMaintenance[]
}

// 人员专业能力
export interface PersonSkill {
  id?: number
  personId?: number
  skillType: string
  skillName: string
  skillLevel: string
  experienceYears: number
  certification?: string
  description?: string
}

// 人员项目经历
export interface PersonProject {
  id?: number
  personId?: number
  projectName: string
  projectCode?: string
  projectType: string
  projectLevel: string
  startDate: string
  endDate?: string
  projectRole: string
  projectStatus?: string
  achievement?: string
  technologyStack?: string
  teamSize?: number
  projectBudget?: number
  description?: string
}

// 人员绩效信息
export interface PersonPerformance {
  id?: number
  personId?: number
  year: number
  quarter?: number
  performanceScore: number
  performanceLevel: string
  kpiCompletion?: number
  workQuality?: number
  workAttitude?: number
  teamContribution?: number
  improvementSuggestions?: string
  evaluator?: string
  evaluationDate?: string
}

// 人员影响力信息
export interface PersonInfluence {
  id?: number
  personId?: number
  influenceType: string
  influenceName: string
  organization: string
  positionLevel?: string
  startDate: string
  endDate?: string
  isCurrent?: string
  influenceScope?: string
  description?: string
}

// 人员创新产出
export interface PersonInnovation {
  id?: number
  personId?: number
  innovationType: string
  innovationName: string
  innovationLevel: string
  completeDate: string
  patentNo?: string
  authors?: string
  authorOrder?: number
  publication?: string
  innovationStatus?: string
  economicBenefit?: number
  socialBenefit?: string
  description?: string
  attachmentUrl?: string
}

// 人员人才评估
export interface PersonEvaluation {
  id?: number
  personId?: number
  evaluationYear: number
  evaluationDimension: string
  dimensionScore: number
  dimensionLevel?: string
  evaluationContent?: string
  strengths?: string
  weaknesses?: string
  developmentSuggestions?: string
  evaluator?: string
  evaluationDate?: string
}

// 人才信息维护
export interface PersonMaintenance {
  id?: number
  personId?: number
  talentProgram: string
  talentPlan: string
  programLevel: string
  selectedDate: string
  validStartDate: string
  validEndDate: string
  isValid?: string
  certificateNo?: string
  supportMeasures?: string
  trainingPlan?: string
  assessmentRequirements?: string
  description?: string
}

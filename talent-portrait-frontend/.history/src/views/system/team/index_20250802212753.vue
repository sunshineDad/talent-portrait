<template>
  <div class="team-container">
    <div class="team-wrapper">
      <!-- 左侧团队树 -->
      <div class="team-tree-panel">
        <div class="tree-header">
          <span>团队结构</span>
          <el-button type="primary" size="small" icon="Plus" @click="handleAdd(null)">新增</el-button>
        </div>
        <div class="tree-search">
          <el-input
            v-model="filterText"
            placeholder="输入团队名称搜索"
            prefix-icon="Search"
          />
        </div>
        <div class="tree-content">
          <el-tree
            ref="treeRef"
            :data="teamTree"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          >
            <template #default="{ node, data }">
              <span class="tree-node">
                <el-icon><Folder /></el-icon>
                <span>{{ node.label }}</span>
                <span class="node-count">({{ data.memberCount || 0 }}人)</span>
              </span>
            </template>
          </el-tree>
        </div>
      </div>

      <!-- 右侧详情 -->
      <div class="team-detail-panel" v-loading="detailLoading">
        <template v-if="currentTeam">
          <!-- 团队基本信息 -->
          <el-card class="info-card">
            <template #header>
              <div class="card-header">
                <span>团队信息</span>
                <div>
                  <el-button type="primary" size="small" @click="handleEdit">编辑</el-button>
                  <el-button type="danger" size="small" @click="handleDelete">删除</el-button>
                </div>
              </div>
            </template>

            <el-descriptions :column="2" border>
              <el-descriptions-item label="团队名称">
                {{ currentTeam.teamName }}
              </el-descriptions-item>
              <el-descriptions-item label="团队编码">
                {{ currentTeam.teamCode }}
              </el-descriptions-item>
              <el-descriptions-item label="上级团队">
                {{ currentTeam.parentName || '无' }}
              </el-descriptions-item>
              <el-descriptions-item label="团队负责人">
                {{ currentTeam.leaderName || '-' }}
              </el-descriptions-item>
              <el-descriptions-item label="团队类型">
                <dict-tag :options="teamTypeOptions" :value="currentTeam.teamType" />
              </el-descriptions-item>
              <el-descriptions-item label="团队级别">
                <dict-tag :options="teamLevelOptions" :value="currentTeam.teamLevel" />
              </el-descriptions-item>
              <el-descriptions-item label="成立日期">
                {{ currentTeam.establishDate }}
              </el-descriptions-item>
              <el-descriptions-item label="状态">
                <el-tag :type="currentTeam.status === '0' ? 'success' : 'danger'">
                  {{ currentTeam.status === '0' ? '正常' : '停用' }}
                </el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="团队描述" :span="2">
                {{ currentTeam.description || '-' }}
              </el-descriptions-item>
            </el-descriptions>
          </el-card>

          <!-- 团队统计信息 -->
          <el-card class="statistics-card">
            <template #header>
              <span>团队统计</span>
            </template>

            <el-row :gutter="20">
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-value">{{ statistics.totalMembers }}</div>
                  <div class="stat-label">团队总人数</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-value">{{ statistics.directMembers }}</div>
                  <div class="stat-label">直属成员</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-value">{{ statistics.subTeams }}</div>
                  <div class="stat-label">下级团队</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-value">{{ statistics.avgAge }}</div>
                  <div class="stat-label">平均年龄</div>
                </div>
              </el-col>
            </el-row>

            <el-divider />

            <el-row :gutter="20">
              <el-col :span="8">
                <div class="chart-title">学历分布</div>
                <div class="mini-chart" ref="educationChart"></div>
              </el-col>
              <el-col :span="8">
                <div class="chart-title">职称分布</div>
                <div class="mini-chart" ref="titleChart"></div>
              </el-col>
              <el-col :span="8">
                <div class="chart-title">性别分布</div>
                <div class="mini-chart" ref="genderChart"></div>
              </el-col>
            </el-row>
          </el-card>

          <!-- 团队成员列表 -->
          <el-card class="members-card">
            <template #header>
              <div class="card-header">
                <span>团队成员</span>
                <el-button type="primary" size="small" @click="handleAddMember">添加成员</el-button>
              </div>
            </template>

            <el-table :data="memberList" border>
              <el-table-column label="姓名" prop="personName" width="100">
                <template #default="scope">
                  <el-link type="primary" @click="viewPerson(scope.row)">
                    {{ scope.row.personName }}
                  </el-link>
                </template>
              </el-table-column>
              <el-table-column label="编号" prop="personCode" width="120" />
              <el-table-column label="职务" prop="position" />
              <el-table-column label="职称" prop="jobTitle">
                <template #default="scope">
                  <dict-tag :options="jobTitleOptions" :value="scope.row.jobTitle" />
                </template>
              </el-table-column>
              <el-table-column label="加入时间" prop="joinTeamDate" width="120" />
              <el-table-column label="是否负责人" width="100" align="center">
                <template #default="scope">
                  <el-tag v-if="scope.row.id === currentTeam.leaderId" type="danger">
                    负责人
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="100" align="center">
                <template #default="scope">
                  <el-button
                    link
                    type="danger"
                    size="small"
                    @click="handleRemoveMember(scope.row)"
                  >移除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </template>

        <el-empty v-else description="请选择团队查看详情" />
      </div>
    </div>

    <!-- 团队表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      :before-close="handleClose"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="上级团队" prop="parentId">
          <el-tree-select
            v-model="form.parentId"
            :data="teamTreeOptions"
            :props="{ value: 'id', label: 'teamName', children: 'children' }"
            placeholder="请选择上级团队"
            clearable
            check-strictly
            style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="团队名称" prop="teamName">
          <el-input v-model="form.teamName" placeholder="请输入团队名称" />
        </el-form-item>

        <el-form-item label="团队编码" prop="teamCode">
          <el-input v-model="form.teamCode" placeholder="请输入团队编码" />
        </el-form-item>

        <el-form-item label="团队负责人" prop="leaderId">
          <el-select
            v-model="form.leaderId"
            placeholder="请选择团队负责人"
            clearable
            filterable
            style="width: 100%"
          >
            <el-option
              v-for="person in leaderOptions"
              :key="person.id"
              :label="`${person.personName} - ${person.personCode}`"
              :value="person.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="团队类型" prop="teamType">
          <el-select v-model="form.teamType" placeholder="请选择团队类型" style="width: 100%">
            <el-option
              v-for="dict in teamTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="团队级别" prop="teamLevel">
          <el-select v-model="form.teamLevel" placeholder="请选择团队级别" style="width: 100%">
            <el-option
              v-for="dict in teamLevelOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="成立日期" prop="establishDate">
          <el-date-picker
            v-model="form.establishDate"
            type="date"
            placeholder="选择成立日期"
            value-format="YYYY-MM-DD"
            style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio

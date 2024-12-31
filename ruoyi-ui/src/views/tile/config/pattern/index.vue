<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item prop="patternName">
        <label for="query-pattern-name">图案名称</label>
        <el-input
          id="query-pattern-name"
          v-model="queryParams.patternName"
          placeholder="请输入图案名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="status">
        <label for="query-status">状态</label>
        <el-select
          id="query-status"
          v-model="queryParams.status"
          placeholder="图案状态"
          clearable
        >
          <el-option
            v-for="dict in sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="Search" @click="handleQuery">搜索</el-button>
        <el-button :icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          :icon="Plus"
          @click="handleAdd"
          v-hasPermi="['tile:pattern:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          :icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['tile:pattern:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          :icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['tile:pattern:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          :icon="Download"
          @click="handleExport"
          v-hasPermi="['tile:pattern:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="patternList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="图案编号" align="center" prop="patternId" />
      <el-table-column label="图案名称" align="center" prop="patternName" />
      <el-table-column label="显示顺序" align="center" prop="orderNum" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="'0'"
            :inactive-value="'1'"
            inline-prompt
            @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            link
            :icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tile:pattern:edit']"
          >修改</el-button>
          <el-button
            link
            :icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tile:pattern:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item prop="patternName">
          <label for="form-pattern-name">图案名称</label>
          <el-input
            id="form-pattern-name"
            v-model="form.patternName"
            placeholder="请输入图案名称"
          />
        </el-form-item>
        <el-form-item prop="orderNum">
          <label for="form-order-num">显示顺序</label>
          <el-input-number
            id="form-order-num"
            v-model="form.orderNum"
            :min="0"
          />
        </el-form-item>
        <el-form-item>
          <label for="form-status">状态</label>
          <el-radio-group
            id="form-status"
            v-model="form.status"
            @change="(val) => form.status = val"
          >
            <el-radio
              v-for="dict in sys_normal_disable"
              :key="dict.value"
              :value="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="remark">
          <label for="form-remark">备注</label>
          <el-input
            id="form-remark"
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Pattern">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Edit, Delete, Download } from '@element-plus/icons-vue'
import { listPattern, getPattern, delPattern, addPattern, updatePattern } from "@/api/tile/pattern"
import { useDict } from '@/utils/dict'

const { sys_normal_disable } = useDict('sys_normal_disable')

// 遮罩层
const loading = ref(true)
// 选中数组
const ids = ref([])
// 非单个禁用
const single = ref(true)
// 非多个禁用
const multiple = ref(true)
// 显示搜索条件
const showSearch = ref(true)
// 总条数
const total = ref(0)
// 图案表格数据
const patternList = ref([])
// 弹出层标题
const title = ref("")
// 是否显示弹出层
const open = ref(false)
// 查询参数
const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  patternName: undefined,
  status: undefined
})
// 表单参数
const form = ref({
  patternId: undefined,
  patternName: '',
  orderNum: 0,
  status: "0",
  remark: ''
})
// 表单校验规则
const rules = ref({
  patternName: [
    { required: true, message: "图案名称不能为空", trigger: "blur" },
    { min: 0, max: 30, message: '图案名称长度不能超过30个字符', trigger: 'blur' }
  ],
  orderNum: [
    { required: true, message: "显示顺序不能为空", trigger: "blur" }
  ]
})

// 表单ref
const formRef = ref()
const queryFormRef = ref()

/** 查询图案列表 */
function getList() {
  loading.value = true
  listPattern(queryParams.value).then(response => {
    patternList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    patternId: undefined,
    patternName: '',
    orderNum: 0,
    status: "0",
    remark: ''
  }
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  queryFormRef.value?.resetFields()
  queryParams.value = {
    pageNum: 1,
    pageSize: 10,
    patternName: undefined,
    status: undefined
  }
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.patternId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加图案"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const patternId = row.patternId || ids.value
  getPattern(patternId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改图案"
  })
}

/** 提交按钮 */
function submitForm() {
  formRef.value.validate(valid => {
    if (valid) {
      ElMessageBox.confirm('是否确认提交数据?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        if (form.value.patternId) {
          updatePattern(form.value).then(response => {
            ElMessage.success("修改成功")
            open.value = false
            getList()
          })
        } else {
          addPattern(form.value).then(response => {
            ElMessage.success("新增成功")
            open.value = false
            getList()
          })
        }
      })
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const patternIds = row.patternId || ids.value
  ElMessageBox.confirm('是否确认删除图案编号为"' + patternIds + '"的数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function() {
    return delPattern(patternIds)
  }).then(() => {
    getList()
    ElMessage.success("删除成功")
  })
}

/** 导出按钮操作 */
function handleExport() {
  ElMessageBox.confirm('是否确认导出所有图案数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function() {
    return exportPattern(queryParams.value)
  }).then(response => {
    download(response.msg)
  })
}

/** 状态修改 */
function handleStatusChange(row) {
  let text = row.status === "0" ? "启用" : "停用"
  ElMessageBox.confirm('确认要"' + text + '""' + row.patternName + '"吗?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function() {
    return updatePattern(row)
  }).then(() => {
    ElMessage.success(text + "成功")
  }).catch(function() {
    row.status = row.status === "0" ? "1" : "0"
  })
}

onMounted(() => {
  getList()
})
</script>

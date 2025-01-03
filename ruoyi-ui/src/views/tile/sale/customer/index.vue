<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" v-show="showSearch">
      <el-form-item label="客户名称" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户类型" prop="customerType">
        <el-select v-model="queryParams.customerType" placeholder="请选择客户类型" clearable>
          <el-option
            v-for="dict in customerTypeOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="联系人" prop="contactName">
        <el-input
          v-model="queryParams.contactName"
          placeholder="请输入联系人姓名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="客户状态" clearable>
          <el-option
            v-for="dict in statusOptions"
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

    <!-- 操作按钮区域 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain :icon="Plus" @click="handleAdd" v-hasPermi="['tile:customer:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain :icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['tile:customer:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain :icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['tile:customer:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain :icon="Download" @click="handleExport" v-hasPermi="['tile:customer:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table
      v-loading="loading"
      :data="customerList"
      @selection-change="handleSelectionChange"
      height="calc(100vh - 240px)"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="客户ID" align="center" prop="customerId" width="120" />
      <el-table-column
        label="客户名称"
        align="center"
        prop="customerName"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="客户类型" align="center" prop="customerType" width="100">
        <template #default="scope">
          <dict-tag :options="customerTypeOptions" :value="scope.row.customerType" />
        </template>
      </el-table-column>
      <el-table-column
        label="联系人"
        align="center"
        prop="contactName"
        width="120"
      />
      <el-table-column
        label="联系电话"
        align="center"
        prop="phone"
        width="120"
      />
      <el-table-column
        label="地址"
        align="center"
        prop="address"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template #default="scope">
          <dict-tag :options="statusOptions" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            link
            type="primary"
            :icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tile:customer:edit']"
          >修改</el-button>
          <el-button
            link
            type="primary"
            :icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tile:customer:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total > 0"
      v-model:total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改客户对话框 -->
    <el-dialog
      :title="title"
      v-model="open"
      width="500px"
      append-to-body
      @close="cancel"
    >
      <el-form
        ref="customerFormRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入客户名称" />
        </el-form-item>
        <el-form-item label="客户类型" prop="customerType">
          <el-select v-model="form.customerType" placeholder="请选择客户类型">
            <el-option
              v-for="dict in customerTypeOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="联系人" prop="contactName">
          <el-input v-model="form.contactName" placeholder="请输入联系人姓名" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input
            v-model="form.address"
            type="textarea"
            placeholder="请输入地址"
            :autosize="{ minRows: 2, maxRows: 4 }"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.value"
              :value="dict.value"
            >{{ dict.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入备注"
            :autosize="{ minRows: 2, maxRows: 4 }"
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

<script setup name="Customer">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Plus, Edit, Delete, Download, Refresh } from '@element-plus/icons-vue'
import { 
  listCustomer,
  getCustomer,
  addCustomer,
  updateCustomer,
  delCustomer
} from '@/api/tile/customer'
import { parseTime } from '@/utils/ruoyi'

// 遮罩层
const loading = ref(false)
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
// 客户表格数据
const customerList = ref([])
// 弹出层标题
const title = ref('')
// 是否显示弹出层
const open = ref(false)

// 客户类型选项
const customerTypeOptions = ref([
  { value: '1', label: '零售客户' },
  { value: '2', label: '批发客户' }
])

// 状态数据字典
const statusOptions = ref([
  { value: '0', label: '正常' },
  { value: '1', label: '停用' }
])

// 查询参数
const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  customerName: undefined,
  customerType: undefined,
  contactName: undefined,
  phone: undefined,
  status: undefined
})

// 表单参数
const form = ref({})

// 表单校验
const rules = {
  customerName: [
    { required: true, message: '客户名称不能为空', trigger: 'blur' }
  ],
  customerType: [
    { required: true, message: '客户类型不能为空', trigger: 'change' }
  ],
  contactName: [
    { required: true, message: '联系人不能为空', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '联系电话不能为空', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '状态不能为空', trigger: 'change' }
  ]
}

// 表单引用
const customerFormRef = ref()
const queryFormRef = ref()

/** 查询客户列表 */
const getList = async () => {
  loading.value = true
  try {
    const res = await listCustomer(queryParams.value)
    customerList.value = res.rows
    total.value = res.total
  } catch (error) {
    ElMessage.error('获取客户列表失败')
  } finally {
    loading.value = false
  }
}

/** 取消按钮 */
const cancel = () => {
  open.value = false
  reset()
}

/** 表单重置 */
const reset = () => {
  form.value = {
    customerId: undefined,
    customerName: undefined,
    customerType: undefined,
    contactName: undefined,
    phone: undefined,
    address: undefined,
    status: '0',
    remark: undefined
  }
  customerFormRef.value?.resetFields()
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields()
  handleQuery()
}

/** 多选框选中数据 */
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.customerId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset()
  title.value = '添加客户'
  open.value = true
}

/** 修改按钮操作 */
const handleUpdate = async (row) => {
  reset()
  const customerId = row?.customerId || ids.value[0]
  try {
    const res = await getCustomer(customerId)
    form.value = res.data
    title.value = '修改客户'
    open.value = true
  } catch (error) {
    ElMessage.error('获取客户信息失败')
  }
}

/** 提交按钮 */
const submitForm = async () => {
  try {
    await customerFormRef.value?.validate()
    
    if (form.value.customerId) {
      await updateCustomer(form.value)
      ElMessage.success('修改成功')
    } else {
      await addCustomer(form.value)
      ElMessage.success('新增成功')
    }
    open.value = false
    getList()
  } catch (error) {
    ElMessage.error(error.message || '操作失败')
  }
}

/** 删除按钮操作 */
const handleDelete = async (row) => {
  const customerIds = row?.customerId || ids.value
  
  try {
    await ElMessageBox.confirm('是否确认删除选中的客户数据项？')
    await delCustomer(customerIds)
    getList()
    ElMessage.success('删除成功')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

/** 导出按钮操作 */
const handleExport = () => {
  ElMessageBox.confirm('是否确认导出所有客户数据项？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    import('@/plugins/download').then(module => {
      module.default.download('/tile/customer/export', {
        ...queryParams.value
      }, `customer_${new Date().getTime()}.xlsx`)
    })
  })
}

onMounted(() => {
  getList()
})
</script>

<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
.dialog-footer {
  padding: 20px;
  text-align: right;
}
</style>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item>
        <label id="categoryNameLabel">分类名称</label>
        <el-input
          id="categoryName"
          v-model="queryParams.categoryName"
          placeholder="请输入分类名称"
          clearable
          @keyup.enter="handleQuery"
          aria-labelledby="categoryNameLabel"
        />
      </el-form-item>
      <el-form-item>
        <label id="visibleSelectLabel">显示状态</label>
        <el-select
          id="visibleSelect"
          v-model="queryParams.visible"
          placeholder="显示状态"
          clearable
          aria-labelledby="visibleSelectLabel"
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
          v-hasPermi="['tile:category:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="categoryList"
      row-key="categoryId"
      :default-expand-all="true"
      :tree-props="{
        children: 'children',
        hasChildren: 'hasChildren',
        lazy: false,
        load: null
      }"
      :height="500"
      :max-height="500"
    >
      <el-table-column prop="categoryName" label="分类名称" width="260"></el-table-column>
      <el-table-column prop="orderNum" label="显示顺序" width="200"></el-table-column>
      <el-table-column prop="visible" label="显示状态" width="100">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.visible"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="200">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            link
            :icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tile:category:edit']"
          >修改</el-button>
          <el-button
            link
            :icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tile:category:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改瓷砖分类对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item prop="parentId">
          <label id="parentCategoryLabel">上级分类</label>
          <el-tree-select
            id="parentCategory"
            v-model="form.parentId"
            :data="categoryOptions"
            :props="defaultProps"
            placeholder="选择上级分类"
            check-strictly
            :filter-node-method="filterNode"
            aria-labelledby="parentCategoryLabel"
          />
        </el-form-item>
        <el-form-item prop="categoryName">
          <label id="formCategoryNameLabel">分类名称</label>
          <el-input
            id="formCategoryName"
            v-model="form.categoryName"
            placeholder="请输入分类名称"
            aria-labelledby="formCategoryNameLabel"
          />
        </el-form-item>
        <el-form-item prop="orderNum">
          <label id="orderNumLabel">显示顺序</label>
          <el-input-number
            id="orderNum"
            v-model="form.orderNum"
            controls-position="right"
            :min="0"
            aria-labelledby="orderNumLabel"
          />
        </el-form-item>
        <el-form-item prop="visible">
          <label id="visibleRadioLabel">显示状态</label>
          <el-radio-group
            id="visibleRadio"
            v-model="form.visible"
            aria-labelledby="visibleRadioLabel"
          >
            <el-radio
              v-for="dict in sys_normal_disable"
              :key="dict.value"
              :value="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
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

<script setup name="Category">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Edit, Delete } from '@element-plus/icons-vue'
import { listCategory, getCategory, delCategory, addCategory, updateCategory } from "@/api/tile/category"
import { useDict } from '@/utils/dict'
import { parseTime, handleTree } from '@/utils/ruoyi'

const { sys_normal_disable } = useDict('sys_normal_disable')

// 遮罩层
const loading = ref(true)
// 显示搜索条件
const showSearch = ref(true)
// 瓷砖分类表格数据
const categoryList = ref([])
// 瓷砖分类树选项
const categoryOptions = ref([])
// 弹出层标题
const title = ref("")
// 是否显示弹出层
const open = ref(false)
// 查询参数
const queryParams = ref({
  categoryName: undefined,
  visible: undefined,
  pageNum: 1,
  pageSize: 1000
})
// 表单参数
const form = ref({})
// 表单校验
const rules = ref({
  parentId: [
    { required: true, message: "上级分类不能为空", trigger: "blur" }
  ],
  categoryName: [
    { required: true, message: "分类名称不能为空", trigger: "blur" }
  ],
  orderNum: [
    { required: true, message: "显示顺序不能为空", trigger: "blur" }
  ],
  visible: [
    { required: true, message: "显示状态不能为空", trigger: "blur" }
  ]
})

// 树形配置选项
const defaultProps = {
  label: 'categoryName',
  value: 'categoryId',
  children: 'children'
}

// 过滤节点方法
function filterNode(value, data) {
  // 获取节点的层级
  const nodeLevel = data.level || 1
  // 如果是新增操作
  if (!form.value.categoryId) {
    // 只允许选择二级及以下的分类作为父分类（因为最大是三级）
    return nodeLevel < 3
  }
  // 如果是编辑操作
  else {
    // 不能选择自己及其子分类作为父分类
    const currentId = form.value.categoryId
    if (data.categoryId === currentId) {
      return false
    }
    // 检查是否是当前节点的子节点
    if (data.ancestors && data.ancestors.split(',').includes(currentId.toString())) {
      return false
    }
    // 同样限制最大三级
    return nodeLevel < 3
  }
}

// 表单ref
const formRef = ref()
const queryFormRef = ref()

/** 查询瓷砖分类列表 */
function getList() {
  loading.value = true
  listCategory(queryParams.value).then(response => {
    // 按层级和父ID分组处理数据
    const rows = response.rows;
    const levelMap = new Map();
    
    // 按层级分组
    rows.forEach(item => {
      if (!levelMap.has(item.level)) {
        levelMap.set(item.level, []);
      }
      levelMap.get(item.level).push(item);
    });

    // 从底层开始构建树
    const levels = Array.from(levelMap.keys()).sort((a, b) => b - a);
    
    // 处理每个层级的节点
    levels.forEach(level => {
      const currentLevelNodes = levelMap.get(level);
      currentLevelNodes.sort((a, b) => {
        if (a.parentId !== b.parentId) {
          return a.parentId - b.parentId;
        }
        return a.orderNum - b.orderNum;
      });
    });

    // 构建树形结构
    const processedData = handleTree(rows, "categoryId", "parentId");
    categoryList.value = processedData;
    loading.value = false;
  });
}

/** 搜索按钮操作 */
function handleQuery() {
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  queryFormRef.value?.resetFields()
  handleQuery()
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加瓷砖分类"
  listCategory().then(response => {
    const data = response.rows
    // 如果没有任何分类，parentId 默认为 0（作为顶级分类）
    if (!data || data.length === 0) {
      form.value.parentId = 0
      categoryOptions.value = []
    } else {
      categoryOptions.value = handleTree(data, "categoryId", "parentId")
    }
  })
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  getCategory(row.categoryId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改瓷砖分类"
    listCategory().then(response => {
      categoryOptions.value = handleTree(response.rows, "categoryId", "parentId")
    })
  })
}

/** 提交按钮 */
function submitForm() {
  formRef.value?.validate((valid) => {
    if (valid) {
      // 设置层级
      if (form.value.parentId === 0 || form.value.parentId === undefined) {
        form.value.level = 1
        form.value.parentId = 0
      } else {
        // 查找父节点的层级
        const findParentLevel = (nodes, parentId) => {
          for (const node of nodes) {
            if (node.categoryId === parentId) {
              return node.level
            }
            if (node.children) {
              const level = findParentLevel(node.children, parentId)
              if (level) return level
            }
          }
          return null
        }
        
        const parentLevel = findParentLevel(categoryOptions.value, form.value.parentId)
        form.value.level = (parentLevel || 0) + 1
      }
      
      if (form.value.categoryId != undefined) {
        updateCategory(form.value).then(response => {
          ElMessage.success("修改成功")
          open.value = false
          getList()
        })
      } else {
        addCategory(form.value).then(response => {
          ElMessage.success("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  ElMessageBox.confirm(
    `是否确认删除名称为"${row.categoryName}"的数据项?`,
    "警告",
    {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  ).then(() => {
    return delCategory(row.categoryId)
  }).then(() => {
    getList()
    ElMessage.success("删除成功")
  }).catch(() => {})
}

/** 表单重置 */
function reset() {
  form.value = {
    categoryId: undefined,
    parentId: undefined,
    categoryName: undefined,
    orderNum: 0,
    visible: "0"
  }
  formRef.value?.resetFields()
}

/** 取消按钮 */
function cancel() {
  open.value = false
  reset()
}

onMounted(() => {
  getList()
})
</script>

<style scoped>
.el-form-item label {
  display: block;
  margin-bottom: 8px;
  color: var(--el-text-color-regular);
}
</style>

<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item prop="goodsCode">
        <label for="query-goods-code">商品编号</label>
        <el-input
          id="query-goods-code"
          v-model="queryParams.goodsCode"
          placeholder="请输入商品编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="goodsName">
        <label for="query-goods-name">商品名称</label>
        <el-input
          id="query-goods-name"
          v-model="queryParams.goodsName"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="categoryId">
        <label for="query-category">商品分类</label>
        <el-cascader
          id="query-category"
          v-model="queryParams.categoryId"
          :options="categoryOptions"
          :props="{ 
            checkStrictly: false,
            value: 'categoryId',
            label: 'categoryName',
            children: 'children',
            expandTrigger: 'hover',
            emitPath: false
          }"
          :show-all-levels="true"
          placeholder="请选择商品分类"
          clearable
        />
      </el-form-item>
      <el-form-item prop="status">
        <label for="query-status">状态</label>
        <el-select
          id="query-status"
          v-model="queryParams.status"
          placeholder="商品状态"
          clearable
        >
          <el-option label="上架" value="0" />
          <el-option label="下架" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="Search" @click="handleQuery">搜索</el-button>
        <el-button :icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          :icon="Plus"
          @click="handleAdd"
          v-hasPermi="['tile:goods:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          :icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['tile:goods:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          :icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['tile:goods:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          :icon="Download"
          @click="handleExport"
          v-hasPermi="['tile:goods:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="goodsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商品ID" align="center" prop="goodsId" width="80" />
      <el-table-column label="商品主图" align="center" width="120">
        <template #default="scope">
          <el-image
            style="width: 80px; height: 80px"
            :src="scope.row.goodsImage"
            :preview-src-list="[scope.row.goodsImage]"
            fit="cover"
          />
        </template>
      </el-table-column>
      <el-table-column label="商品编号" align="center" prop="goodsCode" min-width="120" />
      <el-table-column label="商品分类" align="center" prop="categoryName" min-width="120" />
      <el-table-column label="商品规格" align="center" prop="specName" min-width="120" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="状态" align="center" width="100">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="'0'"
            :inactive-value="'1'"
            @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            type="primary"
            link
            :icon="View"
            @click="handleView(scope.row)"
            v-hasPermi="['tile:goods:query']"
          >详情</el-button>
          <el-button
            type="primary"
            link
            :icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tile:goods:edit']"
          >修改</el-button>
          <el-button
            type="primary"
            link
            :icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tile:goods:remove']"
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
    <goods-dialog
      ref="goodsDialog"
      v-model="dialog.visible"
      :title="dialog.title"
      :category-options="categoryOptions"
      :spec-options="specOptions"
      :material-options="materialOptions"
      :surface-options="surfaceOptions"
      :usage-options="usageOptions"
      :pattern-options="patternOptions"
      @change="handleDialogChange"
      @reset="handleDialogReset"
    />
    <goods-detail
      ref="goodsDetail"
      v-model="detailDialog.visible"
      :title="detailDialog.title"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, getCurrentInstance, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Edit, Delete, Search, Refresh, Download, View } from '@element-plus/icons-vue'
import { 
  listGoods, 
  getGoods, 
  addGoods, 
  updateGoods, 
  delGoods, 
  exportGoods, 
  updateGoodsStatus 
} from '@/api/tile/goods'
import { listCategory } from '@/api/tile/category'
import { listSpec } from '@/api/tile/spec'
import { listMaterial } from '@/api/tile/material'
import { listSurface } from '@/api/tile/surface'
import { listUsage } from '@/api/tile/usage'
import { listPattern } from '@/api/tile/pattern'
import GoodsDialog from './dialog.vue'
import GoodsDetail from './detail.vue'

const { proxy } = getCurrentInstance()
const goodsDialog = ref()
const goodsDetail = ref()
const loading = ref(false)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const goodsList = ref([])

// 选项数据
const categoryOptions = ref([])
const specOptions = ref([])
const materialOptions = ref([])
const surfaceOptions = ref([])
const usageOptions = ref([])
const patternOptions = ref([])

// 弹窗控制
const dialog = reactive({
  visible: false,
  title: ''
})
const detailDialog = reactive({
  visible: false,
  title: ''
})

// 查询参数
const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  goodsCode: undefined,
  goodsName: undefined,
  categoryId: undefined,
  status: undefined
})

/** 查询商品列表 */
const getList = async () => {
  try {
    loading.value = true
    const res = await listGoods(queryParams.value)
    goodsList.value = res.rows
    total.value = res.total
  } catch (error) {
    console.error('获取商品列表失败:', error)
  } finally {
    loading.value = false
  }
}

/** 查询分类树 */
const getCategoryTree = async () => {
  try {
    const res = await listCategory({
      pageSize: 999,
      status: '0'  // 只获取正常状态的分类
    })
    
    // 将扁平的分类列表转换为树形结构
    const buildTree = (items, parentId = 0) => {
      const result = []
      for (const item of items) {
        if (item.parentId === parentId) {
          const children = buildTree(items, item.categoryId)
          if (children.length) {
            item.children = children
          }
          result.push(item)
        }
      }
      return result
    }
    
    categoryOptions.value = buildTree(res.rows)
  } catch (error) {
    console.error('获取分类树失败:', error)
  }
}

/** 查询所有选项数据 */
const getAllOptions = async () => {
  try {
    const [specRes, materialRes, surfaceRes, usageRes, patternRes] = await Promise.all([
      listSpec({
        pageSize: 999,
        status: '0'  // 只获取正常状态的规格
      }),
      listMaterial({
        pageSize: 999,
        status: '0'  // 只获取正常状态的材质
      }),
      listSurface({
        pageSize: 999,
        status: '0'  // 只获取正常状态的表面处理
      }),
      listUsage({
        pageSize: 999,
        status: '0'  // 只获取正常状态的用途
      }),
      listPattern({
        pageSize: 999,
        status: '0'  // 只获取正常状态的图案
      })
    ])
    specOptions.value = specRes.rows
    materialOptions.value = materialRes.rows
    surfaceOptions.value = surfaceRes.rows
    usageOptions.value = usageRes.rows
    patternOptions.value = patternRes.rows
  } catch (error) {
    console.error('获取选项数据失败:', error)
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryParams.value = {
    pageNum: 1,
    pageSize: 10,
    goodsCode: undefined,
    goodsName: undefined,
    categoryId: undefined,
    status: undefined
  }
  getList()
}

/** 多选框选中数据 */
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.goodsId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
const handleAdd = () => {
  dialog.title = "添加商品"
  dialog.visible = true
}

/** 修改按钮操作 */
const handleUpdate = async (row) => {
  const goodsId = row.goodsId || ids.value[0]
  try {
    const response = await getGoods(goodsId)
    dialog.title = "修改商品"
    dialog.visible = true
    nextTick(() => {
      goodsDialog.value.form = response.data
    })
  } catch (error) {
    console.error('获取商品详情失败:', error)
    proxy.$modal.msgError("获取商品详情失败")
  }
}

/** 表单提交 */
const handleDialogChange = async (formData) => {
  try {
    if (formData.goodsId) {
      await updateGoods(formData)
      proxy.$modal.msgSuccess("修改成功")
    } else {
      await addGoods(formData)
      proxy.$modal.msgSuccess("新增成功")
    }
    dialog.visible = false
    getList()
  } catch (error) {
    console.error('操作失败:', error)
    proxy.$modal.msgError("操作失败")
  }
}

/** 表单重置回调 */
const handleDialogReset = () => {
  dialog.visible = false
}

/** 删除按钮操作 */
const handleDelete = (row) => {
  const goodsIds = row.goodsId || ids.value
  proxy.$modal.confirm('是否确认删除选中的数据项?').then(async () => {
    try {
      await delGoods(goodsIds)
      getList()
      proxy.$modal.msgSuccess("删除成功")
    } catch (error) {
      console.error('删除失败:', error)
      proxy.$modal.msgError("删除失败")
    }
  })
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy.download('tile/goods/export', {
    ...queryParams.value
  }, `goods_${new Date().getTime()}.xlsx`)
}

/** 查看按钮操作 */
const handleView = async (row) => {
  detailDialog.visible = true
  detailDialog.title = "查看商品详情"
  const { data } = await getGoods(row.goodsId)
  goodsDetail.value.form = { ...data }
}

/** 状态修改 */
const handleStatusChange = async (row) => {
  const text = row.status === "0" ? "上架" : "下架"
  try {
    await ElMessageBox.confirm(
      `确认要${text}商品"${row.goodsCode}"吗？`,
      "提示",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }
    )
    await updateGoodsStatus(row.goodsId, row.status)
    ElMessage.success(`${text}成功`)
  } catch (error) {
    row.status = row.status === "0" ? "1" : "0"
    console.error("Error updating status:", error)
  }
}

onMounted(() => {
  getList()
  getCategoryTree()
  getAllOptions()
})
</script>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="商品名称" prop="goodsName">
        <el-input
          v-model="queryParams.goodsName"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品编号" prop="goodsCode">
        <el-input
          v-model="queryParams.goodsCode"
          placeholder="请输入商品编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库名称" prop="warehouseName">
        <el-input
          v-model="queryParams.warehouseName"
          placeholder="请输入仓库名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="库存状态" clearable>
          <el-option
            v-for="dict in sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleStockIn"
          v-hasPermi="['tile:stock:in']"
        >入库</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Minus"
          @click="handleStockOut"
          v-hasPermi="['tile:stock:out']"
        >出库</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['tile:stock:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="库存ID" align="center" prop="stockId" />
      <el-table-column label="商品名称" align="center" prop="goodsName" />
      <el-table-column label="商品编号" align="center" prop="goodsCode" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" />
      <el-table-column label="库存数量" align="center" prop="quantity" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['tile:stock:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['tile:stock:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改库存管理对话框 -->
    <StockDialog ref="stockDialog" @refresh="getList" />
  </div>
</template>

<script setup name="Stock">
import { ref, getCurrentInstance } from 'vue';
import { useRouter } from 'vue-router';
import { listStock, delStock, exportStock } from "@/api/tile/stock";
import StockDialog from "./dialog.vue";

const router = useRouter();
const { proxy } = getCurrentInstance();
const { sys_normal_disable } = proxy.useDict("sys_normal_disable");

const stockList = ref([]);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  goodsName: null,
  goodsCode: null,
  warehouseName: null,
  status: null
});

/** 查询库存管理列表 */
function getList() {
  loading.value = true;
  listStock(queryParams.value).then(response => {
    stockList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 取消按钮 */
function cancel() {
  reset();
}

/** 表单重置 */
function reset() {
  proxy.resetForm("stockDialog");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

/** 修改按钮操作 */
function handleUpdate(row) {
  proxy.$refs["stockDialog"].handleUpdate(row);
}

/** 删除按钮操作 */
function handleDelete(row) {
  const stockIds = row.stockId || ids.value;
  proxy.$modal.confirm('是否确认删除库存管理编号为"' + stockIds + '"的数据项？').then(function() {
    return delStock(stockIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.$modal.confirm('是否确认导出所有库存管理数据项？').then(() => {
    proxy.$modal.loading("正在导出数据，请稍候...");
    return exportStock(queryParams.value);
  }).then(response => {
    proxy.download(response.msg);
    proxy.$modal.closeLoading();
  }).catch(() => {});
}

/** 入库按钮操作 */
function handleStockIn() {
  proxy.$router.push('/stock/stock/in');
}

/** 出库按钮操作 */
function handleStockOut() {
  proxy.$router.push('/stock/stock/out');
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.stockId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

getList();
</script>

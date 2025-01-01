<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="操作类型" prop="operType">
        <el-select v-model="queryParams.operType" placeholder="操作类型" clearable style="width: 200px">
          <el-option
            v-for="dict in tile_stock_oper_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="商品" prop="goodsId">
        <goods-select v-model="queryParams.goodsId" />
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseId">
        <warehouse-select v-model="queryParams.warehouseId" />
      </el-form-item>
      <el-form-item label="单据类型" prop="sourceType">
        <el-select v-model="queryParams.sourceType" placeholder="单据类型" clearable style="width: 200px">
          <el-option
            v-for="dict in tile_stock_source_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="单据编号" prop="sourceCode">
        <el-input
          v-model="queryParams.sourceCode"
          placeholder="请输入单据编号"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作时间">
        <el-date-picker
          v-model="dateRange"
          value-format="YYYY-MM-DD"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['tile:stock:record:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['tile:stock:record:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          @click="handleClean"
          v-hasPermi="['tile:stock:record:remove']"
        >清空</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="操作类型" align="center" prop="operType">
        <template #default="scope">
          <dict-tag :options="tile_stock_oper_type" :value="scope.row.operType"/>
        </template>
      </el-table-column>
      <el-table-column label="商品" align="center" prop="goodsName" />
      <el-table-column label="仓库" align="center" prop="warehouseName" />
      <el-table-column label="数量" align="center" prop="quantity" />
      <el-table-column label="单据类型" align="center" prop="sourceType">
        <template #default="scope">
          <dict-tag :options="tile_stock_source_type" :value="scope.row.sourceType"/>
        </template>
      </el-table-column>
      <el-table-column label="单据编号" align="center" prop="sourceCode" />
      <el-table-column label="操作时间" align="center" prop="operTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.operTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作人员" align="center" prop="operBy" />
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            type="text"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tile:stock:record:remove']"
          >删除</el-button>
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
  </div>
</template>

<script setup name="StockRecord">
import { listStockRecord, delStockRecord, exportStockRecord, cleanStockRecord } from "@/api/tile/stock";
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';

const { proxy } = getCurrentInstance();

// 遮罩层
const loading = ref(false);
// 选中数组
const ids = ref([]);
// 非多个禁用
const multiple = ref(true);
// 显示搜索条件
const showSearch = ref(true);
// 总条数
const total = ref(0);
// 库存记录表格数据
const recordList = ref([]);
// 日期范围
const dateRange = ref([]);

// 查询参数
const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  operType: undefined,
  goodsId: undefined,
  warehouseId: undefined,
  sourceType: undefined,
  sourceCode: undefined
});

/** 查询库存记录列表 */
function getList() {
  loading.value = true;
  listStockRecord(proxy.addDateRange(queryParams.value, dateRange.value)).then(response => {
    recordList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  dateRange.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.recordId);
  multiple.value = !selection.length;
}

/** 删除按钮操作 */
function handleDelete(row) {
  const recordIds = row.recordId || ids.value;
  ElMessageBox.confirm('是否确认删除库存记录？', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {
    delStockRecord(recordIds).then(() => {
      getList();
      ElMessage.success("删除成功");
    });
  });
}

/** 清空按钮操作 */
function handleClean() {
  ElMessageBox.confirm('是否确认清空所有库存记录？', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {
    cleanStockRecord().then(() => {
      getList();
      ElMessage.success("清空成功");
    });
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('tile/stock/record/export', {
    ...queryParams.value,
    ...proxy.addDateRange(queryParams.value, dateRange.value)
  }, `库存记录_${new Date().getTime()}.xlsx`);
}

onMounted(() => {
  getList();
});
</script>

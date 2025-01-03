<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="操作类型" prop="operType">
        <el-select v-model="queryParams.operType" id="operType" placeholder="操作类型" clearable style="width: 200px">
          <el-option
            v-for="dict in tile_stock_oper_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="商品" prop="goodsId">
        <el-select v-model="queryParams.goodsId" id="goodsId" placeholder="商品" clearable style="width: 200px">
          <el-option
            v-for="item in goodsOptions"
            :key="item.goodsId"
            :label="item.goodsName"
            :value="item.goodsId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" id="warehouseId" placeholder="仓库" clearable style="width: 200px" @change="handleWarehouseChange">
          <el-option
            v-for="item in warehouseOptions"
            :key="item.warehouseId"
            :label="item.warehouseName"
            :value="item.warehouseId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="货位" prop="locationId" v-if="locationOptions.length > 0">
        <el-select v-model="queryParams.locationId" id="locationId" placeholder="货位" clearable style="width: 200px">
          <el-option
            v-for="item in locationOptions"
            :key="item.locationId"
            :label="item.locationName"
            :value="item.locationId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="批次号" prop="batchNo">
        <el-input
          v-model="queryParams.batchNo"
          id="batchNo"
          placeholder="请输入批次号"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单据类型" prop="sourceType">
        <el-select v-model="queryParams.sourceType" id="sourceType" placeholder="单据类型" clearable style="width: 200px">
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
          id="sourceCode"
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
          :disabled="single"
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

    <el-table v-loading="loading" :data="stockRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="操作类型" align="center" prop="operType">
        <template #default="scope">
          {{ getDictLabel(tile_stock_oper_type, scope.row.operType) }}
        </template>
      </el-table-column>
      <el-table-column label="商品" align="center" prop="goodsName" />
      <el-table-column label="仓库" align="center" prop="warehouseName" />
      <el-table-column label="货位" align="center" prop="locationName" />
      <el-table-column label="批次号" align="center" prop="batchNo" />
      <el-table-column label="数量" align="center">
        <template #default="scope">
          <span :style="{ color: scope.row.operType === '1' ? '#67C23A' : '#F56C6C' }">
            {{ scope.row.operType === '1' ? '+' : '-' }}{{ scope.row.quantity }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="单据类型" align="center" prop="sourceType">
        <template #default="scope">
          {{ getDictLabel(tile_stock_source_type, scope.row.sourceType) }}
        </template>
      </el-table-column>
      <el-table-column label="单据编号" align="center" prop="sourceCode" />
      <el-table-column label="来源单据" align="center">
        <template #default="scope">
          <el-button
            type="text"
            @click="handleViewSource(scope.row)"
          >{{ scope.row.sourceCode }}</el-button>
        </template>
      </el-table-column>
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
import { listStockRecord, delStockRecord, cleanStockRecord, exportStockRecord } from "@/api/tile/stock";
import { listWarehouse } from "@/api/tile/warehouse";
import { listLocation } from "@/api/tile/stock";
import { listGoods } from "@/api/tile/goods";
import { getCurrentInstance, ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { useDict } from '@/utils/dict'
import { parseTime, addDateRange } from '@/utils/ruoyi'

const { proxy } = getCurrentInstance();
const { tile_stock_oper_type, tile_stock_source_type } = useDict('tile_stock_oper_type', 'tile_stock_source_type')

// 遮罩层
const loading = ref(false);
// 选中数组
const ids = ref([]);
// 非单个禁用
const single = ref(true);
// 非多个禁用
const multiple = ref(true);
// 显示搜索条件
const showSearch = ref(true);
// 总条数
const total = ref(0);
// 库存记录表格数据
const stockRecordList = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
// 日期范围
const dateRange = ref([]);
// 仓库选项列表
const warehouseOptions = ref([]);
// 商品选项列表
const goodsOptions = ref([]);
// 货位选项列表
const locationOptions = ref([]);

// 查询参数
const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  warehouseId: null,
  goodsId: null,
  operType: null,
  sourceType: null,
  sourceCode: null,
  batchNo: null,
  locationId: null
});

/** 查询库存记录列表 */
function getList() {
  loading.value = true;
  listStockRecord(addDateRange(queryParams.value, dateRange.value)).then(response => {
    stockRecordList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 查询仓库列表 */
function getWarehouseList() {
  listWarehouse().then(response => {
    warehouseOptions.value = response.rows;
  });
}

/** 查询商品列表 */
function getGoodsList() {
  listGoods().then(response => {
    goodsOptions.value = response.rows;
  });
}

/** 仓库选择改变事件 */
function handleWarehouseChange(value) {
  queryParams.value.locationId = undefined;
  if (value) {
    listLocation({
      warehouseId: value
    }).then(response => {
      locationOptions.value = response.rows;
    });
  } else {
    locationOptions.value = [];
  }
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.recordId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
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
  queryParams.value.locationId = undefined;
  locationOptions.value = [];
  handleQuery();
}

/** 删除按钮操作 */
function handleDelete(row) {
  const recordIds = row.recordId || ids.value;
  ElMessageBox.confirm('是否确认删除库存记录编号为"' + recordIds + '"的数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function() {
    return delStockRecord(recordIds);
  }).then(() => {
    getList();
    ElMessage.success("删除成功");
  }).catch(() => {});
}

/** 清空按钮操作 */
function handleClean() {
  ElMessageBox.confirm('是否确认清空所有库存记录?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function() {
    return cleanStockRecord();
  }).then(() => {
    getList();
    ElMessage.success("清空成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  const queryParams = proxy.addDateRange(queryParams.value, dateRange.value);
  ElMessageBox.confirm('是否确认导出所有库存记录数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function() {
    return exportStockRecord(queryParams);
  }).then(response => {
    proxy.download(response.msg);
  }).catch(() => {});
}

/** 获取字典数据 */
function getDictLabel(dicts, value) {
  if (!dicts || !value) return '';
  const dict = dicts.find(dict => dict.value === value);
  return dict ? dict.label : '';
}

/** 查看来源单据 */
function handleViewSource(row) {
  const sourceType = row.sourceType;
  const sourceId = row.sourceId;
  
  // 根据单据类型跳转到不同的页面
  if (sourceType === "IN") {
    proxy.$router.push({ path: '/tile/stock/in/detail', query: { inId: sourceId }});
  } else if (sourceType === "OUT") {
    proxy.$router.push({ path: '/tile/stock/out/detail', query: { outId: sourceId }});
  }
}

onMounted(() => {
  getWarehouseList();
  getGoodsList();
  getList();
});
</script>

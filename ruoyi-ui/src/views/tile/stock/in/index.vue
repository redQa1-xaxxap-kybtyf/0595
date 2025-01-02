<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="入库单号" prop="inCode">
        <el-input
          v-model="queryParams.inCode"
          placeholder="请输入入库单号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" placeholder="请选择仓库" clearable>
          <el-option
            v-for="item in warehouseOptions"
            :key="item.warehouseId"
            :label="item.warehouseName"
            :value="item.warehouseId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="入库单状态" clearable>
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
          @click="handleAdd"
          v-hasPermi="['tile:stock:in:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['tile:stock:in:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['tile:stock:in:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['tile:stock:in:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockInList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="入库单号" align="center" prop="inCode" />
      <el-table-column label="仓库" align="center" prop="warehouseName" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            type="text"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tile:stock:in:edit']"
            v-if="scope.row.status === '1'"
          >修改</el-button>
          <el-button
            type="text"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tile:stock:in:remove']"
            v-if="scope.row.status === '1'"
          >删除</el-button>
          <el-button
            type="text"
            icon="View"
            @click="handleDetail(scope.row)"
            v-hasPermi="['tile:stock:in:query']"
          >查看</el-button>
          <el-button
            type="text"
            icon="Check"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['tile:stock:in:edit']"
            v-if="scope.row.status === '1'"
          >提交</el-button>
          <el-button
            type="text"
            icon="Close"
            @click="handleCancel(scope.row)"
            v-hasPermi="['tile:stock:in:edit']"
            v-if="scope.row.status === '1'"
          >取消</el-button>
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

    <!-- 添加或修改入库单对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="stockInRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" placeholder="请选择仓库" @change="handleWarehouseChange">
            <el-option
              v-for="item in warehouseOptions"
              :key="item.warehouseId"
              :label="item.warehouseName"
              :value="item.warehouseId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="form.supplierId" placeholder="请选择供应商">
            <el-option
              v-for="item in supplierOptions"
              :key="item.supplierId"
              :label="item.supplierName"
              :value="item.supplierId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="货位" prop="locationId" v-if="locationOptions.length > 0">
          <el-select v-model="form.locationId" placeholder="请选择货位">
            <el-option
              v-for="item in locationOptions"
              :key="item.locationId"
              :label="item.locationName"
              :value="item.locationId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">入库明细</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddDetail">添加明细</el-button>
          </el-col>
        </el-row>
        <el-table :data="form.details" :border="true">
          <el-table-column type="index" label="序号" width="50" />
          <el-table-column label="商品" prop="goodsId">
            <template #default="scope">
              <el-select v-model="scope.row.goodsId" placeholder="请选择商品">
                <el-option
                  v-for="item in goodsOptions"
                  :key="item.goodsId"
                  :label="item.goodsName"
                  :value="item.goodsId"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="数量" prop="quantity">
            <template #default="scope">
              <el-input-number v-model="scope.row.quantity" :min="1" controls-position="right" />
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template #default="scope">
              <el-button type="text" icon="Delete" @click="handleDeleteDetail(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
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

<script setup name="TileStockIn">
import { listStockIn, getStockIn, delStockIn, addStockIn, updateStockIn, submitStockIn, cancelStockIn } from "@/api/tile/stock";
import { listWarehouse } from "@/api/tile/warehouse";
import { listGoods } from "@/api/tile/goods";
import { listLocationByWarehouseId } from "@/api/tile/location";
import { listSupplier } from "@/api/tile/supplier";

const { proxy } = getCurrentInstance();
const { sys_normal_disable } = proxy.useDict("sys_normal_disable");

const stockInList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dateRange = ref([]);
const warehouseOptions = ref([]);
const goodsOptions = ref([]);
const locationOptions = ref([]);
const supplierOptions = ref([]);

const data = reactive({
  form: {
    inId: undefined,
    inCode: undefined,
    warehouseId: undefined,
    locationId: undefined,
    supplierId: undefined,
    status: "1",
    remark: undefined,
    details: []
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    inCode: undefined,
    warehouseId: undefined,
    status: undefined
  },
  rules: {
    warehouseId: [
      { required: true, message: "仓库不能为空", trigger: "blur" }
    ],
    supplierId: [
      { required: true, message: "供应商不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询入库单列表 */
function getList() {
  loading.value = true;
  listStockIn(queryParams.value).then(response => {
    stockInList.value = response.rows;
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

/** 查询供应商列表 */
function getSupplierList() {
  listSupplier().then(response => {
    supplierOptions.value = response.rows;
  });
}

/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}

/** 表单重置 */
function reset() {
  form.value = {
    inId: undefined,
    inCode: undefined,
    warehouseId: undefined,
    locationId: undefined,
    supplierId: undefined,
    status: "1",
    remark: undefined,
    details: []
  };
  locationOptions.value = [];
  proxy.resetForm("stockInRef");
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

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.inId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加入库单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const inId = row.inId || ids.value[0];
  getStockIn(inId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改入库单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["stockInRef"].validate(valid => {
    if (valid) {
      if (form.value.inId != null) {
        updateStockIn(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addStockIn(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const inIds = row.inId || ids.value;
  proxy.$modal.confirm('是否确认删除入库单编号为"' + inIds + '"的数据项？').then(function() {
    return delStockIn(inIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('tile/stock/in/export', {
    ...queryParams.value
  }, `入库单_${new Date().getTime()}.xlsx`);
}

/** 添加明细 */
function handleAddDetail() {
  form.value.details.push({
    goodsId: undefined,
    quantity: 1
  });
}

/** 删除明细 */
function handleDeleteDetail(index) {
  form.value.details.splice(index, 1);
}

/** 提交入库单 */
function handleSubmit(row) {
  proxy.$modal.confirm('是否确认提交入库单？').then(function() {
    return submitStockIn(row.inId);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("提交成功");
  }).catch(() => {});
}

/** 取消入库单 */
function handleCancel(row) {
  proxy.$modal.confirm('是否确认取消入库单？').then(function() {
    return cancelStockIn(row.inId);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("取消成功");
  }).catch(() => {});
}

/** 查看明细 */
function handleDetail(row) {
  const inId = row.inId;
  proxy.$router.push({ path: '/tile/stock/in/detail', query: { inId: inId }});
}

/** 仓库选择改变事件 */
function handleWarehouseChange(value) {
  // 清空货位选择
  form.value.locationId = undefined;
  locationOptions.value = [];
  
  // 如果选择了仓库，则获取对应的货位列表
  if (value) {
    // 确保 value 是数字类型
    const warehouseId = parseInt(value);
    if (!isNaN(warehouseId)) {
      listLocationByWarehouseId(warehouseId).then(response => {
        locationOptions.value = response.data || [];
      }).catch(error => {
        console.error("获取货位列表失败:", error);
      });
    }
  }
}

getWarehouseList();
getGoodsList();
getSupplierList();
getList();
</script>

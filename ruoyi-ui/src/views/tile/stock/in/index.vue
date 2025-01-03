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
      <el-form-item label="入库类型" prop="inType">
        <el-select v-model="queryParams.inType" placeholder="请选择入库类型" clearable>
          <el-option label="采购入库" value="1" />
          <el-option label="退货入库" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="待入库" value="1" />
          <el-option label="已入库" value="2" />
          <el-option label="已取消" value="3" />
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
          type="danger"
          plain
          icon="Delete"
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
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Check"
          @click="handleSubmit"
          v-hasPermi="['tile:stock:in:edit']"
        >提交</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Close"
          @click="handleCancel"
          v-hasPermi="['tile:stock:in:edit']"
        >取消</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockInList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="入库单号" align="center" prop="inCode" />
      <el-table-column label="入库类型" align="center" prop="inType">
        <template #default="scope">
          <dict-tag :options="[{value: '1', label: '采购入库'}, {value: '2', label: '退货入库'}]" :value="scope.row.inType"/>
        </template>
      </el-table-column>
      <el-table-column label="仓库" align="center" prop="warehouseName" />
      <el-table-column label="供应商/客户" align="center" prop="supplierName">
        <template #default="scope">
          {{ scope.row.inType === '1' ? scope.row.supplierName : scope.row.customerName }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="[
            { label: '待入库', value: '1' },
            { label: '已入库', value: '2' },
            { label: '已取消', value: '3' }
          ]" :value="scope.row.status"/>
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
      <el-form ref="stockInRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="入库类型" prop="inType">
          <el-radio-group v-model="form.inType" :disabled="readonly">
            <el-radio :value="'1'">采购入库</el-radio>
            <el-radio :value="'2'">退货入库</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" placeholder="请选择仓库" clearable @change="handleWarehouseChange" :disabled="readonly">
            <el-option
              v-for="item in warehouseOptions"
              :key="item.warehouseId"
              :label="item.warehouseName"
              :value="item.warehouseId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId" v-if="form.inType === '1'">
          <el-select v-model="form.supplierId" placeholder="请选择供应商" clearable :disabled="readonly">
            <el-option
              v-for="item in supplierOptions"
              :key="item.supplierId"
              :label="item.supplierName"
              :value="item.supplierId"
            />
          </el-select>
        </el-form-item>
        <template v-if="form.inType === '2'">
          <el-form-item label="客户" prop="customerId">
            <el-select v-model="form.customerId" placeholder="请选择客户" clearable @change="handleCustomerChange" :disabled="readonly">
              <el-option
                v-for="item in customerOptions"
                :key="item.customerId"
                :label="item.customerName"
                :value="item.customerId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="销售单" prop="saleOrderId">
            <el-select v-model="form.saleOrderId" placeholder="请选择销售单" clearable @change="handleSaleOrderChange" :disabled="readonly">
              <el-option
                v-for="item in saleOrderOptions"
                :key="item.orderId"
                :label="item.orderCode"
                :value="item.orderId"
              />
            </el-select>
          </el-form-item>
        </template>
        <el-form-item label="货位" prop="locationId" v-if="locationOptions.length > 0">
          <el-select v-model="form.locationId" placeholder="请选择货位" clearable :disabled="readonly">
            <el-option
              v-for="item in locationOptions"
              :key="item.locationId"
              :label="item.locationName"
              :value="item.locationId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" :disabled="readonly" />
        </el-form-item>
        <el-divider content-position="center">入库明细</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddDetail" :disabled="readonly">添加明细</el-button>
          </el-col>
        </el-row>
        <el-table :data="form.details" :border="true">
          <el-table-column type="index" label="序号" width="50" />
          <el-table-column label="商品" prop="goodsId">
            <template #default="scope">
              <el-select v-model="scope.row.goodsId" placeholder="请选择商品" :disabled="readonly" @change="(val) => handleGoodsChange(val, scope.$index)">
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
              <el-input-number v-model="scope.row.quantity" :min="1" controls-position="right" :disabled="readonly" />
            </template>
          </el-table-column>
          <el-table-column label="单位" prop="unit">
            <template #default="scope">
              <el-select v-model="scope.row.unit" placeholder="请选择单位" :disabled="readonly">
                <el-option label="箱" value="1" />
                <el-option label="片" value="2" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="批次号" prop="batchNo">
            <template #default="scope">
              <el-input v-model="scope.row.batchNo" placeholder="请输入批次号" :disabled="readonly" />
            </template>
          </el-table-column>
          <el-table-column label="货位" prop="locationId">
            <template #default="scope">
              <el-select v-model="scope.row.locationId" placeholder="请选择货位" :disabled="readonly">
                <el-option
                  v-for="item in locationOptions"
                  :key="item.locationId"
                  :label="item.locationName"
                  :value="item.locationId"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template #default="scope">
              <el-button type="text" icon="Delete" @click="handleDeleteDetail(scope.$index)" :disabled="readonly">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm" :disabled="readonly">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="TileStockIn">
import { listStockIn, getStockIn, delStockIn, addStockIn, updateStockIn, submitStockIn, cancelStockIn } from "@/api/tile/stock";
import { listWarehouse } from "@/api/tile/warehouse";
import { listSupplier } from "@/api/tile/supplier";
import { listGoods } from "@/api/tile/goods";
import { listSaleOrder, getSaleOrder, listCustomer, listLocation } from "@/api/tile/stock";
import { getCurrentInstance, ref, reactive, toRefs } from 'vue';

const { proxy } = getCurrentInstance();
const { sys_normal_disable } = proxy.useDict("sys_normal_disable");
const { tile_stock_in_status } = proxy.useDict("tile_stock_in_status");

const stockInList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const readonly = ref(false);

const warehouseOptions = ref([]);
const supplierOptions = ref([]);
const goodsOptions = ref([]);
const locationOptions = ref([]);
const customerOptions = ref([]);
const saleOrderOptions = ref([]);

const data = reactive({
  form: {
    inId: undefined,
    inCode: undefined,
    warehouseId: undefined,
    supplierId: undefined,
    customerId: undefined,
    saleOrderId: undefined,
    locationId: undefined,
    inType: "1", // 默认采购入库
    status: "1",
    remark: undefined,
    details: [
      {
        goodsId: undefined,
        quantity: 1,
        unit: "1", // 设置默认单位为箱
        batchNo: "",
        locationId: undefined,
        price: 0,
        amount: 0,
        remark: ""
      }
    ]
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    inCode: undefined,
    warehouseId: undefined,
    status: undefined,
    inType: undefined
  },
  rules: {
    warehouseId: [{ required: true, message: "仓库不能为空", trigger: "change" }],
    supplierId: [{ required: true, message: "供应商不能为空", trigger: "change" }],
    locationId: [{ required: true, message: "货位不能为空", trigger: "change" }],
    "details.0": [{ required: true, message: "明细不能为空", trigger: "blur" }]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询仓库列表 */
function getWarehouseList() {
  listWarehouse().then(response => {
    warehouseOptions.value = response.rows;
  });
}

/** 查询供应商列表 */
function getSupplierList() {
  listSupplier().then(response => {
    supplierOptions.value = response.rows;
  });
}

/** 查询商品列表 */
function getGoodsList() {
  listGoods().then(response => {
    goodsOptions.value = response.rows;
  });
}

/** 查询客户列表 */
function getCustomerList() {
  listCustomer().then(response => {
    customerOptions.value = response.rows;
  });
}

/** 查询销售单列表 */
function getSaleOrderList() {
  listSaleOrder({
    customerId: form.value.customerId,
    status: "2" // 已完成的销售单
  }).then(response => {
    saleOrderOptions.value = response.rows;
  });
}

/** 查询货位列表 */
function getLocationList() {
  if (form.value.warehouseId) {
    listLocation({
      warehouseId: form.value.warehouseId
    }).then(response => {
      locationOptions.value = response.rows;
    });
  } else {
    locationOptions.value = [];
  }
}

/** 客户选择改变事件 */
function handleCustomerChange(value) {
  form.value.saleOrderId = undefined;
  if (value) {
    getSaleOrderList();
  } else {
    saleOrderOptions.value = [];
  }
}

/** 销售单选择改变事件 */
async function handleSaleOrderChange(value) {
  if (value) {
    const response = await getSaleOrder(value);
    const saleOrder = response.data;
    // 复制销售单明细到入库单明细
    form.value.details = saleOrder.details.map(detail => ({
      goodsId: detail.goodsId,
      quantity: detail.quantity,
      unit: detail.unit,
      batchNo: "",
      locationId: form.value.locationId,
      price: detail.price,
      amount: detail.amount,
      remark: ""
    }));
  } else {
    form.value.details = [
      {
        goodsId: undefined,
        quantity: 1,
        unit: "1", // 设置默认单位为箱
        batchNo: "",
        locationId: undefined,
        price: 0,
        amount: 0,
        remark: ""
      }
    ];
  }
}

/** 仓库选择改变事件 */
function handleWarehouseChange(value) {
  // 清空主表和明细表的货位ID
  form.value.locationId = undefined;
  form.value.details.forEach(detail => {
    detail.locationId = undefined;
  });
  
  // 加载货位列表
  if (value) {
    listLocation({ warehouseId: value }).then(response => {
      locationOptions.value = response.rows;
      // 如果只有一个货位，自动选择
      if (locationOptions.value.length === 1) {
        const locationId = locationOptions.value[0].locationId;
        form.value.locationId = locationId;
        // 更新所有明细的货位ID
        form.value.details.forEach(detail => {
          detail.locationId = locationId;
        });
      }
    });
  } else {
    locationOptions.value = [];
  }
}

/** 表单重置 */
function reset() {
  form.value = {
    inId: undefined,
    inCode: undefined,
    inType: "1",
    warehouseId: undefined,
    supplierId: undefined,
    customerId: undefined,
    saleOrderId: undefined,
    locationId: undefined,
    status: "1",
    remark: undefined,
    details: [
      {
        goodsId: undefined,
        quantity: 1,
        unit: "1", // 设置默认单位为箱
        batchNo: "",
        locationId: undefined,
        price: 0,
        amount: 0,
        remark: ""
      }
    ]
  };
  proxy.resetForm("stockInRef");
  locationOptions.value = [];
}

/** 新增入库单 */
function handleAdd() {
  reset();
  // 加载仓库列表
  listWarehouse().then(response => {
    warehouseOptions.value = response.rows;
  });
  // 加载供应商列表
  listSupplier().then(response => {
    supplierOptions.value = response.rows;
  });
  // 加载客户列表
  listCustomer().then(response => {
    customerOptions.value = response.rows;
  });
  // 加载商品列表
  listGoods().then(response => {
    goodsOptions.value = response.rows;
  });
  open.value = true;
  title.value = "添加入库单";
  readonly.value = false;
}

/** 搜索按钮操作 */
function handleQuery() {
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
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

/** 提交按钮操作 */
function handleSubmit(row) {
  const inIds = row.inId || ids.value;
  proxy.$modal.confirm('是否确认提交入库单编号为"' + inIds + '"的数据项？').then(function() {
    return submitStockIn(inIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("提交成功");
  }).catch(() => {});
}

/** 取消按钮操作 */
function handleCancel(row) {
  const inIds = row.inId || ids.value;
  proxy.$modal.confirm('是否确认取消入库单编号为"' + inIds + '"的数据项？').then(function() {
    return cancelStockIn(inIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("取消成功");
  }).catch(() => {});
}

/** 查看明细按钮操作 */
function handleDetail(row) {
  reset();
  const inId = row.inId || row;
  readonly.value = true;
  open.value = true;
  title.value = "查看入库单";
  // 先获取仓库对应的货位列表
  if (row.warehouseId) {
    listLocation({
      warehouseId: row.warehouseId
    }).then(response => {
      locationOptions.value = response.rows;
      // 获取完货位列表后再获取入库单详情
      getStockIn(inId).then(response => {
        form.value = response.data;
        // 设置明细的默认单位
        form.value.details.forEach(detail => {
          if (!detail.unit) {
            detail.unit = "1";
          }
        });
      });
    });
  }
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const inId = row.inId || row;
  readonly.value = false;
  open.value = true;
  title.value = "修改入库单";
  // 先获取仓库对应的货位列表
  if (row.warehouseId) {
    listLocation({
      warehouseId: row.warehouseId
    }).then(response => {
      locationOptions.value = response.rows;
      // 获取完货位列表后再获取入库单详情
      getStockIn(inId).then(response => {
        form.value = response.data;
        // 设置明细的默认单位
        form.value.details.forEach(detail => {
          if (!detail.unit) {
            detail.unit = "1";
          }
        });
      });
    });
  }
}

/** 商品选择改变事件 */
function handleGoodsChange(value, index) {
  // 获取商品信息
  const goods = goodsOptions.value.find(item => item.goodsId === value);
  if (goods) {
    // 设置默认单位为箱
    form.value.details[index].unit = "1";
  }
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["stockInRef"].validate(valid => {
    if (valid) {
      // 如果是修改操作，检查入库单状态
      if (form.value.inId != undefined) {
        getStockIn(form.value.inId).then(response => {
          if (response.data.status !== '1') {
            proxy.$modal.msgError("只能修改待入库状态的入库单");
            return;
          }
          updateStockIn(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          });
        });
      } else {
        // 设置明细的货位ID
        form.value.details.forEach(detail => {
          if (!detail.locationId) {
            detail.locationId = form.value.locationId;
          }
          if (!detail.batchNo) {
            detail.batchNo = '';
          }
        });

        addStockIn(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 添加明细 */
function handleAddDetail() {
  // 如果主表已选择货位，则使用主表的货位ID
  const locationId = form.value.locationId;
  form.value.details.push({
    goodsId: undefined,
    quantity: 1,
    unit: "1", // 设置默认单位为箱
    batchNo: "",
    locationId: locationId, // 使用主表的货位ID
    price: 0,
    amount: 0,
    remark: ""
  });
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.inId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 删除明细 */
function handleDeleteDetail(index) {
  form.value.details.splice(index, 1);
}

/** 查询入库单列表 */
function getList() {
  loading.value = true;
  listStockIn(queryParams.value).then(response => {
    stockInList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}

getWarehouseList();
getGoodsList();
getSupplierList();
getCustomerList();
getList();
</script>

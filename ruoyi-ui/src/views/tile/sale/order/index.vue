<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="订单编号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单编号"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户" prop="customerId">
        <el-select
          v-model="queryParams.customerId"
          placeholder="请选择客户"
          clearable
          style="width: 200px"
        >
          <el-option
            v-for="dict in customerOptions"
            :key="dict.customerId"
            :label="dict.customerName"
            :value="dict.customerId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="订单日期" prop="orderDate">
        <el-date-picker
          v-model="queryParams.orderDate"
          type="date"
          placeholder="选择订单日期"
          value-format="YYYY-MM-DD"
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select
          v-model="queryParams.orderStatus"
          placeholder="请选择订单状态"
          clearable
          style="width: 200px"
        >
          <el-option
            v-for="dict in orderStatusOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="支付状态" prop="paymentStatus">
        <el-select
          v-model="queryParams.paymentStatus"
          placeholder="请选择支付状态"
          clearable
          style="width: 200px"
        >
          <el-option
            v-for="dict in paymentStatusOptions"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="icons.Search" @click="handleQuery">搜索</el-button>
        <el-button :icon="icons.Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          :icon="icons.Plus"
          @click="handleAdd"
          v-hasPermi="['tile:sale:order:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          :icon="icons.Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['tile:sale:order:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          :icon="icons.Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['tile:sale:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          :icon="icons.Check"
          :disabled="single"
          @click="handleAudit"
          v-hasPermi="['tile:sale:order:audit']"
        >审核</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          :icon="icons.Close"
          :disabled="single"
          @click="handleCancel"
          v-hasPermi="['tile:sale:order:cancel']"
        >取消</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          :icon="icons.Download"
          @click="handleExport"
          v-hasPermi="['tile:sale:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单编号" align="center" prop="orderNo" />
      <el-table-column label="客户名称" align="center" prop="customer.customerName" />
      <el-table-column label="订单日期" align="center" prop="orderDate" width="100" />
      <el-table-column label="订单金额" align="center" prop="totalAmount">
        <template #default="scope">
          {{ scope.row.totalAmount ? '￥' + scope.row.totalAmount : '' }}
        </template>
      </el-table-column>
      <el-table-column label="订单状态" align="center" prop="orderStatus">
        <template #default="scope">
          <dict-tag :options="orderStatusOptions" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="支付状态" align="center" prop="paymentStatus">
        <template #default="scope">
          <dict-tag :options="paymentStatusOptions" :value="scope.row.paymentStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="已支付金额" align="center" prop="paymentAmount">
        <template #default="scope">
          {{ scope.row.paymentAmount ? '￥' + scope.row.paymentAmount : '' }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            type="text"
            :icon="icons.Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tile:sale:order:edit']"
          >修改</el-button>
          <el-button
            type="text"
            :icon="icons.Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tile:sale:order:remove']"
          >删除</el-button>
          <el-button
            type="text"
            :icon="icons.Check"
            @click="handleAudit(scope.row)"
            v-hasPermi="['tile:sale:order:audit']"
          >审核</el-button>
          <el-button
            type="text"
            :icon="icons.Close"
            @click="handleCancel(scope.row)"
            v-hasPermi="['tile:sale:order:cancel']"
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

    <!-- 添加或修改销售订单对话框 -->
    <el-dialog :title="title" v-model="open" width="900px" append-to-body>
      <el-form ref="orderRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户" prop="customerId">
              <el-select v-model="form.customerId" placeholder="请选择客户" style="width: 100%">
                <el-option
                  v-for="dict in customerOptions"
                  :key="dict.customerId"
                  :label="dict.customerName"
                  :value="dict.customerId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="订单日期" prop="orderDate">
              <el-date-picker
                v-model="form.orderDate"
                type="date"
                placeholder="选择订单日期"
                value-format="YYYY-MM-DD"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="收货地址" prop="deliveryAddress">
              <el-input v-model="form.deliveryAddress" placeholder="请输入收货地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="deliveryPhone">
              <el-input v-model="form.deliveryPhone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人" prop="deliveryContact">
              <el-input v-model="form.deliveryContact" placeholder="请输入联系人" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="订单明细">
          <el-table :data="form.details" :border="true">
            <el-table-column type="index" label="序号" width="50" align="center" />
            <el-table-column label="商品" prop="productId">
              <template #default="scope">
                <el-select v-model="scope.row.productId" placeholder="请选择商品" style="width: 100%">
                  <el-option
                    v-for="dict in goodsOptions"
                    :key="dict.goodsId"
                    :label="dict.goodsName"
                    :value="dict.goodsId"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="仓库" prop="warehouseId">
              <template #default="scope">
                <el-select v-model="scope.row.warehouseId" placeholder="请选择仓库" style="width: 100%">
                  <el-option
                    v-for="dict in warehouseOptions"
                    :key="dict.warehouseId"
                    :label="dict.warehouseName"
                    :value="dict.warehouseId"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="数量" prop="quantity" width="150">
              <template #default="scope">
                <el-input-number v-model="scope.row.quantity" :min="1" :precision="2" @change="calculateRowAmount(scope.$index)" />
              </template>
            </el-table-column>
            <el-table-column label="单价" prop="price" width="150">
              <template #default="scope">
                <el-input-number v-model="scope.row.price" :min="0" :precision="2" @change="calculateRowAmount(scope.$index)" />
              </template>
            </el-table-column>
            <el-table-column label="金额" prop="amount" width="150">
              <template #default="scope">
                {{ scope.row.amount ? '￥' + scope.row.amount : '' }}
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="100">
              <template #default="scope">
                <el-button type="text" :icon="icons.Delete" @click="handleDeleteDetail(scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button type="primary" :icon="icons.Plus" @click="handleAddDetail" style="margin-top: 10px">添加明细</el-button>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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

<script setup name="TileSaleOrder">
import { ref, reactive, toRefs, getCurrentInstance } from 'vue'
import { Search, Refresh, Plus, Edit, Delete, Download, Check, Close } from '@element-plus/icons-vue'
import { listOrder, getOrder, addOrder, updateOrder, delOrder, auditOrder, cancelOrder, exportOrder } from "@/api/tile/sale/order";
import { listCustomer } from "@/api/tile/customer";
import { listGoods } from "@/api/tile/goods";
import { listWarehouse } from "@/api/tile/warehouse";

const { proxy } = getCurrentInstance();

// 定义图标变量
const icons = {
  Search,
  Refresh,
  Plus,
  Edit,
  Delete,
  Download,
  Check,
  Close
};

const orderList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 订单状态字典
const orderStatusOptions = ref([
  { label: '待审核', value: '1' },
  { label: '已审核', value: '2' },
  { label: '已出库', value: '3' },
  { label: '已完成', value: '4' },
  { label: '已取消', value: '5' }
]);

// 支付状态字典
const paymentStatusOptions = ref([
  { label: '未支付', value: '1' },
  { label: '部分支付', value: '2' },
  { label: '已支付', value: '3' }
]);

// 客户列表
const customerOptions = ref([]);

// 商品列表
const goodsOptions = ref([]);

// 仓库列表
const warehouseOptions = ref([]);

const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  orderNo: null,
  customerId: null,
  orderDate: null,
  orderStatus: null,
  paymentStatus: null
});

const form = ref({
  orderId: null,
  orderNo: null,
  customerId: null,
  orderDate: null,
  totalAmount: null,
  orderStatus: '1',
  paymentStatus: '1',
  paymentAmount: null,
  deliveryAddress: null,
  deliveryPhone: null,
  deliveryContact: null,
  remark: null,
  details: []
});

const rules = ref({
  customerId: [
    { required: true, message: "客户不能为空", trigger: "blur" }
  ],
  orderDate: [
    { required: true, message: "订单日期不能为空", trigger: "blur" }
  ]
});

/** 查询销售订单列表 */
function getList() {
  loading.value = true;
  listOrder(queryParams.value).then(response => {
    orderList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 查询客户列表 */
function getCustomerList() {
  listCustomer().then(response => {
    customerOptions.value = response.rows;
  });
}

/** 查询商品列表 */
function getGoodsList() {
  listGoods().then(response => {
    goodsOptions.value = response.rows;
  });
}

/** 查询仓库列表 */
function getWarehouseList() {
  listWarehouse().then(response => {
    warehouseOptions.value = response.rows;
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
    orderId: null,
    orderNo: null,
    customerId: null,
    orderDate: null,
    totalAmount: null,
    orderStatus: '1',
    paymentStatus: '1',
    paymentAmount: null,
    deliveryAddress: null,
    deliveryPhone: null,
    deliveryContact: null,
    remark: null,
    details: []
  };
  proxy.resetForm("orderRef");
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
  ids.value = selection.map(item => item.orderId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  getCustomerList();
  getGoodsList();
  getWarehouseList();
  open.value = true;
  title.value = "添加销售订单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  getCustomerList();
  getGoodsList();
  getWarehouseList();
  const orderId = row.orderId || ids.value[0];
  getOrder(orderId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改销售订单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["orderRef"].validate(valid => {
    if (valid) {
      if (form.value.orderId != null) {
        updateOrder(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addOrder(form.value).then(response => {
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
  const orderIds = row.orderId || ids.value;
  proxy.$modal.confirm('是否确认删除销售订单编号为"' + orderIds + '"的数据项？').then(function() {
    return delOrder(orderIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 审核按钮操作 */
function handleAudit(row) {
  const orderId = row.orderId || ids.value[0];
  proxy.$modal.confirm('是否确认审核销售订单编号为"' + orderId + '"的数据项？').then(function() {
    return auditOrder(orderId);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("审核成功");
  }).catch(() => {});
}

/** 取消按钮操作 */
function handleCancel(row) {
  const orderId = row.orderId || ids.value[0];
  proxy.$modal.confirm('是否确认取消销售订单编号为"' + orderId + '"的数据项？').then(function() {
    return cancelOrder(orderId);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("取消成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.$modal.confirm('是否确认导出所有销售订单数据项？').then(() => {
    proxy.download('tile/sale/order/export', {
      ...queryParams.value
    }, `销售订单_${new Date().getTime()}.xlsx`);
  });
}

/** 添加明细 */
function handleAddDetail() {
  form.value.details.push({
    productId: null,
    warehouseId: null,
    quantity: 1,
    price: 0,
    amount: 0
  });
}

/** 删除明细 */
function handleDeleteDetail(index) {
  form.value.details.splice(index, 1);
  calculateTotalAmount();
}

/** 计算行金额 */
function calculateRowAmount(index) {
  const detail = form.value.details[index];
  detail.amount = detail.quantity * detail.price;
  calculateTotalAmount();
}

/** 计算总金额 */
function calculateTotalAmount() {
  form.value.totalAmount = form.value.details.reduce((sum, detail) => {
    return sum + (detail.amount || 0);
  }, 0);
}

onMounted(() => {
  getList();
});
</script>

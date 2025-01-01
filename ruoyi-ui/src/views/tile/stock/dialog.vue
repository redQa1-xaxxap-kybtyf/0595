<template>
  <el-dialog :title="title" v-model="open" width="500px" append-to-body>
    <el-form ref="stockFormRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="商品" prop="goodsId">
        <el-select v-model="form.goodsId" placeholder="请选择商品" clearable>
          <el-option
            v-for="item in goodsList"
            :key="item.goodsId"
            :label="item.goodsName"
            :value="item.goodsId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseId">
        <el-select v-model="form.warehouseId" placeholder="请选择仓库" clearable @change="handleWarehouseChange">
          <el-option
            v-for="item in warehouseList"
            :key="item.warehouseId"
            :label="item.warehouseName"
            :value="item.warehouseId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓位" prop="positionId">
        <el-select v-model="form.positionId" placeholder="请选择仓位" clearable>
          <el-option
            v-for="item in positionList"
            :key="item.positionId"
            :label="item.positionName"
            :value="item.positionId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="库存数量" prop="quantity">
        <el-input-number v-model="form.quantity" :min="0" controls-position="right" placeholder="请输入库存数量" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio
            v-for="dict in sys_normal_disable"
            :key="dict.value"
            :value="dict.value"
          >{{ dict.label }}</el-radio>
        </el-radio-group>
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
</template>

<script setup name="StockDialog">
import { getStock, addStock, updateStock } from "@/api/tile/stock";
import { listGoods } from "@/api/tile/goods";
import { listWarehouse, listWarehousePosition } from "@/api/tile/warehouse";

const { proxy } = getCurrentInstance();
const { sys_normal_disable } = proxy.useDict("sys_normal_disable");

const open = ref(false);
const loading = ref(true);
const title = ref("");
const goodsList = ref([]);
const warehouseList = ref([]);
const positionList = ref([]);

const emit = defineEmits(["refresh"]);

const data = reactive({
  form: {
    stockId: undefined,
    goodsId: undefined,
    warehouseId: undefined,
    positionId: undefined,
    quantity: 0,
    status: "0",
    remark: undefined
  },
  rules: {
    goodsId: [
      { required: true, message: "商品不能为空", trigger: "change" }
    ],
    warehouseId: [
      { required: true, message: "仓库不能为空", trigger: "change" }
    ],
    positionId: [
      { required: true, message: "仓位不能为空", trigger: "change" }
    ],
    quantity: [
      { required: true, message: "库存数量不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态不能为空", trigger: "change" }
    ]
  }
});

const { form, rules } = toRefs(data);

/** 获取商品列表 */
function getGoodsList() {
  listGoods().then(response => {
    goodsList.value = response.rows;
  });
}

/** 获取仓库列表 */
function getWarehouseList() {
  listWarehouse().then(response => {
    warehouseList.value = response.rows;
  });
}

/** 仓库选择改变时获取仓位列表 */
function handleWarehouseChange(warehouseId) {
  if (!warehouseId) {
    positionList.value = [];
    form.value.positionId = undefined;
    return;
  }
  listWarehousePosition({ warehouseId: warehouseId }).then(response => {
    positionList.value = response.rows;
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
    stockId: undefined,
    goodsId: undefined,
    warehouseId: undefined,
    positionId: undefined,
    quantity: 0,
    status: "0",
    remark: undefined
  };
  positionList.value = [];
  proxy.resetForm("stockFormRef");
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  getGoodsList();
  getWarehouseList();
  open.value = true;
  title.value = "添加库存管理";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  getGoodsList();
  getWarehouseList();
  const stockId = row.stockId;
  getStock(stockId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改库存管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["stockFormRef"].validate(valid => {
    if (valid) {
      if (form.value.stockId != null) {
        updateStock(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          emit("refresh");
        });
      } else {
        addStock(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          emit("refresh");
        });
      }
    }
  });
}

defineExpose({
  handleAdd,
  handleUpdate
});
</script>

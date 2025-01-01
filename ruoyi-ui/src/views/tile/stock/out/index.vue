<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="出库单号" prop="outCode">
        <el-input
          v-model="queryParams.outCode"
          placeholder="请输入出库单号"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseId">
        <warehouse-select v-model="queryParams.warehouseId" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="出库单状态" clearable style="width: 200px">
          <el-option
            v-for="dict in tile_stock_out_status"
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
          v-hasPermi="['tile:stock:out:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['tile:stock:out:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['tile:stock:out:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['tile:stock:out:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockOutList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="出库单号" align="center" prop="outCode" />
      <el-table-column label="仓库" align="center" prop="warehouseName" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="tile_stock_out_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="220" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            type="text"
            icon="View"
            @click="handleView(scope.row)"
            v-hasPermi="['tile:stock:out:query']"
          >查看</el-button>
          <el-button
            type="text"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tile:stock:out:edit']"
            v-if="scope.row.status === '1'"
          >修改</el-button>
          <el-button
            type="text"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tile:stock:out:remove']"
            v-if="scope.row.status === '1'"
          >删除</el-button>
          <el-button
            type="text"
            icon="Check"
            @click="handleSubmit(scope.row)"
            v-hasPermi="['tile:stock:out:edit']"
            v-if="scope.row.status === '1'"
          >提交</el-button>
          <el-button
            type="text"
            icon="Close"
            @click="handleCancel(scope.row)"
            v-hasPermi="['tile:stock:out:edit']"
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

    <!-- 添加或修改出库单对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="stockOutRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="仓库" prop="warehouseId">
          <warehouse-select v-model="form.warehouseId" :disabled="form.status !== '1'" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :disabled="form.status !== '1'" />
        </el-form-item>
        <el-form-item label="出库明细">
          <el-table :data="form.details" :border="true">
            <el-table-column type="index" label="序号" width="50" />
            <el-table-column label="商品" prop="goodsName">
              <template #default="scope">
                <goods-select 
                  v-model="scope.row.goodsId" 
                  @update:modelValue="(val) => handleGoodsChange(val, scope.$index)"
                  :disabled="form.status !== '1'"
                />
              </template>
            </el-table-column>
            <el-table-column label="数量" prop="quantity" width="150">
              <template #default="scope">
                <el-input-number 
                  v-model="scope.row.quantity" 
                  :min="1" 
                  controls-position="right"
                  :disabled="form.status !== '1'"
                />
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="150">
              <template #default="scope">
                <el-button
                  type="text"
                  icon="Plus"
                  @click="handleAddDetail"
                  v-if="scope.$index === form.details.length - 1 && form.status === '1'"
                >添加</el-button>
                <el-button
                  type="text"
                  icon="Delete"
                  @click.prevent="handleDeleteDetail(scope.$index)"
                  v-if="form.details.length > 1 && form.status === '1'"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm" v-if="form.status === '1'">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="StockOut">
import { listStockOut, getStockOut, addStockOut, updateStockOut, delStockOut, submitStockOut, cancelStockOut } from "@/api/tile/stock";
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';

const { proxy } = getCurrentInstance();

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
// 出库单表格数据
const stockOutList = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);

// 查询参数
const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  outCode: undefined,
  warehouseId: undefined,
  status: undefined
});

// 表单参数
const form = ref({
  outId: undefined,
  outCode: undefined,
  warehouseId: undefined,
  status: '1',
  remark: undefined,
  details: [{
    goodsId: undefined,
    quantity: 1
  }]
});

// 表单校验
const rules = ref({
  warehouseId: [
    { required: true, message: "仓库不能为空", trigger: "blur" }
  ]
});

/** 查询出库单列表 */
function getList() {
  loading.value = true;
  listStockOut(queryParams.value).then(response => {
    stockOutList.value = response.rows;
    total.value = response.total;
    loading.value = false;
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
    outId: undefined,
    outCode: undefined,
    warehouseId: undefined,
    status: '1',
    remark: undefined,
    details: [{
      goodsId: undefined,
      quantity: 1
    }]
  };
  proxy.resetForm("stockOutRef");
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

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加出库单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const outId = row.outId || ids.value[0];
  getStockOut(outId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改出库单";
  });
}

/** 查看按钮操作 */
function handleView(row) {
  reset();
  const outId = row.outId;
  getStockOut(outId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "查看出库单";
  });
}

/** 提交按钮 */
function handleSubmit(row) {
  ElMessageBox.confirm('是否确认提交出库单编号为"' + row.outCode + '"的数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {
    submitStockOut(row.outId).then(() => {
      getList();
      ElMessage.success("提交成功");
    });
  });
}

/** 取消按钮 */
function handleCancel(row) {
  ElMessageBox.confirm('是否确认取消出库单编号为"' + row.outCode + '"的数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {
    cancelStockOut(row.outId).then(() => {
      getList();
      ElMessage.success("取消成功");
    });
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["stockOutRef"].validate(valid => {
    if (valid) {
      if (form.value.outId != undefined) {
        updateStockOut(form.value).then(response => {
          ElMessage.success("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addStockOut(form.value).then(response => {
          ElMessage.success("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const outIds = row.outId || ids.value;
  ElMessageBox.confirm('是否确认删除出库单编号为"' + row.outCode + '"的数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {
    delStockOut(outIds).then(() => {
      getList();
      ElMessage.success("删除成功");
    });
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('tile/stock/out/export', {
    ...queryParams.value
  }, `出库单_${new Date().getTime()}.xlsx`);
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

/** 商品选择事件 */
function handleGoodsChange(val, index) {
  if (val) {
    proxy.$refs["goodsSelect" + index].getGoodsName().then(name => {
      form.value.details[index].goodsName = name;
    });
  } else {
    form.value.details[index].goodsName = undefined;
  }
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.outId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

onMounted(() => {
  getList();
});
</script>

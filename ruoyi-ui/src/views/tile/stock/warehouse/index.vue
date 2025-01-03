<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item label="仓库名称" prop="warehouseName">
        <el-input
          v-model="queryParams.warehouseName"
          placeholder="请输入仓库名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
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
          v-hasPermi="['system:warehouse:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:warehouse:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:warehouse:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:warehouse:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="warehouseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="仓库ID" align="center" prop="warehouseId" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="货位数量" align="center" prop="locationCount" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:warehouse:edit']"
          >修改</el-button>
          <el-button
            link
            type="danger"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:warehouse:remove']"
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

    <!-- 添加或修改仓库对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="warehouseForm" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="仓库名称" prop="warehouseName">
          <el-input v-model="form.warehouseName" placeholder="请输入仓库名称" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :value="'0'" label="正常">正常</el-radio>
            <el-radio :value="'1'" label="停用">停用</el-radio>
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
  </div>
</template>

<script setup name="StockWarehouse">
import { listWarehouse, getWarehouse, delWarehouse, addWarehouse, updateWarehouse } from "@/api/tile/warehouse";
import { getCurrentInstance, ref, reactive, toRefs } from 'vue';

const { proxy } = getCurrentInstance();
const { sys_normal_disable } = proxy.useDict("sys_normal_disable");

// 遮罩层
const loading = ref(true);
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
// 仓库表格数据
const warehouseList = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);

const data = reactive({
  form: {
    warehouseId: undefined,
    warehouseName: undefined,
    status: "0",
    remark: undefined
  },
  rules: {
    warehouseName: [
      { required: true, message: "仓库名称不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态不能为空", trigger: "change" }
    ]
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    warehouseName: undefined,
    status: undefined
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询仓库列表 */
function getList() {
  loading.value = true;
  listWarehouse(queryParams.value).then(response => {
    warehouseList.value = response.rows;
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
    warehouseId: undefined,
    warehouseName: undefined,
    status: "0",
    remark: undefined
  };
  proxy.resetForm("warehouseForm");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryForm");
  handleQuery();
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加仓库";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const warehouseId = row.warehouseId || ids.value;
  getWarehouse(warehouseId).then(response => {
    Object.assign(form.value, response.data);
    open.value = true;
    title.value = "修改仓库";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["warehouseForm"].validate(valid => {
    if (valid) {
      if (form.value.warehouseId != undefined) {
        updateWarehouse(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addWarehouse(form.value).then(response => {
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
  const warehouseIds = row.warehouseId || ids.value;
  const warehouseName = row.warehouseName || warehouseList.value.find(item => item.warehouseId === warehouseIds)?.warehouseName;
  proxy.$modal.confirm('是否确认删除仓库"' + warehouseName + '"？').then(function() {
    return delWarehouse(warehouseIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/warehouse/export', {
    ...queryParams.value
  }, `warehouse_${new Date().getTime()}.xlsx`);
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.warehouseId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

onMounted(() => {
  getList();
});
</script>

<style scoped>
.dialog-footer {
  text-align: center;
  padding-top: 20px;
}
</style>

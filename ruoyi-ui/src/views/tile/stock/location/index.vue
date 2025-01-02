<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item label="所属仓库" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" placeholder="请选择所属仓库" clearable>
          <el-option
            v-for="item in warehouseOptions"
            :key="item.warehouseId"
            :label="item.warehouseName"
            :value="item.warehouseId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="货位名称" prop="locationName">
        <el-input
          v-model="queryParams.locationName"
          placeholder="请输入货位名称"
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
          v-hasPermi="['tile:location:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['tile:location:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['tile:location:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['tile:location:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="locationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="货位ID" align="center" prop="locationId" />
      <el-table-column label="所属仓库" align="center" prop="warehouseName" />
      <el-table-column label="货位名称" align="center" prop="locationName" />
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
          <el-button
            link
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tile:location:edit']"
          >修改</el-button>
          <el-button
            link
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tile:location:remove']"
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

    <!-- 添加或修改货位对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="locationRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" placeholder="请选择所属仓库">
            <el-option
              v-for="item in warehouseOptions"
              :key="item.warehouseId"
              :label="item.warehouseName"
              :value="item.warehouseId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="货位名称" prop="locationName">
          <el-input v-model="form.locationName" placeholder="请输入货位名称" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in sys_normal_disable"
              :key="dict.value"
              :value="dict.value"
            >{{dict.label}}</el-radio>
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

<script setup name="Location">
import { listLocation, getLocation, delLocation, addLocation, updateLocation, exportLocation } from "@/api/tile/location";
import { listWarehouse } from "@/api/tile/warehouse";

const { proxy } = getCurrentInstance();
const { sys_normal_disable } = proxy.useDict("sys_normal_disable");

const locationList = ref([]);
const warehouseOptions = ref([]);
const open = ref(false);
const loading = ref(false);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {
    locationId: undefined,
    locationName: undefined,
    warehouseId: undefined,
    status: "0",
    remark: undefined
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    locationName: undefined,
    warehouseId: undefined,
    status: undefined
  },
  rules: {
    locationName: [
      { required: true, message: "货位名称不能为空", trigger: "blur" }
    ],
    warehouseId: [
      { required: true, message: "所属仓库不能为空", trigger: "change" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询货位列表 */
function getList() {
  loading.value = true;
  listLocation(queryParams.value).then(response => {
    locationList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 获取仓库列表 */
function getWarehouseOptions() {
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
    locationId: undefined,
    locationName: undefined,
    warehouseId: undefined,
    status: "0",
    remark: undefined
  };
  proxy.resetForm("locationRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryForm");
  handleQuery();
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.locationId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  getWarehouseOptions();
  open.value = true;
  title.value = "添加货位";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const locationId = row.locationId || ids.value[0];
  getLocation(locationId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改货位";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["locationRef"].validate(valid => {
    if (valid) {
      if (form.value.locationId != null) {
        updateLocation(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addLocation(form.value).then(response => {
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
  const locationIds = row.locationId || ids.value;
  proxy.$modal.confirm('是否确认删除货位编号为"' + locationIds + '"的数据项？').then(function() {
    return delLocation(locationIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.$download('tile/location/export', {
    ...queryParams.value
  }, `location_${new Date().getTime()}.xlsx`);
}

onMounted(() => {
  getList();
});
</script>

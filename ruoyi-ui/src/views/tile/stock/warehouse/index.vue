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
      <el-form-item label="仓位名称" prop="locations">
        <el-input
          v-model="queryParams.locations"
          placeholder="请输入仓位名称"
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
      <el-table-column label="仓库编号" align="center" prop="warehouseId" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" />
      <el-table-column label="仓位名称" align="center" prop="locations" />
      <el-table-column label="仓库地址" align="center" prop="address" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
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
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:warehouse:edit']"
          >修改</el-button>
          <el-button
            link
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
      <el-form ref="warehouseFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="仓库名称" prop="warehouseName">
          <el-input v-model="form.warehouseName" placeholder="请输入仓库名称" />
        </el-form-item>
        <el-form-item label="仓位名称" prop="locations">
          <el-select
            v-model="locationList"
            multiple
            filterable
            allow-create
            default-first-option
            placeholder="请输入仓位名称（可多选）"
            @change="handleLocationsChange"
          >
            <el-option
              v-for="item in locationOptions"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="仓库地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入仓库地址" />
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

<script setup name="Warehouse">
import { listWarehouse, getWarehouse, delWarehouse, addWarehouse, updateWarehouse, exportWarehouse } from "@/api/tile/warehouse";
import { getCurrentInstance, ref, reactive, onMounted, nextTick } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { parseTime } from '@/utils/ruoyi'
import { useDict } from '@/utils/dict'

const { proxy } = getCurrentInstance();
const { sys_normal_disable } = useDict('sys_normal_disable');

const warehouseFormRef = ref(null);
const loading = ref(false);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const showSearch = ref(true);
const open = ref(false);
const title = ref("");
const total = ref(0);
const warehouseList = ref([]);
const locationList = ref([]);
const locationOptions = ref([]);

const data = reactive({
  form: {
    warehouseId: undefined,
    warehouseName: undefined,
    locations: undefined,
    address: undefined,
    status: "0",
    remark: undefined
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    warehouseName: undefined,
    locations: undefined,
    status: undefined
  },
  rules: {
    warehouseName: [
      { required: true, message: "仓库名称不能为空", trigger: "blur" }
    ],
    locations: [
      { required: true, message: "仓位名称不能为空", trigger: "change" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询仓库列表 */
function getList() {
  loading.value = true;
  listWarehouse(queryParams.value).then(response => {
    warehouseList.value = response.rows;
    const allLocations = new Set();
    warehouseList.value.forEach(warehouse => {
      if (warehouse.locations) {
        warehouse.locations.split(',').forEach(location => {
          allLocations.add(location.trim());
        });
      }
    });
    locationOptions.value = Array.from(allLocations);
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
    locations: undefined,
    address: undefined,
    status: "0",
    remark: undefined
  };
  locationList.value = [];
  warehouseFormRef.value?.resetFields();
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.$refs["queryForm"].resetFields();
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.warehouseId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
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
  const warehouseId = row.warehouseId || ids.value[0];
  getWarehouse(warehouseId).then(response => {
    Object.assign(form.value, response.data);
    if (form.value.locations) {
      locationList.value = form.value.locations.split(',').map(item => item.trim()).filter(item => item);
    } else {
      locationList.value = [];
    }
    open.value = true;
    title.value = "修改仓库";
  });
}

/** 仓位变更处理 */
function handleLocationsChange(value) {
  form.value.locations = value ? value.join(',') : '';
}

/** 提交按钮 */
function submitForm() {
  if (!warehouseFormRef.value) return;
  warehouseFormRef.value.validate(valid => {
    if (valid) {
      if (form.value.warehouseId !== undefined) {
        updateWarehouse(form.value).then(response => {
          ElMessage.success("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addWarehouse(form.value).then(response => {
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
  const warehouseIds = row.warehouseId || ids.value;
  ElMessageBox.confirm('是否确认删除仓库编号为"' + warehouseIds + '"的数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function() {
    return delWarehouse(warehouseIds);
  }).then(() => {
    getList();
    ElMessage.success("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  ElMessageBox.confirm('是否确认导出所有仓库数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function() {
    return exportWarehouse(queryParams.value);
  }).then(response => {
    proxy.download(response.msg);
  }).catch(() => {});
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

<template>
  <div class="app-container">
    <el-form :model="data.queryParams" ref="queryRef" :inline="true" v-show="data.showSearch">
      <el-form-item label="供应商" prop="supplierId">
        <el-select v-model="data.queryParams.supplierId" placeholder="请选择供应商" clearable style="width: 200px">
          <el-option
            v-for="supplier in data.supplierOptions"
            :key="supplier.supplierId"
            :label="supplier.supplierName"
            :value="supplier.supplierId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="品类名称" prop="categoryName">
        <el-input
          v-model="data.queryParams.categoryName"
          placeholder="请输入品类名称"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="20">
      <!-- 供应商列表 -->
      <el-col :span="6">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>供应商列表</span>
              <el-button
                type="primary"
                plain
                icon="Plus"
                @click="handleAddSupplier"
                v-hasPermi="['tile:supplier:add']"
              >新增供应商</el-button>
            </div>
          </template>
          <el-table
            :data="data.supplierOptions"
            style="width: 100%"
            highlight-current-row
            @current-change="handleSupplierSelect"
            v-loading="data.supplierLoading"
          >
            <el-table-column label="供应商名称" prop="supplierName" />
            <el-table-column label="联系人" prop="contactName" />
            <el-table-column label="操作" align="center" width="120">
              <template #default="scope">
                <el-button
                  type="text"
                  icon="Edit"
                  @click="handleEditSupplier(scope.row)"
                  v-hasPermi="['tile:supplier:edit']"
                >修改</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <!-- 价格列表 -->
      <el-col :span="18">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>价格列表</span>
              <div class="right-buttons">
                <el-button
                  type="primary"
                  plain
                  icon="Plus"
                  @click="handleAdd"
                  v-hasPermi="['tile:price:add']"
                >新增价格</el-button>
                <el-button
                  type="warning"
                  plain
                  icon="Download"
                  @click="handleExport"
                  v-hasPermi="['tile:price:export']"
                >导出</el-button>
              </div>
            </div>
          </template>

          <el-collapse v-model="data.activeSuppliers">
            <el-collapse-item
              v-for="supplier in data.priceList"
              :key="supplier.supplierId"
              :name="supplier.supplierId"
            >
              <template #title>
                <div class="supplier-title">
                  <span>{{ supplier.supplierName || '未知供应商' }}</span>
                  <span class="supplier-count">({{ supplier.categoryList?.length || 0 }}个品类)</span>
                </div>
              </template>
              
              <el-table 
                :data="supplier.categoryList" 
                style="width: 100%"
                v-loading="data.loading"
              >
                <el-table-column label="品类名称" prop="categoryName" min-width="120"/>
                <el-table-column label="规格" prop="specification" min-width="120"/>
                <el-table-column label="型号" prop="model" min-width="120"/>
                <el-table-column label="单价" prop="piecePrice" min-width="100">
                  <template #default="scope">
                    {{ scope.row.piecePrice ? scope.row.piecePrice + ' 元/片' : '-' }}
                  </template>
                </el-table-column>
                <el-table-column label="平方价格" prop="squarePrice" min-width="100">
                  <template #default="scope">
                    {{ scope.row.squarePrice ? scope.row.squarePrice + ' 元/㎡' : '-' }}
                  </template>
                </el-table-column>
                <el-table-column label="状态" align="center" width="100">
                  <template #default="scope">
                    <dict-tag :options="sys_normal_disable" :value="scope.row.status" />
                  </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
                  <template #default="scope">
                    <el-button
                      type="text"
                      icon="Edit"
                      @click="handleUpdate(scope.row)"
                      v-hasPermi="['tile:price:edit']"
                    >修改</el-button>
                    <el-button
                      type="text"
                      icon="Delete"
                      @click="handleDelete(scope.row)"
                      v-hasPermi="['tile:price:remove']"
                    >删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
    </el-row>

    <pagination
      v-show="data.total>0"
      :total="data.total"
      v-model:page="data.queryParams.pageNum"
      v-model:limit="data.queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改供应商价格对话框 -->
    <el-dialog :title="data.title" v-model="data.open" width="800px" append-to-body>
      <el-form ref="priceRef" :model="data.form" :rules="data.rules" label-width="100px">
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="data.form.supplierId" placeholder="请选择供应商">
            <el-option
              v-for="item in data.supplierOptions"
              :key="item.supplierId"
              :label="item.supplierName"
              :value="item.supplierId"
            />
          </el-select>
        </el-form-item>

        <!-- 品类列表 -->
        <div v-for="(item, index) in data.form.categoryList" :key="index" class="category-card">
          <div class="category-header">
            <span>品类 {{ index + 1 }}</span>
            <el-button v-if="index > 0" type="danger" link @click="removePriceItem(index)">删除</el-button>
          </div>
          
          <el-form-item
            :label="'品类名称'"
            :prop="'categoryList.' + index + '.categoryName'"
            :rules="data.rules.categoryName"
          >
            <el-input v-model="item.categoryName" placeholder="请输入品类名称" />
          </el-form-item>
          
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="规格" :prop="'categoryList.' + index + '.specification'">
                <el-input v-model="item.specification" placeholder="请输入规格" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="型号" :prop="'categoryList.' + index + '.model'">
                <el-input v-model="item.model" placeholder="请输入型号" />
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="单价" :prop="'categoryList.' + index + '.piecePrice'">
                <el-input-number v-model="item.piecePrice" :precision="2" :step="0.01" placeholder="元/片" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="平方价格" :prop="'categoryList.' + index + '.squarePrice'">
                <el-input-number v-model="item.squarePrice" :precision="2" :step="0.01" placeholder="元/㎡" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="状态" :prop="'categoryList.' + index + '.status'">
                <el-radio-group v-model="item.status">
                  <el-radio
                    v-for="dict in sys_normal_disable"
                    :key="dict.value"
                    :label="dict.value"
                  >{{ dict.label }}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-form-item label="备注" :prop="'categoryList.' + index + '.remark'">
            <el-input v-model="item.remark" type="textarea" placeholder="请输入备注" />
          </el-form-item>
        </div>

        <div class="mt-4 text-center">
          <el-button type="primary" plain @click="addPriceItem">添加品类</el-button>
        </div>
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

<script setup name="TileSupplierPrice">
import { ref, reactive, onMounted } from 'vue';
import { listPrice, getPrice, delPrice, addPrice, addPrices, updatePrice, updatePrices, exportPrice, getSupplierPrices } from "@/api/tile/price";
import { listSupplier } from "@/api/tile/supplier";

const { proxy } = getCurrentInstance();
const { sys_normal_disable } = proxy.useDict("sys_normal_disable");

const data = reactive({
  // 遮罩层
  loading: true,
  supplierLoading: false,
  // 选中数组
  ids: [],
  // 非单个禁用
  single: true,
  // 非多个禁用
  multiple: true,
  // 显示搜索条件
  showSearch: true,
  // 总条数
  total: 0,
  // 供应商价格表格数据
  priceList: [],
  // 供应商选项
  supplierOptions: [],
  // 弹出层标题
  title: "",
  // 是否显示弹出层
  open: false,
  // 查询参数
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    supplierId: null,
    categoryName: null,
    status: null
  },
  // 展开的供应商
  activeSuppliers: [],
  // 表单参数
  form: {},
  // 表单校验
  rules: {
    supplierId: [
      { required: true, message: "供应商不能为空", trigger: "blur" }
    ],
    categoryName: [
      { required: true, message: "品类名称不能为空", trigger: "blur" }
    ]
  }
});

// 加载供应商列表
const loadSupplierOptions = async () => {
  data.supplierLoading = true;
  try {
    const response = await listSupplier();
    data.supplierOptions = response.rows;
  } catch (error) {
    console.error("加载供应商列表失败:", error);
  } finally {
    data.supplierLoading = false;
  }
};

// 选择供应商
const handleSupplierSelect = (row) => {
  if (row) {
    data.queryParams.supplierId = row.supplierId;
    handleQuery();
  }
};

// 新增供应商
const handleAddSupplier = () => {
  proxy.$router.push('/partner/supplier');
};

// 修改供应商
const handleEditSupplier = (row) => {
  proxy.$router.push({
    path: '/partner/supplier',
    query: { supplierId: row.supplierId }
  });
};

/** 查询供应商价格列表 */
const getList = async () => {
  data.loading = true;
  try {
    const response = await listPrice(data.queryParams);
    data.priceList = response.rows;
    data.total = response.total;
    
    // 如果有数据，默认展开第一个供应商
    if (data.priceList.length > 0) {
      data.activeSuppliers = [data.priceList[0].supplierId];
    }
  } catch (error) {
    console.error("获取价格列表失败:", error);
  } finally {
    data.loading = false;
  }
};

// 页面加载时
onMounted(() => {
  loadSupplierOptions();
  getList();
});

// 取消按钮
function cancel() {
  data.open = false;
  reset();
}

// 表单重置
function reset() {
  data.form = {
    priceId: undefined,
    supplierId: undefined,
    categoryList: [{
      categoryName: undefined,
      specification: undefined,
      model: undefined,
      piecePrice: undefined,
      squarePrice: undefined,
      unitPrice: undefined,
      status: "0",
      remark: undefined
    }]
  };
  proxy.resetForm("priceRef");
}

// 搜索按钮操作
function handleQuery() {
  data.queryParams.pageNum = 1;
  getList();
}

// 重置按钮操作
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  data.ids = selection.map(item => item.priceId);
  data.single = selection.length != 1;
  data.multiple = !selection.length;
}

// 新增按钮操作
function handleAdd() {
  reset();
  data.open = true;
  data.title = "添加供应商价格";
}

// 修改按钮操作
async function handleUpdate(row) {
  reset();
  const priceId = row.priceId;
  const supplierId = row.supplierId || data.priceList.find(s => s.categoryList.includes(row))?.supplierId;
  
  if (supplierId) {
    const response = await getSupplierPrices(supplierId);
    const prices = response.data || [];
    data.form = {
      supplierId: supplierId,
      categoryList: prices
    };
    data.title = "修改供应商价格";
    data.open = true;
  }
}

// 检查是否有重复的品类
function checkDuplicates(list) {
  const seen = new Set();
  
  for (const item of list) {
    if (!item.categoryName || !item.specification || !item.model) {
      continue;  // 跳过未填写完整的项
    }

    const key = `${item.categoryName}-${item.specification}-${item.model}`;
    if (seen.has(key)) {
      return true;  // 发现重复
    }
    seen.add(key);
  }
  return false;
}

// 添加品类
function addPriceItem() {
  data.form.categoryList.push({
    categoryName: undefined,
    specification: undefined,
    model: undefined,
    piecePrice: undefined,
    squarePrice: undefined,
    unitPrice: undefined,
    status: "0",
    remark: undefined
  });
}

// 删除品类
function removePriceItem(index) {
  data.form.categoryList.splice(index, 1);
}

// 提交按钮
function submitForm() {
  // 检查是否有重复的品类
  if (checkDuplicates(data.form.categoryList)) {
    proxy.$modal.msgError("同一供应商下不能有重复的品类、规格和型号组合，请检查后重试");
    return;
  }

  proxy.$refs["priceRef"].validate(valid => {
    if (valid) {
      const prices = data.form.categoryList.map(item => ({
        ...item,
        supplierId: data.form.supplierId
      }));
      
      // 根据是否有priceId判断是新增还是修改
      const hasUpdate = prices.some(item => item.priceId);
      if (hasUpdate) {
        updatePrices(prices).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          data.open = false;
          getList();
        });
      } else {
        addPrices(prices).then(() => {
          proxy.$modal.msgSuccess("新增成功");
          data.open = false;
          getList();
        });
      }
    }
  });
}

// 删除按钮操作
function handleDelete(row) {
  const priceIds = row.priceId || data.ids;
  proxy.$modal.confirm('是否确认删除供应商价格编号为"' + priceIds + '"的数据项？').then(function() {
    return delPrice(priceIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

// 导出按钮操作
function handleExport() {
  proxy.download('tile/price/export', {
    ...data.queryParams
  }, `供应商价格列表_${new Date().getTime()}.xlsx`);
}

// 添加测试数据
function addTestData() {
  const testData = {
    supplierId: data.supplierOptions[0]?.supplierId,
    categoryList: [
      {
        categoryName: "瓷砖A",
        specification: "600x600",
        model: "A001",
        piecePrice: 50,
        squarePrice: 150,
        unitPrice: 45,
        status: "0"
      },
      {
        categoryName: "瓷砖B",
        specification: "800x800",
        model: "B001",
        piecePrice: 80,
        squarePrice: 200,
        unitPrice: 75,
        status: "0"
      }
    ]
  };

  if (testData.supplierId) {
    addPrices(testData.categoryList.map(item => ({
      ...item,
      supplierId: testData.supplierId
    }))).then(response => {
      proxy.$modal.msgSuccess("添加测试数据成功");
      getList();
    }).catch(error => {
      console.error('Add test data error:', error);
      proxy.$modal.msgError("添加测试数据失败");
    });
  } else {
    proxy.$modal.msgError("请先添加供应商");
  }
}
</script>

<style scoped>
.box-card {
  margin-bottom: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.supplier-title {
  display: flex;
  align-items: center;
  gap: 10px;
}
.supplier-count {
  font-size: 13px;
  color: #909399;
}
.right-buttons {
  display: flex;
  gap: 10px;
}
.category-card {
  margin-bottom: 16px;
}
.category-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.mt-4 {
  margin-top: 16px;
}
.text-center {
  text-align: center;
}
</style>

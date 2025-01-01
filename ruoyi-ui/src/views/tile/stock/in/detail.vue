<template>
  <div class="app-container">
    <el-descriptions title="入库单信息" :column="2" border>
      <el-descriptions-item label="入库单号">{{ form.inCode }}</el-descriptions-item>
      <el-descriptions-item label="仓库">{{ form.warehouseName }}</el-descriptions-item>
      <el-descriptions-item label="状态">
        <dict-tag :options="sys_normal_disable" :value="form.status"/>
      </el-descriptions-item>
      <el-descriptions-item label="备注">{{ form.remark }}</el-descriptions-item>
      <el-descriptions-item label="创建人">{{ form.createBy }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ parseTime(form.createTime) }}</el-descriptions-item>
      <el-descriptions-item label="更新人" v-if="form.updateBy">{{ form.updateBy }}</el-descriptions-item>
      <el-descriptions-item label="更新时间" v-if="form.updateTime">{{ parseTime(form.updateTime) }}</el-descriptions-item>
    </el-descriptions>

    <el-divider content-position="center">入库明细</el-divider>

    <el-table :data="form.details" border>
      <el-table-column type="index" label="序号" width="50" />
      <el-table-column label="商品名称" prop="goodsName" />
      <el-table-column label="数量" prop="quantity" />
    </el-table>

    <div class="mb8" style="margin-top: 20px;">
      <el-button @click="goBack">返回</el-button>
    </div>
  </div>
</template>

<script setup name="TileStockInDetail">
import { getStockIn } from "@/api/tile/stock";

const { proxy } = getCurrentInstance();
const { sys_normal_disable } = proxy.useDict("sys_normal_disable");

const form = ref({});
const route = useRoute();

/** 查询入库单详细 */
function getInfo() {
  const inId = route.query.inId;
  getStockIn(inId).then(response => {
    form.value = response.data;
  });
}

/** 返回按钮操作 */
function goBack() {
  proxy.$router.go(-1);
}

getInfo();
</script>

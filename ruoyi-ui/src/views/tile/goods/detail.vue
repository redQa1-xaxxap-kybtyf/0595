<template>
  <el-dialog :title="title" v-model="open" width="800px" append-to-body>
    <el-descriptions :column="2" border>
      <el-descriptions-item label="商品编号">{{ form.goodsCode }}</el-descriptions-item>
      <el-descriptions-item label="商品名称">{{ form.goodsName }}</el-descriptions-item>
      <el-descriptions-item label="商品分类">{{ form.categoryName }}</el-descriptions-item>
      <el-descriptions-item label="规格尺寸">{{ form.specName }}</el-descriptions-item>
      <el-descriptions-item label="材质">{{ form.materialName }}</el-descriptions-item>
      <el-descriptions-item label="表面处理">{{ form.surfaceName }}</el-descriptions-item>
      <el-descriptions-item label="用途">{{ form.usageName }}</el-descriptions-item>
      <el-descriptions-item label="图案">{{ form.patternName }}</el-descriptions-item>
      <el-descriptions-item label="商品状态">{{ form.status === '0' ? '上架' : '下架' }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ form.createTime }}</el-descriptions-item>
      <el-descriptions-item label="商品描述" :span="2">{{ form.goodsDesc }}</el-descriptions-item>
    </el-descriptions>

    <div class="image-preview" v-if="form.goodsImage || form.goodsImages">
      <h4>商品图片</h4>
      <div class="image-list">
        <el-image 
          v-if="form.goodsImage"
          class="preview-image"
          :src="form.goodsImage"
          :preview-src-list="imageList"
          fit="cover"
        />
        <el-image
          v-for="(image, index) in additionalImages"
          :key="index"
          class="preview-image"
          :src="image"
          :preview-src-list="imageList"
          fit="cover"
        />
      </div>
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, computed } from 'vue'

const emit = defineEmits(['update:modelValue'])

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  title: {
    type: String,
    default: ''
  }
})

const open = computed({
  get: () => props.modelValue,
  set: (value) => {
    emit('update:modelValue', value)
  }
})

const form = ref({
  goodsId: undefined,
  goodsCode: undefined,
  goodsName: undefined,
  goodsImage: undefined,
  goodsImages: undefined,
  categoryId: undefined,
  categoryName: undefined,
  specId: undefined,
  specName: undefined,
  materialId: undefined,
  materialName: undefined,
  surfaceId: undefined,
  surfaceName: undefined,
  usageId: undefined,
  usageName: undefined,
  patternId: undefined,
  patternName: undefined,
  goodsDesc: undefined,
  status: '0',
  createTime: undefined
})

const imageList = computed(() => {
  const list = []
  if (form.value.goodsImage) {
    list.push(form.value.goodsImage)
  }
  if (form.value.goodsImages) {
    list.push(...form.value.goodsImages.split(',').filter(Boolean))
  }
  return list
})

const additionalImages = computed(() => {
  if (!form.value.goodsImages) return []
  return form.value.goodsImages.split(',').filter(Boolean)
})

const cancel = () => {
  open.value = false
}

defineExpose({
  form
})
</script>

<style scoped>
.image-preview {
  margin-top: 20px;
  border-top: 1px solid #eee;
  padding-top: 20px;
}
.image-preview h4 {
  margin-bottom: 15px;
  color: #606266;
}
.image-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}
.preview-image {
  width: 150px;
  height: 150px;
  border-radius: 4px;
  overflow: hidden;
}
</style>

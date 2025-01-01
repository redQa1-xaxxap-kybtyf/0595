<template>
  <el-dialog :title="title" v-model="open" width="800px" append-to-body>
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="商品编号" prop="goodsCode">
            <el-input v-model="form.goodsCode" placeholder="请输入商品编号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="商品名称">
            <el-input v-model="form.goodsName" placeholder="请输入商品名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="商品分类" prop="categoryId">
            <el-cascader
              v-model="form.categoryId"
              :options="categoryOptions"
              :props="{ 
                checkStrictly: true,
                value: 'categoryId',
                label: 'categoryName',
                children: 'children',
                expandTrigger: 'hover',
                emitPath: false
              }"
              :show-all-levels="true"
              placeholder="请选择商品分类"
              clearable
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="规格尺寸" prop="specId">
            <el-select v-model="form.specId" placeholder="请选择规格尺寸">
              <el-option
                v-for="item in specOptions"
                :key="item.specId"
                :label="item.specName"
                :value="item.specId"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="材质">
            <el-select v-model="form.materialId" placeholder="请选择材质">
              <el-option
                v-for="item in materialOptions"
                :key="item.materialId"
                :label="item.materialName"
                :value="item.materialId"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="表面处理">
            <el-select v-model="form.surfaceId" placeholder="请选择表面处理">
              <el-option
                v-for="item in surfaceOptions"
                :key="item.surfaceId"
                :label="item.surfaceName"
                :value="item.surfaceId"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用途">
            <el-select v-model="form.usageId" placeholder="请选择用途">
              <el-option
                v-for="item in usageOptions"
                :key="item.usageId"
                :label="item.usageName"
                :value="item.usageId"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="图案">
            <el-select v-model="form.patternId" placeholder="请选择图案">
              <el-option
                v-for="item in patternOptions"
                :key="item.patternId"
                :label="item.patternName"
                :value="item.patternId"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态">
            <el-radio-group v-model="form.status">
              <el-radio :value="'0'">上架</el-radio>
              <el-radio :value="'1'">下架</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="商品图片" prop="goodsImage">
            <el-upload
              class="avatar-uploader"
              :action="uploadImgUrl"
              :headers="headers"
              :show-file-list="false"
              :on-success="handleUploadSuccess"
              :before-upload="beforeUpload"
            >
              <img v-if="form.goodsImage" :src="form.goodsImage" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { getToken } from '@/utils/auth'

const emit = defineEmits(['update:modelValue', 'change', 'reset'])
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  title: {
    type: String,
    default: ''
  },
  categoryOptions: {
    type: Array,
    default: () => []
  },
  specOptions: {
    type: Array,
    default: () => []
  },
  materialOptions: {
    type: Array,
    default: () => []
  },
  surfaceOptions: {
    type: Array,
    default: () => []
  },
  usageOptions: {
    type: Array,
    default: () => []
  },
  patternOptions: {
    type: Array,
    default: () => []
  }
})

const open = computed({
  get: () => props.modelValue,
  set: (value) => {
    emit('update:modelValue', value)
  }
})

const formRef = ref()
const uploadImgUrl = import.meta.env.VITE_APP_BASE_API + '/common/upload'
const headers = ref({ Authorization: 'Bearer ' + getToken() })

const form = ref({
  goodsId: undefined,
  goodsCode: undefined,
  goodsName: undefined,
  goodsImage: undefined,
  categoryId: undefined,
  specId: undefined,
  materialId: undefined,
  surfaceId: undefined,
  usageId: undefined,
  patternId: undefined,
  status: '0'
})

const rules = {
  goodsCode: [
    { required: true, message: '商品编号不能为空', trigger: 'blur' }
  ],
  categoryId: [
    { required: true, message: '商品分类不能为空', trigger: 'change' }
  ],
  specId: [
    { required: true, message: '规格尺寸不能为空', trigger: 'change' }
  ],
  goodsImage: [
    { required: true, message: '商品主图不能为空', trigger: 'change' }
  ]
}

function handleUploadSuccess(response) {
  form.value.goodsImage = response.url
}

function beforeUpload(file) {
  const isJPG = file.type === 'image/jpeg'
  const isPNG = file.type === 'image/png'
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJPG && !isPNG) {
    ElMessage.error('上传图片只能是 JPG/PNG 格式!')
    return false
  }
  if (!isLt2M) {
    ElMessage.error('上传图片大小不能超过 2MB!')
    return false
  }
  return true
}

function submitForm() {
  formRef.value?.validate((valid) => {
    if (valid) {
      emit('change', form.value)
    }
  })
}

function cancel() {
  reset()
  open.value = false
}

function reset() {
  formRef.value?.resetFields()
  form.value = {
    goodsId: undefined,
    goodsCode: undefined,
    goodsName: undefined,
    goodsImage: undefined,
    categoryId: undefined,
    specId: undefined,
    materialId: undefined,
    surfaceId: undefined,
    usageId: undefined,
    patternId: undefined,
    status: '0'
  }
}

defineExpose({
  form,
  reset
})
</script>

<style scoped>
.avatar-uploader {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader:hover {
  border-color: var(--el-color-primary);
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
  line-height: 178px;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<template>
  <div class="content-edit-container">
    <el-card class="edit-card">
      <template #header>
        <div class="card-header">
          <span>{{ isEdit ? "编辑内容" : "创建新内容" }}</span>
        </div>
      </template>

      <el-form
        :model="contentForm"
        :rules="rules"
        ref="formRef"
        label-width="80px"
      >
        <el-form-item label="标题" prop="title">
          <el-input
            v-model="contentForm.title"
            placeholder="请输入内容标题"
          ></el-input>
        </el-form-item>

        <el-form-item label="分类" prop="category">
          <el-select
            v-model="contentForm.category"
            placeholder="请选择内容分类"
          >
            <el-option label="校园新闻" value="campus_news"></el-option>
            <el-option label="通知公告" value="announcement"></el-option>
            <el-option label="学术活动" value="academic"></el-option>
            <el-option label="招生就业" value="employment"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="摘要" prop="summary">
          <el-input
            v-model="contentForm.summary"
            type="textarea"
            :rows="3"
            placeholder="请输入内容摘要"
          ></el-input>
        </el-form-item>

        <el-form-item label="封面图">
          <el-upload
            class="cover-uploader"
            action="/api/content-production/upload/image"
            :show-file-list="false"
            :on-success="handleCoverSuccess"
            :before-upload="beforeCoverUpload"
          >
            <img
              v-if="contentForm.coverUrl"
              :src="contentForm.coverUrl"
              class="cover-image"
            />
            <el-icon v-else class="cover-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <rich-text-editor
            v-model="contentForm.content"
            placeholder="请输入正文内容..."
          ></rich-text-editor>
        </el-form-item>

        <el-form-item label="标签">
          <el-tag
            v-for="tag in contentForm.tags"
            :key="tag"
            closable
            :disable-transitions="false"
            @close="handleTagClose(tag)"
          >
            {{ tag }}
          </el-tag>
          <el-input
            v-if="inputTagVisible"
            ref="tagInputRef"
            v-model="inputTagValue"
            class="tag-input"
            size="small"
            @keyup.enter="handleTagConfirm"
            @blur="handleTagConfirm"
          />
          <el-button
            v-else
            class="button-new-tag"
            size="small"
            @click="showTagInput"
          >
            + 添加标签
          </el-button>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm">发布</el-button>
          <el-button @click="saveAsDraft">保存草稿</el-button>
          <el-button @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { Plus } from "@element-plus/icons-vue";
import axios from "axios";
import type { FormInstance, FormRules } from "element-plus";
import { ElMessage } from "element-plus";
import { nextTick, onMounted, reactive, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import RichTextEditor from "../../components/editor/RichTextEditor.vue";

const route = useRoute();
const router = useRouter();
const formRef = ref<FormInstance>();
const isEdit = ref(false);
const contentId = ref("");

// 表单数据
const contentForm = reactive({
  title: "",
  category: "",
  summary: "",
  content: "",
  coverUrl: "",
  tags: [] as string[],
  status: 1, // 1-发布 0-草稿
});

// 表单验证规则
const rules = reactive<FormRules>({
  title: [
    { required: true, message: "请输入标题", trigger: "blur" },
    { min: 2, max: 100, message: "长度在 2 到 100 个字符", trigger: "blur" },
  ],
  category: [{ required: true, message: "请选择分类", trigger: "change" }],
  summary: [{ required: true, message: "请输入摘要", trigger: "blur" }],
  content: [{ required: true, message: "请输入内容", trigger: "blur" }],
});

// 标签相关
const inputTagVisible = ref(false);
const inputTagValue = ref("");
const tagInputRef = ref<HTMLInputElement>();

// 显示标签输入框
const showTagInput = () => {
  inputTagVisible.value = true;
  nextTick(() => {
    tagInputRef.value?.focus();
  });
};

// 处理标签确认
const handleTagConfirm = () => {
  if (inputTagValue.value) {
    if (contentForm.tags.indexOf(inputTagValue.value) === -1) {
      contentForm.tags.push(inputTagValue.value);
    }
  }
  inputTagVisible.value = false;
  inputTagValue.value = "";
};

// 删除标签
const handleTagClose = (tag: string) => {
  contentForm.tags.splice(contentForm.tags.indexOf(tag), 1);
};

// 处理封面上传成功
const handleCoverSuccess = (response: any) => {
  if (response.code === 200) {
    contentForm.coverUrl = response.data.url;
  }
};

// 上传前验证
const beforeCoverUpload = (file: File) => {
  const isImage = file.type.indexOf("image/") === 0;
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isImage) {
    ElMessage.error("封面图片只能是图片格式!");
    return false;
  }

  if (!isLt2M) {
    ElMessage.error("封面图片大小不能超过 2MB!");
    return false;
  }

  return true;
};

// 提交表单
const submitForm = async () => {
  if (!formRef.value) return;

  await formRef.value.validate(async (valid) => {
    if (valid) {
      contentForm.status = 1; // 发布状态
      try {
        const url = isEdit.value
          ? `/api/content-production/content/${contentId.value}`
          : "/api/content-production/content";

        const method = isEdit.value ? "put" : "post";

        const response = await axios({
          method,
          url,
          data: contentForm,
        });

        if (response.data.code === 200) {
          ElMessage.success("内容发布成功");
          router.push("/content/list");
        } else {
          ElMessage.error(response.data.message || "发布失败");
        }
      } catch (error) {
        console.error("提交失败:", error);
        ElMessage.error("发布失败，请稍后重试");
      }
    }
  });
};

// 保存为草稿
const saveAsDraft = async () => {
  contentForm.status = 0; // 草稿状态
  try {
    const url = isEdit.value
      ? `/api/content-production/content/${contentId.value}`
      : "/api/content-production/content";

    const method = isEdit.value ? "put" : "post";

    const response = await axios({
      method,
      url,
      data: contentForm,
    });

    if (response.data.code === 200) {
      ElMessage.success("草稿保存成功");
      router.push("/content/drafts");
    } else {
      ElMessage.error(response.data.message || "保存失败");
    }
  } catch (error) {
    console.error("保存失败:", error);
    ElMessage.error("保存失败，请稍后重试");
  }
};

// 取消
const cancel = () => {
  router.back();
};

// 获取内容详情
const getContentDetail = async (id: string) => {
  try {
    const response = await axios.get(`/api/content-production/content/${id}`);
    if (response.data.code === 200) {
      const data = response.data.data;
      contentForm.title = data.title;
      contentForm.category = data.category;
      contentForm.summary = data.summary;
      contentForm.content = data.content;
      contentForm.coverUrl = data.coverUrl;
      contentForm.tags = data.tags || [];
      contentForm.status = data.status;
    } else {
      ElMessage.error("获取内容详情失败");
    }
  } catch (error) {
    console.error("获取内容详情失败:", error);
    ElMessage.error("获取内容详情失败");
  }
};

// 组件挂载时，判断是编辑还是新建
onMounted(() => {
  const id = route.params.id as string;
  if (id) {
    isEdit.value = true;
    contentId.value = id;
    getContentDetail(id);
  }
});
</script>

<style scoped>
.content-edit-container {
  padding: 20px;
}

.edit-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.cover-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 178px;
  height: 178px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.cover-uploader:hover {
  border-color: #409eff;
}

.cover-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
  line-height: 178px;
}

.cover-image {
  width: 178px;
  height: 178px;
  display: block;
  object-fit: cover;
}

.tag-input {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

.el-tag {
  margin-right: 10px;
  margin-bottom: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
</style>

<template>
  <div class="post-form-container">
    <el-card class="post-form-card">
      <template #header>
        <div class="card-header">
          <span>{{ isEdit ? "编辑帖子" : "发布新帖" }}</span>
        </div>
      </template>

      <el-form
        :model="postForm"
        :rules="rules"
        ref="formRef"
        label-width="80px"
      >
        <el-form-item label="标题" prop="title">
          <el-input
            v-model="postForm.title"
            placeholder="请输入帖子标题"
          ></el-input>
        </el-form-item>

        <el-form-item label="板块" prop="categoryId">
          <el-select v-model="postForm.categoryId" placeholder="请选择板块">
            <el-option
              v-for="category in categories"
              :key="category.id"
              :label="category.name"
              :value="category.id"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <el-input
            v-model="postForm.content"
            type="textarea"
            :rows="10"
            placeholder="请输入帖子内容"
          >
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm">发布</el-button>
          <el-button @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import axios from "axios";
import type { FormInstance, FormRules } from "element-plus";
import { ElMessage } from "element-plus";
import { onMounted, reactive, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
const formRef = ref<FormInstance>();
const isEdit = ref(false);
const postId = ref("");

// 表单数据
const postForm = reactive({
  title: "",
  content: "",
  categoryId: null as number | null,
  authorId: 1, // 默认作者ID，实际应从用户登录信息获取
  authorName: "默认用户", // 默认作者名，实际应从用户登录信息获取
});

// 板块列表
const categories = ref<any[]>([]);

// 表单验证规则
const rules = reactive<FormRules>({
  title: [
    { required: true, message: "请输入标题", trigger: "blur" },
    { min: 2, max: 100, message: "长度在 2 到 100 个字符", trigger: "blur" },
  ],
  categoryId: [{ required: true, message: "请选择板块", trigger: "change" }],
  content: [{ required: true, message: "请输入内容", trigger: "blur" }],
});

// 提交表单
const submitForm = async () => {
  if (!formRef.value) return;

  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const url = isEdit.value
          ? `/api/forum/posts/${postId.value}`
          : "/api/forum/posts";

        const method = isEdit.value ? "put" : "post";

        const response = await axios({
          method,
          url,
          data: postForm,
        });

        if (response.data.code === 200) {
          ElMessage.success(isEdit.value ? "帖子更新成功" : "帖子发布成功");
          router.push("/forum");
        } else {
          ElMessage.error(response.data.message || "操作失败");
        }
      } catch (error) {
        console.error("提交失败:", error);
        ElMessage.error("操作失败，请稍后重试");
      }
    }
  });
};

// 取消
const cancel = () => {
  router.back();
};

// 获取板块列表
const getCategories = async () => {
  try {
    const response = await axios.get("/api/forum/categories");
    if (response.data.code === 200) {
      categories.value = response.data.data;
    } else {
      ElMessage.error("获取板块列表失败");
    }
  } catch (error) {
    console.error("获取板块列表失败:", error);
    ElMessage.error("获取板块列表失败");
  }
};

// 获取帖子详情
const getPostDetail = async (id: string) => {
  try {
    const response = await axios.get(`/api/forum/posts/${id}`);
    if (response.data.code === 200) {
      const data = response.data.data;
      postForm.title = data.title;
      postForm.content = data.content;
      postForm.categoryId = data.categoryId;
    } else {
      ElMessage.error("获取帖子详情失败");
    }
  } catch (error) {
    console.error("获取帖子详情失败:", error);
    ElMessage.error("获取帖子详情失败");
  }
};

// 组件挂载时，加载板块列表和判断是编辑还是新建
onMounted(async () => {
  await getCategories();

  const id = route.params.id as string;
  if (id) {
    isEdit.value = true;
    postId.value = id;
    await getPostDetail(id);
  }
});
</script>

<style scoped>
.post-form-container {
  padding: 20px;
}

.post-form-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>

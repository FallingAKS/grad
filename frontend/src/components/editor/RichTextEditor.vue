<template>
  <div class="rich-text-editor">
    <div class="editor-toolbar">
      <el-button-group>
        <el-button
          @click="formatText('bold')"
          :class="{ active: formats.bold }"
        >
          <el-icon><Bold /></el-icon>
        </el-button>
        <el-button
          @click="formatText('italic')"
          :class="{ active: formats.italic }"
        >
          <el-icon><Italic /></el-icon>
        </el-button>
        <el-button
          @click="formatText('underline')"
          :class="{ active: formats.underline }"
        >
          <el-icon><Underline /></el-icon>
        </el-button>
      </el-button-group>

      <el-divider direction="vertical" />

      <el-button-group>
        <el-button
          @click="formatText('list', 'ordered')"
          :class="{ active: formats.list === 'ordered' }"
        >
          <el-icon><List /></el-icon>
        </el-button>
        <el-button
          @click="formatText('list', 'bullet')"
          :class="{ active: formats.list === 'bullet' }"
        >
          <el-icon><Menu /></el-icon>
        </el-button>
      </el-button-group>

      <el-divider direction="vertical" />

      <el-button @click="showImageUpload = true">
        <el-icon><Picture /></el-icon>
      </el-button>

      <el-button @click="showVideoUpload = true">
        <el-icon><VideoCamera /></el-icon>
      </el-button>
    </div>

    <div ref="editorElement" class="editor-content"></div>

    <!-- 图片上传对话框 -->
    <el-dialog v-model="showImageUpload" title="上传图片" width="30%">
      <el-upload
        class="upload-demo"
        action="/api/content-production/upload/image"
        :on-success="handleImageSuccess"
        :before-upload="beforeUpload"
        accept="image/*"
        :limit="1"
      >
        <el-button type="primary">选择图片</el-button>
        <template #tip>
          <div class="el-upload__tip">只能上传jpg/png文件，且不超过5MB</div>
        </template>
      </el-upload>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showImageUpload = false">取消</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 视频上传对话框 -->
    <el-dialog v-model="showVideoUpload" title="上传视频" width="30%">
      <el-upload
        class="upload-demo"
        action="/api/content-production/upload/video"
        :on-success="handleVideoSuccess"
        :before-upload="beforeUpload"
        accept="video/*"
        :limit="1"
      >
        <el-button type="primary">选择视频</el-button>
        <template #tip>
          <div class="el-upload__tip">只能上传mp4文件，且不超过50MB</div>
        </template>
      </el-upload>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showVideoUpload = false">取消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import {
  Bold,
  Italic,
  List,
  Menu,
  Picture,
  Underline,
  VideoCamera,
} from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import Quill from "quill";
import "quill/dist/quill.snow.css";
import { onMounted, onUnmounted, ref, watch } from "vue";

const props = defineProps({
  modelValue: {
    type: String,
    default: "",
  },
  placeholder: {
    type: String,
    default: "请输入内容...",
  },
});

const emit = defineEmits(["update:modelValue", "change"]);

const editorElement = ref<HTMLElement | null>(null);
const editor = ref<any>(null);
const formats = ref<Record<string, any>>({});
const showImageUpload = ref(false);
const showVideoUpload = ref(false);

// 初始化编辑器
onMounted(() => {
  if (editorElement.value) {
    editor.value = new Quill(editorElement.value, {
      modules: {
        toolbar: false, // 使用自定义工具栏
      },
      placeholder: props.placeholder,
      theme: "snow",
    });

    // 设置初始内容
    if (props.modelValue) {
      editor.value.root.innerHTML = props.modelValue;
    }

    // 监听编辑器内容变化
    editor.value.on("text-change", () => {
      const html = editor.value.root.innerHTML;
      emit("update:modelValue", html);
      emit("change", html);
    });

    // 监听选择格式变化
    editor.value.on("selection-change", (range: any) => {
      if (range) {
        const format = editor.value.getFormat();
        formats.value = format;
      }
    });
  }
});

// 监听值变化
watch(
  () => props.modelValue,
  (newVal) => {
    if (editor.value && newVal !== editor.value.root.innerHTML) {
      editor.value.root.innerHTML = newVal;
    }
  }
);

// 格式化文本
const formatText = (format: string, value?: any) => {
  if (editor.value) {
    editor.value.format(
      format,
      value === undefined ? !formats.value[format] : value
    );
  }
};

// 上传前验证
const beforeUpload = (file: File) => {
  const isImage = file.type.indexOf("image/") === 0;
  const isVideo = file.type.indexOf("video/") === 0;
  const isLt5M = file.size / 1024 / 1024 < 5;
  const isLt50M = file.size / 1024 / 1024 < 50;

  if (showImageUpload.value && !isImage) {
    ElMessage.error("只能上传图片文件!");
    return false;
  }

  if (showVideoUpload.value && !isVideo) {
    ElMessage.error("只能上传视频文件!");
    return false;
  }

  if (showImageUpload.value && !isLt5M) {
    ElMessage.error("图片大小不能超过5MB!");
    return false;
  }

  if (showVideoUpload.value && !isLt50M) {
    ElMessage.error("视频大小不能超过50MB!");
    return false;
  }

  return true;
};

// 图片上传成功
const handleImageSuccess = (response: any) => {
  if (response.code === 200 && editor.value) {
    const range = editor.value.getSelection(true);
    editor.value.insertEmbed(range.index, "image", response.data.url);
    editor.value.setSelection(range.index + 1);
  }
  showImageUpload.value = false;
};

// 视频上传成功
const handleVideoSuccess = (response: any) => {
  if (response.code === 200 && editor.value) {
    const range = editor.value.getSelection(true);
    editor.value.insertEmbed(range.index, "video", response.data.url);
    editor.value.setSelection(range.index + 1);
  }
  showVideoUpload.value = false;
};

// 组件销毁时清理
onUnmounted(() => {
  if (editor.value) {
    editor.value = null;
  }
});
</script>

<style scoped>
.rich-text-editor {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}

.editor-toolbar {
  padding: 8px;
  border-bottom: 1px solid #dcdfe6;
  display: flex;
  align-items: center;
}

.editor-content {
  min-height: 300px;
  padding: 12px;
}

.active {
  background-color: #ecf5ff;
  color: #409eff;
}

:deep(.ql-editor) {
  min-height: 300px;
}

:deep(.el-upload) {
  width: 100%;
}
</style>

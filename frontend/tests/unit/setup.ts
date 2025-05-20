import { config } from "@vue/test-utils";
import ElementPlus from "element-plus";
import { createApp } from "vue";

// 创建 Vue 应用实例
const app = createApp({});
app.use(ElementPlus);

// 配置 Vue Test Utils
config.global.plugins = [ElementPlus];
config.global.stubs = {
  "el-button": true,
  "el-input": true,
  "el-form": true,
  "el-form-item": true,
  "el-select": true,
  "el-option": true,
  "el-pagination": true,
  "el-table": true,
  "el-table-column": true,
  "el-dialog": true,
  "el-message": true,
  "el-loading": true,
};

// 模拟 window.matchMedia
Object.defineProperty(window, "matchMedia", {
  writable: true,
  value: jest.fn().mockImplementation((query) => ({
    matches: false,
    media: query,
    onchange: null,
    addListener: jest.fn(),
    removeListener: jest.fn(),
    addEventListener: jest.fn(),
    removeEventListener: jest.fn(),
    dispatchEvent: jest.fn(),
  })),
});

// 模拟 ResizeObserver
window.ResizeObserver = jest.fn().mockImplementation(() => ({
  observe: jest.fn(),
  unobserve: jest.fn(),
  disconnect: jest.fn(),
}));

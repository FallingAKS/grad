import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import { config } from "@vue/test-utils";
import ElementPlus from "element-plus";

// 注册所有 Element Plus 组件
config.global.plugins = [ElementPlus];

// 注册所有 Element Plus 图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  config.global.components[key] = component;
}

// Mock window.matchMedia
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

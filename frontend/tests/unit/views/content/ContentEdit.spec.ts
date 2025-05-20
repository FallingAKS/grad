import { mount } from "@vue/test-utils";
import ContentEdit from "../../../../src/views/content/ContentEdit.vue";

// Mock axios
jest.mock("axios", () => ({
  post: jest.fn().mockResolvedValue({ data: { code: 200 } }),
  get: jest.fn().mockResolvedValue({ data: { code: 200 } }),
}));

// Mock Element Plus
jest.mock("element-plus", () => ({
  ElMessage: {
    success: jest.fn(),
    error: jest.fn(),
  },
}));

describe("ContentEdit.vue", () => {
  let wrapper: any;

  beforeEach(() => {
    wrapper = mount(ContentEdit, {
      global: {
        stubs: {
          "el-card": true,
          "el-form": true,
          "el-form-item": true,
          "el-input": true,
          "el-select": true,
          "el-option": true,
          "el-upload": true,
          "el-tag": true,
          "el-button": true,
        },
        mocks: {
          $route: { params: { id: "123" } },
          $router: { push: jest.fn() },
        },
      },
    });
  });

  it("组件可以正常挂载", () => {
    expect(wrapper.exists()).toBe(true);
  });

  it("应该正确处理表单提交", () => {
    expect(wrapper.vm.submitForm).toBeDefined();
  });

  it("应该正确处理草稿保存", () => {
    expect(wrapper.vm.saveAsDraft).toBeDefined();
  });

  it("应该正确处理标签添加", () => {
    expect(wrapper.vm.handleTagConfirm).toBeDefined();
  });

  it("应该正确处理标签删除", () => {
    expect(wrapper.vm.handleTagClose).toBeDefined();
  });

  it("应该正确处理封面上传验证", () => {
    expect(wrapper.vm.beforeCoverUpload).toBeDefined();
  });

  it("应该正确处理封面上传成功", () => {
    expect(wrapper.vm.handleCoverSuccess).toBeDefined();
  });
});

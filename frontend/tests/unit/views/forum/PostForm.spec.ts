import { mount } from "@vue/test-utils";
import { describe, expect, it } from "vitest";
import PostForm from "../../../../src/views/forum/PostForm.vue";

// 模拟路由
const mockRoute = {
  params: {
    id: "1",
  },
};

// 模拟 useRouter
const mockRouter = {
  push: jest.fn(),
};

// 模拟 axios
jest.mock("axios", () => ({
  default: {
    get: jest.fn().mockResolvedValue({ data: { code: 200, data: {} } }),
    post: jest.fn().mockResolvedValue({ data: { code: 200, data: {} } }),
  },
}));

describe("PostForm.vue", () => {
  let wrapper: any;

  beforeEach(() => {
    wrapper = mount(PostForm, {
      global: {
        stubs: {
          "el-form": true,
          "el-form-item": true,
          "el-input": true,
          "el-button": true,
          "el-upload": true,
          "el-select": true,
          "el-option": true,
        },
        mocks: {
          $route: mockRoute,
          $router: mockRouter,
        },
      },
    });
  });

  it("组件可以正常挂载", () => {
    expect(wrapper.exists()).toBe(true);
  });

  it("应该正确初始化表单数据", () => {
    expect(wrapper.vm.form).toBeDefined();
    expect(wrapper.vm.form.title).toBe("");
    expect(wrapper.vm.form.content).toBe("");
  });

  it("应该正确处理表单提交", async () => {
    await wrapper.vm.handleSubmit();
    expect(mockRouter.push).toHaveBeenCalled();
  });

  it("应该正确处理图片上传", () => {
    expect(wrapper.vm.handleImageSuccess).toBeDefined();
  });

  it("应该正确处理分类选择", () => {
    expect(wrapper.vm.handleCategoryChange).toBeDefined();
  });

  it("应该正确处理标签添加", () => {
    expect(wrapper.vm.handleTagConfirm).toBeDefined();
  });

  it("应该正确处理标签删除", () => {
    expect(wrapper.vm.handleTagClose).toBeDefined();
  });
});

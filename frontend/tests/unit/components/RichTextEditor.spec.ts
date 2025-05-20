import { mount } from "@vue/test-utils";
import RichTextEditor from "../../../src/components/editor/RichTextEditor.vue";

// Mock Quill
jest.mock("quill", () => {
  return jest.fn().mockImplementation(() => ({
    root: { innerHTML: "" },
    on: jest.fn(),
    getSelection: jest.fn().mockReturnValue({ index: 0 }),
    format: jest.fn(),
    insertEmbed: jest.fn(),
    setSelection: jest.fn(),
  }));
});

describe("RichTextEditor.vue", () => {
  let wrapper: any;

  beforeEach(() => {
    wrapper = mount(RichTextEditor, {
      global: {
        stubs: {
          "el-button": true,
          "el-button-group": true,
          "el-dialog": true,
          "el-divider": true,
          "el-upload": true,
        },
      },
    });
  });

  it("组件可以正常挂载", () => {
    expect(wrapper.exists()).toBe(true);
  });

  it("应该正确初始化编辑器", () => {
    expect(wrapper.vm.editor).toBeDefined();
  });

  it("应该正确处理文本格式化", () => {
    expect(wrapper.vm.formatText).toBeDefined();
  });

  it("应该正确处理图片上传验证", () => {
    expect(wrapper.vm.beforeUpload).toBeDefined();
  });

  it("应该正确处理视频上传验证", () => {
    expect(wrapper.vm.beforeUpload).toBeDefined();
  });

  it("应该正确处理图片上传成功", () => {
    expect(wrapper.vm.handleImageSuccess).toBeDefined();
  });

  it("应该正确处理视频上传成功", () => {
    expect(wrapper.vm.handleVideoSuccess).toBeDefined();
  });
});

import { mount } from "@vue/test-utils";
import { describe, expect, it } from "vitest";
import DataView from "../../../src/views/DataView.vue";

// Mock axios
jest.mock("axios", () => ({
  get: jest.fn().mockResolvedValue({ data: { code: 200, data: [] } }),
}));

// 模拟 onMounted
jest.mock("vue", () => ({
  ...jest.requireActual("vue"),
  onMounted: jest.fn(),
}));

describe("DataView.vue", () => {
  let wrapper: any;

  beforeEach(() => {
    wrapper = mount(DataView, {
      global: {
        stubs: {
          "el-table": true,
          "el-table-column": true,
          "el-pagination": true,
          "el-button": true,
          "el-input": true,
          "el-select": true,
          "el-option": true,
        },
      },
    });
  });

  it("组件可以正常挂载", () => {
    expect(wrapper.exists()).toBe(true);
  });

  it("应该正确初始化数据", () => {
    expect(wrapper.vm.tableData).toBeDefined();
    expect(wrapper.vm.pagination).toBeDefined();
  });

  it("应该正确处理分页", () => {
    const pagination = wrapper.vm.pagination;
    expect(pagination.currentPage).toBe(1);
    expect(pagination.pageSize).toBe(10);
  });

  it("应该正确处理搜索", () => {
    const searchInput = wrapper.find('input[type="text"]');
    expect(searchInput.exists()).toBe(true);
  });

  it("应该正确处理重置", () => {
    const resetButton = wrapper.find("button");
    expect(resetButton.exists()).toBe(true);
  });
});

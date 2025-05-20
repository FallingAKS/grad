import { beforeEach, describe, expect, it } from "@jest/globals";
import { mount } from "@vue/test-utils";
import axios from "axios";

// 模拟 axios
jest.mock("axios");
const mockedAxios = axios as jest.Mocked<typeof axios>;

// 使用 defineComponent 定义组件，保证 this 指向和类型
const TestComponent = {
  template: `
    <div>
      <input 
        v-model="inputValue" 
        @input="handleInput"
        data-test="input"
      />
      <button 
        @click="handleClick"
        data-test="button"
      >
        {{ buttonText }}
      </button>
      <div v-if="showMessage" data-test="message">
        {{ message }}
      </div>
      <div v-for="item in items" :key="item.id" data-test="list-item">
        {{ item.name }}
      </div>
    </div>
  `,
  data() {
    return {
      inputValue: "",
      buttonText: "Click Me",
      showMessage: false,
      message: "",
      items: [] as { id: number; name: string }[],
    };
  },
  methods: {
    handleInput(this: any, event: Event) {
      this.inputValue = (event.target as HTMLInputElement).value;
    },
    async handleClick(this: any) {
      try {
        const response = await axios.get("/api/data");
        this.items = response.data;
        this.showMessage = true;
        this.message = "Data loaded successfully";
      } catch (error) {
        this.showMessage = true;
        this.message = "Error loading data";
      }
    },
  },
};

describe("组件测试套件", () => {
  let wrapper: any;

  beforeEach(() => {
    // 重置所有模拟
    jest.clearAllMocks();
    // 创建新的包装器
    wrapper = mount(TestComponent);
  });

  // 1. 基础渲染测试
  describe("基础渲染测试", () => {
    it("组件应该正确渲染", () => {
      expect(wrapper.exists()).toBe(true);
    });

    it("应该包含所有必要的元素", () => {
      expect(wrapper.find('[data-test="input"]').exists()).toBe(true);
      expect(wrapper.find('[data-test="button"]').exists()).toBe(true);
    });
  });

  // 2. 数据绑定测试
  describe("数据绑定测试", () => {
    it("输入框应该正确绑定数据", async () => {
      const input = wrapper.find('[data-test="input"]');
      await input.setValue("测试输入");
      expect(wrapper.vm.inputValue).toBe("测试输入");
    });

    it("按钮文本应该正确显示", () => {
      expect(wrapper.find('[data-test="button"]').text()).toBe("Click Me");
    });
  });

  // 3. 事件处理测试
  describe("事件处理测试", () => {
    it("点击按钮应该触发处理函数", async () => {
      const button = wrapper.find('[data-test="button"]');
      await button.trigger("click");
      expect(wrapper.vm.showMessage).toBe(true);
    });

    it("输入框应该触发输入事件", async () => {
      const input = wrapper.find('[data-test="input"]');
      await input.trigger("input");
      expect(wrapper.vm.inputValue).toBeDefined();
    });
  });

  // 4. API 调用测试
  describe("API 调用测试", () => {
    it("成功获取数据时应该正确显示", async () => {
      const mockData = [
        { id: 1, name: "测试项目 1" },
        { id: 2, name: "测试项目 2" },
      ];
      mockedAxios.get.mockResolvedValueOnce({ data: mockData });

      await wrapper.find('[data-test="button"]').trigger("click");
      await wrapper.vm.$nextTick();

      expect(wrapper.vm.items).toEqual(mockData);
      expect(wrapper.vm.message).toBe("Data loaded successfully");
      expect(wrapper.find('[data-test="message"]').exists()).toBe(true);
    });

    it("API 调用失败时应该显示错误信息", async () => {
      mockedAxios.get.mockRejectedValueOnce(new Error("API Error"));

      await wrapper.find('[data-test="button"]').trigger("click");
      await wrapper.vm.$nextTick();

      expect(wrapper.vm.message).toBe("Error loading data");
      expect(wrapper.find('[data-test="message"]').exists()).toBe(true);
    });
  });

  // 5. 条件渲染测试
  describe("条件渲染测试", () => {
    it("初始状态下不应该显示消息", () => {
      expect(wrapper.find('[data-test="message"]').exists()).toBe(false);
    });

    it("设置 showMessage 为 true 时应该显示消息", async () => {
      await wrapper.setData({ showMessage: true, message: "测试消息" });
      expect(wrapper.find('[data-test="message"]').exists()).toBe(true);
      expect(wrapper.find('[data-test="message"]').text()).toBe("测试消息");
    });
  });

  // 6. 列表渲染测试
  describe("列表渲染测试", () => {
    it("应该正确渲染列表项", async () => {
      const items = [
        { id: 1, name: "项目 1" },
        { id: 2, name: "项目 2" },
      ];
      await wrapper.setData({ items });

      const listItems = wrapper.findAll('[data-test="list-item"]');
      expect(listItems).toHaveLength(2);
      expect(listItems[0].text()).toBe("项目 1");
      expect(listItems[1].text()).toBe("项目 2");
    });
  });

  // 7. 组件状态测试
  describe("组件状态测试", () => {
    it("组件状态应该正确初始化", () => {
      expect(wrapper.vm.inputValue).toBe("");
      expect(wrapper.vm.showMessage).toBe(false);
      expect(wrapper.vm.message).toBe("");
      expect(wrapper.vm.items).toEqual([]);
    });

    it("组件状态应该正确更新", async () => {
      await wrapper.setData({
        inputValue: "新值",
        showMessage: true,
        message: "新消息",
        items: [{ id: 1, name: "新项目" }],
      });

      expect(wrapper.vm.inputValue).toBe("新值");
      expect(wrapper.vm.showMessage).toBe(true);
      expect(wrapper.vm.message).toBe("新消息");
      expect(wrapper.vm.items).toHaveLength(1);
    });
  });
});

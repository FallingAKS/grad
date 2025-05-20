import App from "@/App.vue";
import { mount } from "@vue/test-utils";

describe("App.vue", () => {
  it("可以正常挂载", () => {
    const wrapper = mount(App);
    expect(wrapper.exists()).toBe(true);
  });
});

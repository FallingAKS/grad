import { mount } from "@vue/test-utils";
import HomeView from "../../../src/views/HomeView.vue";

describe("HomeView.vue", () => {
  it("组件可以正常挂载", () => {
    const wrapper = mount(HomeView);
    expect(wrapper.exists()).toBe(true);
  });
});

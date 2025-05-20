import { mount } from "@vue/test-utils";
import ContentView from "../../../src/views/ContentView.vue";

describe("ContentView.vue", () => {
  it("组件可以正常挂载", () => {
    const wrapper = mount(ContentView);
    expect(wrapper.exists()).toBe(true);
  });
});

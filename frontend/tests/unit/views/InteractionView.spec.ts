import { mount } from "@vue/test-utils";
import InteractionView from "../../../src/views/InteractionView.vue";

describe("InteractionView.vue", () => {
  it("组件可以正常挂载", () => {
    const wrapper = mount(InteractionView);
    expect(wrapper.exists()).toBe(true);
  });
});

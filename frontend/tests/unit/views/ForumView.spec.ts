import { mount } from "@vue/test-utils";
import ForumView from "../../../src/views/ForumView.vue";

describe("ForumView.vue", () => {
  it("组件可以正常挂载", () => {
    const wrapper = mount(ForumView);
    expect(wrapper.exists()).toBe(true);
  });
});

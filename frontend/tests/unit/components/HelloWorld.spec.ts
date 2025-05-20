import { mount } from "@vue/test-utils";
import HelloWorld from "../../../src/components/HelloWorld.vue";

describe("HelloWorld.vue", () => {
  it("组件可以正常挂载", () => {
    const wrapper = mount(HelloWorld);
    expect(wrapper.exists()).toBe(true);
  });
});

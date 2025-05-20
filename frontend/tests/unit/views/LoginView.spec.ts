import { mount } from "@vue/test-utils";
import LoginView from "../../../src/views/LoginView.vue";

describe("LoginView.vue", () => {
  it("组件可以正常挂载", () => {
    const wrapper = mount(LoginView);
    expect(wrapper.exists()).toBe(true);
  });
});

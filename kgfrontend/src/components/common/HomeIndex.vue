<!-- 将菜单栏和主页结合 -->
<template>
  <div>
    <nav-menu></nav-menu>
    <router-view id="routerPage"></router-view>
  </div>
</template>

<script>
import NavMenu from '../common/NavMenu.vue'
export default {
  data() {
    return {}
  },
  components: { NavMenu },
  methods: {}
}
</script>

<style>
#routerPage {
  /*正文内容距离主导航栏的距离*/
  margin-top: 5px;
  /*页面的宽度为1520px,高度为600px*/
  width: 1520px;
  height: 800px;
}
</style>

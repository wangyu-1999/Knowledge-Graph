<!-- 菜单栏 -->
<template>
  <div id="fullPage">
    <!-- 要实现router，注意这个:default-active要用$route.path -->
    <el-menu
      :default-active="$route.path"
      class="el-menu-demo"
      mode="horizontal"
      router
    >
      <span id="titleName" @click="toIndex"
        >科技服务知识图谱编辑与分析工具</span
      >
      <el-submenu index="1">
        <template slot="title">选择功能</template>
        <el-menu-item index="/index">使用说明</el-menu-item>
        <el-menu-item index="/kg_index">知识图谱编辑</el-menu-item>
        <!--
        <el-menu-item index="1-3">选项3</el-menu-item>
        -->
      </el-submenu>
      <el-submenu index="2">
        <template slot="title">账户管理</template>
        <el-menu-item @click="logout">退出登录</el-menu-item>
      </el-submenu>

      <exit-button-item id="exitButton" ref="exit"></exit-button-item>
    </el-menu>
  </div>
</template>

<script scoped>
import ExitButtonItem from './ExitButtonItem.vue'
export default {
  data() {
    return {}
  },
  components: { ExitButtonItem },
  methods: {
    // 调用退出按钮的退出登录方法，复用代码
    logout() {
      this.$refs.exit.logout()
    },
    // 调用后返回首页
    toIndex() {
      var _this = this
      _this.$router.push('/index')
    }
  }
}
</script>

<style scoped>
/*退出按钮的距离调整*/
#exitButton {
  float: right;
  margin: 10px;
}
/*导航栏中标题文字的调整*/
#titleName {
  float: left;
  font-size: 25px;
  margin-top: 15px;
  margin-right: 80px;
  margin-left: 40px;
  outline: none;
  cursor: pointer;
}
/*设置主导航栏的宽度*/
#fullPage {
  width: 1520px;
}
</style>

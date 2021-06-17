<!-- 存放菜单栏里面退出的按钮 -->
<template>
  <div>
    <el-tooltip
      class="item"
      effect="light"
      content="退出登录"
      placement="bottom-end"
    >
      <i class="el-icon-switch-button" @click="logout()"></i>
    </el-tooltip>
  </div>
</template>

<script>
export default {
  data() {
    return {}
  },
  components: {},
  methods: {
    // 点击按钮后向后端 /logout 发送 get 报文，得到一个Result ，查看状态码
    logout() {
      var _this = this
      _this.$axios.get('/logout').then(resp => {
        if (resp.data.code === 200) {
          // 前后端状态保持一致
          _this.$store.commit('logout')
          _this.$router.replace('/login')
        }
      })
    }
  }
}
</script>

<style scoped>
/*退出按钮的大小*/
.el-icon-switch-button {
  cursor: pointer;
  font-size: 40px;
}
/*退出按钮悬停的提示*/
.item {
  margin: 4px;
}
</style>

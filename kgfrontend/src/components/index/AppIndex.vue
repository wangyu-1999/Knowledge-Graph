<!-- 除去菜单栏首页内容 -->
<template>
  <div id="fullPage">
    <el-container>
      <el-main> <info-guide></info-guide></el-main>
      <el-footer>©2020 科技服务知识图谱编辑与分析工具</el-footer>
    </el-container>
  </div>
</template>

<script>
import InfoGuide from './InfoGuide.vue'
export default {
  name: 'Index',
  data() {
    return {}
  },
  components: { InfoGuide },
  methods: {}
}
</script>

<style scoped>
#fullPage {
  width: 1520px;
  text-align: center;
}
.el-footer {
  background-color: #b3c0d1;
  text-align: center;
  line-height: 60px;
}
.el-main {
  background-color: #e9eef3;
  text-align: center;
  line-height: 160px;
  padding: 10px;
}
</style>

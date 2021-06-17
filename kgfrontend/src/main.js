import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
/**
引入风格代码
主色调#204D7B
font-size：12 - 20
font-weight：100 - 500
*/
import './theme/index.css'

Vue.use(ElementUI)

// 设置反向代理，前端请求默认发送到后端端口的/api
var axios = require('axios')
axios.defaults.baseURL =
  'http://' + store.getters.getAddress + ':' + store.getters.getEndPort + '/api'
// 让前端能够带上 cookie，我们需要通过 axios 主动开启 withCredentials 功能
axios.defaults.withCredentials = true
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios
//阻止vue 在启动时生成生产提示
Vue.config.productionTip = false

// 如果前端没有登录信息则直接拦截，如果有则判断后端是否正常登录（防止构造参数绕过）
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    if (store.state.user.username) {
      axios.get('/authentication').then(resp => {
        // console.log(resp)
        if (resp) next()
      })
    } else {
      next({
        path: 'login',
        query: { redirect: to.fullPath }
      })
    }
  } else {
    next()
  }
})

new Vue({
  el: '#app',
  render: h => h(App),
  router,
  // 注意这里
  store,
  components: { App },
  template: '<App/>'
})

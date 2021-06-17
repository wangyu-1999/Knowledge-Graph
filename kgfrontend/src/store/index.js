import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      name:
        window.sessionStorage.getItem('user') == null
          ? '未登录'
          : JSON.parse(window.sessionStorage.getItem('user' || '[]')).name,
      username:
        window.sessionStorage.getItem('user') == null
          ? ''
          : JSON.parse(window.sessionStorage.getItem('user' || '[]')).username
    },
    adminMenus: [],
    address: 'localhost',
    frontPort: '8089',
    endPort: '8443'
  },
  mutations: {
    login(state, data) {
      state.user = data
      window.sessionStorage.setItem('user', JSON.stringify(data))
    },
    logout(state) {
      // 注意不能用 null 清除，否则将无法判断 user 里具体的内容
      state.user = []
      window.sessionStorage.removeItem('user')
      state.adminMenus = []
    }
  },
  actions: {},
  getters: {
    getAddress(state) {
      return state.address
    },
    getFrontPort(state) {
      return state.frontPort
    },
    getEndPort(state) {
      return state.endPort
    }
  }
})

import Vue from 'vue'
import VueRouter from 'vue-router'
import AppIndex from '../components/index/AppIndex.vue'
import LoginIndex from '../components/login/LoginIndex.vue'
import HomeIndex from '../components/common/HomeIndex.vue'
import KnowledgeGraphIndex from '../components/knowledgegraph/KnowledgeGraphIndex.vue'

Vue.use(VueRouter)

export default new VueRouter({
  //路由从默认的 hash 模式切换为 histroy 模式
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'AppIndex',
      redirect: '/index',
      component: AppIndex,
      meta: {
        requireAuth: true
      }
    },

    {
      path: '/login',
      name: 'LoginIndex',
      component: LoginIndex
    },

    {
      path: '/home',
      name: 'HomeIndex',
      component: HomeIndex,
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'AppIndex',
          component: AppIndex,
          meta: {
            requireAuth: true
          }
        },

        {
          path: '/kg_index',
          name: 'KnowledgeGraphIndex',
          component: KnowledgeGraphIndex,
          meta: {
            requireAuth: true
          }
        }
      ]
    }
  ]
})

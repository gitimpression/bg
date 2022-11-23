import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login.vue'
import Test1 from '@/views/Test1.vue'
import Test2 from '@/views/Test2.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
    hidden: true // 用于首页v-for在菜单中隐藏
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/Home.vue'),
    children: [
      {
        path: '/test1',
        name: 'Test1',
        title: '测试1', // 在菜单显示
        component: Test1
      },
      {
        path: '/test2',
        name: 'Test2',
        title: '测试2',
        component: Test2
      },
    ]
  },

  // {
  //   path: '/about',
  //   name: 'About',
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

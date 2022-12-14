import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import { initMenu } from './util/menus'// 初始化菜单
import 'font-awesome/css/font-awesome.min.css' // 引入字体图标库
import Bus from './util/bus'  // 事件总线，通信用

// 公共请求
import { getRequest, postRequest, putRequest, delRequest } from '@/util/api.js'
// key信息
import keysProperties from '@/config/keysProperties'
// 在Vue声明请求函数
Vue.prototype.getRequest = getRequest
Vue.prototype.postRequest = postRequest
Vue.prototype.putRequest = putRequest
Vue.prototype.delRequest = delRequest

Vue.prototype.keysProperties = keysProperties  // 全局使用keysProperties
Vue.prototype.Bus = Bus  // 事件总线，通信用

Vue.config.productionTip = false

Vue.use(ElementUI)

// 路由守卫
router.beforeEach((to, from, next) => {
  if (localStorage.getItem(keysProperties.tokenKey)) {// 已登录
    if (to.path != '/') {  // 不是前往登录页  初始化菜单
      initMenu(router, store)
    }
  } else if (to.path != '/' && to.path != '/register') {  // 未登录 访问的不是登录页
    router.push('/')
  }
  next()
})



new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

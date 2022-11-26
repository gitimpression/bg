import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    routes: [] // 路由数据
  },
  mutations: {
    initRoutes(state, data){// 初始化路由
      state.routes = data
    }
  },
  actions: {
  },
  modules: {
  }
})

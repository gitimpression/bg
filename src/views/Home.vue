<template>
  <div class="home">
    <el-container style="height: 100%">
      <!-- 顶部 -->
      <el-header class="home-header">
        <Header></Header>
      </el-header>
      <el-container>
        <!-- 左侧导航 -->
        <el-aside class="home-aside">
            <Aside></Aside>
        </el-aside>
        <!-- 中间内容 -->
        <el-main>
          <div v-if="this.$router.currentRoute.path == '/home'">
            <welcome></welcome>

          </div>
          <!-- <div v-else>
            <el-breadcrumb separator-class="el-icon-arrow-right">
              <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>活动管理</el-breadcrumb-item>
              <el-breadcrumb-item>活动列表</el-breadcrumb-item>
              <el-breadcrumb-item>活动详情</el-breadcrumb-item>
            </el-breadcrumb>
          </div> -->
          <router-view />
          <ChangePass></ChangePass>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import Header from '@/components/header/Header.vue'
import Aside from '@/components/aside/Aside.vue';
import Welcome from '@/components/main/Welcome.vue';
import ChangePass from '@/components/main/prof/ChangePass.vue'

export default {
  name: "Home",
  data() {
    return {
    }
  },
  components: {
    Aside,
    Header,
    Welcome,
    ChangePass
  },
};
</script>

<style >
.home-aside {
  width: auto!important;;
  height: 100%;
  background-color: rgb(84,92,100);
}
.home-header{
  width: 100%;
  background-color: rgb(84,92,100);
}
.home {
  height: 100%;
}
</style>
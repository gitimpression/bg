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
        <el-main class="home-main">
          <div v-if="this.$router.currentRoute.path == '/home'">
            <welcome></welcome>
          </div>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Header from "@/components/header/Header.vue";
import Aside from "@/components/aside/Aside.vue";
import Welcome from "@/components/main/Welcome.vue";

export default {
  name: "Home",
  data() {
    return {};
  },
  components: {
    Aside,
    Header,
    Welcome,
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
.home-main{
  overflow: scroll;
}
</style>
<template>
  <div>
    <el-container>
      <el-header>Header</el-header>
        <el-container>
          <el-aside width="200px">
            <!-- router 点击菜单时会将index作为path进行跳转 -->
            <el-menu router>
              <!-- 获取路由规则列表 -->
              <el-submenu index="1" 
              v-for="(item,index) in this.$router.options.routes.filter(i => i.hidden!==true)" 
              :key="index">
                <template slot="title">{{item.name}}</template>
                <el-menu-item-group>
                  <!-- 从子路由获取名字与路径 -->
                  <el-menu-item 
                  :index="children.path"
                  v-for="(children,index) in item.children" 
                  :key="index">
                  {{children.title}}</el-menu-item>
                </el-menu-item-group>
              </el-submenu>
            </el-menu>
          </el-aside>
          <el-main>
            <router-view />
          </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { getRequest } from '@/util/api'
export default {
    name: 'Home',
    data() {
        return {
            
        }
    },
    methods: {

    },
    mounted(){
      getRequest("/api/sys/menu",{}).then(res => {
        console.log(res);
      })
    }
}
</script>

<style>

</style>
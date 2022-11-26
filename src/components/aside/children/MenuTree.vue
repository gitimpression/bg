<template>
  <div>
    <el-submenu
      v-if="
        menu.children &&
        menu.children instanceof Array &&
        menu.children.length > 0
      "
      :index="'' + menu.id"
    >
      <template slot="title">
        <i :class="menu.iconClz"></i>
        <span>{{ menu.title }}</span>
      </template>
      <MenuTree v-for="m in menu.children" :menu="m" :key="m.id" />
    </el-submenu>
    <el-menu-item v-else :index="'' + menu.path">
      <i :class="menu.iconClz"></i>
      <span>{{ menu.title }}</span>
    </el-menu-item>
  </div>
</template>

<script>
export default {
  name: "MenuTree",
  props: ["menu"], // 子路由
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style scoped>
/* 收起时隐藏文字和图标 有二级菜单时*/
.el-menu--collapse > div > .el-submenu > .el-submenu__title span {
  height: 0;
  width: 0;
  overflow: hidden;
  visibility: hidden;
  display: inline-block;
}
.el-menu--collapse
  > div
  > .el-submenu
  > .el-submenu__title
  .el-submenu__icon-arrow {
  display: none;
}
/* 没有二级菜单时 */
.el-menu--collapse > div > .el-menu-item span {
  display: none;
}
</style>
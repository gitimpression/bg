<template>
  <div>
    <el-menu
      class="el-menu"
      :router="true"
      :unique-opened="true"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      :collapse="isCollapse"
    >
      <el-tooltip
        class="el-icon-menu-box"
        effect="dark"
        :content="menuIconTips"
        placement="right-start"
      >
        <i
          class="el-icon-menu open"
          @click="collapseClick"
        ></i>
      </el-tooltip>
      <MenuTree v-for="menu in menus" :menu="menu" :key="menu.id" />
    </el-menu>
  </div>
</template>

<script>
import MenuTree from "@/components/aside/children/MenuTree.vue";

export default {
  name: "Aside",
  data() {
    return {
      isCollapse: false,
    };
  },
  methods: {
    collapseClick() {
      this.isCollapse = !this.isCollapse;
      return this.isCollapse;
    },
  },
  computed: {
    menus() {
      return this.$store.state.routes;
    },
    menuIconTips(){
      return this.isCollapse ? "展开菜单" : "折叠菜单"
    }
  },
  components: {
    MenuTree,
  },
};
</script>

<style>
.el-menu:not(.el-menu--collapse) {
  border-right: 0;
  width: 200px;
}
.open {
  color: #efefef;
  cursor: pointer;
  margin-right: 20px;
  margin-bottom: 8px;
}
.open::before {
  font-size: 20px;
}
.el-icon-menu-box {
  display: flex;
  flex-direction: row;
  justify-content: right;
}
</style>
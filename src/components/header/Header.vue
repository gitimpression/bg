<template>
  <div class="header">
    <div class="title">
      <h3>校园办公系统</h3>
    </div>
    <div class="user">
      <el-dropdown trigger="click" 
      @command="handleCommand"
      v-loading.fullscreen="loading"
      element-loading-text="loading..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.2)"
      >
        <span class="el-dropdown-link">
          <span class="username">{{ user.username }}</span
          ><i><img class="head-img" :src="user.headImg" /></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item icon="el-icon-s-tools" command="setting"
            >偏好设置</el-dropdown-item
          >
          <el-dropdown-item icon="el-icon-question" command="help"
            >帮助</el-dropdown-item
          >
          <el-dropdown-item icon="el-icon-remove" command="logout"
            >退出登录</el-dropdown-item
          >
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import keysProperties from "@/config/keysProperties";
import Bus from '@/util/bus'
import { getRequest } from "@/util/api";
export default {
  name: "Header",
  data() {
    return {
      user: {},
      loading: false
    };
  },
  methods: {
    handleCommand(command) {
      switch (command) {
        case "setting": //  设置
          break;
        case "help": // 帮助
          break;
        case "logout": // 退出登录
          this.$confirm("退出登录?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            this.loading = true
            if (localStorage.getItem(keysProperties.tokenKey)) {
              this.postRequest("/api/user/logout").then((res) => {
                this.$store.state.routes = []// 清空路由
                localStorage.removeItem(keysProperties.tokenKey) // 清空token
                localStorage.removeItem(keysProperties.userInfoKey) // 清空个人信息
                this.$message({
                  message: res.msg,
                  type: "success",
                })
                setTimeout(() => {
                  this.$router.push("/"); // 跳转页面
                  this.loading = false
                }, 1500);
              })
            } else {
              this.$message({
                message: "当前不处于登录状态！即将跳转至登录页...",
                type: "warning",
              })
              this.loading = false
            }

          }).catch(() => {
            this.loading = false
          })
          break;
        default:
          break;
      }
    },
  },
  mounted() {
    Bus.$on("headerUserInfo", user => { // 个人信息同步Basic组件
      this.user = user
    }),
    getRequest("/api/user").then(res => {
      if (res.code == 200) {
        this.user = res.data.user;
        console.log();
        localStorage.setItem(keysProperties.userInfoKey,JSON.stringify(this.user))
      } else {
        this.$message({
          message: res.msg,
          type: "warning",
        });
        setTimeout(() => {
          this.$router.push('/')
        })
      }
    });
  },
};
</script> 

<style>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.username {
  color: white;
  cursor: pointer;
}
.head-img {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  cursor: pointer;
  vertical-align: middle;
  margin-left: 8px;
}
.title {
  padding-left: 20px;
  color: #efefef;
}
.user {
  color: white;
}
</style>
<template>
  <div>
    <el-form
      ref="registerForm"
      :model="registerForm"
      class="registerForm"
      :rules="rules"
      v-loading.fullscreen="loading"
      element-loading-text="loading..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.2)"
    >
      <h2 class="registerTitle">用户注册</h2>
      <el-form-item prop="username">
        <el-input
          type="text"
          v-model="registerForm.username"
          placeholder="请输入用户名"
        />
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          v-model="registerForm.password"
          placeholder="请输入密码"
        />
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          v-model="registerForm.passwordAgain"
          placeholder="请再次输入密码"
        />
      </el-form-item>
      <el-button type="primary" @click="register()" class="registerBtn"
        >注册</el-button
      >
      <el-row type="flex" justify="space-between" style="margin: 10px auto">
        <el-col style="width: auto">
          <el-link type="primary" @click="toLogin">已有有账号？去登录</el-link>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
  
  <script>
import validator from "@/util/validator";
import { putRequest } from "@/util/api";
import keysProperties from "@/config/keysProperties";
import local from "@/util/local";
export default {
  name: "Register",
  data() {
    return {
      verifyImgUrl: "",
      loading: false,
      registerForm: {
        username: "",
        password: "",
      },
      rules: {
        username: [
          {
            validator: validator.uName,
            trigger: "blur",
          },
        ],
        password: [
          {
            validator: validator.uPass,
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    toLogin() {
      this.$router.push("/");
    },
    register() {
      this.loading = true;
      this.$refs.registerForm.validate((vaild) => {
        if (vaild) {
          if (this.registerForm.password != this.registerForm.passwordAgain) {
            this.$message.error("两次输入的密码不一致");
            this.loading = false;
            return;
          }
          putRequest("/api/user/register", this.registerForm)
            .then((res) => {
              if (res.code === 200) {
                this.$message({
                  message: res.msg,
                  type: "success",
                });
                this.registerForm = {};
                setTimeout(() => {
                    this.$router.push("/")
                },1500)
              } else {
                this.$message.error(res.msg);
              }
              this.loading = false;
            })
            .catch((err) => {
              this.loading = false;
              this.$message.error(err.message);
            });
        } else {
          this.$message.error("请检查输入的内容是否合法");
          this.loading = false;
          return false;
        }
      });
    },
  },
  beforeCreate() {
    // 检查是否已经登录
    if (localStorage.getItem(keysProperties.tokenKey)) {
      // 有token
      getRequest("/api/user/expire").then((res) => {
        console.log(res);
        if (res.code == 200 && res.data.isExpire == false) {
          // 正确响应，身份未过期
          this.$router.push("/home");
        } else {
          // 用户不是主动退出而过期，删除所有有关用户的信息
          local.storage.clearAllUserInfo();
        }
      });
    }
  },
};
</script>
  
  <style scoped>
.registerForm {
  max-width: 500px;
  border-radius: 10px;
  border: 1px solid #efefefc3;
  padding: 20px;
  margin: 20px auto;
}
.registerTitle {
  margin: 10px auto;
  text-align: center;
}
.registerBtn {
  width: 100%;
}
</style>
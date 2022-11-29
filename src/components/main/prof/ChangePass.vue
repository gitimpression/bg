<template>
  <div>
    <el-row>
      <el-col :span="12" :offset="6" class="change-box">
        <el-form :rules="rules" ref="form" :model="model">
          <el-form-item prop="password">
            <el-input
              placeholder="请输入密码"
              v-model="model.password"
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item prop="passwordAgain">
            <el-input
              placeholder="请再次输入密码"
              v-model="model.passwordAgain"
              show-password
            ></el-input>
          </el-form-item>
        </el-form>
        <el-button type="primary" @click="change()" class="btn"
          >确定修改</el-button
        >
      </el-col>
    </el-row>
  </div>
</template>

<script>
import validator from "@/util/validator";
import { postRequest } from "@/util/api";
export default {
  name: "ChangePass",
  data() {
    return {
      model: {
        password: "",
        passwordAgain: "",
      },
      rules: {
        password: [
          {
            validator: validator.uPass,
            trigger: "blur",
          },
        ],
        passwordAgain: [
          {
            validator: validator.uPass,
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    change() {
      if (this.password !== this.passwordAgain) {
        this.$message.error("两次输入密码不一致");
        return;
      }
      this.$refs.form.validate((vaild) => {
        if (vaild) {
          postRequest("/api/user/password", {
            password: this.model.password,
          }).then((res) => {
            if (res.code == 200) {
              this.$message({
                message: res.msg,
                type: "success",
              });
            } else {
              this.$message.error(res.msg);
            }
          });
        } else {
          this.$message({
            message: "请检查信息格式是否符合规范",
            type: "warning",
          });
        }
      });
    },
  },
};
</script>

<style scoped>
.change-box {
  text-align: center;
}
.btn {
  display: inline-block;
}
</style>
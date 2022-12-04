<template>
  <div>
    <el-form ref="form" :model="form" label-width="80px" :rules="rules">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input type="textarea" rows="10" v-model="form.content"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交反馈</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { putRequest } from "@/util/api";
export default {
  name: "Feedback",
  data() {
    return {
      form: {
        title: "",
        content: "",
      },
      rules: {
        title: [
          { required: true, message: "请输入标题", trigger: "blur" },
          {
            min: 6,
            max: 500,
            message: "长度在 6 到 500 个字符",
            trigger: "blur",
          },
        ],
        content: [
          { required: true, message: "请输入内容", trigger: "blur" },
          {
            min: 6,
            max: 500,
            message: "长度在 6 到 500 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    onSubmit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          putRequest("/api/feedback", this.form).then((res) => {
            console.log(res);
            if (res.code == 200) {
              this.form.title = "";
              this.form.content = "";
              this.$message({
                message: res.msg,
                type: "success",
              });
            } else {
              this.$message.error(res.msg);
            }
          });
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
</style>
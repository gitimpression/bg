<template>
  <div>
    <div>
      <el-row>
        <el-col :span="12" :offset="3">
          <el-form
            ref="basicForm"
            :rules="rules"
            :model="editUser"
            label-width="80px"
          >
            <el-form-item label="用户头像">
              <el-image
                :src="editUser.headImg"
                class="headImg"
                :preview-src-list="new Array(editUser.headImg)"
                title="点击查看大图"
              >
                <div slot="placeholder" class="image-slot">
                  加载中<span class="dot">...</span>
                </div>
              </el-image>
              <el-upload
                class="uploadBtn"
                ref="upload"
                action="https://jsonplaceholder.typicode.com/posts/"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :auto-upload="false"
                :file-list="fileList"
                list-type="picture"
              >
                <el-button slot="trigger" size="small" type="primary"
                  >选取文件</el-button
                >
                <el-button
                  style="margin-left: 10px"
                  size="small"
                  type="success"
                  @click="submitUpload"
                  >上传到服务器</el-button
                >
              </el-upload>
            </el-form-item>
            <el-form-item label="用户名" prop="username">
              <el-input v-model="editUser.username"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-radio-group v-model="editUser.gender">
                <el-radio
                  v-for="item in genderData"
                  :label="item.valueId"
                  :key="item.value"
                  >{{ item.value }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
            <el-form-item label="生日">
              <el-date-picker
                value-format="yyyy-MM-dd"
                v-model="user.birthday"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item label="账号状态">
              <div>{{ userStatus }}</div>
            </el-form-item>
            <el-form-item label="身份">
              <div>{{ roleName }}</div>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="editUser.email"></el-input>
            </el-form-item>
            <el-form-item label="登录时间">
              <div>{{ editUser.loginTime }}</div>
            </el-form-item>
            <el-form-item label="创建时间">
              <div>{{ editUser.createTime }}</div>
            </el-form-item>
            <el-button @click="show()" :disabled="!isChange()"
              >保存修改</el-button
            >
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import keysProperties from "@/config/keysProperties";
import validator from "@/util/validator";
import { getRequest, postRequest } from "@/util/api";
import axios from "axios";
export default {
  name: "Basic",
  data() {
    return {
      user: {},
      editUser: {},
      genderData: [],
      userStatusData: [],
      roleName: "",
      fileList: [],
      rules: {
        username: [
          {
            validator: validator.uName,
            trigger: "blur",
          },
        ],
        email: [
          {
            validator: validator.uEmail,
            trigger: "blur",
          },
        ],
      },
    };
  },
  computed: {
    // valueId -->> value
    userStatus() {
      let value;
      this.userStatusData.forEach((u) => {
        if (u.valueId == this.editUser.userStatus) {
          value = u.value;
        }
      });
      return value;
    },
  },
  created() {
    this.user = JSON.parse(localStorage.getItem(keysProperties.userInfoKey));
    this.editUser = JSON.parse(JSON.stringify(this.user)); // 深拷贝
  },
  beforeMount() {
    postRequest("/api/data", {
      // 用户性别字典
      key: "gender",
    }).then((res) => {
      this.genderData = res.data.data;
    });
    postRequest("/api/data", {
      // 用户状态字典
      key: "userStatus",
    }).then((res) => {
      this.userStatusData = res.data.data;
    });
    axios.get("/api/user/role").then((res) => {
      this.roleName = res.data.data;
    });
  },
  methods: {
    show() {
      this.$refs.basicForm.validate((vaild) => {
        if (vaild) {
          console.log("验证通过");
        } else {
          console.log("验证不通过");
        }
      });
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    isChange() {
      return (
        this.user.username != this.editUser.username ||
        this.user.email != this.editUser.email ||
        this.user.birthday != this.editUser.birthday ||
        this.user.headImg != this.editUser.headImg
      );
    },
  },
  mounted() {},
};
</script>

<style>
.headImg {
  width: 200px;
  height: 200px;
  display: inline-block;
  vertical-align: middle;
}
.uploadBtn {
  display: inline-block;
  width: auto;
  padding-left: 4px;
}
</style>
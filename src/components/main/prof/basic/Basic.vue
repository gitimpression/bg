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
            <el-form-item label="头像">
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
              <el-button
                class="uploadBtn"
                size="small"
                @click="changeUserHeadImgDialogVisible = true"
              >
                更换头像
              </el-button>
            </el-form-item>
            <el-form-item label="用户名" prop="username">
              <el-input v-model="editUser.username"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-radio-group v-model="editUser.gender">
                <el-radio
                  :input="isChange()"
                  v-for="item in genderData"
                  :label="item.valueId"
                  :key="item.value"
                  >{{ item.value }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
            <el-form-item label="生日" prop="birthday">
              <el-date-picker
                value-format="yyyy-MM-dd"
                v-model="editUser.birthday"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item label="账号状态">
              <el-tag :type="userStatusClass">{{ userStatus }}</el-tag>
            </el-form-item>
            <el-form-item label="身份">
              <el-tag>{{ roleName }}</el-tag>
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
            <el-button @click="saveChange()" :disabled="!userInfoIsChange"
              >变化与保存</el-button
            >
            <el-button
              @click="resetUserInfoDialog()"
              :disabled="!userInfoIsChange"
              >重置</el-button
            >
          </el-form>
        </el-col>
      </el-row>
      <el-dialog
        title="头像上传"
        :visible.sync="changeUserHeadImgDialogVisible"
        width="50%"
        center
        :show-close="false"
        :close-on-press-escape="false"
        :close-on-click-modal="false"
      >
        <div class="dialogBody">
          <div class="newHeadImg">
            <el-upload
              class="uploader"
              action="/api/upload/img"
              :show-file-list="false"
              :on-success="uploadSuccess"
              :headers="uploadHeaders"
              :before-upload="beforeUpload"
              title="点击选取新头像"
            >
              <img v-if="uploadImgUrl" :src="uploadImgUrl" class="previewImg" />
              <i v-else class="el-icon-plus uploader-icon"></i>
            </el-upload>
          </div>
        </div>

        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveHeadImg()"
            >保存并使用</el-button
          >
          <el-button @click="noSaveHeadImg()">取 消</el-button>
        </span>
      </el-dialog>
      <UserInfoChangeDialog
        :beforeUser="user"
        :afterUser="editUser"
        :dGenderData="genderData"
      ></UserInfoChangeDialog>
    </div>
  </div>
</template>

<script>
import keysProperties from "@/config/keysProperties";
import validator from "@/util/validator";
import { postRequest } from "@/util/api";
import Bus from "@/util/bus";
import UserInfoChangeDialog from "@/components/main/prof/basic/children/UserInfoChangeDialog.vue";

import axios from "axios";
export default {
  name: "Basic",
  data() {
    return {
      user: {},
      editUser: {},
      genderData: [],
      userStatusData: [],
      userInfoIsChange: false,
      roleName: "",
      fileList: [],
      changeUserHeadImgDialogVisible: false,
      uploadImgUrl: "",
      uploadHeaders: {
        u_t: localStorage.getItem(keysProperties.tokenKey),
      },
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
        birthday: [
        {
            validator: validator.uBirthday,
            trigger: "blur",
          },
        ]
      },
    };
  },
  computed: {
    userStatusClass() {
      if(this.user.userStatus === 1){
        return "success"
      }
      if(this.user.userStatus === 2){
        return "danger"
      }
    },
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
    resetUserInfoDialog() {
      this.editUser = JSON.parse(JSON.stringify(this.user));
    },

    // 个人信息发生变化
    isChange() {
      this.userInfoIsChange =
        this.user.username != this.editUser.username ||
        this.user.gender != this.editUser.gender ||
        this.user.email != this.editUser.email ||
        this.user.birthday != this.editUser.birthday ||
        this.user.headImg != this.editUser.headImg;
    },

    // 修改用户信息
    saveChange() {
      this.$refs.basicForm.validate((vaild) => {
        if (vaild) {
          Bus.$emit("UserInfoChangeDialogVisible", true);
        } else {
          this.$message({
            message: "请检查信息格式是否符合规范",
            type: "warning",
          });
        }
      });
    },
    // 头像上传成功
    uploadSuccess(res, file) {
      this.$message({
        message: res.msg,
        type: "success",
      });
      // this.uploadImgUrl = URL.createObjectURL(file.raw);
      this.uploadImgUrl =
        keysProperties.userHeaderImgPrefix + res.data.fileName; // 含前缀http://...
      this.user.headImg = this.uploadImgUrl;
    },
    // 上传之前处理
    beforeUpload(file) {
      const isJPG =
        file.type === "image/jpeg" ||
        file.type === "image/png" ||
        file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 1;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 1MB!");
      }
      return isJPG && isLt2M;
    },
    // 开始上传
    submitUpload() {
      this.$refs.upload.submit();
    },
    // 保存头像
    async saveHeadImg() {
      // 保存并更新本地信息
      postRequest("/api/user/headImg", this.uploadImgUrl).then((res) => {
        let img = res.data.headImg; // 去除双引号 decodeURIComponent
        localStorage.setItem(
          keysProperties.userInfoKey,
          JSON.stringify(this.user)
        );
        this.user.headImg = img;
        this.editUser.headImg = img;
        // TODO 通知header组件用户信息发生变化
        Bus.$emit("headerUserInfo", this.user); // 个人信息同步到Header组件
      });
      this.changeUserHeadImgDialogVisible = false;
    },
    // 取消保存
    noSaveHeadImg() {
      this.changeUserHeadImgDialogVisible = false;
    },
  },
  components: {
    UserInfoChangeDialog,
  },
  mounted() {
    Bus.$on("headerUserInfo", (user) => {
      // 个人信息同步Basic组件
      this.user = user;
    });
  },
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
  margin-left: 4px;
}
.uploader {
  border-radius: 4px;
  width: 200px;
  height: 200px;
  text-align: center;
  border: 1px solid rgb(227, 227, 227);
}
.uploader-icon {
  width: 200px;
  height: 200px;
  font-size: 28px;
  line-height: 200px;
  color: rgb(140, 147, 157);
}
.previewImg {
  width: 200px;
  height: 200px;
  display: inline-block;
}
.dialogBody {
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.oldHeadImg,
.newHeadImg {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.headImgText {
  margin-top: 4px;
  text-align: center;
}
.abnormal {
  color: red;
}
.normal {
  color: green;
}
</style>
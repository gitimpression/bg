<template>
  <div>
    <el-dialog
      title="个人信息变化情况"
      :visible.sync="dialogVisible"
      :show-close="false"
      width="50%"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
    >
    <!-- 用户名 -->
      <el-row :gutter="20" class="changeRow">
        <el-col
          :span="10"
          :class="{ chagneBefore: !equalsUsername }"
          >{{ user.username }}</el-col
        >

        <el-col :span="4">
          <i
            class="el-icon-caret-right"
            v-show="!equalsUsername"
          ></i>
          <i v-show="equalsUsername">-</i>
        </el-col>

        <el-col
          :span="10"
          :class="{ chagneAfter: !equalsUsername }"
          >{{ editUser.username }}</el-col
        >
      </el-row>
<!-- 性别 -->
      <el-row :gutter="20" class="changeRow">
        <el-col
          :span="10"
          :class="{ chagneBefore: !equalsGender }"
          >{{ beforeGender }}</el-col
        >
        <el-col :span="4">
          <i
            class="el-icon-caret-right"
            v-show="!equalsGender"
          ></i>
          <i v-show="equalsGender">-</i>
        </el-col>
        <el-col
          :span="10"
          :class="{ chagneAfter: !equalsGender }"
          >{{ afterGender }}</el-col
        >
      </el-row>
      <!-- 生日 -->
      <el-row class="changeRow">
        <el-col
          :span="10"
          :class="{ chagneBefore: !equalsBirthday }"
          >{{ user.birthday }}</el-col
        >
        <el-col :span="4">
          <i
            class="el-icon-caret-right"
            v-show="!equalsBirthday"
          ></i>
          <i v-show="equalsBirthday">-</i>
        </el-col>
        <el-col
          :span="10"
          :class="{ chagneAfter: !equalsBirthday }"
          >{{ editUser.birthday }}</el-col
        >
      </el-row>
      <!-- 邮箱 -->
      <el-row class="changeRow">
        <el-col
          :span="10"
          :class="{ chagneBefore: !equalsEmail }"
          >{{ user.email }}</el-col
        >
        <el-col :span="4">
          <i
            class="el-icon-caret-right"
            v-show="!equalsEmail"
          ></i>
          <i v-show="equalsEmail">-</i>
        </el-col>
        <el-col
          :span="10"
          :class="{ chagneAfter: !equalsEmail }"
          >{{ editUser.email }}</el-col
        >
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="changeUserInfo()">确定修改</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Bus from "@/util/bus";
import keysProperties from '@/config/keysProperties'
import { postRequest } from '@/util/api';
export default {
  name: "UserInfoChangeDialog",
  props: ["afterUser", "beforeUser", "dGenderData"],
  data() {
    return {
      user: this.beforeUser,
      editUser: this.afterUser,
      dialogVisible: false,
      genderData: this.dGenderData,
    };
  },
  computed: {
    // 获取性别
    equalsGender(){
      return this.user.gender == this.editUser.gender
    },
    equalsUsername(){
      return this.user.username == this.editUser.username
    },
    equalsEmail(){
      return this.user.email == this.editUser.email
    },
    equalsBirthday(){
      return this.user.birthday == this.editUser.birthday
    },
    beforeGender() {
      let value = '';
      this.dGenderData.forEach(g => {
        if (g.valueId == this.user.gender) {
          value = g.value
        }
      });

      return value;
    },
    afterGender() {
      let value = ''
      this.dGenderData.forEach(g => {
        if (g.valueId == this.editUser.gender) {
          value = g.value
        }
      });
      return value;
    },
  },
  methods: {
    // 修改个人信息
    changeUserInfo() {
      postRequest("/api/user",this.editUser).then(res => {
        this.dialogVisible = false
        if (res.code == 200) {
          this.$message({
            message: res.msg,
            type: "success"
          })
          // 更新本地存储 更新header存储 更新basic
          localStorage.setItem(keysProperties.userInfoKey,JSON.stringify(this.editUser))
          this.user = JSON.parse(JSON.stringify(this.editUser))
          Bus.$emit("headerUserInfo",JSON.parse(JSON.stringify(this.editUser)))
        }else{
          this.$message({
            message: res.msg,
            type: "warning"
          })
        }
      })
    },
  },
  mounted() {
    Bus.$on("UserInfoChangeDialogVisible", visible => {
      this.dialogVisible = visible;
    });
  },
};
</script>

<style scoped>
.chagneBefore {
  color: green;
}
.chagneAfter {
  color: blue;
}
.changeRow {
  text-align: center;
  margin: 20px auto;
  font-size: 16px;
}
</style>
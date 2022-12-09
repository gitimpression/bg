<template>
  <div>
    <div class="cardBox">
      <div class="card">
        <div class="headImg">
          <img :src="user.headImg"/>
        </div>
        <div class="list">
          <div>用户ID: {{ user.id }}</div>
          <div>用户名: {{ user.username }}</div>
          <div>邮箱: {{ user.email }}</div>
          <div>注册时间: {{ user.createTime }}</div>
          <div>最后登录时间: {{ user.loginTime }}</div>
          <div>生日: {{ user.birthday }}</div>
          <div>性别: {{ user.gender }}</div>
          <div>状态: {{ user.userStatus }}</div>
          <div>角色ID: {{ user.roleId }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserCard",
  props: ["userInfo"],
  data() {
    return {
      user: this.userInfo,
    };
  },
};
</script>

<style scoped>
.headImg img{
  max-width: 260px;
  height: 260px;
}
.headImg .list{ 
  display: inline-block;

}
.list{
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  padding: 0 20px;
}
.list div{
  margin-bottom: 5px;
}
.card {
  width: 100%;
  margin-right: 20px;
  display: flex;
  flex-direction: row;
  border: 1px solid rgb(226, 226, 226);
  border-radius: 4px;
}
</style>
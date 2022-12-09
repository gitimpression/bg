<template>
  <div>
    <div class="searchBox">
      <el-input
        placeholder="请输入内容"
        v-model="search.content"
        class="input-with-select"
      >
        <el-select v-model="search.type" style="width: 120px" @change="show" slot="prepend" placeholder="请选择">
          <el-option label="用户名" value="username" checked></el-option>
          <el-option label="用户邮箱" value="email"></el-option>
          <el-option label="用户ID" value="userId"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="doSearch">搜索</el-button>
      </el-input>
    </div>
    <user-card :user-info="user" v-if="(user.username)"></user-card>
  </div>
</template>

<script>
import { getRequest } from '@/util/api';
import UserCard from '@/components/main/user/children/UserCard.vue'
export default {
  name: "SearchUser",
  data() {
    return {
      search: {
        type: 'username',
        content: ''
      },
      user: {}
    };
  },
  methods: {
    doSearch() {
      getRequest("/api/user/search?type=" + this.search.type + "&content=" + this.search.content)
      .then(res => {
        console.log(res);
        if(res.data.user){
          this.user = res.data.user
        }
      })
    },
    show(){
      console.log(this.search);
    }
  },
  components: {
    UserCard
  }
};
</script>

<style scoped>
.searchBox {
  display: flex;
  flex-direction: row;
  margin-bottom: 20px;
}

</style>
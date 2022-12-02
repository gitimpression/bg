<template>
  <div>
    <div class="botice-panel" v-if="notice.title != null">
      <div class="notice-title">{{ notice.title }}</div>
      <div class="notice-content">{{ notice.content }}</div>
      <div class="notice-footer">
        <div class="time-publisher">
          <div class="create-time">
            {{ notice.createTime }}
          </div>
          <div class="publisher-name">
            {{ notice.publisherName }}
          </div>
          <div v-if="deleteNoticePm" style="margin-left:10px">
            <el-link type="danger">删除公告</el-link>
          </div>
        </div>
        <div class="visits">
            <i class="el-icon-view visits-icon"></i>
          {{ notice.visits }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Bus from "@/util/bus";
export default {
  name: "NoticeDetail",
  data() {
    return {
      notice: {},
      deleteNoticePm: true
    };
  },
  mounted() {
    Bus.$on("NoticeDetailShow", (notice) => {
      this.notice = notice;
    });
  },
};
</script>

<style scoped>
.botice-panel {
  padding: 10px;
}
.notice-title {
  margin-top: 10px;
  margin-bottom: 10px;
  text-align: center;
  font-weight: bold;
}
.notice-content {
  text-indent: 2rem;
}
.notice-footer {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 20px;
  font-size: 14px;
  color: gray;
}
.time-publisher {
    display: flex;
    flex-direction: row;
}
.publisher-name{
    margin-left: 10px;
}
</style>
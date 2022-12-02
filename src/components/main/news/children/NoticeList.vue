<template>
  <div>
    <div
      v-for="item in pageInfo.list"
      :key="item.id"
      :notice="item"
      class="notice-item"
      @click="getNoticeDetail(item.id)"
    >
      <!-- 处于编辑状态时显示 带复选框 -->
      <el-checkbox
        v-if="batch"
        v-model="checkList"
        :label="item.id"
        @change="hanldeCheckChange"
      >
        <div class="notice-title">
          {{ item.title }}
        </div>
      </el-checkbox>

      <!-- 正常显示列表 -->
      <div v-if="!batch" class="notice-title">
        {{ item.title }}
      </div>
      <div class="footer">
        <div class="date-author">
          <div class="date">{{ item.createTime }}</div>
          <div class="author">{{ item.publisherName }}</div>

        </div>
        <div class="visits">
          <i class="el-icon-view visits-icon"></i>
          {{ item.visits }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Bus from "@/util/bus";
import { getRequest } from "@/util/api";
import keysProperties from "@/config/keysProperties";

export default {
  name: "NoticeList",
  props: ["pageInfo"],
  data() {
    return {
      checkList: [],
      batch: false,
    };
  },
  methods: {
    getNoticeDetail(id) {
      this.$emit("noticeDetail");
      if (this.batch) {
        // 正在批量操作
        return;
      }

      // 检查是否访问过该公告以执行对应操作
      let visitedStr = localStorage.getItem(
        keysProperties.userVisitsNoticeList
      );
      let isVisited = false; // 访问标记
      if (visitedStr) {
        if (visitedStr.indexOf(",") !== -1) {
          visitedStr.split(",").forEach((v) => {
            if (Number(v) == id) {
              // 不止一个
              isVisited = true;
            }
          });
          if (!isVisited) {
            visitedStr = visitedStr + "," + id;
          }
        } else {
          // 一个
          if (Number(visitedStr) == id) {
            isVisited = true;
            console.log("访问过，不添加");
          } else {
            visitedStr = visitedStr + "," + id;
          }
        }
        localStorage.setItem(keysProperties.userVisitsNoticeList, visitedStr);
      } else {
        localStorage.setItem(keysProperties.userVisitsNoticeList, id);
      }
      
      getRequest(
        "/api/notice/detail?noticeId=" + id + "&isVisited=" + isVisited
      )
        .then((res) => {
          if (res.code == 200) {
            Bus.$emit("NoticeDetailShow", res.data.notice);
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error(err);
        });
    },
    hanldeCheckChange() {
      if (this.checkList.length == this.pageInfo.list.length) {
        Bus.$emit("NoticeCheckAll", true);
      } else {
        Bus.$emit("NoticeCheckAll", false);
      }
    },
  },
  mounted() {
    Bus.$on("NoticeListBatch", (b) => {
      this.batch = b;
      if (!this.batch) {
        // 清空选择
        this.checkList = [];
      }
    });

    Bus.$on("NoticeListCheckedAll", (c) => {
      if (c) {
        // 全选
        this.pageInfo.list.forEach((p) => {
          this.checkList.push(p.id);
        });
      } else {
        // 全部选
        this.checkList = [];
      }
    });
  },
};
</script>

<style scoped>
.notice-item:hover .notice-title {
  color: rgb(42, 83, 216);
}
.notice-item {
  cursor: pointer;
}
.notice-title {
  font-weight: bold;
  padding: 12px 6px 2px 6px;
}
.footer {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 10px 6px;
  border-bottom: solid 2px #e9e9e9;
  font-size: 14px;
}
.date-author {
  display: flex;
  flex-direction: row;
}
.date {
  color: gray;
}
.author {
  color: gray;
  margin-left: 10px;
}
.visits {
  color: gray;
}
.visits-icon {
  color: gray;
}
</style>
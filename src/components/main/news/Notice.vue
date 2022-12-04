<template>
  <div>
    <!-- 操作行 -->
    <el-row>
      <!-- 搜索 -->
      <el-col :span="6">
        <el-input v-model="searchContent" placeholder="请输入内容"></el-input>
      </el-col>
      <el-col :span="3">
        <el-button type="primary" icon="el-icon-search" @click="getNotice">搜索</el-button>
      </el-col>
      <!-- 需要权限的编辑操作 -->
      <el-col :span="12" v-if="noticePm">
        <el-button
          v-if=" !batch"
          icon="el-icon-edit-outline"
          @click="openPublishPanel"
          >发布公告</el-button
        >
        <el-button @click="handleBatch">{{ getBatchTitle }}</el-button>
        <el-button v-if="batch" @click="chooseCheckAll">{{
          getCheckAll
        }}</el-button>
        <el-button
          v-if="batch"
          type="danger"
          plain
          icon="el-icon-delete"
          @click="deleteNoticeBatch"
          >删除</el-button
        >
      </el-col>
    </el-row>

    <!-- 左侧 通知列表 -->
    <el-row :gutter="20">
      <el-col :span="9" v-show="!publishFull">
        <NoticeList
          ref="noticeListRef"
          :page-info="pageInfo"
          @noticeDetail="onShowDetail"
        ></NoticeList>
        <!-- 分页按钮 -->
        <div class="pagination">
          <el-pagination
            v-show="!publishFull"
            @size-change="sizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-size="pageSize"
            :page-count="5"
            small
            layout="total, prev, pager, next, jumper"
            :total="pageInfo.total"
          >
          </el-pagination>
        </div>
      </el-col>
      <!-- 右侧 通知详情      现实条件：不发布不操作时-->
      <el-col :span="15" v-if="!publish && !batch">
        <el-row>
          <el-col :span="24">
            <NoticeDetail ref="noticeDetailRef" @reloadNotice="getNotice"></NoticeDetail>
          </el-col>
        </el-row>
      </el-col>
      <!-- 发布公告 -->
      <el-col :span="publishWidth" v-if="noticePm && publish && !batch">
        <el-row>
          <el-col :span="24">
            <div class="publishPanel">
              <!-- 工具 -->
              <div class="publish-tool">
                <el-button
                  icon="el-icon-full-screen"
                  @click="handlePublishPanelFull"
                ></el-button>
                <el-button  
                  icon="el-icon-close"
                  @click="handlePublishPanelClose"
                  title="关闭发布表单"
                ></el-button>
              </div>
              <!-- 发布面板 -->
              <el-form ref="form" :model="publishForm" label-width="80px">
                <el-form-item label="公告标题">
                  <el-input v-model="publishForm.title"></el-input>
                </el-form-item>
                <el-form-item label="公告内容">
                  <el-input
                    type="textarea"
                    v-model="publishForm.content"
                    rows="16"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="doPublish">发布</el-button>
                  <el-button @click="noticeDraft">保存草稿</el-button>
                  <el-button @click="clearForm">清空</el-button>
                  <el-button @click="clearDraft">清空草稿</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import NoticeList from "@/components/main/news/children/NoticeList.vue";
import NoticeDetail from "@/components/main/news/children/NoticeDetail.vue";
import keysProperties from "@/config/keysProperties";

import { delRequest, getRequest, putRequest,getPm } from "@/util/api";
import Bus from "@/util/bus";
export default {
  name: "Notice",
  data() {
    return {
      pageNum: 1,
      pageSize: 6,
      pageInfo: {},
      searchContent: "",
      batch: false, // 批量删除激活状态
      isCheckAll: false,
      publish: false, // 是否发布
      publishForm: {},
      publishFull: false, // 发布面板放大
      publishWidth: 12, // 面板放大
      noticePm: false,
    };
  },
  computed: {
    getBatchTitle() {
      return this.batch ? "取消批量操作" : "批量操作";
    },
    getCheckAll() {
      return this.isCheckAll ? "全不选" : "全选";
    },
  },
  methods: {
    clearDraft(){
      if (confirm("清空草稿？")) {
        localStorage.removeItem(keysProperties.noticeDraftKey)
        this.$message({
          message: "草稿已被清空",
          type: "success"
        })
      }
    },
    sizeChange(val){
      this.pageSize = val
      this.getNotice()
    },
    deleteNoticeBatch() {
      let list = this.$refs["noticeListRef"].checkList;
      if (list.length == 0) {
        alert("请选择要删除的公告");
        return;
      }
      if (confirm("确定删除？操作不可逆！")) {
        delRequest("/api/notice/batch", {
          idList: list,
        })
          .then((res) => {
            if (res.code == 200) {
              this.checkList = [];
              this.$message({
                message: res.msg,
                type: "success",
              });
              this.getNotice();
            } else {
              this.$message.error(res.msg);
            }
          })
          .catch((err) => {
            this.$message.error(err);
          });
      }
    },
    handlePublishPanelClose() {
      this.publish = false;
      this.publishFull = false;
    },
    clearForm() {
      if (confirm("清空当前所填写的公告？此操作不可逆！")) {
        this.publishForm = {};
      }
    },
    noticeDraft() {
      localStorage.setItem(
        keysProperties.noticeDraftKey,
        JSON.stringify(this.publishForm)
      );
      this.$message({
        message: "保存草稿成功！",
        type: "success",
      });
    },
    handlePublishPanelFull() {
      this.publishFull = !this.publishFull;
      this.publishWidth = this.publishFull ? 24 : 12; // 面板大小控制
    },
    onShowDetail() {
      // 显示详情状态，关闭publishPanel
      this.publish = false;
    },
    getNotice() {
      getRequest(
        "/api/notice?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize + "&keyword=" + this.searchContent
      )
        .then((res) => {
          if (res.code == 200) {
            this.pageInfo = res.data.pageInfo;
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error(err);
        });
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getNotice();
    },
    handleBatch() {
      this.batch = !this.batch;
      Bus.$emit("NoticeListBatch", this.batch);
    },
    chooseCheckAll() {
      this.isCheckAll = !this.isCheckAll;
      Bus.$emit("NoticeListCheckedAll", this.isCheckAll);
    },
    openPublishPanel() {
      if (this.publish) {
        return;
      }
      if (localStorage.getItem(keysProperties.noticeDraftKey)) {
        if (confirm("当前存在一则通知草稿，是否查看？")) {
          let notice = JSON.parse(
            localStorage.getItem(keysProperties.noticeDraftKey)
          );
          this.publishForm.title = notice.title;
          this.publishForm.content = notice.content;
        }
      }
      this.publish = true;
      this.batch = false;
    },
    doPublish() {
      putRequest("/api/notice",this.publishForm).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: res.msg,
            type: "success"
          });
          this.publishForm = {};
          this.getNotice()
        } else {
          this.$message.error(res.msg);
        }
      }).catch(err => {
        this.$message.error(err);
      })
    },
  },
  mounted() {
    this.getNotice();
    Bus.$on("NoticeCheckAll", (b) => {
      this.isCheckAll = b;
    });
    // 获取权限
    getPm(keysProperties.noticePmKey).then(res => {
      if (res.code == 200) {
        this.noticePm = res.data.data
        this.$refs['noticeDetailRef'].deleteNoticePm = this.noticePm
      }else{
        this.$message.error(res.msg)
      }
    }).catch(err => {
      this.$message.error(err)
    })
  },
  components: {
    NoticeList,
    NoticeDetail,
  },
};
</script>

<style scoped>
.publish-tool {
  text-align: right;
  margin-bottom: 10px;
}
.publishPanel {
  margin: 10px;
}
.pagination {
  margin-top: 10px;
}
</style>
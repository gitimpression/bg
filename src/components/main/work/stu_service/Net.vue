<template>
  <div>
    <!-- 菜单列表 -->
    <el-row class="notice-peration-box" v-if="netPm">
      <el-button @click="noticeFormVisible = true">修改顶部通知</el-button>
    </el-row>
    <el-alert :title="topNotice" type="info" show-icon> </el-alert>
    <div class="card-box" v-if="netMenuList.length > 0">
      <Card
        v-for="item in netMenuList"
        :key="item.id"
        :menu="item"
        @showHandbookDialog="showHbDialog"
      ></Card>
    </div>
    <!-- 修改顶部通知 -->
    <el-dialog title="修改通知" :visible.sync="noticeFormVisible">
      <el-form :model="notice">
        <el-form-item label="通知内容">
          <el-input v-model="notice.content" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="noticeFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleNoticeSubmit">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 查看手册 -->
    <el-dialog title="使用手册与故障指南" :visible.sync="handbookTableVisible">
      <el-row  v-if="netPm">
        <el-button @click="(addHandbookRowVisible = true)">添加</el-button>
      </el-row>
      <!-- 添加面板 -->
      <el-dialog
        width="45%"
        title="添加手册内容"
        :visible.sync="addHandbookRowVisible"
        append-to-body
      >
        <el-form ref="form" :model="handbookForm">
          <el-form-item label="标题">
            <el-input v-model="handbookForm.title"></el-input>
          </el-form-item>
          <el-form-item label="内容">
            <el-input v-model="handbookForm.content" type="textarea" rows="8"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handbookFormSubmit">确定</el-button>
            <el-button>取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!-- 添加面板end -->
      <el-table :data="handbookList" height="500">
        <el-table-column prop="title" label="问题"></el-table-column>
        <el-table-column prop="content" label="解决方案"></el-table-column>
        <el-table-column label="操作" width="120" v-if="netPm">
          <template slot-scope="scope">
            <el-button
              @click.native.prevent="deleteRow(scope.$index, handbookList)"
              type="text"
              size="small"
            >
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import Card from "@/components/main/work/stu_service/children/Card.vue";
import { delRequest, getRequest, postRequest, putRequest, getPm } from "@/util/api";
import keysProperties from '@/config/keysProperties'
export default {
  name: "Net",
  data() {
    return {
      num: 5,
      netMenuList: [],
      topNotice: "暂无通知",
      noticeFormVisible: false,
      handbookTableVisible: false,
      addHandbookRowVisible: false,
      netPm: false,
      notice: {
        content: "",
      },
      handbookForm: {
        title: '',
        content: ''
      },
      handbookList: [],
    };
  },
  methods: {
    handbookFormSubmit(){
      putRequest("/api/netHandbook",this.handbookForm).then(res => {
        console.log(res);
        if (res.code == 200) {
          this.addHandbookRowVisible = false
          this.$message({
            message: res.msg,
            type: "success"
          })
          this.getHandbook()  // 更新页面数据
        }else{
          this.$message.error(res.msg)
        }
      }).catch(err => {
        this.$message.error(err)
      })
    },
    deleteRow(index, rows) {
      let row = rows[index];
      delRequest("/api/netHandbook",{
        netHandbookId: row.id
      }).then(res => {
        if (res.code == 200) {
          this.$message({
            message: res.msg,
            type: "success"
          })
          this.getHandbook()
        }else{
          this.$message.error(res.msg)
        }
      }).catch(err => {
        this.$message.error(err)
      })
    },
    handleNoticeSubmit() {
      postRequest("/api/netMenu/notice", {
        content: this.notice.content,
      })
        .then((res) => {
          if (res.code == 200) {
            this.noticeFormVisible = false;
            this.topNotice = res.data.content;
            this.$message({
              message: res.msg,
              type: "success",
            });
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error(err);
        });
    },
    showHbDialog() {
      this.handbookTableVisible = true;
    },
    getHandbook(){
      getRequest("/api/netHandbook").then(res => {
        if (res.code == 200) {
          this.handbookList = res.data.list
        }else{
          this.$message.error(res.msg)
        }
      }).catch(err => {
        this.$message.error(err)
      })
    }
  },
  beforeMount() {
    getRequest("/api/netMenu").then((res) => {
      this.netMenuList = res.data.netMenuList;
    });
    getRequest("/api/netMenu/notice").then((res) => {
      this.topNotice = res.data.topNotice;
    });
    this.getHandbook()
    getPm(keysProperties.netPmKey).then((res) => {
      this.netPm = res.data.data;
    });
  },
  components: {
    Card,
  },
};
</script>

<style scoped>
.card-box {
  width: 100%;
  height: 300px;
  display: flex;
  align-content: flex-start;
  flex-wrap: wrap;
}
.notice-peration-box {
  margin-bottom: 10px;
}
</style>
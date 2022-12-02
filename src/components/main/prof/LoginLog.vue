<template>
  <div>
    <el-table :data="pageInfo.list" style="width: 100%">
      <el-table-column prop="createTime" label="时间" width="180">
      </el-table-column>
      <el-table-column prop="operation" label="操作" width="180">
      </el-table-column>
      <el-table-column prop="ip" label="ip地址"> </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageInfo.total"
    >
    </el-pagination>
  </div>
</template>

<script>
import { getRequest } from "@/util/api";
export default {
  name: "LoginLog",
  data() {
    return {
      pageNum: 1,
      pageSize: 10,
      pageInfo: [],
    };
  },
  methods: {
    sizeChange(val){
      this.pageSize = val
      this.getLog()
    },
    handleCurrentChange(val){
      this.pageNum = val
      this.getLog()
    },
    getLog() {
      getRequest(
        "/api/userLog/login?pageNum=" +
          this.pageNum +
          "&pageSize=" +
          this.pageSize
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
  },
  mounted() {
    this.getLog();
  },
};
</script>

<style>
</style>
<template>
  <div>
    <!-- 分页 -->
    <el-pagination
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-sizes="[20, 50, 100, 200]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageInfo.total"
    >
    </el-pagination>
    <div class="operation">
        <el-select class="filter" v-model="search.operation" placeholder="筛选操作" @change="handleChangeOperation">
          <el-option v-for="item in userLogOperation" :label="item.value" :value="item.key" :key="item.value"></el-option>
        </el-select>
        <el-input v-model="search.userId" placeholder="请输入用户ID" class="filter"></el-input>
        <el-date-picker style="max-width:200px;margin-right:10px" v-model="search.createBeginTime" 
        :value-format="dateTimeFormat" type="datetime" placeholder="起始时间"
          :picker-options="pickerOptions">
        </el-date-picker>
        <div class="filter" style="line-height:40px">~</div>
        <el-date-picker style="max-width:200px;margin-right:10px" v-model="search.createEndTime" 
        :value-format="dateTimeFormat" type="datetime" placeholder="截止时间"
          :picker-options="pickerOptions">
        </el-date-picker>
        <el-input v-model="search.consumeBeginTime" placeholder="最少耗时" class="filter"></el-input>
        <div class="filter" style="line-height:40px">~</div>
        <el-input v-model="search.consumeEndTime" placeholder="最多耗时" class="filter"></el-input>
        <el-input v-model="search.ip" placeholder="ip 如127.0..." class="filter"></el-input>
        <el-button type="primary" icon="el-icon-search" style="margin-right: 10px;" @click="getUserLog">搜索</el-button>
        <el-button @click="clearSearchContent">清空</el-button>
    </div>
    <!-- 表格 -->
    <el-table
      v-loading="loading"
      :data="pageInfo.list"
      :border="true"
      style="width: 100%"
      height="900"
    >
      <el-table-column prop="id" label="记录ID" width="80"> </el-table-column>
      <el-table-column prop="userId" label="用户ID" width="80">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="时间"
        width="180"
      ></el-table-column>
      <el-table-column
        prop="operation"
        label="操作"
        width="150"
      ></el-table-column>
      <el-table-column prop="ip" label="IP" width="100"></el-table-column>
      <el-table-column prop="method" label="方法" width="250"></el-table-column>
      <el-table-column prop="params" label="参数" width="300" :formatter="formaterParams">
      </el-table-column>
      <el-table-column
        prop="consumeTime"
        label="耗时/ms"
        width="80"
      ></el-table-column>

      <el-table-column label="操作" width="60">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small"
            >详情</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 记录详情展示 -->
    <el-dialog title="记录详情" :visible.sync="showUserLogDetail">
      <el-descriptions direction="vertical" :column="2" border>
      <el-descriptions-item label="记录ID">{{userLogDetail.id}}</el-descriptions-item>
      <el-descriptions-item label="用户ID">{{userLogDetail.userId}}</el-descriptions-item>
      <el-descriptions-item label="时间">{{userLogDetail.createTime}}</el-descriptions-item>
      <el-descriptions-item label="操作">{{userLogDetail.operation}}</el-descriptions-item>
      <el-descriptions-item label="IP">{{userLogDetail.ip}}</el-descriptions-item>
      <el-descriptions-item label="方法" :span="2">{{userLogDetail.method}}</el-descriptions-item>
      <el-descriptions-item label="参数" :span="2">{{userLogDetail.params}}</el-descriptions-item>
      <el-descriptions-item label="耗时/ms">{{userLogDetail.consumeTime}}</el-descriptions-item>
    </el-descriptions>
    </el-dialog>

  </div>
</template>

<script>
import { getRequest } from "@/util/api";
import dbData from '@/config/data'

export default {
  name: "UserLog",
  data() {
    return {
      pageNum: 1,
      pageSize: 20,
      pageInfo: {},
      loading: false,
      showUserLogDetail: false,
      search: {
        userId: '',
        operation: '',
        createBeginTime: '',
        createEndTime: '',
        consumeBeginTime: '',
        consumeEndTime: '',
        ip: '',
      },
      dateTimeFormat: 'yyyy-MM-dd hh:mm:ss',
      userLogOperation: dbData.userLogOperation,
      pickerOptions: {
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
      userLogDetail: {}
    };
  },
  methods: {
    clearSearchContent(){
      this.search = {
        userId: '',
        operation: '',
        createBeginTime: '',
        createEndTime: '',
        consumeBeginTime: '',
        consumeEndTime: '',
        ip: '',
      }
    },
    handleChangeOperation(){
      console.log(this.search.operation);
    },
    formaterParams(row, column, cellValue, index){
      if (cellValue && cellValue.length > 100) {
        return cellValue.substring(0,100) + '...'
      }
    },
    handleClick(row) {
      this.showUserLogDetail = true
      this.userLogDetail = row
      console.log(row);
    },
    handleCurrentChange(val) {
      this.loading = true;
      // 页码改变
      this.pageNum = val;
      this.getUserLog();
    },
    handleSizeChange(val) {
      this.loading = true;
      // 页长发生改变
      this.pageSize = val;
      this.getUserLog();
    },
    getUserLog() {
      let data = "pageNum=" + this.pageNum + "&pageSize=" + this.pageSize +
      "&userId=" + this.search.userId + "&method=" + this.search.operation +
      "&createBeginTime=" + this.search.createBeginTime + "&createEndTime=" + this.search.createEndTime +
      "&consumeBeginTime=" + this.search.consumeBeginTime + "&consumeEndTime=" + this.search.consumeEndTime +
      "&ip=" + this.search.ip
      getRequest(
        "/api/userLog/page?" + data).then((res) => {
        this.pageInfo = res.data.pageInfo;
        this.loading = false;
      });
    },
  },
  beforeMount() {
    this.getUserLog();
  },
};
</script>
<style scoped>
.filter{
  max-width: 120px;
  margin-right:10px;
  margin-bottom: 5px;
}
.operation{
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    flex-wrap: wrap;
    margin: 10px auto;
}
/* 限制超出高度 */
.el-table__row{
  max-height: 70px;
}
</style>
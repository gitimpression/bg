<template>
  <div>
    <el-row>
      <h3 style="display: inline-block;margin-right:6px">系统菜单</h3>
      <el-button @click="handleAdd">添加</el-button>
    </el-row>
    <!-- 系统菜单展示 -->
    <el-table :data="systemMenuList" height="800">
      <el-table-column label="ID" width="60">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column label="菜单名">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="URL">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.url }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Path">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.path }}</span>
        </template>
      </el-table-column>

      <el-table-column label="组件名">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.component }}</span>
        </template>
      </el-table-column>

      <el-table-column label="父级菜单ID" width="100">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.parentId }}</span>
        </template>
      </el-table-column>

      <el-table-column label="icon">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.iconClz }}</span>
        </template>
      </el-table-column>

      <el-table-column label="是否可见" width="100">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.enabled }}</span>
        </template>
      </el-table-column>

      <el-table-column label="保活" width="60">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.keepAlive }}</span>
        </template>
      </el-table-column>

      <el-table-column label="所需权限" width="100">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.permissionId }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)"
            icon="el-icon-edit"
            plain
          ></el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            icon="el-icon-close"
            plain
          ></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 编辑面板 -->
    <el-dialog
      title="编辑菜单"
      :visible.sync="modifyMenuDialogVisiable"
      width="50%"
    >
      <el-form :model="modifyMenuForm" class="modifyDialog" label-width="80px">
        <el-form-item label="菜单名">
          <el-input v-model="modifyMenuForm.name" placeholder="name 如 个人信息"></el-input>
        </el-form-item>
        <el-form-item label="组件路径">
          <el-input v-model="modifyMenuForm.url" placeholder="url 如 prof/"></el-input>
        </el-form-item>
        <el-form-item label="路由">
          <el-input v-model="modifyMenuForm.path" placeholder="path 如 /basic"></el-input>
        </el-form-item>
        <el-form-item label="组件名">
          <el-input v-model="modifyMenuForm.component" placeholder="component 如 /Basic"></el-input>
        </el-form-item>
        <el-form-item label="父级菜单">
          <el-select
            v-model="modifyMenuForm.parentId"
            placeholder="请选择"
            @change="selectChange(modifyMenuForm.parentId)"
          >
            <el-option
              v-for="item in systemMenuList"
              :label="item.name"
              :value="item.id"
              :key="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="modifyMenuForm.iconClz" placeholder="icon 如 el-icon-user"></el-input>
        </el-form-item>
        <el-form-item label="可见性">
          <el-select
            v-model="modifyMenuForm.enabled"
            placeholder="请选择"
            @change="selectChange(modifyMenuForm.enabled)"
          >
            <el-option label="可见" value="true"></el-option>
            <el-option label="不可见" value="false"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="保活">
          <el-select
            v-model="modifyMenuForm.keepAlive"
            placeholder="请选择"
            @change="selectChange(modifyMenuForm.enabled)"
          >
            <el-option label="是" value="true"></el-option>
            <el-option label="否" value="false"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="访问权限">
          <el-select
            v-model="modifyMenuForm.permissionId"
            placeholder="请选择"
            @change="selectChange(modifyMenuForm.enabled)"
          >
            <el-option
              v-for="item in pmList"
              :label="'【' + item.remark + '】' + item.name"
              :value="item.id"
              :key="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="modifyMenuDialogVisiable = false">取 消</el-button>
        <el-button type="primary" @click="modifyMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { delRequest, getRequest, postRequest, putRequest } from "@/util/api";
export default {
  name: "SystemMenuManage",
  data() {
    return {
      systemMenuList: [],
      modifyMenuDialogVisiable: false,
      modifyMenuForm: {},
      pmList: [],
    };
  },
  methods: {
    handleAdd() {
      this.modifyMenuDialogVisiable = true;
      this.modifyMenuForm = {};
    },
    selectChange(val) {
      console.log(val);
    },
    handleEdit(index, row) {
      console.log(row.name);
      this.modifyMenuDialogVisiable = true;
      this.modifyMenuForm = JSON.parse(JSON.stringify(row));
    },
    handleDelete(index, row) {
      if (confirm("确定删除吗？")) {
        delRequest("/api/sys/menu", {
          systemMenuId: row.id,
        }).then((res) => {
          if (res.code == 200) {
            this.$message({
              message: res.msg,
              type: "success",
            });
            this.getMenuList();
          }
        });
      }
    },
    modifyMenu() {
      if (confirm("确定？")) {
        if (this.modifyMenuForm.id) {
        // 修改
        postRequest("/api/sys/menu", this.modifyMenuForm).then((res) => {
          if (res.code == 200) {
            this.$message({
              message: res.msg,
              type: "success",
              // TODO 重新加载列表
            });
            this.getMenuList()
            this.modifyMenuDialogVisiable = false
          }
        });
      } else {
        // 增加
        putRequest("/api/sys/menu", this.modifyMenuForm).then((res) => {
          if (res.code == 200) {
            this.$message({
              message: res.msg,
              type: "success",
              // TODO 重新加载列表
            });
            this.modifyMenuDialogVisiable = false;
            this.getMenuList();
          }
        });
      }
      }
    },
    getMenuList() {
      getRequest("/api/sys/menu/all").then((res) => {
        if (res.code == 200) {
          this.systemMenuList = res.data.menus;
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    getPm() {
      getRequest("/api/pm").then((res) => {
        this.pmList = res.data.permissionList;
      });
    },
  },
  beforeMount() {
    this.getMenuList();
    this.getPm();
  },
};
</script>

<style scoped>
.modifyDialog {
  height: 500px;
  overflow-y: scroll;
}
</style>
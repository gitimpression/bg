<template>
  <div class="box">
    <el-row>
      <!-- 角色 -->
      <h3 style="display: inline-block">角色表</h3>
      <el-button @click="showAddRole = true">添加角色</el-button>
      <el-table :data="roleList" style="width: 100%" height="300">
        <el-table-column prop="id" label="ID" width="180"> </el-table-column>
        <el-table-column prop="name" label="角色名" width="180">
        </el-table-column>
        <el-table-column prop="remark" label="描述"> </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              icon="el-icon-edit"
              @click="handleEditRole(scope.$index, scope.row)"
            >
            </el-button>
            <el-button
              size="mini"
              @click="handlePmDetail(scope.$index, scope.row)"
              >查看权限</el-button
            >
            <el-button
              size="mini"
              type="danger"
              plain
              icon="el-icon-close"
              @click="handleDeleteRole(scope.$index, scope.row)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <el-row>
      <!-- 权限表 -->
      <h3 style="display: inline-block">权限表</h3>
      <el-button @click="showAddPm = true">添加权限</el-button>
      <el-table :data="pmList" style="width: 100%" height="300">
        <el-table-column prop="id" label="ID" width="180"> </el-table-column>
        <el-table-column prop="name" label="角色名" width="180">
        </el-table-column>
        <el-table-column prop="remark" label="描述"> </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEditPm(scope.$index, scope.row)"
              icon="el-icon-edit"
            ></el-button>
            <el-button
              size="mini"
              type="danger"
              plain
              icon="el-icon-close"
              @click="handleDeletePm(scope.$index, scope.row)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>

    <!-- 查看用户权限 -->
    <el-dialog
      title="权限列表"
      :visible.sync="dialogPmDetailVisible"
      :fullscreen="true"
    >
      <el-form :model="roleDetailForm" label-width="100px">
        <el-form-item label="ID">
          <el-input v-model="roleDetailForm.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="角色名">
          <el-input v-model="roleDetailForm.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="拥有的权限">
          <el-checkbox-group v-model="pmByRoleList">
            <el-checkbox
              v-for="item in pmList"
              :key="item.remark"
              :label="item.id"
              >{{ "【" + item.remark + "】" + item.name }}</el-checkbox
            >
          </el-checkbox-group>
        </el-form-item>

        <el-form-item>
          <el-button @click="handleEditRolePermission">修改</el-button>
          <el-button @click="dialogPmDetailVisible = false">关闭</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 编辑角色 -->
    <el-dialog title="编辑角色" :visible.sync="dialogEditRoleVisible">
      <el-form :model="roleEditForm">
        <el-form-item label="角色ID" label-width="100px">
          <el-input v-model="roleEditForm.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="角色名" label-width="100px">
          <el-input v-model="roleEditForm.name"></el-input>
        </el-form-item>
        <el-form-item label="描述" label-width="100px">
          <el-input v-model="roleEditForm.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="handleEditRoleForm">确 定</el-button>
        <el-button @click="dialogEditRoleVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 编辑权限 -->
    <el-dialog title="添加权限" :visible.sync="dialogEditPmVisible">
      <el-form :model="pmEditFrom">
        <el-form-item label="权限ID" label-width="100px">
          <el-input v-model="pmEditFrom.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="权限名" label-width="100px">
          <el-input v-model="pmEditFrom.name"></el-input>
        </el-form-item>
        <el-form-item label="权限描述" label-width="100px">
          <el-input v-model="pmEditFrom.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="handleEditPmForm">确 定</el-button>
        <el-button @click="dialogEditPmVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加角色 -->
    <el-dialog title="添加角色" :visible.sync="showAddRole">
      <el-form :model="roleData">
        <el-form-item label="角色名">
          <el-input v-model="roleData.name"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="roleData.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleAddRole">确 定</el-button>
        <el-button @click="showAddRole = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加权限 -->
    <el-dialog title="添加权限" :visible.sync="showAddPm">
      <el-form :model="pmData">
        <el-form-item label="权限名">
          <el-input v-model="pmData.name"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="pmData.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleAddPm">确 定</el-button>
        <el-button @click="showAddPm = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { delRequest, getRequest, postRequest, putRequest } from "@/util/api";
export default {
  name: "Permission",
  data() {
    return {
      roleList: [],
      pmList: [],
      pmByRoleList: [],
      dialogPmDetailVisible: false,
      dialogEditRoleVisible: false,
      dialogEditPmVisible: false,
      roleDetailForm: {},
      roleEditForm: {},
      pmEditFrom: {},
      showAddRole: false,
      showAddPm: false,
      pmData: {},
      roleData: {},
    };
  },
  methods: {
    handleAddPm() {
      if(confirm("确定添加吗？")){
        putRequest("/api/pm",this.pmData).then(res => {
        if (res.code == 200) {
          this.$message({
            message: res.msg,
            type:"success"
          })
          this.pmData = {}
          this.showAddPm = false
        }else{
          this.$message.error(res.msg)
        }
      })
      }
    },
    handleAddRole() {
      console.log(this.roleData);
      if(confirm("确定添加吗？")){
        putRequest("/api/role",this.roleData).then(res => {
        if (res.code == 200) {
          this.$message({
            message: res.msg,
            type:"success"
          })
          this.roleData = {}
          this.showAddRole = false
        }else{
          this.$message.error(res.msg)
        }
      })
      }
    },
    handleDeleteRole(index, row) {
      if (confirm("确定删除吗？")) {
        delRequest("/api/role", {
          roleId: row.id,
        }).then((res) => {
          this.$message({
            message: res.msg,
            type: "success",
          });
          this.getRoleList();
          this.dialogEditRoleVisible = false;
        });
      }
    },
    handleDeletePm(index, row) {
      if (confirm("确定删除吗？")) {
        delRequest("/api/pm", {
          permissionId: row.id,
        }).then((res) => {
          this.$message({
            message: res.msg,
            type: "success",
          });
          this.getPmList();
          this.dialogEditPmVisible = false;
        });
      }
    },
    handleEditPmForm() {
      if (confirm("确定提交？")) {
        if (this.pmEditFrom.id) {
          postRequest("/api/pm", this.pmEditFrom).then((res) => {
            this.$message({
              message: res.msg,
              type: "success",
            });
            this.getPmList();
            this.dialogEditPmVisible = false;
          });
        }
        // else {
        //   console.log(this.pmEditFrom);
        //   putRequest("/api/pm", this.pmEditFrom).then((res) => {
        //     this.$message({
        //       message: res.msg,
        //       type: "success",
        //     });
        //     this.getPmList();
        //     this.dialogEditPmVisible = false;
        //   });
        // }
      }
    },
    handleEditRole(index, row) {
      this.roleEditForm = row;
      this.dialogEditRoleVisible = true;
    },
    handleEditPm(index, row) {
      this.pmEditFrom = row;
      this.dialogEditPmVisible = true;
    },
    handleEditRoleForm() {
      // console.log(this.roleEditForm);
      if (confirm("确定修改？")) {
        postRequest("/api/role", this.roleEditForm).then((res) => {
          this.$message({
            message: res.msg,
            type: "success",
          });
          this.getRoleList();
          this.dialogEditRoleVisible = false;
        });
        // else {
        //   delRequest("/api/role", this.roleEditForm).then((res) => {
        //     this.$message({
        //       message: res.msg,
        //       type: "success",
        //     });
        //     this.getRoleList();
        //     this.dialogEditRoleVisible = false;
        //   });
        // }
      }
    },
    handleEditPmForm() {
      postRequest("/api/pm", this.roleEditForm).then((res) => {
        this.$message({
          message: res.msg,
          type: "success",
        });
        this.getPmList();
        this.dialogEditPmVisible = false;
      });
    },
    handleEditRolePermission() {
      postRequest("/api/rolePm/" + this.roleDetailForm.id, {
        idList: this.pmByRoleList,
      }).then((res) => {
        if (res.code == 200) {
          this.$message({
            message: res.msg,
            type: "success",
          });
        }
        this.dialogPmDetailVisible = false;
      });
    },
    handlePmDetail(index, row) {
      this.dialogPmDetailVisible = true;
      this.roleDetailForm = row;
      let id = row.id;
      this.getRolePmList(id);
    },

    getRoleList() {
      getRequest("/api/role").then((res) => {
        this.roleList = res.data.roleList;
      });
    },
    getPmList() {
      getRequest("/api/pm").then((res) => {
        this.pmList = res.data.permissionList;
      });
    },
    getRolePmList(id) {
      getRequest("/api/rolePm/" + id).then((res) => {
        console.log(res);
        this.pmByRoleList = res.data.pmByRoleList;
      });
    },
  },
  beforeMount() {
    this.getPmList();
    this.getRoleList();
  },
};
</script>

<style scoped>
.box {
  height: 100%;
}
</style>
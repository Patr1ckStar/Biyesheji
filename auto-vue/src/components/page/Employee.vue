<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i>员工管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box" v-if="userType === '1'">
        <el-input v-model="query.realName" placeholder="请输入员工姓名" class="handle-input mr10"></el-input>
        <el-button type="primary" plain icon="el-icon-search" @click="getUserInfo" style="margin-left: 20px">查询</el-button>
        <el-button type="warning" plain icon="el-icon-refresh" @click="reset">重置</el-button>
      </div>
      <div class="handle-box">
        <el-button style="margin-bottom: 10px" type="success" v-if="userType === '1'" icon="el-icon-plus"
          class="handle-del mr10" @click="addUserInfo">新增员工</el-button>
      </div>
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column type="index" label="编号" width="80" align="center"></el-table-column>
        <el-table-column prop="realName" label="姓名" align="center"></el-table-column>
        <el-table-column prop="userName" label="账户" align="center"></el-table-column>
        <el-table-column prop="password" label="密码" align="center" v-if="userType === '1'"></el-table-column>
        <el-table-column prop="email" width="120" label="电子邮箱" align="center"></el-table-column>
        <el-table-column prop="address" label="住址" align="center"></el-table-column>
        <el-table-column prop="phone" label="电话" align="center"></el-table-column>
        <el-table-column prop="deptName" label="部门" align="center"></el-table-column>
        <el-table-column prop="jobName" label="岗位" align="center"></el-table-column>
        <!--        <el-table-column prop="jobTwo" label="上任岗位"  align="center"></el-table-column>-->
        <el-table-column prop="sex" label="性别" width="60px" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.sex === '1'">男</span>
            <span v-if="scope.row.sex === '2'">女</span>
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="60px" align="center"></el-table-column>
        <el-table-column prop="imageUrl" label="头像" align="center">
          <template slot-scope="scope">
            <el-image :src="scope.row.imageUrl" style="width: 40px;height: 40px"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="userType" label="用户类型" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.userType === '1'">经理</span>
            <span v-if="scope.row.userType === '2'">用户</span>
            <span v-if="scope.row.userType === '3'">HR</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280" align="center">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" @click="editUserInfo(scope.row)">编辑</el-button>
            <el-button type="danger"  v-if="userType === '1'" icon="el-icon-delete"
              @click="delUserInfo(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination background layout="total, prev, pager, next" :current-page="query.pageNum"
          :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
      </div>
    </div>
    <!-- 添加弹出框 -->
    <el-dialog title="操作" :visible.sync="addVisible" width="40%">
      <el-form ref="form" :model="form" :rules="rule" label-width="70px">
        <el-form-item label="姓名" label-width="100px" prop="realName">
          <el-input v-model="form.realName"></el-input>
        </el-form-item>
        <el-form-item label="用户名" label-width="100px" prop="userName">
          <el-input v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item label="密码" label-width="100px" prop="password" v-if="userType === '1'">
          <el-input v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item label="岗位" label-width="100px" prop="jobId" v-if="userType === '1'">
          <el-select v-model="form.jobId" placeholder="请选择">
            <el-option v-for="item in jobList" :key="item.id" :label="item.jobName" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="部门" label-width="100px" v-if="userType === '1'" prop="deptId">
          <el-select v-model="form.deptId" placeholder="请选择">
            <el-option v-for="item in deptList" :key="item.id" :label="item.deptName" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学历" label-width="100px" v-if="userType === '1'" prop="education">
          <el-select v-model="form.education" placeholder="请选择">
            <el-option label="博士" value="博士"></el-option>
            <el-option label="硕士" value="硕士"></el-option>
            <el-option label="本科" value="本科"></el-option>
            <el-option label="大专及以下" value="大专及以下"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" label-width="100px" v-if="userType === '1'" prop="age">
          <el-select v-model="form.age" placeholder="请选择">
            <el-option v-for="(item, index) in 53" :label="(item + 17)" :value="(item + 17)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电子邮箱" label-width="100px" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="电话" label-width="100px" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="住址" label-width="100px" prop="address">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="头像" label-width="100px" prop="imageUrl">
          <single-upload v-model="form.imageUrl"></single-upload>
        </el-form-item>
        <el-form-item label="性别" label-width="100px" prop="sex">
          <el-radio v-model="form.sex" label="1">男</el-radio>
          <el-radio v-model="form.sex" label="2">女</el-radio>
        </el-form-item>
        <el-form-item label="用户类型" label-width="100px" prop="userType" v-if="userType === '1'">
          <el-radio v-model="form.userType" label="1">管理员</el-radio>
          <el-radio v-model="form.userType" label="2">用户</el-radio>
          <el-radio v-model="form.userType" label="3">HR</el-radio>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveAndUpdateUserInfo">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>


import { getUserList, saveAndUpdateUserInfo, delUserInfo, getJobList } from '../../api/shool';
import SingleUpload from '../common/singleUpload';
import { getDeptInfo } from '../../api/dept';

export default {
  name: 'User',
  components: { SingleUpload },
  data() {



    return {
      query: {
        pageNum: 1,
        pageSize: 10,
        realName: null
      },
      tableData: [],
      option: [],
      pageTotal: 0,
      addVisible: false,
      form: {},
      jobList: [],
      deptList: [],
      rule: {
        deptId: [
          { required: true, message: '请输入该字段', trigger: 'blur' },
        ],
        education: [
          { required: true, message: '请输入该字段', trigger: 'blur' },
        ],
        age: [
          { required: true, message: '请输入该字段', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入该字段', trigger: 'blur' },
          { min: 5, max: 10, message: '密码长度在5到10个字符', trigger: ['blur', 'change'] },
        ],
        sex: [
          { required: true, message: '请输入该字段', trigger: 'blur' },
        ],
        userType: [
          { required: true, message: '请输入该字段', trigger: 'blur' },
        ],

        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码格式', trigger: ['blur', 'change'] },
        ],
        age: [
          { required: true, message: '请输入该字段', trigger: 'blur' },
        ],
        email: [
          { required: true, message: '请输入该字段', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change'] },
        ],
        realName: [
          { required: true, message: '请输入该字段', trigger: 'blur' },
        ],
        userName: [
          { required: true, message: '请输入该字段', trigger: 'blur' },
        ],
        jobId: [
          { required: true, message: '请输入该字段', trigger: 'blur' },
        ],

      }
    };
  },
  created() {
    this.getData();
  },


  computed: {
    userType() {
      return sessionStorage.getItem("userType");
    }
  },

  methods: {

    /**
     * 获取表格中的数据
     */
    getData() {
      if (sessionStorage.getItem('userType') !== '1') {
        this.query.id = sessionStorage.getItem('userId')
      }
      getUserList(this.query).then(res => {
        if (res.code === 1) {
          this.tableData = res.data.list;
          this.pageTotal = res.data.total;
        } else {
          this.$message.error('数据回显异常')
        }
      });
    },

    addUserInfo() {
      this.addVisible = true;
      getJobList({ pageSize: 100 }).then(res => {
        this.jobList = res.data.list;
      });
      getDeptInfo({ pageSize: 100 }).then(res => {
        this.deptList = res.data.list;
      });
      this.form = {};
    },

    saveAndUpdateUserInfo() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id == null) {
            this.form.userType = '2';
          }
          saveAndUpdateUserInfo(this.form).then(res => {
            if (res.code === 1) {
              if (this.form.id == null) {
                this.$message.success('保存成功');
              } else {
                this.$message.success('更新成功');
              }
              this.addVisible = false;
              this.getData();
            } else {
              if (this.form.id != null) {
                this.$message.error('更新失败')
              } else {
                this.$message.error('保存失败');
              }
            }
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });

    },

    editUserInfo(row) {
      this.form.id = row.id;
      this.form = JSON.parse(JSON.stringify(row));
      getJobList({ pageSize: 100 }).then(res => {
        this.jobList = res.data.list;
      });
      getDeptInfo({ pageSize: 100 }).then(res => {
        this.deptList = res.data.list;
      });
      this.addVisible = true;
    },

    delUserInfo(id) {
      this.$confirm('确定要删除这个用户吗?', '提示', {
        type: 'warning'
      }).then(action => {
        if (action === 'confirm') {
          delUserInfo(id).then(res => {
            if (res.code === 1) {
              this.$message.success('删除成功!');
              this.getData();
            } else {
              this.$message.error('删除失败');
            }
          })
        }
      })
    },

    getUserInfo() {
      this.getData();
    },

    handlePageChange(val) {
      this.$set(this.query, 'pageNum', val);
      this.getData();
    },

    reset() {
      this.query.realName = null;
      this.getData();
    },


  }
};
</script>

<style scoped>
.image {
  height: 30px;
}

.handle-box {
  margin-top: 20px;
  margin-bottom: 10px;
}

/deep/ .el-upload--text {
  width: 98px !important;
  height: 34px !important;
  display: inline-grid;
  float: left;
  margin-right: 10px;
  border: 1px solid #DCDFE6;
  border-radius: 3px;
}

.el-upload--text .inline-block {
  display: inline-block;
  border: 0px dashed #d9d9d9
}

.handle-input {
  width: 300px;
  display: inline-block;
}

.table {
  width: 100%;
  font-size: 14px;
}
</style>

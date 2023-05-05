<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i>我的奖金
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-table
              :data="tableData"
              border
              class="table"
              ref="multipleTable"
              header-cell-class-name="table-header"
      >
        <el-table-column type="index" label="编号" width="80" align="center"></el-table-column>
        <el-table-column prop="realName" label="姓名"  align="center"></el-table-column>
        <el-table-column prop="yearBonus" label="奖金"  align="center"></el-table-column>

      </el-table>
    </div>
    <!-- 添加弹出框 -->

  </div>
</template>

<script>


  import { getUserList, saveAndUpdateUserInfo, delUserInfo, getJobList } from '../../api/shool';

  export default {
    name: 'User',
    data() {
      return {
        query: {
          pageNum:1,
          pageSize:10,
          realName:null
        },
        tableData: [],
        option:[],
        pageTotal: 0,
        addVisible:false,
        form:{},
        jobList:[],

      };
    },
    created() {
      this.getData();
    },

    methods: {

      /**
       * 获取表格中的数据
       */
      getData() {
        this.query.id = sessionStorage.getItem("userId");
        getUserList(this.query).then(res => {
          if (res.code === 1) {
            this.tableData = res.data.list;
            this.pageTotal = res.data.total;
          }else {
            this.$message.error('数据回显异常')
          }
        });
      },

      addUserInfo(){
        this.addVisible = true;
        getJobList().then(res =>{
          this.jobList = res.data.list;
        })
        this.form = {};
      },

      saveAndUpdateUserInfo(){
        this.form.userType = '2';
        saveAndUpdateUserInfo(this.form).then(res =>{
          if (res.code === 1){
            if (this.form.id == null){
              this.$message.success('保存成功');
            }else {
              this.$message.success('更新成功');
            }
            this.addVisible = false;
            this.getData();
          }else {
            if (this.form.id != null){
              this.$message.error('更新失败')
            }else {
              this.$message.error('保存失败');
            }
          }
        })
      },

      editUserInfo(row){
        this.form.id = row.id;
        this.form = JSON.parse(JSON.stringify(row));
        getJobList().then(res =>{
          this.jobList = res.data.list;
        })
        this.addVisible = true;
      },

      delUserInfo(id){
        this.$confirm('确定要删除这个用户吗?','提示',{
          type:'warning'
        }).then(action =>{
          if (action === 'confirm'){
            delUserInfo(id).then(res =>{
              if (res.code === 1){
                this.$message.success('删除成功!');
                this.getData();
              }else{
                this.$message.error('删除失败');
              }
            })
          }
        })
      },

      getUserInfo(){
        this.getData();
      },

      handlePageChange(val) {
        this.$set(this.query, 'pageNum', val);
        this.getData();
      },

      reset(){
        this.query.realName = null;
        this.getData();
      },


    }
  };
</script>

<style scoped>

  .image{
    height: 30px;
  }
  .handle-box {
    margin-top: 20px;
    margin-bottom: 10px;
  }
  /deep/ .el-upload--text{
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

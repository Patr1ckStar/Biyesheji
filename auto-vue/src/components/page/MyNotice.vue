<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i>我的通知
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="query.notice" placeholder="请输入公告内容" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="getNoticeInfo" style="margin-left: 20px">查询</el-button>
        <el-button type="primary" icon="el-icon-refresh" @click="reset">重置</el-button>
      </div>
      <div class="handle-box">
        <el-button style="margin-bottom: 10px"
                   type="primary"
                   icon="el-icon-plus"
                   class="handle-del mr10"
                   @click="addNoticeInfo"
        >新增</el-button>
      </div>
      <el-table
              :data="tableData"
              border
              class="table"
              ref="multipleTable"
              header-cell-class-name="table-header"
      >
        <el-table-column type="index" label="编号" width="80" align="center"></el-table-column>
        <el-table-column prop="notice" label="公告"  align="center"></el-table-column>
        <el-table-column label="操作" width="280" align="center">
          <template slot-scope="scope">
            <el-button
                    type="primary"
                    icon="el-icon-edit"
                    @click="editNoticeInfo(scope.row)"
            >编辑</el-button>
            <el-button
                    type="primary"
                    icon="el-icon-edit"
                    @click="delNoticeInfo(scope.row.id)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
                background
                layout="total, prev, pager, next"
                :current-page="query.pageNum"
                :page-size="query.pageSize"
                :total="pageTotal"
                @current-change="handlePageChange"
        ></el-pagination>
      </div>
    </div>
    <!-- 添加弹出框 -->
    <el-dialog title="操作" :visible.sync="addVisible" width="30%">
      <el-form ref="form" :model="form" label-width="70px">
        <el-form-item label="公告" label-width="100px" prop="roleName">
          <el-input v-model="form.notice"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAndUpdateNoticeInfo">确 定</el-button>
            </span>
    </el-dialog>

  </div>
</template>

<script>


  import { getNoticeList, saveAndUpdateNoticeInfo, delNoticeInfo, getJobList } from '../../api/shool';

  export default {
    name: 'Notice',
    data() {
      return {
        query: {
          pageNum:1,
          pageSize:10,
          notice:null
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
        getNoticeList(this.query).then(res => {
          if (res.code === 1) {
            this.tableData = res.data.list;
            this.pageTotal = res.data.total;
          }else {
            this.$message.error('数据回显异常')
          }
        });
      },

      addNoticeInfo(){
        this.addVisible = true;
        getJobList().then(res =>{
          this.jobList = res.data.list;
        })
        this.form = {};
      },

      saveAndUpdateNoticeInfo(){
        this.form.userType = '2';
        saveAndUpdateNoticeInfo(this.form).then(res =>{
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

      editNoticeInfo(row){
        this.form.id = row.id;
        this.form = JSON.parse(JSON.stringify(row));
        getJobList().then(res =>{
          this.jobList = res.data.list;
        })
        this.addVisible = true;
      },

      delNoticeInfo(id){
        this.$confirm('确定要删除这个用户吗?','提示',{
          type:'warning'
        }).then(action =>{
          if (action === 'confirm'){
            delNoticeInfo(id).then(res =>{
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

      getNoticeInfo(){
        this.getData();
      },

      handlePageChange(val) {
        this.$set(this.query, 'pageNum', val);
        this.getData();
      },

      // reset(){
      //   this.query.notice = null;
      //   this.getData();
      // },


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

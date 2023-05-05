<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i>申请管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="query.realName" placeholder="请输入请假时间" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="getApplyInfo" style="margin-left: 20px">查询</el-button>
        <el-button type="primary" icon="el-icon-refresh" @click="reset">重置</el-button>
      </div>
      <div class="handle-box" v-if="userType === '2'">
        <el-button style="margin-bottom: 10px"
                   type="primary"
                   icon="el-icon-plus"
                   class="handle-del mr10"
                   @click="addApplyInfo"
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
        <el-table-column prop="realName" label="申请人"  align="center"></el-table-column>
        <el-table-column prop="email" label="申请人邮箱" width="140" align="center"></el-table-column>
        <el-table-column prop="reason" label="原因"  align="center"></el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="160"  align="center"></el-table-column>
        <el-table-column prop="endTime" label="结束时间" width="160" align="center"></el-table-column>
        <el-table-column prop="type" label="类型"  align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.type === '0'">请假</span>
            <span v-if="scope.row.type === '1'">出差</span>
            <span v-if="scope.row.type === '2'">外出</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态"  align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.status === '0'">申请中</span>
            <span v-if="scope.row.status === '1'">申请通过</span>
            <span v-if="scope.row.status === '2'">申请不通过</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280" align="center"  v-if="userType === '1'">
          <template slot-scope="scope">
            <el-button
                    type="primary"
                    icon="el-icon-edit"
                    @click="editApplyInfo(scope.row)"
            >编辑</el-button>
            <el-button
                    type="primary"
                    icon="el-icon-edit"
                    @click="delApplyInfo(scope.row.id)"
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
    <el-dialog title="申请" :visible.sync="addVisible" width="30%">
      <el-form ref="form" :model="form" label-width="70px" >
        <el-form-item label="原因" label-width="100px" prop="roleName" v-if="userType === '2'">
          <el-input v-model="form.reason"></el-input>
        </el-form-item>
        <el-form-item label="申请类型" label-width="100px" prop="roleName"  v-if="userType === '2'">
          <el-radio v-model="form.type" label="0">请假</el-radio>
          <el-radio v-model="form.type" label="1">出差</el-radio>
          <el-radio v-model="form.type" label="2">外出</el-radio>
        </el-form-item>
        <el-form-item label="开始时间" label-width="100px" prop="roleName" v-if="userType === '2'">
          <el-date-picker
                  v-model="form.startTime"
                  type="datetime"
                  placeholder="选择开始时间"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :picker-options="pikerOptionStart"        
          >
          
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" label-width="100px" prop="roleName" v-if="userType === '2'">
          <el-date-picker
                  v-model="form.endTime"
                  type="datetime"
                  placeholder="选择结束时间"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :picker-options="pikerOptionEnd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="员工" label-width="100px" prop="roleName" v-if="userType === '1'">
          <el-radio v-model="form.status" label="1">通过</el-radio>
          <el-radio v-model="form.status" label="2">不通过</el-radio>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAndUpdateApplyInfo">确 定</el-button>
            </span>
    </el-dialog>

  </div>
</template>

<script>


import { getApplyList, saveAndUpdateApplyInfo, delApplyInfo, getJobList, getUserList } from '../../api/shool';

  export default {
    name: 'Apply',
    data() {
      return {
        query: {
          pageNum:1,
          pageSize:10,
          realName:null,
        },
        options:[],
        tableData: [],
        option:[],
        pageTotal: 0,
        addVisible:false,
        form:{},
        jobList:[],
        pikerOptionStart:{
            disabledDate:(time) =>{
              let sevenDays = 6*24*3600*1000;
                if(this.form.endTime){
                  return(
                    //只能选今天之后的日期
                    time.getTime() < Date.now() ||
                    //开始日期要在结束日期之前
                    time.getTime() > new Date(this.form.endTime).getTime() ||
                    //先选结束日期，结束日期七天之前不能选
                    time.getTime() < new Date(this.form.endTime).getTime() - sevenDays
                  );
               }
               return time.getTime() < Date.now();
               
            }
        },
        pikerOptionEnd:{
            disabledDate:(time) =>{
              let sevenDays = 6*24*3600*1000;
                if(this.form.startTime){
                  return(
                    //只能选今天之后的日期
                    time.getTime() < Date.now() ||
                    //结束日期要在结束日期之前
                    time.getTime() < new Date(this.form.startTime).getTime() ||
                    //先选开始日期，开始日期七天之后不能选
                    time.getTime() > new Date(this.form.startTime).getTime() + sevenDays
                  );
               }
               return time.getTime() < Date.now();
               
            }
        }

      };
    },
    created() {
      this.getData();
    },


    computed:{
      userType(){
        return sessionStorage.getItem("userType");
      }
    },

    methods: {

      /**
       * 获取表格中的数据
       */
      getData() {
        if (this.userType === '2'){
          this.query.userId = sessionStorage.getItem('userId')
        }
        getApplyList(this.query).then(res => {
          if (res.code === 1) {
            this.tableData = res.data.list;
            this.pageTotal = res.data.total;
          }else {
            this.$message.error('数据回显异常')
          }
        });
      },

      addApplyInfo(){
        this.addVisible = true;
        this.form = {};
      },

      saveAndUpdateApplyInfo(){
        if (this.form.id == null){
          this.form.status  = '0';
          this.form.userId = sessionStorage.getItem('userId')
        }
        saveAndUpdateApplyInfo(this.form).then(res =>{

        });
        setTimeout(() =>{
          this.getData();
          this.addVisible =false;
          this.$message.success('操作成功')
        },1500)
      },

      editApplyInfo(row){
        this.form.id = row.id;
        this.form = JSON.parse(JSON.stringify(row));
        this.addVisible = true;
      },

      delApplyInfo(id){
        this.$confirm('确定要删除这个申请吗?','提示',{
          type:'warning'
        }).then(action =>{
          if (action === 'confirm'){
            delApplyInfo(id).then(res =>{
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

      getApplyInfo(){
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

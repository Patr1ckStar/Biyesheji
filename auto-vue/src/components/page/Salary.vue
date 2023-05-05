<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i>薪资管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div >
   
     <div class="echart" id="mychart" :style="myChartStyle" style="padding-top:20px"></div>

    <div class="container">
      <div class="handle-box">
        <el-input v-model="query.realName" placeholder="请输入员工姓名" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="getUserInfo" style="margin-left: 20px">查询</el-button>
        <el-button type="primary" icon="el-icon-refresh" @click="reset">重置</el-button>
      </div>
      <div class="handle-box">
          <el-button type="primary">
            <download-excel 
                :data="tableData" 
                :fields = "json_fields" 
                worksheet = "My Worksheet" 
                name = "工资报表">
                导出Excel</download-excel>
          </el-button>
      </div>
      <el-table
              :data="tableData"
              border
              class="table"
              ref="multipleTable"
              header-cell-class-name="table-header"
      >
        <el-table-column type="index" label="编号" width="80" align="center"></el-table-column>
        <el-table-column prop="realName" label="姓名"  align="center"></el-table-column>
        <el-table-column prop="salaryOne" label="五险一金"  align="center"></el-table-column>
        <el-table-column prop="salaryTwo" label="餐补"  align="center"></el-table-column>
        <el-table-column prop="salaryThree" label="房补"  align="center"></el-table-column>
        <el-table-column prop="salaryFour" label="基本工资"  align="center"></el-table-column>
        <el-table-column prop="salaryFive" label="签到工资"  align="center"></el-table-column>
		    <el-table-column prop="jixiao" label="绩效"  align="center"></el-table-column>
        <el-table-column prop="yearBonus" label="总工资"  align="center"></el-table-column>
        <el-table-column label="操作" width="280" align="center">
          <template slot-scope="scope">
            <el-button
                    type="primary"
                    icon="el-icon-edit"
                    @click="editUserInfo(scope.row)"
            >编辑</el-button>
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
        <el-form-item label="五险一金" label-width="100px" prop="roleName">
          <el-input v-model="form.salaryOne"></el-input>
        </el-form-item>
        <el-form-item label="餐补" label-width="100px" prop="roleName">
          <el-input v-model="form.salaryTwo"></el-input>
        </el-form-item>
        <el-form-item label="房补" label-width="100px" prop="roleName">
          <el-input v-model="form.salaryThree"></el-input>
        </el-form-item>
		<el-form-item label="绩效" label-width="100px" prop="roleName">
		  <el-input v-model="form.jixiao"></el-input>
		</el-form-item>
        <el-form-item label="基本工资" label-width="100px" prop="roleName">
          <el-input v-model="form.salaryFour"></el-input>
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

  import * as echarts from "echarts";
  import { getUserList, saveAndUpdateUserInfo, delUserInfo, getRoleList } from '../../api/shool';

  export default {
    name: 'User',
    data() {
      return {
        
        json_fields: {  //导出Excel表格的表头设置
            '序号': 'number',
            '姓名': 'realName',
            '基本工资': 'salaryFour',
            '五险一金': 'salaryOne',
            '餐补': 'salaryTwo',
            '房补': 'salaryThree',
            '绩效': 'jixiao',
            '签到工资': 'salaryFive',
            '总工资': 'yearBonus',
          },
          

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
        xData: ["4000~5999", "6000~7999", "8000~9999", "10000+"], //横坐标
        yData: [1,4,2,1], //数据
        myChartStyle: { float: "left", width: "100%", height: "400px" } //图表样式

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
        getUserList(this.query).then(res => {
          if (res.code === 1) {
            this.tableData = res.data.list;
            var num=res.data.endRow
            var i=0
            this.yData[0] = 0
            this.yData[1] = 0
            this.yData[2] = 0
            this.yData[3] = 0
            for(i=0;i<num;i++){
                this.tableData[i].number = i+1
                if(this.tableData[i].yearBonus>=4000&&this.tableData[i].yearBonus<=5999){
                  this.yData[0]++
                }else if(this.tableData[i].yearBonus>=6000&&this.tableData[i].yearBonus<=7999){
                  this.yData[1]++
                }else if(this.tableData[i].yearBonus>=8000&&this.tableData[i].yearBonus<=9999){
                  this.yData[2]++
                }else if(this.tableData[i].yearBonus>=10000){
                  this.yData[3]++
                }
            }
            console.log(this.yData)
            console.log(this.tableData)
            this.initEcharts();
                }else {
                  this.$message.error('数据回显异常')
                }
        });


        getRoleList({pageNum:1,pageSize:10000}).then(res =>{
          if (res.code === 1){
            this.option = res.data.list;
          }else{
            console.log('异常')
          }
        })
      },

      addUserInfo(){
        this.addVisible = true;
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


      //  折线图
      initEcharts() {
      console.log(this.yData)
      var option = {
        title: {
						text: '员工工资分布情况统计图',
						left: 'center'
					},
        xAxis: {
          data: this.xData
        },
        yAxis: {},
        series: [
          {
            type: "line", //形状为折线图
            data: this.yData,
            label: {
              show: true,
              position: 'top',
              textStyle: {
                fontSize: 20
              }
            } 
          }
        ]
      };
      var myChart = echarts.init(document.getElementById("mychart"));
      myChart.setOption(option);
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        myChart.resize();
      });

      console.log(this.yData)
      },
      
    },
    mounted() {
    
    },
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

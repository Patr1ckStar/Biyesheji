<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod__invite">
      <el-form :inline="true" :model="query" @keyup.enter.native="getData()">
    <el-form-item>
    <el-input v-model="query.realName" placeholder="姓名" clearable></el-input>
    </el-form-item>
    <el-form-item>
					<el-button type="primary" plain @click="getData()">查询</el-button>
					<el-button type="warning" plain @click="reset()">重置</el-button>
		</el-form-item>
   <el-form-item >
          <el-button :disabled="this.multipleSelection.length === 0" type="danger" plain @click="delBatchInviteInfo">批量删除</el-button>
   </el-form-item>

  </el-form>
  <div class="handle-box">
        		<el-button style="margin-bottom: 10px" type="success" icon="el-icon-plus"
          		class="handle-del mr10" @click="addInviteInfo">录入离职信息</el-button>
     		 </div>
      <el-table 
            v-loading="tableDataLoading" 
            :data="tableData" 
            border 
            @selection-change="selectionChange" 
            style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
       <el-table-column prop="realName" label="姓名" header-align="center" align="center"></el-table-column>
       <el-table-column prop="phone" label="联系方式" header-align="center" align="center"></el-table-column>
       <el-table-column prop="imageUrl" label="照片"  header-align="center" align="center">
          <template slot-scope="scope">
              <el-image
                      style="width: 60px; height: 60px"
                      :src="scope.row.imageUrl"
                      :preview-src-list="[scope.row.imageUrl]">
              </el-image>
          </template>
       </el-table-column>
       <el-table-column prop="remark" label="离职原因" header-align="center" align="center"></el-table-column>
	   <el-table-column prop="gongzi" label="应发工资" header-align="center" align="center"></el-table-column>
       <el-table-column prop="interviewTime" label="离职时间" width="160" header-align="center" align="center"></el-table-column>
       <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="updateInviteInfo(scope.row)">更新</el-button>
            <el-button type="danger" size="small" @click="delInviteInfo(scope.row.id)">删除</el-button>
          </template>
      </el-table-column>
     </el-table>
      <el-pagination
              :current-page="query.pageNum"
              :page-sizes="[10, 20, 50, 100]"
              :page-size="query.pageSize"
              :total="total"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="pageSizeChange"
              @current-change="pageCurrentChange">
      </el-pagination>
      <!-- 弹窗, 新增 / 修改 -->
      <el-dialog :visible.sync="addOrUpdateVisible" :title="!form.id ? '新增' : '编辑'" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form :model="form" :rules="rule" ref="form" @keyup.enter.native="formSubmitHandle()" label-width="100px">
			<el-form-item label="员工" prop="realName">
			  <el-select @change="changeUser" v-model="form.userid" placeholder="请选择">
			    <el-option
			            v-for="item in userlist"
			            :key="item.id"
			            :label="item.realName"
			            :value="item.id">
			    </el-option>
			  </el-select>  
			</el-form-item>
          <el-form-item label="姓名" prop="realName">
            <el-input readonly v-model="form.realName" placeholder="姓名"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
              <el-input v-model="form.phone" placeholder="电话"></el-input>
          </el-form-item>
          <el-form-item label="离职原因" prop="remark">
            <el-input v-model="form.remark" placeholder="离职原因"></el-input>
          </el-form-item>
          <el-form-item label="离职时间" prop="interviewTime">
            <el-date-picker
                    class="runPicker"
                    v-model="form.interviewTime"
                    type="datetime"
                    placeholder="请选择离职时间"
                    value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
        </el-form>
        <template slot="footer">
          <el-button @click="addOrUpdateVisible = false">取消</el-button>
          <el-button type="primary" @click="formSubmitHandle()">确认</el-button>
        </template>
      </el-dialog>

    </div>
  </el-card>
</template>

<script>
  import SingleUpload from '../common/singleUpload';
  import { getUserList} from '../../api/shool';
  import {getInviteInfo,saveInviteInfo,updateInviteInfo,delInviteInfo,delBatchInviteInfo} from '../../api/invite'



  export default {
    components: { SingleUpload },

    data () {
      return {
		    userlist:[],
        tableData: [],
        query: {
          pageNum: 1,
          pageSize: 10,
          realName:null,
        },
        form: {},
        multipleSelection: [],
        tableDataLoading: false,
        total: 0,
        addOrUpdateVisible: false,
        rule: {
         realName: [{required: true, message: '姓名不得为空', trigger: "blur"}],
         phone: [{required: true, message: '联系方式不得为空', trigger: 'blur'}],
         remark: [{required: true, message: '离职原因不得为空', trigger: "blur"}],
         isHire: [{required: true, message: '是否录用不得为空', trigger: "change"}],
         createTime: [{required: true, message: '创建时间不得为空', trigger: "blur"}],
         interviewTime: [{required: true, message: '离职时间不得为空', trigger: "blur"}],
        },
    }
   },
    created() {
      this.getData();
    },

    methods:{
		changeUser(){
			for (var i = 0; i < this.userlist.length; i++) {
				if(this.form.userid==this.userlist[i].id){
					this.form.realName = this.userlist[i].realName
					this.form.phone = this.userlist[i].phone
				}
			}
		},
      /**
       * 获取所有招聘人员
       */
      getData(){
          this.query.type =2;
        getInviteInfo(this.query).then(res =>{
          this.tableData = res.data.list;
          this.total = res.data.total;
        })
		      getUserList({
          pageNum:1,
          pageSize:1000,
          realName:null
        }).then(res=>{
			  this.userlist = res.data.list;
			// for (var i = 0; i < this.userlist.length; i++) {
			// 	if(this.userlist[i].userType)
			// }
		})
      },


      /**
       * 打开添加框
       */
      addInviteInfo(){
        this.form = {};
        this.form.id = null;
        if (this.$refs['form'] !==undefined){
          this.$refs['form'].resetFields();
        }
        this.addOrUpdateVisible = true;
      },

      /**
       * 打开更新框
       */
      updateInviteInfo(row){
        this.form = JSON.parse(JSON.stringify(row));
        this.addOrUpdateVisible = true;
        },

      /**
       * 按住enter键或者确定按钮提交数据,对后台发请求
       * 如果id为null,就是走添加招聘人员的接口
       * 如果id不为null,就是走更新招聘人员的接口
       */
      formSubmitHandle(){
        this.$refs['form'].validate(valid => {
                if(valid){
          if (this.form.id === null){
              this.form.type = 2;
            saveInviteInfo(this.form).then(res =>{
              this.$message.success('添加成功');
              this.getData();
              this.addOrUpdateVisible = false;
            })
          }else {
            updateInviteInfo(this.form).then(res =>{
              this.$message.success('更新成功');
              this.getData();
              this.addOrUpdateVisible = false;
            })
          }
          // this.$refs['form'].resetFields();


        }
        });
      },

      /**
       *单个删除数据
       */
      delInviteInfo(id){
        this.$confirm('确定要删除所选择的吗？', '提示', {
          type: 'warning'
        }).then(action => {
          if (action === 'confirm') {
            delInviteInfo(id).then(res=>{
              if (res.code === 1){
                this.$message.success('删除成功');
                this.getData();
              }else {
                return this.$message.error(res.msg)
              }

            })
          }
        }).catch(() => {});

      },



      /**
       * 选中table表格事件
       */
      selectionChange(val){
        this.multipleSelection = [];
        val.forEach((item) => {
          this.multipleSelection.push(item.id);
        });
      },

        /**
         * 批量删除招聘人员的接口
         */
      delBatchInviteInfo(){
        delBatchInviteInfo(this.multipleSelection.join(',')).then(res =>{
          this.$message.success('批量删除成功');
          this.getData();
        })
      },

      /**
       * 重置数据
       */
      reset(){
        this.query.realName = null;
                  this.$set(this.query, 'pageNum', 1);
        this.getData();
      },


      /**
       * 分页(改变页码)
       * @param val
       */
      pageCurrentChange(val){
        this.$set(this.query, 'pageNum', val);
        this.getData();
      },

      /**
       * 分页(改变每页条数)
       * @param val
       */
      pageSizeChange(val){
        this.$set(this.query, 'pageSize', val);
        this.getData();
      },

      /**
       * 关闭弹窗
       */
      clearAddForm(){
        this.addOrUpdateVisible = false;
      }
    },


  }
</script>

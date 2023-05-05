<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod__dept">
      <el-form :inline="true" :model="query" @keyup.enter.native="getData()">
    <el-form-item>
    <el-input v-model="query.deptName" placeholder="部门名" clearable></el-input>
    </el-form-item>
   <el-form-item>
          <el-button type="primary" @click="getData()">查询</el-button>
          <el-button type="primary" @click="reset()">重置</el-button>
   </el-form-item>
   <el-form-item>
          <el-button type="primary" @click="addDeptInfo">添加</el-button>
        </el-form-item>
   <el-form-item>
          <el-button :disabled="this.multipleSelection.length === 0" type="primary" @click="delBatchDeptInfo">批量删除</el-button>
   </el-form-item>
  </el-form>
      <el-table v-loading="tableDataLoading" :data="tableData" border @selection-change="selectionChange" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
       <el-table-column prop="deptName" label="部门名" header-align="center" align="center"></el-table-column>
       <el-table-column prop="remark" label="描述信息" header-align="center" align="center"></el-table-column>
	   <el-table-column prop="userList" label="部门人员" header-align="center" align="center">
		   <template slot-scope="scope">
		     <el-button type="primary" size="small" @click="LookDeptUser(scope.row)">查看</el-button>
		   </template>
	   </el-table-column>
       <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="updateDeptInfo(scope.row)">更新</el-button>
            <el-button type="primary" size="small" @click="delDeptInfo(scope.row.id,scope.row)">删除</el-button>
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
    <el-form-item label="部门名" prop="deptName">
      <el-input v-model="form.deptName" placeholder="部门名"></el-input>
    </el-form-item>
    <el-form-item label="描述信息" prop="remark">
      <el-input v-model="form.remark" placeholder="描述信息"></el-input>
    </el-form-item>
 </el-form>
        <template slot="footer">
          <el-button @click="addOrUpdateVisible = false">取消</el-button>
          <el-button type="primary" @click="formSubmitHandle()">确认</el-button>
        </template>
      </el-dialog>


<el-dialog :visible.sync="lookUserlistDialog">
	<el-table :data="userlist">
		<el-table-column prop="realName" label="员工姓名"></el-table-column>
	</el-table>
</el-dialog>
    </div>
  </el-card>
</template>

<script>
    import SingleUpload from '../common/singleUpload';
  import {getDeptInfo,saveDeptInfo,updateDeptInfo,delDeptInfo,delBatchDeptInfo} from '../../api/dept'



  export default {
    components: { SingleUpload },

    data () {
      return {
        tableData: [],
        query: {
          pageNum: 1,
          pageSize: 10,
          deptName:null,
        },
        form: {},
        multipleSelection: [],
        tableDataLoading: false,
        total: 0,
        addOrUpdateVisible: false,
        rule: {
         deptName: [{required: true, message: '部门名不得为空', trigger: "blur"}],
         createTime: [{required: true, message: '创建时间不得为空', trigger: "blur"}],
         remark: [{required: true, message: '描述信息不得为空', trigger: "blur"}],
        },
		lookUserlistDialog:false,
		userlist:[],
		multipleSelectionData : []
    }
   },
    created() {
      this.getData();
    },

    methods:{
		LookDeptUser(row){
			this.userlist = row.userList
			this.lookUserlistDialog = true
		},
      /**
       * 获取所有部门信息
       */
      getData(){
        getDeptInfo(this.query).then(res =>{
          this.tableData = res.data.list;
          this.total = res.data.total;
        })
      },


      /**
       * 打开添加框
       */
      addDeptInfo(){
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
      updateDeptInfo(row){
        this.form = JSON.parse(JSON.stringify(row));
        this.addOrUpdateVisible = true;
        },

      /**
       * 按住enter键或者确定按钮提交数据,对后台发请求
       * 如果id为null,就是走添加部门信息的接口
       * 如果id不为null,就是走更新部门信息的接口
       */
      formSubmitHandle(){
        this.$refs['form'].validate(valid => {
                if(valid){
          if (this.form.id === null){
            saveDeptInfo(this.form).then(res =>{
              this.$message.success('添加成功');
              this.getData();
              this.addOrUpdateVisible = false;
            })
          }else {
            updateDeptInfo(this.form).then(res =>{
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
      delDeptInfo(id,row){
		  if(row.userList.length>0){
			  this.$message.error("该部门底下有关联员工，不能删除")
			  return
		  }
		  
		  
        this.$confirm('确定要删除所选择的吗？', '提示', {
          type: 'warning'
        }).then(action => {
          if (action === 'confirm') {
            delDeptInfo(id).then(res=>{
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
		this.multipleSelectionData = [];
        val.forEach((item) => {
          this.multipleSelection.push(item.id);
		  this.multipleSelectionData.push(item);
        });
      },

        /**
         * 批量删除部门信息的接口
         */
      delBatchDeptInfo(){
	for (var i = 0; i < this.multipleSelectionData.length; i++) {
		if(this.multipleSelectionData[i].userList.length>0){
			this.$message.error("所选部门底下有关联员工，不能删除")
			return
		}
	}
        delBatchDeptInfo(this.multipleSelection.join(',')).then(res =>{
          this.$message.success('批量删除成功');
          this.getData();
        })
      },

      /**
       * 重置数据
       */
      reset(){
        this.query.deptName = null;
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

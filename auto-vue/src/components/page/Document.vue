<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i>日志管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input class="handle-input mr10" v-model="query.name" placeholder="请输入日志名"></el-input>
<!--                <el-input class="handle-input mr10" v-model="query.content" placeholder="请输入错题"></el-input>-->
                <el-button type="primary" icon="el-icon-search" style="margin-left: 20px" @click="getDocumentInfo">查询</el-button>
                <el-button type="primary" icon="el-icon-refresh" @click="reset">重置</el-button>
            </div>
            <div class="handle-box" style="display: inline-flex;">
                <el-button style="margin-bottom: 10px"
                           type="primary"
                           icon="el-icon-plus"
                           v-if="userType === '2'"
                           class="handle-del mr10"
                           @click="addDocumentInfo"
                >新增</el-button>
            </div>
            <el-table
                    :data="tableData"
                    border
                    class="table"
                    ref="multipleTable"
                    header-cell-class-name="table-header"
            >
                <el-table-column type="index" label="序号" align="center"></el-table-column>
                <el-table-column prop="name" label="日志名" align="center"></el-table-column>
                <el-table-column prop="createTime" label="发布时间" align="center"></el-table-column>
                <el-table-column prop="documentName" label="文档" align="center">
                    <template slot-scope="scope">
                        <span style="cursor: pointer;color: green" v-if="scope.row.documentUrl !=null" @click="downloadPaper(scope.row.documentUrl,scope.row.realname)">{{scope.row.name}}</span>
                        <span v-else>暂无</span>
                    </template>
                </el-table-column>
                <el-table-column prop="realName" label="提交人" align="center"></el-table-column>
                <el-table-column label="操作" width="280" align="center"  v-if="userType === '2'">
                    <template slot-scope="scope">
                        <el-button
                                type="primary"
                                icon="el-icon-edit"
                                @click="editDocumentInfo(scope.row)"
                        >修改</el-button>
                        <el-button

                                type="primary"
                                icon="el-icon-delete"
                                @click="deleteDocumentInfo(scope.row)"
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
        <el-dialog title="日志信息" :modal="false"  :visible.sync="addVisible" width="33%" :before-close="clearAddForm">
            <el-form label-position="left"  ref="addForm" label-width="80px"  :model="form" >
                <el-form-item label="日志名" prop="userName">
                   <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="日志文档" prop="userName">
                    <single-upload v-model="form.documentUrl"></single-upload>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="clearAddForm">取 消</el-button>
                <el-button type="primary" @click="saveAndUpdateDocumentInfo">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>

    import { delDocumentInfo, getDocumentList, saveAndUpdateDocumentInfo } from '../../api/shool';
    import SingleUpload from '../common/singleUpload';

    export default {
        name: 'Document',
        components: { SingleUpload },
        data() {
            return {
                options:[],
                query: {
                    pageNum:1,
                    pageSize:10,
                    name:null,
                },
                tableData:[],
                addVisible: false,
                addVisible2:false,
                form:{},
                pageTotal: 0,
                answerList:[],
                info:{}

            };
        },
        created() {
            this.getData();
        },

        computed:{
            userType(){
                return sessionStorage.getItem('userType')
            }
        },

        methods: {
            // 获取所有数组(支持分页和关键字查询)
            getData() {
                if (sessionStorage.getItem('userType') === '2'){
                    this.query.userId = sessionStorage.getItem('userId')
                }
                getDocumentList(this.query).then(res => {
                    if (res.code === 1){
                        this.tableData = res.data.list;
                        this.pageTotal = res.data.total;
                    }else {
                        console.log('异常');
                    }

                });
            },


            //单个删除
            deleteDocumentInfo(row){
                this.$confirm('确定要删除所选择的吗？', '提示', {
                    type: 'warning'
                }).then(action => {
                    if (action === 'confirm') {
                        if (row.userType=== '1'){
                            this.$message.warning('管理员用户无法删除');
                        }else {
                            this.form.id = row.id;
                            delDocumentInfo(this.form.id).then(res=>{
                                if (res.code === 1){
                                    this.$message.success('删除成功');
                                    this.getData();
                                }else {
                                    this.$message.error('删除失败');
                                }

                            })
                        }
                    }
                }).catch(() => {});

            },


            editDocumentInfo(row){
                 this.form = JSON.parse(JSON.stringify(row))
                this.addVisible = true;
            },
            //重置
            reset(){
                this.query.name= null;
                this.getData();
            },

            getDocumentInfo(){
                this.getData();
            },

            //添加用户信息
            addDocumentInfo(){
                this.form = {};
                this.addVisible = true;
            },

            saveAndUpdateDocumentInfo(){
                this.form.userId = sessionStorage.getItem('userId')
                saveAndUpdateDocumentInfo(this.form).then(res =>{
                    if (res.code === 1){
                        this.getData();
                        this.$message.success('操作成功');
                        this.addVisible = false;
                    }else {
                        this.$message.error('操作失败')
                    }
                })

            },


            clearAddForm(){
                this.addVisible = false;
                this.addVisible2 = false;
            },

            downloadPaper(url, name) {// 下载图片地址和图片名
                window.location.href = url;

            },

            // 分页导航
            handlePageChange(val) {
                this.$set(this.query, 'pageNum', val);
                this.getData();
            },


        }
    };
</script>

<style scoped>
    .handle-box {
        margin-top: 20px;
        margin-bottom: 10px;
    }
    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .table {
        width: 100%;
        font-size: 14px;
    }
    .crop-demo-btn{
        position: relative;
        width: 98px;
        height: 32px;
        line-height: 32px;
        padding: 0 15px;
        background-color: #409eff;
        color: #fff;
        font-size: 12px;
        border-radius: 4px;
        box-sizing: border-box;
    }
    .crop-input{
        position: absolute;
        width: 98px;
        height: 32px;
        left: 0;
        top: 0;
        opacity: 0;
        cursor: pointer;
    }  .ListCnt ul {
           border-bottom: 1px solid #EBEBEB;
           display: block;
           width: 100%;
           margin: 15px 0 0 0;
       }
    .ListCnt ul {
        margin: 20px 0 0 0;
        padding: 0;
        display: block;
        width: 100%;
    }
    ul {
        margin: 0px;
        padding: 0px;
        list-style-type: none;
    }
    ul {
        display: block;
        list-style-type: disc;
        margin-block-start: 1em;
        margin-block-end: 1em;
        margin-inline-start: 0px;
        margin-inline-end: 0px;
        padding-inline-start: 40px;
    }
    .ListCnt ul li {
        display: block;
        color: #000;
        line-height: 280%;
        padding: 12px;
        margin: 0;
        border-top: 1px solid #EBEBEB;
        background: #FFF;
        display: inline-block;
        color: #555;
        width: 96%;
        font-family: "arial","Microsoft Yahei";
    }li {
         display: list-info;
         text-align: -webkit-match-parent;
     }
    .ListCnt ul li i {
        display: block;
        width: 50px;
        float: left;
        text-align: center;
    }
    i, em {
        font-style: normal;
    }
    .ListCnt ul li a {
        display: block;
        float: left;
        width: 720px;
        padding: 0;
        color: #333;
        font-size: 14px;
        font-family: "arial","Microsoft Yahei";
    }
   /deep/ .el-upload{
        height: 40px;
    }

</style>

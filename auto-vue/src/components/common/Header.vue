<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="collapse-btn" @click="collapseChage">
        <!-- <div class="collapse-btn" @click="collapseChage"> -->
            <i v-if="!collapse" class="el-icon-s-fold"></i>
            <i v-else class="el-icon-s-unfold"></i>
        </div>
        <div @click="toIndex" class="logo">凯达公司员工管理系统</div>
        <div class="header-right">
            <div class="header-user-con">
                <!-- 全屏显示 -->
                <div class="btn-fullscreen" @click="handleFullScreen">
                    <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                        <i class="el-icon-rank"></i>
                    </el-tooltip>
                </div>
                <!-- 用户头像 -->
                <div class="user-avator">
                    <img :src="form.imageUrl" />
                </div>
                <!-- 用户名下拉菜单 -->
                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{username}}
                        <i class="el-icon-caret-bottom"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="updatePassword">修改密码</el-dropdown-item>
                        <el-dropdown-item command="loginout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
        <el-dialog title="操作" :visible.sync="addVisible" width="30%">
            <el-form ref="form" :rules="rule" :model="form" label-width="70px">
                <el-form-item label="原密码" label-width="100px" prop="oldPassword">
                    <el-input v-model="form.oldPassword" show-password></el-input>
                </el-form-item>
                <el-form-item label="新密码" label-width="100px" prop="newPassword">
                    <el-input v-model="form.newPassword" show-password></el-input>
                </el-form-item>
                <el-form-item label="确认密码" label-width="100px" prop="newPasswordCheck">
                    <el-input v-model="form.newPasswordCheck" show-password></el-input>
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
import bus from '../common/bus';
import { getUserList,saveAndUpdateUserInfo } from '../../api/shool';
export default {
    data() {
        return {
            form:{
                id:sessionStorage.getItem("userId"),
                oldPassword:'',
                newPassword:'',
                newPasswordCheck:'',
            },
            addVisible:false,
            collapse: false,
            fullscreen: false,
            name: 'linxin',
            systemTitle:'',

            rule:{
                oldPassword: [
                    { required: true, message: '请输入原始密码', trigger:['blur', 'change'] },
                    { min: 5, max: 10, message: '密码长度在5到10个字符', trigger: ['blur', 'change'] },
                ],
                newPassword: [
                    { required: true, message: '请输入新密码', trigger:['blur', 'change'] },
                    { min: 5, max: 10, message: '密码长度在5到10个字符', trigger: ['blur', 'change'] },
                ],
                newPasswordCheck: [
                    { required: true, message: '请再次输入新密码', trigger:['blur', 'change'] },
                    { min: 5, max: 10, message: '密码长度在5到10个字符', trigger: ['blur', 'change'] },
                ]
            }
        };
    },
    computed: {
        username() {
            let username = sessionStorage.getItem('realName');
            return username ? username : this.name;
        }
    },

    created() {
        this.getData();
    },

    methods: {
		
		toIndex(){
			console.log(sessionStorage.getItem("userType"));
			if(sessionStorage.getItem("userType").toString()=="1"){
				this.$router.push("/index")
			}
		},

        getData(){
            getUserList({id:sessionStorage.getItem('userId')}).then(res =>{
                this.form = res.data.list[0]
                
                console.log(this.form)
            })
            
        },










        // 用户名下拉菜单选择事件
        handleCommand(command) {
            if (command === 'loginout') {
               // localStorage.removeItem('ms_username');
                sessionStorage.clear();
                window.location.reload();
                this.$router.push('/login');
            }
            if (command === 'updatePassword'){
                this.addVisible = true;
            }
        },




        saveAndUpdateUserInfo(){
            if(this.form.oldPassword == this.form.password){
                if(this.form.newPassword == this.form.oldPassword ){
                    this.$message.error('新密码不能和原密码相同')
                }
                else if(this.form.newPassword != this.form.newPasswordCheck){
                    this.$message.error('确认密码与新密码不一致')
                }
                else{
                    this.form.password=this.form.newPassword
                    saveAndUpdateUserInfo(this.form).then(res =>{
                        if (res.code === 1){
                            this.$message.success('修改成功');
                            this.addVisible = false;
                            this.$router.push('/login');
                        }else {
                            this.$message.error('修改失败')
                        }
                    })
                }
            }
            else{
                this.$message.error('请输入正确的原始密码')
            }
            
        },

        // 侧边栏折叠***********************
        collapseChage() {
            this.collapse = !this.collapse;
            bus.$emit('collapse', this.collapse);
        },

        // 全屏事件
        handleFullScreen() {
            let element = document.documentElement;
            if (this.fullscreen) {
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen();
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                } else if (document.msExitFullscreen) {
                    document.msExitFullscreen();
                }
            } else {
                if (element.requestFullscreen) {
                    element.requestFullscreen();
                } else if (element.webkitRequestFullScreen) {
                    element.webkitRequestFullScreen();
                } else if (element.mozRequestFullScreen) {
                    element.mozRequestFullScreen();
                } else if (element.msRequestFullscreen) {
                    // IE11
                    element.msRequestFullscreen();
                }
            }
            this.fullscreen = !this.fullscreen;
        },


    },
    mounted() {
        const that = this;
        if (document.body.clientWidth < 1500) {
            this.collapseChage();
        }
    },

};
</script>
<style scoped>
.header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    color: #fff;
}
.collapse-btn {
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 70px;
}
.header .logo {
    float: left;
    width: 280px;
    line-height: 70px;
}
.header-right {
    float: right;
    padding-right: 50px;
}
.header-user-con {
    display: flex;
    height: 70px;
    align-items: center;
}
.btn-fullscreen {
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
}
.btn-bell,
.btn-fullscreen {
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
}
.btn-bell-badge {
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
}
.btn-bell .el-icon-bell {
    color: #fff;
}
.user-name {
    margin-left: 10px;
}
.user-avator {
    margin-left: 20px;
}
.user-avator img {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}
.el-dropdown-link {
    color: #fff;
    cursor: pointer;
}
.el-dropdown-menu__item {
    text-align: center;
}
</style>

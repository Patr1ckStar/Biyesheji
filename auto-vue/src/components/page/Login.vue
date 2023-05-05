<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">凯达公司员工管理系统</div>
            <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="userName">
                    <el-input v-model="param.userName" placeholder="userName">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                        type="password"
                        placeholder="password"
                        v-model="param.password"
                        @keyup.enter.native="submitForm()"
                    >
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">登录</el-button>
                </div>
            </el-form>
        </div>

       
    </div>
</template>

<script>


    import { Login, saveAndUpdateUserInfo } from '../../api/shool';

export default {
    data: function() {
        return {
            param: {
                username: '',
                password: '',
            },
            addVisible:false,
            form:{
            },
            rules: {
                userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 5, max: 10, message: '长度在 5到 10 个字符' }],
                userType: [{ required: true, message: '请选择用户类型', trigger: 'blur' }],
            },
        };
    },
    methods: {

        submitForm() {
            this.$refs.login.validate(valid => {
                if (valid) {
                    Login(this.param).then(res =>{
                        if (res.code === 1){
                            sessionStorage.setItem("userStatus","1");
                            sessionStorage.setItem("userId",res.data.id);
                            sessionStorage.setItem("userType",res.data.userType);
                            sessionStorage.setItem("projectId",res.data.projectId);
                            this.$message.success('登录成功');
                            sessionStorage.setItem("realName",res.data.realName);
                            if (res.data.userType === '1'){
                                this.$router.push('/index')
                            }else {
                                this.$router.push('/sign');
                            }

                        }else{
                            this.$message.error('登录失败')
                        }
                    })
                } else {
                    this.$message.error('请输入账号和密码');
                    return false;
                }
            });
        },

        openRegister(){
            this.form = {};
            this.addVisible = true;
        },

        saveAndUpdateUserInfo(){
            saveAndUpdateUserInfo(this.form).then(res =>{
                if (res.code === 1){
                    this.$message.success('注册成功');
                    this.addVisible = false;
                }else {
                    this.$message.error('注册失败');
                }
            })
        }
    },
};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../../assets/img/login-bg.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>

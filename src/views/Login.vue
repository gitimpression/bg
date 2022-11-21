<template>
  <div>
    <el-form ref="loginForm" :model="loginForm" class="loginForm" :rules="loginFormDataRules">
        <h2 class="loginTitle">系统登录</h2>
        <el-form-item prop="username">
            <el-input type="text" v-model="loginForm.username" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item prop="password">
            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item prop="code">
            <el-input class="loginCodeInput" type="text" auto-complete="false" v-model="loginForm.code" placeholder="点击更换图片验证码"/>
            <!-- <img :src='verifyImgUrl' class='loginVerifyImg'/> -->
        </el-form-item>
        <el-checkbox v-model="loginForm.remember" class="loginRemember">记住我</el-checkbox>
        <el-button type="primary" @click="login()" class="loginBtn">登录</el-button>
    </el-form>
  </div>
</template>

<script>
export default {
    name: "Login",
    data() {
        return {
            verifyImgUrl: '',
            loginForm: {
                username: '',
                password: '',
                code: '',
                remember: false
            },
            loginFormDataRules:{
                username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                password: [{required: true, message: '请输入密码', trigger: 'blur'}],
                code: [{required: true, message: '请输入验证码', trigger: 'blur'}],
            }
        }
    },
    methods: {
        login(){
            // 校验数据
            this.$refs.loginForm.validate((vaild) => {
                if (vaild) {
                    alert('检验成功')
                } else {
                    this.$message.erroer("校验错误")
                    return false
                }
            })
        }
    }
}
</script>

<style scoped>
    .loginForm{
        max-width: 500px;
        border-radius: 10px;
        border: 1px solid #efefefc3;
        padding: 20px;
        margin: 20px auto;
    }
    .loginTitle{
        margin: 10px auto;
        text-align: center;
    }
    .loginCodeInput{
        width: 60%;
    }
    
    .loginVerifyImg{
        margin-left: 10%;
        width: 45%;
        height: 40px;
    }
    .loginRemember{
        margin-bottom: 10px;
    }
    .loginBtn{
        width: 100%;
    }

</style>
<template>
  <div>
    <el-form ref="loginForm" 
            :model="loginForm" 
            class="loginForm" 
            :rules="rules"
            v-loading.fullscreen="loading"
            element-loading-text="loading..."
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.2)"
    >
        <h2 class="loginTitle">系统登录</h2>
        <el-form-item prop="username">
            <el-input type="text" v-model="loginForm.username" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item prop="password">
            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item prop="code">
            <el-row type="flex" justify="space-between">
                <el-col :span="14">
                    <el-input class="loginCodeInput" type="text" auto-complete="false" v-model="loginForm.code" placeholder="点击更换图片验证码"/>
                </el-col>
                <el-col :span="9">
                    <img :src='verifyImgUrl' class='loginVerifyImg' @click="getVerifyCodeImg()"/>
                </el-col>
            </el-row>
        </el-form-item>
        <el-row type="flex" justify="space-between" style="margin: 10px auto">
            <el-col style="width: auto">
                <el-checkbox v-model="loginForm.remember" class="loginRemember">3天免登录</el-checkbox>
            </el-col>
            <el-col style="width: auto">
                <el-link type="primary">没有账号？去注册</el-link>
            </el-col>
        </el-row>
        <el-button type="primary" @click="login()" class="loginBtn">登录</el-button>
        <el-row type="flex" justify="end" style="margin: 10px auto">
            <el-col style="width: auto">
                <el-link type="primary">找回密码</el-link>
            </el-col>
        </el-row>
    </el-form>
  </div>
</template>

<script>
import validator from '@/util/validator'
import { getRequest, postRequest } from '@/util/api'
import keysProperties from '@/config/keysProperties'

export default {
    name: "Login",
    data() {
        return {
            verifyImgUrl: '',
            loading: false, // 遮罩
            loginForm: {// 用户输入内容
                username: 'admin',
                password: '123456',
                code: '',
                remember: false
            },
            rules:{ // 验证规则
                username: [{
                    validator: validator.uName, 
                    trigger: 'blur'}],
                password: [{
                    validator: validator.uPass, 
                    trigger: 'blur'
                }],
                code: [{
                    validator: validator.uCode, 
                    trigger: 'blur'
                }]
            },
        }
    },
    methods: {
        getVerifyCodeImg(){
            this.loading = true
            // 获取验证码图片
            getRequest('/api/verifyCodeImg?t=' + new Date().getTime()).then(res =>{
                this.verifyImgUrl = res.data.data
            })
            this.loading = false
        }, 
        login(){
            this.loading = true
            // 登录信息校验
            this.$refs.loginForm.validate((vaild) => {
                if (vaild) {
                    postRequest('/api/user/login', this.loginForm)
                    .then(res => {
                        if(res.code === 200){
                            localStorage.setItem(keysProperties.tokenKey, res.data.data);
                            this.$message({
                                message: res.msg,
                                type:'success'
                            })
                            this.$router.replace('/home')
                        }else{
                            this.$message.error(res.msg)
                            this.getVerifyCodeImg()// 刷新验证码
                        }
                    }).catch(err => {
                        this.loading = false
                        this.$message.error(err.message)
                        this.getVerifyCodeImg()// 刷新验证码
                    })
                    
                } else {
                    this.$message.error("请检查输入的内容是否合法")
                    return false
                }
            })
        }
    },
    mounted(){
        this.getVerifyCodeImg()
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
        /* width: 60%; */
    }
    
    .loginVerifyImg{
        width: 100%;
        height: 40px;
    }
    .loginRemember{
        /* margin-bottom: 10px; */
    }
    .loginBtn{
        width: 100%;
    }

</style>
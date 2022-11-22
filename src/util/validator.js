export default {
     uName : (rule, value, callback) => {
        // 验证用户名
        console.log(111);
        let unamePattern = /^[a-zA-Z0-9_-]{4,16}$/ //4到16位（字母，数字，下划线，减号）
        if(!unamePattern.test(value)){
            callback(new Error("用户名由4到16位字母、数字、下划线、减号组成"))
        }else{
            callback();
        }
    },
    uPass: (rule, value, callback) => {
        // 验证密码
        let passPattern = /^[a-zA-Z0-9]{6,20}$/ //6到20位（字母，数字）
        if(!passPattern.test(value)){
            callback(new Error("密码由4到16位字母、数字、下划线、减号组成"))
        }else{
            callback();
        }
    },
    uCode: (rule, value, callback) => {
        // 验证验证码
        let codePattern = /^[a-zA-Z0-9]{4}$/ // 4位数字或字母）
        if(!codePattern.test(value)){
            callback(new Error("验证码由4位数字或字母组成"))
        }else{
            callback();
        }
    }
}


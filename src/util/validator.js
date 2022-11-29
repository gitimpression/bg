
// 注意校验规则与后端要一致

export default {
     uName : (rule, value, callback) => {
        // 验证用户名
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
            callback(new Error("密码由6到20位字母、数字、下划线、减号组成"))
        }else{
            callback();
        }
    },
    uEmail: (rule, value, callback) => {
        // 验证邮箱
        let codePattern = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/
        if(!codePattern.test(value)){
            callback(new Error("邮箱格式不正确"))
        }else{
            callback();
        }
    },
    uCode: (rule, value, callback) => {
        // 验证验证码
        let codePattern = /^[a-zA-Z0-9]{4}$/ // 4位数字或字母）
        if(!codePattern.test(value)){
            callback(new Error("验证码由4位数字或字母混合组成"))
        }else{
            callback();
        }
    },
    uBirthday: (rule, value, callback) => {
        let birthdayPattern = /^([1-9]\d{3}-)(([0]{0,1}[1-9]-)|([1][0-2]-))(([0-3]{0,1}[0-9]))$/
        if(!birthdayPattern.test(value)){
            callback(new Error("请检查生日格式"))
            return
        }

        // 对value进行切割
        let birthday = value.split("-")
        let year = birthday[0]
        let month = birthday[1]
        let day = birthday[2]
        if (year < 1900 || month < 1 || day < 1) {
            callback(new Error("请检查生日格式"))
            return
        }
        
        let nowDate = new Date()
        let nowYear = nowDate.getFullYear()
        let nowMonth = nowDate.getMonth() + 1  // 0~11
        let nowDay = nowDate.getDate()

        if(year < nowYear){  // 今年之前
            callback()
        }else if (year == nowYear) {  // 今年，这个月之前
            if(month < nowMonth){
                callback()
            }else if (month == nowMonth) {  //  今年这个月，今天之前
                if (day <= nowDay) {
                    callback()
                }else{
                    callback(new Error("请检查生日格式"))
                }
            }else{
                callback(new Error("请检查生日格式"))
            }
        }else{
            callback(new Error("请检查生日格式"))
        }
        
    }
}


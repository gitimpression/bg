import axios from "axios";
import { Message } from "element-ui";
import router from "../router"

// 请求拦截器
axios.interceptors.request.use(config => {
    if(localStorage.getItem("uInfo")){
        config.headers['uInfo'] = localStorage.getItem("uInfo")
    }
    return config
}, error => {
    console.log(error);
})

// 响应拦截器 返回处理正确的结果，错误给出提示不返回data
axios.interceptors.response.use(response => {// axios 的 response
    if(response.status !== 200 && response.data.code !== 200){
        Message.error(res.msg)
        return
    }
    return response.data;// ComRet 对象
}, error => {
    Message.error('请求失败，code：' + error.code + ",message：" + error.message)
    router.replace('/')
    return
})


let baseUrl = ''

export const request = (method, url, params) => {
    return axios({
        method,
        url: baseUrl + url,
        data: params
    })
}
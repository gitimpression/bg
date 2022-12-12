import keysProperties from "@/config/keysProperties";
import axios from "axios";
import { Message } from "element-ui";

// 跨域 session丢失
axios.defaults.withCredentials = true
// 请求拦截器
axios.interceptors.request.use(config => {
    if(localStorage.getItem(keysProperties.tokenKey)){
        config.headers = {
            "u_t" : localStorage.getItem(keysProperties.tokenKey),
            "Content-type" : "application/json;charset=UTF-8"
        }
    }else{
        config.headers = {
            "Content-type" : "application/json;charset=UTF-8"
        }
    }
    return config
}, error => {
    Message.error(error)
})

// 响应拦截器 返回处理正确的结果，错误给出提示不返回data
axios.interceptors.response.use(response => {// axios 的 response
    if(response.status !== 200){
        Message.error("error：" + response.message) // 区别普通消息
        return
    }
    if(response.data.code !== 200){ // ComRet
        Message.error("error：" + response.data.msg) // 区别普通消息
        return
    }
    return response.data;// ComRet 对象
}, error => {
    Message.error('请求失败，code：' + error.code + ",message：" + error.message)
    return
})


// let baseUrl = 'http://bg.ctp.zone:8081'
let baseUrl = ''

export const getRequest = (url, params) => {
    return axios({
        method: "GET",
        url: baseUrl + url,
        data: params
    })
}

export const postRequest = (url, params) => {
    return axios({
        method: "POST",
        url: baseUrl + url,
        data: params,
    })
}

export const putRequest = (url, params) => {
    return axios({
        method: "PUT",
        url: baseUrl + url,
        data: params
    })
}

export const delRequest = (url, params) => {
    return axios({
        method: "DELETE",
        url: baseUrl + url,
        data: params
    })
}

export const getPm = (name) => {
    return axios({
        method: "GET",
        url: baseUrl + "/api/user/pm?pmName=" + name,
    })
}
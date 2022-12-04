// 统一key值
let userProperties = {
    tokenKey: "u_t",  // Basic 的 uploadHeaders 上传携带请求头key 请保持一致；需要与api.js的请求头参数一致
    userInfoKey: "u_i",
    userVisitsNoticeList: "u_n_l",  // 用户访问过的公告，用于是否增加次数
    noticeDraftKey: "n_d",  // 公告草稿
    noticePmKey: "notice", // 公告操作权限
    netPmKey: "net", // 手册操作权限
    userHeaderImgPrefix : "http://localhost:8081/upload/img/"
}

export default userProperties
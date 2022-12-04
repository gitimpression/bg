import keysProperties from '@/config/keysProperties'
export default {
    storage: {
        clearAllUserInfo() {
            // 清空token
            if (localStorage.getItem(keysProperties.tokenKey)) {
                localStorage.removeItem(keysProperties.tokenKey)
            }
            // 清空用户信息
            if (localStorage.getItem(keysProperties.userInfoKey)) {
                localStorage.removeItem(keysProperties.userInfoKey)
            }
            // 清空用户公告草稿
            if (localStorage.getItem(keysProperties.noticeDraftKey)) {
                localStorage.removeItem(keysProperties.noticeDraftKey)
            }
            // 清空用户公告浏览记录
            if (localStorage.getItem(keysProperties.userVisitsNoticeList)) {
                localStorage.removeItem(keysProperties.userVisitsNoticeList)
            }  
            // 清空用户公告权限标记
            if (localStorage.getItem(keysProperties.noticePmKey)) {
                localStorage.removeItem(keysProperties.noticePmKey)
            }                   
        }
    }
}
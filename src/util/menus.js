import { getRequest } from "./api";

export const initMenu = (router,store) => {
    if(store.state.routes.length > 0){ // 用户存在路由数据（后端传过来）
        return
    }
    getRequest("/api/sys/menu", {}).then( res => {
        let routes = res.data.routes // menu路由数据
        if(routes){// 有数据 res.data 拿到Map
            console.log(routes);
        }
    })
}
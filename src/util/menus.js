import { getRequest } from "./api";
import keysProperties from "@/config/keysProperties"

const defaultChildrenLength = 0  // 当home的子路由大于这个值，不请求菜单数据

export const initMenu = (router,store) => {
    if(store.state.routes.length > 1){  // 用户存在路由数据（后端传过来）
        return
    }
    if (localStorage.getItem(keysProperties.tokenKey)) {
        getRequest("/api/sys/menu").then( res => {
            let routes = res.data.menus     // menu路由数据
            if(routes){ 
                let fmtRoutes = formatRoutes(routes)     // 格式化Routes
                let storeRoutes = fmtRoutes.filter(r => {
                    // 过滤没有内容的菜单
                    return r.children.length > 0 || r.path.indexOf('/') != -1
                
                }) 
                store.commit('initRoutes', storeRoutes)  // 存储到store
                findPathRoutes(fmtRoutes)       // 过滤Routes
                hasPathRoutes.forEach(h => {
                    router.addRoute("Home", h)  // 作为子路由存到路由/home下
                })
                hasPathRoutes = []              // 重置
            }
        }) 
    }
}

// 格式化路由菜单数据
export const formatRoutes = (routes) => { 
    let fmtRoutes = []                  // 定义一个空数组存放格式化好的数据
    routes.forEach(route => {
        let {                           // 解构每个元素，name = route.name, url = route.url...
            id,
            name,
            url,
            path,
            component,
            iconClz,
            children
        } = route
        if (children && children instanceof Array && children.length > 0) {// 存在子节点
            children = formatRoutes(children)   //  当前节点的子节点是格式化后的节点数组
        }
        let fmtRouter = {
            id,
            name: component,
            title: name,
            path,
            iconClz,
            children,
            component: () => import('@/components/main/' + url + component + '.vue')
        }
        fmtRoutes.push(fmtRouter)               // 节点存入数组
    });
    return fmtRoutes
}

let hasPathRoutes = [] // 有路径的路由
// 筛选出有路径的路由
export const findPathRoutes = (routes) => {
    routes.forEach(route => {
        if (route.children || route.children instanceof Array || route.children.length > defaultChildrenLength) { // 存在子元素
            findPathRoutes(route.children)
        }
        if(route.path && route.path.indexOf('/') != -1){
            hasPathRoutes.push(route)
        }
    })
}


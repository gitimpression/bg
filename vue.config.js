let proxyTable = {
    '/api': {
        ws: false, // websocket
        target: 'http://localhost:8081', // 目标地址
        changeOrigin: true, // 更改请求源为target
        pathRewrite: { // 不重写请求地址
            '^/api': ''
        }
    }
}



module.exports = {
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: proxyTable
    }
}
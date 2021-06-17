module.exports = {
  /* 部署生产环境和开发环境下的URL：可对当前环境进行区分，baseUrl 从 Vue CLI 3.3 起已弃用，要使用publicPath */
  /* baseUrl: process.env.NODE_ENV === 'production' ? './' : '/' */
  // publicPath: process.env.NODE_ENV === 'production' ? '/public/' : './',
  publicPath: '/',
  /* 输出文件目录：在npm run build时，生成文件的目录名称 */
  outputDir: 'dist',
  /* 放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录 */
  assetsDir: 'assets',
  /* 是否在构建生产包时生成 sourceMap 文件，false将提高构建速度 */
  productionSourceMap: false,
  /* 默认情况下，生成的静态资源在它们的文件名中包含了 hash 以便更好的控制缓存，你可以通过将这个选项设为 false 来关闭文件名哈希。(false的时候就是让原来的文件名不改变) */
  filenameHashing: false,
  /* 代码保存时进行eslint检测 */
  lintOnSave: true,
  /* webpack-dev-server 相关配置 */
  runtimeCompiler: true,
  devServer: {
    /* 自动打开浏览器 */
    open: true,
    host: 'localhost',
    port: 8089,
    https: false,
    hotOnly: false,
    /* 使用代理 */
    proxy: {
      //设置代理，必须填
      '/api': {
        target: 'http://localhost:8443', //代理的目标地址
        changeOrigin: true, //是否设置同源,也就是允许跨域，输入是的
        pathRewrite: {
          //路径重写
          '/api': '' //选择忽略拦截器里面的单词
        }
      }
    }
  }
}

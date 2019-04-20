const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path')
const { override, fixBabelImports, addWebpackAlias, addTslintLoader, addDecoratorsLegacy } = require('customize-cra');

function resolve (dir) {
  return path.join(__dirname, dir)
}

module.exports = function (config, env) {
  const isDebug = env === 'development'
  let htmlPlugin = config.plugins.find(d => d instanceof HtmlWebpackPlugin)
  let devHtmlInject = {
    css: [
      '/static/css/common.css',
      '/static/css/atomic.min.css'
    ]
  }
  let prodHtmlInject = {
    css: [
      './static/css/common.css',
      './static/css/atomic.min.css'
    ]
  }
  htmlPlugin.options.injectOption = isDebug ? devHtmlInject : prodHtmlInject
  override([
    fixBabelImports('import', {
      libraryName: 'antd',
      libraryDirectory: 'es',
      style: 'css'
    }),
    // addWebpackAlias({
    //   '@': resolve('src')
    // }),
    // tslint
    addTslintLoader(),
    // 装饰器
    addDecoratorsLegacy()
  ])(config, env)
  return config
}

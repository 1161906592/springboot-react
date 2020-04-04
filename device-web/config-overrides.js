const path = require("path");
const { override, fixBabelImports, addWebpackAlias } = require("customize-cra");

function resolve (dir) {
  return path.join(__dirname, dir);
}

module.exports = function (config, env) {
  override([
    fixBabelImports("import", {
      libraryName: "antd",
      libraryDirectory: "es",
      style: "css"
    }),
    addWebpackAlias({
      "@": resolve("src")
    })
  ])(config, env);
  return config;
};

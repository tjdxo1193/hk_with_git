const path = require('path');

const { VueLoaderPlugin } = require('vue-loader');

const pathMap = require('./pathMap');

module.exports = {
  entry: {
    app: path.resolve(pathMap.SRC, 'index.js'),
  },
  performance: {
    maxAssetSize: 1000000,
  },
  output: {
    path: pathMap.BUILD_PATH,
    publicPath: pathMap.PUBLIC_PATH,
  },
  resolve: {
    extensions: ['.vue', '.js', '.json'],
    modules: [pathMap.SRC, 'node_modules'],
    alias: {
      '@': pathMap.SRC,
    },
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        use: ['vue-loader'],
      },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: ['babel-loader'],
      },
      {
        test: /\.(png|svg|jpg|jpeg|gif)$/,
        type: 'asset',
      },
    ],
  },
  plugins: [new VueLoaderPlugin()],
};

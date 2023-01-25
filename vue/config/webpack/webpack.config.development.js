const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const { merge } = require('webpack-merge');

const env = require(`../../env/${process.env.TARGET_ENV}`);
const pathMap = require('./pathMap');
const baseConfig = require('./webpack.config.base.js');

module.exports = merge(baseConfig, {
  mode: 'development',
  output: {
    filename: 'dev.[contenthash].js',
    assetModuleFilename: 'static/[hash][ext][query]',
  },
  devServer: {
    historyApiFallback: true,
    hot: false,
    compress: true,
    host: 'localhost',
    port: 3000,
    headers: {
      'Cache-Control': 'no-store',
    },
    static: [
      {
        directory: pathMap.SRC,
        publicPath: '/',
      },
      {
        directory: path.resolve(__dirname, pathMap.LIB),
        publicPath: '/lib',
      },
    ],
    proxy: {
      '/api': {
        target: env.API_SERVER.URL,
        secure: false,
      },
    },
  },
  module: {
    rules: [
      {
        test: /\.(css|s[ac]ss)$/,
        use: [
          'style-loader',
          'css-loader',
          {
            loader: 'sass-loader',
            options: {
              additionalData: `
                @import '@/assets/scss/variable.scss';
              `,
            },
          },
        ],
      },
    ],
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new HtmlWebpackPlugin({
      title: '(Dev) HK LIMS',
      favicon: pathMap.FAVICON,
      template: path.resolve(pathMap.PUBLIC, 'index.html'),
      filename: path.resolve(pathMap.BUILD_PATH, 'index.html'),
      files: {
        auigrid: `${pathMap.LIB_PATH}/${env.CURRENT_ENV}${env.AUI_GRID.PATH.JS}`,
        auigridLicense: `${pathMap.LIB_PATH}/${env.CURRENT_ENV}${env.AUI_GRID.PATH.LICENSE}`,
        auigridStyle: `${pathMap.LIB_PATH}/${env.CURRENT_ENV}${env.AUI_GRID.PATH.STYLE}`,
        auigridFileSaver: `${pathMap.LIB_PATH}/${env.CURRENT_ENV}${env.AUI_GRID.PATH.FILE_SAVER}`,
      },
    }),
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(env),
    }),
  ],
});

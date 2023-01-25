const path = require('path');

const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const CopyPlugin = require('copy-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const TerserPlugin = require('terser-webpack-plugin');
const webpack = require('webpack');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
const { merge } = require('webpack-merge');

const env = require(`../../env/${process.env.TARGET_ENV}`);
const pathMap = require('./pathMap');
const baseConfig = require('./webpack.config.base.js');

module.exports = merge(baseConfig, {
  mode: 'production',
  output: {
    filename: '[name].[contenthash].js',
    assetModuleFilename: 'static/[hash][ext][query]',
  },
  optimization: {
    minimize: true,
    minimizer: [
      new TerserPlugin({
        parallel: true,
        exclude: /node_modules/,
        extractComments: true,
        terserOptions: {
          compress: true,
        },
      }),
    ],
  },
  module: {
    rules: [
      {
        test: /\.(css|s[ac]ss)$/,
        use: [
          MiniCssExtractPlugin.loader,
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
    new CleanWebpackPlugin(),
    new HtmlWebpackPlugin({
      title: 'HK LIMS',
      favicon: pathMap.FAVICON,
      template: path.resolve(pathMap.PUBLIC, 'index.html'),
      filename: path.resolve(pathMap.BUILD_PATH, 'index.html'),
      minify: {
        removeComments: true,
        collapseWhitespace: true,
      },
      files: {
        auigrid: `${pathMap.LIB_PATH}${env.AUI_GRID.PATH.JS}`,
        auigridLicense: `${pathMap.LIB_PATH}${env.AUI_GRID.PATH.LICENSE}`,
        auigridStyle: `${pathMap.LIB_PATH}${env.AUI_GRID.PATH.STYLE}`,
        auigridFileSaver: `${pathMap.LIB_PATH}${env.AUI_GRID.PATH.FILE_SAVER}`,
      },
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve(__dirname, '../../lib/common'),
          to: path.resolve(pathMap.BUILD_PATH, 'lib/common'),
          noErrorOnMissing: true,
        },
        {
          from: path.resolve(__dirname, `../../lib/${env.CURRENT_ENV}`),
          to: path.resolve(pathMap.BUILD_PATH, 'lib'),
          noErrorOnMissing: true,
        },
      ],
    }),
    new MiniCssExtractPlugin(),
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(env),
    }),
    new BundleAnalyzerPlugin({
      analyzerMode: 'static',
      analyzerHost: '127.0.0.1',
      analyzerPort: 8888,
      reportTitle: '번들 파일 구성 정보',
      reportFilename: 'bundle-analyzer-report.html',
      generateStatsFile: false,
      openAnalyzer: true,
    }),
  ],
});

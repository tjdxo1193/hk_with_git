const path = require('path');

module.exports = {
  PUBLIC_PATH: '/',
  LIB_PATH: '/lib',
  FAVICON: 'public/favicon.ico',
  COMMON_LIB_PATH: '/lib/common',
  LIB: path.resolve(__dirname, '../../lib'),
  BUILD_PATH: path.resolve(__dirname, '../../../src/main/resources/static'),
  PUBLIC: path.resolve(__dirname, '../../public'),
  SRC: path.resolve(__dirname, '../../src'),
  ENV: path.resolve(__dirname, '../../env'),
};

const SCHEME = 'http';
const HOST = 'localhost';
const PORT = 8080;

const API_SERVER_URL = `${SCHEME}://${HOST}:${PORT}`;

module.exports = {
  CURRENT_ENV: 'local',
  API_SERVER: {
    URI_PREFIX: '/api',
    SCHEME,
    HOST,
    PORT,
    URL: API_SERVER_URL,
  },
  AUI_GRID: {
    PATH: {
      JS: '/auigrid/AUIGrid.js',
      STYLE: '/auigrid/AUIGrid_modern_style.css',
      LICENSE: '/auigrid/AUIGridLicense.js',
      FILE_SAVER: '/auigrid/FileSaver.min.js',
    },
  },
  REPORT_DESIGNER: {
    SERVICE_NAME: 'KOLMAR_LIMS',
    REPOSITORY_PATH: `http://203.229.218.192:23002/ReportingServer/mrd/KOLMAR_LIMS`,
    USER: 'admin',
    PASSWORD: 'iit3274!@',
    REPORTING_SERVER: {
      URL: 'http://203.229.218.192:23002/ReportingServer/service',
    },
    DATA_SERVER: {
      URL: 'http://203.229.218.192:23002/DataServer/rdagent.jsp',
    },
  },
};

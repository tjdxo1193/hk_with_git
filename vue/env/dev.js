const SCHEME = 'http';
const HOST = '10.225.35.54';
const PORT = null;

const API_SERVER_URL = `${SCHEME}://${HOST}`;

module.exports = {
  CURRENT_ENV: 'dev',
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
    SERVICE_NAME: 'LIMS',
    REPOSITORY_PATH: `${API_SERVER_URL}/mrd`,
    USER: 'admin',
    PASSWORD: 'iit3274!@',
    REPORTING_SERVER: {
      URL: 'https://qmrddev.kolmar.co.kr:463/ReportingServer/service',
    },
    DATA_SERVER: {
      URL: 'https://qmrddev.kolmar.co.kr:463/DataServer/rdagent.jsp',
    },
  },
};

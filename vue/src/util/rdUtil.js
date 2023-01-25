import { $m2soft } from '@/plugin';

const { REPORT_DESIGNER } = process.env;
const { REPORTING_SERVER, DATA_SERVER, REPOSITORY_PATH, SERVICE_NAME, USER, PASSWORD } =
  REPORT_DESIGNER;

const getViewer = () => new $m2soft.crownix.Viewer(REPORTING_SERVER.URL);
const makeParam = (rdParam) => `
  /rcontype [Data Server] 
  /rf [${DATA_SERVER.URL}] 
  /rsn [${SERVICE_NAME}] 
  /rui [${USER}] 
  /rpw [${PASSWORD}] 
  ${rdParam}
  `;

export default {
  openReport(filePath, fileParam, options) {
    const viewer = getViewer();
    viewer.openFile(`["${REPOSITORY_PATH}${filePath}"]`, `["${makeParam(fileParam)}"]`, options);
    return viewer;
  },
  openReports(filePaths, fileParams, options) {
    const mrdList = filePaths.map((filePath, index) => ({
      mrdPath: `${REPOSITORY_PATH}${filePath}`,
      mrdParam: fileParams[index] ? makeParam(fileParams[index]) : '',
    }));

    const viewer = getViewer();
    viewer.openFile(mrdList, options);
    return viewer;
  },
  print(viewer) {
    viewer.print({ isServerSide: true });
  },
  printReport(...args) {
    const viewer = this.openReport(...args);
    this.print(viewer);
  },
  printReports(...args) {
    const viewer = this.openReports(...args);
    this.print(viewer);
  },
};

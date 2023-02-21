import auigrid, { $auigrid } from './auigrid';
import axios, { $axios } from './axios';
import { alert, progress, state, eSign, consts, util } from './custom';
import reportDesigner, { $m2soft } from './reportDesigner';

const plugin = {
  install(app) {
    app.use(axios);
    app.use(auigrid);
    app.use(reportDesigner);
    app.use(alert);
    app.use(progress);
    app.use(state);
    app.use(eSign);
    app.use(consts);
    app.use(util);
  },
};

export { $auigrid, $axios, $m2soft };

export default plugin;

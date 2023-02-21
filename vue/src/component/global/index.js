import '@vuepic/vue-datepicker/dist/main.css';

import display from './display';
import entry from './entry';
import feedback from './feedback';
import general from './general';
import layout from './layout';

const components = {
  ...display,
  ...entry,
  ...general,
  ...layout,
  ...feedback,
};

export default {
  install(app) {
    Object.entries(components).forEach(([name, component]) => {
      app.component(name, component);
    });
  },
};

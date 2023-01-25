import { mutationType } from '@/const';
import store from '@/store';

const showProgress = () => {
  store.commit(mutationType.START_LOADING);
};

const hideProgress = () => {
  store.commit(mutationType.START_LOADING);
};

export default {
  install(app) {
    app.config.globalProperties.$showProgress = showProgress;
    app.config.globalProperties.$hideProgress = hideProgress;
  },
};

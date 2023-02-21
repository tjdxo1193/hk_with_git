import { ObjectUtil } from '@/util';

export default {
  install(app) {
    app.config.globalProperties.$copy = (v) => ObjectUtil.copy(v, true);
    app.config.globalProperties.$shallowCopy = (v) => ObjectUtil.copy(v, false);
  },
};

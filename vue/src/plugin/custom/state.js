import { ObjectUtil } from '@/util';

export default {
  install(app) {
    app.config.globalProperties.$setState = function (...args) {
      ObjectUtil.setObject(this.$data, ...args);
    };
  },
};

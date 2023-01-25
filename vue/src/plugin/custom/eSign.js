import { mutationType } from '@/const';
import store from '@/store';

export default {
  //* handling @/component/feedback/ESign.vue
  install(app) {
    const showEsignMethodGenerator = (type) => (asyncFn, option) => {
      return new window.Promise((resolve, reject) => {
        store.commit(mutationType.SHOW_E_SIGN, { type, ...option, resolve, reject, asyncFn });
      });
    };

    app.config.globalProperties.$eSign = showEsignMethodGenerator('info');
    app.config.globalProperties.$eSignWithReason = showEsignMethodGenerator('reason');
  },
};

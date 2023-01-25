import { getterType, mutationType } from '@/const';
import store from '@/store';

export default function auditInterceptor(axios) {
  axios.interceptors.request.use(
    function (config) {
      const isReady = store.getters[getterType.IS_READY_SET_ESIGN_PARAM];

      if (isReady) {
        const param = store.getters[getterType.GET_E_SIGN_PARAM];
        config.headers['e-sign'] = window.encodeURIComponent(JSON.stringify(param));
        store.commit(mutationType.END_SET_ESIGN_PARAM);
      }

      return config;
    },
    function (error) {
      return window.Promise.reject(error);
    },
  );
}

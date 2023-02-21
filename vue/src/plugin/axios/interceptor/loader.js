import { mutationType } from '@/const';
import store from '@/store';

export default function globalLoaderInterceptor(axios) {
  axios.interceptors.request.use(
    function (config) {
      if (config._progress) {
        store.commit(mutationType.START_LOADING);
      }
      return config;
    },
    function (error) {
      store.commit(mutationType.STOP_LOADING);
      return window.Promise.reject(error);
    },
  );

  axios.interceptors.response.use(
    function (response) {
      store.commit(mutationType.STOP_LOADING);
      return response;
    },
    function (error) {
      store.commit(mutationType.STOP_LOADING);
      return window.Promise.reject(error);
    },
  );
}

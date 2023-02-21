import { http, message, mutationType } from '@/const';
import { showAlertInfo } from '@/plugin/custom/alert';
import store from '@/store';

const isTimeout = (status) => status == http.code.TIMEOUT;

export default function authInterceptor(axios) {
  axios.interceptors.request.use(
    function (config) {
      return config;
    },
    function (error) {
      return window.Promise.reject(error);
    },
  );

  axios.interceptors.response.use(
    function (response) {
      return response;
    },
    function (error) {
      if (isTimeout(error.response?.status)) {
        showAlertInfo(message.error.notConnectedServer).then(() => {
          store.commit(mutationType.DE_AUTHENTICATE);
          store.commit(mutationType.TO_WELCOME);
        });
      }
      return window.Promise.reject(error);
    },
  );
}

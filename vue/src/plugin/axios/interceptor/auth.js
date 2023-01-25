import api from '@/api';
import { http, errorType, getterType, actionType, mutationType } from '@/const';
import store from '@/store';

const setTokenToHeader = (request) => {
  request.headers.Authorization = `Bearer ${store.getters[getterType.AUTHENTICATION_TOKEN]}`;
};

const isNoReLoginRequest = (url) => url !== api.auth.url.RE_LOGIN;

const isUnauthentication = (statusCode) => {
  return statusCode === http.code.UNAUTHENTICATION || statusCode === http.code.FORBIDDEN;
};

const isAlreadyProceedingReLogin = (type) => type === errorType.auth.PROCEEDING_RE_LOGIN;

const toWelcome = () => store.commit(mutationType.TO_WELCOME);

const relogin = async () => {
  try {
    await store.dispatch(actionType.RE_LOGIN);
  } catch (e) {
    if (isAlreadyProceedingReLogin(e?.type)) {
      return;
    }
    store.commit(mutationType.DE_AUTHENTICATE);
    toWelcome();
  }
};

export default function authInterceptor(axios) {
  axios.interceptors.request.use(function (config) {
    setTokenToHeader(config);
    return config;
  });

  axios.interceptors.response.use(null, async (error) => {
    const { response = {}, config = {} } = error;
    const { status } = response;

    /**
     ** 에러 응답 상태 코드가 401, 403인 경우, 로그인 상태라면 토큰 재발급을 시도합니다.
     *
     *! 토큰 재발급 요청은 401 상태 코드를 응답하는 케이스가 있습니다.
     *! 즉, 무한 루프의 가능성이 존재합니다.
     *! 따라서 재발급 요청에서 응답한 401코드는 토큰 재발급 로직을 실행하지 않도록 처리합니다.
     */
    if (isNoReLoginRequest(config.url) && isUnauthentication(status)) {
      const isLoggedIn = store.getters[getterType.IS_LOGGED_IN];

      if (!isLoggedIn) {
        toWelcome();
        return window.Promise.reject(error);
      }

      await relogin();

      const originRequest = error.config;
      return await axios.request(originRequest);
    }

    return window.Promise.reject(error);
  });
}

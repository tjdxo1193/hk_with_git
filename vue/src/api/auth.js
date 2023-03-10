import { $axios } from '@/plugin';

const url = {
  LOGIN: '/authentication/token',
  RE_LOGIN: '/authentication/token/reissue',
};

export default {
  login(param) {
    return $axios.post(url.LOGIN, param);
  },
  logout(option) {
    return $axios.post('/authentication/token/revocation', null, option);
  },
  verify() {
    return $axios.get('/authentication/token/verification');
  },
  reLogin(param) {
    return $axios.post(url.RE_LOGIN, param);
  },
  isLoginUrl(s) {
    return s === url.LOGIN || s === url.RE_LOGIN;
  },
};

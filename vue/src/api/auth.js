import { $axios } from '@/plugin';

const url = {
  RE_LOGIN: '/authentication/token/reissue',
};

export default {
  url,
  login(param) {
    return $axios.post('/authentication/token', param);
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
};

import axios from 'axios';

import { FormUtil } from '@/util';

import { useInterceptor } from './interceptor';

axios.defaults.baseURL = `${window.location.origin}${process.env.API_SERVER.URI_PREFIX}`;
axios.defaults.timeout = 10000;
axios.defaults.headers.common['Content-Type'] = 'application/json';

useInterceptor(axios);

const convertToFormData = (param) => FormUtil.toFormData(param);

const convertToQueryString = (param) => {
  return param
    ? encodeURI(
        `?${Object.entries(param)
          .map(([key, value]) => `${key}=${value}`)
          .join('&')}`,
      )
    : '';
};

const formDataHeader = {
  headers: {
    'Content-Type': 'multipart/form-data',
  },
};

const axiosWrapper = {
  get: (url, param, option) => axios.get(`${url}${convertToQueryString(param)}`, option),
  post: (...args) => axios.post(...args),
  put: (...args) => axios.put(...args),
  all: (iterable) => window.Promise.allSettled(iterable),
  spread: (callback) => axios.spread(callback),
  postByForm(url, param, option) {
    return this.post(url, convertToFormData(param), { ...option, ...formDataHeader });
  },
  putByForm(url, param, option) {
    return this.put(url, convertToFormData(param), { ...option, ...formDataHeader });
  },
  CancelToken: axios.CancelToken,
};

export { axiosWrapper as $axios };

export default {
  install(app) {
    app.config.globalProperties.$axios = axiosWrapper;
  },
};

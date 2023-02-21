import router from '@/router';

const isExistsMenuCode = (meta = {}) => !!meta.menu?.code;

const getMenuCode = (meta = {}) => meta.menu?.code;

export default function auditInterceptor(axios) {
  axios.interceptors.request.use(
    function (config) {
      const { meta } = router.currentRoute.value;

      if (isExistsMenuCode(meta)) {
        /**
         * crk-fa: current route key for (audit / authorization)
         */
        config.headers['crk-fa'] = window.btoa(getMenuCode(meta));
      }

      return config;
    },
    function (error) {
      return window.Promise.reject(error);
    },
  );
}

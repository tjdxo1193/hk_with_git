import auditInterceptor from './audit';
import authInterceptor from './auth';
import eSignInterceptor from './eSign';
import generalInterceptor from './general';
import loaderInterfaceptor from './loader';

function useInterceptor(axios) {
  generalInterceptor(axios);
  loaderInterfaceptor(axios);
  authInterceptor(axios);
  auditInterceptor(axios);
  eSignInterceptor(axios);
}

export { useInterceptor };

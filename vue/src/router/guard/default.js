import { mutationType } from '@/const';
import store from '@/store';

function defaultGuard(router) {
  router.beforeEach((to, from, next) => {
    store.commit(mutationType.INIT_GLOBAL_STATUS);

    next();
  });
}

export default defaultGuard;

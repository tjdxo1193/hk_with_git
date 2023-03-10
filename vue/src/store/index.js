import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';

import { getterType, actionType, mutationType } from '@/const';

import modules from './module';

const store = createStore({
  modules,
  plugins: [
    createPersistedState({
      storage: window.sessionStorage,
    }),
  ],
});

/**
 * javascript의 특성 상 최초 화면에 진입할 때와 새로고침할 때의 동작이 동일합니다.
 * 따라서, 전역으로 관리되는 store의 값들에 대한 처리가 필요합니다.
 * 또한, 토큰 유효성에 따른 처리도 포함합니다.
 */
async function initializeStore() {
  try {
    store.commit(mutationType.INIT_GLOBAL_STATUS);
    const isLoggedIn = store.getters[getterType.IS_LOGGED_IN];
    const isVerified = await store.dispatch(actionType.VERIFY_TOKEN);
    const isSSOLogin = window.location.pathname.startsWith('/sso-login');

    console.warn(`isLoggedIn: ${isLoggedIn}, isVerified: ${isVerified}, isSSOLogin: ${isSSOLogin}`);

    if (isSSOLogin) {
      console.warn('Current path is sso path.');
      return;
    }

    if (isLoggedIn && !isVerified) {
      store.dispatch(actionType.RE_LOGIN).catch(() => {
        store.dispatch(actionType.LOGOUT);
        store.commit(mutationType.TO_WELCOME);
      });
    } else if (!isLoggedIn && !isVerified) {
      store.commit(mutationType.TO_WELCOME);
    }
  } catch (e) {
    console.error('refresh error', e);
    store.dispatch(actionType.LOGOUT);
    store.commit(mutationType.TO_WELCOME);
  }
}

export { initializeStore };

export default store;

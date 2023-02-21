import api from '@/api';
import { errorType, actionType, getterType, mutationType } from '@/const';
import router from '@/router';

const state = () => ({
  accessToken: null,
  refreshToken: null,
  claims: null,
  isReLoginProceeding: false,
  loginFailProcessing: false,
});

const getters = {
  [getterType.AUTHENTICATION_TOKEN](state) {
    return state.accessToken;
  },
  [getterType.MY_CLAIMS](state) {
    return state.claims;
  },
  [getterType.IS_LOGGED_IN](state) {
    return state.accessToken != null;
  },
  [getterType.IS_LOGIN_FAIL_PROCESSING](state) {
    return state.loginFailProcessing;
  },
};

const mutations = {
  [mutationType.AUTHENTICATE](state, token) {
    const { accessToken, refreshToken, claims } = token;
    state.accessToken = accessToken;
    state.refreshToken = refreshToken;
    state.claims = claims;
  },
  [mutationType.DE_AUTHENTICATE](state) {
    state.accessToken = null;
    state.refreshToken = null;
    state.claims = null;
  },
  [mutationType.START_RE_AUTHENTICATE](state) {
    state.isReLoginProceeding = true;
  },
  [mutationType.END_RE_AUTHENNTICATE](state) {
    state.isReLoginProceeding = false;
  },
  [mutationType.START_LOGIN_FAIL_PROCESS](state) {
    state.loginFailProcessing = true;
  },
  [mutationType.END_LOGIN_FAIL_PROCESS](state) {
    state.loginFailProcessing = false;
  },
};

const actions = {
  async [actionType.LOGIN]({ commit }, param) {
    const { data } = await api.auth.login(param);
    commit(mutationType.AUTHENTICATE, data);
    router.push('/');
  },
  async [actionType.LOGOUT]({ commit }) {
    await api.auth.logout();
    commit(mutationType.DE_AUTHENTICATE);
    commit(mutationType.END_RE_AUTHENNTICATE);
  },
  async [actionType.VERIFY_TOKEN]() {
    try {
      const { data } = await api.auth.verify();
      return data.result ?? false;
    } catch (error) {
      return false;
    }
  },
  async [actionType.RE_LOGIN]({ state, commit }) {
    try {
      const { refreshToken, isReLoginProceeding } = state;

      if (isReLoginProceeding) {
        return window.Promise.reject({
          type: errorType.auth.PROCEEDING_RE_LOGIN,
        });
      }
      commit(mutationType.START_RE_AUTHENTICATE);

      const { data } = await api.auth.reLogin({ refreshToken });
      commit(mutationType.AUTHENTICATE, data);
      commit(mutationType.END_RE_AUTHENNTICATE);
    } catch (e) {
      commit(mutationType.END_RE_AUTHENNTICATE);
      throw new Error(e);
    }
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};

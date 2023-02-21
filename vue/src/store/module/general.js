import { getterType, mutationType } from '@/const';
import router from '@/router';

const state = () => ({
  isLoading: false,
  alert: {
    type: null,
    message: null,
    show: false,
    resolve: null,
  },
});

const getters = {
  [getterType.IS_LOADING]({ isLoading }) {
    return isLoading;
  },
  [getterType.ALERT]({ alert }) {
    return alert;
  },
  [getterType.IS_SHOW_ALERT]({ alert }) {
    return alert.show;
  },
};

const mutations = {
  [mutationType.START_LOADING](state) {
    state.isLoading = true;
  },
  [mutationType.STOP_LOADING](state) {
    state.isLoading = false;
  },
  [mutationType.SHOW_ALERT](state, param) {
    state.alert = { ...param, show: true };
  },
  [mutationType.HIDE_ALERT](state) {
    state.alert = {
      type: null,
      message: null,
      show: false,
      resolve: null,
    };
  },
  [mutationType.INIT_GLOBAL_STATUS]() {
    this.commit(mutationType.STOP_LOADING);
    this.commit(mutationType.HIDE_ALERT);
    this.commit(mutationType.HIDE_E_SIGN);
    this.commit(mutationType.END_RE_AUTHENNTICATE);
  },
  [mutationType.TO_WELCOME]() {
    this.commit(mutationType.INIT_GLOBAL_STATUS);
    const isLoginFailProcessing = this.getters[getterType.IS_LOGIN_FAIL_PROCESSING];
    if (isLoginFailProcessing) {
      return;
    }
    router.push('/welcome');
  },
  [mutationType.TO_LOGIN]() {
    this.commit(mutationType.INIT_GLOBAL_STATUS);
    router.push('/login');
  },
};

const actions = {};

export default {
  state,
  getters,
  actions,
  mutations,
};

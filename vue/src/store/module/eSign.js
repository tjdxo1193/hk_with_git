import { getterType, mutationType } from '@/const';

const state = () => ({
  eSign: {
    title: null,
    type: null,
    show: false,
    resolve: null,
    reject: null,
    asyncFn: null,
    request: {
      isReady: false,
      param: null,
    },
  },
});

const getters = {
  [getterType.E_SIGN]({ eSign }) {
    return eSign;
  },
  [getterType.IS_READY_SET_ESIGN_PARAM]({ eSign }) {
    return eSign.request.isReady;
  },
  [getterType.GET_E_SIGN_PARAM]({ eSign }) {
    return eSign.request.param;
  },
};

const mutations = {
  [mutationType.SHOW_E_SIGN](state, param) {
    state.eSign = { ...state.eSign, ...param, show: true };
  },
  [mutationType.HIDE_E_SIGN](state) {
    state.eSign = {
      title: null,
      type: null,
      show: false,
      resolve: null,
      reject: null,
      asyncFn: null,
      request: {
        isReady: false,
        param: null,
      },
    };
  },
  [mutationType.READY_SET_ESIGN_PARAM](state, param) {
    state.eSign.request.isReady = true;
    state.eSign.request.param = param;
  },
  [mutationType.END_SET_ESIGN_PARAM](state) {
    state.eSign.request.isReady = false;
    state.eSign.request.param = null;
  },
};

export default {
  state,
  getters,
  mutations,
};

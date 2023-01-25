import api from '@/api';
import { actionType, getterType, mutationType } from '@/const';

const state = () => ({
  menuInfos: [],
});

const getters = {
  [getterType.GET_MENU_INFOS](state) {
    return state.menuInfos;
  },
  [getterType.GET_MENU_INFOS_AS_OBJECT](state) {
    return state.menuInfos.reduce((acc, menu) => {
      acc[menu.menuCd] = null;
      return acc;
    }, {});
  },
};

const mutations = {
  [mutationType.SET_MENU_INFO](state, menuInfos) {
    state.menuInfos = menuInfos;
  },
};

const actions = {
  async [actionType.FETCH_MENU_INFO_FROM_SERVER]({ commit }) {
    const { data } = await api.menu.fetchMenus();
    commit(mutationType.SET_MENU_INFO, data);
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};

import { createRouter, createWebHistory } from 'vue-router';

import { actionType, getterType } from '@/const';
import store from '@/store';
import { ObjectUtil } from '@/util';

import { useGuards } from './guard';
import routes, { menus } from './routes';

const router = createRouter({
  history: createWebHistory(),
  routes,
});

const getWillRemoveRoutes = (menuObj) => {
  return router.getRoutes().filter((menu) => {
    const code = menu?.meta?.menu?.code;

    //* home, login 등 메뉴 코드와 관련 없는 라우터는 삭제하지 않습니다.
    if (!code) {
      return false;
    }

    return !ObjectUtil.has(menuObj, code);
  });
};

const removeRoutes = (routes) => {
  for (const route of routes) {
    const { meta = {} } = route;
    const { menu = {} } = meta;
    menu.show = false;
  }
};

/**
 * 사용하지 않는 메뉴는 Vue routes에서 제거합니다.
 * 제거하지 않고 메뉴에서만 숨길 시 url로 직접 접근이 가능합니다.
 */
const initializeRouter = async () => {
  await store.dispatch(actionType.FETCH_MENU_INFO_FROM_SERVER);
  const menuInfo = store.getters[getterType.GET_MENU_INFOS_AS_OBJECT];
  const removeTargetRoutes = getWillRemoveRoutes(menuInfo);
  removeRoutes(removeTargetRoutes);
};

useGuards(router);

export { routes, menus, initializeRouter };

export default router;

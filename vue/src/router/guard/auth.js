import { message, mutationType } from '@/const';
import { $axios } from '@/plugin';
import { showAlertWarn } from '@/plugin/custom/alert';
import store from '@/store';

const isFreePath = (path) => ['/welcome', '/login', '/sso-login'].includes(path);

const isMenu = ({ meta }) => meta?.menu != null;

const noEixstsMenuCode = ({ meta }) => meta?.menu?.code == null;

const fetchAllowedMenu = (menuCode) => $axios.get(`/authorization/menus/${menuCode}/allowed`);

const noLogIn = () => !store.getters.isLoggedIn;

const isSuperUser = () => store.getters.isSuperUser;

function authGuard(router) {
  router.beforeEach(async (to, from, next) => {
    if (isFreePath(to.path) || isSuperUser() || !isMenu(to)) {
      next();
      return;
    }

    if (noEixstsMenuCode(to)) {
      return;
    }

    if (noLogIn()) {
      store.commit(mutationType.TO_WELCOME);
      return;
    }

    const response = await fetchAllowedMenu(to.meta.menu.code);

    const {
      data: { result: allowed },
    } = response;

    if (!allowed) {
      showAlertWarn(message.error.noAuthorizeMenu).then(() => router.push('/'));
      return;
    }

    next();
  });
}

export default authGuard;

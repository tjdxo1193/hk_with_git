import { BaseLayout } from '@/component/layout';
import Main from '@/page/main';

export default [
  {
    path: '/',
    component: BaseLayout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        component: Main.Home,
        meta: {
          isAuditMenu: false,
        },
      },
    ],
    meta: {
      isAuditMenu: false,
    },
  },
  {
    path: '/welcome',
    component: Main.IntegrationLogin,
    meta: {
      isAuditMenu: false,
    },
  },
  {
    path: '/login',
    component: Main.Login,
    meta: {
      isAuditMenu: false,
    },
  },
  {
    path: '/sso-login',
    component: Main.SSOLogin,
    meta: {
      isAuditMenu: false,
    },
  },
];

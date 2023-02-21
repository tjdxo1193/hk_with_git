import { BaseLayout } from '@/component/layout';
import System from '@/page/sy';

export default [
  {
    path: '/dev',
    name: 'forDeveloper',
    component: BaseLayout,
    meta: {
      isAuditMenu: false,
      menu: {
        title: '개발용 메뉴',
      },
    },
    children: [
      {
        path: 'auditTrailManage',
        name: 'auditTrailManage',
        component: System.AuditTrailManage,
        meta: {
          isAuditMenu: false,
          menu: {
            title: 'AuditTrail 관리 (개발자용)',
          },
        },
      },
    ],
  },
];

import { BaseLayout } from '@/component/layout';
import AnalColManage from '@/page/an';

export default [
  {
    path: '/an',
    name: 'AnalColManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M129',
        title: '분석컬럼관리',
      },
    },
    children: [
      {
        path: 'analColReceipt',
        name: 'analColReceipt',
        component: AnalColManage.AnalColReceipt,
        meta: {
          menu: {
            code: 'M130',
            title: '분석컬럼입고',
          },
        },
      },
      {
        path: 'analColLabelPrint',
        name: 'analColLabelPrint',
        component: AnalColManage.AnalColLabelPrint,
        meta: {
          menu: {
            code: 'M131',
            title: '분석컬럼라벨출력',
          },
        },
      },
      {
        path: 'analColOpenDis',
        name: 'analColOpenDis',
        component: AnalColManage.AnalColOpenDis,
        meta: {
          menu: {
            code: 'M132',
            title: '분석컬럼개봉및폐기',
          },
        },
      },
      {
        path: 'analColDisAppr',
        name: 'analColDisAppr',
        component: AnalColManage.AnalColDisAppr,
        meta: {
          menu: {
            code: 'M134',
            title: '분석컬럼폐기승인',
          },
        },
      },
      {
        path: 'analColOpenCancel',
        name: 'analColOpenCancel',
        component: AnalColManage.AnalColOpenCancel,
        meta: {
          menu: {
            code: 'M133',
            title: '분석컬럼개봉취소',
          },
        },
      },
      {
        path: 'analColUsageHis',
        name: 'analColUsageHis',
        component: AnalColManage.AnalColUsageHis,
        meta: {
          menu: {
            code: 'M135',
            title: '분석컬럼사용기록',
          },
        },
      },
      {
        path: 'analColSearch',
        name: 'analColSearch',
        component: AnalColManage.AnalColSearch,
        meta: {
          menu: {
            code: 'M136',
            title: '분석컬럼조회',
          },
        },
      },
    ],
  },
];

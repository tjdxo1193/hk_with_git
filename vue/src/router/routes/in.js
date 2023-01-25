import { BaseLayout } from '@/component/layout';
import InstrumentManage from '@/page/in';

export default [
  {
    path: '/in',
    name: 'InstrumentManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M007',
        title: '기기관리',
      },
    },
    children: [
      {
        path: 'instManage',
        name: 'instManage',
        component: InstrumentManage.InstManage,
        meta: {
          menu: {
            code: 'M051',
            title: '기기관리',
          },
        },
      },
      {
        path: 'instHisManage',
        name: 'instHisManage',
        component: InstrumentManage.InstHisManage,
        meta: {
          menu: {
            code: 'M052',
            title: '기기이력관리',
          },
        },
      },
      {
        path: 'instHisAppr',
        name: 'instHisAppr',
        component: InstrumentManage.InstHisAppr,
        meta: {
          menu: {
            code: 'M053',
            title: '기기이력관리승인',
          },
        },
      },
      {
        path: 'instUsageHis',
        name: 'instUsageHis',
        component: InstrumentManage.InstUsageHis,
        meta: {
          menu: {
            code: 'M054',
            title: '기기사용기록',
          },
        },
      },
      {
        path: 'instSearch',
        name: 'instSearch',
        component: InstrumentManage.InstSearch,
        meta: {
          menu: {
            code: 'M055',
            title: '기기관리조회',
          },
        },
      },
      {
        path: 'instManageByItem',
        name: 'instManageByItem',
        component: InstrumentManage.InstManageByItem,
        meta: {
          menu: {
            code: 'M056',
            title: '품목별기기관리',
          },
        },
      },
    ],
  },
];

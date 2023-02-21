import { BaseLayout } from '@/component/layout';
import ReagentManage from '@/page/re';

export default [
  {
    path: '/re',
    name: 'reagentManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M008',
        title: '시약관리',
      },
    },
    children: [
      {
        path: 'reagentReceipt',
        name: 'reagentReceipt',
        component: ReagentManage.ReagentReceipt,
        meta: {
          menu: {
            code: 'M057',
            title: '시약입고',
          },
        },
      },
      {
        path: 'reagentLabelPrint',
        name: 'reagentLabelPrint',
        component: ReagentManage.ReagentLabelPrint,
        meta: {
          menu: {
            code: 'M058',
            title: '시약라벨출력',
          },
        },
      },
      {
        path: 'reagentOpenDis',
        name: 'reagentOpenDis',
        component: ReagentManage.ReagentOpenDis,
        meta: {
          menu: {
            code: 'M059',
            title: '시약개봉및폐기',
          },
        },
      },
      {
        path: 'reagentDisAppr',
        name: 'reagentDisAppr',
        component: ReagentManage.ReagentDisAppr,
        meta: {
          menu: {
            code: 'M060',
            title: '시약폐기승인',
          },
        },
      },
      {
        path: 'reagentOpenCancel',
        name: 'reagentOpenCancel',
        component: ReagentManage.ReagentOpenCancel,
        meta: {
          menu: {
            code: 'M061',
            title: '시약개봉취소',
          },
        },
      },
      {
        path: 'reagentUsageHis',
        name: 'reagentUsageHis',
        component: ReagentManage.ReagentUsageHis,
        meta: {
          menu: {
            code: 'M062',
            title: '시약사용기록',
          },
        },
      },
      {
        path: 'reagentSearch',
        name: 'reagentSearch',
        component: ReagentManage.ReagentSearch,
        meta: {
          menu: {
            code: 'M063',
            title: '시약조회',
          },
        },
      },
    ],
  },
];

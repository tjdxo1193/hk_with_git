import { BaseLayout } from '@/component/layout';
import StabilityManage from '@/page/st';

export default [
  {
    path: '/st',
    name: 'StabilityManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M004',
        title: '안정성관리',
      },
    },
    children: [
      {
        path: 'stabPlan',
        name: 'stabPlan',
        component: StabilityManage.StabPlan,
        meta: {
          menu: {
            code: 'M033',
            title: '안정성시험계획',
          },
        },
      },
      {
        path: 'stabPlanDetailAppr',
        name: 'stabPlanDetailAppr',
        component: StabilityManage.StabPlanDetailAppr,
        meta: {
          menu: {
            code: 'M034',
            title: '안정성상세계획승인',
          },
        },
      },
      {
        path: 'stabResultAppr',
        name: 'stabResultAppr',
        component: StabilityManage.StabResultAppr,
        meta: {
          menu: {
            code: 'M035',
            title: '안정성결과승인',
          },
        },
      },
      {
        path: 'stabSampleReceipt',
        name: 'stabSampleReceipt',
        component: StabilityManage.StabSampleReceipt,
        meta: {
          menu: {
            code: 'M036',
            title: '안정성검체입고',
          },
        },
      },
      {
        path: 'stabSampleUsage',
        name: 'stabSampleUsage',
        component: StabilityManage.StabSampleUsage,
        meta: {
          menu: {
            code: 'M037',
            title: '안정성검체사용',
          },
        },
      },
      {
        path: 'stabSampleUsageAppr',
        name: 'stabSampleUsageAppr',
        component: StabilityManage.StabSampleUsageAppr,
        meta: {
          menu: {
            code: 'M038',
            title: '안정성검체사용승인',
          },
        },
      },
      {
        path: 'stabSampleDis',
        name: 'stabSampleDis',
        component: StabilityManage.StabSampleDis,
        meta: {
          menu: {
            code: 'M039',
            title: '안정성검체폐기',
          },
        },
      },
      {
        path: 'stabSampleDisAppr',
        name: 'stabSampleDisAppr',
        component: StabilityManage.StabSampleDisAppr,
        meta: {
          menu: {
            code: 'M040',
            title: '안정성검체폐기승인',
          },
        },
      },
    ],
  },
];

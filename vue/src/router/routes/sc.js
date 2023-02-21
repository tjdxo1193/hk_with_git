import { BaseLayout } from '@/component/layout';
import Search from '@/page/sc';

export default [
  {
    path: '/sc',
    name: 'search',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M011',
        title: '조회',
      },
    },
    children: [
      {
        path: 'SearchForPerformByTester',
        name: 'SearchForPerformByTester',
        component: Search.SearchForPerformByTester,
        meta: {
          menu: {
            code: 'M075',
            title: '시험자실적조회',
          },
        },
      },
      {
        path: 'SearchForTestByTestItem',
        name: 'SearchForTestByTestItem',
        component: Search.SearchForTestByTestItem,
        meta: {
          menu: {
            code: 'M076',
            title: '시험항목별시험조회',
          },
        },
      },
      {
        path: 'SearchForTestByStatus',
        name: 'SearchForTestByStatus',
        component: Search.SearchForTestByStatus,
        meta: {
          menu: {
            code: 'M077',
            title: '시험현황조회',
          },
        },
      },
      {
        path: 'SearchForMonitorTest',
        name: 'SearchForMonitorTest',
        component: Search.SearchForMonitorTest,
        meta: {
          menu: {
            code: 'M078',
            title: '모니터링시험조회',
          },
        },
      },
      {
        path: 'SearchForStabTest',
        name: 'SearchForStabTest',
        component: Search.SearchForStabTest,
        meta: {
          menu: {
            code: 'M079',
            title: '안정성시험조회',
          },
        },
      },
      {
        path: 'SearchForSpecHis',
        name: 'SearchForSpecHis',
        component: Search.SearchForSpecHis,
        meta: {
          menu: {
            code: 'M080',
            title: '규격서 이력조회',
          },
        },
      },
      {
        path: 'SearchForResultByPeriod',
        name: 'SearchForResultByPeriod',
        component: Search.SearchForResultByPeriod,
        meta: {
          menu: {
            code: 'M081',
            title: '기간별시험결과조회',
          },
        },
      },
      {
        path: 'SearchForResultByMonitorPeriod',
        name: 'SearchForResultByMonitorPeriod',
        component: Search.SearchForResultByMonitorPeriod,
        meta: {
          menu: {
            code: 'M082',
            title: '모니터링기간별시험결과조회',
          },
        },
      },
      {
        path: 'InspectProductionPerformance',
        name: 'InspectProductionPerformance',
        component: Search.InspectProductionPerformance,
        meta: {
          menu: {
            code: 'M150',
            title: '생산실적검사',
          },
        },
      },
      {
        path: 'InspectPurchasingPerformance',
        name: 'InspectPurchasingPerformance',
        component: Search.InspectPurchasingPerformance,
        meta: {
          menu: {
            code: 'M151',
            title: '구매실적검사',
          },
        },
      },
    ],
  },
];

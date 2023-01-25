import { BaseLayout } from '@/component/layout';
import NonconformityTestPreventRecurrence from '@/page/np';

export default [
  {
    path: '/np',
    name: 'NonconformityTestPreventRecurrence',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M152',
        title: '부적합재발방지',
      },
    },
    children: [
      {
        path: 'NonconformityReportWrt',
        name: 'NonconformityReportWrt',
        component: NonconformityTestPreventRecurrence.NonconformityReportWrt,
        meta: {
          menu: {
            code: 'M153',
            title: '부적합통보서작성',
          },
        },
      },
      {
        path: 'NonconformityReportAppr',
        name: 'NonconformityReportAppr',
        component: NonconformityTestPreventRecurrence.NonconformityReportAppr,
        meta: {
          menu: {
            code: 'M154',
            title: '부적합통보서승인',
          },
        },
      },
      {
        path: 'PreventRecurrenceReview',
        name: 'PreventRecurrenceReview',
        component: NonconformityTestPreventRecurrence.PreventRecurrenceReview,
        meta: {
          menu: {
            code: 'M132',
            title: '재발방지대책서검토',
          },
        },
      },
      {
        path: 'PreventRecurrenceAppr',
        name: 'PreventRecurrenceAppr',
        component: NonconformityTestPreventRecurrence.PreventRecurrenceAppr,
        meta: {
          menu: {
            code: 'M134',
            title: '재발방지대책서승인',
          },
        },
      },
      {
        path: 'NonconformityReportSearch',
        name: 'NonconformityReportSearch',
        component: NonconformityTestPreventRecurrence.NonconformityReportSearch,
        meta: {
          menu: {
            code: 'M133',
            title: '부적합통보서조회',
          },
        },
      },
    ],
  },
];

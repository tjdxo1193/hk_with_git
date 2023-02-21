import { BaseLayout } from '@/component/layout';
import MonitorTestManage from '@/page/mt';

export default [
  {
    path: '/mt',
    name: 'MonitorTestManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M003',
        title: '모니터링시험관리',
      },
    },
    children: [
      {
        path: 'monitorTestRequest',
        name: 'monitorTestRequest',
        component: MonitorTestManage.MonitorTestRequest,
        meta: {
          menu: {
            code: 'M025',
            title: '모니터링시험의뢰',
          },
        },
      },
      {
        path: 'monitorTestReceipt',
        name: 'monitorTestReceipt',
        component: MonitorTestManage.MonitorTestReceipt,
        meta: {
          menu: {
            code: 'M026',
            title: '모니터링시험접수',
          },
        },
      },
      {
        path: 'monitorTestInstruction',
        name: 'monitorTestInstruction',
        component: MonitorTestManage.MonitorTestInstruction,
        meta: {
          menu: {
            code: 'M027',
            title: '모니터링시험지시',
          },
        },
      },
      {
        path: 'monitorTestAssign',
        name: 'monitorTestAssign',
        component: MonitorTestManage.MonitorTestAssign,
        meta: {
          menu: {
            code: 'M028',
            title: '모니터링시험자배정',
          },
        },
      },
      {
        path: 'monitorResultInput',
        name: 'monitorResultInput',
        component: MonitorTestManage.MonitorResultInput,
        meta: {
          menu: {
            code: 'M029',
            title: '모니터링결과입력',
          },
        },
      },
      {
        path: 'monitorResultReview',
        name: 'monitorResultReview',
        component: MonitorTestManage.MonitorResultReview,
        meta: {
          menu: {
            code: 'M030',
            title: '모니터링결과검토',
          },
        },
      },
      {
        path: 'monitorResultAppr',
        name: 'monitorResultAppr',
        component: MonitorTestManage.MonitorResultAppr,
        meta: {
          menu: {
            code: 'M031',
            title: '모니터링결과승인',
          },
        },
      },
      {
        path: 'monitorResultCancel',
        name: 'monitorResultCancel',
        component: MonitorTestManage.MonitorResultCancel,
        meta: {
          menu: {
            code: 'M032',
            title: '모니터링결과보류 및 취소',
          },
        },
      },
    ],
  },
];

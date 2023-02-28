import { BaseLayout } from '@/component/layout';
import PrintManage from '@/page/pr';

export default [
  {
    path: '/pr',
    name: 'printManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M015',
        title: '출력관리',
      },
    },
    children: [
      {
        path: 'printReport',
        name: 'printReport',
        component: PrintManage.PrintReport,
        meta: {
          menu: {
            code: 'M112',
            title: '리포트출력',
          },
        },
      },
      {
        path: 'printMonitorReport',
        name: 'printMonitorReport',
        component: PrintManage.PrintMonitorReport,
        meta: {
          menu: {
            code: 'M113',
            title: '모니터링리포트출력',
          },
        },
      },
      {
        path: 'printLabel',
        name: 'printLabel',
        component: PrintManage.PrintLabel,
        meta: {
          menu: {
            code: 'M114',
            title: '라벨출력',
          },
        },
      },
      {
        path: 'rePrintLabelRequest',
        name: 'rePrintLabelRequest',
        component: PrintManage.RePrintLabelRequest,
        meta: {
          menu: {
            code: 'M115',
            title: '라벨재출력요청',
          },
        },
      },
      {
        path: 'rePrintLabelAppr',
        name: 'rePrintLabelAppr',
        component: PrintManage.RePrintLabelAppr,
        meta: {
          menu: {
            code: 'M116',
            title: '라벨재출력승인',
          },
        },
      },
      {
        path: 'labelSearch',
        name: 'labelSearch',
        component: PrintManage.LabelSearch,
        meta: {
          menu: {
            code: 'M158',
            title: '라벨출력조회',
          },
        },
      },
      {
        path: 'reportMaster',
        name: 'reportMaster',
        component: PrintManage.ReportMaster,
        meta: {
          menu: {
            code: 'M149',
            title: '리포트마스터',
          },
        },
      },
      {
        path: 'printIntegratedReport',
        name: 'printIntegratedReport',
        component: PrintManage.PrintIntegratedReport,
        meta: {
          menu: {
            code: 'M159',
            title: '리포트출력(통합성적서)',
          },
        },
      },
    ],
  },
];

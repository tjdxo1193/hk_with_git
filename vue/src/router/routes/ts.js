import { BaseLayout } from '@/component/layout';
import TestManage from '@/page/ts';

export default [
  {
    path: '/ts',
    name: 'testManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M002',
        title: '시험관리',
      },
    },
    children: [
      {
        path: 'testRequest',
        name: 'testRequest',
        component: TestManage.TestRequest,
        meta: {
          menu: {
            code: 'M016',
            title: '시험의뢰',
          },
        },
      },
      {
        path: 'testReceipt',
        name: 'testReceipt',
        component: TestManage.TestReceipt,
        meta: {
          menu: {
            code: 'M017',
            title: '시험접수',
          },
        },
      },
      {
        path: 'testCollection',
        name: 'testCollection',
        component: TestManage.TestCollection,
        meta: {
          menu: {
            code: 'M018',
            title: '검체채취',
          },
        },
      },
      {
        path: 'testInstruction',
        name: 'testInstruction',
        component: TestManage.TestInstruction,
        meta: {
          menu: {
            code: 'M019',
            title: '시험지시',
          },
        },
      },
      {
        path: 'testAssign',
        name: 'testAssign',
        component: TestManage.TestAssign,
        meta: {
          menu: {
            code: 'M020',
            title: '시험자배정',
          },
        },
      },
      {
        path: 'testResultInput',
        name: 'testResultInput',
        component: TestManage.TestResultInput,
        meta: {
          menu: {
            code: 'M021',
            title: '결과입력',
          },
        },
      },
      {
        path: 'testResultReview',
        name: 'testResultReview',
        component: TestManage.TestResultReview,
        meta: {
          menu: {
            code: 'M022',
            title: '결과검토',
          },
        },
      },
      {
        path: 'testResultAppr',
        name: 'testResultAppr',
        component: TestManage.TestResultAppr,
        meta: {
          menu: {
            code: 'M023',
            title: '결과승인',
          },
        },
      },
      {
        path: 'testResultCancel',
        name: 'testResultCancel',
        component: TestManage.TestResultCancel,
        meta: {
          menu: {
            code: 'M024',
            title: '결과보류 및 취소',
          },
        },
      },
      {
        path: 'testUnsuitableList',
        name: 'testUnsuitableList',
        component: TestManage.TestUnsuitableList,
        meta: {
          menu: {
            code: 'M141',
            title: '부적합 목록',
          },
        },
      },
    ],
  },
];

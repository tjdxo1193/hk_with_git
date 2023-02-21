import { BaseLayout } from '@/component/layout';
import AddSampleManage from '@/page/kp';

export default [
  {
    path: '/kp',
    name: 'AddSampleManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M006',
        title: '추가검체관리',
      },
    },
    children: [
      {
        path: 'addSampleRequest',
        name: 'addSampleRequest',
        component: AddSampleManage.AddSampleRequest,
        meta: {
          menu: {
            code: 'M046',
            title: '추가검체요청',
          },
        },
      },
      {
        path: 'addSampleAppr',
        name: 'addSampleAppr',
        component: AddSampleManage.AddSampleAppr,
        meta: {
          menu: {
            code: 'M047',
            title: '추가검체승인',
          },
        },
      },
      {
        path: 'addSampleLabelRequest',
        name: 'addSampleLabelRequest',
        component: AddSampleManage.AddSampleLabelRequest,
        meta: {
          menu: {
            code: 'M048',
            title: '용기당검체라벨요청',
          },
        },
      },
      {
        path: 'addSampleLabelPub',
        name: 'addSampleLabelPub',
        component: AddSampleManage.AddSampleLabelPub,
        meta: {
          menu: {
            code: 'M049',
            title: '추가검체라벨발행',
          },
        },
      },
      {
        path: 'addSampleSearch',
        name: 'addSampleSearch',
        component: AddSampleManage.AddSampleSearch,
        meta: {
          menu: {
            code: 'M050',
            title: '추가검체조회',
          },
        },
      },
    ],
  },
];

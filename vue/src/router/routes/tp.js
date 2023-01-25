import { BaseLayout } from '@/component/layout';
import SampleManage from '@/page/tp';

export default [
  {
    path: '/tp',
    name: 'SampleManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M005',
        title: '검체관리',
      },
    },
    children: [
      {
        path: 'sampleManage',
        name: 'sampleManage',
        component: SampleManage.SampleManage,
        meta: {
          menu: {
            code: 'M041',
            title: '검체관리',
          },
        },
      },
      {
        path: 'sampleUsage',
        name: 'sampleUsage',
        component: SampleManage.SampleUsage,
        meta: {
          menu: {
            code: 'M042',
            title: '검체사용',
          },
        },
      },
      {
        path: 'sampleUsageAppr',
        name: 'sampleUsageAppr',
        component: SampleManage.SampleUsageAppr,
        meta: {
          menu: {
            code: 'M043',
            title: '검체사용승인',
          },
        },
      },
      {
        path: 'sampleDis',
        name: 'sampleDis',
        component: SampleManage.SampleDis,
        meta: {
          menu: {
            code: 'M044',
            title: '검체폐기',
          },
        },
      },
      {
        path: 'sampleDisAppr',
        name: 'sampleDisAppr',
        component: SampleManage.SampleDisAppr,
        meta: {
          menu: {
            code: 'M045',
            title: '검체폐기승인',
          },
        },
      },
    ],
  },
];

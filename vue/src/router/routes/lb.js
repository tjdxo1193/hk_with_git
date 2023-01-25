import { BaseLayout } from '@/component/layout';
import LabEventManage from '@/page/lb';

export default [
  {
    path: '/lb',
    name: 'LabEventManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M010',
        title: '실험실이벤트관리',
      },
    },
    children: [
      {
        path: 'labEventReview',
        name: 'labEventReview',
        component: LabEventManage.LabEventReview,
        meta: {
          menu: {
            code: 'M072',
            title: '실험실이벤트검토',
          },
        },
      },
      {
        path: 'labEventAppr',
        name: 'labEventAppr',
        component: LabEventManage.LabEventAppr,
        meta: {
          menu: {
            code: 'M073',
            title: '실험실이벤트승인',
          },
        },
      },
      {
        path: 'labEventSearch',
        name: 'labEventSearch',
        component: LabEventManage.LabEventSearch,
        meta: {
          menu: {
            code: 'M074',
            title: '실험실이벤트조회',
          },
        },
      },
    ],
  },
];

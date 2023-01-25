import { BaseLayout } from '@/component/layout';
import Master from '@/page/ms';

export default [
  {
    path: '/ms',
    name: 'master',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M012',
        title: '마스터',
      },
    },
    children: [
      {
        path: 'itemManage',
        name: 'itemManage',
        component: Master.ItemManage,
        meta: {
          menu: {
            code: 'M084',
            title: '품목관리',
          },
        },
      },
      {
        path: 'itemApprRequest',
        name: 'itemApprRequest',
        component: Master.ItemApprRequest,
        meta: {
          menu: {
            code: 'M139',
            title: '품목승인요청',
          },
        },
      },
      {
        path: 'itemAppr',
        name: 'itemAppr',
        component: Master.ItemAppr,
        meta: {
          menu: {
            code: 'M140',
            title: '품목승인',
          },
        },
      },
      {
        path: 'specManage',
        name: 'specManage',
        component: Master.SpecManage,
        meta: {
          menu: {
            code: 'M086',
            title: '규격서관리',
          },
        },
      },
      {
        path: 'specApprRequest',
        name: 'specApprRequest',
        component: Master.SpecApprRequest,
        meta: {
          menu: {
            code: 'M087',
            title: '규격서승인요청',
          },
        },
      },
      {
        path: 'specAppr',
        name: 'specAppr',
        component: Master.SpecAppr,
        meta: {
          menu: {
            code: 'M088',
            title: '규격서승인',
          },
        },
      },
      {
        path: 'wrapTestManage',
        name: 'wrapTestManage',
        component: Master.WrapTestManage,
        meta: {
          menu: {
            code: 'M147',
            title: '자재계층관리',
          },
        },
      },
      {
        path: 'monitorManage',
        name: 'monitorManage',
        component: Master.MonitorManage,
        meta: {
          menu: {
            code: 'M089',
            title: '모니터링관리',
          },
        },
      },
      {
        path: 'monitorSpecManage',
        name: 'monitorSpecManage',
        component: Master.MonitorSpecManage,
        meta: {
          menu: {
            code: 'M122',
            title: '모니터링규격서관리',
          },
        },
      },
      {
        path: 'monitorSpecApprRequest',
        name: 'monitorSpecApprRequest',
        component: Master.MonitorSpecApprRequest,
        meta: {
          menu: {
            code: 'M123',
            title: '모니터링규격서승인요청',
          },
        },
      },
      {
        path: 'monitorSpecAppr',
        name: 'monitorSpecAppr',
        component: Master.MonitorSpecAppr,
        meta: {
          menu: {
            code: 'M124',
            title: '모니터링규격서승인',
          },
        },
      },
      {
        path: 'testItemManage',
        name: 'testItemManage',
        component: Master.TestItemManage,
        meta: {
          menu: {
            code: 'M085',
            title: '시험항목관리',
          },
        },
      },
      {
        path: 'testItemPerManage',
        name: 'testItemPerManage',
        component: Master.TestItemPerManage,
        meta: {
          menu: {
            code: 'M125',
            title: '시험항목별방법관리',
          },
        },
      },

      {
        path: 'staffJobDelegation',
        name: 'staffJobDelegation',
        component: Master.StaffJobDelegation,
        meta: {
          menu: {
            code: 'M121',
            title: '담당자업무위임',
          },
        },
      },
      {
        path: 'staffJobDelegationApproval',
        name: 'staffJobDelegationApproval',
        component: Master.StaffJobDelegationApproval,
        meta: {
          menu: {
            code: 'M128',
            title: '담당자업무위임승인',
          },
        },
      },

      {
        path: 'testCycle',
        name: 'testCycle',
        component: Master.TestCycle,
        meta: {
          menu: {
            code: 'M126',
            title: '시험주기',
          },
        },
      },
      {
        path: 'testTermManage',
        name: 'testTermManage',
        component: Master.TestTermManage,
        meta: {
          menu: {
            code: 'M127',
            title: '시험기간관리',
          },
        },
      },
      {
        path: 'testMaterialManage',
        name: 'testMaterialManage',
        component: Master.TestMaterialManage,
        meta: {
          menu: {
            code: 'M090',
            title: '시험재료관리',
          },
        },
      },
    ],
  },
];

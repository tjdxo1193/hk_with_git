import { BaseLayout } from '@/component/layout';
import GlassManage from '@/page/gl';

export default [
  {
    path: '/gl',
    name: 'GlassManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M014',
        title: '초자관리',
      },
    },
    children: [
      {
        path: 'glassReceipt',
        name: 'glassReceipt',
        component: GlassManage.GlassReceipt,
        meta: {
          menu: {
            code: 'M107',
            title: '초자입고',
          },
        },
      },
      {
        path: 'glassLabelPrint',
        name: 'glassLabelPrint',
        component: GlassManage.GlassLabelPrint,
        meta: {
          menu: {
            code: 'M108',
            title: '초자라벨출력',
          },
        },
      },
      {
        path: 'glassOpenDis',
        name: 'glassOpenDis',
        component: GlassManage.GlassOpenDis,
        meta: {
          menu: {
            code: 'M109',
            title: '초자개봉및폐기',
          },
        },
      },
      {
        path: 'glassOpenCancel',
        name: 'glassOpenCancel',
        component: GlassManage.GlassOpenCancel,
        meta: {
          menu: {
            code: 'M110',
            title: '초자개봉취소',
          },
        },
      },
      {
        path: 'glassSearch',
        name: 'glassSearch',
        component: GlassManage.GlassSearch,
        meta: {
          menu: {
            code: 'M111',
            title: '초자조회',
          },
        },
      },
    ],
  },
];

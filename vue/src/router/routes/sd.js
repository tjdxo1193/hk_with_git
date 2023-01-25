import { BaseLayout } from '@/component/layout';
import StandardItemManage from '@/page/sd';

export default [
  {
    path: '/sd',
    name: 'stdItemManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M009',
        title: '표준품관리',
      },
    },
    children: [
      {
        path: 'stdItemReceipt',
        name: 'stdItemReceipt',
        component: StandardItemManage.StdItemReceipt,
        meta: {
          menu: {
            code: 'M064',
            title: '표준품입고',
          },
        },
      },
      {
        path: 'stdItemAppr',
        name: 'stdItemAppr',
        component: StandardItemManage.StdItemAppr,
        meta: {
          menu: {
            code: 'M065',
            title: '표준품승인',
          },
        },
      },
      {
        path: 'stdItemLabelPrint',
        name: 'stdItemLabelPrint',
        component: StandardItemManage.StdItemLabelPrint,
        meta: {
          menu: {
            code: 'M066',
            title: '표준품라벨출력',
          },
        },
      },
      {
        path: 'stdItemOpenDis',
        name: 'stdItemOpenDis',
        component: StandardItemManage.StdItemOpenDis,
        meta: {
          menu: {
            code: 'M067',
            title: '표준품개봉및폐기',
          },
        },
      },
      {
        path: 'stdItemDisAppr',
        name: 'stdItemDisAppr',
        component: StandardItemManage.StdItemDisAppr,
        meta: {
          menu: {
            code: 'M068',
            title: '표준품폐기승인',
          },
        },
      },
      {
        path: 'stdItemOpenCancel',
        name: 'stdItemOpenCancel',
        component: StandardItemManage.StdItemOpenCancel,
        meta: {
          menu: {
            code: 'M069',
            title: '표준품개봉취소',
          },
        },
      },
      {
        path: 'stdItemUsageHis',
        name: 'stdItemUsageHis',
        component: StandardItemManage.StdItemUsageHis,
        meta: {
          menu: {
            code: 'M070',
            title: '표준품사용기록',
          },
        },
      },
      {
        path: 'stdItemSearch',
        name: 'stdItemSearch',
        component: StandardItemManage.StdItemSearch,
        meta: {
          menu: {
            code: 'M071',
            title: '표준품조회',
          },
        },
      },
    ],
  },
];

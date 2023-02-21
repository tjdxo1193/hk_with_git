import { BaseLayout } from '@/component/layout';
import SpecimenManage from '@/page/sm';

export default [
  {
    path: '/sm',
    name: 'SpecimenManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M142',
        title: '표준견본관리',
      },
    },
    children: [
      {
        path: 'specimenManage',
        name: 'specimenManage',
        component: SpecimenManage.SpecimenManage,
        props: true,
        meta: {
          menu: {
            code: 'M143',
            title: '표준견본관리',
          },
        },
      },
      {
        path: 'processSpecimenManage',
        name: 'processSpecimenManage',
        component: SpecimenManage.ProcessSpecimenManage,
        props: true,
        meta: {
          menu: {
            code: 'M144',
            title: '공정표준견본관리',
          },
        },
      },
      {
        path: 'packagingSpecimenManage',
        name: 'packagingSpecimenManage',
        component: SpecimenManage.PackagingSpecimenManage,
        meta: {
          menu: {
            code: 'M145',
            title: '포장재표준견본관리',
          },
        },
      },
      {
        path: 'packagingSpecimenApprove',
        name: 'packagingSpecimenApprove',
        component: SpecimenManage.PackagingSpecimenApprove,
        meta: {
          menu: {
            code: 'M145',
            title: '포장재표준견본폐기승인',
          },
        },
      },
    ],
  },
];

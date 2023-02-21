import { BaseLayout } from '@/component/layout';
import Sample from '@/page/sample';

export default [
  {
    path: '/sample',
    name: 'sample',
    component: BaseLayout,
    meta: {
      menu: {
        title: '샘플 페이지',
      },
    },
    children: [
      {
        path: 'tab',
        name: 'tab',
        component: Sample.TabPage,
        meta: {
          menu: {
            title: '탭',
          },
        },
      },
      {
        path: 'single',
        name: 'single',
        component: Sample.AUIGridPage,
        meta: {
          menu: {
            title: '단일 그리드',
          },
        },
      },
      {
        path: 'multiple',
        name: 'multiple',
        component: Sample.AUIGridMultiplePage,
        meta: {
          menu: {
            title: '다중 그리드',
          },
        },
      },
      {
        path: 'jjeong',
        name: 'jjeong',
        component: Sample.ReagentStandardPage,
        meta: {
          menu: {
            title: '샘플 페이지 - 항목기준관리',
          },
        },
      },
    ],
  },
];

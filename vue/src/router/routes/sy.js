import { BaseLayout } from '@/component/layout';
import System from '@/page/sy';

export default [
  {
    path: '/sy',
    name: 'systemManage',
    component: BaseLayout,
    meta: {
      menu: {
        code: 'M013',
        title: '시스템관리',
      },
    },
    children: [
      {
        path: 'authManage',
        name: 'authManage',
        component: System.AuthManage,
        meta: {
          menu: {
            code: 'M092',
            title: '권한관리',
          },
        },
      },
      {
        path: 'userManage',
        name: 'userManage',
        component: System.UserManage,
        meta: {
          menu: {
            code: 'M095',
            title: '사용자관리',
          },
        },
      },
      {
        path: 'departmentManage',
        name: 'departmentManage',
        component: System.DepartmentManage,
        meta: {
          menu: {
            code: 'M117',
            title: '부서 관리',
          },
        },
      },
      {
        path: 'menuManage',
        name: 'menuManage',
        component: System.MenuManage,
        meta: {
          menu: {
            code: 'M093',
            title: '메뉴관리',
          },
        },
      },
      {
        path: 'plantManage',
        name: 'plantManage',
        component: System.PlantManage,
        meta: {
          menu: {
            code: 'M106',
            title: '플랜트 관리',
          },
        },
      },
      {
        path: 'CommonCodeManage',
        name: 'CommonCodeManage',
        component: System.CommonCodeManage,
        props: { mode: 'user' },
        meta: {
          menu: {
            code: 'M104',
            title: '공통코드 관리',
          },
        },
      },
      {
        path: 'SystemCommonCodeManage',
        name: 'SystemCommonCodeManage',
        component: System.CommonCodeManage,
        props: { mode: 'system' },
        meta: {
          menu: {
            code: 'M105',
            title: '시스템 공통코드 관리',
          },
        },
      },
      {
        path: 'scheduleManage',
        name: 'scheduleManage',
        component: System.ScheduleManage,
        meta: {
          menu: {
            code: 'M096',
            title: '일자관리(공휴일관리)',
          },
        },
      },
      {
        path: 'printManage',
        name: 'printManage',
        component: System.PrintManage,
        meta: {
          menu: {
            code: 'M097',
            title: '출력물관리',
          },
        },
      },
      {
        path: 'auditTrail',
        name: 'auditTrail',
        component: System.AuditTrail,
        meta: {
          menu: {
            code: 'M103',
            title: 'AuditTrail',
          },
        },
      },
      {
        path: 'loginHistory',
        name: '로그인 이력',
        component: System.LoginHistory,
        meta: {
          menu: {
            code: 'M119',
            title: '로그인 이력',
          },
        },
      },
      // {
      //   path: '',
      //   name: '',
      //   component: null,
      //   meta: {
      //     menu: {
      //       code: '',
      //       title: '인터페이스 정보 조회',
      //     }
      //   }
      // },,
      // {
      //   path: '',
      //   name: '',
      //   component: null,
      //   meta: {
      //     menu: {
      //       code: '',
      //       title: '인터페이스 수신 이력',
      //     }
      //   }
      // },
      // {
      //   path: '',
      //   name: '',
      //   component: null,
      //   meta: {
      //     menu: {
      //       code: '',
      //       title: '인터페이스 송신 이력',
      //     }
      //   }
      // },
      // {
      //   path: 'notice',
      //   name: 'notice',
      //   component: System.Notice,
      //   meta: {
      //     menu: {
      //       code: 'M099',
      //       title: '공지사항',
      //     },
      //   },
      // },
      // {
      //   path: 'noticeManagement',
      //   name: 'noticeManagement',
      //   component: System.NoticeManagement,
      //   meta: {
      //     menu: {
      //       code: 'M100',
      //       title: '공지사항 관리',
      //     },
      //   },
      // },
    ],
  },
];

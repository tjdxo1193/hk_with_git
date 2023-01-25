import { routes } from '@/router';

export default {
  getUpperMenusForAudit() {
    return new window.Promise((resolve) => {
      resolve({
        data: routes
          .filter((menu) => menu.meta?.isAuditMenu !== false)
          .map((menu) => ({
            value: menu.meta?.menu?.code,
            label: menu.meta?.menu?.title,
            path: menu.path,
          })),
      });
    });
  },
  getLeafMenusForAudit(menuPath) {
    return new window.Promise((resolve) => {
      resolve({
        data:
          routes.find((menu) => menu.path === menuPath).children ??
          [].map((menu) => ({
            value: menu.meta?.menu?.code,
            label: menu.meta?.menu?.title,
            path: menu.path,
          })),
      });
    });
  },
};

import { routes } from '@/router';

const eixstsMenuInfo = (route) => {
  const hasMenuInfo = route.meta?.menu?.code && route.meta?.menu?.title;
  if (!hasMenuInfo) {
    console.warn(`Leaf route has not menu info in meta. path: ${route.path}`);
  }
  return hasMenuInfo;
};

export default {
  getLeafMenusComboForAudit() {
    return new window.Promise((resolve) => {
      resolve({
        data: routes
          .flatMap((branch) => branch.children ?? [])
          .filter((route) => route.meta?.isAuditMenu !== false && eixstsMenuInfo(route))
          .map(
            ({
              meta: {
                menu: { code, title },
              },
            }) => {
              return {
                value: code,
                label: title,
              };
            },
          )
          .sort((a, b) => (a.label > b.label ? 1 : a.label === b.labe ? 0 : -1)),
      });
    });
  },
};

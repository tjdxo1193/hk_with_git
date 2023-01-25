import authGuard from './auth';
import defaultGuard from './default';

const guards = [defaultGuard, authGuard];

function useGuards(router) {
  guards.forEach((guard) => guard(router));
}

export { useGuards };

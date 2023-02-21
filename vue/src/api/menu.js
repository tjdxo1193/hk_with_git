import { $axios } from '@/plugin';

export default {
  fetchMenus() {
    return $axios.get('/menus');
  },
};

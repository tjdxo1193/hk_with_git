import { $axios } from '@/plugin';

export default {
  getUpperMenuList() {
    return $axios.get('/combo/upperMenu');
  },
};

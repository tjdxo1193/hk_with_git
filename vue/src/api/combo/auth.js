import { $axios } from '@/plugin';

export default {
  getUserAuthList() {
    return $axios.get('/combo/userAuth');
  },
};

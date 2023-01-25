import { message } from '@/const';

export default {
  install(app) {
    app.config.globalProperties.$message = message;
  },
};

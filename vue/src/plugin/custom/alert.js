import { mutationType } from '@/const';
import store from '@/store';

const showAlert = (type, message) => {
  return new window.Promise((resolve) => {
    store.commit(mutationType.SHOW_ALERT, { type, message, resolve });
  });
};

export const showAlertInfo = (message) => showAlert('info', message);
export const showAlertWarn = (message) => showAlert('warn', message);
export const showAlertError = (message) => showAlert('error', message);
export const showConfirm = (message) => showAlert('confirm', message);

export default {
  //* handling @/component/feedback/Alert.vue
  install(app) {
    app.config.globalProperties.$info = showAlertInfo;
    app.config.globalProperties.$warn = showAlertWarn;
    app.config.globalProperties.$error = showAlertError;
    app.config.globalProperties.$confirm = showConfirm;
  },
};

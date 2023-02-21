import { createApp } from 'vue';

import App from '@/App';
import globalComponents from '@/component/global';
import router, { initializeRouter } from '@/router';
import store, { initializeStore } from '@/store';

import plugin from './plugin';

const preLoad = async () => {
  await window.Promise.allSettled([initializeStore(), initializeRouter()]);
};

const app = createApp(App).use(store).use(router).use(plugin).use(globalComponents);

preLoad().finally(() => app.mount('#app'));

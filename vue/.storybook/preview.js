import { app } from '@storybook/vue3';
import plugin from '@/plugin';
import globalComponents from '@/component/global';

plugin.install(app);
globalComponents.install(app);

export const parameters = {
  actions: { argTypesRegex: "^on[A-Z].*" },
  controls: {
    matchers: {
      color: /(background|color)$/i,
      date: /Date$/,
    },
  },
}
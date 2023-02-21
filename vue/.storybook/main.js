const path = require("path");
const env = require('../env/local');

module.exports = {
  "stories": [
    "../src/**/*.stories.mdx",
    "../src/**/*.stories.@(js|jsx|ts|tsx)",
  ],
  "addons": [
    "@storybook/addon-links",
    "@storybook/addon-essentials",
    "@storybook/addon-interactions",
    {
      name: "storybook-addon-turbo-build",
      options: {
        optimizationLevel: 2,
        disableSourceMap: true,
        esbuildMinifyOptions: {
          minify: true,
          minifyWhitespace: true,
          minifyIdentifiers: true,
          minifySyntax: true,
          legalComments: 'none',
          sourcemap: false,
          css: true,
        },
      },
    },
  ],
  "framework": "@storybook/vue3",
  "core": {
    "builder": "@storybook/builder-webpack5"
  },
  env: (config) => ({
    ...config,
    ...env,
  }),
  staticDirs: ['../public', '../lib/local', '../src/assets'],
  webpackFinal: async config => {
    // scss load
    config.module.rules.push({
      test: /\.scss$/,
      use: ['style-loader', 'css-loader', {
        loader: 'sass-loader',
        options: {
          additionalData: `
            @import '@/assets/scss/variable.scss';
            @import '@/assets/scss/index.scss';
          `,
        },
      }],
      include: path.resolve(__dirname, '../'),
    });

    // path alias
    config.resolve.alias['@'] = path.resolve(__dirname, '../src/');
    return config;
  },
}
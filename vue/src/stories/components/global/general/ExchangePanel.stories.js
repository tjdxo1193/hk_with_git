import { ExchangePanel } from '@/component/global/general';

export default {
  title: 'General/ExchangePanel',
  component: ExchangePanel,
  argTypes: {
    direction: {
      description: `ExchangePanel의 방향을 지정해줍니다.
      `,
      control: {
        type: 'select',
      },
      options: ['horizontal', 'vertical'],
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'horizontal',
        },
      },
    },
    onClick: {
      action: '@click',
      description: '버튼 클릭 시 실행됩니다.',
      table: {
        type: {
          summary: '@click',
          detail: 'Function',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { ExchangePanel },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <ExchangePanel v-bind="args" @click="onClick" />
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  direction: 'vertical',
};

Default.parameters = {
  docs: {
    source: {
      code: `
      <ExchangePanel :direction="direction" @click="onClick" />
      `,
      language: 'md',
    },
  },
};

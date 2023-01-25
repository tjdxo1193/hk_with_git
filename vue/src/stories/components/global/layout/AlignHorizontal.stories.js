import { AlignHorizontal } from '@/component/global/layout';

export default {
  title: 'layout/AlignHorizontal',
  component: AlignHorizontal,
  argTypes: {
    spans: {
      type: {
        summary: 'Array',
      },
      description: '각 Component의 너비 비율',
      defaultValue: {
        summary: '[10]',
      },
    },
    gap: {
      type: {
        summary: 'Number',
      },
      description: '각 Component간의 간격',
      defaultValue: {
        summary: '0',
      },
    },
    alignItems: {
      control: {
        type: 'select',
      },
      description: '세로 정렬 상태 속성',
      options: ['default', 'start', 'center', 'end'],
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'default',
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
    components: { AlignHorizontal },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `
<Horizontal v-bind="args">
  <AUIGrid />
  <ExchangePanel direction="vertical" @click="onClick" />
  <AUIGrid />
</Horizontal>
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  alignItems: 'center',
  spans: [7, 1, 12],
  gap: 0,
};

Default.parameters = {
  docs: {
    description: {
      component: `
      
    HTML 요소들을 가로로 정렬하기 위해 사용하는 컴포넌트입니다.

      `,
    },
    source: {
      language: 'md',
      code: `
<Horizontal :alignItems="alignItems" :spans="spans" :gap="gap">
  <AUIGrid />
  <ExchangePanel direction="vertical" />
  <AUIGrid />
</Horizontal>
      `,
    },
  },
};

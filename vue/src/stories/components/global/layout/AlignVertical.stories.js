import { AlignVertical } from '@/component/global/layout';

export default {
  title: 'layout/AlignVertical',
  component: AlignVertical,
  argTypes: {
    alignItems: {
      control: {
        type: 'select',
      },
      description: '가로 정렬 상태 속성',
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
    components: { AlignVertical },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `
<vertical v-bind="args">
  <AUIGrid />
  <ExchangePanel direction="horizontal" @click="onClick" />
  <AUIGrid />
</vertical>
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  alignItems: 'center',
};

Default.parameters = {
  docs: {
    description: {
      component: `
      
      HTML 요소들을 세로로 정렬하기 위해 사용하는 컴포넌트입니다.
      
      `,
    },
    source: {
      language: 'md',
      code: `
<Vertical :alignItems="alignItems">
  <AUIGrid />
  <ExchangePanel direction="horizontal" />
  <AUIGridr />
</Vertical>
      `,
    },
  },
};

import { ActionBar } from '@/component/global/general';

export default {
  title: 'General/ActionBar',
  component: ActionBar,
  argTypes: {
    buttons: {
      type: {
        summary: 'Array',
      },
      defaultValue: {
        summary: '[]',
      },
    },
    space: {
      description: `ActionBar의 위치를 설정합니다.
      `,
      control: {
        type: 'select',
      },
      options: ['none', 'center', 'around', 'between', 'end'],
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'end',
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
    components: { ActionBar },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <ActionBar v-bind="args" @click="onClick">
      슬롯 영역
    </ActionBar>
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  buttons: [
    { label: '초기화', value: 'init' },
    { label: '저장', value: 'save' },
    { label: '조회', value: 'search' },
  ],
  space: 'between',
};

Default.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
<ActionBar :buttons="buttons" :space="space" @click="onClick">
  슬롯 영역
</ActionBar>
      `,
    },
  },
};

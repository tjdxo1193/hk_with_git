import { ButtonArrow } from '@/component/global/general';

export default {
  title: 'General/ButtonArrow',
  component: ButtonArrow,
  argTypes: {
    direction: {
      description: `ButtonArrow의 방향을 설정합니다.
      `,
      control: {
        type: 'select',
      },
      options: ['up', 'left', 'down', 'right'],
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'left',
        },
      },
    },
    onClick: {
      action: '@click',
      description: '화살표 방향의 버튼 클릭 시 실행됩니다.',
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
    components: { ButtonArrow },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <ButtonArrow
        v-bind="args"
        @click="onClick"
      />
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  direction: 'left',
};

Default.parameters = {
  docs: {
    source: {
      code: `
<ButtonArrow
  :direction="direction"
  @click="onClick"
/>
      `,
      language: 'md',
    },
  },
};

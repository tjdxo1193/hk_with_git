import { ButtonBase } from '@/component/global/general';

export default {
  title: 'General/ButtonBase',
  component: ButtonBase,
  argTypes: {
    name: {
      type: {
        summary: 'String',
      },
      defaultValue: {
        summary: 'null',
      },
    },
    label: {
      type: {
        summary: 'String',
      },
      defaultValue: {
        summary: 'null',
      },
    },
    type: {
      description: `Button의 사용용도에 따라 스타일을 변경합니다.
      `,
      control: {
        type: 'select',
      },
      options: ['default', 'normal', 'warn', 'danger', 'search', 'clear'],
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'default',
        },
      },
    },
    disabled: {
      description: 'Button의 활성화/비활성화를 제어합니다.',
      type: {
        summary: 'Boolean',
      },
      defaultValue: {
        summary: 'false',
      },
    },
    debounce: {
      description: '클릭 후 다음 클릭이 가능할 때까지의 시간입니다. 단위는 ms입니다.',
      type: {
        summary: 'Number',
      },
      defaultValue: {
        summary: '600',
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
    components: { ButtonBase },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <ButtonBase v-bind="args" @click="onClick" />
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  name: 'key',
  type: 'default',
  label: '조회',
  debounce: 600,
  disabled: false,
};

Default.parameters = {
  docs: {
    description: {
      component: `
      
    더블클릭을 방지하기 위해 throttle이 적용되어 있어 0.6초마다 1번만 클릭할 수 있습니다.

      `,
    },
    source: {
      code: `
<ButtonBase
  :name="name"
  :type="type"
  :label="label"
  :debounce="600"
  @click="onClick"
/>
      `,
      language: 'md',
    },
  },
};

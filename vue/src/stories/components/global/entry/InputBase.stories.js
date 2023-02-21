import { InputBase } from '@/component/global/entry';

export default {
  title: 'Entry/InputBase',
  component: InputBase,
  argTypes: {
    'v-model': {
      table: {
        type: {
          summary: 'directive',
        },
      },
    },
    readonly: {
      table: {
        type: {
          summary: 'Boolean',
        },
        defaultValue: {
          summary: 'false',
        },
      },
    },
    disabled: {
      table: {
        type: {
          summary: 'Boolean',
        },
        defaultValue: {
          summary: 'false',
        },
      },
    },
    type: {
      description: '텍스트, 비밀번호, 숫자 양식을 사용할 지 여부입니다.',
      control: {
        type: 'select',
      },
      options: ['text', 'password', 'number'],
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'text',
        },
      },
    },
    onInput: {
      action: '@input',
      description: `input이벤트 외에도 기본 이벤트를 전부 지원합니다.
      `,
      table: {
        type: {
          summary: '@input',
          detail: 'Function',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { InputBase },
    data() {
      return {
        args,
        value: null,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <InputBase
      v-bind="args"
      v-model="value"
      @input="onInput"
    />
    {{ args.name }}: {{ value }}
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  name: 'data',
  readonly: false,
  disabled: false,
  type: 'text',
};

Default.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
<InputBase
  name="data"
  v-model="value"
  @input="onInput"
/>
      `,
    },
  },
};

import { RadioBase } from '@/component/global/entry';

export default {
  title: 'Entry/RadioBase',
  component: RadioBase,
  argTypes: {
    'v-model': {
      table: {
        type: {
          summary: 'directive',
        },
      },
    },
    name: {
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'null',
        },
      },
    },
    label: {
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'null',
        },
      },
    },
    checkedValue: {
      table: {
        type: {
          summary: 'Any',
        },
        defaultValue: {
          summary: 'true',
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
    onInput: {
      action: '@input',
      description: '값 입력시 실행됩니다. input 외에도 기본 이벤트를 전부 지원합니다.',
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
    components: { RadioBase },
    data() {
      return {
        args,
        value: 'null',
      };
    },
    props: Object.keys(argTypes),
    template: `
    <RadioBase
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
  name: 'category',
  label: '종류',
  disabled: false,
  readonly: false,
  checkedValue: 'true',
};

Default.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
<RadioBase
  v-model="value"
  name="category"
  label="종류"
  @input="onInput"
/>
`,
    },
  },
};

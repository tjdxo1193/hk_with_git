import { TextareaBase } from '@/component/global/entry';

export default {
  title: 'Entry/TextareaBase',
  component: TextareaBase,
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
    rows: {
      table: {
        type: {
          summary: 'Number',
        },
        defaultValue: {
          summary: '1',
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
    components: { TextareaBase },
    data() {
      return {
        args,
        value: null,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <TextareaBase
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
  rows: 5,
};

Default.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
<TextareaBase
  v-model="value"
  name="data"
  :rows="5"
  @input="onInput"
/>
      `,
    },
  },
};

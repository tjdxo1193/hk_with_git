import { Datepicker } from '@/component/global/entry';

export default {
  title: 'Entry/Datepicker',
  component: Datepicker,
  argTypes: {
    'v-model': {
      table: {
        type: {
          summary: 'directive',
        },
      },
    },
    twin: {
      description: '범위 일자 Datepicker를 사용할 지 여부입니다.',
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
      description: `Datepicker의 종류를 지정합니다.
      `,
      control: {
        type: 'select',
      },
      options: ['day'],
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'day',
        },
      },
    },
    maxRange: {
      table: {
        type: {
          summary: 'Number',
        },
        defaultValue: {
          summary: 'null',
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
      description: '날짜 선택 시 실행됩니다.',
      table: {
        type: {
          summary: '@input',
          detail: 'Function',
        },
      },
    },
    onFocus: {
      action: '@focus',
      description: '포커스가 들어왔을 때 실행됩니다.',
      table: {
        type: {
          summary: '@focus',
          detail: 'Function',
        },
      },
    },
    onBlur: {
      action: '@blur',
      description: '포커스가 떠났을 때 실행됩니다.',
      table: {
        type: {
          summary: '@blur',
          detail: 'Function',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { Datepicker },
    data() {
      return {
        args,
        value: null,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <Datepicker
      v-model="value"
      v-bind="args"
      @input="onInput"
      @focus="onFocus"
      @blur="onBlur"
    />
    {{ args.name }}: {{ value }}
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  name: '의뢰일자',
  readonly: false,
  disabled: false,
  type: 'day',
};

Default.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
<Datepicker
  v-model="value"
  name="의뢰일자"
  @input="onInput"
  @focus="onFocus"
  @blur="onBlur"
/>
`,
    },
  },
};

export const Twin = Template.bind({});
Twin.args = {
  name: '의뢰일자',
  maxRange: 30,
  twin: true,
  readonly: false,
  disabled: false,
  type: 'day',
};

Twin.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
<Datepicker
  v-model="value"
  name="의뢰일자"
  :twin="true"
  :maxRange="30"
  @input="onInput"
  @focus="onFocus"
  @blur="onBlur"
/>
`,
    },
  },
};

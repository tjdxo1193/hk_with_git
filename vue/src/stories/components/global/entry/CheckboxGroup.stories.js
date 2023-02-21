import { CheckboxGroup } from '@/component/global/entry';

export default {
  title: 'Entry/CheckboxGroup',
  component: CheckboxGroup,
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
    groups: {
      description: '체크박스를 정의한 배열입니다.',
      table: {
        type: {
          summary: 'Array<CheckboxProps>',
        },
        defaultValue: {
          summary: 'null',
        },
      },
    },
    countPerRow: {
      description: '한 행에 몇 개의 체크박스를 나열할 지 정의합니다.',
      table: {
        type: {
          summary: 'Number',
        },
        defaultValue: {
          summary: 'null',
        },
      },
    },
    gap: {
      description: '체크박스 간의 간격을 설정합니다',
      table: {
        type: {
          summary: 'Number',
        },
        defaultValue: {
          summary: '70',
        },
      },
    },
    rowGap: {
      description: '체크 박스의 상하 간격을 설정합니다',
      table: {
        type: {
          summary: 'Number',
        },
        defaultValue: {
          summary: '0',
        },
      },
    },
    space: {
      description: '체크 박스의 정렬 방법을 정의합니다.',
      control: {
        type: 'select',
      },
      options: ['none', 'center', 'around', 'between', 'end'],
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'none',
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
      description: `input이벤트 외에도 기본 이벤트를 전부 지원합니다.`,
      table: {
        type: {
          summary: '@input',
          detail: 'Function',
        },
      },
    },
    onChecked: {
      action: '@checked',
      description: '체크박스가 체크됐을 때 실행됩니다.',
      table: {
        type: {
          summary: '@checked',
          detail: 'Function',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { CheckboxGroup },
    data() {
      return {
        args,
        value: [],
      };
    },
    props: Object.keys(argTypes),
    template: `
    <CheckboxGroup
      v-bind="args"
      v-model="value"
      @input="onInput"
      @checked="onChecked"
    />
    {{ args.name }}: {{ value }}
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  name: 'fruit',
  groups: [
    { checkedValue: 'apple', label: '사과' },
    { checkedValue: 'watermelon', label: '수박' },
    { checkedValue: 'strawberry', label: '딸기' },
    { checkedValue: 'melon', label: '메론' },
    { checkedValue: 'mango', label: '망고' },
  ],
  countPerRow: 2,
  disabled: false,
  readonly: false,
  space: 'none',
  gap: 80,
  rowGap: 10,
};

Default.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
<CheckboxGroup
  v-model="value"
  name: 'fruit',
  groups: [
    { checkedValue: 'apple', label: '사과' },
    { checkedValue: 'watermelon', label: '수박' },
    { checkedValue: 'strawberry', label: '딸기' },
    { checkedValue: 'melon', label: '메론' },
    { checkedValue: 'mango', label: '망고' }
  ],
  countPerRow: 2,
  @input="onInput"
  @checked="onChecked"
/>
      `,
    },
  },
};

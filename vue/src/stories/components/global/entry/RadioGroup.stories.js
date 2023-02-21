import { RadioGroup } from '@/component/global/entry';

export default {
  title: 'Entry/RadioGroup',
  component: RadioGroup,
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
      description: '라디오 버튼을 정의한 배열입니다.',
      table: {
        type: {
          summary: 'Array<RadioBaseProps>',
        },
        defaultValue: {
          summary: 'null',
        },
      },
    },
    countPerRow: {
      description: '한 행에 몇 개의 라디오 버튼을 나열할 지 정의합니다.',
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
      description: '라디오 버튼 간의 좌우 간격을 설정합니다.',
      table: {
        type: {
          summary: 'Number',
        },
        defaultValue: {
          summary: '50',
        },
      },
    },
    rowGap: {
      description: '라디오 버튼 간의 상하 간격을 설정합니다',
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
      description: '라디오 버튼의 정렬 방법을 정의합니다.',
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
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { RadioGroup },
    data() {
      return {
        args,
        value: [],
      };
    },
    props: Object.keys(argTypes),
    template: `
    <RadioGroup
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
  gap: 50,
  rowGap: 10,
};

Default.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
<RadioGroup
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
  disabled: false,
  readonly: false,
  space: 'none',
  gap: 50,
  @input="onInput"
/>
      `,
    },
  },
};

import { SelectBase } from '@/component/global/entry';

export default {
  title: 'Entry/SelectBase',
  component: SelectBase,
  argTypes: {
    'v-model': {
      table: {
        type: {
          summary: 'directive',
        },
      },
    },
    defaultOption: {
      table: {
        type: {
          summary: 'Object',
        },
        defaultValue: {
          summary: `{ show: true, value: null, label: '선택' }`,
        },
      },
    },
    async: {
      description: `서버에서 목록을 가져와야 할 때 사용합니다. 값이 있다면 'elements'가 무시되며 Promise에서 반환된 Response 객체를 이용해 options를 생성합니다.`,
      table: {
        type: {
          summary: `Function<Promise<Response>>`,
          detail: `
Response {
  data: [
    { value: 'apple', label: '사과' },
    { value: 'watermelon', label: '수박' },
  ]
}
          `,
        },
        defaultValue: {
          summary: 'null',
        },
      },
    },
    elements: {
      description: `옵션을 정의한 배열입니다. 'async'가 존재할 시 무시됩니다. 주로 정적으로 목록을 구성할 때 사용합니다.`,
      table: {
        type: {
          summary: `Array<{ value: 'apple', label: '사과' }>`,
        },
        defaultValue: {
          summary: '[]',
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
    onLoaded: {
      action: '@loaded',
      description: 'options 데이터의 로드가 완료되면 실행됩니다.',
      table: {
        type: {
          summary: '@loaded',
          detail: 'Function',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { SelectBase },
    data() {
      return {
        args,
        value: null,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <SelectBase
      v-model="value"
      v-bind="args"
      @input="onInput"
      @loaded="onLoaded"
    />
    {{ args.name }}: {{ value }}
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  name: 'fruit',
  readonly: false,
  disabled: false,
  defaultOption: {
    show: true,
    value: null,
    label: '선택',
  },
  elements: [
    { value: 'apple', label: '사과' },
    { value: 'watermelon', label: '수박' },
  ],
};

Default.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
<SelectBase
  v-model="value"
  name="fruit"
  elements="[
    { value: 'apple', label: '사과' },
    { value: 'watermelon', label: '수박' },
  ]"
  @input="onInput"
/>
      `,
    },
  },
};

export const Async = Template.bind({});
Async.args = {
  name: 'fruit',
  readonly: false,
  disabled: false,
  async: () =>
    new window.Promise((resolve) => {
      resolve({
        data: [
          { value: 'apple', label: '사과' },
          { value: 'watermelon', label: '수박' },
        ],
      });
    }),
};

Async.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
<SelectBase
  v-model="value"
  name="fruit"
  async="() => new Promise(resolve => {
    resolve({
      data: [
        { value: 'apple', label: '사과' },
        { value: 'watermelon', label: '수박' },
      ],
    });
  })"
  @input="onInput"
/>
      `,
    },
  },
};

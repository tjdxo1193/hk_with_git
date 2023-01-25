import { TabBase } from '@/component/global/display';

export default {
  title: 'Display/TabBase',
  component: TabBase,
  argTypes: {
    tabs: {
      description: 'Tab을 정의한 배열',
      table: {
        type: {
          summary: 'Array<TabBaseProps>',
        },
        defaultValue: {
          summary: '[]',
        },
      },
    },
    buttons: {
      description: 'Tab 컴포넌트에서 공통으로 사용할 버튼 목록',
      table: {
        type: {
          summaray: 'Array<ButtonBaseProps>',
        },
        defaultValue: {
          summary: '[]',
        },
      },
    },
    onChange: {
      description: '탭이 변경될 때 실행됩니다.',
      table: {
        type: {
          summary: '@change',
          detail: 'Function',
        },
      },
    },
    onCommonButtonClick: {
      description: '탭의 공통 버튼을 클릭했을 때 실행됩니다.',
      table: {
        type: {
          summary: '@common-button-click',
          detail: 'Function',
        },
      },
    },
    onButtonClick: {
      description: '각 탭 별로 정의된 버튼을 클릭했을 때 실행됩니다.',
      table: {
        type: {
          summary: '@button-click',
          detail: 'Function',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { TabBase },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <TabBase
      v-bind="args"
      @change="onChange"
      @common-button-click="onCommonButtonClick"
      @button-click="onButtonClick"
    >
      <template #tab-first>
        first tab content.
      </template>

      <template #tab-second>
        second tab content.
      </template>
    </TabBase>
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  tabs: [
    {
      name: 'first',
      label: 'Tab 1',
      buttons: [
        { name: 'tab1-button-1', label: '탭1 버튼 - 1' },
        { name: 'tab1-button-2', label: '탭1 버튼 - 2' },
      ],
    },
    { name: 'second', label: 'Tab 2', buttons: [{ name: 'tab2-button-1', label: '탭2 버튼 - 1' }] },
  ],
  buttons: [
    { name: 'common-1', label: '공통 버튼 1' },
    { name: 'common-2', label: '공통 버튼 2' },
  ],
};

Default.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
const tabs = [
  {
    "name": "first",
    "label": "Tab 1",
    "selected": true,
    "buttons": [
      { "name": "tab1-button-1", "label": "탭1 버튼 - 1" },
      { "name": "tab1-button-2", "label": "탭1 버튼 - 2" },
    ],
  },
  {
    "name": "second",
    "label": "Tab 2",
    "buttons": [
      { "name": "tab2-button-1", "label": "탭2 버튼 - 1" },
    ],
  },
];

const commonButtons = [
  { "name": "common-1", "label": "공통 버튼 1" },
  { "name": "common-2", "label": "공통 버튼 2" },
];

<TabBase
  :tabs="tabs"
  :buttons="commonButtons"
  @changeTab="onChangeTab"
  @common-button-click="onCommonButtonClick"
  @button-click="onButtonClick"
>
  <template #tab-first>
    first tab content.
  </template>

  <template #tab-second>
    second tab content.
  </template>
</TabBase>
      `,
    },
  },
};

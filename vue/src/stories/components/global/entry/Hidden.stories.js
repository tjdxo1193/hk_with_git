import { Hidden } from '@/component/global/entry';

export default {
  title: 'Entry/Hidden',
  component: Hidden,
  argTypes: {
    value: {
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'null',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { Hidden },
    props: Object.keys(argTypes),
    data() {
      return {
        name: args.name,
        value: args.value,
      };
    },
    template: `
    <Hidden
      :value="value"
    />
    {{ name }}: {{ value }}
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  name: 'data',
  value: 'Hello World',
};

Default.parameters = {
  docs: {
    description: {
      component: `
      
      FormBuilder에서 숨겨진 값을 매핑하기 위해 사용되는 Wrapper 컴포넌트입니다.
      `,
    },
    source: {
      language: 'md',
      code: `
<Hidden
  name="data"
  value="Hello World"
/>
      `,
    },
  },
};

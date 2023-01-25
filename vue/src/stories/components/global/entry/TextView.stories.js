import { TextView } from '@/component/global/display';

export default {
  title: 'Entry/TextView',
  component: TextView,
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
    components: { TextView },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <TextView
      v-bind="args"
    />
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  value: 'Hello World',
};

Default.parameters = {
  docs: {
    description: {
      component: `
      
      FormBuilder에서 단순히 값을 보여주기 위해 사용되는 Wrapper 컴포넌트입니다.
      `,
    },
    source: {
      language: 'md',
      code: `
<TextView
  value="Hello World"
/>
      `,
    },
  },
};

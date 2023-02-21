import { Progress } from '@/component/feedback';

export default {
  title: 'feedback/Progress',
  component: Progress,
  argTypes: {
    isLoading: {
      table: {
        type: {
          summary: 'Boolean',
        },
        defaultValue: {
          summary: 'true',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { Progress },
    data() {
      return {
        isLoading: args.isLoading,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <div :class="['progress-container', isLoading && 'progressing']"></div>
    View code / Hide code 버튼을 클릭해보세요.
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  isLoading: true,
};

Default.parameters = {
  docs: {
    description: {
      component: `
      this.$showProgress(); // 커서가 wait 모양으로 변경됩니다. 또한, 화면 클릭이 불가능해집니다.
    this.$hideProgress(); // 원래의 커서 모양으로 돌아오며 화면 클릭이 가능해집니다.
      `,
    },
    source: {
      code: `
      this.$showProgress();
this.$hideProgress();
      `,
      language: 'md',
    },
  },
};

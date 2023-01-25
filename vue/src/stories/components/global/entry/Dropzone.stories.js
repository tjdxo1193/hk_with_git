import { Dropzone } from '@/component/global/entry';

export default {
  title: 'Entry/Dropzone',
  component: Dropzone,
  argTypes: {
    option: {
      description: 'notion의 Dropzone API문서를 참고하세요',
      table: {
        type: {
          summary: 'Object',
        },
        defaultValue: {
          summary: '{}',
        },
      },
    },
    onCreated: {
      action: '@created',
      description: '',
      table: {
        type: {
          summary: '@created',
          detail: 'Function',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { Dropzone },
    props: Object.keys(argTypes),
    template: `
    <Dropzone
      @created="onCreated"
    />
    `,
  };
};

export const Default = Template.bind({});
Default.args = {};

Default.parameters = {
  docs: {
    description: {
      component: `
      
      Drag & Drop으로 파일을 관리하는 컴포넌트입니다.

      `,
    },
    source: {
      language: 'md',
      code: `
<Dropzone
  @created="onCreated"  
/>
      `,
    },
  },
};

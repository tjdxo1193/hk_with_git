import { InputFile } from '@/component/global/entry';

export default {
  title: 'Entry/InputFile',
  component: InputFile,
  argTypes: {
    value: {
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: null,
        },
      },
    },
    accept: {
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: null,
        },
      },
    },
    onAttachFile: {
      action: '@attachFile',
      description: '파일을 선택했을 시 실행됩니다.',
      table: {
        type: {
          summary: '@attachFile',
          detail: 'Function',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { InputFile },
    data() {
      return {
        args,
        value: null,
        accept: null,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <InputFile
    ref="fileAttach"
    :accept="accept"
    @attachFile="onAttachFile" />
    `,
  };
};

export const Default = Template.bind({});
Default.args = {};

Default.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
      <InputFile
      ref="fileAttach"
      :accept="accept"
      @attachFile="onAttachFile" />
      `,
    },
  },
};

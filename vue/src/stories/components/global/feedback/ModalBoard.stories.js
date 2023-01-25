import { ModalBoard } from '@/component/global/feedback';

export default {
  title: 'Feedback/ModalBoard',
  component: ModalBoard,
  argTypes: {
    show: {
      table: {
        type: {
          summary: 'Boolean',
        },
        defaultValue: {
          summary: 'false',
        },
      },
    },
    width: {
      description: 'Modal Width',
      table: {
        type: {
          summary: 'String, Number',
        },
        defaultValue: {
          summary: 'auto',
        },
      },
    },
    height: {
      description: 'Modal Height',
      table: {
        type: {
          summary: 'String, Number',
        },
        defaultValue: {
          summary: 'auto',
        },
      },
    },
    okText: {
      description: '확인 버튼의 텍스트를 설정합니다.',
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: '확인',
        },
      },
    },
    cancelText: {
      description: '취소 버튼의 텍스트를 설정합니다.',
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: '취소',
        },
      },
    },
    plain: {
      description: 'Body 슬롯만 노출하며 바탕 스타일을 생성하지 않습니다.',
      table: {
        type: {
          summary: 'Boolean',
        },
        defaultValue: {
          summary: 'false',
        },
      },
    },
    onOk: {
      action: '@ok',
      description: '확인 버튼 클릭 시 실행됩니다.',
      table: {
        type: {
          summary: '@ok',
          detail: 'Function',
        },
      },
    },
    onClose: {
      action: '@close',
      description: '닫기(X) 버튼 클릭 시 실행됩니다.',
      table: {
        type: {
          summary: '@close',
          detail: 'Function',
        },
      },
    },
    onCancel: {
      action: '@cancel',
      description: '취소 버튼 클릭 시 실행됩니다.',
      table: {
        type: {
          summary: '@cancel',
          detail: 'Function',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { ModalBoard },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <ModalBoard v-bind="args" @ok="onOk" @close="onClose" @cancel="onCancle">
      바디 영역입니다
    </ModalBoard>
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  show: true,
  title: '제목',
  width: 'auto',
  height: 'auto',
  okText: '확인',
  cancelText: '취소',
  plain: false,
};

Default.parameters = {
  docs: {
    source: {
      code: `
<ModalBoard :title="title" :show="show" :width="width" :height="height" :plain="false">
  바디 영역
</ModalBoard>
      `,
      language: 'md',
    },
  },
};

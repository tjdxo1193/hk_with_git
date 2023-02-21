import { ModalBase } from '@/component/global/feedback';

export default {
  title: 'Feedback/ModalBase',
  component: ModalBase,
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
    top: {
      description: '모달 위치를 위로 이동시킵니다.',
      table: {
        type: {
          summary: 'Number',
        },
        defaultValue: {
          summary: '0',
        },
      },
    },
    right: {
      description: '모달 위치를 오른쪽으로 이동시킵니다.',
      table: {
        type: {
          summary: 'Number',
        },
        defaultValue: {
          summary: '0',
        },
      },
    },
    bottom: {
      description: '모달 위치를 아래로 이동시킵니다.',
      table: {
        type: {
          summary: 'Number',
        },
        defaultValue: {
          summary: '0',
        },
      },
    },
    left: {
      description: '모달 위치를 왼쪽으로 이동시킵니다.',
      table: {
        type: {
          summary: 'Number',
        },
        defaultValue: {
          summary: '0',
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
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { ModalBase },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `
     <ModalBase v-bind="args" @close="onClose">
      바디 영역

      <template #footer>
        푸터 영역
      </template>
     </ModalBase>
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  title: '제목',
  show: true,
  width: 'auto',
  height: 'auto',
  top: 0,
  right: 0,
  bottom: 0,
  left: 0,
  plain: false,
};

Default.parameters = {
  docs: {
    source: {
      code: `
<ModalBase :title="title" :show="show" :width="width" :height="height" :plain="false">
  바디 영역
  
  <template #footer>
    푸터 영역
  </template>
</ModalBase>
      `,
      language: 'md',
    },
  },
};

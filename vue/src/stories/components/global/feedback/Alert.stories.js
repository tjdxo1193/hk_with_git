import { Alert } from '@/component/feedback';

export default {
  title: 'feedback/Alert',
  component: Alert,
  argTypes: {
    type: {
      description: `알림창의 사용 용도에 따라 버튼 및 스타일을 변경합니다.
      `,
      control: {
        type: 'select',
      },
      options: ['info', 'warn', 'error', 'confirm'],
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'Info',
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
      description: '취소 버튼 클릭 시 실행됩니다.',
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
    components: { Alert },
    data() {
      return {
        args,
        isConfirmType: args.type === 'confirm',
      };
    },
    methods: {
      onConfirm() {
        args.onOk();
        alert('확인 버튼 클릭시 호출됩니다.');
      },
    },
    props: Object.keys(argTypes),
    template: `
    <div class="alert-wrapper">
      <div :class="['alert-inner', args.type]">
        <div class="alert-inner__body">
          <img />
          <div class="alert-inner__message" v-html="'Hello World'"></div>
        </div>

        <div class="alert-inner__footer">
          <ButtonBase v-if="isConfirmType" type="normal" @click="args.onClose">취소</ButtonBase>
          <ButtonBase @click="onConfirm">확인</ButtonBase>
        </div>
      </div>
    </div>
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  type: 'info',
};

Default.parameters = {
  docs: {
    description: {
      component: `

    다음 타입들의 Alert창을 상황에 따라 사용할 수 있습니다.
      
    info, warn, error, confirm
      `,
    },
    source: {
      code: `
this.$info('Hello World').then(() => {
  alert('확인 버튼 클릭시 호출됩니다.');
});
      `,
      language: 'md',
    },
  },
};

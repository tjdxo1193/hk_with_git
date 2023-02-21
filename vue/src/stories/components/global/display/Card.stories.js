import { Card } from '@/component/global/display';

export default {
  title: 'Display/Card',
  component: Card,
  argTypes: {
    title: {
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'null',
        },
      },
    },
    width: {
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'auto',
        },
      },
    },
    height: {
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: 'auto',
        },
      },
    },
    padding: {
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: '15px',
        },
      },
    },
    margin: {
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: '20px',
        },
      },
    },
    transparent: {
      table: {
        type: {
          summary: 'Boolean',
        },
        defaultValue: {
          summary: 'false',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { Card },
    data() {
      return {
        title: args.title,
        action: args.action,
        footer: args.footer,
        width: args.width,
        height: args.height,
        padding: args.padding,
        margin: args.margin,
        transparent: args.transparent,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <Card :title="title" :width="width" :height="height" :padding="padding" :margin="margin" :transparent="transparent">
      <template v-if="action" #action>
        {{ action }}
      </template>

      <div>콘텐츠 영역</div>

      <template v-if="footer" #footer>
        {{ footer }}
      </template>
    </Content>
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  title: 'Hello World',
  action: '액션 영역',
  footer: '푸터 영역',
  width: 'auto',
  height: 'auto',
  padding: '15px',
  margin: '20px',
  transparent: false,
};

Default.parameters = {
  docs: {
    description: {
      component: `
      
      각 독립된 영역을 둘러싸는 Wrapper 컴포넌트입니다. 각 Card는 제목과 action slot을 가집니다.

    제목과 action이 둘 다 존재하지 않으면 헤더 부분은 렌더링되지 않습니다.

    footer영역은 slot을 추가하여 사용해야 합니다.
      `,
    },
    source: {
      language: 'md',
      code: `
<Card title="Hello World">
  <template #action>
    액션 영역
  </template>

  <div>콘텐츠 영역</div>

  <template #footer>
    푸터 영역
  </template>
</Card>
      `,
    },
  },
};

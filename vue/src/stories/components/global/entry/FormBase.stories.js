import { FormBase } from '@/component/global/entry';
import { FormBuilder, FormUtil } from '@/util';

export default {
  title: 'Entry/FormBase',
  component: FormBase,
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
    labelWidth: {
      description: '검색 Form의 label 너비를 조절합니다.',
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: '140px',
        },
      },
    },
    buttons: {
      table: {
        type: {
          summary: 'Array<ButtonBaseProps>',
        },
        defaultValue: {
          summary: '[]',
        },
      },
    },
    forms: {
      table: {
        type: {
          summary: 'Array<FormItemProps>',
        },
        defaultValue: {
          summary: '[]',
        },
      },
    },
    countPerRow: {
      description: '한 행에 몇 개의 Form Item을 나열할 지 정의합니다.',
      table: {
        type: {
          summary: 'Number',
        },
        defaultValue: {
          summary: '3',
        },
      },
    },
    onEnter: {
      action: '@enter',
      description: '폼 요소에서 엔터 입력 시 실행됩니다.',
      table: {
        type: {
          summary: '@enter',
          detail: 'Function',
        },
      },
    },
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { FormBase },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    computed: {
      formData() {
        return FormUtil.getData(this.args.forms);
      },
    },
    template: `
    <FormBase
      v-bind="args"
      @enter="onEnter"
    />
    {{ formData }}
    `,
  };
};

export const Default = Template.bind({});
Default.args = {
  countPerRow: 4,
  labelWidth: '140px',
  forms: FormBuilder.builder()
    .Input('name', '이름')
    .Datepicker('date', '일자')
    .spanRow(2)
    .multiple(
      'item',
      '항목',
      FormBuilder.builder()
        .Input('title')
        .spanCol(4)
        .Select('unit', {
          elements: [
            { value: 'g', label: 'g' },
            { value: 'kg', label: 'Kg' },
          ],
        })
        .spanCol(2)
        .Button('search', { label: '찾기' })
        .build(),
    )
    .spanCol(2)
    .build(),
};

Default.parameters = {
  docs: {
    description: {
      component: `
      
      `,
    },
    source: {
      language: 'md',
      code: `
<FormBase
  :countPerRow="3"
  :forms="FormBuilder.builder()
    .Input('name', '이름')
    .Datepicker('date', '일자')
    .spanRow(2)
    .multiple(
      'item',
      '항목',
      FormBuilder.builder()
      .Input('title')
      .spanCol(4)
      .Select('unit', {
        elements: [
          { value: 'g', label: 'g' },
          { value: 'kg', label: 'Kg' },
        ]
      })
      .spanCol(2)
      .Button('search', { label: '찾기' })
      .build()
    )
    .spanCol(2)
    .build()"
/>
      `,
    },
  },
};

const FormWithHeaderTemplate = (args, { argTypes }) => {
  return {
    components: { FormBase },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    computed: {
      formData() {
        return FormUtil.getData(this.args.forms);
      },
    },
    template: `
    <FormWithHeader
      v-bind="args"
    />
    {{ formData }}
    `,
  };
};

export const WithHeader = FormWithHeaderTemplate.bind({});
WithHeader.args = {
  title: 'Form 제목',
  countPerRow: 4,
  buttons: [
    { value: 'init', label: '초기화' },
    { value: 'save', label: '조회' },
  ],
  forms: FormBuilder.builder()
    .Input('name', '이름')
    .Datepicker('date', '일자')
    .spanRow(2)
    .multiple(
      'item',
      '항목',
      FormBuilder.builder()
        .Input('title')
        .spanCol(4)
        .Select('unit', {
          elements: [
            { value: 'g', label: 'g' },
            { value: 'kg', label: 'Kg' },
          ],
        })
        .spanCol(2)
        .Button('search', { label: '찾기' })
        .build(),
    )
    .spanCol(2)
    .build(),
};

WithHeader.parameters = {
  docs: {
    source: {
      language: 'md',
      code: `
<FormBase
  title="Form 제목"
  :buttons="[
    { value: 'init', label: '초기화' },
    { value: 'save', label: '조회' },
  ]"
  :forms="FormBuilder.builder()
    .Input('name', '이름')
    .Datepicker('date', '일자')
    .spanRow(2)
    .multiple(
      'item',
      '항목',
      FormBuilder.builder()
      .Input('title')
      .spanCol(4)
      .Select('unit', {
        elements: [
          { value: 'g', label: 'g' },
          { value: 'kg', label: 'Kg' },
        ]
      })
      .spanCol(2)
      .Button('search', { label: '찾기' })
      .build()
    )
    .spanCol(2)
    .build()"
/>
      `,
    },
  },
};

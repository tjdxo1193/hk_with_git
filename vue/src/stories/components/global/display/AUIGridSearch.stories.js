import { AUIGridSearch } from '@/component/global/display';
import { ColumnBuilder, FormBuilder, ObjectUtil } from '@/util';

export default {
  title: 'Display/AUIGridSearch',
  component: AUIGridSearch,
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
    countPerRow: {
      description: 'Form에서 한 행에 몇 개의 Form Item을 나열할 지 정의합니다.',
      table: {
        type: {
          summary: 'Number',
        },
        defaultValue: {
          summary: '3',
        },
      },
    },
    buttons: {
      description: '우측 상단에 표시될 Button을 정의한 배열',
      table: {
        type: {
          summary: 'Array<ButtonProps>',
        },
        defaultValue: {
          summary: 'null',
        },
      },
    },
    forms: {
      description: '그리드 상단에 표시될 Form을 정의한 배열',
      table: {
        type: {
          summary: 'Array<FormItemProps>',
        },
        defaultValue: {
          summary: 'null',
        },
      },
    },
    legends: {
      description: '범례',
      table: {
        type: {
          summary: 'Array<LegendBaseProps>',
        },
        defaultValue: {
          summary: 'null',
        },
      },
    },
    columns: {
      description: 'AUIGrid 컬럼 배열',
      table: {
        type: {
          summary: 'Array',
        },
        defaultValue: {
          summary: '[]',
        },
      },
    },
    props: {
      description: 'Grid Properties',
      table: {
        type: {
          summary: 'Object',
        },
        defaultValue: {
          summary: 'null',
        },
      },
      control: ' ',
    },
    defaultData: {
      description: 'Grid default data',
      table: {
        type: {
          summary: 'Array',
        },
        defaultValue: {
          summary: '[]',
        },
      },
    },
    event: {
      description: 'Grid event',
      table: {
        type: {
          summary: 'Object',
        },
        defaultValue: {
          summary: '{}',
        },
      },
    },
    width: {
      description: 'Grid Width',
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: '100%',
        },
      },
    },
    height: {
      description: 'Grid Height',
      table: {
        type: {
          summary: 'String',
        },
        defaultValue: {
          summary: '100%',
        },
      },
    },
    onGridCreated: {
      action: 'grid-created',
      description: '그리드가 생성된 후 (mounted) 실행됩니다.',
      table: {
        type: {
          summary: '@grid-created',
          detail: 'Function',
        },
      },
    },
    onButtonClick: {
      action: 'button-click',
      description: '버튼 클릭시 실행됩니다.',
      table: {
        type: {
          summary: '@button-click',
          detail: 'Function',
        },
      },
    },
    onFormEvent: {
      action: 'form-event',
      description: 'forms에서 이벤트가 발생할 때 실행됩니다.',
      table: {
        type: {
          summary: '@form-event',
          detail: 'Function',
        },
      },
    },
    onEnter: {
      action: 'enter',
      description: 'input, textarea 등에서 엔터를 입력하면 실행됩니다.',
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
    components: { AUIGridSearch },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <AUIGridSearch
      v-bind="args"
      @grid-created="onGridCreated"
      @button-click="onButtonClick"
      @form-event="onFormEvent"
      @enter="onEnter"
      @grid-button-click="gridButtonClick"
    />
    `,
    methods: {
      gridButtonClick() {
        alert('grid button click!');
      },
    },
  };
};

export const Default = Template.bind({});
const legends = [
  { dataField: 'status', value: '임시저장', className: 'tempSave' },
  { dataField: 'status', value: '시험중단', className: 'testStop' },
];
const comboList = [
  { code: '1', value: '선택1' },
  { code: '2', value: '선택2' },
  { code: '3', value: '선택3' },
];

Default.args = {
  title: 'AUIGrid',
  margin: '20px',
  labelWidth: '140px',
  legends,
  props: {
    showRowCheckColumn: true,
    rowStyleFunction: (rowIndex, item) => {
      const target = legends.find((legend) =>
        ObjectUtil.equals(item, legend.dataField, legend.value),
      );
      return target ? target.className : null;
    },
  },
  event: {
    cellClick: () => alert('click!'),
    cellEditEnd: () => alert('cellEditEnd'),
  },
  buttons: [
    { label: '초기화', value: 'init' },
    { label: '저장', value: 'save', type: 'warning' },
    { label: '조회', value: 'search' },
  ],
  forms: FormBuilder.builder()
    .Input('name', '이름')
    .Select('dept', '부서')
    .Datepicker('date', '등록일자')
    .DatepickerWithSwitch('conditionalDate', '의뢰일자', { value: '2022-07-15' })
    .RadioGroup('type', '종류', {
      groups: [
        { label: '기본', value: 1 },
        { label: '응용', value: 2 },
      ],
    })
    .build(),
  columns: ColumnBuilder.builder()
    .col('id', false)
    .calendar('calendar', '캘린더')
    .button('button', '버튼')
    .combo('combobox1', '콤보박스1', comboList)
    .col('title', 'Title', { width: 140 })
    .col('price', 'Price', { width: 120 })
    .col('status', 'Status', { width: 120 })
    .col('category', 'Category')
    .col('image', 'Image')
    .col('rating', 'Rating', {
      children: ColumnBuilder.builder().col('rate', 'Rate').col('count', 'Count').build(),
    })
    .build(),
  defaultData: [
    {
      title: '샘플 데이터 1',
      price: 100,
      status: '임시저장',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      status: '의뢰',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      status: '의뢰',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      status: '의뢰',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      status: '시험중단',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      status: '의뢰',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      status: '의뢰',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      status: '의뢰',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      status: '의뢰',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
  ],
};

Default.parameters = {
  docs: {
    page: null,
    description: {
      component: `
      
      검색 폼, 버튼 UI를 가진 AUIGrid 컴포넌트입니다.

    Legends 목록은 프로젝트의 @/assets/scss/legned.scss 를 참고하세요.
    `,
    },
    source: {
      language: 'md',
      code: `
<AUIGridSearch
  v-bind="product"
  @button-click="onClickButton"
  @form-event="onFormEvent"
  @grid-created="(proxy) => setState('product.$grid', proxy)"
/>

export default {
  data() {
    const { product, productModal } = values.auigrid; // 정적 값을 정의한 객체
    return {
      product: {
        title: 'AUIGrid',
        legends,
        props: {
          showRowCheckColumn: true,
          rowStyleFunction: (rowIndex, item) => {
            const target = legends.find((legend) =>
              ObjectUtil.equals(item, legend.dataField, legend.value),
            );
            return target ? target.className : null;
          },
        },
        event: {
          cellClick: () => alert('click!'),
        },
        buttons: [
          { label: '초기화', value: 'init' },
          { label: '저장', value: 'save', type: 'warning' },
          { label: '조회', value: 'search' }
        ],
        forms: FormBuilder.builder()
          .Input('name', '이름')
          .Select('dept', '부서')
          .Datepicker('date', '등록일자')
          .DatepickerWithSwitch('conditionalDate', '의뢰일자', { value: '2022-07-15' })
          .RadioGroup('type', '종류', {
            groups: [
              { label: '기본', value: 1 },
              { label: '응용', value: 2 },
            ]
          })
          .build(),
        columns: ColumnBuilder.builder()
          .col('id', false)
          .col('title', 'Title', { width: 140 })
          .col('price', 'Price', { width: 120 })
          .col('status', 'Status', { width: 120 })
          .col('category', 'Category')
          .col('image', 'Image')
          .col('rating', 'Rating', {
            children: ColumnBuilder.builder().col('rate', 'Rate').col('count', 'Count').build(),
          })
          .build(),
        defaultData: [
          { title: '샘플 데이터 1', price: 100, status: '임시저장', category: '카테고리', image: '이미지', rate: 4.3, count: 1  },
          { title: '샘플 데이터 1', price: 100, status: '의뢰', category: '카테고리', image: '이미지', rate: 4.3, count: 1  },
          { title: '샘플 데이터 1', price: 100, status: '의뢰', category: '카테고리', image: '이미지', rate: 4.3, count: 1  },
          { title: '샘플 데이터 1', price: 100, status: '의뢰', category: '카테고리', image: '이미지', rate: 4.3, count: 1  },
          { title: '샘플 데이터 1', price: 100, status: '시험중단', category: '카테고리', image: '이미지', rate: 4.3, count: 1  },
          { title: '샘플 데이터 1', price: 100, status: '의뢰', category: '카테고리', image: '이미지', rate: 4.3, count: 1  },
          { title: '샘플 데이터 1', price: 100, status: '의뢰', category: '카테고리', image: '이미지', rate: 4.3, count: 1  },
          { title: '샘플 데이터 1', price: 100, status: '의뢰', category: '카테고리', image: '이미지', rate: 4.3, count: 1  },
          { title: '샘플 데이터 1', price: 100, status: '의뢰', category: '카테고리', image: '이미지', rate: 4.3, count: 1  },
        ],
      },
    };
  },
  methods: {
    setState(keyChain, value) {
      ObjectUtil.setObject(this.$data, keyChain, value);
    },
    onClickButton({ name }) {
      if (name === 'init') {
        this.init();
      }
      if (name === 'save') {
        this.save();
      }
      if (name === 'search) {
        this.search();
      }
    },
    init() {
      // 초기화
    },
    save() {
      // 저장
    },
    search() {
      // 조회
    },
    onFormEvent(e) {
      const { type, originEvent, item } = e;
      // type 'input', 'change', 'focus' ...
      // item { name: 'name', label: '이름' ... }
    },
  },
};
`,
    },
  },
};

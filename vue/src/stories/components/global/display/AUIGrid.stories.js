import { AUIGrid } from '@/component/global/display';
import { ColumnBuilder } from '@/util';

export default {
  title: 'Display/AUIGrid',
  component: AUIGrid,
  argTypes: {
    title: {
      description: '그리드 제목',
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
      description: 'Grid 생성시 Option 객체',
      table: {
        type: {
          summary: 'Object',
        },
        defaultValue: {
          summary: 'null',
        },
      },
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
  },
};

const Template = (args, { argTypes }) => {
  return {
    components: { AUIGrid },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `<AUIGrid v-bind="args" @grid-created="onGridCreated" @grid-button-click="gridButtonClick" />`,
    methods: {
      gridButtonClick() {
        alert('grid button click!');
      },
    },
  };
};

const comboList = [
  { code: '1', value: '선택1' },
  { code: '2', value: '선택2' },
  { code: '3', value: '선택3' },
];

export const Default = Template.bind({});
Default.args = {
  title: 'AUIGrid',
  width: '100%',
  height: '100%',
  legends: [
    { value: '범례 값1', className: 'tempSave' },
    { value: '범례 값2', className: 'hold' },
  ],
  props: {
    showRowCheckColumn: true,
  },
  event: {
    cellClick: () => alert('click!'),
    cellEditEnd: () => alert('cellEditEnd'),
  },
  columns: ColumnBuilder.builder()
    .col('id', false)
    .calendar('calendar', '캘린더')
    .button('button', '버튼')
    .combo('combobox1', '콤보박스1', comboList)
    .col('title', 'Title', { width: 140 })
    .col('price', 'Price', { width: 120 })
    .col('description', 'Description', { width: 120 })
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
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
  ],
};

Default.parameters = {
  docs: {
    description: {
      component: `
    기본적인 AUIGrid 컴포넌트입니다.
    그리드가 생성된 후 발생하는 grid-created이벤트에서 proxy 객체를 인자로 받을 수 있습니다.
      
    proxy객체는 자체적으로 pid를 가지고 있어 pid를 인자로 넘기지 않고 관련 메서드를 사용할 수 있습니다.
    AUIGrid.getGridData(pid)        ->  proxy.getGridData()
    AUIGrid.setGridData(pid, data)  ->  proxy.setGridData(data)
    `,
    },
    source: {
      language: 'md',
      code: `
<AUIGrid
  title="title"
  legends="legends"
  columns="columns"
  props="props"
  defaultData="data"
  event="event"
  @grid-created="(proxy) => proxy"
/>`,
    },
  },
};

const GridWithHeaderTemplate = (args, { argTypes }) => {
  return {
    components: { AUIGrid },
    data() {
      return {
        args,
      };
    },
    props: Object.keys(argTypes),
    template: `
    <AUIGridWithHeader
      v-bind="args"
      @grid-created="onGridCreated"
      @grid-button-click="gridButtonClick"
    />`,
    methods: {
      gridButtonClick() {
        alert('grid button click!');
      },
    },
  };
};

export const WithHeader = GridWithHeaderTemplate.bind({});
WithHeader.args = {
  title: 'Grid 제목',
  margin: '20px',
  width: '100%',
  height: '100%',
  legends: [
    { value: '범례 값1', className: 'tempSave' },
    { value: '범례 값2', className: 'hold' },
  ],
  props: {
    showRowCheckColumn: true,
  },
  event: {
    cellClick: () => alert('click!'),
    cellEditEnd: () => alert('cellEditEnd'),
  },
  columns: ColumnBuilder.builder()
    .col('id', false)
    .calendar('calendar', '캘린더')
    .button('button', '버튼')
    .combo('combobox1', '콤보박스1', comboList)
    .col('title', 'Title', { width: 140 })
    .col('price', 'Price', { width: 120 })
    .col('description', 'Description', { width: 120 })
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
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
    {
      title: '샘플 데이터 1',
      price: 100,
      description: '설명',
      category: '카테고리',
      image: '이미지',
      rate: 4.3,
      count: 1,
    },
  ],
};

WithHeader.parameters = {
  docs: {
    description: {
      component: `
    기본적인 AUIGrid 컴포넌트입니다.
    그리드가 생성된 후 발생하는 grid-created이벤트에서 proxy 객체를 인자로 받을 수 있습니다.
      
    proxy객체는 자체적으로 pid를 가지고 있어 pid를 인자로 넘기지 않고 관련 메서드를 사용할 수 있습니다.
    AUIGrid.getGridData(pid)        ->  proxy.getGridData()
    AUIGrid.setGridData(pid, data)  ->  proxy.setGridData(data)
    `,
    },
    source: {
      language: 'md',
      code: `
<AUIGridWithHeader 
  title="title"
  legends="legends"
  columns="columns"
  props="props"
  defaultData="data"
  event="event" 
  @grid-created="(proxy) => proxy"
/>`,
    },
  },
};

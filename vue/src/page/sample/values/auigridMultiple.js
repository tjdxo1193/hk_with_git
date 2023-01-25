import { ColumnBuilder, FormBuilder } from '@/util';

const product = {
  static: {
    title: '제품',
    countPerRow: 5,
    $grid: null,
    props: {},
    legends: [],
    buttons: [
      { name: 'init' },
      { name: 'save', type: 'warn', label: '저장' },
      { name: 'select', type: 'danger', label: '조회' },
      { name: 'clear', type: 'clear' },
      { name: 'search', type: 'search' },
      { name: 'disabled', disabled: true, label: '비활성' },
      { name: 'disabled', type: 'normal', label: '노말' },
    ],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .col('title', 'password_check_error_10', { width: 140 })
      .col('price', 'Price', { width: 120 })
      .col('description', 'Description', { width: 120 })
      .col('category', 'Category')
      .col('image', 'Image', { dataType: 'numeric' })
      .col('rating', 'Rating', {
        children: ColumnBuilder.builder().col('rate', 'Rate').col('count', 'Count').build(),
      })
      .build(),
  forms: () =>
    FormBuilder.builder()
      .Textarea('dept', '부서')
      .required()
      .spanCol(2)
      .spanRow(2)
      .Select('div', '구분', {
        defaultOption: { label: '선택' },
        value: null,
      })
      .spanCol(3)
      .Slot('slotItem', '슬롯 아이템')
      .spanCol(3)
      .Textarea('category', '종류2', { checkedValue: 1, label: '우선3' })
      .spanCol(3)
      .Slot('slotItem2', '슬롯 아이템2')
      .spanRow(2)
      .spanCol(2)
      .multiple(
        'key',
        '부서',
        FormBuilder.builder().Textarea('dept2').required().spanCol(3).build(),
      )
      .spanCol(3)
      .build(),
};

const carts = {
  static: {
    title: '카트',
    props: {
      showRowCheckColumn: true,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .col('userId', 'User Id')
      .col('date', 'Title', { width: 140 })
      .col('products', 'Products', {
        children: ColumnBuilder.builder()
          .col('productId', 'Product Id')
          .col('quantity', 'Quantity')
          .build(),
      })
      .build(),
};

const item = {
  static: {
    title: '상세 정보',
    props: {
      showRowCheckColumn: true,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .col('userId', 'User Id')
      .col('date', 'Title', { width: 140 })
      .build(),
};

export default {
  product,
  carts,
  item,
};

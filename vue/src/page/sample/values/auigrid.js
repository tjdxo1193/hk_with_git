import api from '@/api';
import { FormBuilder, ColumnBuilder, ObjectUtil } from '@/util';

const legends = [
  { dataField: 'category', value: `men's clothing`, className: 'tempSave' },
  { dataField: 'rate', value: 4.6, className: 'nonconformity' },
  { dataField: 'price', value: 999.99, className: 'approveRequest' },
];

const product = {
  static: {
    title: 'AUIGrid 샘플',
    countperRow: 3,
    $grid: null,
    legends,
    props: {
      rowStyleFunction: (rowIndex, item) => {
        const target = legends.find((legend) =>
          ObjectUtil.equals(item, legend.dataField, legend.value),
        );
        return target ? target.className : null;
      },
    },
    buttons: [{ name: 'init', label: '초기화' }],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .col('title', 'Title', { width: 140 })
      .col('price', 'Price', { width: 120 })
      .col('description', 'Description', { width: 120 })
      .col('category', 'Category')
      .col('image', 'Image')
      .col('rating', 'Rating', {
        children: ColumnBuilder.builder().col('rate', 'Rate').col('count', 'Count').build(),
      })
      .build(),
  forms: () =>
    FormBuilder.builder()
      .Input('name', '이름', { value: '123123' })
      .required()
      .spanRow(2)
      .Datepicker('dept', '부서', { value: '2022-10-02' })
      .required()
      .spanCol(2)
      .TextView('abc', 'qe', { value: '123' })
      .Textarea('etc', '비고')
      .Select('div', '구분고르기', { async: api.combo.getSampleCombo })
      .disabled()
      .Radio('category', '종류', { checkedValue: 1, label: '우선' })
      .CheckboxGroup('cr', '계열 그룹', {
        value: ['one'],
        countPerRow: 3,
        space: 'none',
        gap: 50,
        groups: [
          { checkedValue: 'one', label: 'One' },
          { checkedValue: 'two', label: 'Two' },
          { checkedValue: 'three', label: 'Three' },
        ],
      })
      .build(),
};

const productModal = {
  forms: () =>
    FormBuilder.builder()
      .Hidden('_$uid')
      .TextView('title', 'Title')
      .TextView('price', 'Price')
      .TextView('description', 'Description')
      .TextView('category', 'Category')
      .TextView('image', 'Image')
      .TextView('rating', 'Rating')
      .TextView('rate', 'Rate')
      .TextView('count', 'Count')
      .build(),
};

export default {
  product,
  productModal,
};

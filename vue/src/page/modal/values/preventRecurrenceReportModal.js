import { ColumnBuilder, FormBuilder } from '@/util/builder';

const searchForm = {
  static: {
    $grid: null,
    props: {
      editable: false,
    },
    countPerRow: 3,
    buttons: [{ name: 'select', label: '조회' }],
    height: '180px',
  },
  forms: () =>
    FormBuilder.builder()
      .Input('batchNo', '배치번호')
      .Input('phsOrderNo', '구매오더번호')
      //(주의) lotNo makNo
      .Input('lotNo', '제조번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('batchNo', '배치번호')
      .col('phsOrderNo', '구매오더번호')
      .col('makNo', '제조번호')
      .col('name', '파일명')
      .col('crtDs', '수신일시')
      .build(),
};

const detail = {
  static: {
    countPerRow: 2,
  },
  forms: () => FormBuilder.builder().Slot('dropzone', '첨부파일').spanCol(2).build(),
};

export default {
  searchForm,
  detail,
};

import { ColumnBuilder, FormBuilder } from '@/util/builder';

const searchForm = {
  static: {
    $grid: null,
    props: {
      editable: false,
    },
    countPerRow: 4,
    buttons: [{ name: 'select', label: '조회' }],
    height: '180px',
  },
  forms: () =>
    FormBuilder.builder()
      .Select('rptDiv', '성적서구분')
      .Input('batchNo', '배치번호')
      .Input('makNo', '제조번호')
      .Input('fileName', '파일명')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('batchNo', '배치번호')
      .col('rptDiv', '성적서구분')
      .col('phsOrderNo', '구매오더번호')
      .col('makNo', '제조번호')
      .col('fileName', '파일명')
      .col('crtDs', '수신일시')
      .col('ifInfoIdx', '연계정보ID')
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

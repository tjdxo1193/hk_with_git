import { ColumnBuilder, FormBuilder } from '@/util/builder';

const list = {
  static: {
    $grid: null,
    props: { editable: false },
    height: '150px',
    buttons: [{ name: 'save', label: '저장' }],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('fileIdx', false)
      .col('fileSrlno', '파일순번')
      .col('name', '파일명')
      .col('udtUid', false)
      .col('udtNm', '등록자')
      .col('udtDs', '등록일')
      .build(),
};

const detail = {
  static: {
    countPerRow: 2,
  },
  forms: () => FormBuilder.builder().Slot('dropzone', '첨부파일').spanCol(2).build(),
};

export default {
  list,
  detail,
};

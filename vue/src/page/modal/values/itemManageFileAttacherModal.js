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
      .col('ctId', false)
      .col('ctSeq', false)
      .col('fileName', '파일명')
      .col('crtDs', false)
      .col('crtDt', '최초등록일')
      .col('udtDs', false)
      .col('udtDt', '등록일')
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

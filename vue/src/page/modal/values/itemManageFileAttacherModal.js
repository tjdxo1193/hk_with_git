import { ColumnBuilder, FormBuilder } from '@/util/builder';

const list = {
  static: {
    $grid: null,
    countPerRow: 2,
    title: 'CT성적서 목록',
    props: { editable: false },
    height: '150px',
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () => FormBuilder.builder().Input('pitmCd', '자재번호').spanCol(2).build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ctId', false)
      .col('ctSeq', false)
      .col('pitmCd', '자재번호')
      .col('fileName', '파일명')
      .col('crtDs', false)
      .col('crtDt', '최초등록일')
      .col('udtDs', false)
      .col('udtDt', '등록일')
      .build(),
};

const detail = {
  static: {
    title: '첨부파일',
    countPerRow: 2,
    buttons: [{name: 'save', label: '저장' }],
  },
  forms: () => FormBuilder.builder().Slot('dropzone', '첨부파일').spanCol(2).build(),
};

export default {
  list,
  detail,
};

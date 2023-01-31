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
      .Input('pitmNm', '품목명', { readonly: true })
      .Input('pitmCd', '품목코드')
      .Input('fileName', '파일명')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmCd', '품목코드')
      .col('ver', '품목버전')
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

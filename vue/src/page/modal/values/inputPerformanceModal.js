import { ColumnBuilder, FormBuilder } from '@/util/builder';

const searchForm = {
  static: {
    $grid: null,
    props: {
      editable: false,
    },
    countPerRow: 3,
    height: '480px',
    buttons: [{ name: 'select', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('phsOrderNo')
      .Hidden('phsOrderItm')
      .Hidden('lotNo')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmNm', '부적합품질검사요청번호')
      .col('zexfield1', '추가필드1')
      .col('zexfield2', '추가필드2')
      .col('fileName', '추가필드3')
      .col('crtDs', '추가필드4')
      .col('crtDs', '추가필드5')
      .col('crtDs', '수신일시')
      .col('ifInfoIdx', '연계정보ID')
      .build(),
};

export default {
  searchForm,
};

import { ColumnBuilder } from '@/util/builder';

const searchForm = {
  static: {
    $grid: null,
    props: {
      editable: false,
    },
    countPerRow: 3,
    buttons: [{ name: 'select', label: '조회' }],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('ispReqNo', '품질검사요청번호')
      .col('ispReqNoBlk', '부적합품질검사요청번호')
      .col('phsOrderTyp', '구매오더유형')
      .col('zexfield1', '추가필드1')
      .col('zexfield2', '추가필드2')
      .col('zexfield3', '추가필드3')
      .col('zexfield4', '추가필드4')
      .col('zexfield5', '추가필드5')
      .col('crtDs', '수신일시')
      .col('ifInfoIdx', '연계정보ID')
      .build(),
};

export default {
  searchForm,
};

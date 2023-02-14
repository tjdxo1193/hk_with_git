import { ColumnBuilder, FormBuilder } from '@/util/builder';

const searchGrid = {
  static: {
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
    countPerRow: 2,
    buttons: [
      { name: 'search', label: '조회' },
      { name: 'select', label: '선택' },
    ],
  },
  forms: () =>
    FormBuilder.builder().Input('aitmKn', '시험항목명').Input('vriaKn', 'VARIANT').build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('aitmCd', '시험항목코드', false)
      .col('amitmCd', '시험항목별방법코드')
      .col('aitmKn', '시험항목국문')
      .col('vriaNo', 'VARIANT NO')
      .col('vriaKn', 'VARIANT 국문')
      .col('aitmAbbr', '시험항목약어')
      .col('aitmEn', '시험항목영문')
      .col('vriaEn', 'VARIANT 영문')
      .col('rmk', '비고')
      .col('useYn', 'VARIANT 영문', false)
      .build(),
};

export default {
  searchGrid,
};

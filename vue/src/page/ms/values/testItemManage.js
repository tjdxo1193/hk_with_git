import { FormBuilder, ColumnBuilder } from '@/util';

const testItemSearch = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('aitmCd', '시험항목코드', { maxLength: 50 })
      .Input('aitmKn', '항목명 국문', { maxLength: 50 })
      .Input('aitmEn', '항목명 영문', { maxLength: 50 })
      .Input('aitmAbbr', '시험항목 약어', { maxLength: 50 })
      .build(),
};

const testItemGrid = {
  static: {
    title: '조회 결과',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('aitmCd', '시험항목코드')
      .col('aitmKn', '항목명 국문')
      .col('aitmEn', '항목명 영문')
      .col('aitmAbbr', '약어', false)
      .build(),
};

const selectedDataInfo = {
  static: {
    title: '시험항목정보',
    countPerRow: 4,
    buttons: [
      { name: 'save', label: '저장' },
      { name: 'reset', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('aitmCd', '시험항목코드', { maxLength: 6 })
      .required()
      .Input('aitmKn', '항목명 국문', { maxLength: 50 })
      .required()
      .Input('aitmEn', '항목명 영문', { maxLength: 50 })
      .Input('aitmAbbr', '시험항목 약어', { maxLength: 50 })
      .build(),
};

export default {
  testItemSearch,
  testItemGrid,
  selectedDataInfo,
};

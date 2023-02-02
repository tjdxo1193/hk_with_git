import {ColumnBuilder, FormBuilder} from '@/util';

const testItemSearch = {
  static: {
    title: '조회',
    countPerRow: 2,
    buttons: [{ name: 'search', label: '조회' }],
    height: '145px',
  },
  forms: () =>
    FormBuilder.builder()
      .Input('aitmKn', '항목명 국문', { maxLength: 50 })
      .Input('aitmEn', '항목명 영문', { maxLength: 50 })
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
      .col('aitmCd', '시험항목코드', false)
      .col('aitmKn', '항목명 국문')
      .col('aitmEn', '항목명 영문')
      .col('aitmAbbr', '약어', false)
      .build(),
};

const testItemPerSearch = {
  static: {
    title: '조회',
    countPerRow: 2,
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('vriaKn', 'Variant 국문', { maxLength: 50 })
      .Input('vriaEn', 'Variant 영문', { maxLength: 50 })
      .RadioGroup('useYn', '사용여부', {
        value: '',
        groups: [
          { checkedValue: '', label: '전체' },
          { checkedValue: 'Y', label: '사용함' },
          { checkedValue: 'N', label: '사용안함' },
        ],
        gap: 50,
      })
      .spanCol(2)
      .Hidden('aitmCd')
      .build(),
};

const testItemPerGrid = {
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
      .col('vriaNm', 'Variant Name', false)
      .col('vriaNo', 'Variant No')
      .col('vriaKn', 'Variant 국문')
      .col('vriaEn', 'Variant 영문')
      .col('amitmCd', '시험방법코드')
      .col('aitmCd', '시험항목코드', false)
      .col('rmk', '비고', false)
      .build(),
};

const vriaNoList = [
  { value: '01', label: '01' },
  { value: '02', label: '02' },
  { value: '03', label: '03' },
  { value: '04', label: '04' },
  { value: '05', label: '05' },
  { value: '06', label: '06' },
  { value: '07', label: '07' },
  { value: '08', label: '08' },
  { value: '09', label: '09' },
  { value: '10', label: '10' },
  { value: '11', label: '11' },
  { value: '12', label: '12' },
];

const testItemPerInfo = {
  static: {
    title: '시험방법정보',
    countPerRow: 2,
    buttons: [
      { name: 'elnSend', label: 'ELN전송' },
      { name: 'save', label: '저장' },
      { name: 'reset', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('aitmCd')
      .Input('amitmCd', '시험방법코드', { maxLength: 8 })
      .readonly()
      .Hidden('aitmCd', '시험항목코드', { maxLength: 6 })
      .Hidden('aitmKn', '항목명 국문', { maxLength: 50 })
      .Hidden('aitmEn', '항목명 영문', { maxLength: 50 })
      .Select('vriaNo', 'Variant No', {
        elements: vriaNoList,
      })
      .required()
      .Input('vriaKn', 'Variant 국문', { maxLength: 50 })
      .required()
      .Input('vriaEn', 'Variant 영문', { maxLength: 50 })
      .Input('rmk', '비고', { maxLength: 50 })
      .RadioGroup('useYn', '사용여부', {
        value: 'Y',
        groups: [
          { checkedValue: 'Y', label: 'Y' },
          { checkedValue: 'N', label: 'N' },
        ],
        gap: 30,
      })
      .build(),
};

export default {
  testItemSearch,
  testItemGrid,
  testItemPerSearch,
  testItemPerGrid,
  testItemPerInfo,
};

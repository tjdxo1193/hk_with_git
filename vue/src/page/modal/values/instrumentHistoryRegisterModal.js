import { ColumnBuilder } from '@/util';

const detail = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    props: {
      showRowCheckColumn: true,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('eqmCd', false)
      .col('hisSeq', false)
      // .col('eqmDiv', false)
      .col('eqmDivNm', '기기분류', { editable: false })
      // .col('eqmHisDiv', false)
      .col('eqmHisDivNm', '이력구분')
      .col('sapAspNo', '자산번호', { editable: false })
      .col('eqmNm', '기기명', { width: 120 })
      // .col('makComp', '제조회사')
      // .col('splComp', '공급회사')
      .col('modNm', '모델명')
      .col('srlNo', '시리얼넘버')
      // .col('crgUid', false)
      // .col('crgNm', '담당자')
      // .col('IQ', 'IQ')
      // .col('OQ', 'OQ')
      // .col('PQ', 'PQ')
      // .col('chkCyl', '점검주기')
      // .col('quaCyl', 'Qualification주기', { width: 120 })
      // .col('calCyl', 'Calibration주기', { width: 120 })
      // .col('eqmCrst', false)
      // .col('eqmCrstNm', '기기현황')
      // .col('rgtUid', false)
      // .col('rgtNm', '작성자')
      // .col('rgtDt', '작성일')
      .col('ansDt', '시험일')
      .col('rmk', '비고')
      .build(),
};

const buttons = [
  { name: 'register', label: '등록' },
  { name: 'delete', label: '삭제', type: 'danger' },
];

export default {
  detail,
  buttons,
};

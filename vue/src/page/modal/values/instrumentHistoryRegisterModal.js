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
      .col('eqmDiv', false)
      .col('eqmHisDiv', false)
      .col('hisSeq', false)
      // .col('기기분류', '기기분류', { editable: false })
      // .col('이력구분', '이력구분', { editable: false })
      // .col('자산번호', '자산번호', { editable: false })
      // .col('기기명', '기기명', { width: 120 })
      // .col('모델명', '모델명', { editable: false })
      // .col('시리얼넘버', '시리얼넘버', { editable: false })
      // .col('시험일', '시험일')
      // .col('비고', '비고')
      .col('eqmDivNm', '기기분류', { editable: false })
      .col('eqmHisDivNm', '이력구분', { editable: false })
      .col('sapAspNo', '자산번호', { editable: false })
      .col('eqmNm', '기기명', { width: 120 })
      .col('modNm', '모델명', { editable: false })
      .col('srlNo', '시리얼넘버', { editable: false })
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

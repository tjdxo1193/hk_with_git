import { ColumnBuilder } from '@/util/builder';

const searchForm = {
  static: {
    $grid: null,
    countPerRow: 4,
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('ispPdtPfaIdx', false)
      .col('ispPhsPfaIdx', false)
      .col('srlNo', false)
      .col('ifMtdDiv', false)
      .col('ifMtdDivNm', '연계 방법 구분')
      .col('ifStt', false)
      .col('ifSttNm', '연계 상태')
      .col('hoprIfUid', false)
      .col('hoprIfNm', '연계자 명')
      .col('ifDate', '연계 일시')
      .col('ifInfoIdx', false)
      .col('degree', '수신차수')
      .col('ispPfaCanlYn', '검사 실적 취소 여부')
      .build(),
};

export default {
  searchForm,
};

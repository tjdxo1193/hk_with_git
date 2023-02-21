import { ColumnBuilder, FormBuilder } from '@/util/builder';

const searchForm = {
  static: {
    $grid: null,
    props: {
      editable: false,
      flat2tree: true,
      treeIdField: 'inpBatchNo',
      treeIdRefField: 'batchNo',
      displayTreeOpen: true,
    },
    countPerRow: 3,
    height: '430px',
    buttons: [{ name: 'select', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder().Input('pitmCd', '자재번호').Input('batchNo', '배치번호').build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('mtrNm', '자재내역', { width: 150 })
      .col('inpMtrCd', '자재번호')
      .col('inpBatchNo', '배치번호')
      .col('phsOrderItm', '구매오더항목')
      .col('phsOrderNo', '구매오더번호')
      .col('pdtOrderNo', '생산오더번호')
      .col('lotNo', '제조번호')
      .col('budat', '입고일자')
      .col('erfmg', '투입실적수량')
      .col('erfme', '입력단위')
      .col('ispReqNo', '검사요청번호')
      .col('ansNo', '시험번호')
      .col('ansProcNm', '진행상태')
      .col('sytJdgNm', '판정결과')
      .col('zexfield1', '입고유형')
      .col('bwart', '이동유형(재고관리)')
      .col('lgort', '저장위치')
      .col('zexfield2', '입고취소여부')
      .col('zexfield3', '전표생성일')
      .col('zexfield4', '추가필드4')
      .col('zexfield5', '추가필드5')
      .col('mrpMng', 'MRP관리자')
      .col('mtrCd', false)
      .col('batchNo', false)
      .build(),
};

export default {
  searchForm,
};

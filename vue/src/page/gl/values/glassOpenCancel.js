import { ColumnBuilder, FormBuilder } from '@/util';

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [
      { name: 'fileSave', label: '파일등록', disabled: true },
      { name: 'cancel', label: '개봉취소' },
      { name: 'search', label: '조회' },
    ],
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('ritmKn', '자재내역')
      .Input('makNo', '제조번호')
      .Input('vdrNm', '제조처')
      .Input('mngNo', '관리번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '자재내역')
      .col('mngProcCd', false)
      .col('mngProcNm', '진행단계')
      .calendar('opnDt', '개봉일')
      .calendar('expirDt', '개봉 후 사용기한')
      .col('opnRmk', '개봉비고')
      .col('mngNo', '관리번호')
      .col('ritmEtrNo', '입고번호')
      .col('ritmLabelNo', '라벨번호')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('makNo', '제조번호')
      .col('etrDt', '입고일')
      .col('expirDtChk', false)
      .col('opnBefExpirDt', '유효기한')
      .col('opnAftExpirTrm', '개봉 후 유효기간')
      .col('etrQty', '입고량')
      .col('etrEachQty', '개별수량')
      .col('ritmUnitNm', '개별수량단위')
      .col('etrTotQty', '총수량')
      .col('ritmUnitNm', '총수량단위')
      .col('sfyStok', '안전재고량')
      .col('ritmUnitNm', '안전재고단위')
      .col('glsVol', '용량')
      .col('glsVolUnitCd', false)
      .col('glsVolUnitNm', '용량단위')
      .col('glsColrMtr', '색상/재질')
      .col('ritmCrgUid', false)
      .col('ritmCrgNm', '담당자')
      .col('etrRmk', '비고')
      .col('fileIdx', false)
      .build(),
};

export default {
  list,
};

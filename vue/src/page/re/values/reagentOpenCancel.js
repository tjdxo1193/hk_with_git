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
      .col('mngNo', '관리번호')
      .col('ritmEtrNo', '입고번호')
      .col('casNo', 'CASNO')
      .col('ritmLabelNo', '라벨번호')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('makNo', '제조번호')
      .col('makDt', '제조일')
      .col('etrDt', '입고일')
      .col('expirDtChk', false)
      .col('opnBefExpirDt', '개봉 전 사용기한')
      .col('opnAftExpirTrm', '개봉 후 사용기간')
      .col('currLotYn', 'Current LOT')
      .col('strgTerms', false)
      .col('strgTermsNm', '보관조건')
      .col('strgPla', false)
      .col('strgPlaNm', '보관장소')
      .col('strgPlaDtl', '세부보관장소')
      .col('fomu', '화학식')
      .col('regRnk', '등급')
      .col('regVol', '용량')
      .col('regVolUnitCd', false)
      .col('regVolUnitNm', '용량단위')
      .col('regCtet', '순도(함량)')
      .col('regCtetUnitCd', false)
      .col('regCtetUnitNm', '순도(함량)단위')
      .col('etrQty', '용기수')
      .col('ritmCrgUid', false)
      .col('ritmCrgNm', '담당자')
      .col('etrEachQty', '개별수량')
      .col('ritmUnitNm', '개별수량단위')
      .col('etrTotQty', '총수량')
      .col('ritmUnitNm', '총수량단위')
      .col('sfyStok', '안전재고량')
      .col('ritmUnitNm', '안전재고단위')
      .col('regFact', 'Factor')
      .col('etrRmk', '비고')
      .col('fileIdx', false)
      .col('leftOverStok', false)
      .build(),
};

export default {
  list,
};

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [
      { name: 'fileSave', label: '첨부파일', disabled: true },
      { name: 'approve', label: '승인' },
      { name: 'reject', label: '반려' },
      { name: 'search', label: '조회' },
    ],
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperRitmTreeCd', '구분', {
        async: () => api.combo.common.getTestMaterialTreeCd('R3000000'),
      })
      .Input('ritmKn', '품명')
      .Input('makNo', '제조번호')
      .Input('mngNo', '관리번호')
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .Input('vdrNm', '제조처')
      .blank()
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '품명')
      .col('mngProcCd', false)
      .col('mngProcNm', '진행단계')
      .calendar('opnDt', '개봉일')
      .calendar('expirDt', '개봉 후 사용기한')
      .col('opnRmk', '개봉비고')
      .col('dpsDt', '폐기요청일')
      .col('dpsUid', false)
      .col('dpsNm', '폐기요청자')
      .col('dpsRea', '폐기사유')
      .col('mngNo', '관리번호')
      .col('ritmEtrNo', '입고번호')
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
      .col('spec', '규격')
      .col('stdMois', '수분')
      .col('stdCtetCd', '함량종류')
      .col('stdCtetVal', '함량')
      .col('ritmUnitCd', false)
      .col('ritmUnitNm', '함량단위')
      .col('stdAsisCtetNm', '함량(As is)종류')
      .col('stdAsisCtetVal', '함량(As is)')
      .col('ritmUnitNm', '함량(As is) 단위')
      .col('etrQty', '용기수')
      .col('etrEachQty', '용량')
      .col('ritmUnitNm', '용량단위')
      .col('etrTotQty', '총수량')
      .col('ritmUnitNm', '총수량단위')
      .col('sfyStok', '안전재고량')
      .col('ritmUnitNm', '안전재고단위')
      .col('ritmCrgUid', false)
      .col('ritmCrgNm', '담당자')
      .col('shadeYn', '차광여부')
      .col('mtrDedutYn', '물질차감여부')
      .col('etrRmk', '비고')
      .col('stdNb', '주의사항')
      .col('fileIdx', false)
      .col('dpsReqAprIdx', false)
      .build(),
};

export default {
  list,
};

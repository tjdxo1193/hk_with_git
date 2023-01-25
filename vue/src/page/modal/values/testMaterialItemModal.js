import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const searchForm = {
  static: {
    $grid: null,
    props: { editable: false },
    countPerRow: 4,
    buttons: [{ name: 'select', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperRitmTreeCd', '구분', {
        async: api.combo.common.getTestMaterialTreeCd,
      })
      .Input('ritmKn', '품명')
      .Input('mngNo', '관리번호')
      .Input('casNo', 'CASNO')
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .blank()
      .spanCol(3)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmCd', '일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmTreeCd', false)
      .col('ritmTreeNm', '재료구분')
      .col('ritmKn', '품명')
      .col('mngNo', '관리번호')
      .col('spec', '규격')
      .col('ritmUnitCd', false)
      .col('ritmUnitNm', '단위')
      .col('opnAftExpirTrm', '사용기간')
      .col('sfyStok', '안전재고량')
      .col('ritmEn', '영문명')
      .col('ritmCrgUid', false)
      .col('ritmCrgNm', '담당자')
      .col('casNo', 'CASNO')
      .col('strgTerms', false)
      .col('strgTermsNm', '보관조건')
      .col('strgPla', false)
      .col('strgPlaNm', '보관장소')
      .col('strgPlaDtl', '세부보관장소')
      .col('shadeYn', '차광여부')
      .col('mtrDedutYn', '물질차감여부')
      .col('poisYn', '유독물여부')
      .col('dangYn', '위험물여부')
      .col('useYn', '사용여부')
      .col('ritmRmk', '비고')
      .build(),
};

export default {
  searchForm,
};

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
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .Input('mngNo', '관리번호')
      .Input('ritmKn', '자재내역')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmCd', '일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '자재내역')
      .col('opnDt', '개봉일')
      .col('mngNo', '관리번호')
      .col('ritmEtrNo', '입고번호')
      .col('ritmLabelNo', '라벨번호')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처')
      .col('makNo', '제조번호')
      .col('makDt', '제조일')
      .col('etrDt', '입고일')
      .col('expirDt', '사용(유효)기간')
      .col('makNo', '제조번호')
      .col('leftOverStok', '남은양')
      .col('ritmUnitCd', false)
      .col('ritmUnitNm', '단위')
      .col('sfyStok', '안전재고량')
      .col('ritmUnitNm', '안전재고단위')
      .col('ancColNo', false)
      .col('ancPitmNm', '자재내역')
      .col('ancAitmNm', '시험항목정보')
      .col('strgTerms', false)
      .col('strgTermsNm', '보관조건')
      .col('strgPla', false)
      .col('strgPlaNm', '보관장소')
      .col('strgPlaDtl', '세부보관장소')
      .col('opnRmk', '개봉비고')
      .build(),
};

export default {
  searchForm,
};

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [
      { name: 'fileSave', label: '파일등록', disabled: true },
      { name: 'print', label: '출력' },
      { name: 'search', label: '조회' },
    ],
    props: {
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperRitmTreeCd', '구분', {
        async: () => api.combo.common.getTestMaterialTreeCd('R2000000'),
      })
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .Input('ritmKn', '자재내역')
      .Input('mngNo', '관리번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmCd', false)
      .col('ritmEtrNo', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '자재내역')
      .col('mngNo', '관리번호')
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
      .col('etrProcCd', false)
      .col('fileIdx', false)
      .col('mngProcCd', false)
      .col('mngProcNm', '라벨출력여부')
      .col('fileIdx', false)
      .build(),
};

export default {
  list,
};

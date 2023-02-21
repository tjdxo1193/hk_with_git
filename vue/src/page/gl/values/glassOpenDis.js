import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [
      { name: 'fileSave', label: '파일등록', disabled: true },
      { name: 'openItem', label: '개봉' },
      { name: 'disposalItem', label: '폐기', disabled: true },
      { name: 'search', label: '조회' },
    ],
    props: {
      showRowCheckColumn: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperRitmTreeCd', '구분', {
        async: () => api.combo.common.getTestMaterialTreeCd('R2000000'),
      })
      .Input('ritmKn', '자재내역')
      .Input('mngNo', '관리번호')
      .Input('makNo', '제조번호')
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .RadioGroup('mngProcCd', '진행구분', {
        value: 'S0020100',
        groups: [
          { checkedValue: 'S0020100', label: '개봉' },
          { checkedValue: 'S0020200', label: '폐기요청' },
        ],
        gap: 50,
      })
      .blank()
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmCd', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '자재내역')
      .col('mngProcCd', false)
      .col('mngProcNm', '진행단계')
      .calendar('opnDt', '개봉일')
      .calendar('expirDt', '개봉 후 사용기한')
      .col('opnRmk', '개봉비고', {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 50,
        },
      })
      .calendar('dpsDt', '폐기일')
      .col('dpsRea', '폐기사유', false, {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 50,
        },
      })
      .col('mngNo', '관리번호')
      .col('ritmLabelNo', '라벨번호')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('makNo', '제조번호')
      .col('etrDt', '입고일')
      .col('expirDtChk', false)
      .col('opnBefExpirDt', '개봉 전 사용기한')
      .col('opnAftExpirTrm', '개봉 후 사용기간')
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

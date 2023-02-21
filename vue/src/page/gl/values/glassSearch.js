import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'init', label: '초기화' },
      { name: 'search', label: '조회' },
    ],
    $grid: null,
    legends: [{ value: '안전재고량이하', className: 'lessThanStock' }],
    props: {
      editable: false,
      rowStyleFunction: function (rowIndex, item) {
        if (item.sfyStok != null && item.sfyStok >= item.leftOverStok) {
          return 'lessThanStock';
        }
        return null;
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperRitmTreeCd', '구분', {
        async: () => api.combo.common.getTestMaterialTreeCd('R2000000'),
      })
      .Input('ritmKn', '자재내역')
      .Input('mngNo', '관리번호')
      .Select('', '조회일자구분')
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .Input('vdrNm', '제조처')
      .DatepickerTwinWithSwitch('', '조회기한', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .Input('pitmNm', '자재내역')
      .Input('makNo', '제조번호')
      .DatepickerTwinWithSwitch('', '사용(유효)기한', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .Select('', '상태')
      .blank()
      .spanCol(3)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('fileIdx', false)
      .button('fileAttacher', '첨부파일')
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '자재내역')
      .col('mngNo', '관리번호')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('makNo', '제조번호')
      .col('etrDt', '입고일')
      .col('opnBefExpirDt', '유효기한')
      .col('opnAftExpirTrm', '개봉 후 유효기간')
      .col('ritmUnitCd', false)
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
      .col('etrProcCd', false)
      .col('etrProcNm', '진행단계')
      .build(),
};

const detailList = {
  static: {
    $grid: null,
    props: {
      editable: false,
      rowStyleFunction: function (rowIndex, item) {
        if (item.mngProcCd === 'S0020300') {
          return 'disposal';
        }
        return null;
      },
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmMngIdx', false)
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('mngProcCd', false)
      .col('mngProcNm', '진행상태')
      .col('ritmKn', '자재내역')
      .col('opnDt', '개봉일')
      .col('expirDt', '유효기간')
      .col('dpsDt', '폐기일')
      .col('dpsRmk', '폐기사유')
      .col('mngNo', '관리번호')
      .col('ritmEtrNo', '입고번호')
      .col('ritmLabelNo', '라벨번호')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('makNo', '제조번호')
      .col('etrDt', '입고일')
      .col('makNo', '제조번호')
      .col('glsVol', '용량')
      .col('glsVolUnitCd', false)
      .col('glsVolUnitNm', '용량단위')
      .col('glsColrMtr', '색상/재질')
      .col('ritmUnitCd', false)
      .col('etrQty', '입고량')
      .col('etrEachQty', '개별수량')
      .col('ritmUnitNm', '개별수량단위')
      .col('etrTotQty', '총수량')
      .col('ritmUnitNm', '총수량단위')
      .col('sfyStok', '안전재고량')
      .col('ritmUnitNm', '안전재고단위')
      .col('etrRmk', '비고')
      .build(),
};

export default {
  list,
  detailList,
};

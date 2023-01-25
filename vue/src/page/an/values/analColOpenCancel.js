import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperRitmTreeCd', '구분', {
        async: () => api.combo.common.getTestMaterialTreeCd('R4000000'),
      })
      .Input('ritmKn', '품명')
      .Input('vdrNm', '제조처')
      .Input('ancCatNo', 'Part or Cat No')
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .Input('ancLen', '길이')
      .Input('ancFpak', '충진물')
      .Input('ancInsDimt', '내경')
      .DatepickerTwinWithSwitch('searchEtrDt', '입고일', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .blank()
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmMngIdx', false)
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '품명')
      .col('mngProcCd', false)
      .col('mngProcNm', '진행단계')
      .col('ritmLabelNo', '라벨번호')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('ancCatNo', 'Part or Cat No')
      .col('ancSrlNo', 'Serial No')
      .col('ancLen', '길이')
      .col('ancLenUnitCd', false)
      .col('ancLenUnitNm', '길이단위')
      .col('ancInsDimt', '내경')
      .col('ancPatcsize', '입자크기')
      .col('ancUspCd', 'USP코드')
      .col('ancFpak', '충진물')
      .col('etrDt', '입고일')
      .col('ancEtrNo', '입고번호')
      .col('etrQty', '수량')
      .col('ancColNo', '컬럼번호')
      .col('ancPitmDivCd', false)
      .col('ancPitmDivNm', '품목구분')
      .col('ancPitmNm', '품목명')
      .col('ancAitmNm', '시험항목정보')
      .col('opnDt', '개봉일')
      .col('ancInitAns', '최초분석')
      .col('ancRes', 'Resolution')
      .col('ancTai', 'Tailing')
      .col('ancThe', '이론단수')
      .col('analCnt', '분석횟수')
      .col('fileIdx', false)
      .build(),
};

const detailColumn = {
  static: {
    title: '컬럼정보',
    countPerRow: 2,
  },
  forms: () =>
    FormBuilder.builder()
      .Input('upperRitmTreeNm', '구분')
      .Input('ancSrlNo', 'Serial No')
      .Input('ritmRootNm', '재료구분')
      .Input('ancLen', '길이')
      .Input('vdrNm', '제조처')
      .Input('ancInsDimt', '내경')
      .Input('ritmKn', '품명')
      .Input('ancPatcsize', '입자크기')
      .Input('ancCatNo', 'Part or Cat No')
      .Input('ancUspCd', 'USP코드')
      .build()
      .map((col) => ({
        ...col,
        disabled: true,
      })),
};

const detailInfo = {
  static: {
    title: '입력정보',
    countPerRow: 2,
  },
  forms: () =>
    FormBuilder.builder()
      .Input('ancColNo', '컬럼번호')
      .Input('etrQty', '수량')
      .Select('ancPitmDivCd', '품목구분', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Datepicker('opnDt', '개봉일', { value: todayDate })
      .Input('ancPitmNm', '품목명')
      .Input('ancAitmNm', '시험항목정보')
      .Input('ancInitAns', '최초분석')
      .Input('ancTai', 'Tailing')
      .Input('ancRes', 'Resolution')
      .Input('ancThe', '이론단수')
      .Hidden('ritmEtrIdx')
      .Hidden('ritmMngIdx')
      .Hidden('mngProcCd')
      .Hidden('analCnt')
      .Hidden('fileIdx', { value: 0 })
      .build()
      .map((col) => ({
        ...col,
        disabled: true,
      })),
};

const buttonGroups = {
  buttons: [
    { name: 'fileSave', label: '파일등록', disabled: true },
    { name: 'cancel', label: '개봉취소', disabled: true },
    { name: 'init', label: '초기화' },
  ],
};

export default {
  list,
  detailColumn,
  detailInfo,
  buttonGroups,
};

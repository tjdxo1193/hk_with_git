import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

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
        async: () => api.combo.common.getTestMaterialTreeCd('R4000000'),
      })
      .Input('ritmKn', '자재내역')
      .Input('vdrNm', '제조처')
      .Input('ancFpak', '충진물')
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .Input('ancLen', '길이')
      .Input('ancInsDimt', '내경')
      .Input('ancCatNo', 'Part or Cat No')
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
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '자재내역')
      .col('mngProcCd', false)
      .col('mngProcNm', '진행단계')
      .col('dpsDt', '폐기요청일')
      .col('dpsUid', false)
      .col('dpsNm', '폐기요청자')
      .col('dpsRea', '폐기사유')
      .col('etrDt', '입고일')
      .col('ritmEtrNo', '입고번호')
      .col('ritmLabelNo', '라벨번호')
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
      .col('ancColNo', '컬럼번호')
      .col('ancPitmDivCd', false)
      .col('ancPitmDivNm', '자재구분')
      .col('ancPitmNm', '자재내역')
      .col('ancAitmNm', '시험항목정보')
      .col('opnDt', '개봉일')
      .col('ancInitAns', '최초분석')
      .col('ancRes', 'Resolution')
      .col('ancTai', 'Tailing')
      .col('ancThe', '이론단수')
      .col('analCnt', '분석횟수')
      .col('mngProcCd', false)
      .col('mngProcNm', '진행단계')
      .col('fileIdx', false)
      .col('dpsReqAprIdx', false)
      .build(),
};

export default {
  list,
};

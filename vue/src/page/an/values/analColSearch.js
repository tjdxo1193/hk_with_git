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
    legends: [{ value: '폐기', className: 'disposal' }],
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
  forms: () =>
    FormBuilder.builder()
      .Select('upperRitmTreeCd', '구분', {
        async: () => api.combo.common.getTestMaterialTreeCd('R4000000'),
      })
      .Input('ritmKn', '품명')
      .Input('mngNo', '관리번호')
      .Input('vdrNm', '제조처')
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .Select('', '조회일자구분')
      .DatepickerTwinWithSwitch('', '조회기한', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .Select('state', '상태')
      .blank()
      .spanCol(3)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmMngIdx', false)
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('fileIdx', false)
      .button('fileAttacher', '첨부파일')
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '품명')
      .col('ancColNo', '컬럼번호')
      .col('ancPitmNm', '품목명')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('ancEtrNo', '입고번호')
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
      .col('opnDt', '개봉일')
      .col('etrDt', '입고일')
      .col('dpsDt', '폐기일')
      .col('etrQty', '수량')
      .col('ancPitmDivCd', false)
      .col('ancPitmDivNm', '품목구분')
      .col('ancAitmNm', '시험항목정보')
      .col('ancInitAns', '최초분석')
      .col('ancRes', 'Resolution')
      .col('ancTai', 'Tailing')
      .col('ancThe', '이론단수')
      .col('mngProcCd', false)
      .col('mngProcNm', '진행상태')
      .build(),
};

const detailList = {
  static: {
    $grid: null,
    props: {
      editable: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmMngIdx', false)
      .col('useSeq', false)
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('ancDevNo', '기기관리번호')
      .col('ancPitmNm', '품목명')
      .col('makNo', '제조번호')
      .col('ansNo', '시험번호')
      .col('ancAitmNm', '시험항목정보')
      .col('useDt', '사용일')
      .col('useUid', false)
      .col('useNm', '사용자')
      .col('useRmk', '비고')
      .build(),
};

export default {
  list,
  detailList,
};

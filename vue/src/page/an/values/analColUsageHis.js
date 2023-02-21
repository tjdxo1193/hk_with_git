import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
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
      .Input('ritmKn', '자재내역')
      .Input('vdrNm', '제조처')
      .Input('ancFpak', '충진물')
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .Input('ancLen', '길이')
      .Input('ancInsDimt', '내경')
      .Input('ancColNo', '컬럼번호')
      .DatepickerTwinWithSwitch('searchUseDt', '사용일', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .blank()
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('useSeq', false)
      .col('ritmMngIdx', false)
      .col('ritmEtrIdx', false)
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '자재내역')
      .col('makNo', '제조번호')
      .col('ansNo', '시험번호')
      .col('ancAitmNm', '시험항목정보')
      .col('ancDevNo', '기기관리번호')
      .col('useDt', '사용일')
      .col('useUid', false)
      .col('useNm', '사용자')
      .col('useRmk', '비고')
      .col('ancPitmDivCd', false)
      .col('ancPitmDivNm', '자재구분')
      .col('pitmNm', '자재내역')
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
      .col('ancColNo', '컬럼번호')
      .col('etrDt', '입고일')
      .col('ancEtrNo', '입고번호')
      .col('ritmLabelNo', '라벨번호')
      .col('opnDt', '개봉일')
      .col('ancInitAns', '최초분석')
      .col('ancRes', 'Resolution')
      .col('ancTai', 'Tailing')
      .col('ancThe', '이론단수')
      .col('analCnt', '분석횟수')
      .build(),
};

const detail = {
  static: {
    title: '정보등록',
    countPerRow: 4,
    buttons: [
      { name: 'fileSave', label: '파일등록', disabled: true },
      { name: 'use', label: '등록' },
      { name: 'update', label: '수정', disabled: true },
      { name: 'delete', label: '삭제', disabled: true },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('useSeq')
      .Hidden('ritmMngIdx')
      .Hidden('ritmEtrIdx')
      .Hidden('fileIdx', { value: 0 })
      .multiple(
        'ancColNoMulti',
        '컬럼번호',
        FormBuilder.builder()
          .Input('ancColNo', { readonly: true })
          .required()
          .spanCol(5)
          .Button('openItemSearch', 'testMaterialOpenItemModal', { type: 'search' })
          .build(),
      )
      .Input('ansNo', '시험번호', { maxLength: 20 })
      .Input('makNo', '제조번호', { maxLength: 20 })
      .Input('ancAitmNm', '시험항목정보', { maxLength: 20 })
      .Hidden('useUid')
      .Input('useNm', '사용자', { readonly: true })
      .required()
      .Datepicker('useDt', '사용일', { value: [todayDate], readonly: true })
      .required()
      .Input('pitmNm', '자재내역', { maxLength: 20 })
      .Input('ancDevNo', '기기관리번호', { maxLength: 20 })
      .Textarea('useRmk', '비고', { rows: 2, maxLength: 500 })
      .spanCol(4)
      .build(),
};

export default {
  list,
  detail,
};

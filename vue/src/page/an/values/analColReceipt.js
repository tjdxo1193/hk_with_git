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
      .Input('ritmKn', '자재내역')
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
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '자재내역')
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
      .col('fileIdx', false)
      .build(),
};

const detail = {
  static: {
    title: '정보등록',
    countPerRow: 4,
    buttons: [
      { name: 'fileSave', label: '파일등록', disabled: true },
      { name: 'save', label: '등록' },
      { name: 'update', label: '수정', disabled: true },
      { name: 'delete', label: '삭제', disabled: true, type: 'danger' },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('ritmEtrIdx')
      .Hidden('fileIdx', { value: 0 })
      .Hidden('upperRitmTreeCd')
      .Input('upperRitmTreeNm', '구분', { readonly: true })
      .required()
      .Hidden('ritmRootCd')
      .Input('ritmRootNm', '재료구분', { readonly: true })
      .required()
      .Input('ancCatNo', 'Part or Cat No', { maxLength: 20 })
      .Input('ancSrlNo', 'Serial No', { maxLength: 20 })
      .multiple(
        'ritm',
        '자재내역',
        FormBuilder.builder()
          .Input('ritmKn', { readonly: true })
          .required()
          .spanCol(7)
          .Button('search', 'testMaterialItemModal', { type: 'search' })
          .Hidden('ritmCd')
          .build(),
      )
      .required()
      .Select('ritmCrgUid', '담당자', {
        async: api.combo.common.getUserList,
      })
      .required()
      .multiple(
        'ancLenMulti',
        '길이',
        FormBuilder.builder()
          .InputNumber('ancLen', '길이', { maxLength: 20 })
          .Select('ancLenUnitCd', '길이단위코드', {
            async: () => api.combo.userCommon.getUnitCombo(),
          })
          .build(),
      )
      .Input('ancInsDimt', '내경', { maxLength: 20 })
      .multiple(
        'vendor',
        '제조처/공급처',
        FormBuilder.builder()
          .Input('vdrNm', { readonly: true })
          .required()
          .spanCol(5)
          .Button('search', 'businessModal', { type: 'search' })
          .Button('clear', 'businessModal', { label: '삭제', type: 'clear' })
          .Hidden('vdrCd')
          .build(),
      )
      .spanCol(2)
      .Input('ancPatcsize', '입자크기', { maxLength: 20 })
      .Input('ancUspCd', 'USP코드', { maxLength: 20 })
      .Datepicker('etrDt', '입고일', { value: todayDate })
      .required()
      .Input('etrQty', '입고수량', { value: 1, maxLength: 5, readonly: true })
      .validator((value) => Number.isInteger(Number(value)))
      .required()
      .Input('ancEtrNo', '입고번호', { maxLength: 20 })
      .required()
      .Input('ancFpak', '충진물', { maxLength: 20 })
      .build(),
};

export default {
  list,
  detail,
};

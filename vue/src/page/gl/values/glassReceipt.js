import dayjs from 'dayjs';

import api from '@/api';
import { message } from '@/const';
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
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '자재내역')
      .col('mngNo', '관리번호')
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
      .col('etrProcNm', '진행단계')
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
      .Input('mngNo', '관리번호', { maxLength: 20 })
      .Input('makNo', '제조번호', { maxLength: 20 })
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
      .spanCol(2)
      .Select('ritmUnitCd', '단위', {
        async: () => api.combo.userCommon.getUnitCombo(),
      })
      .required()
      .Input('glsColrMtr', '색상/재질', { maxLength: 20 })
      .multiple(
        'vendor',
        '제조처/공급처',
        FormBuilder.builder()
          .Input('vdrNm', { readonly: true })
          .spanCol(5)
          .Button('search', 'businessModal', { type: 'search' })
          .Button('clear', 'businessModal', { label: '삭제', type: 'clear' })
          .Hidden('vdrCd')
          .build(),
      )
      .spanCol(2)
      .multiple(
        'safetyStock',
        '안전재고량',
        FormBuilder.builder()
          .Input('sfyStok', { maxLength: 5 })
          .validator((value) => Number.isInteger(Number(value)))
          .Select('ritmUnitCd', {
            async: () => api.combo.userCommon.getUnitCombo(),
            disabled: true,
          })
          .build(),
      )
      .spanCol(2)
      .Select('ritmCrgUid', '담당자', {
        async: api.combo.common.getUserList,
      })
      .required()
      .Datepicker('etrDt', '입고일', { value: todayDate })
      .multiple(
        'enterQuantity',
        '입고수량',
        FormBuilder.builder()
          .Input('etrQty', { maxLength: 5 })
          .validator((value) => Number.isInteger(Number(value)))
          .required()
          .Select('ritmUnitCd', {
            async: () => api.combo.userCommon.getUnitCombo(),
            disabled: true,
          })
          .build(),
      )
      .spanCol(2)
      .Hidden('currLotYn', { value: 'N' })
      .RadioGroup('expirDtChk', '사용/유효기한 구분', {
        value: '1',
        groups: [
          { checkedValue: '1', label: '직접선택' },
          { checkedValue: '2', label: '기간선택' },
          { checkedValue: '3', label: '기한없음(CurrentLot)' },
        ],
        gap: 125,
      })
      .spanCol(2)
      .multiple(
        'enterEachQuantity',
        '개별 수량',
        FormBuilder.builder()
          .Input('etrEachQty', { maxLength: 3 })
          .validator((value) => Number.isInteger(Number(value)))
          .required()
          .Select('ritmUnitCd', {
            async: () => api.combo.userCommon.getUnitCombo(),
            disabled: true,
          })
          .build(),
      )
      .spanCol(2)
      .Datepicker('opnBefExpirDt', '유효기한', { value: todayDate })
      .Input('opnAftExpirTrm', '개봉 후 사용기간(개월)', { maxlength: 5, disabled: true })
      .validator((value) => value === '' || Number.isInteger(Number(value)))
      .multiple(
        'enterTotalQuantity',
        '총수량',
        FormBuilder.builder()
          .Input('etrTotQty', { readonly: true })
          .Select('ritmUnitCd', {
            async: () => api.combo.userCommon.getUnitCombo(),
            disabled: true,
          })
          .build(),
      )
      .spanCol(2)
      .multiple(
        'glsVolMulti',
        '용량',
        FormBuilder.builder()
          .Input('glsVol', { maxLength: 5 })
          .validator((value) => value === '' || value > 0)
          .Select('glsVolUnitCd', {
            async: () => api.combo.userCommon.getUnitCombo(),
          })
          .validator({
            message: message.validate.requireInput,
            handler(value, formData) {
              const { glsVol } = formData;

              if (!glsVol) {
                return true;
              }

              return !!value;
            },
          })
          .build(),
      )
      .spanCol(2)
      .blank()
      .spanCol(2)
      .Textarea('etrRmk', '비고', { rows: 2, maxLength: 500 })
      .spanRow(2)
      .spanCol(4)
      .build(),
};

export default {
  list,
  detail,
};

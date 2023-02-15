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
        async: () => api.combo.common.getTestMaterialTreeCd('R1000000'),
      })
      .Input('ritmKn', '품명')
      .Input('mngNo', '관리번호')
      .Input('makNo', '제조번호')
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .Input('vdrNm', '제조처')
      .DatepickerTwinWithSwitch('searchMakDt', '제조일', {
        value: [todayDate, todayDate],
      })
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
      .col('ritmKn', '품명')
      .col('mngNo', '관리번호')
      .col('casNo', 'CASNO')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('makNo', '제조번호')
      .col('makDt', '제조일')
      .col('etrDt', '입고일')
      .col('expirDtChk', false)
      .col('opnBefExpirDt', '개봉 전 유효기한')
      .col('opnAftExpirTrm', '개봉 후 유효기간')
      .col('currLotYn', 'Current LOT')
      .col('strgTerms', false)
      .col('strgTermsNm', '보관조건')
      .col('strgPla', false)
      .col('strgPlaNm', '보관장소')
      .col('strgPlaDtl', '세부보관장소')
      .col('fomu', '화학식')
      .col('regRnk', '등급')
      .col('regVol', '용량')
      .col('regVolUnitCd', false)
      .col('regVolUnitNm', '용량단위')
      .col('regCtet', '순도(함량)')
      .col('regCtetUnitCd', false)
      .col('regCtetUnitNm', '순도(함량)단위')
      .col('etrQty', '용기수')
      .col('ritmCrgUid', false)
      .col('ritmCrgNm', '담당자')
      .col('etrEachQty', '개별수량')
      .col('ritmUnitNm', '개별수량단위')
      .col('etrTotQty', '총수량')
      .col('ritmUnitNm', '총수량단위')
      .col('sfyStok', '안전재고량')
      .col('ritmUnitNm', '안전재고단위')
      .col('regFact', 'Factor')
      .col('etrRmk', '비고')
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
      .Select('strgTerms', '보관조건', {
        async: () => api.combo.userCommon.getStrgTermsCombo(),
      })
      .multiple(
        'ritm',
        '품명',
        FormBuilder.builder()
          .Input('ritmKn', { readonly: true })
          .spanCol(7)
          .Button('search', 'testMaterialItemModal', { type: 'search' })
          .Hidden('ritmCd')
          .build(),
      )
      .required()
      .spanCol(2)
      .Input('makNo', '제조번호', { maxLength: 20 })
      .Select('strgPla', '보관장소', {
        async: () => api.combo.userCommon.getStrgPlaCombo(),
      })
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
      .Select('ritmUnitCd', '단위', {
        async: () => api.combo.userCommon.getUnitCombo(),
      })
      .required()
      .Input('strgPlaDtl', '세부보관장소', { maxLength: 20 })
      .Select('ritmCrgUid', '담당자', {
        async: api.combo.common.getUserList,
      })
      .required()
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
      .DatepickerWithSwitch('makDt', '제조일', { value: todayDate })
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
      .Datepicker('opnBefExpirDt', '개봉 전 사용기한', { value: todayDate })
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
        'regVolMulti',
        '용량',
        FormBuilder.builder()
          .Input('regVol', { maxLength: 5 })
          .validator((value) => value === '' || value > 0)
          .Select('regVolUnitCd', {
            async: () => api.combo.userCommon.getUnitCombo(),
          })
          .validator({
            message: message.validate.requireInput,
            handler(value, formData) {
              const { regVol } = formData;

              if (!regVol) {
                return true;
              }

              return !!value;
            },
          })
          .build(),
      )
      .spanCol(2)
      .Input('casNo', 'CASNO', { maxLength: 20 })
      .Input('regFact', 'Factor', { maxLength: 20 })
      .multiple(
        'regCtetMulti',
        '순도(함량)',
        FormBuilder.builder()
          .Input('regCtet', { maxLength: 5 })
          .validator((value) => value === '' || value > 0)
          .Select('regCtetUnitCd', {
            async: () => api.combo.userCommon.getUnitCombo(),
          })
          .validator({
            message: message.validate.requireInput,
            handler(value, formData) {
              const { regCtet } = formData;

              if (!regCtet) {
                return true;
              }

              return !!value;
            },
          })
          .build(),
      )
      .spanCol(2)
      .Input('fomu', '화학식', { maxLength: 20 })
      .Input('regRnk', '등급', { maxLength: 20 })
      .Textarea('etrRmk', '비고', { rows: 2, maxLength: 500 })
      .spanRow(2)
      .spanCol(4)
      .build(),
};

export default {
  list,
  detail,
};

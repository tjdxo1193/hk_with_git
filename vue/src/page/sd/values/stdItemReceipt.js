import dayjs from 'dayjs';

import api from '@/api';
import { message } from '@/const';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'search', label: '조회' },
      { name: 'approveReqest', label: '승인요청' },
    ],
    $grid: null,
    legends: [
      { value: '임시저장', className: 'tempSave' },
      { value: '반려', className: 'return' },
    ],
    props: {
      editable: false,
      showRowCheckColumn: true,
      rowStyleFunction: function (rowIndex, item) {
        if (item.etrProcCd === 'S0010100') {
          return 'tempSave';
        }
        if (item.etrProcCd === 'S0010110') {
          return 'return';
        }
        return null;
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperRitmTreeCd', '구분', {
        async: () => api.combo.common.getTestMaterialTreeCd('R3000000'),
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
      .Select('etrReqAprYn', '승인여부', {
        elements: [
          { value: 'N', label: '미승인' },
          { value: 'Y', label: '승인완료' },
        ],
      })
      .blank()
      .spanCol(3)
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
      .col('spec', '규격')
      .col('stdMois', '수분')
      .col('stdCtetCd', false)
      .col('stdCtetCdNm', '함량종류')
      .col('stdCtetVal', '함량')
      .col('stdCtetUnitCd', false)
      .col('stdCtetUnitNm', '함량단위')
      .col('stdAsisCtetNm', '함량(As is)종류')
      .col('stdAsisCtetVal', '함량(As is)')
      .col('stdAsisCtetUnitCd', false)
      .col('stdAsisCtetUnitNm', '함량(As is) 단위')
      .col('etrQty', '용기수')
      .col('etrEachQty', '용량')
      .col('ritmUnitNm', '용량단위')
      .col('etrTotQty', '총수량')
      .col('ritmUnitNm', '총수량단위')
      .col('sfyStok', '안전재고량')
      .col('ritmUnitNm', '안전재고단위')
      .col('ritmCrgUid', false)
      .col('ritmCrgNm', '담당자')
      .col('shadeYn', '차광여부')
      .col('mtrDedutYn', '물질차감여부')
      .col('etrRmk', '비고')
      .col('stdNb', '주의사항')
      .col('rjtRea', '반려사유')
      .col('etrReqAprIdx', false)
      .col('etrProcCd', false)
      .col('etrProcNm', '진행상태')
      .col('aprYn', '승인여부')
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
      .spanCol(2)
      .Select('strgTerms', '보관조건', {
        async: () => api.combo.userCommon.getStrgTermsCombo(),
      })
      .spanCol(2)
      .Hidden('ritmRootCd')
      .Input('ritmRootNm', '재료구분', { readonly: true })
      .required()
      .spanCol(2)
      .Select('strgPla', '보관장소', {
        async: () => api.combo.userCommon.getStrgPlaCombo(),
      })
      .spanCol(2)
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
      .Input('strgPlaDtl', '세부보관장소', { maxLength: 20 })
      .spanCol(2)
      .Input('mngNo', '관리번호', { maxLength: 20 })
      .spanCol(2)
      .Select('ritmUnitCd', '단위', {
        async: () => api.combo.userCommon.getUnitCombo(),
      })
      .required()
      .spanCol(2)
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
      .Input('makNo', '제조번호', { maxLength: 20 })
      .spanCol(2)
      .multiple(
        'enterQuantity',
        '용기수',
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
      .DatepickerWithSwitch('makDt', '제조일', { value: todayDate })
      .Datepicker('etrDt', '입고일', { value: todayDate })
      .multiple(
        'enterEachQuantity',
        '개별 용량/수량',
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
      .Hidden('currLotYn', { value: 'N' })
      .multiple(
        'enterTotalQuantity',
        '총수량(용기수*용량)',
        FormBuilder.builder()
          .Input('etrTotQty', { readonly: true })
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
        'stdCtet',
        '함량',
        FormBuilder.builder()
          .Select('stdCtetCd', {
            async: () => api.combo.userCommon.getStdCtetCombo(),
          })
          .Input('stdCtetVal', { maxLength: 5 })
          .validator((value) => value === '' || value > 0)
          .Select('stdCtetUnitCd', {
            async: () => api.combo.userCommon.getUnitCombo(),
          })
          .validator({
            message: message.validate.requireInput,
            handler(value, formData) {
              const { stdCtetVal } = formData;

              if (!stdCtetVal) {
                return true;
              }

              return !!value;
            },
          })
          .build(),
      )
      .spanCol(2)
      .Select('ritmCrgUid', '담당자', {
        async: api.combo.common.getUserList,
      })
      .required()
      .spanCol(2)
      .multiple(
        'stdAsisCtet',
        '함량(As is)',
        FormBuilder.builder()
          .Input('stdAsisCtetNm', { maxLength: 20 })
          .Input('stdAsisCtetVal', { maxLength: 5 })
          .validator((value) => value === '' || value > 0)
          .Select('stdAsisCtetUnitCd', {
            async: () => api.combo.userCommon.getUnitCombo(),
          })
          .validator({
            message: message.validate.requireInput,
            handler(value, formData) {
              const { stdAsisCtetVal } = formData;

              if (!stdAsisCtetVal) {
                return true;
              }

              return !!value;
            },
          })
          .build(),
      )
      .spanCol(2)
      .CheckboxGroup('extraYn', '부가정보 여부', {
        groups: [
          { checkedValue: 'shadeYn', label: '차광여부' },
          { checkedValue: 'mtrDedutYn', label: '물질차감여부' },
        ],
      })
      .spanCol(2)
      .Input('spec', '규격', { maxLength: 50 })
      .spanCol(2)
      .Textarea('etrRmk', '비고', { rows: 7, maxLength: 500 })
      .spanRow(2)
      .spanCol(2)
      .Input('stdMois', '수분', { maxLength: 20 })
      .spanCol(2)
      .Textarea('stdNb', '주의사항', { rows: 5, maxLength: 500 })
      .spanCol(2)
      .build(),
};

export default {
  list,
  detail,
};

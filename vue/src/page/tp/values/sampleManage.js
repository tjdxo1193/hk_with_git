import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const monthAgoDate = dayjs().add(-1, 'month').format('YYYY-MM-DD');

const todayDate = dayjs().format('YYYY-MM-DD');

const searchGridWithForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('smpDivCd', '검체구분', {
        async: () => api.combo.systemCommon.getSmpDivCombo(),
      })
      .Select('smpDpsProc', '검체상태', {
        async: () => api.combo.systemCommon.getSmpProcCombo(),
      })
      .Select('ansTyp', '시험유형', {
        async: () => api.combo.systemCommon.getAnsTypCombo(),
      })
      .Select('pitmTyp', '품목유형', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .Input('ansNo', '시험번호')
      .Input('pitmCd', '품목코드')
      .Select('smpStrgMtd', '검체보관방법', {
        async: () => api.combo.userCommon.getSmpStrgMtdCombo(),
      })
      .DatepickerTwinWithSwitch('dpsExpDtList', '폐기예정일자', {
        value: [monthAgoDate, todayDate],
      })
      .DatepickerTwinWithSwitch('smpEtrDtList', '검체입고일자', {
        value: [monthAgoDate, todayDate],
      })
      .DatepickerTwinWithSwitch('rcpDtList', '시험접수일자', {
        value: [monthAgoDate, todayDate],
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('smpMngIdx', false)
      .col('smpDpsProc', false)
      .col('smpDivCd', false)
      .col('pitmTyp', false)
      .col('ansTyp', false)
      .col('ansIdx', false)
      .col('mngSmpVol', false)
      .col('smpStrgMtd', false)
      .col('smpStrgMtdNm', false)
      .col('inpUnit', false)
      .col('makDt', false)
      .col('strgLmt', false)
      .col('useLmt', false)
      .col('irgYn', false)
      .col('smpEtrDt', false)
      .col('smpRmk', false)
      .col('dpsExpDt', false)
      .col('dpsFixDt', false)
      .col('smpDpsAprIdx', false)
      .col('dpsRea', false)
      .col('dpsCanRea', false)
      .col('rjtUid', false)
      .col('rjtNm', false)
      .col('rjtRea', false)
      .col('smpDivNm', '검체구분')
      .col('ansNo', '시험번호')
      .col('pitmTypNm', '품목유형')
      .col('ansTypNm', '시험유형')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('smpDpsNm', '검체상태')
      .build(),
};

const inputInfoForm = {
  static: {
    title: '입력 정보',
    countPerRow: 4,
    buttons: [
      { name: 'save', label: '등록' },
      { name: 'update', label: '수정', disabled: true },
      { name: 'delete', label: '삭제', disabled: true },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('smpMngIdx')
      .Hidden('smpdpsProc')
      .Hidden('ansIdx')
      .Hidden('pitmCd')
      .Hidden('pitmTyp')
      .Hidden('smpDpsProc')
      .Hidden('pitmTyp')
      .Hidden('ansTyp')
      .multiple(
        'pitm',
        '품목명',
        FormBuilder.builder()
          .Input('pitmNm', { readonly: true, _required: true, _colSpan: 10 })
          .Button('search', '검색', { type: 'search' })
          .build(),
      )
      .Input('pitmTypNm', '품목구분', { readonly: true })
      .Input('ansNo', '시험번호', { readonly: true })
      .Select('smpDivCd', '검체구분', {
        async: () => api.combo.systemCommon.getSmpDivCombo(),
        _required: true,
      })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('batchNo', '배치번호', { readonly: true })
      .Input('smpDpsNm', '검체상태', { readonly: true })
      .Input('ansTypNm', '시험유형', { readonly: true })
      .Input('rcpDt', '접수일자', { readonly: true })
      .Input('makDt', '제조일자', { readonly: true, _required: true })
      .Input('smpEtrDt', '검체입고일자', { readonly: true })
      .Input('pitmTypNm', '품목유형', { readonly: true })
      .Select('smpStrgMtd', '검체보관방법', {
        async: () => api.combo.userCommon.getSmpStrgMtdCombo(),
      })
      .Input('mngSmpVol', '관리검체량', { readonly: true, _required: true })
      .Input('inpUnit', '입력단위', { readonly: true, _required: true })
      .Input('strgLmt', '보관기한', { readonly: true })
      .Input('useLmt', '사용기한', { readonly: true })
      .RadioGroup('irgYn', '이상여부', {
        groups: [
          { label: '정상', checkedValue: 'N' },
          { label: '이상', checkedValue: 'Y' },
        ],
      })
      .Datepicker('dpsExpDt', '폐기예정일자', { value: todayDate })
      .Textarea('smpRmk', '검체비고', { maxLength: 4000 })
      .build(),
};

export default {
  searchGridWithForm,
  inputInfoForm,
};

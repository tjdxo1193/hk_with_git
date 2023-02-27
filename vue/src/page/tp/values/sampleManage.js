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
    legends: [
      { value: '폐기', className: 'disposal' },
      { value: '이상', className: 'weird' },
    ],
    props: {
      editable: false,
      showRowCheckColumn: false,
      rowStyleFunction: (rowIndex, item) => {
        if (item.smpDpsProc === 'S0270500') {
          return 'disposal';
        } else if (item.irgYn === 'Y') {
          return 'weird';
        }
      },
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
      .Select('pitmTyp', '자재유형', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .Input('ansNo', '시험번호')
      .Input('pitmCd', '자재번호')
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
      .col('plntCd', { visible: false })
      .col('smpMngIdx', { visible: false })
      .col('smpDpsProc', { visible: false })
      .col('smpDivCd', { visible: false })
      .col('pitmTyp', { visible: false })
      .col('ansTyp', { visible: false })
      .col('ansIdx', { visible: false })
      .col('smpStrgMtd', { visible: false })
      .col('strgLmt', { visible: false })
      .col('irgYn', { visible: false })
      .col('smpEtrDt', { visible: false })
      .col('smpRmk', { visible: false })
      .col('dpsExpDt', { visible: false })
      .col('dpsFixDt', { visible: false })
      .col('smpDpsAprIdx', { visible: false })
      .col('dpsRea', { visible: false })
      .col('dpsCanRea', { visible: false })
      .col('rjtUid', { visible: false })
      .col('rjtNm', { visible: false })
      .col('rjtRea', { visible: false })
      .col('smpDivNm', '검체구분')
      .col('ansNo', '시험번호')
      .col('pitmTypNm', '자재유형')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('mngSmpVol', '보관검체량')
      .col('inpUnit', '입력단위')
      .col('makDt', '제조일')
      .col('useLmt', '사용기한')
      .col('ansTypNm', '시험유형')
      .col('smpStrgMtdNm', '보관방법')
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
      .Hidden('smpDivCd')
      .multiple(
        'pitm',
        '자재내역',
        FormBuilder.builder()
          .Input('pitmNm', { readonly: true, _required: true, _colSpan: 10 })
          .Button('search', '검색', { type: 'search' })
          .build(),
      )
      .Input('pitmTypNm', '자재유형', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('batchNo', '배치번호', { readonly: true })
      .Input('smpDivNm', '검체구분', { readonly: true, _required: true })
      .Input('ansTypNm', '시험유형', { readonly: true })
      .Input('ansNo', '시험번호', { readonly: true })
      .Input('smpDpsNm', '검체상태', { readonly: true })
      .Input('rcpDt', '접수일자', { readonly: true })
      .Input('makDt', '제조일자', { readonly: true, _required: true })
      .Input('smpEtrDt', '검체입고일자', { readonly: true })
      .Input('smpStrgMtdNm', '보관방법', { readonly: true })
      .Input('mngSmpVol', '관리검체량', { readonly: true, _required: true })
      .Input('inpUnit', '입력단위', { readonly: true, _required: true })
      .Input('strgLmt', '보관기한', { readonly: true })
      .Input('useLmt', '사용기한', { readonly: true })
      .RadioGroup('irgYn', '이상여부', {
        groups: [
          { label: '정상', checkedValue: 'N' },
          { label: '이상', checkedValue: 'Y' },
        ],
        disabled: true,
      })
      .Datepicker('dpsExpDt', '폐기예정일자', { value: todayDate })
      .Textarea('smpRmk', '비고', { _colSpan: 2, maxLength: 4000 })
      .build(),
};

export default {
  searchGridWithForm,
  inputInfoForm,
};

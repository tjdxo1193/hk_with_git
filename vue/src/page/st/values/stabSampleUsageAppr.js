import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const yesterdayDate = dayjs().add(-1, 'day').format('YYYY-MM-DD');

const sampleGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '자재구분', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('pitmCd', '자재번호', { maxLength: 8 })
      .Input('lotNo', '제조번호', { maxLength: 20 })
      .Input('batchNo', '배치번호', { maxLength: 20 })
      .Input('pitmNm', '자재내역', { maxLength: 100 })
      .DatepickerTwinWithSwitch('useDtList', '사용일', {
        value: [yesterdayDate, todayDate],
        _colSpan: 2,
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('smpMngIdx', { visible: false })
      .col('useSeq', { visible: false })
      .col('smpUseAprIdx', { visible: false })
      .col('smpUseProc', { visible: false })
      .col('smpUseNm', '사용진행상황', { width: '7.5%' })
      .col('pitmTypNm', '자재구분')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('ansNo', '시험번호', { width: '7.5%' })
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('ansTypNm', '시험구분')
      .col('mngSmpVol', '안정성검체량', { width: '5%' })
      .col('useSmpVol', '사용량', { width: '5%' })
      .col('remains', '재고량', { width: '5%' })
      .col('inpUnit', '검체단위', { width: '5%' })
      .col('useNm', '사용자')
      .col('usePps', '사용목적')
      .col('irgYn', '이상여부')
      .col('smpDpsNm', '검체상태')
      .build(),
};

const inputForm = {
  static: {
    title: '입력정보',
    countPerRow: 4,
    buttons: [
      { name: 'approve', label: '승인', disabled: true },
      { name: 'reject', label: '반려', disabled: true },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('smpMngIdx')
      .Hidden('useSeq')
      .Hidden('smpUseAprIdx')
      .Hidden('smpUseProc')
      .Input('pitmNm', '자재내역', { readonly: true, _colSpan: 2 })
      .Input('inpUnit', '보관단위', { readonly: true })
      .Input('ansNo', '시험번호', { readonly: true })
      .Input('remains', '재고량', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('batchNo', '배치번호', { readonly: true })
      .InputNumber('useSmpVol', '사용량', { readonly: true })
      .Input('useNm', '사용자', { readonly: true })
      .Input('useDt', '사용일', { readonly: true })
      .Input('strgPla', '보관장소', { readonly: true })
      .Textarea('usePps', '사용목적', { readonly: true })
      .build(),
};

export default {
  sampleGrid,
  inputForm,
};

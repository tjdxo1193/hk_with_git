import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const yesterdayDate = dayjs().add(-1, 'day').format('YYYY-MM-DD');

const sampleGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
    legends: [
      { value: '임시저장', className: 'tempSave' },
      { value: '승인대기중', className: 'approveWating' },
      { value: '반려', className: 'return' },
      { value: '이상', className: 'weird' },
      { value: '폐기', className: 'disposal' },
    ],
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '품목구분', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('pitmCd', '품목코드')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .Input('pitmNm', '품목명')
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
      .col('smpUseNm', '사용진행상황')
      .col('pitmTypNm', '품목구분')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('ansNo', '시험번호')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('ansTypNm', '시험구분')
      .col('mngSmpVol', '안정성검체량')
      .col('useSmpVol', '사용량')
      .col('remains', '재고량')
      .col('inpUnit', '검체단위')
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
      .Input('pitmNm', '품목명', { readonly: true, _colSpan: 2 })
      .Input('inpUnit', '보관단위', { readonly: true })
      .Input('ansNo', '시험번호', { readonly: true })
      .Input('remains', '재고량', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('batchNo', '배치번호', { readonly: true })
      .InputNumber('useSmpVol', '사용량', { readonly: true })
      .Input('useNm', '사용자', { readonly: true })
      .Input('useDt', '사용일', { readonly: true })
      .Textarea('usePps', '사용목적', { readonly: true, _colSpan: 2 })
      .build(),
};

export default {
  sampleGrid,
  inputForm,
};

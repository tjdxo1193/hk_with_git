import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const sampleGrid = {
  static: {
    title: '조회',
    buttons: [
      { name: 'approve', label: '승인' },
      { name: 'reject', label: '반려' },
      { name: 'search', label: '조회' },
    ],
    props: {
      showRowCheckColumn: true,
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('smpDivCd', '검체구분', { async: () => api.combo.systemCommon.getSmpDivCombo() })
      .Input('pitmNm', '품목명')
      .Input('ansNo', '시험번호')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .Input('pitmCd', '품목코드')
      .Select('smpProcCd', '검체상태', { async: () => api.combo.systemCommon.getSmpProcCombo() })
      .Select('pitmTyp', '품목구분', { async: () =>  api.combo.systemCommon.getPitmDivCombo() })
      .DatepickerTwinWithSwitch('dpsExpDtList', '폐기예정일', { value: [todayDate, todayDate] })
      .DatepickerTwinWithSwitch('smpEtrDtList', '검체입고일자', {
        value: [todayDate, todayDate],
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('smpMngIdx', { visible: false })
      .col('useSeq', { visible: false })
      .col('aprIdx', { visible: false })
      .col('pitmTyp', { visible: false })
      .col('smpDiv', { visible: false })
      .col('smpDpsProc', { visible: false })
      .col('rjtUid', { visible: false })
      .col('pitmNm', '품목명')
      .col('pitmCd', '품목코드')
      .col('pitmTypNm', '품목구분')
      .col('ansNo', '시험번호')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('smpDiv', '검체구분')
      .col('smpDpsNm', '검체상태')
      .build(),
};

const inputForm = {
  static: {
    title: '입력정보',
    buttons: [{ name: 'init', label: '초기화' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('smpMngIdx')
      .Hidden('smpdpsProc')
      .Hidden('ansTyp')
      .Hidden('smpDpsProc')
      .Hidden('pitmTyp')
      .Input('pitmNm', '품목명', { readonly: true })
      .Input('ansNo', '시험번호', { readonly: true })
      .Input('ansTypNm', '시험유형', { readonly: true })
      .Input('rcpDt', '접수일자', { readonly: true })
      .Input('pitmCd', '품목코드', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('batchNo', '배치번호', { readonly: true })
      .Input('makDt', '제조일자', { readonly: true })
      .Input('smpEtrDt', '검체입고일자', { readonly: true })
      .Input('smpDpsNm', '검체상태', { readonly: true })
      .Input('pitmTypNm', '품목유형', { readonly: true })
      .Select('smpMngVol', '검체구분', {
        async: () => api.combo.systemCommon.getSmpDivCombo(),
        disabled: true,
      })
      .Select('smpStrgMtd', '검체보관방법', {
        async: () => api.combo.userCommon.getSmpStrgMtdCombo(),
        disabled: true,
      })
      .Input('mngSmpVol', '관리검체량', { readonly: true })
      .Input('inpUnit', '입력단위', { readonly: true })
      .Input('strgLmt', '보관기한', { readonly: true })
      .Input('useLmt', '사용기한', { readonly: true })
      .RadioGroup('irgYn', '이상여부', {
        groups: [
          { label: '정상', checkedValue: 'N' },
          { label: '이상', checkedValue: 'Y' },
        ],
        disabled: true,
      })
      .Input('dpsExpDt', '폐기예정일자', { value: todayDate, readonly: true })
      .Textarea('smpRmk', '검체비고', { readonly: true })
      .build(),
};

export default {
  sampleGrid,
  inputForm,
};

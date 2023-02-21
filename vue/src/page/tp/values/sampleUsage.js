import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const sampleUsageGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    legends: [
      { value: '임시저장', className: 'tempSave' },
      { value: '승인대기중', className: 'approveWating' },
      { value: '반려', className: 'return' },
      { value: '이상', className: 'weird' },
      { value: '폐기', className: 'disposal' },
    ],
    props: {
      editable: false,
      showRowCheckColumn: false,
      rowStyleFunction: (rowIndex, item) => {
        if (item.irgYn === 'Y') {
          return 'weird';
        }
        if (item.dpsYn === 'Y') {
          return 'disposal';
        }
        if (item.smpUseProc === 'S0280100') {
          return 'tempSave';
        } else if (item.smpUseProc === 'S0280200' || item.smpUseProc === 'S0280400') {
          return 'approveWating';
        } else if (item.smpUseProc === 'S0280110' || item.smpUseProc === 'S0280310') {
          return 'return';
        }
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('smpDivCd', '검체구분', { async: () => api.combo.systemCommon.getSmpDivCombo() })
      .Input('pitmCd', '자재번호')
      .Select('pitmDiv', '자재유형', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .Input('pitmNm', '자재내역')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('smpMngIdx', { visible: false })
      .col('useSeq', { visible: false })
      .col('pitmTyp', { visible: false })
      .col('smpDivCd', { visible: false })
      .col('smpUseProc', { visible: false })
      .col('usePps', { visible: false })
      .col('useUid', { visible: false })
      .col('smpUseAprIdx', { visible: false })
      .col('rjtUid', { visible: false })
      .col('smpDpsProc', { visible: false })
      .col('strgPla', { visible: false })
      .col('pitmNm', '자재내역')
      .col('pitmCd', '자재번호')
      .col('pitmTypNm', '자재유형')
      .col('ansNo', '시험번호')
      .col('ansTypNm', '시험유형')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('smpDivNm', '검체구분')
      .col('useSmpVol', '사용량')
      .col('mngSmpVol', '보관수량')
      .col('sumVol', '총사용량')
      .col('remains', '재고량')
      .col('inpUnit', '보관수량단위')
      .col('useNm', '사용자')
      .col('useDt', '사용일')
      .col('smpDpsNm', '검체상태')
      .col('smpUseNm', '사용이력진행상황')
      .col('rjtNm', '반려자')
      .col('rjtDs', '반려일시')
      .col('rjtRea', '반려사유')
      .build(),
};

const inputForm = {
  static: {
    title: '입력정보',
    countPerRow: 4,
    buttons: [
      { name: 'requestApproveUse', label: '사용승인요청', disabled: false },
      { name: 'save', label: '저장' },
      { name: 'update', label: '수정', disabled: false },
      { name: 'delete', label: '삭제', disabled: false },
      { name: 'requestApproveCancelUse', label: '사용취소요청', disabled: false },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('smpMngIdx')
      .Hidden('useSeq')
      .Hidden('useUid')
      .Hidden('pitmCd')
      .Hidden('pitmTyp')
      .Hidden('smpDivCd')
      .Hidden('smpUseProc')
      .Hidden('smpUseAprIdx')
      .Hidden('rjtUid')
      .Hidden('smpDivCd')
      .multiple(
        'pitm',
        '자재내역',
        FormBuilder.builder()
          .Input('pitmNm', { readonly: true, _required: true, _colSpan: 10 })
          .Button('search', '검색', { type: 'search' })
          .build(),
      )
      .spanCol(2)
      .Input('pitmTypNm', '자재유형', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('batchNo', '배치번호', { readonly: true })
      .Input('smpDivNm', '검체구분', { readonly: true })
      .Input('ansTypNm', '시험유형', { readonly: true })
      .Input('ansNo', '시험번호', { readonly: true })
      .Input('smpDpsNm', '검체상태', { readonly: true })
      .Input('useNm', '사용자', { readonly: true })
      .Input('mngSmpVol', '보관수량', { readonly: true })
      .Input('sumVol', '총사용량', { readonly: true })
      .Input('remains', '재고량', { readonly: true })
      .Datepicker('useDt', '사용일자', { value: todayDate, _required: true })
      .Input('inpUnit', '입력단위', { readonly: true })
      .InputNumber('useSmpVol', '사용검체량', { _required: true })
      .Input('strgPla', '보관장소', { maxLength: 18 })
      .Textarea('usePps', '사용목적', { maxLength: 4000 })
      .build(),
};

export default {
  sampleUsageGrid,
  inputForm,
};

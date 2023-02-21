import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const yesterdayDate = dayjs().add(-1, 'day').format('YYYY-MM-DD');

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
      rowStyleFunction: (rowIndex, item) => {
        if (item.irgYn === 'Y') {
          return 'weird';
        }
        if (item.dpsYn === 'Y' || item.smpDpsProc === 'S0270500') {
          return 'disposal';
        }
        if (item.smpUseProc === 'S0280100') {
          return 'tempSave';
        }
        if (item.smpUseProc === 'S0280200' || item.smpUseProc === 'S0280400') {
          return 'approveWating';
        }
        if (item.smpUseProc === 'S0280110' || item.smpUseProc === 'S0280310') {
          return 'return';
        }
      },
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
      .col('smpMngIdx', { visible: false })
      .col('useSeq', { visible: false })
      .col('smpUseProc', { visible: false })
      .col('useUid', { visible: false })
      .col('smpUseAprIdx', { visible: false })
      .col('rjtUid', { visible: false })
      .col('pitmTyp', { visible: false })
      .col('ansTyp', { visible: false })
      .col('ansIdx', { visible: false })
      .col('smpStrgMtd', { visible: false })
      .col('strgPla', { visible: false })
      .col('smpStrgMtdNm', { visible: false })
      .col('makDt', { visible: false })
      .col('strgLmt', { visible: false })
      .col('smpRmk', { visible: false })
      .col('dpsExpDt', { visible: false })
      .col('smpEtrDt', { visible: false })
      .col('pitmTypNm', '자재유형')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('ansTypNm', '시험유형')
      .col('mngSmpVol', '안정성검체량')
      .col('useSmpVol', '사용량')
      .col('remains', '재고량')
      .col('inpUnit', '입력단위')
      .col('useNm', '사용자')
      .col('useDt', '사용일자')
      .col('usePps', '사용목적')
      .col('irgYn', '이상여부')
      .col('smpDpsNm', '검체상태')
      .col('smpUseNm', '사용이력진행상태')
      .col('rjtNm', '반려자')
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
      { name: 'requestCancelUse', label: '사용취소요청', disabled: false },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('useUid')
      .Hidden('pitmCd')
      .Hidden('useUid')
      .Hidden('smpMngIdx')
      .Hidden('useSeq')
      .multiple(
        'pitm',
        '자재내역',
        FormBuilder.builder()
          .Input('pitmNm', { readonly: true, _colSpan: 10, _required: true })
          .Button('sampleSearch', { type: 'search' })
          .build(),
      )
      .spanCol(2)
      .Input('ansNo', '시험번호', { readonly: true })
      .Input('inpUnit', '입력단위', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('batchNo', '배치번호', { readonly: true })
      .Input('remains', '재고량', { readonly: true })
      .InputNumber('useSmpVol', '사용량', { _required: true })
      .Input('useNm', '사용자', { readonly: true, _required: true })
      .Datepicker('useDt', '사용일', { value: todayDate, _required: true })
      .Input('strgPla', '보관장소', { maxLength: 9 })
      .Textarea('usePps', '사용목적', { maxLength: 4000 })
      .build(),
};

export default {
  sampleUsageGrid,
  inputForm,
};

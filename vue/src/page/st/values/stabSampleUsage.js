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
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmDiv', '품목구분', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('labelCd', '라벨코드')
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
      .col('smpUseNm', '사용이력진행상태')
      .col('useSmpVol', '사용검체량')
      .col('remains', '재고량')
      .col('mngSmpVol', '관리검체량')
      .col('inpUnit', '입력단위')
      .col('usePps', '사용목적')
      .col('useNm', '사용자')
      .col('useDt', '사용일자')
      .col('strgPla', '보관장소')
      .col('rjtNm', '반려자')
      .col('rjtRea', '반려사유')
      .col('pitmTypNm', '품목유형')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('ansTypNm', '시험유형')
      .col('smpStrgMtdNm', '보관방법')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('makDt', '생산일자')
      .col('strgLmt', '보관기한')
      .col('irgYn', '이상여부')
      .col('smpRmk', '비고')
      .col('dpsExpDt', '폐기예정일자')
      .col('smpEtrDt', '입고일자')
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
        '품목명',
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
      .Textarea('usePps', '사용목적', { _colSpan: 2 })
      .build(),
};

export default {
  sampleUsageGrid,
  inputForm,
};

import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const weekAgoDate = dayjs().add(-7, 'd').format('YYYY-MM-DD');

const rePrintLabelRequestGridWithForms = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'select', label: '조회' }],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
      rowStyleFunction: function (rowIndex, item) {
        if (item.labelPrtProc == 'S0350200') {
          return 'approveRequest';
        }
        if (item.labelPrtProc == 'S0350210') {
          return 'return';
        }
        return null;
      },
    },
    legends: [
      { value: '승인요청', className: 'approveRequest' },
      { value: '반려', className: 'return' },
      { value: '승인', className: '' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '자재유형', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Select('rptDiv', '리포트 구분', {
        async: () => api.combo.systemCommon.getRptDivCombo(),
      })
      .Select('ansTyp', '시험유형', {
        async: () => api.combo.systemCommon.getAnsTypCombo(),
      })
      .Input('pitmCd', '원료코드')
      .Select('ansProcCd', '시험진행상황', {
        async: () => api.combo.systemCommon.getAnsProcCombo(),
      })
      .Input('ansNo', '시험번호')
      .Input('batchNo', '배치번호')
      .Input('lotNo', '제조번호')
      .DatepickerTwinWithSwitch('searchRcpDt', '시험접수일자', {
        value: [weekAgoDate, todayDate],
      })
      .spanCol(2)
      .blank()
      .blank()
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('reqIdx', false)
      .col('plntCd', '플랜트 코드', { visible: false })
      .col('pitmTyp', '자재유형', { visible: false })
      .col('pitmTypNm', '자재유형')
      .col('ansTyp', '시험유형', { visible: false })
      .col('ansTypNm', '시험유형')
      .col('ansProcCd', '시험 진행상태 코드', { visible: false })
      .col('ansProcCdNm', '시험 진행상태')
      .col('pitmCd', '원료코드')
      .col('pitmNm', '검체라벨 원료명')
      .col('crtNo', '생산번호')
      .col('ansNo', '시험번호', { width: 90 })
      .col('batchNo', '배치번호')
      .col('lotNo', '제조번호')
      .col('makDt', '제조일자')
      .col('mtrCd', '자재번호')
      .col('reqDt', '의뢰일자')
      .col('rcpDt', '접수일자')
      .col('assDt', '지시일자')
      .col('useLmt', '사용기한')
      .col('etrQty', '입고수량')
      .col('inpUnit', '입력단위')
      .col('etrDt', '입고일자')
      .col('splCd', '공급사코드')
      .col('splNm', '공급사명')
      .col('splLotNo', '공급사제조번호')
      .col('csmNm', '고객처명')
      .col('smpVolTot', '총검체량')
      .col('clltUidNm', '채취자')
      .col('clltDt', '채취일자')
      .col('rptDiv', '리포트 구분', { visible: false })
      .col('rptDivNm', '리포트 구분')
      .col('labelRePrtRjtUid', '반려 UID', { visible: false })
      .col('labelRePrtRjtUidNm', '반려자')
      .col('labelRePrtRjtDs', '반려일시', false)
      .col('labelRePrtRjtDt', '반려일자')
      .col('labelRePrtRjtRea', '반려사유')
      .col('smpRmk', '검체 비고', { visible: false })
      .col('ansIdx', '시험 IDX', { visible: false })
      .col('labelPrtIdx', '라벨 출력 IDX', { visible: false })
      .col('labelRptIdx', '라벨 리포트 IDX', { visible: false })
      .col('prtSeq', '출력 순번', { visible: false })
      .build(),
};

const rePrintLabelRequestInfoForms = {
  static: {
    title: '검체정보',
    countPerRow: 4,
    buttons: [
      { name: 'rePrintLabelRequest', label: '재출력요청' },
      { name: 'rePrintLabelCancel', label: '재출력취소' },
      { name: 'reset', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .multiple(
        'rptDivButton',
        '리포트 구분',
        FormBuilder.builder()
          .Hidden('rptDiv', '리포트 구분')
          .required()
          .Input('rptDivNm', { readonly: true })
          .spanCol(4)
          .Button('labelListModal', { type: 'search' })
          .disabled()
          .build(),
      )
      .Input('ansTypNm', '시험유형', { readonly: true })
      .Hidden('ansProcCd', '시험진행상황')
      .Input('ansProcCdNm', '시험진행상황', { readonly: true })
      .Input('ansNo', '시험번호', { readonly: true })
      .Hidden('pitmTyp', '자재유형')
      .Input('pitmTypNm', '자재유형', { readonly: true })
      .Input('pitmNm', '검체라벨 원료명', { readonly: true })
      .Input('pitmCd', '원료코드', { readonly: true })
      .Input('batchNo', '배치번호', { readonly: true })
      .Input('mtrCd', '자재번호', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('makDt', '제조일자', { readonly: true })
      .Input('crtNo', '생산번호', { readonly: true })
      .Input('splNm', '공급사', { readonly: true })
      .Input('splLotNo', '공급사 제조번호', { readonly: true })
      .multiple(
        'input',
        '입고수량',
        FormBuilder.builder()
          .Input('etrQty', { readonly: true })
          .Input('inpUnit', { readonly: true })
          .spanCol(0.5)
          .build(),
      )
      .Input('etrDt', '입고일자', { readonly: true })
      .Input('useLmt', '사용기한', { readonly: true })
      .Input('csmNm', '고객처명', { readonly: true })
      .Input('smpVolTot', '총검체량', { readonly: true })
      .Input('clltUidNm', '채취자', { readonly: true })
      .Input('clltDt', '채취일자', { readonly: true })
      .Hidden('labelRePrtRjtUid', '반려 UID')
      .Input('labelRePrtRjtUidNm', '반려자', { readonly: true })
      .Hidden('labelRePrtRjtDs', '반려일시')
      .Input('labelRePrtRjtDt', '반려일자', { readonly: true })
      .Input('labelRePrtRjtRea', '반려사유', { readonly: true })
      .Hidden('prtUid', '출력 UID')
      .Input('prtUid', '출력자', { readonly: true })
      .Hidden('prtDs', '출력일시')
      .Input('prtDt', '출력일자', { readonly: true })
      .Hidden('plntCd', '플랜트 코드')
      .Hidden('ansIdx', '시험 IDX')
      .Hidden('labelPrtIdx', '라벨 출력 IDX')
      .Hidden('prtSeq', '출력 순번')
      .Hidden('labelRptIdx', '라벨 리포트 IDX')
      .build(),
};

export default {
  rePrintLabelRequestGridWithForms,
  rePrintLabelRequestInfoForms,
};

import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const weekAgoDate = dayjs().add(-7, 'd').format('YYYY-MM-DD');

const labelSearchGridWithForms = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'select', label: '조회' }],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
      rowStyleFunction: function (rowIndex, item) {
        if (item.useVerYn === 'N' || item.specUseVerYn === 'N') {
          return 'standardNoSet';
        }
        if (item.oosYn === 'Y') {
          return 'oosReTest';
        }
        return null;
      },
    }, //TODO EDITABLE: TRUE 컬럼 확인
    legends: [
      { value: '안정성시험', className: 'stabilityTest' },
      { value: 'OOS 재시험', className: 'oosReTest' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '품목유형', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Select('smpDivCd', '검체구분', {
        async: () => api.combo.systemCommon.getSmpDivCombo(),
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
      .col('plntCd', '사업장 코드', { visible: false })
      .col('addSmpIdx', '추가 검체 IDX', { visible: false })
      .col('pitmTyp', '품목유형', { visible: false })
      .col('pitmTypNm', '품목유형')
      .col('smpDivCd', '검체구분', { visible: false })
      .col('smpDivCdNm', '검체구분')
      .col('ansTyp', '시험유형', { visible: false })
      .col('ansTypNm', '시험유형')
      .col('ansProcCd', '시험 진행상태 코드', { visible: false })
      .col('ansProcCdNm', '시험 진행상태')
      .col('labelPrtProc', '라벨 출력 프로세스', { visible: false })
      .col('labelPrtProcNm', '라벨 출력 프로세스')
      .col('pitmCd', '원료코드')
      .col('pitmNm', '검체라벨 원료명')
      .col('crtNo', '생산번호')
      .col('ansNo', '시험번호', { width: 90 })
      .col('batchNo', '배치번호')
      .col('lotNo', '제조번호')
      .col('makDt', '제조일자')
      .col('mtrCd', '자재코드')
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
      .col('smpRmk', '검체 비고', { visible: false })
      .col('ansIdx', '시험 IDX', { visible: false })
      .col('labelPrtIdx', '라벨 출력 IDX', { visible: false })
      .col('prtSeq', '출력 순번', { visible: false })
      .build(),
};

const labelSearchInfoForms = {
  static: {
    title: '검체정보',
    countPerRow: 4,
    buttons: [{ name: 'reset', label: '초기화' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('pitmTyp', '품목유형')
      .Input('pitmTypNm', '품목유형', { readonly: true })
      .Input('smpDivCdNm', '검체구분', { readonly: true })
      .Input('ansTypNm', '시험유형', { readonly: true })
      .Hidden('rptDiv', '리포트 구분', { readonly: true })
      .Input('rptDivNm', '리포트 구분', { readonly: true })
      .Hidden('ansProcCd', '시험진행상황')
      .Input('ansProcCdNm', '시험진행상황', { readonly: true })
      .Input('pitmNm', '검체라벨 원료명', { readonly: true })
      .Input('pitmCd', '원료코드', { readonly: true })
      .Input('ansNo', '시험번호', { readonly: true })
      .Input('batchNo', '배치번호', { readonly: true })
      .Input('mtrCd', '자재코드', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('makDt', '제조일자', { readonly: true })
      .Input('crtNo', '생산번호', { readonly: true })
      .Input('labelCd', '라벨코드', { readonly: true })
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
      .Textarea('smpRmk', '검체비고', { rows: 4, readonly: true })
      .spanRow(2)
      .Hidden('addSmpIdx', '추가 검체 IDX')
      .Hidden('plntCd', '사업장 코드')
      .Hidden('ansIdx', '시험 IDX')
      .Hidden('labelPrtIdx', '라벨 출력 IDX')
      .Hidden('prtSeq', '출력 순번')
      .build(),
};

export default {
  labelSearchGridWithForms,
  labelSearchInfoForms,
};

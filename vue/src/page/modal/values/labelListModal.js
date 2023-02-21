import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');

const labelListGridWithForms = {
  static: {
    title: '조회',
    countPerRow: 2,
    buttons: [{ name: 'select', label: '조회' }],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: true,
      rowCheckToRadio: true,
      rowStyleFunction: function (rowIndex, item) {
        if (item.useVerYn === 'N' || item.specUseVerYn === 'N') {
          return 'standardNoSet';
        }
        if (item.oosYn === 'Y') {
          return 'oosReTest';
        }
        return null;
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('rptDiv', '리포트 구분', {
        async: () => api.combo.systemCommon.getRptDivCombo(),
      })
      .Input('prtUidNm', '출력자')
      .DatepickerTwinWithSwitch('searchPrtDt', '출력일자', { value: [todayDate, todayDate] })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('rptDiv', '리포트 구분', false)
      .col('rptDivNm', '리포트 구분')
      .col('prtUid', '출력 UID', false)
      .col('prtUidNm', '출력자')
      .col('prtDt', '출력일자')
      .col('labelRePrtRjtUid', '라벨 재출력 반려 UID', false)
      .col('labelRePrtRjtUidNm', '반려자')
      .col('labelRePrtRjtDt', '반려일자')
      .col('labelRePrtRjtRea', '반려사유')
      // .col('reqIdx', false)
      // .col('plntCd', '플랜트 코드', { visible: false })
      // .col('addSmpIdx', '추가 검체 IDX', { visible: false })
      // .col('pitmTyp', '자재유형', { visible: false })
      // .col('pitmTypNm', '자재유형')
      // .col('smpDivCd', '검체구분', { visible: false })
      // .col('smpDivCdNm', '검체구분')
      // .col('ansTyp', '시험유형', { visible: false })
      // .col('ansTypNm', '시험유형')
      // .col('ansProcCd', '시험 진행상태 코드', { visible: false })
      // .col('ansProcCdNm', '시험 진행상태')
      // .col('labelPrtProc', '라벨 출력 프로세스', { visible: false })
      // .col('labelPrtProcNm', '라벨 출력 프로세스')
      // .col('pitmCd', '원료코드')
      // .col('pitmNm', '검체라벨 원료명')
      // .col('crtNo', '생산번호')
      // .col('ansNo', '시험번호', { width: 90 })
      // .col('batchNo', '배치번호')
      // .col('lotNo', '제조번호')
      // .col('makDt', '제조일자')
      // .col('mtrCd', '자재번호')
      // .col('reqDt', '의뢰일자')
      // .col('rcpDt', '접수일자')
      // .col('assDt', '지시일자')
      // .col('useLmt', '사용기한')
      // .col('etrQty', '입고수량')
      // .col('inpUnit', '입력단위')
      // .col('etrDt', '입고일자')
      // .col('splCd', '공급사코드')
      // .col('splNm', '공급사명')
      // .col('splLotNo', '공급사제조번호')
      // .col('csmNm', '고객처명')
      // .col('smpVolTot', '총검체량')
      // .col('clltUidNm', '채취자')
      // .col('clltDt', '채취일자')
      // .col('labelCd', '라벨코드')
      // .col('rptDiv', '리포트 구분', { visible: false })
      // .col('rptDivNm', '리포트 구분')
      // .col('smpRmk', '검체 비고', { visible: false })
      // .col('ansIdx', '시험 IDX', { visible: false })
      // .col('labelPrtIdx', '라벨 출력 IDX', { visible: false })
      // .col('prtSeq', '출력 순번', { visible: false })
      .build(),
};

const buttonGroups = {
  buttons: [
    { name: 'choose', label: '선택' },
    { name: 'close', label: '닫기' },
  ],
};

export default {
  labelListGridWithForms,
  buttonGroups,
};

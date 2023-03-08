import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAgoDate = dayjs().add(-1, 'M').format('YYYY-MM-DD');

const searchForm = {
  static: {
    $grid: null,
    props: {
      editable: false,
    },
    countPerRow: 3,
    buttons: [{ name: 'select', label: '조회' }],
    height: '400px',
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Select('ansTyp', '시험구분', { async: () => api.combo.systemCommon.getAnsTypCombo() })
      .Select('pitmTyp', '자재구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmNm', '자재내역')
      .Input('pitmCd', '자재번호')
      .Input('ansNo', '시험번호')
      .Input('ispReqNo', '검사요청번호')
      .Input('phsOrderNo', '구매오더번호')
      .Input('batchNo', '배치번호')
      .Input('lotNo', '제조번호')
      .DatepickerTwinWithSwitch('searchReqDt', '의뢰일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ansProcNm', '진행상태')
      .col('ansTypNm', '시험구분')
      .col('ansNo', '시험번호', { width: 90 })
      .col('pitmTypNm', '자재구분')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('reqDs', '의뢰일시')
      .col('rcpDs', '접수일시')
      .col('rstAprDs', '승인일시')
      .col('lotNo', '제조번호')
      .col('sytJdgNm', '결과판정')
      .col('rcpRmk', '접수비고', { width: 120 })
      .col('clltRmk', '채취비고', { width: 120 })
      .col('assSpcc', '시험지시특이사항', { width: 120 })
      .col('revwCmmt', '검토의견', { width: 120 })
      .col('pitmSpcc', '자재특이사항', { width: 120 })
      .col('nonCfmRea', '부적합사유', { width: 120 })
      .build(),
};

export default {
  searchForm,
};

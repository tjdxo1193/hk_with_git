import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');

const searchForm = {
  static: {
    $grid: null,
    props: {
      editable: false,
    },
    countPerRow: 4,
    buttons: [{ name: 'select', label: '조회' }],
    height: '450px',
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Select('ansTyp', '시험구분', { async: () => api.combo.systemCommon.getAnsTypCombo() })
      .Select('pitmTyp', '품목구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmNm', '품목명')
      .Input('pitmCd', '품목코드')
      .Select('ansProcCd', '진행상태', {
        async: () => api.combo.systemCommon.getAnsProcCombo(),
      })
      .Input('ansNo', '시험번호')
      .Input('ispReqNo', '검사요청번호')
      .Input('phsOrderNo', '구매오더번호')
      .Input('batchNo', '배치번호')
      .Input('lotNo', '제조번호')
      .DatepickerTwinWithSwitch('searchReqDt', '의뢰일', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ansProcNm', '진행상태')
      .col('ansTypNm', '시험구분')
      .col('ansNo', '시험번호', { width: 90 })
      .col('pitmTypNm', '품목구분')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('reqDt', '의뢰일자')
      .col('rcpDt', '접수일자')
      .col('lotNo', '제조번호')
      .col('sytJdgNm', '결과판정')
      .col('assSpcc', '시험지시특이사항', { width: 120 })
      .col('revwCmmt', '검토의견', { width: 120 })
      .col('pitmSpcc', '품목특이사항', { width: 120 })
      .col('nonCfmRea', '부적합사유', { width: 120 })
      .build(),
};

export default {
  searchForm,
};

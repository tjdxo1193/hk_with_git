import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');
// 조회 폼
const inquireForm = {
  static: {
    title: '조회',
    countPerRow: 2,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '자재구분', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .required()
      .Input('pitmNm', '자재내역')
      .DatepickerTwinWithSwitch('reqDtBetween', '의뢰일', { value: [todayDate, todayDate] })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmTyp', '자재구분', { visible: false })
      .col('pitmTypNm', '자재구분')
      .col('pitmCd', '자재번호', { visible: false })
      .col('pitmNm', '자재내역')
      .col('ansTyp', '시험종류', { visible: false })
      .col('ansTypNm', '시험종류')
      .col('ansNo', '시험번호')
      .col('reqDt', '의뢰일자')
      .col('reqUidNm', '의뢰자')
      .col('reqUid', '의뢰자', { visible: false })
      .col('reqDptCdNm', '의뢰부서')
      .col('reqDptCd', '의뢰부서', { visible: false })
      .col('ansIdx', '시험 IDX', { visible: false })
      .col('clltDt', '채취일자', { visible: false })
      .col('clltUid', '채취 UID', { visible: false })
      .col('clltUidNm', '채취자', { visible: false })
      .build(),
};

export default {
  inquireForm,
};

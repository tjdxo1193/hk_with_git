import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const weekAgoDate = dayjs().add(-1, 'week').format('YYYY-MM-DD');

const performanceGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('pdtOrderNo', '생산오더번호')
      .Input('pitmCd', '품목코드')
      .Input('mtrNm', '품목명')
      .Input('batchNo', '배치번호')
      .DatepickerTwin('etrDtList', '입고일자', { value: [weekAgoDate, todayDate] })

      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('reqIdx')
      .col('pdtOrderNo', '생산오더번호')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('etrDt', '입고일')
      .col('batchNo', '배치번호')
      .build(),
};

const detailGrid = {
  static: {
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '플랜트')
      .col('reqIdx', false)
      .col('ispReqNo', false)
      .col('ispReqDt', false)
      .col('mtrNm', '품목명')
      .col('batchNo', '배치번호')
      .col('etrQty', false)
      .col('inpUnit', false)
      .col('savePla', false)
      .col('etrDt', '입고일자')
      .col('amtUnit', false)
      .col('lotNo', '제조번호')
      .col('pdtOrderTyp', false)
      .col('pdtOrderNo', '생산오더번호')
      .col('udtDs', false)
      .col('revDs', false)
      .col('pitmCd', '품목코드')
      .col('pitmTyp', '품목유형')
      .col('pitmNm', '품목명')
      .build(),
};

export default {
  performanceGrid,
  detailGrid,
};

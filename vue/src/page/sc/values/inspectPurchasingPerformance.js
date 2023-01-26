import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const weekAgoDate = dayjs().add(-1, 'month').format('YYYY-MM-DD');

const performanceGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ value: 'search', label: '조회' }],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('phsOrderNo', '구매오더번호')
      .Input('pitm', '품목코드')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .Input('pitmNm', '품목명')
      .DatepickerTwin('etrDtList', '입고일자', { value: [weekAgoDate, todayDate] })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('phsOrderNo', '구매오더번호')
      .col('pitmCd', '품목코드')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('pitmNm', '품목명')
      .build(),
};

const detailGrid = {
  static: {
    title: '상세조회',
    countPerRow: 4,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('reqIdx', false)
      .col('ispReqNo', false)
      .col('ispReqDt', '시험의뢰일자')
      .col('batchNo', '배치번호')
      .col('etrQty', '입고수량')
      .col('inpUnit', '입력단위')
      .col('savePla', false)
      .col('etrDt', '입고일자')
      .col('lotNo', '제조번호')
      .col('phsOrderTyp', false)
      .col('phsOrderNo', '구매오더번호')
      .col('udtDs', false)
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .build(),
};

export default {
  performanceGrid,
  detailGrid,
};

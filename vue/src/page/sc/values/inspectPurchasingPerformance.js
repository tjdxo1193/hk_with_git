import { ColumnBuilder, FormBuilder } from '@/util';

const searchForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('fiscalYr', '회계연도')
      .Select('month', '기간(월)', { elements: monthList })
      .Input('mtrCd', '자재코드')
      .Input('mtrNm', '자재명')
      .Input('phsOrderNo', 'Order No.')
      .build(),
};

const itemGrid = {
  static: {
    title: '조회결과',
    props: {
      editable: false,
      showRowCheckColumns: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('fiscalYr', '회계연도')
      .col('month', '기간(월)')
      .col('mtrCd', '자재코드')
      .col('mtrNm', '자재명')
      .col('phsOrderNo', 'OrderNo.')
      .col('etrQty', '입고량')
      .col('ansCnt', '검사횟수')
      .col('ifYn', '연계여부')
      .col('ifDs', '연계일시')
      .build(),
};

const requestGrid = {
  static: {
    title: '조회결과',
    props: {
      editable: false,
      showRowCheckColumns: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('reqDt', '의뢰일자')
      .col('ispReqNo', '검사요청번호')
      .col('rcpDt', '접수일자')
      .col('ansNo', '시험번호')
      .col('ftnYn', '기능성여부')
      .build(),
};

const monthList = [
  { value: '1', label: '1월' },
  { value: '2', label: '2월' },
  { value: '3', label: '3월' },
  { value: '4', label: '4월' },
  { value: '5', label: '5월' },
  { value: '6', label: '6월' },
  { value: '7', label: '7월' },
  { value: '8', label: '8월' },
  { value: '9', label: '9월' },
  { value: '10', label: '10월' },
  { value: '11', label: '11월' },
  { value: '12', label: '12월' },
];

export default {
  searchForm,
  itemGrid,
  requestGrid,
};

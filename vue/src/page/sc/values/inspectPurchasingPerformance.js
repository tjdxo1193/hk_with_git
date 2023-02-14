import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const oneYearAgo = dayjs().subtract(1, 'year').year();
const twoYearAgo = dayjs().subtract(2, 'year').year();
const year = dayjs().year();
const month = dayjs().format('MM');

const yearList = [
  { value: twoYearAgo, label: twoYearAgo + '년' },
  { value: oneYearAgo, label: oneYearAgo + '년' },
  { value: year, label: year + '년' },
];

const monthList = [
  { value: '01', label: '1월' },
  { value: '02', label: '2월' },
  { value: '03', label: '3월' },
  { value: '04', label: '4월' },
  { value: '05', label: '5월' },
  { value: '06', label: '6월' },
  { value: '07', label: '7월' },
  { value: '08', label: '8월' },
  { value: '09', label: '9월' },
  { value: '10', label: '10월' },
  { value: '11', label: '11월' },
  { value: '12', label: '12월' },
];

const performanceGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'search', label: '조회' },
      { name: 'send', label: '연계전송' },
    ],
    props: {
      editable: false,
      showRowCheckColumn: true,
      independentAllCheckBox: true,
      rowCheckDisabledFunction: function (rowIndex, isChecked, item) {
        const succeedSend = 'S0340002';
        if (item.ifDate != null && item.ifStt == succeedSend) {
          return false;
        }
        return true;
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('fiscalYr', '년', { elements: yearList, value: year })
      .required()
      .Select('month', '월', { elements: monthList, value: month })
      .required()
      .blank(2)
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ispPhsPfaIdx', false)
      .col('plntCd', false)
      .col('plntNm', '사업장')
      .col('phsOrderNo', '구매오더번호')
      .col('pitmTyp', false)
      .col('pitmTypNm', '품목유형')
      .col('mtrCd', '자재코드')
      .col('pitmNm', '품목명')
      .col('etrQty', '입고수량')
      .col('ispScr', '검사실적')
      .col('ifStt', false)
      .col('ifSttNm', '연계여부', {
        styleFunction: function (rowIndex, columnIndex, value, headerText, item) {
          const succeedSend = 'S0340002';
          const fail = 'S0340003';
          if (item.ifStt == succeedSend) {
            return 'success';
          }
          if (item.ifStt == fail) {
            return 'fail';
          }
          return null;
        },
      })
      .col('ifDate', '연계일시')
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
      .col('plntNm', '사업장')
      .col('phsOrderNo', '구매오더번호')
      .col('pitmTyp', false)
      .col('pitmTypNm', '품목유형')
      .col('mtrCd', '자재코드')
      .col('pitmNm', '품목명')
      .col('batchNo', '배치번호')
      .col('savePla', false)
      .col('etrDt', '입고일')
      .col('etrQty', '입고수량')
      .col('inpUnit', '입력단위')
      .col('ispScr', '검사실적')
      .col('ispReqNo', '검사요청번호')
      .col('ispReqDt', '검사요청일자')
      .col('splLotNo', '공급사제조번호')
      .build(),
};

export default {
  performanceGrid,
  detailGrid,
};

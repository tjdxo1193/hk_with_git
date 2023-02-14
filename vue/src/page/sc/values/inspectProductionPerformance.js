import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const weekAgoDate = dayjs().add(-1, 'week').format('YYYY-MM-DD');

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
      .DatepickerTwin('etrDtList', '입고일자', { value: [weekAgoDate, todayDate] })
      .spanCol(2)
      .blank()
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ispPdtPfaIdx', false)
      .col('pdtOrderNo', '생산오더번호')
      .col('pitmTypNm', '품목유형')
      .col('mtrCd', '자재코드')
      .col('pitmNm', '품목명')
      .col('batchNo', '배치번호')
      .col('etrDt', '입고일')
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
      .col('ispPfaCanlYn', '검사실적 취소여부')
      .build(),
};

const detailGrid = {
  static: {
    props: {
      editable: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('pdtOrderNo', '생산오더번호')
      .col('pitmTypNm', '품목유형')
      .col('mtrCd', '자재코드')
      .col('pitmNm', '품목명')
      .col('batchNo', '배치번호')
      .col('etrDt', '입고일')
      .col('ispScr', '검사실적')
      .col('ispReqNo', '검사요청번호')
      .col('ispReqDt', '검사요청일자')
      .col('sytJdg', '적부판정')
      .col('ansProcCd', '진행상태')
      .col('plntCd', false)
      .col('plntNm', '사업장')
      .build(),
};

export default {
  performanceGrid,
  detailGrid,
};

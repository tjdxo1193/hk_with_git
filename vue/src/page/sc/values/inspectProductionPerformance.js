import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAgoDate = dayjs().add(-1, 'month').format('YYYY-MM-DD');

const searchGridWithForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('mtrCd', '자재코드')
      .Input('mtrNm', '자재명')
      .DatepickerTwinWithSwitch('reqDtList', '의뢰일자', { value: [monthAgoDate, todayDate] })
      .spanCol(2)
      .Input('phsOrderNo', 'Order No.')
      .Input('lotNo', 'Lot No.')
      .Input('vatNo', 'Batch')
      .Input('ansReqNo', '검사요청번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('reqDt', '의뢰일자')
      .col('mtrCd', '자재코드')
      .col('mtrNm', '자재명')
      .col('phsOrderNo', 'OrderNo')
      .col('lotNo', 'LotNo')
      .col('batchNo', 'Batch')
      .col('ispReqNo', '검사요청번호')
      .col('rcpDt', '접수일자')
      .col('ansNo', '시험번호')
      .col('j', '검사횟수')
      .col('ftnYn', '기능성유무')
      .col('ifYn', '연계여부')
      .col('ifDs', '연계일시')
      .build(),
};

export default {
  searchGridWithForm,
};

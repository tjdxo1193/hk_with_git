import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAgoDate = dayjs().add(-1, 'M').format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    props: { editable: false, showRowCheckColumn: true },
    buttons: [
      { name: 'requestCancel', label: '의뢰취소' },
      { name: 'request', label: '의뢰' },
      { name: 'select', label: '조회' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('mitmNm', '시험항목')
      .Select('mitmWrkPlcDivNm', '작업실')
      .Input('point', '포인트')
      .Input('roomno', 'RoomNo')
      .DatepickerTwinWithSwitch('searchAnsEdt', '시험예정일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('mitmNm', '시험항목')
      .col('mitmCd', '시험항목코드')
      .col('mitmWrkPlcDivNm', '작업실')
      .col('roomno', 'RoomNo')
      .col('grade', 'Grade')
      .col('ansCylRuleNm', '시험주기')
      .col('ansEdt', '시험예정일')
      .col('point', '포인트')
      .col('crgDptNm', '담당부서')
      .build(),
};

export default {
  list,
};

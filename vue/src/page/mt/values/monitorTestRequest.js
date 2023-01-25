import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 3,
    $grid: null,
    props: { editable: false, showRowCheckColumn: true },
    buttons: [
      { name: 'select', label: '조회' },
      { name: 'requestCancel', label: '의뢰취소' },
      { name: 'request', label: '의뢰' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('mitmNm', '시험항목')
      .Select('mitmWrkPlcDivNm', '작업실')
      .Input('point', '포인트')
      .Select('ansCylRuleNm', '시험주기')
      .Input('roomno', 'RoomNo')
      .DatepickerTwinWithSwitch('ansEdt', '시험예정일', {
        value: [todayDate, todayDate],
      })
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

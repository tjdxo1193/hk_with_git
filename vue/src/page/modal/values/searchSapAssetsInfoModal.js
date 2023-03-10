import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const aMonthAgo = dayjs().add(-1, 'year').format('YYYY-MM-DD');

const assetsList = {
  static: {
    $grid: null,
    props: {
      editable: false,
    },
    countPerRow: 4,
    buttons: [
      { name: 'select', label: '선택' },
      { name: 'search', label: '조회' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .DatepickerTwin('searchPrtDt', '조회일자', { value: [aMonthAgo, todayDate] })
      .spanCol(4)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('anln1', '주요자산번호')
      .col('anln2', '자산하위번호')
      .col('anlkl', '자산클래스')
      .col('txt50', '자산내역(자산명)')
      .col('erdat', '생성일')
      .col('aedat', '변경일자')
      .col('zugdt', '첫취득일')
      .col('deakt', '비활성화일')
      .col('txa50', '추가설명')
      .col('zzpic', '담당자사번')
      .col('kostl', '코스트센터')
      .build(),
};

export default {
  assetsList,
};

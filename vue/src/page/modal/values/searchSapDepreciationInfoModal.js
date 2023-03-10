import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');

const depreciationList = {
  static: {
    $grid: null,
    props: { editable: false },
    buttons: [
      { name: 'select', label: '선택' },
      { name: 'search', label: '조회' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Datepicker('bzdat', '자산기준일', { value: todayDate })
      .spanCol(4)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('bukrs', '회사코드')
      .col('anln1', '주요자산번호')
      .col('anln2', '자산하위번호')
      .col('anlkl', '자산클래스')
      .col('kansw', '취득가액')
      .col('kumafa', '감가상각누계액')
      .col('bzdat', '자산기준일')
      .build(),
};

export default {
  depreciationList,
};

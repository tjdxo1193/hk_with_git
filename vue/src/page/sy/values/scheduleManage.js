import api from '@/api';
import { FormBuilder, ColumnBuilder } from '@/util';

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

const calListWithSearch = {
  static: {
    title: '일자관리',
    countPerRow: 4,
    $grid: null,
    height: '500px',
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('year', '년', {
        async: () => api.combo.common.getYear(),
      })
      .Select('month', '월', { elements: monthList })
      .RadioGroup('workYn', '근무여부', {
        value: '',
        gap: 50,
        groups: [
          { checkedValue: '', label: '전체' },
          { checkedValue: 'Y', label: '근무함', width: '80px' },
          { checkedValue: 'N', label: '근무안함', width: '80px' },
        ],
      })
      .blank()
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ident', '공장달력')
      .col('year', '년')
      .col('month', '월')
      .col('day', '일')
      .col('caldDesc', '달력설명')
      .col('workYn', '근무여부')
      .col('caldDT', '날짜')
      .build(),
};
export default {
  calListWithSearch,
};

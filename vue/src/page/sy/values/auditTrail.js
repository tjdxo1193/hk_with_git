import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const today = dayjs();

const format = (date) => date.format('YYYY-MM-DD');

const audit = {
  static: {
    title: 'inquireData',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('menuCode', '메뉴', {
        defaultOption: {
          label: '전체',
        },
        async: api.audit.getLeafMenusComboForAudit,
      })
      .Input('ip', 'ip')
      .DatepickerTwin('searchRange', '저장일자', {
        value: [format(today.subtract(7, 'day')), format(today)],
      })
      .required()
      .spanCol(2)
      .build(),
  columns: () => ColumnBuilder.builder().build(),
};

export default {
  audit,
};

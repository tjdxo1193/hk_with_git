import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const today = dayjs().format('YYYY-MM-DD');

const printManage = {
  static: {
    title: '조회',
    countPerRow: 2,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('selectDiv', '조회분류', {})
      .DatepickerTwinWithSwitch('useDt', '사용일', {
        value: [today, today],
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('1', '일련번호', false)
      .col('2', '조회분류')
      .col('3', '리포트명')
      .col('4', '출력물명')
      .col('5', '사용시작일')
      .col('6', '사용종료일')
      .col('7', 'SOP No.')
      .build(),
};

const printManageInfo = {
  static: {
    title: '정보입력',
    countPerRow: 3,
    buttons: [
      { name: 'save', label: '등록' },
      { name: 'update', label: '수정', disabled: true },
      { name: 'delete', label: '삭제', disabled: true, type: 'danger' },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('1', '조회분류')
      .required()
      .Datepicker('2', '사용시작일', { value: today })
      .Input('3', 'SOP No.')
      .Select('4', '리포트명')
      .required()
      .Datepicker('5', '사용종료일', { value: today })
      .Textarea('6', '비고', { rows: 3 })
      .spanRow(2)
      .Select('7', '출력물명')
      .required()
      .blank()
      .build(),
};

export default {
  printManage,
  printManageInfo,
};

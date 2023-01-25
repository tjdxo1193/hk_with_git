import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const searchForm = {
  static: {
    title: '조회',
    countPerRow: 2,
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('1', '품목구분')
      .Input('2', '라벨번호')
      .Input('3', '품목코드')
      .Input('4', '제조번호')
      .Input('5', '품목명')
      .DatepickerTwinWithSwitch('6', '사용일', { value: [todayDate, todayDate] })
      .build(),
};

const gridForSearchResult = {
  static: {
    title: '조회결과',
    $grid: null,
    data: [{}, {}, {}],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('1', '승인구분')
      .col('2', '품목구분')
      .col('3', '품목코드')
      .col('4', '품목명')
      .col('5', '공정명')
      .col('6', '제조번호')
      .col('7', '안정성검체량')
      .col('8', '사용량')
      .col('9', '재고량')
      .col('10', '안정성검체단위')
      .col('11', '사용자')
      .col('12', '사용일')
      .col('13', '사용목적')
      .col('14', '이상여부')
      .col('15', '검체상태')
      .col('16', '사용이력진행상황')
      .col('17', '반려여부')
      .col('18', '반려일')
      .col('19', '반려사유')
      .build(),
};

const inputInfoForm = {
  static: {
    title: '입력정보',
    countPerRow: 2,
  },

  forms: () =>
    FormBuilder.builder()
      .Input('1', '품목명')
      .readonly()
      .Input('1', '보관단위')
      .readonly()
      .Input('1', '시험번호')
      .readonly()
      .Input('1', '재고량')
      .readonly()
      .Input('1', '제조번호')
      .readonly()
      .Input('1', '사용량')
      .readonly()
      .Select('1', '사용자')
      .readonly()
      .Textarea('1', '사용목적')
      .spanRow(2)
      .readonly()
      .Datepicker('1', '사용일', { value: todayDate })
      .readonly()
      .build(),
};

const buttonGroups = {
  buttons: [
    { name: 'approve', label: '승인', disabled: true },
    { name: 'reject', label: '반려', disabled: true },
    { name: 'init', label: '초기화' },
  ],
};

export default {
  searchForm,
  gridForSearchResult,
  inputInfoForm,
  buttonGroups,
};

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
      .Input('2', '제조번호')
      .Input('3', '품목코드')
      .Select('4', '보관장소')
      .Input('5', '품목명')
      .DatepickerTwinWithSwitch('6', '사용(유효)기한', { value: [todayDate, todayDate] })
      .build(),
};

const gridForSearchResult = {
  static: {
    title: '조회결과',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('1', '승인구분')
      .col('1', '품목구분')
      .col('2', '품목코드')
      .col('3', '공정명')
      .col('4', '품목명')
      .col('5', '제조번호')
      .col('6', '제조일')
      .col('7', '사용(유효)기한')
      .col('8', '시험목적')
      .col('9', '상세시험목적')
      .col('10', '시험종류')
      .col('11', '보관장소')
      .col('12', '보관조건')
      .col('13', '포장형태')
      .col('14', '재고량')
      .col('15', '안정성검체량')
      .col('16', '안정성검체량단위')
      .build(),
};

const buttonGroups = {
  buttons: [
    { name: 'approve', label: '승인' },
    { name: 'reject', label: '반려' },
    { name: 'init', label: '초기화' },
  ],
};

export default {
  searchForm,
  gridForSearchResult,
  buttonGroups,
};

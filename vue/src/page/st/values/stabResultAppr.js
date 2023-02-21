import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const searchForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('1', '시험종류')
      .Input('2', '자재내역')
      .Select('3', '진행상황')
      .DatepickerTwinWithSwitch('6', '시작일', { value: [todayDate, todayDate] })
      .Input('4', '제조번호')
      .Input('5', '자재번호')
      .blank()
      .blank()
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
      .col('2', '승인비고')
      .col('3', '자재번호')
      .col('4', '공정명')
      .col('5', '폼목명')
      .col('6', '제조번호')
      .col('7', '제조/입고일자')
      .col('8', '시험종류')
      .col('9', '시험목적')
      .col('10', '상세시험목적')
      .col('11', '보관조건')
      .col('12', '보관장소')
      .col('13', '안정성검체수량')
      .col('14', '안정성검체단위')
      .col('15', '포장수량')
      .col('16', '포장단위')
      .col('17', '포장상태')
      .col('18', '시험예정일')
      .col('19', '개월차수')
      .col('20', '시험시작일')
      .col('21', '시험종료일')
      .col('22', '시험주기')
      .col('23', '시험기간')
      .col('24', '담당자')
      .col('25', '시험상태')
      .col('26', '비고')
      .col('27', '문서번호')
      .col('28', '제조수량')
      .col('29', '제조단위')
      .col('30', '유효기간')
      .col('31', '허가규격')
      .col('32', '등록자')
      .col('33', '사용기간')
      .col('34', '승인순서')
      .col('35', '총승인원수')
      .col('36', '승인구분코드')
      .col('37', '안정성승인차수')
      .col('38', '승인가능여부')
      .build(),
};

const gridForItemSetting = {
  static: {
    title: '항목설정',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () => ColumnBuilder.builder().build(),
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
  gridForItemSetting,
  buttonGroups,
};

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
      .Select('1', '자재구분')
      .required()
      .Select('2', '포장형태')
      .Input('10', '제조번호')
      .DatepickerTwinWithSwitch('3', '등록일', { value: [todayDate, todayDate] })
      .Input('4', '자재번호')
      .required()
      .Select('5', '보관장소')
      .Select('11', '담당자')
      .DatepickerTwinWithSwitch('6', '의뢰일', { value: [todayDate, todayDate] })
      .Input('7', '자재내역')
      .required()
      .Select('8', '시험종류')
      .blank()
      .DatepickerTwinWithSwitch('9', '채취일', { value: [todayDate, todayDate] })
      .build(),
};

const legends = [
  { className: 'weird', value: '이상' },
  { className: 'disposal', value: '폐기' },
];

const gridForSearchResult = {
  static: {
    title: '조회결과',
    legends: legends,
    $grid: null,
    data: [{ 1: '시험종류' }, { 1: '시험종류2' }, { 1: '시험종류3' }],

    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('1', '시험종류')
      .col('2', '자재구분')
      .col('3', '자재번호')
      .col('4', '자재내역')
      .col('5', '영문명')
      .col('6', '공장명')
      .col('7', '시험번호')
      .col('8', '제조번호')
      .col('9', '의뢰특이사항')
      .col('10', '보관장소')
      .col('11', '보관조건')
      .col('12', '제조일')
      .col('13', '사용(유효)기한')
      .col('14', '재고량')
      .col('15', '안정성검체량')
      .col('16', '안정성검체단위')
      .col('17', '포장수량')
      .col('18', '포장단위')
      .col('19', '포장형태')
      .col('20', '폐기일자')
      .col('21', '폐기사유')
      .col('22', '비고')
      .col('23', '검체상태')
      .col('24', '이상여부')
      .build(),
};

const inputInfoForm = {
  static: {
    title: '입력정보',
    countPerRow: 4,
  },

  forms: () =>
    FormBuilder.builder()
      .multiple(
        '1',
        '자재내역',
        FormBuilder.builder()
          .Input('11')
          .readonly()
          .spanCol(5)
          .Button('search', 'stabItemSearchModal', { type: 'search' })
          .build(),
      )
      .required()
      .Input('1', '시험번호')
      .readonly()
      .Select('1', '보관장소')
      .required()
      .Datepicker('1', '제조일', { value: todayDate })
      .required()
      .Input('1', '영문명')
      .readonly()
      .Input('1', '제조번호')
      .readonly()
      .Select('1', '보관조건')
      .required()
      .DatepickerWithSwitch('1', '사용(유효)기한', { value: todayDate })
      .multiple(
        '1',
        '안전검체량',
        FormBuilder.builder().Input('12').required().Select('13').required().build(),
      )
      .RadioGroup('Yn', '이상여부', {
        value: '1',
        groups: [
          { checkedValue: '1', label: '정상' },
          { checkedValue: '0', label: '이상' },
        ],
        gap: 60,
      })
      .Select('1', '포장형태')
      .required()
      .Textarea('1', '비고')
      .build(),
};

const buttonGroups = {
  buttons: [
    { name: 'save', label: '저장' },
    { name: 'update', label: '수정', disabled: true },
    { name: 'delete', label: '삭제', disabled: true },
    { name: 'init', label: '초기화' },
  ],
};

export default {
  searchForm,
  gridForSearchResult,
  inputInfoForm,
  buttonGroups,
};

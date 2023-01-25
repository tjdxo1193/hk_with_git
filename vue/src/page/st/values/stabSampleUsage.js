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
      .Select('1', '품목구분')
      .required()
      .Input('3', '품목코드')
      .Input('5', '품목명')
      .required()
      .DatepickerTwinWithSwitch('6', '사용일', { value: [todayDate, todayDate] })
      .Input('2', '라벨번호')
      .Input('4', '제조번호')
      .required()
      .build(),
};

const legends = [
  { className: 'tempSave', value: '임시저장' },
  { className: 'approveWating', value: '승인대기중' },
  { className: 'return', value: '반려' },
  { className: 'weird', value: '이상' },
  { className: 'disposal', value: '폐기' },
];

const gridForSearchResult = {
  static: {
    title: '조회결과',
    legends: legends,
    $grid: null,
    data: [{ div: '1' }, { div: '2' }, { div: '3' }, { div: '4' }],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('1', '품목구분')
      .col('2', '품목코드')
      .col('3', '품목명')
      .col('4', '공정명')
      .col('5', '제조번호')
      .col('6', '시험종류')
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
    countPerRow: 4,
  },

  forms: () =>
    FormBuilder.builder()
      .multiple(
        '1',
        '품목명',
        FormBuilder.builder()
          .Input('11', { readonly: true })
          .spanCol(5)
          .Button('search', 'stabTargetItemSearchModal', { type: 'search' })
          .build(),
      )
      .Input('1', '보관단위', { readonly: true })
      .Input('1', '시험번호', { readonly: true })
      .Input('1', '재고량', { readonly: true })
      .Input('1', '제조번호', { readonly: true })
      .Input('1', '사용량')
      .required()
      .Select('1', '사용자', { readonly: true })
      .required()
      .Textarea('1', '사용목적')
      .spanRow(2)
      .Datepicker('1', '사용일', { value: todayDate })
      .required()
      .build(),
};

const buttonGroups = {
  buttons: [
    { name: 'useapproveReq', label: '사용승인요청', disabled: true },
    { name: 'save', label: '저장' },
    { name: 'update', label: '수정', disabled: true },
    { name: 'delete', label: '삭제', disabled: true, type: 'danger' },
    { name: 'useCancelReq', label: '사용취소요청', disabled: true },
    { name: 'init', label: '초기화' },
  ],
};

export default {
  searchForm,
  gridForSearchResult,
  inputInfoForm,
  buttonGroups,
};

import { ColumnBuilder, FormBuilder } from '@/util';
// 조회 폼
const inquireForm = {
  static: {
    title: '조회',
    countPerRow: 2,
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('1', '품목구분', {})
      .required()
      .Input('2', '품목코드')
      .Input('3', '품목명')
      .blank()
      .build(),
};

const gridForSearchResult = {
  static: {
    title: '조회결과',
    $grid: null,
    data: [
      {
        1: '1',
        2: '2',
        3: '3',
      },
      {
        1: '3',
        2: '4',
        3: '5',
      },
    ],
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
      .col('5', '시험번호')
      .col('6', '제조번호')
      .col('7', '제조일')
      .col('8', '입고일')
      .col('9', '안정성시험종류')
      .col('10', '안정성시험목적')
      .col('11', '보관장소')
      .col('12', '보관조건')
      .col('13', '안정성검체량')
      .col('14', '안정성검체단위')
      .col('15', '재고량')
      .build(),
};

export default {
  inquireForm,
  gridForSearchResult,
};

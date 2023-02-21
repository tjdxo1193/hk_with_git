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
      .Select('1', '검체구분')
      .required()
      .Input('2', '자재내역')
      .Select('3', '자재구분')
      .required()
      .Input('4', '제조번호')
      .Input('5', '자재번호')
      .Input('6', '시험번호')
      .build(),
};

const gridForSearchResult = {
  static: {
    title: '조회결과',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('1', '시험번호')
      .col('2', '자재번호')
      .col('3', '자재내역')
      .col('4', '제조번호')
      .col('5', '사용(유효)기한')
      .col('6', '제조일')
      .col('7', '시험검체량')
      .col('8', '시험검체량단위')
      .col('9', '보관수량')
      .col('10', '보관수량단위')
      .col('11', '기타검체량')
      .col('12', '기타검체량단위')
      .col('13', '포장수량')
      .col('14', '포장수량단위')
      .col('15', '자재구분')
      .col('16', '영문명')
      .col('17', '공정명')
      .col('18', '보관장소')
      .col('19', '보관방법')
      .col('20', '버전')
      .build(),
};

export default {
  inquireForm,
  gridForSearchResult,
};

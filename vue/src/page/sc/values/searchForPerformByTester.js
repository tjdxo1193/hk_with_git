import { ColumnBuilder, FormBuilder } from '@/util/builder';

// 조회 그리드
const searchForPerformByTesterGrid = {
  static: {
    title: '조회',
    countPerRow: 2,
    $grid: null,
    props: { editable: false },
    buttons: [{ name: 'select', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('01', '자재구분')
      .multiple('02', '진행상황', FormBuilder.builder().Select('03').Select('04').build())
      .DatepickerTwinWithSwitch('05', '승인일')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('01', '시험자ID')
      .col('02', '시험자')
      .col('03', '총건수(자재)')
      .col('04', '시간내완료건수자재')
      .col('05', '시간초과건수자재')
      .col('06', '백분율자재')
      .col('07', '총건수항목')
      .col('08', '시간내완료건수항목')
      .col('09', '시간초과건수항목')
      .col('10', '백분율항목')
      .col('11', '공수시간(노무)')
      .col('12', '공수시간(기기)')
      .build(),
};

// 레코드 색 정의
// 표준완료일초과: rgb(222, 184, 135)
const legends = [{ className: 'exceedCompleteDate', value: '표준완료일초과' }];

// Test History 그리드
const testHistoryGrid = {
  static: {
    title: 'Test History',
    $grid: null,
    props: { editable: false },
    legends: legends,
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('01', '자재구분')
      .col('02', '시험번호')
      .col('03', '공정코드')
      .col('04', '공정명')
      .col('05', '자재내역')
      .col('06', '항목명')
      .col('07', '세부항목명')
      .col('08', '의뢰일')
      .col('09', '표준완료일')
      .col('10', '승인일')
      .col('11', '채취수량')
      .col('12', '채취방법')
      .col('13', '채취자')
      .col('14', '긴급여부')
      .build(),
};

export default {
  searchForPerformByTesterGrid,
  legends,
  testHistoryGrid,
};

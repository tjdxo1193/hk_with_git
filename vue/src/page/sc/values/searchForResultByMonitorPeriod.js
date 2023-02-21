import { ColumnBuilder, FormBuilder } from '@/util/builder';

const workplaceSearchGrid = {
  static: {
    title: '조회',
    countPerRow: 2,
    $grid: null,
    props: { editable: false },
    buttons: [{ name: 'select', label: '작업실조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('01', '시험구분')
      .Select('02', '시험명')
      .Select('03', '작업소')
      .DatepickerTwinWithSwitch('05', '채취일')
      .Input('06', '포인트')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('01', '작업소')
      .col('02', '작업실')
      .col('03', 'ROOMNO.')
      .col('04', '포인트')
      .col('05', '기기명')
      .col('06', '시험구분')
      .col('07', '클래스')
      .build(),
};

const itemSearchGrid = {
  static: {
    title: '시험항목',
    $grid: null,
    props: { editable: false },
  },
  columns: () =>
    ColumnBuilder.builder().col('01', '선택').col('02', '시험명').col('03', '항목명').build(),
};

const testItemByPointGrid = {
  static: {
    title: '포인트별시험항목',
    $grid: null,
    props: { editable: false },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('01', '선택')
      .col('02', '시험명')
      .col('03', '작업장')
      .col('04', 'ROOMNO')
      .col('05', '항목명')
      .col('06', '포인트')
      .col('07', '기기명')
      .col('08', '허가기준')
      .col('09', '자사기준')
      .build(),
};

const resultSearch = {
  static: {
    title: '결과조회',
    $grid: null,
    props: { editable: false },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('01', '선택')
      .col('02', '작업장')
      .col('03', 'ROOMNO')
      .col('04', '주기')
      .col('05', '결과값')
      .col('06', '포인트')
      .col('07', '기기명')
      .col('08', '항목명')
      .col('09', '허가규격')
      .build(),
};

export default {
  workplaceSearchGrid,
  itemSearchGrid,
  testItemByPointGrid,
  resultSearch,
};

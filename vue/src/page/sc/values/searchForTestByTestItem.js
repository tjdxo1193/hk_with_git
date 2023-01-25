import { ColumnBuilder, FormBuilder } from '@/util';

const searchForTestByTestItemGrid = {
  static: {
    title: '조회',
    countPerRow: 2,
    $grid: null,
    props: { editable: false },
    buttons: [{ name: 'select', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('01', '대분류항목명')
      .Input('02', '품목명')
      .Input('03', '항목명')
      .Input('04', '품목코드')
      .Input('05', '시험번호')
      .DatepickerTwinWithSwitch('06', '승인일')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('01', '대분류항목명')
      .col('02', '항목명')
      .col('03', '변수명')
      .col('04', '시험번호')
      .col('05', '품목코드')
      .col('06', '공정명')
      .col('07', '허가기준')
      .col('08', '자사기준')
      .col('09', '결과값')
      .col('10', '시험자')
      .col('11', '시험담당자')
      .col('12', '승인일')
      .col('13', '진행단계')
      .col('14', '소요시간')
      .build(),
};

export default {
  searchForTestByTestItemGrid,
};

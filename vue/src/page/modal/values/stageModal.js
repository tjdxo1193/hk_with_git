import { ColumnBuilder, FormBuilder } from '@/util';

// 조회 폼
const inquireForm = {
  static: {
    title: '조회',
    countPerRow: 1,
  },
  forms: () => FormBuilder.builder().Select('1', 'stageDiv', {}).build(),
};

// 폼 및 그리드
const inquireResultForm = {
  static: {
    title: '조회결과',
    countPerRow: 4,
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder().Select('1', 'stage', {}).Input('2', 'equation').spanCol(3).build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('01', 'variableNm')
      .col('02', 'formuleResult')
      .col('03', 'interlockDiv')
      .col('04', 'sdmsHeader')
      .col('05', 'standardType')
      .col('06', 'testType')
      .col('07', 'permitCriteria', {
        children: ColumnBuilder.builder()
          .col('701', 'minimum')
          .col('702', 'maximum')
          .col('703', 'description')
          .build(),
      })
      .col('08', 'companyStandard', {
        children: ColumnBuilder.builder()
          .col('801', 'minimum')
          .col('802', 'maximum')
          .col('803', 'description')
          .build(),
      })
      .col('09', 'englishStandard')
      .col('10', 'resultUnit')
      .col('11', 'resultDecimalPoint')
      .col('12', 'standardDecimalPoint')
      .col('13', 'limitOfQuantificationYn')
      .col('14', 'valuesBelowTheLimitOfQuantitation')
      .col('15', 'quantitativeLimitDisplayValue')
      .col('16', 'reportStandardOutputVal')
      .col('17', 'reportOutputItemNm')
      .col('18', 'reportPrintCategoryEngNm')
      .col('19', 'reportOutputYn')
      .col('20', 'requiredYn')
      .col('21', 'note')
      .build(),
};

const buttonGroups = {
  buttons: [
    { name: 'save', label: 'save', disabled: true },
    { name: 'delete', label: 'delete', disabled: true, type: 'danger' },
    { name: 'init', label: 'init', disabled: true },
  ],
};

export default {
  inquireForm,
  inquireResultForm,
  buttonGroups,
};

import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const detailPlanInfo = {
  static: {
    title: '상세계획정보',
    countPerRow: 3,
  },
  forms: () =>
    FormBuilder.builder()
      .Textarea('txtinfo1', { rows: 6 })
      .readonly()
      .Textarea('txtinfo2', { rows: 6 })
      .readonly()
      .Textarea('txtinfo3', { rows: 6 })
      .readonly()
      .build(),
};

const stabEachTestInfo = {
  static: {
    title: '안정성 개별시험정보',
    countPerRow: 3,
    buttons: [
      { name: 'add', label: '추가', disabled: false },
      { name: 'update', label: '수정', disabled: true },
      { name: 'delete', label: '삭제', disabled: true, type: 'danger' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Datepicker('ansDt', '시험일자', { value: todayDate })
      .required()
      .multiple(
        'txtRemark',
        '표시내용',
        FormBuilder.builder()
          .InputNumber('accMarkNm')
          .spanCol(2)
          .required()
          .TextView('ansCylDivNm')
          .Hidden('ansCylDiv')
          .build(),
      )
      .InputNumber('ansSmpVol', '검체량')
      .required()
      .Hidden('sbtPlnIdx', '안정성 계획 IDX')
      .Hidden('sbtAnsIdx', '안정성 시험 IDX')
      .build(),
};

const itemSettingList = {
  static: {
    title: '항목설정',
    $grid: null,
    height: '600px',
    props: {
      enableCellMerge: true,
    },
    legends: [
      { className: 'beforeTest', value: '시험시작전' },
      { className: 'testProgress', value: '시험진행' },
      { className: 'testStop', value: '시험중단' },
      { className: 'textExit', value: '시험종료' },
    ],
  },
  columns: () =>
    ColumnBuilder.builder().col('1', '항목명', { width: 120, cellMerge: true }).build(),
};

const buttonGroups = {
  buttons: [
    { name: 'save', label: '저장' },
    { name: 'reset', label: '초기화', type: 'default' },
  ],
};

export default {
  detailPlanInfo,
  stabEachTestInfo,
  itemSettingList,
  buttonGroups,
};

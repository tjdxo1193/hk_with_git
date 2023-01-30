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

const testData1 = [
  {
    stabilityUnitCode: '2023000601', // 무쓸모(아마 data1, data2 맵핑용 인듯), ☆ 이걸 뭘로 할지가 기술임!!!
    version: '0003', // 무쓸모
    ansDt: '20230105', // 시험 날짜(ST_SBT_ANS - ANS_DT)
    accMarkNm: 'Initial', // ~ 번째 (i, orderNo 등, plntCd와 sbtPlnIdx로 종속된 ST_SBT_ANS의 순서에 따라 출력 + (택 1, Enum의 valueOfCode를 이용해 EnglishNm으로 뽑기 || 쿼리에서 Week, Day 등 뽑기))
    sbtAnsStt: '안정성시험중', // 시험 상태(ST_SBT_ANS - SBT_ANS_STT)
    ansSmpVol: null, // 검체량(ST_SBT_ANS - ANS_SMP_VOL)
    sumTestSample: 1, // 실제로 사용X, 아마 검체량의 총 량일듯.
  },
  {
    stabilityUnitCode: '2023000602',
    version: null,
    ansDt: '20230112',
    accMarkNm: '1 Week',
    sbtAnsStt: '시험시작전',
    ansSmpVol: '.2',
    sumTestSample: 1,
  },
];

const testData2 = {
  table1: [
    // 이 데이터가 아마 체크 확인하는 데이터(실제 값 데이터)
    {
      amitmCd: 'ANAL0000000000000180', // 무쓸모(아마 data1, data2 맵핑용 인듯), (ST_SBT_ANS_AITM, MS_AMITM - AMITM_CD)
      aitmKn: '성상', // 타이틀 용(왼쪽에 시험 종류), (MS_AMITM - AITM_KN)
      orederNo: '1', // 미사용, 그냥 조회 시 정렬용
      lanalName2: '성상', // 미사용
      analName2: '성상', // 미사용
      useYn: '1', // 사용여부(ST_SBT_ANS = ANS_DEL_YN)
      col0: true, // 얘들은 실제 체크하는 데이터
      col3: true,
    },
    {
      amitmCd: 'ANAL0000000000001623',
      aitmKn: '중량',
      orederNo: '2',
      lanalName2: '중량',
      analName2: '중량',
      useYn: '1',
      col0: true,
      col1: true,
      col2: true,
    },
    {
      amitmCd: 'ANAL0000000000001665',
      aitmKn: '총장',
      orederNo: '3',
      lanalName2: '총장',
      analName2: '총장',
      useYn: '1',
      col0: true,
      col3: true,
    },
    {
      amitmCd: 'ANAL0000000000001649',
      aitmKn: '직경',
      orederNo: '4',
      lanalName2: '직경',
      analName2: '직경',
      useYn: '1',
      col0: true,
      col2: true,
      col3: true,
      col4: true,
    },
    {
      amitmCd: 'ANAL0000000000003773',
      aitmKn: '구외경',
      orederNo: '5',
      lanalName2: '구외경',
      analName2: '구외경',
      useYn: '1',
      col0: true,
    },
    {
      amitmCd: 'ANAL0000000000000533',
      aitmKn: '알칼리용출',
      orederNo: '6',
      lanalName2: '알칼리용출',
      analName2: '알칼리용출',
      useYn: '1',
      col0: true,
      col2: true,
      col4: true,
    },
    {
      amitmCd: 'ANAL0000000000000148',
      aitmKn: '비소',
      orederNo: '7',
      lanalName2: '비소',
      analName2: '비소',
      useYn: '1',
      col0: true,
      col4: true,
    },
    {
      amitmCd: 'ANAL0000000000001606',
      aitmKn: '제조원',
      orederNo: '8',
      lanalName2: '제조원',
      analName2: '제조원',
      useYn: '1',
      col0: true,
      col5: true,
    },
  ],
  // table2: [
  //   {
  //     stabilityUnitCode: '2023000601',          // (아마 data1, data2 맵핑용 인듯), ☆ 이걸 뭘로 할지가 기술임!!!
  //     amitmCd: 'ANAL0000000000000180',         // (아마 data2 맵핑용 인듯)
  //     itemName: '성상',                         // 무쓸모
  //     orederNo: '1',                            // 조회 정렬(조회 시에만 사용, 현재 미사용)
  //     testDate: '20230105',                     // 시험 날짜(ST_SBT_ANS - ANS_DT), 미사용
  //     decisionCode: null,                       // 예는 파악 필요.
  //     decision: null,                           // ㄴ 같이 파악 필요
  //   },
  //   {
  //     stabilityUnitCode: '2023000601',
  //     amitmCd: 'ANAL0000000000001623',
  //     itemName: '중량',
  //     orederNo: '2',
  //     testDate: '20230105',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000601',
  //     amitmCd: 'ANAL0000000000001665',
  //     itemName: '총장',
  //     orederNo: '3',
  //     testDate: '20230105',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000601',
  //     amitmCd: 'ANAL0000000000001649',
  //     itemName: '직경',
  //     orederNo: '4',
  //     testDate: '20230105',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000601',
  //     amitmCd: 'ANAL0000000000003773',
  //     itemName: '구외경',
  //     orederNo: '5',
  //     testDate: '20230105',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000601',
  //     amitmCd: 'ANAL0000000000000533',
  //     itemName: '알칼리용출',
  //     orederNo: '6',
  //     testDate: '20230105',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000601',
  //     amitmCd: 'ANAL0000000000000148',
  //     itemName: '비소',
  //     orederNo: '7',
  //     testDate: '20230105',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000601',
  //     amitmCd: 'ANAL0000000000001606',
  //     itemName: '제조원',
  //     orederNo: '8',
  //     testDate: '20230105',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000602',
  //     amitmCd: 'ANAL0000000000001623',
  //     itemName: '중량',
  //     orederNo: '2',
  //     testDate: '20230112',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000603',
  //     amitmCd: 'ANAL0000000000001623',
  //     itemName: '중량',
  //     orederNo: '2',
  //     testDate: '20230119',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000603',
  //     amitmCd: 'ANAL0000000000001649',
  //     itemName: '직경',
  //     orederNo: '4',
  //     testDate: '20230119',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000603',
  //     amitmCd: 'ANAL0000000000000533',
  //     itemName: '알칼리용출',
  //     orederNo: '6',
  //     testDate: '20230119',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000604',
  //     amitmCd: 'ANAL0000000000000180',
  //     itemName: '성상',
  //     orederNo: '1',
  //     testDate: '20230126',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000604',
  //     amitmCd: 'ANAL0000000000001665',
  //     itemName: '총장',
  //     orederNo: '3',
  //     testDate: '20230126',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000604',
  //     amitmCd: 'ANAL0000000000001649',
  //     itemName: '직경',
  //     orederNo: '4',
  //     testDate: '20230126',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000605',
  //     amitmCd: 'ANAL0000000000001649',
  //     itemName: '직경',
  //     orederNo: '4',
  //     testDate: '20230202',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000605',
  //     amitmCd: 'ANAL0000000000000533',
  //     itemName: '알칼리용출',
  //     orederNo: '6',
  //     testDate: '20230202',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000605',
  //     amitmCd: 'ANAL0000000000000148',
  //     itemName: '비소',
  //     orederNo: '7',
  //     testDate: '20230202',
  //     decisionCode: null,
  //     decision: null,
  //   },
  //   {
  //     stabilityUnitCode: '2023000606',
  //     amitmCd: 'ANAL0000000000001606',
  //     itemName: '제조원',
  //     orederNo: '8',
  //     testDate: '20230209',
  //     decisionCode: null,
  //     decision: null,
  //   },
  // ],
};

export default {
  detailPlanInfo,
  stabEachTestInfo,
  itemSettingList,
  buttonGroups,
  testData1,
  testData2,
};

import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');

const searchForm = {
  static: {
    title: '조회',
    countPerRow: 3,
    buttons: [{ name: 'select', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('testDiv', '시험구분')
      .Input('testNo', '시험번호')
      .multiple(
        'a',
        '시험주기',
        FormBuilder.builder()
          .Input('a')
          .spanCol(2)
          .Checkbox('inputResult', { label: '결과입력' })
          .build(),
      )
      .Select('a', '시험명')
      .Input('itemCd', '품목코드')
      .Input('a', 'Grade')
      .Select('a', '작업소')
      .Input('manufactureNo', '제조번호')
      .DatepickerTwinWithSwitch('requestDate', '의뢰일', {
        value: [todayDate, todayDate],
      })
      .Input('a', '작업실')
      .Input('a', '포인트')
      .DatepickerTwinWithSwitch('a', '접수일', {
        value: [todayDate, todayDate],
      })
      .build(),
};

const list = {
  static: {
    title: '조회결과',
    countPerRow: 3,
    $grid: null,
    props: { editable: false },
    height: '200px',
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .col('receiptDate', '접수일자')
      .col('requestDate', '의뢰일자')
      .col('a', '의뢰번호')
      .col('testNo', '시험번호')
      .col('testDiv', '시험구분')
      .col('a', '시험명')
      .col('a', '작업소')
      .col('a', '작업실')
      .col('a', 'RoomNo')
      .col('a', 'Grade')
      .col('a', '시험주기')
      .col('a', '포인트')
      .col('a', '기기명')
      .col('a', '담당자')
      .col('a', '지시특이사항')
      .col('a', '진행상태')
      .col('a', '버전')
      .col('a', '최신 규격서 버전')
      .build(),
};

const itemInfoSearchForm = {
  static: {
    countPerRow: 3,
  },
  forms: () =>
    FormBuilder.builder()
      .Select('testGroup', '그룹')
      .Select('tester1', '시험자1')
      .Select('tester2', '시험자2')
      .build(),
};

const itemInfo = {
  static: {
    $grid: null,
    props: { showRowCheckColumn: true },
    height: '200px',
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .checkbox('testOmittedYn', '시험생략여부')
      .col('largeCategoryNm', '대분류항목명')
      .col('itemName', '항목명')
      .combo('normal1', '일반1', { list: sampleList1 })
      .combo('normal2', '일반2', { async: api.combo.common.getUserList })
      .combo('group', '그룹', {
        descendants: ['tester1', 'tester2'],
        descendantDefaultValues: [],
        async: api.combo.common.getUserList,
      })
      .combo('tester1', '테스터1', {
        ancestor: 'group',
        async: (rowItem) => api.combo.common.getUserList(rowItem),
      })
      .combo('tester2', '테스터2', {
        ancestor: 'group',
        async: (rowItem) => api.combo.common.getUserList(rowItem),
      })
      .build(),
};

const sampleList1 = [
  { value: '1', label: '선택1' },
  { value: '2', label: '선택2' },
  { value: '3', label: '선택3' },
];

const itemDetailInfo = {
  static: {
    $grid: null,
    props: { editable: false },
    height: '200px',
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .col('variableNm', '변수명')
      .col('interlockDiv', '연동구분')
      .col('standardType', '규격유형')
      .col('testType', '시험유형')
      .col('permitCriteria', '허가기준', {
        children: ColumnBuilder.builder()
          .col('minimum', '하한')
          .col('minimumDiv', '하한구분')
          .col('maximum', '상한')
          .col('maximumDiv', '상한구분')
          .col('description', '서술')
          .build(),
      })
      .col('companyStandard', '자사기준', {
        children: ColumnBuilder.builder()
          .col('minimum', '하한')
          .col('minimumDiv', '하한구분')
          .col('maximum', '상한')
          .col('maximumDiv', '상한구분')
          .col('description', '서술')
          .build(),
      })
      .col('resultUnit', '결과단위')
      .col('resultDecimalPoint', '결과소수점')
      .col('standardDecimalPoint', '기준소수점')
      .col('limitOfQuantificationYn', '정량한계여부')
      .col('valuesBelowTheLimitOfQuantitation', '정량한계미만값')
      .col('quantitativeLimitDisplayValue', '정량한계표기값')
      .col('reportOutputStandard', '성적서출력기준')
      .col('reportOutputYn', '성적서출력여부')
      .col('requiredYn', '필수여부')
      .col('note', '비고')
      .build(),
};

const formButtons = {
  buttons: [
    { name: 'addFile', label: '파일등록' },
    { name: 'assignTester', label: '시험자배정' },
    { name: 'init', label: '초기화' },
    //TODO 테스트버튼
    { name: 'addRow', label: '행추가' },
  ],
};

export default {
  searchForm,
  list,
  itemInfoSearchForm,
  itemInfo,
  itemDetailInfo,
  formButtons,
};

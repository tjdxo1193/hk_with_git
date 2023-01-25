import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAgoDate = dayjs().add(-1, 'M').format('YYYY-MM-DD');

const searchForm = {
  static: {
    title: '조회',
    countPerRow: 3,
    buttons: [{ name: 'select', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('itemDiv', '품목구분')
      .Input('itemCd', '품목코드')
      .multiple(
        'testNo',
        '시험번호',
        FormBuilder.builder()
          .Input('testNo')
          .spanCol(2)
          .Checkbox('inputResult', { label: '결과입력' })
          .build(),
      )
      .Select('testDiv', '시험구분')
      .Input('itemNm', '품목명')
      .Input('manufactureNo', '제조번호')
      .Select('processNm', '공정명')
      .DatepickerTwinWithSwitch('requestDate', '의뢰일', {
        value: [monthAgoDate, todayDate],
      })
      .build(),
};

const list = {
  static: {
    title: '조회결과',
    countPerRow: 3,
    $grid: null,
    props: { showRowCheckColumn: false },
    legends: [{ value: '안정성시험', className: 'stabilityTest' }],
    height: '200px',
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .col('requestDate', '의뢰일자')
      .col('receiptDate', '접수일자')
      .col('testDiv', '시험구분')
      .col('itemDiv', '품목구분')
      .col('manufacturePurpose', '제조목적')
      .col('itemCd', '품목코드')
      .col('itemNm', '품목명')
      .col('processNm', '공정명')
      .col('manufactureNo', '제조번호')
      .col('manufacturerNo', '제조처제조번호')
      .col('testNo', '시험번호')
      .col('emergency', '긴급')
      .col('permitStandards', '허가규격')
      .col('manufactureDate', '제조일자')
      .col('warehousingDate', '입고일자')
      .col('requestRemark', '의뢰특이사항')
      .col('receiptRemark', '접수특이사항')
      .col('attachments', '첨부')
      .col('instructionDate', '시험지시일자')
      .col('collectionRemark', '채취특이사항')
      .col('instructionRemark', '시험지시특이사항')
      .col('useVaildPeriod', '사용(유효)기한')
      .col('packingQuantity', '포장수량')
      .col('packingQuantityUnit', '포장수량단위')
      .col('collector', '검체채취(확인)자')
      .col('standardTestEndDate', '표준시험완료일')
      .col('supplier', '공급처')
      .col('manufacturerNm', '제조처')
      .col('version', '버전')
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
    props: {},
    height: '200px',
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .col('testOmittedYn', '시험생략여부')
      .col('largeCategoryNm', '대분류항목명')
      .col('itemName', '항목명')
      .col('testGroup', '시험그룹')
      .col('tester1', '시험자1')
      .col('tester2', '시험자2')
      .build(),
};

const itemDetailInfo = {
  static: {
    $grid: null,
    props: { showRowCheckColumn: false },
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
      .col('reportOutputItemNm', '성적서출력항목명')
      .col('reportOutputYn', '성적서출력여부')
      .col('requiredYn', '필수여부')
      .col('note', '비고')
      .build(),
};

const formButtons = {
  buttons: [
    { name: 'addFile', label: '파일등록', disabled: true },
    { name: 'assignTester', label: '시험자배정' },
    { name: 'init', label: '초기화' },
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

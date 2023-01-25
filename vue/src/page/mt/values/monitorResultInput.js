import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAgoDate = dayjs().add(-1, 'M').format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'select', label: '조회' }],
    $grid: null,
    legends: [
      { value: '안정성시험', className: 'stabilityTest' },
      { value: '반려시험', className: 'returnTest' },
      { value: 'OOS 재시험', className: 'oosReTest' },
    ],
    props: { showRowCheckColumn: false },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperMitmPitmDiv', '품목구분', {
        async: () => api.combo.common.getTreeCd('M1000000'),
      })
      .Select('mitmPitmDiv', '품목명', {
        async: (param) => api.combo.common.getTreeCd(param),
      })
      .Input('mitmCd', '품목코드')
      .Select('crgDptCd', '담당부서', {
        async: api.combo.common.getDpt,
      })
      .Input('ansNo', '시험번호')
      .Input('reqNo', '의뢰번호')
      .DatepickerTwinWithSwitch('formReqDt', '의뢰일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .DatepickerTwinWithSwitch('formAssDt', '지시일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('mitmReqIdx', false)
      .col('ansProcNm', '진행상태')
      .col('reqDt', '의뢰일자')
      .col('reqNo', '의뢰번호')
      .col('ansNo', '시험번호')
      .col('assNo', '지시번호')
      .col('rcpDt', '접수일자')
      .col('mitmCd', '품목코드')
      .col('upperMitmPitmDivNm', '품목구분')
      .col('mitmPitmDivNm', '품목명')
      .combo('sytJdg', '결과판정', {
        async: () => api.combo.systemCommon.getSytTypCombo(),
      })
      .col('upperMitmWrkPlcDivNm', '작업소')
      .col('mitmWrkPlcDivNm', '작업실')
      .col('roomno', 'RoomNo')
      .col('grade', 'Grade')
      .col('ansCylRuleNm', '시험주기')
      .col('ansEdt', '시험예정일')
      .col('point', '포인트')
      .col('crgDptNm', '담당부서')
      .build(),
};

const itemList = {
  static: {
    title: '결과입력',
    $grid: null,
    props: {
      rowStyleFunction: function (rowIndex, item) {
        if (item.rstJdg === 'S0120002') {
          return 'redFont';
        }
        return null;
      },
    },
    height: '300px',
    buttons: [
      { name: 'resultHistory', label: '결과이력', disabled: true },
      { name: 'preResultTrend', label: '이전결과동향', disabled: true },
      { name: 'requestReview', label: '검토요청', disabled: true },
      { name: 'save', label: '저장', disabled: true },
      { name: 'init', label: '초기화', disabled: true },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('mitmReqIdx')
      .Input('upperMitmPitmDivNm', '품목구분', { readonly: true })
      .Input('mitmPitmDivNm', '품목명', { readonly: true })
      .Input('ansNo', '시험번호', { readonly: true })
      .Select('sytJdg', '결과판정', {
        async: () => api.combo.systemCommon.getSytTypCombo(),
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('aitmSpecIdx', false)
      .col('rstSeq', false)
      .col('rstProcNm', '결과진행상태', { editable: false })
      .col('ansDptNm', '시험부서', { editable: false })
      .col('amitmCd', false)
      .col('aitmKn', '시험항목명', { editable: false, width: 150 })
      .col('vriaNo', 'VARIANT<br>NO', { editable: false })
      .col('vriaKn', 'VARIANT<br>국문', { editable: false })
      .col('specTypNm', '규격유형', { editable: false })
      .col('jdgTypNm', '판정유형', { editable: false })
      .col('rstVal', '결과값', {
        editRenderer: {
          type: 'ConditionRenderer',
          conditionFunction: function (rowIndex, columnIndex, value, item) {
            if (item.jdgTyp === 'S0070001') {
              const list = [
                //{ value: '', label: '선택' },
                { value: item.slvJdgCfm, label: item.slvJdgCfmNm },
                { value: item.slvJdgNonCfm, label: item.slvJdgNonCfmNm },
              ];
              return {
                type: 'DropDownListRenderer',
                list,
                keyField: 'value',
                valueField: 'label',
              };
            } else if (item.jdgTyp === 'S0070002' || item.jdgTyp === 'S0070003') {
              return {
                type: 'InputEditRenderer',
                maxlength: 7,
                onlyNumeric: true,
                allowPoint: true,
              };
            } else {
              return {
                type: 'InputEditRenderer',
                maxlength: 50,
              };
            }
          },
        },
        labelFunction: function (rowIndex, columnIndex, value, headerText, item) {
          const list = [
            //{ value: '', label: '선택' },
            { value: item.slvJdgCfm, label: item.slvJdgCfmNm },
            { value: item.slvJdgNonCfm, label: item.slvJdgNonCfmNm },
          ];
          const labelList = list.filter((item) => item.value == value);
          if (labelList[0] != null && item.jdgTyp === 'S0070001') {
            return labelList[0].label;
          } else {
            return value;
          }
        },
        headerStyle: 'editableFontColumn',
      })
      .col('markVal', '표기값', {
        headerStyle: 'editableFontColumn',
        editRenderer: {
          type: 'ConditionRenderer',
          conditionFunction: function (rowIndex, columnIndex, value, item) {
            if (item.jdgTyp === 'S0070001') {
              const list = [
                { value: item.slvJdgCfm, label: item.slvJdgCfmNm },
                { value: item.slvJdgNonCfm, label: item.slvJdgNonCfmNm },
              ];
              return {
                type: 'DropDownListRenderer',
                list,
                keyField: 'value',
                valueField: 'label',
              };
            } else if (item.jdgTyp === 'S0070002' || item.jdgTyp === 'S0070003') {
              return {
                type: 'InputEditRenderer',
                maxlength: 7,
                onlyNumeric: true,
                allowPoint: true,
              };
            } else {
              return {
                type: 'InputEditRenderer',
                maxlength: 50,
              };
            }
          },
        },
        labelFunction: function (rowIndex, columnIndex, value, headerText, item) {
          const list = [
            //{ value: '', label: '선택' },
            { value: item.slvJdgCfm, label: item.slvJdgCfmNm },
            { value: item.slvJdgNonCfm, label: item.slvJdgNonCfmNm },
          ];
          const labelList = list.filter((item) => item.value == value);
          if (labelList[0] != null && item.jdgTyp === 'S0070001') {
            return labelList[0].label;
          } else {
            return value;
          }
        },
      })
      .col('rstRmk', '결과비고', {
        width: 200,
        editRendrer: {
          type: 'InputEditRenderer',
          maxlength: 100,
        },
        headerStyle: 'editableFontColumn',
      })
      .combo('rstJdg', '결과판정', {
        async: () => api.combo.systemCommon.getRstJdgCombo(),
        headerStyle: 'editableFontColumn',
      })
      .col('permitCriteria', '허가기준', {
        children: ColumnBuilder.builder()
          .col('perSlvLow', '하한', { editable: false })
          .col('perSlvUpp', '상한', { editable: false })
          .col('perSlvDesc', '서술', { editable: false })
          .build(),
      })
      .col('companyStandard', '자사기준', {
        children: ColumnBuilder.builder()
          .col('owcSlvLow', '하한', { editable: false })
          .col('owcSlvUpp', '상한', { editable: false })
          .col('owcSlvDesc', '서술', { editable: false })
          .build(),
      })
      .col('slvJdgCfmNm', '기준적합판정', { editable: false })
      .col('slvJdgNonCfmNm', '기준부적합판정', { editable: false })
      .col('rstUnitNm', '결과단위', { editable: false })
      .col('rstDpnt', '결과소수점', { editable: false })
      .combo('rptPrtYn', '성적서<br>출력여부', {
        list: [
          { value: 'Y', label: '출력함' },
          { value: 'N', label: '출력안함' },
        ],
        headerStyle: 'editableBgColumn',
      })
      .col('rptPrtSlvVal', '성적서<br>출력기준', {
        editRendrer: {
          type: 'InputEditRenderer',
          maxlength: 30,
        },
        headerStyle: 'editableBgColumn',
      })
      .col('rptPrtItmNm', '성적서<br>출력항목', {
        editRendrer: {
          type: 'InputEditRenderer',
          maxlength: 30,
        },
        headerStyle: 'editableBgColumn',
      })
      .col('ispDurTm', '검사소요시간', {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 5,
          onlyNumeric: true,
          allowPoint: true,
        },
        headerStyle: 'editableBgColumn',
      })
      .col('eqmUseTm', '기기사용시간', {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 5,
          onlyNumeric: true,
          allowPoint: true,
        },
        headerStyle: 'editableBgColumn',
      })
      .col('ansEqmCd', '분석기기', {
        editRendrer: {
          type: 'InputEditRenderer',
          maxlength: 30,
        },
        headerStyle: 'editableBgColumn',
      })
      .col('smpClltDt', '검체채취일자', {
        editRenderer: {
          type: 'CalendarRenderer',
          maxlength: 10,
          defaultFormat: 'yyyy-mm-dd',
        },
        headerStyle: 'editableBgColumn',
      })
      .col('clltTmStr', '검체채취시작', {
        editable: true,
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 5,
          onlyNumeric: true,
        },
        headerStyle: 'editableBgColumn',
      })
      .col('clltTmEnd', '검체채취종료', {
        editable: true,
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 5,
          onlyNumeric: true,
        },
        headerStyle: 'editableBgColumn',
      })
      .col('smpClltQty', '검체채취수량', {
        editable: true,
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 5,
          onlyNumeric: true,
        },
        headerStyle: 'editableBgColumn',
      })
      .combo('smpClltUnit', '검체채취단위', {
        width: 100,
        async: () => api.combo.userCommon.getSmpVolUnitCombo(),
        headerStyle: 'editableBgColumn',
      })
      .col('ansRstInpDs', '결과입력일시', { width: 150 })
      .build(),
};

const buttonGroups = {
  buttons: [
    { name: 'resultHistory', label: '결과이력', disabled: true },
    { name: 'preResultTrend', label: '이전결과동향', disabled: true },
    { name: 'requestReview', label: '검토요청', disabled: true },
    { name: 'save', label: '저장', disabled: true },
    { name: 'init', label: '초기화', disabled: true },
  ],
};

export default {
  list,
  itemList,
  buttonGroups,
};

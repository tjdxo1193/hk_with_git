import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAgoDate = dayjs().add(-1, 'M').format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [{ name: 'select', label: '조회' }],
    legends: [
      { value: '완료예정임박', className: 'closeToCompletionDate' },
      { value: '반려시험', className: 'returnTest' },
      { value: 'OOS 재시험', className: 'oosReTest' },
    ],
    props: {
      rowStyleFunction: function (rowIndex, item) {
        //? rowStyleFunction은 다중 css클래스를 지원하지 않는다.
        if (item.emgYn === 'Y') {
          return 'redFont';
        }
        //TODO 몇 일 이하일때 표시할지,
        //?완료예정일자가 7일 이하로 남았을 때
        if (
          item.cplRqmDt != null &&
          item.cplRqmDt <= dayjs(todayDate).add(7, 'd').format('YYYY-MM-DD')
        ) {
          return 'closeToCompletionDate';
        }
        if (item.ansProcCd === 'S0130610') {
          return 'returnTest';
        }
        if (item.oosYn === 'Y') {
          return 'oosReTest';
        }
        return null;
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('ansIdx')
      .Hidden('pitmSpecIdx')
      .Select('pitmTyp', '품목구분', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('pitmCd', '품목코드')
      .Input('ansNo', '시험번호')
      .Input('lotNo', '제조번호')
      .Input('assNo', '지시번호')
      .Input('pitmNm', '품목명')
      .Input('aitmKn', '항목명')
      .Select('rstProcCd', '진행단계', {
        async: () => api.combo.systemCommon.getRstProcCombo(),
      })
      .DatepickerTwinWithSwitch('searchReqDt', '의뢰일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .blank(2)
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('reqIdx', false)
      .col('fileIdx', false)
      .col('fileCnt', '첨부파일', { colSpan: 2, width: 50 })
      .button('fileAttacher', '첨부', { width: 50 })
      .col('rcpDt', '접수일자')
      .col('emgYn', '긴급')
      .col('ansNo', '시험번호', { width: 90 })
      .col('reqDt', '의뢰일자')
      .col('pitmTyp', false)
      .col('pitmTypNm', '품목구분')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('lotNo', '제조번호')
      .combo('sytJdg', '결과판정', {
        async: () => api.combo.systemCommon.getSytTypCombo(),
      })
      .col('reqRmk', '의뢰자비고')
      .col('rcpRmk', '접수비고')
      .col('assSpcc', '시험지시특이사항', { width: 120 })
      .col('makDt', '제조일자')
      .col('etrDt', '입고일자')
      .col('splLotNo', '공급사제조번호', { width: 120 })
      .col('useLmt', '사용기한')
      .col('cplRqmDt', '완료요구일자')
      .col('assNo', '지시번호')
      .col('assDt', '지시일자')
      .col('splCd', false)
      .col('splNm', '공급사')
      .col('reqUid', false)
      .col('reqNm', '의뢰자')
      .col('reqDptCd', false)
      .col('reqDptNm', '의뢰부서')
      .col('etrQty', '입고수량')
      .col('inpUnit', '입력단위')
      .col('rjtUid', false)
      .col('rjtNm', '반려자')
      .col('rjtDs', '반려일')
      .col('rjtRea', '반려사유')
      .col('oosYn', false)
      .col('ansIdx', false)
      .col('pitmSpecIdx', false)
      .col('crgDptCd', false)
      .col('crgDptNm', false)
      .build(),
};

const resultInputInfo = {
  static: {
    title: '결과입력',
    $grid: null,
    buttons: [
      { name: 'finalOrder', label: '마감오더', disabled: true },
      { name: 'resultHistory', label: '결과이력', disabled: true },
      { name: 'labEventPublish', label: '실험실이벤트발행', disabled: true },
      { name: 'requestReview', label: '검토요청', disabled: true },
      { name: 'save', label: '저장', disabled: true },
      { name: 'init', label: '초기화', disabled: true },
    ],
    props: {
      rowStyleFunction: function (rowIndex, item) {
        if (item.rstJdg === 'S0120002') {
          return 'redFont';
        }
        if (item.rstJdgEtc === 'S0120002') {
          return 'boldFont';
        }
        return null;
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('phsOrderNo')
      .Hidden('pdtOrderNo')
      .Hidden('phsOrderItm')
      .Hidden('lotNo')
      .Hidden('ansIdx')
      .Input('pitmNm', '품목명', { readonly: true })
      .spanCol(2)
      .Input('ansNo', '시험번호', { readonly: true })
      .Select('sytJdg', '결과판정', {
        async: () => api.combo.systemCommon.getSytTypCombo(),
      })
      .required()
      .Textarea('pitmSpcc', '품목특이사항', { rows: 1 })
      .spanCol(2)
      .Textarea('nonCfmRea', '부적합사유', { rows: 1 })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('reqIdx', false)
      .col('ansIdx', false)
      .col('rstSeq', false)
      .col('orderNo', false)
      .col('rcpDt', false)
      .col('fileIdx', false)
      .col('fileCnt', '첨부파일', { colSpan: 2, width: 50 })
      .button('fileAttacher', '첨부', { width: 50 })
      .col('aitmCd', false)
      .col('aitmKn', '항목명', { width: 150 })
      .col('vriaNo', 'VARIANT NO', { width: 100 })
      .col('vriaKn', 'VARIANT 국문', { width: 100 })
      .col('ansDptCd', false)
      .col('ansDptNm', '시험파트')
      .col('ansUid', false)
      .col('ansNm', '시험자')
      .col('perspecTxt', '허가규격', { width: 150 })
      .col('owcSpecTxt', '자사규격', { width: 150 })
      .combo('specTyp', '규격유형', {
        async: () => api.combo.systemCommon.getSpecTypCombo(),
      })
      .col('jdgTyp', false)
      .col('jdgTypNm', '판정유형', { width: 100 })
      .col('mkrQty', '표시량')
      .col('rstVal', '결과값', {
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
      .col('markVal', '표기값')
      .col('rstJdgEtc', false)
      .combo('rstJdg', '결과판정', {
        async: () => api.combo.systemCommon.getRstJdgCombo(),
      })
      .col('rstRmk', '결과비고', {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 50,
        },
      })
      .col('perSlv', '허가', {
        children: ColumnBuilder.builder()
          .col('perSlvLow', '하한', { editable: false })
          .col('perSlvUpp', '상한', { editable: false })
          .col('perSlvDesc', '서술', { editable: false })
          .build(),
      })
      .col('owcSlv', '자사', {
        children: ColumnBuilder.builder()
          .col('owcSlvLow', '하한', { editable: false })
          .col('owcSlvUpp', '상한', { editable: false })
          .col('owcSlvDesc', '서술', { editable: false })
          .build(),
      })
      .col('slvJdgCfm', false)
      .col('slvJdgNonCfm', false)
      .col('slvJdgCfmNm', '기준 적합값', { width: 90 })
      .col('slvJdgNonCfmNm', '기준 부적합값', { width: 90 })
      .col('rstUnitCd', false)
      .col('rstUnitNm', '결과단위')
      .col('rstDpnt', '결과소수점')
      .combo('rptPrtYn', '성적서출력여부', {
        width: 100,
        list: [
          { value: 'Y', label: 'Y' },
          { value: 'N', label: 'N' },
        ],
      })
      .col('rptPrtItmNm', '성적서출력항목명', {
        width: 110,
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 30,
        },
      })
      .col('rptPrtSlvVal', '성적서출력기준값', {
        width: 110,
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 30,
        },
      })
      .col('ansRstInpDs', '결과입력일시', { width: 150 })
      .col('aitmRmk', '비고', { width: 200 })
      .col('ansNo', false)
      .col('lotNo', false)
      .col('pitmTyp', false)
      .col('pitmCd', false)
      .col('batchNo', false)
      .col('amitmCd', false)
      .col('rstProcCd', false)
      .col('labEvtIdx', false)
      .col('labEvtAprIdx', false)
      .col('aprYn', false)
      .build(),
};

export default {
  list,
  resultInputInfo,
};

import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const weekAgoDate = dayjs().add(-7, 'd').format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [{ name: 'select', label: '조회' }],
    legends: [{ value: 'OOS 재시험', className: 'oosReTest' }],
    props: {
      showRowCheckColumn: false,
      //TODO 긴급시험일 경우 빨간글씨로 상단에 위치되도록
      rowStyleFunction: function (rowIndex, item) {
        if (item.emgYn === 'Y') {
          return 'redFont';
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
      .Select('pitmTyp', '품목구분', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('pitmCd', '품목코드')
      .Input('pitmNm', '품목명')
      .Input('ansNo', '시험번호')
      .Input('lotNo', '제조번호')
      .Input('assNo', '지시번호')
      .DatepickerTwinWithSwitch('searchReqDt', '의뢰일', {
        value: [weekAgoDate, todayDate],
      })
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
        async: () => api.combo.systemCommon.getSytJdgCombo(),
      })
      .col('assSpcc', '시험지시특이사항', { width: 120 })
      .col('pitmSpcc', '품목특이사항', { width: 120 })
      .col('nonCfmRea', '부적합사유', { width: 120 })
      .col('makDt', '제조일자')
      .col('etrDt', '입고일자')
      .col('splLotNo', '공급사제조번호', { width: 120 })
      .col('useLmt', '사용기한')
      .col('pitmEn', '품목영문명')
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
      .col('crgDptCd', false)
      .col('crgDptNm', false)
      .build(),
};

const requestInfo = {
  static: {
    title: '항목조회',
    $grid: null,
    props: {
      editable: false,
      rowStyleFunction: function (rowIndex, item) {
        if (item.rstJdg === 'S0120002') {
          return 'redFont';
        }
        return null;
      },
    },
    buttons: [
      { name: 'rejection', label: '반려', disabled: true },
      { name: 'hold', label: '보류', disabled: true },
      { name: 'reviewCompletion', label: '검토완료', disabled: true },
      { name: 'init', label: '초기화', disabled: true },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('ansIdx')
      .Hidden('phsOrderNo')
      .Hidden('pdtOrderNo')
      .Hidden('lotNo')
      .Hidden('batchNo')
      .Hidden('ispReqNo')
      .Input('pitmNm', '품목명', { readonly: true })
      .spanCol(2)
      .Input('ansNo', '시험번호', { readonly: true })
      .Select('sytJdg', '결과판정', {
        async: () => api.combo.systemCommon.getSytJdgCombo(),
      })
      .required()
      .Textarea('pitmSpcc', '품목특이사항', { rows: 1, readonly: true })
      .spanCol(2)
      .Textarea('nonCfmRea', '부적합사유', { rows: 1, readonly: true })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('pitmSpecIdx', false)
      .col('reqIdx', false)
      .col('ansIdx', false)
      .col('rstSeq', false)
      .col('ansProcCd', false)
      .col('fileIdx', false)
      .col('fileCnt', '첨부파일', { colSpan: 2, width: 50 })
      .button('fileAttacher', '첨부', { width: 50 })
      .button('resultHistory', '결과이력')
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
      .col('specTyp', false)
      .col('specTypNm', '규격유형')
      .col('jdgTyp', false)
      .col('jdgTypNm', '판정유형', { width: 100 })
      .col('mkrQty', '표시량')
      .col('rstVal', '결과값')
      .col('markVal', '표기값')
      .col('rstJdg', false)
      .col('rstJdgNm', '결과판정')
      .col('rstRmk', '결과비고')
      .col('perSlv', '허가', {
        children: ColumnBuilder.builder()
          .col('perSlvLow', '하한')
          .col('perSlvUpp', '상한')
          .col('perSlvDesc', '서술')
          .build(),
      })
      .col('owcSlv', '자사', {
        children: ColumnBuilder.builder()
          .col('owcSlvLow', '하한')
          .col('owcSlvUpp', '상한')
          .col('owcSlvDesc', '서술')
          .build(),
      })
      .col('slvJdgCfm', false)
      .col('slvJdgNonCfm', false)
      .col('slvJdgCfmNm', '기준 적합값', { width: 90 })
      .col('slvJdgNonCfmNm', '기준 부적합값', { width: 90 })
      .col('rstUnitCd', false)
      .col('rstUnitNm', '결과단위')
      .col('rstDpnt', '결과소수점')
      .col('rptPrtYn', '성적서출력여부', { width: 100 })
      .col('rptPrtItmNm', '성적서출력항목명', { width: 110 })
      .col('rptPrtSlvVal', '성적서출력기준값', { width: 110 })
      .col('ansRstInpDs', '결과입력일시', { width: 150 })
      .col('aitmRmk', '비고', { width: 200 })
      .build(),
};

export default {
  list,
  requestInfo,
};

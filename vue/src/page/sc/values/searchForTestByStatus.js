import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const dayjs = require('dayjs');

const todayDate = dayjs().format('YYYY-MM-DD');

const monthAgoDate = dayjs().add(-1, 'month').format('YYYY-MM-DD');

const searchGridWithForm = {
  static: {
    title: '조회',
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .RadioGroup('testDiv', '구분', {
        value: '',
        groups: [
          { checkedValue: '', label: '전체' },
          { checkedValue: 'hold', label: '시험보류' },
          { checkedValue: 'cancel', label: '시험취소' },
        ],
        gap: 100,
      })
      .spanCol(2)
      .Select('pitmTyp', '품목구분', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('pitmCd', '품목코드')
      .Select('ansProcCd', '진행상황', {
        async: () => api.combo.systemCommon.getAnsProcCombo(),
      })
      .Select('SytJdg', '결과판정', {
        async: () => api.combo.systemCommon.getSytTypCombo(),
      })
      .Input('pitmNm', '품목명')
      .Input('ansNo', '시험번호')
      .DatepickerTwinWithSwitch('reqDtList', '의뢰일', { value: [monthAgoDate, todayDate] })
      .spanCol(2)
      .DatepickerTwinWithSwitch('rcpDtList', '접수일', { value: [monthAgoDate, todayDate] })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('reqIdx', { visible: false })
      .col('fileIdx', { visible: false })
      .col('fileCnt', '첨부파일', { colSpan: 2, width: 50 })
      .button('fileAttacher', '첨부', { width: 50 })
      .col('rcpDt', '접수일자')
      .col('emgYn', '긴급')
      .col('ansNo', '시험번호', { width: 90 })
      .col('reqDt', '의뢰일자')
      .col('pitmTyp', { visible: false })
      .col('pitmTypNm', '품목구분')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('lotNo', '제조번호')
      .combo('sytJdg', '결과판정', {
        async: () => api.combo.systemCommon.getSytTypCombo(),
      })
      .col('revwCmmt', '검토의견')
      .col('assSpcc', '시험지시특이사항', { width: 120 })
      .col('makDt', '제조일자')
      .col('etrDt', '입고일자')
      .col('splLotNo', '공급사제조번호', { width: 120 })
      .col('useLmt', '사용기한')
      .col('pitmEn', '품목영문명')
      .col('cplRqmDt', '완료요구일자')
      .col('assNo', '지시번호')
      .col('assDt', '지시일자')
      .col('splCd', { visible: false })
      .col('splNm', '공급사')
      .col('reqUid', { visible: false })
      .col('reqNm', '의뢰자')
      .col('reqDptCd', { visible: false })
      .col('reqDptNm', '의뢰부서')
      .col('etrQty', '입고수량')
      .col('inpUnit', '입력단위')
      .col('rjtUid', { visible: false })
      .col('rjtNm', '반려자')
      .col('rjtDs', '반려일')
      .col('rjtRea', '반려사유')
      .col('oosYn', { visible: false })
      .col('crgDptCd', { visible: false })
      .col('crgDptNm', { visible: false })
      .build(),
};

const aitmSpecGrid = {
  static: {
    title: '품목조회',
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('pitmSpecIdx', { visible: false })
      .col('reqIdx', { visible: false })
      .col('ansIdx', { visible: false })
      .col('rstSeq', { visible: false })
      .col('ansProcCd', { visible: false })
      .col('fileIdx', { visible: false })
      .col('fileCnt', '첨부파일', { colSpan: 2, width: 50 })
      .button('fileAttacher', '첨부', { width: 50 })
      .col('aitmCd', { visible: false })
      .col('aitmKn', '항목명', { width: 150 })
      .col('vriaNo', 'VARIANT<br/>NO', { width: 100 })
      .col('vriaKn', 'VARIANT<br/>국문', { width: 100 })
      .col('ansDptCd', { visible: false })
      .col('ansDptNm', '시험파트')
      .col('ansUid', { visible: false })
      .col('ansNm', '시험자')
      .col('perspecTxt', '허가규격', { width: 150 })
      .col('owcSpecTxt', '자사규격', { width: 150 })
      .col('specTyp', { visible: false })
      .col('specTypNm', '규격유형')
      .col('jdgTyp', { visible: false })
      .col('jdgTypNm', '판정유형', { width: 100 })
      .col('mkrQty', '표시량')
      .col('rstVal', '결과값')
      .col('markVal', '표기값')
      .col('rstJdg', { visible: false })
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
      .col('slvJdgCfm', { visible: false })
      .col('slvJdgNonCfm', { visible: false })
      .col('slvJdgCfmNm', '기준 적합값', { width: 90 })
      .col('slvJdgNonCfmNm', '기준 부적합값', { width: 90 })
      .col('rstUnitCd', { visible: false })
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
  searchGridWithForm,
  aitmSpecGrid,
};

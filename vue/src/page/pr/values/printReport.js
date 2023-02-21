import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const weekAgoDate = dayjs().add(-7, 'd').format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'select', label: '조회' }],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
      //headerHeight:'40px',
      rowStyleFunction: function (rowIndex, item) {
        if (item.useVerYn === 'N' || item.specUseVerYn === 'N') {
          return 'standardNoSet';
        }
        if (item.oosYn === 'Y') {
          return 'oosReTest';
        }
        return null;
      },
    }, //TODO EDITABLE: TRUE 컬럼 확인
    legends: [
      { value: '안정성시험', className: 'stabilityTest' },
      { value: 'OOS 재시험', className: 'oosReTest' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '자재구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmCd', '자재번호')
      .Input('pitmNm', '자재내역')
      .Input('ispReqNo', '검사요청번호')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .DatepickerTwinWithSwitch('searchIspReqDt', '검사요청일자', {
        value: [weekAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('reqIdx', false)
      .col('plntCd', '플랜트코드')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('pitmEn', '자재내역(영문)')
      .col('ansNo', '시험번호', { width: 90 })
      .col('reqDt', '의뢰일자')
      .col('reqDt', '지시일자')
      .col('sytJdgNm', '결과판정')
      .col('lotNo', '제조번호')
      .col('makDt', '제조일자')
      .col('mtrCd', '자재번호')
      .col('batchNo', '배치번호')
      .col('ispReqNo', '검사요청번호')
      .col('etrQty', '입고수량')
      .col('inpUnit', '입력단위')
      .col('etrDt', '입고일자')
      .col('splCd', '공급사코드')
      .col('splNm', '공급사명')
      .col('splLotNo', '공급사제조번호')
      .col('csmNm', '고객처명')
      .col('smpVolTot', '총검체량')
      .col('clltUnm', '채취자')
      .col('smpVolTot', '채취일자')
      .col('rmtrSpec', '원료규격')
      .col('pkgMtrSpec', '포장재규격')
      .col('rmtrCfmul', '원료조성성분비')
      .build(),
};

const testInfo = {
  static: {
    title: '의뢰정보',
    countPerRow: 4,
    id: 'testInfo',
  },
  forms: () =>
    FormBuilder.builder()
      .Input('ansNo', '시험번호', { readonly: true })
      .Input('pitmNm', '자재내역', { readonly: true })
      .Input('pitmEn', '자재내역(영문)', { readonly: true })
      .Input('pitmCd', '자재번호', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('makDt', '제조일자', { readonly: true })
      .Input('mtrCd', '자재번호', { readonly: true })
      .Input('csmNm', '고객처명', { readonly: true })
      .Input('splNm', '공급사', { readonly: true })
      .multiple(
        'input',
        '입고수량',
        FormBuilder.builder()
          .Input('etrQty', { readonly: true })
          .Input('inpUnit', { readonly: true })
          .spanCol(0.5)
          .build(),
      )
      .Input('etrDt', '입고일자', { readonly: true })
      .Input('smpVolTot', '총검체량', { readonly: true })
      .Input('clltUnm', '채취자', { readonly: true })
      .Input('smpVolTot', '채취일자', { readonly: true })
      .Input('sytJdgNm', '결과판정', { readonly: true })
      .Input('rmtrSpec', '원료규격', { readonly: true })
      .Input('pkgMtrSpec', '포장재규격', { readonly: true })
      .Input('rmtrCfmul', '원료조성성분비', { readonly: true })
      .build(),
};

const reportInfo = {
  static: {
    title: '리포트정보',
    countPerRow: 4,
    buttons: [{ name: 'save', label: '저장', disabled: true }],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('ansIdx')
      .Select('pmSpcmNo', '통합성적서구분')
      .spanCol(2)
      .Textarea('rptSpcc', '성적서특이사항', { rows: 5 })
      .spanCol(2)
      .spanRow(2)
      .Textarea('arptSpcc', '통합성적서특이사항', { rows: 3 })
      .spanCol(2)
      .build(),
};

const testItemList = {
  static: {
    title: '시험항목',
    $grid: null,
    props: { editable: false, showRowCheckColumn: false, enableCellMerge: true },
    height: '300px',
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('pitmSpecIdx', false)
      .col('reqIdx', false)
      .col('ansIdx', false)
      .col('rstSeq', false)
      .col('ansProcCd', false)
      // .col('fileIdx', false)
      // .col('fileCnt', '첨부파일', { colSpan: 2, width: 50 })
      // .button('fileAttacher', '첨부', { width: 50 })
      // .button('resultHistory', '결과이력')
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

const tabs = {
  tabs: [
    { name: 'testInfo', label: '시험정보' },
    { name: 'itemInfo', label: '항목정보' },
  ],
  buttons: [
    { name: 'printIntegratedReport', label: '통합성적서', disabled: true },
    { name: 'printTestReport', label: '시험성적서', disabled: true },
    { name: 'printTestInstruction', label: '시험지시서', disabled: true },
    { name: 'init', label: '초기화', disabled: true },
  ],
};

export default {
  list,
  testInfo,
  reportInfo,
  testItemList,
  tabs,
};

import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const weekAgoDate = dayjs().add(-7, 'd').format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'receipt', label: '접수' },
      { name: 'select', label: '조회' },
    ],
    $grid: null,
    props: {
      //editable: false,
      showRowCheckColumn: true,
      //headerHeight:'40px',
      rowStyleFunction: function (rowIndex, item) {
        if (item.useVerYn === 'N' || item.specUseVerYn === 'N') {
          return 'standardNoSet';
        }
        return null;
      },
    }, //TODO EDITABLE: TRUE 컬럼 확인
    legends: [
      { value: '안정성시험', className: 'stabilityTest' },
      { value: '미승인규격', className: 'standardNoSet' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '품목구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmCd', '품목코드')
      .Input('pitmNm', '품목명')
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
      .combo('emgYn', '긴급여부', {
        width: 60,
        list: [
          { value: 'Y', label: 'Y' },
          { value: 'N', label: 'N' },
        ],
      })
      .col('plntCd', '사업장코드')
      .col('ansTypNm', '시험구분')
      .col('ansNo', '시험번호')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명(국문)')
      .col('pitmEn', '품목명(영문)')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('ispReqNo', '검사요청번호')
      .col('labNo', 'Lab No.')
      .col('brdAbbr', '브랜드약어')
      .col('etrCtnQty', '입고용기수량')
      .col('rmtrSpec', '원료규격')
      .col('pkgMtrSpec', '포장재규격')
      .col('rmtrCfmul', '원료조성성분비')
      .col('ftnYn', '기능성유무')
      .col('useTrm', '사용기간')
      .col('otcPrd', 'OTC제품')
      .col('mkrVol', '표시용량')
      .col('mkrVolUnit', '표시용량단위')
      .col('etnAnsReq', '외부시험의뢰')
      .col('perNo', '허가번호')
      .col('chagVol', '실충전용량')
      .col('chagVolUnit', '실충전용량단위')
      .col('etrQty', '입고수량')
      .col('inpUnit', '입력단위')
      .col('etrDt', '입고일자')
      .col('splCd', '공급사코드')
      .col('splNm', '공급사명')
      .col('vdrCtrtDt', '납품약정일자')
      .col('vdrRsvTm', '납품예약시간')
      .col('vdrRptRcpCrst', '거래처성적서접수현황')
      .col('splLotNo', '공급사제조번호')
      .col('useLmt', '사용기한')
      .col('itnPrsCompNm', '내부임가공업체명')
      .col('makEqp', '제조설비')
      .col('wrkNm', '작업자명')
      .col('csmNm', '고객처명')
      .col('pcs01', '공정01')
      .col('pcs02', '공정02')
      .col('pcs03', '공정03')
      .col('pcs04', '공정04')
      .col('dioYn', '디옥산관리대상')
      .col('qdrugYn', '의약외품유무')
      .col('prbInYn', '파라벤포함여부')
      .col('prbFeYn', '파라벤프리관리대상')
      .col('pnxFeYn', '페녹시에탄올프리관리대상')
      .col('dlvYn', '택배유무')
      .col('dmsEptYn', '내수/수출')
      .col('pearDiv', '펄구분')
      .col('specUseVerYn', '규격서사용여부')
      .col('addCol1Nm', '입고유형')
      .col('addCol2', '입고취소여부')
      .col('addCol3', '전표생성일자')
      .build(),
};

const requestInfo = {
  static: {
    countPerRow: 4,
    id: 'requestInfo',
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('pitmVer')
      .Hidden('reqIdx')
      .Hidden('batchNo')
      .Input('ispReqNo', '검사요청번호', { readonly: true })
      .Input('pitmNm', '품목명(국문)', { readonly: true })
      .Input('pitmEn', '품목명(영문)', { readonly: true })
      .Input('pitmCd', '품목코드', { readonly: true })
      .Input('ispReqDt', '검사요청일자', { readonly: true })
      .Input('strgLmt', '보관기한', { readonly: true })
      .Input('useLmt', '사용기한', { readonly: true })
      .Input('vdrCtrtDt', '납품약정일자', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('makDt', '제조일자', { readonly: true })
      .Input('splLotNo', '공급사제조번호', { readonly: true })
      .Input('splNm', '공급사', { readonly: true })
      .Input('phsOrderNo', '구매오더번호', { readonly: true })
      .Input('phsOrderItm', '구매오더항목', { readonly: true })
      .Input('phsOrderQty', '구매오더수량', { readonly: true })
      .Input('phsOrderUnit', '구매오더단위', { readonly: true })
      .Input('pdtOrderNo', '생산오더번호', { readonly: true })
      .Input('pdtOrderTyp', '생산오더유형', { readonly: true })
      .Input('phsCrgNm', '구매담당자', { readonly: true })
      .Input('mtrDocNo', '자재문서번호', { readonly: true })
      .Input('phsUnitPre', '구매단가', { readonly: true })
      .Input('amtUnit', '가격단위', { readonly: true })
      .Input('etrQty', '입고수량', { readonly: true })
      .Input('etrDt', '입고일자', { readonly: true })
      .Input('makEqp', '제조설비', { readonly: true })
      .Input('wrkNm', '작업자명', { readonly: true })
      .Input('addCol1', '입고유형')
      .Input('addCol2', '입고취소여부')
      .Input('addCol3', '전표생성일자')
      .build(),
};

const itemList = {
  static: {
    $grid: null,
    props: { editable: false, showRowCheckColumn: false, enableCellMerge: true },
    height: '300px',
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmSpecIdx', false)
      .col('aitmSpecIdx', false)
      .col('aitmSeq', false)
      .col('pitmCd', false)
      .col('pitmVer', '품목<br>버전', { width: 50, cellMerge: true, visible: false })
      .col('aitmSpecVer', '규격서<br>버전', { width: 50, cellMerge: true })
      .col('amitmCd', false)
      .col('aitmKn', '시험항목명')
      .col('vriaNo', 'VARIANT<br>NO')
      .col('vriaKn', 'VARIANT<br>국문')
      .col('ansDptNm', '시험파트')
      .col('perspecTxt', '허가규격', { width: 150 })
      .col('owcSpecTxt', '자사규격', { width: 150 })
      .col('specTypNm', '규격유형')
      .col('jdgTypNm', '판정유형')
      .col('mkrQty', '표시량')
      .col('permitCriteria', '허가기준', {
        children: ColumnBuilder.builder()
          .col('perSlvLow', '하한')
          .col('perSlvUpp', '상한')
          .col('perSlvDesc', '서술')
          .build(),
      })
      .col('companyStandard', '자사기준', {
        children: ColumnBuilder.builder()
          .col('owcSlvLow', '하한')
          .col('owcSlvUpp', '상한')
          .col('owcSlvDesc', '서술')
          .build(),
      })
      .col('slvJdgCfmNm', '기준적합판정')
      .col('slvJdgNonCfmNm', '기준부적합판정')
      .col('rstUnitNm', '결과단위')
      .col('rstDpnt', '결과소수점')
      .col('rptPrtSlvVal', '성적서<br>출력기준')
      .col('rptPrtItmNm', '성적서<br>출력항목')
      .col('rptPrtYn', '성적서<br>출력여부')
      .col('aitmRmk', '비고', { width: 200 })
      .build(),
};

const tabs = {
  tabs: [
    { name: 'requestInfo', label: '시험의뢰정보' },
    { name: 'testInfo', label: '항목정보' },
  ],
  buttons: [
    { name: 'inputPerformance', label: '투입실적', disabled: true },
    { name: 'finalOrder', label: '마감오더', disabled: true },
    { name: 'preventRecurrenceReport', label: '재발방지대책서', disabled: true },
    { name: 'srmReport', label: 'SRM성적서', disabled: true },
    { name: 'packagingSpec', label: 'MES포장사양서', disabled: true },
    { name: 'nonconformityTestList', label: '품목시험의뢰부적합', disabled: true },
    { name: 'specimen', label: '표준견본생성', disabled: true },
    { name: 'processSpecimen', label: '공정표준견본생성', disabled: true },
    { name: 'packagingSpecimen', label: '포장재표준견본생성', disabled: true },
    { name: 'init', label: '초기화', disabled: true },
  ],
};

export default {
  list,
  requestInfo,
  itemList,
  tabs,
};

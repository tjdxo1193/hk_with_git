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
      { name: 'requestCreate', label: '안정성시험수동생성' },
      { name: 'receipt', label: '접수' },
      { name: 'select', label: '조회' },
    ],
    $grid: null,
    props: {
      isFitColumnSizeHeaderText: false,
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
    legends: [{ value: '미승인규격', className: 'standardNoSet' }],
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
      .col('group_cmmn', '공통정보', {
        children: ColumnBuilder.builder()
          .col('plntCd', '플랜트')
          .combo('emgYn', '긴급여부', {
            width: 60,
            list: [
              { value: 'Y', label: 'Y' },
              { value: 'N', label: 'N' },
            ],
          })
          .col('ansTypNm', '시험구분')
          .col('ansNo', '시험번호')
          .col('reqDs', '의뢰일시', { width: 120 })
          .col('reqUnm', '의뢰자')
          .col('pitmTypNm', '자재구분')
          .col('pitmCd', '자재번호')
          .col('pitmNm', '자재내역')
          .col('pitmEn', '자재내역(영문)')
          .col('ispReqNo', '검사요청번호')
          .col('ispReqDt', '검사요청일자')
          .col('spcmNo', '표준견본번호')
          .col('lotNo', '제조번호')
          .col('batchNo', '배치번호')
          .col('makDt', '제조일자')
          .col('addCol1Nm', '입고유형')
          .col('etrQty', '입고수량')
          .col('inpUnit', '입력단위')
          .col('etrDt', '입고일자')
          .col('addCol2', '입고취소여부')
          .col('addCol3', '전표생성일자')
          .col('phsOrderTyp', '구매오더유형')
          .col('phsOrderNo', '구매오더번호')
          .col('phsOrderItm', '구매오더항목')
          .col('splCd', '공급사코드')
          .col('splNm', '공급사명', { width: 110 })
          .col('splLotNo', '공급사제조번호', { width: 100 })
          .col('phsCrgNm', '구매담당자')
          .col('savePla', '저장위치')
          .col('mtrDocNo', '자재문서번호')
          .col('mtrDocItm', '자재문서항목')
          .col('nomUnit', '기본단위')
          .build(),
      })
      .col('group_detail', '', {
        children: ColumnBuilder.builder()
          .col('nbr', '호수')
          .col('mkrVol', '표시용량')
          .col('mkrVolUnit', '표시용량단위')
          .col('useTrm', '사용기간')
          .col('useLmt', '사용기한')
          .col('csmBpCd', '고객처BP코드', { width: 100 })
          .col('csmNm', '고객처명')
          .col('brdAbbr', '브랜드약어')
          .col('repBomNo', '대체BOM번호', { width: 100 })
          .col('nomMtr', '기본자재')
          .col('chagVol', '실충전용량')
          .col('chagVolUnit', '실충전용량단위', { width: 100 })
          .col('pdtOrderTyp', '생산오더유형')
          .col('pdtOrderNo', '생산오더번호')
          .col('phsOrderQty', '구매오더수량')
          .col('phsOrderUnit', '구매오더단위')
          .col('vdrRptRcpCrst', '거래처성적서접수현황', { minColumnWidth: 20 })
          .col('itnPrsCompCd', '내부임가공업체코드', { width: 120 })
          .col('itnPrsCompNm', '내부임가공업체명', { width: 120 })
          .col('itmCtg', '아이템카테고리', { width: 120 })
          .col('strgLmt', '보관기한')
          .col('dlvYn', '택배유무')
          .col('vdrCtrtDt', '납품약정일자')
          .col('vdrRsvTm', '납품약정시간')
          .col('etrCtnQty', '입고용기수량')
          .col('rmtrSpec', '원료규격')
          .col('rmtrCfmul', '원료조성성분비', { width: 100 })
          .col('pkgMtrSpec', '포장재규격')
          .col('labNo', 'Lab No.')
          .col('ftnYn', '기능성유무')
          .col('qdrugYn', '의약외품유무')
          .col('pcs01', '공정01')
          .col('pcs02', '공정02')
          .col('pcs03', '공정03')
          .col('pcs04', '공정04')
          .col('makEqp', '제조설비')
          .col('wrkNm', '작업자명')
          .col('pearDiv', '펄구분')
          .col('otcPrd', 'OTC제품')
          .col('dmsEptYn', '내수/수출')
          .col('etnAnsReq', '외부시험의뢰')
          .col('ctrptNo', 'CT성적서번호', { width: 100 })
          .col('perNo', '허가번호')
          .col('dioYn', '디옥산관리대상', { width: 100 })
          .col('prbInYn', '파라벤포함여부', { width: 100 })
          .col('prbFeYn', '파라벤프리관리대상', { width: 120 })
          .col('pnxFeYn', '페녹시에탄올프리관리대상', { width: 140 })
          .col('phsUnitPre', '구매단가')
          .col('amtUnit', '가격단위')
          .col('currCd', '통화키')
          .col('amtLoccurr', '금액현지통화', { width: 100 })
          .build(),
      })
      .build(),
};

const itemInfo = {
  static: {
    title: '자재정보',
    countPerRow: 4,
    id: 'itemInfo',
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('reqIdx')
      .Hidden('ansIdx')
      .Hidden('batchNo')
      .Hidden('pitmVer')
      .Hidden('ansTyp')
      .Hidden('aitmSpecIdx')
      .Hidden('pitmCd', '자재번호')
      .Hidden('pitmNm', '자재내역')
      .Input('ispReqNo', '검사요청번호', { readonly: true })
      .Input('ispReqDt', '검사요청일자', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('makDt', '제조일자', { readonly: true })
      .Input('addCol1Nm', '입고유형', { readonly: true })
      .multiple(
        'input',
        '입고수량',
        FormBuilder.builder()
          .Input('etrQty', { readonly: true })
          .Input('inpUnit', { readonly: true })
          .spanCol(0.5)
          .build(),
      )
      .Input('useLmt', '사용기한', { readonly: true })
      .Input('strgLmt', '보관기한', { readonly: true })
      .Input('phsOrderNo', '구매오더번호', { readonly: true })
      .Input('splCd', '공급사코드', { readonly: true })
      .Textarea('detailInfo', '상세정보', { readonly: true, rows: 8 })
      .spanRow(5)
      .spanCol(2)
      .Input('splNm', '공급사명', { readonly: true })
      .Input('splLotNo', '공급사제조번호', { readonly: true })
      .Input('phsCrgNm', '구매담당자', { readonly: true })
      .Input('pdtOrderNo', '생산오더번호', { readonly: true })
      .Input('makEqp', '제조설비', { readonly: true })
      .Input('wrkNm', '작업자명', { readonly: true })
      .build(),
};

const requestInfo = {
  static: {
    title: '의뢰정보',
    countPerRow: 4,
    id: 'requestInfo',
  },
  forms: () =>
    FormBuilder.builder()
      .Input('pitmTypNm', '자재구분', { readonly: true })
      .Input('pitmCd', '자재번호', { readonly: true })
      .Input('pitmNm', '자재내역', { readonly: true })
      .Input('pitmEn', '자재내역(영문)', { readonly: true })
      .Input('reqUnm', '의뢰자', { readonly: true })
      .Input('reqDpt', '의뢰부서', { readonly: true })
      .Input('reqDs', '의뢰일시', { readonly: true })
      .Input('reqRmk', '의뢰비고', { readonly: true })
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
      .col('pitmVer', '자재<br>버전', { width: 50, cellMerge: true, visible: false })
      .col('aitmSpecVer', '규격서<br>버전', { width: 50, cellMerge: true })
      .col('amitmCd', false)
      .col('ansDptNm', '시험파트')
      .col('aitmKn', '시험항목명', { width: 150 })
      .col('vriaNo', 'VARIANT<br>NO')
      .col('vriaKn', 'VARIANT<br>국문')
      .col('specTypNm', '규격유형')
      .col('specTxt', '기준(통합)', {
        children: ColumnBuilder.builder()
          .col('perspecTxt', '기준(허가)', { width: 150 })
          .col('owcSpecTxt', '기준(자사)', { width: 150 })
          .col('specTxtEn', '기준(영문)')
          .build(),
      })
      .col('jdgTypNm', '판정유형')
      .col('mkrQty', '표시량')
      .col('permitCriteria', '기준값(허가)', {
        children: ColumnBuilder.builder()
          .col('perSlvLow', '하한')
          .col('perSlvUpp', '상한')
          .col('perSlvDesc', '서술')
          .build(),
      })
      .col('companyStandard', '기준값(자사)', {
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
      .col('companyStandard', '성적서', {
        children: ColumnBuilder.builder()
          .col('rptPrtItmNm', '항목(출력)')
          .col('rptPrtSlvVal', '기준(출력)')
          .build(),
      })
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
    { name: 'nonconformityTestList', label: '자재시험의뢰부적합', disabled: true },
    { name: 'specimen', label: '표준견본생성', disabled: true },
    { name: 'processSpecimen', label: '공정표준견본생성', disabled: true },
    { name: 'packagingSpecimen', label: '포장재표준견본생성', disabled: true },
    { name: 'init', label: '초기화', disabled: true },
  ],
};

export default {
  list,
  requestInfo,
  itemInfo,
  itemList,
  tabs,
};

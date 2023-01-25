import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAgoDate = dayjs().add(-1, 'M').format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'select', label: '조회' },
      { name: 'instruct', label: '지시승인' },
    ],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
    legends: [{ value: '안정성시험', className: 'stabilityTest' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '품목구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmCd', '품목코드')
      .Input('pitmNm', '품목명')
      .Input('ansNo', '시험번호')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .DatepickerTwinWithSwitch('formReqDt', '의뢰일자', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .col('reqIdx', false)
      .col('fileCnt', '첨부파일', { colSpan: 2, width: 50 })
      .button('fileAttacher', '첨부', { width: 50 })
      .col('rcpDt', '접수일자')
      .col('plntCd', '사업장코드')
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
      .col('prbInYn', '파라벤포함여부')
      .col('prbFeYn', '파라벤프리관리대상')
      .col('pnxFeYn', '페녹시에탄올프리관리대상')
      .col('dlvYn', '택배유무')
      .col('dmsEptYn', '내수/수출')
      .col('pearDiv', '펄 구분')
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
      .build(),
};

const itemList = {
  static: {
    $grid: null,
    props: { editable: false, showRowCheckColumn: true },
    height: '300px',
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('ansIdx', false)
      .col('rstSeq', false)
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

const testCollectionInfo = {
  static: {
    title: '검체채취정보',
    countPerRow: 4,
    id: 'testCollectionInfo',
  },
  forms: () =>
    FormBuilder.builder()
      .Input('spcmNo', '표준견본번호', { readonly: true })
      .Input('clltPlaNm', '채취장소', { readonly: true })
      .Input('clltMcnNm', '채취기구', { readonly: true })
      .Input('clltMtdNm', '채취방법', { readonly: true })
      .multiple(
        'smpVolTot',
        '총검체량',
        FormBuilder.builder()
          .Input('smpVolTot', { readonly: true, value: 0 })
          .Input('smpVolUnitNm', { readonly: true })
          .spanCol(2)
          .build(),
      )
      .Input('smpVolAns', '시험검체량', { readonly: true })
      .Input('smpVolStrg', '보관검체량', { readonly: true })
      .Input('smpVolEtc', '기타검체량', { readonly: true })
      .Input('smpStrgMtdNm', '검체보관방법', { readonly: true })
      .Input('clltUnm', '채취자', { readonly: true })
      .Datepicker('clltDt', '검체채취일', { readonly: true })
      .RadioGroup('micYn', '미생물 유무', {
        disabled: true,
        groups: [
          { label: '유', checkedValue: 'Y' },
          { label: '무', checkedValue: 'N' },
        ],
      })
      .Textarea('clltRmk', '채취비고', { readonly: true, rows: 2 })
      .spanCol(2)
      .build(),
};

const tabs = {
  tabs: [
    { name: 'requestInfo', label: '시험의뢰정보' },
    {
      name: 'testInfo',
      label: '항목정보',
      buttons: [{ name: 'delete', label: '삭제', disabled: true, type: 'danger' }],
    },
  ],
  buttons: [{ name: 'init', label: '초기화', disabled: true }],
};

export default {
  list,
  requestInfo,
  itemList,
  testCollectionInfo,
  tabs,
};

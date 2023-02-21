import dayjs from 'dayjs';

import api from '@/api';
import { TokenUtil } from '@/util';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAgoDate = dayjs().add(-1, 'M').format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'select', label: '조회' }],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
    legends: [{ value: '안정성시험', className: 'stabilityTest' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '자재구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmCd', '자재번호')
      .Input('pitmNm', '자재내역')
      .Input('ansNo', '시험번호')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .DatepickerTwinWithSwitch('searchReqDt', '의뢰일', {
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
      .col('ansTypNm', '시험구분')
      .col('ansNo', '시험번호')
      .col('plntCd', '플랜트코드')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('pitmEn', '자재내역(영문)')
      .col('rcpDt', '접수일자')
      .col('rcpRmk', '접수비고')
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
    title: '시험정보',
    countPerRow: 2,
    id: 'requestInfo',
    height: '350px',
  },
  forms: () =>
    FormBuilder.builder()
      .Input('ansNo', '시험번호', { readonly: true })
      .multiple(
        'input',
        '입고수량',
        FormBuilder.builder()
          .Input('etrQty', { readonly: true })
          .Input('inpUnit', { readonly: true })
          .spanCol(0.5)
          .build(),
      )
      .Input('pitmNm', '자재내역', { readonly: true })
      .Input('etrDt', '입고일자', { readonly: true })
      .Input('pitmCd', '자재번호', { readonly: true })
      .Input('phsOrderQty', '구매오더수량', { readonly: true })
      .Input('useLmt', '사용기한', { readonly: true })
      .Input('phsOrderNo', '구매오더번호', { readonly: true })
      .Input('splNm', '공급사', { readonly: true })
      .Input('splLotNo', '공급사제조번호', { readonly: true })
      .Input('makDt', '제조일자', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('rcpDt', '접수일자', { readonly: true })
      .Input('rcpUnm', '접수자', { readonly: true })
      .build(),
};

const testCollectionInfo = {
  static: {
    title: '검체채취',
    countPerRow: 2,
    id: 'testCollectionInfo',
    height: '350px',
    buttons: [
      { name: 'save', label: '저장', disabled: true },
      { name: 'collection', label: '채취', disabled: true },
      { name: 'init', label: '초기화', disabled: true },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('reqIdx', '의뢰idx')
      .Hidden('ansIdx', '시험idx')
      .Hidden('fileIdx', '첨부파일idx')
      .Hidden('plntCd', '플랜트코드')
      .Input('spcmNo', '표준견본번호')
      .multiple(
        '',
        '총검체량',
        FormBuilder.builder()
          .Input('smpVolTot', { readonly: true })
          .spanCol(2)
          .Input('inpUnit', { readonly: true })
          .build(),
      )
      .Select('clltMtd', '채취방법', {
        async: () => api.combo.userCommon.getStrgPlaCombo(),
      })
      .InputNumber('smpVolAns', '시험검체량')
      .required()
      .Select('clltMcn', '채취기구', {
        async: () => api.combo.userCommon.getClltMcnCombo(),
      })
      .InputNumber('smpVolStrg', '보관검체량')
      .Select('clltPla', '채취장소', {
        async: () => api.combo.userCommon.getClltPlaCombo(),
      })
      .InputNumber('smpVolEtc', '기타검체량')
      .Input('clltUnm', '채취자', { value: TokenUtil.myName(), disabled: true })
      .Select('smpStrgMtd', '검체보관방법', {
        async: () => api.combo.userCommon.getSmpStrgMtdCombo(),
      })
      .Datepicker('clltDt', '검체채취일', { value: todayDate, disabled: true })
      .RadioGroup('micYn', '미생물유무', {
        groups: [
          { label: '유', checkedValue: 'Y' },
          { label: '무', checkedValue: 'N' },
        ],
      })
      .build(),
};

export default {
  list,
  requestInfo,
  testCollectionInfo,
};

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'request', label: '의뢰' },
      { name: 'select', label: '조회' },
    ],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '자재구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmCd', '자재번호')
      .Input('pitmNm', '자재내역')
      .Input('ansNo', '시험번호')
      .Input('ispReqNo', '검사요청번호')
      .Input('phsOrderNo', '구매오더번호')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      //공통
      .col('group_cmmn', '공통정보', {
        children: ColumnBuilder.builder()
          .col('plntCd', '플랜트코드')
          .col('ansTypNm', '시험유형')
          .col('ansNo', '시험번호', { width: 100 })
          .col('ansEdt', '시험예정일자')
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
          .col('addCol1', '입고유형')
          .col('etrQty', '입고수량')
          .col('inpUnit', '입력단위')
          .col('etrDt', '입고일자')
          .col('addCol2', '입고취소여부')
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
      .Input('ispReqNo', '검사요청번호', { readonly: true })
      .Input('ispReqDt', '검사요청일자', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('batchNo', '배치번호', { readonly: true })
      .Input('makDt', '제조일자', { readonly: true })
      .Input('addInput1', '입고유형', { readonly: true })
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
      .Input('addInput2', '입고취소여부', { readonly: true })
      .Input('phsOrderTyp', '구매오더유형', { readonly: true })
      .Input('phsOrderNo', '구매오더번호', { readonly: true })
      .Input('phsOrderItm', '구매오더항목', { readonly: true })
      .Input('splCd', '공급사코드', { readonly: true })
      .Input('splNm', '공급사명', { readonly: true })
      .Input('splLotNo', '공급사제조번호', { readonly: true })
      .Input('phsCrgNm', '구매담당자', { readonly: true })
      .Input('savePla', '저장위치', { readonly: true })
      .Input('mtrDocNo', '자재문서번호', { readonly: true })
      .Input('mtrDocItm', '자재문서항목', { readonly: true })
      .Input('nomUnit', '기본단위', { readonly: true })
      .build(),
};

const testInfo = {
  static: {
    title: '시험정보',
    countPerRow: 4,
    id: 'testInfo',
    buttons: [
      { name: 'save', label: '수정', disabled: true },
      { name: 'requestRegist', label: '의뢰등록', disabled: true },
      { name: 'init', label: '초기화', disabled: true },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('reqIdx')
      .Hidden('ansIdx')
      .Hidden('pitmSpecIdx')
      .Hidden('pitmVer')
      .Hidden('pitmTyp')
      .multiple(
        'itemInfo',
        '시험선택',
        FormBuilder.builder()
          .Select('ansTyp', {
            async: () =>
              api.combo.systemCommon.getAnsTypCombo().then((res) => {
                res.data = res.data.filter(
                  ({ value }) =>
                    value !== 'S0230001' &&
                    value !== 'S0230002' &&
                    value !== 'S0230003' &&
                    value !== 'S0230004',
                );
                return res;
              }),
          })
          .required()
          .spanCol(2)
          .Input('ansNo', { readonly: true })
          .required()
          .spanCol(3)
          .Button('search', 'testModal', { type: 'search' })
          .build(),
      )
      .spanCol(2)
      .Input('pitmTypNm', '자재구분', { readonly: true })
      .Input('pitmCd', '자재번호', { readonly: true })
      .Input('ansProcNm', '진행상태', { readonly: true })
      .Input('sytJdgNm', '결과판정', { readonly: true })
      .Input('pitmNm', '자재내역', { readonly: true })
      .Input('pitmEn', '자재내역(영문)', { readonly: true })
      .build(),
};

export default {
  list,
  requestInfo,
  testInfo,
};

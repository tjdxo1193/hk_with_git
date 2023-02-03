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
      .Select('pitmTyp', '품목구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmCd', '품목코드')
      .Input('pitmNm', '품목명')
      .Input('ansNo', '시험번호')
      .Input('ispReqNo', '검사요청번호')
      .Input('phsOrderNo', '구매오더번호')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .col('plntCd', '사업장코드')
      .col('ansTypNm', '시험유형')
      .col('ansNo', '시험번호')
      .col('reqIdx', '의뢰IDX')
      .col('pitmTypNm', '품목구분')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명(국문)')
      .col('pitmEn', '품목명(영문)')
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
      .col('splNm', '공급사명')
      .col('splLotNo', '공급사제조번호')
      .col('phsCrgNm', '구매담당자')
      .col('savePla', '저장위치')
      .col('mtrDocNo', '자재문서번호')
      .col('mtrDocItm', '자재문서항목')
      .col('nomUnit', '기본단위')
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
      .Input('ispReqDt', '검사요청일자', { readonly: true })
      .Input('strgLmt', '보관기한', { readonly: true })
      .Input('useLmt', '사용기한', { readonly: true })
      .Input('ansTypNm', '입고유형', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('makDt', '제조일자', { readonly: true })
      .Input('vdrCtrtDt', '납품약정일자', { readonly: true })
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
      .Input('makEqp', '제조설비', { readonly: true })
      .Input('wrkNm', '작업자명', { readonly: true })
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
      .Input('pitmTypNm', '품목구분', { readonly: true })
      .Input('pitmCd', '품목코드', { readonly: true })
      .Input('ansProcNm', '진행상태', { readonly: true })
      .Input('sytJdgNm', '결과판정', { readonly: true })
      .Input('pitmNm', '품목명(국문)', { readonly: true })
      .Input('pitmEn', '품목명(영문)', { readonly: true })
      .Input('rcpUnm', '접수자', { readonly: true })
      .Input('rcpDt', '접수일자', { readonly: true })
      .Input('assUnm', '지시자', { readonly: true })
      .Input('assDt', '지시일자', { readonly: true })
      .Input('revwUnm', '검토자', { readonly: true })
      .Input('revwDt', '검토일자', { readonly: true })
      .Input('rstAprUnm', '결과승인자', { readonly: true })
      .Input('rstAprDs', '결과승인일시', { readonly: true })
      .build(),
};

export default {
  list,
  requestInfo,
  testInfo,
};

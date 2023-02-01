import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const srmOrderList = {
  static: {
    title: 'SRM마감오더',
    $grid: null,
    props: {
      editable: false,
    },
    countPerRow: 2,
    buttons: [{ name: 'selectSRM', label: '조회' }],
    height: '320px',
  },
  forms: () =>
    FormBuilder.builder()
      .Input('phsOrderNo', '구매오더번호')
      .Input('phsOrderItm', '오더항목')
      .Input('lotNo', '제조번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmTypNm', '품목유형')
      .col('pitmCd', false)
      .col('pitmNm', '품목명')
      .col('phsOrderTyp', '구매오더유형')
      .col('phsOrderNo', '구매오더번호')
      .col('orderItm', '오더항목', { width: 150 })
      .col('ispReqNo', '검사요청번호')
      .col('lotNo', '제조번호')
      .col('ansNo', '시험번호')
      .col('ansProcNm', '진행상태')
      .col('finlStt', '마감상태')
      .col('sytJdgNm', '판정결과')
      .col('crtDs', '수신일자')
      .col('ifInfoIdx', '연계정보ID')
      .build(),
};

const mesOrderList = {
  static: {
    title: 'MES마감오더',
    $grid: null,
    props: {
      editable: false,
    },
    countPerRow: 2,
    buttons: [{ name: 'selectMES', label: '조회' }],
    height: '320px',
  },
  forms: () =>
    FormBuilder.builder()
      .Input('pdtOrderNo', '생산오더번호')
      .Input('phsOrderItm', '오더항목')
      .Input('lotNo', '제조번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmTypNm', '품목유형')
      .col('pitmCd', false)
      .col('pitmNm', '품목명')
      .col('pdtOrderTyp', '생산오더유형')
      .col('pdtOrderNo', '생산오더번호')
      .col('orderItm', '오더항목', { width: 150 })
      .col('ispReqNo', '검사요청번호')
      .col('lotNo', '제조번호')
      .col('ansNo', '시험번호')
      .col('ansProcNm', '진행상태')
      .col('finlStt', '마감상태')
      .col('sytJdgNm', '판정결과')
      .col('crtDs', '수신일자')
      .col('ifInfoIdx', '연계정보ID')
      .build(),
};
export default {
  srmOrderList,
  mesOrderList,
};

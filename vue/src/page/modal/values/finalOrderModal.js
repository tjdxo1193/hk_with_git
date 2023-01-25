import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const srmSearchForm = {
  static: {
    title: 'SRM마감오더',
    $grid: null,
    props: {
      editable: false,
    },
    countPerRow: 2,
    buttons: [{ name: 'selectSRM', label: '조회' }],
    height: '350px',
  },
  forms: () =>
    FormBuilder.builder()
      .Select('phsOrderTyp', '구매오더유형', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('orderItm', '오더항목')
      .Input('lotNo', '제조번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('phsOrderTyp', '구매오더유형')
      .col('orderItm', '오더항목', { width: 150 })
      .col('lotNo', '제조번호')
      .col('finlStt', '마감상태')
      .col('crtDs', '수신일자')
      .col('ifInfoIdx', '연계정보ID')
      .build(),
};

const mesSearchForm = {
  static: {
    title: 'MES마감오더',
    $grid: null,
    props: {
      editable: false,
    },
    countPerRow: 2,
    buttons: [{ name: 'selectMES', label: '조회' }],
    height: '350px',
  },
  forms: () =>
    FormBuilder.builder()
      .Input('pdtOrderNo', '구매오더번호')
      .Input('orderItm', '오더항목')
      .Input('lotNo', '제조번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pdtOrderNo', '구매오더번호')
      .col('orderItm', '오더항목', { width: 150 })
      .col('lotNo', '제조번호')
      .col('finlStt', '마감상태')
      .col('crtDs', '수신일자')
      .col('ifInfoIdx', '연계정보ID')
      .build(),
};
export default {
  srmSearchForm,
  mesSearchForm,
};

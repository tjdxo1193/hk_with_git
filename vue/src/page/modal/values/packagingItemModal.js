import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const searchForm = {
  static: {
    $grid: null,
    props: {
      editable: false,
    },
    countPerRow: 4,
    buttons: [{ name: 'select', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('pitmTyp', { value: 'S0180500' })
      .Input('pitmTypNm', '자재구분', { readonly: true, value: '포장재' })
      .Input('pitmNm', '자재내역')
      .Input('pitmCd', '자재번호')
      .Select('crgDptCd', '담당부서', { async: api.combo.common.getDpt })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역', { width: 150 })
      .col('pitmEn', '자재내역(영문)')
      .col('pitmTypNm', '자재구분')
      .col('crgDptNm', '담당부서')
      .col('pitmVer', '자재버전')
      .col('useVerYn', '버전사용여부')
      .col('specProcNm', '진행상태')
      .build(),
};

export default {
  searchForm,
};

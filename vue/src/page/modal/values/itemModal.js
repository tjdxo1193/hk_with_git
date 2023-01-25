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
      .Hidden('opsSpecUseVerYn')
      .Select('pitmTyp', '품목구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmNm', '품목명')
      .Input('pitmCd', '품목코드')
      .Select('crgDptCd', '담당부서', { async: api.combo.common.getDpt })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명', { width: 150 })
      .col('pitmEn', '품목영문명')
      .col('pitmTypNm', '품목구분')
      .col('crgDptNm', '담당부서')
      .col('pitmVer', '품목버전')
      .col('useVerYn', '버전사용여부')
      .col('specProcNm', '진행상태')
      .build(),
};

export default {
  searchForm,
};

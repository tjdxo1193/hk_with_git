import { FormBuilder, ColumnBuilder } from '@/util';

const plant = {
  static: {
    title: '플랜트 관리',
    countPerRow: 1,
    $grid: null,
    props: {
      showRowCheckColumn: false,
      editable: false,
    },
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () => FormBuilder.builder().Input('plntNm', '플랜트 명').build(),
  columns: () =>
    ColumnBuilder.builder().col('plntCd', '플랜트 코드', false).col('plntNm', '플랜트 명').build(),
};

const input = {
  static: {
    countPerRow: 1,
    buttons: [
      { name: 'init', label: '신규' },
      { name: 'save', label: '저장' },
    ],
  },
  forms: () =>
    FormBuilder.builder().Hidden('plntCd', '플랜트 코드').Input('plntNm', '플랜트 명').build(),
};

export default {
  plant,
  input,
};

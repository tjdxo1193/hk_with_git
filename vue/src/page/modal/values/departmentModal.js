import { ColumnBuilder } from '@/util/builder';

const list = {
  static: {
    $grid: null,
    props: {
      displayTreeOpen: false,
      flat2tree: true,
      treeIdField: 'dptCd',
      treeIdRefField: 'hirDptCd',
      selectionMode: 'singleCell',
      showRowCheckColumn: false,
      useContextMenu: true,
      enableFilter: true,
      editable: false,
    },
    buttons: [{ name: 'select', label: '조회' }],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('dptNm', '부서 명', { width: '40%' })
      .col('plntCd', false)
      .col('dptCd', '부서 코드', false)
      .col('rmk', '비고', { width: '50%' })
      .col('useYn', '사용여부', { width: '10%' })
      .col('hirDptCd', false)
      .build(),
};

export default {
  list,
};

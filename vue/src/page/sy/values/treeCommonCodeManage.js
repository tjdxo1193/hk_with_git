import { ColumnBuilder, FormBuilder } from '@/util';

const useYnCombo = [
  { value: 'Y', label: '사용함' },
  { value: 'N', label: '사용안함' },
];

const commonCode = {
  static: {
    countPerRow: 1,
    $grid: null,
    props: {
      flat2tree: true,
      treeIdField: 'treeCd',
      treeIdRefField: 'hirTreeCd',
      editable: true,
      displayTreeOpen: true,
    },
    buttons: [
      { name: 'addRow', label: '하위 행 추가' },
      { name: 'search', label: '조회' },
      { name: 'save', label: '저장' },
    ],
  },
  forms: () => FormBuilder.builder().Input('treeNm', '코드명').build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('treeNm', '코드명')
      .col('treeEn', '코드 영문명')
      .col('treeAbbr', '약칭')
      .combo('useYn', '사용여부', {
        list: useYnCombo,
        width: '8%',
      })
      .col('treeCdOrd', '정렬 순서', { width: '8%' })
      .col('treeRmk', '비고')
      .col('treeCd', '코드', false)
      .col('hirTreeCd', '상위 코드', false)
      .build(),
};

export default {
  commonCode,
};

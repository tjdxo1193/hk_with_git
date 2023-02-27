import { ColumnBuilder, FormBuilder } from '@/util/builder';

const list = {
  static: {
    title: '부서 관리',
    countPerRow: 2,
    $grid: null,
    buttons: [{ name: 'select', label: '조회' }],
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
  },
  forms: () =>
    FormBuilder.builder()
      .Input('dptNm', '부서 명')
      .RadioGroup('useYn', '사용여부', {
        value: '',
        groups: [
          { checkedValue: '', label: '전체' },
          { checkedValue: 'Y', label: '사용함' },
          { checkedValue: 'N', label: '사용안함' },
        ],
        gap: 60,
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('dptNm', '부서 명', { width: '25%' })
      .col('plntCd', { visible: false })
      .col('dptCd', { visible: false })
      .col('dptAbbr', '부서 약어', { width: '25%' })
      .col('rmk', '비고', { width: '20%' })
      .col('useYn', '사용여부', { width: '10%' })
      .col('hirDptCd', { visible: false })
      .col('ord', '정렬순서', { width: '10%' })
      .col('seq', '순번', { width: '10%' })
      .build(),
};

const detail = {
  static: {
    title: '상세정보',
    countPerRow: 3,
    id: 'detail',
    buttons: [
      { name: 'new', label: '신규' },
      { name: 'save', label: '저장' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .multiple(
        'hirDptCd',
        '상위 부서 명',
        FormBuilder.builder()
          .Input('hirDptNm', { readonly: true })
          .spanCol(4)
          .required()
          .Button('search', 'departmentModal', { type: 'search' })
          .build(),
      )
      .Hidden('hirDptCd')
      .Hidden('dptCd', '부서 코드')
      .Input('dptNm', '부서 명', { maxLength: 20 })
      .required()
      .Input('dptAbbr', '부서 약어', { maxLength: 20 })
      .RadioGroup('useYn', '사용여부', {
        value: 'Y',
        groups: [
          { checkedValue: 'Y', label: '사용함' },
          { checkedValue: 'N', label: '사용안함' },
        ],
        gap: 60,
      })
      .InputNumber('ord', '정렬순서')
      .InputNumber('seq', '순번')
      .Input('rmk', '비고', { maxLength: 100 })
      .spanCol(2)
      .build(),
};

export default {
  list,
  detail,
};

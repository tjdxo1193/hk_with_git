import { ColumnBuilder, FormBuilder } from '@/util/builder';

const list = {
  static: {
    title: '메뉴관리',
    countPerRow: 2,
    $grid: null,
    buttons: [{ name: 'select', label: '조회' }],
    props: {
      editable: false,
      displayTreeOpen: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('menuNm', '메뉴명')
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
      .col('menuNm', '메뉴명', { width: 250 })
      .col('menuDesc', '메뉴 설명')
      .col('useYn', '사용여부', { width: 100 })
      .col('hirMenuCd', false)
      .col('menuCd', false)
      .build(),
};

const detail = {
  static: {
    title: '상세정보',
    countPerRow: 4,
    id: 'detail',
    buttons: [{ name: 'save', label: '저장' }],
  },
  forms: () =>
    FormBuilder.builder()
      .RadioGroup('useYn', '사용여부', {
        value: 'Y',
        groups: [
          { checkedValue: 'Y', label: '사용함' },
          { checkedValue: 'N', label: '사용안함' },
        ],
        gap: 60,
      })
      .required()
      .Input('menuDesc', '메뉴 설명', { maxLength: 100 })
      .spanCol(3)
      .Hidden('menuCd')
      .build(),
};

export default {
  list,
  detail,
};

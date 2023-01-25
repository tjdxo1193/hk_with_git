import { ColumnBuilder, FormBuilder } from '@/util';

const useYnCombo = [
  { value: 'Y', label: 'Y' },
  { value: 'N', label: 'N' },
];

const authManageGrid = {
  static: {
    title: '권한 조회',
    countPerRow: 2,
    data: [],
    $grid: null,
    props: { editable: true },
    buttons: [
      { name: 'select', label: '조회' },
      { name: 'addRow', label: '행 추가' },
      { name: 'save', label: '저장' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('athNm', '권한 그룹명')
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
      .col('athCd', false)
      .col('athNm', '권한 그룹 명')
      .col('athdesc', '권한 그룹 설명')
      .col('ord', '순서')
      .combo('useYn', '사용여부', { list: useYnCombo })
      .col('crtUid', '생성자', { editable: false })
      .col('crtDs', '생성 일시', { editable: false })
      .build(),
};

const allMenuGrid = {
  static: {
    title: '전체 메뉴',
    data: [],
    $grid: null,
    props: { editable: false, showRowCheckColumn: true },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('hirMenuNm', '상위 메뉴 명')
      .col('athCd', false)
      .col('menuNm', '메뉴명')
      .col('menuCd', false)
      .build(),
};

const authMenuGrid = {
  static: {
    title: ' 권한 메뉴',
    data: [],
    $grid: null,
    props: { editable: false, showRowCheckColumn: true },
    buttons: [{ label: '저장', name: 'menuSave' }],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('hirMenuNm', '상위 메뉴 명')
      .col('athCd', false)
      .col('menuNm', '메뉴명')
      .col('menuCd', false)
      .build(),
};

export default {
  authManageGrid,
  allMenuGrid,
  authMenuGrid,
};

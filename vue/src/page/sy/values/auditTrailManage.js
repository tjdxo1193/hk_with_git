import { ColumnBuilder, FormBuilder } from '@/util';

const menu = {
  static: {
    title: '메뉴 목록',
    labelWidth: '90px',
    countPerRow: 3,
    $grid: null,
    height: '600px',
    props: {
      showRowCheckColumn: false,
      displayTreeOpen: true,
    },
    buttons: [{ name: 'search', label: '조회' }],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('code', '메뉴 코드', { editable: false, width: 150 })
      .col('name', '메뉴 명', { editable: false })
      .build(),
  forms: () =>
    FormBuilder.builder()
      .Input('menuCd', '메뉴코드')
      .Input('upperMenuNm', '상위메뉴명')
      .Input('menuNm', '하위메뉴명')
      .build(),
};

const column = {
  static: {
    title: 'Audit 마스터 목록',
    $grid: null,
    height: '673px',
    props: {
      showRowCheckColumn: false,
    },
    buttons: [
      { name: 'init', label: '초기화', debounce: 0 },
      { name: 'addRow', label: '행추가', debounce: 0 },
      { name: 'removeRow', label: '행삭제', debounce: 0 },
      { name: 'save', label: '저장', type: 'warn' },
    ],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('seqNo', '일련번호', false)
      .col('menuCd', '메뉴코드', false)
      .col('menuNm', '메뉴')
      .col('columnHeaderNm', '헤더명')
      .col('tableNm', '테이블명', {
        editRenderer: {
          type: 'DropDownListRenderer',
          keyField: 'value',
          valueField: 'label',
          list: [],
        },
      })
      .col('columnNm', '컬럼명', {
        editRenderer: {
          type: 'DropDownListRenderer',
          keyField: 'value',
          valueField: 'label',
          list: [],
        },
      })
      .col('columnComment', '컬럼 코멘트', { editable: false })
      .col('joinTableNm', '조인 테이블명', {
        editRenderer: {
          type: 'DropDownListRenderer',
          keyField: 'value',
          valueField: 'label',
          list: [],
        },
      })
      .col('joinColumnNm', '조인 조건 컬럼명', {
        editRenderer: {
          type: 'DropDownListRenderer',
          keyField: 'value',
          valueField: 'label',
          list: [],
        },
      })
      .col('joinDisplayColumnNm', '조인 목표 컬럼명', {
        editRenderer: {
          type: 'DropDownListRenderer',
          keyField: 'value',
          valueField: 'label',
          list: [],
        },
      })
      .build(),
};

export default {
  menu,
  column,
};

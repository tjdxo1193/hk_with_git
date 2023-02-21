import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const reagentStandardGrid = {
  static: {
    title: '조회',
    countPerRow: 2,
    $grid: null,
    props: { editable: false },
    legends: [],
    buttons: [{ name: 'select', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('div', '구분', {
        value: null,
        async: api.combo.getSampleCombo,
      })
      .Input('dept', '자재내역')
      .Select('div', '재료구분', {
        value: null,
        async: api.combo.getSampleCombo,
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .col('title', '구분')
      .col('price', '재료구분')
      .col('description', '자재내역')
      .col('category', '관리번호')
      .col('image', '규격')
      .col('price', '단위')
      .col('title', '사용기간')
      .col('price', '동구분')
      .col('description', '안전재고량')
      .col('category', '차광여부')
      .col('image', '물질차감여부')
      .col('price', '유독물질')
      .col('title', '사고대비물질')
      .col('price', '특별관리물질')
      .col('description', '위험물여부')
      .col('category', '영문명')
      .col('image', '담당자')
      .col('price', 'CASNO')
      .col('title', '보관조건')
      .col('price', '세부보관장소')
      .col('description', '비고')
      .build(),
};

const reagentStandardForm = {
  static: {
    title: '정보등록',
    countPerRow: 3,
    id: 'reagentStandardForm',
  },
  forms: () =>
    FormBuilder.builder()
      .Select('title', '구분', {
        value: null,
        async: api.combo.getSampleCombo,
      })
      .Input('image', '규격')
      .Select('price', '동구분', {
        value: null,
        async: api.combo.getSampleCombo,
      })
      .Select('price', '재료구분', {
        value: null,
        async: api.combo.getSampleCombo,
      })
      .Select('price', '단위', {
        value: null,
        async: api.combo.getSampleCombo,
      })
      .Input('description', '안전재고량')
      .Input('description', '자재내역')
      .Input('title', '사용기간')
      .Slot('slotItem', '슬롯 아이템')
      .spanRow(2)
      .Input('category', '영문명')
      .Select('image', '담당자', {
        value: null,
        async: api.combo.getSampleCombo,
      })
      .Input('price', 'CASNO')
      .Select('title', '보관조건', {
        value: null,
        async: api.combo.getSampleCombo,
      })
      .Textarea('description', '비고', { rows: 3 })
      .spanRow(2)
      .Input('category', '관리번호')
      .Input('price', '세부보관장소')
      .build(),
};

const mainButtons = [
  { name: 'save', label: '등록' },
  { name: 'update', disabled: true, label: '수정' },
  { name: 'delete', disabled: true, label: '삭제' },
  { name: 'init', label: '초기화' },
];

export default {
  reagentStandardGrid,
  reagentStandardForm,
  mainButtons,
};

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const list = {
  static: {
    title: '주기목록',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plantCd', '공장코드', false)
      .col('ansCylCd', '시험주기코드', false)
      .col('ansCylRuleNm', '시험주기규칙명')
      .col('ansCylMarkNm', '시험주기표기명')
      .col('ansCylDiv', '시험주기구분코드', false)
      .col('ansCylDivNm', '시험주기구분')
      .col('ansItv', '시험간격')
      .col('cylOrd', '정렬순서')
      .build(),
};

const detailForm = {
  static: {
    title: '상세정보',
    countPerRow: 4,
    buttons: [
      { name: 'save', label: '저장' },
      { name: 'update', label: '수정', disabled: true },
      { name: 'delete', label: '삭제', disabled: true, type: 'danger' },
      { name: 'init', label: '초기화' },
    ],
  },

  forms: () =>
    FormBuilder.builder()
      .Hidden('ansCylCd', '시험주기코드')
      .Input('ansCylRuleNm', '시험주기규칙명')
      .readonly()
      .Input('ansCylMarkNm', '시험주기표기명')
      .required()
      .Select('ansCylDiv', '시험주기구분', {
        async: () => api.combo.systemCommon.getAnsCylDivCombo(),
      })
      .required()
      .InputNumber('ansItv', '시험간격')
      .required()
      .InputNumber('cylOrd', '정렬순서')
      .required()
      .build(),
};

export default {
  list,
  detailForm,
};

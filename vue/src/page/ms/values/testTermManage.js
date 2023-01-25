import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const term = {
  static: {
    title: '시험 기간 목록',
    countPerRow: 1,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    height: '563px',
    props: { showRowCheckColumn: false, editable: false },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('ansTrmMarkNm', '표기명')
      .RadioGroup('useYn', '사용여부', {
        value: '',
        groups: [
          { checkedValue: '', label: '전체' },
          { checkedValue: 'Y', label: '사용함' },
          { checkedValue: 'N', label: '사용안함' },
        ],
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '사업장코드', false)
      .col('ansTrmCd', '시험기간코드', false)
      .col('ansTrmMarkNm', '시험기간표기명')
      .col('ansCylDiv', '시험주기구분코드', false)
      .col('ansCylDivNm', '시험주기구분')
      .col('ansCylDivEn', '시험주기구분영문명', false)
      .col('ansCylDivAbbr', '시험주기구분약어', false)
      .col('ansTrm', '시험기간')
      .col('ansItv', '시험간격')
      .col('trmOrd', '정렬순서')
      .col('useYn', '사용여부')
      .build(),
};

const termInput = {
  static: {
    title: '시험 기간',
    countPerRow: 2,
    buttons: [
      { name: 'delete', label: '삭제', type: 'danger', disabled: true },
      { name: 'new', label: '신규' },
      { name: 'save', label: '저장' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('ansTrmCd')
      .Input('ansTrmMarkNm', '시험 기간 표기명')
      .required()
      .Select('ansCylDiv', '주기 구분', { async: () => api.combo.systemCommon.getAnsCylDivCombo() })
      .required()
      .Hidden('ansCylDivNm', '주기 구분명')
      .Hidden('ansCylDivEn', '주기 구분 영문명')
      .Hidden('ansCylDivAbbr', '주기 구분 약어')
      .InputNumber('ansTrm', '시험 기간')
      .required()
      .validator({
        message: `시험기간이 시험간격 보다 작습니다.`,
        handler(value, formData) {
          if (value < formData.ansItv) {
            return '시험기간이 시험간격 보다 작습니다.';
          }
          return true;
        },
      })
      .InputNumber('ansItv', '시험 간격')
      .required()
      .validator({
        message: `시험간격이 시험기간보다 큽니다.`,
        handler(value, formData) {
          if (value > formData.ansTrm) {
            return '시험간격이 시험기간보다 큽니다.';
          }
          return true;
        },
      })
      .InputNumber('trmOrd', '정렬순서')
      .required()
      .RadioGroup('useYn', '사용 여부', {
        value: 'Y',
        groups: [
          { checkedValue: 'Y', label: '사용함' },
          { checkedValue: 'N', label: '사용안함' },
        ],
      })
      .build(),
};

const interval = {
  static: {
    title: '시험 기간 간격 목록',
    $grid: null,
    height: '447px',
    props: { showRowCheckColumn: true, editable: true },
    buttons: [
      { name: 'addRow', label: '행추가', debounce: 0, disabled: true },
      { name: 'removeRow', label: '행삭제', disabled: true },
      { name: 'save', label: '저장', disabled: true },
    ],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plantCd', '공장코드', false)
      .col('ansTrmCd', '시험기간코드', false)
      .col('itvSeq', '간격순번', false)
      .col('ansAccVal', '시험누적값', {
        editRenderer: {
          type: 'DropDownListRenderer',
          keyField: 'value',
          valueField: 'label',
          list: [],
        },
      })
      .col('accRulNm', ' 누적규칙명', { editable: false })
      .col('accMarkNm', '누적표기명')
      .build(),
};

export default {
  term,
  termInput,
  interval,
};

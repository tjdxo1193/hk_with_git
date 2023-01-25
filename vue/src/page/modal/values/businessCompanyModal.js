import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const mainButtons = {
  buttons: [
    { name: 'choose', label: '선택' },
    { name: 'create', label: '등록' },
    { name: 'update', label: '수정', disabled: true },
    { name: 'delete', label: '삭제', disabled: true, type: 'danger' },
    { name: 'select', label: '조회' },
  ],
};

const list = {
  static: {
    $grid: null,
    props: { editable: false },
    countPerRow: 3,
  },
  forms: () =>
    FormBuilder.builder()
      .Select('vdrDiv', '구분', {
        async: () => api.combo.systemCommon.getVdrDivCombo(),
      })
      .RadioGroup('delYn', '사용여부', {
        value: '',
        groups: [
          { checkedValue: '', label: '전체' },
          { checkedValue: 'N', label: '사용중' },
          { checkedValue: 'Y', label: '사용안함' },
        ],
        gap: 60,
      })
      .spanCol(2)
      .Input('vdrNm', '거래처명')
      .spanCol(3)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('vdrCd', false)
      .col('vdrDiv', false)
      .col('vdrDivNm', '구분')
      .col('vdrNm', '거래처명')
      .col('vdrCrgNm', '거래처 담당자')
      .col('vdrCrgTel', '전화번호')
      .col('vdrAdr', '주소')
      .col('vdrAdrDtl', '상세주소')
      .build(),
};

const detail = {
  static: {
    countPerRow: 2,
  },
  forms: () =>
    FormBuilder.builder()
      .Select('vdrDiv', '구분', {
        async: () => api.combo.systemCommon.getVdrDivCombo(),
      })
      .required()
      .Input('vdrNm', '거래처명')
      .required()
      .Input('vdrCrgNm', '거래처 담당자')
      .Input('vdrCrgTel', '전화번호')
      .Input('vdrAdr', '주소')
      .spanCol(2)
      .Input('vdrAdrDtl', '상세주소')
      .spanCol(2)
      .Hidden('plntCd')
      .Hidden('vdrCd')
      .build(),
};

export default {
  mainButtons,
  list,
  detail,
};

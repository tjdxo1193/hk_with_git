import api from '@/api';
import { FormBuilder } from '@/util';

const regReason = {
  static: {
    title: '사유등록',
    countPerRow: 2,
  },
  forms: () =>
    FormBuilder.builder()
      .Input('pitmCd', '품목코드')
      .readonly()
      .Input('pitmNm', '품목명')
      .readonly()
      .Select('rvsReaCd', '개정사유', { async: () => api.combo.userCommon.getRvsReaCombo() })
      .required()
      .spanCol(2)
      .Textarea('rvsCtt', '개정세부내역', { rows: 8 })
      .required()
      .spanCol(2)
      .spanRow(2)
      .build(),
};

const regButton = {
  buttons: [{ name: 'reg', label: '등록' }],
};

export default {
  regReason,
  regButton,
};

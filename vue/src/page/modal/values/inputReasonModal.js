import api from '@/api';
import { FormBuilder } from '@/util';

const reasonForm = {
  static: {
    countPerRow: 1,
  },
  forms: () =>
    FormBuilder.builder()
      .Select('rjtReaDiv', '사유', {
        async: () => api.combo.userCommon.getRjtReaCombo(),
      })
      .required()
      .Textarea('rjtRea', '세부내역', { rows: 8 })
      .spanRow(2)
      .required()
      .build(),
};

const buttonGroups = {
  buttons: [
    { name: 'check', label: '확인' },
    { name: 'close', label: '닫기' },
  ],
};
export default {
  reasonForm,
  buttonGroups,
};

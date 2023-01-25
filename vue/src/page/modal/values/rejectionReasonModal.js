import { FormBuilder } from '@/util';

const rejectionReason = {
  static: {
    countPerRow: 1,
  },
  forms: () => FormBuilder.builder().Textarea('rjtRea', '사유', { rows: 8 }).build(),
};
const buttonsGroup = {
  buttons: [
    { name: 'close', label: '닫기' },
    { name: 'check', label: '확인' },
  ],
};
export default {
  rejectionReason,
  buttonsGroup,
};

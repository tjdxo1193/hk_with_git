import { FormBuilder } from '@/util';

const reason = {
  static: {
    countPerRow: 1,
  },
  forms: () => FormBuilder.builder().Textarea('reason', '사유', { rows: 3 }).build(),
};

const sign = {
  static: {
    countPerRow: 1,
  },
  forms: () =>
    FormBuilder.builder()
      .Input('username', '아이디')
      .required()
      .InputPassword('password', '비밀번호')
      .required()
      .build(),
};

export default {
  reason,
  sign,
};

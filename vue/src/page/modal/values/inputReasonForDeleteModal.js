import { FormBuilder } from '@/util';

const reasonForm = {
  static: {
    countPerRow: 2,
  },
  forms: () => FormBuilder.builder().Textarea('delRea', { rows: 8 }).spanCol(2).required().build(),
};

export default {
  reasonForm,
};

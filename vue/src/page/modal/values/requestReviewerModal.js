import api from '@/api';
import { FormBuilder } from '@/util';

const list = {
  static: {
    countPerRow: 2,
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('useUid')
      .Input('useNm', '검토요청자', { disabled: true })
      .Input('todayDate', '검토요청일', { disabled: true })
      .Select('user', '검토자', {
        async: api.combo.common.getReviewerList,
      })
      .required()
      .spanCol(2)
      .build(),
};

const buttonGroups = {
  buttons: [{ name: 'requestReview', label: '검토요청' }],
};

export default {
  list,
  buttonGroups,
};

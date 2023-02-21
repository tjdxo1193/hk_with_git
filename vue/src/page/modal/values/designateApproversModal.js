import api from '@/api';
import { FormBuilder } from '@/util';

const list = {
  static: {
    countPerRow: 2,
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('useUid')
      .Input('useNm', '승인요청자', { disabled: true })
      .Input('todayDate', '승인요청일', { disabled: true })
      .Select('user', '승인자', {
        async: api.combo.common.getApproverList,
      })
      .required()
      .spanCol(2)
      .build(),
};

const buttonGroups = {
  buttons: [{ name: 'approveRequest', label: '승인요청' }],
};

export default {
  list,
  buttonGroups,
};

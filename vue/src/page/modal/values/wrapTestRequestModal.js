import dayjs from 'dayjs';

import api from '@/api';
import { FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const rvsDivPsComboList = [
  { value: 'P', label: '자재' },
  { value: 'S', label: '규격서' },
];

const reasonForm = {
  static: {
    countPerRow: 2,
  },
  forms: () =>
    FormBuilder.builder()
      .Input('rvsNo', '개정번호')
      .required()
      .Input('docNo', '문서번호')
      .required()
      .Datepicker('rvsDt', '개정일자', { value: todayDate })
      .Datepicker('enfoDt', '시행일', { value: todayDate })
      .required()
      .Select('rvsDivPs', '개정구분', { elements: rvsDivPsComboList })
      .Select('rvsReaCd', '개정사유', { async: () => api.combo.userCommon.getRvsReaCombo() })
      .Textarea('rvsCtt', '개정세부내역', { rows: 8 })
      .spanCol(2)
      .spanRow(2)
      .Select('aprUid', '승인자', {
        async: api.combo.common.getApproverList,
      })
      .required()
      .Textarea('aprReqRea', '승인요청사유', { rows: 4 })
      .required()
      .spanCol(2)
      .spanRow(2)
      .spanCol(2)
      .build(),
};

const buttonGroup = {
  buttons: [
    { name: 'save', label: '등록' },
    { name: 'close', label: '닫기' },
  ],
};
export default {
  reasonForm,
  buttonGroup,
};

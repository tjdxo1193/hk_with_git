import dayjs from 'dayjs';

import { FormBuilder } from '@/util';
const todayDate = dayjs().format('YYYY-MM-DD');

const regReason = {
  static: {
    title: '사유등록',
    countPerRow: 2,
  },
  forms: () =>
    FormBuilder.builder()
      .multiple(
        '01',
        '품목정보',
        FormBuilder.builder().Input('02').readonly().Input('03').readonly().spanCol(2).build(),
      )
      .spanCol(2)
      .Input('04', '개정번호')
      .Input('05', '문서번호')
      .Datepicker('06', '개정일자', { value: todayDate })
      .Datepicker('07', '시행일', { value: todayDate })
      .disabled()
      .CheckboxGroup('08', '개정구분', {
        value: ['one'],
        countPerRow: 3,
        space: 'none',
        gap: 50,
        groups: [
          { checkedValue: '81', label: '품목' },
          { checkedValue: '82', label: '규격서' },
        ],
      })
      .Select('09', '개정사유', {})
      .spanCol(3)
      .Textarea('10', '개정세부내역', { rows: 4 })
      .spanCol(2)
      .spanRow(2)
      .build(),
};

const button = {
  buttons: [{ name: 'reg', label: '등록' }],
};

export default {
  regReason,
  button,
};

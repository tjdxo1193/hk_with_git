import { FormBuilder } from '@/util';

const inputForm = {
  static: {
    title: '비밀번호 설정',
    buttons: [{ value: 'save', label: '저장' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Input(1, '최소자리수')
      .Input(2, '문자중복횟수제한')
      .RadioGroup(5, '조합옵션', {
        value: 'C',
        noGap: true,
        groups: [
          { checkedValue: 'Y', label: '영문자' },
          { checkedValue: 'N', label: '숫자' },
          { checkedValue: 'N', label: '영문자 + 숫자' },
          { checkedValue: 'N', label: '영문자 + 숫자 + 특수문자' },
        ],
      })
      .spanCol(2)
      .spanRow(4)
      .Input(3, '재사용')
      .RadioGroup(4, '연속비밀번호사용여부', {
        value: 'C',
        groups: [
          { checkedValue: 'Y', label: 'Y' },
          { checkedValue: 'N', label: 'N' },
        ],
        gap: 30,
      })
      .Input(6, '관리자 변경기간(일)')
      .Input(8, '사용자 변경기간(일)')
      .Input(7, '관리자 입력횟수(회)')
      .Input(9, '사용자 입력횟수(회)')
      .build(),
};

export default {
  inputForm,
};

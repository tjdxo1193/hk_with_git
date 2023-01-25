import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const userGrid = {
  static: {
    title: '조회',
    countPerRow: 3,
    $grid: null,
    props: { editable: false },
    buttons: [{ name: 'select', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('userNm', '사용자명')
      .Input('userLognId', 'ID')
      .Input('dptNm', '부서명')
      .Input('userTel', '전화번호')
      .Input('gradeNm', '직급')
      .Input('titleNm', '직책')
      .RadioGroup('useYn', '사용여부', {
        value: '',
        groups: [
          { checkedValue: '', label: '전체' },
          { checkedValue: 'Y', label: '사용함' },
          { checkedValue: 'N', label: '사용안함' },
        ],
        gap: 60,
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('userId', false)
      .col('plntCd', false)
      .col('dptCd', false)
      .col('athCd', false)
      .col('lognFailCnt', false)
      .col('lockYn', false)
      .col('userLognId', 'ID')
      .col('userNm', '사용자명')
      .col('emid', '사번')
      .col('dptNm', '부서명')
      .col('gradeNm', '직급')
      .col('titleNm', '직책')
      .col('useYn', '사용여부')
      .col('userTel', '전화번호')
      .col('userMail', '이메일')
      .build(),
};

const userForm = {
  static: {
    title: '상세정보',
    countPerRow: 3,
    buttons: [
      { name: 'init', label: '초기화' },
      { name: 'save', label: '저장' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('userNm', '사용자명', { disabled: true })
      .required()
      .Hidden('userId')
      .Input('userLognId', 'ID', { disabled: true })
      .required()
      .Input('emid', '사번', { disabled: true })
      .multiple(
        'dptNm',
        '부서명',
        FormBuilder.builder()
          .Input('dptNm', { disabled: true })
          .required()
          .spanCol(3)
          .Button('search', { type: 'search' })
          .build(),
      )
      .Hidden('dptCd')
      .Input('gradeNm', '직급', { disabled: true })
      .Input('titleNm', '직책', { disabled: true })
      .Select('athCd', '사용권한', {
        value: null,
        async: api.combo.auth.getUserAuthList,
      })
      .required()
      .multiple(
        'pwd',
        '비밀번호',
        FormBuilder.builder().Button('initPwd', { label: '비밀번호초기화' }).build(),
      )
      .Input('lognFailCnt', '로그인 실패 횟수', { disabled: true })
      .Input('userMail', '이메일', { disabled: true })
      .Input('userTel', '전화번호', { disabled: true })
      .RadioGroup('useYn', '사용여부', {
        value: 'Y',
        groups: [
          { checkedValue: 'Y', label: '사용함' },
          { checkedValue: 'N', label: '사용안함' },
        ],
        gap: 60,
      })
      .blank()
      .build(),
};

export default {
  userGrid,
  userForm,
};

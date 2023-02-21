import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const userGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
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
      .col('userId', { visible: false })
      .col('plntCd', { visible: false })
      .col('dptCd', { visible: false })
      .col('athCd', { visible: false })
      .col('lognFailCnt', { visible: false })
      .col('lockYn', { visible: false })
      .col('emid', { visible: false })
      .col('ansYn', '시험자여부', { visible: false })
      .col('clltYn', '채취자여부', { visible: false })
      .col('revwYn', '검토자여부', { visible: false })
      .col('aprYn', '승인자여부', { visible: false })
      .col('ord', { visible: false })
      .col('userLognId', 'ID')
      .col('userNm', '사용자명')
      .col('emid', '사번')
      .col('dptNm', '부서명')
      .col('gradeNm', '직급')
      .col('titleNm', '직책')
      .col('useYn', '사용여부')
      .col('userTel', '전화번호')
      .col('userMail', '이메일')
      .col('userNmEn', '사용자명영문')
      .col('userCompTel', '사용자회사연락처')
      .build(),
};

const userForm = {
  static: {
    title: '상세정보',
    countPerRow: 4,
    buttons: [
      { name: 'save', label: '저장' },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('userId')
      .Hidden('dptCd')
      .Input('userNm', '사용자명', { disabled: true, _required: true })
      .Input('userLognId', 'ID', { disabled: true, _required: true })
      .Input('emid', '사번', { disabled: true })
      .multiple(
        'dptNm',
        '부서명',
        FormBuilder.builder()
          .Input('dptNm', { disabled: true, _required: true, _colSpan: 3 })
          .Button('search', { type: 'search' })
          .build(),
      )
      .Input('gradeNm', '직급', { disabled: true })
      .Input('titleNm', '직책', { disabled: true })
      .Select('athCd', '사용권한', {
        value: null,
        async: api.combo.auth.getUserAuthList,
        _required: true,
      })
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
      })
      .RadioGroup('ansYn', '시험자여부', {
        value: 'Y',
        groups: [
          { checkedValue: 'Y', label: '예' },
          { checkedValue: 'N', label: '아니오' },
        ],
      })
      .RadioGroup('clltYn', '채취자여부', {
        value: 'Y',
        groups: [
          { checkedValue: 'Y', label: '예' },
          { checkedValue: 'N', label: '아니오' },
        ],
      })
      .RadioGroup('revwYn', '검토자여부', {
        value: 'Y',
        groups: [
          { checkedValue: 'Y', label: '예' },
          { checkedValue: 'N', label: '아니오' },
        ],
      })
      .RadioGroup('aprYn', '검토자여부', {
        value: 'Y',
        groups: [
          { checkedValue: 'Y', label: '예' },
          { checkedValue: 'N', label: '아니오' },
        ],
      })
      .build(),
};

export default {
  userGrid,
  userForm,
};

import { ColumnBuilder, FormBuilder } from '@/util/builder';

const list = {
  static: {
    title: '공통 코드 목록',
    countPerRow: 2,
    $grid: null,
    buttons: [
      { name: 'add', label: '행추가' },
      { name: 'save', label: '저장' },
      { name: 'search', label: '조회' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('hirCdNm', '그룹코드명')
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
      .combo('cdDivSu', '코드 구분', {
        list: [
          { value: 'U', label: '사용자 코드' },
          { value: 'S', label: '시스템 코드' },
        ],
        width: '10%',
      })
      .col('hirCdNm', '그룹코드명', {
        width: '15%',
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 20,
        },
      })
      .col('hirCd', '그룹코드', { editable: false })
      .col('hirDesc', '설명', {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 100,
        },
      })
      .combo('useYn', '사용여부', {
        list: [
          { value: 'Y', label: '사용' },
          { value: 'N', label: '사용안함' },
        ],
        width: '8%',
      })
      .col('udtUid', '수정자ID', { widht: '15%' })
      .col('udtDs', '수정일', { widht: '15%' })
      .col('hirCdOrd', '정렬순서', {
        width: '8%',
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
          maxlength: 3,
        },
      })
      .col('plntCd', false)
      .build(),
};

const detail = {
  static: {
    title: '상세 정보',
    countPerRow: 2,
    $grid: null,
    buttons: [
      { name: 'add', label: '행추가', disabled: true },
      { name: 'save', label: '저장', disabled: true },
      { name: 'search', label: '조회', disabled: true },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('dtlNm', '코드명')
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
      .col('dtlNm', '코드명', {
        width: '25%',
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 20,
        },
      })
      .col('dtlCd', '코드')
      .col('dtlEn', '영문명', {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 20,
        },
      })
      .col('dtlRmk', '약어', {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 20,
        },
      })
      .col('extCd1', '여분코드1', {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 10,
        },
      })
      .col('extCd2', '여분코드2', {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 10,
        },
      })
      .col('extCd3', '여분코드3', {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 10,
        },
      })
      .combo('useYn', '사용여부', {
        list: [
          { value: 'Y', label: '사용' },
          { value: 'N', label: '사용안함' },
        ],
        width: '8%',
      })
      .col('dtlCdOrd', '정렬순서', {
        width: '8%',
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
          maxlength: 3,
        },
      })
      .col('hirCd', false)
      .col('plntCd', false)
      .build(),
};

export default {
  list,
  detail,
};

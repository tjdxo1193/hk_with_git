import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const tabs = {
  tabs: [
    {
      name: 'testMaterialInfo',
      label: '시험재료관리',
    },
    {
      name: 'testMaterialSe',
      label: '시험재료 구분 관리',
    },
  ],
};

const testMaterialManage = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperRitmTreeCd', '구분', {
        async: api.combo.common.getTestMaterialTreeCd,
      })
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .Input('ritmKn', '자재내역')
      .RadioGroup('useYn', '사용여부', {
        value: '',
        groups: [
          { checkedValue: '', label: '전체' },
          { checkedValue: 'Y', label: '사용함' },
          { checkedValue: 'N', label: '사용안함' },
        ],
        gap: 50,
      })
      .Hidden('treeCd', { value: 'R0000000' })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmCd', '일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmTreeCd', false)
      .col('ritmTreeNm', '재료구분', { width: 120 })
      .col('ritmKn', '자재내역')
      .col('mngNo', '관리번호')
      .col('spec', '규격')
      .col('ritmUnitCd', false)
      .col('ritmUnitNm', '단위')
      .col('opnAftExpirTrm', '사용기간(개월)', { width: 100 })
      .col('sfyStok', '안전재고량')
      .col('ritmEn', '영문명')
      .col('ritmCrgUid', '담당자')
      .col('casNo', 'CASNO')
      .col('strgTerms', false)
      .col('strgTermsNm', '보관조건')
      .col('strgPla', false)
      .col('shadeYn', '차광여부')
      .col('mtrDedutYn', '물질차감여부')
      .col('poisYn', '유독물여부')
      .col('dangYn', '위험물여부')
      .col('useYn', '사용여부')
      .col('ritmRmk', '비고')
      .build(),
};

const testMaterialManageInfo = {
  static: {
    title: '정보등록',
    countPerRow: 4,
    buttons: [
      { name: 'save', label: '저장' },
      { name: 'delete', label: '삭제', disabled: true },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperRitmTreeCd', '구분', {
        async: api.combo.common.getTestMaterialTreeCd,
      })
      .required()
      .Input('sfyStok', '안전재고량', { maxLength: 5 })
      .validator((value) => value === '' || Number.isInteger(Number(value)))
      .Select('ritmCrgUid', '담당자', {
        async: api.combo.common.getUserList,
      })
      .CheckboxGroup('extraYn', '부가정보 여부', {
        groups: [
          { checkedValue: 'shadeYn', label: '차광여부' },
          { checkedValue: 'mtrDedutYn', label: '물질차감여부' },
          { checkedValue: 'poisYn', label: '유독물여부' },
          { checkedValue: 'dangYn', label: '위험물여부' },
        ],
        gap: 80,
        countPerRow: 2,
      })
      .spanRow(3)
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .required()
      .Select('ritmUnitCd', '단위', {
        async: () => api.combo.userCommon.getUnitCombo(),
      })
      .Input('casNo', 'CASNO', { maxLength: 20 })
      .Input('ritmKn', '자재내역', { maxLength: 50 })
      .required()
      .Input('opnAftExpirTrm', '사용기간(개월)', { maxLength: 5 })
      .validator((value) => value === '' || Number.isInteger(Number(value)))
      .Input('mngNo', '관리번호', { maxLength: 20 })
      .Input('ritmEn', '영문명', { maxLength: 50 })
      .Select('strgPla', '보관장소', {
        async: () => api.combo.userCommon.getStrgPlaCombo(),
      })
      .Input('spec', '규격', { maxLength: 50 })
      .Textarea('ritmRmk', '비고', { rows: 4, maxLength: 500 })
      .spanRow(2)
      .Input('ritmAbbr', '약어', { maxLength: 20 })
      .Select('strgTerms', '보관조건', {
        async: () => api.combo.userCommon.getStrgTermsCombo(),
      })
      .Input('fomu', '화학식', { maxLength: 20 })
      .Hidden('ritmCd')
      .build(),
};

export default {
  tabs,
  testMaterialManage,
  testMaterialManageInfo,
};

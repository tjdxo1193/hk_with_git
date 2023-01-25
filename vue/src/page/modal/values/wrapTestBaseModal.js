import api from '@/api';
import { FormBuilder, ColumnBuilder } from '@/util';

const list = {
  static: {
    countPerRow: 2,
  },
  forms: () =>
    FormBuilder.builder()
      // pkgaTypNm
      .Select('pkgaDiv', '포장시험 유형 명', {
        async: () => api.combo.systemCommon.getPackageDivCombo(),
      })
      .required()
      .Input('sapPrdha', 'SAP 제품계층구조')
      .disabled()
      .required()
      .Input('specProcCdNm', '진행상태')
      .disabled()
      .Input('useVerYn', '사용 버전 여부')
      .disabled()
      .build(),
};

const sapItemList = {
  static: {
    title: 'SAP 리스트',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('prdha')
      .col('idx', '고유일련번호')
      .col('degree', '수신차수')
      .col('matnr', '자재번호')
      .col('mtart', '자재유형')
      .col('meins', '기본단위')
      .col('matkl', '기본그룹')
      .col('bismt', '기존자재번호')
      .col('spart', '제품군')
      .col('mstae', '플랜트간자재상태')
      .col('wrkst', '기본자재')
      .col('zeinr', '전표')
      .col('taklv', '세금분류')
      .col('bstme', '오더단위')
      .col('xchpf', '배치관리')
      .col('ekwsl', '구매값키')
      .col('mhdhb', '총셀프라이프')
      .col('zlabno', 'LabNo')
      .col('zprodAbbr', '생산약어(약호)')
      .col('crtDs', '수신일시')
      .col('ifInfoIdx')
      .build(),
};

const listButtonGroups = {
  buttons: [{ name: 'okay', label: '확인' }],
};

export default {
  list,
  listButtonGroups,
  sapItemList,
};

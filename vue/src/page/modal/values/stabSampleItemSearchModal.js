import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const sampleGrid = {
  static: {
    countPerRow: 2,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      showRowCheckColumns: false,
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '품목구분', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('pitmCd', '품목코드')
      .Input('pitmNm', '품목명')
      .blank()
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('smpMngIdx', { visible: false })
      .col('smpDpsProc', { visible: false })
      .col('pitmTyp', { visible: false })
      .col('pitmTypNm', '품목유형')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('ansTyp', { visible: false })
      .col('ansTypNm', '시험유형')
      .col('ansIdx', { visible: false })
      .col('smpDivCd', { visible: false })
      .col('smpDivNm', '검체구분')
      .col('inpUnit', '보관단위')
      .col('remains', '재고량')
      .col('smpStrgMtd', { visible: false })
      .col('smpStrgMtdNm', '검체보관방법')
      .col('smpDpsNm', '검체진행상태')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('makDt', '제조일자')
      .col('strgLmt', '보관기한')
      .col('useLmt', '사용기한')
      .col('irgYn', '이상여부')
      .col('smpRmk', '비고')
      .col('dpsExpDt', '폐기확정일자')
      .col('smpDpsAprIdx', { visible: false })
      .col('smpEtrDt', '입고일자')
      .build(),
};

const bottomButtons = {
  static: {
    buttons: [
      { name: 'select', label: '선택' },
      { name: 'close', label: '닫기', type: 'normal' },
    ],
  },
};

export default {
  sampleGrid,
  bottomButtons,
};

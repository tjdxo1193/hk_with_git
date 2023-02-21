import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const testSearchGrid = {
  static: {
    buttons: [{ name: 'search', label: '조회' }],
    countPerRow: 2,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('smpDivCd', '검체구분', {
        async: () => api.combo.systemCommon.getSmpDivCombo(),
      })
      .Select('ansTyp', '시험유형', {
        async: () => api.combo.systemCommon.getAnsTypCombo(),
      })
      .Input('pitmNm', '자재내역')
      .Input('ansNo', '시험번호')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('ansTyp', { visible: false })
      .col('reqIdx', { visible: false })
      .col('smpMngIdx', { visible: false })
      .col('pitmTyp', { visible: false })
      .col('smpDivCd', { visible: false })
      .col('ansNo', '시험번호')
      .col('smpDivNm', '검체구분')
      .col('pitmTypNm', '자재유형')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('useLmt', '사용기한')
      .col('makDt', '제조일자')
      .col('mngSmpVol', '시험검체량')
      .col('inpUnit', '입력단위')
      .col('ansNo', '시험번호')
      .col('ansTypNm', '시험유형')
      .col('rcpDt', '접수일자')
      .col('reqDt', '의뢰일자')
      .col('batchNo', '배치번호')
      .col('strgLmt', '보관기한')
      .col('irgYn', '이상여부')
      .col('smpEtrDt', '검체입고일자')
      .col('smpStrgMtdNm', '검체보관방법')
      .col('smpDpsNm', '검체상태')
      .build(),
};

const modalButtons = {
  buttons: [
    { name: 'select', label: '선택' },
    { name: 'close', label: '닫기' },
  ],
};

export default {
  testSearchGrid,
  modalButtons,
};

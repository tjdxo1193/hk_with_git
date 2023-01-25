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
      .Select('mngSmpVol', '검체구분', {
        async: () => api.combo.systemCommon.getSmpDivCombo(),
      })
      .Select('ansTyp', '시험유형', {
        async: () => api.combo.systemCommon.getAnsTypCombo(),
      })
      .Input('pitmNm', '품목명')
      .Input('ansNo', '시험번호')
      .Input('batchNo', '배치번호')
      .Input('lotNo', '제조번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('ansTyp', { visible: false })
      .col('reqIdx', { visible: false })
      .col('smpMngIdx')
      .col('ansNo', '시험번호')
      .col('pitmNm', '품목명')
      .col('pitmCd', '품목코드')
      .col('ansTypNm', '시험유형')
      .col('rcpDt', '접수일자')
      .col('reqDt', '의뢰일자')
      .col('batchNo', '배치번호')
      .col('lotNo', '제조번호')
      .col('makDt', '제조일자')
      .col('strgLmt', '보관기한')
      .col('useLmt', '사용기한')
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

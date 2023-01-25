import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const sampleGrid = {
  static: {
    countPerRow: 2,
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('smpDiv', '검체구분', {
        async: () => api.combo.systemCommon.getSmpDivCombo(),
      })
      .Select('pitmDiv', '품목구분', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('ansNo', '시험번호')
      .Input('pitmCd', '품목코드')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('smpMngIdx', false)
      .col('smpDpsProc', false)
      .col('smpDpsNm', '검체입고')
      .col('pitmTyp', false)
      .col('pitmTypNm', '검체상태')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('ansTyp', false)
      .col('ansTypNm', '시험유형')
      .col('ansIdx', false)
      .col('smpDivCd', false)
      .col('ansDivNm', '검체구분')
      .col('mngSmpVol', '')
      .build(),
};

export default {
  sampleGrid,
};

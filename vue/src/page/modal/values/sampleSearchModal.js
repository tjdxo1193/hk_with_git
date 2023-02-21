import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const sampleGrid = {
  static: {
    countPerRow: 2,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('smpDiv', '검체구분', {
        async: () => api.combo.systemCommon.getSmpDivCombo(),
      })
      .Select('pitmDiv', '자재구분', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('ansNo', '시험번호')
      .Input('pitmCd', '자재번호')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('smpMngIdx', { visible: false })
      .col('smpDpsProc', { visible: false })
      .col('pitmTyp', { visible: false })
      .col('pitmTypNm', '자재구분')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('ansTyp', { visible: false })
      .col('ansTypNm', '시험유형')
      .col('ansIdx', { visible: false })
      .col('smpDivCd', { visible: false })
      .col('smpDivNm', '검체구분')
      .col('ansTypNm', '시험구분')
      .col('mngSmpVol', '검체량')
      .col('sumVol', '총사용량')
      .col('remains', '재고량')
      .col('inpUnit', '보관수량단위')
      .col('smpDpsNm', '검체상태')
      .build(),
};

export default {
  sampleGrid,
};

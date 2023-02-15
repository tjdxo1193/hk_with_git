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
      .col('ansTyp', { visible: false })
      .col('ansIdx', { visible: false })
      .col('smpDivCd', { visible: false })
      .col('smpStrgMtd', { visible: false })
      .col('smpDpsAprIdx', { visible: false })
      .col('smpStrgMtdNm', { visible: false })
      .col('smpDpsNm', { visible: false })
      .col('strgLmt', { visible: false })
      .col('useLmt', { visible: false })
      .col('irgYn', { visible: false })
      .col('smpRmk', { visible: false })
      .col('dpsExpDt', { visible: false })
      .col('smpEtrDt', { visible: false })
      .col('pitmTypNm', '품목유형')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('ansNo', '시험번호')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('makDt', '제조일자')
      .col('ansTypNm', '시험유형')
      .col('smpDivNm', '검체구분')
      .col('mngSmpVol', '안정성검체량')
      .col('inpUnit', '보관단위')
      .col('remains', '재고량')
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

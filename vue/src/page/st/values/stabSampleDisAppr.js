import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const weekAgoDate = dayjs().add(-1, 'week').format('YYYY-MM-DD');

const sampleGrid = {
  static: {
    title: '조회',
    buttons: [
      { name: 'approve', label: '승인' },
      { name: 'reject', label: '반려' },
      { name: 'search', label: '조회' },
    ],
    props: {
      showRowCheckColumn: true,
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '자재유형', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('pitmNm', '자재내역', { maxLength: 100 })
      .Input('lotNo', '제조번호', { maxLength: 20 })
      .Input('batchNo', '배치번호', { maxLength: 20 })
      .Input('pitmCd', '자재번호', { maxLength: 8 })
      .Input('strgPla', '보관장소', { maxLength: 9 })
      .DatepickerTwinWithSwitch('useLmtDtList', '사용기한', {
        value: [weekAgoDate, todayDate],
        _colSpan: 2,
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('smpDpsNm', '승인구분')
      .col('pitmTypNm', '자재유형')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('makDt', '제조일자')
      .col('useLmt', '사용기한')
      .col('ansTypNm', '시험유형')
      .col('remains', '재고량')
      .col('mngSmpVol', '안정성검체량')
      .col('inpUnit', '안정성검체량단위')
      .build(),
};

export default {
  sampleGrid,
};

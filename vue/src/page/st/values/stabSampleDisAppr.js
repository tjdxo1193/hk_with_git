import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const yesterdayDate = dayjs().add(-1, 'day').format('YYYY-MM-DD');

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
      .Select('pitmTyp', '품목유형', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .Input('pitmCd', '품목코드')
      .Select('strgPla', '보관장소', {
        api: () => api.combo.userCommon.getSmpStrgMtdCombo(),
      })
      .Input('pitmNm', '품목명')
      .DatepickerTwinWithSwitch('useLmt', '사용기한', {
        value: [yesterdayDate, todayDate],
        _colSpan: 2,
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmTypNm', '품목유형')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('lotNo', '제조번호')
      .col('useLmt', '사용기한')
      .col('remains', '재고량')
      .col('mngSmpVol', '안정성검체량')
      .col('inpUnit', '안정성검체량단위')
      .build(),
};

export default {
  sampleGrid,
};

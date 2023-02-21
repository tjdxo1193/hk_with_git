import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const yesterdayDate = dayjs().add(-1, 'day').format('YYYY-MM-DD');

const sampleGrid = {
  static: {
    title: '조회',
    legends: [
      { value: '승인대기중', className: 'approveWating' },
      { value: '반려', className: 'return' },
      { value: '폐기', className: 'disposal' },
    ],
    buttons: [
      { name: 'requestDispose', label: '폐기요청' },
      { name: 'requestCancelDispose', label: '폐기취소요청' },
      { name: 'search', label: '조회' },
    ],
    props: {
      showRowCheckColumn: true,
      editable: false,
      rowStyleFunction: (rowIndex, item) => {
        if (item.dpsYn === 'Y' || item.smpDpsProc === 'S0270500') {
          return 'disposal';
        }
        if (item.smpDpsProc === 'S0270400' || item.smpDpsProc === 'S0270600') {
          return 'approveWating';
        }
        if (item.smpDpsProc === 'S0270310' || item.smpDpsProc === 'S0270510') {
          return 'return';
        }
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '자재유형', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('lotNo', '제조번호', { maxLength: 20 })
      .Input('batchNo', '배치번호', { maxLength: 20 })
      .Input('pitmCd', '자재번호', { maxLength: 8 })
      .Input('pitmNm', '자재내역', { maxLength: 100 })
      .DatepickerTwinWithSwitch('useLmtDtList', '사용기한', {
        value: [yesterdayDate, todayDate],
        _colSpan: 2,
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmTyp', { visible: false })
      .col('pitmTypNm', '자재유형')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('lotNo', '제조번호')
      .col('makDt', '제조일자')
      .col('ansTypNm', '시험유형')
      .col('useLmt', '사용기한')
      .col('smpDivNm', '검체구분')
      .col('remains', '재고량')
      .col('mngSmpVol', '안정성검체량')
      .col('inpUnit', '안정성검체량단위')
      .col('smpDpsNm', '폐기진행상태')
      .build(),
};

export default {
  sampleGrid,
};

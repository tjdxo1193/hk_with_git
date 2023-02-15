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
        if (item.dpsYn === 'Y') {
          return 'disposal';
        }
        if (item.smpUseProc === 'S0280200' || item.smpUseProc === 'S0280400') {
          return 'approveWating';
        }
        if (item.smpUseProc === 'S0280110' || item.smpUseProc === 'S0280310') {
          return 'return';
        }
      },
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
      .Input('pitmNm', '품목명')
      .DatepickerTwinWithSwitch('useLmtDtList', '사용기한', {
        value: [yesterdayDate, todayDate],
        _colSpan: 2,
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmTyp', { visible: false })
      .col('pitmTypNm', '품목유형')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
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

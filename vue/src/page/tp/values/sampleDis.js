import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const monthAgoDate = dayjs().add(-1, 'month').format('YYYY-MM-DD');

const todayDate = dayjs().format('YYYY-MM-DD');

const sampleDisGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'requestDis', label: '폐기요청' },
      { name: 'requestCancelDis', label: '폐기취소요청' },
      { name: 'search', label: '조회' },
    ],
    legends: [
      { value: '승인대기중', className: 'approveWating' },
      { value: '반려', className: 'return' },
      { value: '폐기', className: 'disposal' },
    ],
    props: {
      editable: false,
      showRowCheckColumn: true,
      rowStyleFunction: (rowIndex, item) => {
        if (item.smpDpsProc === 'S0270500') {
          return 'disposal';
        } else if (item.smpDpsProc === 'S0270400' || item.smpDpsProc === 'S0270600') {
          return 'approveWating';
        } else if (item.smpDpsProc === 'S0270310' || item.smpDpsProc === 'S0270510') {
          return 'return';
        }
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('smpDivCd', '검체구분', { async: () => api.combo.systemCommon.getSmpDivCombo() })
      .Input('pitmNm', '품목명')
      .Input('ansNo', '시험번호')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .Input('pitmCd', '품목코드')
      .Select('smpProcCd', '검체상태', { async: () => api.combo.systemCommon.getSmpProcCombo() })
      .Select('pitmTyp', '품목구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .DatepickerTwinWithSwitch('dpsExpDtList', '폐기예정일', { value: [monthAgoDate, todayDate] })
      .DatepickerTwinWithSwitch('smpEtrDtList', '검체입고일자', {
        value: [monthAgoDate, todayDate],
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('smpMngIdx', { visible: false })
      .col('useSeq', { visible: false })
      .col('pitmTyp', { visible: false })
      .col('smpDiv', { visible: false })
      .col('smpDpsProc', { visible: false })
      .col('usePps', { visible: false })
      .col('useUid', { visible: false })
      .col('rjtUid', { visible: false })
      .col('pitmNm', '품목명')
      .col('pitmCd', '품목코드')
      .col('ansNo', '시험번호')
      .col('pitmTypNm', '품목구분')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('smpDivNm', '검체구분')
      .col('smpDpsNm', '검체상태')
      .col('rjtNm', '반려자')
      .col('rjtDs', '반려일시')
      .build(),
};

export default {
  sampleDisGrid,
};

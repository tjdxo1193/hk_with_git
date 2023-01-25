import dayjs from 'dayjs';

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
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('smpDiv', '검체구분')
      .Input('pitmNm', '품목명')
      .Input('ansNo', '시험번호')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .Input('pitmCd', '품목코드')
      .Select('smpProcCd', '검체상태')
      .Select('pitmDiv', '품목구분')
      .DatepickerTwinWithSwitch('dpsExpDtList', '폐기예정일', { value: [monthAgoDate, todayDate] })
      .DatepickerTwinWithSwitch('smpEtrDtList', '검체입고일자', {
        value: [monthAgoDate, todayDate],
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('smpMngIdx', false)
      .col('useSeq', false)
      .col('pitmNm', '품목명')
      .col('pitmCd', '품목코드')
      .col('ansNo', '시험번호')
      .col('pitmTyp', false)
      .col('pitmTypNm', '품목구분')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('smpDiv', false)
      .col('smpDivNm', '검체구분')
      .col('smpDpsProc', false)
      .col('smpDpsNm', '검체상태')
      .col('usePps', false)
      .col('useUid', false)
      .col('rjtUid', false)
      .col('rjtNm', '반려자')
      .col('rjtDs', '반려일시')
      .build(),
};

export default {
  sampleDisGrid,
};

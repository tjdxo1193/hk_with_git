import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const sampleGrid = {
  static: {
    title: '조회',
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      showRowCheckColumn: true,
      editable: false,
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
      .DatepickerTwinWithSwitch('dpsExpDtList', '폐기예정일', { value: [todayDate, todayDate] })
      .DatepickerTwinWithSwitch('smpEtrDtList', '검체입고일자', {
        value: [todayDate, todayDate],
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('smpMngIdx', false)
      .col('useSeq', false)
      .col('aprIdx', false)
      .col('pitmNm', '품목명')
      .col('pitmCd', '품목코드')
      .col('pitmTyp', false)
      .col('pitmTypNm', '품목구분')
      .col('ansNo', '시험번호')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('smpDiv', false)
      .col('smpDiv', '검체구분')
      .col('smpDpsProc', false)
      .col('smpDpsNm', '검체상태')
      .col('rjtUid', false)
      .col('rjtNm', '반려자')
      .col('rjtDs', '반려일시')
      .build(),
};

const inputForm = {
  static: {
    title: '입력정보',
    buttons: [
      { name: 'approve', label: '승인' },
      { name: 'reject', label: '반려' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('smpMngIdx')
      .Hidden('smpdpsProc')
      .Input('pitmNm', '품목명')
      .readonly()
      .Input('ansNo', '시험번호')
      .readonly()
      .Input('ansTypNm', '시험유형')
      .readonly()
      .Hidden('ansTyp')
      .Input('rcpDt', '접수일자')
      .readonly()
      .Input('pitmCd', '품목코드')
      .readonly()
      .Input('lotNo', '제조번호')
      .readonly()
      .Input('batchNo', '배치번호')
      .readonly()
      .Input('makDt', '제조일자')
      .readonly()
      .Input('smpEtrDt', '검체입고일자')
      .readonly()
      .Input('smpDpsNm', '검체상태')
      .readonly()
      .Hidden('smpDpsProc')
      .Hidden('pitmTyp')
      .Input('pitmTypNm', '품목유형')
      .readonly()
      .Select('smpMngVol', '검체구분', {
        async: () => api.combo.systemCommon.getSmpDivCombo(),
      })
      .Select('smpStrgMtd', '검체보관방법', {
        async: () => api.combo.userCommon.getSmpStrgMtdCombo(),
      })
      .multiple(
        'smp',
        '관리검체량',
        FormBuilder.builder()
          .Input('smpStrgVol', { readonly: true })
          .Select('smpVolUnit', {
            async: () => api.combo.userCommon.getSmpVolUnitCombo(),
          })
          .build(),
      )
      .Input('inpUnit', '입력단위')
      .readonly()
      .Input('strgLmt', '보관기한')
      .readonly()
      .Input('useLmt', '사용기한')
      .readonly()
      .RadioGroup('irgYn', '이상여부', {
        groups: [
          { label: '정상', checkedValue: 'N' },
          { label: '이상', checkedValue: 'Y' },
        ],
      })
      .Input('dpsExpDt', '폐기예정일자', { value: todayDate })
      .readonly()
      .Textarea('smpRmk', '검체비고')
      .readonly()
      .build(),
};

export default {
  sampleGrid,
  inputForm,
};

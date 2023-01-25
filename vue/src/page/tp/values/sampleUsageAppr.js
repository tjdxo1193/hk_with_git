import { ColumnBuilder, FormBuilder } from '@/util';

const sampleUsageGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
    props: {
      showRowCheckColumn: true,
      editable: false,
    },
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('smpDiv', ' 검체구분')
      .Input('pitmCd', '품목코드')
      .Select('pitmDiv', '품목구분')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .Input('pitmNm', '품목명')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('smpMngIdx', false)
      .col('useSeq', false)
      .col('pitmNm', '품목명')
      .col('pitmCd', '품목코드')
      .col('pitmTyp', { visible: false })
      .col('pitmTypNm', '품목구분')
      .col('ansNo', '시험번호')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('smpDiv', { visible: false })
      .col('smpDiv', '검체구분')
      .col('smpUseProc', { visible: false })
      .col('smpUseNm', '검체상태')
      .col('useSmpVol', '사용검체량')
      .col('usePps', { visible: false })
      .col('useUid', { visible: false })
      .col('useNm', '사용자')
      .col('strgPla', '보관장소')
      .col('smpUseAprIdx', { visible: false })
      .col('rjtUid', { visible: false })
      .build(),
};

const inputForm = {
  static: {
    title: '입력정보',
    countPerRow: 4,
    buttons: [
      { name: 'approve', label: '승인' },
      { name: 'reject', label: '반려' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('smpMngIdx')
      .Hidden('useSeq')
      .Input('pitmNm', '품목명')
      .readonly()
      .Input('pitmCd', '품목코드')
      .readonly()
      .Input('pitmDiv', '품목구분')
      .readonly()
      .Input('ansNo', '시험번호')
      .readonly()
      .Input('lotNo', '제조번호')
      .readonly()
      .Input('batchNo', '배치번호')
      .readonly()
      .Input('smpDiv', '검체구분')
      .readonly()
      .Input('smpUseProc', '검체상태')
      .readonly()
      .Input('useSmpVol', '사용검체량')
      .readonly()
      .Hidden('usePps')
      .Hidden('useUid')
      .Input('useNm', '사용자')
      .readonly()
      .Input('strgPla', '보관장소')
      .readonly()
      .Hidden('smpUseAprIdx')
      .build(),
};

export default {
  sampleUsageGrid,
  inputForm,
};

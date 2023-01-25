import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const sampleUsageGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
    },
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
      .col('plntCd', { visible: false })
      .col('smpMngIdx', { visible: false })
      .col('useSeq', { visible: false })
      .col('pitmNm', '품목명')
      .col('pitmCd', '품목코드')
      .col('pitmTyp', { visible: false })
      .col('pitmTypNm', '품목구분')
      .col('ansNo', '시험번호')
      .col('smpDivNm', '검체구분')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('smpDivCd', { visible: false })
      .col('smpUseProc', { visible: false })
      .col('smpUseNm', '검체상태')
      .col('useSmpVol', '사용검체량')
      .col('usePps', { visible: false })
      .col('useUid', { visible: false })
      .col('useNm', '사용자')
      .col('strgPla', '보관장소')
      .col('smpUseAprIdx', { visible: false })
      .col('rjtUid', { visible: false })
      .col('rjtNm', '반려자')
      .col('rjtDs', '반려일시')
      .col('rjtRea', '반려사유')
      .build(),
};

const inputForm = {
  static: {
    title: '입력정보',
    countPerRow: 4,
    buttons: [
      { name: 'requestApproveUse', label: '사용승인요청', disabled: false },
      { name: 'save', label: '저장' },
      { name: 'update', label: '수정', disabled: false },
      { name: 'delete', label: '삭제', disabled: false },
      { name: 'requestApproveCancelUse', label: '사용취소요청', disabled: false },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('smpMngIdx', 'smpMngIdx')
      .Hidden('useSeq', 'useSeq')
      .multiple(
        'pitm',
        '품목명',
        FormBuilder.builder()
          .Input('pitmNm')
          .required()
          .readonly()
          .spanCol(10)
          .Button('search', '검색', { type: 'search' })
          .build(),
      )
      .Hidden('pitmCd', '품목코드')
      .Hidden('pitmTyp')
      .Input('pitmTypNm', '품목구분')
      .readonly()
      .Input('ansNo', '시험번호')
      .readonly()
      .Select('smpDivCd', '검체구분', {
        async: () => api.combo.systemCommon.getSmpDivCombo(),
      })
      .Input('lotNo', '제조번호')
      .readonly()
      .Input('batchNo', '배치번호')
      .readonly()
      .Hidden('smpDivCd')
      .Hidden('smpUseProc')
      .Input('smpUseNm', '검체상태')
      .readonly()
      .Input('useSmpVol', '사용검체량')
      .Hidden('useUid')
      .Input('useNm', '사용자')
      .readonly()
      .Input('strgPla', '보관장소')
      .Textarea('usePps', '사용목적')
      .Hidden('smpUseAprIdx')
      .Hidden('rjtUid', 'rjtUid')
      .build(),
};

export default {
  sampleUsageGrid,
  inputForm,
};

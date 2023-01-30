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
      .col('plntCd', false)
      .col('smpMngIdx', false)
      .col('useSeq', false)
      .col('pitmTyp', false)
      .col('smpDivCd', false)
      .col('smpUseProc', false)
      .col('usePps', false)
      .col('useUid', false)
      .col('smpUseAprIdx', false)
      .col('rjtUid', false)
      .col('pitmNm', '품목명')
      .col('pitmCd', '품목코드')
      .col('pitmTypNm', '품목구분')
      .col('ansNo', '시험번호')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('smpDivNm', '검체구분')
      .col('useSmpVol', '사용량')
      .col('mngSmpVol', '보관수량')
      .col('sumVol', '총사용량')
      .col('remains', '재고량')
      .col('inpUnit', '보관수량단위')
      .col('useNm', '사용자')
      .col('useDt', '사용일')
      .col('smpDpsNm', '상태')
      .col('smpDpsProc', false)
      .col('smpUseNm', '사용이력진행상황')
      .col('strgPla', false)
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
      .Hidden('useSeq')
      .Hidden('useUid')
      .Hidden('pitmCd')
      .Hidden('pitmTyp')
      .Hidden('smpDivCd')
      .Hidden('smpUseProc')
      .Hidden('smpUseAprIdx')
      .Hidden('rjtUid')
      .Hidden('smpDivCd')
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
      .Input('smpDivNm', '검체구분')
      .readonly()
      .Input('pitmTypNm', '품목구분')
      .readonly()
      .Input('ansNo', '시험번호')
      .readonly()
      .Input('lotNo', '제조번호')
      .readonly()
      .Input('batchNo', '배치번호')
      .readonly()
      .Input('smpDpsNm', '검체상태')
      .readonly()
      .Input('useNm', '사용자')
      .readonly()
      .Input('mngSmpVol', '보관수량')
      .readonly()
      .Input('sumVol', '총사용량')
      .readonly()
      .Input('remains', '재고량')
      .readonly()
      .Input('inpUnit', '보관단위')
      .readonly()
      .InputNumber('useSmpVol', '사용검체량')
      .Input('strgPla', '보관장소')
      .Textarea('usePps', '사용목적')
      .spanCol(2)
      .build(),
};

export default {
  sampleUsageGrid,
  inputForm,
};

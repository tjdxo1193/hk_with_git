import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const sampleUsageGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
    props: {
      showRowCheckColumn: true,
      editable: false,
    },
    buttons: [
      { name: 'approve', label: '승인' },
      { name: 'reject', label: '반려' },
      { name: 'search', label: '조회' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('smpDivCd', ' 검체구분', { async: () => api.combo.systemCommon.getSmpDivCombo() })
      .Input('pitmCd', '품목코드')
      .Select('pitmTyp', '품목구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .Input('pitmNm', '품목명')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('smpMngIdx', { visible: false })
      .col('useSeq', { visible: false })
      .col('pitmTyp', { visible: false })
      .col('smpDiv', { visible: false })
      .col('smpUseProc', { visible: false })
      .col('usePps', { visible: false })
      .col('useUid', { visible: false })
      .col('smpUseAprIdx', { visible: false })
      .col('rjtUid', { visible: false })
      .col('pitmNm', '품목명')
      .col('pitmCd', '품목코드')
      .col('pitmTypNm', '품목구분')
      .col('ansNo', '시험번호')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('smpDivNm', '검체구분')
      .col('useSmpVol', '사용검체량')
      .col('useNm', '사용자')
      .col('strgPla', '보관장소')
      .col('smpUseNm', '검체상태')
      .col('useDt', '사용일자')
      .build(),
};

const inputForm = {
  static: {
    title: '입력정보',
    countPerRow: 4,
    buttons: [{ name: 'init', label: '초기화' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('smpMngIdx')
      .Hidden('useSeq')
      .Hidden('usePps')
      .Hidden('useUid')
      .Hidden('smpUseAprIdx')
      .Input('pitmNm', '품목명', { readonly: true })
      .Input('pitmCd', '품목코드', { readonly: true })
      .Input('pitmTypNm', '품목구분', { readonly: true })
      .Input('ansNo', '시험번호', { readonly: true })
      .Input('lotNo', '제조번호', { readonly: true })
      .Input('batchNo', '배치번호', { readonly: true })
      .Input('smpDivNm', '검체구분', { readonly: true })
      .Input('smpUseNm', '검체상태', { readonly: true })
      .Input('mngSmpVol', '관리검체량', { readonly: true })
      .Input('useSmpVol', '사용검체량', { readonly: true })
      .Input('inpUnit', '입력단위', { readonly: true })
      .Input('useNm', '사용자', { readonly: true })
      .Input('strgPla', '보관장소', { readonly: true })
      .Input('useDt', '사용일자', { readonly: true })
      .build(),
};

export default {
  sampleUsageGrid,
  inputForm,
};

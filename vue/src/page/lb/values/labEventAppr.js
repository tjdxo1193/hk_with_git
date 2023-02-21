import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const searchGridWithForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'approve', label: '승인' },
      { name: 'return', label: '반려' },
      { name: 'search', label: '조회' },
    ],
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '자재구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmCd', '자재번호')
      .Input('pitmNm', '자재내역')
      .Input('ansNo', '시험 번호')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('labEvtIdx', false)
      .col('ansNo', '시험 번호')
      .col('ansIdx', false)
      .col('rstSeq', false)
      .col('pitmTyp', false)
      .col('pitmTypNm', '자재구분')
      .col('pitmCd', false)
      .col('pitmNm', '자재내역')
      .col('orderNo', '오더 번호')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치 번호')
      .col('amitmCd', '시험항목별방법 코드')
      .col('amitmNm', '시험항목별방법 명')
      .col('ocrDt', '발생 일자')
      .col('ansUid', false)
      .col('tstLogRpubYn', '시험일지 재발행 여부')
      .col('ansUidNm', '시험자')
      .col('crtvMsr', false)
      .col('revwCmmt', false)
      .col('revwUid', false)
      .col('revwDs', false)
      .col('revwIp', false)
      .col('labEvtAprIdx', false)
      .col('rjtUid', false)
      .col('rjtDs', false)
      .col('rjtRea', false)
      .build(),
};

const reviewInfoForm = {
  static: {
    title: '검토의견',
    countPerRow: 4,
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('revwUid', '검토 UID')
      .Input('revwUidNm', '검토자')
      .readonly()
      .Hidden('revwDs', '검토 일시')
      .Input('revwDt', '검토 일자')
      .readonly()
      .Hidden('revwIp', '검토 IP')
      .blank()
      .spanCol(2)
      .Textarea('revwCmmt', '검토의견', { rows: 4 })
      .spanCol(4)
      .readonly()
      .build(),
};

const detailInfoForm = {
  static: {
    title: '상세정보',
    countPerRow: 4,
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('labEvtIdx', '실험실 이벤트 IDX')
      .Input('ansNo', '시험 번호')
      .readonly()
      .Hidden('ansIdx', '시험 IDX')
      .Hidden('pitmTyp', '자재구분')
      .Input('pitmTypNm', '자재구분')
      .readonly()
      .Input('pitmCd', '자재번호')
      .readonly()
      .Input('pitmNm', '자재내역')
      .readonly()
      .Input('amitmNm', '시험항목별방법 명')
      .readonly()
      .Input('orderNo', '오더 번호')
      .readonly()
      .Input('lotNo', '제조번호')
      .readonly()
      .Input('batchNo', '배치 번호')
      .readonly()
      .Hidden('amitmCd', '시험항목별방법 코드')
      .Input('rstSeq', '결과 순번')
      .readonly()
      .Input('ocrDt', '발생 일자')
      .readonly()
      .Hidden('ansUid', '시험 UID')
      .Input('tstLogRpubYn', '시험일지 재발행 여부')
      .readonly()
      .blank()
      .Textarea('crtvMsr', '상세의견및조치사항', { rows: 4 })
      .spanCol(4)
      .readonly()
      .Hidden('revwCmmt', '검토 의견')
      .Hidden('revwUid', '검토 UID')
      .Hidden('revwDs', '검토 일시')
      .Hidden('revwIp', '검토 IP')
      .Hidden('labEvtAprIdx', '실험실 이벤트 승인 IDX')
      .Hidden('rjtUid', '반려 UId')
      .Hidden('rjtDs', '반려 일시')
      .Hidden('rjtRea', '반려 사유')
      .build(),
};

export default {
  searchGridWithForm,
  reviewInfoForm,
  detailInfoForm,
};

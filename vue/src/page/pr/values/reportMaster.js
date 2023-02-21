import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const searchGridWithForm = {
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
      .Select('rptDiv', '리포트 구분', {
        async: () => api.combo.systemCommon.getRptDivCombo(),
      })
      .Input('rptNm', '성적서 명')
      .Input('rptRdPath', '성적서 경로')
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('rptIdx', '성적서IDX', { visible: false })
      .col('rptDiv', '리포트 구분', { visible: false })
      .col('rptDivNm', '리포트 구분', { width: '10%' })
      .col('rptNm', '성적서명', { width: '20%' })
      .col('rptRdPath', '성적서RD경로', { width: '30%' })
      .col('rptRmk', '성적서 비고', { width: '20%' })
      .col('etcRmk', '기타 비고', { width: '20%' })
      .col('crtIp', '생성 IP', { visible: false })
      .col('crtUid', '생성 UID', { visible: false })
      .col('crtUidNm', '생성자')
      .col('crtDs', '생성 일시', { visible: false })
      .col('crtDt', '생성 일자')
      .col('udtIp', '수정 IP', { visible: false })
      .col('udtUid', '수정 UID', { visible: false })
      .col('udtUidNm', '수정자')
      .col('udtDs', '수정 일시', { visible: false })
      .col('udtDt', '수정 일자')
      .build(),
};

const detailInfoForm = {
  static: {
    title: '상세정보',
    countPerRow: 4,
    buttons: [
      { name: 'save', label: '저장' },
      { name: 'delete', label: '삭제' },
      { name: 'reset', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('rptIdx', '성적서IDX')
      .Select('rptDiv', '리포트 구분', {
        async: () => api.combo.systemCommon.getRptDivCombo(),
      })
      .required()
      .Input('rptNm', '성적서명', { maxLength: 20 })
      .required()
      .Input('rptRdPath', '성적서 RD 경로', { maxLength: 100 })
      .spanCol(2)
      .readonly()
      .Textarea('rptRmk', '성적서 비고', { rows: 3, maxLength: 2000 })
      .spanCol(2)
      .Textarea('etcRmk', '기타 비고', { rows: 3, maxLength: 2000 })
      .spanCol(2)
      .Hidden('crtIp', '생성 IP')
      .Hidden('crtUid', '생성 UID')
      .Hidden('crtDs', '생성 일시')
      .Hidden('crtUidNm', '생성자')
      .Hidden('crtDt', '생성 일자')
      .Hidden('udtIp', '수정 IP')
      .Hidden('udtDs', '수정 일시')
      .Hidden('udtUid', '수정 UID')
      .Hidden('udtUidNm', '수정자')
      .Hidden('udtDt', '수정 일자')
      .build(),
};

export default {
  searchGridWithForm,
  detailInfoForm,
};

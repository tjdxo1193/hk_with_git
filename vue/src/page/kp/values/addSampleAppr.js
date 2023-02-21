import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const weekAgoDate = dayjs().add(-1, 'week').format('YYYY-MM-DD');

const todayDate = dayjs().format('YYYY-MM-DD');

const addSampleGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('pitmNm', '자재내역')
      .Input('pitmCd', '자재번호')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .DatepickerTwinWithSwitch('smpReqDtList', '요청일자', { value: [weekAgoDate, todayDate] })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('addSmpIdx', { visible: false })
      .col('addSmpProc', { visible: false })
      .col('smpMngIdx', { visible: false })
      .col('addSmpAprReqIdx', { visible: false })
      .col('smpReqRea', { visible: false })
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('ansNo', '시험번호')
      .col('lotNo', '제조번호')
      .col('reqSmpVol', '요청검체수량')
      .col('inpUnit', '요청검체단위')
      .col('smpReqReaNm', '요청사유')
      .col('smpReqReaDtl', '요청사유상세')
      .build(),
};

const inputForm = {
  static: {
    title: '입력정보',
    buttons: [
      { name: 'approve', label: '승인' },
      { name: 'reject', label: '반려' },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('addSmpIdx')
      .Hidden('ansIdx')
      .Hidden('smpMngIdx')
      .Hidden('ansTyp')
      .Hidden('pitmTyp')
      .Hidden('batchNo')
      .Hidden('addSmpAprReqIdx')
      .multiple(
        'pitm',
        '자재정보',
        FormBuilder.builder()
          .Input('pitmCd', { readonly: true, _required: true })
          .Input('pitmNm', { readonly: true })
          .build(),
      )
      .spanCol(2)
      .multiple(
        'number',
        '시험번호 및 제조번호',
        FormBuilder.builder()
          .Input('ansNo', { readonly: true, _required: true })
          .Input('lotNo', { readonly: true })
          .build(),
      )
      .spanCol(2)
      .InputNumber('reqSmpVol', '요청검체수량', { readonly: true, _required: true })
      .Input('inpUnit', '입력단위', { readonly: true, _required: true })
      .RadioGroup('smpReqRea', '요청사유', {
        groups: [
          { label: '기준일탈', checkedValue: 'U0250001' },
          { label: '기타', checkedValue: 'U0250002' },
        ],
        disabled: true,
      })
      .Textarea('smpReqReaDtl', '요청사유상세', { readonly: true })
      .build(),
};

export default {
  addSampleGrid,
  inputForm,
};

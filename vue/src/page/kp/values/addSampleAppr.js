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
      .Input('pitmNm', '품목명')
      .Input('pitmCd', '품목코드')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .DatepickerTwinWithSwitch('smpReqDtList', '요청일자', { value: [weekAgoDate, todayDate] })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('addSmpIdx', false)
      .col('addSmpProc', false)
      .col('smpMngIdx', false)
      .col('addSmpAprReqIdx', false)
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('ansNo', '시험번호')
      .col('lotNo', '제조번호')
      .col('reqSmpVol', '요청검체수량')
      .col('inpUnit', '요청검체단위')
      .col('smpReqRea', false)
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
        '품목정보',
        FormBuilder.builder()
          .Input('pitmCd')
          .required()
          .readonly()
          .Input('pitmNm')
          .readonly()
          .build(),
      )
      .multiple(
        'number',
        '시험번호 및 제조번호',
        FormBuilder.builder()
          .Input('ansNo')
          .required()
          .readonly()
          .Input('lotNo')
          .readonly()
          .build(),
      )
      .multiple(
        'addSample',
        '요청검체수량',
        FormBuilder.builder()
          .Input('reqSmpVol')
          .readonly()
          .required()
          .Input('inpUnit')
          .readonly()
          .build(),
      )
      .RadioGroup('smpReqRea', '요청사유', {
        groups: [
          { label: '기준일탈', checkedValue: 'U0250001' },
          { label: '기타', checkedValue: 'U0250002' },
        ],
      })
      .readonly()
      .Textarea('smpReqReaDtl', '요청사유상세')
      .readonly()
      .build(),
};

export default {
  addSampleGrid,
  inputForm,
};

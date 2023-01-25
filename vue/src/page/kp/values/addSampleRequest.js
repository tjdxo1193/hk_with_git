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
      .col('addSmpProcNm', '진행상태')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('pitmTyp', false)
      .col('pitmTypNm', '품목구분')
      .col('ansNo', '시험번호')
      .col('ansTyp', false)
      .col('ansTypNm', '시험유형')
      .col('lotNo', '제조번호')
      .col('batchNo', false)
      .col('reqSmpVol', '요청검체수량')
      .col('smpVolUnit', false)
      .col('inpUnit', '요청검체단위')
      .col('smpReqRea', false)
      .col('smpReqReaNm', '요청사유')
      .col('smpReqReaDtl', '요청사유상세')
      .col('rjtUid', false)
      .col('smpReqDt', '요청일자')
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
      { name: 'requestApprove', label: '승인요청', disabled: true },
      { name: 'save', label: '등록' },
      { name: 'update', label: '수정', disabled: true },
      { name: 'delete', label: '삭제', disabled: true },
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
      .readonly()
      .multiple(
        'pitm',
        '품목정보',
        FormBuilder.builder()
          .Input('pitmCd')
          .spanCol(2)
          .readonly()
          .required()
          .Input('pitmNm')
          .spanCol(2)
          .readonly()
          .Button('search', '검색', { type: 'search' })
          .build(),
      )
      .multiple(
        'number',
        '시험번호 및 제조번호',
        FormBuilder.builder()
          .Input('ansNo')
          .readonly()
          .required()
          .Input('lotNo')
          .readonly()
          .required()
          .build(),
      )
      .multiple(
        'addSmp',
        '요청검체수량',
        FormBuilder.builder()
          .InputNumber('reqSmpVol')
          .required()
          .Input('inpUnit')
          .required()
          .build(),
      )
      .RadioGroup('smpReqRea', '요청사유', {
        groups: [
          { label: '기준일탈', checkedValue: 'U0250001' },
          { label: '기타', checkedValue: 'U0250002' },
        ],
      })
      .required()
      .Textarea('smpReqReaDtl', '요청사유상세')
      .build(),
};

export default {
  addSampleGrid,
  inputForm,
};

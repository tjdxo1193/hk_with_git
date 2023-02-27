import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const weekAgoDate = dayjs().add(-1, 'week').format('YYYY-MM-DD');

const todayDate = dayjs().format('YYYY-MM-DD');

const addSampleGrid = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    legends: [],
    props: {
      editable: false,
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
      .col('pitmTyp', { visible: false })
      .col('ansTyp', { visible: false })
      .col('batchNo', { visible: false })
      .col('smpVolUnit', { visible: false })
      .col('smpReqRea', { visible: false })
      .col('rjtUid', { visible: false })
      .col('addSmpProcNm', '진행상태', { width: '10%' })
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('pitmTypNm', '자재구분')
      .col('ansNo', '시험번호')
      .col('ansTypNm', '시험유형')
      .col('lotNo', '제조번호')
      .col('reqSmpVol', '요청검체수량')
      .col('inpUnit', '요청검체단위')
      .col('smpReqReaNm', '요청사유')
      .col('smpReqReaDtl', '요청사유상세')
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
      .multiple(
        'pitm',
        '자재정보',
        FormBuilder.builder()
          .Input('pitmCd', { readonly: true, _required: true, _colSpan: 10 })
          .Input('pitmNm', { readonly: true, _colSpan: 10 })
          .Button('search', '검색', { type: 'search' })
          .build(),
      )
      .spanCol(2)
      .multiple(
        'number',
        '시험번호 및 제조번호',
        FormBuilder.builder()
          .Input('ansNo', { readonly: true, _required: true })
          .Input('lotNo', { readonly: true, _required: true })
          .build(),
      )
      .spanCol(2)
      .InputNumber('reqSmpVol', '요청검체수량', { _required: true })
      .Input('inpUnit', '입력단위', { maxLength: 6, _required: true })
      .RadioGroup('smpReqRea', '요청사유', {
        groups: [
          { label: '기준일탈', checkedValue: 'U0250001' },
          { label: '기타', checkedValue: 'U0250002' },
        ],
        _required: true,
      })
      .Textarea('smpReqReaDtl', '요청사유상세', { maxLength: 4000 })
      .build(),
};

export default {
  addSampleGrid,
  inputForm,
};

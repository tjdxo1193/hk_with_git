import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const weekAgoDate = dayjs().add(-1, 'week').format('YYYY-MM-DD');

const todayDate = dayjs().format('YYYY-MM-DD');

const addSampleGrid = {
  static: {
    title: '조회결과',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('addSmpProc', '진행상태', {
        async: () => api.combo.systemCommon.getAddSmpProcCombo(),
      })
      .Input('pitmNm', '자재내역')
      .Input('pitmCd', '자재번호')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .DatepickerTwinWithSwitch('smpReqDtList', '요청일자', { value: [weekAgoDate, todayDate] })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmTyp', { visible: false })
      .col('ansTyp', { visible: false })
      .col('batchNo', { visible: false })
      .col('smpReqRea', { visible: false })
      .col('rjtUid', { visible: false })
      .col('addSmpProcNm', '진행상태')
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
      .build(),
};

const requestForm = {
  static: {
    title: '추가검체요청정보',
    countPerRow: 4,
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('addSmpIdx')
      .multiple(
        'pitm',
        '자재정보',
        FormBuilder.builder()
          .Input('pitmCd', { readonly: true })
          .Input('pitmNm', { readonly: true })
          .build(),
      )
      .spanCol(2)
      .multiple(
        'number',
        '시험번호 및 제조번호',
        FormBuilder.builder()
          .Input('ansNo', { readonly: true })
          .Input('lotNo', { readonly: true })
          .build(),
      )
      .spanCol(2)
      .Input('reqSmpVol', '요청검체량', { readonly: true })
      .Input('inpUnit', '입력단위', { readonly: true })
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
  requestForm,
};

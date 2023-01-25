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
      .Input('pitmNm', '품목명')
      .Input('pitmCd', '품목코드')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .DatepickerTwinWithSwitch('smpReqDtList', '요청일자', { value: [weekAgoDate, todayDate] })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
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
      .col('inpUnit', '요청검체단위')
      .col('smpReqRea', false)
      .col('smpReqReaNm', '요청사유')
      .col('smpReqReaDtl', '요청사유상세')
      .col('rjtUid', false)
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
        '품목정보',
        FormBuilder.builder().Input('pitmCd').disabled().Input('pitmNm').disabled().build(),
      )
      .multiple(
        'number',
        '시험번호 및 제조번호',
        FormBuilder.builder().Input('ansNo').disabled().Input('lotNo').disabled().build(),
      )
      .multiple(
        'addSample',
        '요청검체수량',
        FormBuilder.builder().Input('ansNo').disabled().Input('inpUnit').disabled().build(),
      )
      .RadioGroup('smpReqRea', '요청사유', {
        groups: [
          { label: '기준일탈', checkedValue: 'U0250001' },
          { label: '기타', checkedValue: 'U0250002' },
        ],
      })
      .disabled()
      .Textarea('smpReqReaDtl', '요청사유상세')
      .disabled()
      .build(),
};

export default {
  addSampleGrid,
  requestForm,
};

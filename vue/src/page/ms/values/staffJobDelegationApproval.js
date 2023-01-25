import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const searchForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('dlgSttCd', '부재여부', {
        value: '',
        elements: [
          { value: 'S0160001', label: '위임상태' },
          { value: 'S0160002', label: '재실상태' },
        ],
      })
      .Input('userNm', '부재자')
      .DatepickerTwinWithSwitch('dlgDtList', '위임일', { value: [todayDate, todayDate] })
      .spanCol(2)
      .build(),
};

const searchResultGrid = {
  static: {
    title: '조회결과',
    $grid: null,
    buttons: [
      { name: 'approve', label: '승인' },
      { name: 'reject', label: '반려' },
    ],
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('dlgSeq', { visible: false })
      .col('userNm', '부재자')
      .col('userId', { visible: false })
      .col('dlgNm', '대리자')
      .col('dlgUid', { visible: false })
      .col('dlgDt', '위임시작일')
      .col('gbkDt', '재실일자')
      .col('dlgSttCd', { visible: false })
      .col('dlgSttNm', '재실여부')
      .col('dlgRea', '위임사유')
      .col('dlgProcCd', '업무위임진행상태코드', { visible: false })
      .col('dlgProcNm', '진행상황')
      .col('rjtYn', '반려여부')
      .col('rjtDs', '반려일')
      .col('rjtRea', '반려사유')
      .col('dlgAprReqIdx', {})
      .build(),
};

const staffInfoForm = {
  static: {
    title: '정보입력',
    countPerRow: 4,
    buttons: [{ name: 'init', label: '초기화' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('userNm', '부재자')
      .disabled()
      .Textarea('dlgRea', '위임사유', { rows: 3 })
      .disabled()
      .spanCol(3)
      .spanRow(2)
      .Select('dlgUid', '대리자', {
        async: api.combo.common.getUserList,
      })
      .disabled()
      .Hidden('dlgUid')
      .Hidden('userId')
      .Hidden('dlgAprReqIdx')
      .Hidden('dlgSeq')
      .Hidden('dlgAprReqIdx')
      .build(),
};

export default {
  searchForm,
  searchResultGrid,
  staffInfoForm,
};

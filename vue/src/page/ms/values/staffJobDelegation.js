import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder, TokenUtil } from '@/util';

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
    legends: [
      { value: '임시저장', className: 'tempSave' },
      { value: '승인대기중', className: 'approveWating' },
    ],
    props: {
      editable: false,
      showRowCheckColumn: false,
      rowStyleFunction: (rowIndex, item) => {
        if (item.dlgProcCd === 'S0150100') {
          return 'tempSave';
        }
        if (item.dlgProcCd === 'S0150200') {
          return 'approveWating';
        }
        if (item.dlgProcCd === 'S0150110') {
          return 'tempSave';
        }
        return null;
      },
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('dlgSeq', { visible: false })
      .col('userNm', '부재자')
      .col('userLognId', '부재자아이디')
      .col('userId', { visible: false })
      .col('dlgNm', '대리자')
      .col('dlgLognId', '대리자아이디')
      .col('dlgUid', { visible: false })
      .col('dlgDt', '위임시작일')
      .col('gbkDt', '재실일자')
      .col('dlgSttCd', { visible: false })
      .col('dlgSttNm', '재실여부')
      .col('dlgRea', '위임사유')
      .col('dlgProcCd', '업무위임진행상태코드', { visible: false })
      .col('dlgAprReqIdx', { visible: false })
      .col('dlgProcNm', '진행상황')
      .col('rjtYn', '반려여부')
      .col('rjtDs', '반려일')
      .col('rjtRea', '반려사유')
      .build(),
};

const staffInfoForm = {
  static: {
    title: '정보입력',
    countPerRow: 4,
    buttons: [
      { name: 'apprRequest', label: '승인요청', disabled: true },
      { name: 'jobDelegation', label: '업무위임' },
      { name: 'gbkRegist', label: '재실등록', disabled: true },
      { name: 'update', label: '수정', disabled: true },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('aprReqDiv')
      .Hidden('userId', 'userId')
      .Hidden('dlgAprReqIdx', 'dlgAprReqIdx')
      .Hidden('dlgSeq', 'dlgSeq')
      .Input('userNm', '부재자', { readonly: true, _required: true })
      .Textarea('dlgRea', '위임사유', {
        maxLength: 300,
        rows: 3,
        _colSpan: 3,
        _rowSpan: 3,
        _required: true,
      })
      .Select('dlgUid', '대리자', {
        async: () =>
          api.combo.common.getUserList().then((res) => {
            res.data = res.data.filter(({ value }) => value !== TokenUtil.myId());
            return res;
          }),
        _required: true,
      })
      .build(),
};

export default {
  searchForm,
  searchResultGrid,
  staffInfoForm,
};

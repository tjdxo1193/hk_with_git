import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const today = dayjs().format('YYYY-MM-DD');
const nextMonth = dayjs().add(1, 'M').format('YYYY-MM-DD');

const noticeManagement = {
  static: {
    title: '공지사항',
    countPerRow: 4,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('ntbTit', '제목')
      .Input('udtUid', '작성자')
      .DatepickerTwin('popupPeriod', '팝업기간', {
        value: [today, nextMonth],
      })
      .RadioGroup('popYn', '팝업여부', {
        value: '',
        gap: 50,
        groups: [
          { checkedValue: '', label: '전체' },
          { checkedValue: 'Y', label: '사용함', width: '80px' },
          { checkedValue: 'N', label: '사용안함', width: '80px' },
        ],
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ntbTit', '제목')
      .col('udtUid', '작성자')
      .col('udtDs', '작성일')
      .col('spopupPeriod', '팝업기간')
      .col('popTrmStr', '팝업시작일', false)
      .col('popTrmEnd', '팝업종료일', false)
      .col('popYn', '팝업여부')
      .build(),
};

const noticeManagementInfo = {
  static: {
    title: '상세정보',
    countPerRow: 4,
    buttons: [
      { name: 'init', label: '초기화' },
      { name: 'save', label: '저장' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('ntbIdx')
      .Input('ntbTit', '제목')
      .required()
      .Input('udtUid', '작성자')
      .required()
      .DatepickerTwin('popupPeriod', '팝업기간', {
        value: [today, nextMonth],
      })
      .required()
      .RadioGroup('popYn', '팝업여부', {
        value: 'Y',
        gap: 50,
        groups: [
          { checkedValue: 'Y', label: '사용함', width: '80px' },
          { checkedValue: 'N', label: '사용안함', width: '80px' },
        ],
      })
      .required()
      .Textarea('ntbCtt', '내용', { rows: 4 })
      .spanCol(4)
      .required()
      .build(),
};

export default {
  noticeManagement,
  noticeManagementInfo,
};

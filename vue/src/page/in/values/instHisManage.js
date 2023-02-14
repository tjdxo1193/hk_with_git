import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [
      { name: 'registerHistory', label: '이력등록' },
      { name: 'search', label: '조회' },
    ],
    legends: [
      { value: '임시저장', className: 'tempSave' },
      { value: '승인요청', className: 'approveRequest' },
      { value: '반려', className: 'return' },
    ],
    props: {
      editable: false,
      rowStyleFunction: function (rowIndex, item) {
        if (item.eqmHisProcCd === 'S0190100') {
          return 'tempSave';
        }
        if (item.eqmHisProcCd === 'S0190200') {
          return 'approveRequest';
        }
        if (item.eqmHisProcCd === 'S0190210') {
          return 'return';
        }
        return null;
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('eqmDiv', '기기분류', {
        async: () => api.combo.userCommon.getEqmDivCombo(),
      })
      .Select('eqmHisDiv', '이력구분', {
        async: () => api.combo.userCommon.getEqmHisDivCombo(),
      })
      .Select('eqmCrst', '기기현황', {
        async: () => api.combo.userCommon.getEqmCrstCombo(),
      })
      .RadioGroup('delYn', '삭제여부', {
        value: 'N',
        groups: [
          { checkedValue: 'Y', label: '삭제' },
          { checkedValue: 'N', label: '미삭제' },
        ],
      })
      .Input('eqmNm', '기기명')
      .Input('srlNo', '시리얼넘버')
      .Hidden('eqmHisAprIdx')
      .DatepickerTwinWithSwitch('searchEqmHisAprDt', '승인일자', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('eqmCd', false)
      .col('hisSeq', false)
      .col('eqmDiv', false)
      .col('eqmDivNm', '기기분류')
      .col('eqmHisDiv', false)
      .col('eqmHisDivNm', '이력구분')
      .col('sapAstNo', '자산번호')
      .col('eqmNm', '기기명', { width: 120 })
      .col('modNm', '모델명')
      .col('srlNo', '시리얼넘버')
      .col('eqmCrst', false)
      .col('eqmCrstNm', '기기현황')
      .col('ansDt', '시험일')
      .col('rmk', '비고')
      .col('eqmHisProcCd', false)
      .col('eqmHisProcNm', '진행상태')
      .col('eqmHisAprIdx', false)
      .col('eqmHisAprDt', '승인일')
      .col('rjtDs', '반려일')
      .col('rjtUid', false)
      .col('rjtNm', '반려자')
      .col('rjtRea', '반려사유')
      .build(),
};

const detail = {
  static: {
    title: '정보등록',
    countPerRow: 4,
    buttons: [
      { name: 'requestApproval', label: '승인요청', disabled: true },
      { name: 'save', label: '저장' },
      { name: 'delete', label: '삭제', disabled: true },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('eqmCd')
      .Hidden('hisSeq')
      .Hidden('eqmHisProcCd')
      .Hidden('hisFileIdx')
      .multiple(
        'eqmDivMulti',
        '기기분류',
        FormBuilder.builder()
          .Input('eqmDivNm', { readonly: true })
          .required()
          .spanCol(7)
          .Button('search', 'instrumentSearchModal', { type: 'search' })
          .Hidden('eqmDiv')
          .build(),
      )
      .required()
      .Input('eqmNm', '기기명', { readonly: true })
      .required()
      .Select('eqmHisDiv', '이력구분', {
        async: () => api.combo.userCommon.getEqmHisDivCombo(),
      })
      .required()
      .Datepicker('ansDt', '시험일', {
        value: todayDate,
      })
      .required()
      .Textarea('rmk', '비고', { rows: 3, maxLength: 500 })
      .spanCol(4)
      .Slot('dropzone', '첨부파일')
      .spanCol(4)
      .build(),
};

export default {
  list,
  detail,
};

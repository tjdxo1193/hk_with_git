import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const monthAgoDate = dayjs().add(-1, 'month').format('YYYY-MM-DD');

const todayDate = dayjs().format('YYYY-MM-DD');

const legendList = [];

const searchGridWithForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    legends: legendList,
    data: [],
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('smpDivCd', '검체구분', {
        async: () => api.combo.systemCommon.getSmpDivCombo(),
      })
      .Select('smpDpsProc', '검체상태', {
        async: () => api.combo.systemCommon.getSmpProcCombo(),
      })
      .Select('ansTyp', '시험유형', {
        async: () => api.combo.systemCommon.getAnsTypCombo(),
      })
      .Select('pitmTyp', '품목유형', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .Input('ansNo', '시험번호')
      .Input('pitmCd', '품목코드')
      .Select('smpStrgMtd', '검체보관방법', {
        async: () => api.combo.userCommon.getSmpStrgMtdCombo(),
      })
      .DatepickerTwinWithSwitch('dpsExpDtList', '폐기예정일자', {
        value: [monthAgoDate, todayDate],
      })
      .DatepickerTwinWithSwitch('smpEtrDtList', '검체입고일자', {
        value: [monthAgoDate, todayDate],
      })
      .DatepickerTwinWithSwitch('rcpDtList', '시험접수일자', {
        value: [monthAgoDate, todayDate],
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('smpMngIdx', false)
      .col('smpDpsProc', false)
      .col('smpDivNm', '검체구분')
      .col('smpDivCd', false)
      .col('smpDpsNm', '검체상태')
      .col('pitmTyp', false)
      .col('ansNo', '시험번호')
      .col('pitmTypNm', '품목유형')
      .col('ansTypNm', '시험유형')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('lotNo', '제조번호')
      .col('batchNo', '배치번호')
      .col('ansTyp', false)
      .col('ansIdx', false)
      .col('mngSmpVol', '관리검체량', false)
      .col('smpStrgMtd', false)
      .col('smpStrgMtdNm', '검체보관방법', false)
      .col('smpVolUnit', false)
      .col('smpVolUnitNm', '검체량단위', false)
      .col('inpUnit', '입력단위', false)
      .col('makDt', '제조일자', false)
      .col('strgLmt', '보관기한', false)
      .col('useLmt', '사용기한', false)
      .col('irgYn', '이상여부', false)
      .col('smpEtrDt', '검체입고일자', false)
      .col('smpRmk', '검체비고', false)
      .col('dpsExpDt', '폐기예정일자', false)
      .col('dpsFixDt', '폐기확정일자', false)
      .col('smpDpsAprIdx', false)
      .col('dpsRea', '폐기사유', false)
      .col('dpsCanRea', '폐기취소사유', false)
      .col('rjtUid', false)
      .col('rjtNm', '반려자', false)
      .col('rjtRea', '반려사유', false)
      .build(),
};

const inputInfoForm = {
  static: {
    title: '입력 정보',
    countPerRow: 4,
    buttons: [
      { name: 'save', label: '등록' },
      { name: 'update', label: '수정', disabled: true },
      { name: 'delete', label: '삭제', disabled: true },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('smpMngIdx')
      .Hidden('smpdpsProc')
      .Hidden('ansIdx')
      .multiple(
        'pitm',
        '품목명',
        FormBuilder.builder()
          .Input('pitmNm')
          .required()
          .readonly()
          .spanCol(10)
          .Button('search', '검색', { type: 'search' })
          .build(),
      )
      .Hidden('pitmCd', '품목코드')
      .Hidden('pitmTyp')
      .Input('pitmTypNm', '품목구분')
      .readonly()
      .Input('ansNo', '시험번호')
      .readonly()
      .Select('smpDivCd', '검체구분', {
        async: () => api.combo.systemCommon.getSmpDivCombo(),
      })
      .Input('lotNo', '제조번호')
      .readonly()
      .Input('batchNo', '배치번호')
      .readonly()
      .Input('smpDpsNm', '검체상태')
      .readonly()
      .Input('ansTypNm', '시험유형')
      .readonly()
      .Hidden('ansTyp')
      .Input('rcpDt', '접수일자')
      .readonly()
      .Input('makDt', '제조일자')
      .readonly()
      .Input('smpEtrDt', '검체입고일자')
      .readonly()
      .Hidden('smpDpsProc')
      .Hidden('pitmTyp')
      .Input('pitmTypNm', '품목유형')
      .readonly()
      .Select('smpStrgMtd', '검체보관방법', {
        async: () => api.combo.userCommon.getSmpStrgMtdCombo(),
      })
      .Input('mngSmpVol', '관리검체량')
      .readonly()
      .Input('inpUnit', '입력단위')
      .readonly()
      .Input('strgLmt', '보관기한')
      .readonly()
      .Input('useLmt', '사용기한')
      .readonly()
      .RadioGroup('irgYn', '이상여부', {
        groups: [
          { label: '정상', checkedValue: 'N' },
          { label: '이상', checkedValue: 'Y' },
        ],
      })
      .readonly()
      .Datepicker('dpsExpDt', '폐기예정일자', { value: todayDate })
      .Textarea('smpRmk', '검체비고')
      .build(),
};

export default {
  searchGridWithForm,
  inputInfoForm,
};

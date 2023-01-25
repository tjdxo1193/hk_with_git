import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const tomorrowDate = dayjs().add(1, 'day').format('YYYY-MM-DD');

const searchGridWithForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    legends: [
      { value: '예정일도래', className: 'qualifyArriveDueDate' },
      { value: '예정일초과', className: 'calibrationExceedDueDate' },
    ],
    props: {
      editable: false,
      rowStyleFunction: (rowIndex, item) => {
        if (
          dayjs().isAfter(item.qualAprDt) ||
          dayjs().isAfter(item.calAprDt) ||
          dayjs().isAfter(item.rglChkAprDt)
        ) {
          return 'calibrationExceedDueDate';
        }
        if (
          dayjs().isBefore(dayjs(item.qualAprDt), 'month') ||
          dayjs().isBefore(dayjs(item.calAprDt), 'month') ||
          dayjs().isBefore(dayjs(item.rglChkAprDt), 'month')
        ) {
          return 'qualifyArriveDueDate';
        }
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('eqmDiv', '기기분류', {
        async: () => api.combo.userCommon.getEqmDivCombo(),
      })
      .Input('eqmNm', '기기명')
      .Input('eqmCd', '기기코드')
      .DatepickerTwinWithSwitch('dpsDtList', '폐기일', {
        value: [todayDate, tomorrowDate],
      })
      .DatepickerTwinWithSwitch('qualAprDtList', 'Qualification예정일', {
        value: [todayDate, tomorrowDate],
      })
      .DatepickerTwinWithSwitch('calAprDtList', 'Calibration예정일', {
        value: [todayDate, tomorrowDate],
      })
      .Select('eqmCrst', '기기현황', {
        async: () => api.combo.userCommon.getEqmCrstCombo(),
      })
      .DatepickerTwinWithSwitch('rglChkAprDtList', '정기점검예정일', {
        value: [todayDate, tomorrowDate],
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('eqmCd', '기기코드')
      .col('eqmNm', '기기명')
      .col('eqmDiv', { visible: false })
      .col('eqmDivNm', '기기분류')
      .col('eqmCrst', { visible: false })
      .col('eqmCrstNm', '기기현황')
      .col('eqmStt', { visible: false })
      .col('eqmSttNm', '기기상태')
      .col('istPla', { visible: false })
      .col('istPlaNm', '설치장소')
      .col('makComp', '제조회사')
      .col('splComp', '공급회사')
      .col('modNm', '모델이름')
      .col('srlNo', 'Serial No.')
      .col('crgUid', { visible: false })
      .col('crgNm', '담당자')
      .col('iq', 'IQ')
      .col('oq', 'OQ')
      .col('pq', 'PQ')
      .col('arpNo', '승인번호')
      .col('chkCyl', { visible: false })
      .col('chkCylNm', '점검주기')
      .col('quaCyl', { visible: false })
      .col('quaCylNm', 'Qualification주기')
      .col('calCyl', { visible: false })
      .col('calCylNm', 'Calibration주기')
      .col('etrDt', '입고일자')
      .col('istDt', '설치일자')
      .col('dpsDt', '폐기일자')
      .col('qualAprDt', 'Qual승인일자')
      .col('calAprDt', 'Cal승인일자')
      .col('rglChkAprDt', '정기점검승인일자')
      .col('useRng', '사용가능범위')
      .col('qttRng', '정량범위')
      .col('rmk', { visible: false })
      .col('eqmFileIdx', { visible: false })
      .col('sapAstNo', 'SAP자산번호', { visible: false })
      .col('sapAstNm', 'SAP자산명', { visible: false })
      .col('sapCrtDt', 'SAP생성일자', { visible: false })
      .col('sapChgDt', 'SAP변경일자', { visible: false })
      .col('sapAcqDt', 'SAP취득일자', { visible: false })
      .col('sapSaleDpsDt', 'SAP매각/폐기일자', { visible: false })
      .col('sapAddDesc', 'SAP추가설명', { visible: false })
      .col('sapCrgNmEmid', 'SAP담당자사번', { visible: false })
      .col('sapCosc', 'SAP코스트센터', { visible: false })
      .col('sapOrco', 'SAP취득가액', { visible: false })
      .col('sapAccd', 'SAP감가상각누계액', { visible: false })
      .col('pmsChkTagtYn', 'PMS점검대상여부', { visible: false })
      .build(),
};

const registerForm = {
  static: {
    title: '정보등록',
    countPerRow: 4,
    buttons: [
      { name: 'excel', label: 'Excel' },
      { name: 'saveFile', label: '파일등록', disabled: true },
      { name: 'save', label: '등록' },
      { name: 'update', label: '수정', disabled: true },
      { name: 'delete', label: '삭제', disabled: true },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('eqmCd', '기기코드')
      .Input('eqmNm', '기기명')
      .required()
      .Select('eqmDiv', '기기분류', {
        async: () => api.combo.userCommon.getEqmDivCombo(),
      })
      .required()
      .Select('eqmCrst', '기기현황', {
        async: () => api.combo.userCommon.getEqmCrstCombo(),
      })
      .required()
      .Select('eqmStt', '기기상태', {
        async: () => api.combo.userCommon.getEqmSttCombo(),
      })
      .required()
      .Select('istPla', '설치장소', {
        async: () => api.combo.userCommon.getIstPlaCombo(),
      })
      .required()
      .Input('makComp', '제조회사')
      .Input('splComp', '공급회사')
      .Input('modNm', '모델이름')
      .Input('srlNo', 'Serial No.')
      .Select('crgUid', '담당자', {
        async: api.combo.common.getUserList,
      })
      .Input('iq', 'IQ')
      .Input('oq', 'OQ')
      .Input('pq', 'PQ')
      .Input('arpNo', '승인번호')
      .readonly()
      .Select('chkCyl', '점검주기', {
        async: () => api.combo.userCommon.getChkCylCombo(),
      })
      .Select('quaCyl', 'Qualification주기', {
        async: () => api.combo.userCommon.getChkCylCombo(),
      })
      .Select('calCyl', 'Calibration주기', {
        async: () => api.combo.userCommon.getChkCylCombo(),
      })
      .DatepickerWithSwitch('etrDt', '입고일자', { value: todayDate })
      .DatepickerWithSwitch('istDt', '설치일자', { value: todayDate })
      .DatepickerWithSwitch('dpsDt', '폐기일자', { value: todayDate })
      .DatepickerWithSwitch('qualAprDt', 'Qual승인일자', { value: todayDate })
      .DatepickerWithSwitch('calAprDt', 'Cal승인일자', { value: todayDate })
      .DatepickerWithSwitch('rglChkAprDt', '정기점검승인일자', { value: todayDate })
      .Input('useRng', '사용가능범위')
      .Input('qttRng', '정량범위')
      .Textarea('rmk', '비고')
      .Hidden('eqmFileIdx', '기기파일IDX')
      .Input('sapAstNo', 'SAP자산번호')
      .readonly()
      .Input('sapAstNm', 'SAP자산명')
      .readonly()
      .Input('sapCrtDt', 'SAP생성일자')
      .readonly()
      .Input('sapChgDt', 'SAP변경일자')
      .readonly()
      .Input('sapAcqDt', 'SAP취득일자')
      .readonly()
      .Input('sapSaleDpsDt', 'SAP매각/폐기일자')
      .readonly()
      .Input('sapAddDesc', 'SAP추가설명')
      .readonly()
      .Input('sapCrgNmEmid', 'SAP담당자사번')
      .readonly()
      .Input('sapCosc', 'SAP코스트센터')
      .readonly()
      .Input('sapOrco', 'SAP취득가액')
      .readonly()
      .Input('sapAccd', 'SAP감가상각누계액')
      .readonly()
      .Input('pmsChkTagtYn', 'PMS점검대상여부')
      .readonly()
      .build(),
};

const accessoryGrid = {
  static: {
    title: '구성품정보',
    buttons: [
      { name: 'addRow', label: '행추가' },
      { name: 'deleteRow', label: '행삭제' },
    ],
    props: {
      editable: true,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('acsrSeq', { visible: false })
      .col('acsrNm', '구성품명')
      .col('acsrModNm', '구성품모델명')
      .col('acsrSrlNo', 'Serial No.')
      .build(),
};

const printButtons = {
  buttons: [{ name: 'print', label: '출력' }],
};

export default {
  searchGridWithForm,
  registerForm,
  accessoryGrid,
  printButtons,
};

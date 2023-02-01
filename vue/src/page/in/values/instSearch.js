import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const tomorrowDate = dayjs().add(1, 'day').format('YYYY-MM-DD');

const searchGridWithForm = {
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
      .Select('eqmDiv', '기기분류', {
        async: () => api.combo.userCommon.getEqmDivCombo(),
      })
      .Input('eqmNm', '기기명')
      .Input('eqmCd', '기기코드')
      .DatepickerTwinWithSwitch('dpsDt', '폐기일', { value: [todayDate, tomorrowDate] })
      .DatepickerTwinWithSwitch('quaAprDtList', 'Qualification 예정일', {
        value: [todayDate, tomorrowDate],
      })
      .DatepickerTwinWithSwitch('calAprDtList', 'Calibration 예정일', {
        value: [todayDate, tomorrowDate],
      })
      .Select('eqmStt', '기기상태', {
        async: () => api.combo.userCommon.getEqmSttCombo(),
      })
      .Select('eqmCrst', '기기현황', {
        async: () => api.combo.userCommon.getEqmCrstCombo(),
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('eqmFileIdx', { visible: false })
      .col('eqmFileCnt', '첨부파일', { colSpan: 2, width: 50 })
      .button('fileAttacher', '첨부', { width: 50 })
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
      .col('srlNo', 'S/N')
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
      .col('rmk', '비고')
      .col('sapAstNo', 'SAP자산번호')
      .col('sapAstNm', 'SAP자산명')
      .col('sapCrtDt', 'SAP생성일자')
      .col('sapChgDt', 'SAP변경일자')
      .col('sapAcqDt', 'SAP취득일자')
      .col('sapSaleDpsDt', 'SAP매각/폐기일자')
      .col('sapAddDesc', 'SAP추가설명')
      .col('sapCrgNmEmid', 'SAP담당자사번')
      .col('sapCosc', 'SAP코스트센터')
      .col('sapOrco', 'SAP취득가액')
      .col('sapAccd', 'SAP감가상각누계액')
      .col('pmsChkTagtYn', 'PMS점검대상여부')
      .build(),
};

const historyGrid = {
  static: {
    title: '기기이력',
    buttons: [
      { name: 'excel', label: 'Excel' },
      { name: 'init', label: '초기화' },
    ],
    props: {
      editable: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('eqmCd', '기기코드')
      .col('hisSeq', '이력순번')
      .col('eqmHisDiv', { visible: false })
      .col('eqmHisDivNm', '기기이력구분')
      .col('eqmHisProcCd', { visible: false })
      .col('eqmHisProcNm', '기기이력진행상태코드')
      .col('eqmHisAprIdx', { visible: false })
      .col('rjtUid', { visible: false })
      .col('rjtNm', '반려자')
      .col('rjtDs', '반려일시')
      .col('rjtRea', '반려사유')
      .col('rgtUid', { visible: false })
      .col('rgtNm', '등록자')
      .col('rgtDt', '등록일자')
      .col('ansDt', '시험일자')
      .col('rmk', '비고')
      .col('hisFileIdx', { visible: false })
      .build(),
};

export default {
  searchGridWithForm,
  historyGrid,
};

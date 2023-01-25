import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'approve', label: '승인' },
      { name: 'reject', label: '반려' },
      { name: 'search', label: '조회' },
    ],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      //TODO 검색조건 확인
      .Select('eqmHisDiv', '이력구분', {
        async: () => api.combo.userCommon.getEqmHisDivCombo(),
      })
      .blank()
      .spanCol(3)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('hisSeq', false)
      .col('eqmHisAprIdx', false)
      .col('eqmHisDiv', false)
      .col('eqmHisDivNm', '이력구분')
      .col('aprReqUid', false)
      .col('aprReqNm', '승인요청자')
      .col('aprReqDs', '승인요청일')
      .build(),
};

const detail = {
  static: {
    title: '상세조회',
    $grid: null,
    props: {
      editable: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      // .col('hisFileIdx', false)
      .col('fileIdx', false)
      .col('fileCnt', '첨부파일', { colSpan: 2, width: 50 })
      .button('fileAttacher', '첨부', { width: 50 })
      .col('plntCd', false)
      .col('eqmCd', false)
      .col('hisSeq', false)
      .col('eqmDiv', false)
      .col('eqmDivNm', '기기분류')
      .col('eqmHisDiv', false)
      .col('eqmHisDivNm', '이력구분')
      .col('sapAstNo', '자산번호')
      .col('eqmNm', '기기명', { width: 120 })
      // .col('makComp', '제조회사')
      // .col('splComp', '공급회사')
      .col('modNm', '모델명')
      .col('srlNo', '시리얼넘버')
      // .col('crgUid', false)
      // .col('crgNm', '담당자')
      // .col('IQ', 'IQ')
      // .col('OQ', 'OQ')
      // .col('PQ', 'PQ')
      // .col('chkCyl', '점검주기')
      // .col('quaCyl', 'Qualification주기', { width: 120 })
      // .col('calCyl', 'Calibration주기', { width: 120 })
      // .col('eqmCrst', false)
      // .col('eqmCrstNm', '기기현황')
      // .col('rgtUid', false)
      // .col('rgtNm', '작성자')
      // .col('rgtDt', '작성일')
      .col('ansDt', '시험일')
      .col('rmk', '비고')
      .col('eqmHisAprIdx', false)
      .build(),
};

export default {
  list,
  detail,
};

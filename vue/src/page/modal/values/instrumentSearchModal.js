import { ColumnBuilder, FormBuilder } from '@/util';

const instrumentGridWithForm = {
  static: {
    buttons: [{ name: 'search', label: '조회' }],
    countPerRow: 2,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () => FormBuilder.builder().Input('eqmCd', '기기코드').Input('eqmNm', '기기명').build(),
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
      .col('eqmFileIdx', '기기파일IDX')
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

const modalButtons = {
  buttons: [
    { name: 'select', label: '선택' },
    { name: 'close', label: '닫기', type: 'normal' },
  ],
};

export default {
  instrumentGridWithForm,
  modalButtons,
};

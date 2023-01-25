import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

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
      .Select('pitmTyp', '품목유형', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .required()
      .Input('pitmCd', '품목코드')
      .Input('pitmNm', '품목명')
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('pitmTypNm', '품목구분')
      .col('crgDptNm', '담당부서')
      .col('pitmVer', '품목버전')
      .col('useVerYn', { visible: false })
      .col('specProcNm', '진행상태')
      .build(),
};

const detailSearchGridWithForm = {
  static: {
    title: '상세조회',
    countPerRow: 4,
    buttons: [
      { name: 'detailSearch', label: '조회' },
      { name: 'save', label: '저장', disabled: true },
      { name: 'init', label: '초기화' },
    ],
    props: {
      showRowCheckColumn: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('eqmCrstNm', '기기현황', {
        async: () => api.combo.userCommon.getEqmCrstCombo(),
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
      .col('eqmFileIdx', { visible: false })
      .col('sapAstNo', { visible: false })
      .col('sapAstNm', { visible: false })
      .col('sapCrtDt', { visible: false })
      .col('sapChgDt', { visible: false })
      .col('sapAcqDt', { visible: false })
      .col('sapSaleDpsDt', { visible: false })
      .col('sapAddDesc', { visible: false })
      .col('sapCrgNmEmid', { visible: false })
      .col('sapCosc', { visible: false })
      .col('sapOrco', { visible: false })
      .col('sapAccd', { visible: false })
      .col('pmsChkTagtYn', { visible: false })
      .build(),
};

const printButtons = {
  buttons: [{ name: 'print', label: '출력' }],
};

const printForms = {
  static: {
    countPerRow: 2,
  },
  forms: () => FormBuilder.builder().Checkbox('previewYn', '미리보기').Select('b').build(),
};

export default {
  searchGridWithForm,
  detailSearchGridWithForm,
  printButtons,
  printForms,
};

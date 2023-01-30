import api from '@/api';
import {ColumnBuilder, FormBuilder} from '@/util';

const rvsDivPsComboList = [
  { value: 'P', label: '품목' },
  { value: 'S', label: '규격서' },
];

const versionList = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'search', label: '조회' },
      { name: 'approveRequest', label: '승인요청' },
      { name: 'reject', label: '반려' },
    ],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '품목유형', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmCd', '품목코드')
      .Input('pitmNm', '품목명')
      .Select('crgDptCd', '담당부서', { async: api.combo.common.getDpt })
      .build(),

  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '공장코드', false)
      .col('pitmSpecIdx', '품목규격서IDX', false)
      .col('delYn', '삭제 여부', false)
      .col('pitmCd', '품목코드')
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('pitmTyp', '품목유형', false)
      .col('pitmAnsSpecAprIdx', '품목시험규격승인IDX', false)
      .col('pitmTypNm', '품목유형')
      .col('pitmNm', '품목명')
      .col('pitmEn', '품목영문명')
      .col('crgDptNm', '담당부서')
      .col('sapPrdha', 'SAP제품계층구조', false)
      .col('pitmVer', '품목버전')
      .col('aitmSpecVer', '규격서버전')
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('docNo', '문서번호')
      .col('rvsNo', '개정번호')
      .col('rvsDt', '개정일자')
      .col('enfoDt', '시행일자')
      .col('rvsCtt', '개정내역')
      .combo('rvsDivPs', '개정구분', { list: rvsDivPsComboList })
      .combo('rvsReaCd', '개정사유', { async: () => api.combo.userCommon.getRvsReaCombo() })
      .col('specProcCd', '진행상태코드', false)
      .col('specProcNm', '진행상태')
      .col('revwUid', '검토자', false)
      .col('revwUnm', '검토자')
      .col('revwDs', '검토일시')
      .col('revwUnm', '반려자')
      .col('rjtUid', '반려자', false)
      .col('rjtDs', '반려일시')
      .col('rjtRea', '반려사유')
      .build(),
};

const testItemList = {
  static: {
    title: '시험항목목록',
    $grid: null,
    props: {
      editable: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '사업장코드', false)
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('aitmSeq', '시험항목순번', false)
      .combo('ansDptCd', '시험파트', { async: api.combo.common.getDpt })
      .col('aitmKn', '시험항목명')
      .col('vriaNo', 'VariantNo')
      .col('vriaKn', 'Variant국문')
      .col('amitmCd', '시험항목별방법코드', false)
      .col('aitmOrd', '시험항목정렬순서', {
        width: 120,
        editable: false,
      })
      .col('perspecTxt', '허가규격텍스트', { width: 100 })
      .col('owcSpecTxt', '자사규격텍스트', { width: 100 })
      .col('specTxtEn', '규격텍스트영문', { width: 100 })
      .combo('specTyp', '규격유형', {
        async: () => api.combo.systemCommon.getSpecTypCombo(),
      })
      .combo('jdgTyp', '판정유형', { async: () => api.combo.systemCommon.getJdgTypCombo() })
      .col('mkrQty', '표시량', {
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
        },
      })
      .col('perStandard', '허가기준', {
        children: ColumnBuilder.builder()
          .col('perSlvLow', '하한', {
            editRenderer: {
              type: 'InputEditRenderer',
              onlyNumeric: true,
            },
          })
          .col('perSlvUpp', '상한', {
            editRenderer: {
              type: 'InputEditRenderer',
              onlyNumeric: true,
            },
          })
          .col('perSlvDesc', '서술')
          .build(),
      })
      .col('owcStandard', '자사기준', {
        children: ColumnBuilder.builder()
          .col('owcSlvLow', '하한', {
            editRenderer: {
              type: 'InputEditRenderer',
              onlyNumeric: true,
            },
          })
          .col('owcSlvUpp', '상한', {
            editRenderer: {
              type: 'InputEditRenderer',
              onlyNumeric: true,
            },
          })
          .col('owcSlvDesc', '서술')
          .build(),
      })
      .combo('slvJdgCfm', '기준판정적합', {
        width: 100,
        async: () => api.combo.userCommon.getSlvJdgCombo(),
      })
      .combo('slvJdgNonCfm', '기준판정부적합', {
        width: 100,
        async: () => api.combo.userCommon.getSlvJdgCombo(),
      })
      .combo('rstUnitCd', '결과단위코드', {
        width: 100,
        async: () => api.combo.userCommon.getSmpVolUnitCombo(),
      })
      .col('rstDpnt', '결과소수점', {
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
        },
      })
      .col('rptPrtSlvVal', '성적서출력기준값', { width: 120 })
      .col('rptPrtItmNm', '성적서출력항목명', { width: 120 })
      .combo('rptPrtYn', '성적서출력여부', {
        width: 100,
        list: [
          { label: 'Y', value: 'Y' },
          { label: 'N', value: 'N' },
        ],
      })
      .col('aitmRmk', '비고')
      .col('smpClltQty', '검체채취수량', false, {
        width: 100,
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
        },
      })
      .combo('smpClltUnit', '검체채취단위', false, {
        width: 100,
        async: () => api.combo.userCommon.getSmpVolUnitCombo(),
      })
      .col('ispDurTm', '검사소여시간', false, {
        width: 100,
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
        },
      })
      .col('eqmUseTm', '기기사용시간', false, {
        width: 100,
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
        },
      })
      .build(),
};

export default {
  versionList,
  testItemList,
};

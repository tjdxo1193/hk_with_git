import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const pitemtype = {
  finishedSet: 'S0180100',
  finishedSingle: 'S0180101',
  beautifulPackaging: 'S0180102',
  semiManufacturesFillingFoam: 'S0180201',
  semiManufacturesOtherProduct: 'S0180202',
  semiManufacturesBulk: 'S0180203',
  semiManufacturesBase: 'S0180204',
  rawMaterial: 'S0180400',
  packagingMaterial: 'S0180500',
  goods: 'S0180600',
};

const pItemSpecList = {
  static: {
    title: '자재규격서목록',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
    countPerRow: 3,
    buttons: [
      { name: 'search', label: '조회' },
      { name: 'select', label: '선택' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '자재유형', {
        async: () =>
          api.combo.systemCommon.getPitmDivCombo().then((res) => {
            res.data = res.data.filter(
              ({ value }) =>
                value == pitemtype.rawMaterial ||
                value == pitemtype.goods ||
                value == pitemtype.semiManufacturesFillingFoam ||
                value == pitemtype.semiManufacturesOtherProduct ||
                value == pitemtype.semiManufacturesBulk ||
                value == pitemtype.semiManufacturesBase,
            );
            return res;
          }),
      })
      .required()
      .Input('pitmCd', '자재번호')
      .Input('pitmNm', '자재내역')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '공장코드', false)
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('pitmCd', '자재번호')
      .col('pitmTyp', '자재유형코드', false)
      .col('pitmTypNm', '자재유형')
      .col('pitmNm', '자재내역')
      .col('pitmEn', '자재내역(영문)')
      .col('pitmVer', '자재버전')
      .col('crgDptNm', '담당부서')
      .col('crgDptCd', '담당부서코드', false)
      .col('specProcCd', '진행상태', false)
      .col('specProcNm', '진행상태')
      .col('qpSpecProcCd', '자재진행상태코드', false)
      .col('qpSpecProcNm', '자재진행상태')
      .build(),
};

const aItemList = {
  static: {
    title: '시험항목목록',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '플랜트코드', false)
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('aitmSeq', '시험항목순번', false)
      .combo('ansDptCd', '시험파트', {
        async: api.combo.common.getDpt,
        width: 150,
      })
      .col('aitmKn', '시험항목명', {
        width: 120,
      })
      .col('vriaNo', 'VariantNo', {
        width: 80,
      })
      .col('vriaKn', 'Variant국문', {
        width: 100,
      })
      .col('amitmCd', '시험항목별방법코드', false)
      .col('aitmOrd', '시험항목정렬순서')
      .col('perspecTxt', '허가규격텍스트', { width: 100 })
      .col('owcSpecTxt', '자사규격텍스트', { width: 100 })
      .col('specTxtEn', '규격텍스트영문', { width: 100 })
      .combo('specTyp', '규격유형', {
        async: () => api.combo.systemCommon.getSpecTypCombo(),
      })
      .col('mkrQty', '표시량')
      .combo('jdgTyp', '판정유형', { async: () => api.combo.systemCommon.getJdgTypCombo() })
      .col('perStandard', '허가기준', {
        children: ColumnBuilder.builder()
          .col('perSlvLow', '하한')
          .col('perSlvUpp', '상한')
          .col('perSlvDesc', '서술')
          .build(),
      })
      .col('owcStandard', '자사기준', {
        children: ColumnBuilder.builder()
          .col('owcSlvLow', '하한')
          .col('owcSlvUpp', '상한')
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
      .col('rstDpnt', '결과소수점')
      .col('rptPrtSlvVal', '성적서출력기준값', { width: 120 })
      .col('rptPrtItmNm', '성적서출력항목명', { width: 120 })
      .col('rptPrtYn', '성적서출력여부')
      .col('aitmRmk', '비고')
      .col('smpClltQty', '검체채취수량', false)
      .combo('smpClltUnit', '검체채취단위', false, {
        width: 100,
        async: () => api.combo.userCommon.getSmpVolUnitCombo(),
      })
      .col('ispDurTm', '검사소여시간', false)
      .col('eqmUseTm', '기기사용시간', false)
      .build(),
};

export default {
  pItemSpecList,
  aItemList,
};

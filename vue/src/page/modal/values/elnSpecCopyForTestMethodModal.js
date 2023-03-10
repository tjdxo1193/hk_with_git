import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAgoDate = dayjs().add(-1, 'M').format('YYYY-MM-DD');

const prdDivList = [
  { value: 'F', label: '완제품' },
  { value: 'S', label: '반제품' },
];
const buttonGroups = { buttons: [{ name: 'search', label: '조회' }] };
const pItemSpecForm = {
  static: {
    title: 'ELN규격목록',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
    countPerRow: 3,
  },
  forms: () =>
    FormBuilder.builder()
      .Input('labNo', 'LAB NO')
      .required()
      .Select('prdDiv', '반완제품구분', {
        elements: prdDivList,
      })
      .required()
      .DatepickerTwin('ifDt', '연계일시', { value: [monthAgoDate, todayDate] })
      .build(),
};

const pItemSpecList = {
  static: {
    title: '자재목록',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '공장코드')
      .col('labNo', 'LAB NO')
      .col('prdDiv', '반제품구분')
      .col('ifDt', '연계일시')
      .build(),
};

const aItemList = {
  static: {
    title: '시험항목목록',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
    buttons: [{ name: 'select', label: '선택' }],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '플랜트코드', false)
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('aitmSeq', '시험항목순번', false)
      .col('ansDptCd', '시험파트', false)
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
  pItemSpecForm,
  pItemSpecList,
  aItemList,
  buttonGroups,
};

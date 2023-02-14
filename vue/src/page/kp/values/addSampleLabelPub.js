import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const weekAgoDate = dayjs().add(-1, 'week').format('YYYY-MM-DD');

const todayDate = dayjs().format('YYYY-MM-DD');

const pitmTypList = {
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

const sampleGrid = {
  static: {
    title: '조회결과',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('pitmNm', '품목명')
      .Input('pitmCd', '품목코드')
      .Input('lotNo', '제조번호')
      .Input('batchNo', '배치번호')
      .DatepickerTwinWithSwitch('searchSmpReqDt', '요청일자', { value: [weekAgoDate, todayDate] })
      .spanCol(2)
      .blank()
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '사업장 코드', { visible: false })
      .col('pitmTyp', { visible: false })
      .col('ansTyp', { visible: false })
      .col('batchNo', { visible: false })
      .col('smpReqRea', { visible: false })
      .col('rjtUid', { visible: false })
      .col('addSmpProc', '진행상태', { visible: false })
      .col('addSmpProcNm', '진행상태')
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('pitmTypNm', '품목구분')
      .col('ansNo', '시험번호')
      .col('ansTypNm', '시험유형')
      .col('lotNo', '제조번호')
      .col('reqSmpVol', '요청검체수량')
      .col('inpUnit', '요청검체단위')
      .col('smpReqReaNm', '요청사유')
      .col('smpReqReaDtl', '요청사유상세')
      .col('smpReqDt', '요청일자')
      .build(),
};

const requestForm = {
  static: {
    title: '추가검체요청정보',
    countPerRow: 4,
    buttons: [
      { name: 'printLabel', label: '라벨발행' },
      { name: 'reset', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('addSmpIdx')
      .multiple(
        'pitm',
        '품목정보',
        FormBuilder.builder()
          .Input('pitmCd', { readonly: true })
          .Input('pitmNm', { readonly: true })
          .build(),
      )
      .spanCol(2)
      .multiple(
        'number',
        '시험번호 및 제조번호',
        FormBuilder.builder()
          .Input('ansNo', { readonly: true })
          .Input('lotNo', { readonly: true })
          .build(),
      )
      .spanCol(2)
      .Input('reqSmpVol', '요청검체량', { readonly: true })
      .Input('inpUnit', '입력단위', { readonly: true })
      .RadioGroup('smpReqRea', '요청사유', {
        groups: [
          { label: '기준일탈', checkedValue: 'U0250001' },
          { label: '기타', checkedValue: 'U0250002' },
        ],
        disabled: true,
      })
      .Textarea('smpReqReaDtl', '요청사유상세', { readonly: true })
      .Input('labelCd', '라벨코드', { readonly: true })
      .Hidden('plntCd', '사업장 코드')
      .build(),
};

export default {
  pitmTypList,
  sampleGrid,
  requestForm,
};

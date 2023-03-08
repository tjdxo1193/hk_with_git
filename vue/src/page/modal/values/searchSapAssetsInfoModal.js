import { ColumnBuilder, FormBuilder } from '@/util/builder';

const assetsValueForm = {
  forms: () =>
  FormBuilder.builder()
    .Hidden('anln1', '주요자산번호')
    .Hidden('anln2', '자산하위번호')
    .Hidden('anlkl', '자산클래스')
    .Hidden('txt50', '자산내역(자산명)')
    .Hidden('erdat', '생성일')
    .Hidden('aedat', '변경일자')
    .Hidden('zugdt', '첫취득일')
    .Hidden('deakt', '비활성화일')
    .Hidden('txa50', '추가설명')
    .Hidden('zzpic', '담당자사번')
    .Hidden('kostl', '코스트센터')
    .Hidden('kansw', '취득가액')
    .Hidden('kumafa', '감가상각누계액')
    .Hidden('bzdat', '자산기준일')
    .build(),
}

const assetsList = {
  static: {
    $grid: null,
    props: { editable: false },
    buttons: [
      { name: 'select', label: '선택' },
      { name: 'search', label: '조회' },
    ],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('anln1', '주요자산번호')
      .col('anln2', '자산하위번호')
      .col('anlkl', '자산클래스')
      .col('txt50', '자산내역(자산명)')
      .col('erdat', '생성일')
      .col('aedat', '변경일자')
      .col('zugdt', '첫취득일')
      .col('deakt', '비활성화일')
      .col('txa50', '추가설명')
      .col('zzpic', '담당자사번')
      .col('kostl', '코스트센터')
      .col('kansw', '취득가액')
      .col('kumafa', '감가상각누계액')
      .col('bzdat', '자산기준일')
      .build(),
};

export default {
  assetsList,
  assetsValueForm,
};

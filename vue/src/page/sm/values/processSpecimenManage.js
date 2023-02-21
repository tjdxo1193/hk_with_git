import dayjs from 'dayjs';

import { TokenUtil } from '@/util';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAgoDate = dayjs().add(-1, 'M').format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'select', label: '조회' }],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('pitmVer')
      .Input('pitmNm', '자재내역')
      .Input('pitmCd', '자재번호')
      .Input('pdtNo', '생산번호')
      .RadioGroup('useYn', '사용여부', {
        value: 'Y',
        groups: [
          { checkedValue: '', label: '전체' },
          { checkedValue: 'Y', label: '사용함' },
          { checkedValue: 'N', label: '사용안함' },
        ],
      })
      .DatepickerTwinWithSwitch('searchEnmDt', '제정일자', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .DatepickerTwinWithSwitch('searchExpirDt', '유효기한', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', false)
      .col('spcmNo', '표준견본번호')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('pitmEn', '자재내역(영문)')
      .col('pitmTypNm', '자재구분')
      .col('pdtNo', '생산번호')
      .col('enmDt', '제정일자')
      .col('expirDt', '유효기한')
      .col('muft', '제조원')
      .build(),
};

const detailInfo = {
  static: {
    title: '공정표준견본정보',
    countPerRow: 4,
    buttons: [
      { name: 'save', label: '저장' },
      { name: 'init', label: '초기화', disabled: true },
    ],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('psSpcmIdx', { value: 0 })
      .Hidden('aprUid', { value: TokenUtil.myId() })
      .Hidden('pitmVer')
      .multiple(
        'itemInfo',
        '자재정보',
        FormBuilder.builder()
          .Input('pitmCd', { readonly: true })
          .required()
          .spanCol(2)
          .Input('pitmNm', { readonly: true })
          .required()
          .spanCol(3)
          .Button('search', 'itemModal', { type: 'search' })
          .build(),
      )
      .spanCol(2)
      .Datepicker('enmDt', '제정일자', { value: todayDate })
      .required()
      .Input('aprUnm', '승인자', { value: TokenUtil.myName(), readonly: true })
      .Input('spcmNo', '표준견본번호', { readonly: true })
      .Input('muft', '제조원')
      .multiple(
        'expirDtInfo',
        '유효기한',
        FormBuilder.builder()
          .Datepicker('expirDt')
          .spanCol(3)
          .Button('add1Year', 'add1Year', { label: '+ 1년' })
          .spanCol(0.5)
          .Button('add2Year', 'add2Year', { label: '+ 2년' })
          .spanCol(0.5)
          .build(),
      )
      .spanCol(2)
      .Textarea('rmk', '비고', { rows: 2 })
      .spanCol(2)
      .Input('pdtNo', '생산번호', { readonly: true })
      .RadioGroup('useYn', '사용여부', {
        value: 'Y',
        groups: [
          { checkedValue: 'Y', label: '사용함' },
          { checkedValue: 'N', label: '사용안함' },
        ],
      })
      .build(),
};

export default {
  list,
  detailInfo,
};

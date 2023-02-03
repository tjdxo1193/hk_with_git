import dayjs from 'dayjs';

import api from '@/api';
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
      showRowCheckColumn: false,
      enableCellMerge: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('pitmNm', '품목명')
      .Input('pitmCd', '품목코드')
      .Input('pmSpcmNo', '포장재표준견본번호')
      .Select('pkgMtrDiv', '포장재구분', {
        async: () => api.combo.systemCommon.getPkgMtrDivCombo(),
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
      .col('pmSpcmNo', '포장재표준견본번호', { cellMerge: true })
      .col('pkgMtrDivNm', '포장재구분', {
        cellMerge: true,
        mergeRef: 'pmSpcmNo',
        mergePolicy: 'restrict',
      })
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('expirDt', '유효기한')
      .col('muft', '제조원')
      .col('rmk', '비고')
      .col('udtDs', '수정일')
      .build(),
};

const detailInfo = {
  static: {
    title: '포장재표준견본정보',
    countPerRow: 4,
    buttons: [
      { name: 'reject', label: '반려', disabled: true },
      { name: 'approve', label: '삭제승인', disabled: true },
      { name: 'init', label: '초기화', disabled: true },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('pmSpcmIdx', { value: 0 })
      .Hidden('pmSpcmDelAprIdx', { value: 0 })
      .Input('pmSpcmNo', '포장재표준견본번호', { readonly: true })
      .Input('crtUnm', '생성자', { readonly: true })
      .Input('crtDs', '생성일시', { readonly: true })
      .RadioGroup('delAprYn', '삭제승인여부', {
        value: 'Y',
        groups: [
          { checkedValue: 'Y', label: '삭제' },
          { checkedValue: 'N', label: '미삭제' },
        ],
        disabled: true,
      })
      .Input('pkgMtrDivNm', '포장재구분', { readonly: true })
      .Input('crtUnm', '등록자', { readonly: true })
      .Input('crtDs', '등록일시', { readonly: true })
      .Input('pmSpcmDelAprUnm', '삭제승인자', { readonly: true })
      .Input('rjtUnm', '반려자', { readonly: true })
      .Input('rjtDs', '반려일시', { readonly: true })
      .Input('rjtRea', '반려사유', { readonly: true })
      .spanCol(2)
      .build(),
};

const detailList = {
  static: {
    title: '구성목록',
    buttons: [],
    $grid: null,
    height: '200px',
    props: {
      showRowCheckColumn: false,
      editable: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('pitmVer', '품목버전')
      .col('expirDt', '유효기한', {
        editRenderer: {
          type: 'CalendarRenderer',
          maxlength: 10,
          defaultFormat: 'yyyy-mm-dd',
        },
      })
      .col('aprUnm', '승인자')
      .col('muft', '제조원')
      .col('rmk', '비고', { width: 300 })
      .build(),
};

export default {
  list,
  detailInfo,
  detailList,
};

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
      rowStyleFunction: function (rowIndex, item) {
        if (item.maxExpirDt < todayDate) {
          return 'redFont';
        }
        return null;
      },
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
      .DatepickerTwinWithSwitch('formEnmDt', '제정일자', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .DatepickerTwinWithSwitch('formExpirDt', '유효기한', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .Select('pmSpcmProc', '진행상태', {
        value: 'S0220100',
        async: () =>
          api.combo.systemCommon.getPmSpcmProcCombo().then((res) => {
            res.data = res.data.filter(({ value }) => value !== 'S0220300');
            return res;
          }),
      })
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
      .col('pmSpcmProcNm', '진행상태', { width: 90 })
      .col('finRcpDt', '최신접수일자', { width: 100 })
      .col('udtDs', '수정일', { width: 100 })
      .build(),
};

const detailInfo = {
  static: {
    title: '포장재표준견본정보',
    countPerRow: 4,
    buttons: [
      { name: 'deleteRequest', label: '삭제승인요청', disabled: true },
      { name: 'save', label: '저장' },
      { name: 'init', label: '초기화', disabled: true },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('pmSpcmIdx', { value: 0 })
      .Input('pmSpcmNo', '포장재표준견본번호', { readonly: true })
      .Select('pkgMtrDiv', '포장재구분', {
        async: () => api.combo.systemCommon.getPkgMtrDivCombo(),
      })
      .required()
      .Input('crtUnm', '생성자', { readonly: true })
      .Input('crtDs', '생성일시', { readonly: true })
      .Input('rjtUnm', '반려자', { readonly: true })
      .Input('rjtDs', '반려일시', { readonly: true })
      .Input('rjtRea', '반려사유', { readonly: true })
      .spanCol(2)
      .build(),
};

const detailList = {
  static: {
    title: '구성목록',
    buttons: [
      { name: 'add', label: '추가' },
      { name: 'delete', label: '삭제' },
    ],
    $grid: null,
    height: '200px',
    props: {
      showRowCheckColumn: true,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('pitmVer', '품목버전')
      .col('enmDt', '제정일자', {
        editRenderer: {
          type: 'CalendarRenderer',
          maxlength: 10,
          defaultFormat: 'yyyy-mm-dd',
        },
      })
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

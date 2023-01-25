import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const tabs = {
  tabs: [
    {
      name: 'monitorItemInfo',
      label: '모니터링품목정보',
    },
    {
      name: 'itemSe',
      label: '모니터링 품목 구분 관리',
    },
    {
      name: 'workplaceSe',
      label: '모니터링 작업실 구분 관리',
    },
  ],
};

const monitorManage = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('mitmPitmUpperDiv', '품목구분', {
        async: () => api.combo.common.getTreeCd('M1000000'),
      })
      .Select('mitmPitmDiv', '품목명', {
        async: (param) => api.combo.common.getTreeCd(param),
      })
      .Select('crgDptCd', '담당부서', {
        async: () => api.combo.common.getdptByLevel(2),
      })
      .Input('perSpec', '허가규격')
      .Select('mitmWrkStudioDiv', '작업동', {
        async: () => api.combo.common.getTreeCd('M2000000'),
      })
      .Select('mitmWrkPlcUpperDiv', '작업소', {
        async: (param) => api.combo.common.getTreeCd(param),
      })
      .Select('mitmWrkPlcDiv', '작업실', {
        async: (param) => api.combo.common.getTreeCd(param),
      })
      .Input('point', '포인트')
      .build(),
};
const monitorSearchResult = {
  static: {
    title: '조회결과',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('mitmPitmNm', '품목명')
      .col('mitmCd', '품목코드')
      .col('mitmWrkPlcNm', '작업실')
      .col('roomno', 'RoomNo')
      .col('point', '포인트')
      .col('grade', 'Grade')
      .col('ansCylNm', '시험주기')
      .col('perSpec', '허가규격')
      .col('ansStrDt', '시험시작일')
      .col('revwDurTm', '검토소요시간')
      .col('aprDurTm', '승인소요시간')
      .col('crgDptNm', '담당부서')
      .col('plntCd', { visible: false })
      .col('mitmWrkPlcDiv', { visible: false })
      .col('mitmPitmDiv', { visible: false })
      .col('ansCylDt', { visible: false })
      .col('crgDptCd', { visible: false })
      .col('useYn', { visible: false })
      .col('mitmWrkStudioDiv', { visible: false })
      .col('mitmWrkPlcUpperDiv', { visible: false })
      .col('mitmPitmUpperDiv', { visible: false })
      .build(),
};

const monitorManageInfo = {
  static: {
    title: '품목정보',
    countPerRow: 4,
    buttons: [
      { name: 'save', label: '등록' },
      { name: 'update', label: '수정', disabled: true },
      { name: 'delete', label: '삭제', type: 'danger', disabled: true },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('mitmPitmUpperDiv', '품목구분', {
        async: () => api.combo.common.getTreeCd('M1000001'),
      })
      .required()
      .Select('mitmPitmDiv', '품목명', {
        async: (param) => api.combo.common.getTreeCd(param),
      })
      .required()
      .Select('crgDptCd', '담당부서', {
        async: () => api.combo.common.getdptByLevel(2),
      })
      .required()
      .Input('perSpec', '허가규격')
      .Select('mitmWrkStudioDiv', '작업동', {
        async: () => api.combo.common.getTreeCd('M2000000'),
      })
      .required()
      .Select('mitmWrkPlcUpperDiv', '작업소', {
        async: (param) => api.combo.common.getTreeCd(param),
      })
      .required()
      .Select('mitmWrkPlcDiv', '작업실', {
        async: (param) => api.combo.common.getTreeCd(param),
      })
      .required()
      .Input('point', '포인트')
      .readonly()
      .Select('ansCylCd', '시험주기', {
        async: api.combo.common.getAnsCyl,
      })
      .required()
      .Datepicker('ansStrDt', '시험시작일', { value: todayDate })
      .Input('roomno', 'RoomNo')
      .readonly()
      .Input('grade', 'Grade')
      .readonly()
      .Input('revwDurTm', '검토소요시간')
      .Input('aprDurTm', '승인소요시간')
      .blank()
      .blank()
      .Hidden('mitmCd', 'mitmCd')
      .build(),
};

export default {
  tabs,
  monitorManage,
  monitorManageInfo,
  monitorSearchResult,
};

import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const tabs = {
  tabs: [
    {
      name: 'monitorItemInfo',
      label: '모니터링자재정보',
    },
    {
      name: 'itemSe',
      label: '모니터링 자재 구분 관리',
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
      .Select('mitmPitmUpperDiv', '자재구분', {
        async: () => api.combo.common.getTreeCd('M1000001'),
      })
      .Select('mitmPitmDiv', '자재내역', {
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
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('upperMitmPitmNm', '자재구분')
      .col('mitmPitmNm', '자재내역')
      .col('mitmCd', '자재번호')
      .col('mitmWrkPlcNm', '작업실')
      .col('roomno', 'RoomNo')
      .col('point', '포인트')
      .col('grade', false)
      .col('gradeNm', 'Grade')
      .col('ansCylNm', '시험주기')
      .col('perSpec', '허가규격')
      .col('ansStrDt', '시험시작일')
      .col('revwDurTm', '검토소요시간')
      .col('aprDurTm', '승인소요시간')
      .col('crgDptNm', '담당부서')
      .col('wrkDiv', false)
      .col('wrkDivNm', '작업구분')
      .col('plntCd', false)
      .col('mitmWrkPlcDiv', false)
      .col('mitmPitmDiv', false)
      .col('ansCylDt', false)
      .col('crgDptCd', false)
      .col('useYn', false)
      .col('mitmWrkStudioDiv', false)
      .col('mitmWrkPlcUpperDiv', false)
      .col('mitmPitmUpperDiv', false)
      .build(),
};

const monitorManageInfo = {
  static: {
    title: '자재정보',
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
      .Hidden('mitmCd')
      .Select('mitmPitmUpperDiv', '자재구분', {
        async: () => api.combo.common.getTreeCd('M1000001'),
        _required: true,
      })
      .Select('mitmPitmDiv', '자재내역', {
        async: (param) => api.combo.common.getTreeCd(param),
        _required: true,
      })
      .Select('ansCylCd', '시험주기', {
        async: api.combo.common.getAnsCyl,
        _required: true,
      })
      .Datepicker('ansStrDt', '시험시작일', { value: todayDate, _required: true })
      .Select('mitmWrkStudioDiv', '작업동', {
        async: () => api.combo.common.getTreeCd('M2000000'),
        _required: true,
      })
      .Select('mitmWrkPlcUpperDiv', '작업소', {
        async: (param) => api.combo.common.getTreeCd(param),
        _required: true,
      })
      .Select('mitmWrkPlcDiv', '작업실', {
        async: (param) => api.combo.common.getTreeCd(param),
        _required: true,
      })
      .Select('crgDptCd', '담당부서', {
        async: () => api.combo.common.getdptByLevel(2),
      })
      .Input('roomno', 'RoomNo', { maxLength: 5 })
      .Input('point', '포인트', { maxLength: 5 })
      .Select('grade', 'Grade', {
        async: () => api.combo.userCommon.getGradeCombo(),
      })
      .Select('wrkDiv', '작업구분', {
        async: () => api.combo.userCommon.getWorkDivCombo(),
      })
      .Input('perSpec', '허가규격', { maxLength: 20 })
      .Input('revwDurTm', '검토소요시간', { maxLength: 20 })
      .Input('aprDurTm', '승인소요시간', { maxLength: 20 })
      .blank()
      .build(),
};

export default {
  tabs,
  monitorManage,
  monitorManageInfo,
  monitorSearchResult,
};

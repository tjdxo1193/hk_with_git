import dayjs from 'dayjs';
import api from '@/api';

import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAddDate = dayjs().add(+1, 'M').format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    height: '450px',
    $grid: null,
    props: { editable: false, showRowCheckColumn: true },
    buttons: [
      { name: 'requestCreate', label: '수동생성' },
      { name: 'requestCancel', label: '의뢰취소' },
      { name: 'request', label: '의뢰' },
      { name: 'select', label: '조회' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperMitmPitmDiv', '품목구분', {
        async: () => api.combo.common.getTreeCd('M1000001'),
      })
      .Select('mitmPitmDiv', '품목명', {
        async: (param) => api.combo.common.getTreeCd(param),
      })
      .Input('mitmCd', '품목코드')
      .Select('crgDptCd', '담당부서', {
        async: api.combo.common.getDpt,
      })
      .Select('mitmWrkStudioDiv', '작업동', {
        async: () => api.combo.common.getUpperTreeCd('M2000000'),
      })
      .Select('upperMitmWrkPlcDiv', '작업소', {
        async: (param) => api.combo.common.getTreeCd(param),
      })
      .Select('mitmWrkPlcDiv', '작업실', {
        async: (param) => api.combo.common.getTreeCd(param),
      })
      .Input('roomno', 'RoomNo')
      .DatepickerTwinWithSwitch('searchAnsEdt', '시험예정일', {
        value: [todayDate, monthAddDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('upperMitmPitmDivNm', '품목구분')
      .col('mitmCd', '품목코드')
      .col('mitmPitmDivNm', '품목명')
      .col('mitmWrkStudioDivNm', '작업동')
      .col('upperMitmWrkPlcDivNm', '작업소')
      .col('mitmWrkPlcDivNm', '작업실')
      .col('roomno', 'RoomNo')
      .col('grade', 'Grade')
      .col('ansCylRuleNm', '시험주기')
      .col('ansEdt', '시험예정일')
      .col('point', '포인트')
      .col('crgDptNm', '담당부서')
      .col('wrkDivNm', '작업구분')
      .build(),
};


export default {
  list,
};

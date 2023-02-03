import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAgoDate = dayjs().add(-1, 'M').format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'receipt', label: '접수' },
      { name: 'select', label: '조회' },
    ],
    props: { editable: false, showRowCheckColumn: true },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperMitmPitmDiv', '품목구분', {
        async: () => api.combo.common.getTreeCd('M1000000'),
      })
      .Input('point', '포인트')
      .Input('roomno', 'RoomNo')
      .Select('ansCylCd', '시험주기', {
        async: api.combo.common.getAnsCyl,
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
      .Select('crgDptCd', '담당부서', {
        async: api.combo.common.getDpt,
      })
      .DatepickerTwinWithSwitch('searchReqDt', '의뢰일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('mitmReqIdx', false)
      .col('ansProcNm', '진행상태')
      .col('reqDt', '의뢰일자')
      .col('reqNo', '의뢰번호')
      .col('rcpDt', '접수일자')
      .col('mitmCd', '품목코드')
      .col('upperMitmPitmDivNm', '품목구분')
      .col('mitmPitmDivNm', '품목명')
      .col('upperMitmWrkPlcDivNm', '작업소')
      .col('mitmWrkPlcDivNm', '작업실')
      .col('roomno', 'RoomNo')
      .col('grade', 'Grade')
      .col('ansCylRuleNm', '시험주기')
      .col('ansEdt', '시험예정일')
      .col('point', '포인트')
      .col('crgDptNm', '담당부서')
      .build(),
};

const requestInfo = {
  static: {
    countPerRow: 4,
    id: 'requestInfo',
  },
  forms: () =>
    FormBuilder.builder()
      .Input('mitmCd', '품목코드')
      .Input('reqNo', '의뢰번호')
      .Input('reqDt', '의뢰일자')
      .Input('upperMitmPitmDivNm', '품목구분')
      .Input('mitmPitmDivNm', '품목명')
      .Input('ansCylRuleNm', '시험주기')
      .Input('roomno', 'RoomNo')
      .Input('grade', 'Grade')
      .Input('point', '포인트')
      .Input('upperMitmWrkPlcDivNm', '작업소')
      .Input('mitmWrkPlcDivNm', '작업실')
      .Input('crgDptNm', '담당부서')
      .build()
      .map((col) => ({
        ...col,
        disabled: true,
      })),
};

const itemList = {
  static: {
    $grid: null,
    props: { editable: false, showRowCheckColumn: false },
    height: '300px',
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('aitmSpecIdx', false)
      .col('aitmSeq', false)
      .col('amitmCd', false)
      .col('aitmKn', '시험항목명')
      .col('vriaNo', 'VARIANT<br>NO')
      .col('vriaKn', 'VARIANT<br>국문')
      .col('specTypNm', '규격유형')
      .col('jdgTypNm', '판정유형')
      .col('permitCriteria', '허가기준', {
        children: ColumnBuilder.builder()
          .col('perSlvLow', '하한')
          .col('perSlvUpp', '상한')
          .col('perSlvDesc', '서술')
          .build(),
      })
      .col('companyStandard', '자사기준', {
        children: ColumnBuilder.builder()
          .col('owcSlvLow', '하한')
          .col('owcSlvUpp', '상한')
          .col('owcSlvDesc', '서술')
          .build(),
      })
      .col('slvJdgCfmNm', '기준적합판정')
      .col('slvJdgNonCfmNm', '기준부적합판정')
      .col('rstUnitNm', '결과단위')
      .col('rstDpnt', '결과소수점')
      .col('rptPrtSlvVal', '성적서<br>출력기준')
      .col('rptPrtItmNm', '성적서<br>출력항목')
      .col('rptPrtYn', '성적서<br>출력여부')
      .col('ispDurTm', '검사소요시간')
      .col('eqmUseTm', '기기사용시간')
      .build(),
};

const tabs = {
  tabs: [
    { name: 'requestInfo', label: '시험의뢰정보' },
    { name: 'testInfo', label: '시험항목' },
  ],
  buttons: [{ name: 'init', label: '초기화', disabled: true }],
};

export default {
  list,
  requestInfo,
  itemList,
  tabs,
};

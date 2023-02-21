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
      { name: 'instruct', label: '지시승인' },
      { name: 'select', label: '조회' },
    ],
    props: { showRowCheckColumn: true },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperMitmPitmDiv', '자재구분', {
        async: () => api.combo.common.getTreeCd('M1000001'),
      })
      .Select('mitmPitmDiv', '자재내역', {
        async: (param) => api.combo.common.getTreeCd(param),
      })
      .Input('mitmCd', '자재번호')
      .Input('ansNo', '시험번호')
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
      .Input('point', '포인트')
      .Select('crgDptCd', '담당부서', {
        async: api.combo.common.getDpt,
      })
      .DatepickerTwinWithSwitch('searchRcpDt', '접수일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('mitmReqIdx', false)
      .col('aitmSpecIdx', false)
      .col('ansProcNm', '진행상태')
      .col('cplRqmDt', '완료요구일', {
        editRenderer: {
          type: 'CalendarRenderer',
          maxlength: 10,
          defaultFormat: 'yyyy-mm-dd',
        },
        headerStyle: 'editableBgColumn',
      })
      .col('rcpDt', '접수일자')
      .col('reqDt', '의뢰일자')
      .col('reqNo', '의뢰번호')
      .col('ansNo', '시험번호')
      .col('rcpRmk', '접수비고', { width: 150 })
      .col('upperMitmPitmDivNm', '자재구분')
      .col('mitmCd', '자재번호')
      .col('mitmPitmDivNm', '자재내역')
      .col('mitmWrkStudioDivNm', '작업동')
      .col('upperMitmWrkPlcDivNm', '작업소')
      .col('mitmWrkPlcDivNm', '작업실')
      .col('roomno', 'RoomNo')
      .col('grade', 'Grade')
      .col('ansCylRuleNm', '시험주기')
      .col('point', '포인트')
      .col('crgDptNm', '담당부서')
      .build(),
};

const itemList = {
  static: {
    $grid: null,
    title: '항목정보',
    props: { editable: false, showRowCheckColumn: true },
    height: '300px',
    buttons: [
      { name: 'delete', label: '삭제', disabled: true },
      { name: 'init', label: '초기화', disabled: true },
    ],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('mitmReqIdx', false)
      .col('rstSeq', false)
      .col('rstProcNm', false)
      .col('ansDptNm', '시험부서')
      .col('amitmCd', false)
      .col('aitmKn', '시험항목명', { editable: false })
      .col('vriaNo', 'VARIANT<br>NO', { editable: false })
      .col('vriaKn', 'VARIANT<br>국문', { editable: false })
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
      .col('rstJdgNm', '결과판정')
      .col('rptPrtSlvVal', '성적서<br>출력기준')
      .col('rptPrtItmNm', '성적서<br>출력항목')
      .col('rptPrtYn', '성적서<br>출력여부')
      .col('ispDurTm', '검사소요시간')
      .col('eqmUseTm', '기기사용시간')
      .col('ansEqmCd', '분석기기')
      .col('smpClltDt', '검체채취일자')
      .col('clltTmStr', '검체채취시작')
      .col('clltTmEnd', '검체채취종료')
      .col('smpClltQty', '검체채취수량')
      .col('smpClltUnit', '검체채취단위')
      .build(),
};
export default {
  list,
  itemList,
};

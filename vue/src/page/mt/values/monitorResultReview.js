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
    legends: [{ value: '안정성시험', className: 'stabilityTest' }],
    props: { editable: false, showRowCheckColumn: false },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperMitmPitmDiv', '품목구분', {
        async: () => api.combo.common.getTreeCd('M1000000'),
      })
      .Select('mitmPitmDiv', '품목명', {
        async: (param) => api.combo.common.getTreeCd(param),
      })
      .Input('mitmCd', '품목코드')
      .Select('crgDptCd', '담당부서', {
        async: api.combo.common.getDpt,
      })
      .DatepickerTwinWithSwitch('formReqDt', '의뢰일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .DatepickerTwinWithSwitch('formAssDt', '지시일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('mitmReqIdx', false)
      .col('ansProcNm', '진행상태')
      .col('reqNo', '의뢰번호')
      .col('ansNo', '시험번호')
      .col('assNo', '지시번호')
      .col('upperMitmPitmDivNm', '품목구분')
      .col('mitmPitmDivNm', '품목명')
      .col('mitmCd', '품목코드')
      .col('upperMitmWrkPlcDivNm', '작업소')
      .col('mitmWrkPlcDivNm', '작업실')
      .col('roomno', 'RoomNo')
      .col('grade', 'Grade')
      .col('point', '포인트')
      .col('crgDptNm', '담당부서')
      .col('reqDt', '의뢰일자')
      .col('rcpDt', '접수일자')
      .col('assDt', '지시일자')
      .col('rjtUnm', '반려자')
      .col('rjtDs', '반려일')
      .col('rjtRea', '반려사유')
      .col('cplRqmDt', '완료요구일')
      .build(),
};

const itemList = {
  static: {
    $grid: null,
    title: '항목정보',
    props: { editable: false, showRowCheckColumn: false },
    height: '300px',
    buttons: [
      { name: 'preResultTrend', label: '이전결과동향', disabled: true },
      { name: 'stage', label: 'STAGE', disabled: true },
      { name: 'hold', label: '시험보류', disabled: true },
      { name: 'reject', label: '검토반려', disabled: true },
      { name: 'approveRequest', label: '승인요청', disabled: true },
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
      .col('rstVal', '결과값')
      .col('markVal', '표기값')
      .col('rstRmk', '결과비고')
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

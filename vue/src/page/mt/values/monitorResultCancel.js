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
    props: { editable: false },
  },
  forms: () =>
    FormBuilder.builder()
      .RadioGroup('testDiv', '시험구분', {
        value: 'Y',
        groups: [
          { checkedValue: 'Y', label: '시험보류중' },
          { checkedValue: 'N', label: '시험진행중' },
        ],
        gap: 100,
      })
      .spanCol(2)
      .Select('ansProcCd', '진행상태', {
        async: () => api.combo.systemCommon.getAnsProcCombo(),
      })
      .Input('ansNo', '시험번호')
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
      .DatepickerTwinWithSwitch('searchReqDt', '의뢰일', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .DatepickerTwinWithSwitch('searchRcpDt', '접수일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('mitmReqIdx', false)
      .col('ansProcNm', '진행상태')
      .combo('sytJdg', '결과판정', {
        async: () => api.combo.systemCommon.getSytJdgCombo(),
      })
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
      .col('wrkDivNm', '작업구분')
      .col('point', '포인트')
      .col('crgDptNm', '담당부서')
      .col('reqDt', '의뢰일자')
      .col('rcpDt', '접수일자')
      .col('assDt', '지시일자')
      .col('cplRqmDt', '완료요구일')
      .col('rjtUnm', '반려자')
      .col('rjtDs', '반려일')
      .col('rjtRea', '반려사유')
      .col('hldDs', '보류일')
      .col('hldUid', '보류자')
      .col('hldRea', '보류사유')
      .col('hldCanlDs', '보류취소일')
      .col('hldCanlUid', '보류취소자')
      .col('hldCanlRea', '보류취소사유')
      .col('ansCanlDs', '시험취소일')
      .col('ansCanlUid', '시험취소자')
      .col('ansCanlRea', '시험취소사유')
      .build(),
};

const itemList = {
  static: {
    $grid: null,
    title: '항목정보',
    height: '300px',
    props: { editable: false, showRowCheckColumn: false },
    buttons: [
      { name: 'preResultTrend', label: '이전결과동향', disabled: true },
      { name: 'testCancel', label: '시험취소', disabled: true },
      { name: 'hold', label: '보류', disabled: true },
      { name: 'holdOff', label: '보류취소', disabled: true },
      { name: 'init', label: '초기화', disabled: true },
    ],
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('mitmReqIdx', false)
      .col('rstSeq', false)
      .col('rstProcNm', '결과진행상태')
      .col('ansDptNm', '시험부서')
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

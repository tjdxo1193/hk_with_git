import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const dayjs = require('dayjs');

const todayDate = dayjs().format('YYYY-MM-DD');

const monthAgoDate = dayjs().add(-1, 'month').format('YYYY-MM-DD');

const searchGridWithForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .RadioGroup('testDiv', '시험구분', {
        value: '',
        groups: [
          { checkedValue: '', label: '전체' },
          { checkedValue: 'hold', label: '시험보류' },
          { checkedValue: 'cancel', label: '시험취소' },
        ],
        gap: 60,
      })
      .Select('ansProcCd', '진행상황', {
        async: () => api.combo.systemCommon.getAnsProcCombo(),
      })
      .Select('upperMitmPitmDiv', '자재구분', {
        async: () => api.combo.common.getTreeCd('M1000001'),
      })
      .Select('mitmPitmDiv', '자재', {
        async: (param) => api.combo.common.getTreeCd(param),
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
      .Input('mitmCd', '항목코드')
      .Input('ansNo', '시험번호')
      .Input('roomno', 'RoomNo')
      .Input('grade', 'Grade')
      .Input('point', '포인트')
      .DatepickerTwinWithSwitch('reqDtList', '의뢰일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .DatepickerTwinWithSwitch('rcpDtList', '접수일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('mitmSpecIdx', { visible: false })
      .col('mitmReqIdx', { visible: false })
      .col('ansProcCd', { visible: false })
      .col('reqDptCd', { visible: false })
      .col('rjtUid', { visible: false })
      .col('hldUid', { visible: false })
      .col('ansCanlUid', { visible: false })
      .col('mitmWrkPlcDiv', { visible: false })
      .col('crgDptCd', { visible: false })
      .col('ansCylCd', { visible: false })
      .col('mitmPitmDiv', { visible: false })
      .col('mitmCd', '항목코드')
      .col('mitmPitmDivNm', '모니터링자재')
      .col('ansProcNm', '진행상태')
      .col('reqNo', '의뢰번호')
      .col('reqDt', '의뢰일자')
      .col('ansEdt', '시험예정일자')
      .col('ansNo', '시험번호')
      .col('reqDptNm', '의뢰부서')
      .col('rcpDt', '접수일자')
      .col('assNo', '지시번호')
      .col('assDt', '지시일자')
      .col('rjtDs', '반려일시')
      .col('rjtRea', '반려사유')
      .col('rjtNm', '반려자')
      .col('hldDs', '보류일시')
      .col('hldNm', '보류자')
      .col('hldRea', '보류사유')
      .col('ansCanlNm', '시험취소자')
      .col('ansCanlRea', '시험취소사유')
      .col('mitmWrkPlcDivNm', '작업소')
      .col('point', '포인트')
      .col('roomno', 'RoomNo')
      .col('crgDptNm', '담당부서')
      .col('ansCylNm', '시험주기')
      .build(),
};

const itemInfoGrid = {
  static: {
    title: '항목정보',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('mitmReqIdx', { visible: false })
      .col('reqIdx', { visible: false })
      .col('aitmKn', '시험항목명')
      .col('ansDptNm', '시험부서')
      .col('vriaNo', 'VARIANT<br/>NO')
      .col('vriaKn', 'VARIANT<br/>국문')
      .col('specTypNm', '규격유형')
      .col('jdgTypNm', '판정유형')
      .col('perStandard', '허가기준', {
        children: ColumnBuilder.builder()
          .col('perSlvLow', '하한')
          .col('perSlvUpp', '상한')
          .col('perSlvDesc', '서술')
          .build(),
      })
      .col('owcStandard', '자사기준', {
        children: ColumnBuilder.builder()
          .col('owcSlvLow', '하한')
          .col('owcSlvUpp', '상한')
          .col('owcSlvDesc', '서술')
          .build(),
      })
      .col('slvJdgCfmNm', '기준적합<br/>판정')
      .col('slvJdgNonCfmNm', '기준부적합<br/>판정')
      .col('rstUnitNm', '결과단위')
      .col('rstDpnt', '결과소수점')
      .col('rstVal', '결과값')
      .col('markVal', '표기값')
      .col('rstRmk', '결과비고')
      .col('rstJdgNm', '결과판정')
      .col('rptPrtSlvVal', '성적서<br/>출력기준')
      .col('rptPrtItmNm', '성적서<br/>출력항목')
      .col('rptPrtYn', '성적서<br/>출력여부')
      .col('ispDurTm', '검사소요시간')
      .col('eqmUseTm', '기기사용시간')
      .col('ansEqmNm', '분석기기')
      .col('smpClltDt', '검체채취일자')
      .col('clltTmStr', '검체채취시작')
      .col('clltTmEnd', '검체채취종료')
      .col('smpClltQty', '검체채취수량')
      .col('smpClltUnitNm', '검체채취단위')
      .build(),
};

export default {
  todayDate,
  searchGridWithForm,
  itemInfoGrid,
};

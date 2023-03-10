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
    props: {
      showRowCheckColumn: false,
      // rowStyleFunction: function (rowIndex, item) {
      //   return null;
      // },
    },
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
      .DatepickerTwinWithSwitch('searchRcpDt', '접수일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .DatepickerTwinWithSwitch('searchAssDt', '지시일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('mitmReqIdx', false)
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
      .col('point', '포인트')
      .col('crgDptNm', '담당부서')
      .col('reqDt', '의뢰일자')
      .col('rcpDt', '접수일자')
      .col('assDt', '지시일자')
      .col('cplRqmDt', '완료요구일')
      .col('rcpUnm', '접수자')
      .col('rjtUnm', '반려자')
      .col('rjtDs', '반려일')
      .col('rjtRea', '반려사유')
      .col('wrkDivNm', '작업구분')
      .build(),
};

const itemList = {
  static: {
    $grid: null,
    title: '항목정보',
    height: '300px',
    buttons: [
      { name: 'preResultTrend', label: '이전결과동향', disabled: true },
      { name: 'hold', label: '보류', disabled: true },
      { name: 'reject', label: '승인반려', disabled: true },
      { name: 'approve', label: '승인', disabled: true },
      { name: 'init', label: '초기화', disabled: true },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('mitmReqIdx')
      .Hidden('rstAprReqIdx')
      .Input('upperMitmPitmDivNm', '품목구분', { readonly: true })
      .Input('mitmPitmDivNm', '품목명', { readonly: true })
      .Input('ansNo', '시험번호', { readonly: true })
      .Select('sytJdg', '결과판정', {
        async: () => api.combo.systemCommon.getSytJdgCombo(),
      })
      .required()
      .Textarea('revwCmmt', '검토의견', { rows: 1, readonly: true })
      .spanCol(4)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('mitmReqIdx', false)
      .col('rstSeq', false)
      .col('rstProcNm', false)
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
      .col('smpClltUnitNm', '검체채취단위')
      .build(),
};

export default {
  list,
  itemList,
};

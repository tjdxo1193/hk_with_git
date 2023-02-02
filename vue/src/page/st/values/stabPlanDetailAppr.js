import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const searchForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('pitmNm', '품목명')
      .Input('pitmCd', '품목코드')
      .Select('ansKnd', '시험종류', {
        async: () => api.combo.userCommon.getAnsKndCombo(),
      })
      .Select('ansPps', '시험목적', {
        async: () => api.combo.userCommon.getAnsPpsCombo(),
      })
      .Input('sbtAnsPlnNo', '안정성 계획 번호')
      .Select('sbtCrgUid', '담당자', {
        async: api.combo.common.getUserList,
      })
      .DatepickerTwinWithSwitch('ansEdtBetween', '시험예정일', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .Hidden('sbtPlnIdx', '안정성 계획 IDX')
      .build(),
};

const legends = [
  { className: 'approveWating', value: '승인대기' },
  { className: 'testStop', value: '시험중단 및 중단취소' },
];

const statusList = {
  approveRequest: ['S0290200'],
  stopAndStopCencelRequest: ['S0290400', 'S0290600'],
};

const gridForSearchResult = {
  static: {
    title: '조회결과',
    $grid: null,
    legends,
    props: {
      editable: false,
      showRowCheckColumn: true,
      rowStyleFunction: (rowIndex, item) => {
        if (statusList.approveRequest.includes(item.sbtAnsProc)) {
          return 'approveWating';
        }
        if (statusList.stopAndStopCencelRequest.includes(item.sbtAnsProc)) {
          return 'testStop';
        }
        return '';
      },
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      // .col('1', '승인구분')
      // .col('2', '중단(취소)사유')
      // .col('pitmCd', '품목코드')
      // .col('4', '공정명')
      // .col('pitmNm', '품목명')
      // .col('lotNo', '제조번호')
      // .col('7', '제조/입고일자')
      // .col('ansKnd', '시험종류', { visible: false })
      // .col('ansKndNm', '시험종류')
      // .col('ansPps', '시험목적', { visible: false })
      // .col('ansPpsNm', '시험목적')
      // .col('ansPpsDtl', '상세시험목적')
      // .col('11', '보관조건')
      // .col('12', '보관장소')
      // .col('13', '안정성검체수량')
      // .col('14', '안정성검체단위')
      // .col('15', '포장수량')
      // .col('16', '포장단위')
      // .col('17', '포장상태')
      // .col('18', '시험예정일')
      // .col('19', '개월차수')
      // .col('20', '시험시작일')
      // .col('21', '시험종료일')
      // .col('22', '시험주기')
      // .col('23', '시험기간')
      // .col('24', '담당자')
      // .col('25', '시험상태')
      // .col('26', '비고')
      // .col('27', '문서번호')
      // .col('28', '제조수량')
      // .col('29', '제조단위')
      // .col('30', '유효기한')
      // .col('31', '허가규격')
      .col('pitmNm', '품목명')
      .col('pitmCd', '품목코드')
      .col('lotNo', '제조번호')
      .col('makDt', '제조일자')
      .col('sbtAnsProc', '진행상황', { visible: false })
      .col('sbtAnsProcNm', '진행상황', { width: '150' })
      .col('ansPps', '시험목적', { visible: false })
      .col('ansPpsNm', '시험목적')
      .col('ansPpsDtl', '상세시험목적')
      .col('ansKnd', '시험종류', { visible: false })
      .col('ansKndNm', '시험종류')
      .col('ansStrDt', '시작일자')
      .col('ansTrmCd', '시험기간', { visible: false })
      .col('ansTrmMarkNm', '시험기간')
      .col('sbtCrgUid', '안정성 담당 UID', { visible: false })
      .col('sbtCrgUidNm', '담당자')
      .col('sbtSmpVol', '안정성검체수량')
      .col('smpVolUnit', '안정성검체단위', { visible: false })
      .col('smpVolUnitNm', '안정성검체단위')
      .col('strgTerms', '보관조건', { visible: false })
      .col('strgTermsNm', '보관조건')
      .col('strgPla', '보관장소', { visible: false })
      .col('strgPlaNm', '보관장소')
      .col('rmk', '비고')
      .col('sbtAnsPlnNo', '시험번호')
      .col('docNo', '문서번호')
      .col('clltDt', '채취일자', { visible: false })
      .col('clltUid', '채취 UID', { visible: false })
      .col('clltUidNm', '채취자')
      .col('plnRjtDS', '반려일자', { visible: false })
      .col('plnRjtDt', '반려일자')
      .col('plnRjtUid', '반려자', { visible: false })
      .col('plnRjtUidNm', '반려자')
      .col('plnRjtRea', '반려사유')
      .col('sbtPlnIdx', '안정성 계획 IDX', { visible: false })
      .col('ansIdx', '시험 IDX', { visible: false })
      .col('sbtAnsPlnAprIdx', '안정성 시험계획 승인 IDX', { visible: false })
      .build(),
};

const itemSettingList = {
  static: {
    title: '항목설정',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
      copySingleCellOnRowMode: true,
      selectionMode: 'singleRow',
      showRowNumColumn: true,
      enableSorting: false,
      fixedColumnCount: 1,
      editableOnFixedCell: true,
    },
  },
  columns: () => ColumnBuilder.builder().build(),
};

const buttonGroups = {
  buttons: [
    { name: 'approve', label: '승인' },
    { name: 'reject', label: '반려' },
    { name: 'reset', label: '초기화' },
  ],
};

export default {
  searchForm,
  gridForSearchResult,
  itemSettingList,
  buttonGroups,
};

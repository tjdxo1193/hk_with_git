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
      .Select('ansKnd', '시험종류', {
        async: () => api.combo.userCommon.getAnsKndCombo(),
      })
      .DatepickerTwinWithSwitch('ansEdtBetween', '시험예정일', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .Input('lotNo', '제조번호')
      .Select('sbtCrgUid', '담당자', {
        async: api.combo.common.getUserList,
      })
      .DatepickerTwinWithSwitch('6', '시험일', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .build(),
};

const legends = [
  { className: 'return', value: '반려' },
  { className: 'approveRequest', value: '승인요청' },
  { className: 'testStop', value: '시험중단' },
];

const statusList = {
  return: ['S0290210', 'S0290410', 'S0290610'],
  request: ['S0290200', 'S0290400', 'S0290600'],
  stop: ['S0290500'],
};

const gridForSearchResult = {
  static: {
    title: '조회결과',
    $grid: null,
    legends: legends,
    props: {
      editable: false,
      showRowCheckColumn: false,
      rowStyleFunction: (rowIndex, item) => {
        if (statusList.return.includes(item.sbtAnsProc)) {
          return 'return';
        }
        if (statusList.request.includes(item.sbtAnsProc)) {
          return 'approveRequest';
        }
        if (statusList.stop.includes(item.sbtAnsProc)) {
          return 'testStop';
        }
        return null;
      },
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .button('fileAttacher', '첨부')
      .col('pitmNm', '품목명')
      .col('pitmCd', '품목코드', { visible: false })
      // .col('3', '공정명')
      .col('lotNo', '제조번호')
      .col('makDt', '제조일자')
      .col('sbtAnsProc', '진행상황', { visible: false })
      .col('sbtAnsProcNm', '진행상황')
      .col('ansPps', '시험목적', { visible: false })
      .col('ansPpsNm', '시험목적')
      .col('ansPpsDtl', '상세시험목적')
      .col('ansKnd', '시험종류', { visible: false })
      .col('ansKndNm', '시험종류')
      .col('ansStrDt', '시작일자')
      .col('ansEdt', '시험예정일')
      // .col('11', '개월차수')
      .col('ansEndDt', '시험종료일')
      .col('ansTrmCd', '시험기간', { visible: false })
      .col('ansTrmMarkNm', '시험기간')
      .col('sbtCrgUid', '안정성 담당 UID', { visible: false })
      .col('sbtCrgUidNm', '담당자')
      .col('sbtSmpVol', '안정성검체수량')
      .col('smpVolUnit', '안정성검체단위', { visible: false })
      .col('smpVolUnitNm', '안정성검체단위')
      .col('strgTerms', '보관조건', { visible: false })
      .col('strgTermsNm', '보관조건')
      // .col('23', '보관조건(허가사항)')
      .col('strgPla', '보관장소', { visible: false })
      .col('strgPlaNm', '보관장소')
      .col('rmk', '비고')
      .col('sbtAnsPlnNo', '시험번호')
      .col('docNo', '문서번호')
      .col('clltDt', '채취일자', { visible: false })
      .col('clltUid', '채취 UID', { visible: false })
      .col('clltUidNm', '채취자')
      // .col('32', '제조수량')
      // .col('33', '제조단위')
      // .col('34', '입고수량')
      // .col('35', '입고단위')
      // .col('36', '입고일')
      // .col('37', '사용(유효)기한')
      // .col('39', '사용기간')
      .col('plnRjtDS', '반려일자', { visible: false })
      .col('plnRjtDt', '반려일자')
      .col('plnRjtUid', '반려자', { visible: false })
      .col('plnRjtUidNm', '반려자')
      .col('plnRjtRea', '반려사유')
      // 이건 추가
      .col('sbtPlnIdx', '안정성 계획 IDX', { visible: false })
      .col('ansIdx', '시험 IDX', { visible: false })
      .col('sbtAnsPlnAprIdx', '안정성 시험계획 승인 IDX', { visible: false })
      .col('ansCylDiv', '시험 주기 구분', { visible: false })
      .col('ansCylDivNm', '시험 주기 구분명', { visible: false })
      // 이건 실제로 없는거d
      // .col('25', '시험상태')
      // .col('27', '중단사유')
      // .col('28', '중단취소사유')
      // .col('42', '등록일')
      .build(),
};

const stabItemSearchForm = {
  static: {
    title: '안정성품목조회',
    countPerRow: 1,
  },
  forms: () =>
    FormBuilder.builder()
      .multiple(
        'pitmData',
        '품목명',
        FormBuilder.builder()
          .Input('pitmCd', { readonly: true })
          .required()
          .spanCol(1)
          .Input('pitmNm', { readonly: true })
          .spanCol(4)
          .Button('search', 'stabItemSearchModal', { type: 'search' })
          .Button('itemReg', {
            disabled: true,
            type: 'default',
            label: '안정성상세계획등록',
          })
          .build(),
      )
      .multiple(
        'txtinfo',
        '정보',
        FormBuilder.builder()
          .Textarea('txtinfo1', { readonly: true, rows: 4 })
          .Textarea('txtinfo2', { readonly: true, rows: 4 })
          .Textarea('txtinfo3', { readonly: true, rows: 4 })
          .build(),
      )
      .spanRow(2)
      .Hidden('process')
      .Hidden('ansPps', '시험목적')
      .Hidden('ansPpsNm', '시험목적명')
      .Hidden('ansPpsDtl', '상세시험목적')
      .Hidden('ansKnd', '시험종류')
      .Hidden('ansKndNm', '시험종류')
      .Hidden('ansStrDt', '시작일자')
      .Hidden('ansTrmCd', '시험기간')
      .Hidden('ansTrmMarkNm', '시험기간')
      .Hidden('strgTerms', '보관조건')
      .Hidden('strgTermsNm', '보관조건')
      .Hidden('sbtSmpVol', '안정성검체량')
      .Hidden('smpVolUnit', '안정성검체단위')
      .Hidden('smpVolUnitNm', '안정성검체단위')
      .Hidden('lotNo', '제조번호')
      .Hidden('ansIdx', '시험 IDX')
      .Hidden('expiredate')
      .Hidden('ansCylDiv', '시험 주기 구분')
      .Hidden('ansCylDivNm', '시험 주기 구분명')
      .build(),
};

const stabInfoRegForm = {
  static: {
    title: '안정성정보등록',
    countPerRow: 4,
  },
  forms: () =>
    FormBuilder.builder()
      .Select('ansKnd', '시험종류', {
        async: () => api.combo.userCommon.getAnsKndCombo(),
      })
      .required()
      .Select('strgPla', '보관장소', {
        async: () => api.combo.userCommon.getStabPlanStrgPlaCombo(),
      })
      .required()
      .Select('ansTrmCd', '시험기간', {
        async: () => api.combo.common.getTestTerm(),
      })
      .required()
      .Datepicker('ansStrDt', '시작일자', {
        value: todayDate,
      })
      .required()
      .Select('ansPps', '시험목적', {
        async: () => api.combo.userCommon.getAnsPpsCombo(),
      })
      .required()
      .Select('strgTerms', '보관조건', {
        async: () => api.combo.userCommon.getStabPlanStrgTermsCombo(),
      })
      .required()
      .multiple(
        'sbtSmp',
        '안정성검체량',
        FormBuilder.builder()
          .InputNumber('sbtSmpVol')
          .required()
          .spanCol(2)
          .Select('smpVolUnit', null, {
            async: () => api.combo.userCommon.getSmpVolUnitCombo(),
          })
          .required()
          .build(),
      )
      .Select('sbtCrgUid', '담당자', {
        async: api.combo.common.getUserList,
      })
      .required()
      .Input('ansPpsDtl', '상세시험목적', { maxlength: 100 })
      .Input('docNo', '문서번호', { maxlength: 25 })
      .Input('sbtAnsPlnNo', '시험번호', { maxlength: 11 })
      .Textarea('rmk', '비고', { rows: 4, maxlength: 2000 })
      .spanRow(2)
      // .Input('14', '제조번호구분')
      // .required()
      .Hidden('sbtPlnIdx', '안정성 계획 IDX')
      .Hidden('ansIdx', '시험 IDX')
      .Hidden('sbtAnsPlnAprIdx', '안정성 시험 계획 승인 IDX')
      .build(),
};

const buttonGroups = {
  buttons: [
    { name: 'stopRequest', label: '시험중단', disabled: true },
    { name: 'stopCancelRequest', label: '시험중단취소', disabled: true },
    { name: 'approveRequest', label: '승인요청', disabled: true },
    { name: 'save', label: '저장' },
    { name: 'update', label: '수정', disabled: true },
    { name: 'delete', label: '삭제', disabled: true, type: 'danger' },
    { name: 'reset', label: '초기화' },
  ],
};

export default {
  searchForm,
  gridForSearchResult,
  stabItemSearchForm,
  stabInfoRegForm,
  buttonGroups,
};

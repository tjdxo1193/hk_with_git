import api from '@/api';
import {ColumnBuilder, FormBuilder} from '@/util';

const codeWithSearchGrid = {
  forms: () =>
    FormBuilder.builder()
      .Hidden('specProcCd', '규격서 프로세스 코드')
      .Hidden('qpSpecProcCd', '품목 프로세스 코드')
      .Hidden('pitmTyp', '품목타입')
      .build(),
};

const searchGridWithForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    legends: [
      { value: '품목미등록', className: 'notRegisterItem' },
      { value: '임시저장', className: 'tempSave' },
      { value: '승인/검토중', className: 'approveWating' },
      { value: '반려품목', className: 'return' },
    ],
    props: {
      editable: false,
      showRowCheckColumn: false,
      rowStyleFunction: (rowIndex, item) => {
        if (item.qpSpecProcCd === 'S0080100') {
          return 'notRegisterItem';
        }
        if (item.specProcCd === 'S0080100') {
          return 'tempSave';
        }
        if (item.specProcCd === 'S0080300' || item.specProcCd === 'S0080200') {
          return 'approveWating';
        }
        if (item.specProcCd === 'S0080110' || item.specProcCd === 'S0080210') {
          return 'return';
        }
        if (item.specProcCd === '') {
          return null;
        }
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('plntCd', '사업장', { async: api.combo.common.getPlntCd })
      .required()
      .Select('specProcCd', '등록여부', { async: () => api.combo.systemCommon.getSpecProcCombo() })
      .Input('pitmCd', '품목코드')
      .Input('pitmNm', '품목명')
      .Select('pitmTyp', '품목구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('pitmCd', '품목코드')
      .col('pitmNm', '품목명')
      .col('pitmTypNm', '품목구분')
      .col('crgDptNm', '담당부서')
      .col('pitmVer', '품목버전')
      .col('useVerYn', '버전사용여부')
      .col('specProcNm', '진행상태')
      .col('qpSpecProcCd', '품목진행상태', { visible: false })
      .col('revwNm', '검토자', { visible: false })
      .col('rjtNm', '반려자', { visible: false })
      .col('specProcCd', { visible: false })
      .col('rvsNo', { visible: false })
      .col('docNo', { visible: false })
      .col('rvsDt', { visible: false })
      .col('enfoDt', { visible: false })
      .col('rvsReaCd', { visible: false })
      .col('rvsCtt', { visible: false })
      .col('rvsDivPs', { visible: false })
      .col('revwUid', { visible: false })
      .col('revwDs', { visible: false })
      .col('rjtDs', { visible: false })
      .col('rjtRea', { visible: false })
      .col('revwIp', { visible: false })
      .col('pitmMstAprIdx', { visible: false })
      .col('pitmTyp', { visible: false })
      .col('pitmEn', { visible: false })
      .col('sapPrdha', { visible: false })
      .col('ansDurDay', { visible: false })
      .col('crgDptCd', { visible: false })
      .col('stdSpcmNo', { visible: false })
      .col('micYn', { visible: false })
      .col('clltMtd', { visible: false })
      .col('clltMcn', { visible: false })
      .col('clltPla', { visible: false })
      .col('smpStrgMtd', { visible: false })
      .col('smpVolUnit', { visible: false })
      .col('smpVolAns', { visible: false })
      .col('smpvolEtc', { visible: false })
      .col('smpVolStrg', { visible: false })
      .col('mtrTyp', { visible: false })
      .col('mtrMrp', { visible: false })
      .col('labNo', { visible: false })
      .col('brdAbbr', { visible: false })
      .col('nomMtr', { visible: false })
      .col('etrCtnQty', { visible: false })
      .col('rmtrSpec', { visible: false })
      .col('nbr', { visible: false })
      .col('ftnYn', { visible: false })
      .col('pcs01', { visible: false })
      .col('pcs02', { visible: false })
      .col('pcs03', { visible: false })
      .col('pcs04', { visible: false })
      .col('useTrm', { visible: false })
      .col('otcPrd', { visible: false })
      .col('dmsEptYn', { visible: false })
      .col('pearDiv', { visible: false })
      .col('mkrVol', { visible: false })
      .col('mkrVolUnit', { visible: false })
      .col('etnAnsReq', { visible: false })
      .col('ctrptNo', { visible: false })
      .col('perNo', { visible: false })
      .col('chagVol', { visible: false })
      .col('chagVolUnit', { visible: false })
      .col('dioYn', { visible: false })
      .col('prbInYn', { visible: false })
      .col('prbFeYn', { visible: false })
      .col('pnxFeYn', { visible: false })
      .build(),
};

const itemVersionGrid = {
  static: {
    title: '버전목록',
    height: '150px',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmVer', '품목버전')
      .col('rvsNo', { visible: false })
      .col('docNo', { visible: false })
      .col('rvsDt', { visible: false })
      .col('enfoDt', { visible: false })
      .col('rvsCtt', '개정내역', { visible: false })
      .col('useVerYn', '사용버전여부')
      .col('rvsReaNm', '개정사유')
      .col('revwNm', '검토자', { visible: false })
      .col('rjtNm', { visible: false })
      .col('rjtRea', { visible: false })
      .col('specProcNm', '진행상태')
      .col('udtNm', '수정자')
      .col('udtDs', '수정일시')
      .col('pitmCd', { visible: false })
      .col('specProcCd', { visible: false })
      .col('rvsReaCd', { visible: false })
      .col('rvsDivPs', { visible: false })
      .col('revwUid', { visible: false })
      .col('revwDs', { visible: false })
      .col('rjtDs', { visible: false })
      .col('revwIp', { visible: false })
      .col('pitmMstAprIdx', { visible: false })
      .col('rjtUid', { visible: false })
      .col('pitmTyp', { visible: false })
      .col('pitmNm', { visible: false })
      .col('pitmEn', { visible: false })
      .col('sapPrdha', { visible: false })
      .col('ansDurDay', { visible: false })
      .col('crgDptCd', { visible: false })
      .col('stdSpcmNo', { visible: false })
      .col('micYn', { visible: false })
      .col('clltMtd', { visible: false })
      .col('clltMcn', { visible: false })
      .col('clltPla', { visible: false })
      .col('smpStrgMtd', { visible: false })
      .col('smpVolUnit', { visible: false })
      .col('smpVolAns', { visible: false })
      .col('smpvolEtc', { visible: false })
      .col('smpVolStrg', { visible: false })
      .col('mtrTyp', { visible: false })
      .col('mtrMrp', { visible: false })
      .col('labNo', { visible: false })
      .col('brdAbbr', { visible: false })
      .col('nomMtr', { visible: false })
      .col('etrCtnQty', { visible: false })
      .col('rmtrSpec', { visible: false })
      .col('nbr', { visible: false })
      .col('ftnYn', { visible: false })
      .col('pcs01', { visible: false })
      .col('pcs02', { visible: false })
      .col('pcs03', { visible: false })
      .col('pcs04', { visible: false })
      .col('useTrm', { visible: false })
      .col('otcPrd', { visible: false })
      .col('dmsEptYn', { visible: false })
      .col('pearDiv', { visible: false })
      .col('mkrVol', { visible: false })
      .col('mkrVolUnit', { visible: false })
      .col('etnAnsReq', { visible: false })
      .col('ctrptNo', { visible: false })
      .col('perNo', { visible: false })
      .col('chagVol', { visible: false })
      .col('chagVolUnit', { visible: false })
      .col('dioYn', { visible: false })
      .col('prbInYn', { visible: false })
      .col('prbFeYn', { visible: false })
      .col('pnxFeYn', { visible: false })
      .build(),
};

const commonInfoForm = {
  static: {
    title: '공통정보',
    countPerRow: 2,
    height: '220px',
    buttons: [
      { name: 'bomModal', label: 'BOM', disabled: true },
      // { name: 'itemManageFileAttacherModal', label: 'CT 성적서', disabled: true },
      { name: 'firstReg', label: '최초 등록', disabled: true },
      { name: 'save', label: '저장', disabled: true },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('pitmCd', '품목코드')
      .readonly()
      .Input('pitmTypNm', '품목구분')
      .readonly()
      .Input('pitmNm', '품목명')
      .readonly()
      .Input('pitmEn', '품목영문')
      .readonly()
      .Hidden('pitmTyp')
      .readonly()
      .Select('crgDptCd', '담당부서', { async: () => api.combo.common.getdptByLevel(2) })
      .required()
      .Hidden('pitmVer', { value: 1 })
      .Checkbox('micYn', '미생물유무')
      .readonly()
      .blank()
      .blank()
      .Hidden('useVerYn')
      .Hidden('rvsNo')
      .Hidden('docNo')
      .Hidden('rvsDt')
      .Hidden('enfoDt')
      .Hidden('rvsReaCd')
      .Hidden('rvsCtt')
      .Hidden('rvsDivPs')
      .Hidden('revwUid')
      .Hidden('revwDs')
      .Hidden('revwIp')
      .Hidden('pitmMstAprIdx')
      .Hidden('rjtUid')
      .Hidden('rjtDs')
      .Hidden('rjtRea')
      .Hidden('insertYn')
      .Hidden('plntCd')
      .Hidden('specProcCd')
      .Hidden('qpSpecProcCd')
      .build(),
};

const materialInfoForm = {
  static: {
    title: '자재정보',
    countPerRow: 4,
  },
  forms: () =>
    FormBuilder.builder()
      .multiple(
        'sapPrdhaMulti',
        '자재계층코드',
        FormBuilder.builder()
          .Input('sapPrdha', '자재계층코드', { readonly: true })
          .Button('sapPrdhaSearch', 'sapPrdhaSearchModal', { type: 'search', disabled: true })
          .build(),
      )
      .readonly()
      .Input('mtrTyp', '자재유형')
      .readonly()
      .Input('mtrMrp', '자재MRP')
      .readonly()
      .Input('labNo', 'LAB NO')
      .readonly()
      .Input('brdAbbr', '브랜드약어')
      .readonly()
      .Input('nomMtr', '기본자재')
      .readonly()
      .InputNumber('etrCtnQty', '입고용기수량')
      .readonly()
      .Input('rmtrSpec', '원료규격')
      .readonly()
      .Input('nbr', '호수')
      .readonly()
      .Input('ftnYn', '기능성유무')
      .readonly()
      .Input('pcs01', '공정01')
      .readonly()
      .Input('pcs02', '공정02')
      .readonly()
      .Input('pcs03', '공정03')
      .readonly()
      .Input('pcs04', '공정04')
      .readonly()
      .multiple(
        'useTrm',
        '사용기간',
        FormBuilder.builder()
          .InputNumber('useTrm')
          .readonly()
          .TextView('useTrmUnit', { value: '개월' })
          .build(),
      )
      .Input('otcPrd', 'OTC제품')
      .readonly()
      .Input('dmsEptYn', '내수수출여부')
      .readonly()
      .Input('pearDiv', '펄구분')
      .readonly()
      .InputNumber('mkrVol', ' 표시용량')
      .readonly()
      .Input('mkrVolUnit', '표시용량단위')
      .readonly()
      .Input('etnAnsReq', '외부시험의뢰')
      .readonly()
      // .Input('ctrptNo', 'CT성적서번호')
      .multiple(
        'ctrptNoMulti',
        'CT성적서번호',
        FormBuilder.builder()
          .Input('ctrptNo', 'CT성적서번호', { readonly: true })
          .Button('ctrptNoSearch', 'itemManageFileAttacherModal', {
            type: 'search',
            disabled: true,
          })
          .build(),
      )
      .readonly()
      .Input('perNo', '허가번호')
      .readonly()
      .Input('chagVol', '실충전용량')
      .readonly()
      .Input('chagVolUnit', '실충전용량단위')
      .readonly()
      .Input('dioYn', '디옥산관리대상')
      .readonly()
      .Input('prbInYn', '파라벤포함여부')
      .readonly()
      .Input('prbFeYn', '파라벤프리관리대상')
      .readonly()
      .Input('pnxFeYn', '페녹시에탄올프리관리대상')
      .readonly()
      .Input('smpVolUnit', '기본단위')
      .readonly()
      .build(),
};

const sampleInfoForm = {
  static: {
    title: '검체정보',
    countPerRow: 4,
  },
  forms: () =>
    FormBuilder.builder()
      .Input('stdspcmNo', '표준견본번호')
      .InputNumber('ansDurDay', '시험소요일수')
      .Select('clltMtd', '검체채취방법', {
        async: () => api.combo.userCommon.getClltMtdCombo(),
      })
      .Select('clltMcn', '검체채취기구', {
        async: () => api.combo.userCommon.getClltMcnCombo(),
      })
      .Select('clltPla', '검체채취장소', {
        async: () => api.combo.userCommon.getClltPlaCombo(),
      })
      .Select('smpStrgMtd', '검체보관방법', {
        async: () => api.combo.userCommon.getSmpStrgMtdCombo(),
      })
      .InputNumber('smpVolAns', '검체량시험')
      .InputNumber('smpvolEtc', '검체량기타')
      .InputNumber('smpVolStrg', '검체량보관')
      .build(),
};

export default {
  searchGridWithForm,
  itemVersionGrid,
  commonInfoForm,
  materialInfoForm,
  sampleInfoForm,
  codeWithSearchGrid,
};

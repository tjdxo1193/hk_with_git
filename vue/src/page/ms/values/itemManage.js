import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const pitemType = {
  FINISHED_SET: 'S0180100',
  FINISHED_SINGLE: 'S0180101',
  BEAUTIFUL_PACKAGING: 'S0180102',
  SEMI_MANUFACTURES_FILLING_FOAM: 'S0180201',
  SEMI_MANUFACTURES_OTHER_PRODUCT: 'S0180202',
  SEMI_MANUFACTURES_BULK: 'S0180203',
  SEMI_MANUFACTURES_BASE: 'S0180204',
  RAW_MATERIAL: 'S0180400',
  PACKAGING_MATERIAL: 'S0180500',
  GOODS: 'S0180600',
};

const processCode = {
  TEMPORARY_SAVE: 'S0080100',
  APPROVED: 'S0080400',
};

const codeWithSearchGrid = {
  forms: () =>
    FormBuilder.builder()
      .Hidden('specProcCd', '규격서 프로세스 코드')
      .Hidden('qpSpecProcCd', '자재 프로세스 코드')
      .Hidden('pitmTyp', '자재타입')
      .build(),
};

const searchGridWithForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    legends: [
      { value: '자재미등록', className: 'notRegisterItem' },
      { value: '임시저장', className: 'tempSave' },
      { value: '승인/검토중', className: 'approveWating' },
      { value: '반려자재', className: 'return' },
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
      .Select('plntCd', '플랜트', { async: api.combo.common.getPlntCd })
      .required()
      .Select('pitmTyp', '자재구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .required()
      .Input('pitmCd', '자재번호')
      .Input('pitmNm', '자재내역')
      .Select('specProcCd', '진행상태', { async: () => api.combo.systemCommon.getSpecProcCombo() })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('pitmTypNm', '자재구분')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('specProcNm', '진행상태')
      .col('crgDptNm', '담당부서')
      .col('pitmVer', '자재버전')
      .col('specProcCd', 'spec_진행상태', { visible: false })
      .col('pitmSpecIdx', 'spec_자재규격IDX', { visible: false })
      .col('qpSpecProcCd', 'qm_pitm_자재진행상태', { visible: false })
      .col('useVerYn', 'qm_pitm_버전사용여부', { visible: false })
      .col('crgDptCd', 'qm_pitm_info', { visible: false })
      .col('pitmTyp', 'qm_pitm_info_자재유형', { visible: false })
      .col('pitmEn', 'qm_pitm_info_자재영문명', { visible: false })
      .col('sapPrdha', 'qm_pitm_info_자재계층코드', { visible: false })
      .build(),
};

const itemVersionGrid = {
  static: {
    title: '이력',
    height: '150px',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmVer', '자재버전')
      .col('rvsReaNm', '개정사유')
      .col('specProcNm', '진행상태')
      .col('udtNm', '수정자')
      .col('udtDs', '수정일시')
      .col('crgDptNm', '담당부서', { visible: false })
      .col('specProcCd', 'spec_진행상태', { visible: false })
      .col('pitmSpecIdx', 'spec_자재규격IDX', { visible: false })
      .col('qpSpecProcCd', 'qm_pitm_자재진행상태', { visible: false })
      .col('useVerYn', 'qm_pitm_버전사용여부', { visible: false })
      .col('delYn', 'qm_pitm_삭제여부', { visible: false })
      .col('rvsDt', 'qm_pitm_계정일자', { visible: false })
      .col('rvsReaCd', 'qm_pitm_개정사유', { visible: false })
      .col('rvsCtt', 'qm_pitm_개정내역', { visible: false })
      .col('pitmTyp', 'qm_pitm_info_자재유형', { visible: false })
      .col('pitmTypNm', 'qm_pitm_info_자재유형', { visible: false })
      .col('pitmEn', 'qm_pitm_info_자재영문명', { visible: false })
      .col('sapPrdha', 'qm_pitm_info_자재계층코드', { visible: false })
      .col('ansDurDay', 'qm_pitm_info', { visible: false })
      .col('crgDptCd', 'qm_pitm_info', { visible: false })
      .col('spcmNo', 'qm_pitm_info', { visible: false })
      .col('micYn', 'qm_pitm_info', { visible: false })
      .col('clltMtd', 'qm_pitm_info', { visible: false })
      .col('clltMcn', 'qm_pitm_info', { visible: false })
      .col('clltPla', 'qm_pitm_info', { visible: false })
      .col('smpStrgMtd', 'qm_pitm_info', { visible: false })
      .col('smpVolUnit', 'qm_pitm_info', { visible: false })
      .col('smpVolAns', 'qm_pitm_info', { visible: false })
      .col('smpVolEtc', 'qm_pitm_info', { visible: false })
      .col('smpVolMng', 'qm_pitm_info', { visible: false })
      .col('pkgMtrSpec', 'qm_pitm_info', { visible: false })
      .col('smpVolStrg', 'qm_pitm_info', { visible: false })
      .col('pkgaCd', 'qm_pitm_info_포장재시험코드', { visible: false })
      .col('mtrTyp', 'qm_pitm_info_sap', { visible: false })
      .col('mtrMrp', 'qm_pitm_info_sap', { visible: false })
      .col('labNo', 'qm_pitm_info_sap', { visible: false })
      .col('brdAbbr', 'qm_pitm_info_sap', { visible: false })
      .col('brdLne', 'qm_pitm_info_sap', { visible: false })
      .col('nomMtr', 'qm_pitm_info_sap', { visible: false })
      .col('etrCtnQty', 'qm_pitm_info_sap', { visible: false })
      .col('rmtrSpec', 'qm_pitm_info_sap', { visible: false })
      .col('nbr', 'qm_pitm_info_sap', { visible: false })
      .col('ftnYn', 'qm_pitm_info_sap', { visible: false })
      .col('pcs01', 'qm_pitm_info_sap', { visible: false })
      .col('pcs02', 'qm_pitm_info_sap', { visible: false })
      .col('pcs03', 'qm_pitm_info_sap', { visible: false })
      .col('pcs04', 'qm_pitm_info_sap', { visible: false })
      .col('useTrm', 'qm_pitm_info_sap', { visible: false })
      .col('otcPrd', 'qm_pitm_info_sap', { visible: false })
      .col('dmsEptYn', 'qm_pitm_info_sap', { visible: false })
      .col('pearDiv', 'qm_pitm_info_sap', { visible: false })
      .col('mkrVol', 'qm_pitm_info_sap', { visible: false })
      .col('mkrVolUnit', 'qm_pitm_info_sap', { visible: false })
      .col('busCrg', 'qm_pitm_info_sap', { visible: false })
      .col('etnAnsReq', 'qm_pitm_info_sap', { visible: false })
      .col('ctrptNo', 'qm_pitm_info_sap', { visible: false })
      .col('perNo', 'qm_pitm_info_sap', { visible: false })
      .col('chagVol', 'qm_pitm_info_sap', { visible: false })
      .col('chagVolUnit', 'qm_pitm_info_sap', { visible: false })
      .col('dioYn', 'qm_pitm_info_sap', { visible: false })
      .col('prbInYn', 'qm_pitm_info_sap', { visible: false })
      .col('prbFeYn', 'qm_pitm_info_sap', { visible: false })
      .col('pnxFeYn', 'qm_pitm_info_sap', { visible: false })
      .col('rmtrCfmul', 'qm_pitm_info_sap', { visible: false })
      .col('qdrugYn', 'qm_pitm_info_sap', { visible: false })
      .col('nomUnit', 'qm_pitm_info_sap', { visible: false })
      .col('pkgaTypNm', 'QM_PKGA_자재유형명', { visible: false })
      .build(),
};

const commonInfoForm = {
  static: {
    title: '공통정보',
    countPerRow: 2,
    height: '220px',
    buttons: [
      { name: 'bomModal', label: 'BOM', disabled: true },
      { name: 'firstReg', label: '최초 등록', disabled: true },
      { name: 'save', label: '저장', disabled: true },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('pitmTypNm', '자재구분')
      .readonly()
      .Input('pitmCd', '자재번호')
      .readonly()
      .Input('pitmNm', '자재내역')
      .readonly()
      .Input('pitmEn', '자재내역(영문)')
      .readonly()
      .Select('crgDptCd', '담당부서', { async: () => api.combo.common.getdptByLevel(2) })
      .required()
      .blank()
      .Hidden('plntCd')
      .Hidden('pitmTyp', '자재구분코드')
      .Hidden('pitmVer')
      .Hidden('useVerYn')
      .Hidden('delYn', 'qm_pitm_삭제여부')
      .Hidden('rvsDt', 'qm_pitm_계정일자')
      .Hidden('rvsReaCd', 'qm_pitm_개정사유')
      .Hidden('rvsCtt', 'qm_pitm_개정내역')
      .Hidden('specProcCd')
      .Hidden('qpSpecProcCd')
      .Hidden('pitmSpecIdx')
      .build(),
};

const detailInfoForm = {
  static: {
    title: '세부정보',
    countPerRow: 4,
  },
  forms: () =>
    FormBuilder.builder()
      .Input('sapPrdha', '자재계층코드')
      .readonly()
      .Input('sapPrdha', '자재계층명')
      .readonly()
      .Input('pkgaCd', '1차 포장재유형코드')
      .readonly()
      .Input('pkgaTypNm', '1차 포장재유형명')
      .readonly()
      .InputNumber('smpVolAns', '시험검체량(일반)')
      .InputNumber('smpVolStrg', '시험검체량(초도)')
      .InputNumber('smpVolEtc', '시험검체량(기타)')
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
      .Select('smpStrgMtd', '검체보관방법', {
        async: () => api.combo.userCommon.getSmpStrgMtdCombo(),
      })
      .Select('clltMtd', '검체채취방법', {
        async: () => api.combo.userCommon.getClltMtdCombo(),
      })
      .Select('clltMcn', '검체채취기구', {
        async: () => api.combo.userCommon.getClltMcnCombo(),
      })
      .Select('clltPla', '검체채취장소', {
        async: () => api.combo.userCommon.getClltPlaCombo(),
      })
      .Hidden('ansDurDay', 'qm_pitm_info')
      .Hidden('spcmNo', 'qm_pitm_info')
      .Hidden('micYn', 'qm_pitm_info')
      .Hidden('smpVolUnit', 'qm_pitm_info')
      .Hidden('smpVolMng', 'qm_pitm_info')
      .Hidden('pkgMtrSpec', 'qm_pitm_info')
      .Hidden('mtrTyp', 'qm_pitm_info_sap')
      .Hidden('mtrMrp', 'qm_pitm_info_sap')
      .Hidden('labNo', 'qm_pitm_info_sap')
      .Hidden('brdAbbr', 'qm_pitm_info_sap')
      .Hidden('brdLne', 'qm_pitm_info_sap')
      .Hidden('nomMtr', 'qm_pitm_info_sap')
      .Hidden('etrCtnQty', 'qm_pitm_info_sap')
      .Hidden('rmtrSpec', 'qm_pitm_info_sap')
      .Hidden('nbr', 'qm_pitm_info_sap')
      .Hidden('ftnYn', 'qm_pitm_info_sap')
      .Hidden('pcs01', 'qm_pitm_info_sap')
      .Hidden('pcs02', 'qm_pitm_info_sap')
      .Hidden('pcs03', 'qm_pitm_info_sap')
      .Hidden('pcs04', 'qm_pitm_info_sap')
      .Hidden('useTrm', 'qm_pitm_info_sap')
      .Hidden('otcPrd', 'qm_pitm_info_sap')
      .Hidden('dmsEptYn', 'qm_pitm_info_sap')
      .Hidden('pearDiv', 'qm_pitm_info_sap')
      .Hidden('mkrVol', 'qm_pitm_info_sap')
      .Hidden('mkrVolUnit', 'qm_pitm_info_sap')
      .Hidden('busCrg', 'qm_pitm_info_sap')
      .Hidden('etnAnsReq', 'qm_pitm_info_sap')
      .Hidden('perNo', 'qm_pitm_info_sap')
      .Hidden('chagVol', 'qm_pitm_info_sap')
      .Hidden('chagVolUnit', 'qm_pitm_info_sap')
      .Hidden('dioYn', 'qm_pitm_info_sap')
      .Hidden('prbInYn', 'qm_pitm_info_sap')
      .Hidden('prbFeYn', 'qm_pitm_info_sap')
      .Hidden('pnxFeYn', 'qm_pitm_info_sap')
      .Hidden('rmtrCfmul', 'qm_pitm_info_sap')
      .Hidden('qdrugYn', 'qm_pitm_info_sap')
      .Hidden('nomUnit', 'qm_pitm_info_sap')
      .build(),
};

const materialInfoForm = {
  static: {
    title: '재질정보',
    countPerRow: 4,
  },
  forms: () =>
    FormBuilder.builder()
      .Input('1', '재질')
      .Input('2', '규격1')
      .Input('3', '규격2')
      .Input('4', '중량')
      .multiple(
        '5',
        '만충용량',
        FormBuilder.builder()
          .InputNumber('51', '')
          .Select('52', { async: () => api.combo.userCommon.getSmpVolUnitCombo() })
          .build(),
      )
      .Input('6', '비고')
      .blank()
      .blank()
      .build(),
};

export default {
  pitemType,
  processCode,
  searchGridWithForm,
  itemVersionGrid,
  commonInfoForm,
  detailInfoForm,
  materialInfoForm,
  codeWithSearchGrid,
};

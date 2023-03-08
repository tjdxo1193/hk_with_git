import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const processCode = {
  TEMPORARY_SAVE: 'S0080100',
  REVIEW_RETURN: 'S0080110',
  REQUEST_REVIEW: 'S0080200',
  APPROVAL_REJECTION: 'S0080210',
  APPROVAL_REQUEST: 'S0080300',
  APPROVED: 'S0080400',
  SPEC_REMOVE: 'S0080900',
};

const pitemtype = {
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

const valueWithPitmGrid = {
  forms: () =>
    FormBuilder.builder()
      .Hidden('pitmCd', '자재번호')
      .Hidden('pitmVer', '자재버전')
      .Hidden('pitmTyp', '자재구분')
      .build(),
};

const valueWithVersionGrid = {
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd', '공장코드')
      .Hidden('pitmSpecIdx', '자재규격서IDX')
      .Hidden('delYn', '삭제 여부')
      .Hidden('pitmCd', '자재번호')
      .Hidden('aitmSpecIdx', '시험항목규격IDX')
      .Hidden('aitmSpecVer', '규격서버전')
      .Hidden('pitmVer', '자재버전')
      .Hidden('docNo', '문서번호')
      .Hidden('rvsNo', '개정번호')
      .Hidden('rvsDt', '개정일자')
      .Hidden('enfoDt', '시행일자')
      .Hidden('rvsCtt', '개정내역')
      .Hidden('rvsDivPs', '개정구분')
      .Hidden('useVerYn', '사용버전여부')
      .Hidden('rvsReaCd', '개정사유')
      .Hidden('specProcCd', '진행상태코드')
      .Hidden('specProcNm', '진행상태')
      .Hidden('revwUid', '검토자')
      .Hidden('revwUnm', '검토자')
      .Hidden('rjtUid', '반려자')
      .Hidden('rjtUnm', '반려자')
      .Hidden('rjtRea', '반려사유')
      .build(),
};

const pitmList = {
  static: {
    title: '자재 목록',
    countPerRow: 4,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    legends: [
      { value: '임시저장', className: 'tempSave' },
      { value: '승인/검토중', className: 'approveWating' },
      { value: '반려자재', className: 'return' },
    ],
    props: {
      editable: false,
      showRowCheckColumn: false,
      rowStyleFunction: (rowIndex, item) => {
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
      .Select('pitmTyp', '자재구분', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmCd', '자재번호')
      .Input('pitmNm', '자재내역')
      .Select('specProcCd', '진행상태', { async: () => api.combo.systemCommon.getSpecProcCombo() })
      .Select('crgDptCd', '담당부서', { async: () => api.combo.common.getdptByLevel(2) })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '공장코드', false)
      .col('pitmTyp', '자재유형', false)
      .col('pitmTypNm', '자재유형')
      .col('pitmCd', '자재번호')
      .col('pitmNm', '자재내역')
      .col('pitmEn', '자재내역(영문)')
      .col('pitmVer', '자재버전')
      .col('specProcNm', '규격진행상태')
      .col('stdspcmNo', '표준견본번호')
      .col('ansDurDay', '시험소요일')
      .col('_', '외부시험의뢰')
      .col('crgDptNm', '담당부서')
      .combo('smpStrgMtd', '검체보관방법', {
        async: () => api.combo.userCommon.getSmpStrgMtdCombo(),
      })
      .combo('clltMtd', '채취방법', { async: () => api.combo.userCommon.getClltMtdCombo() })
      .combo('clltMcn', '채취기구', { async: () => api.combo.userCommon.getClltMcnCombo() })
      .combo('clltPla', '채취장소', { async: () => api.combo.userCommon.getClltPlaCombo() })
      .col('prbFeYn', '파라벤프리관리대상')
      .col('pnxFeYn', '페녹시에탄올프리관리대상')
      .col('dioYn', '디옥산관리대상')
      .col('crgDptCd', '담당부서코드', false)
      .col('sapPrdha', 'SAP제품계층구조', false)
      .col('specProcCd', '규격진행상태코드', false)
      .col('qpSpecProcNm', '자재진행상태', false)
      .col('qpSpecProcCd', '자재진행상태코드', false)
      .col('useVerYn', '사용버전여부', false)
      .col('delYn', '삭제여부', false)
      .col('pitmMstAprIdx', '자재마스터승인IDX', false)
      .build(),
};

const versionList = {
  static: {
    title: '버전목록',
    height: '300px',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '공장코드', false)
      .col('pitmSpecIdx', '자재규격서IDX', false)
      .col('delYn', '삭제 여부', false)
      .col('pitmCd', '자재번호', false)
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('pitmVer', '자재버전')
      .col('aitmSpecVer', '규격서버전')
      .col('docNo', '문서번호')
      .col('rvsNo', '개정번호')
      .col('rvsDt', '개정일자')
      .col('enfoDt', '시행일자')
      .col('rvsCtt', '개정내역')
      .combo('rvsReaCd', '개정사유', { async: () => api.combo.userCommon.getRvsReaCombo() })
      .col('specProcNm', '진행상태')
      .col('revwUnm', '검토자')
      .col('rjtUnm', '반려자')
      .col('rjtRea', '반려사유')
      .col('pitmTyp', '자재구분', false)
      .col('sapPrdha', '자재계층코드(SAP)', false)
      .col('1_', '자재계층명(SAP)', false)
      .col('2_', '1차 포장재유형 코드', false)
      .col('3_', '1차 포장재유형', false)
      .col('ansDurDay', '시험소요일', false)
      .col('perNo', '허가번호', false)
      .col('4_', '심사번호', false)
      .col('revwUid', '검토자', false)
      .col('rjtUid', '반려자', false)
      .col('useVerYn', '사용버전여부', false)
      .col('specProcCd', '진행상태코드', false)
      .col('pitmNm', '자재내역', false)
      .col('pkgaCd', '포장재시험코드(QM_PITM_INFO)', false)
      .col('lsapPrdha', '계층유형코드(Lims)', false)
      .col('pkgaTypNm', '계층유형명(Lims)', false)
      .col('labNo', 'LAB NO', false)
      .col('ctrptNo', 'CT 성적서 번호', false)
      .build(),
};

const commonInfoForm = {
  static: {
    title: '규격정보',
    countPerRow: 2,
    height: '370px',
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pitmTyp', '자재유형', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .disabled()
      .Input('pitmCd', '자재번호')
      .readonly()
      .Input('pitmNm', '자재내역')
      .spanCol(2)
      .readonly()
      .Hidden('pkgaCd', '계층 유형 코드')
      .Input('sapPrdha', '자재 계층 코드(SAP)')
      .readonly()
      .Input('_', '자재 계층 명(SAP)')
      .readonly()
      .Input('_', '1차포장재유형코드')
      .readonly()
      .Input('_', '1차포장재유형')
      .readonly()
      .Input('labNo', 'LAB NO')
      .readonly()
      .Input('ctrptNo', 'CT 성적서 번호')
      .readonly()
      .multiple(
        'pkgaCdMulti',
        '계층 유형 코드(LIMS)',
        FormBuilder.builder()
          .Input('lsapPrdha', '계층 유형 코드')
          .readonly()
          .Button('pkgaCdSearch', 'pkgaCdModal', { type: 'search', disabled: true })
          .build(),
      )
      .readonly()
      .Input('pkgaTypNm', '계층 유형 명(LIMS)')
      .readonly()
      .Input('ansDurDay', '시험소요일')
      .readonly()
      .Input('_', '심사번호')
      .readonly()
      .Input('perNo', '허가번호')
      .readonly()
      .Hidden('plntCd', '사업장 코드')
      .Hidden('pitmVer', '자재 버전')
      .Hidden('pitmSpecIdx', '품목 규격서 IDX')
      .Hidden('aitmSpecIdx', '시험항목 규격 IDX')
      .build(),
};

const testItemList = {
  static: {
    title: '시험항목',
    $grid: null,
    buttons: [
      { name: 'temporarySave', label: '임시저장', disabled: true },
      { name: 'requestReview', label: '검토요청', disabled: true },
      { name: 'updateVersion', label: '수정', disabled: true },
      { name: 'addRow', label: '항목추가', disabled: true },
      { name: 'copyRow', label: '항목복사', disabled: true },
      { name: 'removeRow', label: '항목삭제', disabled: true },
      { name: 'up', label: '위', type: 'up', disabled: true },
      { name: 'down', label: '아래', type: 'down', disabled: true },
      { name: 'elnSpec', label: 'ELN규격', disabled: true },
      { name: '_', label: '허가자료열람', disabled: true },
      { name: 'packagingSpec', label: '포장사양서', disabled: true },
    ],
    props: {
      editable: true,
      selectionMode: 'multipleCells',
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '플랜트코드', false)
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('aitmSeq', '시험항목순번', false)
      .combo('ansDptCd', '시험파트', {
        async: () => api.combo.common.getdptByLevel(3),
        width: 150,
      })
      .col('aitmKn', '시험항목명', {
        width: 120,
        editable: false,
      })
      .col('vriaNo', 'VariantNo', {
        width: 80,
        editable: false,
      })
      .col('vriaKn', 'Variant국문', {
        width: 100,
        editable: false,
      })
      .combo('specTyp', '규격유형', {
        async: () => api.combo.systemCommon.getSpecTypCombo(),
      })
      .col('StandardIntegration', '기준통합', {
        children: ColumnBuilder.builder()
          .col('perspecTxt', '기준(허가)')
          .col('owcSpecTxt', '기준(자사)')
          .col('specTxtEn', '기준(영문)')
          .build(),
      })
      .col('amitmCd', '시험항목별방법코드', false)
      .col('aitmOrd', '시험항목정렬순서', {
        width: 120,
        editable: false,
      })
      .combo('jdgTyp', '판정유형', { async: () => api.combo.systemCommon.getJdgTypCombo() })
      .col('mkrQty', '표시량', {
        width: 100,
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
        },
      })
      .col('perStandard', '기준값(허가)', {
        children: ColumnBuilder.builder()
          .col('perSlvLow', '하한', {
            editRenderer: {
              type: 'InputEditRenderer',
              onlyNumeric: true,
              allowPoint: true,
            },
          })
          .col('perSlvUpp', '상한', {
            editRenderer: {
              type: 'InputEditRenderer',
              onlyNumeric: true,
              allowPoint: true,
            },
          })
          .col('perSlvDesc', '서술')
          .build(),
      })
      .col('owcStandard', '기준값(자사)', {
        children: ColumnBuilder.builder()
          .col('owcSlvLow', '하한', {
            editRenderer: {
              type: 'InputEditRenderer',
              onlyNumeric: true,
              allowPoint: true,
            },
          })
          .col('owcSlvUpp', '상한', {
            editRenderer: {
              type: 'InputEditRenderer',
              onlyNumeric: true,
              allowPoint: true,
            },
          })
          .col('owcSlvDesc', '서술')
          .build(),
      })
      .combo('slvJdgCfm', '기준적합명', {
        width: 100,
        async: () => api.combo.userCommon.getSlvJdgCombo(),
      })
      .combo('slvJdgNonCfm', '기준부적합명', {
        width: 100,
        async: () => api.combo.userCommon.getSlvJdgCombo(),
      })
      .combo('rstUnitCd', '단위', {
        width: 100,
        async: () => api.combo.userCommon.getSmpVolUnitCombo(),
      })
      .col('rstDpnt', '결과소수점', {
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
        },
      })
      .col('report', '성적서', {
        children: ColumnBuilder.builder()
          .col('rptPrtItmNm', '항목(출력)')
          .col('rptPrtSlvVal', '기준(출력)')
          .build(),
      })
      .combo('rptPrtYn', '성적서출력여부', {
        width: 100,
        list: [
          { label: 'Y', value: 'Y' },
          { label: 'N', value: 'N' },
        ],
      })
      .col('aitmRmk', '비고', { width: 200 })
      .col('smpClltQty', '검체채취수량', false, {
        width: 100,
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
        },
      })
      .combo('smpClltUnit', '검체채취단위', false, {
        width: 100,
        async: () => api.combo.userCommon.getSmpVolUnitCombo(),
      })
      .col('ispDurTm', '검사소요시간', false, {
        width: 100,
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
        },
      })
      .col('eqmUseTm', '기기사용시간', false, {
        width: 100,
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
        },
      })
      .build(),
};

export default {
  pitemtype,
  processCode,
  pitmList,
  versionList,
  commonInfoForm,
  testItemList,
  valueWithPitmGrid,
  valueWithVersionGrid,
};

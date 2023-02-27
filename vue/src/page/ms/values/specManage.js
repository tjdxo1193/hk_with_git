import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const rvsDivPsComboList = [
  { value: 'P', label: '자재' },
  { value: 'S', label: '규격서' },
];

const valueWithPitmGrid = {
  forms: () =>
    FormBuilder.builder()
      .Hidden('pitmCd', '자재번호')
      .Hidden('pitmVer', '자재버전')
      .Hidden('pitmTyp', '자재 타입')
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
      .Select('pitmTyp', '자재유형', { async: () => api.combo.systemCommon.getPitmDivCombo() })
      .Input('pitmCd', '자재번호')
      .Input('pitmNm', '자재내역')
      .Select('crgDptCd', '담당부서', { async: () => api.combo.common.getdptByLevel(2) })
      .Select('specProcCd', '진행상태', { async: () => api.combo.systemCommon.getSpecProcCombo() })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '공장코드', false)
      .col('pitmCd', '자재번호')
      .col('pitmTyp', '자재유형', false)
      .col('pitmTypNm', '자재유형')
      .col('pitmNm', '자재내역')
      .col('pitmEn', '자재내역(영문)')
      .col('pitmVer', '자재버전')
      .col('crgDptNm', '담당부서')
      .col('crgDptCd', '담당부서코드', false)
      .col('sapPrdha', 'SAP제품계층구조', false)
      .col('ansDurDay', '시험소요일수')
      .col('stdspcmNo', '표준견본번호')
      .col('micYn', '미생물여부')
      .combo('clltMtd', '채취방법', { async: () => api.combo.userCommon.getClltMtdCombo() })
      .combo('clltMcn', '채취기구', { async: () => api.combo.userCommon.getClltMcnCombo() })
      .combo('clltPla', '채취장소', { async: () => api.combo.userCommon.getClltPlaCombo() })
      .combo('smpStrgMtd', '검체보관방법', {
        async: () => api.combo.userCommon.getSmpStrgMtdCombo(),
      })
      .col('specProcNm', '규격진행상태')
      .col('specProcCd', '규격진행상태코드', false)
      .col('qpSpecProcNm', '자재진행상태', false)
      .col('qpSpecProcCd', '자재진행상태코드', false)
      .col('useVerYn', '사용버전여부')
      .col('delYn', '삭제여부', false)
      .col('pitmMstAprIdx', '자재마스터승인IDX', false)
      .col('opsSpecSapPrdha', '포장시험에 등록된 SAP 계층코드', false)
      .col('labNo', 'LAB NO', false)
      .col('ctrptNo', 'CT 성적서 번호', false)
      .build(),
};

const versionList = {
  static: {
    title: '버전목록',
    height: '200px',
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
      .col('aitmSpecVer', '규격서버전')
      .col('pitmVer', '자재버전')
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('docNo', '문서번호')
      .col('rvsNo', '개정번호')
      .col('rvsDt', '개정일자')
      .col('enfoDt', '시행일자')
      .col('rvsCtt', '개정내역')
      .combo('rvsDivPs', '개정구분', { list: rvsDivPsComboList })
      .col('useVerYn', '사용버전여부', { width: 100 })
      .combo('rvsReaCd', '개정사유', { async: () => api.combo.userCommon.getRvsReaCombo() })
      .col('specProcCd', '진행상태코드', false)
      .col('specProcNm', '진행상태')
      .col('revwUid', '검토자', false)
      .col('revwUnm', '검토자')
      .col('rjtUid', '반려자', false)
      .col('rjtUnm', '반려자')
      .col('rjtRea', '반려사유')
      .build(),
};

const commonInfoForm = {
  static: {
    title: '자재정보',
    countPerRow: 2,
    height: '268px',
    buttons: [{ name: 'putPkgaCd', label: '저장', disabled: true }],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('pitmCd', '자재번호')
      .readonly()
      .Select('pitmTyp', '자재유형', {
        async: () => api.combo.systemCommon.getPitmDivCombo(),
      })
      .disabled()
      .multiple(
        'pkgaCdModal',
        '자재계층',
        FormBuilder.builder()
          .Input('pkgaCd', '자재계층', { readonly: true })
          .required()
          .Button('pkgaCdSearch', 'pkgaCdModal2', { type: 'search', disabled: true })
          .build(),
      )
      .Input('sapPrdha', '자재계층코드')
      .readonly()
      .Input('pitmNm', '자재내역')
      .readonly()
      .Input('labNo', 'LAB NO')
      .readonly()
      .Input('ctrptNo', 'CT 성적서 번호')
      .readonly()
      .Hidden('plntCd', '사업장 코드')
      .Hidden('pitmCd', '품목 코드')
      .Hidden('pitmVer', '품목 버전')
      .Hidden('pitmSpecIdx', '품목 규격서 IDX')
      .Hidden('aitmSpecIdx', '시험항목 규격 IDX')
      .build(),
};

const testItemList = {
  static: {
    title: '시험항목목록',
    $grid: null,
    buttons: [
      { name: 'temporarySave', label: '임시저장', disabled: true },
      { name: 'requestReview', label: '검토요청', disabled: true },
      { name: 'updateVersion', label: '수정', disabled: true },
      { name: 'elnSpec', label: 'ELN규격', disabled: true },
      { name: 'addRow', label: '항목추가', disabled: true },
      { name: 'copyRow', label: '항목복사', disabled: true },
      { name: 'removeRow', label: '항목삭제', disabled: true },
      { name: 'up', label: '위', type: 'up', disabled: true },
      { name: 'down', label: '아래', type: 'down', disabled: true },
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
      .col('amitmCd', '시험항목별방법코드', false)
      .col('aitmOrd', '시험항목정렬순서', {
        width: 120,
        editable: false,
      })
      .col('perspecTxt', '허가규격텍스트', { width: 100 })
      .col('owcSpecTxt', '자사규격텍스트', { width: 100 })
      .col('specTxtEn', '규격텍스트영문', { width: 100 })
      .combo('specTyp', '규격유형', {
        async: () => api.combo.systemCommon.getSpecTypCombo(),
      })
      .combo('jdgTyp', '판정유형', { async: () => api.combo.systemCommon.getJdgTypCombo() })
      .col('mkrQty', '표시량', {
        width: 100,
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
        },
      })
      .col('perStandard', '허가기준', {
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
      .col('owcStandard', '자사기준', {
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
      .combo('slvJdgCfm', '기준판정적합', {
        width: 100,
        async: () => api.combo.userCommon.getSlvJdgCombo(),
      })
      .combo('slvJdgNonCfm', '기준판정부적합', {
        width: 100,
        async: () => api.combo.userCommon.getSlvJdgCombo(),
      })
      .combo('rstUnitCd', '결과단위코드', {
        width: 100,
        async: () => api.combo.userCommon.getSmpVolUnitCombo(),
      })
      .col('rstDpnt', '결과소수점', {
        editRenderer: {
          type: 'InputEditRenderer',
          onlyNumeric: true,
        },
      })
      .col('rptPrtSlvVal', '성적서출력기준값', { width: 120 })
      .col('rptPrtItmNm', '성적서출력항목명', { width: 120 })
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
  pitmList,
  versionList,
  commonInfoForm,
  testItemList,
  valueWithPitmGrid,
  valueWithVersionGrid,
};

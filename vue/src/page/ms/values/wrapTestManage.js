import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const rvsDivPsComboList = [
  { value: 'P', label: '품목' },
  { value: 'S', label: '규격서' },
];

const valueWithqmPkgaListGrid = {
  forms: () =>
    FormBuilder.builder()
      .Hidden('pkgaTypNm', '자재 유형')
      .Hidden('specProcCdNm', '진행상태')
      .Hidden('delYn', '삭제 여부')
      .Hidden('sapPrdha', '자재 계층 코드')
      .Hidden('plntCd', '사업장 코드', false)
      .Hidden('pkgaCd', '자재계층관리 코드', false)
      .Hidden('pkgaVer', '자재계층관리 버전', false)
      .Hidden('aitmSpecIdx', '시험항목 규격 IDX', false)
      .Hidden('pkgaDiv', 'ZS010자재계층관리 구분', false)
      .Hidden('specProcCd', 'ZS008규격서 진행상태 코드', false)
      .Hidden('rvsNo', '개정 번호', false)
      .Hidden('docNo', '문서 번호', false)
      .Hidden('rvsDt', '개정 일자', false)
      .Hidden('enfoDt', '시행 일자', false)
      .Hidden('rvsReaCd', 'ZU007개정 사유 코드', false)
      .Hidden('rvsCtt', '개정 내역', false)
      .Hidden('rvsDivPs', '개정 구분 PS', false)
      .Hidden('useVerYn', '사용 버전 여부', false)
      .Hidden('pkgaSpecAprIdx', '자재계층관리 규격 승인IDX', false)
      .build(),
};

const qmPkgaList = {
  static: {
    title: '조회 결과',
    countPerRow: 2,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    legends: [
      { value: '임시저장', className: 'tempSave' },
      { value: '승인대기중', className: 'approveWating' },
      { value: '반려품목', className: 'return' },
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
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('sapPrdha', '자재 계층 코드', { maxLength: 80 })
      .Input('docNo', '문서번호', { maxLength: 80 })
      .Select('pkgaDiv', '자재계층관리 구분', {
        async: () => api.combo.systemCommon.getPackageDivCombo(),
      })
      .RadioGroup('delYn', '사용여부', {
        value: '',
        groups: [
          { checkedValue: '', label: '전체' },
          { checkedValue: 'Y', label: '사용중' },
          { checkedValue: 'N', label: '사용안함' },
        ],
      })
      .spanRow(2)
      .Select('specProcCd', '진행상태', {
        async: () => api.combo.systemCommon.getSpecProcCombo(),
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pkgaTypNm', '자재 유형')
      .col('sapPrdha', '자재 계층 코드')
      .col('specProcCdNm', '진행상태')
      .col('delYn', '삭제 여부')
      .col('delYnNm', '사용여부', false)
      .col('udtUserNm', '최종 수정자')
      .col('plntCd', '사업장 코드', false)
      .col('pkgaCd', '자재계층관리 코드', false)
      .col('pkgaVer', '자재계층관리 버전', false)
      .col('aitmSpecIdx', '시험항목 규격 IDX', false)
      .col('pkgaDiv', 'ZS010자재계층관리 구분', false)
      .col('specProcCd', 'ZS008규격서 진행상태 코드', false)
      .col('useVerYn', '사용 버전 여부', false)
      .col('rvsNo', '개정 번호', false)
      .col('docNo', '문서 번호', false)
      .col('rvsDt', '개정 일자', false)
      .col('enfoDt', '시행 일자', false)
      .col('rvsReaCd', 'ZU007개정 사유 코드', false)
      .col('rvsCtt', '개정 내역', false)
      .col('rvsDivPs', '개정 구분 PS', false)
      .col('revwUid', '검토 UID', false)
      .col('revwDs', '검토 일시', false)
      .col('revwIp', '검토 IP', false)
      .col('pkgaSpecAprIdx', '자재계층관리 규격 승인IDX', false)
      .col('rjtUid', '반려 UID', false)
      .col('rjtDs', '반려 일시', false)
      .col('rjtRea', '반려 사유', false)
      .col('crtIp', '생성 IP', false)
      .col('crtDs', '생성 일시', false)
      .col('crtUid', '생성 UID', false)
      .col('udtIp', '수정 IP', false)
      .col('udtDs', '수정 일시', false)
      .col('udtUid', '수정 UID', false)
      .build(),
};

const versionList = {
  static: {
    title: '버전 목록',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('aitmSpecVer', '시험항목 규격 버전')
      .col('pkgaTypNm', '자재 유형')
      .col('sapPrdha', '자재 계층 코드')
      .col('specProcCdNm', '진행상태')
      .col('rvsNo', '개정 번호')
      .col('docNo', '문서 번호')
      .combo('rvsReaCd', '개정사유', { async: () => api.combo.userCommon.getRvsReaCombo() })
      .combo('rvsDivPs', '개정구분', { list: rvsDivPsComboList })
      .col('rvsCtt', '개정 내역')
      .col('useVerYn', '사용 버전 여부')
      .col('delYn', '삭제 여부')
      .col('delYnNm', '사용중', false)
      .col('plntCd', '사업장 코드', false)
      .col('aitmSpecIdx', '시험항목 규격 IDX', false)
      .col('pkgaVer', '자재계층관리 버전', false)
      .col('pkgaCd', '자재계층관리 코드', false)
      .col('pkgaDiv', '자재계층관리 구분', false)
      .col('specProcCd', 'ZS008규격서 진행상태 코드', false)
      .col('pkgaSpecAprIdx', '자재계층관리 규격 승인IDX', false)
      .col('rvsDt', '개정 일자', false)
      .col('enfoDt', '시행 일자', false)
      .col('rvsReaCdNm', '개정 사유', false)
      .col('revwUid', '검토 UID', false)
      .col('revwDs', '검토 일시', false)
      .col('revwIp', '검토 IP', false)
      .col('rjtUid', '반려 UID', false)
      .col('rjtDs', '반려 일시', false)
      .col('rjtRea', '반려 사유', false)
      .col('crtIp', '생성 IP', false)
      .col('crtDs', '생성 일시', false)
      .col('crtUid', '생성 UID', false)
      .col('udtIp', '수정 IP', false)
      .col('udtDs', '수정 일시', false)
      .col('udtUid', '수정 UID', false)
      .col('udtUserNm', '수정자')
      .build(),
};

const versionForm = {
  static: {
    title: '시험방법정보',
    countPerRow: 2,
    buttons: [
      { name: 'temporarySave', label: '임시저장', disabled: false },
      { name: 'updateVersion', label: '수정', disabled: true },
      { name: 'approval', label: '검토완료', disabled: true },
      { name: 'reset', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('pkgaDiv', '자재계층관리 구분', {
        async: () => api.combo.systemCommon.getPackageDivCombo(),
      })
      .required()
      .Input('pkgaTypNm', '자재 유형', { maxLength: 25 })
      .required()
      .Input('sapPrdha', '자재 계층 코드', { maxLength: 18 })
      .Input('rvsNo', '개정 번호', { maxLength: 6 })
      .disabled()
      .Input('docNo', '문서 번호', { maxLength: 6 })
      .disabled()
      .Input('specProcCdNm', '규격서진행상태', { disabled: true })
      .disabled()
      .RadioGroup('delYn', '사용여부', {
        value: 'N',
        groups: [
          { checkedValue: 'N', label: '사용중' },
          { checkedValue: 'Y', label: '사용안함' },
        ],
      })
      .required()
      .blank()
      .Hidden('aitmSpecVer', '시험항목 규격 버전')
      .Hidden('rvsReaCd', '개정사유')
      .Hidden('rvsDivPs', '개정구분')
      .Hidden('rvsCtt', '개정 내역')
      .Hidden('useVerYn', '사용 버전 여부')
      .Hidden('aitmSpecIdx', '시험항목 규격 IDX')
      .Hidden('pkgaVer', '자재계층관리 버전')
      .Hidden('pkgaCd', '자재계층관리 코드')
      .Hidden('pkgaDiv', '자재계층관리 구분')
      .Hidden('specProcCd', 'ZS008규격서 진행상태 코드')
      .Hidden('rvsDt', '개정 일자')
      .Hidden('enfoDt', '시행 일자')
      .Hidden('rvsReaCdNm', '개정 사유')
      .build(),
};

const versionFormRequiredList = ['pkgaDiv', 'pkgaTypNm', 'sapPrdha', 'delYn'];

const tabs = {
  tabs: [
    {
      name: 'testItemList',
      label: '시험항목 목록',
    },
    {
      name: 'specList',
      label: '품목 목록',
    },
  ],
};

const testItemList = {
  static: {
    title: '시험항목 목록',
    $grid: null,
    buttons: [
      { name: 'addTestItem', label: '항목추가', disabled: true },
      { name: 'removeTestItem', label: '항목삭제', disabled: true },
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
      .col('plntCd', '사업장코드', false)
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('aitmSeq', '시험항목순번', false)
      .combo('ansDptCd', '시험파트', {
        async: () => api.combo.common.getdptByLevel(3),
        width: 150,
      })
      .col('aitmKn', '시험항목명', { editable: false })
      .col('vriaNo', 'VARIANT NO', { editable: false })
      .col('vriaKn', 'VARIANT 국문', { editable: false })
      .col('amitmCd', '시험항목별방법 CD', false)
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
            },
          })
          .col('perSlvUpp', '상한', {
            editRenderer: {
              type: 'InputEditRenderer',
              onlyNumeric: true,
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
            },
          })
          .col('owcSlvUpp', '상한', {
            editRenderer: {
              type: 'InputEditRenderer',
              onlyNumeric: true,
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
      .col('aitmRmk', '비고')
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
      .col('ispDurTm', '검사소여시간', false, {
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

const specList = {
  static: {
    title: '품목 목록',
    $grid: null,
    legends: [
      { value: '임시저장', className: 'tempSave' },
      { value: '승인대기중', className: 'approveWating' },
      { value: '반려품목', className: 'return' },
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
      },
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('pitmNm', '품목 명')
      .col('pitmEn', '품목 영문')
      .col('plntCd', '공장코드', false)
      .col('pitmSpecIdx', '품목규격서IDX', false)
      .col('delYn', '삭제 여부', false)
      .col('pitmCd', '품목코드', false)
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('aitmSpecVer', '규격서버전')
      .col('pitmVer', '품목버전')
      .col('docNo', '문서번호')
      .col('rvsNo', '개정번호')
      .col('rvsDt', '개정일자')
      .col('enfoDt', '시행일자')
      .col('rvsCtt', '개정내역')
      .combo('rvsDivPs', '개정구분', { list: rvsDivPsComboList })
      .col('useVerYn', '사용버전여부', { width: 100 })
      .combo('rvsReaCd', '개정사유코드', {
        async: () => api.combo.userCommon.getRvsReaCombo(),
      })
      .col('specProcCd', '진행상태코드', false)
      .col('specProcCdNm', '진행상태')
      .col('revwUid', '검토자', false)
      .col('revwUserNm', '검토자', false)
      .col('rjtUid', '반려자')
      .col('rjtRea', '반려사유')
      .build(),
};

export default {
  qmPkgaList,
  versionList,
  versionForm,
  versionFormRequiredList,
  tabs,
  testItemList,
  specList,
  valueWithqmPkgaListGrid,
};

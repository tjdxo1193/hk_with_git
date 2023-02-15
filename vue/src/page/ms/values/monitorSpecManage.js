import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const rvsDivPsComboList = [
  { value: 'P', label: '품목' },
  { value: 'S', label: '규격서' },
];

const valueWithMItemSpecGrid = {
  forms: () => FormBuilder.builder().Hidden('mitmCd', '품목코드').build(),
};
const valueWithVersionGrid = {
  forms: () =>
    FormBuilder.builder()
      .Hidden('specProcCd', '진행상황')
      .Hidden('mitmCd', '모니터링항목코드')
      .Hidden('aitmSpecIdx', '시험항목 규격 IDX')
      .Hidden('mitmSpecIdx', '모니터링규격IDX')
      .Hidden('aitmSpecVer', '버전')
      .Hidden('useVerYn', '버전사용여부')
      .Hidden('docNo', '문서번호')
      .Hidden('enfoDt', '시행일자')
      .build(),
};

const mItemSpecList = {
  static: {
    title: '모니터링항목 목록',
    countPerRow: 4,
    $grid: null,
    legends: [
      { value: '임시저장', className: 'tempSave' },
      { value: '승인/검토중', className: 'approveWating' },
      { value: '반려품목', className: 'return' },
    ],
    buttons: [{ name: 'search', label: '조회' }],
    props: {
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
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('mitmCd', '품목코드')
      .Input('mitmPitmDivNm', '품목명')
      .Select('crgDptCd', '담당부서', { async: api.combo.common.getDpt })
      .Select('specProcCd', '진행상태', { async: () => api.combo.systemCommon.getSpecProcCombo() })
      .build(),

  columns: () =>
    ColumnBuilder.builder()
      .col('plantCd', '공장코드', false)
      .col('mitmCd', '품목코드')
      .col('mitmPitmDivNm', '품목명')
      .col('mitmWrkPlcDivNm', '작업실')
      .col('mitmWrkPlcDiv', '모니터링항목작업실구분', false)
      .col('mitmPitmDiv', '모니터링항목품목구분', false)
      .col('point', '포인트')
      .col('roomno', 'RoomNo')
      .col('grade', 'Grade', false)
      .col('gradeNm', 'Grade')
      .col('ansStrDt', '시험시작일자')
      .col('ansCylNm', '시험주기')
      .col('ansCylCd', '시험주기코드', false)
      .col('crgDptNm', '담당부서')
      .col('crgDptCd', '담당부서코드', false)
      .col('perSpec', '허가규격')
      .col('revwDurTm', '검토소요시간')
      .col('aprDurTm', '승인소요시간')
      .col('mitmSpecIdx', false)
      .col('aitmSpecIdx', false)
      .col('specProcCd', '진행상황', false)
      .col('specProcNm', '진행상황')
      .col('useVerYn', '사용버전여부', false)
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
      .col('plantCd', '공장코드', false)
      .col('mitmSpecIdx', '모니터링항목 규격서 IDX', false)
      .col('aitmSpecIdx', '시험항목 규격 IDX', false)
      .col('delYn', '삭제 여부', false)
      .col('mitmSpecAprIdx', '모니터링항목 규격 승인 IDX', false)
      .col('mitmCd', '모니터링항목코드', false)
      .col('aitmSpecVer', '버전')
      .col('docNo', '문서번호')
      .col('rvsNo', '개정번호')
      .calendar('rvsDt', '개정일자')
      .calendar('enfoDt', '시행일자')
      .col('rvsCtt', '개정내역')
      .col('useVerYn', '사용 버전 여부', { width: 120 })
      .combo('rvsReaCd', '개정사유', { async: () => api.combo.userCommon.getRvsReaCombo() })
      .combo('rvsDivPs', '개정구분', { list: rvsDivPsComboList })
      .col('specProcCd', '진행상태코드', false)
      .combo('specProcNm', '진행상태', {
        async: () => api.combo.systemCommon.getSpecProcCombo(),
      })
      .col('revwUnm', '검토자')
      .col('revwUid', false)
      .col('revwDs', '검토일시')
      .col('rjtUnm', '반려자')
      .col('rjtUid', false)
      .col('rjtDs', '반려일시')
      .col('rjtRea', '반려사유')
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
      { name: 'addRow', label: '항목추가', disabled: true },
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
      .col('plntCd', '사업장코드', false)
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('aitmSeq', '시험항목순번', false)
      .combo('ansDptCd', '시험파트', {
        async: () => api.combo.common.getdptByLevel(3),
        width: 150,
      })
      .col('aitmKn', '시험항목명', { editable: false })
      .col('vriaNo', 'VariantNo', { editable: false })
      .col('vriaKn', 'Variant국문', { editable: false })
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

export default {
  mItemSpecList,
  versionList,
  testItemList,
  valueWithMItemSpecGrid,
  valueWithVersionGrid,
};

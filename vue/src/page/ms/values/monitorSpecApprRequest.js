import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const rvsDivPsComboList = [
  { value: 'P', label: '자재' },
  { value: 'S', label: '규격서' },
];

const versionValueHiddenForm = {
  forms: () =>
    FormBuilder.builder()
      .Hidden('specProcCd', '진행상황')
      .Hidden('mitmCd', '모니터링항목코드')
      .Hidden('aitmSpecIdx', '시험항목 규격 IDX')
      .Hidden('mitmSpecIdx', '모니터링규격IDX')
      .Hidden('aitmSpecVer', '버전')
      .build(),
};
const versionList = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'search', label: '조회' },
      { name: 'approveRequest', label: '승인요청' },
      { name: 'reject', label: '반려' },
    ],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('rvsNo', '개정번호')
      .Input('docNo', '문서번호')
      .DatepickerTwinWithSwitch('rvsDt', '개정일자', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .build(),

  columns: () =>
    ColumnBuilder.builder()
      .col('plantCd', '공장코드', false)
      .col('mitmSpecIdx', '모니터링항목 규격서 IDX', false)
      .col('aitmSpecIdx', '시험항목 규격 IDX', false)
      .col('delYn', '삭제 여부', false)
      .col('mitmSpecAprIdx', '모니터링항목 규격 승인 IDX', false)
      .col('mitmCd', '자재번호')
      .col('mitmPitmDivNm', '자재내역')
      .col('mitmWrkPlcDivNm', '작업실')
      .col('mitmWrkPlcDiv', '모니터링항목작업실구분', false)
      .col('mitmPitmDiv', '모니터링항목자재구분', false)
      .col('aitmSpecVer', '버전')
      .col('docNo', '문서번호')
      .col('rvsNo', '개정번호')
      .col('rvsDt', '개정일자')
      .col('enfoDt', '시행일자')
      .col('rvsCtt', '개정내역')
      .col('useVerYn', '사용 버전 여부', { width: 120 })
      .combo('rvsReaCd', '개정사유', { async: () => api.combo.userCommon.getRvsReaCombo() })
      .combo('rvsDivPs', '개정구분', { list: rvsDivPsComboList })
      .col('specProcCd', '진행상태코드', false)
      .col('specProcNm', '진행상태')
      .col('point', '포인트')
      .col('roomno', 'RoomNo')
      .col('perSpec', '허가규격')
      .col('grade', 'Grade', false)
      .col('gradeNm', 'Grade')
      .col('ansStrDt', '시험시작일자')
      .col('ansCylNm', '시험주기')
      .col('ansCylCd', '시험주기코드', false)
      .col('crgDptNm', '담당부서')
      .col('crgDptCd', '담당부서코드', false)
      .col('revwDurTm', '검토소요시간')
      .col('aprDurTm', '승인소요시간')
      .col('revwUid', '검토자')
      .col('revwDs', '검토일시')
      .col('rjtUid', '반려자')
      .col('rjtDs', '반려일시')
      .col('rjtRea', '반려사유')
      .build(),
};

const testItemList = {
  static: {
    title: '시험항목목록',
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', '플랜트코드', false)
      .col('aitmSpecIdx', '시험항목규격IDX', false)
      .col('aitmSeq', '시험항목순번', false)
      .combo('ansDptCd', '시험파트', { async: api.combo.common.getDpt })
      .col('aitmKn', '시험항목명')
      .col('vriaNo', 'VariantNo')
      .col('vriaKn', 'Variant국문')
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
  versionList,
  testItemList,
  versionValueHiddenForm,
};

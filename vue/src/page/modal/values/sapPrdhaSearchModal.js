import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const valueWithWrapInfo = {
  forms: () =>
    FormBuilder.builder()
      .Hidden('pkgaCd', '포장재시험 코드')
      .Hidden('aitmSpecIdx', '시험규격IDX')
      .Hidden('pkgaTypNm', '시험규격IDX')
      .Hidden('sapPrdha', '자재 계층 코드')
      .build(),
};

const searchForm = {
  static: {
    $grid: null,
    props: { editable: false },
    countPerRow: 2,
    buttons: [
      { name: 'select', label: '선택' },
      { name: 'search', label: '조회' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Input('sapPrdha', '자재 계층 코드', { maxLength: 80 })
      .Select('pkgaDiv', '자재 유형', {
        async: () => api.combo.systemCommon.getPackageDivCombo(),
      })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('pkgaTypNm', '자재 유형')
      .col('sapPrdha', '자재 계층 코드')
      .col('specProcCdNm', '진행상태')
      .col('delYn', '삭제 여부')
      .col('udtUserNm', '최종 수정자')
      .col('plntCd', '플랜트 코드', false)
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

export default {
  valueWithWrapInfo,
  searchForm,
};

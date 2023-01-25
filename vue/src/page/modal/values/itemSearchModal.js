import { ColumnBuilder, FormBuilder } from '@/util/builder';

const searchForm = {
  static: {
    $grid: null,
    props: {},
    countPerRow: 2,
    buttons: [{ name: 'select', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Select('itemDiv', '품목구분')
      .Input('itemNm', '품목명')
      .Input('itemCd', '품목코드')
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('id', false)
      .col('itemDiv', '품목구분')
      .col('itemCd', '품목코드')
      .col('processNm', '공정명')
      .col('version', '버전')
      .col('itemNm', '품목명')
      .col('englishItemNm', '품목영문명')
      .col('formDiv', '제형구분')
      .col('workplace', '작업실')
      .col('roomNo', '룸번호')
      .col('cleanliness', '청정도')
      .col('pointNo', 'PointNo.')
      .col('turn', '순번')
      .col('inspectionCycle', '검사주기')
      .col('strDt', '시작일')
      .col('periodUse', '사용기간')
      .col('sampleCollector', '검체채취자')
      .col('totalSampleAmount', '총검체량')
      .col('totalSampleUnit', '총검체단위')
      .col('harvestsNum', '검체채취수량')
      .col('samplingUnit', '검체채취단위')
      .col('storageSampleAmount', '보관검체수량')
      .col('storageSampleUnit', '보관검체단위')
      .col('referenceSampleAmount', '참조검체수량')
      .col('referenceSampleUnit', '참조검체단위')
      .col('sampleCollectionSite', '검체채취장소')
      .col('sampleCollectionMethod', '검체채취방법')
      .col('imsStorageMethod', 'IMS저장방법')
      .col('sampleStorageMethod', '검체보관방법')
      .col('sampleStoragePlace', '검체보관장소')
      .col('manufactureQuantity', '제조수량')
      .col('manufactureQuantityUnit', '제조단위')
      .col('standardAnalysisTime', '표준분석시간')
      .col('testTime', '시험시간(s)')
      .col('productStatus', '제품상태')
      .col('manager', '담당자')
      .col('revisionNum', '개정번호')
      .col('revisionDate', '개정일자')
      .col('reviser', '개정자')
      .col('revisionRea', '개정사유')
      .col('approvalDiv', '승인여부')
      .col('itemDivCd', '품목구분코드')
      .col('processCd', '공정코드')
      .col('formDivCd', '제향구분코드')
      .col('collectionMethodCd', '채취방법코드')
      .col('collectionDeviceCd', '채취기구코드')
      .col('collectionSiteCd', '채취장소코드')
      .col('storageSampleUnitCd', '보관검체단위코드')
      .col('manufacturUnitCd', '제조단위코드')
      .col('managerId', '담당자아이디')
      .col('approvalDiv', '승인구분코드')
      .col('storagePlaceCd', '보관장소코드')
      .col('storageMethodCd', '보관방법코드')
      .col('samplingUnitCd', '채취단위코드')
      .col('productStatusCd', '제품상태코드')
      .col('sampleCollectorId', '검체채취자아이디')
      .col('manufacturerCd', '제조처코드')
      .col('supplierCd', '공급처코드')
      .col('processCd', '프로세스')
      .col('permitStandards', '허가규격')
      .col('packingQuantity', '포장수량')
      .col('packingQuantityUnit', '포장수량단위')
      .col('packingQuantityUnitCd', '포장단위수량코드')
      .col('containersNo', '용기수')
      .build(),
};

export default {
  searchForm,
};

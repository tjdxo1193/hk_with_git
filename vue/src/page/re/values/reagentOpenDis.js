import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [
      { name: 'fileSave', label: '파일등록', disabled: true },
      { name: 'openItem', label: '개봉' },
      { name: 'disposalItem', label: '폐기요청', disabled: true },
      { name: 'search', label: '조회' },
    ],
    props: {
      showRowCheckColumn: true,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperRitmTreeCd', '구분', {
        async: () => api.combo.common.getTestMaterialTreeCd('R1000000'),
      })
      .Input('ritmKn', '자재내역')
      .Input('mngNo', '관리번호')
      .Input('makNo', '제조번호')
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .Input('vdrNm', '제조처')
      .DatepickerTwinWithSwitch('searchEtrDt', '입고일', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .RadioGroup('mngProcCd', '진행구분', {
        value: 'S0020100',
        groups: [
          { checkedValue: 'S0020100', label: '개봉' },
          { checkedValue: 'S0020200', label: '폐기요청' },
        ],
        gap: 50,
      })
      .blank()
      .spanCol(3)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmCd', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '자재내역')
      .col('mngProcCd', false)
      .col('mngProcNm', '진행단계')
      .calendar('opnDt', '개봉일')
      .calendar('expirDt', '개봉 후 사용기한')
      .col('opnRmk', '개봉비고', {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 50,
        },
      })
      .col('mngNo', '관리번호')
      .col('dpsRea', '폐기사유', false, {
        editRenderer: {
          type: 'InputEditRenderer',
          maxlength: 50,
        },
      })
      .col('ritmEtrNo', '입고번호')
      .col('casNo', 'CASNO')
      .col('ritmLabelNo', '라벨번호')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('makNo', '제조번호')
      .col('makDt', '제조일')
      .col('etrDt', '입고일')
      .col('expirDtChk', false)
      .col('opnBefExpirDt', '개봉 전 사용기한')
      .col('opnAftExpirTrm', '개봉 후 사용기간')
      .col('strgTerms', false)
      .col('strgTermsNm', '보관조건')
      .col('strgPla', false)
      .col('strgPlaNm', '보관장소')
      .col('strgPlaDtl', '세부보관장소')
      .col('fomu', '화학식')
      .col('regRnk', '등급')
      .col('regVol', '용량')
      .col('regVolUnitCd', false)
      .col('regVolUnitNm', '용량단위')
      .col('regCtet', '순도(함량)')
      .col('regCtetUnitCd', false)
      .col('regCtetUnitNm', '순도(함량)단위')
      .col('ritmCrgUid', false)
      .col('ritmCrgNm', '담당자')
      .col('etrEachQty', '개별수량')
      .col('ritmUnitNm', '개별수량단위')
      .col('etrTotQty', '총수량')
      .col('ritmUnitNm', '총수량단위')
      .col('sfyStok', '안전재고량')
      .col('ritmUnitNm', '안전재고단위')
      .col('regFact', 'Factor')
      .col('etrRmk', '비고')
      .col('rjtRea', '반려사유')
      .col('fileIdx', false)
      .build(),
};

export default {
  list,
};

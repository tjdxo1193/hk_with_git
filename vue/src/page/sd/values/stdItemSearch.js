import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [
      { name: 'init', label: '초기화' },
      { name: 'search', label: '조회' },
    ],
    $grid: null,
    legends: [
      { value: '유효기간(한달)', className: 'expirationPeriod' },
      { value: '안전재고량이하', className: 'lessThanStock' },
    ],
    props: {
      editable: false,
      rowStyleFunction: function (rowIndex, item) {
        const oneMonthAgo = dayjs().add(-1, 'M').format('YYYY-MM-DD');
        if (item.opnBefExpirDt < oneMonthAgo) {
          return 'expirationPeriod';
        }
        if (item.sfyStok != null && item.sfyStok >= item.leftOverStok) {
          return 'lessThanStock';
        }
        return null;
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('upperRitmTreeCd', '구분', {
        async: () => api.combo.common.getTestMaterialTreeCd('R3000000'),
      })
      .Input('ritmKn', '품명')
      .Input('mngNo', '관리번호')
      .Input('vdrNm', '제조처')
      .Select('ritmTreeCd', '재료구분', {
        async: api.combo.common.getTreeCd,
      })
      .Select('', '조회일자구분')
      .DatepickerTwinWithSwitch('', '조회기한', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .DatepickerTwinWithSwitch('expirDt', '사용(유효)기한', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .Select('state', '상태')
      .blank()
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('fileIdx', false)
      .button('fileAttacher', '첨부파일')
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '품명')
      .col('mngNo', '관리번호')
      .col('leftOverStok', '현재고량')
      .col('openedStok', '현개봉량')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('makNo', '제조번호')
      .col('makDt', '제조일')
      .col('etrDt', '입고일')
      .col('opnBefExpirDt', '사용(유효)기한')
      .col('currLotYn', 'Current LOT')
      .col('strgTerms', false)
      .col('strgTermsNm', '보관조건')
      .col('strgPla', false)
      .col('strgPlaNm', '보관장소')
      .col('strgPlaDtl', '세부보관장소')
      .col('makNo', '제조번호')
      .col('spec', '규격')
      .col('stdMois', '수분')
      .col('stdCtetCd', '함량종류')
      .col('stdCtetVal', '함량')
      .col('ritmUnitCd', false)
      .col('ritmUnitNm', '함량단위')
      .col('stdAsisCtetNm', '함량(As is)종류')
      .col('stdAsisCtetVal', '함량(As is)')
      .col('ritmUnitNm', '함량(As is) 단위')
      .col('etrQty', '용기수')
      .col('etrEachQty', '용량')
      .col('ritmUnitNm', '용량단위')
      .col('etrTotQty', '총수량')
      .col('ritmUnitNm', '총수량단위')
      .col('sfyStok', '안전재고량')
      .col('ritmUnitNm', '안전재고단위')
      .col('ritmCrgUid', false)
      .col('ritmCrgNm', '담당자')
      .col('etrProcCd', false)
      .col('etrProcNm', '진행상태')
      .col('shadeYn', '차광여부')
      .col('mtrDedutYn', '물질차감여부')
      .col('etrRmk', '비고')
      .col('stdNb', '주의사항')
      .build(),
};

const detailList = {
  static: {
    $grid: null,
    props: {
      editable: false,
    },
  },
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmMngIdx', false)
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('etrProcCd', false)
      .col('mngProcCd', false)
      .col('mngProcNm', '진행상태')
      .col('ritmKn', '품명')
      .col('opnDt', '개봉일')
      .col('expirDt', '사용(유효)기간')
      .col('dpsDt', '폐기일')
      .col('dpsRea', '폐기사유')
      .col('mngNo', '관리번호')
      .col('ritmEtrNo', '입고번호')
      .col('ritmLabelNo', '라벨번호')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('makNo', '제조번호')
      .col('makDt', '제조일')
      .col('etrDt', '입고일')
      .col('currLotYn', 'Current LOT')
      .col('makNo', '제조번호')
      .col('spec', '규격')
      .col('stdMois', '수분')
      .col('stdCtetCd', '함량종류')
      .col('stdCtetVal', '함량')
      .col('ritmUnitCd', false)
      .col('ritmUnitNm', '함량단위')
      .col('stdAsisCtetNm', '함량(As is)종류')
      .col('stdAsisCtetVal', '함량(As is)')
      .col('ritmUnitNm', '함량(As is) 단위')
      .col('etrQty', '용기수')
      .col('etrEachQty', '용량')
      .col('ritmUnitNm', '용량단위')
      .col('etrTotQty', '총수량')
      .col('ritmUnitNm', '총수량단위')
      .col('sfyStok', '안전재고량')
      .col('ritmUnitNm', '안전재고단위')
      .col('strgTerms', false)
      .col('strgTermsNm', '보관조건')
      .col('strgPla', false)
      .col('strgPlaNm', '보관장소')
      .col('etrRmk', '비고')
      .col('opnRmk', '개봉비고')
      .build(),
};

const usageList = {
  static: {
    title: '사용내역조회',
    countPerRow: 3,
    $grid: null,
    buttons: [
      { name: 'usageInit', label: '초기화' },
      { name: 'usageSearch', label: '조회' },
    ],
    props: {
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select('delYn', '사용유무', {
        elements: [
          { value: 'N', label: '사용' },
          { value: 'Y', label: '삭제' },
        ],
      })
      .blank()
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ritmEtrIdx', false)
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('etrEachQty', '기본수량(용량)')
      .col('useQty', '사용량')
      .col('leftOverStok', '용기별재고량')
      .col('ritmUnitCd', false)
      .col('ritmUnitNm', '단위')
      .col('pitmNm', '품목명')
      .col('ansNo', '시험번호')
      .col('makNo', '제조번호')
      .col('aitmNm', '항목명')
      .col('useUid', false)
      .col('useNm', '사용자')
      .col('useDt', '사용일')
      .col('usePps', '사용목적')
      .col('useRmk', '비고')
      .build(),
};

export default {
  list,
  detailList,
  usageList,
};

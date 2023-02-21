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
    legends: [{ value: '안전재고량이하', className: 'lessThanStock' }],
    props: {
      editable: false,
      rowStyleFunction: function (rowIndex, item) {
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
        async: () => api.combo.common.getTestMaterialTreeCd('R1000000'),
      })
      .Input('ritmKn', '자재내역')
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
      .col('ritmKn', '자재내역')
      .col('ritmCrgUid', false)
      .col('ritmCrgNm', '담당자')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('sfyStok', '안전재고량')
      .col('ritmUnitNm', '안전재고단위')
      .col('leftOverStok', '총재고량')
      .col('openedStok', '총개봉량')
      .col('disposedStok', '총폐기량')
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
      .col('ritmKn', '자재내역')
      .col('opnDt', '개봉일')
      .col('expirDt', '사용(유효)기간')
      .col('dpsDt', '폐기일')
      .col('dpsRea', '폐기사유')
      .col('mngNo', '관리번호')
      .col('casNo', 'CASNO')
      .col('ritmEtrNo', '입고번호')
      .col('ritmLabelNo', '라벨번호')
      .col('vdrCd', false)
      .col('vdrNm', '제조처/공급처', { width: 100 })
      .col('makNo', '제조번호')
      .col('makDt', '제조일')
      .col('etrDt', '입고일')
      .col('currLotYn', 'Current LOT')
      .col('makNo', '제조번호')
      .col('fomu', '화학식')
      .col('regRnk', '등급')
      .col('ritmUnitCd', false)
      .col('regVol', '용량')
      .col('regVolUnitCd', false)
      .col('regVolUnitNm', '용량단위')
      .col('regCtet', '순도(함량)')
      .col('regCtetUnitCd', false)
      .col('regCtetUnitNm', '순도(함량)단위')
      .col('etrQty', '용기수')
      .col('ritmCrgUid', false)
      .col('ritmCrgNm', '담당자')
      .col('etrTotQty', '총수량')
      .col('ritmUnitNm', '총수량단위')
      .col('sfyStok', '안전재고량')
      .col('ritmUnitNm', '안전재고단위')
      .col('regFact', 'Factor')
      .col('etrRmk', '비고')
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
      .col('pitmNm', '자재내역')
      .col('ansNo', '시험번호')
      .col('makNo', '제조번호')
      .col('aitmNm', '항목명')
      .col('useUid', false)
      .col('useNm', '사용자')
      .col('useDt', '사용일')
      .col('usePps', '사용목적')
      .col('useRmk', '수정사유')
      .build(),
};

export default {
  list,
  detailList,
  usageList,
};

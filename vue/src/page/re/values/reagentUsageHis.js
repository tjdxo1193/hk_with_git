import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const list = {
  static: {
    title: '조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    $grid: null,
    legends: [{ value: '폐기', className: 'disposal' }],
    props: {
      editable: false,
      rowStyleFunction: function (rowIndex, item) {
        if (item.mngProcCd === 'S0020300') {
          return 'disposal';
        }
        return null;
      },
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('ritmKn', '자재내역')
      .Input('mngNo', '관리번호')
      .Input('makNo', '제조번호')
      .Input('vdrNm', '제조처')
      .CheckboxGroup('mngProcCd', '진행구분', {
        groups: [{ checkedValue: 'S0020300', label: '폐기포함' }],
        gap: 60,
      })
      .DatepickerTwinWithSwitch('searchUseDt', '사용일', {
        value: [todayDate, todayDate],
      })
      .spanCol(2)
      .blank()
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('useSeq', false)
      .col('ritmMngIdx', false)
      .col('ritmEtrIdx', false)
      .col('ritmCd', '재료일련번호', false)
      .col('upperRitmTreeCd', false)
      .col('upperRitmTreeNm', '구분', { width: 150 })
      .col('ritmRootCd', false)
      .col('ritmRootNm', '재료구분')
      .col('ritmKn', '자재내역')
      .col('mngNo', '관리번호')
      .col('ritmEtrNo', '입고번호')
      .col('ritmLabelNo', '라벨번호')
      .col('useDt', '사용일')
      .col('useUid', false)
      .col('useNm', '사용자')
      .col('useQty', '사용량')
      .col('ritmUnitCd', false)
      .col('ritmUnitNm', '단위')
      .col('etrRmk', '비고')
      .col('regCtet', '함량')
      .col('regCtetUnitCd', false)
      .col('regCtetUnitNm', '함량단위')
      .col('regVol', '용량')
      .col('regVolUnitCd', false)
      .col('regVolUnitNm', '용량단위')
      .col('etrProcCd', false)
      .col('mngProcCd', false)
      .col('mngProcNm', '진행상태')
      .col('sfyStok', '안전재고량')
      .col('etrEachQty', '용기별재고량')
      .col('etrTotQty', '총수량')
      .col('pitmNm', '자재내역')
      .col('ansNo', '시험번호')
      .col('casNo', 'CASNO')
      .col('vdrCd', false)
      .col('vdrNm', '제조처')
      .col('makNo', '제조번호')
      .col('ritmCrgUid', false)
      .col('ritmCrgNm', '담당자')
      .col('aitmNm', '항목명')
      .col('usePps', '사용목적')
      .col('expirDt', '사용(유효)기간')
      .col('currLotYn', 'Current LOT')
      .col('stdNb', '주의사항')
      .build(),
};

const detail = {
  static: {
    title: '정보등록',
    countPerRow: 4,
    buttons: [
      { name: 'fileSave', label: '파일등록', disabled: true },
      { name: 'use', label: '사용' },
      { name: 'update', label: '수정', disabled: true },
      { name: 'delete', label: '삭제', disabled: true },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('useSeq')
      .Hidden('ritmMngIdx')
      .Hidden('ritmEtrIdx')
      .Hidden('fileIdx', { value: 0 })
      .Input('mngNo', '관리번호', { disabled: true, maxLength: 20 })
      .required()
      .Input('pitmNm', '자재내역', { maxLength: 20 })
      .Input('ansNo', '시험번호', { maxLength: 20 })
      .Input('makNo', '제조번호', { maxLength: 20 })
      .multiple(
        'ritmRootMulti',
        '시약코드',
        FormBuilder.builder()
          .Input('ritmRootCd', { disabled: true })
          .required()
          .spanCol(5)
          .Button('openItemSearch', 'testMaterialOpenItemModal', { type: 'search' })
          .build(),
      )
      .Input('aitmNm', '항목명', { maxLength: 20 })
      .Hidden('useUid')
      .Input('useNm', '사용자', { disabled: true })
      .Datepicker('useDt', '사용일', { value: [todayDate], disabled: true })
      .Input('ritmKn', '자재내역', { disabled: true })
      .required()
      .Input('leftOverStok', '재고량', { readonly: true })
      .required()
      .Hidden('compareUseQty')
      .Input('useQty', '사용량', { maxLength: 6 })
      .validator((value) => value > 0 || value === '')
      .required()
      .Hidden('ritmUnitCd')
      .Input('ritmUnitNm', '단위', { disabled: true })
      .Textarea('stdNb', '주의사항', { rows: 2, disabled: true })
      .spanCol(4)
      .Textarea('usePps', '사용목적', { rows: 2, maxLength: 50 })
      .spanCol(4)
      .Textarea('useRmk', '비고', { rows: 2, maxLength: 500 })
      .spanCol(4)
      .build(),
};

export default {
  list,
  detail,
};

import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const tomorrowDate = dayjs().add(1, 'day').format('YYYY-MM-DD');

const searchGridWithForm = {
  static: {
    title: '조회',
    countPerRow: 4,
    $grid: null,
    buttons: [{ name: 'search', label: '조회' }],
    props: {
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('eqmCd', '기기코드')
      .Input('eqmNm', '기기명')
      .Input('makComp', '제조사')
      .Input('pitmNm', '품목명')
      .Input('crgNm', '담당자')
      .Input('splComp', '공급회사')
      .Input('srlNo', 'S/N')
      .DatepickerTwinWithSwitch('useDsList', '사용일자', { value: [todayDate, tomorrowDate] })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntCd', { visible: false })
      .col('eqmCd', '기기코드')
      .col('eqmNm', '기기명')
      .col('useSeq', '사용순번')
      .col('useStrDs', '사용시작일시')
      .col('useEndDs', '사용종료일시')
      .col('pitmNm', '품목명')
      .col('crgNm', '기기담당자')
      .col('ansNo', '시험번호')
      .col('batchNo', '배치번호')
      .col('aitmNm', '시험항목명')
      .col('ansUid', { visible: false })
      .col('ansNm', '시험자')
      .col('eqmFileIdx', { visible: false })
      .col('rmk', { visible: false })
      .build(),
};

const registerForm = {
  static: {
    title: '정보등록',
    countPerRow: 4,
    buttons: [
      { name: 'excel', label: 'Excel' },
      { name: 'saveFile', label: '파일등록', disabled: true },
      { name: 'save', label: '등록' },
      { name: 'update', label: '수정', disabled: true },
      { name: 'delete', label: '삭제', disabled: true },
      { name: 'init', label: '초기화' },
    ],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('useSeq')
      .multiple(
        'eqm',
        '기기명',
        FormBuilder.builder()
          .Input('eqmNm', { readonly: true })
          .required()
          .Button('instrumentSearchModal', 'instrumentSearchModal', { type: 'search' })
          .build(),
      )
      .Input('eqmCd', '기기코드')
      .readonly()
      .required()
      .Input('makComp', '제조사')
      .readonly()
      .Input('splComp', '공급회사')
      .readonly()
      .Hidden('crgUid')
      .Input('crgNm', '담당자')
      .readonly()
      .Input('pitmNm', '품목명')
      .Input('ansNo', '시험번호')
      .Input('aitmNm', '시험항목')
      .DatepickerTwinWithSwitch('useDsList', '사용일자', { value: [todayDate, tomorrowDate] })
      .spanCol(2)
      .Input('ansNm', '시험자')
      .readonly()
      .Hidden('ansUid')
      .Textarea('rmk', '비고')
      .Hidden('eqmFileIdx')
      .build(),
};

export default {
  searchGridWithForm,
  registerForm,
};

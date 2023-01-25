import { FormBuilder } from '@/util';

const list = {
  static: {
    countPerRow: 2,
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('plntCd')
      .Hidden('ansIdx')
      .Hidden('rstSeq')
      .Hidden('batchNo')
      .Hidden('amitmCd')
      .Input('ansNo', '시험번호', { readonly: true })
      .required()
      .Input('lotNo', '제조번호', { readonly: true })
      .multiple(
        'pitmInfo',
        '품목정보',
        FormBuilder.builder()
          .Hidden('pitmTyp')
          .Input('pitmCd', { readonly: true })
          .Input('pitmNm', { readonly: true })
          .build(),
      )
      .required()
      .Input('aitmKn', '시험항목', { readonly: true })
      .required()
      .Datepicker('ocrDt', '발생일', { readonly: true })
      .Hidden('ansUid')
      .Input('ansNm', '시험자', { readonly: true })
      .Textarea('crtvMsr', '상세의견 및 조치사항', { rows: 2, maxLength: 100 })
      .required()
      .spanCol(2)
      .build(),
};

const buttonGroups = {
  buttons: [
    { name: 'save', label: '발생' },
    { name: 'cancel', label: '취소' },
  ],
};

export default {
  list,
  buttonGroups,
};

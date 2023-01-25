import dayjs from 'dayjs';

import { ColumnBuilder, FormBuilder } from '@/util';

const todayDate = dayjs().format('YYYY-MM-DD');

const AweekAgo = dayjs().add(-7, 'day').format('YYYY-MM-DD');

const searchGridWithForm = {
  static: {
    title: '로그인 이력 조회',
    countPerRow: 4,
    buttons: [{ name: 'search', label: '조회' }],
    height: '500px',
    props: {
      editable: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Input('plntNm', '플랜트 명')
      .Input('userNm', '접속자 명')
      .Input('acssUid', '접속자 ID')
      .DatepickerTwin('acssDsList', '접속일자', { value: [AweekAgo, todayDate] })
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('plntNm', '플랜트 명')
      .col('acssDiv', '접속구분')
      .col('userNm', '접속자')
      .col('acssUid', '접속자UID')
      .col('acssDs', '접속일시')
      .col('acssIp', '접속 IP')
      .build(),
};

export default {
  searchGridWithForm,
};

import dayjs from 'dayjs';

import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util/builder';

const todayDate = dayjs().format('YYYY-MM-DD');
const monthAgoDate = dayjs().add(-1, 'M').format('YYYY-MM-DD');

const searchForm = {
  static: {
    $grid: null,
    props: {},
    countPerRow: 4,
    buttons: [{ name: 'select', label: '조회' }],
  },
  forms: () =>
    FormBuilder.builder()
      .Hidden('amitmCd')
      .Hidden('vriaNo')
      .Hidden('aitmCd')
      .Hidden('ansProcCd')
      .Select('rstJdg', '판정', {
        async: () => api.combo.systemCommon.getRstJdgCombo(),
      })
      .spanCol(2)
      .DatepickerTwin('searchRcpDt', '접수일', {
        value: [monthAgoDate, todayDate],
      })
      .spanCol(2)
      .build(),
  columns: () =>
    ColumnBuilder.builder()
      .col('ansNo', false)
      .col('aitmCd', false)
      .col('aitmKn', '항목명', { width: 150 })
      .col('vriaNo', 'VARIANT NO', { width: 100 })
      .col('vriaKn', 'VARIANT 국문', { width: 100 })
      .col('ansDptCd', false)
      .col('ansDptNm', '시험파트')
      .col('ansUid', false)
      .col('ansNm', '시험자')
      .col('specTyp', false)
      .col('specTypNm', '규격유형')
      .col('jdgTyp', false)
      .col('jdgTypNm', '판정유형', { width: 100 })
      .col('rstVal', '결과값')
      .col('markVal', '표기값')
      .col('rstJdg', false)
      .col('rstJdgNm', '결과판정')
      .col('rstRmk', '결과비고')
      .col('rstUnitCd', false)
      .col('rstUnitNm', '결과단위')
      .col('rstDpnt', '결과소수점')
      .col('rptPrtYn', '성적서출력여부', { width: 100 })
      .col('rptPrtItmNm', '성적서출력항목명', { width: 110 })
      .col('rptPrtSlvVal', '성적서출력기준값', { width: 110 })
      .col('ansRstInpDs', '결과입력일시', { width: 150 })
      .build(),
};

export default {
  searchForm,
};

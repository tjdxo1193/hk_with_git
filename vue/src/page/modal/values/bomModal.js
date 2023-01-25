import { ColumnBuilder, FormBuilder } from '@/util';

const bomGridWithForm = {
  static: {
    buttons: [{ name: 'search', label: '조회' }],
    countPerRow: 2,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () => FormBuilder.builder().Input('posnr', '품목').Input('maktx', '자재내역').build(),
  columns: () =>
    ColumnBuilder.builder()
      // .col('posnr', { visible: false })
      .col('posnr', '품목')
      .col('stlal', '대체 BOM')
      .col('werks', '플랜트')
      .col('matnr', '자재 번호')
      .col('maktx', '자재내역')
      .col('mtart', '자재유형')
      .col('name1', '플랜트내역1')
      .col('dispo', 'MRP 관리자')
      .col('dsnam', 'MRP 관리자 내역')
      .col('stktx', '대체 BOM 내역')
      .col('bmeng', '기준수량')
      .col('bmein', '단위')
      .col('validFrom', '효력시작일')
      .col('validTo', '효력종료일')
      .col('stlnr', 'BOM No')
      .col('ztext', 'BOM 내역')
      .col('lkenz', 'BOM 삭제지시자')
      .col('postp', '품목 범주')
      .col('ptext', '품목 범주 텍스트')
      .col('idnrk', '구성 품목')
      .col('idnrkMakt', '구성 품목 내역')
      .col('potx1', '구성 품목 텍스트')
      .col('menge', '구성 품목 수량')
      .col('meins', '구성 품목 단위')
      .col('fmnge', '고정 수량')
      .col('beikz', '자재 공급 지시자')
      .col('crtDs', '수신일시')
      .col('udtDs', '수정일시')

      .build(),
};

const modalButtons = {
  buttons: [{ name: 'close', label: '닫기', type: 'normal' }],
};

export default {
  bomGridWithForm,
  modalButtons,
};

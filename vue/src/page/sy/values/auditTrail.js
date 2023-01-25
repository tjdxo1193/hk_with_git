import api from '@/api';
import { ColumnBuilder, FormBuilder } from '@/util';

const audit = {
  static: {
    title: 'inquireData',
    countPerRow: 4,
    buttons: [{ name: 'search', label: 'inquireData' }],
    $grid: null,
    props: {
      editable: false,
      showRowCheckColumn: false,
    },
  },
  forms: () =>
    FormBuilder.builder()
      .Select(1, 'auditDiv', {
        defaultOption: {
          label: 'select',
        },
        async: api.combo.getUpperMenus,
      })
      .required()
      .Select(3, 'auditDetailDiv', {
        defaultOption: {
          label: 'select',
        },
      })
      .required()
      .Input(4, 'auditTargetIP')
      .DatepickerTwinWithSwitch(2, 'auditTargetDate')
      .build(),
  columns: () => ColumnBuilder.builder().build(),
};

export default {
  audit,
};

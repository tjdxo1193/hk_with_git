<template>
  <AUIGridSearch
    v-bind="list"
    :columns="computedListColumns"
    @button-click="onListButtonClick"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="listFormEvent"
  />
  <AUIGridSearch
    v-bind="detail"
    :columns="computedDetailColumns"
    @button-click="onDetailButtonClick"
    @grid-created="(proxy) => $setState('detail.$grid', proxy)"
    @form-event="detailFormEvent"
  />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/commonCodeManage';

export default {
  name: 'CommonCodeManage',
  props: {
    mode: {
      type: String,
      default: 'user',
      description: 'Code that classifies commonCode and systemCommonCode',
    },
  },
  mounted() {
    this.getUpperCmmnCode();
  },
  data() {
    const { list, detail } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: () => {
            this.getDetailCmmnCode();
          },
          cellEditBegin: (e) => {
            return this.isEditableColumns(e);
          },
        },
      },
      detail: {
        ...detail.static,
        forms: detail.forms(),
        columns: detail.columns(),
      },
    };
  },
  methods: {
    async getUpperCmmnCode() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);
      if (this.$props.mode === 'user') {
        parameter.cdDivSu = 'U';
      }
      if (this.$props.mode === 'system') {
        parameter.cdDivSu = 'S';
      }

      const data = await $grid
        ._useLoader(() => this.$axios.get('/sy/commonCodeManage/upperCode', parameter))
        .then(({ data }) => data);

      this.list.$grid.setGridData(data);
      this.detailGridClear();
    },
    getDetailCmmnCode() {
      const hirCd = this.getHirCd();
      if (!hirCd) return;
      const { $grid, forms } = this.detail;
      const parameter = FormUtil.getData(forms);
      parameter.hirCd = hirCd;

      $grid
        ._useLoader(() => this.$axios.get('/sy/commonCodeManage/detailCode', parameter))
        .then(({ data }) => {
          this.detail.$grid.setGridData(data);
          this.setEnabledButtonState();
        });
    },
    listFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getUpperCmmnCode();
      }
    },
    detailFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getDetailCmmnCode();
      }
    },
    onListButtonClick({ name }) {
      if (name === 'search') {
        this.getUpperCmmnCode();
      }
      if (name === 'add') {
        if (this.isUserMode()) {
          this.list.$grid.addRow({ cdDivSu: 'U' });
        }
        if (this.isSystemMode()) {
          this.list.$grid.addRow({});
        }
        this.detailGridClear();
      }
      if (name === 'save') {
        this.save();
      }
    },
    onDetailButtonClick({ name }) {
      if (name === 'search') {
        this.getDetailCmmnCode();
      }
      if (name === 'add') {
        const hirCd = this.getHirCd();

        const { $grid } = this.detail;
        $grid.addRow({ hirCd: hirCd });
      }
      if (name === 'save') {
        this.detailSave();
      }
    },
    listGridDataValidate($grid) {
      const isValid = $grid.validateGridData(
        ['cdDivSu', 'hirCdNm', 'useYn', 'hirCdOrd'],
        '필수 입력입니다.',
      );
      return isValid;
    },
    save() {
      const { $grid } = this.list;
      const isValid = this.listGridDataValidate($grid);

      if (isValid) {
        this.isUpdate($grid) ? this.codeUpdate() : this.codeInsert();
      }
    },
    detailGridDataValidate($grid) {
      const isValid = $grid.validateGridData(['dtlNm', 'useYn', 'dtlCdOrd'], '필수 입력입니다.');
      return isValid;
    },
    detailSave() {
      const { $grid } = this.detail;
      const isValid = this.detailGridDataValidate($grid);

      if (isValid) {
        this.isUpdate($grid) ? this.detailCodeUpdate() : this.detailCodeInsert();
      }
    },
    isUpdate(pid) {
      const editedRowItems = pid.getEditedRowItems();
      return editedRowItems.length > 0 ? true : false;
    },
    isUserMode() {
      return this.$props.mode === 'user';
    },
    isSystemMode() {
      return this.$props.mode === 'system';
    },
    codeInsert() {
      const parameter = this.getParameterOfAddEditGridData(this.list.$grid);

      this.$eSign(() => this.$axios.post('/sy/commonCodeManage/upperCode', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.getUpperCmmnCode();
          this.init();
        })
        .catch(() => this.$error(this.$message.error.createData));
    },
    codeUpdate() {
      const parameter = this.getParameterOfAddEditGridData(this.list.$grid);

      this.$eSignWithReason(() =>
        this.$axios
          .post('/sy/commonCodeManage/upperCode', parameter)
          .then(() => {
            this.$info(this.$message.info.saved);
            this.getUpperCmmnCode();
            this.init();
          })
          .catch(() => this.$error(this.$message.error.updateData)),
      );
    },
    detailCodeInsert() {
      const parameter = this.getParameterOfAddEditGridData(this.detail.$grid);

      this.$eSign(() => this.$axios.post('/sy/commonCodeManage/detailCode', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.getDetailCmmnCode();
        })
        .catch(() => this.$error(this.$message.error.createData));
    },
    detailCodeUpdate() {
      const parameter = this.getParameterOfAddEditGridData(this.detail.$grid);

      this.$eSignWithReason(() =>
        this.$axios
          .post('/sy/commonCodeManage/detailCode', parameter)
          .then(() => {
            this.$info(this.$message.info.saved);
            this.getDetailCmmnCode();
          })
          .catch(() => this.$error(this.$message.error.updateData)),
      );
    },
    setDisabledButtonState() {
      FormUtil.disableButtons(this.detail.buttons, ['search', 'add', 'save']);
    },
    setEnabledButtonState() {
      FormUtil.enableButtons(this.detail.buttons, ['search', 'add', 'save']);
    },
    init() {
      this.list.forms = values.list.forms();
      this.detail.$grid.clearGridData();
      this.detail.forms = values.detail.forms();
    },
    detailGridClear() {
      this.detail.$grid.clearGridData();
      this.setDisabledButtonState();
    },
    getHirCd() {
      const selectedItem = this.list.$grid.getSelectedItems();
      return selectedItem[0].item.hirCd;
    },
    getParameterOfAddEditGridData(pid) {
      const addedRowItems = pid.getAddedRowItems();
      const editedRowItems = pid.getEditedRowItems();

      return {
        addedRowItems: addedRowItems,
        editedRowItems: editedRowItems,
      };
    },
    isEditableColumns(event) {
      const hirCd = event.item.hirCd;
      const editableColumns = ['hirCdNm', 'hirDesc', 'useYn', 'hirCdOrd'];
      const isEditable = editableColumns.includes(event.dataField);

      return isEditable || hirCd == null ? true : false;
    },
  },
  computed: {
    computedListColumns() {
      return this.isUserMode()
        ? this.list.columns
        : this.list.columns.map((column) =>
            column.dataField === 'cdDivSu' ? { ...column, editable: true } : column,
          );
    },
    computedDetailColumns() {
      return this.detail.columns.map((column) =>
        column.dataField === 'dtlCd' ? { ...column, editable: false } : column,
      );
    },
  },
};
</script>

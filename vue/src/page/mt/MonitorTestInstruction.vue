<template>
  <AUIGridSearch
    v-bind="list"
    :columns="computedListColumns"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <AUIGridWithHeader
    v-bind="itemList"
    @grid-created="(proxy) => $setState('itemList.$grid', proxy)"
    @button-click="onClickButton"
  />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/monitorTestInstruction';

export default {
  name: 'MonitorTestInstruction',
  components: {},
  mounted() {
    this.getMonitorTestInstruction();
  },
  data() {
    const { list, itemList } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getMonitorTestRst(e);
            this.enableButtons(['delete', 'init']);
          },
        },
      },
      itemList: {
        ...itemList.static,
        columns: itemList.columns(),
        event: {},
      },
    };
  },
  methods: {
    async getMonitorTestInstruction() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/mt/monitorTestInstruction', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    async setSelectByUpperCd(forms, upperCd, targetName) {
      let targetForm = FormUtil.findItem(forms, targetName);
      let result = await targetForm.async(upperCd).then(({ data }) => (result = data));
      const resultForm = forms.find((item) => item.name === targetName);
      resultForm.value = '';
      resultForm.elements = result;
    },
    getMonitorTestRst(event) {
      const parameter = event.item;
      this.itemList.$grid
        ._useLoader(() =>
          this.$axios.get('/mt/monitorTestInstruction/getMonitorTestRst', parameter),
        )
        .then(({ data }) => this.itemList.$grid.setGridData(data));
    },
    instruct() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      if (checkedRows.length > 0) {
        const parameter = checkedRows.map((row) => ({
          ...row.item,
        }));
        this.$eSignWithReason(() =>
          this.$axios.put('/mt/monitorTestInstruction/instruct', parameter),
        )
          .then(() => {
            this.$info(this.$message.info.saved);
            this.getMonitorTestInstruction();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      } else {
        this.$warn(this.$message.warn.unSelectedData);
      }
    },
    deleteResult(checkedRows) {
      const parameter = checkedRows.map((row) => ({
        ...row.item,
      }));
      this.$eSignWithReason(() =>
        this.$axios.put('/mt/monitorTestInstruction/deleteRst', parameter),
      )
        .then(() => {
          this.$info(this.$message.info.deleted);
          this.getMonitorTestRst(checkedRows[0]);
        })
        .catch(() => this.$error(this.$message.error.deleteData));
    },
    searchFormEvent(event) {
      const forms = this.list.forms;
      const value = event.item.value;
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getMonitorTestInstruction();
      }
      if (event.type === 'change' && event.item.name === 'mitmWrkStudioDiv') {
        this.setSelectByUpperCd(forms, value, 'upperMitmWrkPlcDiv');
      }
      if (event.type === 'change' && event.item.name === 'upperMitmWrkPlcDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmWrkPlcDiv');
      }
      if (event.type === 'change' && event.item.name === 'upperMitmPitmDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmPitmDiv');
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getMonitorTestInstruction();
      }
      if (name === 'instruct') {
        this.instruct();
      }
      if (name === 'init') {
        this.init();
      }
      if (name === 'delete') {
        const checkedRows = this.itemList.$grid.getCheckedRowItems();
        if (checkedRows.length > 0) {
          this.deleteResult(checkedRows);
        } else {
          this.$warn(this.$message.warn.unSelectedData);
        }
      }
    },
    init() {
      this.list.forms = values.list.forms();
      this.list.$grid.clearGridData();
      this.enableButtons(['delete', 'init']);
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.itemList.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.itemList.buttons, buttons);
    },
  },
  computed: {
    computedListColumns() {
      const editableColumns = ['cplRqmDt'];

      return this.list.columns.map((col) => ({
        ...col,
        editable: editableColumns.includes(col.dataField) ? true : false,
      }));
    },
  },
};
</script>

<style></style>

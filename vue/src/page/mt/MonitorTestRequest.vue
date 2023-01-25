<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="formEvent"
  />
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/monitorTestRequest';

export default {
  name: 'MonitorTestRequest',
  mounted() {
    this.getMonitorTestRequest();
  },
  data() {
    const { list } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
      },
    };
  },
  methods: {
    async getMonitorTestRequest() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/mt/monitorTestRequest', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    request() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const parameter = checkedRows.map((row) => row.item);
      if (checkedRows.length > 0) {
        this.$eSignWithReason(() => this.$axios.put('/mt/monitorTestRequest/request', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.getMonitorTestRequest();
          })
          .catch(() => {
            this.$error(this.$message.error.saveData);
          });
      } else {
        this.$warn(this.$message.warn.unSelectedData);
      }
    },
    requestCancel() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const parameter = checkedRows.map((row) => row.item);
      if (checkedRows.length > 0) {
        this.$eSignWithReason(() =>
          this.$axios.put('/mt/monitorTestRequest/requestCancel', parameter),
        )
          .then(() => {
            this.$info(this.$message.info.saved);
            this.getMonitorTestRequest();
          })
          .catch(() => {
            this.$error(this.$message.error.saveData);
          });
      } else {
        this.$warn(this.$message.warn.unSelectedData);
      }
    },
    formEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getMonitorTestRequest();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getMonitorTestRequest();
      }
      if (name === 'request') {
        this.request();
      }
      if (name === 'requestCancel') {
        this.requestCancel();
      }
    },
  },
};
</script>

<style></style>

<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />
  <Card>
    <TabBase v-bind="tabs">
      <template #tab-testInfo>
        <FormWithHeader v-bind="testInfo" />
        <Horizontal align-items="top" :spans="[5, 5]">
          <FormWithHeader v-bind="reportInfo" @button-click="onClickButton" />
          <AUIGridWithHeader 
            v-bind="reportHistoryGrid"
            @grid-created="(proxy) => $setState('reportHistoryGrid.$grid', proxy)"
          />
        </Horizontal>
      </template>
      <template #tab-itemInfo>
        <AUIGrid
          v-bind="testItemList"
          @grid-created="(proxy) => $setState('testItemList.$grid', proxy)"
        />
      </template>
    </TabBase>
  </Card>
</template>

<script>
import { FormUtil } from '@/util';

import values from './values/printIntegratedReport';

export default {
  name: 'PrintIntegrated',
  components: {},
  mounted() {
    this.getTestReportList();
  },
  data() {
    const { list, testInfo, reportInfo, testItemList, tabs, reportHistoryGrid } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.setDataToForms(e);
          },
        },
      },
      testInfo: {
        ...testInfo.static,
        forms: testInfo.forms(),
      },
      reportInfo: {
        ...reportInfo.static,
        forms: reportInfo.forms(),
      },
      testItemList: {
        ...testItemList.static,
        columns: testItemList.columns(),
      },
      tabs,
      reportHistoryGrid: {
        ...reportHistoryGrid.static,
        columns: reportHistoryGrid.columns(),
      },
    };
  },
  methods: {
    init() {
      const { $grid } = this.testItemList;
      this.testInfo.forms = values.testInfo.forms();
      $grid.clearGridData();
      this.disableButtons();
    },
    async getTestReportList() {
      const { forms, $grid } = this.list;
      const parameter = FormUtil.getData(forms);
      await $grid
        ._useLoader(() => this.$axios.get('/pr/printIntegratedReport', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    async getTestItmList(event) {
      const { $grid } = this.testItemList;
      const parameter = { ansIdx: event.item.ansIdx };
      await $grid
        ._useLoader(() => this.$axios.get('/pr/printIntegratedReport/testItem', parameter))
        .then(({ data }) => {
          this.testItemList.$grid.setGridData(data);
        });
    },
    async getReportHistory(event) {
      const { $grid } = this.reportHistoryGrid;
      const param = { ansIdx: event.item.ansIdx };
      await $grid
        ._useLoader(() => this.$axios.get('/pr/printIntegratedReport/reportHistory', param))
        .then(({ data }) => {
          this.reportHistoryGrid.$grid.setGridData(data);
        });
    },
    async save() {
      const parameter = FormUtil.getData(this.reportInfo.forms);
      await this.$eSignWithReason(() => this.$axios.post('/pr/printReport', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getTestReportList();
        })
        .ckatch(() => this.$error(this.$message.error.updateData));
    },
    setDataToForms(event) {
      FormUtil.setData(this.testInfo.forms, event.item);
      FormUtil.setData(this.reportInfo.forms, event.item);
      this.getTestItmList(event);
      this.getReportHistory(event);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getTestReportList();
      }
    },

    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
  },
};
</script>

<style></style>

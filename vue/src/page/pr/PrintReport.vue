<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />
  <Card>
    <TabBase v-bind="tabs" @common-button-click="onClickButton">
      <template #tab-testInfo>
        <FormWithHeader v-bind="testInfo" />
        <FormWithHeader v-bind="reportInfo" @button-click="onClickButton" />
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
import { FormUtil, RdUtil } from '@/util';

import values from './values/printReport';

export default {
  name: 'PrintReport',
  components: {},
  mounted() {
    this.getTestReportList();
  },
  data() {
    const { list, testInfo, reportInfo, testItemList, tabs } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            FormUtil.setData(this.testInfo.forms, e.item);
            FormUtil.setData(this.reportInfo.forms, e.item);
            this.enableButtons(['init', 'test', 'save']);
            this.getTestItmList(e);
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
    };
  },
  methods: {
    async getTestReportList() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/pr/printReport', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    getTestItmList(event) {
      const parameter = { ansIdx: event.item.ansIdx };
      this.testItemList.$grid
        ._useLoader(() => this.$axios.get('/pr/printReport/getTestItmList', parameter))
        .then(({ data }) => {
          this.testItemList.$grid.setGridData(data);
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
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getTestReportList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getTestReportList();
      }
      if (name === 'init') {
        this.init();
      }
      if (name === 'printTestReport') {
        const parameter = FormUtil.getData(this.reportInfo.forms);
        RdUtil.openReport(
          '/HF_FINISHED_PRODUCT_TEST_REPORT.mrd',
          `/rp [${parameter.plntCd}] [${parameter.ansIdx}]`,
        );
      }
      if (name === 'save') {
        this.save();
      }
    },
    init() {
      this.testInfo.forms = values.testInfo.forms();
      this.testItemList.$grid.clearGridData();
      this.disableButtons();
    },
    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
    enableButtons() {
      FormUtil.enableButtons(this.tabs.buttons, [
        'init',
        'printIntegratedReport',
        'printTestReport',
        'printTestInstruction',
      ]);
      FormUtil.enableButtons(this.reportInfo.buttons, ['save']);
    },
    disableButtons() {
      FormUtil.disableButtons(this.tabs.buttons, [
        'init',
        'printIntegratedReport',
        'printTestReport',
        'printTestInstruction',
      ]);
      FormUtil.disableButtons(this.reportInfo.buttons, ['save']);
    },
  },
};
</script>

<style></style>

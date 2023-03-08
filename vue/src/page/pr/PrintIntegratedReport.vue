<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="getTestReportList"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />
  <Card>
    <TabBase v-bind="tabs">
      <template #tab-testInfo>
        <FormWithHeader v-bind="testInfo" @button-click="onClickTestInfo" />
        <Horizontal align-items="top" :spans="[5, 5]">
          <FormWithHeader
            v-bind="reportInfo"
            @button-click="onClickReportInfo"
            @form-event="onChangeReportDivision"
          />
          <AUIGridWithHeader
            v-bind="reportHistoryGrid"
            @grid-created="(proxy) => $setState('reportHistoryGrid.$grid', proxy)"
            @button-click="onClickReportInfo"
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
  <InputPerformanceModal
    :show="inputPerformanceModal.show"
    :parameter="inputPerformanceModal.parameter"
    @close="hideModal('inputPerformanceModal')"
  />
</template>

<script>
import { InputPerformanceModal } from '@/page/modal';
import { FormUtil, RdUtil } from '@/util';

import values from './values/printIntegratedReport';

export default {
  name: 'PrintIntegrated',
  components: {
    InputPerformanceModal,
  },
  mounted() {
    this.getTestReportList();
    this.init();
  },
  data() {
    const { list, testInfo, reportInfo, testItemList, tabs, reportHistoryGrid } =
      this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.setDataToForms(e.item);
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
        event: {
          cellDoubleClick: (e) => {
            this.setDataToReportInfo(e.item);
          },
        },
      },
      inputPerformanceModal: {
        show: false,
        parameter: {},
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
    initReportForm() {
      this.reportInfo.forms = values.reportInfo.forms();
      const { $grid } = this.reportHistoryGrid;
      $grid.clearGridData();
      this.disableButtons();
    },
    disableButtons() {
      FormUtil.disableButtons(this.reportInfo.buttons, ['save', 'init']);
      FormUtil.disableButtons(this.reportHistoryGrid.buttons, ['print']);
      FormUtil.disableButtons(this.testInfo.buttons, ['inputPerformance']);
    },
    async getTestReportList() {
      const { forms, $grid } = this.list;
      const parameter = FormUtil.getData(forms);
      await $grid
        ._useLoader(() => this.$axios.get('/pr/printIntegratedReport', parameter))
        .then(({ data }) => $grid.setGridData(data));
      this.init();
    },
    async getTestItmList(event) {
      const { $grid } = this.testItemList;
      const parameter = { ansIdx: event.ansIdx };
      await $grid
        ._useLoader(() => this.$axios.get('/pr/printIntegratedReport/testItem', parameter))
        .then(({ data }) => {
          $grid.setGridData(data);
        });
    },
    async getReportHistory(event) {
      const { $grid } = this.reportHistoryGrid;
      const param = { ansIdx: event.ansIdx };
      await $grid
        ._useLoader(() => this.$axios.get('/pr/printIntegratedReport/reportHistory', param))
        .then(({ data }) => {
          $grid.setGridData(data);
        });
    },
    async save() {
      const { forms } = this.reportInfo;
      const param = FormUtil.getData(forms);
      await this.$eSignWithReason(() => this.$axios.post('/pr/printIntegratedReport', param))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.getTestReportList();
          this.getReportHistory(param);
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    async onChangeReportDivision(event) {
      if (event.type === 'change' && event.item.name === 'rptDiv' && event.item.value !== '') {
        const param = { rptIdx: event.item.value };
        await this.$axios
          .get('/pr/printIntegratedReport/integratedReport', param)
          .then(({ data }) => {
            FormUtil.setData(this.reportInfo.forms, {
              rptRdPath: data.rptRdPath,
              arptSpcc: data.arptSpcc,
            });
          })
          .catch();
      }
    },
    onClickReportInfo({ name }) {
      if (name === 'save') {
        this.reportInfo.forms
          .validate()
          .then(() => this.save())
          .catch(() => {});
      }
      if (name === 'print') {
        this.bringIntegratedTestReport();
      }
      if (name === 'init') {
        this.init();
        this.initReportForm();
      }
    },
    setDataToForms(data) {
      FormUtil.setData(this.testInfo.forms, data);
      FormUtil.setData(this.reportInfo.forms, data);
      this.getTestItmList(data);
      this.getReportHistory(data);
      FormUtil.enableButtons(this.reportInfo.buttons, ['save', 'init']);
      FormUtil.enableButtons(this.reportHistoryGrid.buttons, ['print']);
      FormUtil.enableButtons(this.testInfo.buttons, ['inputPerformance']);
    },
    setDataToReportInfo(data) {
      const { forms } = this.reportInfo;
      FormUtil.setData(forms, data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getTestReportList();
      }
    },
    onClickTestInfo({ name }) {
      if (name === 'inputPerformance') {
        this.inputPerformanceModal.parameter = FormUtil.getData(this.testInfo.forms);
        this.showModal('inputPerformanceModal');
      }
    },
    showModal(name) {
      if (name === 'inputPerformanceModal') {
        return (this.inputPerformanceModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'inputPerformanceModal') {
        return (this.inputPerformanceModal.show = false);
      }
    },
    bringIntegratedTestReport() {
      const parameter = FormUtil.getData(this.testInfo.forms);
      RdUtil.openReport(
        '/INTEGRATED_TEST_REPORT_THREE.mrd',
        `/rp [${parameter.plntCd}] [${parameter.ansIdx}] [${parameter.mtrCd}] [${parameter.batchNo}]`,
      );
      //  `/rp [1100] [142] ['10100000'] ['2209190005']`,
    },
  },
};
</script>

<style></style>

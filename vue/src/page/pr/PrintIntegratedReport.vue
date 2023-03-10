<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="getTestReportList"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />
  <Card>
    <TabBase v-bind="tabs" @common-button-click="onClickButton">
      <template #tab-testInfo>
        <FormWithHeader v-bind="testInfo" @button-click="onClickButton" />
        <Horizontal align-items="top" :spans="[5, 5]">
          <FormWithHeader
            v-bind="reportInfo"
            @button-click="onClickButton"
            @form-event="onChangeReportDivision"
          />
          <AUIGridWithHeader
            v-bind="reportHistoryGrid"
            @grid-created="(proxy) => $setState('reportHistoryGrid.$grid', proxy)"
            @button-click="onClickButton"
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
          rowCheckClick: () => {
            this.enableButtons(['print']);
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
      this.disableButtons(['save', 'init', 'print', 'inputPerformance']);
    },
    initReportForm() {
      this.reportInfo.forms = values.reportInfo.forms();
      const { $grid } = this.reportHistoryGrid;
      $grid.clearGridData();
      this.disableButtons(['save', 'init', 'print', 'inputPerformance']);
    },
    async getTestReportList() {
      const { forms, $grid } = this.list;
      const parameter = FormUtil.getData(forms);
      await $grid
        ._useLoader(() => this.$axios.get('/pr/printIntegratedReport', parameter))
        .then(({ data }) => $grid.setGridData(data));
    },
    async getTestItmList(item) {
      const { $grid } = this.testItemList;
      const parameter = { ansIdx: item.ansIdx, plntCd: item.plntCd };
      await $grid
        ._useLoader(() => this.$axios.get('/pr/printIntegratedReport/testItem', parameter))
        .then(({ data }) => {
          $grid.setGridData(data);
        });
    },
    async getReportHistory(item) {
      const { $grid } = this.reportHistoryGrid;
      const parameter = { ansIdx: item.ansIdx, plntCd: item.plntCd };
      await $grid
        ._useLoader(() => this.$axios.get('/pr/printIntegratedReport/reportHistory', parameter))
        .then(({ data }) => {
          $grid.setGridData(data);
        });
    },
    async save() {
      const { forms } = this.reportInfo;
      const param = FormUtil.getData(forms);
      await this.$eSignWithReason(() => this.$axios.post('/pr/printIntegratedReport/save', param))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.reportInfo.forms = values.reportInfo.forms();
          this.getTestReportList();
          this.getReportHistory(param);
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    async onChangeReportDivision(event) {
      if (event.type === 'change' && event.item.name === 'arptRptIdx' && event.item.value !== '') {
        const param = { rptIdx: event.item.value };
        await this.$axios
          .get('/pr/printIntegratedReport/integratedReport', param)
          .then(({ data }) => {
            FormUtil.setData(this.reportInfo.forms, {
              arptRptIdx: data.rptIdx,
              arptSpcc: data.arptSpcc,
            });
          })
          .catch();
      }
    },
    onClickButton({ name }) {
      if (name === 'save') {
        this.reportInfo.forms.validate().then(() => this.save());
      }
      if (name === 'print') {
        this.bringIntegratedTestReport();
      }
      if (name === 'init') {
        this.init();
        this.initReportForm();
      }
      if (name === 'inputPerformance') {
        this.inputPerformanceModal.parameter = FormUtil.getData(this.testInfo.forms);
        this.showModal('inputPerformanceModal');
      }
    },
    setDataToForms(data) {
      FormUtil.setData(this.testInfo.forms, data);
      FormUtil.setData(this.reportInfo.forms, {
        plntCd: data.plntCd,
        ansIdx: data.ansIdx,
        rptRdPath: data.rptRdPath,
      });
      this.getTestItmList(data);
      this.getReportHistory(data);
      this.enableButtons(['init', 'save', 'inputPerformance']);
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
    enableButtons(buttons) {
      FormUtil.enableButtons(this.reportInfo.buttons, buttons);
      FormUtil.enableButtons(this.testInfo.buttons, buttons);
      FormUtil.enableButtons(this.reportHistoryGrid.buttons, buttons);
      FormUtil.enableButtons(this.tabs.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.reportInfo.buttons, buttons);
      FormUtil.disableButtons(this.testInfo.buttons, buttons);
      FormUtil.disableButtons(this.reportHistoryGrid.buttons, buttons);
      FormUtil.disableButtons(this.tabs.buttons, buttons);
    },
    bringIntegratedTestReport() {
      const checkedRows = this.reportHistoryGrid.$grid.getCheckedRowItems();
      const parameter = checkedRows[0].item;
      RdUtil.openReport(
        `/${parameter.rptRdPath}`,
        `/rp [${parameter.plntCd}] [${parameter.ansIdx}] [${parameter.arptSeq}] [${parameter.mtrCd}] [${parameter.batchNo}]`,
        //  `/rp [1100] [142] ['10000440'] ['2302060009']`,
      );
    },
  },
};
</script>

<style></style>

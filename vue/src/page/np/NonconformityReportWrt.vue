<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
    @grid-button-click="gridButtonClick"
  />

  <AUIGridSearch
    v-bind="testInfo"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('testInfo.$grid', proxy)"
    @grid-button-click="gridButtonClick"
  />

  <RequestApproverModal
    :title="requestApproverModal.title"
    :show="requestApproverModal.show"
    :aprReqDiv="requestApproverModal.aprReqDiv"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="request"
  />
  <FileAttacherModal
    :fileIdx="fileAttacherModal.fileIdx"
    :show="fileAttacherModal.show"
    :readonly="fileAttacherModal.readonly"
    @close="hideModal('fileAttacherModal')"
  />
</template>

<script>
import { RequestApproverModal, FileAttacherModal } from '@/page/modal';
import { FormUtil, RdUtil } from '@/util';

import values from './values/nonconformityReportWrt';

export default {
  name: 'NonconformityReportWrt',
  components: {
    RequestApproverModal,
    FileAttacherModal,
  },
  mounted() {
    this.getNonconformityTestList();
  },
  data() {
    const { list, testInfo } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getResultDetail(e);
            this.enableButtons(['save', 'request', 'init', 'noncfmReport', 'prvRcrReport']);
          },
        },
      },
      testInfo: {
        ...testInfo.static,
        forms: testInfo.forms(),
        columns: testInfo.columns(),
      },
      requestApproverModal: {
        show: false,
        aprReqDiv: 'S0050015',
        title: '승인요청',
      },
      fileAttacherModal: {
        show: false,
        fileIdx: 0,
        readonly: true,
      },
    };
  },
  methods: {
    async getNonconformityTestList() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/np/nonconformityReportWrt', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);

      this.testInfo.forms = values.testInfo.forms();
      this.testInfo.$grid.clearGridData();
    },
    async getResultDetail(event) {
      const { $grid } = this.testInfo;
      const parameter = event.item;
      const data = await $grid
        ._useLoader(() =>
          this.$axios.get('/np/nonconformityReportWrt/findResultItem', {
            ansIdx: parameter.ansIdx,
          }),
        )
        .then(({ data }) => data);
      $grid.setGridData(data);
      FormUtil.setData(this.testInfo.forms, event.item);
    },
    save() {
      const parameter = FormUtil.getData(this.testInfo.forms);
      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$eSign(() => this.$axios.put('/np/nonconformityReportWrt/save', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.init();
            this.getNonconformityTestList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    request(approveInfo) {
      let parameter = FormUtil.getData(this.testInfo.forms);
      parameter = {
        ...parameter,
        approveInfo,
      };
      this.$eSignWithReason(() => this.$axios.put('/np/nonconformityReportWrt/request', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getNonconformityTestList();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getNonconformityTestList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getNonconformityTestList();
      }
      if (name === 'save') {
        return this.save(name);
      }
      if (name === 'request') {
        return this.showModal('requestApproverModal');
      }
      if (name === 'noncfmReport') {
        const parameter = FormUtil.getData(this.testInfo.forms);
        RdUtil.openReport(
          '/NONCONFORMITY_REPORT.mrd',
          `/rp [${parameter.plntCd}] [${parameter.ansIdx}]`,
        );
      }
      if (name === 'prvRcrReport') {
        const parameter = FormUtil.getData(this.testInfo.forms);
        RdUtil.openReport(
          '/MEASURES_TO_PREVENT_RECURRENCE.mrd',
          `/rp [${parameter.plntCd}] [${parameter.ansIdx}]`,
        );
      }
      if (name === 'init') {
        this.init();
      }
    },
    init() {
      this.testInfo.forms = values.testInfo.forms();
      this.testInfo.$grid.clearGridData();
      this.disableButtons(['save', 'request', 'init', 'noncfmReport', 'prvRcrReport']);
    },
    gridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        const fileIdx = Number(event.item.fileIdx);
        this.fileAttacherModal.fileIdx = fileIdx;
        return this.showModal('fileAttacherModal');
      }
    },
    showModal(name) {
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = true);
      }
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = false);
      }
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.testInfo.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.testInfo.buttons, buttons);
    },
  },
};
</script>

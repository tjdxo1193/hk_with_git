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

  <FileAttacherModal
    :fileIdx="fileAttacherModal.fileIdx"
    :show="fileAttacherModal.show"
    :readonly="fileAttacherModal.readonly"
    @close="hideModal('fileAttacherModal')"
  />
</template>

<script>
import { FileAttacherModal } from '@/page/modal';
import { FormUtil, RdUtil } from '@/util';

import values from './values/nonconformityReportAppr';

export default {
  name: 'NonconformityReportAppr',
  components: {
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
            this.enableButtons(['reject', 'approve', 'init', 'noncfmReport', 'prvRcrReport']);
          },
        },
      },
      testInfo: {
        ...testInfo.static,
        forms: testInfo.forms(),
        columns: testInfo.columns(),
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
        ._useLoader(() => this.$axios.get('/np/nonconformityReportAppr', parameter))
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
          this.$axios.get('/np/nonconformityReportAppr/findResultItem', {
            ansIdx: parameter.ansIdx,
          }),
        )
        .then(({ data }) => data);
      $grid.setGridData(data);
      FormUtil.setData(this.testInfo.forms, event.item);
    },
    approve() {
      const parameter = FormUtil.getData(this.testInfo.forms);
      this.$confirm(this.$message.confirm.approved).then(() => {
        this.$eSignWithReason(() =>
          this.$axios.put('/np/nonconformityReportAppr/approve', parameter),
        )
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
    reject() {
      let parameter = FormUtil.getData(this.testInfo.forms);
      this.$confirm(this.$message.confirm.rejected).then(() => {
        this.$eSignWithReason(() =>
          this.$axios.put('/np/nonconformityReportAppr/reject', parameter),
        )
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
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getNonconformityTestList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getNonconformityTestList();
      }
      if (name === 'approve') {
        return this.approve();
      }
      if (name === 'reject') {
        return this.reject();
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
      this.disableButtons(['reject', 'approve', 'init', 'noncfmReport', 'prvRcrReport']);
    },
    gridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        const ansIdx = Number(event.item.ansIdx);
        const rstSeq = Number(event.item.rstSeq);
        return this.showModal('fileAttacherModal', { ansIdx, rstSeq });
      }
    },
    showModal(name, parameter = {}) {
      if (name === 'fileAttacherModal') {
        this.fileAttacherModal.fileIdx = this.getFildIdx(parameter);
        return (this.fileAttacherModal.show = true);
      }
    },
    hideModal(name) {
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
    getFildIdx(parameter) {
      const isRstSeqEmpty = parameter.rstSeq == 0 ? true : false;
      const selectedItem = isRstSeqEmpty
        ? this.list.$grid.getSelectedItems()
        : this.testInfo.$grid.getSelectedItems();
      return selectedItem[0].item.fileIdx;
    },
  },
};
</script>

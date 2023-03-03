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
  <PreventRecurrenceReportModal
    :show="preventRecurrenceReportModal.show"
    :parameter="preventRecurrenceReportModal.parameter"
    @close="hideModal('preventRecurrenceReportModal')"
  ></PreventRecurrenceReportModal>
</template>

<script>
import { FileAttacherModal, PreventRecurrenceReportModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/preventRecurrenceAppr';

export default {
  name: 'PreventRecurrenceAppr',
  components: {
    FileAttacherModal,
    PreventRecurrenceReportModal,
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
            this.enableButtons(['approve', 'init']);
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
      preventRecurrenceReportModal: {
        show: false,
        parameter: {},
      },
    };
  },
  methods: {
    async getNonconformityTestList() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/np/preventRecurrenceAppr', parameter))
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
          this.$axios.get('/np/preventRecurrenceAppr/findResultItem', {
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
        this.$eSignWithReason(() => this.$axios.put('/np/preventRecurrenceAppr/approve', parameter))
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
        this.approve();
      }
      if (name === 'init') {
        this.init();
      }
    },
    init() {
      this.testInfo.forms = values.testInfo.forms();
      this.testInfo.$grid.clearGridData();
      this.disableButtons(['approve', 'init']);
    },
    gridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        const fileIdx = Number(event.item.fileIdx);
        this.fileAttacherModal.fileIdx = fileIdx;
        return this.showModal('fileAttacherModal');
      }
      if (event.dataField === 'prvRcrReport') {
        this.preventRecurrenceReportModal.parameter = event.item;
        return this.showModal('preventRecurrenceReportModal');
      }
    },
    showModal(name) {
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = true);
      }
      if (name === 'preventRecurrenceReportModal') {
        return (this.preventRecurrenceReportModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
      if (name === 'preventRecurrenceReportModal') {
        return (this.preventRecurrenceReportModal.show = false);
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

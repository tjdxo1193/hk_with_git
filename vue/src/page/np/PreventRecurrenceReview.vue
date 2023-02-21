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
import { FormUtil } from '@/util';

import values from './values/preventRecurrenceReview';

export default {
  name: 'PreventRecurrenceReview',
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
            this.enableButtons(['save', 'request', 'init']);
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
        ._useLoader(() => this.$axios.get('/np/preventRecurrenceReview', parameter))
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
          this.$axios.get('/np/preventRecurrenceReview/findResultItem', {
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
        this.$eSign(() => this.$axios.put('/np/preventRecurrenceReview/save', parameter))
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
      this.$eSignWithReason(() => this.$axios.put('/np/preventRecurrenceReview/request', parameter))
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
      if (name === 'init') {
        this.init();
      }
    },
    init() {
      this.testInfo.forms = values.testInfo.forms();
      this.testInfo.$grid.clearGridData();
      this.disableButtons(['save', 'request', 'init']);
    },
    gridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        const ansIdx = Number(event.item.ansIdx);
        const rstSeq = Number(event.item.rstSeq);
        return this.showModal('fileAttacherModal', { ansIdx, rstSeq });
      }
    },
    showModal(name, parameter = {}) {
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = true);
      }
      if (name === 'fileAttacherModal') {
        this.fileAttacherModal.fileIdx = this.getFildIdx(parameter);
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

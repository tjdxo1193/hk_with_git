<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <AUIGridSearch
    v-bind="itemList"
    @grid-created="(proxy) => $setState('itemList.$grid', proxy)"
    @button-click="onClickButton"
  />

  <FileAttacherModal
    title="파일등록"
    :show="fileAttacherModal.show"
    @close="hideModal('fileAttacherModal')"
    width="60%"
    height="450px"
  >
  </FileAttacherModal>

  <RequestApproverModal
    :show="requestApproverModal.show"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="approveRequest"
  />
</template>

<script>
import { FileAttacherModal, RequestApproverModal } from '@/page/modal';
import { FormUtil, StringUtil } from '@/util';

import values from './values/monitorResultReview';

export default {
  name: 'MonitorResultReview',
  components: {
    FileAttacherModal,
    RequestApproverModal,
  },
  mounted() {
    this.getMonitorTestResultReview();
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
            FormUtil.setData(this.itemList.forms, e.item);
            this.getMonitorTestRst(e);
            this.enableButtons(['preResultTrend', 'hold', 'reject', 'approveRequest', 'init']);
          },
        },
      },
      itemList: {
        ...itemList.static,
        forms: itemList.forms(),
        columns: itemList.columns(),
        event: {},
      },
      fileAttacherModal: {
        show: false,
      },
      requestApproverModal: {
        show: false,
        aprReqDiv: 'S0050008',
      },
    };
  },
  methods: {
    async getMonitorTestResultReview() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/mt/monitorTestResultReview', parameter))
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
          this.$axios.get('/mt/monitorTestResultReview/getMonitorTestRst', parameter),
        )
        .then(({ data }) => this.itemList.$grid.setGridData(data));
    },
    approveRequest(approveInfo) {
      let parameter = FormUtil.getData(this.itemList.forms);
      parameter = {
        ...parameter,
        approveInfo,
      };
      this.$eSignWithReason(() =>
        this.$axios.put('/mt/monitorTestResultReview/apprRequest', parameter),
      )
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getMonitorTestResultReview();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    reject() {
      const parameter = FormUtil.getData(this.itemList.forms);
      this.$eSignWithReason(() => this.$axios.put('/mt/monitorTestResultReview/reject', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getMonitorTestResultReview();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    hold() {
      const parameter = FormUtil.getData(this.itemList.forms);
      this.$confirm(this.$message.confirm.hold).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/mt/monitorTestResultReview/hold', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.init();
            this.getMonitorTestResultReview();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    beforeCheckCompletedReview() {
      /**
       * ? 선행조건: 1. 결과판정은 필수값임
       */
      if (!this.hasNonResultJudge()) {
        return this.$warn(this.$message.warn.noResultJudge);
      }
      return this.showModal('requestApproverModal');
    },
    hasNonResultJudge() {
      const item = FormUtil.getData(this.itemList.forms);
      return StringUtil.isNotEmpty(item.sytJdg);
    },
    searchFormEvent(event) {
      const forms = this.list.forms;
      const value = event.item.value;
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getMonitorTestResultReview();
      }
      if (event.type === 'change' && event.item.name === 'upperMitmPitmDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmPitmDiv');
      }
      if (event.type === 'change' && event.item.name === 'mitmWrkStudioDiv') {
        this.setSelectByUpperCd(forms, value, 'upperMitmWrkPlcDiv');
      }
      if (event.type === 'change' && event.item.name === 'upperMitmWrkPlcDiv') {
        this.setSelectByUpperCd(forms, value, 'mitmWrkPlcDiv');
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getMonitorTestResultReview();
      }
      if (name === 'init') {
        this.init();
      }
      if (name === 'preResultTrend') {
        alert('이전결과동향');
      }
      if (name === 'hold') {
        this.hold();
      }
      if (name === 'reject') {
        this.reject();
      }
      if (name === 'approveRequest') {
        return this.beforeCheckCompletedReview();
      }
    },
    init() {
      this.itemList.$grid.clearGridData();
      this.disableButtons(['preResultTrend', 'hold', 'reject', 'approveRequest', 'init']);
    },
    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.itemList.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.itemList.buttons, buttons);
    },
  },
};
</script>

<style></style>

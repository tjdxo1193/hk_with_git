<template>
  <AUIGridSearch
    v-bind="list"
    :columns="computedListColumns"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
    @grid-button-click="gridButtonClick"
  />

  <AUIGridSearch
    v-bind="requestInfo"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('requestInfo.$grid', proxy)"
    @grid-button-click="gridButtonClick"
  />

  <FileAttacherModal
    :fileIdx="fileAttacherModal.fileIdx"
    :show="fileAttacherModal.show"
    :readonly="fileAttacherModal.readonly"
    @close="hideModal('fileAttacherModal')"
  />

  <ResultHistoryModal
    :parameter="resultHistoryModal.parameter"
    :show="resultHistoryModal.show"
    @close="hideModal('resultHistoryModal')"
  />
</template>

<script>
import { FileAttacherModal, ResultHistoryModal } from '@/page/modal';
import { FormUtil, TokenUtil, StringUtil } from '@/util';

import values from './values/testResultAppr';

export default {
  name: 'TestResultAppr',
  components: {
    FileAttacherModal,
    ResultHistoryModal,
  },
  mounted() {
    this.getTestResultApprList();
  },
  data() {
    const { list, requestInfo } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            FormUtil.setData(this.requestInfo.forms, e.item);
            this.getResultDetail(e);
            this.enableButtons(['init']);
          },
        },
      },
      requestInfo: {
        ...requestInfo.static,
        forms: requestInfo.forms(),
        columns: requestInfo.columns(),
      },
      fileAttacherModal: {
        show: false,
        fileIdx: 0,
        readonly: true,
      },
      resultHistoryModal: {
        show: false,
        parameter: {},
      },
    };
  },
  methods: {
    async getTestResultApprList() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ts/testResultAppr', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);

      this.requestInfo.$grid.clearGridData();
    },
    async getResultDetail(event) {
      const { $grid } = this.requestInfo;
      const data = await $grid
        ._useLoader(() => this.$axios.get(`/ts/testResultAppr/${event.item.ansIdx}`))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getTestResultApprList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getTestResultApprList();
      }
      if (name === 'init') {
        return this.init();
      }
      if (name === 'approve') {
        const checkedRows = this.list.$grid.getCheckedRowItems();
        if (checkedRows.length) {
          return this.approve();
        } else {
          return this.$warn(this.$message.warn.unCheckedData);
        }
      }
      if (name === 'hold') {
        const checkedRows = this.list.$grid.getCheckedRowItems();
        if (checkedRows.length) {
          return this.hold();
        } else {
          return this.$warn(this.$message.warn.unCheckedData);
        }
      }
      if (name === 'rejection') {
        const checkedRows = this.list.$grid.getCheckedRowItems();
        if (checkedRows.length) {
          return this.requestRejection();
        } else {
          return this.$warn(this.$message.warn.unCheckedData);
        }
      }
    },
    init() {
      this.requestInfo.forms = values.requestInfo.forms();
      this.requestInfo.$grid.clearGridData();
      this.disableButtons(['init']);
    },
    compareAnsUid() {
      const checkedRows = this.requestInfo.$grid.getCheckedRowItems();
      const useUid = TokenUtil.myId();
      return checkedRows.filter(({ item }) => item.ansUid === useUid).length === 0 ? true : false;
    },
    beforeCheckRequestReview() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      return checkedRows.filter(({ item }) => StringUtil.isEmpty(item.sytJdg)).length === 0
        ? true
        : false;
    },
    approve() {
      if (!this.beforeCheckRequestReview()) {
        return this.$warn(this.$message.warn.noCompleteResultData);
      }

      const checkedRows = this.list.$grid.getCheckedRowItems();
      const parameter = checkedRows.map(({ item }) => item);

      this.$confirm(this.$message.confirm.approved).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/ts/testResultAppr', parameter))
          .then(() => {
            this.$info(this.$message.info.approve);
            this.init();
            this.getTestResultApprList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    hold() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const parameter = checkedRows.map(({ item }) => item);

      this.$confirm(this.$message.confirm.hold).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/ts/testResultAppr/requestHold', parameter))
          .then(() => {
            this.$info(this.$message.info.hold);
            this.init();
            this.getTestResultApprList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    requestRejection() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const parameter = checkedRows.map(({ item }) => item);

      this.$confirm(this.$message.confirm.rejected).then(() => {
        this.$eSignWithReason(() =>
          this.$axios.put('/ts/testResultAppr/requestRejection', parameter),
        )
          .then(() => {
            this.$info(this.$message.info.reject);
            this.init();
            this.getTestResultApprList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    gridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        const ansIdx = Number(event.item.ansIdx);
        const rstSeq = Number(event.item.rstSeq);
        return this.showModal('fileAttacherModal', { ansIdx, rstSeq });
      }
      if (event.dataField === 'resultHistory') {
        /**
         * TODO 같은 자재규격서를 사용한 시험항목 이력 보여주기? 시험번호?
         * ? 조건 확인
         */
        const selectedItem = this.requestInfo.$grid.getSelectedItems();
        const pitmSpecIdx = selectedItem[0].item.pitmSpecIdx;
        const parameter = { pitmSpecIdx };
        return this.showModal('resultHistoryModal', parameter);
      }
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.requestInfo.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.requestInfo.buttons, buttons);
    },
    showModal(name, parameter = {}) {
      if (name === 'fileAttacherModal') {
        this.fileAttacherModal.fileIdx = this.getFildIdx(parameter);
        return (this.fileAttacherModal.show = true);
      }
      if (name === 'resultHistoryModal') {
        this.resultHistoryModal.parameter = parameter;
        return (this.resultHistoryModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
      if (name === 'resultHistoryModal') {
        return (this.resultHistoryModal.show = false);
      }
    },
    getFildIdx(parameter) {
      const isRstSeqEmpty = parameter.rstSeq == 0 ? true : false;
      const selectedItem = isRstSeqEmpty
        ? this.list.$grid.getSelectedItems()
        : this.requestInfo.$grid.getSelectedItems();
      return selectedItem[0].item.fileIdx;
    },
  },
  computed: {
    computedListColumns() {
      const editableColumns = [];

      return this.list.columns.map((col) => ({
        ...col,
        editable: editableColumns.includes(col.dataField) ? true : false,
      }));
    },
  },
};
</script>

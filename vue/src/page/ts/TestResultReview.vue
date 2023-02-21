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
    ref="fileAttacherModal"
    :fileIdx="fileAttacherModal.fileIdx"
    :show="fileAttacherModal.show"
    @save="fileSave"
    @close="hideModal('fileAttacherModal')"
  />

  <RequestApproverModal
    :show="requestApproverModal.show"
    :aprReqDiv="requestApproverModal.aprReqDiv"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="requestApproverModalReturnDataEvent"
  />

  <ResultHistoryModal
    :parameter="resultHistoryModal.parameter"
    :show="resultHistoryModal.show"
    @close="hideModal('resultHistoryModal')"
  />
</template>

<script>
import { FileAttacherModal, RequestApproverModal, ResultHistoryModal } from '@/page/modal';
import { FormUtil, StringUtil } from '@/util';

import values from './values/testResultReview';

export default {
  name: 'TestResultReview',
  components: {
    FileAttacherModal,
    RequestApproverModal,
    ResultHistoryModal,
  },
  mounted() {
    this.getTestResultReviewList();
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
            this.getResultDetail(e.item.ansIdx);
            this.enableButtons(['rejection', 'hold', 'reviewCompletion', 'init']);
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
      },
      requestApproverModal: {
        show: false,
        aprReqDiv: 'S0050014',
      },
      resultHistoryModal: {
        show: false,
        parameter: {},
      },
    };
  },
  methods: {
    async getTestResultReviewList() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ts/testResultReview', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);

      this.requestInfo.$grid.clearGridData();
    },
    async getResultDetail(ansIdx) {
      const { $grid } = this.requestInfo;
      const data = await $grid
        ._useLoader(() => this.$axios.get(`/ts/testResultReview/${ansIdx}`))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getTestResultReviewList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        return this.getTestResultReviewList();
      }
      if (name === 'rejection') {
        return this.requestRejection();
      }
      if (name === 'hold') {
        return this.hold();
      }
      if (name === 'reviewCompletion') {
        return this.beforeCheckCompletedReview();
      }
      if (name === 'init') {
        return this.init();
      }
    },
    init() {
      this.requestInfo.forms = values.requestInfo.forms();
      this.requestInfo.$grid.clearGridData();
      this.disableButtons(['rejection', 'hold', 'reviewCompletion', 'init']);
    },
    requestRejection() {
      const parameter = FormUtil.getData(this.requestInfo.forms);
      this.$confirm(this.$message.confirm.rejected).then(() => {
        this.$eSignWithReason(() =>
          this.$axios.put('/ts/testResultReview/requestRejection', parameter),
        )
          .then(() => {
            this.$info(this.$message.info.reject);
            this.init();
            this.getTestResultReviewList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    hold() {
      const parameter = FormUtil.getData(this.requestInfo.forms);
      this.$confirm(this.$message.confirm.hold).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/ts/testResultReview/requestHold', parameter))
          .then(() => {
            this.$info(this.$message.info.hold);
            this.init();
            this.getTestResultReviewList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    beforeCheckCompletedReview() {
      /**
       * ? 선행조건: 1. 결과판정은 필수값임
       * ?          2. 결과판정이 부적합, 조건부적합이 있을 경우, 검토의견을 필수로 작성해야 함
       */
      if (!this.hasNonResultJudge()) {
        return this.$warn(this.$message.warn.noResultJudge);
      }
      if (this.hasNonConformity()) {
        //return this.$warn(this.$message.warn.inputNonConformityReason);
      }
      return this.showModal('requestApproverModal');
    },
    hasNonResultJudge() {
      const item = FormUtil.getData(this.requestInfo.forms);
      return StringUtil.isNotEmpty(item.sytJdg);
    },
    hasNonConformity() {
      const conformity = 'S0110001';
      const item = FormUtil.getData(this.requestInfo.forms);
      return item.sytJdg !== conformity && StringUtil.isEmpty(item.revwCmmt) ? true : false;
    },
    requestApproverModalReturnDataEvent(approveInfo) {
      let parameter = FormUtil.getData(this.requestInfo.forms);
      parameter = {
        ...parameter,
        approveInfo,
      };
      this.$confirm(this.$message.confirm.requestApproved).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/ts/testResultReview', parameter))
          .then(() => {
            this.$info(this.$message.info.approveRequest);
            this.init();
            this.getTestResultReviewList();
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
    showModal(name, parameter = {}) {
      if (name === 'fileAttacherModal') {
        this.fileAttacherModal.fileIdx = this.getFildIdx(parameter);
        return (this.fileAttacherModal.show = true);
      }
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = true);
      }
      if (name === 'resultHistoryModal') {
        return (this.resultHistoryModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = false);
      }
      if (name === 'resultHistoryModal') {
        return (this.resultHistoryModal.show = false);
      }
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.requestInfo.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.requestInfo.buttons, buttons);
    },
    getFildIdx(parameter) {
      const isRstSeqEmpty = parameter.rstSeq == 0 ? true : false;
      const selectedItem = isRstSeqEmpty
        ? this.list.$grid.getSelectedItems()
        : this.requestInfo.$grid.getSelectedItems();
      return selectedItem[0].item.fileIdx;
    },
    getAnsIdx() {
      const selectedItem = this.list.$grid.getSelectedItems();
      return selectedItem[0].item.ansIdx;
    },
    getRstSeq() {
      const selectedItem = this.requestInfo.$grid.getSelectedItems();
      return selectedItem.length ? selectedItem[0].item.rstSeq : 0;
    },
    fileSave({ addedFiles, removedFileIds }) {
      const ansIdx = Number(this.getAnsIdx());
      const rstSeq = Number(this.getRstSeq());
      const fileIdx = Number(this.getFildIdx({ ansIdx, rstSeq }));
      const fileInfoList = { addedFiles, removedFileIds, ansIdx, rstSeq, fileIdx };

      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$axios
          .postByForm('/ts/testResultReview/savedFile', fileInfoList)
          .then(({ data }) => {
            if (addedFiles.length == 0) {
              this.$info(this.$message.info.removedFiles);
            } else {
              this.$info(this.$message.info.savedFiles);
            }
            this.getTestResultReviewList();
            this.getResultDetail(ansIdx);
            this.getFileListByFileIdx(fileIdx, data);
          })
          .catch(() => {
            this.$error(this.$message.error.savedFiles);
          });
      });
    },
    getFileListByFileIdx(originFileIdx, fileIdx) {
      if (originFileIdx > 0) {
        return this.$refs.fileAttacherModal.getFileList();
      } else {
        return this.setInitFileIdx(fileIdx);
      }
    },
    setInitFileIdx(fileIdx) {
      this.fileAttacherModal.fileIdx = fileIdx;
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

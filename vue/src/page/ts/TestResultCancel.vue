<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
    @grid-button-click="gridButtonClick"
  />

  <AUIGridSearch
    v-bind="resultInputInfo"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('resultInputInfo.$grid', proxy)"
    @grid-button-click="gridButtonClick"
  />

  <FileAttacherModal
    ref="fileAttacherModal"
    :fileIdx="fileAttacherModal.fileIdx"
    :show="fileAttacherModal.show"
    @save="fileSave"
    @close="hideModal('fileAttacherModal')"
  />
</template>

<script>
import { FileAttacherModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/testResultCancel';

export default {
  name: 'TestResultCancel',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getTestResultCancelList();
  },
  data() {
    const { list, resultInputInfo } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => this.getResultDetail(e.item.ansIdx),
        },
      },
      resultInputInfo: {
        ...resultInputInfo.static,
        columns: resultInputInfo.columns(),
      },
      fileAttacherModal: {
        show: false,
        fileIdx: 0,
      },
    };
  },
  methods: {
    async getTestResultCancelList() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ts/testResultCancel', parameter))
        .then(({ data }) => data);
      $grid.setGridData(data);

      this.resultInputInfo.$grid.clearGridData();
    },
    async getResultDetail(ansIdx) {
      const { $grid } = this.resultInputInfo;
      const data = await $grid
        ._useLoader(() => this.$axios.get(`/ts/testResultCancel/${ansIdx}`))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getTestResultCancelList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        const testDiv = FormUtil.getValue(this.list.forms, 'testDiv');
        this.setButtonsState(testDiv);
        return this.getTestResultCancelList();
      }
      if (name === 'init') {
        return this.init();
      }
      if (name === 'testCancel') {
        const checkedRows = this.list.$grid.getCheckedRowItems();
        if (checkedRows.length) {
          return this.testCancel();
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
      if (name === 'holdOff') {
        const checkedRows = this.list.$grid.getCheckedRowItems();
        if (checkedRows.length) {
          return this.holdOff();
        } else {
          return this.$warn(this.$message.warn.unCheckedData);
        }
      }
    },
    init() {
      this.resultInputInfo.$grid.clearGridData();
    },
    setButtonsState(testDiv) {
      const holdButtons = ['hold'];
      const normalButtons = ['testCancel', 'holdOff'];

      if (testDiv === 'Y') {
        FormUtil.enableButtons(this.list.buttons, normalButtons);
        FormUtil.disableButtons(this.list.buttons, holdButtons);
      } else {
        FormUtil.enableButtons(this.list.buttons, holdButtons);
        FormUtil.disableButtons(this.list.buttons, normalButtons);
      }
    },
    getListCheckedData() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      return checkedRows.map(({ item }) => item);
    },
    testCancel() {
      const parameter = this.getListCheckedData();
      this.$confirm(this.$message.confirm.canceld).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/ts/testResultCancel/testCancel', parameter))
          .then(() => {
            this.$info(this.$message.info.canceld);
            this.getTestResultCancelList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    hold() {
      const parameter = this.getListCheckedData();
      this.$confirm(this.$message.confirm.hold).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/ts/testResultCancel/hold', parameter))
          .then(() => {
            this.$info(this.$message.info.hold);
            this.getTestResultCancelList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    holdOff() {
      const parameter = this.getListCheckedData();
      this.$confirm(this.$message.confirm.holdOff).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/ts/testResultCancel/holdOff', parameter))
          .then(() => {
            this.$info(this.$message.info.holdOff);
            this.getTestResultCancelList();
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
    getFildIdx(parameter) {
      const isRstSeqEmpty = parameter.rstSeq == 0 ? true : false;
      const selectedItem = isRstSeqEmpty
        ? this.list.$grid.getSelectedItems()
        : this.resultInputInfo.$grid.getSelectedItems();
      return selectedItem[0].item.fileIdx;
    },
    getAnsIdx() {
      const selectedItem = this.list.$grid.getSelectedItems();
      return selectedItem[0].item.ansIdx;
    },
    getRstSeq() {
      const selectedItem = this.resultInputInfo.$grid.getSelectedItems();
      return selectedItem.length ? selectedItem[0].item.rstSeq : 0;
    },
    fileSave({ addedFiles, removedFileIds }) {
      const ansIdx = Number(this.getAnsIdx());
      const rstSeq = Number(this.getRstSeq());
      const fileIdx = Number(this.getFildIdx({ ansIdx, rstSeq }));
      const fileInfoList = { addedFiles, removedFileIds, ansIdx, rstSeq, fileIdx };

      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$axios
          .postByForm('/ts/testResultCancel/savedFile', fileInfoList)
          .then(({ data }) => {
            if (addedFiles.length == 0) {
              this.$info(this.$message.info.removedFiles);
            } else {
              this.$info(this.$message.info.savedFiles);
            }
            this.getResultInputList();
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
};
</script>

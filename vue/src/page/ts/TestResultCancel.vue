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
          cellDoubleClick: (e) => {
            this.getResultDetail(e.item.ansIdx);
            this.enableButtons(['init']);
            if(e.item.hldYn === 'Y'){
              this.enableButtons(['oos']);
            }
          },
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
      fileInfo: {
        ansIdx: '',
        fileIdx: '',
        rstSeq: ''
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

      this.disableButtons(['init', 'oos']);
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
      if (name === 'hold') {
        const checkedRows = this.list.$grid.getCheckedRowItems();
        if (checkedRows.length) {
          return this.hold();
        } else {
          return this.$warn(this.$message.warn.unCheckedData);
        }
      }
      if (name === 'oos') {
        const checkedRows = this.resultInputInfo.$grid.getCheckedRowItems();
        if (checkedRows.length) {
          return this.oos();
        } else {
          return this.$warn(this.$message.warn.unCheckedData);
        }
      }
    },
    init() {
      this.resultInputInfo.$grid.clearGridData();
      this.disableButtons(['init', 'oos']);
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
    oos() {
      const checkedRows = this.resultInputInfo.$grid.getCheckedRowItems();
      const parameter = checkedRows.map(({ item }) => item);
      if(parameter[0].ansProcCd === "S0130600" || parameter[0].ansProcCd === "S0130610"){
        this.$confirm(this.$message.confirm.oos).then(() => {
          // this.$axios.put('/ts/testResultReview/savedFile', parameter)
          // .then(() => {
          //   this.$info(this.$message.info.approveRequest);
          //   this.init();
          //   this.getTestResultReviewList();
          // })
          // .catch(() => {
          //   this.$error(this.$message.error.updateData);
          // });
        });
      }else{
        return this.$warn(this.$message.warn.notoosItem);
      }
    },
    gridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        const ansIdx = Number(event.item.ansIdx);
        const rstSeq = Number(event.item.rstSeq);
        const fileIdx = Number(event.item.fileIdx);
        this.fileInfo = { ansIdx: ansIdx, fileIdx: fileIdx, rstSeq: rstSeq };
        return this.showModal('fileAttacherModal', { fileIdx });
      }
    },
    showModal(name, parameter = {}) {
      if (name === 'fileAttacherModal') {
        this.fileAttacherModal.fileIdx = parameter.fileIdx;
        return (this.fileAttacherModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.resultInputInfo.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.resultInputInfo.buttons, buttons);
    },
    fileSave({ addedFiles, removedFileIds }) {
      let parameter = this.fileInfo;
      const ansIdx = parameter.ansIdx;
      const fileIdx = parameter.fileIdx;
      parameter = {
        ...parameter,
        addedFiles,
        removedFileIds
      };
      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$axios
          .postByForm('/ts/testResultCancel/savedFile', parameter)
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
        this.fileAttacherModal.fileIdx = fileIdx;
        this.fileInfo.fileIdx = fileIdx;
      }
    },
  },
};
</script>

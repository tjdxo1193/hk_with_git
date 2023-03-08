<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-button-click="gridButtonClick"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <Card>
    <TabBase v-bind="tabs" @button-click="onClickButton" @common-button-click="onClickButton">
      <template #tab-requestInfo>
        <FormWithHeader v-bind="requestInfo" />
        <FormWithHeader v-bind="testCollectionInfo" />
      </template>

      <template #tab-testInfo>
        <AUIGrid v-bind="itemList" @grid-created="(proxy) => $setState('itemList.$grid', proxy)" />
      </template>
    </TabBase>
  </Card>

  <FileAttacherModal
    ref="fileAttacherModal"
    :fileIdx="fileAttacherModal.fileIdx"
    :show="fileAttacherModal.show"
    @save="fileSave"
    @close="hideModal('fileAttacherModal')"
  >
  </FileAttacherModal>
</template>

<script>
import { FileAttacherModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/testInstruction';

export default {
  name: 'TestInstruction',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getTestInstructList();
  },
  data() {
    const { list, requestInfo, testCollectionInfo, itemList, tabs } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            FormUtil.setData(this.requestInfo.forms, e.item);
            FormUtil.setData(this.testCollectionInfo.forms, e.item);
            this.enableButtons(['init', 'delete', 'addFile']);
            this.getTestAitm(e);
          },
        },
      },
      requestInfo: {
        ...requestInfo.static,
        forms: requestInfo.forms(),
      },
      testCollectionInfo: {
        ...testCollectionInfo.static,
        forms: testCollectionInfo.forms(),
      },
      itemList: {
        ...itemList.static,
        columns: itemList.columns(),
      },
      fileAttacherModal: {
        show: false,
        fileIdx: 0,
      },
      tabs,
      fileInfo: {
        ansIdx: '',
        fileIdx: '',
      },
    };
  },
  methods: {
    async getTestInstructList() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/ts/testInstruction', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    getTestAitm(event) {
      const parameter = { ansIdx: event.item.ansIdx };
      this.itemList.$grid
        ._useLoader(() => this.$axios.get('/ts/testInstruction/getTestAitm', parameter))
        .then(({ data }) => {
          this.itemList.$grid.setGridData(data);
          if (data.length == 0) {
            this.disableButtons(['delete']);
          }
        });
    },
    instruct() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      if (checkedRows.length > 0) {
        const parameter = checkedRows.map((row) => ({
          ...row.item,
        }));
        this.$eSignWithReason(() => this.$axios.put('/ts/testInstruction/instruct', parameter))
          .then(() => {
            this.$info(this.$message.info.approve);
            this.init();
            this.getTestInstructList();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      } else {
        this.$warn(this.$message.warn.unSelectedData);
      }
    },
    deleteResult(checkedRows) {
      const parameter = checkedRows.map((row) => ({
        ...row.item,
      }));
      this.$eSign(() => this.$axios.put('/ts/testInstruction/deleteRst', parameter))
        .then(() => {
          this.$info(this.$message.info.deleted);
          this.getTestAitm(checkedRows[0]);
        })
        .catch(() => this.$error(this.$message.error.deleteData));
    },
    fileSave({ addedFiles, removedFileIds }) {
      let parameter = this.fileInfo;
      const fileIdx = parameter.fileIdx;
      parameter = {
        ...parameter,
        addedFiles,
        removedFileIds,
      };
      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$axios
          .postByForm('/ts/testInstruction/saveFile', parameter)
          .then(({ data }) => {
            if (addedFiles.length == 0) {
              this.$info(this.$message.info.removedFiles);
            } else {
              this.$info(this.$message.info.savedFiles);
            }
            this.getTestInstructList();
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
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getTestInstructList();
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getTestInstructList();
      }
      if (name === 'addFile') {
        this.showModal('fileAttacherModal');
      }
      if (name === 'instruct') {
        this.instruct();
      }
      if (name === 'delete') {
        const checkedRows = this.itemList.$grid.getCheckedRowItems();
        if (checkedRows.length > 0) {
          this.deleteResult(checkedRows);
        } else {
          this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'init') {
        this.init();
      }
    },
    gridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        const ansIdx = Number(event.item.ansIdx);
        const fileIdx = Number(event.item.fileIdx);
        this.fileAttacherModal.fileIdx = fileIdx;
        this.fileInfo = { ansIdx: ansIdx, fileIdx: fileIdx };
        return this.showModal('fileAttacherModal');
      }
    },
    init() {
      this.requestInfo.forms = values.requestInfo.forms();
      this.testCollectionInfo.forms = values.testCollectionInfo.forms();
      this.itemList.$grid.clearGridData();
      this.disableButtons(['init', 'delete', 'addFile']);
    },
    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.tabs.buttons, buttons);
      FormUtil.enableButtons(this.tabs.tabs[1].buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.tabs.buttons, buttons);
      FormUtil.disableButtons(this.tabs.tabs[1].buttons, buttons);
    },
  },
};
</script>

<style></style>

<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-button-click="gridButtonClick"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />
  <Horizontal align-items="center" :spans="[10, 0.1, 10]">
    <FormWithHeader v-bind="requestInfo" />
    <div></div>
    <FormWithHeader
      v-bind="testCollectionInfo"
      @button-click="onClickButton"
      @form-event="inputFormEvent"
    />
  </Horizontal>

  <FileAttacherModal
    ref="fileAttacherModal"
    :fileIdx="fileAttacherModal.fileIdx"
    :show="fileAttacherModal.show"
    @save="fileSave"
    @close="hideModal('fileAttacherModal')"
  >
  </FileAttacherModal>

  <RequestApproverModal
    :title="requestApproverModal.title"
    :show="requestApproverModal.show"
    :aprReqDiv="requestApproverModal.aprReqDiv"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="collect"
  />
</template>

<script>
import { FileAttacherModal, RequestApproverModal } from '@/page/modal';
import { FormUtil, TokenUtil } from '@/util';

import values from './values/testCollection';

export default {
  name: 'TestCollection',
  components: {
    FileAttacherModal,
    RequestApproverModal,
  },
  mounted() {
    this.getTestCollectionList();
  },
  data() {
    const { list, requestInfo, testCollectionInfo } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            FormUtil.setData(this.requestInfo.forms, e.item);
            FormUtil.setData(this.testCollectionInfo.forms, e.item);
            if(e.item.clltUnm === null){
              FormUtil.setData(this.testCollectionInfo.forms, {clltUnm : TokenUtil.myName()});
            }
            this.enableButtons(['save', 'collection', 'init']);
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
      fileAttacherModal: {
        show: false,
        fileIdx: 0,
      },
      requestApproverModal: {
        show: false,
        aprReqDiv: 'S0050013',
        title: '지시요청',
      },
    };
  },
  methods: {
    async getTestCollectionList() {
      const parameter = FormUtil.getData(this.list.forms);
      await this.list.$grid
        ._useLoader(() => this.$axios.get('/ts/testCollection', parameter))
        .then(({ data }) => this.list.$grid.setGridData(data));
    },
    collect(approveInfo) {
      let parameter = FormUtil.getData(this.testCollectionInfo.forms);
      parameter = {
        ...parameter,
        approveInfo,
      };
      this.$eSignWithReason(() => this.$axios.put('/ts/testCollection/collect', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getTestCollectionList();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    save() {
      let parameter = FormUtil.getData(this.testCollectionInfo.forms);
      this.$eSignWithReason(() => this.$axios.put('/ts/testCollection/save', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.getTestCollectionList();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    fileSave({ addedFiles, removedFileIds }) {
      const selectedItem = this.list.$grid.getSelectedItems();
      const ansIdx = Number(selectedItem[0].item.ansIdx);
      const fileIdx = Number(selectedItem[0].item.fileIdx);
      const fileInfoList = { addedFiles, removedFileIds, ansIdx, fileIdx };
      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$axios
          .postByForm('/ts/testCollection/saveFile', fileInfoList)
          .then(({ data }) => {
            if (addedFiles.length == 0) {
              this.$info(this.$message.info.removedFiles);
            } else {
              this.$info(this.$message.info.savedFiles);
            }
            this.getTestCollectionList();
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
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getTestCollectionList();
      }
    },
    inputFormEvent(event) {
      if (event.type === 'change') {
        if (
          event.item.name === 'smpVolAns' ||
          event.item.name === 'smpVolStrg' ||
          event.item.name === 'smpVolEtc'
        ) {
          const forms = FormUtil.getData(this.testCollectionInfo.forms);
          const smpVolTot =
            Number(forms.smpVolAns) +
            Number(forms.smpVolStrg) +
            Number(forms.smpVolEtc);
          FormUtil.setData(this.testCollectionInfo.forms, { smpVolTot: smpVolTot });
        }
      }
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getTestCollectionList();
      }
      if (name === 'addFile') {
        this.showModal('fileAttacherModal');
      }
      if (name === 'collection') {
        this.showModal('requestApproverModal');
      }
      if (name === 'save') {
        this.save();
      }
      if (name === 'init') {
        this.init();
      }
    },
    gridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        const ansIdx = Number(event.item.ansIdx);
        this.fileAttacherModal.fileIdx = Number(event.item.fileIdx);
        return this.showModal('fileAttacherModal', { ansIdx });
      }
    },
    init() {
      this.requestInfo.forms = values.requestInfo.forms();
      this.testCollectionInfo.forms = values.testCollectionInfo.forms();
      this.disableButtons(['save', 'collection', 'init']);
    },
    showModal(name) {
      this.$setState(name, { show: true });
    },
    hideModal(name) {
      this.$setState(name, { show: false });
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.testCollectionInfo.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.testCollectionInfo.buttons, buttons);
    },
  },
};
</script>

<style></style>

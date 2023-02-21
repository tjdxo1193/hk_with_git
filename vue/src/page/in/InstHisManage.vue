<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <FormWithHeader v-bind="detail" @button-click="onClickButton" @form-event="inputFormEvent">
    <template #form-dropzone>
      <Dropzone @created="$setState('detail.dropzone', $event)" :option="{ readonly }" />
    </template>
  </FormWithHeader>

  <InstrumentHistoryRegisterModal
    :show="instrumentHistoryRegisterModal.show"
    @reloadData="reloadData"
    @close="hideModal('instrumentHistoryRegisterModal')"
  />

  <InstrumentSearchModal
    :show="instrumentSearchModal.show"
    @modalReturnDataEvent="InstrumentSearch"
    @close="hideModal('instrumentSearchModal')"
  />

  <RequestApproverModal
    :show="requestApproverModal.show"
    :aprReqDiv="requestApproverModal.aprReqDiv"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="approveModalReturnDataEvent"
  ></RequestApproverModal>
</template>

<script>
import {
  InstrumentSearchModal,
  InstrumentHistoryRegisterModal,
  RequestApproverModal,
} from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/instHisManage';

export default {
  name: 'InstHisManage',
  components: {
    InstrumentSearchModal,
    InstrumentHistoryRegisterModal,
    RequestApproverModal,
  },
  mounted() {
    this.getInstHisManageList();
  },
  data() {
    const { list, detail } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getDetailInstHisManage(e);
          },
        },
      },
      detail: {
        ...detail.static,
        forms: detail.forms(),
        dropzone: null,
      },
      instrumentSearchModal: {
        show: false,
      },
      instrumentHistoryRegisterModal: {
        show: false,
      },
      requestApproverModal: {
        show: false,
        aprReqDiv: 'S0050010',
      },
      readonly: false,
      // TODO DropZone 옵션 동적으로 변경가능한지?
      // dropzone: {
      //   option: {
      //     readonly: true,
      //     validate: {
      //       maxCount: 1,
      //     },
      //   },
      // },
    };
  },
  methods: {
    async getInstHisManageList() {
      const parameter = FormUtil.getData(this.list.forms);
      const data = await this.list.$grid
        ._useLoader(() => this.$axios.get('/in/instHisManage', parameter))
        .then(({ data }) => data);

      this.list.$grid.setGridData(data);
    },
    getDetailInstHisManage(event) {
      const selectedItem = event.item;
      FormUtil.setData(this.detail.forms, selectedItem);
      this.getFileList(selectedItem.hisFileIdx);

      if (this.isProcessAfterRequestApproval(selectedItem.eqmHisProcCd)) {
        this.disableAllButtons();
        this.disableAllForms();
      } else {
        this.enableButtons();
        this.disableForms();
      }
    },
    async getFileList(hisFileIdx) {
      if (hisFileIdx) {
        const data = await this.$axios.get(`/files/${hisFileIdx}`).then(({ data }) => data);
        this.detail.dropzone.clear();
        this.detail.dropzone.addFiles(data);
      }
    },
    isProcessAfterRequestApproval(eqmHisProcCd) {
      const requestApproval = 'S0190200';
      const completedApproval = 'S0190300';
      const registerPMS = 'S0190900';
      return (
        eqmHisProcCd === requestApproval ||
        eqmHisProcCd === completedApproval ||
        eqmHisProcCd === registerPMS
      );
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.getInstHisManageList();
        this.initDetail();
        return;
      }
      if (name === 'init') {
        return this.initDetail();
      }
      if (name === 'save') {
        return this.dataCheckBeforeSave();
      }
      if (name === 'delete') {
        return this.delete();
      }
      if (name === 'requestApproval') {
        return this.dataCheckBeforeRequestApproval();
      }
      if (name === 'registerHistory') {
        return this.showModal('instrumentHistoryRegisterModal');
      }
    },
    dataCheckBeforeRequestApproval() {
      this.detail.forms.validate().then(() => {
        this.showModal('requestApproverModal');
      });
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getInstHisManageList();
      }
    },
    inputFormEvent(event) {
      if (event.item.itemLabel === 'instrumentSearchModal') {
        return this.showModal('instrumentSearchModal');
      }
    },
    initDetail() {
      this.detail.dropzone.clear();
      this.detail.forms = values.detail.forms();
      this.disableButtons();
    },
    isExist() {
      return FormUtil.existsValue(this.detail.forms, 'hisSeq');
    },
    dataCheckBeforeSave() {
      this.detail.forms.validate().then(() => {
        this.isExist() ? this.update() : this.create();
      });
    },
    getFormData() {
      const { dropzone } = this.detail;
      const addedFiles = dropzone.getAddedFiles();
      const removedFileIds = dropzone.getRemovedIds();
      const parameter = FormUtil.getData(this.detail.forms);
      parameter.addedFiles = addedFiles;
      parameter.removedFileIds = removedFileIds;
      return parameter;
    },
    create() {
      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$eSignWithReason(() => this.$axios.postByForm('/in/instHisManage', this.getFormData()))
          .then(({ data }) => {
            this.$info(this.$message.info.saved);
            this.getInstHisManageList();

            if (data) {
              FormUtil.setData(this.detail.forms, 'hisFileIdx');
            }
          })
          .catch(() => {
            this.$error(this.$message.error.saveData);
          });
      });
    },
    update() {
      this.$confirm(this.$message.confirm.updated).then(() => {
        this.$eSignWithReason(() => this.$axios.postByForm('/in/instHisManage', this.getFormData()))
          .then(({ data }) => {
            this.$info(this.$message.info.updated);
            this.getInstHisManageList();

            if (data) {
              FormUtil.setData(this.detail.forms, 'hisFileIdx');
            }
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    delete() {
      const parameter = FormUtil.getData(this.detail.forms);
      this.$confirm(this.$message.confirm.deleted).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/in/instHisManage/delete', parameter))
          .then(() => {
            this.$info(this.$message.info.deleted);
            this.getInstHisManageList();
            this.initDetail();
          })
          .catch(() => {
            this.$error(this.$message.error.deleteData);
          });
      });
    },
    enableButtons() {
      FormUtil.enableButtons(this.detail.buttons, ['requestApproval', 'save', 'delete']);
    },
    disableButtons() {
      FormUtil.enableButtons(this.detail.buttons, ['save']);
      FormUtil.disableButtons(this.detail.buttons, ['requestApproval', 'delete']);
    },
    disableAllButtons() {
      FormUtil.disableButtons(this.detail.buttons, ['requestApproval', 'save', 'delete']);
    },
    disableForms() {
      FormUtil.disable(this.detail.forms, ['eqmDivNm', 'eqmNm', 'eqmHisDiv', 'search']);
    },
    disableAllForms() {
      FormUtil.disable(this.detail.forms);
    },
    showModal(name) {
      if (name === 'instrumentSearchModal') {
        return (this.instrumentSearchModal.show = true);
      }
      if (name === 'instrumentHistoryRegisterModal') {
        return (this.instrumentHistoryRegisterModal.show = true);
      }
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'instrumentSearchModal') {
        return (this.instrumentSearchModal.show = false);
      }
      if (name === 'instrumentHistoryRegisterModal') {
        return (this.instrumentHistoryRegisterModal.show = false);
      }
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = false);
      }
    },
    InstrumentSearch(data) {
      FormUtil.setData(this.detail.forms, data);
    },
    approveModalReturnDataEvent(approveInfo) {
      const parameter = FormUtil.getData(this.detail.forms);
      parameter.approveInfo = approveInfo;
      this.$eSignWithReason(() => this.$axios.put('/in/instHisManage/requestApprove', parameter))
        .then(() => {
          this.$info(this.$message.info.approveRequest);
          this.getInstHisManageList();
          this.initDetail();
        })
        .catch(() => {
          this.$error(this.$message.error.saveData);
        });
    },
    reloadData() {
      this.getInstHisManageList();
    },
  },
};
</script>

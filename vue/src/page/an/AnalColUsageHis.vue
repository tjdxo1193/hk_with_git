<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <FormWithHeader v-bind="detail" @button-click="onClickButton" @form-event="inputFormEvent" />

  <TestMaterialOpenItemModal
    :show="testMaterialOpenItemModal.show"
    :treeCd="testMaterialOpenItemModal.treeCd"
    @close="hideModal('testMaterialOpenItemModal')"
    @modalReturnDataEvent="openItemModalReturnDataEvent"
  />

  <FileAttacherModal
    ref="fileAttacherModal"
    :show="fileAttacherModal.show"
    :fileIdx="fileAttacherModal.fileIdx"
    @save="fileSave"
    @close="hideModal('fileAttacherModal')"
  />
</template>

<script>
import dayjs from 'dayjs';

import { FileAttacherModal, TestMaterialOpenItemModal } from '@/page/modal';
import { FormUtil, TokenUtil } from '@/util';

import values from './values/analColUsageHis';

export default {
  name: 'AnalColUsageHis',
  components: {
    FileAttacherModal,
    TestMaterialOpenItemModal,
  },
  mounted() {
    this.getAnalColItemUsageHis();
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
            this.getAnalColItemUsageHisByItmCd(e);
            this.setDisabeldFormData(e.item.mngProcCd);
          },
        },
      },
      detail: {
        ...detail.static,
        forms: detail.forms(),
      },
      testMaterialOpenItemModal: {
        show: false,
        treeCd: 'R4000000',
      },
      fileAttacherModal: {
        fileIdx: 0,
        show: false,
      },
    };
  },
  methods: {
    async getAnalColItemUsageHis() {
      const parameter = FormUtil.getData(this.list.forms);
      const data = await this.list.$grid
        ._useLoader(() => this.$axios.get('/an/analColUsageHis', parameter))
        .then(({ data }) => data);

      this.list.$grid.setGridData(data);
      this.setTokenUser();
    },
    getAnalColItemUsageHisByItmCd(event) {
      FormUtil.setData(this.detail.forms, event.item);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.getAnalColItemUsageHis();
        this.initDetail();
        return;
      }
      if (name === 'init') {
        return this.initDetail();
      }
      if (name === 'use' || name === 'update') {
        return this.dataCheckBeforeSave(name);
      }
      if (name === 'delete') {
        return this.deleteTestMaterialManage();
      }
      if (name === 'fileSave') {
        return this.showModal('fileAttacherModal');
      }
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getAnalColItemUsageHis();
      }
    },
    inputFormEvent(event) {
      if (event.originEvent === 'openItemSearch') {
        return this.showModal(event.item.itemLabel);
      }
    },
    setDetailData(data) {
      FormUtil.setData(this.detail.forms, { useSeq: data });
      this.enableButtonWhenUpdate();
    },
    initDetail() {
      this.detail.forms = values.detail.forms();
      this.enableButtonWhenInit();
      this.setTokenUser();
    },
    dataCheckBeforeSave() {
      this.detail.forms.validate().then(() => {
        return FormUtil.existsValue(this.detail.forms, 'useSeq') ? this.update() : this.create();
      });
    },
    create() {
      const param = FormUtil.getData(this.detail.forms);

      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$eSign(() => this.$axios.post('/an/analColUsageHis', param))
          .then(({ data }) => {
            this.$info(this.$message.info.saved);
            this.getAnalColItemUsageHis();
            this.setDetailData(data);
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      });
    },
    update() {
      const param = FormUtil.getData(this.detail.forms);

      this.$confirm(this.$message.confirm.updated).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/an/analColUsageHis', param))
          .then(() => {
            this.$info(this.$message.info.updated);
            this.getAnalColItemUsageHis();
            this.enableButtonWhenUpdate();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    deleteTestMaterialManage() {
      const param = FormUtil.getData(this.detail.forms);

      this.$confirm(this.$message.confirm.deleted).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/an/analColUsageHis/delete', param))
          .then(() => {
            this.$info(this.$message.info.deleted);
            this.getAnalColItemUsageHis();
            this.initDetail();
          })
          .catch(() => {
            this.$error(this.$message.error.deleteData);
          });
      });
    },
    showModal(name) {
      if (name === 'testMaterialOpenItemModal') {
        return (this.testMaterialOpenItemModal.show = true);
      }
      if (name === 'fileAttacherModal') {
        this.fileAttacherModal.fileIdx = FormUtil.getValue(this.detail.forms, 'fileIdx');
        return (this.fileAttacherModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'testMaterialOpenItemModal') {
        return (this.testMaterialOpenItemModal.show = false);
      }
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
    },
    setTokenUser() {
      const useNm = TokenUtil.myName();
      const useUid = TokenUtil.myId();
      FormUtil.setData(this.detail.forms, { useUid });
      FormUtil.setData(this.detail.forms, { useNm });
    },
    openItemModalReturnDataEvent(data) {
      data.pitmNm = data.ancPitmNm;
      data.useDt = dayjs().format('YYYY-MM-DD');
      FormUtil.setData(this.detail.forms, data);
      this.setTokenUser();
      this.hideModal('testMaterialOpenItemModal');
    },
    enableButtonWhenInit() {
      FormUtil.enableButtons(this.detail.buttons, ['use']);
      FormUtil.disableButtons(this.detail.buttons, ['fileSave', 'update', 'delete']);
    },
    enableButtonWhenUpdate() {
      FormUtil.enableButtons(this.detail.buttons, ['fileSave', 'update', 'delete']);
      FormUtil.disableButtons(this.detail.buttons, ['use']);
    },
    enableButtonWhenDisposalItem() {
      FormUtil.disableButtons(this.detail.buttons, ['use', 'fileSave', 'update', 'delete']);
    },
    fileSave({ addedFiles, removedFileIds }) {
      const ritmEtrIdx = FormUtil.getValue(this.detail.forms, 'ritmEtrIdx');
      const fileIdx = Number(FormUtil.getValue(this.detail.forms, 'fileIdx'));
      const fileInfoList = { addedFiles, removedFileIds, ritmEtrIdx, fileIdx };

      this.$axios
        .postByForm('/an/analColReceipt/savedFile', fileInfoList)
        .then(({ data }) => {
          if (addedFiles.length == 0) {
            this.$info(this.$message.info.removedFiles);
          } else {
            this.$info(this.$message.info.savedFiles);
          }
          this.getAnalColItemUsageHis();
          this.getFileListByFileIdx(fileIdx, data);
        })
        .catch(() => {
          this.$error(this.$message.error.savedFiles);
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
      FormUtil.setData(this.detail.forms, { fileIdx });
    },
    setDisabeldFormData(mngProcCd) {
      const disposalRequestState = 'S0020200';
      if (mngProcCd === disposalRequestState) {
        this.enableButtonWhenUpdate();
        this.setEnableForms();
      } else {
        this.enableButtonWhenDisposalItem();
        FormUtil.disable(this.detail.forms);
      }
      FormUtil.disable(this.detail.forms, 'openItemSearch');
    },
    setEnableForms() {
      this.detail.forms = values.detail.forms();
      const selecteItem = this.list.$grid.getSelectedItems();
      this.getAnalColItemUsageHisByItmCd(selecteItem[0]);
    },
  },
};
</script>

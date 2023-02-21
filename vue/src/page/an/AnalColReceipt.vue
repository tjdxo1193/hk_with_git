<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <FormWithHeader v-bind="detail" @button-click="onClickButton" @form-event="inputFormEvent" />

  <TestMaterialItemModal
    :show="testMaterialItemModal.show"
    :treeCd="testMaterialItemModal.treeCd"
    @close="hideModal('testMaterialItemModal')"
    @modalReturnDataEvent="itemModalReturnDataEvent"
  />

  <BusinessCompanyModal
    :show="businessModal.show"
    @close="hideModal('businessModal')"
    @modalReturnDataEvent="vendorModalReturnDataEvent"
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
import { BusinessCompanyModal, FileAttacherModal, TestMaterialItemModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/analColReceipt';

export default {
  name: 'AnalColReceipt',
  components: {
    BusinessCompanyModal,
    FileAttacherModal,
    TestMaterialItemModal,
  },
  mounted() {
    this.getAnalColItem();
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
            this.getAnalColItemByRitmEtrIdx(e);
            this.setDisabeldFormData(e.item.etrProcCd);
          },
        },
      },
      detail: {
        ...detail.static,
        forms: detail.forms(),
      },
      testMaterialItemModal: {
        show: false,
        treeCd: 'R4000000',
      },
      businessModal: {
        show: false,
      },
      fileAttacherModal: {
        fileIdx: 0,
        show: false,
      },
    };
  },
  methods: {
    async getAnalColItem() {
      const parameter = FormUtil.getData(this.list.forms);
      const data = await this.list.$grid
        ._useLoader(() => this.$axios.get('/an/analColReceipt', parameter))
        .then(({ data }) => data);

      this.list.$grid.setGridData(data);
    },
    getAnalColItemByRitmEtrIdx(event) {
      const selectedItem = event.item;
      FormUtil.setData(this.detail.forms, selectedItem);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.getAnalColItem();
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
      if (name === 'update') {
        return this.dataCheckBeforeSave();
      }
      if (name === 'fileSave') {
        return this.showModal('fileAttacherModal');
      }
    },
    searchFormEvent(event) {
      const isUpperRitmTreeCd = event.item.name === 'upperRitmTreeCd';

      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getAnalColItem();
      }
      if (event.type === 'change' && isUpperRitmTreeCd) {
        return this.setRitmTreeCdCombo(this.list.forms, event.item.value);
      }
    },
    inputFormEvent(event) {
      if (event.originEvent === 'search') {
        return this.showModal(event.item.itemLabel);
      }
      if (event.originEvent === 'clear') {
        return FormUtil.setData(this.detail.forms, { vdrNm: '', vdrCd: '' });
      }
    },
    setDetailData(ritmEtrIdx) {
      FormUtil.setData(this.detail.forms, { ritmEtrIdx });
    },
    initDetail() {
      this.detail.forms = values.detail.forms();
      this.disableButtons();
    },
    dataCheckBeforeSave() {
      this.detail.forms.validate().then(() => {
        this.isExist() ? this.update() : this.create();
      });
    },
    isExist() {
      return FormUtil.existsValue(this.detail.forms, 'ritmEtrIdx');
    },
    create() {
      const param = FormUtil.getData(this.detail.forms);

      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$eSign(() => this.$axios.post('/an/analColReceipt', param))
          .then(({ data }) => {
            this.$info(this.$message.info.saved);
            this.getAnalColItem();
            this.setDetailData(data);
            this.enableButtons();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      });
    },
    update() {
      const param = FormUtil.getData(this.detail.forms);

      this.$confirm(this.$message.confirm.updated).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/an/analColReceipt', param))
          .then(() => {
            this.$info(this.$message.info.updated);
            this.getAnalColItem();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    delete() {
      const param = FormUtil.getData(this.detail.forms);

      this.$confirm(this.$message.confirm.deleted).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/an/analColReceipt/delete', param))
          .then(() => {
            this.$info(this.$message.info.deleted);
            this.getAnalColItem();
            this.initDetail();
          })
          .catch(() => {
            this.$error(this.$message.error.deleteData);
          });
      });
    },
    showModal(name) {
      if (name === 'testMaterialItemModal') {
        return (this.testMaterialItemModal.show = true);
      }
      if (name === 'businessModal' || name === 'supplier') {
        return (this.businessModal.show = true);
      }
      if (name === 'fileAttacherModal') {
        this.fileAttacherModal.fileIdx = FormUtil.getValue(this.detail.forms, 'fileIdx');
        return (this.fileAttacherModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'testMaterialItemModal') {
        return (this.testMaterialItemModal.show = false);
      }
      if (name === 'businessModal' || name === 'supplier') {
        return (this.businessModal.show = false);
      }
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
    },
    itemModalReturnDataEvent(data) {
      data.ritmRootCd = data.ritmTreeCd;
      data.ritmRootNm = data.ritmTreeNm;
      FormUtil.setData(this.detail.forms, data);
      this.hideModal('testMaterialItemModal');
    },
    vendorModalReturnDataEvent(data) {
      FormUtil.setData(this.detail.forms, data);
      this.hideModal('businessModal');
    },
    setDisabeldFormData(etrProcCd) {
      const completeApprove = 'S0010300';
      if (etrProcCd === completeApprove) {
        this.disableButtons(etrProcCd);
        FormUtil.disable(this.detail.forms);
      } else {
        this.enableButtons();
        this.setEnableForms();
      }
    },
    setEnableForms() {
      this.detail.forms = values.detail.forms();
      const selecteItem = this.list.$grid.getSelectedItems();
      this.getAnalColItemByRitmEtrIdx(selecteItem[0]);
    },
    enableButtons() {
      FormUtil.enableButtons(this.detail.buttons, ['fileSave', 'update', 'delete']);
      FormUtil.disableButtons(this.detail.buttons, ['save']);
    },
    disableButtons(etrProcCd) {
      const completeApprove = 'S0010300';

      if (etrProcCd === completeApprove) {
        FormUtil.disableButtons(this.detail.buttons, ['save', 'fileSave', 'update', 'delete']);
      } else {
        FormUtil.disableButtons(this.detail.buttons, ['fileSave', 'update', 'delete']);
        FormUtil.enableButtons(this.detail.buttons, ['save']);
      }
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
          this.getAnalColItem();
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
    setRitmTreeCdCombo(forms, upperRitmTreeCd, initValue = '') {
      forms.map((item) => {
        if (item.name == 'ritmTreeCd') {
          item.value = initValue;
          item.async(upperRitmTreeCd).then(({ data }) => (item.elements = data));
        }
      });
    },
  },
};
</script>

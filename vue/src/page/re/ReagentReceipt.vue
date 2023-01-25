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
import dayjs from 'dayjs';

import { BusinessCompanyModal, FileAttacherModal, TestMaterialItemModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/reagentReceipt';

export default {
  name: 'ReagentReceipt',
  components: {
    BusinessCompanyModal,
    FileAttacherModal,
    TestMaterialItemModal,
  },
  mounted() {
    this.getReagentItem();
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
            this.getReagentItemByRitmEtrIdx(e);
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
        treeCd: 'R1000000',
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
    async getReagentItem() {
      const parameter = FormUtil.getData(this.list.forms);
      const data = await this.list.$grid
        ._useLoader(() => this.$axios.get('/re/reagentReceipt', parameter))
        .then(({ data }) => data);

      this.list.$grid.setGridData(data);
    },
    getReagentItemByRitmEtrIdx(event) {
      const selectedItem = event.item;
      FormUtil.setData(this.detail.forms, selectedItem);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.getReagentItem();
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
        return this.getReagentItem();
      }
      if (event.type === 'change' && isUpperRitmTreeCd) {
        return this.setRitmTreeCdCombo(this.list.forms, event.item.value);
      }
    },
    inputFormEvent(event) {
      const isRitmUnitCd = event.item.name === 'ritmUnitCd';
      const isExpirDtChk = event.item.name === 'expirDtChk';
      const isInputQty = event.item.name === 'etrQty' || event.item.name === 'etrEachQty';

      if (event.originEvent === 'search') {
        return this.showModal(event.item.itemLabel);
      }
      if (event.originEvent === 'clear') {
        return FormUtil.setData(this.detail.forms, { vdrNm: '', vdrCd: '' });
      }
      if (event.type === 'change' && isRitmUnitCd) {
        const ritmUnitCd = event.item.value;
        return FormUtil.setData(this.detail.forms, { ritmUnitCd });
      }
      if (event.type === 'change' && isExpirDtChk) {
        return this.setDisabledColumn(event.item.value);
      }
      if (event.type === 'input' && isInputQty) {
        return this.calculateEtrTotQty();
      }
    },
    calculateEtrTotQty() {
      const etrQty = FormUtil.getValue(this.detail.forms, 'etrQty');
      const etrEachQty = FormUtil.getValue(this.detail.forms, 'etrEachQty');

      const etrTotQty = Number.parseInt(etrQty * etrEachQty);

      if (isNaN(etrTotQty)) {
        FormUtil.setData(this.detail.forms, { etrTotQty: 0 });
      }

      if (etrTotQty >= 0) {
        FormUtil.setData(this.detail.forms, { etrTotQty });
      }
    },
    setDisabledColumn(value) {
      const manualSelection = 1;
      const periodSelection = 2;
      const noLimitSelection = 3;

      if (value == manualSelection) {
        this.isCurrLotYn('N');
        FormUtil.enable(this.detail.forms, 'opnBefExpirDt');
        FormUtil.disable(this.detail.forms, 'opnAftExpirTrm');
        return;
      }
      if (value == periodSelection) {
        this.isCurrLotYn('N');
        FormUtil.enable(this.detail.forms, 'opnAftExpirTrm');
        FormUtil.disable(this.detail.forms, 'opnBefExpirDt');
        return;
      }
      if (value == noLimitSelection) {
        this.isCurrLotYn('Y');
        FormUtil.disable(this.detail.forms, ['opnBefExpirDt', 'opnAftExpirTrm']);
        return;
      }
    },
    isCurrLotYn(currLotYn) {
      return FormUtil.setData(this.detail.forms, { currLotYn });
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
        this.$eSign(() => this.$axios.post('/re/reagentReceipt', param))
          .then(({ data }) => {
            this.$info(this.$message.info.saved);
            this.getReagentItem();
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
        this.$eSignWithReason(() => this.$axios.put('/re/reagentReceipt', param))
          .then(() => {
            this.$info(this.$message.info.updated);
            this.getReagentItem();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    delete() {
      const param = FormUtil.getData(this.detail.forms);

      this.$confirm(this.$message.confirm.deleted).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/re/reagentReceipt/delete', param))
          .then(() => {
            this.$info(this.$message.info.deleted);
            this.getReagentItem();
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
      data.etrRmk = data.ritmRmk;
      if (data.opnAftExpirTrm != null) {
        const todayDate = dayjs().format('YYYY-MM-DD');
        data.opnBefExpirDt = dayjs(todayDate)
          .add(data.opnAftExpirTrm, 'month')
          .subtract(1, 'day')
          .format('YYYY-MM-DD');
      }
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
      this.getReagentItemByRitmEtrIdx(selecteItem[0]);
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
        .postByForm('/re/reagentReceipt/savedFile', fileInfoList)
        .then(({ data }) => {
          if (addedFiles.length == 0) {
            this.$info(this.$message.info.removedFiles);
          } else {
            this.$info(this.$message.info.savedFiles);
          }
          this.getReagentItem();
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

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
  ></TestMaterialItemModal>

  <BusinessCompanyModal
    :show="businessModal.show"
    @close="hideModal('businessModal')"
    @modalReturnDataEvent="vendorModalReturnDataEvent"
  ></BusinessCompanyModal>

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
    @modalReturnDataEvent="approveModalReturnDataEvent"
  ></RequestApproverModal>
</template>

<script>
import dayjs from 'dayjs';

import {
  BusinessCompanyModal,
  FileAttacherModal,
  TestMaterialItemModal,
  RequestApproverModal,
} from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/stdItemReceipt';

export default {
  name: 'StdItemReceipt',
  components: {
    BusinessCompanyModal,
    FileAttacherModal,
    TestMaterialItemModal,
    RequestApproverModal,
  },
  mounted() {
    this.getStdItem();
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
            this.getStdItemByItmCd(e);
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
        treeCd: 'R3000000',
      },
      businessModal: {
        show: false,
      },
      fileAttacherModal: {
        fileIdx: 0,
        show: false,
      },
      requestApproverModal: {
        show: false,
        aprReqDiv: 'S0050001',
      },
    };
  },
  methods: {
    async getStdItem() {
      const parameter = FormUtil.getData(this.list.forms);
      const data = await this.list.$grid
        ._useLoader(() => this.$axios.get('/sd/stdItemReceipt', parameter))
        .then(({ data }) => data);

      this.list.$grid.setGridData(data);
    },
    getStdItemByItmCd(event) {
      const selectedItem = event.item;
      this.setCheckedValue(selectedItem);
      this.setDisabledColumn(selectedItem.expirDtChk);
      FormUtil.setData(this.detail.forms, selectedItem);
    },
    setCheckedValue(data) {
      const item = this.detail.forms.filter((column) => column.name === 'extraYn');
      const checkedValue = item[0].groups
        .map((checkbox) => checkbox.checkedValue)
        .filter((group) => data[group] === 'Y');

      this.detail.forms.filter((column) => {
        if (column.name === 'extraYn') {
          column.value = checkedValue;
        }
        if (column.name === 'makDt') {
          column.disabled = data.makDt != null ? false : true;
        }
      });
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.getStdItem();
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
      if (name === 'approveReqest') {
        const checkedRows = this.list.$grid.getCheckedRowItems();
        if (checkedRows.length > 0) {
          return this.showModal('requestApproverModal');
        } else {
          return this.$warn(this.$message.warn.unSelectedData);
        }
      }
    },
    searchFormEvent(event) {
      const isUpperRitmTreeCd = event.item.name === 'upperRitmTreeCd';

      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getStdItem();
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
      if (param.extraYn == '') {
        param.extraYn = [];
      }
      const checkboxGroups = param.extraYn.reduce((acc, v) => {
        acc[v] = 'Y';
        return acc;
      }, {});

      this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$eSign(() => this.$axios.post('/sd/stdItemReceipt', { ...param, ...checkboxGroups }))
          .then(({ data }) => {
            this.$info(this.$message.info.saved);
            this.getStdItem();
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
      if (param.extraYn == '') {
        param.extraYn = [];
      }
      const checkboxGroups = param.extraYn.reduce((acc, v) => {
        acc[v] = 'Y';
        return acc;
      }, {});

      this.$confirm(this.$message.confirm.updated).then(() => {
        this.$eSignWithReason(() =>
          this.$axios.put('/sd/stdItemReceipt', { ...param, ...checkboxGroups }),
        )
          .then(() => {
            this.$info(this.$message.info.updated);
            this.getStdItem();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    delete() {
      const param = FormUtil.getData(this.detail.forms);

      this.$confirm(this.$message.confirm.deleted).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/sd/stdItemReceipt/delete', param))
          .then(() => {
            this.$info(this.$message.info.deleted);
            this.getStdItem();
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
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = true);
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
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = false);
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

      this.setCheckedValue(data);
      FormUtil.setData(this.detail.forms, data);
      this.hideModal('testMaterialItemModal');
    },
    vendorModalReturnDataEvent(data) {
      FormUtil.setData(this.detail.forms, data);
      this.hideModal('businessModal');
    },
    approveModalReturnDataEvent(approveInfo) {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const param = checkedRows.map((row) => ({
        ...row.item,
        approveInfo,
      }));

      this.$eSignWithReason(() => this.$axios.put('/sd/stdItemReceipt/requestApprove', param))
        .then(() => {
          this.$info(this.$message.info.approveRequest);
          this.getStdItem();
          this.initDetail();
        })
        .catch(() => {
          this.$error(this.$message.error.saveData);
        });
    },
    setDisabeldFormData(etrProcCd) {
      const completeApprove = 'S0010300';
      const reqeustApprove = 'S0010200';

      if (etrProcCd === completeApprove || etrProcCd === reqeustApprove) {
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
      this.getStdItemByItmCd(selecteItem[0]);
    },
    enableButtons() {
      FormUtil.enableButtons(this.detail.buttons, ['fileSave', 'update', 'delete']);
      FormUtil.disableButtons(this.detail.buttons, ['save']);
    },
    disableButtons(etrProcCd) {
      const beforeApproveRequest = ['fileSave', 'update', 'delete'];
      const afterApproveRequest = ['fileSave', 'update', 'delete', 'save'];

      if (etrProcCd) {
        FormUtil.disableButtons(this.detail.buttons, afterApproveRequest);
      } else {
        FormUtil.disableButtons(this.detail.buttons, beforeApproveRequest);
        FormUtil.enableButtons(this.detail.buttons, ['save']);
      }
    },
    fileSave({ addedFiles, removedFileIds }) {
      const ritmEtrIdx = FormUtil.getValue(this.detail.forms, 'ritmEtrIdx');
      const fileIdx = Number(FormUtil.getValue(this.detail.forms, 'fileIdx'));
      const fileInfoList = { addedFiles, removedFileIds, ritmEtrIdx, fileIdx };

      this.$axios
        .postByForm('/sd/stdItemReceipt/savedFile', fileInfoList)
        .then(({ data }) => {
          if (addedFiles.length == 0) {
            this.$info(this.$message.info.removedFiles);
          } else {
            this.$info(this.$message.info.savedFiles);
          }
          this.getStdItem();
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

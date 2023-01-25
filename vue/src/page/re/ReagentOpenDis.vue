<template>
  <AUIGridSearch
    v-bind="list"
    :columns="computedListColumns"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <FileAttacherModal
    ref="fileAttacherModal"
    :show="fileAttacherModal.show"
    :fileIdx="fileAttacherModal.fileIdx"
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

import { FileAttacherModal, RequestApproverModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/reagentOpenDis';

export default {
  name: 'ReagentOpenDis',
  components: {
    FileAttacherModal,
    RequestApproverModal,
  },
  mounted() {
    this.getReagentOpenDis();
  },
  data() {
    const { list } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: () => {
            this.enableButtons(['fileSave']);
          },
        },
      },
      fileAttacherModal: {
        show: false,
        fileIdx: 0,
      },
      requestApproverModal: {
        show: false,
        aprReqDiv: 'S0050002',
      },
    };
  },
  methods: {
    async getReagentOpenDis() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/re/reagentOpenDis', parameter))
        .then(({ data }) => this.setOpnDtAndExpirDt(data));

      $grid.setGridData(data);
      this.setChangeButtonsState(parameter.mngProcCd);
      this.showAndHideDpsReaColumn(parameter.mngProcCd);
    },
    setOpnDtAndExpirDt(data) {
      const todayDate = dayjs().format('YYYY-MM-DD');
      return data.map((row) => ({
        ...row,
        opnDt: todayDate,
        expirDt: this.isExpirDtChk(row.expirDtChk, row.opnBefExpirDt, row.opnAftExpirTrm),
      }));
    },
    isExpirDtChk(expirDtChk, opnBefExpirDt, opnAftExpirTrm) {
      /**
       * 개봉일, 개봉 후 사용기한 초기값 세팅
       * 0. 개봉일 초기값 = 오늘
       * 1. EXPIR_DT_CHK의 값이 1(직접선택)일 때,
       *    개봉 후 사용기한 = 입고 시 입력한 사용기한
       * 2. EXPIR_DT_CHK의 값이 2(기간선택)일 때,
       *    개봉 후 사용기한 = 오늘 + 입고 시 입력한 사용기간(number(1=한달))
       * 3. EXPIR_DT_CHK의 값이 3(기한없음)일 때,
       *    개봉 후 사용기한 = '9999-12-31'
       */
      const manualSelection = 1;
      const periodSelection = 2;
      const todayDate = dayjs().format('YYYY-MM-DD');
      const expireDate = dayjs(todayDate).add(opnAftExpirTrm, 'month').format('YYYY-MM-DD');
      const noLimitDate = '9999-12-31';

      return expirDtChk == manualSelection
        ? opnBefExpirDt
        : expirDtChk == periodSelection
        ? expireDate
        : noLimitDate;
    },
    onClickButton({ name }) {
      if (name === 'search') {
        return this.getReagentOpenDis();
      }
      if (name === 'fileSave') {
        return this.showModal('fileAttacherModal');
      }
      if (name === 'openItem' && !this.dataCheckBeforeSave()) {
        return this.open();
      }
      if (name === 'disposalItem' && !this.dataCheckBeforeSave()) {
        this.$confirm(this.$message.confirm.disposalRequest).then(() => {
          return this.showModal('requestApproverModal');
        });
      }
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getReagentOpenDis();
      }
      if (event.type === 'change' && event.item.name === 'upperRitmTreeCd') {
        return this.setRitmTreeCdCombo(this.list.forms, event.item.value);
      }
    },
    isBiggerThanExpirDt(parameter) {
      return parameter.filter((row) => row.opnDt > row.expirDt).length > 0 ? true : false;
    },
    isBiggerThanEtrDt(parameter) {
      return parameter.filter((row) => row.opnDt < row.etrDt).length > 0 ? true : false;
    },
    isUnMatchedExpirDt(parameter) {
      const manualSelection = 1;
      const periodSelection = 2;

      return parameter.filter((row) => {
        if (row.expirDtChk == manualSelection) {
          return row.expirDt > row.opnBefExpirDt;
        }
        if (row.expirDtChk == periodSelection) {
          const expireDate = dayjs(row.opnDt).add(row.opnAftExpirTrm, 'month').format('YYYY-MM-DD');
          return row.expirDt > expireDate;
        }
      }).length > 0
        ? true
        : false;
    },
    dataCheckBeforeSave() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      if (checkedRows.length) {
        const parameter = checkedRows.map((row) => row.item);
        return this.isBiggerThanExpirDt(parameter)
          ? this.$warn(this.$message.warn.biggerThanExpirDt)
          : this.isBiggerThanEtrDt(parameter)
          ? this.$warn(this.$message.warn.biggerThanEtrDt)
          : this.isUnMatchedExpirDt(parameter)
          ? this.$warn(this.$message.warn.unMatchedExpirDt)
          : false;
      } else {
        return this.$warn(this.$message.warn.unSelectedData);
      }
    },
    open() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const parameter = checkedRows.map((row) => row.item);

      if (checkedRows.length > 0) {
        this.$confirm(this.$message.confirm.opened).then(() => {
          this.$eSign(() => this.$axios.put('/re/reagentOpenDis/open', parameter))
            .then(() => {
              this.$info(this.$message.info.opened);
              this.getReagentOpenDis();
            })
            .catch(() => {
              this.$error(this.$message.error.updateData);
            });
        });
      } else {
        this.$warn(this.$message.warn.unSelectedData);
      }
    },
    approveModalReturnDataEvent(approveInfo) {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const parameter = checkedRows.map((row) => ({
        ...row.item,
        approveInfo,
      }));

      if (checkedRows.length > 0) {
        this.$eSign(() => this.$axios.put('/re/reagentOpenDis/requestDisposal', parameter))
          .then(() => {
            this.$info(this.$message.info.disposalRequest);
            this.getReagentOpenDis();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      } else {
        this.$warn(this.$message.warn.unSelectedData);
      }
    },
    getFildIdx() {
      const selectedItem = this.list.$grid.getSelectedItems();
      return selectedItem[0].item.fileIdx;
    },
    getRitmEtrIdx() {
      const selectedItem = this.list.$grid.getSelectedItems();
      return selectedItem[0].item.ritmEtrIdx;
    },
    showModal(name) {
      if (name === 'fileAttacherModal') {
        this.fileAttacherModal.fileIdx = this.getFildIdx();
        return (this.fileAttacherModal.show = true);
      }
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = false);
      }
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.list.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.list.buttons, buttons);
    },
    setChangeButtonsState(mngProcCd) {
      const openState = 'S0020100';
      const disposalRequestState = 'S0020200';

      if (mngProcCd === openState) {
        this.enableButtons(['openItem']);
        this.disableButtons(['fileSave', 'disposalItem']);
        return;
      }
      if (mngProcCd === disposalRequestState) {
        this.enableButtons(['disposalItem']);
        this.disableButtons(['fileSave', 'openItem']);
        return;
      }
    },
    showAndHideDpsReaColumn(mngProcCd) {
      const openState = 'S0020100';
      const disposalRequestState = 'S0020200';

      if (mngProcCd === openState) {
        return this.list.$grid.hideColumnByDataField(['dpsRea']);
      }
      if (mngProcCd === disposalRequestState) {
        return this.list.$grid.showColumnByDataField(['dpsRea']);
      }
    },
    setRitmTreeCdCombo(forms, upperRitmTreeCd, initValue = '') {
      forms.map((item) => {
        if (item.name == 'ritmTreeCd') {
          item.value = initValue;
          item.async(upperRitmTreeCd).then(({ data }) => (item.elements = data));
        }
      });
    },
    fileSave({ addedFiles, removedFileIds }) {
      const ritmEtrIdx = this.getRitmEtrIdx();
      const fileIdx = Number(this.getFildIdx());
      const fileInfoList = { addedFiles, removedFileIds, ritmEtrIdx, fileIdx };

      this.$axios
        .postByForm('/re/reagentReceipt/savedFile', fileInfoList)
        .then(({ data }) => {
          if (addedFiles.length == 0) {
            this.$info(this.$message.info.removedFiles);
          } else {
            this.$info(this.$message.info.savedFiles);
          }
          this.getReagentOpenDis();
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
    },
  },
  computed: {
    computedListColumns() {
      const openColumns = ['opnDt', 'expirDt', 'opnRmk'];
      const disposalReaColumns = ['dpsRea'];
      const openItemButtonState = this.list.buttons.filter(
        (button) => button.name === 'openItem' && button.disabled,
      );

      return this.list.columns.map((col) => ({
        ...col,
        visible:
          openItemButtonState.length && disposalReaColumns.includes(col.dataField)
            ? true
            : col.visible,
        editable:
          !openItemButtonState.length && openColumns.includes(col.dataField)
            ? true
            : openItemButtonState.length && disposalReaColumns.includes(col.dataField)
            ? true
            : false,
      }));
    },
  },
};
</script>

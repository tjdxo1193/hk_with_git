<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <FileAttacherModal
    ref="fileAttacherModal"
    :show="fileAttacherModal.show"
    :fileIdx="fileAttacherModal.fileIdx"
    @save="fileSave"
    @close="hideModal"
  />
</template>

<script>
import { FileAttacherModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/stdItemOpenCancel';

export default {
  name: 'StdItemOpenCancel',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getStdOpenCancel();
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
          selectionChange: (e) => {
            this.setSelectedRowInfo(e.primeCell.item);
          },
        },
      },
      fileAttacherModal: {
        fileIdx: 0,
        show: false,
      },
      selectedRowInfo: {
        fileIdx: 0,
        ritmEtrIdx: 0,
      },
    };
  },
  methods: {
    async getStdOpenCancel() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/sd/stdItemOpenCancel', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
      this.disableButtons(['fileSave']);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        return this.getStdOpenCancel();
      }
      if (name === 'cancel') {
        return this.cancel();
      }
      if (name === 'fileSave') {
        return this.showModal();
      }
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getStdOpenCancel();
      }
    },
    isUsedItem() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      return checkedRows.filter((col) => col.item.leftOverStok > 0);
    },
    cancel() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const parameter = checkedRows.map((row) => row.item);

      if (this.isUsedItem().length > 0) {
        return this.$warn(this.$message.warn.useItemNotCancel);
      }

      if (checkedRows.length > 0) {
        this.$confirm(this.$message.confirm.canceld).then(() => {
          this.$eSignWithReason(() => this.$axios.put('/sd/stdItemOpenCancel', parameter))
            .then(() => {
              this.$info(this.$message.info.canceld);
              this.getStdOpenCancel();
            })
            .catch(() => {
              this.$error(this.$message.error.updateData);
            });
        });
      } else {
        this.$warn(this.$message.warn.unSelectedData);
      }
    },
    showModal() {
      this.fileAttacherModal.fileIdx = this.selectedRowInfo.fileIdx;
      this.fileAttacherModal.show = true;
    },
    hideModal() {
      this.fileAttacherModal.show = false;
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.list.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.list.buttons, buttons);
    },
    fileSave({ addedFiles, removedFileIds }) {
      const ritmEtrIdx = this.selectedRowInfo.ritmEtrIdx;
      const fileIdx = Number(this.selectedRowInfo.fileIdx);
      const fileInfoList = { addedFiles, removedFileIds, ritmEtrIdx, fileIdx };

      this.$axios
        .postByForm('/sd/stdItemReceipt/savedFile', fileInfoList)
        .then(({ data }) => {
          if (addedFiles.length == 0) {
            this.$info(this.$message.info.removedFiles);
          } else {
            this.$info(this.$message.info.savedFiles);
          }
          this.getStdOpenCancel();
          this.getFileListByFileIdx(fileIdx, data);
        })
        .catch(() => {
          this.$error(this.$message.error.savedFiles);
        });
    },
    getFileListByFileIdx(originFileIdx, fileIdx) {
      if (originFileIdx > 0) {
        this.selectedRowInfo.fileIdx = originFileIdx;
        return this.$refs.fileAttacherModal.getFileList();
      } else {
        this.selectedRowInfo.fileIdx = fileIdx;
        return this.setInitFileIdx(fileIdx);
      }
    },
    setInitFileIdx(fileIdx) {
      this.fileAttacherModal.fileIdx = fileIdx;
    },
    setSelectedRowInfo(item) {
      this.selectedRowInfo.fileIdx = item.fileIdx;
      this.selectedRowInfo.ritmEtrIdx = item.ritmEtrIdx;
    },
  },
};
</script>

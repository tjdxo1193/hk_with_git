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

import values from './values/reagentOpenCancel';

export default {
  name: 'ReagentOpenCancel',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getReagentOpenCancel();
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
        fileIdx: 0,
        show: false,
      },
    };
  },
  methods: {
    async getReagentOpenCancel() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/re/reagentOpenCancel', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
      this.disableButtons(['fileSave']);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        return this.getReagentOpenCancel();
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
        return this.getReagentOpenCancel();
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
          this.$eSignWithReason(() => this.$axios.put('/re/reagentOpenCancel', parameter))
            .then(() => {
              this.$info(this.$message.info.canceld);
              this.getReagentOpenCancel();
            })
            .catch(() => {
              this.$error(this.$message.error.updateData);
            });
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
    showModal() {
      this.fileAttacherModal.fileIdx = this.getFildIdx();
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
          this.getReagentOpenCancel();
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
};
</script>

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

import values from './values/glassOpenCancel';

export default {
  name: 'GlassOpenCancel',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getGlassOpenCancel();
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
    async getGlassOpenCancel() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/gl/glassOpenCancel', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
      this.disableButtons(['fileSave']);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        return this.getGlassOpenCancel();
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
        return this.getGlassOpenCancel();
      }
    },
    cancel() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const parameter = checkedRows.map((row) => row.item);

      if (checkedRows.length > 0) {
        this.$confirm(this.$message.confirm.canceld).then(() => {
          this.$eSignWithReason(() => this.$axios.put('/gl/glassOpenCancel', parameter))
            .then(() => {
              this.$info(this.$message.info.canceld);
              this.getGlassOpenCancel();
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
        .postByForm('/gl/glassReceipt/savedFile', fileInfoList)
        .then(({ data }) => {
          if (addedFiles.length == 0) {
            this.$info(this.$message.info.removedFiles);
          } else {
            this.$info(this.$message.info.savedFiles);
          }
          this.getGlassOpenCancel();
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

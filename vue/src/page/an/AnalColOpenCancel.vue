<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <Card>
    <ActionBar :buttons="buttonGroups.buttons" @button-click="onClickButton" />

    <Horizontal align-items="center" :spans="[5, 5]">
      <FormWithHeader v-bind="detailColumn" />

      <FormWithHeader v-bind="detailInfo" />
    </Horizontal>
  </Card>

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

import values from './values/analColOpenCancel';

export default {
  name: 'AnalColOpenCancel',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getAnalColOpenCancel();
  },
  data() {
    const { list, detailColumn, detailInfo, buttonGroups } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
        event: {
          cellDoubleClick: (e) => {
            this.getAnalColOpenCancelByRitmMngIdx(e);
          },
        },
      },
      detailColumn: {
        ...detailColumn.static,
        forms: detailColumn.forms(),
      },
      detailInfo: {
        ...detailInfo.static,
        forms: detailInfo.forms(),
      },
      buttonGroups: {
        buttons: buttonGroups.buttons,
      },
      fileAttacherModal: {
        fileIdx: 0,
        show: false,
      },
    };
  },
  methods: {
    async getAnalColOpenCancel() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/an/analColOpenCancel', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    getAnalColOpenCancelByRitmMngIdx(event) {
      const selectedItem = event.item;

      FormUtil.setData(this.detailColumn.forms, selectedItem);
      FormUtil.setData(this.detailInfo.forms, selectedItem);
      this.enableButtons(['fileSave', 'cancel']);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        return this.initDetail();
      }
      if (name === 'cancel') {
        return this.cancel();
      }
      if (name === 'fileSave') {
        return this.showModal();
      }
      if (name === 'init') {
        return this.initDetail();
      }
    },
    initDetail() {
      this.detailColumn.forms = values.detailColumn.forms();
      this.detailInfo.forms = values.detailInfo.forms();
      this.disableButtons(['fileSave', 'cancel']);
      this.getAnalColOpenCancel();
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getAnalColOpenCancel();
      }
    },
    isUsedItem() {
      return FormUtil.getValue(this.detailInfo.forms, 'analCnt') > 0 ? true : false;
    },
    cancel() {
      if (this.isUsedItem()) {
        return this.$warn(this.$message.warn.useItemNotCancel);
      }

      this.$confirm(this.$message.confirm.canceld).then(() => {
        const ritmMngIdx = FormUtil.getValue(this.detailInfo.forms, 'ritmMngIdx');
        this.$eSignWithReason(() => this.$axios.put('/an/analColOpenCancel', { ritmMngIdx }))
          .then(() => {
            this.$info(this.$message.info.canceld);
            this.getAnalColOpenCancel();
            this.initDetail();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    showModal() {
      this.fileAttacherModal.fileIdx = FormUtil.getValue(this.detailInfo.forms, 'fileIdx');
      this.fileAttacherModal.show = true;
    },
    hideModal() {
      this.fileAttacherModal.show = false;
    },
    enableButtons(buttons) {
      FormUtil.enableButtons(this.buttonGroups.buttons, buttons);
    },
    disableButtons(buttons) {
      FormUtil.disableButtons(this.buttonGroups.buttons, buttons);
    },
    fileSave({ addedFiles, removedFileIds }) {
      const ritmEtrIdx = FormUtil.getValue(this.detailInfo.forms, 'ritmEtrIdx');
      const fileIdx = Number(FormUtil.getValue(this.detailInfo.forms, 'fileIdx'));
      const fileInfoList = { addedFiles, removedFileIds, ritmEtrIdx, fileIdx };

      this.$axios
        .postByForm('/an/analColReceipt/savedFile', fileInfoList)
        .then(({ data }) => {
          if (addedFiles.length == 0) {
            this.$info(this.$message.info.removedFiles);
          } else {
            this.$info(this.$message.info.savedFiles);
          }
          this.getAnalColOpenCancel();
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
      FormUtil.setData(this.detailInfo.forms, { fileIdx });
    },
  },
};
</script>

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

import values from './values/glassLabelPrint';

export default {
  name: 'GlassLabelPrint',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getGlassLabelPrint();
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
            this.enableButtons();
          },
        },
      },
      fileAttacherModal: {
        show: false,
        fileIdx: 0,
      },
    };
  },
  methods: {
    async getGlassLabelPrint() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/gl/glassLabelPrint', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
      this.disableButtons();
    },
    onClickButton({ name }) {
      if (name === 'search') {
        return this.getGlassLabelPrint();
      }
      if (name === 'fileSave') {
        return this.showModal();
      }
      if (name === 'print') {
        return this.print();
      }
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getGlassLabelPrint();
      }
      if (event.type === 'change' && event.item.name === 'upperRitmTreeCd') {
        return this.setRitmTreeCdCombo(this.list.forms, event.item.value);
      }
    },
    print() {
      const selectedItem = this.list.$grid.getSelectedItems();
      if (selectedItem.length == 0) {
        return this.$warn(this.$message.warn.unSelectedData);
      }
      this.$confirm(this.$message.confirm.printed).then(() => {
        if (selectedItem.length > 0) {
          this.$eSign(() => this.$axios.put('/gl/glassLabelPrint', selectedItem[0].item))
            .then(() => {
              this.getGlassLabelPrint();
              // TODO ?????? RD
              alert('?????? ????????? ?????? ?????? RD');
            })
            .catch(() => {
              this.$error(this.$message.error.printData);
            });
        }
      });
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
    enableButtons() {
      FormUtil.enableButtons(this.list.buttons, ['fileSave']);
    },
    disableButtons() {
      FormUtil.disableButtons(this.list.buttons, ['fileSave']);
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
        .postByForm('/gl/glassReceipt/savedFile', fileInfoList)
        .then(({ data }) => {
          if (addedFiles.length == 0) {
            this.$info(this.$message.info.removedFiles);
          } else {
            this.$info(this.$message.info.savedFiles);
          }
          this.getGlassLabelPrint();
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

<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <FileAttacherModal
    :show="fileAttacherModal.show"
    :fileIdx="fileAttacherModal.fileIdx"
    :readonly="fileAttacherModal.readonly"
    @close="hideModal"
  />
</template>

<script>
import { FileAttacherModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/reagentDisAppr';

export default {
  name: 'ReagentDisAppr',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getReagentDisAppr();
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
        readonly: true,
      },
    };
  },
  methods: {
    async getReagentDisAppr() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/re/reagentDisAppr', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
      this.disableButtons(['fileSave']);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.getReagentDisAppr();
      }
      if (name === 'approve') {
        this.approve();
      }
      if (name === 'reject') {
        this.reject();
      }
      if (name === 'fileSave') {
        return this.showModal();
      }
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        this.getReagentDisAppr();
      }
      if (event.type === 'change' && event.item.name === 'upperRitmTreeCd') {
        const hirTreeCd = event.item.value;
        this.list.forms.map((item) => {
          if (item.name == 'ritmTreeCd') {
            item.async(hirTreeCd).then(({ data }) => (item.elements = data));
          }
        });
      }
    },
    approve() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const parameter = checkedRows.map((row) => row.item);

      if (checkedRows.length > 0) {
        this.$confirm(this.$message.confirm.approved).then(() => {
          this.$eSignWithReason(() => this.$axios.put('/re/reagentDisAppr/approve', parameter))
            .then(() => {
              this.$info(this.$message.info.approve);
              this.getReagentDisAppr();
            })
            .catch(() => {
              this.$error(this.$message.error.updateData);
            });
        });
      } else {
        this.$warn(this.$message.warn.unSelectedData);
      }
    },
    reject() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const parameter = checkedRows.map((row) => row.item);

      if (checkedRows.length > 0) {
        this.$confirm(this.$message.confirm.rejected).then(() => {
          this.$eSignWithReason(() => this.$axios.put('/re/reagentDisAppr/reject', parameter))
            .then(() => {
              this.$info(this.$message.info.reject);
              this.getReagentDisAppr();
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
  },
};
</script>

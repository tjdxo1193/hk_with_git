<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
    @grid-button-click="gridButtonClick"
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

import values from './values/stdItemAppr';

export default {
  name: 'StdItemAppr',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getStdItemAppr();
  },
  data() {
    const { list } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
        columns: list.columns(),
      },
      fileAttacherModal: {
        fileIdx: 0,
        show: false,
        readonly: true,
      },
    };
  },
  methods: {
    async getStdItemAppr() {
      const { $grid, forms } = this.list;
      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/sd/stdItemAppr', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        return this.getStdItemAppr();
      }
      if (name === 'approve') {
        const checkedRows = this.list.$grid.getCheckedRowItems();
        if (checkedRows.length > 0) {
          return this.approve();
        } else {
          return this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'reject') {
        const checkedRows = this.list.$grid.getCheckedRowItems();
        if (checkedRows.length > 0) {
          return this.reject();
        } else {
          return this.$warn(this.$message.warn.unSelectedData);
        }
      }
    },
    gridButtonClick(event) {
      if (event.dataField === 'fileAttacher') {
        return this.showModal(event.item);
      }
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getStdItemAppr();
      }
      if (event.type === 'change' && event.item.name === 'upperRitmTreeCd') {
        return this.setRitmTreeCdCombo(this.list.forms, event.item.value);
      }
    },
    approve() {
      const completeApprove = 'S0010300';
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const param = checkedRows.map((row) => ({
        ...row.item,
        etrProcCd: completeApprove,
      }));

      this.$confirm(this.$message.confirm.approved).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/sd/stdItemAppr/approve', param))
          .then(() => {
            this.$info(this.$message.info.approve);
            this.getStdItemAppr();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      });
    },
    reject() {
      const requestApproveReject = 'S0010110';
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const param = checkedRows.map((row) => ({
        ...row.item,
        etrProcCd: requestApproveReject,
      }));

      this.$confirm(this.$message.confirm.rejected).then(() => {
        this.$eSignWithReason(() => this.$axios.put('/sd/stdItemAppr/reject', param))
          .then(() => {
            this.$info(this.$message.info.reject);
            this.getStdItemAppr();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    showModal(item) {
      this.fileAttacherModal.fileIdx = item.fileIdx;
      this.fileAttacherModal.show = true;
    },
    hideModal() {
      this.fileAttacherModal.show = false;
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

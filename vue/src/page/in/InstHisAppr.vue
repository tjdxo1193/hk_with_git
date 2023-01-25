<template>
  <AUIGridSearch
    v-bind="list"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('list.$grid', proxy)"
    @form-event="searchFormEvent"
  />

  <AUIGridWithHeader v-bind="detail" @grid-created="(proxy) => $setState('detail.$grid', proxy)" />

  <FileAttacherModal
    :fileIdx="fileAttacherModal.fileIdx"
    :show="fileAttacherModal.show"
    :readonly="fileAttacherModal.readonly"
    @close="hideModal('fileAttacherModal')"
  />
</template>

<script>
import { FileAttacherModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/instHisAppr';

export default {
  name: 'InstHisAppr',
  components: {
    FileAttacherModal,
  },
  mounted() {
    this.getInstHisApprList();
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
            this.getInstHisApprByHisSeq(e.item.hisSeq);
          },
        },
      },
      detail: {
        ...detail.static,
        columns: detail.columns(),
      },
      fileAttacherModal: {
        fileIdx: 0,
        show: false,
        readonly: true,
      },
    };
  },
  methods: {
    async getInstHisApprList() {
      const parameter = FormUtil.getData(this.list.forms);
      const data = await this.list.$grid
        ._useLoader(() => this.$axios.get('/in/instHisAppr', parameter))
        .then(({ data }) => data);

      this.list.$grid.setGridData(data);
    },
    async getInstHisApprByHisSeq(hisSeq) {
      const data = await this.detail.$grid
        ._useLoader(() => this.$axios.get(`/in/instHisAppr/${hisSeq}`))
        .then(({ data }) => data);

      this.detail.$grid.setGridData(data);
    },
    onClickButton({ name }) {
      if (name === 'search') {
        this.getInstHisApprList();
        return;
      }
      if (name === 'approve') {
        return this.approve();
      }
      if (name === 'reject') {
        return this.reject();
      }
    },
    approve() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const param = checkedRows.map(({ item }) => item);
      this.$eSignWithReason(() => this.$axios.put('/in/instHisAppr/approve', param))
        .then(() => {
          this.$info(this.$message.info.approve);
          this.getInstHisApprList();
          this.detail.$grid.clearGridData();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    reject() {
      const checkedRows = this.list.$grid.getCheckedRowItems();
      const param = checkedRows.map(({ item }) => item);
      this.$eSignWithReason(() => this.$axios.put('/in/instHisAppr/reject', param))
        .then(() => {
          this.$info(this.$message.info.reject);
          this.getInstHisApprList();
          this.detail.$grid.clearGridData();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    searchFormEvent(event) {
      if (event.type === 'keydown' && event.originEvent.key === 'Enter') {
        return this.getInstHisApprList();
      }
    },
    showModal(name) {
      if (name === 'fileAttacherModal') {
        this.fileAttacherModal.fileIdx = this.getFildIdx();
        return (this.fileAttacherModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'fileAttacherModal') {
        return (this.fileAttacherModal.show = false);
      }
    },
    getFildIdx() {
      const selectedItem = this.detail.$grid.getSelectedItems();
      return selectedItem[0].item.fileIdx;
    },
  },
};
</script>

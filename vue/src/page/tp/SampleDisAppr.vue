<template>
  <AUIGridSearch
    v-bind="sampleGrid"
    @grid-created="(proxy) => $setState('sampleGrid.$grid', proxy)"
    @button-click="onClickSampleGridButtons"
    @form-event="onDoubleClickSampleGridCell"
  />
  <FormWithHeader v-bind="inputForm" @button-click="onClickInputFormButtons" />
  <RejectionReasonModal :show="rejectionReasonModal.show"  @check="reject" @close="hideModal('rejectionReasonModal')"/>
</template>

<script>
import { RejectionReasonModal } from '@/page/modal';
import { FormUtil } from '@/util/index.js';

import values from './values/sampleDisAppr.js';

export default {
  name: 'sampleDisAppr',
  components: {
    RejectionReasonModal,
  },
  data() {
    const { sampleGrid, inputForm } = values;
    return {
      sampleGrid: {
        ...sampleGrid.static,
        forms: sampleGrid.forms(),
        columns: sampleGrid.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.onDoubleClickSampleGridCell(event);
          },
        },
      },
      inputForm: {
        ...inputForm.static,
        forms: inputForm.forms(),
      },
      rejectionReasonModal: {
        show: false,
      },
    };
  },
  methods: {
    init() {
      this.inputForm.forms = values.inputForm.forms();
    },
    async fetchSampleGrid() {
      const { forms, $grid } = this.sampleGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/tp/sampleDisAppr', param))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    async approve() {
      const { $grid } = this.sampleGrid;
      const checkedRow = $grid.getCheckedRowItems();
      const param = checkedRow.map((row) => ({
        ...row.item,
      }));
      await this.$eSign(() => this.$axios.put('/tp/sampleDisAppr/approve', param))
        .then(() => {
          this.$info(this.$message.info.message.approve);
          this.fetchSampleGrid();
          this.init();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    async reject({ rjtRea }) {
      const { $grid } = this.sampleGrid;
      const checkedRow = $grid.getCheckedRowItems();
      const param = checkedRow.map((row) => ({
        ...row.item,
        rjtRea,
      }));
      await this.$eSign(() => this.$axios.put('/tp/sampleDisAppr/reject', param))
        .then(() => {
          this.$info(this.$message.info.reject);
          this.fetchSampleGrid();
          this.init();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    showModal(name) {
      if (name === 'rejectionReasonModal') {
        return (this.rejectionReasonModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'rejectionReasonModal') {
        return (this.rejectionReasonModal.show = false);
      }
    },
    onDoubleClickSampleGridCell(event) {
      const forms = this.inputForm.forms;
      FormUtil.setData(forms, event.item);
    },
    onClickSampleGridButtons({ name }) {
      const { $grid } = this.sampleGrid;
      const checkedRows = $grid.getCheckedRowItems();
      if (name === 'search') {
        this.fetchSampleGrid();
      }
      if (name === 'approve') {
        if (checkedRows.length > 0) {
          this.approve();
        } else {
          return this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'reject') {
        if (checkedRows.length > 0) {
            this.showModal('rejectionReasonModal');
          } else {
            return this.$warn(this.$message.warn.unSelectedData);
          }
      }
    },
    onClickInputFormButtons({ name }) {
      if (name === 'init') {
        this.init();
      }
    },
  },
  mounted() {
    this.fetchSampleGrid();
  },
};
</script>

<style></style>

<template>
  <AUIGridSearch
    v-bind="sampleGrid"
    @grid-created="(proxy) => $setState('sampleGrid.$grid', proxy)"
    @button-click="onClickSampleGridButtons"
    @form-event="onDoubleClickSampleGridCell"
  />
  <FormWithHeader v-bind="inputForm" @button-click="onClickInputFormButtons" />
  <RejectionReasonModal :show="rejectionReasonModal.show" />
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
    init() {},
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
          this.$info(this.$message.info.message.reject);
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
      if (event.type === 'cellDoubleClick') {
        const forms = this.inputForm.forms;
        FormUtil.setData(forms, event.item);
      }
    },
    onClickSampleGridButtons({ name }) {
      if (name === 'search') {
        this.fetchSampleGrid();
      }
    },
    onClickInputFormButtons({ name }) {
      const { $grid } = this.sampleGrid;
      const checkedRows = $grid.getCheckedRowItems();
      if (checkedRows.length > 0) {
        if (name === 'approve') {
          this.approve();
        }
        if (name === 'reject') {
          this.reject();
        }
      } else {
        return this.$warn(this.$message.warn.unSelectedData);
      }
    },
  },
  mounted() {
    this.fetchSampleGrid();
  },
};
</script>

<style></style>

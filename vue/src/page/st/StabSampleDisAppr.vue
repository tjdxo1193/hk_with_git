<template>
  <AUIGridSearch
    v-bind="sampleGrid"
    @grid-created="(proxy) => $setState('sampleGrid.$grid', proxy)"
    @button-click="onClickGridButtons"
  />
  <RejectionReasonModal
    :show="rejectionReasonModal.show"
    @close="hideModal('rejectionReasonModal')"
    @check="reject"
  />
</template>

<script>
import { RejectionReasonModal } from '@/page/modal';
import { FormUtil } from '@/util/index.js';

import values from './values/stabSampleDisAppr.js';

export default {
  name: 'StabSampleDisAppr',
  components: {
    RejectionReasonModal,
  },
  data() {
    const { sampleGrid } = this.$copy(values);
    return {
      sampleGrid: {
        ...sampleGrid.static,
        forms: sampleGrid.forms(),
        columns: sampleGrid.columns(),
      },
      rejectionReasonModal: {
        show: false,
      },
    };
  },
  methods: {
    init() {
      this.sampleGrid.forms = values.sampleGrid.forms();
    },
    async fetchSampleGrid() {
      const { forms, $grid } = this.sampleGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/st/stabSampleDisAppr', param))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    async approve(checkedRow) {
      const param = checkedRow.map((rows) => ({
        ...rows.item,
      }));
      await this.$eSign(() => this.$axios.put('/st/stabSampleDisAppr/approve', param))
        .then(() => {
          this.$info(this.$message.info.approve);
          this.fetchSampleGrid();
          this.init();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    async reject({ rjtRea }) {
      const { $grid } = this.sampleGrid;
      const checkedRow = $grid.getCheckedRowItems();
      const param = checkedRow.map((rows) => ({
        ...rows.item,
        rjtRea,
      }));
      await this.$eSign(() => this.$axios.put('/st/stabSampleDisAppr/reject', param))
        .then(() => {
          this.$info(this.$message.info.reject);
          this.fetchSampleGrid();
          this.init();
        })
        .catch(() => {
          this.$error(this.$message.error.saveData);
        });
    },
    onClickGridButtons({ name }) {
      const { $grid } = this.sampleGrid;
      const checkedRow = $grid.getCheckedRowItems();
      if (name === 'search') {
        this.fetchSampleGrid();
      }
      if (name === 'approve') {
        if (checkedRow.length > 0) {
          this.approve(checkedRow);
        } else {
          this.$warn(this.$message.warn.unSelectedData);
        }
      }
      if (name === 'reject') {
        if (checkedRow.length > 0) {
          this.showModal('rejectionReasonModal');
        } else {
          this.$warn(this.$message.warn.unSelectedData);
        }
      }
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
  },
  mounted() {
    this.fetchSampleGrid();
  },
};
</script>

<style></style>

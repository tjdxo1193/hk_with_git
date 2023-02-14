<template>
  <AUIGridSearch
    v-bind="sampleGrid"
    @grid-created="(proxy) => $setState('sampleGrid.$grid', proxy)"
  />
  <FormWithHeader v-bind="inputForm" @button-click="onClickFormButtons" />
  <RejectionReasonModal
    :show="rejectionReasonModal.show"
    @close="hideModal('rejectionReasonModal')"
    @check="reject"
  />
</template>

<script>
import { RejectionReasonModal } from '@/page/modal';
import { FormUtil } from '@/util/index.js';

import values from './values/stabSampleUsageAppr.js';

export default {
  name: 'StabSampleUsageAppr',
  components: {
    RejectionReasonModal,
  },
  data() {
    const { sampleGrid, inputForm } = this.$copy(values);
    return {
      sampleGrid: {
        ...sampleGrid.static,
        forms: sampleGrid.forms(),
        columns: sampleGrid.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.onCellDoubleClick(event.item);
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
      const { buttons } = this.inputForm;
      FormUtil.enableButtons(buttons, ['init']);
      FormUtil.disableButtons(buttons, ['approve', 'reject']);
    },
    async fetchSampleGrid() {
      const { forms, $grid } = this.sampleGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/st/stabSampleUsageAppr', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
    },
    async approve() {
      const param = FormUtil.getData(this.inputForm.forms);
      await this.$eSign(() => this.$axios.put('/st/stabSampleUsageAppr/approve', param))
        .then(() => {
          this.$info(this.$message.info.approve);
          this.fetchSampleGrid();
          this.init();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    async reject({ rjtRea }) {
      const param = { ...FormUtil.getData(this.inputForm.forms), rjtRea };
      await this.$eSign(() => this.$axios.put('/st/stabSampleUsageAppr/reject', param))
        .then(() => {
          this.$info(this.$message.info.reject);
          this.fetchSampleGrid();
          this.init();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    onCellDoubleClick(data) {
      const { forms, buttons } = this.inputForm;
      FormUtil.setData(forms, data);
      FormUtil.enableButtons(buttons, ['approve', 'reject', 'init']);
    },
    onClickFormButtons({ name }) {
      if (name === 'init') {
        this.init();
      }
      if (name === 'approve') {
        this.approve();
      }
      if (name === 'reject') {
        this.showModal('rejectionReasonModal');
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
    this.init();
    this.fetchSampleGrid();
  },
};
</script>

<style></style>

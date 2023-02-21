<template>
  <AUIGridSearch
    v-bind="sampleUsageGrid"
    @grid-created="(proxy) => $setState('sampleUsageGrid.$grid', proxy)"
    @button-click="onClickSampleUsageGridButton"
  />
  <FormWithHeader v-bind="inputForm" @button-click="onClickInputFormButtons" />
  <RejectionReasonModal
    title="반려사유"
    :show="rejectionReasonModal.show"
    @close="hideModal('rejectionModal')"
    @check="reject"
  />
</template>

<script>
import { RejectionReasonModal } from '@/page/modal';
import { FormUtil } from '@/util/index.js';

import values from './values/sampleUsageAppr.js';

export default {
  name: 'SampleUsageAppr',
  components: {
    RejectionReasonModal,
  },
  data() {
    const { sampleUsageGrid, inputForm } = values;
    return {
      sampleUsageGrid: {
        ...sampleUsageGrid.static,
        forms: sampleUsageGrid.forms(),
        columns: sampleUsageGrid.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.setDataToForm(event.item);
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
    async fetchSampleUsageGrid() {
      const param = FormUtil.getData(this.sampleUsageGrid.forms);
      const { $grid } = this.sampleUsageGrid;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/tp/sampleUsageAppr', param))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    async approve(checkedRow) {
      const param = checkedRow.map((rows) => ({
        ...rows.item,
      }));
      await this.$eSign(() => this.$axios.put('/tp/sampleUsageAppr/approve', param))
        .then(() => {
          this.$info(this.$message.info.approve);
          this.fetchSampleUsageGrid();
          this.init();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    async reject({ rjtRea }) {
      const { $grid } = this.sampleUsageGrid;
      const checkedRow = $grid.getCheckedRowItems();
      const param = checkedRow.map((row) => ({
        ...row.item,
        rjtRea,
      }));
      await this.$eSign(() => this.$axios.put('/tp/sampleUsageAppr/reject', param))
        .then(() => {
          this.$info(this.$message.info.reject);
          this.fetchSampleUsageGrid();
          this.init();
        })
        .catch(() => this.$error(this.$message.error.saveData));
    },
    setDataToForm(data) {
      const { forms, buttons } = this.inputForm;
      FormUtil.setData(forms, data);
      FormUtil.disableButtons(buttons, ['save']);
      FormUtil.enableButtons(buttons, ['update', 'delete']);
    },
    onClickSampleUsageGridButton({ name }) {
      const { $grid } = this.sampleUsageGrid;
      const checkedRow = $grid.getCheckedRowItems();
      if (name === 'search') {
        this.fetchSampleUsageGrid();
        this.init();
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
          this.showModal('rejectionModal');
        } else {
          this.$warn(this.$message.warn.unSelectedData);
        }
      }
    },
    onClickInputFormButtons({ name }) {
      if (name === 'init') {
        this.init();
      }
    },
    showModal(name) {
      if (name === 'rejectionModal') {
        return (this.rejectionReasonModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'rejectionModal') {
        return (this.rejectionReasonModal.show = false);
      }
    },
  },
  mounted() {
    this.fetchSampleUsageGrid();
  },
};
</script>

<style></style>

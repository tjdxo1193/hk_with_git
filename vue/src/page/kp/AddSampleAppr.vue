<template>
  <AUIGridSearch
    v-bind="addSampleGrid"
    @grid-created="(proxy) => $setState('addSampleGrid.$grid', proxy)"
  />
  <FormWithHeader v-bind="inputForm" @button-click="onClickInputFormButtons" />
  <RejectionReasonModal
    :show="rejectionReasonModal.show"
    @close="hideModal('rejectionReasonModal')"
    @check="onReturnRejectionReasonModal"
  />
</template>

<script>
import { RejectionReasonModal } from '@/page/modal';
import { FormUtil } from '@/util/index.js';

import values from './values/addSampleAppr.js';

export default {
  name: 'AddSampleAppr',
  components: {
    RejectionReasonModal,
  },
  data() {
    const { addSampleGrid, inputForm } = values;
    return {
      addSampleGrid: {
        ...addSampleGrid.static,
        forms: addSampleGrid.forms(),
        columns: addSampleGrid.columns(),
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
    async fetchAddSampleGrid() {
      const { forms, $grid } = this.addSampleGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/kp/addSampleAppr', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
      this.init();
    },
    async approve() {
      const param = FormUtil.getData(this.inputForm.forms);
      await this.$eSign(() => this.$axios.put('/kp/addSampleAppr/approve', param))
        .then(() => {
          this.$info(this.$message.info.approve);
          this.fetchAddSampleGrid();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    async reject(param) {
      await this.$eSign(() => this.$axios.put('/kp/addSampleAppr/reject', param))
        .then(() => {
          this.$info(this.$message.info.reject);
          this.fetchAddSampleGrid();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    onReturnRejectionReasonModal(data) {
      const formData = FormUtil.getData(this.inputForm.forms);
      const param = {
        rjtRea: data.rjtRea,
        ...formData,
      };
      this.reject(param);
    },
    setDataToForm(data) {
      FormUtil.setData(this.inputForm.forms, data);
    },
    onClickInputFormButtons({ name }) {
      if (name === 'approve') {
        this.inputForm.forms
          .validate()
          .then(() => {
            this.approve();
          })
          .catch(() => {});
      }
      if (name === 'reject') {
        this.inputForm.forms
          .validate()
          .then(() => {
            this.showModal('rejectionReasonModal');
          })
          .catch(() => {});
      }
      if (name === 'init') {
        this.init();
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
    this.fetchAddSampleGrid();
  },
};
</script>

<style></style>

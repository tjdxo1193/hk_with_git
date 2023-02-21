<template>
  <AUIGridSearch
    v-bind="rePrintLabelApprGridWithForms"
    @grid-created="(proxy) => $setState('rePrintLabelApprGridWithForms.$grid', proxy)"
    @button-click="onButtonClick"
    @form-event="rePrintLabelApprGridWithFormsEvent"
  />

  <FormWithHeader v-bind="rePrintLabelApprInfoForms" @button-click="onButtonClick" />

  <InputReasonModal
    :show="inputReasonModal.show"
    @modalReturnDataEvent="modalReturnDataEvent"
    @close="hideModal('inputReasonModal')"
  />
</template>

<script>
import { InputReasonModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/rePrintLabelAppr';

export default {
  name: 'RePrintLabelAppr',
  components: { InputReasonModal },
  data() {
    const { rePrintLabelApprGridWithForms, rePrintLabelApprInfoForms } = this.$copy(values);
    return {
      rePrintLabelApprGridWithForms: {
        ...rePrintLabelApprGridWithForms.static,
        forms: rePrintLabelApprGridWithForms.forms(),
        columns: rePrintLabelApprGridWithForms.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.rePrintLabelApprGridWithFormsDoubleClicked(event);
          },
        },
      },
      rePrintLabelApprInfoForms: {
        ...rePrintLabelApprInfoForms.static,
        forms: rePrintLabelApprInfoForms.forms(),
      },
      inputReasonModal: {
        show: false,
        updateType: null,
      },
    };
  },
  mounted() {
    this.doInit();
  },
  methods: {
    doInit() {
      this.fetchRePrintLabelApprGridWithForms();
      this.resetRePrintLabelApprInfoForms();
    },
    async fetchRePrintLabelApprGridWithForms() {
      const { forms, $grid } = this.rePrintLabelApprGridWithForms;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/pr/rePrintLabelAppr', parameter))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));

      $grid.setGridData(data);
    },
    resetRePrintLabelApprInfoForms() {
      this.rePrintLabelApprInfoForms.forms = values.rePrintLabelApprInfoForms.forms();
    },
    rePrintLabelApprGridWithFormsDoubleClicked(event) {
      const { forms } = this.rePrintLabelApprInfoForms;
      const { item } = event;

      FormUtil.setData(forms, item);
    },
    onButtonClick({ name }) {
      if (name === 'select') {
        this.doInit();
      } else if (name === 'reset') {
        this.resetRePrintLabelApprInfoForms();
      } else if (name === 'approve') {
        this.reLabelPrintApprove();
      } else if (name === 'reject') {
        this.inputReasonModal.updateType = 'reject';
        this.showModal('inputReasonModal');
      }
    },
    rePrintLabelApprGridWithFormsEvent({ type, originEvent }) {
      if (type === 'keydown' && originEvent.key === 'Enter') {
        this.doInit();
      }
    },
    reLabelPrintApprove() {
      const { forms } = this.rePrintLabelApprInfoForms;
      const parameter = FormUtil.getData(forms);

      this.$eSign(() => this.$axios.put('/pr/rePrintLabelAppr', parameter))
        .then(() => {
          this.$info(this.$message.info.approve);
          this.doInit();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    reLabelPrintReject({ rjtRea }) {
      const { forms } = this.rePrintLabelApprInfoForms;
      const parameter = { rjtRea, ...FormUtil.getData(forms) };

      this.$eSign(() => this.$axios.put('/pr/rePrintLabelAppr/reject', parameter))
        .then(() => {
          this.$info(this.$message.info.reject);
          this.doInit();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    showModal(name) {
      if (name === 'inputReasonModal') {
        return (this.inputReasonModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'inputReasonModal') {
        return (this.inputReasonModal.show = false);
      }
    },
    modalReturnDataEvent({ rjtRea }) {
      const { updateType } = this.inputReasonModal;

      if (updateType && updateType == 'reject') {
        this.reLabelPrintReject({ rjtRea });
      }
    },
  },
};
</script>

<style></style>

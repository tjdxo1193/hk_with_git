<template>
  <AUIGridSearch
    v-bind="rePrintLabelRequestGridWithForms"
    @grid-created="(proxy) => $setState('rePrintLabelRequestGridWithForms.$grid', proxy)"
    @button-click="onButtonClick"
    @form-event="rePrintLabelRequestGridWithFormsEvent"
  />

  <FormWithHeader
    v-bind="rePrintLabelRequestInfoForms"
    @button-click="onButtonClick"
    @form-event="rePrintLabelRequestInfoFormsEvent"
  />

  <RequestApproverModal
    :show="requestApproverModal.show"
    :aprReqDiv="requestApproverModal.aprReqDiv"
    @modalReturnDataEvent="modalReturnDataEvent"
    @close="hideModal('requestApproverModal')"
  ></RequestApproverModal>

  <LabelListModal
    :show="labelListModal.show"
    :ansIdx="labelListModal.ansIdx"
    @labelListModalEvent="labelListModalEvent"
    @close="hideModal('labelListModal')"
  />
</template>

<script>
import { RequestApproverModal, LabelListModal } from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/rePrintLabelRequest';

export default {
  name: 'RePrintLabelRequest',
  components: { RequestApproverModal, LabelListModal },
  data() {
    const { rePrintLabelRequestGridWithForms, rePrintLabelRequestInfoForms } = this.$copy(values);
    return {
      rePrintLabelRequestGridWithForms: {
        ...rePrintLabelRequestGridWithForms.static,
        forms: rePrintLabelRequestGridWithForms.forms(),
        columns: rePrintLabelRequestGridWithForms.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.rePrintLabelRequestGridWithFormsDoubleClicked(event);
          },
        },
      },
      rePrintLabelRequestInfoForms: {
        ...rePrintLabelRequestInfoForms.static,
        forms: rePrintLabelRequestInfoForms.forms(),
      },
      requestApproverModal: {
        show: false,
        updateType: null,
        aprReqDiv: 'S0050022', // 내일 할 일 ☆ , S05코드 새로 발급받아야 함
      },
      labelListModal: {
        show: false,
        ansIdx: null,
      },
    };
  },
  mounted() {
    this.doInit();
  },
  methods: {
    doInit() {
      this.fetchRePrintLabelRequestGridWithForms();
      this.resetRePrintLabelRequestInfoForms();
    },
    async fetchRePrintLabelRequestGridWithForms() {
      const { forms, $grid } = this.rePrintLabelRequestGridWithForms;
      const parameter = FormUtil.getData(forms);

      const data = await $grid
        ._useLoader(() => this.$axios.get('/pr/rePrintLabelRequest', parameter))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));

      $grid.setGridData(data);
    },
    rePrintLabelRequestGridWithFormsDoubleClicked(event) {
      const { forms } = this.rePrintLabelRequestInfoForms;
      const { item } = event;

      FormUtil.setData(forms, item);

      // 라벨목록 ansIdx 바인딩
      const ansIdx = item?.ansIdx;
      if (ansIdx) {
        this.labelListModal.ansIdx = item.ansIdx;
        FormUtil.enableButtons(forms[0]._multiForms, ['labelListModal']);
      }
    },
    resetRePrintLabelRequestInfoForms() {
      this.rePrintLabelRequestInfoForms.forms = values.rePrintLabelRequestInfoForms.forms();
      this.resetLabelListModal();
    },
    resetLabelListModal() {
      this.labelListModal.show = false;
      this.labelListModal.ansIdx = null;
    },
    onButtonClick({ name }) {
      if (name === 'select') {
        this.doInit();
      } else if (name === 'reset') {
        this.resetRePrintLabelRequestInfoForms();
      } else if (name === 'rePrintLabelRequest') {
        const { forms } = this.rePrintLabelRequestInfoForms;
        forms.validate().then(() => {
          this.showModal('requestApproverModal');
          this.requestApproverModal.updateType = 'rePrintLabelRequest';
        });
      } else if (name === 'rePrintLabelCancel') {
        this.rePrintLabelCancel();
      }
    },
    rePrintLabelRequestGridWithFormsEvent({ type, originEvent }) {
      if (type === 'keydown' && originEvent.key === 'Enter') {
        this.doInit();
      }
    },
    rePrintLabelRequestInfoFormsEvent(event) {
      if (event.originEvent === 'labelListModal') {
        this.showModal('labelListModal');
      }
    },
    modalReturnDataEvent({ aprReqDiv, aprReqUid, aprUid }) {
      this.rePrintLabelRequest({ aprReqDiv, aprReqUid, aprUid });
    },
    rePrintLabelRequest({ aprReqDiv, aprReqUid, aprUid }) {
      const { forms } = this.rePrintLabelRequestInfoForms;
      const parameter = { ...FormUtil.getData(forms), aprReqDiv, aprReqUid, aprUid };

      forms
        .validate()
        .then(() => this.$eSign(() => this.$axios.put(`/pr/rePrintLabelRequest`, parameter)))
        .then(() => {
          this.$info(this.$message.info.approveRequest);
          this.doInit();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    rePrintLabelCancel() {
      const { forms } = this.rePrintLabelRequestInfoForms;
      const parameter = FormUtil.getData(forms);

      forms
        .validate()
        .then(() => this.$confirm(this.$message.confirm.canceld))
        .then(() => this.$eSign(() => this.$axios.put(`/pr/rePrintLabelRequest/cancel`, parameter)))
        .then(() => {
          this.$info(this.$message.info.canceld);
          this.doInit();
        })
        .catch(() => this.$error(this.$message.error.cancelData));
    },
    labelListModalEvent({ rptDiv, rptDivNm }) {
      const { forms } = this.rePrintLabelRequestInfoForms;
      FormUtil.setData(forms, { rptDiv, rptDivNm });
      this.hideModal('labelListModal');
    },
    showModal(name) {
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = true);
      } else if (name === 'labelListModal') {
        return (this.labelListModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = false);
      } else if (name === 'labelListModal') {
        return (this.labelListModal.show = false);
      }
    },
  },
};
</script>

<style></style>

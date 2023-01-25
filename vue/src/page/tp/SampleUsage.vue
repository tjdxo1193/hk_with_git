<template>
  <AUIGridSearch
    v-bind="sampleUsageGrid"
    @grid-created="(proxy) => $setState('sampleUsageGrid.$grid', proxy)"
    @button-click="onClickGridButtons"
  />
  <FormWithHeader
    v-bind="inputForm"
    @form-event="inputFormEvent"
    @button-click="onClickInputFormButtons"
  />
  <SampleSearchModal
    :show="sampleSearchModal.show"
    @close="hideModal('sampleSearchModal')"
    @onModalDataReturn="onSelectSample"
  />
  <RequestApproverModal
    :show="requestApproveUse.show"
    :aprReqDiv="requestApproveUse.aprReqDiv"
    @close="hideModal('requestApproveUse')"
    @modalReturnDataEvent="approveUseModalReturnDataEvent"
  />
  <RequestApproverModal
    :show="requestApproveCancelUse.show"
    :aprReqDiv="requestApproveCancelUse.aprReqDiv"
    @close="hideModal('requestApproveCancelUse')"
    @modalReturnDataEvent="approveCancelUseModalReturnDataEvent"
  />
</template>

<script>
import { SampleSearchModal, RequestApproverModal } from '@/page/modal';
import { FormUtil, TokenUtil } from '@/util/index.js';

import values from './values/sampleUsage.js';

const TEMP_SAVE = 'S0280100';
const REQUEST_USE = 'S0280200';
const REJECT_USE = 'S0280110';
const APPROVE_USE = 'S0280300';
const REQUEST_CANCEL_USE = 'S0280400';
const REJECT_CANCEL_USE = 'S0280310';
const APPROVE_USE_CANCEL = 'S0280500';

export default {
  name: 'SampleUsage',
  components: {
    SampleSearchModal,
    RequestApproverModal,
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
            this.setDataToForm(event);
          },
        },
      },
      inputForm: {
        ...inputForm.static,
        forms: inputForm.forms(),
      },
      sampleSearchModal: {
        show: false,
      },
      requestApproveUse: {
        show: false,
        aprReqDiv: 'S0050018',
      },
      requestApproveCancelUse: {
        show: false,
        aprReqDiv: 'S0050018',
      },
    };
  },
  mounted() {
    this.fetchSampleUsageGrid();
    this.init();
  },
  methods: {
    init() {
      const buttons = this.inputForm.buttons;
      this.inputForm.forms = values.inputForm.forms();
      this.setUserInfo();
      FormUtil.disableButtons(buttons, [
        'requestApproveUse',
        'update',
        'delete',
        'requestApproveCancelUse',
      ]);
      FormUtil.enableButtons(buttons, ['save', 'init']);
    },
    setUserInfo() {
      const form = this.inputForm.forms;
      const useNm = TokenUtil.myName();
      const useUid = TokenUtil.myId();
      FormUtil.setData(form, { useUid, useNm });
    },
    async fetchSampleUsageGrid() {
      const param = FormUtil.getData(this.sampleUsageGrid.forms);
      const { $grid } = this.sampleUsageGrid;
      const data = await $grid
        ._useLoader(() => this.$axios.get('/tp/sampleUsage', param))
        .then(({ data }) => data)
        .catch(() => {
          this.$error(this.$message.error.fetchData);
        });
      $grid.setGridData(data);
      this.init();
    },
    onClickGridButtons({ name }) {
      if (name === 'search') {
        this.fetchSampleUsageGrid();
        this.init();
      }
    },
    async approveUseModalReturnDataEvent(data) {
      const param = FormUtil.getData(this.inputForm.forms);
      await this.$eSign(() =>
        this.$axios.put('/tp/sampleUsage/requestApproveUse', { ...data, ...param }),
      )
        .then(() => {
          this.$info(this.$message.info.approveRequest);
          this.fetchSampleUsageGrid();
          this.init();
        })
        .catch(() => {
          this.$error(this.$message.error.saveData);
        });
    },
    async approveCancelUseModalReturnDataEvent(data) {
      const param = FormUtil.getData(this.inputForm.forms);
      await this.$eSign(() =>
        this.$axios.put('/tp/sampleUsage/requestApproveCancelUse', { ...data, ...param }),
      )
        .then(() => {
          this.$info(this.$message.info.approveRequest);
          this.fetchSampleUsageGrid();
          this.init();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    async save() {
      const param = FormUtil.getData(this.inputForm.forms);
      await this.$eSign(() => this.$axios.post('/tp/sampleUsage', param))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchSampleUsageGrid();
          this.init();
        })
        .catch(() => {
          this.$error(this.$message.error.saveData);
        });
    },
    async update() {
      const param = FormUtil.getData(this.inputForm.forms);
      await this.$eSign(() => this.$axios.put('/tp/sampleUsage', param))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.fetchSampleUsageGrid();
        })
        .catch(() => {
          this.$error(this.$message.error.saveData);
        });
    },
    async delete() {
      const param = FormUtil.getData(this.inputForm.forms);
      await this.$eSign(() => this.$axios.put('/tp/sampleUsage/delete', param))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.init();
          this.fetchSampleUsageGrid();
        })
        .catch(() => {
          this.$error(this.$message.error.deleteData);
        });
    },
    onClickInputFormButtons({ name }) {
      if (name === 'requestApproveUse') {
        this.inputForm.forms
          .validate()
          .then(() => {
            this.showModal('requestApproveUse');
          })
          .catch(() => {});
      }
      if (name === 'save') {
        this.inputForm.forms
          .validate()
          .then(() => this.save())
          .catch(() => {});
      }
      if (name === 'update') {
        this.inputForm.forms
          .validate()
          .then(() => this.update())
          .catch(() => {});
      }
      if (name === 'delete') {
        this.inputForm.forms
          .validate()
          .then(() => this.delete())
          .catch(() => {});
      }
      if (name === 'requestApproveCancelUse') {
        this.inputForm.forms
          .validate()
          .then(() => {
            this.showModal('requestApproveCancelUse');
          })
          .catch(() => {});
      }
      if (name === 'init') {
        this.init();
      }
    },
    setDataToForm(event) {
      const data = event.item;
      const forms = this.inputForm.forms;
      FormUtil.setData(forms, data);
      this.setUserInfo();
      this.setButtonsEnable(data.smpUseProc);
    },
    onSelectSample(data) {
      const forms = this.inputForm.forms;
      FormUtil.setData(forms, data);
      this.setUserInfo();
    },
    inputFormEvent(event) {
      if (event.originEvent === 'search') {
        this.showModal('sampleSearchModal');
      }
    },
    setButtonsEnable(status) {
      const buttons = this.inputForm.buttons;
      if (status === TEMP_SAVE) {
        FormUtil.disableButtons(buttons, ['save']);
        FormUtil.enableButtons(buttons, [
          'requestApproveUse',
          'update',
          'requestApproveCancelUse',
          'delete',
        ]);
      }
      if (status === REQUEST_USE || status === REQUEST_CANCEL_USE) {
        FormUtil.disableButtons(buttons, [
          'save',
          'requestApproveUse',
          'update',
          'delete',
          'requestApproveCancelUse',
        ]);
        FormUtil.enableButtons(buttons, ['init']);
      }
      if (status === REJECT_USE || status === REJECT_CANCEL_USE) {
        FormUtil.disableButtons(buttons, ['save']);
        FormUtil.enableButtons(buttons, [
          'requestApproveUse',
          'update',
          'delete',
          'requestApproveCancelUse',
        ]);
      }
      if (status === APPROVE_USE || status === APPROVE_USE_CANCEL) {
        FormUtil.disableButtons(buttons, [
          'save',
          'requestApproveUse',
          'update',
          'delete',
          'requestApproveCancelUse',
        ]);
        FormUtil.enableButtons(buttons, ['init']);
      }
    },
    showModal(name) {
      if (name === 'sampleSearchModal') {
        return (this.sampleSearchModal.show = true);
      }
      if (name === 'requestApproveUse') {
        return (this.requestApproveUse.show = true);
      }
      if (name === 'requestApproveCancelUse') {
        return (this.requestApproveCancelUse.show = true);
      }
    },
    hideModal(name) {
      if (name === 'sampleSearchModal') {
        return (this.sampleSearchModal.show = false);
      }
      if (name === 'requestApproveUse') {
        return (this.requestApproveUse.show = false);
      }
      if (name === 'requestApproveCancelUse') {
        return (this.requestApproveCancelUse.show = false);
      }
    },
  },
};
</script>

<style></style>

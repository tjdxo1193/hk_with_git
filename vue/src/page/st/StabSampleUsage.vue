<template>
  <AUIGridSearch
    v-bind="sampleUsageGrid"
    @button-click="fetchSampleUsageGrid"
    @grid-created="(proxy) => $setState('sampleUsageGrid.$grid', proxy)"
  />
  <FormWithHeader v-bind="inputForm" @button-click="onClickFormButtons" @form-event="onFormEvent" />
  <StabSampleItemSearchModal
    v-bind="stabSampleItemSearchModal"
    @close="hideModal('stabSampleItemSearchModal')"
    @select="onSelectSample"
    :show="stabSampleItemSearchModal.show"
  />
  <RequestApproverModal
    :show="requestApproveUse.show"
    :aprReqDiv="requestApproveUse.aprReqDiv"
    @close="hideModal('requestApproveUse')"
    @modalReturnDataEvent="approveUseModalReturnDataEvent"
  />
  <RequestApproverModal
    :show="requestCancelUse.show"
    :aprReqDiv="requestCancelUse.aprReqDiv"
    @close="hideModal('requestCancelUse')"
    @modalReturnDataEvent="requestCancelUseModalReturnDataEvent"
  />
</template>

<script>
import dayjs from 'dayjs';

import { StabSampleItemSearchModal, RequestApproverModal } from '@/page/modal';
import { FormUtil, TokenUtil } from '@/util/index.js';

import values from './values/stabSampleUsage.js';

const TEMP_SAVE = 'S0280100';
const REQUEST_USE = 'S0280200';
const REJECT_USE = 'S0280110';
const APPROVE_USE = 'S0280300';
const REQUEST_CANCEL_USE = 'S0280400';
const REJECT_CANCEL_USE = 'S0280310';
const APPROVE_USE_CANCEL = 'S0280500';

export default {
  name: 'stabSampleUsage',
  components: {
    StabSampleItemSearchModal,
    RequestApproverModal,
  },
  data() {
    const { sampleUsageGrid, inputForm } = this.$copy(values);
    return {
      sampleUsageGrid: {
        ...sampleUsageGrid.static,
        forms: sampleUsageGrid.forms(),
        columns: sampleUsageGrid.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.onDoubleClickCell(event.item);
          },
        },
      },
      inputForm: {
        ...inputForm.static,
        forms: inputForm.forms(),
      },
      stabSampleItemSearchModal: {
        show: false,
      },
      requestApproveUse: {
        show: false,
        aprReqDiv: 'S0050018',
      },
      requestCancelUse: {
        show: false,
        aprReqDiv: 'S0050018',
      },
    };
  },
  methods: {
    init() {
      this.inputForm.forms = values.inputForm.forms();
      const buttons = this.inputForm.buttons;
      FormUtil.disableButtons(buttons, [
        'requestApproveUse',
        'update',
        'delete',
        'requestCancelUse',
      ]);
      FormUtil.enableButtons(buttons, ['save', 'init']);
      this.setDefaultInfo();
    },
    setDefaultInfo() {
      const { forms } = this.inputForm;
      const todayDate = dayjs().format('YYYY-MM-DD');
      const defaultInfo = {
        useUid: TokenUtil.myId(),
        useNm: TokenUtil.myName(),
        useDt: todayDate,
      };
      FormUtil.setData(forms, defaultInfo);
    },
    async fetchSampleUsageGrid() {
      const { forms, $grid } = this.sampleUsageGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/st/stabSampleUsage', param))
        .then(({ data }) => data);
      $grid.setGridData(data);
    },
    async save() {
      const { forms } = this.inputForm;
      const param = FormUtil.getData(forms);
      if (this.isBiggerThanSmpVol()) {
        if (this.isPositive()) {
          await this.$eSign(() => this.$axios.post('/st/stabSampleUsage', param))
            .then(() => {
              this.$info(this.$message.info.saved);
              this.fetchSampleUsageGrid();
              this.init();
            })
            .catch(() => {
              this.$error(this.$message.error.saveData);
            });
        } else {
          return this.$warn(this.$message.warn.noNegativeNumber);
        }
      } else {
        return this.$warn(this.$message.warn.biggerThanRemains);
      }
    },
    async update() {
      const { forms } = this.inputForm;
      const param = FormUtil.getData(forms);
      if (this.isBiggerThanSmpVol()) {
        if (this.isPositive()) {
          await this.$eSign(() => this.$axios.put('/st/stabSampleUsage', param))
            .then(() => {
              this.$info(this.$message.info.saved);
              this.fetchSampleUsageGrid();
              this.init();
            })
            .catch(() => {
              this.$error(this.$message.error.saveData);
            });
        } else {
          return this.$warn(this.$message.warn.noNegativeNumber);
        }
      } else {
        return this.$warn(this.$message.warn.biggerThanRemains);
      }
    },
    async delete() {
      const { forms } = this.inputForm;
      const param = FormUtil.getData(forms);
      await this.$eSign(() => this.$axios.put('/st/stabSampleUsage/delete', param))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchSampleUsageGrid();
          this.init();
        })
        .catch(() => {
          this.$error(this.$message.error.saveData);
        });
    },
    async approveUseModalReturnDataEvent(data) {
      const param = FormUtil.getData(this.inputForm.forms);
      await this.$eSign(() =>
        this.$axios.put('/st/stabSampleUsage/requestApproveUse', { ...data, ...param }),
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
    async requestCancelUseModalReturnDataEvent(data) {
      const param = FormUtil.getData(this.inputForm.forms);
      await this.$eSign(() =>
        this.$axios.put('/st/stabSampleUsage/requestCancelUse', { ...data, ...param }),
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
    isBiggerThanSmpVol() {
      const { forms } = this.inputForm;
      const remains = FormUtil.getValue(forms, 'remains');
      const useSmpVol = FormUtil.getValue(forms, 'useSmpVol');
      return Number(remains) >= Number(useSmpVol);
    },
    isPositive() {
      const { forms } = this.inputForm;
      const useSmpVol = FormUtil.getValue(forms, 'useSmpVol');
      return Number(useSmpVol) > 0;
    },
    onSelectSample(event) {
      const { forms } = this.inputForm;
      FormUtil.setData(forms, event[0]);
      this.setDefaultInfo();
    },
    onDoubleClickCell(data) {
      const { forms } = this.inputForm;
      FormUtil.setData(forms, data);
      this.setButtonsEnable(data.smpUseProc);
    },
    setButtonsEnable(status) {
      const { buttons } = this.inputForm;
      if (status === TEMP_SAVE || status === REJECT_USE) {
        FormUtil.enableButtons(buttons, ['init', 'requestApproveUse', 'update', 'delete']);
        FormUtil.disableButtons(buttons, ['save', 'requestCancelUse']);
      } else if (
        status === REQUEST_USE ||
        status === REQUEST_CANCEL_USE ||
        status === APPROVE_USE_CANCEL
      ) {
        FormUtil.enableButtons(buttons, ['init']);
        FormUtil.disableButtons(buttons, [
          'requestApproveUse',
          'update',
          'delete',
          'save',
          'requestCancelUse',
        ]);
      } else if (status === APPROVE_USE || status === REJECT_CANCEL_USE) {
        FormUtil.enableButtons(buttons, ['init', 'requestCancelUse']);
        FormUtil.disableButtons(buttons, ['requestApproveUse', 'update', 'delete', 'save']);
      }
    },
    onClickFormButtons({ name }) {
      if (name === 'requestApproveUse') {
        this.inputForm.forms
          .validate()
          .then(() => {
            if (this.isBiggerThanSmpVol()) {
              if (this.isPositive()) {
                this.showModal('requestApproveUse');
              } else {
                return this.$warn(this.$message.warn.noNegativeNumber);
              }
            } else {
              return this.$warn(this.$message.warn.biggerThanRemains);
            }
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
        this.delete();
      }
      if (name === 'requestCancelUse') {
        this.inputForm.forms
          .validate()
          .then(() => {
            this.showModal('requestCancelUse');
          })
          .catch(() => {});
      }
      if (name === 'init') {
        this.init();
      }
    },
    onFormEvent(event) {
      if (event.originEvent === 'sampleSearch') {
        this.showModal('stabSampleItemSearchModal');
        this.init();
      }
    },
    showModal(name) {
      if (name === 'stabSampleItemSearchModal') {
        return (this.stabSampleItemSearchModal.show = true);
      }
      if (name === 'requestApproveUse') {
        return (this.requestApproveUse.show = true);
      }
      if (name === 'requestCancelUse') {
        return (this.requestCancelUse.show = true);
      }
    },
    hideModal(name) {
      if (name === 'stabSampleItemSearchModal') {
        return (this.stabSampleItemSearchModal.show = false);
      }
      if (name === 'requestApproveUse') {
        return (this.requestApproveUse.show = false);
      }
      if (name === 'requestCancelUse') {
        return (this.requestCancelUse.show = false);
      }
    },
  },
  mounted() {
    this.fetchSampleUsageGrid();
    this.init();
  },
};
</script>

<style></style>

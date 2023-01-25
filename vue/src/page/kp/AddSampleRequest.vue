<template>
  <AUIGridSearch
    v-bind="addSampleGrid"
    @grid-created="(proxy) => $setState('addSampleGrid.$grid', proxy)"
    @button-click="fetchAddSampleGrid"
  />
  <FormWithHeader
    v-bind="inputForm"
    @form-event="inputFormEvent"
    @button-click="onClickInputFormButtons"
  />
  <TestSearchModal
    :show="testSearchModal.show"
    @close="hideModal('testSearchModal')"
    @onModalDataReturn="onReturnTestSearchModal"
  />
  <InputReasonForDeleteModal
    :show="inputReasonForDeleteModal.show"
    @close="hideModal('inputReasonForDeleteModal')"
    @onModalDataReturn="onReturnInputReasonForDeleteModal"
  />
  <RequestApproverModal
    :show="requestApproverModal.show"
    :aprReqDiv="requestApproverModal.aprReqDiv"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="onReturnRequestApproverModal"
  />
</template>

<script>
const TEMP_SAVE = 'S0320100';
const REQUEST_APPROVE = 'S0320200';
const REJECTED = 'S0320110';
const APPROVED = 'S0320300';

import { TestSearchModal, InputReasonForDeleteModal, RequestApproverModal } from '@/page/modal';
import { FormUtil } from '@/util/index.js';

import values from './values/addSampleRequest.js';

export default {
  name: 'AddSampleRequest',
  components: {
    TestSearchModal,
    InputReasonForDeleteModal,
    RequestApproverModal,
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
      testSearchModal: {
        show: false,
      },
      inputReasonForDeleteModal: {
        show: false,
      },
      requestApproverModal: {
        show: false,
        aprReqDiv: 'S0050021',
      },
    };
  },
  methods: {
    init() {
      this.inputForm.forms = values.inputForm.forms();
      FormUtil.disableButtons(this.inputForm.buttons, ['requestApprove', 'update', 'delete']);
      FormUtil.enableButtons(this.inputForm.buttons, ['save', 'init']);
    },
    async fetchAddSampleGrid() {
      const { forms, $grid } = this.addSampleGrid;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/kp/addSampleRequest', param))
        .then(({ data }) => data)
        .catch(() => this.$error(this.$message.error.fetchData));
      $grid.setGridData(data);
      this.init();
    },
    setDataToForm(data) {
      const addSmpProc = data.addSmpProc;
      if (addSmpProc === TEMP_SAVE || addSmpProc === REJECTED) {
        FormUtil.disableButtons(this.inputForm.buttons, ['save']);
        FormUtil.enableButtons(this.inputForm.buttons, [
          'requestApprove',
          'update',
          'delete',
          'init',
        ]);
      }
      if (addSmpProc === REQUEST_APPROVE || addSmpProc === APPROVED) {
        FormUtil.disableButtons(this.inputForm.buttons, [
          'requestApprove',
          'save',
          'update',
          'delete',
        ]);
        FormUtil.enableButtons(this.inputForm.buttons, ['init']);
      }
      FormUtil.setData(this.inputForm.forms, data);
    },
    async save() {
      const param = FormUtil.getData(this.inputForm.forms);
      await this.$confirm(this.$message.confirm.saveData).then(() => {
        this.$eSign(() => this.$axios.post('/kp/addSampleRequest', param))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.fetchAddSampleGrid();
          })
          .catch(() => {
            this.$error(this.$message.error.saveData);
          });
      });
    },
    async update() {
      const param = FormUtil.getData(this.inputForm.forms);
      await this.$confirm(this.$message.confirm.updated).then(() => {
        this.$eSign(() => this.$axios.put('/kp/addSampleRequest', param))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.fetchAddSampleGrid();
          })
          .catch(() => {
            this.$error(this.$message.error.updateData);
          });
      });
    },
    async delete(param) {
      await this.$eSign(() => this.$axios.put('/kp/addSampleRequest/delete', param))
        .then(() => {
          this.$info(this.$message.info.deleted);
          this.fetchAddSampleGrid();
        })
        .catch(() => {
          this.$error(this.$message.error.deleteData);
        });
    },
    async requestApprove(param) {
      await this.$eSign(() => this.$axios.put('/kp/addSampleRequest/requestApprove', param))
        .then(() => {
          this.$info(this.$message.info.approveRequest);
          this.fetchAddSampleGrid();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    onClickInputFormButtons({ name }) {
      if (name === 'requestApprove') {
        this.inputForm.forms
          .validate()
          .then(() => {
            this.$confirm(this.$message.confirm.requestApproved).then(() => {
              this.showModal('requestApproverModal');
            });
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
          .then(() => {
            this.$confirm(this.$message.confirm.deleted).then(() => {
              this.showModal('inputReasonForDeleteModal');
            });
          })
          .catch(() => {});
      }
      if (name === 'init') {
        this.init();
      }
    },
    onReturnTestSearchModal(data) {
      FormUtil.setData(this.inputForm.forms, data);
    },
    onReturnInputReasonForDeleteModal(data) {
      const formData = FormUtil.getData(this.inputForm.forms);
      const param = {
        delRea: data.delRea,
        ...formData,
      };
      this.delete(param);
    },
    onReturnRequestApproverModal(data) {
      const formData = FormUtil.getData(this.inputForm.forms);
      const param = {
        ...data,
        ...formData,
      };
      this.requestApprove(param);
    },
    inputFormEvent(event) {
      const forms = this.inputForm.forms;
      if (event.originEvent === 'search') {
        this.showModal('testSearchModal');
      }
      if (
        event.type === 'input' &&
        event.item.name === 'smpReqRea' &&
        event.item.value === 'U0250002'
      ) {
        FormUtil.toRequired(forms, 'smpReqReaDtl');
      }
      if (
        event.type === 'input' &&
        event.item.name === 'smpReqRea' &&
        event.item.value === 'U0250001'
      ) {
        FormUtil.toOptional(forms, 'smpReqReaDtl');
      }
    },
    showModal(name) {
      if (name === 'testSearchModal') {
        return (this.testSearchModal.show = true);
      }
      if (name === 'inputReasonForDeleteModal') {
        return (this.inputReasonForDeleteModal.show = true);
      }
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'testSearchModal') {
        return (this.testSearchModal.show = false);
      }
      if (name === 'inputReasonForDeleteModal') {
        return (this.inputReasonForDeleteModal.show = false);
      }
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = false);
      }
    },
  },
  mounted() {
    this.init();
    this.fetchAddSampleGrid();
  },
};
</script>

<style></style>

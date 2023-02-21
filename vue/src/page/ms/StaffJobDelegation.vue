<template>
  <FormWithHeader
    v-bind="searchForm"
    @button-click="onClickSearchFormButton"
    @form-event="searchFormEvent"
  />
  <AUIGridWithHeader
    v-bind="searchResultGrid"
    @grid-created="(proxy) => $setState('searchResultGrid.$grid', proxy)"
  />
  <FormWithHeader v-bind="staffInfoForm" @button-click="onClickStaffInfoFormButton" />
  <RequestApproverModal
    :show="requestApproverModal.show"
    :aprReqDiv="aprReqDiv"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="approveModalReturnDataEvent"
  />
</template>

<script>
import { RequestApproverModal } from '@/page/modal';
import { FormUtil, ObjectUtil, TokenUtil } from '@/util';

import values from './values/staffJobDelegation';

export default {
  name: 'staffJobDelegation',
  components: {
    RequestApproverModal,
  },
  mounted() {
    this.initStaffInfoForm();
    this.fetchSearchResultData();
  },
  data() {
    const { searchForm, searchResultGrid, staffInfoForm } = this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
      },
      searchResultGrid: {
        ...searchResultGrid.static,
        columns: searchResultGrid.columns(),
        event: {
          cellDoubleClick: (event) => {
            this.setDataToStaffInfoFormByDoubleClickEvent(event);
          },
        },
      },
      staffInfoForm: {
        ...staffInfoForm.static,
        forms: staffInfoForm.forms(),
      },
      requestApproverModal: {
        show: false,
      },
      aprReqDiv: 'S0050009',
    };
  },
  methods: {
    initStaffInfoForm() {
      this.staffInfoForm.forms = values.staffInfoForm.forms();
      const forms = this.staffInfoForm.forms;
      const userData = {
        userNm: TokenUtil.myName(),
        userId: TokenUtil.myId(),
        dlgRea: undefined,
      };
      FormUtil.setData(forms, userData);
    },
    async fetchSearchResultData() {
      const { $grid } = this.searchResultGrid;
      const { forms } = this.searchForm;
      const param = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('/ms/staffJobDelegation', param))
        .then(({ data }) => data);
      this.searchResultGrid.$grid.setGridData(data);
    },
    async createJobDelegation() {
      const param = FormUtil.getData(this.staffInfoForm.forms);
      await this.$eSign(() => this.$axios.post('/ms/staffJobDelegation', param))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.fetchSearchResultData();
          this.initStaffInfoForm();
        })
        .catch(() => this.$error(this.$message.error.createData));
    },
    async updateGbkRegist() {
      const param = FormUtil.getData(this.staffInfoForm.forms);
      await this.$eSign(() => this.$axios.put('/ms/staffJobDelegation/gbkRegist', param))
        .then(() => {
          this.$info(this.$message.info.updated);
          this.initStaffInfoForm();
          this.fetchSearchResultData();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    async update() {
      const param = FormUtil.getData(this.staffInfoForm.forms);
      await this.$eSign(() => this.$axios.put('/ms/staffJobDelegation', param))
        .then(() => {
          this.$info(this.$message.info.updated);
          this.fetchSearchResultData();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    async approveModalReturnDataEvent(data) {
      const formData = FormUtil.getData(this.staffInfoForm.forms);
      const param = ObjectUtil.mergeObject(formData, data);
      await this.$eSign(() => this.$axios.put('/ms/staffJobDelegation/apprRequest', param))
        .then(() => {
          this.$info(this.$message.info.approveRequest);
          this.fetchSearchResultData();
        })
        .catch(() => this.$error(this.$message.error.updateData));
    },
    searchFormEvent({ originEvent }) {
      if (originEvent.key === 'Enter') {
        this.fetchSearchResultData();
        this.initStaffInfoForm();
      }
    },
    setDataToStaffInfoFormByDoubleClickEvent(event) {
      const forms = this.staffInfoForm.forms;
      const data = event.item;
      const buttons = this.staffInfoForm.buttons;
      FormUtil.setData(forms, data);
      const dlgProcCd = data.dlgProcCd;
      if (dlgProcCd === 'S0150100') {
        FormUtil.readonly(forms, ['userNm']);
        FormUtil.enable(forms, ['dlgUid', 'dlgRea']);
        FormUtil.enableButtons(buttons, ['apprRequest', 'update', 'init']);
        FormUtil.disableButtons(buttons, ['jobDelegation', 'gbkRegist']);
      }
      if (dlgProcCd === 'S0150110') {
        FormUtil.readonly(forms, ['userNm']);
        FormUtil.enable(forms, ['dlgUid', 'dlgRea']);
        FormUtil.enableButtons(buttons, ['apprRequest', 'update', 'init']);
        FormUtil.disableButtons(buttons, ['jobDelegation', 'gbkRegist']);
      }
      if (dlgProcCd === 'S0150200') {
        FormUtil.disable(forms, ['userNm', 'dlgUid', 'dlgRea']);
        FormUtil.enableButtons(buttons, ['init']);
        FormUtil.disableButtons(buttons, ['apprRequest', 'jobDelegation', 'gbkRegist', 'update']);
      }
      if (dlgProcCd === 'S0150300') {
        FormUtil.readonly(forms, ['userNm', 'dlgUid', 'dlgRea']);
        FormUtil.enableButtons(buttons, ['gbkRegist', 'init']);
        FormUtil.disableButtons(buttons, ['apprRequest', 'jobDelegation', 'update']);
      }
      const dlgSttCd = event.item.dlgSttCd;
      if (dlgSttCd === 'S0160001') {
        FormUtil.disable(forms, ['userNm', 'dlgUid', 'dlgRea']);
        FormUtil.enableButtons(buttons, ['init', 'gbkRegist']);
        FormUtil.disableButtons(buttons, ['apprRequest', 'jobDelegation', 'update']);
      }
      if (dlgSttCd === 'S0160002') {
        FormUtil.disable(forms, ['userNm', 'dlgUid', 'dlgRea']);
        FormUtil.enableButtons(buttons, ['init']);
        FormUtil.disableButtons(buttons, ['gbkRegist', 'apprRequest', 'jobDelegation', 'update']);
      }
    },
    showModal(name) {
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = true);
      }
    },
    hideModal(name) {
      if (name === 'requestApproverModal') {
        return (this.requestApproverModal.show = false);
      }
    },
    onClickSearchFormButton({ name }) {
      if (name === 'search') {
        this.fetchSearchResultData();
        this.initStaffInfoForm();
      }
    },
    onClickStaffInfoFormButton({ name }) {
      const buttons = this.staffInfoForm.buttons;
      if (name === 'apprRequest') {
        FormUtil.enableButtons(buttons, ['init']);
        FormUtil.disableButtons(buttons, ['apprRequest', 'JobDelegation', 'GbkRegist', 'update']);
        this.showModal('requestApproverModal');
      }
      if (name === 'jobDelegation') {
        this.staffInfoForm.forms
          .validate()
          .then(() => this.createJobDelegation())
          .catch(() => {});
      }
      if (name === 'gbkRegist') {
        FormUtil.enableButtons(buttons, ['init']);
        FormUtil.disableButtons(buttons, ['apprRequest', 'JobDelegation', 'GbkRegist', 'update']);
        this.updateGbkRegist();
      }
      if (name === 'update') {
        FormUtil.enableButtons(buttons, ['apprRequest', 'update', 'init']);
        FormUtil.disableButtons(buttons, ['jobDelegation', 'gbkRegist']);
        this.staffInfoForm.forms
          .validate()
          .then(() => this.update())
          .catch(() => {});
      }
      if (name === 'init') {
        FormUtil.enableButtons(buttons, ['jobDelegation', 'init']);
        FormUtil.disableButtons(buttons, ['apprRequest', 'gbkRegist', 'update']);
        this.initStaffInfoForm();
      }
    },
  },
};
</script>
